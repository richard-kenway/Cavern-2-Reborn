package com.richardkenway.cavernreborn.app.state;

import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

public interface WorldPortalIndexRepository {
    PortalWorldIndex load(String worldKey);

    void save(String worldKey, PortalWorldIndex worldIndex);
}
