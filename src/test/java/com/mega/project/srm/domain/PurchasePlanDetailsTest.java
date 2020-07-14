package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class PurchasePlanDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchasePlanDetails.class);
        PurchasePlanDetails purchasePlanDetails1 = new PurchasePlanDetails();
        purchasePlanDetails1.setId(1L);
        PurchasePlanDetails purchasePlanDetails2 = new PurchasePlanDetails();
        purchasePlanDetails2.setId(purchasePlanDetails1.getId());
        assertThat(purchasePlanDetails1).isEqualTo(purchasePlanDetails2);
        purchasePlanDetails2.setId(2L);
        assertThat(purchasePlanDetails1).isNotEqualTo(purchasePlanDetails2);
        purchasePlanDetails1.setId(null);
        assertThat(purchasePlanDetails1).isNotEqualTo(purchasePlanDetails2);
    }
}
