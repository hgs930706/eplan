package com.lcm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lcm.domain.CFacConstraintCapa;

public interface CFacConstraintCapaRepository extends JpaRepository<CFacConstraintCapa, String> {
	
	@Query("select c from CFacConstraintCapa c "
			+ "where c.site=:site and c.fab in :fabList and c.shift_date=:shift_date")
	public List<CFacConstraintCapa> find(@Param("site") String site,
										@Param("fabList") List<String> fabList,
										@Param("shift_date") LocalDate shift_date);
}
