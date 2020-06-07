package com.lcm.service;

import com.lcm.domain.AdjustmentInit;
import com.lcm.domain.QAdjustmentInit;
import com.lcm.domain.QRJobDashboard;
import com.lcm.domain.RJobDashboard;
import com.lcm.domain.dto.*;
import com.lcm.repository.AdjustmentInitRepository;
import com.lcm.repository.AdjustmentRepository;
import com.lcm.repository.LineRepository;
import com.lcm.repository.RJobDashboardRepository;
import com.lcm.util.DateUtils;
import com.lcm.util.TableUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanMatchReportService {
    private final DecimalFormat df = new DecimalFormat("0.00");//设置保留位数
    private final AdjustmentRepository adjustmentRepository;
    private final AdjustmentInitRepository adjustmentInitRepository;
    private final RJobDashboardRepository rJobDashboardRepository;
    private final LineRepository lineRepository;
    private final String PM = "PM";
    private final String NON_SCHEDULE = "NON-SCHEDULE";
    private final String CHANGE = "CHANGE";
    private final String REWORK = "REWORK";

    public PlanMatchReportService(AdjustmentInitRepository adjustmentInitRepository, AdjustmentRepository adjustmentRepository, RJobDashboardRepository rJobDashboardRepository, LineRepository lineRepository) {
        this.adjustmentRepository = adjustmentRepository;
        this.rJobDashboardRepository = rJobDashboardRepository;
        this.adjustmentInitRepository = adjustmentInitRepository;
        this.lineRepository = lineRepository;
    }

    //main 以adjustment表为主
    public PlanMatchDto getMatchReport(MatchParamDto matchParamDto) {
        List<PlanMatchReportDto> listDto = new ArrayList<>();
        Map<String, List<String>> listMap = new HashMap<>();

        String site = matchParamDto.getSite();
        String fab = matchParamDto.getFab();
        String area = matchParamDto.getArea();
        String shift = matchParamDto.getShift();
        String matchWoId = matchParamDto.getMatchWoId();
        LocalDate startDate = LocalDate.parse(matchParamDto.getStartTime(), DateUtils.dateFormatter1);
        LocalDate endDate = LocalDate.parse(matchParamDto.getEndTime(), DateUtils.dateFormatter1);

        List<AdjustmentInit> adjustListAll = this.findAllAdjustment(site, fab, area, shift, startDate, endDate);
        List<RJobDashboard> RJobDashboardListAll = this.findAllRJobDashboard(site, fab, area, shift, startDate, endDate);

        List<LocalDate> shiftDates = adjustListAll.stream().map(a -> a.getAdjustmentInitId().getShiftDate()).distinct().sorted().collect(Collectors.toList());
        List<String> fabs = adjustListAll.stream().map(a -> a.getAdjustmentInitId().getFab()).distinct().sorted().collect(Collectors.toList());
        List<String> shifts = adjustListAll.stream().map(a -> a.getAdjustmentInitId().getShift()).distinct().sorted().collect(Collectors.toList());
        //表格
        for (int z = 0; z < shiftDates.size(); z++) {
            LocalDate local = shiftDates.get(z);
            for (int z1 = 0; z1 < fabs.size(); z1++) {
                String strFab = fabs.get(z1);
//                List<String> areas = adjustListAll.stream().filter(a -> a.getAdjustmentInitId().getFab().equals(strFab))
//                        .map(b -> b.getAdjustmentInitId().getArea()).distinct().sorted().collect(Collectors.toList());
                List<String> lineList;
                if(StringUtils.isEmpty(area)){
                    if (listMap.get(strFab) == null) {
                        lineList = this.findLineList(site, strFab,area);//  线体总数
                        listMap.put(strFab, lineList);
                    } else {
                        lineList = listMap.get(strFab);
                    }
                }else{
                    if (listMap.get(strFab + "-" + area) == null) {
                        lineList = this.findLineList(site, strFab, area);//  线体总数
                        listMap.put(strFab + "-" + area, lineList);
                    } else {
                        lineList = listMap.get(strFab + "-" + area);
                    }
                }
                for (int z2 = 0; z2 < shifts.size(); z2++) {
                    String shi = shifts.get(z2);
                    int sum = 0;
                    List<PlanMatchPplDto> pplDtoList = new ArrayList<>();
                    for (String line : lineList) {//loop start
                        List<AdjustmentInit> collect = adjustListAll.stream().filter(all -> local.isEqual(all.getAdjustmentInitId().getShiftDate())
                                && strFab.equals(all.getAdjustmentInitId().getFab())
                                && shi.equals(all.getAdjustmentInitId().getShift())
                                && line.equals(all.getAdjustmentInitId().getLine())).collect(Collectors.toList());
//                        if("N".equals(matchWoId)){//排除工单，进行分组
//                            Map<List<String>, Integer> adjustmentInitGroup = collect.stream().collect(Collectors.groupingBy(adj -> Arrays.asList(adj.getPartNo(),
//                                    adj.getAdjustmentInitId().getModelNo(),
//                                    adj.getAdjustmentInitId().getGrade()), Collectors.summingInt(AdjustmentInit::getPlanQty)));
//                        }

                        List<RJobDashboard> collect1 = RJobDashboardListAll.stream().filter(all -> local.isEqual(all.getShiftDate())
                                && strFab.equals(all.getFab())
                                && shi.equals(all.getShift())
                                && line.equals(all.getLine())).collect(Collectors.toList());
                        List<RJobDashboard> pm = collect1.stream().filter(c -> c.getJobType().equals("PM") || c.getJobType().equals(NON_SCHEDULE)).collect(Collectors.toList());
                        List<RJobDashboard> pm1 = collect1.stream().filter(c -> !c.getJobType().equals("PM") && !c.getJobType().equals(NON_SCHEDULE)).collect(Collectors.toList());

                        List<JobDashboardMatchDto> matchDtoList = new ArrayList<>();//collect1转换为matchDtoList

                        for (int h = 0; h < pm.size(); h++) {
                            JobDashboardMatchDto matchDto = new JobDashboardMatchDto();
                            this.getMatchDto(matchDto, site, strFab, local, shi, line);
                            matchDto.setModelNo(pm.get(h).getModelNo());
                            matchDto.setWoId(pm.get(h).getWoId());
                            matchDto.setPartNo(pm.get(h).getPartNo());
                            matchDto.setGrade(pm.get(h).getGrade());
                            matchDto.setForecastQty(pm.get(h).getForecastQty());
                            matchDto.setJobType(pm.get(h).getJobType());
                            matchDto.setProcessStartTime(pm.get(h).getProcessStartTime());
                            matchDto.setProcessEndTime(pm.get(h).getProcessEndTime());
                            matchDtoList.add(matchDto);
                        }
                        //TODO 累加求和,用到了分组
                        if (pm1.size() > 1) {
                            Map<List<Object>, Integer> collect3 = pm1.stream().collect(
                                    Collectors.groupingBy(r -> Arrays.asList(r.getModelNo(), r.getWoId(), r.getPartNo(), r.getGrade(), r.getJobType()),
                                            Collectors.summingInt(RJobDashboard::getForecastQty)+"'\+)
                            );
                            collect3.forEach((key, value) -> {
                                JobDashboardMatchDto matchDto = new JobDashboardMatchDto();
                                this.getMatchDto(matchDto, site, strFab, local, shi, line);
                                matchDto.setModelNo((String) key.get(0));
                                matchDto.setWoId((String) key.get(1));
                                matchDto.setPartNo((String) key.get(2));
                                matchDto.setGrade((String) key.get(3));
                                matchDto.setForecastQty(value);//其它栏位相同，forecastqty不同。
                                //获取第一笔的ProcessStartTime参数
                                List<RJobDashboard> rJobDashboardStream = pm1.stream().filter(r -> r.getModelNo().equals((String) key.get(0))
                                        && r.getWoId().equals((String) key.get(1))
                                        && r.getPartNo().equals((String) key.get(2))
                                        && r.getGrade().equals((String) key.get(3))
                                        && r.getJobType().equals((String) key.get(4))).collect(Collectors.toList());
                                if (rJobDashboardStream.size() > 1) {
                                    rJobDashboardStream.sort(Comparator.comparing(RJobDashboard::getProcessStartTime));
                                }
                                matchDto.setProcessStartTime(rJobDashboardStream.get(0).getProcessStartTime());//作用体现在排序
                                matchDto.setJobType((String) key.get(4));
                                matchDtoList.add(matchDto);
                            });
                        } else if (pm1.size() == 1) {
                            JobDashboardMatchDto matchDto = new JobDashboardMatchDto();
                            this.getMatchDto(matchDto, site, strFab, local, shi, line);
                            matchDto.setModelNo(pm1.get(0).getModelNo());
                            matchDto.setWoId(pm1.get(0).getWoId());
                            matchDto.setPartNo(pm1.get(0).getPartNo());
                            matchDto.setGrade(pm1.get(0).getGrade());
                            matchDto.setForecastQty(pm1.get(0).getForecastQty());
                            matchDto.setProcessStartTime(pm1.get(0).getProcessStartTime());//作用体现在排序
                            matchDto.setJobType(pm1.get(0).getJobType());
                            matchDtoList.add(matchDto);
                        }


                        //进行匹配
                        collect.sort(Comparator.comparing(AdjustmentInit::getRunSeq));
                        matchDtoList.sort(Comparator.comparing(JobDashboardMatchDto::getProcessStartTime));


                        if (collect.size() == 0 && matchDtoList.size() == 0) {
                            this.pplInfo(TableUtils.ONE, true, site, strFab, local + "", shi, line, collect, matchDtoList, pplDtoList);
                            sum++;
                        } else if (collect.size() > 0 && matchDtoList.size() > 0 && collect.size() == matchDtoList.size()) {
                            boolean flag = this.getSum(collect, matchDtoList, matchWoId);
                            if (flag) {
                                this.pplInfo(TableUtils.TWO, true, site, strFab, local + "", shi, line, collect, matchDtoList, pplDtoList);
                                sum++;
                            } else {//虽然数量一样，但实际内容不匹配
                                this.pplInfo(TableUtils.TWO, false, site, strFab, local + "", shi, line, collect, matchDtoList, pplDtoList);
                            }
                        } else {//数量不同，以多的为主。
                            this.pplInfo(TableUtils.THREE, false, site, strFab, local + "", shi, line, collect, matchDtoList, pplDtoList);
                        }
                    }//loop end
                    List<AdjustmentInit> p;
                    List<RJobDashboard> s;
                    if(StringUtils.isEmpty(area)) {
                        p = adjustListAll.stream().filter(peo -> local.isEqual(peo.getAdjustmentInitId().getShiftDate())
                                && strFab.equals(peo.getAdjustmentInitId().getFab())
                                && shi.equals(peo.getAdjustmentInitId().getShift())).collect(Collectors.toList());
                        s = RJobDashboardListAll.stream().filter(sys -> local.isEqual(sys.getShiftDate())
                                && strFab.equals(sys.getFab())
                                && shi.equals(sys.getShift())).collect(Collectors.toList());
                    }else{
                        p = adjustListAll.stream().filter(peo -> local.isEqual(peo.getAdjustmentInitId().getShiftDate())
                                && strFab.equals(peo.getAdjustmentInitId().getFab())
                                && area.equals(peo.getAdjustmentInitId().getArea())
                                && shi.equals(peo.getAdjustmentInitId().getShift())).collect(Collectors.toList());
                        s = RJobDashboardListAll.stream().filter(sys -> local.isEqual(sys.getShiftDate())
                                && strFab.equals(sys.getFab())
                                && area.equals(sys.getArea())
                                && shi.equals(sys.getShift())).collect(Collectors.toList());
                    }
                    List<String> pSize = p.stream().map(c -> c.getAdjustmentInitId().getLine()).distinct().collect(Collectors.toList());//人员排线
                    List<String> sSize = s.stream().map(c -> c.getLine()).distinct().collect(Collectors.toList());//系统排线

                    PlanMatchReportDto dto = new PlanMatchReportDto();
                    dto.setSite(site);
                    dto.setFab(strFab);
                    dto.setShift(shi);
                    dto.setShiftDate(local);
                    dto.setPeopleLineTotal(pSize.size());
                    dto.setSystmeLineTotal(sSize.size());
                    dto.setOkTotal(sum);
                    dto.setTotal(lineList.size());
                    String format = lineList.size() == 0 ? "0.00" : df.format(((double) sum / lineList.size()) * 100);
                    dto.setPpl(format + " %");
                    dto.setPplChart(Double.parseDouble(format));
                    dto.setPlanMatchPplDtoList(pplDtoList);
                    listDto.add(dto);
                }

            }
        }
        //图表展示
        PlanMatchDto planMatchDto = this.getChart(listDto);
        return planMatchDto;
    }

    public JobDashboardMatchDto getMatchDto(JobDashboardMatchDto dto, String site, String strFab, LocalDate local, String shi, String line) {
        dto.setSite(site);
        dto.setFab(strFab);
        dto.setShiftDate(local);
        dto.setShift(shi);
        dto.setLine(line);
        return dto;
    }

    //图表展示 List<PlanMatchReportDto> listDto
    public PlanMatchDto getChart(List<PlanMatchReportDto> listDto) {
        listDto.sort(Comparator.comparing(PlanMatchReportDto::getFab).thenComparing(PlanMatchReportDto::getShiftDate));
        List<PlanMatchLineChartDto> listAll = new ArrayList<>();
        List<String> fabsChart = listDto.stream().map(a -> a.getFab()).distinct().sorted().collect(Collectors.toList());
        for (int i = 0; i < fabsChart.size(); i++) {
            String chart = fabsChart.get(i);
            List<PlanMatchReportDto> collect = listDto.stream().filter(f -> chart.equals(f.getFab())).collect(Collectors.toList());
            PlanMatchLineChartDto line = new PlanMatchLineChartDto();
            line.setLineList(collect);
            listAll.add(line);//所有线
        }
        List<String> set = new ArrayList<>();
        listDto.forEach(f -> {
            set.add(f.getShiftDate() + f.getShift());
        });
        List<String> shiftDatesChart = set.stream().distinct().sorted().collect(Collectors.toList());
        PlanMatchDto planMatchDto = new PlanMatchDto();
        planMatchDto.setReportList(listDto);
        planMatchDto.setListAll(listAll);
        planMatchDto.setFabs(fabsChart);
        planMatchDto.setShiftDates(shiftDatesChart);
        return planMatchDto;
    }

    //匹配红色，不匹配绿色
    public List<PlanMatchPplDto> pplInfo(int count, boolean flag, String site, String fab, String shiftDate, String shift, String line,
                                         List<AdjustmentInit> adjustmentInit, List<JobDashboardMatchDto> matchDtoList,
                                         List<PlanMatchPplDto> pplDtoList) {
        PlanMatchPplDto dto;
        switch (count) {
            case 1:
                dto = this.getDtoKeys(site, fab, shiftDate, shift, line);
                dto.setColor("green");
                pplDtoList.add(dto);
                break;
            case 2:
                for (int j = 0; j < adjustmentInit.size(); j++) {
                    dto = this.getDtoKeys(site, fab, shiftDate, shift, line);
                    if (flag) {
                        dto.setColor("green");
                    } else {
                        dto.setColor("red");
                    }
                    this.getDtoMain(j, dto, adjustmentInit);
                    this.getDtoMain(j, matchDtoList, dto);
                    pplDtoList.add(dto);
                }
                break;
            case 3:
                int adjustSize = adjustmentInit.size();
                int dashSize = matchDtoList.size();
                int size = adjustSize > dashSize ? adjustSize : dashSize;
                for (int j = 0; j < size; j++) {
                    dto = this.getDtoKeys(site, fab, shiftDate, shift, line);
                    dto.setColor("red");
                    if (j < adjustSize) {
                        this.getDtoMain(j, dto, adjustmentInit);
                    }
                    if (j < dashSize) {
                        this.getDtoMain(j, matchDtoList, dto);
                    }
                    pplDtoList.add(dto);
                }
                break;
        }
        return pplDtoList;
    }

    public PlanMatchPplDto getDtoKeys(String site, String fab, String shiftDate, String shift, String line) {
        PlanMatchPplDto dto = new PlanMatchPplDto();
        dto.setSite(site);
        dto.setFab(fab);
        dto.setShiftDate(shiftDate);
        dto.setShift(shift);
        dto.setLine(line);
        return dto;
    }

    public PlanMatchPplDto getDtoMain(int j, PlanMatchPplDto dto, List<AdjustmentInit> adjustmentInit) {
        dto.setDuration(adjustmentInit.get(j).getDuration());
        dto.setGradeAdj(adjustmentInit.get(j).getAdjustmentInitId().getGrade());
        dto.setPlanQtyAdj(adjustmentInit.get(j).getPlanQty() + "");
        dto.setModelNoAdj(adjustmentInit.get(j).getAdjustmentInitId().getModelNo());
        dto.setPartNoAdj(adjustmentInit.get(j).getPartNo());
        dto.setWoIdAdj(adjustmentInit.get(j).getAdjustmentInitId().getWoId());
        dto.setJobTypeAdj(adjustmentInit.get(j).getAdjustmentInitId().getJobType());
        return dto;
    }

    public PlanMatchPplDto getDtoMain(int j, List<JobDashboardMatchDto> matchDtoList, PlanMatchPplDto dto) {
        if(matchDtoList.get(j).getProcessEndTime() != null){
             Duration duration1 = Duration.between(matchDtoList.get(j).getProcessStartTime(), matchDtoList.get(j).getProcessEndTime());
            Double d = (double) duration1.toHours();
            dto.setDurationDash(d + "");
        }

        dto.setGradeDash(matchDtoList.get(j).getGrade());
        dto.setForecastQtyDash(matchDtoList.get(j).getForecastQty() + "");
        dto.setModelNoDash(matchDtoList.get(j).getModelNo());
        dto.setPartNoDash(matchDtoList.get(j).getPartNo());
        dto.setWoIdDash(matchDtoList.get(j).getWoId());
        dto.setJobTypeDash(matchDtoList.get(j).getJobType());
        return dto;
    }

    //条数相等，每条内容相同，sum + 1
    public boolean getSum(List<AdjustmentInit> list, List<JobDashboardMatchDto> list2, String matchWoId) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if ("PM".equals(list.get(i).getAdjustmentInitId().getJobType()) && "PM".equals(list2.get(i).getJobType())) {
                boolean flag = this.getCountPm(list.get(i), list2.get(i));
                if (flag) {
                    count++;
                } else {
                    return false;
                }
            } else if (NON_SCHEDULE.equals(list.get(i).getAdjustmentInitId().getJobType()) && NON_SCHEDULE.equals(list2.get(i).getJobType())) {
                boolean flag = this.getCountPm(list.get(i), list2.get(i));
                if (flag) {
                    count++;
                } else {
                    return false;
                }
            } else {
                boolean flag = this.getCount(list.get(i), list2.get(i), matchWoId);
                if (flag) {
                    count++;
                } else {
                    return false;
                }
            }
        }
        return list.size() == count ? true : false;
    }

    //正常匹配规则
    public boolean getCount(AdjustmentInit adjustmentInit, JobDashboardMatchDto rJobDashboard, String matchWoId) {

        int palnQty = adjustmentInit.getPlanQty();
        int a = (int) (palnQty - palnQty * 0.05);
        int b = (int) ((palnQty + palnQty * 0.05));

        String grade = rJobDashboard.getGrade() == null ? "-" : rJobDashboard.getGrade();
        String grade1 = adjustmentInit.getAdjustmentInitId().getGrade() == null ? "-" : adjustmentInit.getAdjustmentInitId().getGrade();

        if ("Y".equals(matchWoId)) {
            if ( a <= rJobDashboard.getForecastQty() && rJobDashboard.getForecastQty() <= b
                    && (adjustmentInit.getAdjustmentInitId().getModelNo()).equals(rJobDashboard.getModelNo())
                    && (adjustmentInit.getAdjustmentInitId().getWoId()).equals(rJobDashboard.getWoId())
                    && (adjustmentInit.getPartNo()).equals(rJobDashboard.getPartNo())
                    && grade.equals(grade1)) {
                return true;
            }
        } else {
            if (a <= rJobDashboard.getForecastQty() && rJobDashboard.getForecastQty() <= b
                    && (adjustmentInit.getAdjustmentInitId().getModelNo()).equals(rJobDashboard.getModelNo())
//                    && (adjustmentInit.getAdjustmentInitId().getWoId()).equals(rJobDashboard.getWoId())
                    && (adjustmentInit.getPartNo()).equals(rJobDashboard.getPartNo())
                    && grade.equals(grade1)) {
                return true;
            }
        }

        return false;
    }

    //pm匹配规则
    public boolean getCountPm(AdjustmentInit adjustmentInit, JobDashboardMatchDto rJobDashboard) {
        Double c = Double.parseDouble(adjustmentInit.getDuration());

        Duration duration1 = Duration.between(rJobDashboard.getProcessStartTime(), rJobDashboard.getProcessEndTime());
        Double d = (double) duration1.toHours();//系统排产小时

        double dis = 1e-3;
        if (Math.abs(c - d) < dis) {
            return true;
        }

        return false;
    }

    //总线数
    public List<String> findLineList(String site, String fab,String area) {
        if(StringUtils.isEmpty(area)) {
            return lineRepository.findLineList(site, fab);
        }else{
            return lineRepository.findLineList(site, fab, area);
        }
    }

    //获取基础数据
    public List<AdjustmentInit> findAllAdjustment(String site, String fab, String area, String shift, LocalDate startDate, LocalDate endDate) {

        QAdjustmentInit query = QAdjustmentInit.adjustmentInit;
        //初始化组装条件(类似where 1=1)
        Predicate pre = query.adjustmentInitId.isNotNull();
        pre = site == null ? pre : ExpressionUtils.and(pre, query.adjustmentInitId.site.eq(site));
        if (!StringUtils.isEmpty(fab)) {
            pre = ExpressionUtils.and(pre, query.adjustmentInitId.fab.eq(fab));
        }
        if (!StringUtils.isEmpty(shift)) {
            pre = ExpressionUtils.and(pre, query.adjustmentInitId.shift.eq(shift));
        }
        if (!StringUtils.isEmpty(area)) {
            pre = ExpressionUtils.and(pre, query.adjustmentInitId.area.eq(area));
        }
        pre = ExpressionUtils.and(pre, query.adjustmentInitId.shiftDate.between(startDate, endDate));

        pre = ExpressionUtils.and(pre, query.adjustmentInitId.jobType.ne(CHANGE));
        pre = ExpressionUtils.and(pre,query.adjustmentInitId.jobType.ne(REWORK));

        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(Sort.Order.asc("adjustmentInitId.shiftDate"));
        orders.add(Sort.Order.asc("adjustmentInitId.line"));
        orders.add(Sort.Order.asc("runSeq"));

        return (List<AdjustmentInit>) adjustmentInitRepository.findAll(pre, Sort.by(orders));
    }

    public List<RJobDashboard> findAllRJobDashboard(String site, String fab, String area, String shift, LocalDate startDate, LocalDate endDate) {
        List<RJobDashboard> dashList = this.findAllDash(site, fab, area, shift, startDate, endDate);
        List<RJobDashboard> dashListPm = this.findAllDashPm(site, fab, area, shift, startDate, endDate);
        dashList.addAll(dashListPm);
        dashList.sort(Comparator.comparing(RJobDashboard::getShiftDate)
                .thenComparing(RJobDashboard::getLine)
                .thenComparing(RJobDashboard::getProcessStartTime));
        return dashList;
    }

    public List<RJobDashboard> findAllDash(String site, String fab, String area, String shift, LocalDate startDate, LocalDate endDate) {

        QRJobDashboard query = QRJobDashboard.rJobDashboard;
        //初始化组装条件(类似where 1=1)
        Predicate pre = query.isNotNull();
        pre = site == null ? pre : ExpressionUtils.and(pre, query.site.eq(site));
        if (!StringUtils.isEmpty(fab)) {
            pre = ExpressionUtils.and(pre, query.fab.eq(fab));
        }
        if (!StringUtils.isEmpty(area)) {
            pre = ExpressionUtils.and(pre, query.area.eq(area));
        }
        if (!StringUtils.isEmpty(shift)) {
            pre = ExpressionUtils.and(pre, query.shift.eq(shift));
        }
        pre = ExpressionUtils.and(pre, query.shiftDate.between(startDate, endDate));

        pre = ExpressionUtils.and(pre, query.jobType.notIn(PM, NON_SCHEDULE));
        pre = ExpressionUtils.and(pre, query.forecastQty.ne(0));

//        List<Sort.Order> orders = new ArrayList<Sort.Order>();
//        orders.add(Sort.Order.asc("shiftDate"));
//        orders.add(Sort.Order.asc("line"));
//        orders.add(Sort.Order.asc("processStartTime"));

//        return (List<RJobDashboard>)rJobDashboardRepository.findAll(pre,Sort.by(orders));
        return (List<RJobDashboard>) rJobDashboardRepository.findAll(pre);
    }

    public List<RJobDashboard> findAllDashPm(String site, String fab, String area, String shift, LocalDate startDate, LocalDate endDate) {

        QRJobDashboard query = QRJobDashboard.rJobDashboard;
        //初始化组装条件(类似where 1=1)
        Predicate pre = query.isNotNull();
        pre = site == null ? pre : ExpressionUtils.and(pre, query.site.eq(site));
        if (!StringUtils.isEmpty(fab)) {
            pre = ExpressionUtils.and(pre, query.fab.eq(fab));
        }
        if (!StringUtils.isEmpty(area)) {
            pre = ExpressionUtils.and(pre, query.area.eq(area));
        }
        if (!StringUtils.isEmpty(shift)) {
            pre = ExpressionUtils.and(pre, query.shift.eq(shift));
        }
        pre = ExpressionUtils.and(pre, query.shiftDate.between(startDate, endDate));
        pre = ExpressionUtils.and(pre, query.jobType.in(PM, NON_SCHEDULE));

//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(Sort.Order.asc("shiftDate"));
//        orders.add(Sort.Order.asc("line"));
//        orders.add(Sort.Order.asc("processStartTime"));

        return (List<RJobDashboard>) rJobDashboardRepository.findAll(pre);
    }
}
