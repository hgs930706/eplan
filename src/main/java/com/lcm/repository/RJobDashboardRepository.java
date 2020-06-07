package com.lcm.repository;

import com.lcm.domain.RJobDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RJobDashboardRepository extends JpaRepository<RJobDashboard, String>, QuerydslPredicateExecutor<RJobDashboard> {
    List<RJobDashboard> findBySiteAndFabAndShiftDateBetween(String site, String fab, LocalDate fromDate, LocalDate toDate);

	List<RJobDashboard> findBySiteAndFabAndAreaAndShiftDateBetween(String site, String fab, String area,LocalDate fromDate, LocalDate toDate);
    List<RJobDashboard> findBySiteAndShiftDateBetween(String site, LocalDate fromDate, LocalDate toDate);

	@Query("select r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, sum(r.forecastQty) as qty, r.fab, r.woId, min(r.processStartTime),r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.shiftDate between ?2 and ?3 and r.forecastQty > 0 and r.jobType != 'PM' and r.jobType != 'NON-SCHEDULE' group by r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, r.fab, r.woId,r.grade,r.remark")
	List<Object> findBySiteAndShiftDate(String site, LocalDate fromDate, LocalDate toDate);//1
	@Query("select r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, r.forecastQty, r.fab, r.woId, r.processStartTime, r.processEndTime,r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.shiftDate between ?2 and ?3 and r.jobType = 'PM'")
	List<Object> findBySiteAndShiftDateForPm(String site, LocalDate fromDate, LocalDate toDate);//1
	@Query("select r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, r.forecastQty, r.fab, r.woId, r.processStartTime, r.processEndTime,r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.shiftDate between ?2 and ?3 and r.jobType = 'NON-SCHEDULE'")
	List<Object> findBySiteAndShiftDateForNonSchedule(String site, LocalDate fromDate, LocalDate toDate);//1
	@Query("select r.line, r.changeShiftDate, r.changeShift, 'CHANGE' as jobType, r.modelNo, r.partNo, 0 as qty, r.fab, r.woId, r.changeStartTime, r.changeLevel, r.changeDuration, r.changeKey,r.grade,r.remark, r.changeStartTime from RJobDashboard r where r.site = ?1 " +
			"and r.shiftDate between ?2 and ?3 and r.changeDuration <> 0")
	List<Object> findBySiteAndShiftDateForChange(String site, LocalDate fromDate, LocalDate toDate);//1


	@Query("select r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, sum(r.forecastQty) as qty, r.fab, r.woId, min(r.processStartTime),r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.fab = ?2 and r.shiftDate between ?3 and ?4 and r.forecastQty > 0 and r.jobType != 'PM'  and r.jobType != 'NON-SCHEDULE' group by r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, r.fab, r.woId,r.grade,r.remark")
	List<Object> findBySiteAndFabAndShiftDate(String site, String fab, LocalDate fromDate, LocalDate toDate);//11
	@Query("select r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, r.forecastQty, r.fab, r.woId, r.processStartTime, r.processEndTime,r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.fab = ?2 and r.shiftDate between ?3 and ?4 and r.jobType = 'PM'")
	List<Object> findBySiteAndFabAndShiftDateForPm(String site, String fab, LocalDate fromDate, LocalDate toDate);//11
	@Query("select r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, r.forecastQty, r.fab, r.woId, r.processStartTime, r.processEndTime,r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.fab = ?2 and r.shiftDate between ?3 and ?4 and r.jobType = 'NON-SCHEDULE'")
	List<Object> findBySiteAndFabAndShiftDateForNonSchedule(String site, String fab, LocalDate fromDate, LocalDate toDate);//11
	@Query("select r.line, r.changeShiftDate, r.changeShift, 'CHANGE' as jobType, r.modelNo, r.partNo, 0 as qty, r.fab, r.woId, r.changeStartTime, r.changeLevel, r.changeDuration, r.changeKey,r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.fab = ?2 and r.shiftDate between ?3 and ?4 and r.changeDuration <> 0 ")
	List<Object> findBySiteAndFabAndShiftDateForChange(String site, String fab, LocalDate fromDate, LocalDate toDate);//11

    @Modifying
	@Transactional
    public void deleteBySiteInAndFabInAndAreaInAndShiftDateIn(List<String> site, List<String> fabList, List<String> areaList, List<LocalDate> shiftDate);

	List<RJobDashboard> findBySiteAndFabInAndAreaInAndProcessEndTimeBetweenOrderByLineAscProcessEndTimeDescLmTimeDesc(
			String site, List<String> fabList, List<String> areaList, LocalDateTime start, LocalDateTime end);
	
	List<RJobDashboard> findBySiteAndFabInAndShiftDateBetweenOrderByLineAscProcessEndTimeAscLmTimeAsc(
			String site, List<String> fabList, LocalDate start, LocalDate end);






	@Modifying
	@Query("update RJobDashboard r set r.remark = :remark where r.site = :site and r.fab = :fab " +
			"and r.line = :line and r.shiftDate = :shiftDate and r.shift = :shift and r.jobType = :jobType " +
			"and r.modelNo is null and r.woId = :woId and r.partNo is null and r.grade = :grade")
	int updateByKeys1(@Param("site") String site,@Param("fab") String fab,@Param("line") String line,@Param("shiftDate") LocalDate shiftDate,
					 @Param("shift") String shift,@Param("jobType") String jobType,@Param("woId") String woId,
					@Param("grade") String grade,@Param("remark") String remark);

	@Modifying
	@Query("update RJobDashboard r set r.remark = :remark where r.site = :site and r.fab = :fab " +
			"and r.line = :line and r.shiftDate = :shiftDate and r.shift = :shift and r.jobType = :jobType " +
			"and r.modelNo is null and r.woId = :woId and r.partNo is null and r.grade is null")
	int updateByKeys11(@Param("site") String site,@Param("fab") String fab,@Param("line") String line,@Param("shiftDate") LocalDate shiftDate,
					  @Param("shift") String shift,@Param("jobType") String jobType,@Param("woId") String woId,@Param("remark") String remark);

	@Modifying
	@Query("update RJobDashboard r set r.remark = :remark where r.site = :site and r.fab = :fab " +
			"and r.line = :line and r.shiftDate = :shiftDate and r.shift = :shift and r.jobType = :jobType " +
			"and r.modelNo = :modelNo and r.woId = :woId and r.partNo = :partNo and r.grade = :grade")
	int updateByKeys2(@Param("site") String site,@Param("fab") String fab,@Param("line") String line,@Param("shiftDate") LocalDate shiftDate,
					 @Param("shift") String shift,@Param("jobType") String jobType,@Param("modelNo") String modelNo,@Param("woId") String woId,
					 @Param("partNo") String partNo,@Param("grade") String grade,@Param("remark") String remark);

	@Modifying
	@Query("update RJobDashboard r set r.remark = :remark where r.site = :site and r.fab = :fab " +
			"and r.line = :line and r.shiftDate = :shiftDate and r.shift = :shift and r.jobType = :jobType " +
			"and r.modelNo = :modelNo and r.woId = :woId and r.partNo = :partNo and r.grade is null")
	int updateByKeys22(@Param("site") String site,@Param("fab") String fab,@Param("line") String line,@Param("shiftDate") LocalDate shiftDate,
					   @Param("shift") String shift,@Param("jobType") String jobType,@Param("modelNo") String modelNo,@Param("woId") String woId,
					   @Param("partNo") String partNo,@Param("remark") String remark);



	@Query("select r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, sum(r.forecastQty) as qty, r.fab, r.woId, min(r.processStartTime),r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.fab = ?2 and r.area = ?3 and r.shiftDate between ?4 and ?5 and r.forecastQty > 0 and r.jobType != 'PM'  and r.jobType != 'NON-SCHEDULE' group by r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, r.fab, r.woId,r.grade,r.remark")
	List<Object> findBySiteAndFabAndShiftDate(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//11
	@Query("select r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, r.forecastQty, r.fab, r.woId, r.processStartTime, r.processEndTime,r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.fab = ?2 and r.area = ?3 and r.shiftDate between ?4 and ?5 and r.jobType = 'PM'")
	List<Object> findBySiteAndFabAndShiftDateForPm(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//11
	@Query("select r.line, r.shiftDate, r.shift, r.jobType, r.modelNo, r.partNo, r.forecastQty, r.fab, r.woId, r.processStartTime, r.processEndTime,r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.fab = ?2 and r.area = ?3 and r.shiftDate between ?4 and ?5 and r.jobType = 'NON-SCHEDULE'")
	List<Object> findBySiteAndFabAndShiftDateForNonSchedule(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//11
	@Query("select r.line, r.changeShiftDate, r.changeShift, 'CHANGE' as jobType, r.modelNo, r.partNo, 0 as qty, r.fab, r.woId, r.changeStartTime, r.changeLevel, r.changeDuration, r.changeKey,r.grade,r.remark from RJobDashboard r where r.site = ?1 " +
			"and r.fab = ?2 and r.area = ?3 and r.shiftDate between ?4 and ?5 and r.changeDuration <> 0 ")
	List<Object> findBySiteAndFabAndShiftDateForChange(String site, String fab,String area, LocalDate fromDate, LocalDate toDate);//11
}
