package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class InstalmentPlanDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstalmentPlanDetails.class);
        InstalmentPlanDetails instalmentPlanDetails1 = new InstalmentPlanDetails();
        instalmentPlanDetails1.setId(1L);
        InstalmentPlanDetails instalmentPlanDetails2 = new InstalmentPlanDetails();
        instalmentPlanDetails2.setId(instalmentPlanDetails1.getId());
        assertThat(instalmentPlanDetails1).isEqualTo(instalmentPlanDetails2);
        instalmentPlanDetails2.setId(2L);
        assertThat(instalmentPlanDetails1).isNotEqualTo(instalmentPlanDetails2);
        instalmentPlanDetails1.setId(null);
        assertThat(instalmentPlanDetails1).isNotEqualTo(instalmentPlanDetails2);
    }
}
