package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.InventoryHis;
import com.mega.project.srm.repository.InventoryHisRepository;

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
 * Integration tests for the {@link InventoryHisResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryHisResourceIT {

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_INV_QTY = 1D;
    private static final Double UPDATED_INV_QTY = 2D;

    private static final String DEFAULT_INV_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_INV_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_MONTH = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPLOAD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InventoryHisRepository inventoryHisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryHisMockMvc;

    private InventoryHis inventoryHis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryHis createEntity(EntityManager em) {
        InventoryHis inventoryHis = new InventoryHis()
            .materialCode(DEFAULT_MATERIAL_CODE)
            .invQty(DEFAULT_INV_QTY)
            .invUnit(DEFAULT_INV_UNIT)
            .month(DEFAULT_MONTH)
            .uploadDate(DEFAULT_UPLOAD_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return inventoryHis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryHis createUpdatedEntity(EntityManager em) {
        InventoryHis inventoryHis = new InventoryHis()
            .materialCode(UPDATED_MATERIAL_CODE)
            .invQty(UPDATED_INV_QTY)
            .invUnit(UPDATED_INV_UNIT)
            .month(UPDATED_MONTH)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return inventoryHis;
    }

    @BeforeEach
    public void initTest() {
        inventoryHis = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryHis() throws Exception {
        int databaseSizeBeforeCreate = inventoryHisRepository.findAll().size();
        // Create the InventoryHis
        restInventoryHisMockMvc.perform(post("/api/inventory-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryHis)))
            .andExpect(status().isCreated());

        // Validate the InventoryHis in the database
        List<InventoryHis> inventoryHisList = inventoryHisRepository.findAll();
        assertThat(inventoryHisList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryHis testInventoryHis = inventoryHisList.get(inventoryHisList.size() - 1);
        assertThat(testInventoryHis.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testInventoryHis.getInvQty()).isEqualTo(DEFAULT_INV_QTY);
        assertThat(testInventoryHis.getInvUnit()).isEqualTo(DEFAULT_INV_UNIT);
        assertThat(testInventoryHis.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testInventoryHis.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
        assertThat(testInventoryHis.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInventoryHis.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInventoryHis.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testInventoryHis.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createInventoryHisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryHisRepository.findAll().size();

        // Create the InventoryHis with an existing ID
        inventoryHis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryHisMockMvc.perform(post("/api/inventory-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryHis)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryHis in the database
        List<InventoryHis> inventoryHisList = inventoryHisRepository.findAll();
        assertThat(inventoryHisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventoryHis() throws Exception {
        // Initialize the database
        inventoryHisRepository.saveAndFlush(inventoryHis);

        // Get all the inventoryHisList
        restInventoryHisMockMvc.perform(get("/api/inventory-his?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryHis.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].invQty").value(hasItem(DEFAULT_INV_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].invUnit").value(hasItem(DEFAULT_INV_UNIT)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(DEFAULT_UPLOAD_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getInventoryHis() throws Exception {
        // Initialize the database
        inventoryHisRepository.saveAndFlush(inventoryHis);

        // Get the inventoryHis
        restInventoryHisMockMvc.perform(get("/api/inventory-his/{id}", inventoryHis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryHis.getId().intValue()))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE))
            .andExpect(jsonPath("$.invQty").value(DEFAULT_INV_QTY.doubleValue()))
            .andExpect(jsonPath("$.invUnit").value(DEFAULT_INV_UNIT))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.uploadDate").value(DEFAULT_UPLOAD_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInventoryHis() throws Exception {
        // Get the inventoryHis
        restInventoryHisMockMvc.perform(get("/api/inventory-his/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryHis() throws Exception {
        // Initialize the database
        inventoryHisRepository.saveAndFlush(inventoryHis);

        int databaseSizeBeforeUpdate = inventoryHisRepository.findAll().size();

        // Update the inventoryHis
        InventoryHis updatedInventoryHis = inventoryHisRepository.findById(inventoryHis.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryHis are not directly saved in db
        em.detach(updatedInventoryHis);
        updatedInventoryHis
            .materialCode(UPDATED_MATERIAL_CODE)
            .invQty(UPDATED_INV_QTY)
            .invUnit(UPDATED_INV_UNIT)
            .month(UPDATED_MONTH)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restInventoryHisMockMvc.perform(put("/api/inventory-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryHis)))
            .andExpect(status().isOk());

        // Validate the InventoryHis in the database
        List<InventoryHis> inventoryHisList = inventoryHisRepository.findAll();
        assertThat(inventoryHisList).hasSize(databaseSizeBeforeUpdate);
        InventoryHis testInventoryHis = inventoryHisList.get(inventoryHisList.size() - 1);
        assertThat(testInventoryHis.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testInventoryHis.getInvQty()).isEqualTo(UPDATED_INV_QTY);
        assertThat(testInventoryHis.getInvUnit()).isEqualTo(UPDATED_INV_UNIT);
        assertThat(testInventoryHis.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testInventoryHis.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testInventoryHis.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInventoryHis.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInventoryHis.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testInventoryHis.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryHis() throws Exception {
        int databaseSizeBeforeUpdate = inventoryHisRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryHisMockMvc.perform(put("/api/inventory-his")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryHis)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryHis in the database
        List<InventoryHis> inventoryHisList = inventoryHisRepository.findAll();
        assertThat(inventoryHisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryHis() throws Exception {
        // Initialize the database
        inventoryHisRepository.saveAndFlush(inventoryHis);

        int databaseSizeBeforeDelete = inventoryHisRepository.findAll().size();

        // Delete the inventoryHis
        restInventoryHisMockMvc.perform(delete("/api/inventory-his/{id}", inventoryHis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryHis> inventoryHisList = inventoryHisRepository.findAll();
        assertThat(inventoryHisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
