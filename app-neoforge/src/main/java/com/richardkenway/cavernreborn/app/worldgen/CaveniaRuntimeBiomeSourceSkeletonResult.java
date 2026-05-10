package com.richardkenway.cavernreborn.app.worldgen;

import java.util.Objects;

public record CaveniaRuntimeBiomeSourceSkeletonResult(
    int x,
    int y,
    int z,
    int inputWeightValue,
    int normalizedWeightValue,
    String legacyBiomeName,
    String candidateModernBiomeKey,
    int weight,
    boolean candidateOnly,
    boolean finalRuntimeMapping,
    boolean registryVerified,
    boolean runtimeBiomeResolved,
    boolean runtimeBiomeSourceResult
) {
    public CaveniaRuntimeBiomeSourceSkeletonResult {
        legacyBiomeName = Objects.requireNonNull(legacyBiomeName, "legacyBiomeName");
        candidateModernBiomeKey = Objects.requireNonNull(candidateModernBiomeKey, "candidateModernBiomeKey");
    }
}
