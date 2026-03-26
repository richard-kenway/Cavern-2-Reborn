package com.richardkenway.cavernreborn.app.state;

import com.richardkenway.cavernreborn.data.state.PortalWorldIndexData;

public interface WorldPortalIndexRepository {
    PortalWorldIndexData load(String worldKey);

    void save(String worldKey, PortalWorldIndexData worldIndex);
}
