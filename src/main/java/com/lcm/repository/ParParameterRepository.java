package com.lcm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.lcm.domain.ParParameter;

public interface ParParameterRepository extends JpaRepository<ParParameter, String>, QuerydslPredicateExecutor<ParParameter> {

    //List<ParParameter> findBySiteOrderBySiteAscItemNameAscSeqAsc(String site);
	@Query("select p from ParParameter p where site=?1 and (fab in ?2 or fab='%') and (area in ?3 or area is null or area='%')")
	List<ParParameter> findBySiteAndFabInAndAreaInOrderBySiteAscItemNameAscSeqAsc(String site, List<String> fabList, List<String> areaList);

	@Query("select distinct inValue1 from ParParameter where site = :site and itemName = :itemName")
    String findBySiteAndItemName(@Param("site") String site,@Param("itemName") String itemName);
//
//    @Query("select inValue1 from ParParameter where site = :site and itemName = :itemName")
//    String findBySiteAndItemName1(@Param("site") String site,@Param("itemName") String itemName);

}
