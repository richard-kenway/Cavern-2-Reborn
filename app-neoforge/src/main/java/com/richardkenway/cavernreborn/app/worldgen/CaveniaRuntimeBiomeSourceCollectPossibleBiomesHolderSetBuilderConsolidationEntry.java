package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationEntry(
    CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidationComponent component,
    String sourceContractName,
    String consolidatedState,
    String runtimeBoundary,
    boolean builderReady,
    boolean runtimeReady,
    boolean nextDecisionInput,
    String blocker
) {
}
