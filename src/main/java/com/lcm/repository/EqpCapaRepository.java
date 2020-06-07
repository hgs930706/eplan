package com.lcm.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.lcm.domain.EqpCapa;
import com.lcm.domain.EqpCapaId;

public interface EqpCapaRepository extends JpaRepository<EqpCapa, EqpCapaId>, QuerydslPredicateExecutor<EqpCapa> {
	
	List<EqpCapa> findByEqpCapaIdSiteAndFabInAndAreaIn(String site, List<String> fabList, List<String> areaList);

	EqpCapa findByEqpCapaIdSiteAndFabAndEqpCapaIdLineAndModelNoAndEqpCapaIdPartNo(String site,String fab,String line,String modelNo,String partNo);

	List<EqpCapa> findByEqpCapaIdSiteAndFabInAndEqpCapaIdLineIn(String site, Set<String> fabList, Set<String> lines);
}
