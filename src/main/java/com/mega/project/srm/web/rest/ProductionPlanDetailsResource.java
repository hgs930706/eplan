package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.ProductionPlanDetails;
import com.mega.project.srm.repository.ProductionPlanDetailsRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.ProductionPlanDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductionPlanDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProductionPlanDetailsResource.class);

    private static final String ENTITY_NAME = "productionPlanDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionPlanDetailsRepository productionPlanDetailsRepository;

    public ProductionPlanDetailsResource(ProductionPlanDetailsRepository productionPlanDetailsRepository) {
        this.productionPlanDetailsRepository = productionPlanDetailsRepository;
    }

    /**
     * {@code POST  /production-plan-details} : Create a new productionPlanDetails.
     *
     * @param productionPlanDetails the productionPlanDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionPlanDetails, or with status {@code 400 (Bad Request)} if the productionPlanDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-plan-details")
    public ResponseEntity<ProductionPlanDetails> createProductionPlanDetails(@RequestBody ProductionPlanDetails productionPlanDetails) throws URISyntaxException {
        log.debug("REST request to save ProductionPlanDetails : {}", productionPlanDetails);
        if (productionPlanDetails.getId() != null) {
            throw new BadRequestAlertException("A new productionPlanDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionPlanDetails result = productionPlanDetailsRepository.save(productionPlanDetails);
        return ResponseEntity.created(new URI("/api/production-plan-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-plan-details} : Updates an existing productionPlanDetails.
     *
     * @param productionPlanDetails the productionPlanDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionPlanDetails,
     * or with status {@code 400 (Bad Request)} if the productionPlanDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionPlanDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-plan-details")
    public ResponseEntity<ProductionPlanDetails> updateProductionPlanDetails(@RequestBody ProductionPlanDetails productionPlanDetails) throws URISyntaxException {
        log.debug("REST request to update ProductionPlanDetails : {}", productionPlanDetails);
        if (productionPlanDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductionPlanDetails result = productionPlanDetailsRepository.save(productionPlanDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productionPlanDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /production-plan-details} : get all the productionPlanDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionPlanDetails in body.
     */
    @GetMapping("/production-plan-details")
    public ResponseEntity<List<ProductionPlanDetails>> getAllProductionPlanDetails(Pageable pageable) {
        log.debug("REST request to get a page of ProductionPlanDetails");
        Page<ProductionPlanDetails> page = productionPlanDetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /production-plan-details/:id} : get the "id" productionPlanDetails.
     *
     * @param id the id of the productionPlanDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionPlanDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-plan-details/{id}")
    public ResponseEntity<ProductionPlanDetails> getProductionPlanDetails(@PathVariable Long id) {
        log.debug("REST request to get ProductionPlanDetails : {}", id);
        Optional<ProductionPlanDetails> productionPlanDetails = productionPlanDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productionPlanDetails);
    }

    /**
     * {@code DELETE  /production-plan-details/:id} : delete the "id" productionPlanDetails.
     *
     * @param id the id of the productionPlanDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-plan-details/{id}")
    public ResponseEntity<Void> deleteProductionPlanDetails(@PathVariable Long id) {
        log.debug("REST request to delete ProductionPlanDetails : {}", id);
        productionPlanDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
