package com.richardkenway.cavernreborn.app.state;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.richardkenway.cavernreborn.data.state.PortalWorldIndexData;

public final class InMemoryWorldPortalIndexRepository implements WorldPortalIndexRepository {
    private final Map<String, PortalWorldIndexData> indices = new ConcurrentHashMap<>();

    @Override
    public PortalWorldIndexData load(String worldKey) {
        return indices.getOrDefault(worldKey, PortalWorldIndexData.empty());
    }

    @Override
    public void save(String worldKey, PortalWorldIndexData worldIndex) {
        indices.put(worldKey, worldIndex);
    }
}
