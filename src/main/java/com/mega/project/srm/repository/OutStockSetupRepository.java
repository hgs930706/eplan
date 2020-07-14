package com.mega.project.srm.repository;

import com.mega.project.srm.domain.OutStockSetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OutStockSetup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutStockSetupRepository extends JpaRepository<OutStockSetup, Long> {
}
