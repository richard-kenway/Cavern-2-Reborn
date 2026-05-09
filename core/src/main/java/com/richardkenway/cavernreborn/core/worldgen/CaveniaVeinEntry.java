package com.richardkenway.cavernreborn.core.worldgen;

import java.util.List;
import java.util.Objects;

public record CaveniaVeinEntry(
    String legacyBlockId,
    int legacyBlockMeta,
    String targetBlockId,
    int targetBlockMeta,
    int weight,
    double chance,
    int size,
    int minHeight,
    int maxHeight,
    List<String> biomeFilters,
    CaveniaVeinBiomeFilterMode biomeFilterMode
) {
    public CaveniaVeinEntry {
        legacyBlockId = Objects.requireNonNull(legacyBlockId, "legacyBlockId");
        targetBlockId = Objects.requireNonNull(targetBlockId, "targetBlockId");
        biomeFilters = List.copyOf(Objects.requireNonNull(biomeFilters, "biomeFilters"));
        biomeFilterMode = Objects.requireNonNull(biomeFilterMode, "biomeFilterMode");
    }
}
