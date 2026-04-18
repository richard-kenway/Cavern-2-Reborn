package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

class PortalFrameMaterialPolicyTest {
    @Test
    void exposesNamedFramePolicyConstants() throws Exception {
        Field field = PortalFrameMaterialPolicy.class.getDeclaredField("MOSSY_COBBLESTONE");
        Field stoneBricksField = PortalFrameMaterialPolicy.class.getDeclaredField("MOSSY_STONE_BRICKS");
        Field taggedField = PortalFrameMaterialPolicy.class.getDeclaredField("CAVERN_DEFAULT");

        assertNotNull(taggedField);
        assertNotNull(field);
        assertNotNull(stoneBricksField);
    }

    @Test
    void rejectsNullFrameMaterial() {
        assertThrows(NullPointerException.class, () -> PortalFrameMaterialPolicy.of(null));
    }
}
