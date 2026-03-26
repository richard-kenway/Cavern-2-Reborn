package com.richardkenway.cavernreborn.core.state;

import java.util.Optional;
import java.util.UUID;

public interface PlayerReturnStateStore {
    Optional<PortalReturnState> load(UUID playerId);

    void save(UUID playerId, PortalReturnState returnState);

    void clear(UUID playerId);
}
