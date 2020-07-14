package com.mega.project.srm.web.rest;

import com.mega.project.srm.MegaframeworkApp;
import com.mega.project.srm.domain.SummaryMaterialReport;
import com.mega.project.srm.repository.SummaryMaterialReportRepository;

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
 * Integration tests for the {@link SummaryMaterialReportResource} REST controller.
 */
@SpringBootTest(classes = MegaframeworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SummaryMaterialReportResourceIT {

    private static final String DEFAULT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPONENT_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPONENT_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPONENT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPONENT_MATERIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_MATERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_MATERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SHIP_CYCLE = "AAAAAAAAAA";
    private static final String UPDATED_SHIP_CYCLE = "BBBBBBBBBB";

    private static final Double DEFAULT_USE_QTY = 1D;
    private static final Double UPDATED_USE_QTY = 2D;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final Double DEFAULT_STOCK_QTY = 1D;
    private static final Double UPDATED_STOCK_QTY = 2D;

    private static final String DEFAULT_ITEM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY = 1D;
    private static final Double UPDATED_QTY = 2D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SummaryMaterialReportRepository summaryMaterialReportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSummaryMaterialReportMockMvc;

    private SummaryMaterialReport summaryMaterialReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SummaryMaterialReport createEntity(EntityManager em) {
        SummaryMaterialReport summaryMaterialReport = new SummaryMaterialReport()
            .materialCode(DEFAULT_MATERIAL_CODE)
            .componentMaterialCode(DEFAULT_COMPONENT_MATERIAL_CODE)
            .componentMaterialName(DEFAULT_COMPONENT_MATERIAL_NAME)
            .customerMaterialCode(DEFAULT_CUSTOMER_MATERIAL_CODE)
            .vendorCode(DEFAULT_VENDOR_CODE)
            .shipCycle(DEFAULT_SHIP_CYCLE)
            .useQty(DEFAULT_USE_QTY)
            .unit(DEFAULT_UNIT)
            .stockQty(DEFAULT_STOCK_QTY)
            .itemType(DEFAULT_ITEM_TYPE)
            .date(DEFAULT_DATE)
            .qty(DEFAULT_QTY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return summaryMaterialReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SummaryMaterialReport createUpdatedEntity(EntityManager em) {
        SummaryMaterialReport summaryMaterialReport = new SummaryMaterialReport()
            .materialCode(UPDATED_MATERIAL_CODE)
            .componentMaterialCode(UPDATED_COMPONENT_MATERIAL_CODE)
            .componentMaterialName(UPDATED_COMPONENT_MATERIAL_NAME)
            .customerMaterialCode(UPDATED_CUSTOMER_MATERIAL_CODE)
            .vendorCode(UPDATED_VENDOR_CODE)
            .shipCycle(UPDATED_SHIP_CYCLE)
            .useQty(UPDATED_USE_QTY)
            .unit(UPDATED_UNIT)
            .stockQty(UPDATED_STOCK_QTY)
            .itemType(UPDATED_ITEM_TYPE)
            .date(UPDATED_DATE)
            .qty(UPDATED_QTY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return summaryMaterialReport;
    }

    @BeforeEach
    public void initTest() {
        summaryMaterialReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createSummaryMaterialReport() throws Exception {
        int databaseSizeBeforeCreate = summaryMaterialReportRepository.findAll().size();
        // Create the SummaryMaterialReport
        restSummaryMaterialReportMockMvc.perform(post("/api/summary-material-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(summaryMaterialReport)))
            .andExpect(status().isCreated());

        // Validate the SummaryMaterialReport in the database
        List<SummaryMaterialReport> summaryMaterialReportList = summaryMaterialReportRepository.findAll();
        assertThat(summaryMaterialReportList).hasSize(databaseSizeBeforeCreate + 1);
        SummaryMaterialReport testSummaryMaterialReport = summaryMaterialReportList.get(summaryMaterialReportList.size() - 1);
        assertThat(testSummaryMaterialReport.getMaterialCode()).isEqualTo(DEFAULT_MATERIAL_CODE);
        assertThat(testSummaryMaterialReport.getComponentMaterialCode()).isEqualTo(DEFAULT_COMPONENT_MATERIAL_CODE);
        assertThat(testSummaryMaterialReport.getComponentMaterialName()).isEqualTo(DEFAULT_COMPONENT_MATERIAL_NAME);
        assertThat(testSummaryMaterialReport.getCustomerMaterialCode()).isEqualTo(DEFAULT_CUSTOMER_MATERIAL_CODE);
        assertThat(testSummaryMaterialReport.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testSummaryMaterialReport.getShipCycle()).isEqualTo(DEFAULT_SHIP_CYCLE);
        assertThat(testSummaryMaterialReport.getUseQty()).isEqualTo(DEFAULT_USE_QTY);
        assertThat(testSummaryMaterialReport.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testSummaryMaterialReport.getStockQty()).isEqualTo(DEFAULT_STOCK_QTY);
        assertThat(testSummaryMaterialReport.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testSummaryMaterialReport.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSummaryMaterialReport.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testSummaryMaterialReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSummaryMaterialReport.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSummaryMaterialReport.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSummaryMaterialReport.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSummaryMaterialReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = summaryMaterialReportRepository.findAll().size();

        // Create the SummaryMaterialReport with an existing ID
        summaryMaterialReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSummaryMaterialReportMockMvc.perform(post("/api/summary-material-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(summaryMaterialReport)))
            .andExpect(status().isBadRequest());

        // Validate the SummaryMaterialReport in the database
        List<SummaryMaterialReport> summaryMaterialReportList = summaryMaterialReportRepository.findAll();
        assertThat(summaryMaterialReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSummaryMaterialReports() throws Exception {
        // Initialize the database
        summaryMaterialReportRepository.saveAndFlush(summaryMaterialReport);

        // Get all the summaryMaterialReportList
        restSummaryMaterialReportMockMvc.perform(get("/api/summary-material-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(summaryMaterialReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialCode").value(hasItem(DEFAULT_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].componentMaterialCode").value(hasItem(DEFAULT_COMPONENT_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].componentMaterialName").value(hasItem(DEFAULT_COMPONENT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].customerMaterialCode").value(hasItem(DEFAULT_CUSTOMER_MATERIAL_CODE)))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].shipCycle").value(hasItem(DEFAULT_SHIP_CYCLE)))
            .andExpect(jsonPath("$.[*].useQty").value(hasItem(DEFAULT_USE_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].stockQty").value(hasItem(DEFAULT_STOCK_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSummaryMaterialReport() throws Exception {
        // Initialize the database
        summaryMaterialReportRepository.saveAndFlush(summaryMaterialReport);

        // Get the summaryMaterialReport
        restSummaryMaterialReportMockMvc.perform(get("/api/summary-material-reports/{id}", summaryMaterialReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(summaryMaterialReport.getId().intValue()))
            .andExpect(jsonPath("$.materialCode").value(DEFAULT_MATERIAL_CODE))
            .andExpect(jsonPath("$.componentMaterialCode").value(DEFAULT_COMPONENT_MATERIAL_CODE))
            .andExpect(jsonPath("$.componentMaterialName").value(DEFAULT_COMPONENT_MATERIAL_NAME))
            .andExpect(jsonPath("$.customerMaterialCode").value(DEFAULT_CUSTOMER_MATERIAL_CODE))
            .andExpect(jsonPath("$.vendorCode").value(DEFAULT_VENDOR_CODE))
            .andExpect(jsonPath("$.shipCycle").value(DEFAULT_SHIP_CYCLE))
            .andExpect(jsonPath("$.useQty").value(DEFAULT_USE_QTY.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.stockQty").value(DEFAULT_STOCK_QTY.doubleValue()))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSummaryMaterialReport() throws Exception {
        // Get the summaryMaterialReport
        restSummaryMaterialReportMockMvc.perform(get("/api/summary-material-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSummaryMaterialReport() throws Exception {
        // Initialize the database
        summaryMaterialReportRepository.saveAndFlush(summaryMaterialReport);

        int databaseSizeBeforeUpdate = summaryMaterialReportRepository.findAll().size();

        // Update the summaryMaterialReport
        SummaryMaterialReport updatedSummaryMaterialReport = summaryMaterialReportRepository.findById(summaryMaterialReport.getId()).get();
        // Disconnect from session so that the updates on updatedSummaryMaterialReport are not directly saved in db
        em.detach(updatedSummaryMaterialReport);
        updatedSummaryMaterialReport
            .materialCode(UPDATED_MATERIAL_CODE)
            .componentMaterialCode(UPDATED_COMPONENT_MATERIAL_CODE)
            .componentMaterialName(UPDATED_COMPONENT_MATERIAL_NAME)
            .customerMaterialCode(UPDATED_CUSTOMER_MATERIAL_CODE)
            .vendorCode(UPDATED_VENDOR_CODE)
            .shipCycle(UPDATED_SHIP_CYCLE)
            .useQty(UPDATED_USE_QTY)
            .unit(UPDATED_UNIT)
            .stockQty(UPDATED_STOCK_QTY)
            .itemType(UPDATED_ITEM_TYPE)
            .date(UPDATED_DATE)
            .qty(UPDATED_QTY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSummaryMaterialReportMockMvc.perform(put("/api/summary-material-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSummaryMaterialReport)))
            .andExpect(status().isOk());

        // Validate the SummaryMaterialReport in the database
        List<SummaryMaterialReport> summaryMaterialReportList = summaryMaterialReportRepository.findAll();
        assertThat(summaryMaterialReportList).hasSize(databaseSizeBeforeUpdate);
        SummaryMaterialReport testSummaryMaterialReport = summaryMaterialReportList.get(summaryMaterialReportList.size() - 1);
        assertThat(testSummaryMaterialReport.getMaterialCode()).isEqualTo(UPDATED_MATERIAL_CODE);
        assertThat(testSummaryMaterialReport.getComponentMaterialCode()).isEqualTo(UPDATED_COMPONENT_MATERIAL_CODE);
        assertThat(testSummaryMaterialReport.getComponentMaterialName()).isEqualTo(UPDATED_COMPONENT_MATERIAL_NAME);
        assertThat(testSummaryMaterialReport.getCustomerMaterialCode()).isEqualTo(UPDATED_CUSTOMER_MATERIAL_CODE);
        assertThat(testSummaryMaterialReport.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testSummaryMaterialReport.getShipCycle()).isEqualTo(UPDATED_SHIP_CYCLE);
        assertThat(testSummaryMaterialReport.getUseQty()).isEqualTo(UPDATED_USE_QTY);
        assertThat(testSummaryMaterialReport.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testSummaryMaterialReport.getStockQty()).isEqualTo(UPDATED_STOCK_QTY);
        assertThat(testSummaryMaterialReport.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testSummaryMaterialReport.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSummaryMaterialReport.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testSummaryMaterialReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSummaryMaterialReport.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSummaryMaterialReport.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSummaryMaterialReport.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSummaryMaterialReport() throws Exception {
        int databaseSizeBeforeUpdate = summaryMaterialReportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSummaryMaterialReportMockMvc.perform(put("/api/summary-material-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(summaryMaterialReport)))
            .andExpect(status().isBadRequest());

        // Validate the SummaryMaterialReport in the database
        List<SummaryMaterialReport> summaryMaterialReportList = summaryMaterialReportRepository.findAll();
        assertThat(summaryMaterialReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSummaryMaterialReport() throws Exception {
        // Initialize the database
        summaryMaterialReportRepository.saveAndFlush(summaryMaterialReport);

        int databaseSizeBeforeDelete = summaryMaterialReportRepository.findAll().size();

        // Delete the summaryMaterialReport
        restSummaryMaterialReportMockMvc.perform(delete("/api/summary-material-reports/{id}", summaryMaterialReport.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SummaryMaterialReport> summaryMaterialReportList = summaryMaterialReportRepository.findAll();
        assertThat(summaryMaterialReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
