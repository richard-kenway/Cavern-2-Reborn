package com.richardkenway.cavernreborn.core.worldgen;

import java.util.Objects;

public record CaveniaBiomeEntry(
    String legacyBiomeName,
    int weight,
    String terrainBlockId,
    String topBlockId
) {
    public CaveniaBiomeEntry {
        legacyBiomeName = Objects.requireNonNull(legacyBiomeName, "legacyBiomeName");
        terrainBlockId = Objects.requireNonNull(terrainBlockId, "terrainBlockId");
        topBlockId = Objects.requireNonNull(topBlockId, "topBlockId");
    }
}
