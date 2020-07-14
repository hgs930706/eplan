package com.mega.project.srm.repository;

import com.mega.project.srm.domain.SummaryMaterialReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SummaryMaterialReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SummaryMaterialReportRepository extends JpaRepository<SummaryMaterialReport, Long> {
}
