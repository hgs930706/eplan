package com.lcm.web.rest;

import com.lcm.domain.*;
import com.lcm.domain.QLine;
import com.lcm.domain.QRJobDashboard;
import com.lcm.domain.dto.ColumnDto;
import com.lcm.domain.dto.JobChartDto;
import com.lcm.domain.dto.JobTableDto;
import com.lcm.domain.dto.JobDto;
import com.lcm.repository.LineRepository;
import com.lcm.repository.RJobDashboardRepository;
import com.lcm.service.JobDashboardService;
import com.lcm.util.TableUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/job")
@RestController
public class JobDashboardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobDashboardController.class);
    private final JobDashboardService jobDashboardService;
    private final LineRepository lineRepository;
    //构造器注入
    public JobDashboardController(LineRepository lineRepository,JobDashboardService jobDashboardService){
        this.jobDashboardService = jobDashboardService;
        this.lineRepository = lineRepository;
    }
    //获取查询结果
    @GetMapping("queryJob")
    public ResponseEntity queryJob(String site, String fab,String area, String startTime, String endTime){
        //所有线
        QLine query = QLine.line1;
        Predicate pre = query.line.isNotNull();
        pre = ExpressionUtils.and(pre, query.site.eq(site));
        pre = !StringUtils.isEmpty(fab) ? ExpressionUtils.and(pre, query.fab.eq(fab)) : pre;
        pre = !StringUtils.isEmpty(area) ? ExpressionUtils.and(pre, query.area.eq(area)) : pre;
        pre = ExpressionUtils.and(pre, query.activeFlag.eq("Y"));
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.asc("line"));

        LinkedList<String> linkedList = new LinkedList<>();
        lineRepository.findAll(pre,Sort.by(orders)).forEach(line ->{
                    linkedList.add(line.getFab() + TableUtils.FLAG + line.getLine());
                }
        );

        //获取基础数据
        List<JobDashboard> jobList = jobDashboardService.getTJOBData(site, fab,area, startTime, endTime);
        //合并白晚班数据
        if(!jobList.isEmpty()){
            JobDto list = jobDashboardService.getListTable(jobList,linkedList);
            return ResponseEntity.ok(list);
        }else{
            return ResponseEntity.ok(null);
        }
    }

    //获取的图表只显示两天数据
    @GetMapping("queryChart")
    public ResponseEntity queryChart(String site, String fab,String area, String startTime){
        startTime = startTime.replace("/","-");
        List<JobChartDto> list = null;
        try {
            List<JobDashboard> jobList = jobDashboardService.getTJOBChartData(site,fab,area,startTime);
            if(!jobList.isEmpty()){//
                list = jobDashboardService.getListChart(jobList);
            }
        }catch (Exception e){
            LOGGER.error("错误信息："+e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }

        return ResponseEntity.ok(list);
    }


}
