package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.PoHeader;
import com.mega.project.srm.repository.PoHeaderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mega.project.srm.domain.enumeration.PoStates;
/**
 * Integration tests for the {@link PoHeaderResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PoHeaderResourceIT {

    private static final String DEFAULT_PO_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PO_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_PO_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_PO_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PLANT = "AAAAAAAAAA";
    private static final String UPDATED_PLANT = "BBBBBBBBBB";

    private static final String DEFAULT_SHIP_TO = "AAAAAAAAAA";
    private static final String UPDATED_SHIP_TO = "BBBBBBBBBB";

    private static final PoStates DEFAULT_PO_STATUS = PoStates.CLOSE;
    private static final PoStates UPDATED_PO_STATUS = PoStates.ACCEPT;

    private static final String DEFAULT_BUYER = "AAAAAAAAAA";
    private static final String UPDATED_BUYER = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PoHeaderRepository poHeaderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPoHeaderMockMvc;

    private PoHeader poHeader;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoHeader createEntity(EntityManager em) {
        PoHeader poHeader = new PoHeader()
            .poNum(DEFAULT_PO_NUM)
            .poVersion(DEFAULT_PO_VERSION)
            .vendorCode(DEFAULT_VENDOR_CODE)
            .vendorName(DEFAULT_VENDOR_NAME)
            .plant(DEFAULT_PLANT)
            .shipTo(DEFAULT_SHIP_TO)
            .poStatus(DEFAULT_PO_STATUS)
            .buyer(DEFAULT_BUYER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return poHeader;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoHeader createUpdatedEntity(EntityManager em) {
        PoHeader poHeader = new PoHeader()
            .poNum(UPDATED_PO_NUM)
            .poVersion(UPDATED_PO_VERSION)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorName(UPDATED_VENDOR_NAME)
            .plant(UPDATED_PLANT)
            .shipTo(UPDATED_SHIP_TO)
            .poStatus(UPDATED_PO_STATUS)
            .buyer(UPDATED_BUYER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return poHeader;
    }

    @BeforeEach
    public void initTest() {
        poHeader = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoHeader() throws Exception {
        int databaseSizeBeforeCreate = poHeaderRepository.findAll().size();
        // Create the PoHeader
        restPoHeaderMockMvc.perform(post("/api/po-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poHeader)))
            .andExpect(status().isCreated());

        // Validate the PoHeader in the database
        List<PoHeader> poHeaderList = poHeaderRepository.findAll();
        assertThat(poHeaderList).hasSize(databaseSizeBeforeCreate + 1);
        PoHeader testPoHeader = poHeaderList.get(poHeaderList.size() - 1);
        assertThat(testPoHeader.getPoNum()).isEqualTo(DEFAULT_PO_NUM);
        assertThat(testPoHeader.getPoVersion()).isEqualTo(DEFAULT_PO_VERSION);
        assertThat(testPoHeader.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testPoHeader.getVendorName()).isEqualTo(DEFAULT_VENDOR_NAME);
        assertThat(testPoHeader.getPlant()).isEqualTo(DEFAULT_PLANT);
        assertThat(testPoHeader.getShipTo()).isEqualTo(DEFAULT_SHIP_TO);
        assertThat(testPoHeader.getPoStatus()).isEqualTo(DEFAULT_PO_STATUS);
        assertThat(testPoHeader.getBuyer()).isEqualTo(DEFAULT_BUYER);
        assertThat(testPoHeader.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPoHeader.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPoHeader.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPoHeader.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPoHeaderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poHeaderRepository.findAll().size();

        // Create the PoHeader with an existing ID
        poHeader.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoHeaderMockMvc.perform(post("/api/po-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poHeader)))
            .andExpect(status().isBadRequest());

        // Validate the PoHeader in the database
        List<PoHeader> poHeaderList = poHeaderRepository.findAll();
        assertThat(poHeaderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPoHeaders() throws Exception {
        // Initialize the database
        poHeaderRepository.saveAndFlush(poHeader);

        // Get all the poHeaderList
        restPoHeaderMockMvc.perform(get("/api/po-headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].poNum").value(hasItem(DEFAULT_PO_NUM)))
            .andExpect(jsonPath("$.[*].poVersion").value(hasItem(DEFAULT_PO_VERSION)))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].plant").value(hasItem(DEFAULT_PLANT)))
            .andExpect(jsonPath("$.[*].shipTo").value(hasItem(DEFAULT_SHIP_TO)))
            .andExpect(jsonPath("$.[*].poStatus").value(hasItem(DEFAULT_PO_STATUS.toString())))
            .andExpect(jsonPath("$.[*].buyer").value(hasItem(DEFAULT_BUYER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPoHeader() throws Exception {
        // Initialize the database
        poHeaderRepository.saveAndFlush(poHeader);

        // Get the poHeader
        restPoHeaderMockMvc.perform(get("/api/po-headers/{id}", poHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poHeader.getId().intValue()))
            .andExpect(jsonPath("$.poNum").value(DEFAULT_PO_NUM))
            .andExpect(jsonPath("$.poVersion").value(DEFAULT_PO_VERSION))
            .andExpect(jsonPath("$.vendorCode").value(DEFAULT_VENDOR_CODE))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME))
            .andExpect(jsonPath("$.plant").value(DEFAULT_PLANT))
            .andExpect(jsonPath("$.shipTo").value(DEFAULT_SHIP_TO))
            .andExpect(jsonPath("$.poStatus").value(DEFAULT_PO_STATUS.toString()))
            .andExpect(jsonPath("$.buyer").value(DEFAULT_BUYER))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPoHeader() throws Exception {
        // Get the poHeader
        restPoHeaderMockMvc.perform(get("/api/po-headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoHeader() throws Exception {
        // Initialize the database
        poHeaderRepository.saveAndFlush(poHeader);

        int databaseSizeBeforeUpdate = poHeaderRepository.findAll().size();

        // Update the poHeader
        PoHeader updatedPoHeader = poHeaderRepository.findById(poHeader.getId()).get();
        // Disconnect from session so that the updates on updatedPoHeader are not directly saved in db
        em.detach(updatedPoHeader);
        updatedPoHeader
            .poNum(UPDATED_PO_NUM)
            .poVersion(UPDATED_PO_VERSION)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorName(UPDATED_VENDOR_NAME)
            .plant(UPDATED_PLANT)
            .shipTo(UPDATED_SHIP_TO)
            .poStatus(UPDATED_PO_STATUS)
            .buyer(UPDATED_BUYER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPoHeaderMockMvc.perform(put("/api/po-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoHeader)))
            .andExpect(status().isOk());

        // Validate the PoHeader in the database
        List<PoHeader> poHeaderList = poHeaderRepository.findAll();
        assertThat(poHeaderList).hasSize(databaseSizeBeforeUpdate);
        PoHeader testPoHeader = poHeaderList.get(poHeaderList.size() - 1);
        assertThat(testPoHeader.getPoNum()).isEqualTo(UPDATED_PO_NUM);
        assertThat(testPoHeader.getPoVersion()).isEqualTo(UPDATED_PO_VERSION);
        assertThat(testPoHeader.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testPoHeader.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testPoHeader.getPlant()).isEqualTo(UPDATED_PLANT);
        assertThat(testPoHeader.getShipTo()).isEqualTo(UPDATED_SHIP_TO);
        assertThat(testPoHeader.getPoStatus()).isEqualTo(UPDATED_PO_STATUS);
        assertThat(testPoHeader.getBuyer()).isEqualTo(UPDATED_BUYER);
        assertThat(testPoHeader.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPoHeader.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPoHeader.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPoHeader.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPoHeader() throws Exception {
        int databaseSizeBeforeUpdate = poHeaderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoHeaderMockMvc.perform(put("/api/po-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poHeader)))
            .andExpect(status().isBadRequest());

        // Validate the PoHeader in the database
        List<PoHeader> poHeaderList = poHeaderRepository.findAll();
        assertThat(poHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoHeader() throws Exception {
        // Initialize the database
        poHeaderRepository.saveAndFlush(poHeader);

        int databaseSizeBeforeDelete = poHeaderRepository.findAll().size();

        // Delete the poHeader
        restPoHeaderMockMvc.perform(delete("/api/po-headers/{id}", poHeader.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PoHeader> poHeaderList = poHeaderRepository.findAll();
        assertThat(poHeaderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
