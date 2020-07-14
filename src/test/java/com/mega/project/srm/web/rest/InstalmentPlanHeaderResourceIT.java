package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.InstalmentPlanHeader;
import com.mega.project.srm.repository.InstalmentPlanHeaderRepository;

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

/**
 * Integration tests for the {@link InstalmentPlanHeaderResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstalmentPlanHeaderResourceIT {

    private static final String DEFAULT_VENDOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PO_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PO_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER = "AAAAAAAAAA";
    private static final String UPDATED_BUYER = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InstalmentPlanHeaderRepository instalmentPlanHeaderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstalmentPlanHeaderMockMvc;

    private InstalmentPlanHeader instalmentPlanHeader;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstalmentPlanHeader createEntity(EntityManager em) {
        InstalmentPlanHeader instalmentPlanHeader = new InstalmentPlanHeader()
            .vendorCode(DEFAULT_VENDOR_CODE)
            .vendorName(DEFAULT_VENDOR_NAME)
            .poNum(DEFAULT_PO_NUM)
            .buyer(DEFAULT_BUYER)
            .state(DEFAULT_STATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return instalmentPlanHeader;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstalmentPlanHeader createUpdatedEntity(EntityManager em) {
        InstalmentPlanHeader instalmentPlanHeader = new InstalmentPlanHeader()
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorName(UPDATED_VENDOR_NAME)
            .poNum(UPDATED_PO_NUM)
            .buyer(UPDATED_BUYER)
            .state(UPDATED_STATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return instalmentPlanHeader;
    }

    @BeforeEach
    public void initTest() {
        instalmentPlanHeader = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstalmentPlanHeader() throws Exception {
        int databaseSizeBeforeCreate = instalmentPlanHeaderRepository.findAll().size();
        // Create the InstalmentPlanHeader
        restInstalmentPlanHeaderMockMvc.perform(post("/api/instalment-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instalmentPlanHeader)))
            .andExpect(status().isCreated());

        // Validate the InstalmentPlanHeader in the database
        List<InstalmentPlanHeader> instalmentPlanHeaderList = instalmentPlanHeaderRepository.findAll();
        assertThat(instalmentPlanHeaderList).hasSize(databaseSizeBeforeCreate + 1);
        InstalmentPlanHeader testInstalmentPlanHeader = instalmentPlanHeaderList.get(instalmentPlanHeaderList.size() - 1);
        assertThat(testInstalmentPlanHeader.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testInstalmentPlanHeader.getVendorName()).isEqualTo(DEFAULT_VENDOR_NAME);
        assertThat(testInstalmentPlanHeader.getPoNum()).isEqualTo(DEFAULT_PO_NUM);
        assertThat(testInstalmentPlanHeader.getBuyer()).isEqualTo(DEFAULT_BUYER);
        assertThat(testInstalmentPlanHeader.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testInstalmentPlanHeader.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInstalmentPlanHeader.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInstalmentPlanHeader.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testInstalmentPlanHeader.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createInstalmentPlanHeaderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instalmentPlanHeaderRepository.findAll().size();

        // Create the InstalmentPlanHeader with an existing ID
        instalmentPlanHeader.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstalmentPlanHeaderMockMvc.perform(post("/api/instalment-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instalmentPlanHeader)))
            .andExpect(status().isBadRequest());

        // Validate the InstalmentPlanHeader in the database
        List<InstalmentPlanHeader> instalmentPlanHeaderList = instalmentPlanHeaderRepository.findAll();
        assertThat(instalmentPlanHeaderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstalmentPlanHeaders() throws Exception {
        // Initialize the database
        instalmentPlanHeaderRepository.saveAndFlush(instalmentPlanHeader);

        // Get all the instalmentPlanHeaderList
        restInstalmentPlanHeaderMockMvc.perform(get("/api/instalment-plan-headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instalmentPlanHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].poNum").value(hasItem(DEFAULT_PO_NUM)))
            .andExpect(jsonPath("$.[*].buyer").value(hasItem(DEFAULT_BUYER)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getInstalmentPlanHeader() throws Exception {
        // Initialize the database
        instalmentPlanHeaderRepository.saveAndFlush(instalmentPlanHeader);

        // Get the instalmentPlanHeader
        restInstalmentPlanHeaderMockMvc.perform(get("/api/instalment-plan-headers/{id}", instalmentPlanHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instalmentPlanHeader.getId().intValue()))
            .andExpect(jsonPath("$.vendorCode").value(DEFAULT_VENDOR_CODE))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME))
            .andExpect(jsonPath("$.poNum").value(DEFAULT_PO_NUM))
            .andExpect(jsonPath("$.buyer").value(DEFAULT_BUYER))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInstalmentPlanHeader() throws Exception {
        // Get the instalmentPlanHeader
        restInstalmentPlanHeaderMockMvc.perform(get("/api/instalment-plan-headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstalmentPlanHeader() throws Exception {
        // Initialize the database
        instalmentPlanHeaderRepository.saveAndFlush(instalmentPlanHeader);

        int databaseSizeBeforeUpdate = instalmentPlanHeaderRepository.findAll().size();

        // Update the instalmentPlanHeader
        InstalmentPlanHeader updatedInstalmentPlanHeader = instalmentPlanHeaderRepository.findById(instalmentPlanHeader.getId()).get();
        // Disconnect from session so that the updates on updatedInstalmentPlanHeader are not directly saved in db
        em.detach(updatedInstalmentPlanHeader);
        updatedInstalmentPlanHeader
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorName(UPDATED_VENDOR_NAME)
            .poNum(UPDATED_PO_NUM)
            .buyer(UPDATED_BUYER)
            .state(UPDATED_STATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restInstalmentPlanHeaderMockMvc.perform(put("/api/instalment-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstalmentPlanHeader)))
            .andExpect(status().isOk());

        // Validate the InstalmentPlanHeader in the database
        List<InstalmentPlanHeader> instalmentPlanHeaderList = instalmentPlanHeaderRepository.findAll();
        assertThat(instalmentPlanHeaderList).hasSize(databaseSizeBeforeUpdate);
        InstalmentPlanHeader testInstalmentPlanHeader = instalmentPlanHeaderList.get(instalmentPlanHeaderList.size() - 1);
        assertThat(testInstalmentPlanHeader.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testInstalmentPlanHeader.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testInstalmentPlanHeader.getPoNum()).isEqualTo(UPDATED_PO_NUM);
        assertThat(testInstalmentPlanHeader.getBuyer()).isEqualTo(UPDATED_BUYER);
        assertThat(testInstalmentPlanHeader.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testInstalmentPlanHeader.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInstalmentPlanHeader.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInstalmentPlanHeader.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testInstalmentPlanHeader.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInstalmentPlanHeader() throws Exception {
        int databaseSizeBeforeUpdate = instalmentPlanHeaderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstalmentPlanHeaderMockMvc.perform(put("/api/instalment-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instalmentPlanHeader)))
            .andExpect(status().isBadRequest());

        // Validate the InstalmentPlanHeader in the database
        List<InstalmentPlanHeader> instalmentPlanHeaderList = instalmentPlanHeaderRepository.findAll();
        assertThat(instalmentPlanHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstalmentPlanHeader() throws Exception {
        // Initialize the database
        instalmentPlanHeaderRepository.saveAndFlush(instalmentPlanHeader);

        int databaseSizeBeforeDelete = instalmentPlanHeaderRepository.findAll().size();

        // Delete the instalmentPlanHeader
        restInstalmentPlanHeaderMockMvc.perform(delete("/api/instalment-plan-headers/{id}", instalmentPlanHeader.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstalmentPlanHeader> instalmentPlanHeaderList = instalmentPlanHeaderRepository.findAll();
        assertThat(instalmentPlanHeaderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
