package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract(
    CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean allowsNextSlicePureBridgeAction,
    boolean allowsNextSliceRuntimeAction,
    boolean blocksActivation,
    String blocker
) {
}
