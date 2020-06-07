package com.lcm.repository;

import com.lcm.domain.Adjustment;
import com.lcm.domain.AdjustmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AdjustmentRepository extends JpaRepository<Adjustment, AdjustmentId> , QuerydslPredicateExecutor<Adjustment> {
    List<Adjustment> findAllByAdjustmentIdSiteAndAdjustmentIdShiftDateBetween(
            String site, LocalDate startDate, LocalDate endDate);

    List<Adjustment> findAllByAdjustmentIdSiteAndAdjustmentIdFabAndAdjustmentIdShiftDateBetween(
            String site, String fab, LocalDate startDate, LocalDate endDate);



    List<Adjustment> findAllByAdjustmentIdSiteAndAdjustmentIdFabAndAdjustmentIdAreaAndAdjustmentIdShiftDateBetween(
            String site, String fab,String area, LocalDate startDate, LocalDate endDate);

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, sum(a.planQty) as qty, a.adjustmentId.fab,a.adjustmentId.area, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.fab = ?2 and a.adjustmentId.shiftDate between ?3 and ?4 and a.adjustmentId.jobType != 'PM' and a.adjustmentId.jobType != 'NON-SCHEDULE' and a.adjustmentId.jobType != 'CHANGE' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDate(String site, String fab, LocalDate fromDate, LocalDate toDate);//fab

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, '0' as qty, a.adjustmentId.fab,a.adjustmentId.area, a.duration, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.fab = ?2 and a.adjustmentId.shiftDate between ?3 and ?4 and a.adjustmentId.jobType = 'PM' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area, a.duration,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDateForPm(String site, String fab, LocalDate fromDate, LocalDate toDate);//fab

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, '0' as qty, a.adjustmentId.fab,a.adjustmentId.area , a.duration, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.fab = ?2 and a.adjustmentId.shiftDate between ?3 and ?4 and a.adjustmentId.jobType = 'NON-SCHEDULE' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area, a.duration,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDateForNonSchedule(String site, String fab, LocalDate fromDate, LocalDate toDate);//fab

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, '0' as qty, a.adjustmentId.fab,a.adjustmentId.area, a.changeLevel, a.duration, a.adjustmentId.changeKey, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.fab = ?2 and a.adjustmentId.shiftDate between ?3 and ?4 and a.adjustmentId.jobType = 'CHANGE' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area, a.changeLevel, a.duration, a.adjustmentId.changeKey,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDateForChange(String site, String fab, LocalDate fromDate, LocalDate toDate);//fab

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, sum(a.planQty) as qty, a.adjustmentId.fab,a.adjustmentId.area, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.fab = ?2 and a.adjustmentId.area = ?3 and a.adjustmentId.shiftDate between ?4 and ?5 and a.adjustmentId.jobType != 'PM' and a.adjustmentId.jobType != 'NON-SCHEDULE' and a.adjustmentId.jobType != 'CHANGE' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDate(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//area

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, '0' as qty, a.adjustmentId.fab,a.adjustmentId.area, a.duration, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.fab = ?2 and a.adjustmentId.area = ?3 and a.adjustmentId.shiftDate between ?4 and ?5 and a.adjustmentId.jobType = 'PM' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area, a.duration,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDateForPm(String site, String fab, String area,LocalDate fromDate, LocalDate toDate);//area

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, '0' as qty, a.adjustmentId.fab,a.adjustmentId.area, a.duration, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.fab = ?2 and a.adjustmentId.area = ?3 and a.adjustmentId.shiftDate between ?4 and ?5 and a.adjustmentId.jobType = 'NON-SCHEDULE' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area, a.duration,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDateForNonSchedule(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//area

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, '0' as qty, a.adjustmentId.fab,a.adjustmentId.area, a.changeLevel, a.duration, a.adjustmentId.changeKey, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.fab = ?2 and a.adjustmentId.area = ?3 and a.adjustmentId.shiftDate between ?4 and ?5 and a.adjustmentId.jobType = 'CHANGE' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area, a.changeLevel, a.duration, a.adjustmentId.changeKey,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndFabAndShiftDateForChange(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//area

















    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, sum(a.planQty) as qty, a.adjustmentId.fab,a.adjustmentId.area, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.shiftDate between ?2 and ?3 and a.adjustmentId.jobType != 'PM' and a.adjustmentId.jobType != 'NON-SCHEDULE' and a.adjustmentId.jobType != 'CHANGE' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndShiftDate(String site, LocalDate fromDate, LocalDate toDate);//1

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, '0' as qty, a.adjustmentId.fab,a.adjustmentId.area, a.duration, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.shiftDate between ?2 and ?3 and a.adjustmentId.jobType = 'PM' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area, a.duration,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndShiftDateForPm(String site, LocalDate fromDate, LocalDate toDate);//1

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, '0' as qty, a.adjustmentId.fab,a.adjustmentId.area, a.duration, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.shiftDate between ?2 and ?3 and a.adjustmentId.jobType = 'NON-SCHEDULE' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area, a.duration,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndShiftDateForNonSchedule(String site, LocalDate fromDate, LocalDate toDate);//1

    @Query("select a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, '0' as qty, a.adjustmentId.fab,a.adjustmentId.area, a.changeLevel, a.duration, a.adjustmentId.changeKey, min(runSeq),a.adjustmentId.grade,a.remark from Adjustment a where a.adjustmentId.site = ?1 " +
            "and a.adjustmentId.shiftDate between ?2 and ?3 and a.adjustmentId.jobType = 'CHANGE' group by a.adjustmentId.line, a.adjustmentId.shiftDate, a.adjustmentId.shift, a.adjustmentId.jobType, a.adjustmentId.modelNo, a.partNo, a.adjustmentId.fab,a.adjustmentId.area, a.changeLevel, a.duration, a.adjustmentId.changeKey,a.adjustmentId.grade,a.remark")
    List<Object> findBySiteAndShiftDateForChange(String site, LocalDate fromDate, LocalDate toDate);//1

    @Modifying
    @Query("delete from Adjustment a where a.adjustmentId.shiftDate in ?1 ")
    void deleteByShiftDates(List<LocalDate> dates);

    @Modifying
    @Query("delete from Adjustment a where a.adjustmentId.shiftDate in ?1 and a.adjustmentId.site = ?2")
    void deleteByShiftDatesAndSite(List<LocalDate> dates, String site);

    @Modifying
    @Query("delete from Adjustment a where a.adjustmentId.shiftDate in ?1 and a.adjustmentId.site = ?2 and a.adjustmentId.fab in ?3 and a.adjustmentId.area in ?4")
    void deleteByShiftDatesAndSiteAndFabs(List<LocalDate> dates, String site, Set<String> fabs,Set<String> areas);
    

    @Query("select a from Adjustment a where a.adjustmentId.site = ?1"
    		+ " and a.adjustmentId.fab in ?2 and a.adjustmentId.shiftDate between ?3 and ?4"
    		+ " order by a.adjustmentId.line ASC, a.adjustmentId.shiftDate ASC, a.adjustmentId.shift ASC, a.runSeq DESC")
    List<Adjustment> findBySiteAndFabAndShiftDateForAllType(String site, List<String> fabList, LocalDate start, LocalDate end);

}
