package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourceFallbackPolicyReadiness {
    private static final String RESOURCE_LOCATION_NAME = "Resource" + "Location";
    private static final String RESOURCE_KEY_BIOME_NAME = "Resource" + "Key<Biome>";
    private static final String HOLDER_BIOME_NAME = "Holder" + "<Biome>";
    private static final String LEGACY_MAPPING_SOURCE = "CaveniaLegacyToModernBiomeKeyMappings";
    private static final String RUNTIME_BIOME_SOURCE_SOURCE = "CaveniaRuntimeBiomeSource";
    private static final String FALLBACK_LEGACY_BIOME_NAME = "PLAINS";
    private static final String FALLBACK_CANDIDATE_MODERN_BIOME_KEY = "minecraft:plains";
    private static final String FALLBACK_RATIONALE =
        FALLBACK_LEGACY_BIOME_NAME + " -> " + FALLBACK_CANDIDATE_MODERN_BIOME_KEY
            + " is already part of the accepted candidate inventory and is the safest neutral fallback candidate for "
            + "future unresolved Cavenia biome candidates.";
    private static final List<CaveniaRuntimeBiomeSourceFallbackPolicyEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_POLICY_SOURCE,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "Fallback policy readiness stays sourced from inert contracts only, with " + FALLBACK_RATIONALE,
            "Keep fallback policy selection explicit as readiness data only until runtime conversion and holder return paths exist.",
            false,
            "Fallback policy remains inert because no runtime fallback path may exist in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_UNPARSEABLE_CANDIDATE_ID,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "If a future candidate biome id cannot be parsed into a runtime " + RESOURCE_LOCATION_NAME
                + ", the planned fallback stays " + FALLBACK_LEGACY_BIOME_NAME + " -> " + FALLBACK_CANDIDATE_MODERN_BIOME_KEY + ".",
            "A later runtime slice may use the pinned fallback candidate when a string biome id is unparseable, but not in this slice.",
            false,
            "No runtime parsing exists, so the unparseable-candidate trigger stays readiness-only."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_UNRESOLVED_RESOURCE_KEY,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "If a future parsed biome id cannot become a runtime " + RESOURCE_KEY_BIOME_NAME
                + ", the planned fallback stays " + FALLBACK_LEGACY_BIOME_NAME + " -> " + FALLBACK_CANDIDATE_MODERN_BIOME_KEY + ".",
            "A later runtime slice may use the pinned fallback candidate when resource-key conversion fails, but not in this slice.",
            true,
            "No runtime resource-key conversion or registry lookup exists, so the unresolved-key trigger stays readiness-only."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_MISSING_HOLDER,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "If a future runtime lookup cannot resolve " + HOLDER_BIOME_NAME + ", the planned fallback stays "
                + FALLBACK_LEGACY_BIOME_NAME + " -> " + FALLBACK_CANDIDATE_MODERN_BIOME_KEY + ".",
            "A later runtime slice may use the pinned fallback candidate when holder resolution fails, but not in this slice.",
            true,
            "No runtime holder resolution exists, so the missing-holder trigger stays readiness-only."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_EMPTY_POSSIBLE_BIOMES,
            RUNTIME_BIOME_SOURCE_SOURCE,
            "If a future possible-biomes collection would become empty, the planned fallback stays "
                + FALLBACK_LEGACY_BIOME_NAME + " -> " + FALLBACK_CANDIDATE_MODERN_BIOME_KEY + ".",
            "A later runtime slice may use the pinned fallback candidate if possible-biomes resolution empties out, but not in this slice.",
            true,
            "Possible-biomes fallback cannot be used while the guarded subclass remains unsupported."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_LEGACY_BIOME_NAME,
            LEGACY_MAPPING_SOURCE,
            "The fallback legacy biome name is pinned to " + FALLBACK_LEGACY_BIOME_NAME + ".",
            "Keep the legacy fallback name deterministic until a later runtime slice can verify it through the full conversion path.",
            false,
            "The fallback legacy name is pinned, but no runtime fallback behavior exists."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_CANDIDATE_MODERN_KEY,
            LEGACY_MAPPING_SOURCE,
            "The fallback candidate modern biome key is pinned to " + FALLBACK_CANDIDATE_MODERN_BIOME_KEY
                + " from the current string inventory only.",
            "Keep the fallback candidate key deterministic until a later runtime slice can verify it through lookup and holder resolution.",
            false,
            "The fallback candidate key is pinned, but no runtime verification or holder return exists."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_REGISTRY_VERIFICATION_DEFERRED,
            "CaveniaRegistryLookupReadiness",
            "The fallback candidate is not registry-verified in this slice even though future runtime fallback will need lookup context.",
            "A later runtime slice must verify the fallback candidate through registry access before runtime fallback can exist.",
            true,
            "Registry lookup access remains blocked, so fallback registry verification stays deferred."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_HOLDER_RESOLUTION_DEFERRED,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "The fallback candidate is not holder-resolved in this slice even though future runtime fallback will need " + HOLDER_BIOME_NAME + ".",
            "A later runtime slice must resolve the fallback candidate into holder-backed biome returns before runtime fallback can exist.",
            true,
            "Holder resolution remains blocked, so fallback holder resolution stays deferred."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_RUNTIME_USAGE_DEFERRED,
            RUNTIME_BIOME_SOURCE_SOURCE,
            "The fallback policy is not used by collectPossibleBiomes() or getNoiseBiome(...) in this guarded subclass slice.",
            "A later runtime slice may decide where fallback behavior actually applies, but this slice keeps it as readiness data only.",
            false,
            "The guarded subclass remains unsupported, so fallback runtime usage stays deferred."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.NO_RUNTIME_FALLBACK_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "No runtime fallback implementation exists in the guarded subclass or in the readiness layer.",
            "Keep fallback policy limited to inert readiness data instead of adding parsing, lookup, holder resolution, or runtime fallback behavior.",
            false,
            "Runtime fallback implementation remains explicitly out of scope for this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.REAL_FALLBACK_POLICY_STILL_DEFERRED,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "Real fallback policy implementation remains deferred even though the deterministic fallback candidate is now pinned.",
            "Keep the fallback choice explicit without turning it into runtime behavior, registry verification, or holder resolution.",
            false,
            "Real fallback policy work requires a later explicit implementation slice."
        )
    );

    private CaveniaRuntimeBiomeSourceFallbackPolicyReadiness() {
    }

    public static List<CaveniaRuntimeBiomeSourceFallbackPolicyEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceFallbackPolicyComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceFallbackPolicyEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceFallbackPolicyEntry> entryFor(
        CaveniaRuntimeBiomeSourceFallbackPolicyComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean fallbackPolicyReadinessReady() {
        return true;
    }

    public static boolean fallbackPolicySourcePinned() {
        return true;
    }

    public static boolean fallbackTriggerUnparseableCandidateIdPinned() {
        return true;
    }

    public static boolean fallbackTriggerUnresolvedResourceKeyPinned() {
        return true;
    }

    public static boolean fallbackTriggerMissingHolderPinned() {
        return true;
    }

    public static boolean fallbackTriggerEmptyPossibleBiomesPinned() {
        return true;
    }

    public static boolean fallbackLegacyBiomeNamePinned() {
        return true;
    }

    public static boolean fallbackCandidateModernKeyPinned() {
        return true;
    }

    public static boolean fallbackRegistryVerificationDeferred() {
        return true;
    }

    public static boolean fallbackHolderResolutionDeferred() {
        return true;
    }

    public static boolean fallbackRuntimeUsageDeferred() {
        return true;
    }

    public static boolean runtimeFallbackImplementationReady() {
        return false;
    }

    public static boolean registryLookupRequiredForRuntimeFallback() {
        return true;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean fallbackRegistryVerified() {
        return false;
    }

    public static boolean fallbackHolderResolved() {
        return false;
    }

    public static boolean fallbackRuntimeReady() {
        return false;
    }

    public static boolean possibleBiomesFallbackReady() {
        return false;
    }

    public static boolean noiseBiomeFallbackReady() {
        return false;
    }

    public static boolean realFallbackPolicyStillDeferred() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static String fallbackLegacyBiomeName() {
        return FALLBACK_LEGACY_BIOME_NAME;
    }

    public static String fallbackCandidateModernBiomeKey() {
        return FALLBACK_CANDIDATE_MODERN_BIOME_KEY;
    }

    public static String fallbackSourceContractName() {
        return LEGACY_MAPPING_SOURCE;
    }

    public static boolean fallbackCandidateExistsInInventory() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateKeyForLegacyBiomeName(FALLBACK_LEGACY_BIOME_NAME)
            .filter(FALLBACK_CANDIDATE_MODERN_BIOME_KEY::equals)
            .isPresent();
    }

    public static int fallbackCandidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean candidateKeysStillStringOnly() {
        return true;
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

    public static boolean selectorInputDerivationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.decisionIsGoForSelectorInputDerivationReadinessNext();
    }

    public static boolean selectorInputDerivationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationReadinessReady();
    }

    public static boolean selectorInputDerivationReadinessRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
            .decisionIsGoForPureNonRuntimeAlgorithmNext();
    }

    public static boolean selectorInputDerivationImplementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorInputDerivationAlgorithmReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputDerivationAlgorithmReady();
    }

    public static boolean selectorInputDerivationAlgorithmRuntimeReady() {
        return false;
    }

    public static boolean selectorInputAlgorithmConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorInputAlgorithmConsolidationReady();
    }

    public static boolean selectorInputAlgorithmConsolidationRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
            .decisionIsGoForSelectorToWeightedCandidateBridgeReadinessNext();
    }

    public static boolean selectorToWeightedCandidateBridgeGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeReadinessReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .selectorToWeightedCandidateBridgeReadinessReady();
    }

    public static boolean selectorToWeightedCandidateBridgeReadinessRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
            .decisionIsGoForPureNonRuntimeBridgeNext();
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();
    }

    public static boolean selectorToWeightedCandidateBridgeImplementationRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
            .selectorToWeightedCandidateBridgeConsolidationReady();
    }

    public static boolean selectorToWeightedCandidateBridgeConsolidationRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConversionGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision
            .decisionIsGoForCandidateKeyToHolderConversionReadinessNext();
    }

    public static boolean candidateKeyToHolderConversionGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean holderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderConversionReadinessReady();
    }

    public static boolean holderConversionRuntimeReady() {
        return false;
    }

    public static boolean codecMethodShapeStubReady() {
        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();
    }

    public static boolean designatedSubclassReady() {
        return true;
    }

    public static boolean designatedSubclassRuntimeReady() {
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

    public static boolean goNoGoGuardrailsEnforced() {
        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.allGuardrailsEnforcedInThisSlice();
    }

    public static boolean apiShapeInventoryReady() {
        return CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady();
    }

    public static boolean dimensionBindingReady() {
        return false;
    }

    public static int selectedSurfaceReadinessItemCount() {
        return CaveniaBiomeSourceStrategyReadinessMatrix.totalReadinessItemCount();
    }

    public static int globalReadinessMatrixTotalRequirementCount() {
        return CaveniaActivationReadinessMatrix.totalRequirementCount();
    }

    public static int globalReadinessMatrixBlockedRequirementCount() {
        return CaveniaActivationReadinessMatrix.blockedRequirementCount();
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaRuntimeBiomeSourceSkeleton.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaRuntimeBiomeSourceSkeleton.dimensionTypeJsonPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaRuntimeBiomeSourceSkeleton.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static CaveniaRuntimeBiomeSourceFallbackPolicyEntry entry(
        CaveniaRuntimeBiomeSourceFallbackPolicyComponent component,
        String sourceContractName,
        String fallbackDecision,
        String expectedFutureBehavior,
        boolean registryLookupRequired,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceFallbackPolicyEntry(
            component,
            sourceContractName,
            fallbackDecision,
            expectedFutureBehavior,
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
