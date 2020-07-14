package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.ProductionPlanDetails;
import com.mega.project.srm.repository.ProductionPlanDetailsRepository;

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
 * Integration tests for the {@link ProductionPlanDetailsResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductionPlanDetailsResourceIT {

    private static final String DEFAULT_PRODUCTS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SEMI_PRODUCTS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SEMI_PRODUCTS_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEMI_PRO_QTY = 1;
    private static final Integer UPDATED_SEMI_PRO_QTY = 2;

    private static final Instant DEFAULT_SEMI_PRO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SEMI_PRO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductionPlanDetailsRepository productionPlanDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionPlanDetailsMockMvc;

    private ProductionPlanDetails productionPlanDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlanDetails createEntity(EntityManager em) {
        ProductionPlanDetails productionPlanDetails = new ProductionPlanDetails()
            .productsCode(DEFAULT_PRODUCTS_CODE)
            .semiProductsCode(DEFAULT_SEMI_PRODUCTS_CODE)
            .semiProQty(DEFAULT_SEMI_PRO_QTY)
            .semiProDate(DEFAULT_SEMI_PRO_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productionPlanDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlanDetails createUpdatedEntity(EntityManager em) {
        ProductionPlanDetails productionPlanDetails = new ProductionPlanDetails()
            .productsCode(UPDATED_PRODUCTS_CODE)
            .semiProductsCode(UPDATED_SEMI_PRODUCTS_CODE)
            .semiProQty(UPDATED_SEMI_PRO_QTY)
            .semiProDate(UPDATED_SEMI_PRO_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productionPlanDetails;
    }

    @BeforeEach
    public void initTest() {
        productionPlanDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductionPlanDetails() throws Exception {
        int databaseSizeBeforeCreate = productionPlanDetailsRepository.findAll().size();
        // Create the ProductionPlanDetails
        restProductionPlanDetailsMockMvc.perform(post("/api/production-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanDetails)))
            .andExpect(status().isCreated());

        // Validate the ProductionPlanDetails in the database
        List<ProductionPlanDetails> productionPlanDetailsList = productionPlanDetailsRepository.findAll();
        assertThat(productionPlanDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionPlanDetails testProductionPlanDetails = productionPlanDetailsList.get(productionPlanDetailsList.size() - 1);
        assertThat(testProductionPlanDetails.getProductsCode()).isEqualTo(DEFAULT_PRODUCTS_CODE);
        assertThat(testProductionPlanDetails.getSemiProductsCode()).isEqualTo(DEFAULT_SEMI_PRODUCTS_CODE);
        assertThat(testProductionPlanDetails.getSemiProQty()).isEqualTo(DEFAULT_SEMI_PRO_QTY);
        assertThat(testProductionPlanDetails.getSemiProDate()).isEqualTo(DEFAULT_SEMI_PRO_DATE);
        assertThat(testProductionPlanDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductionPlanDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductionPlanDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductionPlanDetails.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProductionPlanDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionPlanDetailsRepository.findAll().size();

        // Create the ProductionPlanDetails with an existing ID
        productionPlanDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionPlanDetailsMockMvc.perform(post("/api/production-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlanDetails in the database
        List<ProductionPlanDetails> productionPlanDetailsList = productionPlanDetailsRepository.findAll();
        assertThat(productionPlanDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductionPlanDetails() throws Exception {
        // Initialize the database
        productionPlanDetailsRepository.saveAndFlush(productionPlanDetails);

        // Get all the productionPlanDetailsList
        restProductionPlanDetailsMockMvc.perform(get("/api/production-plan-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionPlanDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].productsCode").value(hasItem(DEFAULT_PRODUCTS_CODE)))
            .andExpect(jsonPath("$.[*].semiProductsCode").value(hasItem(DEFAULT_SEMI_PRODUCTS_CODE)))
            .andExpect(jsonPath("$.[*].semiProQty").value(hasItem(DEFAULT_SEMI_PRO_QTY)))
            .andExpect(jsonPath("$.[*].semiProDate").value(hasItem(DEFAULT_SEMI_PRO_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductionPlanDetails() throws Exception {
        // Initialize the database
        productionPlanDetailsRepository.saveAndFlush(productionPlanDetails);

        // Get the productionPlanDetails
        restProductionPlanDetailsMockMvc.perform(get("/api/production-plan-details/{id}", productionPlanDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionPlanDetails.getId().intValue()))
            .andExpect(jsonPath("$.productsCode").value(DEFAULT_PRODUCTS_CODE))
            .andExpect(jsonPath("$.semiProductsCode").value(DEFAULT_SEMI_PRODUCTS_CODE))
            .andExpect(jsonPath("$.semiProQty").value(DEFAULT_SEMI_PRO_QTY))
            .andExpect(jsonPath("$.semiProDate").value(DEFAULT_SEMI_PRO_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProductionPlanDetails() throws Exception {
        // Get the productionPlanDetails
        restProductionPlanDetailsMockMvc.perform(get("/api/production-plan-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductionPlanDetails() throws Exception {
        // Initialize the database
        productionPlanDetailsRepository.saveAndFlush(productionPlanDetails);

        int databaseSizeBeforeUpdate = productionPlanDetailsRepository.findAll().size();

        // Update the productionPlanDetails
        ProductionPlanDetails updatedProductionPlanDetails = productionPlanDetailsRepository.findById(productionPlanDetails.getId()).get();
        // Disconnect from session so that the updates on updatedProductionPlanDetails are not directly saved in db
        em.detach(updatedProductionPlanDetails);
        updatedProductionPlanDetails
            .productsCode(UPDATED_PRODUCTS_CODE)
            .semiProductsCode(UPDATED_SEMI_PRODUCTS_CODE)
            .semiProQty(UPDATED_SEMI_PRO_QTY)
            .semiProDate(UPDATED_SEMI_PRO_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductionPlanDetailsMockMvc.perform(put("/api/production-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductionPlanDetails)))
            .andExpect(status().isOk());

        // Validate the ProductionPlanDetails in the database
        List<ProductionPlanDetails> productionPlanDetailsList = productionPlanDetailsRepository.findAll();
        assertThat(productionPlanDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductionPlanDetails testProductionPlanDetails = productionPlanDetailsList.get(productionPlanDetailsList.size() - 1);
        assertThat(testProductionPlanDetails.getProductsCode()).isEqualTo(UPDATED_PRODUCTS_CODE);
        assertThat(testProductionPlanDetails.getSemiProductsCode()).isEqualTo(UPDATED_SEMI_PRODUCTS_CODE);
        assertThat(testProductionPlanDetails.getSemiProQty()).isEqualTo(UPDATED_SEMI_PRO_QTY);
        assertThat(testProductionPlanDetails.getSemiProDate()).isEqualTo(UPDATED_SEMI_PRO_DATE);
        assertThat(testProductionPlanDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductionPlanDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductionPlanDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductionPlanDetails.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductionPlanDetails() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionPlanDetailsMockMvc.perform(put("/api/production-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlanDetails in the database
        List<ProductionPlanDetails> productionPlanDetailsList = productionPlanDetailsRepository.findAll();
        assertThat(productionPlanDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductionPlanDetails() throws Exception {
        // Initialize the database
        productionPlanDetailsRepository.saveAndFlush(productionPlanDetails);

        int databaseSizeBeforeDelete = productionPlanDetailsRepository.findAll().size();

        // Delete the productionPlanDetails
        restProductionPlanDetailsMockMvc.perform(delete("/api/production-plan-details/{id}", productionPlanDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionPlanDetails> productionPlanDetailsList = productionPlanDetailsRepository.findAll();
        assertThat(productionPlanDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
