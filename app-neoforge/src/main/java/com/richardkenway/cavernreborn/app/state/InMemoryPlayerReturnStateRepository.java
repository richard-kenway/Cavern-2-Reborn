package com.richardkenway.cavernreborn.app.state;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.richardkenway.cavernreborn.data.state.PortalReturnStateData;

public final class InMemoryPlayerReturnStateRepository implements PlayerReturnStateRepository {
    private final Map<UUID, PortalReturnStateData> states = new ConcurrentHashMap<>();

    @Override
    public Optional<PortalReturnStateData> load(UUID playerId) {
        return Optional.ofNullable(states.get(playerId));
    }

    @Override
    public void save(UUID playerId, PortalReturnStateData returnState) {
        states.put(playerId, returnState);
    }

    @Override
    public void clear(UUID playerId) {
        states.remove(playerId);
    }
}
