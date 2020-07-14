package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.PurchasePlanDetails;
import com.mega.project.srm.repository.PurchasePlanDetailsRepository;

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

import com.mega.project.srm.domain.enumeration.PurchasePlanNumClass;
/**
 * Integration tests for the {@link PurchasePlanDetailsResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PurchasePlanDetailsResourceIT {

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPTO = "AAAAAAAAAA";
    private static final String UPDATED_SHIPTO = "BBBBBBBBBB";

    private static final PurchasePlanNumClass DEFAULT_NUM_CLASS = PurchasePlanNumClass.PLAN;
    private static final PurchasePlanNumClass UPDATED_NUM_CLASS = PurchasePlanNumClass.CONFIRM;

    private static final Instant DEFAULT_PUR_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUR_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PUR_QTY = 1D;
    private static final Double UPDATED_PUR_QTY = 2D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PurchasePlanDetailsRepository purchasePlanDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchasePlanDetailsMockMvc;

    private PurchasePlanDetails purchasePlanDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasePlanDetails createEntity(EntityManager em) {
        PurchasePlanDetails purchasePlanDetails = new PurchasePlanDetails()
            .materialCode(DEFAULT_MATERIAL_CODE)
            .materialName(DEFAULT_MATERIAL_NAME)
            .shipto(DEFAULT_SHIPTO)
            .numClass(DEFAULT_NUM_CLASS)
            .purDate(DEFAULT_PUR_DATE)
            .purQty(DEFAULT_PUR_QTY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return purchasePlanDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasePlanDetails createUpdatedEntity(EntityManager em) {
        PurchasePlanDetails purchasePlanDetails = new PurchasePlanDetails()
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialName(UPDATED_MATERIAL_NAME)
            .shipto(UPDATED_SHIPTO)
            .numClass(UPDATED_NUM_CLASS)
            .purDate(UPDATED_PUR_DATE)
            .purQty(UPDATED_PUR_QTY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return purchasePlanDetails;
    }

    @BeforeEach
    public void initTest() {
        purchasePlanDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchasePlanDetails() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanDetailsRepository.findAll().size();
        // Create the PurchasePlanDetails
        restPurchasePlanDetailsMockMvc.perform(post("/api/purchase-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanDetails)))
            .andExpect(status().isCreated());

        // Validate the PurchasePlanDetails in the database
        List<PurchasePlanDetails> purchasePlanDetailsList = purchasePlanDetailsRepository.findAll();
        assertThat(purchasePlanDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PurchasePlanDetails testPurchasePlanDetails = purchasePlanDetailsList.get(purchasePlanDetailsList.size() - 1);
        assertThat(testPurchasePlanDetails.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testPurchasePlanDetails.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
        assertThat(testPurchasePlanDetails.getShipto()).isEqualTo(DEFAULT_SHIPTO);
        assertThat(testPurchasePlanDetails.getNumClass()).isEqualTo(DEFAULT_NUM_CLASS);
        assertThat(testPurchasePlanDetails.getPurDate()).isEqualTo(DEFAULT_PUR_DATE);
        assertThat(testPurchasePlanDetails.getPurQty()).isEqualTo(DEFAULT_PUR_QTY);
        assertThat(testPurchasePlanDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPurchasePlanDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPurchasePlanDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchasePlanDetails.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPurchasePlanDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanDetailsRepository.findAll().size();

        // Create the PurchasePlanDetails with an existing ID
        purchasePlanDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchasePlanDetailsMockMvc.perform(post("/api/purchase-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlanDetails in the database
        List<PurchasePlanDetails> purchasePlanDetailsList = purchasePlanDetailsRepository.findAll();
        assertThat(purchasePlanDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurchasePlanDetails() throws Exception {
        // Initialize the database
        purchasePlanDetailsRepository.saveAndFlush(purchasePlanDetails);

        // Get all the purchasePlanDetailsList
        restPurchasePlanDetailsMockMvc.perform(get("/api/purchase-plan-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchasePlanDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].shipto").value(hasItem(DEFAULT_SHIPTO)))
            .andExpect(jsonPath("$.[*].numClass").value(hasItem(DEFAULT_NUM_CLASS.toString())))
            .andExpect(jsonPath("$.[*].purDate").value(hasItem(DEFAULT_PUR_DATE.toString())))
            .andExpect(jsonPath("$.[*].purQty").value(hasItem(DEFAULT_PUR_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchasePlanDetails() throws Exception {
        // Initialize the database
        purchasePlanDetailsRepository.saveAndFlush(purchasePlanDetails);

        // Get the purchasePlanDetails
        restPurchasePlanDetailsMockMvc.perform(get("/api/purchase-plan-details/{id}", purchasePlanDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchasePlanDetails.getId().intValue()))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME))
            .andExpect(jsonPath("$.shipto").value(DEFAULT_SHIPTO))
            .andExpect(jsonPath("$.numClass").value(DEFAULT_NUM_CLASS.toString()))
            .andExpect(jsonPath("$.purDate").value(DEFAULT_PUR_DATE.toString()))
            .andExpect(jsonPath("$.purQty").value(DEFAULT_PUR_QTY.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPurchasePlanDetails() throws Exception {
        // Get the purchasePlanDetails
        restPurchasePlanDetailsMockMvc.perform(get("/api/purchase-plan-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchasePlanDetails() throws Exception {
        // Initialize the database
        purchasePlanDetailsRepository.saveAndFlush(purchasePlanDetails);

        int databaseSizeBeforeUpdate = purchasePlanDetailsRepository.findAll().size();

        // Update the purchasePlanDetails
        PurchasePlanDetails updatedPurchasePlanDetails = purchasePlanDetailsRepository.findById(purchasePlanDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPurchasePlanDetails are not directly saved in db
        em.detach(updatedPurchasePlanDetails);
        updatedPurchasePlanDetails
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialName(UPDATED_MATERIAL_NAME)
            .shipto(UPDATED_SHIPTO)
            .numClass(UPDATED_NUM_CLASS)
            .purDate(UPDATED_PUR_DATE)
            .purQty(UPDATED_PUR_QTY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPurchasePlanDetailsMockMvc.perform(put("/api/purchase-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchasePlanDetails)))
            .andExpect(status().isOk());

        // Validate the PurchasePlanDetails in the database
        List<PurchasePlanDetails> purchasePlanDetailsList = purchasePlanDetailsRepository.findAll();
        assertThat(purchasePlanDetailsList).hasSize(databaseSizeBeforeUpdate);
        PurchasePlanDetails testPurchasePlanDetails = purchasePlanDetailsList.get(purchasePlanDetailsList.size() - 1);
        assertThat(testPurchasePlanDetails.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testPurchasePlanDetails.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
        assertThat(testPurchasePlanDetails.getShipto()).isEqualTo(UPDATED_SHIPTO);
        assertThat(testPurchasePlanDetails.getNumClass()).isEqualTo(UPDATED_NUM_CLASS);
        assertThat(testPurchasePlanDetails.getPurDate()).isEqualTo(UPDATED_PUR_DATE);
        assertThat(testPurchasePlanDetails.getPurQty()).isEqualTo(UPDATED_PUR_QTY);
        assertThat(testPurchasePlanDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPurchasePlanDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPurchasePlanDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchasePlanDetails.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchasePlanDetails() throws Exception {
        int databaseSizeBeforeUpdate = purchasePlanDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchasePlanDetailsMockMvc.perform(put("/api/purchase-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlanDetails in the database
        List<PurchasePlanDetails> purchasePlanDetailsList = purchasePlanDetailsRepository.findAll();
        assertThat(purchasePlanDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchasePlanDetails() throws Exception {
        // Initialize the database
        purchasePlanDetailsRepository.saveAndFlush(purchasePlanDetails);

        int databaseSizeBeforeDelete = purchasePlanDetailsRepository.findAll().size();

        // Delete the purchasePlanDetails
        restPurchasePlanDetailsMockMvc.perform(delete("/api/purchase-plan-details/{id}", purchasePlanDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchasePlanDetails> purchasePlanDetailsList = purchasePlanDetailsRepository.findAll();
        assertThat(purchasePlanDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
