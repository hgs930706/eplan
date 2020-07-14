package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.InstalmentPlanDetailActual;
import com.mega.project.srm.repository.InstalmentPlanDetailActualRepository;

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
 * Integration tests for the {@link InstalmentPlanDetailActualResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstalmentPlanDetailActualResourceIT {

    private static final Instant DEFAULT_ACTUAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTUAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_ACTUAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACTUAL_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InstalmentPlanDetailActualRepository instalmentPlanDetailActualRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstalmentPlanDetailActualMockMvc;

    private InstalmentPlanDetailActual instalmentPlanDetailActual;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstalmentPlanDetailActual createEntity(EntityManager em) {
        InstalmentPlanDetailActual instalmentPlanDetailActual = new InstalmentPlanDetailActual()
            .actualDate(DEFAULT_ACTUAL_DATE)
            .actualAmount(DEFAULT_ACTUAL_AMOUNT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return instalmentPlanDetailActual;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstalmentPlanDetailActual createUpdatedEntity(EntityManager em) {
        InstalmentPlanDetailActual instalmentPlanDetailActual = new InstalmentPlanDetailActual()
            .actualDate(UPDATED_ACTUAL_DATE)
            .actualAmount(UPDATED_ACTUAL_AMOUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return instalmentPlanDetailActual;
    }

    @BeforeEach
    public void initTest() {
        instalmentPlanDetailActual = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstalmentPlanDetailActual() throws Exception {
        int databaseSizeBeforeCreate = instalmentPlanDetailActualRepository.findAll().size();
        // Create the InstalmentPlanDetailActual
        restInstalmentPlanDetailActualMockMvc.perform(post("/api/instalment-plan-detail-actuals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instalmentPlanDetailActual)))
            .andExpect(status().isCreated());

        // Validate the InstalmentPlanDetailActual in the database
        List<InstalmentPlanDetailActual> instalmentPlanDetailActualList = instalmentPlanDetailActualRepository.findAll();
        assertThat(instalmentPlanDetailActualList).hasSize(databaseSizeBeforeCreate + 1);
        InstalmentPlanDetailActual testInstalmentPlanDetailActual = instalmentPlanDetailActualList.get(instalmentPlanDetailActualList.size() - 1);
        assertThat(testInstalmentPlanDetailActual.getActualDate()).isEqualTo(DEFAULT_ACTUAL_DATE);
        assertThat(testInstalmentPlanDetailActual.getActualAmount()).isEqualTo(DEFAULT_ACTUAL_AMOUNT);
        assertThat(testInstalmentPlanDetailActual.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInstalmentPlanDetailActual.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInstalmentPlanDetailActual.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testInstalmentPlanDetailActual.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createInstalmentPlanDetailActualWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instalmentPlanDetailActualRepository.findAll().size();

        // Create the InstalmentPlanDetailActual with an existing ID
        instalmentPlanDetailActual.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstalmentPlanDetailActualMockMvc.perform(post("/api/instalment-plan-detail-actuals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instalmentPlanDetailActual)))
            .andExpect(status().isBadRequest());

        // Validate the InstalmentPlanDetailActual in the database
        List<InstalmentPlanDetailActual> instalmentPlanDetailActualList = instalmentPlanDetailActualRepository.findAll();
        assertThat(instalmentPlanDetailActualList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstalmentPlanDetailActuals() throws Exception {
        // Initialize the database
        instalmentPlanDetailActualRepository.saveAndFlush(instalmentPlanDetailActual);

        // Get all the instalmentPlanDetailActualList
        restInstalmentPlanDetailActualMockMvc.perform(get("/api/instalment-plan-detail-actuals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instalmentPlanDetailActual.getId().intValue())))
            .andExpect(jsonPath("$.[*].actualDate").value(hasItem(DEFAULT_ACTUAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].actualAmount").value(hasItem(DEFAULT_ACTUAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getInstalmentPlanDetailActual() throws Exception {
        // Initialize the database
        instalmentPlanDetailActualRepository.saveAndFlush(instalmentPlanDetailActual);

        // Get the instalmentPlanDetailActual
        restInstalmentPlanDetailActualMockMvc.perform(get("/api/instalment-plan-detail-actuals/{id}", instalmentPlanDetailActual.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instalmentPlanDetailActual.getId().intValue()))
            .andExpect(jsonPath("$.actualDate").value(DEFAULT_ACTUAL_DATE.toString()))
            .andExpect(jsonPath("$.actualAmount").value(DEFAULT_ACTUAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInstalmentPlanDetailActual() throws Exception {
        // Get the instalmentPlanDetailActual
        restInstalmentPlanDetailActualMockMvc.perform(get("/api/instalment-plan-detail-actuals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstalmentPlanDetailActual() throws Exception {
        // Initialize the database
        instalmentPlanDetailActualRepository.saveAndFlush(instalmentPlanDetailActual);

        int databaseSizeBeforeUpdate = instalmentPlanDetailActualRepository.findAll().size();

        // Update the instalmentPlanDetailActual
        InstalmentPlanDetailActual updatedInstalmentPlanDetailActual = instalmentPlanDetailActualRepository.findById(instalmentPlanDetailActual.getId()).get();
        // Disconnect from session so that the updates on updatedInstalmentPlanDetailActual are not directly saved in db
        em.detach(updatedInstalmentPlanDetailActual);
        updatedInstalmentPlanDetailActual
            .actualDate(UPDATED_ACTUAL_DATE)
            .actualAmount(UPDATED_ACTUAL_AMOUNT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restInstalmentPlanDetailActualMockMvc.perform(put("/api/instalment-plan-detail-actuals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstalmentPlanDetailActual)))
            .andExpect(status().isOk());

        // Validate the InstalmentPlanDetailActual in the database
        List<InstalmentPlanDetailActual> instalmentPlanDetailActualList = instalmentPlanDetailActualRepository.findAll();
        assertThat(instalmentPlanDetailActualList).hasSize(databaseSizeBeforeUpdate);
        InstalmentPlanDetailActual testInstalmentPlanDetailActual = instalmentPlanDetailActualList.get(instalmentPlanDetailActualList.size() - 1);
        assertThat(testInstalmentPlanDetailActual.getActualDate()).isEqualTo(UPDATED_ACTUAL_DATE);
        assertThat(testInstalmentPlanDetailActual.getActualAmount()).isEqualTo(UPDATED_ACTUAL_AMOUNT);
        assertThat(testInstalmentPlanDetailActual.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInstalmentPlanDetailActual.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInstalmentPlanDetailActual.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testInstalmentPlanDetailActual.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInstalmentPlanDetailActual() throws Exception {
        int databaseSizeBeforeUpdate = instalmentPlanDetailActualRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstalmentPlanDetailActualMockMvc.perform(put("/api/instalment-plan-detail-actuals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instalmentPlanDetailActual)))
            .andExpect(status().isBadRequest());

        // Validate the InstalmentPlanDetailActual in the database
        List<InstalmentPlanDetailActual> instalmentPlanDetailActualList = instalmentPlanDetailActualRepository.findAll();
        assertThat(instalmentPlanDetailActualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstalmentPlanDetailActual() throws Exception {
        // Initialize the database
        instalmentPlanDetailActualRepository.saveAndFlush(instalmentPlanDetailActual);

        int databaseSizeBeforeDelete = instalmentPlanDetailActualRepository.findAll().size();

        // Delete the instalmentPlanDetailActual
        restInstalmentPlanDetailActualMockMvc.perform(delete("/api/instalment-plan-detail-actuals/{id}", instalmentPlanDetailActual.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstalmentPlanDetailActual> instalmentPlanDetailActualList = instalmentPlanDetailActualRepository.findAll();
        assertThat(instalmentPlanDetailActualList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
