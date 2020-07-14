package com.mega.project.srm.repository;

import com.mega.project.srm.domain.PurchasePlanDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PurchasePlanDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchasePlanDetailsRepository extends JpaRepository<PurchasePlanDetails, Long> {
}
