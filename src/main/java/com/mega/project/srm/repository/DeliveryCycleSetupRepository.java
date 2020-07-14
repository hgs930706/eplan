package com.mega.project.srm.repository;

import com.mega.project.srm.domain.DeliveryCycleSetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DeliveryCycleSetup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryCycleSetupRepository extends JpaRepository<DeliveryCycleSetup, Long> {
}
