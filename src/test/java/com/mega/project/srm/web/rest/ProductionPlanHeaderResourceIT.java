package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.ProductionPlanHeader;
import com.mega.project.srm.repository.ProductionPlanHeaderRepository;

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

import com.mega.project.srm.domain.enumeration.ProductionPlanState;
/**
 * Integration tests for the {@link ProductionPlanHeaderResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductionPlanHeaderResourceIT {

    private static final String DEFAULT_PP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_PP_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PP_CUST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PP_CUST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PP_CUST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PP_CUST_NAME = "BBBBBBBBBB";

    private static final ProductionPlanState DEFAULT_PP_STATE = ProductionPlanState.CALCULATING;
    private static final ProductionPlanState UPDATED_PP_STATE = ProductionPlanState.CALCULATED_DONE;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductionPlanHeaderRepository productionPlanHeaderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionPlanHeaderMockMvc;

    private ProductionPlanHeader productionPlanHeader;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlanHeader createEntity(EntityManager em) {
        ProductionPlanHeader productionPlanHeader = new ProductionPlanHeader()
            .ppCode(DEFAULT_PP_CODE)
            .ppVersion(DEFAULT_PP_VERSION)
            .ppCustCode(DEFAULT_PP_CUST_CODE)
            .ppCustName(DEFAULT_PP_CUST_NAME)
            .ppState(DEFAULT_PP_STATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productionPlanHeader;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlanHeader createUpdatedEntity(EntityManager em) {
        ProductionPlanHeader productionPlanHeader = new ProductionPlanHeader()
            .ppCode(UPDATED_PP_CODE)
            .ppVersion(UPDATED_PP_VERSION)
            .ppCustCode(UPDATED_PP_CUST_CODE)
            .ppCustName(UPDATED_PP_CUST_NAME)
            .ppState(UPDATED_PP_STATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productionPlanHeader;
    }

    @BeforeEach
    public void initTest() {
        productionPlanHeader = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductionPlanHeader() throws Exception {
        int databaseSizeBeforeCreate = productionPlanHeaderRepository.findAll().size();
        // Create the ProductionPlanHeader
        restProductionPlanHeaderMockMvc.perform(post("/api/production-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanHeader)))
            .andExpect(status().isCreated());

        // Validate the ProductionPlanHeader in the database
        List<ProductionPlanHeader> productionPlanHeaderList = productionPlanHeaderRepository.findAll();
        assertThat(productionPlanHeaderList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionPlanHeader testProductionPlanHeader = productionPlanHeaderList.get(productionPlanHeaderList.size() - 1);
        assertThat(testProductionPlanHeader.getPpCode()).isEqualTo(DEFAULT_PP_CODE);
        assertThat(testProductionPlanHeader.getPpVersion()).isEqualTo(DEFAULT_PP_VERSION);
        assertThat(testProductionPlanHeader.getPpCustCode()).isEqualTo(DEFAULT_PP_CUST_CODE);
        assertThat(testProductionPlanHeader.getPpCustName()).isEqualTo(DEFAULT_PP_CUST_NAME);
        assertThat(testProductionPlanHeader.getPpState()).isEqualTo(DEFAULT_PP_STATE);
        assertThat(testProductionPlanHeader.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductionPlanHeader.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductionPlanHeader.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductionPlanHeader.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProductionPlanHeaderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionPlanHeaderRepository.findAll().size();

        // Create the ProductionPlanHeader with an existing ID
        productionPlanHeader.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionPlanHeaderMockMvc.perform(post("/api/production-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanHeader)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlanHeader in the database
        List<ProductionPlanHeader> productionPlanHeaderList = productionPlanHeaderRepository.findAll();
        assertThat(productionPlanHeaderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductionPlanHeaders() throws Exception {
        // Initialize the database
        productionPlanHeaderRepository.saveAndFlush(productionPlanHeader);

        // Get all the productionPlanHeaderList
        restProductionPlanHeaderMockMvc.perform(get("/api/production-plan-headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionPlanHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].ppCode").value(hasItem(DEFAULT_PP_CODE)))
            .andExpect(jsonPath("$.[*].ppVersion").value(hasItem(DEFAULT_PP_VERSION)))
            .andExpect(jsonPath("$.[*].ppCustCode").value(hasItem(DEFAULT_PP_CUST_CODE)))
            .andExpect(jsonPath("$.[*].ppCustName").value(hasItem(DEFAULT_PP_CUST_NAME)))
            .andExpect(jsonPath("$.[*].ppState").value(hasItem(DEFAULT_PP_STATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductionPlanHeader() throws Exception {
        // Initialize the database
        productionPlanHeaderRepository.saveAndFlush(productionPlanHeader);

        // Get the productionPlanHeader
        restProductionPlanHeaderMockMvc.perform(get("/api/production-plan-headers/{id}", productionPlanHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionPlanHeader.getId().intValue()))
            .andExpect(jsonPath("$.ppCode").value(DEFAULT_PP_CODE))
            .andExpect(jsonPath("$.ppVersion").value(DEFAULT_PP_VERSION))
            .andExpect(jsonPath("$.ppCustCode").value(DEFAULT_PP_CUST_CODE))
            .andExpect(jsonPath("$.ppCustName").value(DEFAULT_PP_CUST_NAME))
            .andExpect(jsonPath("$.ppState").value(DEFAULT_PP_STATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProductionPlanHeader() throws Exception {
        // Get the productionPlanHeader
        restProductionPlanHeaderMockMvc.perform(get("/api/production-plan-headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductionPlanHeader() throws Exception {
        // Initialize the database
        productionPlanHeaderRepository.saveAndFlush(productionPlanHeader);

        int databaseSizeBeforeUpdate = productionPlanHeaderRepository.findAll().size();

        // Update the productionPlanHeader
        ProductionPlanHeader updatedProductionPlanHeader = productionPlanHeaderRepository.findById(productionPlanHeader.getId()).get();
        // Disconnect from session so that the updates on updatedProductionPlanHeader are not directly saved in db
        em.detach(updatedProductionPlanHeader);
        updatedProductionPlanHeader
            .ppCode(UPDATED_PP_CODE)
            .ppVersion(UPDATED_PP_VERSION)
            .ppCustCode(UPDATED_PP_CUST_CODE)
            .ppCustName(UPDATED_PP_CUST_NAME)
            .ppState(UPDATED_PP_STATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductionPlanHeaderMockMvc.perform(put("/api/production-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductionPlanHeader)))
            .andExpect(status().isOk());

        // Validate the ProductionPlanHeader in the database
        List<ProductionPlanHeader> productionPlanHeaderList = productionPlanHeaderRepository.findAll();
        assertThat(productionPlanHeaderList).hasSize(databaseSizeBeforeUpdate);
        ProductionPlanHeader testProductionPlanHeader = productionPlanHeaderList.get(productionPlanHeaderList.size() - 1);
        assertThat(testProductionPlanHeader.getPpCode()).isEqualTo(UPDATED_PP_CODE);
        assertThat(testProductionPlanHeader.getPpVersion()).isEqualTo(UPDATED_PP_VERSION);
        assertThat(testProductionPlanHeader.getPpCustCode()).isEqualTo(UPDATED_PP_CUST_CODE);
        assertThat(testProductionPlanHeader.getPpCustName()).isEqualTo(UPDATED_PP_CUST_NAME);
        assertThat(testProductionPlanHeader.getPpState()).isEqualTo(UPDATED_PP_STATE);
        assertThat(testProductionPlanHeader.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductionPlanHeader.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductionPlanHeader.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductionPlanHeader.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductionPlanHeader() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanHeaderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionPlanHeaderMockMvc.perform(put("/api/production-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanHeader)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlanHeader in the database
        List<ProductionPlanHeader> productionPlanHeaderList = productionPlanHeaderRepository.findAll();
        assertThat(productionPlanHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductionPlanHeader() throws Exception {
        // Initialize the database
        productionPlanHeaderRepository.saveAndFlush(productionPlanHeader);

        int databaseSizeBeforeDelete = productionPlanHeaderRepository.findAll().size();

        // Delete the productionPlanHeader
        restProductionPlanHeaderMockMvc.perform(delete("/api/production-plan-headers/{id}", productionPlanHeader.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionPlanHeader> productionPlanHeaderList = productionPlanHeaderRepository.findAll();
        assertThat(productionPlanHeaderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
