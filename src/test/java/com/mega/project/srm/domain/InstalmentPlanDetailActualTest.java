package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class InstalmentPlanDetailActualTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstalmentPlanDetailActual.class);
        InstalmentPlanDetailActual instalmentPlanDetailActual1 = new InstalmentPlanDetailActual();
        instalmentPlanDetailActual1.setId(1L);
        InstalmentPlanDetailActual instalmentPlanDetailActual2 = new InstalmentPlanDetailActual();
        instalmentPlanDetailActual2.setId(instalmentPlanDetailActual1.getId());
        assertThat(instalmentPlanDetailActual1).isEqualTo(instalmentPlanDetailActual2);
        instalmentPlanDetailActual2.setId(2L);
        assertThat(instalmentPlanDetailActual1).isNotEqualTo(instalmentPlanDetailActual2);
        instalmentPlanDetailActual1.setId(null);
        assertThat(instalmentPlanDetailActual1).isNotEqualTo(instalmentPlanDetailActual2);
    }
}
