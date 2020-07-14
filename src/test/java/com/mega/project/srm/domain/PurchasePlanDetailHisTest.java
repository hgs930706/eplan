package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class PurchasePlanDetailHisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchasePlanDetailHis.class);
        PurchasePlanDetailHis purchasePlanDetailHis1 = new PurchasePlanDetailHis();
        purchasePlanDetailHis1.setId(1L);
        PurchasePlanDetailHis purchasePlanDetailHis2 = new PurchasePlanDetailHis();
        purchasePlanDetailHis2.setId(purchasePlanDetailHis1.getId());
        assertThat(purchasePlanDetailHis1).isEqualTo(purchasePlanDetailHis2);
        purchasePlanDetailHis2.setId(2L);
        assertThat(purchasePlanDetailHis1).isNotEqualTo(purchasePlanDetailHis2);
        purchasePlanDetailHis1.setId(null);
        assertThat(purchasePlanDetailHis1).isNotEqualTo(purchasePlanDetailHis2);
    }
}
