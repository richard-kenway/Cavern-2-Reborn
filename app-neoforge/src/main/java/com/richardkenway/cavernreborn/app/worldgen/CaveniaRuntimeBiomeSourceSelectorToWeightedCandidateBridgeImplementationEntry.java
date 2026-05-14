package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry(
    CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent component,
    String sourceContractName,
    String bridgeDecision,
    String runtimeBoundary,
    boolean bridgeReady,
    boolean runtimeReady,
    boolean minecraftRuntimeApiRequired,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
