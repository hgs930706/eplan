package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class PurchasePlanHeaderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchasePlanHeader.class);
        PurchasePlanHeader purchasePlanHeader1 = new PurchasePlanHeader();
        purchasePlanHeader1.setId(1L);
        PurchasePlanHeader purchasePlanHeader2 = new PurchasePlanHeader();
        purchasePlanHeader2.setId(purchasePlanHeader1.getId());
        assertThat(purchasePlanHeader1).isEqualTo(purchasePlanHeader2);
        purchasePlanHeader2.setId(2L);
        assertThat(purchasePlanHeader1).isNotEqualTo(purchasePlanHeader2);
        purchasePlanHeader1.setId(null);
        assertThat(purchasePlanHeader1).isNotEqualTo(purchasePlanHeader2);
    }
}
