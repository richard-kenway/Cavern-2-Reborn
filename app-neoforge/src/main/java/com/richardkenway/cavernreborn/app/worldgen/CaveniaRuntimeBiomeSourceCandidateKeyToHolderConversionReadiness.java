package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness {
    private static final String CANDIDATE_KEY_INPUT_SHAPE = "string modern biome candidate key";
    private static final String RESOURCE_LOCATION_NAME = "Resource" + "Location";
    private static final String RESOURCE_KEY_BIOME_NAME = "Resource" + "Key<Biome>";
    private static final String HOLDER_BIOME_NAME = "Holder" + "<Biome>";
    private static final String EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY = "minecraft:taiga";
    private static final String EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY = "minecraft:desert";
    private static final String EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY = "minecraft:desert";
    private static final String FUTURE_RESOURCE_LOCATION_POLICY =
        "future implementation may parse candidate key string into resource-location only after a separate implementation decision";
    private static final String FUTURE_RESOURCE_KEY_POLICY =
        "future implementation may construct biome resource key only after resource-location conversion and a separate implementation decision";
    private static final String FUTURE_REGISTRY_LOOKUP_POLICY =
        "future implementation requires biome registry lookup but access is not available in this slice";
    private static final String FUTURE_HOLDER_RESOLUTION_POLICY =
        "future implementation may resolve biome holder only after registry lookup access is explicitly allowed";
    private static final String FUTURE_HOLDER_RETURN_BOUNDARY =
        "future holder output may feed getNoiseBiome and collectPossibleBiomes only after runtime wiring is explicitly allowed";
    private static final String FUTURE_MISSING_FALLBACK_POLICY =
        "fallback remains PLAINS to minecraft:plains readiness data and is not runtime-resolved in this slice";
    private static final List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.STRING_CANDIDATE_KEY_INPUT,
            "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge",
            "Future conversion input remains the string modern biome candidate key returned by the current pure bridge.",
            "Keep the future conversion path anchored to the existing string candidate-key surface until a later implementation slice is explicitly opened.",
            false,
            "Candidate-key input remains pinned only as inert string readiness data."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.CANDIDATE_KEY_INVENTORY_SOURCE,
            "CaveniaLegacyToModernBiomeKeyMappings",
            "Candidate-key inventory remains sourced from CaveniaLegacyToModernBiomeKeyMappings.candidateModernBiomeKeys().",
            "Keep future conversion bounded to the current inventoried modern biome keys before any runtime conversion exists.",
            false,
            "Candidate inventory remains pinned only as current string-only readiness data."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.RESOURCE_LOCATION_POLICY,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness",
            "Future conversion still needs a string-to-" + RESOURCE_LOCATION_NAME + " parsing policy.",
            FUTURE_RESOURCE_LOCATION_POLICY,
            false,
            "No string candidate key is parsed into a runtime location in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.RESOURCE_KEY_POLICY,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness",
            "Future conversion still needs a parsed-id-to-" + RESOURCE_KEY_BIOME_NAME + " policy.",
            FUTURE_RESOURCE_KEY_POLICY,
            false,
            "No biome resource key is constructed in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.BIOME_REGISTRY_LOOKUP_POLICY,
            "CaveniaRegistryLookupReadiness",
            "Future runtime conversion still needs an explicit biome registry lookup policy after string-only key stages are pinned.",
            FUTURE_REGISTRY_LOOKUP_POLICY,
            true,
            "Registry lookup access remains absent in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.HOLDER_RESOLUTION_POLICY,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "Future runtime conversion still needs an explicit policy for resolving " + HOLDER_BIOME_NAME + " after lookup succeeds.",
            FUTURE_HOLDER_RESOLUTION_POLICY,
            true,
            "No holder is resolved in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.HOLDER_RETURN_BOUNDARY,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            "Future runtime holder output still needs a boundary for " + HOLDER_BIOME_NAME + " returns from getNoiseBiome and collectPossibleBiomes.",
            FUTURE_HOLDER_RETURN_BOUNDARY,
            true,
            "No holder is returned in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.MISSING_CANDIDATE_KEY_POLICY,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "Future runtime conversion still needs a policy for missing candidate keys before any runtime fallback can exist.",
            FUTURE_MISSING_FALLBACK_POLICY,
            false,
            "Missing-candidate handling remains readiness-only and string-only in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.UNRESOLVED_RESOURCE_LOCATION_POLICY,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "Future runtime conversion still needs a policy for candidate keys that fail later " + RESOURCE_LOCATION_NAME + " parsing.",
            FUTURE_MISSING_FALLBACK_POLICY,
            false,
            "No resource-location conversion exists in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.UNRESOLVED_RESOURCE_KEY_POLICY,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "Future runtime conversion still needs a policy for candidate keys that fail later " + RESOURCE_KEY_BIOME_NAME + " construction.",
            FUTURE_MISSING_FALLBACK_POLICY,
            false,
            "No resource-key conversion exists in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.MISSING_HOLDER_FALLBACK_POLICY,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "Future runtime conversion still needs a policy for lookup results that cannot resolve " + HOLDER_BIOME_NAME + ".",
            FUTURE_MISSING_FALLBACK_POLICY,
            true,
            "Fallback remains PLAINS to minecraft:plains readiness data and is not holder-resolved in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.NO_CONVERSION_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness",
            "No candidate-key-to-holder conversion implementation exists in this slice.",
            "Keep this slice limited to inert readiness contracts and block all real conversion work.",
            false,
            "Real conversion implementation remains explicitly out of scope for this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRegistryLookupReadiness",
            "No biome registry lookup access exists for this future conversion path.",
            "Keep future conversion planning explicit without adding runtime registry access in this slice.",
            false,
            "Registry lookup access remains absent in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent
                .REAL_CANDIDATE_KEY_TO_HOLDER_CONVERSION_STILL_DEFERRED,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness",
            "Real candidate-key-to-holder conversion remains deferred even though the future path is now pinned as readiness data.",
            "Keep the future conversion path explicit without turning it into runtime conversion, runtime wiring or activation.",
            false,
            "Real candidate-key-to-holder conversion requires a later explicit implementation slice."
        )
    );

    private CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness() {
    }

    public static List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry> entryFor(
        CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean candidateKeyToHolderConversionReadinessReady() {
        return true;
    }

    public static boolean stringCandidateKeyInputPinned() {
        return true;
    }

    public static boolean candidateKeyInventorySourcePinned() {
        return true;
    }

    public static boolean resource\u004cocationPolicyPinned() {
        return true;
    }

    public static boolean resourceKeyPolicyPinned() {
        return true;
    }

    public static boolean biomeRegistryLookupPolicyPinned() {
        return true;
    }

    public static boolean holderResolutionPolicyPinned() {
        return true;
    }

    public static boolean holderReturnBoundaryPinned() {
        return true;
    }

    public static boolean missingCandidateKeyPolicyPinned() {
        return true;
    }

    public static boolean unresolvedResource\u004cocationPolicyPinned() {
        return true;
    }

    public static boolean unresolvedResourceKeyPolicyPinned() {
        return true;
    }

    public static boolean missingHolderFallbackPolicyPinned() {
        return true;
    }

    public static boolean conversionImplementationReady() {
        return false;
    }

    public static boolean registryLookupRequiredForRuntimeConversion() {
        return true;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean holderResolutionReady() {
        return false;
    }

    public static boolean holderReturnReady() {
        return false;
    }

    public static boolean resource\u004cocationConversionReady() {
        return false;
    }

    public static boolean resourceKeyConversionReady() {
        return false;
    }

    public static boolean fallbackRuntimeReady() {
        return false;
    }

    public static boolean realCandidateKeyToHolderConversionStillDeferred() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static String candidateKeyInputShape() {
        return CANDIDATE_KEY_INPUT_SHAPE;
    }

    public static String futureResource\u004cocationPolicy() {
        return FUTURE_RESOURCE_LOCATION_POLICY;
    }

    public static String futureResourceKeyPolicy() {
        return FUTURE_RESOURCE_KEY_POLICY;
    }

    public static String futureRegistryLookupPolicy() {
        return FUTURE_REGISTRY_LOOKUP_POLICY;
    }

    public static String futureHolderResolutionPolicy() {
        return FUTURE_HOLDER_RESOLUTION_POLICY;
    }

    public static String futureHolderReturnBoundary() {
        return FUTURE_HOLDER_RETURN_BOUNDARY;
    }

    public static String futureMissingFallbackPolicy() {
        return FUTURE_MISSING_FALLBACK_POLICY;
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
            .selectorToWeightedCandidateBridgeConsolidationReady();
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();
    }

    public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {
        return false;
    }

    public static String candidateKeyOutputShape() {
        return CANDIDATE_KEY_INPUT_SHAPE;
    }

    public static boolean candidateKeysRemainStringOnly() {
        return true;
    }

    public static int candidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static List<String> candidateModernBiomeKeys() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateModernBiomeKeys();
    }

    public static String candidateKeyForSampleOrigin() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyForSampleOrigin();
    }

    public static String candidateKeyForSampleMixed() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyForSampleMixed();
    }

    public static String candidateKeyForSampleNegative() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyForSampleNegative();
    }

    public static String expectedSampleOriginCandidateKey() {
        return EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY;
    }

    public static String expectedSampleMixedCandidateKey() {
        return EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY;
    }

    public static String expectedSampleNegativeCandidateKey() {
        return EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY;
    }

    public static boolean sampleKeysExistInInventory() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.sampleKeysExistInInventory();
    }

    public static boolean goNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
            .decisionIsGoForCandidateKeyToHolderConversionReadinessNext();
    }

    public static boolean goNoGoRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
            .decisionIsGoForGuardedCandidateKeyToHolderConversionImplementationNext();
    }

    public static boolean candidateKeyToHolderConversionImplementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean holderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderConversionReadinessReady();
    }

    public static boolean holderConversionRuntimeReady() {
        return false;
    }

    public static boolean fallbackPolicyReadinessReady() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();
    }

    public static boolean fallbackPolicyRuntimeReady() {
        return false;
    }

    public static String fallbackLegacyBiomeName() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeName();
    }

    public static String fallbackCandidateModernBiomeKey() {
        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernBiomeKey();
    }

    public static boolean possibleBiomesReadinessReady() {
        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesReadinessReady();
    }

    public static boolean possibleBiomesRuntimeReady() {
        return false;
    }

    public static boolean noiseBiomeSelectionReadinessReady() {
        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady();
    }

    public static boolean noiseBiomeSelectionRuntimeReady() {
        return false;
    }

    public static boolean readinessChainConsolidationReady() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.readinessChainConsolidationReady();
    }

    public static boolean readinessChainRuntimeReady() {
        return false;
    }

    public static boolean allCurrentReadinessLayersReady() {
        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.allCurrentReadinessLayersReady();
    }

    public static int runtimeReadyLayerCount() {
        return 0;
    }

    public static int consolidatedReadinessLayerCount() {
        return 6;
    }

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();
    }

    public static boolean codecMethodShapeRuntimeReady() {
        return false;
    }

    public static boolean designatedSubclassReady() {
        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();
    }

    public static boolean designatedSubclassRuntimeReady() {
        return false;
    }

    public static boolean runtimeHolderReturnReady() {
        return false;
    }

    public static boolean collectPossibleBiomesImplementationReady() {
        return false;
    }

    public static boolean getNoiseBiomeImplementationReady() {
        return false;
    }

    public static boolean usableRuntimeBiomeSourceReady() {
        return false;
    }

    public static boolean usableCodecImplementationReady() {
        return false;
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean biomeSourceTypeRegistered() {
        return false;
    }

    public static boolean normalRuntimeConstructionAllowed() {
        return false;
    }

    public static boolean unsupportedMethodStubsOnly() {
        return true;
    }

    public static boolean collectPossibleBiomesStubbed() {
        return true;
    }

    public static boolean getNoiseBiomeStubbed() {
        return true;
    }

    public static boolean codecMethodStubbed() {
        return true;
    }

    public static boolean dimensionBindingReady() {
        return false;
    }

    public static int selectedSurfaceReadinessItemCount() {
        return 68;
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return 46;
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return 46;
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry entry(
        CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent component,
        String sourceContractName,
        String conversionSurface,
        String futurePolicy,
        boolean registryLookupRequired,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry(
            component,
            sourceContractName,
            conversionSurface,
            futurePolicy,
            true,
            false,
            registryLookupRequired,
            false,
            false,
            false,
            blocker
        );
    }
}
