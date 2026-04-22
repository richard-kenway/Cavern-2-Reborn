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
        return recordMiningEvent(playerId, dimensionId, blockId, 0);
    }

    public CavernProgressionUpdateResult recordMiningEvent(UUID playerId, String dimensionId, String blockId, int bonusScoreDelta) {
        Objects.requireNonNull(playerId, "playerId");
        String normalizedBlockId = requireText(blockId, "blockId");
        if (bonusScoreDelta < 0) {
            throw new IllegalArgumentException("bonusScoreDelta must not be negative");
        }
        CavernPlayerProgressionState currentState = progressionStore.load(playerId);
        CavernProgressionSnapshot previousSnapshot = snapshot(currentState);
        if (!CavernProgressionPolicy.countsTowardProgression(dimensionId, normalizedBlockId)) {
            return new CavernProgressionUpdateResult(previousSnapshot, previousSnapshot, false, normalizedBlockId, 0);
        }

        int baseScoreDelta = CavernProgressionPolicy.scoreForBlock(normalizedBlockId);
        CavernPlayerProgressionState updatedState = currentState.withMinedBlock(normalizedBlockId, baseScoreDelta, bonusScoreDelta);
        progressionStore.save(updatedState);
        return new CavernProgressionUpdateResult(
            previousSnapshot,
            snapshot(updatedState),
            true,
            normalizedBlockId,
            baseScoreDelta + bonusScoreDelta
        );
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
