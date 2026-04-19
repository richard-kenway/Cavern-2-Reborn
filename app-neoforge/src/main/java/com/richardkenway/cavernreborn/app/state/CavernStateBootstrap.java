package com.richardkenway.cavernreborn.app.state;

import com.richardkenway.cavernreborn.app.dimension.CavernTravelBridge;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionService;
import com.richardkenway.cavernreborn.app.portal.CavernNonPlayerPortalInteractionService;
import com.richardkenway.cavernreborn.app.portal.PortalInteractionCooldowns;
import com.richardkenway.cavernreborn.app.progression.InMemoryPlayerClaimedRewardRepository;
import com.richardkenway.cavernreborn.app.progression.InMemoryPlayerServiceStateRepository;
import com.richardkenway.cavernreborn.app.progression.SavedDataBackedPlayerServiceStateRepository;
import com.richardkenway.cavernreborn.app.progression.InMemoryPlayerMiningProgressionRepository;
import com.richardkenway.cavernreborn.app.progression.SavedDataBackedPlayerClaimedRewardRepository;
import com.richardkenway.cavernreborn.app.progression.SavedDataBackedPlayerMiningProgressionRepository;
import com.richardkenway.cavernreborn.app.progression.CavernCatalogAccess;
import com.richardkenway.cavernreborn.app.progression.CavernCatalogGuiOpener;
import com.richardkenway.cavernreborn.app.progression.CavernRewardGranter;
import com.richardkenway.cavernreborn.core.progression.CavernRewardService;
import com.richardkenway.cavernreborn.core.CavernProject;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernInteractionService;
import com.richardkenway.cavernreborn.core.progression.PlayerClaimedRewardStore;
import com.richardkenway.cavernreborn.core.progression.PlayerServiceStateStore;
import com.richardkenway.cavernreborn.core.progression.PlayerMiningProgressionStore;
import com.richardkenway.cavernreborn.core.state.CavernDimensionTravelPlanner;
import com.richardkenway.cavernreborn.core.state.PlayerReturnStateStore;
import com.richardkenway.cavernreborn.core.state.PortalLoopService;
import com.richardkenway.cavernreborn.core.state.WorldPortalIndexStore;

public final class CavernStateBootstrap {
    private final PlayerReturnStateStore playerReturnStateStore;
    private final WorldPortalIndexStore worldPortalIndexStore;
    private final PlayerMiningProgressionStore playerMiningProgressionStore;
    private final PlayerClaimedRewardStore playerClaimedRewardStore;
    private final PortalLoopService portalLoopService;
    private final CavernDimensionTravelPlanner cavernDimensionTravelPlanner;
    private final CavernTravelBridge cavernTravelBridge;
    private final PortalInteractionCooldowns portalInteractionCooldowns;
    private final CavernPortalInteractionService cavernPortalInteractionService;
    private final CavernNonPlayerPortalInteractionService nonPlayerPortalInteractionService;
    private final CavernProgressionService cavernProgressionService;
    private final CavernRewardService cavernRewardService;
    private final PlayerServiceStateStore playerServiceStateStore;
    private final CavernInteractionService cavernInteractionService;
    private final CavernCatalogGuiOpener cavernCatalogGuiOpener;

    public CavernStateBootstrap() {
        this(
            new SavedDataBackedPlayerReturnStateRepository(),
            new SavedDataBackedWorldPortalIndexRepository(),
            new SavedDataBackedPlayerMiningProgressionRepository(),
            new SavedDataBackedPlayerClaimedRewardRepository(),
            new SavedDataBackedPlayerServiceStateRepository()
        );
    }

    public CavernStateBootstrap(
        PlayerReturnStateStore playerReturnStateStore,
        WorldPortalIndexStore worldPortalIndexStore
    ) {
        this(
            playerReturnStateStore,
            worldPortalIndexStore,
            new InMemoryPlayerMiningProgressionRepository()
        );
    }

    public CavernStateBootstrap(
        PlayerReturnStateStore playerReturnStateStore,
        WorldPortalIndexStore worldPortalIndexStore,
        PlayerMiningProgressionStore playerMiningProgressionStore
    ) {
        this(
            playerReturnStateStore,
            worldPortalIndexStore,
            playerMiningProgressionStore,
            new InMemoryPlayerClaimedRewardRepository(),
            new InMemoryPlayerServiceStateRepository()
        );
    }

    public CavernStateBootstrap(
        PlayerReturnStateStore playerReturnStateStore,
        WorldPortalIndexStore worldPortalIndexStore,
        PlayerMiningProgressionStore playerMiningProgressionStore,
        PlayerClaimedRewardStore playerClaimedRewardStore,
        PlayerServiceStateStore playerServiceStateStore
    ) {
        this.playerReturnStateStore = playerReturnStateStore;
        this.worldPortalIndexStore = worldPortalIndexStore;
        this.playerMiningProgressionStore = playerMiningProgressionStore;
        this.playerClaimedRewardStore = playerClaimedRewardStore;
        this.playerServiceStateStore = playerServiceStateStore;
        this.portalInteractionCooldowns = new PortalInteractionCooldowns();
        this.portalLoopService = new PortalLoopService(playerReturnStateStore, worldPortalIndexStore);
        this.cavernDimensionTravelPlanner = new CavernDimensionTravelPlanner(portalLoopService);
        this.cavernTravelBridge = new CavernTravelBridge(cavernDimensionTravelPlanner, worldPortalIndexStore);
        this.cavernPortalInteractionService = new CavernPortalInteractionService(cavernTravelBridge, portalInteractionCooldowns);
        this.nonPlayerPortalInteractionService = new CavernNonPlayerPortalInteractionService(
            cavernTravelBridge,
            portalLoopService,
            worldPortalIndexStore
        );
        this.cavernProgressionService = new CavernProgressionService(playerMiningProgressionStore);
        this.cavernRewardService = new CavernRewardService(playerClaimedRewardStore);
        this.cavernInteractionService = new CavernInteractionService(playerClaimedRewardStore, playerServiceStateStore);
        this.cavernCatalogGuiOpener = new CavernCatalogGuiOpener(
            new CavernCatalogAccess(cavernProgressionService, cavernInteractionService, new CavernRewardGranter())
        );
    }

    public PlayerReturnStateStore playerReturnStateStore() {
        return playerReturnStateStore;
    }

    public WorldPortalIndexStore worldPortalIndexStore() {
        return worldPortalIndexStore;
    }

    public PlayerMiningProgressionStore playerMiningProgressionStore() {
        return playerMiningProgressionStore;
    }

    public PlayerClaimedRewardStore playerClaimedRewardStore() {
        return playerClaimedRewardStore;
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

    public CavernNonPlayerPortalInteractionService nonPlayerPortalInteractionService() {
        return nonPlayerPortalInteractionService;
    }

    public CavernProgressionService cavernProgressionService() {
        return cavernProgressionService;
    }

    public CavernRewardService cavernRewardService() {
        return cavernRewardService;
    }

    public PlayerServiceStateStore playerServiceStateStore() {
        return playerServiceStateStore;
    }

    public CavernInteractionService cavernInteractionService() {
        return cavernInteractionService;
    }

    public CavernCatalogGuiOpener cavernCatalogGuiOpener() {
        return cavernCatalogGuiOpener;
    }

    public String describe() {
        return CavernProject.PROJECT_NAME
            + " return-state, portal block flow, safe-arrival checks, CAVERN dimension travel bridge and mining progression/reward shell ready";
    }
}
