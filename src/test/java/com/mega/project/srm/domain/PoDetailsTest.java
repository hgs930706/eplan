package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class PoDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoDetails.class);
        PoDetails poDetails1 = new PoDetails();
        poDetails1.setId(1L);
        PoDetails poDetails2 = new PoDetails();
        poDetails2.setId(poDetails1.getId());
        assertThat(poDetails1).isEqualTo(poDetails2);
        poDetails2.setId(2L);
        assertThat(poDetails1).isNotEqualTo(poDetails2);
        poDetails1.setId(null);
        assertThat(poDetails1).isNotEqualTo(poDetails2);
    }
}
