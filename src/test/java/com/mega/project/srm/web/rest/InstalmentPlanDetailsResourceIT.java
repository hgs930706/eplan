package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.InstalmentPlanDetails;
import com.mega.project.srm.repository.InstalmentPlanDetailsRepository;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InstalmentPlanDetailsResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstalmentPlanDetailsResourceIT {

    private static final String DEFAULT_PAYMENT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM = "BBBBBBBBBB";

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PAYMENT_SCALE = 1D;
    private static final Double UPDATED_PAYMENT_SCALE = 2D;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_BPM_NUM = "AAAAAAAAAA";
    private static final String UPDATED_BPM_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InstalmentPlanDetailsRepository instalmentPlanDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstalmentPlanDetailsMockMvc;

    private InstalmentPlanDetails instalmentPlanDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstalmentPlanDetails createEntity(EntityManager em) {
        InstalmentPlanDetails instalmentPlanDetails = new InstalmentPlanDetails()
            .paymentTerm(DEFAULT_PAYMENT_TERM)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentScale(DEFAULT_PAYMENT_SCALE)
            .amount(DEFAULT_AMOUNT)
            .bpmNum(DEFAULT_BPM_NUM)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return instalmentPlanDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstalmentPlanDetails createUpdatedEntity(EntityManager em) {
        InstalmentPlanDetails instalmentPlanDetails = new InstalmentPlanDetails()
            .paymentTerm(UPDATED_PAYMENT_TERM)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentScale(UPDATED_PAYMENT_SCALE)
            .amount(UPDATED_AMOUNT)
            .bpmNum(UPDATED_BPM_NUM)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return instalmentPlanDetails;
    }

    @BeforeEach
    public void initTest() {
        instalmentPlanDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstalmentPlanDetails() throws Exception {
        int databaseSizeBeforeCreate = instalmentPlanDetailsRepository.findAll().size();
        // Create the InstalmentPlanDetails
        restInstalmentPlanDetailsMockMvc.perform(post("/api/instalment-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instalmentPlanDetails)))
            .andExpect(status().isCreated());

        // Validate the InstalmentPlanDetails in the database
        List<InstalmentPlanDetails> instalmentPlanDetailsList = instalmentPlanDetailsRepository.findAll();
        assertThat(instalmentPlanDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        InstalmentPlanDetails testInstalmentPlanDetails = instalmentPlanDetailsList.get(instalmentPlanDetailsList.size() - 1);
        assertThat(testInstalmentPlanDetails.getPaymentTerm()).isEqualTo(DEFAULT_PAYMENT_TERM);
        assertThat(testInstalmentPlanDetails.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testInstalmentPlanDetails.getPaymentScale()).isEqualTo(DEFAULT_PAYMENT_SCALE);
        assertThat(testInstalmentPlanDetails.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInstalmentPlanDetails.getBpmNum()).isEqualTo(DEFAULT_BPM_NUM);
        assertThat(testInstalmentPlanDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInstalmentPlanDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInstalmentPlanDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testInstalmentPlanDetails.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createInstalmentPlanDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instalmentPlanDetailsRepository.findAll().size();

        // Create the InstalmentPlanDetails with an existing ID
        instalmentPlanDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstalmentPlanDetailsMockMvc.perform(post("/api/instalment-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instalmentPlanDetails)))
            .andExpect(status().isBadRequest());

        // Validate the InstalmentPlanDetails in the database
        List<InstalmentPlanDetails> instalmentPlanDetailsList = instalmentPlanDetailsRepository.findAll();
        assertThat(instalmentPlanDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstalmentPlanDetails() throws Exception {
        // Initialize the database
        instalmentPlanDetailsRepository.saveAndFlush(instalmentPlanDetails);

        // Get all the instalmentPlanDetailsList
        restInstalmentPlanDetailsMockMvc.perform(get("/api/instalment-plan-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instalmentPlanDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentTerm").value(hasItem(DEFAULT_PAYMENT_TERM)))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentScale").value(hasItem(DEFAULT_PAYMENT_SCALE.doubleValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].bpmNum").value(hasItem(DEFAULT_BPM_NUM)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getInstalmentPlanDetails() throws Exception {
        // Initialize the database
        instalmentPlanDetailsRepository.saveAndFlush(instalmentPlanDetails);

        // Get the instalmentPlanDetails
        restInstalmentPlanDetailsMockMvc.perform(get("/api/instalment-plan-details/{id}", instalmentPlanDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instalmentPlanDetails.getId().intValue()))
            .andExpect(jsonPath("$.paymentTerm").value(DEFAULT_PAYMENT_TERM))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentScale").value(DEFAULT_PAYMENT_SCALE.doubleValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.bpmNum").value(DEFAULT_BPM_NUM))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInstalmentPlanDetails() throws Exception {
        // Get the instalmentPlanDetails
        restInstalmentPlanDetailsMockMvc.perform(get("/api/instalment-plan-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstalmentPlanDetails() throws Exception {
        // Initialize the database
        instalmentPlanDetailsRepository.saveAndFlush(instalmentPlanDetails);

        int databaseSizeBeforeUpdate = instalmentPlanDetailsRepository.findAll().size();

        // Update the instalmentPlanDetails
        InstalmentPlanDetails updatedInstalmentPlanDetails = instalmentPlanDetailsRepository.findById(instalmentPlanDetails.getId()).get();
        // Disconnect from session so that the updates on updatedInstalmentPlanDetails are not directly saved in db
        em.detach(updatedInstalmentPlanDetails);
        updatedInstalmentPlanDetails
            .paymentTerm(UPDATED_PAYMENT_TERM)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentScale(UPDATED_PAYMENT_SCALE)
            .amount(UPDATED_AMOUNT)
            .bpmNum(UPDATED_BPM_NUM)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restInstalmentPlanDetailsMockMvc.perform(put("/api/instalment-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstalmentPlanDetails)))
            .andExpect(status().isOk());

        // Validate the InstalmentPlanDetails in the database
        List<InstalmentPlanDetails> instalmentPlanDetailsList = instalmentPlanDetailsRepository.findAll();
        assertThat(instalmentPlanDetailsList).hasSize(databaseSizeBeforeUpdate);
        InstalmentPlanDetails testInstalmentPlanDetails = instalmentPlanDetailsList.get(instalmentPlanDetailsList.size() - 1);
        assertThat(testInstalmentPlanDetails.getPaymentTerm()).isEqualTo(UPDATED_PAYMENT_TERM);
        assertThat(testInstalmentPlanDetails.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testInstalmentPlanDetails.getPaymentScale()).isEqualTo(UPDATED_PAYMENT_SCALE);
        assertThat(testInstalmentPlanDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInstalmentPlanDetails.getBpmNum()).isEqualTo(UPDATED_BPM_NUM);
        assertThat(testInstalmentPlanDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInstalmentPlanDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInstalmentPlanDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testInstalmentPlanDetails.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInstalmentPlanDetails() throws Exception {
        int databaseSizeBeforeUpdate = instalmentPlanDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstalmentPlanDetailsMockMvc.perform(put("/api/instalment-plan-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instalmentPlanDetails)))
            .andExpect(status().isBadRequest());

        // Validate the InstalmentPlanDetails in the database
        List<InstalmentPlanDetails> instalmentPlanDetailsList = instalmentPlanDetailsRepository.findAll();
        assertThat(instalmentPlanDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstalmentPlanDetails() throws Exception {
        // Initialize the database
        instalmentPlanDetailsRepository.saveAndFlush(instalmentPlanDetails);

        int databaseSizeBeforeDelete = instalmentPlanDetailsRepository.findAll().size();

        // Delete the instalmentPlanDetails
        restInstalmentPlanDetailsMockMvc.perform(delete("/api/instalment-plan-details/{id}", instalmentPlanDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstalmentPlanDetails> instalmentPlanDetailsList = instalmentPlanDetailsRepository.findAll();
        assertThat(instalmentPlanDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
