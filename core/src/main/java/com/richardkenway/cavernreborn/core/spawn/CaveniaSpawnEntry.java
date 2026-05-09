package com.richardkenway.cavernreborn.core.spawn;

import java.util.Objects;

public record CaveniaSpawnEntry(
    String legacyClassName,
    String rebornEntityId,
    int weight,
    int minGroupSize,
    int maxGroupSize
) {
    public CaveniaSpawnEntry {
        legacyClassName = Objects.requireNonNull(legacyClassName, "legacyClassName");
        rebornEntityId = Objects.requireNonNull(rebornEntityId, "rebornEntityId");
    }

    public boolean hasActiveRebornEntityId() {
        return !rebornEntityId.startsWith(CaveniaSpawnProviderPolicy.DEFERRED_REBORN_ENTITY_ID_PREFIX);
    }
}
