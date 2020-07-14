package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.InstalmentPlanDetails;
import com.mega.project.srm.repository.InstalmentPlanDetailsRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.InstalmentPlanDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InstalmentPlanDetailsResource {

    private final Logger log = LoggerFactory.getLogger(InstalmentPlanDetailsResource.class);

    private static final String ENTITY_NAME = "instalmentPlanDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstalmentPlanDetailsRepository instalmentPlanDetailsRepository;

    public InstalmentPlanDetailsResource(InstalmentPlanDetailsRepository instalmentPlanDetailsRepository) {
        this.instalmentPlanDetailsRepository = instalmentPlanDetailsRepository;
    }

    /**
     * {@code POST  /instalment-plan-details} : Create a new instalmentPlanDetails.
     *
     * @param instalmentPlanDetails the instalmentPlanDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instalmentPlanDetails, or with status {@code 400 (Bad Request)} if the instalmentPlanDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instalment-plan-details")
    public ResponseEntity<InstalmentPlanDetails> createInstalmentPlanDetails(@RequestBody InstalmentPlanDetails instalmentPlanDetails) throws URISyntaxException {
        log.debug("REST request to save InstalmentPlanDetails : {}", instalmentPlanDetails);
        if (instalmentPlanDetails.getId() != null) {
            throw new BadRequestAlertException("A new instalmentPlanDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstalmentPlanDetails result = instalmentPlanDetailsRepository.save(instalmentPlanDetails);
        return ResponseEntity.created(new URI("/api/instalment-plan-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instalment-plan-details} : Updates an existing instalmentPlanDetails.
     *
     * @param instalmentPlanDetails the instalmentPlanDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instalmentPlanDetails,
     * or with status {@code 400 (Bad Request)} if the instalmentPlanDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instalmentPlanDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instalment-plan-details")
    public ResponseEntity<InstalmentPlanDetails> updateInstalmentPlanDetails(@RequestBody InstalmentPlanDetails instalmentPlanDetails) throws URISyntaxException {
        log.debug("REST request to update InstalmentPlanDetails : {}", instalmentPlanDetails);
        if (instalmentPlanDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstalmentPlanDetails result = instalmentPlanDetailsRepository.save(instalmentPlanDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instalmentPlanDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instalment-plan-details} : get all the instalmentPlanDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instalmentPlanDetails in body.
     */
    @GetMapping("/instalment-plan-details")
    public ResponseEntity<List<InstalmentPlanDetails>> getAllInstalmentPlanDetails(Pageable pageable) {
        log.debug("REST request to get a page of InstalmentPlanDetails");
        Page<InstalmentPlanDetails> page = instalmentPlanDetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /instalment-plan-details/:id} : get the "id" instalmentPlanDetails.
     *
     * @param id the id of the instalmentPlanDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instalmentPlanDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instalment-plan-details/{id}")
    public ResponseEntity<InstalmentPlanDetails> getInstalmentPlanDetails(@PathVariable Long id) {
        log.debug("REST request to get InstalmentPlanDetails : {}", id);
        Optional<InstalmentPlanDetails> instalmentPlanDetails = instalmentPlanDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(instalmentPlanDetails);
    }

    /**
     * {@code DELETE  /instalment-plan-details/:id} : delete the "id" instalmentPlanDetails.
     *
     * @param id the id of the instalmentPlanDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instalment-plan-details/{id}")
    public ResponseEntity<Void> deleteInstalmentPlanDetails(@PathVariable Long id) {
        log.debug("REST request to delete InstalmentPlanDetails : {}", id);
        instalmentPlanDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
