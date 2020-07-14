package com.mega.project.srm.repository;

import com.mega.project.srm.domain.PoDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PoDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoDetailsRepository extends JpaRepository<PoDetails, Long> {
}
