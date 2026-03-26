package com.richardkenway.cavernreborn.app.state;

import com.richardkenway.cavernreborn.core.CavernProject;

public final class CavernStateBootstrap {
    private final PlayerReturnStateRepository playerReturnStateRepository;
    private final WorldPortalIndexRepository worldPortalIndexRepository;
    private final PortalLoopCoordinator portalLoopCoordinator;

    public CavernStateBootstrap() {
        this(new InMemoryPlayerReturnStateRepository(), new InMemoryWorldPortalIndexRepository());
    }

    public CavernStateBootstrap(
        PlayerReturnStateRepository playerReturnStateRepository,
        WorldPortalIndexRepository worldPortalIndexRepository
    ) {
        this.playerReturnStateRepository = playerReturnStateRepository;
        this.worldPortalIndexRepository = worldPortalIndexRepository;
        this.portalLoopCoordinator = new PortalLoopCoordinator(playerReturnStateRepository, worldPortalIndexRepository);
    }

    public PlayerReturnStateRepository playerReturnStateRepository() {
        return playerReturnStateRepository;
    }

    public WorldPortalIndexRepository worldPortalIndexRepository() {
        return worldPortalIndexRepository;
    }

    public PortalLoopCoordinator portalLoopCoordinator() {
        return portalLoopCoordinator;
    }

    public String describe() {
        return CavernProject.PROJECT_NAME + " return-state and portal wiring ready";
    }
}
