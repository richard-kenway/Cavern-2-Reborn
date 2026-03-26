package com.richardkenway.cavernreborn.core.state;

public interface WorldPortalIndexStore {
    PortalWorldIndex load(String worldKey);

    void save(String worldKey, PortalWorldIndex worldIndex);
}
