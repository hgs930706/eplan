package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.PurchasePlanHeader;
import com.mega.project.srm.repository.PurchasePlanHeaderRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.PurchasePlanHeader}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PurchasePlanHeaderResource {

    private final Logger log = LoggerFactory.getLogger(PurchasePlanHeaderResource.class);

    private static final String ENTITY_NAME = "purchasePlanHeader";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchasePlanHeaderRepository purchasePlanHeaderRepository;

    public PurchasePlanHeaderResource(PurchasePlanHeaderRepository purchasePlanHeaderRepository) {
        this.purchasePlanHeaderRepository = purchasePlanHeaderRepository;
    }

    /**
     * {@code POST  /purchase-plan-headers} : Create a new purchasePlanHeader.
     *
     * @param purchasePlanHeader the purchasePlanHeader to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchasePlanHeader, or with status {@code 400 (Bad Request)} if the purchasePlanHeader has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-plan-headers")
    public ResponseEntity<PurchasePlanHeader> createPurchasePlanHeader(@RequestBody PurchasePlanHeader purchasePlanHeader) throws URISyntaxException {
        log.debug("REST request to save PurchasePlanHeader : {}", purchasePlanHeader);
        if (purchasePlanHeader.getId() != null) {
            throw new BadRequestAlertException("A new purchasePlanHeader cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchasePlanHeader result = purchasePlanHeaderRepository.save(purchasePlanHeader);
        return ResponseEntity.created(new URI("/api/purchase-plan-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-plan-headers} : Updates an existing purchasePlanHeader.
     *
     * @param purchasePlanHeader the purchasePlanHeader to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchasePlanHeader,
     * or with status {@code 400 (Bad Request)} if the purchasePlanHeader is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchasePlanHeader couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-plan-headers")
    public ResponseEntity<PurchasePlanHeader> updatePurchasePlanHeader(@RequestBody PurchasePlanHeader purchasePlanHeader) throws URISyntaxException {
        log.debug("REST request to update PurchasePlanHeader : {}", purchasePlanHeader);
        if (purchasePlanHeader.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchasePlanHeader result = purchasePlanHeaderRepository.save(purchasePlanHeader);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchasePlanHeader.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-plan-headers} : get all the purchasePlanHeaders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchasePlanHeaders in body.
     */
    @GetMapping("/purchase-plan-headers")
    public ResponseEntity<List<PurchasePlanHeader>> getAllPurchasePlanHeaders(Pageable pageable) {
        log.debug("REST request to get a page of PurchasePlanHeaders");
        Page<PurchasePlanHeader> page = purchasePlanHeaderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /purchase-plan-headers/:id} : get the "id" purchasePlanHeader.
     *
     * @param id the id of the purchasePlanHeader to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchasePlanHeader, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-plan-headers/{id}")
    public ResponseEntity<PurchasePlanHeader> getPurchasePlanHeader(@PathVariable Long id) {
        log.debug("REST request to get PurchasePlanHeader : {}", id);
        Optional<PurchasePlanHeader> purchasePlanHeader = purchasePlanHeaderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(purchasePlanHeader);
    }

    /**
     * {@code DELETE  /purchase-plan-headers/:id} : delete the "id" purchasePlanHeader.
     *
     * @param id the id of the purchasePlanHeader to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-plan-headers/{id}")
    public ResponseEntity<Void> deletePurchasePlanHeader(@PathVariable Long id) {
        log.debug("REST request to delete PurchasePlanHeader : {}", id);
        purchasePlanHeaderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
