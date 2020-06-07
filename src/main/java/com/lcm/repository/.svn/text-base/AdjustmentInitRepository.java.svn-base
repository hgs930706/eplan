package com.lcm.repository;

import com.lcm.domain.AdjustmentInit;
import com.lcm.domain.AdjustmentInitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.List;

public interface AdjustmentInitRepository extends JpaRepository<AdjustmentInit, AdjustmentInitId>, QuerydslPredicateExecutor<AdjustmentInit> {

    List<AdjustmentInit> findByAdjustmentInitIdSiteAndAdjustmentInitIdFabAndAdjustmentInitIdAreaAndAdjustmentInitIdShiftDate(
            String site, String fab, String area, LocalDate shiftDate);

    List<AdjustmentInit> findAllByAdjustmentInitIdSiteAndAdjustmentInitIdShiftDateBetween(
            String site, LocalDate startDate, LocalDate endDate);

    List<AdjustmentInit> findAllByAdjustmentInitIdSiteAndAdjustmentInitIdFabAndAdjustmentInitIdShiftDateBetween(
            String site, String fab, LocalDate startDate, LocalDate endDate);

    @Query("select a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, sum(a.planQty) as qty, a.adjustmentInitId.fab, min(runSeq),a.adjustmentInitId.grade,a.remark from AdjustmentInit a where a.adjustmentInitId.site = ?1 " +
            "and a.adjustmentInitId.shiftDate between ?2 and ?3 and a.adjustmentInitId.jobType != 'PM' and a.adjustmentInitId.jobType != 'NON-SCHEDULE' and a.adjustmentInitId.jobType != 'CHANGE' group by a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, a.adjustmentInitId.fab,a.adjustmentInitId.grade,a.remark")
    List<Object> findBySiteAndShiftDate(String site, LocalDate fromDate, LocalDate toDate);//1

    @Query("select a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, '0' as qty, a.adjustmentInitId.fab, a.duration, min(runSeq),a.adjustmentInitId.grade,a.remark from AdjustmentInit a where a.adjustmentInitId.site = ?1 " +
            "and a.adjustmentInitId.shiftDate between ?2 and ?3 and a.adjustmentInitId.jobType = 'PM' group by a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, a.adjustmentInitId.fab, a.duration,a.adjustmentInitId.grade,a.remark")
    List<Object> findBySiteAndShiftDateForPm(String site, LocalDate fromDate, LocalDate toDate);//1

    @Query("select a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, '0' as qty, a.adjustmentInitId.fab, a.duration, min(runSeq),a.adjustmentInitId.grade,a.remark from AdjustmentInit a where a.adjustmentInitId.site = ?1 " +
            "and a.adjustmentInitId.shiftDate between ?2 and ?3 and a.adjustmentInitId.jobType = 'NON-SCHEDULE' group by a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, a.adjustmentInitId.fab, a.duration,a.adjustmentInitId.grade,a.remark")
    List<Object> findBySiteAndShiftDateForNonSchedule(String site, LocalDate fromDate, LocalDate toDate);//1

    @Query("select a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, '0' as qty, a.adjustmentInitId.fab, a.changeLevel, a.duration, a.adjustmentInitId.changeKey, min(runSeq),a.adjustmentInitId.grade,a.remark from AdjustmentInit a where a.adjustmentInitId.site = ?1 " +
            "and a.adjustmentInitId.shiftDate between ?2 and ?3 and a.adjustmentInitId.jobType = 'CHANGE' group by a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, a.adjustmentInitId.fab, a.changeLevel, a.duration, a.adjustmentInitId.changeKey,a.adjustmentInitId.grade,a.remark")
    List<Object> findBySiteAndShiftDateForChange(String site, LocalDate fromDate, LocalDate toDate);//1

    @Query("select a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, sum(a.planQty) as qty, a.adjustmentInitId.fab, min(runSeq),a.adjustmentInitId.grade,a.remark from AdjustmentInit a where a.adjustmentInitId.site = ?1 " +
            "and a.adjustmentInitId.fab = ?2 and a.adjustmentInitId.shiftDate between ?3 and ?4 and a.adjustmentInitId.jobType != 'PM' and a.adjustmentInitId.jobType != 'NON-SCHEDULE' and a.adjustmentInitId.jobType != 'CHANGE' group by a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, a.adjustmentInitId.fab,a.adjustmentInitId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDate(String site, String fab, LocalDate fromDate, LocalDate toDate);//2

    @Query("select a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, '0' as qty, a.adjustmentInitId.fab, a.duration, min(runSeq),a.adjustmentInitId.grade,a.remark from AdjustmentInit a where a.adjustmentInitId.site = ?1 " +
            "and a.adjustmentInitId.fab = ?2 and a.adjustmentInitId.shiftDate between ?3 and ?4 and a.adjustmentInitId.jobType = 'PM' group by a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, a.adjustmentInitId.fab, a.duration,a.adjustmentInitId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDateForPm(String site, String fab, LocalDate fromDate, LocalDate toDate);//2

    @Query("select a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, '0' as qty, a.adjustmentInitId.fab, a.duration, min(runSeq),a.adjustmentInitId.grade,a.remark from AdjustmentInit a where a.adjustmentInitId.site = ?1 " +
            "and a.adjustmentInitId.fab = ?2 and a.adjustmentInitId.shiftDate between ?3 and ?4 and a.adjustmentInitId.jobType = 'NON-SCHEDULE' group by a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, a.adjustmentInitId.fab, a.duration,a.adjustmentInitId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDateForNonSchedule(String site, String fab, LocalDate fromDate, LocalDate toDate);//2

    @Query("select a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, '0' as qty, a.adjustmentInitId.fab, a.changeLevel, a.duration, a.adjustmentInitId.changeKey, min(runSeq),a.adjustmentInitId.grade,a.remark from AdjustmentInit a where a.adjustmentInitId.site = ?1 " +
            "and a.adjustmentInitId.fab = ?2 and a.adjustmentInitId.shiftDate between ?3 and ?4 and a.adjustmentInitId.jobType = 'CHANGE' group by a.adjustmentInitId.line, a.adjustmentInitId.shiftDate, a.adjustmentInitId.shift, a.adjustmentInitId.jobType, a.adjustmentInitId.modelNo, a.partNo, a.adjustmentInitId.fab, a.changeLevel, a.duration, a.adjustmentInitId.changeKey,a.adjustmentInitId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDateForChange(String site, String fab, LocalDate fromDate, LocalDate toDate);//2



}
