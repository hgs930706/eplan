package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.ProductionPlanHeader;
import com.mega.project.srm.repository.ProductionPlanHeaderRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.ProductionPlanHeader}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductionPlanHeaderResource {

    private final Logger log = LoggerFactory.getLogger(ProductionPlanHeaderResource.class);

    private static final String ENTITY_NAME = "productionPlanHeader";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionPlanHeaderRepository productionPlanHeaderRepository;

    public ProductionPlanHeaderResource(ProductionPlanHeaderRepository productionPlanHeaderRepository) {
        this.productionPlanHeaderRepository = productionPlanHeaderRepository;
    }

    /**
     * {@code POST  /production-plan-headers} : Create a new productionPlanHeader.
     *
     * @param productionPlanHeader the productionPlanHeader to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionPlanHeader, or with status {@code 400 (Bad Request)} if the productionPlanHeader has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-plan-headers")
    public ResponseEntity<ProductionPlanHeader> createProductionPlanHeader(@RequestBody ProductionPlanHeader productionPlanHeader) throws URISyntaxException {
        log.debug("REST request to save ProductionPlanHeader : {}", productionPlanHeader);
        if (productionPlanHeader.getId() != null) {
            throw new BadRequestAlertException("A new productionPlanHeader cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionPlanHeader result = productionPlanHeaderRepository.save(productionPlanHeader);
        return ResponseEntity.created(new URI("/api/production-plan-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-plan-headers} : Updates an existing productionPlanHeader.
     *
     * @param productionPlanHeader the productionPlanHeader to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionPlanHeader,
     * or with status {@code 400 (Bad Request)} if the productionPlanHeader is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionPlanHeader couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-plan-headers")
    public ResponseEntity<ProductionPlanHeader> updateProductionPlanHeader(@RequestBody ProductionPlanHeader productionPlanHeader) throws URISyntaxException {
        log.debug("REST request to update ProductionPlanHeader : {}", productionPlanHeader);
        if (productionPlanHeader.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductionPlanHeader result = productionPlanHeaderRepository.save(productionPlanHeader);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productionPlanHeader.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /production-plan-headers} : get all the productionPlanHeaders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionPlanHeaders in body.
     */
    @GetMapping("/production-plan-headers")
    public ResponseEntity<List<ProductionPlanHeader>> getAllProductionPlanHeaders(Pageable pageable) {
        log.debug("REST request to get a page of ProductionPlanHeaders");
        Page<ProductionPlanHeader> page = productionPlanHeaderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /production-plan-headers/:id} : get the "id" productionPlanHeader.
     *
     * @param id the id of the productionPlanHeader to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionPlanHeader, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-plan-headers/{id}")
    public ResponseEntity<ProductionPlanHeader> getProductionPlanHeader(@PathVariable Long id) {
        log.debug("REST request to get ProductionPlanHeader : {}", id);
        Optional<ProductionPlanHeader> productionPlanHeader = productionPlanHeaderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productionPlanHeader);
    }

    /**
     * {@code DELETE  /production-plan-headers/:id} : delete the "id" productionPlanHeader.
     *
     * @param id the id of the productionPlanHeader to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-plan-headers/{id}")
    public ResponseEntity<Void> deleteProductionPlanHeader(@PathVariable Long id) {
        log.debug("REST request to delete ProductionPlanHeader : {}", id);
        productionPlanHeaderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
