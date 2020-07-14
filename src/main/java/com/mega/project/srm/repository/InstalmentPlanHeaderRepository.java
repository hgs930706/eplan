package com.mega.project.srm.repository;

import com.mega.project.srm.domain.InstalmentPlanHeader;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InstalmentPlanHeader entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstalmentPlanHeaderRepository extends JpaRepository<InstalmentPlanHeader, Long> {
}
