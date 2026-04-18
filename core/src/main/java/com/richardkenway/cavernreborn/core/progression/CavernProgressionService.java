package com.richardkenway.cavernreborn.core.progression;

import java.util.Objects;
import java.util.UUID;

public final class CavernProgressionService {
    private final PlayerMiningProgressionStore progressionStore;

    public CavernProgressionService(PlayerMiningProgressionStore progressionStore) {
        this.progressionStore = Objects.requireNonNull(progressionStore, "progressionStore");
    }

    public CavernProgressionSnapshot inspect(UUID playerId) {
        return snapshot(progressionStore.load(Objects.requireNonNull(playerId, "playerId")));
    }

    public CavernProgressionUpdateResult recordMiningEvent(UUID playerId, String dimensionId, String blockId) {
        Objects.requireNonNull(playerId, "playerId");
        String normalizedBlockId = requireText(blockId, "blockId");
        CavernPlayerProgressionState currentState = progressionStore.load(playerId);
        CavernProgressionSnapshot previousSnapshot = snapshot(currentState);
        if (!CavernProgressionPolicy.countsTowardProgression(dimensionId, normalizedBlockId)) {
            return new CavernProgressionUpdateResult(previousSnapshot, previousSnapshot, false, normalizedBlockId, 0);
        }

        int scoreDelta = CavernProgressionPolicy.scoreForBlock(normalizedBlockId);
        CavernPlayerProgressionState updatedState = currentState.withMinedBlock(normalizedBlockId, scoreDelta);
        progressionStore.save(updatedState);
        return new CavernProgressionUpdateResult(previousSnapshot, snapshot(updatedState), true, normalizedBlockId, scoreDelta);
    }

    private static CavernProgressionSnapshot snapshot(CavernPlayerProgressionState state) {
        return new CavernProgressionSnapshot(
            state.playerId(),
            state.countedBlocks(),
            state.progressionScore(),
            CavernProgressionRank.forScore(state.progressionScore()),
            state.minedBlocksById()
        );
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
