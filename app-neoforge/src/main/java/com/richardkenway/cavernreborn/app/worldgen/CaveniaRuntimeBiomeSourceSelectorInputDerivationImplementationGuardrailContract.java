package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract(
    CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean allowsNextSlicePureAlgorithmAction,
    boolean allowsNextSliceRuntimeAction,
    boolean blocksActivation,
    String blocker
) {
}
