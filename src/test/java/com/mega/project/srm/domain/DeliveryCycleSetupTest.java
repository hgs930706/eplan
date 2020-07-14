package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class DeliveryCycleSetupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryCycleSetup.class);
        DeliveryCycleSetup deliveryCycleSetup1 = new DeliveryCycleSetup();
        deliveryCycleSetup1.setId(1L);
        DeliveryCycleSetup deliveryCycleSetup2 = new DeliveryCycleSetup();
        deliveryCycleSetup2.setId(deliveryCycleSetup1.getId());
        assertThat(deliveryCycleSetup1).isEqualTo(deliveryCycleSetup2);
        deliveryCycleSetup2.setId(2L);
        assertThat(deliveryCycleSetup1).isNotEqualTo(deliveryCycleSetup2);
        deliveryCycleSetup1.setId(null);
        assertThat(deliveryCycleSetup1).isNotEqualTo(deliveryCycleSetup2);
    }
}
