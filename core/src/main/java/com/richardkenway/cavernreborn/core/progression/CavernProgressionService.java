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

    public CavernProgressionSnapshot recordMiningEvent(UUID playerId, String dimensionId, String blockId) {
        Objects.requireNonNull(playerId, "playerId");
        CavernPlayerProgressionState currentState = progressionStore.load(playerId);
        if (!CavernProgressionPolicy.countsTowardProgression(dimensionId, blockId)) {
            return snapshot(currentState);
        }

        CavernPlayerProgressionState updatedState = currentState.withMinedBlock(blockId, CavernProgressionPolicy.scoreForBlock(blockId));
        progressionStore.save(updatedState);
        return snapshot(updatedState);
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
}
