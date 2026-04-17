package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

class PortalFrameMaterialPolicyTest {
    @Test
    void exposesNamedMossyCobblestonePolicyConstant() throws Exception {
        Field field = PortalFrameMaterialPolicy.class.getDeclaredField("MOSSY_COBBLESTONE");

        assertNotNull(field);
    }

    @Test
    void rejectsNullFrameMaterial() {
        assertThrows(NullPointerException.class, () -> PortalFrameMaterialPolicy.of(null));
    }
}
