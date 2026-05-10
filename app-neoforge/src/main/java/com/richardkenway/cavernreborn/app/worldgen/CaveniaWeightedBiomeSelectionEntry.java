package com.richardkenway.cavernreborn.app.worldgen;

import java.util.Objects;

public record CaveniaWeightedBiomeSelectionEntry(
    String legacyBiomeName,
    String candidateModernBiomeKey,
    int weight,
    int inclusiveStartWeight,
    int exclusiveEndWeight,
    boolean candidateInventoryOnly,
    boolean finalRuntimeMapping,
    boolean registryVerified,
    boolean runtimeReady
) {
    public CaveniaWeightedBiomeSelectionEntry {
        legacyBiomeName = Objects.requireNonNull(legacyBiomeName, "legacyBiomeName");
        candidateModernBiomeKey = Objects.requireNonNull(candidateModernBiomeKey, "candidateModernBiomeKey");
    }
}
