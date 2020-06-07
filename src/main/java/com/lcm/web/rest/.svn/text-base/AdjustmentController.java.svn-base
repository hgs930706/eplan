package com.lcm.web.rest;

import com.lcm.domain.QLine;
import com.lcm.domain.dto.AdjustmentDto;
import com.lcm.domain.dto.JobAdjustChartDto;
import com.lcm.domain.dto.JobDto;
import com.lcm.repository.LineRepository;
import com.lcm.service.AdjustmentService;
import com.lcm.util.DateUtils;
import com.lcm.util.TableUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/adjustment")
@RestController
public class AdjustmentController {

    private final AdjustmentService adjustmentService;
    private final LineRepository lineRepository;

    public AdjustmentController(LineRepository lineRepository,AdjustmentService adjustmentService) {
        this.adjustmentService = adjustmentService;
        this.lineRepository = lineRepository;

    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("site") String site,@RequestBody MultipartFile file) {
        try {
            adjustmentService.parseExcel(site, file);
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("query")
    public JobDto index(@RequestParam String site, String fab, String area,@RequestParam String startTime, @RequestParam String endTime, boolean showWoId) {
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
        //新建aliasmap，key值为Line表的site,fab,area,line ，value为Line表的aliasname.
        Map<String,String> aliasmap = new HashMap<>();
        lineRepository.findAll(pre,Sort.by(orders)).forEach(line ->{
                    linkedList.add(line.getFab() + TableUtils.FLAG + line.getLine());
                    String aliaskey = line.getSite()+TableUtils.FLAG +line.getFab()+TableUtils.FLAG +line.getArea()+TableUtils.FLAG +line.getLine();
                    String aliasvalue =  line.getAliasName();
                    aliasmap.put(aliaskey , aliasvalue);
                }
        );

        return adjustmentService.getData(site, fab,area, startTime, endTime, showWoId,linkedList,aliasmap);
    }


    //获取的图表只显示两天数据
    @GetMapping("queryChart")
    public ResponseEntity queryChart(String site, String fab,String area, String startTime){
        List<JobAdjustChartDto> list;
        try {
            //计划产出
            LocalDate startDate = LocalDate.parse(startTime, DateUtils.dateFormatter1);
            List<AdjustmentDto> adjustChartData = adjustmentService.getAdjustChartData(site, fab,area, startDate, startDate.plusDays(1));
            list = adjustmentService.getAdjustChart(adjustChartData);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }

        return ResponseEntity.ok(list);
    }

    //只匹配今天
    @GetMapping("queryChart1")
    public ResponseEntity queryChart1(String site, String fab,String area, String startTime){
        List<JobAdjustChartDto> list;
        try {
            LocalDate startDate = LocalDate.parse(startTime, DateUtils.dateFormatter1);
            List<AdjustmentDto> adjustChartData = adjustmentService.getAdjustChartData(site, fab,area, startDate, startDate.plusDays(1));

            //当天计划产出
            List<AdjustmentDto> adjustChartData2 = adjustmentService.getAdjustChartData(site, fab, area, startDate, startDate.plusDays(1));
            List<AdjustmentDto> collect = adjustChartData2.stream().filter(adj -> adj.getShiftDate().isEqual(startDate)).collect(Collectors.toList());
            Map<List<String>, Integer>  map = adjustmentService.getLotOpsuList(site, fab, area);  //实际产出

            list = adjustmentService.getAdjustChart2(adjustChartData, collect, map);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }

        return ResponseEntity.ok(list);
    }

}
