package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetEntry(
    CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetComponent component,
    String sourceContractName,
    String holderSetSurface,
    String futurePolicy,
    boolean readinessPinned,
    boolean implementationReady,
    boolean runtimeWiringReady,
    boolean lookupProviderRequired,
    boolean lookupProviderAvailableInThisSlice,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
