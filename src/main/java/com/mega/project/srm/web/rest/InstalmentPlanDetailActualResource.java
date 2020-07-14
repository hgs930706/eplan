package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.InstalmentPlanDetailActual;
import com.mega.project.srm.repository.InstalmentPlanDetailActualRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.InstalmentPlanDetailActual}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InstalmentPlanDetailActualResource {

    private final Logger log = LoggerFactory.getLogger(InstalmentPlanDetailActualResource.class);

    private static final String ENTITY_NAME = "instalmentPlanDetailActual";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstalmentPlanDetailActualRepository instalmentPlanDetailActualRepository;

    public InstalmentPlanDetailActualResource(InstalmentPlanDetailActualRepository instalmentPlanDetailActualRepository) {
        this.instalmentPlanDetailActualRepository = instalmentPlanDetailActualRepository;
    }

    /**
     * {@code POST  /instalment-plan-detail-actuals} : Create a new instalmentPlanDetailActual.
     *
     * @param instalmentPlanDetailActual the instalmentPlanDetailActual to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instalmentPlanDetailActual, or with status {@code 400 (Bad Request)} if the instalmentPlanDetailActual has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instalment-plan-detail-actuals")
    public ResponseEntity<InstalmentPlanDetailActual> createInstalmentPlanDetailActual(@RequestBody InstalmentPlanDetailActual instalmentPlanDetailActual) throws URISyntaxException {
        log.debug("REST request to save InstalmentPlanDetailActual : {}", instalmentPlanDetailActual);
        if (instalmentPlanDetailActual.getId() != null) {
            throw new BadRequestAlertException("A new instalmentPlanDetailActual cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstalmentPlanDetailActual result = instalmentPlanDetailActualRepository.save(instalmentPlanDetailActual);
        return ResponseEntity.created(new URI("/api/instalment-plan-detail-actuals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instalment-plan-detail-actuals} : Updates an existing instalmentPlanDetailActual.
     *
     * @param instalmentPlanDetailActual the instalmentPlanDetailActual to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instalmentPlanDetailActual,
     * or with status {@code 400 (Bad Request)} if the instalmentPlanDetailActual is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instalmentPlanDetailActual couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instalment-plan-detail-actuals")
    public ResponseEntity<InstalmentPlanDetailActual> updateInstalmentPlanDetailActual(@RequestBody InstalmentPlanDetailActual instalmentPlanDetailActual) throws URISyntaxException {
        log.debug("REST request to update InstalmentPlanDetailActual : {}", instalmentPlanDetailActual);
        if (instalmentPlanDetailActual.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstalmentPlanDetailActual result = instalmentPlanDetailActualRepository.save(instalmentPlanDetailActual);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instalmentPlanDetailActual.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instalment-plan-detail-actuals} : get all the instalmentPlanDetailActuals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instalmentPlanDetailActuals in body.
     */
    @GetMapping("/instalment-plan-detail-actuals")
    public ResponseEntity<List<InstalmentPlanDetailActual>> getAllInstalmentPlanDetailActuals(Pageable pageable) {
        log.debug("REST request to get a page of InstalmentPlanDetailActuals");
        Page<InstalmentPlanDetailActual> page = instalmentPlanDetailActualRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /instalment-plan-detail-actuals/:id} : get the "id" instalmentPlanDetailActual.
     *
     * @param id the id of the instalmentPlanDetailActual to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instalmentPlanDetailActual, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instalment-plan-detail-actuals/{id}")
    public ResponseEntity<InstalmentPlanDetailActual> getInstalmentPlanDetailActual(@PathVariable Long id) {
        log.debug("REST request to get InstalmentPlanDetailActual : {}", id);
        Optional<InstalmentPlanDetailActual> instalmentPlanDetailActual = instalmentPlanDetailActualRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(instalmentPlanDetailActual);
    }

    /**
     * {@code DELETE  /instalment-plan-detail-actuals/:id} : delete the "id" instalmentPlanDetailActual.
     *
     * @param id the id of the instalmentPlanDetailActual to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instalment-plan-detail-actuals/{id}")
    public ResponseEntity<Void> deleteInstalmentPlanDetailActual(@PathVariable Long id) {
        log.debug("REST request to delete InstalmentPlanDetailActual : {}", id);
        instalmentPlanDetailActualRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
