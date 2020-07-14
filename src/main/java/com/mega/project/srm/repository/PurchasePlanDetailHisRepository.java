package com.mega.project.srm.repository;

import com.mega.project.srm.domain.PurchasePlanDetailHis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PurchasePlanDetailHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchasePlanDetailHisRepository extends JpaRepository<PurchasePlanDetailHis, Long> {
}
