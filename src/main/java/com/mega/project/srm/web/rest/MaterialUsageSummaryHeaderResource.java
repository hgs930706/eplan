package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.MaterialUsageSummaryHeader;
import com.mega.project.srm.repository.MaterialUsageSummaryHeaderRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.MaterialUsageSummaryHeader}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MaterialUsageSummaryHeaderResource {

    private final Logger log = LoggerFactory.getLogger(MaterialUsageSummaryHeaderResource.class);

    private static final String ENTITY_NAME = "materialUsageSummaryHeader";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialUsageSummaryHeaderRepository materialUsageSummaryHeaderRepository;

    public MaterialUsageSummaryHeaderResource(MaterialUsageSummaryHeaderRepository materialUsageSummaryHeaderRepository) {
        this.materialUsageSummaryHeaderRepository = materialUsageSummaryHeaderRepository;
    }

    /**
     * {@code POST  /material-usage-summary-headers} : Create a new materialUsageSummaryHeader.
     *
     * @param materialUsageSummaryHeader the materialUsageSummaryHeader to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialUsageSummaryHeader, or with status {@code 400 (Bad Request)} if the materialUsageSummaryHeader has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/material-usage-summary-headers")
    public ResponseEntity<MaterialUsageSummaryHeader> createMaterialUsageSummaryHeader(@RequestBody MaterialUsageSummaryHeader materialUsageSummaryHeader) throws URISyntaxException {
        log.debug("REST request to save MaterialUsageSummaryHeader : {}", materialUsageSummaryHeader);
        if (materialUsageSummaryHeader.getId() != null) {
            throw new BadRequestAlertException("A new materialUsageSummaryHeader cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialUsageSummaryHeader result = materialUsageSummaryHeaderRepository.save(materialUsageSummaryHeader);
        return ResponseEntity.created(new URI("/api/material-usage-summary-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-usage-summary-headers} : Updates an existing materialUsageSummaryHeader.
     *
     * @param materialUsageSummaryHeader the materialUsageSummaryHeader to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialUsageSummaryHeader,
     * or with status {@code 400 (Bad Request)} if the materialUsageSummaryHeader is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialUsageSummaryHeader couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/material-usage-summary-headers")
    public ResponseEntity<MaterialUsageSummaryHeader> updateMaterialUsageSummaryHeader(@RequestBody MaterialUsageSummaryHeader materialUsageSummaryHeader) throws URISyntaxException {
        log.debug("REST request to update MaterialUsageSummaryHeader : {}", materialUsageSummaryHeader);
        if (materialUsageSummaryHeader.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaterialUsageSummaryHeader result = materialUsageSummaryHeaderRepository.save(materialUsageSummaryHeader);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialUsageSummaryHeader.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /material-usage-summary-headers} : get all the materialUsageSummaryHeaders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialUsageSummaryHeaders in body.
     */
    @GetMapping("/material-usage-summary-headers")
    public ResponseEntity<List<MaterialUsageSummaryHeader>> getAllMaterialUsageSummaryHeaders(Pageable pageable) {
        log.debug("REST request to get a page of MaterialUsageSummaryHeaders");
        Page<MaterialUsageSummaryHeader> page = materialUsageSummaryHeaderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /material-usage-summary-headers/:id} : get the "id" materialUsageSummaryHeader.
     *
     * @param id the id of the materialUsageSummaryHeader to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialUsageSummaryHeader, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/material-usage-summary-headers/{id}")
    public ResponseEntity<MaterialUsageSummaryHeader> getMaterialUsageSummaryHeader(@PathVariable Long id) {
        log.debug("REST request to get MaterialUsageSummaryHeader : {}", id);
        Optional<MaterialUsageSummaryHeader> materialUsageSummaryHeader = materialUsageSummaryHeaderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(materialUsageSummaryHeader);
    }

    /**
     * {@code DELETE  /material-usage-summary-headers/:id} : delete the "id" materialUsageSummaryHeader.
     *
     * @param id the id of the materialUsageSummaryHeader to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/material-usage-summary-headers/{id}")
    public ResponseEntity<Void> deleteMaterialUsageSummaryHeader(@PathVariable Long id) {
        log.debug("REST request to delete MaterialUsageSummaryHeader : {}", id);
        materialUsageSummaryHeaderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
