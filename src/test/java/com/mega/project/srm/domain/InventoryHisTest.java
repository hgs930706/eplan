package com.mega.project.srm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mega.project.srm.web.rest.TestUtil;

public class InventoryHisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryHis.class);
        InventoryHis inventoryHis1 = new InventoryHis();
        inventoryHis1.setId(1L);
        InventoryHis inventoryHis2 = new InventoryHis();
        inventoryHis2.setId(inventoryHis1.getId());
        assertThat(inventoryHis1).isEqualTo(inventoryHis2);
        inventoryHis2.setId(2L);
        assertThat(inventoryHis1).isNotEqualTo(inventoryHis2);
        inventoryHis1.setId(null);
        assertThat(inventoryHis1).isNotEqualTo(inventoryHis2);
    }
}
