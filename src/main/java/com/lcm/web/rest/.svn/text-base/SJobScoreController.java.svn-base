package com.lcm.web.rest;

import com.lcm.domain.JobDashboard;
import com.lcm.domain.SJobDashboard;
import com.lcm.domain.dto.JobDto1;
import com.lcm.repository.SJobDashboardRepository;
import com.lcm.service.SJobScoreService;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("/score")
@RestController
public class SJobScoreController {

    private static final Logger LOGGER = LoggerFactory.logger(LineController.class);

    private final SJobScoreService sJobScoreService;
    private final SJobDashboardRepository sJobDashboardRepository;
    public SJobScoreController(SJobScoreService sJobScoreService,SJobDashboardRepository sJobDashboardRepository){
            this.sJobScoreService = sJobScoreService;
            this.sJobDashboardRepository = sJobDashboardRepository;
    }

    @GetMapping("/queryMaxTrxId")
    public ResponseEntity<String> queryMaxTrxId(@RequestParam String site, @RequestParam String[] fab, @RequestParam String[] area, @RequestParam String planStart, @RequestParam String planEnd) {
        if (planStart != null && planEnd != null && fab.length > 0 && area.length > 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            SJobDashboard sJobDashboard = sJobDashboardRepository.
                    findTop1BySiteAndFabInAndAreaInAndPlanStartDateAndPlanEndDateOrderByTrxIdDesc(site, fab, area, LocalDate.parse(planStart, formatter), LocalDate.parse(planEnd, formatter));
            if (sJobDashboard != null) {
                return ResponseEntity.ok(sJobDashboard.getTrxId());
            }
        }
        return ResponseEntity.ok("");
    }



    @GetMapping("/queryScore")
    public ResponseEntity queryScore(String trxId){
        return ResponseEntity.ok(sJobScoreService.findAll(trxId));
    }

    @GetMapping("/querySjob")
    public ResponseEntity querySjob(String site, String trxId){
        //获取插入临时表T_job_dashboard的数据
        List<JobDashboard> tJobList = sJobScoreService.findBySiteAndTrxId(site,trxId);
        //处理临时表数据（合并白晚班数据）
        JobDto1 list = null;
        if(!tJobList.isEmpty()){
            list = sJobScoreService.getListSJobTable(tJobList);
        }
        return ResponseEntity.ok(list);
    }



}
