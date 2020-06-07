package com.lcm.service;

import com.lcm.repository.PlanRepository;
import com.lcm.repository.SpecialRepository;
import com.lcm.service.dto.ReportDTO;
import com.lcm.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final PlanRepository planRepository;
    private final SpecialRepository specialRepository;

    public ReportService(PlanRepository planRepository, SpecialRepository specialRepository) {
        this.planRepository = planRepository;
        this.specialRepository = specialRepository;
    }

    public List<ReportDTO> getList(String site, String fab, String area, String shiftDate,String shiftDate2) {
        LocalDate date = LocalDate.parse(shiftDate, DateUtils.dateFormatter1);
        LocalDate date2 = LocalDate.parse(shiftDate2, DateUtils.dateFormatter1);
        List<Object> plans = planRepository.findBySiteAndShiftDateExcludeCapa(site, date,date2);
        List<Object> specials = specialRepository.findBySiteAndShiftDateExcludeCapa(site, date,date2);
        List<ReportDTO> reports = new ArrayList<>(plans.size() + specials.size());
        boolean compareFab = !StringUtils.isEmpty(fab);
        boolean compareArea = !StringUtils.isEmpty(area);
        plans.forEach(plan -> {
            Object[] p = (Object[]) plan;
            if (compareFab && !fab.equals(p[1])) {
                return;
            }
            if (compareArea && !area.equals(p[2])) {
                return;
            }

            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setSite((String) p[0]);
            reportDTO.setFab((String) p[1]);
            reportDTO.setArea((String) p[2]);
            reportDTO.setLine((String) p[3]);
            reportDTO.setModelNo((String) p[4]);
            reportDTO.setPartNo((String) p[5]);
            reports.add(reportDTO);
        });
        specials.forEach(special -> {
            Object[] s = (Object[]) special;
            if (compareFab && !fab.equals(s[1])) {
                return;
            }
            if (compareArea && !area.equals(s[2])) {
                return;
            }

            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setSite((String) s[0]);
            reportDTO.setFab((String) s[1]);
            reportDTO.setArea((String) s[2]);
            reportDTO.setLine((String) s[3]);
            reportDTO.setModelNo((String) s[4]);
            reportDTO.setPartNo((String) s[5]);
            reports.add(reportDTO);
        });
        List<ReportDTO> reportList = reports.stream().distinct().collect(Collectors.toList());
        sort(reportList);
        return reportList;
    }

    private void sort(List<ReportDTO> list) {
        list.sort((r1, r2) -> {
            int ret = r1.getSite().compareTo(r2.getSite());
            if (ret == 0) {
                ret = r1.getFab().compareTo(r2.getFab());
                if (ret == 0) {
                    ret = r1.getArea().compareTo(r2.getArea());
                    if (ret == 0) {
                        ret = r1.getLine().compareTo(r2.getLine());
                        if (ret == 0) {
                            ret = r1.getModelNo().compareTo(r2.getModelNo());
                            if (ret == 0) {
                                ret = r1.getPartNo().compareTo(r2.getPartNo());
                            }
                        }
                    }
                }
            }
            return ret;
        });
    }
}
