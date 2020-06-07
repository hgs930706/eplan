package com.lcm.web.rest;

import com.lcm.service.ReportService;
import com.lcm.service.dto.ReportDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> index(@RequestParam String site, String fab, String area, @RequestParam String shiftDate,String shiftDate2) {
        return ResponseEntity.ok(reportService.getList(site, fab, area, shiftDate, shiftDate2));
    }
}
