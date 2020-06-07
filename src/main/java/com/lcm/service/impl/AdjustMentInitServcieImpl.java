package com.lcm.service.impl;

import com.lcm.domain.AdjustmentInit;
import com.lcm.domain.AdjustmentInitId;
import com.lcm.domain.JobDashboard;
import com.lcm.domain.QAdjustmentInit;
import com.lcm.domain.dto.*;
import com.lcm.repository.AdjustmentInitRepository;
import com.lcm.service.AdjustmentInitServcie;
import com.lcm.service.dto.TJobStatisticsDTO;
import com.lcm.util.DateUtils;
import com.lcm.util.TableUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AdjustMentInitServcieImpl implements AdjustmentInitServcie {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdjustMentInitServcieImpl.class);

    private final AdjustmentInitRepository adjustmentInitRepository;

    public AdjustMentInitServcieImpl(AdjustmentInitRepository adjustmentInitRepository) {
        this.adjustmentInitRepository = adjustmentInitRepository;
    }

    //图表数据
    @Override
    public List<AdjustmentDto> getAdjustChartData(String site, String fab, String startTime) {
        LocalDate startDate = LocalDate.parse(startTime, DateUtils.dateFormatter1);
        LocalDate endDate = startDate.plusDays(1);
//        LocalDate endDate = LocalDate.parse(endTime, DateUtils.dateFormatter1);
        QAdjustmentInit query = QAdjustmentInit.adjustmentInit;
        Predicate pre = query.isNotNull();
        pre = site == null ? pre : ExpressionUtils.and(pre, query.adjustmentInitId.site.eq(site));
        if(!StringUtils.isEmpty(fab)){
            pre = ExpressionUtils.and(pre,query.adjustmentInitId.fab.eq(fab));
        }
        pre = ExpressionUtils.and(pre,query.adjustmentInitId.shiftDate.between(startDate,endDate));
        List<AdjustmentInit> list = (List<AdjustmentInit>)adjustmentInitRepository.findAll(pre);
        List<AdjustmentDto> listDto = new ArrayList<>();
        list.forEach(adj ->{
            AdjustmentDto adjDto = new AdjustmentDto();
            adjDto.setSite(adj.getAdjustmentInitId().getSite());
            adjDto.setFab(adj.getAdjustmentInitId().getFab());
            adjDto.setLine(adj.getAdjustmentInitId().getLine());
            adjDto.setShift(adj.getAdjustmentInitId().getShift());
            adjDto.setShiftDate(adj.getAdjustmentInitId().getShiftDate());
            adjDto.setJobType(adj.getAdjustmentInitId().getJobType());
            adjDto.setProcessStartTime(adj.getProcessStartTime());
            adjDto.setProcessEndTime(adj.getProcessEndTime());
            if("CHANGE".equals(adj.getAdjustmentInitId().getJobType())){
                adjDto.setModelNo("CHANGE");
            }else if("PM".equals(adj.getAdjustmentInitId().getJobType())){
                adjDto.setModelNo("PM");
            }else if("NON-SCHEDULE".equals(adj.getAdjustmentInitId().getJobType())){
                adjDto.setModelNo("NON-SCHEDULE");
            }else{
                adjDto.setModelNo(adj.getAdjustmentInitId().getModelNo());
            }
            adjDto.setPlanQty(adj.getPlanQty()+"");
            listDto.add(adjDto);
        });
        return listDto;
    }
   //图表展示
    @Override
    public List<JobAdjustChartDto> getAdjustChart(List<AdjustmentDto> listChart){
        List<JobAdjustChartDto> chartList = new ArrayList<>();//结果集
        Map<String,List<AdjustmentDto>> lineMap = new TreeMap<>();
        listChart.forEach(t -> {
            if(lineMap.get(t.getLine()) == null){

                List<AdjustmentDto>  list = new ArrayList<>();
                list.add(t);
                lineMap.put(t.getLine(),list);
            }else{
                lineMap.get(t.getLine()).add(t);
            }
        });
        for(String line : lineMap.keySet()){
            JobAdjustChartDto lineJob = new JobAdjustChartDto();
            List<AdjustmentDto> adjustments = lineMap.get(line);
            if(adjustments == null){
                continue;
            }
            for(int j = 0; j < adjustments.size(); j++){
                AdjustmentDto adjustment = adjustments.get(j);
                String color = adjustment.getJobType();
                if("PROD".equals(color)){// #ffffff
                    adjustment.setJobType("#669999");
                }else if("PM".equals(color)){//
                    adjustment.setJobType("#0099ff");
                    adjustment.setModelNo("PM");
                }else if("NON-SCHEDULE".equals(color)){//
                    adjustment.setJobType("#808080");
                    adjustment.setModelNo("NON-SCHEDULE");
                }else if("ENG".equals(color)){//
                    adjustment.setJobType("#00cc44");
                }else if("SPL".equals(color)){//
                    adjustment.setJobType("#ff8000");
                }else if("CHANGE".equals(color)){//
                    adjustment.setJobType("#ff0000");
                }else if("REWORK".equals(color)){
                    adjustment.setJobType("#669999");
                }
            }
            lineJob.setAdjustmentList(adjustments);//作为一个对象
            lineJob.setLine(line);
            chartList.add(lineJob);
        }
        return chartList;
    }
    //表格数据与展示
    @Override
    public JobDto getData(String site, String fab, String startTime, String endTime, boolean showWoId) {
        startTime = startTime.replace("/","-");
        endTime = endTime.replace("/","-");

        LocalDate startDate = LocalDate.parse(startTime, DateUtils.dateFormatter1);
        LocalDate endDate = LocalDate.parse(endTime, DateUtils.dateFormatter1);

        List<JobDashboard> tList = new ArrayList<>();
        if (showWoId) {
            List<AdjustmentInit> aList;
            if (StringUtils.isEmpty(fab)) {
                aList = adjustmentInitRepository.findAllByAdjustmentInitIdSiteAndAdjustmentInitIdShiftDateBetween(site, startDate, endDate);
            } else {
                aList = adjustmentInitRepository.findAllByAdjustmentInitIdSiteAndAdjustmentInitIdFabAndAdjustmentInitIdShiftDateBetween(site, fab, startDate, endDate);
            }
            for (int i = 0; i < aList.size(); i++) {
                JobDashboard t = new JobDashboard();
                t.setJobId("a" + i);
                AdjustmentInit adjustmentInit = aList.get(i);
                AdjustmentInitId idInit = adjustmentInit.getAdjustmentInitId();
                t.setLine(idInit.getLine());
                t.setFab(idInit.getFab());
                String grade = adjustmentInit.getAdjustmentInitId().getGrade();
                t.setPartNo(TableUtils.handlePartNo(adjustmentInit.getPartNo(),grade));
                t.setRemark(adjustmentInit.getRemark());
                t.setForecastQty(adjustmentInit.getPlanQty());
                t.setShiftDate(idInit.getShiftDate());
                t.setShift(idInit.getShift());
                String jobType = idInit.getJobType();
                t.setJobType(jobType);
                t.setModelNo(idInit.getModelNo());
                t.setWoId(idInit.getWoId());
                t.setSeq(adjustmentInit.getRunSeq());
                if (showWoId) {
                    if ("PM".equals(jobType) || "NON-SCHEDULE".equals(jobType)) {
                        t.setChangeDuration(adjustmentInit.getDuration());
                    } else if ("CHANGE".equals(jobType)) {
                        t.setChangeLevel(adjustmentInit.getChangeLevel());
                        t.setChangeDuration(adjustmentInit.getDuration());
                        t.setChangeKey(idInit.getChangeKey());
                    }
                }
                tList.add(t);
            }
        } else {
            List<Object> aList = null;
            if (StringUtils.isEmpty(fab)) {
                aList = adjustmentInitRepository.findBySiteAndShiftDate(site, startDate, endDate);
                aList.addAll(adjustmentInitRepository.findBySiteAndShiftDateForPm(site, startDate, endDate));
                aList.addAll(adjustmentInitRepository.findBySiteAndShiftDateForNonSchedule(site, startDate, endDate));
                aList.addAll(adjustmentInitRepository.findBySiteAndShiftDateForChange(site, startDate, endDate));
            } else {
                aList = adjustmentInitRepository.findBySiteAndFabAndShiftDate(site, fab, startDate, endDate);
                aList.addAll(adjustmentInitRepository.findBySiteAndFabAndShiftDateForPm(site, fab, startDate, endDate));
                aList.addAll(adjustmentInitRepository.findBySiteAndFabAndShiftDateForNonSchedule(site, fab, startDate, endDate));
                aList.addAll(adjustmentInitRepository.findBySiteAndFabAndShiftDateForChange(site, fab, startDate, endDate));
            }
            for (int i = 0; i < aList.size(); i++) {
                JobDashboard t = new JobDashboard();
                t.setJobId("a" + i);
                Object[] a = (Object[]) aList.get(i);
                t.setLine((String) a[0]);
                t.setShiftDate((LocalDate) a[1]);
                t.setShift((String) a[2]);
                String jobType = (String) a[3];
                t.setJobType(jobType);
                t.setModelNo((String) a[4]);
                String partNo = (String) a[5];
                t.setForecastQty(Integer.parseInt((String) a[6]));
                t.setFab((String) a[7]);
                if ("PM".equals(jobType) || "NON-SCHEDULE".equals(jobType)) {
                    String grade = (String) a[10];
                    t.setPartNo(TableUtils.handlePartNo(partNo,grade));
                    t.setRemark((String)a[11]);

                    t.setChangeDuration((String) a[8]);
                    t.setSeq((String) a[9]);
                } else if ("CHANGE".equals(jobType)) {
                    String grade = (String) a[12];
                    t.setPartNo(TableUtils.handlePartNo(partNo,grade));
                    t.setRemark((String)a[13]);

                    t.setChangeLevel((String) a[8]);
                    t.setChangeDuration((String) a[9]);
                    t.setChangeKey((String) a[10]);
                    t.setSeq((String) a[11]);
                } else {
                    String grade = (String) a[9];
                    t.setPartNo(TableUtils.handlePartNo(partNo,grade));
                    t.setRemark((String)a[10]);

                    t.setSeq((String) a[8]);
                }
                tList.add(t);
            }
        }
        return getListTable(showWoId, tList);
    }

    public JobDto getListTable(boolean showWoId, List<JobDashboard> tList) {
        Map<String, TJobStatisticsDTO> fabLineMap = TableUtils.handleTJobList(tList);
        int sum = 0;
        List<JobTableDto> tableDtoList= new ArrayList<>();
        List<LocalDate> shiftDates = tList.stream().map(t -> t.getShiftDate()).distinct().sorted().collect(Collectors.toList()); //获取区间查询日期
        LOGGER.info("一共查询：" + shiftDates.size() + "天。");
        int dCount;
        int nCount;
        List<List<JobDashboardDto>> allListDto = new ArrayList<>();//最终返回的结果集

        for (int h = 0; h < shiftDates.size(); h++) {
            LocalDate shiftDate = shiftDates.get(h);
            List<JobDashboardDto> listDto = new ArrayList<>();

            for (String key : fabLineMap.keySet()) {
                TJobStatisticsDTO statisticsDTO = fabLineMap.get(key);
                int count = statisticsDTO.getSum();
                String[] fabLineArr = key.split(TableUtils.FLAG);
                String fab = fabLineArr[0];
                String line = fabLineArr[1];
                Map<String, List<JobDashboard>> data = statisticsDTO.getData();
                List<JobDashboard> dLineList = data.get(shiftDate + TableUtils.FLAG + TableUtils.DSHIFT);//白班生产
                List<JobDashboard> nLineList = data.get(shiftDate + TableUtils.FLAG + TableUtils.NSHIFT);//晚班生产
                if (dLineList == null) {
                    dLineList = new ArrayList<>();
                } else {
                    if (showWoId) {
                        dLineList.sort(Comparator.comparing(JobDashboard::getSeq));
                    } else {
                        dLineList.sort(Comparator.comparing(JobDashboard::getPartNo));
                    }
                }
                if (nLineList == null) {
                    nLineList = new ArrayList<>();
                } else {
                    if (showWoId) {
                        nLineList.sort(Comparator.comparing(JobDashboard::getSeq));
                    } else {
                        nLineList.sort(Comparator.comparing(JobDashboard::getPartNo));
                    }
                }
                dCount = dLineList.size();
                nCount = nLineList.size();
                if(dCount < count || nCount < count){
                    for (int i = 0; i < count - dCount; i++){
                        JobDashboard obj = new JobDashboard();
                        obj.setFab(fab);
                        obj.setLine(line);
                        obj.setModelNo("-");
                        obj.setPartNo("-");
                        obj.setWoId("-");
                        obj.setRemark("-");
                        dLineList.add(obj);
                    }
                    for (int i = 0; i < count - nCount; i++){
                        JobDashboard obj = new JobDashboard();
                        obj.setFab(fab);
                        obj.setLine(line);
                        obj.setModelNo("-");
                        obj.setPartNo("-");
                        obj.setWoId("-");
                        obj.setRemark("-");
                        nLineList.add(obj);
                    }
                }

                //合并白晚班数据
                for (int i = 0; i < count; i++) {
                    JobDashboardDto dto = new JobDashboardDto();
                    dto.setFab(fab);
                    dto.setLine(line);
                    JobDashboard dObj = dLineList.get(i);
                    String dJobType = dObj.getJobType();
                    String dModelNo = dObj.getModelNo();
                    if("ENG".equals(dJobType)){
                        dto.setModelNo("*" + dModelNo);
                    }else if("SPL".equals(dJobType)){
                        dto.setModelNo("#" + dModelNo);
                    }else if("PM".equals(dJobType)){
                        dto.setModelNo("PM:" + dObj.getChangeDuration());
                    }else if("NON-SCHEDULE".equals(dJobType)){
                        dto.setModelNo("NON-SCHEDULE:" + dObj.getChangeDuration());
                    }else if ("CHANGE".equals(dJobType)){
//                        dto.setModelNo("CHANGE" + dObj.getChangeKey() + ":" +dObj.getChangeLevel() + ":" + dObj.getChangeDuration());
                        dto.setModelNo("CHANGE:" +dObj.getChangeLevel() + ":" + dObj.getChangeDuration());
                    }else if("REWORK".equals(dJobType)){
                        dto.setModelNo("%" + dModelNo);
                    } else {
                        dto.setModelNo(dModelNo);
                    }
                    dto.setSeq(dObj.getSeq());

                    dto.setPartNo(dObj.getPartNo());
                    dto.setWoId(dObj.getWoId());
                    dto.setRemark(dObj.getRemark());
                    dto.setForecastQty(dObj.getForecastQty());
                    dto.setJobType(dObj.getJobType());
                    dto.setChangeDuration(dObj.getChangeDuration());
                    JobDashboard nObj = nLineList.get(i);
                    String nJobType = nObj.getJobType();
                    String nModelNo = nObj.getModelNo();
                    if("ENG".equals(nJobType)){
                        dto.setNmodelNo("*" + nModelNo);
                    }else if("SPL".equals(nJobType)){
                        dto.setNmodelNo("#" + nModelNo);
                    }else if("PM".equals(nJobType)){
                        dto.setNmodelNo("PM:" + nObj.getChangeDuration());
                    }else if("NON-SCHEDULE".equals(nJobType)){
                        dto.setNmodelNo("NON-SCHEDULE:" + nObj.getChangeDuration());
                    }else if ("CHANGE".equals(nJobType)){
//                        dto.setNmodelNo("CHANGE:" + nObj.getChangeKey() + ":" + nObj.getChangeLevel() + ":" + nObj.getChangeDuration());
                        dto.setNmodelNo("CHANGE:" + nObj.getChangeLevel() + ":" + nObj.getChangeDuration());
                    }else if("REWORK".equals(nJobType)){
                        dto.setNmodelNo("%" + nModelNo);
                    } else {
                        dto.setNmodelNo(nModelNo);
                    }
                    dto.setNseq(nObj.getSeq());

                    dto.setNpartNo(nObj.getPartNo());
                    dto.setNwoId(nObj.getWoId());
                    dto.setNremark(nObj.getRemark());
                    dto.setNforecastQty(nObj.getForecastQty());
                    dto.setNjobType(nObj.getJobType());
                    dto.setNchangeDuration(nObj.getChangeDuration());
                    listDto.add(dto);
                }
            }
            if( h == 0 ) {
                sum = listDto.size();
            }
            allListDto.add(listDto);//所有天数的数据
        }
        //获取表格数据
        for (int i = 0; i < sum; i++) {//循环表格多少行
            JobTableDto tableDto = new JobTableDto();
            for (int j = 0; j < allListDto.size(); j++) {//这个循环区间天数
                List<JobDashboardDto> listDto = allListDto.get(j);
                JobDashboardDto dto = listDto.get(i);
                getTableData(tableDto,dto,j);
            }
            tableDtoList.add(tableDto);
        }
        //获取列数据
        List<String> columnList = new ArrayList<>();//所有属性，所有列
        Class clz = JobTableDto.class;
        Field[] fields=clz.getDeclaredFields();
        for (Field field : fields) {
            columnList.add(field.getName());
        }
        int a = 2;
        int b = 22;
        List<ColumnDto> columnDtoList = new ArrayList<>(); //表格所需列
        Map<String, Integer> shiftDateShiftPartNoCountMap = TableUtils.getShiftDateShiftPartNoCountMap(tList);
        for (int i = 0; i < shiftDates.size(); i++) {//查了几天循环几次//统计91、97、白晚班
            LocalDate shiftDate = shiftDates.get(i);
            List<String>  list = columnList.subList(a,b);

            ColumnDto columnDto = new ColumnDto();
            columnDto.setShiftDate(shiftDate);
            columnDto = TableUtils.get91and97and90(columnDto, shiftDateShiftPartNoCountMap, shiftDate);
            columnDto.setJobType(list.get(0));
            columnDto.setPartNo(list.get(1));
            columnDto.setModelNo(list.get(2));
            columnDto.setWoId(list.get(3));
            columnDto.setForecastQty91(list.get(5));
            columnDto.setForecastQty97(list.get(6));
            columnDto.setChangeDuration(list.get(7));
            columnDto.setPartNoFull(list.get(8));
            columnDto.setRemark(list.get(9));
            columnDto.setNjobType(list.get(10));
            columnDto.setNpartNo(list.get(11));
            columnDto.setNmodelNo(list.get(12));
            columnDto.setNwoId(list.get(13));
            columnDto.setNforecastQty91(list.get(15));
            columnDto.setNforecastQty97(list.get(16));
            columnDto.setNchangeDuration(list.get(17));
            columnDto.setNpartNoFull(list.get(18));
            columnDto.setNremark(list.get(19));
            columnDtoList.add(columnDto);
            a += 20;
            b += 20;
        }
        JobDto job = new JobDto();
        job.setTableDtoList(tableDtoList);
        job.setColumnDtoList(columnDtoList);
        return job;
    }
    //adjust:Table方法
    public void getTableData(JobTableDto tableDto,JobDashboardDto dto,int j){
        if(0 == j){
            tableDto.setFab(dto.getFab());
            tableDto.setLine(dto.getLine());
            tableDto.setChangeDuration(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty91(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty97(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty91(dto.getForecastQty());
            }
            tableDto.setJobType(dto.getJobType());
            tableDto.setModelNo(dto.getModelNo());
            tableDto.setPartNo(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull(dto.getPartNo());
            tableDto.setWoId(dto.getWoId());
            tableDto.setRemark(dto.getRemark());
            tableDto.setSeq(dto.getSeq());

            tableDto.setNchangeDuration(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty91(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty97(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty91(dto.getNforecastQty());
            }
            tableDto.setNjobType(dto.getNjobType());
            tableDto.setNmodelNo(dto.getNmodelNo());
            tableDto.setNpartNo(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull(dto.getNpartNo());
            tableDto.setNwoId(dto.getNwoId());
            tableDto.setNremark(dto.getNremark());
            tableDto.setNseq(dto.getNseq());
        }else if(1 == j){
            tableDto.setChangeDuration1(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty911(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty971(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty911(dto.getForecastQty());
            }
            tableDto.setJobType1(dto.getJobType());
            tableDto.setModelNo1(dto.getModelNo());
            tableDto.setPartNo1(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull1(dto.getPartNo());
            tableDto.setWoId1(dto.getWoId());
            tableDto.setRemark1(dto.getRemark());

            tableDto.setSeq1(dto.getSeq());

            tableDto.setNchangeDuration1(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty911(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty971(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty911(dto.getNforecastQty());
            }
            tableDto.setNjobType1(dto.getNjobType());
            tableDto.setNmodelNo1(dto.getNmodelNo());
            tableDto.setNpartNo1(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull1(dto.getNpartNo());
            tableDto.setNwoId1(dto.getNwoId());
            tableDto.setNremark1(dto.getNremark());

            tableDto.setNseq1(dto.getNseq());
        }else if(2 == j){
            tableDto.setChangeDuration2(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty912(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty972(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty912(dto.getForecastQty());
            }
            tableDto.setJobType2(dto.getJobType());
            tableDto.setModelNo2(dto.getModelNo());
            tableDto.setPartNo2(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull2(dto.getPartNo());
            tableDto.setWoId2(dto.getWoId());
            tableDto.setRemark2(dto.getRemark());

            tableDto.setSeq2(dto.getSeq());

            tableDto.setNchangeDuration2(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty912(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty972(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty912(dto.getNforecastQty());
            }
            tableDto.setNjobType2(dto.getNjobType());
            tableDto.setNmodelNo2(dto.getNmodelNo());
            tableDto.setNpartNo2(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull2(dto.getNpartNo());
            tableDto.setNwoId2(dto.getNwoId());
            tableDto.setNremark2(dto.getNremark());

            tableDto.setNseq2(dto.getNseq());
        }else if(3 == j){
            tableDto.setChangeDuration3(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty913(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty973(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty913(dto.getForecastQty());
            }
            tableDto.setJobType3(dto.getJobType());
            tableDto.setModelNo3(dto.getModelNo());
            tableDto.setPartNo3(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull3(dto.getPartNo());
            tableDto.setWoId3(dto.getWoId());
            tableDto.setRemark3(dto.getRemark());

            tableDto.setSeq3(dto.getSeq());

            tableDto.setNchangeDuration3(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty913(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty973(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty913(dto.getNforecastQty());
            }
            tableDto.setNjobType3(dto.getNjobType());
            tableDto.setNmodelNo3(dto.getNmodelNo());
            tableDto.setNpartNo3(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull3(dto.getNpartNo());
            tableDto.setNwoId3(dto.getNwoId());
            tableDto.setNremark3(dto.getNremark());

            tableDto.setNseq3(dto.getNseq());
        }else if(4 == j){
            tableDto.setChangeDuration4(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty914(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty974(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty914(dto.getForecastQty());
            }
            tableDto.setJobType4(dto.getJobType());
            tableDto.setModelNo4(dto.getModelNo());
            tableDto.setPartNo4(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull4(dto.getPartNo());
            tableDto.setWoId4(dto.getWoId());
            tableDto.setRemark4(dto.getRemark());

            tableDto.setSeq4(dto.getSeq());

            tableDto.setNchangeDuration4(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty914(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty974(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty914(dto.getNforecastQty());
            }
            tableDto.setNjobType4(dto.getNjobType());
            tableDto.setNmodelNo4(dto.getNmodelNo());
            tableDto.setNpartNo4(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull4(dto.getNpartNo());
            tableDto.setNwoId4(dto.getNwoId());
            tableDto.setNremark4(dto.getNremark());

            tableDto.setNseq4(dto.getNseq());
        }else if(5 == j){
            tableDto.setChangeDuration5(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty915(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty975(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty915(dto.getForecastQty());
            }
            tableDto.setJobType5(dto.getJobType());
            tableDto.setModelNo5(dto.getModelNo());
            tableDto.setPartNo5(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull5(dto.getPartNo());
            tableDto.setWoId5(dto.getWoId());
            tableDto.setRemark5(dto.getRemark());

            tableDto.setSeq5(dto.getSeq());

            tableDto.setNchangeDuration5(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty915(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty975(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty915(dto.getNforecastQty());
            }
            tableDto.setNjobType5(dto.getNjobType());
            tableDto.setNmodelNo5(dto.getNmodelNo());
            tableDto.setNpartNo5(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull5(dto.getNpartNo());
            tableDto.setNwoId5(dto.getNwoId());
            tableDto.setNremark5(dto.getNremark());

            tableDto.setNseq5(dto.getNseq());
        }else if(6 == j){
            tableDto.setChangeDuration6(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty916(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty976(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty916(dto.getForecastQty());
            }
            tableDto.setJobType6(dto.getJobType());
            tableDto.setModelNo6(dto.getModelNo());
            tableDto.setPartNo6(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull6(dto.getPartNo());
            tableDto.setWoId6(dto.getWoId());
            tableDto.setRemark6(dto.getRemark());

            tableDto.setSeq6(dto.getSeq());

            tableDto.setNchangeDuration6(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty916(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty976(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty916(dto.getNforecastQty());
            }
            tableDto.setNjobType6(dto.getNjobType());
            tableDto.setNmodelNo6(dto.getNmodelNo());
            tableDto.setNpartNo6(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull6(dto.getNpartNo());
            tableDto.setNwoId6(dto.getNwoId());
            tableDto.setNremark6(dto.getNremark());

            tableDto.setNseq6(dto.getNseq());
        }else if(7 == j){
            tableDto.setChangeDuration7(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty917(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty977(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty917(dto.getForecastQty());
            }
            tableDto.setJobType7(dto.getJobType());
            tableDto.setModelNo7(dto.getModelNo());
            tableDto.setPartNo7(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull7(dto.getPartNo());
            tableDto.setWoId7(dto.getWoId());
            tableDto.setRemark7(dto.getRemark());

            tableDto.setSeq7(dto.getSeq());

            tableDto.setNchangeDuration7(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty917(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty977(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty917(dto.getNforecastQty());
            }
            tableDto.setNjobType7(dto.getNjobType());
            tableDto.setNmodelNo7(dto.getNmodelNo());
            tableDto.setNpartNo7(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull7(dto.getNpartNo());
            tableDto.setNwoId7(dto.getNwoId());
            tableDto.setNremark7(dto.getNremark());

            tableDto.setNseq7(dto.getNseq());
        }else if(8 == j){
            tableDto.setChangeDuration8(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty918(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty978(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty918(dto.getForecastQty());
            }
            tableDto.setJobType8(dto.getJobType());
            tableDto.setModelNo8(dto.getModelNo());
            tableDto.setPartNo8(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull8(dto.getPartNo());
            tableDto.setWoId8(dto.getWoId());
            tableDto.setRemark8(dto.getRemark());

            tableDto.setSeq8(dto.getSeq());

            tableDto.setNchangeDuration8(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty918(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty978(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty918(dto.getNforecastQty());
            }
            tableDto.setNjobType8(dto.getNjobType());
            tableDto.setNmodelNo8(dto.getNmodelNo());
            tableDto.setNpartNo8(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull8(dto.getNpartNo());
            tableDto.setNwoId8(dto.getNwoId());
            tableDto.setNremark8(dto.getNremark());

            tableDto.setNseq8(dto.getNseq());
        }else if(9 == j){
            tableDto.setChangeDuration9(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty919(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty979(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty919(dto.getForecastQty());
            }
            tableDto.setJobType9(dto.getJobType());
            tableDto.setModelNo9(dto.getModelNo());
            tableDto.setPartNo9(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull9(dto.getPartNo());
            tableDto.setWoId9(dto.getWoId());
            tableDto.setRemark9(dto.getRemark());

            tableDto.setSeq9(dto.getSeq());

            tableDto.setNchangeDuration9(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty919(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty979(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty919(dto.getNforecastQty());
            }
            tableDto.setNjobType9(dto.getNjobType());
            tableDto.setNmodelNo9(dto.getNmodelNo());
            tableDto.setNpartNo9(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull9(dto.getNpartNo());
            tableDto.setNwoId9(dto.getNwoId());
            tableDto.setNremark9(dto.getNremark());

            tableDto.setNseq9(dto.getNseq());
        }else if(10 == j){
            tableDto.setChangeDuration10(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9110(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9710(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9110(dto.getForecastQty());

            }
            tableDto.setJobType10(dto.getJobType());
            tableDto.setModelNo10(dto.getModelNo());
            tableDto.setPartNo10(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull10(dto.getPartNo());
            tableDto.setWoId10(dto.getWoId());
            tableDto.setRemark10(dto.getRemark());

            tableDto.setSeq10(dto.getSeq());

            tableDto.setNchangeDuration10(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9110(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9710(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9110(dto.getNforecastQty());
            }
            tableDto.setNjobType10(dto.getNjobType());
            tableDto.setNmodelNo10(dto.getNmodelNo());
            tableDto.setNpartNo10(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull10(dto.getNpartNo());
            tableDto.setNwoId10(dto.getNwoId());
            tableDto.setNremark10(dto.getNremark());

            tableDto.setNseq10(dto.getNseq());
        }else if(11 == j){
            tableDto.setChangeDuration11(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9111(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9711(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9111(dto.getForecastQty());
            }
            tableDto.setJobType11(dto.getJobType());
            tableDto.setModelNo11(dto.getModelNo());
            tableDto.setPartNo11(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull11(dto.getPartNo());
            tableDto.setWoId11(dto.getWoId());
            tableDto.setRemark11(dto.getRemark());

            tableDto.setSeq11(dto.getSeq());

            tableDto.setNchangeDuration11(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9111(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9711(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9111(dto.getNforecastQty());
            }
            tableDto.setNjobType11(dto.getNjobType());
            tableDto.setNmodelNo11(dto.getNmodelNo());
            tableDto.setNpartNo11(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull11(dto.getNpartNo());
            tableDto.setNwoId11(dto.getNwoId());
            tableDto.setNremark11(dto.getNremark());

            tableDto.setNseq11(dto.getNseq());
        }else if(12 == j){
            tableDto.setChangeDuration12(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9112(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9712(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9112(dto.getForecastQty());
            }
            tableDto.setJobType12(dto.getJobType());
            tableDto.setModelNo12(dto.getModelNo());
            tableDto.setPartNo12(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull12(dto.getPartNo());
            tableDto.setWoId12(dto.getWoId());
            tableDto.setRemark12(dto.getRemark());

            tableDto.setSeq12(dto.getSeq());

            tableDto.setNchangeDuration12(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9112(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9712(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9112(dto.getNforecastQty());

            }
            tableDto.setNjobType12(dto.getNjobType());
            tableDto.setNmodelNo12(dto.getNmodelNo());
            tableDto.setNpartNo12(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull12(dto.getNpartNo());
            tableDto.setNwoId12(dto.getNwoId());
            tableDto.setNremark12(dto.getNremark());

            tableDto.setNseq12(dto.getNseq());
        }else if(13 == j){
            tableDto.setChangeDuration13(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9113(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9713(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9113(dto.getForecastQty());
            }
            tableDto.setJobType13(dto.getJobType());
            tableDto.setModelNo13(dto.getModelNo());
            tableDto.setPartNo13(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull13(dto.getPartNo());
            tableDto.setWoId13(dto.getWoId());
            tableDto.setRemark13(dto.getRemark());

            tableDto.setSeq13(dto.getSeq());

            tableDto.setNchangeDuration13(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9113(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9713(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9113(dto.getNforecastQty());
            }
            tableDto.setNjobType13(dto.getNjobType());
            tableDto.setNmodelNo13(dto.getNmodelNo());
            tableDto.setNpartNo13(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull13(dto.getNpartNo());
            tableDto.setNwoId13(dto.getNwoId());
            tableDto.setNremark13(dto.getNremark());

            tableDto.setNseq13(dto.getNseq());
        }else if(14 == j){
            tableDto.setChangeDuration14(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9114(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9714(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9114(dto.getForecastQty());
            }
            tableDto.setJobType14(dto.getJobType());
            tableDto.setModelNo14(dto.getModelNo());
            tableDto.setPartNo14(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull14(dto.getPartNo());
            tableDto.setWoId14(dto.getWoId());
            tableDto.setRemark14(dto.getRemark());

            tableDto.setSeq14(dto.getSeq());

            tableDto.setNchangeDuration14(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9114(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9714(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9114(dto.getNforecastQty());
            }
            tableDto.setNjobType14(dto.getNjobType());
            tableDto.setNmodelNo14(dto.getNmodelNo());
            tableDto.setNpartNo14(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull14(dto.getNpartNo());
            tableDto.setNwoId14(dto.getNwoId());
            tableDto.setNremark14(dto.getNremark());

            tableDto.setNseq14(dto.getNseq());
        }else if(15 == j){
            tableDto.setChangeDuration15(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9115(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9715(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9115(dto.getForecastQty());
            }
            tableDto.setJobType15(dto.getJobType());
            tableDto.setModelNo15(dto.getModelNo());
            tableDto.setPartNo15(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull15(dto.getPartNo());
            tableDto.setWoId15(dto.getWoId());
            tableDto.setRemark15(dto.getRemark());

            tableDto.setSeq15(dto.getSeq());

            tableDto.setNchangeDuration15(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9115(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9715(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9115(dto.getNforecastQty());
            }
            tableDto.setNjobType15(dto.getNjobType());
            tableDto.setNmodelNo15(dto.getNmodelNo());
            tableDto.setNpartNo15(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull15(dto.getNpartNo());
            tableDto.setNwoId15(dto.getNwoId());
            tableDto.setNremark15(dto.getNremark());

            tableDto.setNseq15(dto.getNseq());
        }else if(16 == j){
            tableDto.setChangeDuration16(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9116(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9716(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9116(dto.getForecastQty());
            }
            tableDto.setJobType16(dto.getJobType());
            tableDto.setModelNo16(dto.getModelNo());
            tableDto.setPartNo16(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull16(dto.getPartNo());
            tableDto.setWoId16(dto.getWoId());
            tableDto.setRemark16(dto.getRemark());

            tableDto.setSeq16(dto.getSeq());

            tableDto.setNchangeDuration16(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9116(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9716(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9116(dto.getNforecastQty());

            }
            tableDto.setNjobType16(dto.getNjobType());
            tableDto.setNmodelNo16(dto.getNmodelNo());
            tableDto.setNpartNo16(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull16(dto.getNpartNo());
            tableDto.setNwoId16(dto.getNwoId());
            tableDto.setNremark16(dto.getNremark());

            tableDto.setNseq16(dto.getNseq());
        }else if(17 == j){
            tableDto.setChangeDuration17(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9117(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9717(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9117(dto.getForecastQty());
            }
            tableDto.setJobType17(dto.getJobType());
            tableDto.setModelNo17(dto.getModelNo());
            tableDto.setPartNo17(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull17(dto.getPartNo());
            tableDto.setWoId17(dto.getWoId());
            tableDto.setRemark17(dto.getRemark());

            tableDto.setSeq17(dto.getSeq());

            tableDto.setNchangeDuration17(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9117(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9717(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9117(dto.getNforecastQty());
            }
            tableDto.setNjobType17(dto.getNjobType());
            tableDto.setNmodelNo17(dto.getNmodelNo());
            tableDto.setNpartNo17(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull17(dto.getNpartNo());
            tableDto.setNwoId17(dto.getNwoId());
            tableDto.setNremark17(dto.getNremark());

            tableDto.setNseq17(dto.getNseq());
        }else if(18 == j){
            tableDto.setChangeDuration18(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9118(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9718(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9118(dto.getForecastQty());
            }
            tableDto.setJobType18(dto.getJobType());
            tableDto.setModelNo18(dto.getModelNo());
            tableDto.setPartNo18(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull18(dto.getPartNo());
            tableDto.setWoId18(dto.getWoId());
            tableDto.setRemark18(dto.getRemark());

            tableDto.setSeq18(dto.getSeq());

            tableDto.setNchangeDuration18(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9118(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9718(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9118(dto.getNforecastQty());
            }
            tableDto.setNjobType18(dto.getNjobType());
            tableDto.setNmodelNo18(dto.getNmodelNo());
            tableDto.setNpartNo18(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull18(dto.getNpartNo());
            tableDto.setNwoId18(dto.getNwoId());
            tableDto.setNremark18(dto.getNremark());

            tableDto.setNseq18(dto.getNseq());
        }else if(19 == j){
            tableDto.setChangeDuration19(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9119(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9719(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9119(dto.getForecastQty());
            }
            tableDto.setJobType19(dto.getJobType());
            tableDto.setModelNo19(dto.getModelNo());
            tableDto.setPartNo19(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull19(dto.getPartNo());
            tableDto.setWoId19(dto.getWoId());
            tableDto.setRemark19(dto.getRemark());

            tableDto.setSeq19(dto.getSeq());

            tableDto.setNchangeDuration19(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9119(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9719(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9119(dto.getNforecastQty());
            }
            tableDto.setNjobType19(dto.getNjobType());
            tableDto.setNmodelNo19(dto.getNmodelNo());
            tableDto.setNpartNo19(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull19(dto.getNpartNo());
            tableDto.setNwoId19(dto.getNwoId());
            tableDto.setNremark19(dto.getNremark());

            tableDto.setNseq19(dto.getNseq());
        }else if(20 == j){
            tableDto.setChangeDuration20(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9120(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9720(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9120(dto.getForecastQty());
            }
            tableDto.setJobType20(dto.getJobType());
            tableDto.setModelNo20(dto.getModelNo());
            tableDto.setPartNo20(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull20(dto.getPartNo());
            tableDto.setWoId20(dto.getWoId());
            tableDto.setRemark20(dto.getRemark());

            tableDto.setSeq20(dto.getSeq());

            tableDto.setNchangeDuration20(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9120(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9720(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9120(dto.getNforecastQty());
            }
            tableDto.setNjobType20(dto.getNjobType());
            tableDto.setNmodelNo20(dto.getNmodelNo());
            tableDto.setNpartNo20(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull20(dto.getNpartNo());
            tableDto.setNwoId20(dto.getNwoId());
            tableDto.setNremark20(dto.getNremark());

            tableDto.setNseq20(dto.getNseq());
        }else if(21 == j){
            tableDto.setChangeDuration21(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9121(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9721(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9121(dto.getForecastQty());
            }
            tableDto.setJobType21(dto.getJobType());
            tableDto.setModelNo21(dto.getModelNo());
            tableDto.setPartNo21(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull21(dto.getPartNo());
            tableDto.setWoId21(dto.getWoId());
            tableDto.setRemark21(dto.getRemark());

            tableDto.setSeq21(dto.getSeq());

            tableDto.setNchangeDuration21(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9121(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9721(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9121(dto.getNforecastQty());
            }
            tableDto.setNjobType21(dto.getNjobType());
            tableDto.setNmodelNo21(dto.getNmodelNo());
            tableDto.setNpartNo21(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull21(dto.getNpartNo());
            tableDto.setNwoId21(dto.getNwoId());
            tableDto.setNremark21(dto.getNremark());

            tableDto.setNseq21(dto.getNseq());
        }else if(22 == j){
            tableDto.setChangeDuration22(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9122(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9722(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9122(dto.getForecastQty());
            }
            tableDto.setJobType22(dto.getJobType());
            tableDto.setModelNo22(dto.getModelNo());
            tableDto.setPartNo22(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull22(dto.getPartNo());
            tableDto.setWoId22(dto.getWoId());
            tableDto.setRemark22(dto.getRemark());

            tableDto.setSeq22(dto.getSeq());

            tableDto.setNchangeDuration22(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9122(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9722(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9122(dto.getNforecastQty());
            }
            tableDto.setNjobType22(dto.getNjobType());
            tableDto.setNmodelNo22(dto.getNmodelNo());
            tableDto.setNpartNo22(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull22(dto.getNpartNo());
            tableDto.setNwoId22(dto.getNwoId());
            tableDto.setNremark22(dto.getNremark());

            tableDto.setNseq22(dto.getNseq());
        }else if(23 == j){
            tableDto.setChangeDuration23(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9123(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9723(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9123(dto.getForecastQty());
            }
            tableDto.setJobType23(dto.getJobType());
            tableDto.setModelNo23(dto.getModelNo());
            tableDto.setPartNo23(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull23(dto.getPartNo());
            tableDto.setWoId23(dto.getWoId());
            tableDto.setRemark23(dto.getRemark());

            tableDto.setSeq23(dto.getSeq());

            tableDto.setNchangeDuration23(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9123(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9723(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9123(dto.getNforecastQty());
            }
            tableDto.setNjobType23(dto.getNjobType());
            tableDto.setNmodelNo23(dto.getNmodelNo());
            tableDto.setNpartNo23(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull23(dto.getNpartNo());
            tableDto.setNwoId23(dto.getNwoId());
            tableDto.setNremark23(dto.getNremark());

            tableDto.setNseq23(dto.getNseq());
        }else if(24 == j){
            tableDto.setChangeDuration24(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9124(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9724(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9124(dto.getForecastQty());
            }
            tableDto.setJobType24(dto.getJobType());
            tableDto.setModelNo24(dto.getModelNo());
            tableDto.setPartNo24(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull24(dto.getPartNo());
            tableDto.setWoId24(dto.getWoId());
            tableDto.setRemark24(dto.getRemark());

            tableDto.setSeq24(dto.getSeq());

            tableDto.setNchangeDuration24(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9124(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9724(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9124(dto.getNforecastQty());
            }
            tableDto.setNjobType24(dto.getNjobType());
            tableDto.setNmodelNo24(dto.getNmodelNo());
            tableDto.setNpartNo24(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull24(dto.getNpartNo());
            tableDto.setNwoId24(dto.getNwoId());
            tableDto.setNremark24(dto.getNremark());

            tableDto.setNseq24(dto.getNseq());
        }else if(25 == j){
            tableDto.setChangeDuration25(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9125(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9725(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9125(dto.getForecastQty());
            }
            tableDto.setJobType25(dto.getJobType());
            tableDto.setModelNo25(dto.getModelNo());
            tableDto.setPartNo25(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull25(dto.getPartNo());
            tableDto.setWoId25(dto.getWoId());
            tableDto.setRemark25(dto.getRemark());

            tableDto.setSeq25(dto.getSeq());

            tableDto.setNchangeDuration25(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9125(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9725(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9125(dto.getNforecastQty());
            }
            tableDto.setNjobType25(dto.getNjobType());
            tableDto.setNmodelNo25(dto.getNmodelNo());
            tableDto.setNpartNo25(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull25(dto.getNpartNo());
            tableDto.setNwoId25(dto.getNwoId());
            tableDto.setNremark25(dto.getNremark());

            tableDto.setNseq25(dto.getNseq());
        }else if(26 == j){
            tableDto.setChangeDuration26(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9126(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9726(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9126(dto.getForecastQty());
            }
            tableDto.setJobType26(dto.getJobType());
            tableDto.setModelNo26(dto.getModelNo());
            tableDto.setPartNo26(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull26(dto.getPartNo());
            tableDto.setWoId26(dto.getWoId());
            tableDto.setRemark26(dto.getRemark());

            tableDto.setSeq26(dto.getSeq());

            tableDto.setNchangeDuration26(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9126(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9726(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9126(dto.getNforecastQty());
            }
            tableDto.setNjobType26(dto.getNjobType());
            tableDto.setNmodelNo26(dto.getNmodelNo());
            tableDto.setNpartNo26(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull26(dto.getNpartNo());
            tableDto.setNwoId26(dto.getNwoId());
            tableDto.setNremark26(dto.getNremark());

            tableDto.setNseq26(dto.getNseq());
        }else if(27 == j){
            tableDto.setChangeDuration27(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9127(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9727(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9127(dto.getForecastQty());
            }
            tableDto.setJobType27(dto.getJobType());
            tableDto.setModelNo27(dto.getModelNo());
            tableDto.setPartNo27(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull27(dto.getPartNo());
            tableDto.setWoId27(dto.getWoId());
            tableDto.setRemark27(dto.getRemark());

            tableDto.setSeq27(dto.getSeq());

            tableDto.setNchangeDuration27(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9127(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9727(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9127(dto.getNforecastQty());
            }
            tableDto.setNjobType27(dto.getNjobType());
            tableDto.setNmodelNo27(dto.getNmodelNo());
            tableDto.setNpartNo27(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull27(dto.getNpartNo());
            tableDto.setNwoId27(dto.getNwoId());
            tableDto.setNremark27(dto.getNremark());

            tableDto.setNseq27(dto.getNseq());
        }else if(28 == j){
            tableDto.setChangeDuration28(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9128(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9728(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9128(dto.getForecastQty());
            }
            tableDto.setJobType28(dto.getJobType());
            tableDto.setModelNo28(dto.getModelNo());
            tableDto.setPartNo28(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull28(dto.getPartNo());
            tableDto.setWoId28(dto.getWoId());
            tableDto.setRemark28(dto.getRemark());

            tableDto.setSeq28(dto.getSeq());

            tableDto.setNchangeDuration28(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9128(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9728(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9128(dto.getNforecastQty());
            }
            tableDto.setNjobType28(dto.getNjobType());
            tableDto.setNmodelNo28(dto.getNmodelNo());
            tableDto.setNpartNo28(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull28(dto.getNpartNo());
            tableDto.setNwoId28(dto.getNwoId());
            tableDto.setNremark28(dto.getNremark());

            tableDto.setNseq28(dto.getNseq());
        }else if(29 == j){
            tableDto.setChangeDuration29(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9129(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9729(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9129(dto.getForecastQty());
            }
            tableDto.setJobType29(dto.getJobType());
            tableDto.setModelNo29(dto.getModelNo());
            tableDto.setPartNo29(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull29(dto.getPartNo());
            tableDto.setWoId29(dto.getWoId());
            tableDto.setRemark29(dto.getRemark());

            tableDto.setSeq29(dto.getSeq());

            tableDto.setNchangeDuration29(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9129(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9729(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9129(dto.getNforecastQty());
            }
            tableDto.setNjobType29(dto.getNjobType());
            tableDto.setNmodelNo29(dto.getNmodelNo());
            tableDto.setNpartNo29(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull29(dto.getNpartNo());
            tableDto.setNwoId29(dto.getNwoId());
            tableDto.setNremark29(dto.getNremark());

            tableDto.setNseq29(dto.getNseq());
        }else if(30 == j){
            tableDto.setChangeDuration30(dto.getChangeDuration());
            if(dto.getPartNo() != null && dto.getPartNo().indexOf("91") > -1){
                tableDto.setForecastQty9130(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("97") > -1){
                tableDto.setForecastQty9730(dto.getForecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setForecastQty9130(dto.getForecastQty());
            }
            tableDto.setJobType30(dto.getJobType());
            tableDto.setModelNo30(dto.getModelNo());
            tableDto.setPartNo30(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull30(dto.getPartNo());
            tableDto.setWoId30(dto.getWoId());
            tableDto.setRemark30(dto.getRemark());

            tableDto.setSeq30(dto.getSeq());

            tableDto.setNchangeDuration30(dto.getNchangeDuration());
            if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("91") > -1){
                tableDto.setNforecastQty9130(dto.getNforecastQty());
            }else if(dto.getNpartNo() != null && dto.getNpartNo().indexOf("97") > -1){
                tableDto.setNforecastQty9730(dto.getNforecastQty());
            }else if(dto.getPartNo() != null && dto.getPartNo().indexOf("90") > -1){
                tableDto.setNforecastQty9130(dto.getNforecastQty());
            }
            tableDto.setNjobType30(dto.getNjobType());
            tableDto.setNmodelNo30(dto.getNmodelNo());
            tableDto.setNpartNo30(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull30(dto.getNpartNo());
            tableDto.setNwoId30(dto.getNwoId());
            tableDto.setNremark30(dto.getNremark());

            tableDto.setNseq30(dto.getNseq());
        }
    }


}
