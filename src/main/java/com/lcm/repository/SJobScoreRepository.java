package com.lcm.repository;

import com.lcm.domain.SJobScore;
import com.lcm.domain.SJobScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface SJobScoreRepository extends JpaRepository<SJobScore, SJobScoreId>, QuerydslPredicateExecutor<SJobScore> {


    @Query("from SJobScore s order by s.sjobscoreId.constraintItem,s.sjobscoreId.score,s.sjobscoreId.scoreItem")
    List<SJobScore> findByAll();
    
    @Modifying
	@Transactional
	public void deleteBySjobscoreIdSiteAndSjobscoreIdTrxId(String site, String trx_id);

    List<SJobScore> findBySjobscoreIdTrxId(String trxId);

}
