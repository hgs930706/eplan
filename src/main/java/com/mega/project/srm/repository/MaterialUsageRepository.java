package com.mega.project.srm.repository;

import com.mega.project.srm.domain.MaterialUsage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MaterialUsage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialUsageRepository extends JpaRepository<MaterialUsage, Long> {
}
