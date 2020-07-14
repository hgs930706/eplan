package com.mega.project.srm.repository;

import com.mega.project.srm.domain.ProductionPlanDetailHis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductionPlanDetailHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionPlanDetailHisRepository extends JpaRepository<ProductionPlanDetailHis, Long> {
}
