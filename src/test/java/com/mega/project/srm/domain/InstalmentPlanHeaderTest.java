package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class InstalmentPlanHeaderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstalmentPlanHeader.class);
        InstalmentPlanHeader instalmentPlanHeader1 = new InstalmentPlanHeader();
        instalmentPlanHeader1.setId(1L);
        InstalmentPlanHeader instalmentPlanHeader2 = new InstalmentPlanHeader();
        instalmentPlanHeader2.setId(instalmentPlanHeader1.getId());
        assertThat(instalmentPlanHeader1).isEqualTo(instalmentPlanHeader2);
        instalmentPlanHeader2.setId(2L);
        assertThat(instalmentPlanHeader1).isNotEqualTo(instalmentPlanHeader2);
        instalmentPlanHeader1.setId(null);
        assertThat(instalmentPlanHeader1).isNotEqualTo(instalmentPlanHeader2);
    }
}
