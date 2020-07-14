package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.DeliveryCycleSetup;
import com.mega.project.srm.repository.DeliveryCycleSetupRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.DeliveryCycleSetup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeliveryCycleSetupResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryCycleSetupResource.class);

    private static final String ENTITY_NAME = "deliveryCycleSetup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryCycleSetupRepository deliveryCycleSetupRepository;

    public DeliveryCycleSetupResource(DeliveryCycleSetupRepository deliveryCycleSetupRepository) {
        this.deliveryCycleSetupRepository = deliveryCycleSetupRepository;
    }

    /**
     * {@code POST  /delivery-cycle-setups} : Create a new deliveryCycleSetup.
     *
     * @param deliveryCycleSetup the deliveryCycleSetup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryCycleSetup, or with status {@code 400 (Bad Request)} if the deliveryCycleSetup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-cycle-setups")
    public ResponseEntity<DeliveryCycleSetup> createDeliveryCycleSetup(@RequestBody DeliveryCycleSetup deliveryCycleSetup) throws URISyntaxException {
        log.debug("REST request to save DeliveryCycleSetup : {}", deliveryCycleSetup);
        if (deliveryCycleSetup.getId() != null) {
            throw new BadRequestAlertException("A new deliveryCycleSetup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryCycleSetup result = deliveryCycleSetupRepository.save(deliveryCycleSetup);
        return ResponseEntity.created(new URI("/api/delivery-cycle-setups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-cycle-setups} : Updates an existing deliveryCycleSetup.
     *
     * @param deliveryCycleSetup the deliveryCycleSetup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryCycleSetup,
     * or with status {@code 400 (Bad Request)} if the deliveryCycleSetup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryCycleSetup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-cycle-setups")
    public ResponseEntity<DeliveryCycleSetup> updateDeliveryCycleSetup(@RequestBody DeliveryCycleSetup deliveryCycleSetup) throws URISyntaxException {
        log.debug("REST request to update DeliveryCycleSetup : {}", deliveryCycleSetup);
        if (deliveryCycleSetup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryCycleSetup result = deliveryCycleSetupRepository.save(deliveryCycleSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryCycleSetup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delivery-cycle-setups} : get all the deliveryCycleSetups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryCycleSetups in body.
     */
    @GetMapping("/delivery-cycle-setups")
    public ResponseEntity<List<DeliveryCycleSetup>> getAllDeliveryCycleSetups(Pageable pageable) {
        log.debug("REST request to get a page of DeliveryCycleSetups");
        Page<DeliveryCycleSetup> page = deliveryCycleSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /delivery-cycle-setups/:id} : get the "id" deliveryCycleSetup.
     *
     * @param id the id of the deliveryCycleSetup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryCycleSetup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-cycle-setups/{id}")
    public ResponseEntity<DeliveryCycleSetup> getDeliveryCycleSetup(@PathVariable Long id) {
        log.debug("REST request to get DeliveryCycleSetup : {}", id);
        Optional<DeliveryCycleSetup> deliveryCycleSetup = deliveryCycleSetupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deliveryCycleSetup);
    }

    /**
     * {@code DELETE  /delivery-cycle-setups/:id} : delete the "id" deliveryCycleSetup.
     *
     * @param id the id of the deliveryCycleSetup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-cycle-setups/{id}")
    public ResponseEntity<Void> deleteDeliveryCycleSetup(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryCycleSetup : {}", id);
        deliveryCycleSetupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
