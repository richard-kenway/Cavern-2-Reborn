package com.richardkenway.cavernreborn.app.state;

import java.util.Optional;
import java.util.UUID;

import com.richardkenway.cavernreborn.data.state.PortalReturnStateData;

public interface PlayerReturnStateRepository {
    Optional<PortalReturnStateData> load(UUID playerId);

    void save(UUID playerId, PortalReturnStateData returnState);

    void clear(UUID playerId);
}
