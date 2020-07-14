package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.DeliveryCycleSetup;
import com.mega.project.srm.repository.DeliveryCycleSetupRepository;

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
 * Integration tests for the {@link DeliveryCycleSetupResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeliveryCycleSetupResourceIT {

    private static final String DEFAULT_MATERIAL_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_CYCLE = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_CYCLE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DeliveryCycleSetupRepository deliveryCycleSetupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryCycleSetupMockMvc;

    private DeliveryCycleSetup deliveryCycleSetup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryCycleSetup createEntity(EntityManager em) {
        DeliveryCycleSetup deliveryCycleSetup = new DeliveryCycleSetup()
            .materialClass(DEFAULT_MATERIAL_CLASS)
            .deliveryCycle(DEFAULT_DELIVERY_CYCLE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return deliveryCycleSetup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryCycleSetup createUpdatedEntity(EntityManager em) {
        DeliveryCycleSetup deliveryCycleSetup = new DeliveryCycleSetup()
            .materialClass(UPDATED_MATERIAL_CLASS)
            .deliveryCycle(UPDATED_DELIVERY_CYCLE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return deliveryCycleSetup;
    }

    @BeforeEach
    public void initTest() {
        deliveryCycleSetup = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryCycleSetup() throws Exception {
        int databaseSizeBeforeCreate = deliveryCycleSetupRepository.findAll().size();
        // Create the DeliveryCycleSetup
        restDeliveryCycleSetupMockMvc.perform(post("/api/delivery-cycle-setups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryCycleSetup)))
            .andExpect(status().isCreated());

        // Validate the DeliveryCycleSetup in the database
        List<DeliveryCycleSetup> deliveryCycleSetupList = deliveryCycleSetupRepository.findAll();
        assertThat(deliveryCycleSetupList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryCycleSetup testDeliveryCycleSetup = deliveryCycleSetupList.get(deliveryCycleSetupList.size() - 1);
        assertThat(testDeliveryCycleSetup.getMaterialClass()).isEqualTo(DEFAULT_MATERIAL_CLASS);
        assertThat(testDeliveryCycleSetup.getDeliveryCycle()).isEqualTo(DEFAULT_DELIVERY_CYCLE);
        assertThat(testDeliveryCycleSetup.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDeliveryCycleSetup.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDeliveryCycleSetup.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testDeliveryCycleSetup.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDeliveryCycleSetupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryCycleSetupRepository.findAll().size();

        // Create the DeliveryCycleSetup with an existing ID
        deliveryCycleSetup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryCycleSetupMockMvc.perform(post("/api/delivery-cycle-setups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryCycleSetup)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryCycleSetup in the database
        List<DeliveryCycleSetup> deliveryCycleSetupList = deliveryCycleSetupRepository.findAll();
        assertThat(deliveryCycleSetupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeliveryCycleSetups() throws Exception {
        // Initialize the database
        deliveryCycleSetupRepository.saveAndFlush(deliveryCycleSetup);

        // Get all the deliveryCycleSetupList
        restDeliveryCycleSetupMockMvc.perform(get("/api/delivery-cycle-setups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryCycleSetup.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialClass").value(hasItem(DEFAULT_MATERIAL_CLASS)))
            .andExpect(jsonPath("$.[*].deliveryCycle").value(hasItem(DEFAULT_DELIVERY_CYCLE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDeliveryCycleSetup() throws Exception {
        // Initialize the database
        deliveryCycleSetupRepository.saveAndFlush(deliveryCycleSetup);

        // Get the deliveryCycleSetup
        restDeliveryCycleSetupMockMvc.perform(get("/api/delivery-cycle-setups/{id}", deliveryCycleSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryCycleSetup.getId().intValue()))
            .andExpect(jsonPath("$.materialClass").value(DEFAULT_MATERIAL_CLASS))
            .andExpect(jsonPath("$.deliveryCycle").value(DEFAULT_DELIVERY_CYCLE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDeliveryCycleSetup() throws Exception {
        // Get the deliveryCycleSetup
        restDeliveryCycleSetupMockMvc.perform(get("/api/delivery-cycle-setups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryCycleSetup() throws Exception {
        // Initialize the database
        deliveryCycleSetupRepository.saveAndFlush(deliveryCycleSetup);

        int databaseSizeBeforeUpdate = deliveryCycleSetupRepository.findAll().size();

        // Update the deliveryCycleSetup
        DeliveryCycleSetup updatedDeliveryCycleSetup = deliveryCycleSetupRepository.findById(deliveryCycleSetup.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryCycleSetup are not directly saved in db
        em.detach(updatedDeliveryCycleSetup);
        updatedDeliveryCycleSetup
            .materialClass(UPDATED_MATERIAL_CLASS)
            .deliveryCycle(UPDATED_DELIVERY_CYCLE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restDeliveryCycleSetupMockMvc.perform(put("/api/delivery-cycle-setups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeliveryCycleSetup)))
            .andExpect(status().isOk());

        // Validate the DeliveryCycleSetup in the database
        List<DeliveryCycleSetup> deliveryCycleSetupList = deliveryCycleSetupRepository.findAll();
        assertThat(deliveryCycleSetupList).hasSize(databaseSizeBeforeUpdate);
        DeliveryCycleSetup testDeliveryCycleSetup = deliveryCycleSetupList.get(deliveryCycleSetupList.size() - 1);
        assertThat(testDeliveryCycleSetup.getMaterialClass()).isEqualTo(UPDATED_MATERIAL_CLASS);
        assertThat(testDeliveryCycleSetup.getDeliveryCycle()).isEqualTo(UPDATED_DELIVERY_CYCLE);
        assertThat(testDeliveryCycleSetup.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDeliveryCycleSetup.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDeliveryCycleSetup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDeliveryCycleSetup.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryCycleSetup() throws Exception {
        int databaseSizeBeforeUpdate = deliveryCycleSetupRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryCycleSetupMockMvc.perform(put("/api/delivery-cycle-setups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryCycleSetup)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryCycleSetup in the database
        List<DeliveryCycleSetup> deliveryCycleSetupList = deliveryCycleSetupRepository.findAll();
        assertThat(deliveryCycleSetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryCycleSetup() throws Exception {
        // Initialize the database
        deliveryCycleSetupRepository.saveAndFlush(deliveryCycleSetup);

        int databaseSizeBeforeDelete = deliveryCycleSetupRepository.findAll().size();

        // Delete the deliveryCycleSetup
        restDeliveryCycleSetupMockMvc.perform(delete("/api/delivery-cycle-setups/{id}", deliveryCycleSetup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryCycleSetup> deliveryCycleSetupList = deliveryCycleSetupRepository.findAll();
        assertThat(deliveryCycleSetupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
