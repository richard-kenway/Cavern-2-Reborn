package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceNoiseBiomeSelectionEntry(
    CaveniaRuntimeBiomeSourceNoiseBiomeSelectionComponent component,
    String sourceContractName,
    String readinessDecision,
    String expectedFutureBehavior,
    boolean readinessPinned,
    boolean implementationReady,
    boolean registryLookupRequired,
    boolean registryLookupAvailable,
    boolean noiseBiomeRuntimeReady,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
