package com.lcm.repository;

import com.lcm.domain.SJobDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface SJobDashboardRepository extends JpaRepository<SJobDashboard,String>, QuerydslPredicateExecutor<SJobDashboard> {

    List<SJobDashboard> findBySiteAndTrxId(String site, String trxId);

	@Query("select s.line, s.shiftDate, s.shift, s.jobType, s.modelNo, s.partNo, sum(s.forecastQty) as qty, s.fab from SJobDashboard s where s.site = ?1 " +
			"and s.trxId = ?2 and (s.forecastQty > 0 or s.jobType in ('PM', 'NON-SCHEDULE')) group by s.line, s.shiftDate, s.shift, s.jobType, s.modelNo, s.partNo, s.fab")
	List<Object> findBySiteAndTrxId1(String site, String trxId);

	@Query("select s.line, s.shiftDate, s.shift, 'CHANGE' as jobType, s.modelNo, s.partNo, 0 as qty, s.fab from SJobDashboard s where s.site = ?1 " +
			"and s.trxId = ?2 and s.changeDuration <> 0 group by s.line, s.shiftDate, s.shift, s.jobType, s.modelNo, s.partNo, s.fab")
	List<Object> findBySiteAndTrxId2(String site, String trxId);
    
    @Modifying
	@Transactional
	public void deleteBySiteAndFabInAndAreaInAndTrxId(String site, List<String> fabList, List<String> areaList, String trx_id);
	
	SJobDashboard findTop1BySiteAndFabInAndAreaInAndPlanStartDateAndPlanEndDateOrderByTrxIdDesc(String site, String[] fab, String[] area, LocalDate startDate, LocalDate endDate);


}
