package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.PurchasePlanHeader;
import com.mega.project.srm.repository.PurchasePlanHeaderRepository;

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

import com.mega.project.srm.domain.enumeration.PurchasePlanStatus;
/**
 * Integration tests for the {@link PurchasePlanHeaderResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PurchasePlanHeaderResourceIT {

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

    private static final Integer DEFAULT_DELIVERY_TIMES = 1;
    private static final Integer UPDATED_DELIVERY_TIMES = 2;

    private static final PurchasePlanStatus DEFAULT_STATUS = PurchasePlanStatus.CREATE;
    private static final PurchasePlanStatus UPDATED_STATUS = PurchasePlanStatus.SEND_OUT;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PurchasePlanHeaderRepository purchasePlanHeaderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchasePlanHeaderMockMvc;

    private PurchasePlanHeader purchasePlanHeader;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasePlanHeader createEntity(EntityManager em) {
        PurchasePlanHeader purchasePlanHeader = new PurchasePlanHeader()
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
        return purchasePlanHeader;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasePlanHeader createUpdatedEntity(EntityManager em) {
        PurchasePlanHeader purchasePlanHeader = new PurchasePlanHeader()
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
        return purchasePlanHeader;
    }

    @BeforeEach
    public void initTest() {
        purchasePlanHeader = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchasePlanHeader() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanHeaderRepository.findAll().size();
        // Create the PurchasePlanHeader
        restPurchasePlanHeaderMockMvc.perform(post("/api/purchase-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanHeader)))
            .andExpect(status().isCreated());

        // Validate the PurchasePlanHeader in the database
        List<PurchasePlanHeader> purchasePlanHeaderList = purchasePlanHeaderRepository.findAll();
        assertThat(purchasePlanHeaderList).hasSize(databaseSizeBeforeCreate + 1);
        PurchasePlanHeader testPurchasePlanHeader = purchasePlanHeaderList.get(purchasePlanHeaderList.size() - 1);
        assertThat(testPurchasePlanHeader.getPurScheCode()).isEqualTo(DEFAULT_PUR_SCHE_CODE);
        assertThat(testPurchasePlanHeader.getPurScheVersion()).isEqualTo(DEFAULT_PUR_SCHE_VERSION);
        assertThat(testPurchasePlanHeader.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testPurchasePlanHeader.getVendorName()).isEqualTo(DEFAULT_VENDOR_NAME);
        assertThat(testPurchasePlanHeader.getBuyer()).isEqualTo(DEFAULT_BUYER);
        assertThat(testPurchasePlanHeader.getPurClass()).isEqualTo(DEFAULT_PUR_CLASS);
        assertThat(testPurchasePlanHeader.getDeliveryTimes()).isEqualTo(DEFAULT_DELIVERY_TIMES);
        assertThat(testPurchasePlanHeader.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPurchasePlanHeader.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPurchasePlanHeader.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPurchasePlanHeader.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchasePlanHeader.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPurchasePlanHeaderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanHeaderRepository.findAll().size();

        // Create the PurchasePlanHeader with an existing ID
        purchasePlanHeader.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchasePlanHeaderMockMvc.perform(post("/api/purchase-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanHeader)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlanHeader in the database
        List<PurchasePlanHeader> purchasePlanHeaderList = purchasePlanHeaderRepository.findAll();
        assertThat(purchasePlanHeaderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurchasePlanHeaders() throws Exception {
        // Initialize the database
        purchasePlanHeaderRepository.saveAndFlush(purchasePlanHeader);

        // Get all the purchasePlanHeaderList
        restPurchasePlanHeaderMockMvc.perform(get("/api/purchase-plan-headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchasePlanHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].purScheCode").value(hasItem(DEFAULT_PUR_SCHE_CODE)))
            .andExpect(jsonPath("$.[*].purScheVersion").value(hasItem(DEFAULT_PUR_SCHE_VERSION)))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].buyer").value(hasItem(DEFAULT_BUYER)))
            .andExpect(jsonPath("$.[*].purClass").value(hasItem(DEFAULT_PUR_CLASS)))
            .andExpect(jsonPath("$.[*].deliveryTimes").value(hasItem(DEFAULT_DELIVERY_TIMES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchasePlanHeader() throws Exception {
        // Initialize the database
        purchasePlanHeaderRepository.saveAndFlush(purchasePlanHeader);

        // Get the purchasePlanHeader
        restPurchasePlanHeaderMockMvc.perform(get("/api/purchase-plan-headers/{id}", purchasePlanHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchasePlanHeader.getId().intValue()))
            .andExpect(jsonPath("$.purScheCode").value(DEFAULT_PUR_SCHE_CODE))
            .andExpect(jsonPath("$.purScheVersion").value(DEFAULT_PUR_SCHE_VERSION))
            .andExpect(jsonPath("$.vendorCode").value(DEFAULT_VENDOR_CODE))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME))
            .andExpect(jsonPath("$.buyer").value(DEFAULT_BUYER))
            .andExpect(jsonPath("$.purClass").value(DEFAULT_PUR_CLASS))
            .andExpect(jsonPath("$.deliveryTimes").value(DEFAULT_DELIVERY_TIMES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPurchasePlanHeader() throws Exception {
        // Get the purchasePlanHeader
        restPurchasePlanHeaderMockMvc.perform(get("/api/purchase-plan-headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchasePlanHeader() throws Exception {
        // Initialize the database
        purchasePlanHeaderRepository.saveAndFlush(purchasePlanHeader);

        int databaseSizeBeforeUpdate = purchasePlanHeaderRepository.findAll().size();

        // Update the purchasePlanHeader
        PurchasePlanHeader updatedPurchasePlanHeader = purchasePlanHeaderRepository.findById(purchasePlanHeader.getId()).get();
        // Disconnect from session so that the updates on updatedPurchasePlanHeader are not directly saved in db
        em.detach(updatedPurchasePlanHeader);
        updatedPurchasePlanHeader
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

        restPurchasePlanHeaderMockMvc.perform(put("/api/purchase-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchasePlanHeader)))
            .andExpect(status().isOk());

        // Validate the PurchasePlanHeader in the database
        List<PurchasePlanHeader> purchasePlanHeaderList = purchasePlanHeaderRepository.findAll();
        assertThat(purchasePlanHeaderList).hasSize(databaseSizeBeforeUpdate);
        PurchasePlanHeader testPurchasePlanHeader = purchasePlanHeaderList.get(purchasePlanHeaderList.size() - 1);
        assertThat(testPurchasePlanHeader.getPurScheCode()).isEqualTo(UPDATED_PUR_SCHE_CODE);
        assertThat(testPurchasePlanHeader.getPurScheVersion()).isEqualTo(UPDATED_PUR_SCHE_VERSION);
        assertThat(testPurchasePlanHeader.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testPurchasePlanHeader.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testPurchasePlanHeader.getBuyer()).isEqualTo(UPDATED_BUYER);
        assertThat(testPurchasePlanHeader.getPurClass()).isEqualTo(UPDATED_PUR_CLASS);
        assertThat(testPurchasePlanHeader.getDeliveryTimes()).isEqualTo(UPDATED_DELIVERY_TIMES);
        assertThat(testPurchasePlanHeader.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPurchasePlanHeader.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPurchasePlanHeader.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPurchasePlanHeader.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchasePlanHeader.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchasePlanHeader() throws Exception {
        int databaseSizeBeforeUpdate = purchasePlanHeaderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchasePlanHeaderMockMvc.perform(put("/api/purchase-plan-headers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlanHeader)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlanHeader in the database
        List<PurchasePlanHeader> purchasePlanHeaderList = purchasePlanHeaderRepository.findAll();
        assertThat(purchasePlanHeaderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchasePlanHeader() throws Exception {
        // Initialize the database
        purchasePlanHeaderRepository.saveAndFlush(purchasePlanHeader);

        int databaseSizeBeforeDelete = purchasePlanHeaderRepository.findAll().size();

        // Delete the purchasePlanHeader
        restPurchasePlanHeaderMockMvc.perform(delete("/api/purchase-plan-headers/{id}", purchasePlanHeader.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchasePlanHeader> purchasePlanHeaderList = purchasePlanHeaderRepository.findAll();
        assertThat(purchasePlanHeaderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
