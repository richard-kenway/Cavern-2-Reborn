package com.richardkenway.cavernreborn.app.state;

import com.richardkenway.cavernreborn.core.CavernProject;
import com.richardkenway.cavernreborn.core.state.PlayerReturnStateStore;
import com.richardkenway.cavernreborn.core.state.PortalLoopService;
import com.richardkenway.cavernreborn.core.state.WorldPortalIndexStore;

public final class CavernStateBootstrap {
    private final PlayerReturnStateStore playerReturnStateStore;
    private final WorldPortalIndexStore worldPortalIndexStore;
    private final PortalLoopService portalLoopService;

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

    public String describe() {
        return CavernProject.PROJECT_NAME + " return-state and portal wiring ready";
    }
}
