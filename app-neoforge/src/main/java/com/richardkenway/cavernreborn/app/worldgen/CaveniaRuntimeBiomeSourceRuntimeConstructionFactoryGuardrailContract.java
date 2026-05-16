package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract(
    CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean allowsNextSliceReadinessAction,
    boolean allowsNextSliceImplementationAction,
    boolean allowsNextSliceRuntimeWiringAction,
    boolean allowsNextSliceActivationAction,
    boolean blocksActivation,
    String blocker
) {
}
