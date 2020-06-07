package com.lcm.web.rest;


import com.lcm.domain.Adjustment;
import com.lcm.domain.RJobDashboard;
import com.lcm.domain.dto.MatchParamDto;
import com.lcm.domain.dto.PlanMatchPplDto;
import com.lcm.service.PlanMatchReportService;
import com.lcm.util.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/match")
@Controller
public class PlanMatchReportController {

    private final PlanMatchReportService planMatchReportService;


    public PlanMatchReportController(PlanMatchReportService planMatchReportService) {
        this.planMatchReportService = planMatchReportService;
    }

    // 查詢匹配率
    @GetMapping("/query")
    @ResponseBody
    public ResponseEntity query(MatchParamDto matchParamDto){
        return ResponseEntity.ok(planMatchReportService.getMatchReport(matchParamDto));
    }

}
