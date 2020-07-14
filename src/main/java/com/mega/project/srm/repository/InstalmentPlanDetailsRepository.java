package com.mega.project.srm.repository;

import com.mega.project.srm.domain.InstalmentPlanDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InstalmentPlanDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstalmentPlanDetailsRepository extends JpaRepository<InstalmentPlanDetails, Long> {
}
