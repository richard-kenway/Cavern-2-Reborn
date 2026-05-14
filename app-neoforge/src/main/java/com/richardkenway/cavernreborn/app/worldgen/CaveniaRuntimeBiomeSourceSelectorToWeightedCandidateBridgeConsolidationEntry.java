package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationEntry(
    CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent component,
    String sourceContractName,
    String consolidatedState,
    String nextBoundary,
    boolean bridgeReady,
    boolean runtimeReady,
    boolean nextDecisionInput,
    String blocker
) {
}
