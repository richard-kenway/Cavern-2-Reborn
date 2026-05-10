package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaLegacyToModernBiomeKeyMappingEntry(
    String legacyBiomeName,
    String candidateModernBiomeKey,
    CaveniaBiomeKeyMappingKind mappingKind,
    boolean candidateInventoried,
    boolean finalRuntimeMapping,
    boolean registryVerified,
    boolean runtimeReady,
    String rationale
) {
}
