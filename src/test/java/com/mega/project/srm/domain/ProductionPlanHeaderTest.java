package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class ProductionPlanHeaderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionPlanHeader.class);
        ProductionPlanHeader productionPlanHeader1 = new ProductionPlanHeader();
        productionPlanHeader1.setId(1L);
        ProductionPlanHeader productionPlanHeader2 = new ProductionPlanHeader();
        productionPlanHeader2.setId(productionPlanHeader1.getId());
        assertThat(productionPlanHeader1).isEqualTo(productionPlanHeader2);
        productionPlanHeader2.setId(2L);
        assertThat(productionPlanHeader1).isNotEqualTo(productionPlanHeader2);
        productionPlanHeader1.setId(null);
        assertThat(productionPlanHeader1).isNotEqualTo(productionPlanHeader2);
    }
}
