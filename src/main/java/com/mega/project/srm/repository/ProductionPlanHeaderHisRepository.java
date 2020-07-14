package com.mega.project.srm.repository;

import com.mega.project.srm.domain.ProductionPlanHeaderHis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductionPlanHeaderHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionPlanHeaderHisRepository extends JpaRepository<ProductionPlanHeaderHis, Long> {
}
