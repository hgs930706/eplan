package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.PurchasePlanHeaderHis;
import com.mega.project.srm.repository.PurchasePlanHeaderHisRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.PurchasePlanHeaderHis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PurchasePlanHeaderHisResource {

    private final Logger log = LoggerFactory.getLogger(PurchasePlanHeaderHisResource.class);

    private static final String ENTITY_NAME = "purchasePlanHeaderHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchasePlanHeaderHisRepository purchasePlanHeaderHisRepository;

    public PurchasePlanHeaderHisResource(PurchasePlanHeaderHisRepository purchasePlanHeaderHisRepository) {
        this.purchasePlanHeaderHisRepository = purchasePlanHeaderHisRepository;
    }

    /**
     * {@code POST  /purchase-plan-header-his} : Create a new purchasePlanHeaderHis.
     *
     * @param purchasePlanHeaderHis the purchasePlanHeaderHis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchasePlanHeaderHis, or with status {@code 400 (Bad Request)} if the purchasePlanHeaderHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-plan-header-his")
    public ResponseEntity<PurchasePlanHeaderHis> createPurchasePlanHeaderHis(@RequestBody PurchasePlanHeaderHis purchasePlanHeaderHis) throws URISyntaxException {
        log.debug("REST request to save PurchasePlanHeaderHis : {}", purchasePlanHeaderHis);
        if (purchasePlanHeaderHis.getId() != null) {
            throw new BadRequestAlertException("A new purchasePlanHeaderHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchasePlanHeaderHis result = purchasePlanHeaderHisRepository.save(purchasePlanHeaderHis);
        return ResponseEntity.created(new URI("/api/purchase-plan-header-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-plan-header-his} : Updates an existing purchasePlanHeaderHis.
     *
     * @param purchasePlanHeaderHis the purchasePlanHeaderHis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchasePlanHeaderHis,
     * or with status {@code 400 (Bad Request)} if the purchasePlanHeaderHis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchasePlanHeaderHis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-plan-header-his")
    public ResponseEntity<PurchasePlanHeaderHis> updatePurchasePlanHeaderHis(@RequestBody PurchasePlanHeaderHis purchasePlanHeaderHis) throws URISyntaxException {
        log.debug("REST request to update PurchasePlanHeaderHis : {}", purchasePlanHeaderHis);
        if (purchasePlanHeaderHis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchasePlanHeaderHis result = purchasePlanHeaderHisRepository.save(purchasePlanHeaderHis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchasePlanHeaderHis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-plan-header-his} : get all the purchasePlanHeaderHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchasePlanHeaderHis in body.
     */
    @GetMapping("/purchase-plan-header-his")
    public ResponseEntity<List<PurchasePlanHeaderHis>> getAllPurchasePlanHeaderHis(Pageable pageable) {
        log.debug("REST request to get a page of PurchasePlanHeaderHis");
        Page<PurchasePlanHeaderHis> page = purchasePlanHeaderHisRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /purchase-plan-header-his/:id} : get the "id" purchasePlanHeaderHis.
     *
     * @param id the id of the purchasePlanHeaderHis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchasePlanHeaderHis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-plan-header-his/{id}")
    public ResponseEntity<PurchasePlanHeaderHis> getPurchasePlanHeaderHis(@PathVariable Long id) {
        log.debug("REST request to get PurchasePlanHeaderHis : {}", id);
        Optional<PurchasePlanHeaderHis> purchasePlanHeaderHis = purchasePlanHeaderHisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(purchasePlanHeaderHis);
    }

    /**
     * {@code DELETE  /purchase-plan-header-his/:id} : delete the "id" purchasePlanHeaderHis.
     *
     * @param id the id of the purchasePlanHeaderHis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-plan-header-his/{id}")
    public ResponseEntity<Void> deletePurchasePlanHeaderHis(@PathVariable Long id) {
        log.debug("REST request to delete PurchasePlanHeaderHis : {}", id);
        purchasePlanHeaderHisRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
