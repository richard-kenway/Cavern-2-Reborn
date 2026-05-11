package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract(
    CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean allowsNextSliceReadinessAction,
    boolean allowsNextSliceRuntimeAction,
    boolean blocksActivation,
    String blocker
) {
}
