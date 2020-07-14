package com.mega.project.srm.web.rest;

import com.mega.project.srm.domain.PoDetails;
import com.mega.project.srm.repository.PoDetailsRepository;
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
 * REST controller for managing {@link com.mega.project.srm.domain.PoDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PoDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PoDetailsResource.class);

    private static final String ENTITY_NAME = "poDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoDetailsRepository poDetailsRepository;

    public PoDetailsResource(PoDetailsRepository poDetailsRepository) {
        this.poDetailsRepository = poDetailsRepository;
    }

    /**
     * {@code POST  /po-details} : Create a new poDetails.
     *
     * @param poDetails the poDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poDetails, or with status {@code 400 (Bad Request)} if the poDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/po-details")
    public ResponseEntity<PoDetails> createPoDetails(@RequestBody PoDetails poDetails) throws URISyntaxException {
        log.debug("REST request to save PoDetails : {}", poDetails);
        if (poDetails.getId() != null) {
            throw new BadRequestAlertException("A new poDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoDetails result = poDetailsRepository.save(poDetails);
        return ResponseEntity.created(new URI("/api/po-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /po-details} : Updates an existing poDetails.
     *
     * @param poDetails the poDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poDetails,
     * or with status {@code 400 (Bad Request)} if the poDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/po-details")
    public ResponseEntity<PoDetails> updatePoDetails(@RequestBody PoDetails poDetails) throws URISyntaxException {
        log.debug("REST request to update PoDetails : {}", poDetails);
        if (poDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoDetails result = poDetailsRepository.save(poDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, poDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /po-details} : get all the poDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poDetails in body.
     */
    @GetMapping("/po-details")
    public ResponseEntity<List<PoDetails>> getAllPoDetails(Pageable pageable) {
        log.debug("REST request to get a page of PoDetails");
        Page<PoDetails> page = poDetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /po-details/:id} : get the "id" poDetails.
     *
     * @param id the id of the poDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/po-details/{id}")
    public ResponseEntity<PoDetails> getPoDetails(@PathVariable Long id) {
        log.debug("REST request to get PoDetails : {}", id);
        Optional<PoDetails> poDetails = poDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(poDetails);
    }

    /**
     * {@code DELETE  /po-details/:id} : delete the "id" poDetails.
     *
     * @param id the id of the poDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/po-details/{id}")
    public ResponseEntity<Void> deletePoDetails(@PathVariable Long id) {
        log.debug("REST request to delete PoDetails : {}", id);
        poDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
