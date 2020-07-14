package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class MaterialUsageSummaryHeaderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialUsageSummaryHeader.class);
        MaterialUsageSummaryHeader materialUsageSummaryHeader1 = new MaterialUsageSummaryHeader();
        materialUsageSummaryHeader1.setId(1L);
        MaterialUsageSummaryHeader materialUsageSummaryHeader2 = new MaterialUsageSummaryHeader();
        materialUsageSummaryHeader2.setId(materialUsageSummaryHeader1.getId());
        assertThat(materialUsageSummaryHeader1).isEqualTo(materialUsageSummaryHeader2);
        materialUsageSummaryHeader2.setId(2L);
        assertThat(materialUsageSummaryHeader1).isNotEqualTo(materialUsageSummaryHeader2);
        materialUsageSummaryHeader1.setId(null);
        assertThat(materialUsageSummaryHeader1).isNotEqualTo(materialUsageSummaryHeader2);
    }
}
