package com.lcm.domain.dto;

import java.time.LocalDate;
import java.util.List;

public class PlanMatchDto {

    private List<PlanMatchReportDto> reportList;

    private List<PlanMatchLineChartDto> listAll;

    private  List<String> shiftDates;

    private List<String> fabs;

    public List<PlanMatchLineChartDto> getListAll() {
        return listAll;
    }

    public void setListAll(List<PlanMatchLineChartDto> listAll) {
        this.listAll = listAll;
    }

    public List<String> getFabs() {
        return fabs;
    }

    public void setFabs(List<String> fabs) {
        this.fabs = fabs;
    }

    public List<String> getShiftDates() {
        return shiftDates;
    }

    public void setShiftDates(List<String> shiftDates) {
        this.shiftDates = shiftDates;
    }

    public List<PlanMatchReportDto> getReportList() {
        return reportList;
    }

    public void setReportList(List<PlanMatchReportDto> reportList) {
        this.reportList = reportList;
    }
}
