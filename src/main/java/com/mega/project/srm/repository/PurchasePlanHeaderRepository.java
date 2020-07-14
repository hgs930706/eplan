package com.mega.project.srm.repository;

import com.mega.project.srm.domain.PurchasePlanHeader;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PurchasePlanHeader entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchasePlanHeaderRepository extends JpaRepository<PurchasePlanHeader, Long> {
}
