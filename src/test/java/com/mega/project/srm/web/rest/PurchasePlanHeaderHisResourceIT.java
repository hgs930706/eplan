package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.PurchasePlanHeaderHis;
import com.mega.project.srm.repository.PurchasePlanHeaderHisRepository;

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
 * Integration tests for the {@link PurchasePlanHeaderHisResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PurchasePlanHeaderHisResourceIT {

    private static final String DEFAULT_PUR_SCHE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PUR_SCHE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PUR_SCHE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_PUR_SCHE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER = "AAAAAAAAAA";
    private static final String UPDATED_BUYER = "BBBBBBBBBB";

    private static final String DEFAULT_PUR_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_PUR_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_TIMES = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_TIMES = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PurchasePlanHeaderHisRepository purchasePlanHeaderHisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchasePlanHeaderHisMockMvc;

    private PurchasePlanHeaderHis purchasePlanHeaderHis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasePlanHeaderHis createEntity(EntityManager em) {
        PurchasePlanHeaderHis purchasePlanHeaderHis = new PurchasePlanHeaderHis()
            .purScheCode(DEFAULT_PUR_SCHE_CODE)
            .purScheVersion(DEFAULT_PUR_SCHE_VERSION)
            .vendorCode(DEFAULT_VENDOR_CODE)
            .vendorName(DEFAULT_VENDOR_NAME)
            .buyer(DEFAULT_BUYER)
            .purClass(DEFAULT_PUR_CLASS)
            .deliveryTimes(DEFAULT_DELIVERY_TIMES)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return purchasePlanHeaderHis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasePlanHeaderHis createUpdatedEntity(EntityManager em) {
        PurchasePlanHeaderHis purchasePlanHeaderHis = new PurchasePlanHeaderHis()
            .purScheCode(UPDATED_PUR_SCHE_CODE)
            .purScheVersion(UPDATED_PUR_SCHE_VERSION)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorName(UPDATED_VENDOR_NAME)
            .buyer(UPDATED_BUYER)
            .purClass(UPDATED_PUR_CLASS)
            .deliveryTimes(UPDATED_DELIVERY_TIMES)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return purchasePlanHeaderHis;
    }

    @BeforeEach
    public void initTest() {
        purchasePlanHeaderHis = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchasePlanHeaderHis() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanHeaderHisRepository.findAll().size();
        // Create the PurchasePlanHeaderHis
        restPurchasePlanHeaderHisMockMvc.perform(post("/api/purchase-plan-header-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanHeaderHis)))
            .andExpect(status().isCreated());

        // Validate the PurchasePlanHeaderHis in the database
        List<PurchasePlanHeaderHis> purchasePlanHeaderHisList = purchasePlanHeaderHisRepository.findAll();
        assertThat(purchasePlanHeaderHisList).hasSize(databaseSizeBeforeCreate + 1);
        PurchasePlanHeaderHis testPurchasePlanHeaderHis = purchasePlanHeaderHisList.get(purchasePlanHeaderHisList.size() - 1);
        assertThat(testPurchasePlanHeaderHis.getPurScheCode()).isEqualTo(DEFAULT_PUR_SCHE_CODE);
        assertThat(testPurchasePlanHeaderHis.getPurScheVersion()).isEqualTo(DEFAULT_PUR_SCHE_VERSION);
        assertThat(testPurchasePlanHeaderHis.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testPurchasePlanHeaderHis.getVendorName()).isEqualTo(DEFAULT_VENDOR_NAME);
        assertThat(testPurchasePlanHeaderHis.getBuyer()).isEqualTo(DEFAULT_BUYER);
        assertThat(testPurchasePlanHeaderHis.getPurClass()).isEqualTo(DEFAULT_PUR_CLASS);
        assertThat(testPurchasePlanHeaderHis.getDeliveryTimes()).isEqualTo(DEFAULT_DELIVERY_TIMES);
        assertThat(testPurchasePlanHeaderHis.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPurchasePlanHeaderHis.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPurchasePlanHeaderHis.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPurchasePlanHeaderHis.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchasePlanHeaderHis.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPurchasePlanHeaderHisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanHeaderHisRepository.findAll().size();

        // Create the PurchasePlanHeaderHis with an existing ID
        purchasePlanHeaderHis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchasePlanHeaderHisMockMvc.perform(post("/api/purchase-plan-header-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanHeaderHis)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlanHeaderHis in the database
        List<PurchasePlanHeaderHis> purchasePlanHeaderHisList = purchasePlanHeaderHisRepository.findAll();
        assertThat(purchasePlanHeaderHisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurchasePlanHeaderHis() throws Exception {
        // Initialize the database
        purchasePlanHeaderHisRepository.saveAndFlush(purchasePlanHeaderHis);

        // Get all the purchasePlanHeaderHisList
        restPurchasePlanHeaderHisMockMvc.perform(get("/api/purchase-plan-header-his?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchasePlanHeaderHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].purScheCode").value(hasItem(DEFAULT_PUR_SCHE_CODE)))
            .andExpect(jsonPath("$.[*].purScheVersion").value(hasItem(DEFAULT_PUR_SCHE_VERSION)))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].buyer").value(hasItem(DEFAULT_BUYER)))
            .andExpect(jsonPath("$.[*].purClass").value(hasItem(DEFAULT_PUR_CLASS)))
            .andExpect(jsonPath("$.[*].deliveryTimes").value(hasItem(DEFAULT_DELIVERY_TIMES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchasePlanHeaderHis() throws Exception {
        // Initialize the database
        purchasePlanHeaderHisRepository.saveAndFlush(purchasePlanHeaderHis);

        // Get the purchasePlanHeaderHis
        restPurchasePlanHeaderHisMockMvc.perform(get("/api/purchase-plan-header-his/{id}", purchasePlanHeaderHis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchasePlanHeaderHis.getId().intValue()))
            .andExpect(jsonPath("$.purScheCode").value(DEFAULT_PUR_SCHE_CODE))
            .andExpect(jsonPath("$.purScheVersion").value(DEFAULT_PUR_SCHE_VERSION))
            .andExpect(jsonPath("$.vendorCode").value(DEFAULT_VENDOR_CODE))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME))
            .andExpect(jsonPath("$.buyer").value(DEFAULT_BUYER))
            .andExpect(jsonPath("$.purClass").value(DEFAULT_PUR_CLASS))
            .andExpect(jsonPath("$.deliveryTimes").value(DEFAULT_DELIVERY_TIMES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPurchasePlanHeaderHis() throws Exception {
        // Get the purchasePlanHeaderHis
        restPurchasePlanHeaderHisMockMvc.perform(get("/api/purchase-plan-header-his/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchasePlanHeaderHis() throws Exception {
        // Initialize the database
        purchasePlanHeaderHisRepository.saveAndFlush(purchasePlanHeaderHis);

        int databaseSizeBeforeUpdate = purchasePlanHeaderHisRepository.findAll().size();

        // Update the purchasePlanHeaderHis
        PurchasePlanHeaderHis updatedPurchasePlanHeaderHis = purchasePlanHeaderHisRepository.findById(purchasePlanHeaderHis.getId()).get();
        // Disconnect from session so that the updates on updatedPurchasePlanHeaderHis are not directly saved in db
        em.detach(updatedPurchasePlanHeaderHis);
        updatedPurchasePlanHeaderHis
            .purScheCode(UPDATED_PUR_SCHE_CODE)
            .purScheVersion(UPDATED_PUR_SCHE_VERSION)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorName(UPDATED_VENDOR_NAME)
            .buyer(UPDATED_BUYER)
            .purClass(UPDATED_PUR_CLASS)
            .deliveryTimes(UPDATED_DELIVERY_TIMES)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPurchasePlanHeaderHisMockMvc.perform(put("/api/purchase-plan-header-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchasePlanHeaderHis)))
            .andExpect(status().isOk());

        // Validate the PurchasePlanHeaderHis in the database
        List<PurchasePlanHeaderHis> purchasePlanHeaderHisList = purchasePlanHeaderHisRepository.findAll();
        assertThat(purchasePlanHeaderHisList).hasSize(databaseSizeBeforeUpdate);
        PurchasePlanHeaderHis testPurchasePlanHeaderHis = purchasePlanHeaderHisList.get(purchasePlanHeaderHisList.size() - 1);
        assertThat(testPurchasePlanHeaderHis.getPurScheCode()).isEqualTo(UPDATED_PUR_SCHE_CODE);
        assertThat(testPurchasePlanHeaderHis.getPurScheVersion()).isEqualTo(UPDATED_PUR_SCHE_VERSION);
        assertThat(testPurchasePlanHeaderHis.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testPurchasePlanHeaderHis.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testPurchasePlanHeaderHis.getBuyer()).isEqualTo(UPDATED_BUYER);
        assertThat(testPurchasePlanHeaderHis.getPurClass()).isEqualTo(UPDATED_PUR_CLASS);
        assertThat(testPurchasePlanHeaderHis.getDeliveryTimes()).isEqualTo(UPDATED_DELIVERY_TIMES);
        assertThat(testPurchasePlanHeaderHis.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPurchasePlanHeaderHis.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPurchasePlanHeaderHis.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPurchasePlanHeaderHis.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchasePlanHeaderHis.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchasePlanHeaderHis() throws Exception {
        int databaseSizeBeforeUpdate = purchasePlanHeaderHisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchasePlanHeaderHisMockMvc.perform(put("/api/purchase-plan-header-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanHeaderHis)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlanHeaderHis in the database
        List<PurchasePlanHeaderHis> purchasePlanHeaderHisList = purchasePlanHeaderHisRepository.findAll();
        assertThat(purchasePlanHeaderHisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchasePlanHeaderHis() throws Exception {
        // Initialize the database
        purchasePlanHeaderHisRepository.saveAndFlush(purchasePlanHeaderHis);

        int databaseSizeBeforeDelete = purchasePlanHeaderHisRepository.findAll().size();

        // Delete the purchasePlanHeaderHis
        restPurchasePlanHeaderHisMockMvc.perform(delete("/api/purchase-plan-header-his/{id}", purchasePlanHeaderHis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchasePlanHeaderHis> purchasePlanHeaderHisList = purchasePlanHeaderHisRepository.findAll();
        assertThat(purchasePlanHeaderHisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
