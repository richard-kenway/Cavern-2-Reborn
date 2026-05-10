package com.richardkenway.cavernreborn.app.worldgen;

import java.util.Objects;

public record CaveniaWeightedBiomeSelectionResult(
    int inputValue,
    int normalizedWeightValue,
    String legacyBiomeName,
    String candidateModernBiomeKey,
    int weight,
    boolean finalRuntimeMapping,
    boolean registryVerified,
    boolean runtimeReady
) {
    public CaveniaWeightedBiomeSelectionResult {
        legacyBiomeName = Objects.requireNonNull(legacyBiomeName, "legacyBiomeName");
        candidateModernBiomeKey = Objects.requireNonNull(candidateModernBiomeKey, "candidateModernBiomeKey");
    }
}
