package com.richardkenway.cavernreborn.app.state;

import com.richardkenway.cavernreborn.app.dimension.CavernTravelBridge;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionService;
import com.richardkenway.cavernreborn.app.portal.PortalInteractionCooldowns;
import com.richardkenway.cavernreborn.core.CavernProject;
import com.richardkenway.cavernreborn.core.state.CavernDimensionTravelPlanner;
import com.richardkenway.cavernreborn.core.state.PlayerReturnStateStore;
import com.richardkenway.cavernreborn.core.state.PortalLoopService;
import com.richardkenway.cavernreborn.core.state.WorldPortalIndexStore;

public final class CavernStateBootstrap {
    private final PlayerReturnStateStore playerReturnStateStore;
    private final WorldPortalIndexStore worldPortalIndexStore;
    private final PortalLoopService portalLoopService;
    private final CavernDimensionTravelPlanner cavernDimensionTravelPlanner;
    private final CavernTravelBridge cavernTravelBridge;
    private final PortalInteractionCooldowns portalInteractionCooldowns;
    private final CavernPortalInteractionService cavernPortalInteractionService;

    public CavernStateBootstrap() {
        this(new SavedDataBackedPlayerReturnStateRepository(), new SavedDataBackedWorldPortalIndexRepository());
    }

    public CavernStateBootstrap(
        PlayerReturnStateStore playerReturnStateStore,
        WorldPortalIndexStore worldPortalIndexStore
    ) {
        this.playerReturnStateStore = playerReturnStateStore;
        this.worldPortalIndexStore = worldPortalIndexStore;
        this.portalInteractionCooldowns = new PortalInteractionCooldowns();
        this.portalLoopService = new PortalLoopService(playerReturnStateStore, worldPortalIndexStore);
        this.cavernDimensionTravelPlanner = new CavernDimensionTravelPlanner(portalLoopService);
        this.cavernTravelBridge = new CavernTravelBridge(cavernDimensionTravelPlanner, worldPortalIndexStore);
        this.cavernPortalInteractionService = new CavernPortalInteractionService(cavernTravelBridge, portalInteractionCooldowns);
    }

    public PlayerReturnStateStore playerReturnStateStore() {
        return playerReturnStateStore;
    }

    public WorldPortalIndexStore worldPortalIndexStore() {
        return worldPortalIndexStore;
    }

    public PortalLoopService portalLoopService() {
        return portalLoopService;
    }

    public CavernDimensionTravelPlanner cavernDimensionTravelPlanner() {
        return cavernDimensionTravelPlanner;
    }

    public CavernTravelBridge cavernTravelBridge() {
        return cavernTravelBridge;
    }

    public PortalInteractionCooldowns portalInteractionCooldowns() {
        return portalInteractionCooldowns;
    }

    public CavernPortalInteractionService cavernPortalInteractionService() {
        return cavernPortalInteractionService;
    }

    public String describe() {
        return CavernProject.PROJECT_NAME + " return-state, portal block flow, safe-arrival checks and CAVERN dimension travel bridge ready";
    }
}
