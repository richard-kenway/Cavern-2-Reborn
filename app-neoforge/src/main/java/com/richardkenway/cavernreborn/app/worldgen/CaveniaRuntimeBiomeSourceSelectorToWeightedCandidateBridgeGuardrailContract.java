package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract(
    CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean allowsNextSliceReadinessAction,
    boolean allowsNextSliceBridgeImplementation,
    boolean allowsNextSliceRuntimeAction,
    boolean blocksActivation,
    String blocker
) {
}
