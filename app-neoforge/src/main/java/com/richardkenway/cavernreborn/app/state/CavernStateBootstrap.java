package com.richardkenway.cavernreborn.app.state;

import com.richardkenway.cavernreborn.core.CavernProject;

public final class CavernStateBootstrap {
    private final PlayerReturnStateRepository playerReturnStateRepository;
    private final WorldPortalIndexRepository worldPortalIndexRepository;

    public CavernStateBootstrap() {
        this(new InMemoryPlayerReturnStateRepository(), new InMemoryWorldPortalIndexRepository());
    }

    public CavernStateBootstrap(
        PlayerReturnStateRepository playerReturnStateRepository,
        WorldPortalIndexRepository worldPortalIndexRepository
    ) {
        this.playerReturnStateRepository = playerReturnStateRepository;
        this.worldPortalIndexRepository = worldPortalIndexRepository;
    }

    public PlayerReturnStateRepository playerReturnStateRepository() {
        return playerReturnStateRepository;
    }

    public WorldPortalIndexRepository worldPortalIndexRepository() {
        return worldPortalIndexRepository;
    }

    public String describe() {
        return CavernProject.PROJECT_NAME + " return-state skeleton ready";
    }
}
