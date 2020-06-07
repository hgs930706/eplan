package com.lcm.service;


import com.lcm.domain.AdjustmentInitId;
import com.lcm.domain.dto.AdjustmentDto;
import com.lcm.domain.dto.JobAdjustChartDto;
import com.lcm.domain.dto.JobDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdjustmentInitServcie{

    List<AdjustmentDto> getAdjustChartData(String site, String fab, String startTime);

    JobDto getData(String site, String fab, String startTime, String endTime, boolean showWoId);

    List<JobAdjustChartDto> getAdjustChart(List<AdjustmentDto> listChart);


}
