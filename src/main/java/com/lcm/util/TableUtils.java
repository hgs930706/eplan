package com.lcm.util;

import com.lcm.domain.JobDashboard;
import com.lcm.domain.dto.ColumnDto;
import com.lcm.domain.dto.ColumnDto1;
import com.lcm.service.dto.TJobStatisticsDTO;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

public class TableUtils {
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final String FLAG = "@";
    public static final String DSHIFT = "D";
    public static final String NSHIFT = "N";
    public static final String ZERONE = "91_part_level";
    public static final String SEVEN = "97_part_level";
    public static final String DSTART = "shift_d_start";
    public static final String ESTART = "shift_e_start";
    public static String[] SHIFTS = {"D","N"};

    //按照fab、line排序分组
    public static Map<String , TJobStatisticsDTO> handleTJobList(List<JobDashboard> tJobList) {
        Map<String , TJobStatisticsDTO> fabLineMap = new TreeMap<>();
        tJobList.forEach(t -> {
            String fabLine = t.getFab() +FLAG + t.getLine();
            String shift = t.getShiftDate() +FLAG + t.getShift();
            if (fabLineMap.get(fabLine) == null) {
                TJobStatisticsDTO statisticsDTO = new TJobStatisticsDTO();
                addToStatisticsDTO(t, statisticsDTO, shift);
                fabLineMap.put(fabLine, statisticsDTO);
            } else {
                TJobStatisticsDTO statisticsDTO = fabLineMap.get(fabLine);
                addToStatisticsDTO(t, statisticsDTO, shift);
            }
        });
        return fabLineMap;
    }

    public static void addToStatisticsDTO(JobDashboard t, TJobStatisticsDTO statisticsDTO, String shift) {
        if (statisticsDTO.getData().get(shift) == null) {
            List<JobDashboard> list = new ArrayList<>();
            list.add(t);
            statisticsDTO.getData().put(shift, list);
            if (statisticsDTO.getSum() < 1) {
                statisticsDTO.setSum(1);
            }
        } else {
            List<JobDashboard> list = statisticsDTO.getData().get(shift);
            list.add(t);
            if (statisticsDTO.getSum() < list.size()) {
                statisticsDTO.setSum(list.size());
            }
        }
    }

    //计算特定shiftDate、shift、partNo的数量
    public static Map<String, Integer> getShiftDateShiftPartNoCountMap(List<JobDashboard> tJobList) {
        Map<String, Integer> countMap = new HashMap<>();
        tJobList.forEach(t -> {
            if (t.getPartNo() != null && t.getPartNo().length() >= 2) {//只统计partNo可以截取2位的情况
                String shiftDateShift = t.getShiftDate() + "_" + t.getShift() + "_" + t.getPartNo().split("\\.")[0];
                Integer count = t.getForecastQty();
                if (countMap.get(shiftDateShift) != null) {
                    count += countMap.get(shiftDateShift);
                }
                countMap.put(shiftDateShift, count);
            }
        });
        return countMap;
    }

    //合并显示partNo和grade
    public static String handlePartNo(String partNo,String grade){
        if(StringUtils.isEmpty(grade)){
            return partNo == null ? "-" : partNo;
        }else{
            return (partNo == null ? "-" : partNo) + ( grade.equals("-") ? "" : (":"+ grade));
        }
    }

    public static String getGrade(String partNo){
        int index = partNo.indexOf(":");
        String grade = index > 0 ? partNo.substring(index+1,partNo.length()) : "-";
        return grade;
    }

    public static String getPartNo(String partNo){
        int index = partNo.indexOf(":");
        String pn = index > 0 ? partNo.substring(0,index) : partNo;
        return pn;
    }


    public static ColumnDto1 get91and97and90(ColumnDto1 columnDto, Map<String, Integer> shiftDateShiftPartNoCountMap, LocalDate shiftDate){
        Integer DforecastQtySum91 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + DSHIFT + "_91") == null ? 0 : shiftDateShiftPartNoCountMap.get(shiftDate + "_" + DSHIFT + "_91");
        Integer DforecastQtySum97 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + DSHIFT + "_97");
        Integer NforecastQtySum91 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + NSHIFT + "_91") == null ? 0 : shiftDateShiftPartNoCountMap.get(shiftDate + "_" + NSHIFT + "_91");
        Integer NforecastQtySum97 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + NSHIFT + "_97");
        Integer DforecastQtySum90 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + DSHIFT + "_90") == null ? 0 : shiftDateShiftPartNoCountMap.get(shiftDate +  "_" + DSHIFT + "_90");
        Integer NforecastQtySum90 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + NSHIFT + "_90") == null ? 0 : shiftDateShiftPartNoCountMap.get(shiftDate + "_" +  NSHIFT + "_90");
        columnDto.setDforecastQtySum91(DforecastQtySum90 + DforecastQtySum91 +"");
        columnDto.setDforecastQtySum97(DforecastQtySum97 == null ? "0" : DforecastQtySum97.toString());
        columnDto.setNforecastQtySum91(NforecastQtySum90 + NforecastQtySum91 +"");
        columnDto.setNforecastQtySum97(NforecastQtySum97 == null ? "0" : NforecastQtySum97.toString());
        return columnDto;
    };

    public static ColumnDto get91and97and90(ColumnDto columnDto, Map<String, Integer> shiftDateShiftPartNoCountMap, LocalDate shiftDate){
        Integer DforecastQtySum91 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + DSHIFT + "_91") == null ? 0 : shiftDateShiftPartNoCountMap.get(shiftDate + "_" + DSHIFT + "_91");
        Integer DforecastQtySum97 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + DSHIFT + "_97");
        Integer NforecastQtySum91 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + NSHIFT + "_91") == null ? 0 : shiftDateShiftPartNoCountMap.get(shiftDate + "_" + NSHIFT + "_91");
        Integer NforecastQtySum97 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + NSHIFT + "_97");
        Integer DforecastQtySum90 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + DSHIFT + "_90") == null ? 0 : shiftDateShiftPartNoCountMap.get(shiftDate +  "_" + DSHIFT + "_90");
        Integer NforecastQtySum90 = shiftDateShiftPartNoCountMap.get(shiftDate + "_" + NSHIFT + "_90") == null ? 0 : shiftDateShiftPartNoCountMap.get(shiftDate + "_" +  NSHIFT + "_90");
        columnDto.setDforecastQtySum91(DforecastQtySum90 + DforecastQtySum91 +"");
        columnDto.setDforecastQtySum97(DforecastQtySum97 == null ? "0" : DforecastQtySum97.toString());
        columnDto.setNforecastQtySum91(NforecastQtySum90 + NforecastQtySum91 +"");
        columnDto.setNforecastQtySum97(NforecastQtySum97 == null ? "0" : NforecastQtySum97.toString());
        return columnDto;
    };

}
