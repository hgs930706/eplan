package com.lcm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.lcm.domain.EquipmentPreventiveMaintenance;

public interface EquipmentPreventiveMaintenanceRepository extends JpaRepository<EquipmentPreventiveMaintenance, String>, QuerydslPredicateExecutor<EquipmentPreventiveMaintenance> {
	
	@Query("select a" +
			" from EquipmentPreventiveMaintenance a" + 
			" where a.site = :site and a.fab in :fabList and a.area in :areaList and a.shiftDate >= :shift_start and shiftDate <= :shift_end" + 
			" order by a.line, a.shiftDate")
	public List<EquipmentPreventiveMaintenance> find(@Param("site") String site,
								@Param("fabList") List<String> fabList,
								@Param("areaList") List<String> areaList,
								@Param("shift_start") LocalDate shift_start,
								@Param("shift_end") LocalDate shift_end);
}
