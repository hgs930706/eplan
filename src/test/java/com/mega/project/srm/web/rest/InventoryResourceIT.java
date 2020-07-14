package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.Inventory;
import com.mega.project.srm.repository.InventoryRepository;

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
 * Integration tests for the {@link InventoryResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryResourceIT {

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
    private InventoryRepository inventoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryMockMvc;

    private Inventory inventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .materialCode(DEFAULT_MATERIAL_CODE)
            .invQty(DEFAULT_INV_QTY)
            .invUnit(DEFAULT_INV_UNIT)
            .month(DEFAULT_MONTH)
            .uploadDate(DEFAULT_UPLOAD_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return inventory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createUpdatedEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .materialCode(UPDATED_MATERIAL_CODE)
            .invQty(UPDATED_INV_QTY)
            .invUnit(UPDATED_INV_UNIT)
            .month(UPDATED_MONTH)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return inventory;
    }

    @BeforeEach
    public void initTest() {
        inventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventory() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();
        // Create the Inventory
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate + 1);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testInventory.getInvQty()).isEqualTo(DEFAULT_INV_QTY);
        assertThat(testInventory.getInvUnit()).isEqualTo(DEFAULT_INV_UNIT);
        assertThat(testInventory.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testInventory.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
        assertThat(testInventory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInventory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInventory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testInventory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // Create the Inventory with an existing ID
        inventory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventories() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList
        restInventoryMockMvc.perform(get("/api/inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
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
    public void getInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId().intValue()))
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
    public void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory
        Inventory updatedInventory = inventoryRepository.findById(inventory.getId()).get();
        // Disconnect from session so that the updates on updatedInventory are not directly saved in db
        em.detach(updatedInventory);
        updatedInventory
            .materialCode(UPDATED_MATERIAL_CODE)
            .invQty(UPDATED_INV_QTY)
            .invUnit(UPDATED_INV_UNIT)
            .month(UPDATED_MONTH)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventory)))
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testInventory.getInvQty()).isEqualTo(UPDATED_INV_QTY);
        assertThat(testInventory.getInvUnit()).isEqualTo(UPDATED_INV_UNIT);
        assertThat(testInventory.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testInventory.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testInventory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInventory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testInventory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        int databaseSizeBeforeDelete = inventoryRepository.findAll().size();

        // Delete the inventory
        restInventoryMockMvc.perform(delete("/api/inventories/{id}", inventory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
