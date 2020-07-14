package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class ProductionPlanDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionPlanDetails.class);
        ProductionPlanDetails productionPlanDetails1 = new ProductionPlanDetails();
        productionPlanDetails1.setId(1L);
        ProductionPlanDetails productionPlanDetails2 = new ProductionPlanDetails();
        productionPlanDetails2.setId(productionPlanDetails1.getId());
        assertThat(productionPlanDetails1).isEqualTo(productionPlanDetails2);
        productionPlanDetails2.setId(2L);
        assertThat(productionPlanDetails1).isNotEqualTo(productionPlanDetails2);
        productionPlanDetails1.setId(null);
        assertThat(productionPlanDetails1).isNotEqualTo(productionPlanDetails2);
    }
}
