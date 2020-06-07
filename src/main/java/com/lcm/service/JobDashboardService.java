package com.lcm.service;

import com.lcm.domain.HJobDashboard;
import com.lcm.domain.JobDashboard;
import com.lcm.domain.RJobDashboard;
import com.lcm.domain.dto.*;
import com.lcm.repository.HJobDashboardRepository;
import com.lcm.repository.RJobDashboardRepository;
import com.lcm.service.dto.TJobStatisticsDTO;
import com.lcm.util.DateUtils;
import com.lcm.util.TableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobDashboardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobDashboardService.class);

    private final RJobDashboardRepository rJobDashboardRepository;

    private final HJobDashboardRepository hJobDashboardRepository;



    public  JobDashboardService(RJobDashboardRepository rJobDashboardRepository,HJobDashboardRepository hJobDashboardRepository){
        this.rJobDashboardRepository = rJobDashboardRepository;
        this.hJobDashboardRepository = hJobDashboardRepository;

    }

    //第一步、整合R、H表，返回基础数据
    public List<JobDashboard> getTJOBData(String site, String fab,String area, String startTime, String endTime){
        LOGGER.info("Table查询条件:"+site+"，"+fab+"，"+area+"，"+startTime+"，"+endTime);
        startTime = startTime.replace("/","-");
        endTime = endTime.replace("/","-");
        LocalDate startDate = LocalDate.parse(startTime, DateUtils.dateFormatter1);
        LocalDate endDate = LocalDate.parse(endTime, DateUtils.dateFormatter1);
        List<JobDashboard> TList = new ArrayList<JobDashboard>();
        List<Object> rList = null;
        List<Object> hList = null;
        if(!StringUtils.isEmpty(fab) && StringUtils.isEmpty(area)){
            rList = rJobDashboardRepository.findBySiteAndFabAndShiftDate(site, fab, startDate, endDate);
            rList.addAll(rJobDashboardRepository.findBySiteAndFabAndShiftDateForPm(site, fab, startDate, endDate));
            rList.addAll(rJobDashboardRepository.findBySiteAndFabAndShiftDateForNonSchedule(site, fab, startDate, endDate));
            rList.addAll(rJobDashboardRepository.findBySiteAndFabAndShiftDateForChange(site, fab, startDate, endDate));

            hList = hJobDashboardRepository.findBySiteAndFabAndShiftDate(site, fab, startDate, endDate);
            hList.addAll(hJobDashboardRepository.findBySiteAndFabAndShiftDateForPm(site, fab, startDate, endDate));
            hList.addAll(hJobDashboardRepository.findBySiteAndFabAndShiftDateForNonSchedule(site, fab, startDate, endDate));
            hList.addAll(hJobDashboardRepository.findBySiteAndFabAndShiftDateForChange(site, fab, startDate, endDate));

        }else if(!StringUtils.isEmpty(fab) && !StringUtils.isEmpty(area)){
            rList = rJobDashboardRepository.findBySiteAndFabAndShiftDate(site, fab, area,startDate, endDate);
            rList.addAll(rJobDashboardRepository.findBySiteAndFabAndShiftDateForPm(site, fab,area, startDate, endDate));
            rList.addAll(rJobDashboardRepository.findBySiteAndFabAndShiftDateForNonSchedule(site, fab,area, startDate, endDate));
            rList.addAll(rJobDashboardRepository.findBySiteAndFabAndShiftDateForChange(site, fab,area, startDate, endDate));

            hList = hJobDashboardRepository.findBySiteAndFabAndShiftDate(site, fab, area,startDate, endDate);
            hList.addAll(hJobDashboardRepository.findBySiteAndFabAndShiftDateForPm(site, fab,area, startDate, endDate));
            hList.addAll(hJobDashboardRepository.findBySiteAndFabAndShiftDateForNonSchedule(site, fab,area, startDate, endDate));
            hList.addAll(hJobDashboardRepository.findBySiteAndFabAndShiftDateForChange(site, fab,area, startDate, endDate));
        }else{
            rList = rJobDashboardRepository.findBySiteAndShiftDate(site, startDate, endDate);
            rList.addAll(rJobDashboardRepository.findBySiteAndShiftDateForPm(site, startDate, endDate));
            rList.addAll(rJobDashboardRepository.findBySiteAndShiftDateForNonSchedule(site, startDate, endDate));
            rList.addAll(rJobDashboardRepository.findBySiteAndShiftDateForChange(site, startDate, endDate));

            hList = hJobDashboardRepository.findBySiteAndShiftDate(site, startDate, endDate);
            hList.addAll(hJobDashboardRepository.findBySiteAndShiftDateForPm(site, startDate, endDate));
            hList.addAll(hJobDashboardRepository.findBySiteAndShiftDateForNonSchedule(site, startDate, endDate));
            hList.addAll(hJobDashboardRepository.findBySiteAndShiftDateForChange(site, startDate, endDate));

        }

        for (int i = 0; i < rList.size(); i++) {
            JobDashboard t = new JobDashboard();
            Object[] obj = (Object[]) rList.get(i);
            t.setJobId("r" + i);
            t.setLine((String) obj[0]);
            t.setShiftDate((LocalDate) obj[1]);
            t.setShift((String) obj[2]);
            String jobType = (String) obj[3];
            t.setJobType(jobType);
            t.setModelNo((String) obj[4]);
            String partNo = (String) obj[5];
            t.setForecastQty(Integer.parseInt(obj[6].toString()));
            t.setFab((String) obj[7]);
            t.setWoId("NULL".equals(obj[8]) ? "-" : (String) obj[8]);
            t.setProcessStartTime((LocalDateTime) obj[9]);
            if ("PM".equals(jobType) || "NON-SCHEDULE".equals(jobType)) {
                String grade = (String) obj[11];
                t.setPartNo(TableUtils.handlePartNo(partNo,grade));
                t.setRemark((String)obj[12]);

                t.setProcessEndTime((LocalDateTime) obj[10]);
            } else if ("CHANGE".equals(jobType)) {
                String grade = (String) obj[13];
                t.setPartNo(TableUtils.handlePartNo(partNo,grade));
                t.setRemark((String)obj[14]);

                t.setChangeLevel((String) obj[10]);
                t.setChangeDuration(String.valueOf(obj[11]));
                t.setChangeKey((String) obj[12]);
            }else{
                String grade = (String) obj[10];
                t.setPartNo(TableUtils.handlePartNo(partNo,grade));
                t.setRemark((String)obj[11]);

            }
            TList.add(t);
        }
        for (int i = 0; i < hList.size(); i++) {
            JobDashboard t = new JobDashboard();
            Object[] obj = (Object[]) hList.get(i);
            t.setJobId("h" + i);
            t.setLine((String) obj[0]);
            t.setShiftDate((LocalDate) obj[1]);
            t.setShift((String) obj[2]);
            String jobType = (String) obj[3];
            t.setJobType(jobType);
            t.setModelNo((String) obj[4]);
            String partNo = (String) obj[5];
            t.setForecastQty(Integer.parseInt(obj[6].toString()));
            t.setFab((String) obj[7]);
            t.setWoId("NULL".equals(obj[8]) ? "-" : (String) obj[8]);
            t.setProcessStartTime((LocalDateTime) obj[9]);
            if ("PM".equals(jobType) || "NON-SCHEDULE".equals(jobType)) {
                String grade = (String) obj[11];
                t.setPartNo(TableUtils.handlePartNo(partNo,grade));
                t.setRemark((String)obj[12]);

                t.setProcessEndTime((LocalDateTime) obj[10]);
            } else if ("CHANGE".equals(jobType)) {
                String grade = (String) obj[13];
                t.setPartNo(TableUtils.handlePartNo(partNo,grade));
                t.setRemark((String)obj[14]);

                t.setChangeLevel((String) obj[10]);
                t.setChangeDuration(String.valueOf(obj[11]));
                t.setChangeKey((String) obj[12]);
            }else{
                String grade = (String) obj[10];//TODO grade
                t.setPartNo(TableUtils.handlePartNo(partNo,grade));
                t.setRemark((String)obj[11]);

            }

            TList.add(t);
        }
        return TList;
    }

    //第二步、合并白晚班并返回最终数据
    @Transactional
    public JobDto getListTable(List<JobDashboard> tJobList, LinkedList<String> linkedList) {
        Map<String, TJobStatisticsDTO> fabLineMap = TableUtils.handleTJobList(tJobList);
        for (String existsLine : fabLineMap.keySet()){
            linkedList.remove(existsLine);
        }
        int sum = 0;
        List<JobTableDto> tableDtoList= new ArrayList<JobTableDto>();
        List<NoScheduleDto>  noScheduleTableDtoList= new ArrayList<>();
        List<LocalDate> shiftDates = tJobList.stream().map(t -> t.getShiftDate()).distinct().sorted().collect(Collectors.toList()); //获取区间查询日期
        LOGGER.info("一共查询：" + shiftDates.size() + "天。");
        int dCount;
        int nCount;
        List<List<JobDashboardDto>> allListDto = new ArrayList<List<JobDashboardDto>>();//最终返回的结果集

        for (int h = 0; h < shiftDates.size(); h++) {
            LocalDate shiftDate = shiftDates.get(h);
            List<JobDashboardDto> listDto = new ArrayList<JobDashboardDto>();

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
                    dLineList.sort(Comparator.comparing(JobDashboard::getProcessStartTime));
                }
                if (nLineList == null) {
                    nLineList = new ArrayList<>();
                } else {
                    nLineList.sort(Comparator.comparing(JobDashboard::getProcessStartTime));
                }
                dCount = dLineList.size();
                nCount = nLineList.size();
                if(dCount < count || nCount < count){
                    for (int i = 0; i < count - dCount; i++){
                        JobDashboard obj = new JobDashboard();
                        obj.setFab(fab);
                        obj.setLine(line);//
                        obj.setModelNo("-");
                        obj.setPartNo("-");
                        obj.setWoId("-");
                        obj.setRemark("-");
                        dLineList.add(obj);
                    }
                    for (int i = 0; i < count - nCount; i++){
                        JobDashboard obj = new JobDashboard();
                        obj.setFab(fab);
                        obj.setLine(line);//
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
                    JobDashboard dObj = dLineList.get(i);//白班
                    String dJobType = dObj.getJobType();
                    String dModelNo = dObj.getModelNo();
                    if("ENG".equals(dJobType)){
                        dto.setModelNo("*" + dModelNo);
                    }else if("SPL".equals(dJobType)){
                        dto.setModelNo("#" + dModelNo);
                    }else if("PM".equals(dJobType) || "NON-SCHEDULE".equals(dJobType)){
                        LocalDateTime processEndTime = dObj.getProcessEndTime();
                        LocalDateTime processStartTime = dObj.getProcessStartTime();
                        BigDecimal bigDecimal = new BigDecimal(Duration.between(processStartTime, processEndTime).getSeconds() / 3600.0).setScale(2, RoundingMode.HALF_UP);
                        dto.setModelNo(dJobType + ":" + bigDecimal.doubleValue());
                    }else if ("CHANGE".equals(dJobType)){
                        dto.setModelNo("CHANGE:" + (dObj.getChangeKey() == null ? "-" : dObj.getChangeKey()) + ":" + dObj.getChangeLevel() + ":" + dObj.getChangeDuration());
                    } else {
                        dto.setModelNo(dModelNo);
                    }
                    dto.setPartNo(dObj.getPartNo());
                    dto.setWoId(dObj.getWoId());
                    dto.setRemark(dObj.getRemark());
                    dto.setForecastQty(dObj.getForecastQty());
                    dto.setJobType(dObj.getJobType());
                    dto.setChangeDuration(dObj.getChangeDuration());

                    JobDashboard nObj = nLineList.get(i);//晚班
                    String nJobType = nObj.getJobType();
                    String nModelNo = nObj.getModelNo();
                    if("ENG".equals(nJobType)){
                        dto.setNmodelNo("*" + nModelNo);
                    }else if("SPL".equals(nJobType)){
                        dto.setNmodelNo("#" + nModelNo);
                    }else if("PM".equals(nJobType) || "NON-SCHEDULE".equals(nJobType)){
                        LocalDateTime processEndTime = nObj.getProcessEndTime();
                        LocalDateTime processStartTime = nObj.getProcessStartTime();
                        BigDecimal bigDecimal = new BigDecimal((processEndTime.getSecond() - processStartTime.getSecond()) / 3600.0).setScale(2, RoundingMode.UP);
                        dto.setNmodelNo(nJobType + ":" + bigDecimal.doubleValue());
                    }else if ("CHANGE".equals(nJobType)){
                        dto.setNmodelNo("CHANGE:" + (nObj.getChangeKey() == null ? "-" : nObj.getChangeKey()) + ":" + nObj.getChangeLevel() + ":" + nObj.getChangeDuration());
                    } else {
                        dto.setNmodelNo(nModelNo);
                    }
                    dto.setNpartNo(nObj.getPartNo());
                    dto.setNwoId(nObj.getWoId());
                    dto.setNremark(nObj.getRemark());
                    dto.setNforecastQty(nObj.getForecastQty());
                    dto.setNjobType(nObj.getJobType());
                    dto.setNchangeDuration(nObj.getChangeDuration());
                    listDto.add(dto);
                }
            }
            if( h == 0 ) {//每一天listDto的长度是一样的
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

        for(String linked : linkedList){
            NoScheduleDto noScheduleDto = new NoScheduleDto();
            String[] split = linked.split(TableUtils.FLAG);
            noScheduleDto.setFab(split[0]);
            noScheduleDto.setLine(split[1]);
            noScheduleTableDtoList.add(noScheduleDto);
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
        Map<String, Integer> shiftDateShiftPartNoCountMap = TableUtils.getShiftDateShiftPartNoCountMap(tJobList);
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
        job.setNoScheduleTableDtoList(noScheduleTableDtoList);
        return job;
    }

    //第三步、返回表格结构
    public void getTableData(JobTableDto tableDto,JobDashboardDto dto,int j){
        if(0 == j){
            tableDto.setFab(dto.getFab());
            tableDto.setLine(dto.getLine());
            tableDto.setChangeDuration(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty91(dto.getForecastQty());
            }else{
                tableDto.setForecastQty97(dto.getForecastQty());
            }
            tableDto.setJobType(dto.getJobType());
            tableDto.setModelNo(dto.getModelNo());
            tableDto.setPartNo(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);

            tableDto.setPartNoFull(dto.getPartNo());
            tableDto.setWoId(dto.getWoId());
            tableDto.setRemark(dto.getRemark());
            tableDto.setNchangeDuration(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty91(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty97(dto.getNforecastQty());
            }
            tableDto.setNjobType(dto.getNjobType());
            tableDto.setNmodelNo(dto.getNmodelNo());
            tableDto.setNpartNo(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull(dto.getNpartNo());
            tableDto.setNwoId(dto.getNwoId());
            tableDto.setNremark(dto.getNremark());
        }else if(1 == j){
            tableDto.setChangeDuration1(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty911(dto.getForecastQty());
            }else{
                tableDto.setForecastQty971(dto.getForecastQty());
            }
            tableDto.setJobType1(dto.getJobType());
            tableDto.setModelNo1(dto.getModelNo());
            tableDto.setPartNo1(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull1(dto.getPartNo());
            tableDto.setWoId1(dto.getWoId());
            tableDto.setRemark1(dto.getRemark());

            tableDto.setNchangeDuration1(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty911(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty971(dto.getNforecastQty());
            }
            tableDto.setNjobType1(dto.getNjobType());
            tableDto.setNmodelNo1(dto.getNmodelNo());
            tableDto.setNpartNo1(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull1(dto.getNpartNo());
            tableDto.setNwoId1(dto.getNwoId());
            tableDto.setNremark1(dto.getNremark());

        }else if(2 == j){
            tableDto.setChangeDuration2(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty912(dto.getForecastQty());
            }else{
                tableDto.setForecastQty972(dto.getForecastQty());
            }
            tableDto.setJobType2(dto.getJobType());
            tableDto.setModelNo2(dto.getModelNo());
            tableDto.setPartNo2(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull2(dto.getPartNo());
            tableDto.setWoId2(dto.getWoId());
            tableDto.setRemark2(dto.getRemark());

            tableDto.setNchangeDuration2(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty912(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty972(dto.getNforecastQty());
            }
            tableDto.setNjobType2(dto.getNjobType());
            tableDto.setNmodelNo2(dto.getNmodelNo());
            tableDto.setNpartNo2(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull2(dto.getNpartNo());
            tableDto.setNwoId2(dto.getNwoId());
            tableDto.setNremark2(dto.getNremark());

        }else if(3 == j){
            tableDto.setChangeDuration3(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty913(dto.getForecastQty());
            }else{
                tableDto.setForecastQty973(dto.getForecastQty());
            }
            tableDto.setJobType3(dto.getJobType());
            tableDto.setModelNo3(dto.getModelNo());
            tableDto.setPartNo3(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull3(dto.getPartNo());
            tableDto.setWoId3(dto.getWoId());
            tableDto.setRemark3(dto.getRemark());

            tableDto.setNchangeDuration3(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty913(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty973(dto.getNforecastQty());
            }
            tableDto.setNjobType3(dto.getNjobType());
            tableDto.setNmodelNo3(dto.getNmodelNo());
            tableDto.setNpartNo3(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull3(dto.getNpartNo());
            tableDto.setNwoId3(dto.getNwoId());
            tableDto.setNremark3(dto.getNremark());

        }else if(4 == j){
            tableDto.setChangeDuration4(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty914(dto.getForecastQty());
            }else{
                tableDto.setForecastQty974(dto.getForecastQty());
            }
            tableDto.setJobType4(dto.getJobType());
            tableDto.setModelNo4(dto.getModelNo());
            tableDto.setPartNo4(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull4(dto.getPartNo());
            tableDto.setWoId4(dto.getWoId());
            tableDto.setRemark4(dto.getRemark());

            tableDto.setNchangeDuration4(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty914(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty974(dto.getNforecastQty());
            }
            tableDto.setNjobType4(dto.getNjobType());
            tableDto.setNmodelNo4(dto.getNmodelNo());
            tableDto.setNpartNo4(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull4(dto.getNpartNo());
            tableDto.setNwoId4(dto.getNwoId());
            tableDto.setNremark4(dto.getNremark());

        }else if(5 == j){
            tableDto.setChangeDuration5(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty915(dto.getForecastQty());
            }else{
                tableDto.setForecastQty975(dto.getForecastQty());
            }
            tableDto.setJobType5(dto.getJobType());
            tableDto.setModelNo5(dto.getModelNo());
            tableDto.setPartNo5(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull5(dto.getPartNo());
            tableDto.setWoId5(dto.getWoId());
            tableDto.setRemark5(dto.getRemark());

            tableDto.setNchangeDuration5(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty915(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty975(dto.getNforecastQty());
            }
            tableDto.setNjobType5(dto.getNjobType());
            tableDto.setNmodelNo5(dto.getNmodelNo());
            tableDto.setNpartNo5(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull5(dto.getNpartNo());
            tableDto.setNwoId5(dto.getNwoId());
            tableDto.setNremark5(dto.getNremark());

        }else if(6 == j){
            tableDto.setChangeDuration6(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty916(dto.getForecastQty());
            }else{
                tableDto.setForecastQty976(dto.getForecastQty());
            }
            tableDto.setJobType6(dto.getJobType());
            tableDto.setModelNo6(dto.getModelNo());
            tableDto.setPartNo6(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull6(dto.getPartNo());
            tableDto.setWoId6(dto.getWoId());
            tableDto.setRemark6(dto.getRemark());

            tableDto.setNchangeDuration6(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty916(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty976(dto.getNforecastQty());
            }
            tableDto.setNjobType6(dto.getNjobType());
            tableDto.setNmodelNo6(dto.getNmodelNo());
            tableDto.setNpartNo6(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull6(dto.getNpartNo());
            tableDto.setNwoId6(dto.getNwoId());
            tableDto.setNremark6(dto.getNremark());

        }else if(7 == j){
            tableDto.setChangeDuration7(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty917(dto.getForecastQty());
            }else{
                tableDto.setForecastQty977(dto.getForecastQty());
            }
            tableDto.setJobType7(dto.getJobType());
            tableDto.setModelNo7(dto.getModelNo());
            tableDto.setPartNo7(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull7(dto.getPartNo());
            tableDto.setWoId7(dto.getWoId());
            tableDto.setRemark7(dto.getRemark());

            tableDto.setNchangeDuration7(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty917(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty977(dto.getNforecastQty());
            }
            tableDto.setNjobType7(dto.getNjobType());
            tableDto.setNmodelNo7(dto.getNmodelNo());
            tableDto.setNpartNo7(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull7(dto.getNpartNo());
            tableDto.setNwoId7(dto.getNwoId());
            tableDto.setNremark7(dto.getNremark());

        }else if(8 == j){
            tableDto.setChangeDuration8(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty918(dto.getForecastQty());
            }else{
                tableDto.setForecastQty978(dto.getForecastQty());
            }
            tableDto.setJobType8(dto.getJobType());
            tableDto.setModelNo8(dto.getModelNo());
            tableDto.setPartNo8(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull8(dto.getPartNo());
            tableDto.setWoId8(dto.getWoId());
            tableDto.setRemark8(dto.getRemark());

            tableDto.setNchangeDuration8(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty918(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty978(dto.getNforecastQty());
            }
            tableDto.setNjobType8(dto.getNjobType());
            tableDto.setNmodelNo8(dto.getNmodelNo());
            tableDto.setNpartNo8(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull8(dto.getNpartNo());
            tableDto.setNwoId8(dto.getNwoId());
            tableDto.setNremark8(dto.getNremark());

        }else if(9 == j){
            tableDto.setChangeDuration9(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty919(dto.getForecastQty());
            }else{
                tableDto.setForecastQty979(dto.getForecastQty());
            }
            tableDto.setJobType9(dto.getJobType());
            tableDto.setModelNo9(dto.getModelNo());
            tableDto.setPartNo9(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull9(dto.getPartNo());
            tableDto.setWoId9(dto.getWoId());
            tableDto.setRemark9(dto.getRemark());

            tableDto.setNchangeDuration9(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty919(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty979(dto.getNforecastQty());
            }
            tableDto.setNjobType9(dto.getNjobType());
            tableDto.setNmodelNo9(dto.getNmodelNo());
            tableDto.setNpartNo9(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull9(dto.getNpartNo());
            tableDto.setNwoId9(dto.getNwoId());
            tableDto.setNremark9(dto.getNremark());

        }else if(10 == j){
            tableDto.setChangeDuration10(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9110(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9710(dto.getForecastQty());
            }
            tableDto.setJobType10(dto.getJobType());
            tableDto.setModelNo10(dto.getModelNo());
            tableDto.setPartNo10(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull10(dto.getPartNo());
            tableDto.setWoId10(dto.getWoId());
            tableDto.setRemark10(dto.getRemark());

            tableDto.setNchangeDuration10(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9110(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9710(dto.getNforecastQty());
            }
            tableDto.setNjobType10(dto.getNjobType());
            tableDto.setNmodelNo10(dto.getNmodelNo());
            tableDto.setNpartNo10(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull10(dto.getNpartNo());
            tableDto.setNwoId10(dto.getNwoId());
            tableDto.setNremark10(dto.getNremark());

        }else if(11 == j){
            tableDto.setChangeDuration11(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9111(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9711(dto.getForecastQty());
            }
            tableDto.setJobType11(dto.getJobType());
            tableDto.setModelNo11(dto.getModelNo());
            tableDto.setPartNo11(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull11(dto.getPartNo());
            tableDto.setWoId11(dto.getWoId());
            tableDto.setRemark11(dto.getRemark());

            tableDto.setNchangeDuration11(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9111(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9711(dto.getNforecastQty());
            }
            tableDto.setNjobType11(dto.getNjobType());
            tableDto.setNmodelNo11(dto.getNmodelNo());
            tableDto.setNpartNo11(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull11(dto.getNpartNo());
            tableDto.setNwoId11(dto.getNwoId());
            tableDto.setNremark11(dto.getNremark());

        }else if(12 == j){
            tableDto.setChangeDuration12(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9112(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9712(dto.getForecastQty());
            }
            tableDto.setJobType12(dto.getJobType());
            tableDto.setModelNo12(dto.getModelNo());
            tableDto.setPartNo12(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull12(dto.getPartNo());
            tableDto.setWoId12(dto.getWoId());
            tableDto.setRemark12(dto.getRemark());

            tableDto.setNchangeDuration12(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9112(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9712(dto.getNforecastQty());
            }
            tableDto.setNjobType12(dto.getNjobType());
            tableDto.setNmodelNo12(dto.getNmodelNo());
            tableDto.setNpartNo12(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull12(dto.getNpartNo());
            tableDto.setNwoId12(dto.getNwoId());
            tableDto.setNremark12(dto.getNremark());

        }else if(13 == j){
            tableDto.setChangeDuration13(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9113(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9713(dto.getForecastQty());
            }
            tableDto.setJobType13(dto.getJobType());
            tableDto.setModelNo13(dto.getModelNo());
            tableDto.setPartNo13(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull13(dto.getPartNo());
            tableDto.setWoId13(dto.getWoId());
            tableDto.setRemark13(dto.getRemark());

            tableDto.setNchangeDuration13(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9113(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9713(dto.getNforecastQty());
            }
            tableDto.setNjobType13(dto.getNjobType());
            tableDto.setNmodelNo13(dto.getNmodelNo());
            tableDto.setNpartNo13(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull13(dto.getNpartNo());
            tableDto.setNwoId13(dto.getNwoId());
            tableDto.setNremark13(dto.getNremark());

        }else if(14 == j){
            tableDto.setChangeDuration14(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9114(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9714(dto.getForecastQty());
            }
            tableDto.setJobType14(dto.getJobType());
            tableDto.setModelNo14(dto.getModelNo());
            tableDto.setPartNo14(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull14(dto.getPartNo());
            tableDto.setWoId14(dto.getWoId());
            tableDto.setRemark14(dto.getRemark());

            tableDto.setNchangeDuration14(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9114(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9714(dto.getNforecastQty());
            }
            tableDto.setNjobType14(dto.getNjobType());
            tableDto.setNmodelNo14(dto.getNmodelNo());
            tableDto.setNpartNo14(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull14(dto.getNpartNo());
            tableDto.setNwoId14(dto.getNwoId());
            tableDto.setNremark14(dto.getNremark());

        }else if(15 == j){
            tableDto.setChangeDuration15(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9115(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9715(dto.getForecastQty());
            }
            tableDto.setJobType15(dto.getJobType());
            tableDto.setModelNo15(dto.getModelNo());
            tableDto.setPartNo15(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull15(dto.getPartNo());
            tableDto.setWoId15(dto.getWoId());
            tableDto.setRemark15(dto.getRemark());

            tableDto.setNchangeDuration15(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9115(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9715(dto.getNforecastQty());
            }
            tableDto.setNjobType15(dto.getNjobType());
            tableDto.setNmodelNo15(dto.getNmodelNo());
            tableDto.setNpartNo15(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull15(dto.getNpartNo());
            tableDto.setNwoId15(dto.getNwoId());
            tableDto.setNremark15(dto.getNremark());

        }else if(16 == j){
            tableDto.setChangeDuration16(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9116(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9716(dto.getForecastQty());
            }
            tableDto.setJobType16(dto.getJobType());
            tableDto.setModelNo16(dto.getModelNo());
            tableDto.setPartNo16(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull16(dto.getPartNo());
            tableDto.setWoId16(dto.getWoId());
            tableDto.setRemark16(dto.getRemark());

            tableDto.setNchangeDuration16(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9116(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9716(dto.getNforecastQty());
            }
            tableDto.setNjobType16(dto.getNjobType());
            tableDto.setNmodelNo16(dto.getNmodelNo());
            tableDto.setNpartNo16(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull16(dto.getNpartNo());
            tableDto.setNwoId16(dto.getNwoId());
            tableDto.setNremark16(dto.getNremark());

        }else if(17 == j){
            tableDto.setChangeDuration17(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9117(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9717(dto.getForecastQty());
            }
            tableDto.setJobType17(dto.getJobType());
            tableDto.setModelNo17(dto.getModelNo());
            tableDto.setPartNo17(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull17(dto.getPartNo());
            tableDto.setWoId17(dto.getWoId());
            tableDto.setRemark17(dto.getRemark());

            tableDto.setNchangeDuration17(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9117(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9717(dto.getNforecastQty());
            }
            tableDto.setNjobType17(dto.getNjobType());
            tableDto.setNmodelNo17(dto.getNmodelNo());
            tableDto.setNpartNo17(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull17(dto.getNpartNo());
            tableDto.setNwoId17(dto.getNwoId());
            tableDto.setNremark17(dto.getNremark());

        }else if(18 == j){
            tableDto.setChangeDuration18(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9118(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9718(dto.getForecastQty());
            }
            tableDto.setJobType18(dto.getJobType());
            tableDto.setModelNo18(dto.getModelNo());
            tableDto.setPartNo18(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull18(dto.getPartNo());
            tableDto.setWoId18(dto.getWoId());
            tableDto.setRemark18(dto.getRemark());

            tableDto.setNchangeDuration18(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9118(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9718(dto.getNforecastQty());
            }
            tableDto.setNjobType18(dto.getNjobType());
            tableDto.setNmodelNo18(dto.getNmodelNo());
            tableDto.setNpartNo18(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull18(dto.getNpartNo());
            tableDto.setNwoId18(dto.getNwoId());
            tableDto.setNremark18(dto.getNremark());

        }else if(19 == j){
            tableDto.setChangeDuration19(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9119(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9719(dto.getForecastQty());
            }
            tableDto.setJobType19(dto.getJobType());
            tableDto.setModelNo19(dto.getModelNo());
            tableDto.setPartNo19(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull19(dto.getPartNo());
            tableDto.setWoId19(dto.getWoId());
            tableDto.setRemark19(dto.getRemark());

            tableDto.setNchangeDuration19(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9119(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9719(dto.getNforecastQty());
            }
            tableDto.setNjobType19(dto.getNjobType());
            tableDto.setNmodelNo19(dto.getNmodelNo());
            tableDto.setNpartNo19(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull19(dto.getNpartNo());
            tableDto.setNwoId19(dto.getNwoId());
            tableDto.setNremark19(dto.getNremark());

        }else if(20 == j){
            tableDto.setChangeDuration20(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9120(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9720(dto.getForecastQty());
            }
            tableDto.setJobType20(dto.getJobType());
            tableDto.setModelNo20(dto.getModelNo());
            tableDto.setPartNo20(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull20(dto.getPartNo());
            tableDto.setWoId20(dto.getWoId());
            tableDto.setRemark20(dto.getRemark());

            tableDto.setNchangeDuration20(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9120(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9720(dto.getNforecastQty());
            }
            tableDto.setNjobType20(dto.getNjobType());
            tableDto.setNmodelNo20(dto.getNmodelNo());
            tableDto.setNpartNo20(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull20(dto.getNpartNo());
            tableDto.setNwoId20(dto.getNwoId());
            tableDto.setNremark20(dto.getNremark());

        }else if(21 == j){
            tableDto.setChangeDuration21(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9121(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9721(dto.getForecastQty());
            }
            tableDto.setJobType21(dto.getJobType());
            tableDto.setModelNo21(dto.getModelNo());
            tableDto.setPartNo21(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull21(dto.getPartNo());
            tableDto.setWoId21(dto.getWoId());
            tableDto.setRemark21(dto.getRemark());

            tableDto.setNchangeDuration21(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9121(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9721(dto.getNforecastQty());
            }
            tableDto.setNjobType21(dto.getNjobType());
            tableDto.setNmodelNo21(dto.getNmodelNo());
            tableDto.setNpartNo21(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull21(dto.getNpartNo());
            tableDto.setNwoId21(dto.getNwoId());
            tableDto.setNremark21(dto.getNremark());

        }else if(22 == j){
            tableDto.setChangeDuration22(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9122(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9722(dto.getForecastQty());
            }
            tableDto.setJobType22(dto.getJobType());
            tableDto.setModelNo22(dto.getModelNo());
            tableDto.setPartNo22(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull22(dto.getPartNo());
            tableDto.setWoId22(dto.getWoId());
            tableDto.setRemark22(dto.getRemark());

            tableDto.setNchangeDuration22(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9122(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9722(dto.getNforecastQty());
            }
            tableDto.setNjobType22(dto.getNjobType());
            tableDto.setNmodelNo22(dto.getNmodelNo());
            tableDto.setNpartNo22(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull22(dto.getNpartNo());
            tableDto.setNwoId22(dto.getNwoId());
            tableDto.setNremark22(dto.getNremark());

        }else if(23 == j){
            tableDto.setChangeDuration23(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9123(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9723(dto.getForecastQty());
            }
            tableDto.setJobType23(dto.getJobType());
            tableDto.setModelNo23(dto.getModelNo());
            tableDto.setPartNo23(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull23(dto.getPartNo());
            tableDto.setWoId23(dto.getWoId());
            tableDto.setRemark23(dto.getRemark());

            tableDto.setNchangeDuration23(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9123(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9723(dto.getNforecastQty());
            }
            tableDto.setNjobType23(dto.getNjobType());
            tableDto.setNmodelNo23(dto.getNmodelNo());
            tableDto.setNpartNo23(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull23(dto.getNpartNo());
            tableDto.setNwoId23(dto.getNwoId());
            tableDto.setNremark23(dto.getNremark());

        }else if(24 == j){
            tableDto.setChangeDuration24(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9124(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9724(dto.getForecastQty());
            }
            tableDto.setJobType24(dto.getJobType());
            tableDto.setModelNo24(dto.getModelNo());
            tableDto.setPartNo24(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull24(dto.getPartNo());
            tableDto.setWoId24(dto.getWoId());
            tableDto.setRemark24(dto.getRemark());

            tableDto.setNchangeDuration24(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9124(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9724(dto.getNforecastQty());
            }
            tableDto.setNjobType24(dto.getNjobType());
            tableDto.setNmodelNo24(dto.getNmodelNo());
            tableDto.setNpartNo24(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull24(dto.getNpartNo());
            tableDto.setNwoId24(dto.getNwoId());
            tableDto.setNremark24(dto.getNremark());

        }else if(25 == j){
            tableDto.setChangeDuration25(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9125(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9725(dto.getForecastQty());
            }
            tableDto.setJobType25(dto.getJobType());
            tableDto.setModelNo25(dto.getModelNo());
            tableDto.setPartNo25(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull25(dto.getPartNo());
            tableDto.setWoId25(dto.getWoId());
            tableDto.setRemark25(dto.getRemark());

            tableDto.setNchangeDuration25(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9125(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9725(dto.getNforecastQty());
            }
            tableDto.setNjobType25(dto.getNjobType());
            tableDto.setNmodelNo25(dto.getNmodelNo());
            tableDto.setNpartNo25(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull25(dto.getNpartNo());
            tableDto.setNwoId25(dto.getNwoId());
            tableDto.setNremark25(dto.getNremark());

        }else if(26 == j){
            tableDto.setChangeDuration26(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9126(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9726(dto.getForecastQty());
            }
            tableDto.setJobType26(dto.getJobType());
            tableDto.setModelNo26(dto.getModelNo());
            tableDto.setPartNo26(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull26(dto.getPartNo());
            tableDto.setWoId26(dto.getWoId());
            tableDto.setRemark26(dto.getRemark());

            tableDto.setNchangeDuration26(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9126(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9726(dto.getNforecastQty());
            }
            tableDto.setNjobType26(dto.getNjobType());
            tableDto.setNmodelNo26(dto.getNmodelNo());
            tableDto.setNpartNo26(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull26(dto.getNpartNo());
            tableDto.setNwoId26(dto.getNwoId());
            tableDto.setNremark26(dto.getNremark());

        }else if(27 == j){
            tableDto.setChangeDuration27(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9127(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9727(dto.getForecastQty());
            }
            tableDto.setJobType27(dto.getJobType());
            tableDto.setModelNo27(dto.getModelNo());
            tableDto.setPartNo27(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull27(dto.getPartNo());
            tableDto.setWoId27(dto.getWoId());
            tableDto.setRemark27(dto.getRemark());


            tableDto.setNchangeDuration27(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9127(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9727(dto.getNforecastQty());
            }
            tableDto.setNjobType27(dto.getNjobType());
            tableDto.setNmodelNo27(dto.getNmodelNo());
            tableDto.setNpartNo27(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull27(dto.getNpartNo());
            tableDto.setNwoId27(dto.getNwoId());
            tableDto.setNremark27(dto.getNremark());


        }else if(28 == j){
            tableDto.setChangeDuration28(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9128(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9728(dto.getForecastQty());
            }
            tableDto.setJobType28(dto.getJobType());
            tableDto.setModelNo28(dto.getModelNo());
            tableDto.setPartNo28(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull28(dto.getPartNo());
            tableDto.setWoId28(dto.getWoId());
            tableDto.setRemark28(dto.getRemark());


            tableDto.setNchangeDuration28(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9128(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9728(dto.getNforecastQty());
            }
            tableDto.setNjobType28(dto.getNjobType());
            tableDto.setNmodelNo28(dto.getNmodelNo());
            tableDto.setNpartNo28(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull28(dto.getNpartNo());
            tableDto.setNwoId28(dto.getNwoId());
            tableDto.setNremark28(dto.getNremark());


        }else if(29 == j){
            tableDto.setChangeDuration29(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9129(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9729(dto.getForecastQty());
            }
            tableDto.setJobType29(dto.getJobType());
            tableDto.setModelNo29(dto.getModelNo());
            tableDto.setPartNo29(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull29(dto.getPartNo());
            tableDto.setWoId29(dto.getWoId());
            tableDto.setRemark29(dto.getRemark());

            tableDto.setNchangeDuration29(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9129(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9729(dto.getNforecastQty());
            }
            tableDto.setNjobType29(dto.getNjobType());
            tableDto.setNmodelNo29(dto.getNmodelNo());
            tableDto.setNpartNo29(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull29(dto.getNpartNo());
            tableDto.setNwoId29(dto.getNwoId());
            tableDto.setNremark29(dto.getNremark());

        }else if(30 == j){
            tableDto.setChangeDuration30(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9130(dto.getForecastQty());
            }else{
                tableDto.setForecastQty9730(dto.getForecastQty());
            }
            tableDto.setJobType30(dto.getJobType());
            tableDto.setModelNo30(dto.getModelNo());
            tableDto.setPartNo30(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0,2) : null);
            tableDto.setPartNoFull30(dto.getPartNo());
            tableDto.setWoId30(dto.getWoId());
            tableDto.setRemark30(dto.getRemark());

            tableDto.setNchangeDuration30(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getNpartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9130(dto.getNforecastQty());
            }else{
                tableDto.setNforecastQty9730(dto.getNforecastQty());
            }
            tableDto.setNjobType30(dto.getNjobType());
            tableDto.setNmodelNo30(dto.getNmodelNo());
            tableDto.setNpartNo30(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0,2) : null);
            tableDto.setNpartNoFull30(dto.getNpartNo());
            tableDto.setNwoId30(dto.getNwoId());
            tableDto.setNremark30(dto.getNremark());

        }
    }

    //甘特图基础数据
    public List<JobDashboard> getTJOBChartData(String site, String fab,String area, String startTime){
        LocalDate startDate = LocalDate.parse(startTime, DateUtils.dateFormatter1);
        LocalDate endDate = startDate.plusDays(1);

        List<JobDashboard> TList = new ArrayList<JobDashboard>();
        List<RJobDashboard> rList = null;
        List<HJobDashboard> hList = null;

        if(!StringUtils.isEmpty(fab) && StringUtils.isEmpty(area)){
            rList = rJobDashboardRepository.findBySiteAndFabAndShiftDateBetween(site, fab, startDate, endDate);
            hList = hJobDashboardRepository.findBySiteAndFabAndShiftDateBetween(site, fab, startDate, endDate);
        }else if(!StringUtils.isEmpty(fab) && !StringUtils.isEmpty(area)){
            rList = rJobDashboardRepository.findBySiteAndFabAndAreaAndShiftDateBetween(site, fab,area, startDate, endDate);
            hList = hJobDashboardRepository.findBySiteAndFabAndAreaAndShiftDateBetween(site, fab,area, startDate, endDate);
        }else{
            rList = rJobDashboardRepository.findBySiteAndShiftDateBetween(site, startDate, endDate);
            hList = hJobDashboardRepository.findBySiteAndShiftDateBetween(site, startDate, endDate);
        }
        for (int i = 0; i < rList.size(); i++) {
            JobDashboard t = new JobDashboard();
            RJobDashboard obj = rList.get(i);
            t.setJobId(obj.getJobId());
            t.setSite(obj.getSite());
            t.setFab(obj.getFab());
            t.setLine(obj.getLine());
            t.setWoId(obj.getWoId());

            String partNo = obj.getPartNo();

            /*if(!StringUtils.isEmpty(partNo)){
                partNo = partNo.substring(0,2);
            }*/

            t.setPartNo(partNo);
            t.setPlanQty(obj.getPlanQty());
            t.setForecastQty(obj.getForecastQty());
            t.setShiftDate(obj.getShiftDate());
            t.setShift(obj.getShift());
            t.setJobType(obj.getJobType());
            t.setProcessStartTime(obj.getProcessStartTime());
            t.setProcessEndTime(obj.getProcessEndTime());
            t.setChangeLevel(obj.getChangeLevel());


            if("ENG".equals(obj.getJobType())){
                t.setModelNo("*"+obj.getModelNo());
            }else if("SPL".equals(obj.getJobType())){
                t.setModelNo("#"+obj.getModelNo());
            }else if("PM".equals(obj.getJobType())){
                t.setModelNo("PM");
            }else if("NON-SCHEDULE".equals(obj.getJobType())){
                t.setModelNo("NON-SCHEDULE");
            } else{
                t.setModelNo(obj.getModelNo());
            }
            t.setChangeDuration(String.valueOf(obj.getChangeDuration()));
            t.setChangeStartTime(obj.getChangeStartTime());
            t.setChangeEndTime(obj.getChangeEndTime());
            t.setLmUser(obj.getLmUser());
            t.setLmTime(obj.getLmTime());
            TList.add(t);
        }
        for (int i = 0; i < hList.size(); i++) {
            JobDashboard t = new JobDashboard();
            HJobDashboard obj = hList.get(i);
            t.setJobId(obj.getJobId());
            t.setSite(obj.getSite());
            t.setFab(obj.getFab());
            t.setLine(obj.getLine());
            t.setWoId(obj.getWoId());

            String partNo = obj.getPartNo();

            /*if(!StringUtils.isEmpty(partNo)){
                partNo = partNo.substring(0,2);
            }*/

            t.setPartNo(partNo);
            t.setPlanQty(obj.getPlanQty());
            t.setForecastQty(obj.getForecastQty());
            t.setShiftDate(obj.getShiftDate());
            t.setShift(obj.getShift());
            t.setJobType(obj.getJobType());
            t.setProcessStartTime(obj.getProcessStartTime());
            t.setProcessEndTime(obj.getProcessEndTime());
            t.setChangeLevel(obj.getChangeLevel());

            if("ENG".equals(obj.getJobType())){
                t.setModelNo("*"+obj.getModelNo());
            }else if("SPL".equals(obj.getJobType())){
                t.setModelNo("#"+obj.getModelNo());
            }else if("PM".equals(obj.getJobType())){
                t.setModelNo("PM");
            }else if("NON-SCHEDULE".equals(obj.getJobType())){
                t.setModelNo("NON-SCHEDULE");
            }else{
                t.setModelNo(obj.getModelNo());
            }

            t.setChangeDuration(String.valueOf(obj.getChangeDuration()));
            t.setChangeStartTime(obj.getChangeStartTime());
            t.setChangeEndTime(obj.getChangeEndTime());
            t.setLmUser(obj.getLmUser());
            t.setLmTime(obj.getLmTime());
            TList.add(t);
        }
        return TList;
    }

    //甘特图最终数据
    public  List<JobChartDto> getListChart(List<JobDashboard>  jobList){

        List<JobChartDto> chartList = new ArrayList<>();//结果集
        Map<String, List<JobDashboard>> lineMap = new TreeMap<>();
        jobList.forEach(t -> {
            if (lineMap.get(t.getLine()) == null) {
                List<JobDashboard> list = new ArrayList<>();
                list.add(t);
                lineMap.put(t.getLine(), list);
            } else {
                lineMap.get(t.getLine()).add(t);
            }
        });
        for (String line : lineMap.keySet()) {
            JobChartDto lineObj = new JobChartDto();
            //获得当前line，多天一共生产的modelNo
            List<JobDashboard> list = lineMap.get(line);
            if (list == null) {
                continue;
            }
            //这里需要把jobType值改为对应color
            int number = list.size();
            for(int j = 0; j < number; j++){
                JobDashboard t = list.get(j);
                String color = t.getJobType();//
                if("PROD".equals(color)){// #ffffff
                    t.setJobType("#669999");
                }else if("PM".equals(color)){//
                    t.setJobType("#0099ff");
                    t.setModelNo("PM");
                }else if("NON-SCHEDULE".equals(color)){//
                    t.setJobType("#808080");
                    t.setModelNo("NON-SCHEDULE");
                }else if("ENG".equals(color)){//
                    t.setJobType("#00cc44");
                }else if("SPL".equals(color)){//
                    t.setJobType("#ff8000");
                }
                double count = Double.parseDouble(t.getChangeDuration());
                if(count > 0){//如果有换线时间，背景为红色
                    if(null != t.getChangeStartTime() && null != t.getChangeEndTime()){
                        JobDashboard cd = new JobDashboard();
                        cd.setJobType("#ff0000");
                        cd.setModelNo("CHANGE");
                        cd.setProcessStartTime(t.getChangeStartTime());
                        cd.setProcessEndTime(t.getChangeEndTime());
                        cd.setForecastQty(0);
                        cd.setShiftDate(t.getShiftDate());
                        list.add(cd);
                    }
                }
            }
            lineObj.settJobDashboardList(list);//作为一个对象
            lineObj.setLine(line);
            chartList.add(lineObj);//添加到结果集
        }
        return chartList;
    }

}
