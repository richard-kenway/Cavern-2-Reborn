package com.richardkenway.cavernreborn.app.state;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

public final class InMemoryWorldPortalIndexRepository implements WorldPortalIndexRepository {
    private final Map<String, PortalWorldIndex> indices = new ConcurrentHashMap<>();

    @Override
    public PortalWorldIndex load(String worldKey) {
        return indices.getOrDefault(worldKey, PortalWorldIndex.empty());
    }

    @Override
    public void save(String worldKey, PortalWorldIndex worldIndex) {
        indices.put(worldKey, worldIndex);
    }
}
