package com.richardkenway.cavernreborn.app.state;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.richardkenway.cavernreborn.core.state.PlayerReturnStateStore;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;

public final class InMemoryPlayerReturnStateRepository implements PlayerReturnStateStore {
    private final Map<UUID, PortalReturnState> states = new ConcurrentHashMap<>();

    @Override
    public Optional<PortalReturnState> load(UUID playerId) {
        return Optional.ofNullable(states.get(playerId));
    }

    @Override
    public void save(UUID playerId, PortalReturnState returnState) {
        states.put(playerId, returnState);
    }

    @Override
    public void clear(UUID playerId) {
        states.remove(playerId);
    }
}
