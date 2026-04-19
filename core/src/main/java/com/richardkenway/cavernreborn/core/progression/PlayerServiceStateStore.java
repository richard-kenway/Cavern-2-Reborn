package com.richardkenway.cavernreborn.core.progression;

import java.util.UUID;

public interface PlayerServiceStateStore {
    CavernPlayerServiceState load(UUID playerId);

    void save(CavernPlayerServiceState serviceState);

    void clear(UUID playerId);
}
