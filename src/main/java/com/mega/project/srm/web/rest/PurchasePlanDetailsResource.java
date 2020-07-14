package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.PurchasePlanDetails;
import com.mega.project.srm.repository.PurchasePlanDetailsRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.PurchasePlanDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PurchasePlanDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PurchasePlanDetailsResource.class);

    private static final String ENTITY_NAME = "purchasePlanDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchasePlanDetailsRepository purchasePlanDetailsRepository;

    public PurchasePlanDetailsResource(PurchasePlanDetailsRepository purchasePlanDetailsRepository) {
        this.purchasePlanDetailsRepository = purchasePlanDetailsRepository;
    }

    /**
     * {@code POST  /purchase-plan-details} : Create a new purchasePlanDetails.
     *
     * @param purchasePlanDetails the purchasePlanDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchasePlanDetails, or with status {@code 400 (Bad Request)} if the purchasePlanDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-plan-details")
    public ResponseEntity<PurchasePlanDetails> createPurchasePlanDetails(@RequestBody PurchasePlanDetails purchasePlanDetails) throws URISyntaxException {
        log.debug("REST request to save PurchasePlanDetails : {}", purchasePlanDetails);
        if (purchasePlanDetails.getId() != null) {
            throw new BadRequestAlertException("A new purchasePlanDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchasePlanDetails result = purchasePlanDetailsRepository.save(purchasePlanDetails);
        return ResponseEntity.created(new URI("/api/purchase-plan-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-plan-details} : Updates an existing purchasePlanDetails.
     *
     * @param purchasePlanDetails the purchasePlanDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchasePlanDetails,
     * or with status {@code 400 (Bad Request)} if the purchasePlanDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchasePlanDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-plan-details")
    public ResponseEntity<PurchasePlanDetails> updatePurchasePlanDetails(@RequestBody PurchasePlanDetails purchasePlanDetails) throws URISyntaxException {
        log.debug("REST request to update PurchasePlanDetails : {}", purchasePlanDetails);
        if (purchasePlanDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchasePlanDetails result = purchasePlanDetailsRepository.save(purchasePlanDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchasePlanDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-plan-details} : get all the purchasePlanDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchasePlanDetails in body.
     */
    @GetMapping("/purchase-plan-details")
    public ResponseEntity<List<PurchasePlanDetails>> getAllPurchasePlanDetails(Pageable pageable) {
        log.debug("REST request to get a page of PurchasePlanDetails");
        Page<PurchasePlanDetails> page = purchasePlanDetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /purchase-plan-details/:id} : get the "id" purchasePlanDetails.
     *
     * @param id the id of the purchasePlanDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchasePlanDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-plan-details/{id}")
    public ResponseEntity<PurchasePlanDetails> getPurchasePlanDetails(@PathVariable Long id) {
        log.debug("REST request to get PurchasePlanDetails : {}", id);
        Optional<PurchasePlanDetails> purchasePlanDetails = purchasePlanDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(purchasePlanDetails);
    }

    /**
     * {@code DELETE  /purchase-plan-details/:id} : delete the "id" purchasePlanDetails.
     *
     * @param id the id of the purchasePlanDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-plan-details/{id}")
    public ResponseEntity<Void> deletePurchasePlanDetails(@PathVariable Long id) {
        log.debug("REST request to delete PurchasePlanDetails : {}", id);
        purchasePlanDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
