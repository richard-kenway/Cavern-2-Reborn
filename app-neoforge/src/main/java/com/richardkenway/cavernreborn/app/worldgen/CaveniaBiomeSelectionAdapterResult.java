package com.richardkenway.cavernreborn.app.worldgen;

import java.util.Objects;

public record CaveniaBiomeSelectionAdapterResult(
    int inputWeightValue,
    int normalizedWeightValue,
    String legacyBiomeName,
    String candidateModernBiomeKey,
    int weight,
    boolean candidateOnly,
    boolean finalRuntimeMapping,
    boolean registryVerified,
    boolean runtimeReady,
    boolean runtimeBiomeSourceResult
) {
    public CaveniaBiomeSelectionAdapterResult {
        legacyBiomeName = Objects.requireNonNull(legacyBiomeName, "legacyBiomeName");
        candidateModernBiomeKey = Objects.requireNonNull(candidateModernBiomeKey, "candidateModernBiomeKey");
    }
}
