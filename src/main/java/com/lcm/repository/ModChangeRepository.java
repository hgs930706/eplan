package com.lcm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.lcm.domain.ModChange;

public interface ModChangeRepository extends JpaRepository<ModChange, String>, QuerydslPredicateExecutor<ModChange> {
    void deleteBySite(String site);
    List<ModChange> findBySiteOrderByChangeKeyAsc(String site);
    
    //update by avonchung 20190508 取消fab條件
//    List<ModChange> findBySiteAndFabInOrderByPriority(String site, List<String> fabList);
    @Query("select m from ModChange m where m.site=?1 and (m.area in ?2 or m.area='%') order by m.priority asc")
    List<ModChange> findBySiteAndAreaInOrderByPriority(String site, List<String> areaList);
    
    //add by avonchung 20190429 
    //update by avonchung 20190508 取消fab條件
    @Query("select m.site, m.fab, m.line ," + 
    		"m.toModelSite as fromModelSite , m.toModelType as fromModelType , m.toPanelSize as fromPanelSize , m.toPartLevel as fromPartLevel, " + 
    		"m.toBarType as fromBarType , m.toPanelSizeGroup as fromPanelSizeGroup, m.toIsBuildPcb as fromIsBuildPcb , " + 
    		"m.toIsDemura as fromIsDemura, m.toTuffyType as fromTuffyType , m.toIsOverSixMonth as fromIsOverSixMonth, " + 
    		"m.toPartNo as fromPartNo, m.toChangeGroup as fromChangeGroup , m.fromModelSite as toModelSite , m.fromModelType as toModelType, " + 
    		"m.fromPanelSize as toPanelSize , m.fromPartLevel as toPartLevel, m.fromBarType as toBarType , fromPanelSizeGroup as toPanelSizeGroup, " + 
    		"m.fromIsBuildPcb as toIsBuildPcb , m.fromIsDemura as toIsDemura, m.fromTuffyType as toTuffyType , m.fromIsOverSixMonth as toIsOverSixMonth, " + 
    		"m.fromPartNo as toPartNo, m.fromChangeGroup as toChangeGroup, m.changeKey , m.changeLevel, m.changeDuration, " + 
    		"m.fromToReverse, priority , m.affectCapaPercent1, m.affectCapaQty1, m.affectCapaPercent2, m.affectCapaQty2, " + 
    		"m.affectCapaPercent3, m.affectCapaQty3, m.affectCapaPercent4, m.affectCapaQty4, " + 
    		"m.fromPartsGroup as toPartsGroup, m.toPartsGroup as fromPartsGroup" +
    		//" from ModChange m where m.site = ?1 and m.fab in ?2 and m.fromToReverse = 'Y' order by m.priority")
    		" from ModChange m where m.site = ?1 and m.area in ?2 and m.fromToReverse = 'Y' order by m.priority")
//    List<Object> findReverseBySiteAndFabInOrderByPriority(String site, List<String> fabList);
    List<Object> findReverseBySiteAndAreaInOrderByPriority(String site, List<String> areaList);
    
}

