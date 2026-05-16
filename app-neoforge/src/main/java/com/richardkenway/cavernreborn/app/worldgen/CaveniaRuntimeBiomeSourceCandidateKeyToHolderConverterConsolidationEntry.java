package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationEntry(
    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent component,
    String sourceContractName,
    String consolidatedState,
    String runtimeBoundary,
    boolean converterReady,
    boolean runtimeReady,
    boolean nextDecisionInput,
    String blocker
) {
}
