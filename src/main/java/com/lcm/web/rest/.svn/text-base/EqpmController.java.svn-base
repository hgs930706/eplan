package com.lcm.web.rest;

import com.lcm.domain.EquipmentPreventiveMaintenance;
import com.lcm.repository.EquipmentPreventiveMaintenanceRepository;
import com.lcm.service.PmService;
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

@RequestMapping("/pm")
@RestController
public class EqpmController {

    private final EquipmentPreventiveMaintenanceRepository eqpmRepository;
    private final PmService pmService;

    public EqpmController(EquipmentPreventiveMaintenanceRepository eqpmRepository, PmService pmService) {
        this.eqpmRepository = eqpmRepository;
        this.pmService = pmService;
    }

    @GetMapping("")
    public ResponseEntity<Page<EquipmentPreventiveMaintenance>> index(@QuerydslPredicate(root = EquipmentPreventiveMaintenance.class) Predicate predicate,
                                                                      @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(eqpmRepository.findAll(predicate, pageable));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody MultipartFile file) {
        try {
            pmService.parseExcel(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }

    @PutMapping("")
    public ResponseEntity<String> update(String pmDuration, String remark, @RequestParam String jobId) {
        pmService.update(pmDuration, remark, jobId);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("")
    public ResponseEntity<String> delete(@RequestParam String jobIds) {
        List<EquipmentPreventiveMaintenance> eqpms = Arrays.stream(jobIds.split(",")).map(jobId -> {
            EquipmentPreventiveMaintenance eqpm = new EquipmentPreventiveMaintenance();
            eqpm.setJobId(jobId);
            return eqpm;
        }).collect(Collectors.toList());
        eqpmRepository.deleteInBatch(eqpms);
        return ResponseEntity.ok("success");
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody EquipmentPreventiveMaintenance eqpm) {
        pmService.save(eqpm);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/export")
    public void exportExcel(@QuerydslPredicate(root = EquipmentPreventiveMaintenance.class) Predicate predicate, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = "Pm";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
        pmService.exportExcel(predicate, out);
    }
}
