package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceReadinessChainEntry(
    CaveniaRuntimeBiomeSourceReadinessChainComponent component,
    String sourceContractName,
    String consolidatedReadiness,
    String runtimeState,
    boolean readinessReady,
    boolean runtimeReady,
    boolean blocksActivation,
    boolean nextDecisionInput,
    String blocker
) {
}
