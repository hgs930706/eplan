package com.lcm.web.rest;

import com.lcm.domain.Plan;
import com.lcm.repository.PlanRepository;
import com.lcm.service.PlanService;
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

@RequestMapping("/plan")
@RestController
public class PlanController {

    private final PlanService planService;
    private final PlanRepository planRepository;

    public PlanController(PlanService planService, PlanRepository planRepository) {
        this.planService = planService;
        this.planRepository = planRepository;
    }

    @GetMapping("")
    public ResponseEntity<Page<Plan>> index(@QuerydslPredicate(root = Plan.class) Predicate predicate,
                                            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(planRepository.findAll(predicate, pageable));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody MultipartFile file) {
        try {
            planService.parseExcel(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }

    @PutMapping("")
    public ResponseEntity<String> update(String cellPartNo, String grade, String planQty, @RequestParam String jobId) {
        planService.update(cellPartNo, grade, planQty, jobId);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("")
    public ResponseEntity<String> delete(@RequestParam String jobIds) {
        List<Plan> plans = Arrays.stream(jobIds.split(",")).map(jobId -> {
            Plan plan = new Plan();
            plan.setJobId(jobId);
            return plan;
        }).collect(Collectors.toList());
        planRepository.deleteInBatch(plans);
        return ResponseEntity.ok("success");
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody Plan plan) {
        planService.save(plan);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/export")
    public void exportExcel(@QuerydslPredicate(root = Plan.class) Predicate predicate, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = "Plan";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
        planService.exportExcel(predicate, out);
    }
}
