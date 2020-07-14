package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.PurchasePlanDetailHis;
import com.mega.project.srm.repository.PurchasePlanDetailHisRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.PurchasePlanDetailHis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PurchasePlanDetailHisResource {

    private final Logger log = LoggerFactory.getLogger(PurchasePlanDetailHisResource.class);

    private static final String ENTITY_NAME = "purchasePlanDetailHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchasePlanDetailHisRepository purchasePlanDetailHisRepository;

    public PurchasePlanDetailHisResource(PurchasePlanDetailHisRepository purchasePlanDetailHisRepository) {
        this.purchasePlanDetailHisRepository = purchasePlanDetailHisRepository;
    }

    /**
     * {@code POST  /purchase-plan-detail-his} : Create a new purchasePlanDetailHis.
     *
     * @param purchasePlanDetailHis the purchasePlanDetailHis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchasePlanDetailHis, or with status {@code 400 (Bad Request)} if the purchasePlanDetailHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-plan-detail-his")
    public ResponseEntity<PurchasePlanDetailHis> createPurchasePlanDetailHis(@RequestBody PurchasePlanDetailHis purchasePlanDetailHis) throws URISyntaxException {
        log.debug("REST request to save PurchasePlanDetailHis : {}", purchasePlanDetailHis);
        if (purchasePlanDetailHis.getId() != null) {
            throw new BadRequestAlertException("A new purchasePlanDetailHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchasePlanDetailHis result = purchasePlanDetailHisRepository.save(purchasePlanDetailHis);
        return ResponseEntity.created(new URI("/api/purchase-plan-detail-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-plan-detail-his} : Updates an existing purchasePlanDetailHis.
     *
     * @param purchasePlanDetailHis the purchasePlanDetailHis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchasePlanDetailHis,
     * or with status {@code 400 (Bad Request)} if the purchasePlanDetailHis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchasePlanDetailHis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-plan-detail-his")
    public ResponseEntity<PurchasePlanDetailHis> updatePurchasePlanDetailHis(@RequestBody PurchasePlanDetailHis purchasePlanDetailHis) throws URISyntaxException {
        log.debug("REST request to update PurchasePlanDetailHis : {}", purchasePlanDetailHis);
        if (purchasePlanDetailHis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchasePlanDetailHis result = purchasePlanDetailHisRepository.save(purchasePlanDetailHis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchasePlanDetailHis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-plan-detail-his} : get all the purchasePlanDetailHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchasePlanDetailHis in body.
     */
    @GetMapping("/purchase-plan-detail-his")
    public ResponseEntity<List<PurchasePlanDetailHis>> getAllPurchasePlanDetailHis(Pageable pageable) {
        log.debug("REST request to get a page of PurchasePlanDetailHis");
        Page<PurchasePlanDetailHis> page = purchasePlanDetailHisRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /purchase-plan-detail-his/:id} : get the "id" purchasePlanDetailHis.
     *
     * @param id the id of the purchasePlanDetailHis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchasePlanDetailHis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-plan-detail-his/{id}")
    public ResponseEntity<PurchasePlanDetailHis> getPurchasePlanDetailHis(@PathVariable Long id) {
        log.debug("REST request to get PurchasePlanDetailHis : {}", id);
        Optional<PurchasePlanDetailHis> purchasePlanDetailHis = purchasePlanDetailHisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(purchasePlanDetailHis);
    }

    /**
     * {@code DELETE  /purchase-plan-detail-his/:id} : delete the "id" purchasePlanDetailHis.
     *
     * @param id the id of the purchasePlanDetailHis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-plan-detail-his/{id}")
    public ResponseEntity<Void> deletePurchasePlanDetailHis(@PathVariable Long id) {
        log.debug("REST request to delete PurchasePlanDetailHis : {}", id);
        purchasePlanDetailHisRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
