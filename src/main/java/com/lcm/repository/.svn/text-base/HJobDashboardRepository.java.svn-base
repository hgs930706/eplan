package com.lcm.repository;

import com.lcm.domain.HJobDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.List;

public interface HJobDashboardRepository extends JpaRepository<HJobDashboard, String>, QuerydslPredicateExecutor<HJobDashboard> {
    List<HJobDashboard> findBySiteAndFabAndShiftDateBetween(String site, String fab, LocalDate fromDate, LocalDate toDate);
    List<HJobDashboard> findBySiteAndFabAndAreaAndShiftDateBetween(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);

    List<HJobDashboard> findBySiteAndShiftDateBetween(String site, LocalDate fromDate, LocalDate toDate);



    @Query("select h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, sum(h.forecastQty) as qty, h.fab, h.woId, min(h.processStartTime),h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.shiftDate between ?2 and ?3 and h.forecastQty > 0 and h.jobType != 'PM' and h.jobType != 'NON-SCHEDULE' group by h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, h.fab, h.woId,h.grade,h.remark")
    List<Object> findBySiteAndShiftDate(String site, LocalDate fromDate, LocalDate toDate);//1
    @Query("select h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, h.forecastQty, h.fab, h.woId, h.processStartTime, h.processEndTime,h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.shiftDate between ?2 and ?3 and h.jobType = 'PM'")
    List<Object> findBySiteAndShiftDateForPm(String site, LocalDate fromDate, LocalDate toDate);//1
    @Query("select h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, h.forecastQty, h.fab, h.woId, h.processStartTime, h.processEndTime,h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.shiftDate between ?2 and ?3 and h.jobType = 'NON-SCHEDULE'")
    List<Object> findBySiteAndShiftDateForNonSchedule(String site, LocalDate fromDate, LocalDate toDate);//1
    @Query("select h.line, h.changeShiftDate, h.changeShift, 'CHANGE' as jobType, h.modelNo, h.partNo, 0 as qty, h.fab, h.woId, h.changeStartTime, h.changeLevel, h.changeDuration, h.changeKey,h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.shiftDate between ?2 and ?3 and h.changeDuration <> 0")
    List<Object> findBySiteAndShiftDateForChange(String site, LocalDate fromDate, LocalDate toDate);//1


    @Query("select h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, sum(h.forecastQty) as qty, h.fab, h.woId, min(h.processStartTime),h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.fab = ?2 and h.shiftDate between ?3 and ?4 and h.forecastQty > 0 and h.jobType != 'PM' and h.jobType != 'NON-SCHEDULE' group by h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, h.fab, h.woId,h.grade,h.remark")
    List<Object> findBySiteAndFabAndShiftDate(String site, String fab, LocalDate fromDate, LocalDate toDate);//22
    @Query("select h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, h.forecastQty, h.fab, h.woId, h.processStartTime, h.processEndTime,h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.fab = ?2 and h.shiftDate between ?3 and ?4 and h.jobType = 'PM'")
    List<Object> findBySiteAndFabAndShiftDateForPm(String site, String fab, LocalDate fromDate, LocalDate toDate);//22
    @Query("select h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, h.forecastQty, h.fab, h.woId, h.processStartTime, h.processEndTime,h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.fab = ?2 and h.shiftDate between ?3 and ?4 and h.jobType = 'NON-SCHEDULE'")
    List<Object> findBySiteAndFabAndShiftDateForNonSchedule(String site, String fab, LocalDate fromDate, LocalDate toDate);//22
    @Query("select h.line, h.changeShiftDate, h.changeShift, 'CHANGE' as jobType, h.modelNo, h.partNo, 0 as qty, h.fab, h.woId, h.changeStartTime, h.changeLevel, h.changeDuration, h.changeKey,h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.fab = ?2 and h.shiftDate between ?3 and ?4 and h.changeDuration <> 0 ")
    List<Object> findBySiteAndFabAndShiftDateForChange(String site, String fab, LocalDate fromDate, LocalDate toDate);//22

    @Query("select h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, sum(h.forecastQty) as qty, h.fab, h.woId, min(h.processStartTime),h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.fab = ?2 and h.area = ?3 and h.shiftDate between ?4 and ?5 and h.forecastQty > 0 and h.jobType != 'PM' and h.jobType != 'NON-SCHEDULE' group by h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, h.fab, h.woId,h.grade,h.remark")
    List<Object> findBySiteAndFabAndShiftDate(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//22
    @Query("select h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, h.forecastQty, h.fab, h.woId, h.processStartTime, h.processEndTime,h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.fab = ?2 and h.area = ?3 and h.shiftDate between ?4 and ?5 and h.jobType = 'PM'")
    List<Object> findBySiteAndFabAndShiftDateForPm(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//22
    @Query("select h.line, h.shiftDate, h.shift, h.jobType, h.modelNo, h.partNo, h.forecastQty, h.fab, h.woId, h.processStartTime, h.processEndTime,h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.fab = ?2 and h.area = ?3 and h.shiftDate between ?4 and ?5 and h.jobType = 'NON-SCHEDULE'")
    List<Object> findBySiteAndFabAndShiftDateForNonSchedule(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//22
    @Query("select h.line, h.changeShiftDate, h.changeShift, 'CHANGE' as jobType, h.modelNo, h.partNo, 0 as qty, h.fab, h.woId, h.changeStartTime, h.changeLevel, h.changeDuration, h.changeKey,h.grade,h.remark from HJobDashboard h where h.site = ?1 " +
            "and h.fab = ?2 and h.area = ?3 and h.shiftDate between ?4 and ?5 and h.changeDuration <> 0 ")
    List<Object> findBySiteAndFabAndShiftDateForChange(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//22


}
