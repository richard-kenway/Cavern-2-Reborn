package com.richardkenway.cavernreborn.app.progression;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.richardkenway.cavernreborn.core.progression.CavernPlayerProgressionState;
import com.richardkenway.cavernreborn.core.progression.PlayerMiningProgressionStore;

public final class InMemoryPlayerMiningProgressionRepository implements PlayerMiningProgressionStore {
    private final Map<UUID, CavernPlayerProgressionState> states = new LinkedHashMap<>();

    @Override
    public CavernPlayerProgressionState load(UUID playerId) {
        UUID normalizedPlayerId = Objects.requireNonNull(playerId, "playerId");
        return states.getOrDefault(normalizedPlayerId, CavernPlayerProgressionState.empty(normalizedPlayerId));
    }

    @Override
    public void save(CavernPlayerProgressionState progressionState) {
        CavernPlayerProgressionState normalizedState = Objects.requireNonNull(progressionState, "progressionState");
        if (normalizedState.isEmpty()) {
            states.remove(normalizedState.playerId());
            return;
        }

        states.put(normalizedState.playerId(), normalizedState);
    }

    @Override
    public void clear(UUID playerId) {
        states.remove(Objects.requireNonNull(playerId, "playerId"));
    }
}
