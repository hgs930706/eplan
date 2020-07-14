package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.OutStockSetup;
import com.mega.project.srm.repository.OutStockSetupRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.OutStockSetup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OutStockSetupResource {

    private final Logger log = LoggerFactory.getLogger(OutStockSetupResource.class);

    private static final String ENTITY_NAME = "outStockSetup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OutStockSetupRepository outStockSetupRepository;

    public OutStockSetupResource(OutStockSetupRepository outStockSetupRepository) {
        this.outStockSetupRepository = outStockSetupRepository;
    }

    /**
     * {@code POST  /out-stock-setups} : Create a new outStockSetup.
     *
     * @param outStockSetup the outStockSetup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outStockSetup, or with status {@code 400 (Bad Request)} if the outStockSetup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/out-stock-setups")
    public ResponseEntity<OutStockSetup> createOutStockSetup(@RequestBody OutStockSetup outStockSetup) throws URISyntaxException {
        log.debug("REST request to save OutStockSetup : {}", outStockSetup);
        if (outStockSetup.getId() != null) {
            throw new BadRequestAlertException("A new outStockSetup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OutStockSetup result = outStockSetupRepository.save(outStockSetup);
        return ResponseEntity.created(new URI("/api/out-stock-setups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /out-stock-setups} : Updates an existing outStockSetup.
     *
     * @param outStockSetup the outStockSetup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outStockSetup,
     * or with status {@code 400 (Bad Request)} if the outStockSetup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outStockSetup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/out-stock-setups")
    public ResponseEntity<OutStockSetup> updateOutStockSetup(@RequestBody OutStockSetup outStockSetup) throws URISyntaxException {
        log.debug("REST request to update OutStockSetup : {}", outStockSetup);
        if (outStockSetup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OutStockSetup result = outStockSetupRepository.save(outStockSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, outStockSetup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /out-stock-setups} : get all the outStockSetups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outStockSetups in body.
     */
    @GetMapping("/out-stock-setups")
    public ResponseEntity<List<OutStockSetup>> getAllOutStockSetups(Pageable pageable) {
        log.debug("REST request to get a page of OutStockSetups");
        Page<OutStockSetup> page = outStockSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /out-stock-setups/:id} : get the "id" outStockSetup.
     *
     * @param id the id of the outStockSetup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outStockSetup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/out-stock-setups/{id}")
    public ResponseEntity<OutStockSetup> getOutStockSetup(@PathVariable Long id) {
        log.debug("REST request to get OutStockSetup : {}", id);
        Optional<OutStockSetup> outStockSetup = outStockSetupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(outStockSetup);
    }

    /**
     * {@code DELETE  /out-stock-setups/:id} : delete the "id" outStockSetup.
     *
     * @param id the id of the outStockSetup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/out-stock-setups/{id}")
    public ResponseEntity<Void> deleteOutStockSetup(@PathVariable Long id) {
        log.debug("REST request to delete OutStockSetup : {}", id);
        outStockSetupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
