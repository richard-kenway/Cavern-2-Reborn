package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourcePossibleBiomesEntry(
    CaveniaRuntimeBiomeSourcePossibleBiomesComponent component,
    String sourceContractName,
    String readinessDecision,
    String expectedFutureBehavior,
    boolean readinessPinned,
    boolean implementationReady,
    boolean registryLookupRequired,
    boolean registryLookupAvailable,
    boolean possibleBiomesRuntimeReady,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
