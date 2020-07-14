package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.ProductionPlanHeaderHis;
import com.mega.project.srm.repository.ProductionPlanHeaderHisRepository;

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
 * Integration tests for the {@link ProductionPlanHeaderHisResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductionPlanHeaderHisResourceIT {

    private static final String DEFAULT_PP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_PP_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PP_CUST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PP_CUST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_CUST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PP_CUST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PP_STATE = "AAAAAAAAAA";
    private static final String UPDATED_PP_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductionPlanHeaderHisRepository productionPlanHeaderHisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionPlanHeaderHisMockMvc;

    private ProductionPlanHeaderHis productionPlanHeaderHis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlanHeaderHis createEntity(EntityManager em) {
        ProductionPlanHeaderHis productionPlanHeaderHis = new ProductionPlanHeaderHis()
            .ppCode(DEFAULT_PP_CODE)
            .ppVersion(DEFAULT_PP_VERSION)
            .ppCustCode(DEFAULT_PP_CUST_CODE)
            .ppCustName(DEFAULT_PP_CUST_NAME)
            .ppState(DEFAULT_PP_STATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productionPlanHeaderHis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlanHeaderHis createUpdatedEntity(EntityManager em) {
        ProductionPlanHeaderHis productionPlanHeaderHis = new ProductionPlanHeaderHis()
            .ppCode(UPDATED_PP_CODE)
            .ppVersion(UPDATED_PP_VERSION)
            .ppCustCode(UPDATED_PP_CUST_CODE)
            .ppCustName(UPDATED_PP_CUST_NAME)
            .ppState(UPDATED_PP_STATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productionPlanHeaderHis;
    }

    @BeforeEach
    public void initTest() {
        productionPlanHeaderHis = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductionPlanHeaderHis() throws Exception {
        int databaseSizeBeforeCreate = productionPlanHeaderHisRepository.findAll().size();
        // Create the ProductionPlanHeaderHis
        restProductionPlanHeaderHisMockMvc.perform(post("/api/production-plan-header-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanHeaderHis)))
            .andExpect(status().isCreated());

        // Validate the ProductionPlanHeaderHis in the database
        List<ProductionPlanHeaderHis> productionPlanHeaderHisList = productionPlanHeaderHisRepository.findAll();
        assertThat(productionPlanHeaderHisList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionPlanHeaderHis testProductionPlanHeaderHis = productionPlanHeaderHisList.get(productionPlanHeaderHisList.size() - 1);
        assertThat(testProductionPlanHeaderHis.getPpCode()).isEqualTo(DEFAULT_PP_CODE);
        assertThat(testProductionPlanHeaderHis.getPpVersion()).isEqualTo(DEFAULT_PP_VERSION);
        assertThat(testProductionPlanHeaderHis.getPpCustCode()).isEqualTo(DEFAULT_PP_CUST_CODE);
        assertThat(testProductionPlanHeaderHis.getPpCustName()).isEqualTo(DEFAULT_PP_CUST_NAME);
        assertThat(testProductionPlanHeaderHis.getPpState()).isEqualTo(DEFAULT_PP_STATE);
        assertThat(testProductionPlanHeaderHis.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductionPlanHeaderHis.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductionPlanHeaderHis.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductionPlanHeaderHis.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProductionPlanHeaderHisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionPlanHeaderHisRepository.findAll().size();

        // Create the ProductionPlanHeaderHis with an existing ID
        productionPlanHeaderHis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionPlanHeaderHisMockMvc.perform(post("/api/production-plan-header-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanHeaderHis)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlanHeaderHis in the database
        List<ProductionPlanHeaderHis> productionPlanHeaderHisList = productionPlanHeaderHisRepository.findAll();
        assertThat(productionPlanHeaderHisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductionPlanHeaderHis() throws Exception {
        // Initialize the database
        productionPlanHeaderHisRepository.saveAndFlush(productionPlanHeaderHis);

        // Get all the productionPlanHeaderHisList
        restProductionPlanHeaderHisMockMvc.perform(get("/api/production-plan-header-his?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionPlanHeaderHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].ppCode").value(hasItem(DEFAULT_PP_CODE)))
            .andExpect(jsonPath("$.[*].ppVersion").value(hasItem(DEFAULT_PP_VERSION)))
            .andExpect(jsonPath("$.[*].ppCustCode").value(hasItem(DEFAULT_PP_CUST_CODE)))
            .andExpect(jsonPath("$.[*].ppCustName").value(hasItem(DEFAULT_PP_CUST_NAME)))
            .andExpect(jsonPath("$.[*].ppState").value(hasItem(DEFAULT_PP_STATE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductionPlanHeaderHis() throws Exception {
        // Initialize the database
        productionPlanHeaderHisRepository.saveAndFlush(productionPlanHeaderHis);

        // Get the productionPlanHeaderHis
        restProductionPlanHeaderHisMockMvc.perform(get("/api/production-plan-header-his/{id}", productionPlanHeaderHis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionPlanHeaderHis.getId().intValue()))
            .andExpect(jsonPath("$.ppCode").value(DEFAULT_PP_CODE))
            .andExpect(jsonPath("$.ppVersion").value(DEFAULT_PP_VERSION))
            .andExpect(jsonPath("$.ppCustCode").value(DEFAULT_PP_CUST_CODE))
            .andExpect(jsonPath("$.ppCustName").value(DEFAULT_PP_CUST_NAME))
            .andExpect(jsonPath("$.ppState").value(DEFAULT_PP_STATE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProductionPlanHeaderHis() throws Exception {
        // Get the productionPlanHeaderHis
        restProductionPlanHeaderHisMockMvc.perform(get("/api/production-plan-header-his/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductionPlanHeaderHis() throws Exception {
        // Initialize the database
        productionPlanHeaderHisRepository.saveAndFlush(productionPlanHeaderHis);

        int databaseSizeBeforeUpdate = productionPlanHeaderHisRepository.findAll().size();

        // Update the productionPlanHeaderHis
        ProductionPlanHeaderHis updatedProductionPlanHeaderHis = productionPlanHeaderHisRepository.findById(productionPlanHeaderHis.getId()).get();
        // Disconnect from session so that the updates on updatedProductionPlanHeaderHis are not directly saved in db
        em.detach(updatedProductionPlanHeaderHis);
        updatedProductionPlanHeaderHis
            .ppCode(UPDATED_PP_CODE)
            .ppVersion(UPDATED_PP_VERSION)
            .ppCustCode(UPDATED_PP_CUST_CODE)
            .ppCustName(UPDATED_PP_CUST_NAME)
            .ppState(UPDATED_PP_STATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductionPlanHeaderHisMockMvc.perform(put("/api/production-plan-header-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductionPlanHeaderHis)))
            .andExpect(status().isOk());

        // Validate the ProductionPlanHeaderHis in the database
        List<ProductionPlanHeaderHis> productionPlanHeaderHisList = productionPlanHeaderHisRepository.findAll();
        assertThat(productionPlanHeaderHisList).hasSize(databaseSizeBeforeUpdate);
        ProductionPlanHeaderHis testProductionPlanHeaderHis = productionPlanHeaderHisList.get(productionPlanHeaderHisList.size() - 1);
        assertThat(testProductionPlanHeaderHis.getPpCode()).isEqualTo(UPDATED_PP_CODE);
        assertThat(testProductionPlanHeaderHis.getPpVersion()).isEqualTo(UPDATED_PP_VERSION);
        assertThat(testProductionPlanHeaderHis.getPpCustCode()).isEqualTo(UPDATED_PP_CUST_CODE);
        assertThat(testProductionPlanHeaderHis.getPpCustName()).isEqualTo(UPDATED_PP_CUST_NAME);
        assertThat(testProductionPlanHeaderHis.getPpState()).isEqualTo(UPDATED_PP_STATE);
        assertThat(testProductionPlanHeaderHis.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductionPlanHeaderHis.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductionPlanHeaderHis.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductionPlanHeaderHis.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductionPlanHeaderHis() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanHeaderHisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionPlanHeaderHisMockMvc.perform(put("/api/production-plan-header-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanHeaderHis)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlanHeaderHis in the database
        List<ProductionPlanHeaderHis> productionPlanHeaderHisList = productionPlanHeaderHisRepository.findAll();
        assertThat(productionPlanHeaderHisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductionPlanHeaderHis() throws Exception {
        // Initialize the database
        productionPlanHeaderHisRepository.saveAndFlush(productionPlanHeaderHis);

        int databaseSizeBeforeDelete = productionPlanHeaderHisRepository.findAll().size();

        // Delete the productionPlanHeaderHis
        restProductionPlanHeaderHisMockMvc.perform(delete("/api/production-plan-header-his/{id}", productionPlanHeaderHis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionPlanHeaderHis> productionPlanHeaderHisList = productionPlanHeaderHisRepository.findAll();
        assertThat(productionPlanHeaderHisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
