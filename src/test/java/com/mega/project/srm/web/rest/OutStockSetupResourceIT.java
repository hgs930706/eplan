package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.OutStockSetup;
import com.mega.project.srm.repository.OutStockSetupRepository;

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
 * Integration tests for the {@link OutStockSetupResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OutStockSetupResourceIT {

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STOCK_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_STOCK_QTY = 1D;
    private static final Double UPDATED_STOCK_QTY = 2D;

    private static final Instant DEFAULT_SETUP_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SETUP_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OutStockSetupRepository outStockSetupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOutStockSetupMockMvc;

    private OutStockSetup outStockSetup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OutStockSetup createEntity(EntityManager em) {
        OutStockSetup outStockSetup = new OutStockSetup()
            .materialCode(DEFAULT_MATERIAL_CODE)
            .stockCode(DEFAULT_STOCK_CODE)
            .stockQty(DEFAULT_STOCK_QTY)
            .setupDate(DEFAULT_SETUP_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return outStockSetup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OutStockSetup createUpdatedEntity(EntityManager em) {
        OutStockSetup outStockSetup = new OutStockSetup()
            .materialCode(UPDATED_MATERIAL_CODE)
            .stockCode(UPDATED_STOCK_CODE)
            .stockQty(UPDATED_STOCK_QTY)
            .setupDate(UPDATED_SETUP_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return outStockSetup;
    }

    @BeforeEach
    public void initTest() {
        outStockSetup = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutStockSetup() throws Exception {
        int databaseSizeBeforeCreate = outStockSetupRepository.findAll().size();
        // Create the OutStockSetup
        restOutStockSetupMockMvc.perform(post("/api/out-stock-setups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outStockSetup)))
            .andExpect(status().isCreated());

        // Validate the OutStockSetup in the database
        List<OutStockSetup> outStockSetupList = outStockSetupRepository.findAll();
        assertThat(outStockSetupList).hasSize(databaseSizeBeforeCreate + 1);
        OutStockSetup testOutStockSetup = outStockSetupList.get(outStockSetupList.size() - 1);
        assertThat(testOutStockSetup.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testOutStockSetup.getStockCode()).isEqualTo(DEFAULT_STOCK_CODE);
        assertThat(testOutStockSetup.getStockQty()).isEqualTo(DEFAULT_STOCK_QTY);
        assertThat(testOutStockSetup.getSetupDate()).isEqualTo(DEFAULT_SETUP_DATE);
        assertThat(testOutStockSetup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOutStockSetup.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOutStockSetup.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOutStockSetup.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createOutStockSetupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outStockSetupRepository.findAll().size();

        // Create the OutStockSetup with an existing ID
        outStockSetup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutStockSetupMockMvc.perform(post("/api/out-stock-setups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outStockSetup)))
            .andExpect(status().isBadRequest());

        // Validate the OutStockSetup in the database
        List<OutStockSetup> outStockSetupList = outStockSetupRepository.findAll();
        assertThat(outStockSetupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOutStockSetups() throws Exception {
        // Initialize the database
        outStockSetupRepository.saveAndFlush(outStockSetup);

        // Get all the outStockSetupList
        restOutStockSetupMockMvc.perform(get("/api/out-stock-setups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outStockSetup.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].stockCode").value(hasItem(DEFAULT_STOCK_CODE)))
            .andExpect(jsonPath("$.[*].stockQty").value(hasItem(DEFAULT_STOCK_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].setupDate").value(hasItem(DEFAULT_SETUP_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOutStockSetup() throws Exception {
        // Initialize the database
        outStockSetupRepository.saveAndFlush(outStockSetup);

        // Get the outStockSetup
        restOutStockSetupMockMvc.perform(get("/api/out-stock-setups/{id}", outStockSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(outStockSetup.getId().intValue()))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE))
            .andExpect(jsonPath("$.stockCode").value(DEFAULT_STOCK_CODE))
            .andExpect(jsonPath("$.stockQty").value(DEFAULT_STOCK_QTY.doubleValue()))
            .andExpect(jsonPath("$.setupDate").value(DEFAULT_SETUP_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingOutStockSetup() throws Exception {
        // Get the outStockSetup
        restOutStockSetupMockMvc.perform(get("/api/out-stock-setups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutStockSetup() throws Exception {
        // Initialize the database
        outStockSetupRepository.saveAndFlush(outStockSetup);

        int databaseSizeBeforeUpdate = outStockSetupRepository.findAll().size();

        // Update the outStockSetup
        OutStockSetup updatedOutStockSetup = outStockSetupRepository.findById(outStockSetup.getId()).get();
        // Disconnect from session so that the updates on updatedOutStockSetup are not directly saved in db
        em.detach(updatedOutStockSetup);
        updatedOutStockSetup
            .materialCode(UPDATED_MATERIAL_CODE)
            .stockCode(UPDATED_STOCK_CODE)
            .stockQty(UPDATED_STOCK_QTY)
            .setupDate(UPDATED_SETUP_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restOutStockSetupMockMvc.perform(put("/api/out-stock-setups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOutStockSetup)))
            .andExpect(status().isOk());

        // Validate the OutStockSetup in the database
        List<OutStockSetup> outStockSetupList = outStockSetupRepository.findAll();
        assertThat(outStockSetupList).hasSize(databaseSizeBeforeUpdate);
        OutStockSetup testOutStockSetup = outStockSetupList.get(outStockSetupList.size() - 1);
        assertThat(testOutStockSetup.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testOutStockSetup.getStockCode()).isEqualTo(UPDATED_STOCK_CODE);
        assertThat(testOutStockSetup.getStockQty()).isEqualTo(UPDATED_STOCK_QTY);
        assertThat(testOutStockSetup.getSetupDate()).isEqualTo(UPDATED_SETUP_DATE);
        assertThat(testOutStockSetup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOutStockSetup.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOutStockSetup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOutStockSetup.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOutStockSetup() throws Exception {
        int databaseSizeBeforeUpdate = outStockSetupRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutStockSetupMockMvc.perform(put("/api/out-stock-setups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outStockSetup)))
            .andExpect(status().isBadRequest());

        // Validate the OutStockSetup in the database
        List<OutStockSetup> outStockSetupList = outStockSetupRepository.findAll();
        assertThat(outStockSetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOutStockSetup() throws Exception {
        // Initialize the database
        outStockSetupRepository.saveAndFlush(outStockSetup);

        int databaseSizeBeforeDelete = outStockSetupRepository.findAll().size();

        // Delete the outStockSetup
        restOutStockSetupMockMvc.perform(delete("/api/out-stock-setups/{id}", outStockSetup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OutStockSetup> outStockSetupList = outStockSetupRepository.findAll();
        assertThat(outStockSetupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
