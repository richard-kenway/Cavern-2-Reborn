package com.richardkenway.cavernreborn.app.state;

import java.util.Optional;
import java.util.UUID;

import com.richardkenway.cavernreborn.core.state.PortalReturnState;

public interface PlayerReturnStateRepository {
    Optional<PortalReturnState> load(UUID playerId);

    void save(UUID playerId, PortalReturnState returnState);

    void clear(UUID playerId);
}
