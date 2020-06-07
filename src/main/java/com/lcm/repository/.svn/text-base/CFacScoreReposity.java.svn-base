package com.lcm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lcm.domain.CFacScore;

public interface CFacScoreReposity extends JpaRepository<CFacScore, String> {
	List<CFacScore> findBySiteAndAreaIn(String site, List<String> areaList);
}
