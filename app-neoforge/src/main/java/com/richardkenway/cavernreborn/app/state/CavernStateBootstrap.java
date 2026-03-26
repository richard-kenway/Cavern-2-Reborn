package com.richardkenway.cavernreborn.app.state;

import com.richardkenway.cavernreborn.app.dimension.CavernTravelBridge;
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

    public CavernStateBootstrap() {
        this(new InMemoryPlayerReturnStateRepository(), new InMemoryWorldPortalIndexRepository());
    }

    public CavernStateBootstrap(
        PlayerReturnStateStore playerReturnStateStore,
        WorldPortalIndexStore worldPortalIndexStore
    ) {
        this.playerReturnStateStore = playerReturnStateStore;
        this.worldPortalIndexStore = worldPortalIndexStore;
        this.portalLoopService = new PortalLoopService(playerReturnStateStore, worldPortalIndexStore);
        this.cavernDimensionTravelPlanner = new CavernDimensionTravelPlanner(portalLoopService);
        this.cavernTravelBridge = new CavernTravelBridge(cavernDimensionTravelPlanner);
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

    public String describe() {
        return CavernProject.PROJECT_NAME + " return-state, portal wiring and CAVERN dimension travel bridge ready";
    }
}
