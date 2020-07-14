package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.PoHeader;
import com.mega.project.srm.repository.PoHeaderRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.PoHeader}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PoHeaderResource {

    private final Logger log = LoggerFactory.getLogger(PoHeaderResource.class);

    private static final String ENTITY_NAME = "poHeader";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoHeaderRepository poHeaderRepository;

    public PoHeaderResource(PoHeaderRepository poHeaderRepository) {
        this.poHeaderRepository = poHeaderRepository;
    }

    /**
     * {@code POST  /po-headers} : Create a new poHeader.
     *
     * @param poHeader the poHeader to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poHeader, or with status {@code 400 (Bad Request)} if the poHeader has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/po-headers")
    public ResponseEntity<PoHeader> createPoHeader(@RequestBody PoHeader poHeader) throws URISyntaxException {
        log.debug("REST request to save PoHeader : {}", poHeader);
        if (poHeader.getId() != null) {
            throw new BadRequestAlertException("A new poHeader cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoHeader result = poHeaderRepository.save(poHeader);
        return ResponseEntity.created(new URI("/api/po-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /po-headers} : Updates an existing poHeader.
     *
     * @param poHeader the poHeader to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poHeader,
     * or with status {@code 400 (Bad Request)} if the poHeader is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poHeader couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/po-headers")
    public ResponseEntity<PoHeader> updatePoHeader(@RequestBody PoHeader poHeader) throws URISyntaxException {
        log.debug("REST request to update PoHeader : {}", poHeader);
        if (poHeader.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoHeader result = poHeaderRepository.save(poHeader);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, poHeader.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /po-headers} : get all the poHeaders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poHeaders in body.
     */
    @GetMapping("/po-headers")
    public ResponseEntity<List<PoHeader>> getAllPoHeaders(Pageable pageable) {
        log.debug("REST request to get a page of PoHeaders");
        Page<PoHeader> page = poHeaderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /po-headers/:id} : get the "id" poHeader.
     *
     * @param id the id of the poHeader to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poHeader, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/po-headers/{id}")
    public ResponseEntity<PoHeader> getPoHeader(@PathVariable Long id) {
        log.debug("REST request to get PoHeader : {}", id);
        Optional<PoHeader> poHeader = poHeaderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(poHeader);
    }

    /**
     * {@code DELETE  /po-headers/:id} : delete the "id" poHeader.
     *
     * @param id the id of the poHeader to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/po-headers/{id}")
    public ResponseEntity<Void> deletePoHeader(@PathVariable Long id) {
        log.debug("REST request to delete PoHeader : {}", id);
        poHeaderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
