package com.mega.project.srm.repository;

import com.mega.project.srm.domain.InventoryHis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InventoryHis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryHisRepository extends JpaRepository<InventoryHis, Long> {
}
