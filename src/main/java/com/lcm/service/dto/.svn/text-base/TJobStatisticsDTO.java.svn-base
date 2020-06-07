package com.lcm.service.dto;

import com.lcm.domain.JobDashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class TJobStatisticsDTO {

    private int sum;
    private Map<String, List<JobDashboard>> data = new HashMap<>();

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Map<String, List<JobDashboard>> getData() {
        return data;
    }

    public void setData(Map<String, List<JobDashboard>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TJobStatisticsDTO.class.getSimpleName() + "[", "]")
                .add("sum=" + sum)
                .add("data=" + data)
                .toString();
    }
}
