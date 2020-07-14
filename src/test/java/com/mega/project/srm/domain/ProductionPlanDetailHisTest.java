package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class ProductionPlanDetailHisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionPlanDetailHis.class);
        ProductionPlanDetailHis productionPlanDetailHis1 = new ProductionPlanDetailHis();
        productionPlanDetailHis1.setId(1L);
        ProductionPlanDetailHis productionPlanDetailHis2 = new ProductionPlanDetailHis();
        productionPlanDetailHis2.setId(productionPlanDetailHis1.getId());
        assertThat(productionPlanDetailHis1).isEqualTo(productionPlanDetailHis2);
        productionPlanDetailHis2.setId(2L);
        assertThat(productionPlanDetailHis1).isNotEqualTo(productionPlanDetailHis2);
        productionPlanDetailHis1.setId(null);
        assertThat(productionPlanDetailHis1).isNotEqualTo(productionPlanDetailHis2);
    }
}
