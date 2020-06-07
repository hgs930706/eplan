package com.lcm.repository;

import com.lcm.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface LineRepository extends JpaRepository<Line, String>, QuerydslPredicateExecutor<Line> {

    List<Line> findBySite(String site);

    @Query("select distinct L.site from Line L order by L.site")
    List<String> findDistinctSite();

    @Query("select distinct L.fab from Line L")
    List<String> findDistinctFab();

    @Query("select distinct L.fab from Line L where L.site = ?1 order by L.fab")
    List<String> findDistinctFabBySite(String site);

    @Query("select distinct L.area from Line L")
    List<String> findDistinctArea();

    @Query("select distinct L.area from Line L where L.site = ?1 and L.fab = ?2 order by L.area")
    List<String> findDistinctAreaBySiteAndFab(String site, String fab);

    @Query("select distinct L.area from Line L where L.site = ?1 order by L.area")
    List<String> findDistinctAreaBySite (String site);

    @Query("select distinct L.line from Line L where L.site = ?1 and L.fab = ?2 and L.area = ?3 order by L.line")
    List<String> findDistinctLineBySiteAndFabAndArea(String site, String fab, String area);
	
	List<Line> findBySiteAndFabInAndAreaInAndActiveFlag(String site, List<String> fabList, List<String> areaList, String flag);

	@Query("select distinct L.line from Line L where L.site = ?1 and L.fab = ?2 and activeFlag = 'Y' order by L.line")
    List<String> findLineList(String site,String fab);

	@Query("select distinct L.line from Line L where L.site = ?1 and L.fab = ?2 and L.area = ?3 and activeFlag = 'Y' order by L.line")
    List<String> findLineList(String site,String fab,String area);


	String findBySiteAndFabAndLine(String site,String fab,String line);

	@Query("select distinct L.area from Line L where L.site = ?1 and L.fab in ?2 order by L.area")
    List<String> findAreas(String site,String [] fab);
}
