package com.lcm.web.rest;

import com.lcm.domain.ModChange;
import com.lcm.domain.QModChange;
import com.lcm.repository.ModChangeRepository;
import com.lcm.service.ModChangeService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/modChange")
public class ModChangeController {

    private final ModChangeRepository modChangeRepository;
    private final ModChangeService modChangeService;

    public ModChangeController(ModChangeRepository modChangeRepository, ModChangeService modChangeService) {
        this.modChangeRepository = modChangeRepository;
        this.modChangeService = modChangeService;
    }

    @GetMapping("")
    public ResponseEntity<Page<ModChange>> index(String site,String fab ,String area ,String lineStr, @PageableDefault Pageable pageable) {
        QModChange query = QModChange.modChange;
        Predicate predicate = query.isNotNull();
        if ("%".equals(lineStr)) {
            if(!StringUtils.isEmpty(site)){
                predicate = ExpressionUtils.and(predicate,query.site.eq(site));
            }
            if(!StringUtils.isEmpty(fab)){
                predicate = ExpressionUtils.and(predicate,query.fab.eq(fab).or(query.fab.eq("%")));
            }
            if(!StringUtils.isEmpty(area)){
                predicate = ExpressionUtils.and(predicate,query.area.eq(area).or(query.area.eq("")));
            }
            predicate = ExpressionUtils.and(predicate, QModChange.modChange.line.eq(lineStr));
        } else {
            if(!StringUtils.isEmpty(site)){
                predicate = ExpressionUtils.and(predicate,query.site.eq(site));
            }
            if(!StringUtils.isEmpty(fab)){
                predicate = ExpressionUtils.and(predicate,query.fab.eq(fab).or(query.fab.eq("%")));
            }
            if(!StringUtils.isEmpty(area)){
                predicate = ExpressionUtils.and(predicate,query.area.eq(area).or(query.area.eq("")));
            }
            predicate = ExpressionUtils.and(predicate, QModChange.modChange.line.like("%" + lineStr + "%"));
        }
        return ResponseEntity.ok(modChangeRepository.findAll(predicate, pageable));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam String site, @RequestBody MultipartFile file) {
        try {
            modChangeService.parseExcel(site, file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("")
    public ResponseEntity<String> delete(@RequestParam String ids) {
        List<ModChange> changes = Arrays.stream(ids.split(",")).map(jobId -> {
            ModChange change = new ModChange();
            change.setChangeKey(jobId);
            return change;
        }).collect(Collectors.toList());
        modChangeRepository.deleteInBatch(changes);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/export")
    public void exportExcel(@RequestParam String site, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = "c_mod_change";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        modChangeService.exportExcel(site, out);
    }
}
