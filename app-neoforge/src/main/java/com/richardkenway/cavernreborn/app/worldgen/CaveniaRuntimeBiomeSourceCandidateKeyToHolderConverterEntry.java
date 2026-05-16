package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry(
    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent component,
    String sourceContractName,
    String converterBehavior,
    String runtimeBoundary,
    boolean converterReady,
    boolean runtimeBiomeSourceReady,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
