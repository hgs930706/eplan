package com.mega.project.srm.repository;

import com.mega.project.srm.domain.ProductionPlanHeader;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductionPlanHeader entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionPlanHeaderRepository extends JpaRepository<ProductionPlanHeader, Long> {
}
