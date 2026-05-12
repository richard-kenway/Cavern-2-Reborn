package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorInputDerivationEntry(
    CaveniaRuntimeBiomeSourceSelectorInputDerivationComponent component,
    String sourceContractName,
    String inputSurface,
    String futurePolicy,
    boolean readinessPinned,
    boolean implementationReady,
    boolean runtimeSelectionReady,
    boolean runtimeApiUsageAllowedInThisSlice,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
