package com.mega.project.srm.repository;

import com.mega.project.srm.domain.MaterialUsageSummaryHeader;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MaterialUsageSummaryHeader entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialUsageSummaryHeaderRepository extends JpaRepository<MaterialUsageSummaryHeader, Long> {
}
