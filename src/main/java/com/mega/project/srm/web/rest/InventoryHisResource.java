package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.InventoryHis;
import com.mega.project.srm.repository.InventoryHisRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.InventoryHis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InventoryHisResource {

    private final Logger log = LoggerFactory.getLogger(InventoryHisResource.class);

    private static final String ENTITY_NAME = "inventoryHis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryHisRepository inventoryHisRepository;

    public InventoryHisResource(InventoryHisRepository inventoryHisRepository) {
        this.inventoryHisRepository = inventoryHisRepository;
    }

    /**
     * {@code POST  /inventory-his} : Create a new inventoryHis.
     *
     * @param inventoryHis the inventoryHis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryHis, or with status {@code 400 (Bad Request)} if the inventoryHis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-his")
    public ResponseEntity<InventoryHis> createInventoryHis(@RequestBody InventoryHis inventoryHis) throws URISyntaxException {
        log.debug("REST request to save InventoryHis : {}", inventoryHis);
        if (inventoryHis.getId() != null) {
            throw new BadRequestAlertException("A new inventoryHis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryHis result = inventoryHisRepository.save(inventoryHis);
        return ResponseEntity.created(new URI("/api/inventory-his/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-his} : Updates an existing inventoryHis.
     *
     * @param inventoryHis the inventoryHis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryHis,
     * or with status {@code 400 (Bad Request)} if the inventoryHis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryHis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-his")
    public ResponseEntity<InventoryHis> updateInventoryHis(@RequestBody InventoryHis inventoryHis) throws URISyntaxException {
        log.debug("REST request to update InventoryHis : {}", inventoryHis);
        if (inventoryHis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryHis result = inventoryHisRepository.save(inventoryHis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryHis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventory-his} : get all the inventoryHis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryHis in body.
     */
    @GetMapping("/inventory-his")
    public ResponseEntity<List<InventoryHis>> getAllInventoryHis(Pageable pageable) {
        log.debug("REST request to get a page of InventoryHis");
        Page<InventoryHis> page = inventoryHisRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-his/:id} : get the "id" inventoryHis.
     *
     * @param id the id of the inventoryHis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryHis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-his/{id}")
    public ResponseEntity<InventoryHis> getInventoryHis(@PathVariable Long id) {
        log.debug("REST request to get InventoryHis : {}", id);
        Optional<InventoryHis> inventoryHis = inventoryHisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inventoryHis);
    }

    /**
     * {@code DELETE  /inventory-his/:id} : delete the "id" inventoryHis.
     *
     * @param id the id of the inventoryHis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-his/{id}")
    public ResponseEntity<Void> deleteInventoryHis(@PathVariable Long id) {
        log.debug("REST request to delete InventoryHis : {}", id);
        inventoryHisRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
