package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class OutStockSetupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OutStockSetup.class);
        OutStockSetup outStockSetup1 = new OutStockSetup();
        outStockSetup1.setId(1L);
        OutStockSetup outStockSetup2 = new OutStockSetup();
        outStockSetup2.setId(outStockSetup1.getId());
        assertThat(outStockSetup1).isEqualTo(outStockSetup2);
        outStockSetup2.setId(2L);
        assertThat(outStockSetup1).isNotEqualTo(outStockSetup2);
        outStockSetup1.setId(null);
        assertThat(outStockSetup1).isNotEqualTo(outStockSetup2);
    }
}
