package com.mega.project.srm.repository;

import com.mega.project.srm.domain.InstalmentPlanDetailActual;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InstalmentPlanDetailActual entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstalmentPlanDetailActualRepository extends JpaRepository<InstalmentPlanDetailActual, Long> {
}
