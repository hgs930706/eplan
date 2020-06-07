package com.lcm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lcm.domain.Woxx;
import com.lcm.domain.WoxxId;

public interface WoxxRepository extends JpaRepository<Woxx, WoxxId> {

	@Query("select distinct W.woxxId.woId from Woxx W where W.woxxId.site = ?1 and W.woxxId.fab = ?2 and W.woxxId.area = ?3 and W.partNo = ?4 order by W.woxxId.woId")
	List<String> findDistinctWoIdBySiteAndFabAndAreaAndPartNo(String site, String fab, String area, String partNo);

	@Query("select w from Woxx w where w.woxxId.site=?1 and w.woxxId.fab in ?2 and w.woxxId.area in ?3 and w.status!='Closed' and material_category='main' order by site, part_no,start_date, wo_qty_total desc")
	List<Woxx> findBySite(String site, List<String> fabList, List<String> areaList);
}
