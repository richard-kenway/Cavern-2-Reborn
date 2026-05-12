package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationEntry(
    CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent component,
    String sourceContractName,
    String consolidatedState,
    String nextBoundary,
    boolean algorithmReady,
    boolean bridgeReady,
    boolean runtimeReady,
    boolean nextDecisionInput,
    String blocker
) {
}
