package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.ProductionPlanDetailHis;
import com.mega.project.srm.repository.ProductionPlanDetailHisRepository;

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
 * Integration tests for the {@link ProductionPlanDetailHisResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductionPlanDetailHisResourceIT {

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
    private ProductionPlanDetailHisRepository productionPlanDetailHisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionPlanDetailHisMockMvc;

    private ProductionPlanDetailHis productionPlanDetailHis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlanDetailHis createEntity(EntityManager em) {
        ProductionPlanDetailHis productionPlanDetailHis = new ProductionPlanDetailHis()
            .productsCode(DEFAULT_PRODUCTS_CODE)
            .semiProductsCode(DEFAULT_SEMI_PRODUCTS_CODE)
            .semiProQty(DEFAULT_SEMI_PRO_QTY)
            .semiProDate(DEFAULT_SEMI_PRO_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productionPlanDetailHis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlanDetailHis createUpdatedEntity(EntityManager em) {
        ProductionPlanDetailHis productionPlanDetailHis = new ProductionPlanDetailHis()
            .productsCode(UPDATED_PRODUCTS_CODE)
            .semiProductsCode(UPDATED_SEMI_PRODUCTS_CODE)
            .semiProQty(UPDATED_SEMI_PRO_QTY)
            .semiProDate(UPDATED_SEMI_PRO_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productionPlanDetailHis;
    }

    @BeforeEach
    public void initTest() {
        productionPlanDetailHis = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductionPlanDetailHis() throws Exception {
        int databaseSizeBeforeCreate = productionPlanDetailHisRepository.findAll().size();
        // Create the ProductionPlanDetailHis
        restProductionPlanDetailHisMockMvc.perform(post("/api/production-plan-detail-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanDetailHis)))
            .andExpect(status().isCreated());

        // Validate the ProductionPlanDetailHis in the database
        List<ProductionPlanDetailHis> productionPlanDetailHisList = productionPlanDetailHisRepository.findAll();
        assertThat(productionPlanDetailHisList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionPlanDetailHis testProductionPlanDetailHis = productionPlanDetailHisList.get(productionPlanDetailHisList.size() - 1);
        assertThat(testProductionPlanDetailHis.getProductsCode()).isEqualTo(DEFAULT_PRODUCTS_CODE);
        assertThat(testProductionPlanDetailHis.getSemiProductsCode()).isEqualTo(DEFAULT_SEMI_PRODUCTS_CODE);
        assertThat(testProductionPlanDetailHis.getSemiProQty()).isEqualTo(DEFAULT_SEMI_PRO_QTY);
        assertThat(testProductionPlanDetailHis.getSemiProDate()).isEqualTo(DEFAULT_SEMI_PRO_DATE);
        assertThat(testProductionPlanDetailHis.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductionPlanDetailHis.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductionPlanDetailHis.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductionPlanDetailHis.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProductionPlanDetailHisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionPlanDetailHisRepository.findAll().size();

        // Create the ProductionPlanDetailHis with an existing ID
        productionPlanDetailHis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionPlanDetailHisMockMvc.perform(post("/api/production-plan-detail-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanDetailHis)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlanDetailHis in the database
        List<ProductionPlanDetailHis> productionPlanDetailHisList = productionPlanDetailHisRepository.findAll();
        assertThat(productionPlanDetailHisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductionPlanDetailHis() throws Exception {
        // Initialize the database
        productionPlanDetailHisRepository.saveAndFlush(productionPlanDetailHis);

        // Get all the productionPlanDetailHisList
        restProductionPlanDetailHisMockMvc.perform(get("/api/production-plan-detail-his?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionPlanDetailHis.getId().intValue())))
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
    public void getProductionPlanDetailHis() throws Exception {
        // Initialize the database
        productionPlanDetailHisRepository.saveAndFlush(productionPlanDetailHis);

        // Get the productionPlanDetailHis
        restProductionPlanDetailHisMockMvc.perform(get("/api/production-plan-detail-his/{id}", productionPlanDetailHis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionPlanDetailHis.getId().intValue()))
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
    public void getNonExistingProductionPlanDetailHis() throws Exception {
        // Get the productionPlanDetailHis
        restProductionPlanDetailHisMockMvc.perform(get("/api/production-plan-detail-his/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductionPlanDetailHis() throws Exception {
        // Initialize the database
        productionPlanDetailHisRepository.saveAndFlush(productionPlanDetailHis);

        int databaseSizeBeforeUpdate = productionPlanDetailHisRepository.findAll().size();

        // Update the productionPlanDetailHis
        ProductionPlanDetailHis updatedProductionPlanDetailHis = productionPlanDetailHisRepository.findById(productionPlanDetailHis.getId()).get();
        // Disconnect from session so that the updates on updatedProductionPlanDetailHis are not directly saved in db
        em.detach(updatedProductionPlanDetailHis);
        updatedProductionPlanDetailHis
            .productsCode(UPDATED_PRODUCTS_CODE)
            .semiProductsCode(UPDATED_SEMI_PRODUCTS_CODE)
            .semiProQty(UPDATED_SEMI_PRO_QTY)
            .semiProDate(UPDATED_SEMI_PRO_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductionPlanDetailHisMockMvc.perform(put("/api/production-plan-detail-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductionPlanDetailHis)))
            .andExpect(status().isOk());

        // Validate the ProductionPlanDetailHis in the database
        List<ProductionPlanDetailHis> productionPlanDetailHisList = productionPlanDetailHisRepository.findAll();
        assertThat(productionPlanDetailHisList).hasSize(databaseSizeBeforeUpdate);
        ProductionPlanDetailHis testProductionPlanDetailHis = productionPlanDetailHisList.get(productionPlanDetailHisList.size() - 1);
        assertThat(testProductionPlanDetailHis.getProductsCode()).isEqualTo(UPDATED_PRODUCTS_CODE);
        assertThat(testProductionPlanDetailHis.getSemiProductsCode()).isEqualTo(UPDATED_SEMI_PRODUCTS_CODE);
        assertThat(testProductionPlanDetailHis.getSemiProQty()).isEqualTo(UPDATED_SEMI_PRO_QTY);
        assertThat(testProductionPlanDetailHis.getSemiProDate()).isEqualTo(UPDATED_SEMI_PRO_DATE);
        assertThat(testProductionPlanDetailHis.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductionPlanDetailHis.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductionPlanDetailHis.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductionPlanDetailHis.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductionPlanDetailHis() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanDetailHisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionPlanDetailHisMockMvc.perform(put("/api/production-plan-detail-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productionPlanDetailHis)))
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlanDetailHis in the database
        List<ProductionPlanDetailHis> productionPlanDetailHisList = productionPlanDetailHisRepository.findAll();
        assertThat(productionPlanDetailHisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductionPlanDetailHis() throws Exception {
        // Initialize the database
        productionPlanDetailHisRepository.saveAndFlush(productionPlanDetailHis);

        int databaseSizeBeforeDelete = productionPlanDetailHisRepository.findAll().size();

        // Delete the productionPlanDetailHis
        restProductionPlanDetailHisMockMvc.perform(delete("/api/production-plan-detail-his/{id}", productionPlanDetailHis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionPlanDetailHis> productionPlanDetailHisList = productionPlanDetailHisRepository.findAll();
        assertThat(productionPlanDetailHisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
