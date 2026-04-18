package com.richardkenway.cavernreborn.core.progression;

import java.util.UUID;

public interface PlayerMiningProgressionStore {
    CavernPlayerProgressionState load(UUID playerId);

    void save(CavernPlayerProgressionState progressionState);

    void clear(UUID playerId);
}
