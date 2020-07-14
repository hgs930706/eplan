package com.mega.project.srm.repository;

import com.mega.project.srm.domain.PurchasePlanHeaderHis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PurchasePlanHeaderHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchasePlanHeaderHisRepository extends JpaRepository<PurchasePlanHeaderHis, Long> {
}
