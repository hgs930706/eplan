package com.lcm.repository;

import com.lcm.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, String>, QuerydslPredicateExecutor<Plan> {
	@Query("select a" +
			" from Plan a" + 
			" where a.site = :site and a.fab in :fabList and a.area in :areaList and a.shiftDate >= :shift_start and a.shiftDate <= :shift_end" + 
			" and a.planQty >0" +
			" order by a.line, a.partNo, a.shiftDate, a.grade desc")
	List<Plan> find(@Param("site") String site,
					@Param("fabList") List<String> fabList,
					@Param("areaList") List<String> areaList,
					@Param("shift_start") LocalDate shift_start,
					@Param("shift_end") LocalDate shift_end);

	@Query("select a.site, a.fab, a.area, a.line, a.modelNo, a.partNo from Plan a where a.site = ?1 and a.shiftDate >= ?2 and a.shiftDate <= ?3" +
			" and (select count(1) from EqpCapa b where a.site = b.eqpCapaId.site and a.fab = b.fab and a.area = b.area" +
			" and a.line = b.eqpCapaId.line and a.modelNo = b.modelNo and a.partNo = b.eqpCapaId.partNo) = 0")
	List<Object> findBySiteAndShiftDateExcludeCapa(String site, LocalDate shiftDate, LocalDate shiftDate2);

	void deleteBySiteAndFabAndAreaAndShiftDate(String site, String fab, String area, LocalDate shiftDate);
}
