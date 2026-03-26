package com.richardkenway.cavernreborn.app.state;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.richardkenway.cavernreborn.core.state.PortalEntryReceipt;
import com.richardkenway.cavernreborn.core.state.PortalReturnOperation;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

public final class PortalLoopCoordinator {
    private final PlayerReturnStateRepository playerReturnStateRepository;
    private final WorldPortalIndexRepository worldPortalIndexRepository;

    public PortalLoopCoordinator(
        PlayerReturnStateRepository playerReturnStateRepository,
        WorldPortalIndexRepository worldPortalIndexRepository
    ) {
        this.playerReturnStateRepository = Objects.requireNonNull(playerReturnStateRepository, "playerReturnStateRepository");
        this.worldPortalIndexRepository = Objects.requireNonNull(worldPortalIndexRepository, "worldPortalIndexRepository");
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

        playerReturnStateRepository.save(playerId, returnState);
        registerPortal(returnState.returnDimensionId(), returnState.portalKey(), portalPlacement);

        return new PortalEntryReceipt(returnState, teleportContext);
    }

    public void registerPortal(String worldKey, String portalKey, PortalWorldIndex.PortalPlacement placement) {
        String normalizedWorldKey = requireText(worldKey, "worldKey");
        String normalizedPortalKey = requireText(portalKey, "portalKey");
        Objects.requireNonNull(placement, "placement");
        PortalWorldIndex currentIndex = worldPortalIndexRepository.load(normalizedWorldKey);
        worldPortalIndexRepository.save(normalizedWorldKey, currentIndex.withPortal(normalizedPortalKey, placement));
    }

    public Optional<PortalReturnOperation> planReturn(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");

        return playerReturnStateRepository.load(playerId)
            .map(returnState -> {
                PortalWorldIndex worldIndex = worldPortalIndexRepository.load(returnState.returnDimensionId());
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
