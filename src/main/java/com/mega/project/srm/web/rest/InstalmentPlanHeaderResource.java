package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.InstalmentPlanHeader;
import com.mega.project.srm.repository.InstalmentPlanHeaderRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.InstalmentPlanHeader}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InstalmentPlanHeaderResource {

    private final Logger log = LoggerFactory.getLogger(InstalmentPlanHeaderResource.class);

    private static final String ENTITY_NAME = "instalmentPlanHeader";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstalmentPlanHeaderRepository instalmentPlanHeaderRepository;

    public InstalmentPlanHeaderResource(InstalmentPlanHeaderRepository instalmentPlanHeaderRepository) {
        this.instalmentPlanHeaderRepository = instalmentPlanHeaderRepository;
    }

    /**
     * {@code POST  /instalment-plan-headers} : Create a new instalmentPlanHeader.
     *
     * @param instalmentPlanHeader the instalmentPlanHeader to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instalmentPlanHeader, or with status {@code 400 (Bad Request)} if the instalmentPlanHeader has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instalment-plan-headers")
    public ResponseEntity<InstalmentPlanHeader> createInstalmentPlanHeader(@RequestBody InstalmentPlanHeader instalmentPlanHeader) throws URISyntaxException {
        log.debug("REST request to save InstalmentPlanHeader : {}", instalmentPlanHeader);
        if (instalmentPlanHeader.getId() != null) {
            throw new BadRequestAlertException("A new instalmentPlanHeader cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstalmentPlanHeader result = instalmentPlanHeaderRepository.save(instalmentPlanHeader);
        return ResponseEntity.created(new URI("/api/instalment-plan-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instalment-plan-headers} : Updates an existing instalmentPlanHeader.
     *
     * @param instalmentPlanHeader the instalmentPlanHeader to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instalmentPlanHeader,
     * or with status {@code 400 (Bad Request)} if the instalmentPlanHeader is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instalmentPlanHeader couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instalment-plan-headers")
    public ResponseEntity<InstalmentPlanHeader> updateInstalmentPlanHeader(@RequestBody InstalmentPlanHeader instalmentPlanHeader) throws URISyntaxException {
        log.debug("REST request to update InstalmentPlanHeader : {}", instalmentPlanHeader);
        if (instalmentPlanHeader.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstalmentPlanHeader result = instalmentPlanHeaderRepository.save(instalmentPlanHeader);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instalmentPlanHeader.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instalment-plan-headers} : get all the instalmentPlanHeaders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instalmentPlanHeaders in body.
     */
    @GetMapping("/instalment-plan-headers")
    public ResponseEntity<List<InstalmentPlanHeader>> getAllInstalmentPlanHeaders(Pageable pageable) {
        log.debug("REST request to get a page of InstalmentPlanHeaders");
        Page<InstalmentPlanHeader> page = instalmentPlanHeaderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /instalment-plan-headers/:id} : get the "id" instalmentPlanHeader.
     *
     * @param id the id of the instalmentPlanHeader to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instalmentPlanHeader, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instalment-plan-headers/{id}")
    public ResponseEntity<InstalmentPlanHeader> getInstalmentPlanHeader(@PathVariable Long id) {
        log.debug("REST request to get InstalmentPlanHeader : {}", id);
        Optional<InstalmentPlanHeader> instalmentPlanHeader = instalmentPlanHeaderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(instalmentPlanHeader);
    }

    /**
     * {@code DELETE  /instalment-plan-headers/:id} : delete the "id" instalmentPlanHeader.
     *
     * @param id the id of the instalmentPlanHeader to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instalment-plan-headers/{id}")
    public ResponseEntity<Void> deleteInstalmentPlanHeader(@PathVariable Long id) {
        log.debug("REST request to delete InstalmentPlanHeader : {}", id);
        instalmentPlanHeaderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
