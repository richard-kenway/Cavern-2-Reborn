package com.richardkenway.cavernreborn.app.progression;

import com.richardkenway.cavernreborn.core.progression.CavernPlayerServiceState;
import com.richardkenway.cavernreborn.core.progression.PlayerServiceStateStore;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class InMemoryPlayerServiceStateRepository implements PlayerServiceStateStore {
    private final Map<UUID, CavernPlayerServiceState> states = new LinkedHashMap<>();

    @Override
    public CavernPlayerServiceState load(UUID playerId) {
        return states.getOrDefault(playerId, CavernPlayerServiceState.empty(playerId));
    }

    @Override
    public void save(CavernPlayerServiceState serviceState) {
        if (serviceState.isEmpty()) {
            states.remove(serviceState.playerId());
            return;
        }
        states.put(serviceState.playerId(), serviceState);
    }

    @Override
    public void clear(UUID playerId) {
        states.remove(playerId);
    }
}
