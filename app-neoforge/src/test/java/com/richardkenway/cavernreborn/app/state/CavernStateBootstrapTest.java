package com.richardkenway.cavernreborn.app.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class CavernStateBootstrapTest {
    @Test
    void bootstrapExposesStateRepositories() {
        CavernStateBootstrap bootstrap = new CavernStateBootstrap();

        assertNotNull(bootstrap.playerReturnStateStore());
        assertNotNull(bootstrap.worldPortalIndexStore());
        assertNotNull(bootstrap.portalLoopService());
        assertEquals("Cavern Reborn return-state and portal wiring ready", bootstrap.describe());
    }
}
