package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeEntry(
    CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent component,
    String sourceContractName,
    String bridgeSurface,
    String futurePolicy,
    boolean readinessPinned,
    boolean bridgeImplementationReady,
    boolean runtimeReady,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
