package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaBiomeSourceStrategyPlanEntry(
    CaveniaBiomeSourceStrategyPlanStep step,
    CaveniaBiomeSourceStrategyRequirement requirement,
    String sourceContractName,
    boolean selectedSurfaceStep,
    boolean inputPinned,
    boolean runtimeReady,
    boolean activationAllowedInThisSlice,
    String blocker
) {
}
