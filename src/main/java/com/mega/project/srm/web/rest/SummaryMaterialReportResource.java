package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.SummaryMaterialReport;
import com.mega.project.srm.repository.SummaryMaterialReportRepository;
import com.mega.project.srm.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mega.project.srm.domain.SummaryMaterialReport}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SummaryMaterialReportResource {

    private final Logger log = LoggerFactory.getLogger(SummaryMaterialReportResource.class);

    private static final String ENTITY_NAME = "summaryMaterialReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SummaryMaterialReportRepository summaryMaterialReportRepository;

    public SummaryMaterialReportResource(SummaryMaterialReportRepository summaryMaterialReportRepository) {
        this.summaryMaterialReportRepository = summaryMaterialReportRepository;
    }

    /**
     * {@code POST  /summary-material-reports} : Create a new summaryMaterialReport.
     *
     * @param summaryMaterialReport the summaryMaterialReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new summaryMaterialReport, or with status {@code 400 (Bad Request)} if the summaryMaterialReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/summary-material-reports")
    public ResponseEntity<SummaryMaterialReport> createSummaryMaterialReport(@RequestBody SummaryMaterialReport summaryMaterialReport) throws URISyntaxException {
        log.debug("REST request to save SummaryMaterialReport : {}", summaryMaterialReport);
        if (summaryMaterialReport.getId() != null) {
            throw new BadRequestAlertException("A new summaryMaterialReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SummaryMaterialReport result = summaryMaterialReportRepository.save(summaryMaterialReport);
        return ResponseEntity.created(new URI("/api/summary-material-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /summary-material-reports} : Updates an existing summaryMaterialReport.
     *
     * @param summaryMaterialReport the summaryMaterialReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated summaryMaterialReport,
     * or with status {@code 400 (Bad Request)} if the summaryMaterialReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the summaryMaterialReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/summary-material-reports")
    public ResponseEntity<SummaryMaterialReport> updateSummaryMaterialReport(@RequestBody SummaryMaterialReport summaryMaterialReport) throws URISyntaxException {
        log.debug("REST request to update SummaryMaterialReport : {}", summaryMaterialReport);
        if (summaryMaterialReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SummaryMaterialReport result = summaryMaterialReportRepository.save(summaryMaterialReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, summaryMaterialReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /summary-material-reports} : get all the summaryMaterialReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of summaryMaterialReports in body.
     */
    @GetMapping("/summary-material-reports")
    public ResponseEntity<List<SummaryMaterialReport>> getAllSummaryMaterialReports(Pageable pageable) {
        log.debug("REST request to get a page of SummaryMaterialReports");
        Page<SummaryMaterialReport> page = summaryMaterialReportRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /summary-material-reports/:id} : get the "id" summaryMaterialReport.
     *
     * @param id the id of the summaryMaterialReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the summaryMaterialReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/summary-material-reports/{id}")
    public ResponseEntity<SummaryMaterialReport> getSummaryMaterialReport(@PathVariable Long id) {
        log.debug("REST request to get SummaryMaterialReport : {}", id);
        Optional<SummaryMaterialReport> summaryMaterialReport = summaryMaterialReportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(summaryMaterialReport);
    }

    /**
     * {@code DELETE  /summary-material-reports/:id} : delete the "id" summaryMaterialReport.
     *
     * @param id the id of the summaryMaterialReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/summary-material-reports/{id}")
    public ResponseEntity<Void> deleteSummaryMaterialReport(@PathVariable Long id) {
        log.debug("REST request to delete SummaryMaterialReport : {}", id);
        summaryMaterialReportRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
