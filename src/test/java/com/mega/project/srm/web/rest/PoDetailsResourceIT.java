package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.PoDetails;
import com.mega.project.srm.repository.PoDetailsRepository;

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
 * Integration tests for the {@link PoDetailsResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PoDetailsResourceIT {

    private static final Integer DEFAULT_PO_DETAIL_NUM = 1;
    private static final Integer UPDATED_PO_DETAIL_NUM = 2;

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MATERIAL_QTY = 1;
    private static final Integer UPDATED_MATERIAL_QTY = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Double DEFAULT_TAX = 1D;
    private static final Double UPDATED_TAX = 2D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PoDetailsRepository poDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPoDetailsMockMvc;

    private PoDetails poDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoDetails createEntity(EntityManager em) {
        PoDetails poDetails = new PoDetails()
            .poDetailNum(DEFAULT_PO_DETAIL_NUM)
            .materialCode(DEFAULT_MATERIAL_CODE)
            .materialName(DEFAULT_MATERIAL_NAME)
            .materialQty(DEFAULT_MATERIAL_QTY)
            .price(DEFAULT_PRICE)
            .tax(DEFAULT_TAX)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return poDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoDetails createUpdatedEntity(EntityManager em) {
        PoDetails poDetails = new PoDetails()
            .poDetailNum(UPDATED_PO_DETAIL_NUM)
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialName(UPDATED_MATERIAL_NAME)
            .materialQty(UPDATED_MATERIAL_QTY)
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return poDetails;
    }

    @BeforeEach
    public void initTest() {
        poDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoDetails() throws Exception {
        int databaseSizeBeforeCreate = poDetailsRepository.findAll().size();
        // Create the PoDetails
        restPoDetailsMockMvc.perform(post("/api/po-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poDetails)))
            .andExpect(status().isCreated());

        // Validate the PoDetails in the database
        List<PoDetails> poDetailsList = poDetailsRepository.findAll();
        assertThat(poDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PoDetails testPoDetails = poDetailsList.get(poDetailsList.size() - 1);
        assertThat(testPoDetails.getPoDetailNum()).isEqualTo(DEFAULT_PO_DETAIL_NUM);
        assertThat(testPoDetails.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testPoDetails.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
        assertThat(testPoDetails.getMaterialQty()).isEqualTo(DEFAULT_MATERIAL_QTY);
        assertThat(testPoDetails.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPoDetails.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testPoDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPoDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPoDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPoDetails.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPoDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poDetailsRepository.findAll().size();

        // Create the PoDetails with an existing ID
        poDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoDetailsMockMvc.perform(post("/api/po-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PoDetails in the database
        List<PoDetails> poDetailsList = poDetailsRepository.findAll();
        assertThat(poDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPoDetails() throws Exception {
        // Initialize the database
        poDetailsRepository.saveAndFlush(poDetails);

        // Get all the poDetailsList
        restPoDetailsMockMvc.perform(get("/api/po-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].poDetailNum").value(hasItem(DEFAULT_PO_DETAIL_NUM)))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].materialQty").value(hasItem(DEFAULT_MATERIAL_QTY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPoDetails() throws Exception {
        // Initialize the database
        poDetailsRepository.saveAndFlush(poDetails);

        // Get the poDetails
        restPoDetailsMockMvc.perform(get("/api/po-details/{id}", poDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poDetails.getId().intValue()))
            .andExpect(jsonPath("$.poDetailNum").value(DEFAULT_PO_DETAIL_NUM))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME))
            .andExpect(jsonPath("$.materialQty").value(DEFAULT_MATERIAL_QTY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPoDetails() throws Exception {
        // Get the poDetails
        restPoDetailsMockMvc.perform(get("/api/po-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoDetails() throws Exception {
        // Initialize the database
        poDetailsRepository.saveAndFlush(poDetails);

        int databaseSizeBeforeUpdate = poDetailsRepository.findAll().size();

        // Update the poDetails
        PoDetails updatedPoDetails = poDetailsRepository.findById(poDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPoDetails are not directly saved in db
        em.detach(updatedPoDetails);
        updatedPoDetails
            .poDetailNum(UPDATED_PO_DETAIL_NUM)
            .materialCode(UPDATED_MATERIAL_CODE)
            .materialName(UPDATED_MATERIAL_NAME)
            .materialQty(UPDATED_MATERIAL_QTY)
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPoDetailsMockMvc.perform(put("/api/po-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoDetails)))
            .andExpect(status().isOk());

        // Validate the PoDetails in the database
        List<PoDetails> poDetailsList = poDetailsRepository.findAll();
        assertThat(poDetailsList).hasSize(databaseSizeBeforeUpdate);
        PoDetails testPoDetails = poDetailsList.get(poDetailsList.size() - 1);
        assertThat(testPoDetails.getPoDetailNum()).isEqualTo(UPDATED_PO_DETAIL_NUM);
        assertThat(testPoDetails.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testPoDetails.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
        assertThat(testPoDetails.getMaterialQty()).isEqualTo(UPDATED_MATERIAL_QTY);
        assertThat(testPoDetails.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPoDetails.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testPoDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPoDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPoDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPoDetails.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPoDetails() throws Exception {
        int databaseSizeBeforeUpdate = poDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoDetailsMockMvc.perform(put("/api/po-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PoDetails in the database
        List<PoDetails> poDetailsList = poDetailsRepository.findAll();
        assertThat(poDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoDetails() throws Exception {
        // Initialize the database
        poDetailsRepository.saveAndFlush(poDetails);

        int databaseSizeBeforeDelete = poDetailsRepository.findAll().size();

        // Delete the poDetails
        restPoDetailsMockMvc.perform(delete("/api/po-details/{id}", poDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PoDetails> poDetailsList = poDetailsRepository.findAll();
        assertThat(poDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
