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
        assertNotNull(bootstrap.cavernDimensionTravelPlanner());
        assertNotNull(bootstrap.cavernTravelBridge());
        assertNotNull(bootstrap.cavernPortalInteractionService());
        assertEquals("Cavern Reborn return-state, portal block flow, safe-arrival checks and CAVERN dimension travel bridge ready", bootstrap.describe());
    }
}
