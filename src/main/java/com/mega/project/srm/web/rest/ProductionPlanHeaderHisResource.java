package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.ProductionPlanHeaderHis;
import com.mega.project.srm.repository.ProductionPlanHeaderHisRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.ProductionPlanHeaderHis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductionPlanHeaderHisResource {

    private final Logger log = LoggerFactory.getLogger(ProductionPlanHeaderHisResource.class);

    private static final String ENTITY_NAME = "productionPlanHeaderHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionPlanHeaderHisRepository productionPlanHeaderHisRepository;

    public ProductionPlanHeaderHisResource(ProductionPlanHeaderHisRepository productionPlanHeaderHisRepository) {
        this.productionPlanHeaderHisRepository = productionPlanHeaderHisRepository;
    }

    /**
     * {@code POST  /production-plan-header-his} : Create a new productionPlanHeaderHis.
     *
     * @param productionPlanHeaderHis the productionPlanHeaderHis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionPlanHeaderHis, or with status {@code 400 (Bad Request)} if the productionPlanHeaderHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-plan-header-his")
    public ResponseEntity<ProductionPlanHeaderHis> createProductionPlanHeaderHis(@RequestBody ProductionPlanHeaderHis productionPlanHeaderHis) throws URISyntaxException {
        log.debug("REST request to save ProductionPlanHeaderHis : {}", productionPlanHeaderHis);
        if (productionPlanHeaderHis.getId() != null) {
            throw new BadRequestAlertException("A new productionPlanHeaderHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionPlanHeaderHis result = productionPlanHeaderHisRepository.save(productionPlanHeaderHis);
        return ResponseEntity.created(new URI("/api/production-plan-header-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-plan-header-his} : Updates an existing productionPlanHeaderHis.
     *
     * @param productionPlanHeaderHis the productionPlanHeaderHis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionPlanHeaderHis,
     * or with status {@code 400 (Bad Request)} if the productionPlanHeaderHis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionPlanHeaderHis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-plan-header-his")
    public ResponseEntity<ProductionPlanHeaderHis> updateProductionPlanHeaderHis(@RequestBody ProductionPlanHeaderHis productionPlanHeaderHis) throws URISyntaxException {
        log.debug("REST request to update ProductionPlanHeaderHis : {}", productionPlanHeaderHis);
        if (productionPlanHeaderHis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductionPlanHeaderHis result = productionPlanHeaderHisRepository.save(productionPlanHeaderHis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productionPlanHeaderHis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /production-plan-header-his} : get all the productionPlanHeaderHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionPlanHeaderHis in body.
     */
    @GetMapping("/production-plan-header-his")
    public ResponseEntity<List<ProductionPlanHeaderHis>> getAllProductionPlanHeaderHis(Pageable pageable) {
        log.debug("REST request to get a page of ProductionPlanHeaderHis");
        Page<ProductionPlanHeaderHis> page = productionPlanHeaderHisRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /production-plan-header-his/:id} : get the "id" productionPlanHeaderHis.
     *
     * @param id the id of the productionPlanHeaderHis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionPlanHeaderHis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-plan-header-his/{id}")
    public ResponseEntity<ProductionPlanHeaderHis> getProductionPlanHeaderHis(@PathVariable Long id) {
        log.debug("REST request to get ProductionPlanHeaderHis : {}", id);
        Optional<ProductionPlanHeaderHis> productionPlanHeaderHis = productionPlanHeaderHisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productionPlanHeaderHis);
    }

    /**
     * {@code DELETE  /production-plan-header-his/:id} : delete the "id" productionPlanHeaderHis.
     *
     * @param id the id of the productionPlanHeaderHis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-plan-header-his/{id}")
    public ResponseEntity<Void> deleteProductionPlanHeaderHis(@PathVariable Long id) {
        log.debug("REST request to delete ProductionPlanHeaderHis : {}", id);
        productionPlanHeaderHisRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
