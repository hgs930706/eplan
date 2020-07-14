package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.MaterialUsageSummaryHeader;
import com.mega.project.srm.repository.MaterialUsageSummaryHeaderRepository;

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
 * Integration tests for the {@link MaterialUsageSummaryHeaderResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MaterialUsageSummaryHeaderResourceIT {

    private static final String DEFAULT_VENDOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_USAGE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_USAGE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_USAGE_QTY = 1;
    private static final Integer UPDATED_USAGE_QTY = 2;

    private static final String DEFAULT_SUM_ID = "AAAAAAAAAA";
    private static final String UPDATED_SUM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MaterialUsageSummaryHeaderRepository materialUsageSummaryHeaderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialUsageSummaryHeaderMockMvc;

    private MaterialUsageSummaryHeader materialUsageSummaryHeader;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialUsageSummaryHeader createEntity(EntityManager em) {
        MaterialUsageSummaryHeader materialUsageSummaryHeader = new MaterialUsageSummaryHeader()
            .vendorId(DEFAULT_VENDOR_ID)
            .materialCode(DEFAULT_MATERIAL_CODE)
            .usageDate(DEFAULT_USAGE_DATE)
            .usageQty(DEFAULT_USAGE_QTY)
            .sumId(DEFAULT_SUM_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return materialUsageSummaryHeader;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialUsageSummaryHeader createUpdatedEntity(EntityManager em) {
        MaterialUsageSummaryHeader materialUsageSummaryHeader = new MaterialUsageSummaryHeader()
            .vendorId(UPDATED_VENDOR_ID)
            .materialCode(UPDATED_MATERIAL_CODE)
            .usageDate(UPDATED_USAGE_DATE)
            .usageQty(UPDATED_USAGE_QTY)
            .sumId(UPDATED_SUM_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return materialUsageSummaryHeader;
    }

    @BeforeEach
    public void initTest() {
        materialUsageSummaryHeader = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialUsageSummaryHeader() throws Exception {
        int databaseSizeBeforeCreate = materialUsageSummaryHeaderRepository.findAll().size();
        // Create the MaterialUsageSummaryHeader
        restMaterialUsageSummaryHeaderMockMvc.perform(post("/api/material-usage-summary-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialUsageSummaryHeader)))
            .andExpect(status().isCreated());

        // Validate the MaterialUsageSummaryHeader in the database
        List<MaterialUsageSummaryHeader> materialUsageSummaryHeaderList = materialUsageSummaryHeaderRepository.findAll();
        assertThat(materialUsageSummaryHeaderList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialUsageSummaryHeader testMaterialUsageSummaryHeader = materialUsageSummaryHeaderList.get(materialUsageSummaryHeaderList.size() - 1);
        assertThat(testMaterialUsageSummaryHeader.getVendorId()).isEqualTo(DEFAULT_VENDOR_ID);
        assertThat(testMaterialUsageSummaryHeader.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testMaterialUsageSummaryHeader.getUsageDate()).isEqualTo(DEFAULT_USAGE_DATE);
        assertThat(testMaterialUsageSummaryHeader.getUsageQty()).isEqualTo(DEFAULT_USAGE_QTY);
        assertThat(testMaterialUsageSummaryHeader.getSumId()).isEqualTo(DEFAULT_SUM_ID);
        assertThat(testMaterialUsageSummaryHeader.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMaterialUsageSummaryHeader.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMaterialUsageSummaryHeader.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMaterialUsageSummaryHeader.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createMaterialUsageSummaryHeaderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialUsageSummaryHeaderRepository.findAll().size();

        // Create the MaterialUsageSummaryHeader with an existing ID
        materialUsageSummaryHeader.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialUsageSummaryHeaderMockMvc.perform(post("/api/material-usage-summary-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialUsageSummaryHeader)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialUsageSummaryHeader in the database
        List<MaterialUsageSummaryHeader> materialUsageSummaryHeaderList = materialUsageSummaryHeaderRepository.findAll();
        assertThat(materialUsageSummaryHeaderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMaterialUsageSummaryHeaders() throws Exception {
        // Initialize the database
        materialUsageSummaryHeaderRepository.saveAndFlush(materialUsageSummaryHeader);

        // Get all the materialUsageSummaryHeaderList
        restMaterialUsageSummaryHeaderMockMvc.perform(get("/api/material-usage-summary-headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialUsageSummaryHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID)))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].usageDate").value(hasItem(DEFAULT_USAGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].usageQty").value(hasItem(DEFAULT_USAGE_QTY)))
            .andExpect(jsonPath("$.[*].sumId").value(hasItem(DEFAULT_SUM_ID)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMaterialUsageSummaryHeader() throws Exception {
        // Initialize the database
        materialUsageSummaryHeaderRepository.saveAndFlush(materialUsageSummaryHeader);

        // Get the materialUsageSummaryHeader
        restMaterialUsageSummaryHeaderMockMvc.perform(get("/api/material-usage-summary-headers/{id}", materialUsageSummaryHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialUsageSummaryHeader.getId().intValue()))
            .andExpect(jsonPath("$.vendorId").value(DEFAULT_VENDOR_ID))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE))
            .andExpect(jsonPath("$.usageDate").value(DEFAULT_USAGE_DATE.toString()))
            .andExpect(jsonPath("$.usageQty").value(DEFAULT_USAGE_QTY))
            .andExpect(jsonPath("$.sumId").value(DEFAULT_SUM_ID))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMaterialUsageSummaryHeader() throws Exception {
        // Get the materialUsageSummaryHeader
        restMaterialUsageSummaryHeaderMockMvc.perform(get("/api/material-usage-summary-headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialUsageSummaryHeader() throws Exception {
        // Initialize the database
        materialUsageSummaryHeaderRepository.saveAndFlush(materialUsageSummaryHeader);

        int databaseSizeBeforeUpdate = materialUsageSummaryHeaderRepository.findAll().size();

        // Update the materialUsageSummaryHeader
        MaterialUsageSummaryHeader updatedMaterialUsageSummaryHeader = materialUsageSummaryHeaderRepository.findById(materialUsageSummaryHeader.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialUsageSummaryHeader are not directly saved in db
        em.detach(updatedMaterialUsageSummaryHeader);
        updatedMaterialUsageSummaryHeader
            .vendorId(UPDATED_VENDOR_ID)
            .materialCode(UPDATED_MATERIAL_CODE)
            .usageDate(UPDATED_USAGE_DATE)
            .usageQty(UPDATED_USAGE_QTY)
            .sumId(UPDATED_SUM_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restMaterialUsageSummaryHeaderMockMvc.perform(put("/api/material-usage-summary-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaterialUsageSummaryHeader)))
            .andExpect(status().isOk());

        // Validate the MaterialUsageSummaryHeader in the database
        List<MaterialUsageSummaryHeader> materialUsageSummaryHeaderList = materialUsageSummaryHeaderRepository.findAll();
        assertThat(materialUsageSummaryHeaderList).hasSize(databaseSizeBeforeUpdate);
        MaterialUsageSummaryHeader testMaterialUsageSummaryHeader = materialUsageSummaryHeaderList.get(materialUsageSummaryHeaderList.size() - 1);
        assertThat(testMaterialUsageSummaryHeader.getVendorId()).isEqualTo(UPDATED_VENDOR_ID);
        assertThat(testMaterialUsageSummaryHeader.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testMaterialUsageSummaryHeader.getUsageDate()).isEqualTo(UPDATED_USAGE_DATE);
        assertThat(testMaterialUsageSummaryHeader.getUsageQty()).isEqualTo(UPDATED_USAGE_QTY);
        assertThat(testMaterialUsageSummaryHeader.getSumId()).isEqualTo(UPDATED_SUM_ID);
        assertThat(testMaterialUsageSummaryHeader.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMaterialUsageSummaryHeader.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMaterialUsageSummaryHeader.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMaterialUsageSummaryHeader.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialUsageSummaryHeader() throws Exception {
        int databaseSizeBeforeUpdate = materialUsageSummaryHeaderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialUsageSummaryHeaderMockMvc.perform(put("/api/material-usage-summary-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialUsageSummaryHeader)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialUsageSummaryHeader in the database
        List<MaterialUsageSummaryHeader> materialUsageSummaryHeaderList = materialUsageSummaryHeaderRepository.findAll();
        assertThat(materialUsageSummaryHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaterialUsageSummaryHeader() throws Exception {
        // Initialize the database
        materialUsageSummaryHeaderRepository.saveAndFlush(materialUsageSummaryHeader);

        int databaseSizeBeforeDelete = materialUsageSummaryHeaderRepository.findAll().size();

        // Delete the materialUsageSummaryHeader
        restMaterialUsageSummaryHeaderMockMvc.perform(delete("/api/material-usage-summary-headers/{id}", materialUsageSummaryHeader.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialUsageSummaryHeader> materialUsageSummaryHeaderList = materialUsageSummaryHeaderRepository.findAll();
        assertThat(materialUsageSummaryHeaderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
