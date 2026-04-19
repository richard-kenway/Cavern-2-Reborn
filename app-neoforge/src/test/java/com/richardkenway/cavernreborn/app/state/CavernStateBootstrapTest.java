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
        assertNotNull(bootstrap.playerMiningProgressionStore());
        assertNotNull(bootstrap.playerClaimedRewardStore());
        assertNotNull(bootstrap.portalLoopService());
        assertNotNull(bootstrap.cavernDimensionTravelPlanner());
        assertNotNull(bootstrap.cavernTravelBridge());
        assertNotNull(bootstrap.cavernPortalInteractionService());
        assertNotNull(bootstrap.nonPlayerPortalInteractionService());
        assertNotNull(bootstrap.cavernProgressionService());
        assertNotNull(bootstrap.cavernRewardService());
        assertEquals(
            "Cavern II: Reborn return-state, portal block flow, safe-arrival checks, CAVERN dimension travel bridge and mining progression/reward shell ready",
            bootstrap.describe()
        );
    }
}
