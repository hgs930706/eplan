package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.ProductionPlanDetailHis;
import com.mega.project.srm.repository.ProductionPlanDetailHisRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.ProductionPlanDetailHis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductionPlanDetailHisResource {

    private final Logger log = LoggerFactory.getLogger(ProductionPlanDetailHisResource.class);

    private static final String ENTITY_NAME = "productionPlanDetailHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionPlanDetailHisRepository productionPlanDetailHisRepository;

    public ProductionPlanDetailHisResource(ProductionPlanDetailHisRepository productionPlanDetailHisRepository) {
        this.productionPlanDetailHisRepository = productionPlanDetailHisRepository;
    }

    /**
     * {@code POST  /production-plan-detail-his} : Create a new productionPlanDetailHis.
     *
     * @param productionPlanDetailHis the productionPlanDetailHis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionPlanDetailHis, or with status {@code 400 (Bad Request)} if the productionPlanDetailHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-plan-detail-his")
    public ResponseEntity<ProductionPlanDetailHis> createProductionPlanDetailHis(@RequestBody ProductionPlanDetailHis productionPlanDetailHis) throws URISyntaxException {
        log.debug("REST request to save ProductionPlanDetailHis : {}", productionPlanDetailHis);
        if (productionPlanDetailHis.getId() != null) {
            throw new BadRequestAlertException("A new productionPlanDetailHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionPlanDetailHis result = productionPlanDetailHisRepository.save(productionPlanDetailHis);
        return ResponseEntity.created(new URI("/api/production-plan-detail-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-plan-detail-his} : Updates an existing productionPlanDetailHis.
     *
     * @param productionPlanDetailHis the productionPlanDetailHis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionPlanDetailHis,
     * or with status {@code 400 (Bad Request)} if the productionPlanDetailHis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionPlanDetailHis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-plan-detail-his")
    public ResponseEntity<ProductionPlanDetailHis> updateProductionPlanDetailHis(@RequestBody ProductionPlanDetailHis productionPlanDetailHis) throws URISyntaxException {
        log.debug("REST request to update ProductionPlanDetailHis : {}", productionPlanDetailHis);
        if (productionPlanDetailHis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductionPlanDetailHis result = productionPlanDetailHisRepository.save(productionPlanDetailHis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productionPlanDetailHis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /production-plan-detail-his} : get all the productionPlanDetailHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionPlanDetailHis in body.
     */
    @GetMapping("/production-plan-detail-his")
    public ResponseEntity<List<ProductionPlanDetailHis>> getAllProductionPlanDetailHis(Pageable pageable) {
        log.debug("REST request to get a page of ProductionPlanDetailHis");
        Page<ProductionPlanDetailHis> page = productionPlanDetailHisRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /production-plan-detail-his/:id} : get the "id" productionPlanDetailHis.
     *
     * @param id the id of the productionPlanDetailHis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionPlanDetailHis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-plan-detail-his/{id}")
    public ResponseEntity<ProductionPlanDetailHis> getProductionPlanDetailHis(@PathVariable Long id) {
        log.debug("REST request to get ProductionPlanDetailHis : {}", id);
        Optional<ProductionPlanDetailHis> productionPlanDetailHis = productionPlanDetailHisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productionPlanDetailHis);
    }

    /**
     * {@code DELETE  /production-plan-detail-his/:id} : delete the "id" productionPlanDetailHis.
     *
     * @param id the id of the productionPlanDetailHis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-plan-detail-his/{id}")
    public ResponseEntity<Void> deleteProductionPlanDetailHis(@PathVariable Long id) {
        log.debug("REST request to delete ProductionPlanDetailHis : {}", id);
        productionPlanDetailHisRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
