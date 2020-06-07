package com.lcm.domain.dto;

import com.lcm.domain.JobDashboard;

import java.util.List;

/**
 * 返回甘特图数据
 */
public class JobChartDto {

    private String fab;

    private String Line;

    private List<JobDashboard> tJobDashboardList;

    public List<JobDashboard> gettJobDashboardList() {
        return tJobDashboardList;
    }

    public void settJobDashboardList(List<JobDashboard> tJobDashboardList) {
        this.tJobDashboardList = tJobDashboardList;
    }

    public String getFab() {
        return fab;
    }

    public void setFab(String fab) {
        this.fab = fab;
    }

    public String getLine() {
        return Line;
    }

    public void setLine(String line) {
        Line = line;
    }
}
