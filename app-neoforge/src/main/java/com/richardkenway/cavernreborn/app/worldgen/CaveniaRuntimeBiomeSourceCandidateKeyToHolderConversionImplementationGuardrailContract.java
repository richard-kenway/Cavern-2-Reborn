package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract(
    CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean allowsNextSliceConversionAction,
    boolean allowsNextSliceRuntimeWiringAction,
    boolean allowsNextSliceActivationAction,
    boolean blocksActivation,
    String blocker
) {
}
