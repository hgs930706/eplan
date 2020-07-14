package com.mega.project.srm.repository;

import com.mega.project.srm.domain.ProductionPlanDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductionPlanDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionPlanDetailsRepository extends JpaRepository<ProductionPlanDetails, Long> {
}
