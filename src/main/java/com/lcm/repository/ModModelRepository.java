package com.lcm.repository;

import com.lcm.domain.ModModel;
import com.lcm.domain.ModelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ModModelRepository extends JpaRepository<ModModel, ModelId> , QuerydslPredicateExecutor<ModModel> {

    @Query("select distinct m.modelId.partNo from ModModel m where m.modelId.site = ?1 order by m.modelId.partNo")
    List<String> findDistinctPartNoBySite(String site);

    List<ModModel> findByOrderByModelNoDesc();//根据modelno降序
    
	List<ModModel> findByModelIdSite(String site);
}
