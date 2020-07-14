package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.MaterialUsage;
import com.mega.project.srm.repository.MaterialUsageRepository;

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
 * Integration tests for the {@link MaterialUsageResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MaterialUsageResourceIT {

    private static final String DEFAULT_PRODUCTS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REPLACE_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REPLACE_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BOM_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_BOM_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_BOM_NUM = "AAAAAAAAAA";
    private static final String UPDATED_BOM_NUM = "BBBBBBBBBB";

    private static final Instant DEFAULT_USAGE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_USAGE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_USAGE_QTY = 1D;
    private static final Double UPDATED_USAGE_QTY = 2D;

    private static final Double DEFAULT_REPLACE_USAGE_QTY = 1D;
    private static final Double UPDATED_REPLACE_USAGE_QTY = 2D;

    private static final Double DEFAULT_SCALE = 1D;
    private static final Double UPDATED_SCALE = 2D;

    private static final String DEFAULT_INTER_ID = "AAAAAAAAAA";
    private static final String UPDATED_INTER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MaterialUsageRepository materialUsageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialUsageMockMvc;

    private MaterialUsage materialUsage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialUsage createEntity(EntityManager em) {
        MaterialUsage materialUsage = new MaterialUsage()
            .productsCode(DEFAULT_PRODUCTS_CODE)
            .materialCode(DEFAULT_MATERIAL_CODE)
            .replaceMaterialCode(DEFAULT_REPLACE_MATERIAL_CODE)
            .bomVersion(DEFAULT_BOM_VERSION)
            .bomNum(DEFAULT_BOM_NUM)
            .usageDate(DEFAULT_USAGE_DATE)
            .usageQty(DEFAULT_USAGE_QTY)
            .replaceUsageQty(DEFAULT_REPLACE_USAGE_QTY)
            .scale(DEFAULT_SCALE)
            .interId(DEFAULT_INTER_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return materialUsage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialUsage createUpdatedEntity(EntityManager em) {
        MaterialUsage materialUsage = new MaterialUsage()
            .productsCode(UPDATED_PRODUCTS_CODE)
            .materialCode(UPDATED_MATERIAL_CODE)
            .replaceMaterialCode(UPDATED_REPLACE_MATERIAL_CODE)
            .bomVersion(UPDATED_BOM_VERSION)
            .bomNum(UPDATED_BOM_NUM)
            .usageDate(UPDATED_USAGE_DATE)
            .usageQty(UPDATED_USAGE_QTY)
            .replaceUsageQty(UPDATED_REPLACE_USAGE_QTY)
            .scale(UPDATED_SCALE)
            .interId(UPDATED_INTER_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return materialUsage;
    }

    @BeforeEach
    public void initTest() {
        materialUsage = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialUsage() throws Exception {
        int databaseSizeBeforeCreate = materialUsageRepository.findAll().size();
        // Create the MaterialUsage
        restMaterialUsageMockMvc.perform(post("/api/material-usages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialUsage)))
            .andExpect(status().isCreated());

        // Validate the MaterialUsage in the database
        List<MaterialUsage> materialUsageList = materialUsageRepository.findAll();
        assertThat(materialUsageList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialUsage testMaterialUsage = materialUsageList.get(materialUsageList.size() - 1);
        assertThat(testMaterialUsage.getProductsCode()).isEqualTo(DEFAULT_PRODUCTS_CODE);
        assertThat(testMaterialUsage.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testMaterialUsage.getReplaceMaterialCode()).isEqualTo(DEFAULT_REPLACE_MATERIAL_CODE);
        assertThat(testMaterialUsage.getBomVersion()).isEqualTo(DEFAULT_BOM_VERSION);
        assertThat(testMaterialUsage.getBomNum()).isEqualTo(DEFAULT_BOM_NUM);
        assertThat(testMaterialUsage.getUsageDate()).isEqualTo(DEFAULT_USAGE_DATE);
        assertThat(testMaterialUsage.getUsageQty()).isEqualTo(DEFAULT_USAGE_QTY);
        assertThat(testMaterialUsage.getReplaceUsageQty()).isEqualTo(DEFAULT_REPLACE_USAGE_QTY);
        assertThat(testMaterialUsage.getScale()).isEqualTo(DEFAULT_SCALE);
        assertThat(testMaterialUsage.getInterId()).isEqualTo(DEFAULT_INTER_ID);
        assertThat(testMaterialUsage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMaterialUsage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMaterialUsage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMaterialUsage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createMaterialUsageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialUsageRepository.findAll().size();

        // Create the MaterialUsage with an existing ID
        materialUsage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialUsageMockMvc.perform(post("/api/material-usages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialUsage)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialUsage in the database
        List<MaterialUsage> materialUsageList = materialUsageRepository.findAll();
        assertThat(materialUsageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMaterialUsages() throws Exception {
        // Initialize the database
        materialUsageRepository.saveAndFlush(materialUsage);

        // Get all the materialUsageList
        restMaterialUsageMockMvc.perform(get("/api/material-usages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialUsage.getId().intValue())))
            .andExpect(jsonPath("$.[*].productsCode").value(hasItem(DEFAULT_PRODUCTS_CODE)))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].replaceMaterialCode").value(hasItem(DEFAULT_REPLACE_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].bomVersion").value(hasItem(DEFAULT_BOM_VERSION)))
            .andExpect(jsonPath("$.[*].bomNum").value(hasItem(DEFAULT_BOM_NUM)))
            .andExpect(jsonPath("$.[*].usageDate").value(hasItem(DEFAULT_USAGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].usageQty").value(hasItem(DEFAULT_USAGE_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].replaceUsageQty").value(hasItem(DEFAULT_REPLACE_USAGE_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].scale").value(hasItem(DEFAULT_SCALE.doubleValue())))
            .andExpect(jsonPath("$.[*].interId").value(hasItem(DEFAULT_INTER_ID)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMaterialUsage() throws Exception {
        // Initialize the database
        materialUsageRepository.saveAndFlush(materialUsage);

        // Get the materialUsage
        restMaterialUsageMockMvc.perform(get("/api/material-usages/{id}", materialUsage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialUsage.getId().intValue()))
            .andExpect(jsonPath("$.productsCode").value(DEFAULT_PRODUCTS_CODE))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE))
            .andExpect(jsonPath("$.replaceMaterialCode").value(DEFAULT_REPLACE_MATERIAL_CODE))
            .andExpect(jsonPath("$.bomVersion").value(DEFAULT_BOM_VERSION))
            .andExpect(jsonPath("$.bomNum").value(DEFAULT_BOM_NUM))
            .andExpect(jsonPath("$.usageDate").value(DEFAULT_USAGE_DATE.toString()))
            .andExpect(jsonPath("$.usageQty").value(DEFAULT_USAGE_QTY.doubleValue()))
            .andExpect(jsonPath("$.replaceUsageQty").value(DEFAULT_REPLACE_USAGE_QTY.doubleValue()))
            .andExpect(jsonPath("$.scale").value(DEFAULT_SCALE.doubleValue()))
            .andExpect(jsonPath("$.interId").value(DEFAULT_INTER_ID))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMaterialUsage() throws Exception {
        // Get the materialUsage
        restMaterialUsageMockMvc.perform(get("/api/material-usages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialUsage() throws Exception {
        // Initialize the database
        materialUsageRepository.saveAndFlush(materialUsage);

        int databaseSizeBeforeUpdate = materialUsageRepository.findAll().size();

        // Update the materialUsage
        MaterialUsage updatedMaterialUsage = materialUsageRepository.findById(materialUsage.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialUsage are not directly saved in db
        em.detach(updatedMaterialUsage);
        updatedMaterialUsage
            .productsCode(UPDATED_PRODUCTS_CODE)
            .materialCode(UPDATED_MATERIAL_CODE)
            .replaceMaterialCode(UPDATED_REPLACE_MATERIAL_CODE)
            .bomVersion(UPDATED_BOM_VERSION)
            .bomNum(UPDATED_BOM_NUM)
            .usageDate(UPDATED_USAGE_DATE)
            .usageQty(UPDATED_USAGE_QTY)
            .replaceUsageQty(UPDATED_REPLACE_USAGE_QTY)
            .scale(UPDATED_SCALE)
            .interId(UPDATED_INTER_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restMaterialUsageMockMvc.perform(put("/api/material-usages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaterialUsage)))
            .andExpect(status().isOk());

        // Validate the MaterialUsage in the database
        List<MaterialUsage> materialUsageList = materialUsageRepository.findAll();
        assertThat(materialUsageList).hasSize(databaseSizeBeforeUpdate);
        MaterialUsage testMaterialUsage = materialUsageList.get(materialUsageList.size() - 1);
        assertThat(testMaterialUsage.getProductsCode()).isEqualTo(UPDATED_PRODUCTS_CODE);
        assertThat(testMaterialUsage.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testMaterialUsage.getReplaceMaterialCode()).isEqualTo(UPDATED_REPLACE_MATERIAL_CODE);
        assertThat(testMaterialUsage.getBomVersion()).isEqualTo(UPDATED_BOM_VERSION);
        assertThat(testMaterialUsage.getBomNum()).isEqualTo(UPDATED_BOM_NUM);
        assertThat(testMaterialUsage.getUsageDate()).isEqualTo(UPDATED_USAGE_DATE);
        assertThat(testMaterialUsage.getUsageQty()).isEqualTo(UPDATED_USAGE_QTY);
        assertThat(testMaterialUsage.getReplaceUsageQty()).isEqualTo(UPDATED_REPLACE_USAGE_QTY);
        assertThat(testMaterialUsage.getScale()).isEqualTo(UPDATED_SCALE);
        assertThat(testMaterialUsage.getInterId()).isEqualTo(UPDATED_INTER_ID);
        assertThat(testMaterialUsage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMaterialUsage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMaterialUsage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMaterialUsage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialUsage() throws Exception {
        int databaseSizeBeforeUpdate = materialUsageRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialUsageMockMvc.perform(put("/api/material-usages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialUsage)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialUsage in the database
        List<MaterialUsage> materialUsageList = materialUsageRepository.findAll();
        assertThat(materialUsageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaterialUsage() throws Exception {
        // Initialize the database
        materialUsageRepository.saveAndFlush(materialUsage);

        int databaseSizeBeforeDelete = materialUsageRepository.findAll().size();

        // Delete the materialUsage
        restMaterialUsageMockMvc.perform(delete("/api/material-usages/{id}", materialUsage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialUsage> materialUsageList = materialUsageRepository.findAll();
        assertThat(materialUsageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
