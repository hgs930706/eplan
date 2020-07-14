package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.PurchasePlanDetailHis;
import com.mega.project.srm.repository.PurchasePlanDetailHisRepository;

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
 * Integration tests for the {@link PurchasePlanDetailHisResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PurchasePlanDetailHisResourceIT {

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPTO = "AAAAAAAAAA";
    private static final String UPDATED_SHIPTO = "BBBBBBBBBB";

    private static final PurchasePlanNumClass DEFAULT_NUM_CLASS = PurchasePlanNumClass.PLAN;
    private static final PurchasePlanNumClass UPDATED_NUM_CLASS = PurchasePlanNumClass.CONFIRM;

    private static final String DEFAULT_PUR_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PUR_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PUR_QTY = "AAAAAAAAAA";
    private static final String UPDATED_PUR_QTY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PurchasePlanDetailHisRepository purchasePlanDetailHisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchasePlanDetailHisMockMvc;

    private PurchasePlanDetailHis purchasePlanDetailHis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasePlanDetailHis createEntity(EntityManager em) {
        PurchasePlanDetailHis purchasePlanDetailHis = new PurchasePlanDetailHis()
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
        return purchasePlanDetailHis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasePlanDetailHis createUpdatedEntity(EntityManager em) {
        PurchasePlanDetailHis purchasePlanDetailHis = new PurchasePlanDetailHis()
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
        return purchasePlanDetailHis;
    }

    @BeforeEach
    public void initTest() {
        purchasePlanDetailHis = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchasePlanDetailHis() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanDetailHisRepository.findAll().size();
        // Create the PurchasePlanDetailHis
        restPurchasePlanDetailHisMockMvc.perform(post("/api/purchase-plan-detail-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanDetailHis)))
            .andExpect(status().isCreated());

        // Validate the PurchasePlanDetailHis in the database
        List<PurchasePlanDetailHis> purchasePlanDetailHisList = purchasePlanDetailHisRepository.findAll();
        assertThat(purchasePlanDetailHisList).hasSize(databaseSizeBeforeCreate + 1);
        PurchasePlanDetailHis testPurchasePlanDetailHis = purchasePlanDetailHisList.get(purchasePlanDetailHisList.size() - 1);
        assertThat(testPurchasePlanDetailHis.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testPurchasePlanDetailHis.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
        assertThat(testPurchasePlanDetailHis.getShipto()).isEqualTo(DEFAULT_SHIPTO);
        assertThat(testPurchasePlanDetailHis.getNumClass()).isEqualTo(DEFAULT_NUM_CLASS);
        assertThat(testPurchasePlanDetailHis.getPurDate()).isEqualTo(DEFAULT_PUR_DATE);
        assertThat(testPurchasePlanDetailHis.getPurQty()).isEqualTo(DEFAULT_PUR_QTY);
        assertThat(testPurchasePlanDetailHis.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPurchasePlanDetailHis.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPurchasePlanDetailHis.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchasePlanDetailHis.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPurchasePlanDetailHisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanDetailHisRepository.findAll().size();

        // Create the PurchasePlanDetailHis with an existing ID
        purchasePlanDetailHis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchasePlanDetailHisMockMvc.perform(post("/api/purchase-plan-detail-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanDetailHis)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlanDetailHis in the database
        List<PurchasePlanDetailHis> purchasePlanDetailHisList = purchasePlanDetailHisRepository.findAll();
        assertThat(purchasePlanDetailHisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurchasePlanDetailHis() throws Exception {
        // Initialize the database
        purchasePlanDetailHisRepository.saveAndFlush(purchasePlanDetailHis);

        // Get all the purchasePlanDetailHisList
        restPurchasePlanDetailHisMockMvc.perform(get("/api/purchase-plan-detail-his?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchasePlanDetailHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].shipto").value(hasItem(DEFAULT_SHIPTO)))
            .andExpect(jsonPath("$.[*].numClass").value(hasItem(DEFAULT_NUM_CLASS.toString())))
            .andExpect(jsonPath("$.[*].purDate").value(hasItem(DEFAULT_PUR_DATE)))
            .andExpect(jsonPath("$.[*].purQty").value(hasItem(DEFAULT_PUR_QTY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchasePlanDetailHis() throws Exception {
        // Initialize the database
        purchasePlanDetailHisRepository.saveAndFlush(purchasePlanDetailHis);

        // Get the purchasePlanDetailHis
        restPurchasePlanDetailHisMockMvc.perform(get("/api/purchase-plan-detail-his/{id}", purchasePlanDetailHis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchasePlanDetailHis.getId().intValue()))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME))
            .andExpect(jsonPath("$.shipto").value(DEFAULT_SHIPTO))
            .andExpect(jsonPath("$.numClass").value(DEFAULT_NUM_CLASS.toString()))
            .andExpect(jsonPath("$.purDate").value(DEFAULT_PUR_DATE))
            .andExpect(jsonPath("$.purQty").value(DEFAULT_PUR_QTY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPurchasePlanDetailHis() throws Exception {
        // Get the purchasePlanDetailHis
        restPurchasePlanDetailHisMockMvc.perform(get("/api/purchase-plan-detail-his/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchasePlanDetailHis() throws Exception {
        // Initialize the database
        purchasePlanDetailHisRepository.saveAndFlush(purchasePlanDetailHis);

        int databaseSizeBeforeUpdate = purchasePlanDetailHisRepository.findAll().size();

        // Update the purchasePlanDetailHis
        PurchasePlanDetailHis updatedPurchasePlanDetailHis = purchasePlanDetailHisRepository.findById(purchasePlanDetailHis.getId()).get();
        // Disconnect from session so that the updates on updatedPurchasePlanDetailHis are not directly saved in db
        em.detach(updatedPurchasePlanDetailHis);
        updatedPurchasePlanDetailHis
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

        restPurchasePlanDetailHisMockMvc.perform(put("/api/purchase-plan-detail-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchasePlanDetailHis)))
            .andExpect(status().isOk());

        // Validate the PurchasePlanDetailHis in the database
        List<PurchasePlanDetailHis> purchasePlanDetailHisList = purchasePlanDetailHisRepository.findAll();
        assertThat(purchasePlanDetailHisList).hasSize(databaseSizeBeforeUpdate);
        PurchasePlanDetailHis testPurchasePlanDetailHis = purchasePlanDetailHisList.get(purchasePlanDetailHisList.size() - 1);
        assertThat(testPurchasePlanDetailHis.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testPurchasePlanDetailHis.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
        assertThat(testPurchasePlanDetailHis.getShipto()).isEqualTo(UPDATED_SHIPTO);
        assertThat(testPurchasePlanDetailHis.getNumClass()).isEqualTo(UPDATED_NUM_CLASS);
        assertThat(testPurchasePlanDetailHis.getPurDate()).isEqualTo(UPDATED_PUR_DATE);
        assertThat(testPurchasePlanDetailHis.getPurQty()).isEqualTo(UPDATED_PUR_QTY);
        assertThat(testPurchasePlanDetailHis.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPurchasePlanDetailHis.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPurchasePlanDetailHis.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchasePlanDetailHis.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchasePlanDetailHis() throws Exception {
        int databaseSizeBeforeUpdate = purchasePlanDetailHisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchasePlanDetailHisMockMvc.perform(put("/api/purchase-plan-detail-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanDetailHis)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlanDetailHis in the database
        List<PurchasePlanDetailHis> purchasePlanDetailHisList = purchasePlanDetailHisRepository.findAll();
        assertThat(purchasePlanDetailHisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchasePlanDetailHis() throws Exception {
        // Initialize the database
        purchasePlanDetailHisRepository.saveAndFlush(purchasePlanDetailHis);

        int databaseSizeBeforeDelete = purchasePlanDetailHisRepository.findAll().size();

        // Delete the purchasePlanDetailHis
        restPurchasePlanDetailHisMockMvc.perform(delete("/api/purchase-plan-detail-his/{id}", purchasePlanDetailHis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchasePlanDetailHis> purchasePlanDetailHisList = purchasePlanDetailHisRepository.findAll();
        assertThat(purchasePlanDetailHisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
