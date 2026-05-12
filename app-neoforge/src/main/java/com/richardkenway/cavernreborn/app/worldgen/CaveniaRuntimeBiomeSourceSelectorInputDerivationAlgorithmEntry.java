package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmEntry(
    CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent component,
    String sourceContractName,
    String algorithmDecision,
    String runtimeBoundary,
    boolean algorithmReady,
    boolean runtimeReady,
    boolean minecraftRuntimeApiRequired,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
