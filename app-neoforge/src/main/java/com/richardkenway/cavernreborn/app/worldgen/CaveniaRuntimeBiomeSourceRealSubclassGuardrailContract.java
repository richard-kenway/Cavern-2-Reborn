package com.richardkenway.cavernreborn.app.worldgen;

public record CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract(
    CaveniaRuntimeBiomeSourceRealSubclassGuardrail guardrail,
    String sourceContractName,
    boolean enforcedInThisSlice,
    boolean allowsNextSliceAction,
    boolean runtimeActivationAllowed,
    String blocker
) {
}
