package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class MaterialUsageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialUsage.class);
        MaterialUsage materialUsage1 = new MaterialUsage();
        materialUsage1.setId(1L);
        MaterialUsage materialUsage2 = new MaterialUsage();
        materialUsage2.setId(materialUsage1.getId());
        assertThat(materialUsage1).isEqualTo(materialUsage2);
        materialUsage2.setId(2L);
        assertThat(materialUsage1).isNotEqualTo(materialUsage2);
        materialUsage1.setId(null);
        assertThat(materialUsage1).isNotEqualTo(materialUsage2);
    }
}
