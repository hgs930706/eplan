package com.lcm.web.rest;

import com.lcm.domain.Special;
import com.lcm.repository.SpecialRepository;
import com.lcm.service.SpecialService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/special")
@RestController
public class SpecialController {

    private final SpecialService specialService;
    private final SpecialRepository specialRepository;

    public SpecialController(SpecialService specialService, SpecialRepository specialRepository) {
        this.specialService = specialService;
        this.specialRepository = specialRepository;
    }

    @GetMapping("")
    public ResponseEntity<Page<Special>> index(@QuerydslPredicate(root = Special.class) Predicate predicate,
                                                                      @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(specialRepository.findAll(predicate, pageable));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody MultipartFile file) {
        try {
            specialService.parseExcel(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }

    @PutMapping("")
    public ResponseEntity<String> update(String changeLevel, String planQty,String jobType,String remark, @RequestParam String jobId) {
        specialService.update(changeLevel, planQty, jobType, remark, jobId);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("")
    public ResponseEntity<String> delete(@RequestParam String jobIds) {
        List<Special> eqpms = Arrays.stream(jobIds.split(",")).map(jobId -> {
            Special special = new Special();
            special.setJobId(jobId);
            return special;
        }).collect(Collectors.toList());
        specialRepository.deleteInBatch(eqpms);
        return ResponseEntity.ok("success");
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody Special special) {
        specialService.save(special);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/export")
    public void exportExcel(@QuerydslPredicate(root = Special.class) Predicate predicate, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = "Special";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
        specialService.exportExcel(predicate, out);
    }
}
