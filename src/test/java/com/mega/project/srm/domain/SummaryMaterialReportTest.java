package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class SummaryMaterialReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SummaryMaterialReport.class);
        SummaryMaterialReport summaryMaterialReport1 = new SummaryMaterialReport();
        summaryMaterialReport1.setId(1L);
        SummaryMaterialReport summaryMaterialReport2 = new SummaryMaterialReport();
        summaryMaterialReport2.setId(summaryMaterialReport1.getId());
        assertThat(summaryMaterialReport1).isEqualTo(summaryMaterialReport2);
        summaryMaterialReport2.setId(2L);
        assertThat(summaryMaterialReport1).isNotEqualTo(summaryMaterialReport2);
        summaryMaterialReport1.setId(null);
        assertThat(summaryMaterialReport1).isNotEqualTo(summaryMaterialReport2);
    }
}
