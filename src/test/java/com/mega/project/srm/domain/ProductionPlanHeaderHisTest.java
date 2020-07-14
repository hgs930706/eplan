package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class ProductionPlanHeaderHisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionPlanHeaderHis.class);
        ProductionPlanHeaderHis productionPlanHeaderHis1 = new ProductionPlanHeaderHis();
        productionPlanHeaderHis1.setId(1L);
        ProductionPlanHeaderHis productionPlanHeaderHis2 = new ProductionPlanHeaderHis();
        productionPlanHeaderHis2.setId(productionPlanHeaderHis1.getId());
        assertThat(productionPlanHeaderHis1).isEqualTo(productionPlanHeaderHis2);
        productionPlanHeaderHis2.setId(2L);
        assertThat(productionPlanHeaderHis1).isNotEqualTo(productionPlanHeaderHis2);
        productionPlanHeaderHis1.setId(null);
        assertThat(productionPlanHeaderHis1).isNotEqualTo(productionPlanHeaderHis2);
    }
}
