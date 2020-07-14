package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.MaterialUsage;
import com.mega.project.srm.repository.MaterialUsageRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.MaterialUsage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MaterialUsageResource {

    private final Logger log = LoggerFactory.getLogger(MaterialUsageResource.class);

    private static final String ENTITY_NAME = "materialUsage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialUsageRepository materialUsageRepository;

    public MaterialUsageResource(MaterialUsageRepository materialUsageRepository) {
        this.materialUsageRepository = materialUsageRepository;
    }

    /**
     * {@code POST  /material-usages} : Create a new materialUsage.
     *
     * @param materialUsage the materialUsage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialUsage, or with status {@code 400 (Bad Request)} if the materialUsage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/material-usages")
    public ResponseEntity<MaterialUsage> createMaterialUsage(@RequestBody MaterialUsage materialUsage) throws URISyntaxException {
        log.debug("REST request to save MaterialUsage : {}", materialUsage);
        if (materialUsage.getId() != null) {
            throw new BadRequestAlertException("A new materialUsage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialUsage result = materialUsageRepository.save(materialUsage);
        return ResponseEntity.created(new URI("/api/material-usages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-usages} : Updates an existing materialUsage.
     *
     * @param materialUsage the materialUsage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialUsage,
     * or with status {@code 400 (Bad Request)} if the materialUsage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialUsage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/material-usages")
    public ResponseEntity<MaterialUsage> updateMaterialUsage(@RequestBody MaterialUsage materialUsage) throws URISyntaxException {
        log.debug("REST request to update MaterialUsage : {}", materialUsage);
        if (materialUsage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaterialUsage result = materialUsageRepository.save(materialUsage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialUsage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /material-usages} : get all the materialUsages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialUsages in body.
     */
    @GetMapping("/material-usages")
    public ResponseEntity<List<MaterialUsage>> getAllMaterialUsages(Pageable pageable) {
        log.debug("REST request to get a page of MaterialUsages");
        Page<MaterialUsage> page = materialUsageRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /material-usages/:id} : get the "id" materialUsage.
     *
     * @param id the id of the materialUsage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialUsage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/material-usages/{id}")
    public ResponseEntity<MaterialUsage> getMaterialUsage(@PathVariable Long id) {
        log.debug("REST request to get MaterialUsage : {}", id);
        Optional<MaterialUsage> materialUsage = materialUsageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(materialUsage);
    }

    /**
     * {@code DELETE  /material-usages/:id} : delete the "id" materialUsage.
     *
     * @param id the id of the materialUsage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/material-usages/{id}")
    public ResponseEntity<Void> deleteMaterialUsage(@PathVariable Long id) {
        log.debug("REST request to delete MaterialUsage : {}", id);
        materialUsageRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
