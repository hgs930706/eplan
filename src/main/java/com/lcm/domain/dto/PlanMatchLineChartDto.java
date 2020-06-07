package com.lcm.domain.dto;

import java.util.List;

public class PlanMatchLineChartDto {

    private List<PlanMatchReportDto> lineList;

    public List<PlanMatchReportDto> getLineList() {
        return lineList;
    }

    public void setLineList(List<PlanMatchReportDto> lineList) {
        this.lineList = lineList;
    }
}
