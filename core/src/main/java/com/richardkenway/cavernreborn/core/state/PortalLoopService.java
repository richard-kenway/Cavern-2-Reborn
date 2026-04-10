package com.richardkenway.cavernreborn.core.state;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class PortalLoopService {
    private final PlayerReturnStateStore playerReturnStateStore;
    private final WorldPortalIndexStore worldPortalIndexStore;

    public PortalLoopService(
        PlayerReturnStateStore playerReturnStateStore,
        WorldPortalIndexStore worldPortalIndexStore
    ) {
        this.playerReturnStateStore = Objects.requireNonNull(playerReturnStateStore, "playerReturnStateStore");
        this.worldPortalIndexStore = Objects.requireNonNull(worldPortalIndexStore, "worldPortalIndexStore");
    }

    public PortalEntryReceipt captureEntry(
        UUID playerId,
        PortalReturnState returnState,
        TeleportContext teleportContext,
        PortalWorldIndex.PortalPlacement portalPlacement
    ) {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(returnState, "returnState");
        Objects.requireNonNull(teleportContext, "teleportContext");
        Objects.requireNonNull(portalPlacement, "portalPlacement");

        if (!returnState.portalKey().equals(teleportContext.portalKey())) {
            throw new IllegalArgumentException("returnState and teleportContext must use the same portalKey");
        }

        playerReturnStateStore.save(playerId, returnState);
        registerPortal(returnState.returnDimensionId(), returnState.portalKey(), portalPlacement);

        return new PortalEntryReceipt(returnState, teleportContext, portalPlacement);
    }

    public void registerPortal(String worldKey, String portalKey, PortalWorldIndex.PortalPlacement placement) {
        String normalizedWorldKey = requireText(worldKey, "worldKey");
        String normalizedPortalKey = requireText(portalKey, "portalKey");
        Objects.requireNonNull(placement, "placement");

        PortalWorldIndex currentIndex = worldPortalIndexStore.load(normalizedWorldKey);
        worldPortalIndexStore.save(normalizedWorldKey, currentIndex.withPortal(normalizedPortalKey, placement));
    }

    public Optional<PortalReturnOperation> planReturn(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");

        return playerReturnStateStore.load(playerId)
            .map(returnState -> {
                PortalWorldIndex worldIndex = worldPortalIndexStore.load(returnState.returnDimensionId());
                return new PortalReturnOperation(returnState, worldIndex.firstPlacementFor(returnState.portalKey()));
            });
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }
}
