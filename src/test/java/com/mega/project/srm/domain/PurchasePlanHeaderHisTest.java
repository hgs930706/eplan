package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class PurchasePlanHeaderHisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchasePlanHeaderHis.class);
        PurchasePlanHeaderHis purchasePlanHeaderHis1 = new PurchasePlanHeaderHis();
        purchasePlanHeaderHis1.setId(1L);
        PurchasePlanHeaderHis purchasePlanHeaderHis2 = new PurchasePlanHeaderHis();
        purchasePlanHeaderHis2.setId(purchasePlanHeaderHis1.getId());
        assertThat(purchasePlanHeaderHis1).isEqualTo(purchasePlanHeaderHis2);
        purchasePlanHeaderHis2.setId(2L);
        assertThat(purchasePlanHeaderHis1).isNotEqualTo(purchasePlanHeaderHis2);
        purchasePlanHeaderHis1.setId(null);
        assertThat(purchasePlanHeaderHis1).isNotEqualTo(purchasePlanHeaderHis2);
    }
}
