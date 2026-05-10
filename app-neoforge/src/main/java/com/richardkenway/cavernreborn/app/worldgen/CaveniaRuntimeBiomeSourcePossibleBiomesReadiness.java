package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaRuntimeBiomeSourcePossibleBiomesReadiness {
    private static final String HOLDER_BIOME_NAME = "Holder" + "<Biome>";
    private static final String LEGACY_MAPPING_SOURCE = "CaveniaLegacyToModernBiomeKeyMappings";
    private static final String COLLECT_POSSIBLE_BIOMES_METHOD = "collectPossibleBiomes()";
    private static final String LEGACY_INVENTORY_ORDER = "legacy inventory order";
    private static final String HOLDER_DEDUPLICATION_POLICY =
        "deduplicate resolved holder-backed outputs by future resolved holder/key";
    private static final List<CaveniaRuntimeBiomeSourcePossibleBiomesEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.POSSIBLE_BIOMES_METHOD_SHAPE,
            "CaveniaRuntimeBiomeSourceApiShapeInventory",
            "The guarded runtime-biome-source path keeps the local " + COLLECT_POSSIBLE_BIOMES_METHOD
                + " method shape pinned without adding runtime holder-backed behavior.",
            "A later slice may implement the exact local possible-biomes return path only after the runtime conversion path is ready.",
            false,
            "The method shape is pinned, but the guarded subclass must remain unsupported in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.CANDIDATE_KEYS_SOURCE,
            LEGACY_MAPPING_SOURCE,
            "Possible-biomes candidate keys still come from " + LEGACY_MAPPING_SOURCE + ".candidateModernBiomeKeys()"
                + " as the current string-only inventory.",
            "Keep the current string candidate inventory as the future source for possible-biomes until a later runtime slice resolves holders.",
            false,
            "The current candidate inventory remains string-only and cannot become a runtime holder stream in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.CANDIDATE_ENTRY_COUNT,
            LEGACY_MAPPING_SOURCE,
            "The current possible-biomes candidate inventory count remains pinned at "
                + CaveniaLegacyToModernBiomeKeyMappings.entryCount() + ".",
            "Keep the current candidate count stable until a later slice explicitly changes the inventory or ordering policy.",
            false,
            "The candidate inventory count is pinned, but no runtime holder-backed collection exists in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.CANDIDATE_ORDERING_POLICY,
            "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
            "Future possible-biomes ordering stays pinned to " + LEGACY_INVENTORY_ORDER + ".",
            "Preserve the current mapping inventory order unless a later explicit slice chooses a different runtime ordering policy.",
            false,
            "Ordering policy is readiness data only because no runtime possible-biomes implementation exists."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.CANDIDATE_DEDUPLICATION_POLICY,
            "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
            "Future possible-biomes deduplication stays pinned to " + HOLDER_DEDUPLICATION_POLICY + ".",
            "Do not deduplicate the current string inventory in this slice; only future holder-backed outputs may be deduplicated.",
            false,
            "Deduplication policy is readiness data only because no runtime holder-backed outputs exist."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.HOLDER_CONVERSION_DEPENDENCY,
            "CaveniaRuntimeBiomeSourceHolderConversionReadiness",
            "Future " + COLLECT_POSSIBLE_BIOMES_METHOD + " behavior still depends on the holder/resource-key conversion path reaching "
                + HOLDER_BIOME_NAME + "-backed runtime outputs.",
            "A later slice must finish the runtime conversion path before possible-biomes can return holder-backed values.",
            true,
            "Holder conversion remains blocked, so possible-biomes runtime behavior stays deferred."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.FALLBACK_POLICY_SOURCE,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "Future possible-biomes fallback still comes from the inert fallback policy readiness layer.",
            "Keep missing-biome fallback policy separate and explicit while possible-biomes stays unsupported.",
            false,
            "Fallback policy remains readiness data only because no runtime possible-biomes path exists."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.FALLBACK_INCLUDED_IF_EMPTY_POLICY,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "If future candidate conversion would otherwise produce an empty possible-biomes result, include "
                + CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeName() + " -> "
                + CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernBiomeKey() + ".",
            "A later runtime slice may include the deterministic fallback candidate only when conversion would otherwise produce an empty result.",
            true,
            "Fallback inclusion remains readiness-only because no runtime conversion or holder resolution exists."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.NO_RUNTIME_HOLDER_STREAM,
            "CaveniaRuntimeBiomeSource",
            "No runtime " + HOLDER_BIOME_NAME + "-backed stream or set exists for " + COLLECT_POSSIBLE_BIOMES_METHOD + ".",
            "Keep possible-biomes limited to inert readiness data instead of returning a runtime holder-backed stream in this slice.",
            true,
            "Runtime holder-stream creation would require conversion, lookup and holder resolution that this slice must not add."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            "CaveniaRuntimeBiomeSource",
            "The guarded subclass still leaves " + COLLECT_POSSIBLE_BIOMES_METHOD + " unsupported.",
            "Do not implement runtime possible-biomes behavior until a later explicit slice allows it.",
            true,
            "The guarded subclass must remain unsupported and unregistered in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.NO_REGISTRY_LOOKUP_ACCESS,
            "CaveniaRegistryLookupReadiness",
            "No registry lookup access exists for future runtime possible-biomes resolution.",
            "Keep registry lookup absent while possible-biomes stays readiness-only and non-activating.",
            false,
            "Registry lookup access remains blocked by the existing readiness chain."
        ),
        entry(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.REAL_POSSIBLE_BIOMES_STILL_DEFERRED,
            "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness",
            "Real possible-biomes runtime behavior remains deferred even though the future source, ordering, deduplication and fallback policies are pinned.",
            "Keep the possible-biomes policy explicit without turning it into runtime holder-stream behavior or activation.",
            true,
            "Real possible-biomes work requires a later explicit implementation slice."
        )
    );

    private CaveniaRuntimeBiomeSourcePossibleBiomesReadiness() {
    }

    public static List<CaveniaRuntimeBiomeSourcePossibleBiomesEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourcePossibleBiomesComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourcePossibleBiomesEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourcePossibleBiomesEntry> entryFor(
        CaveniaRuntimeBiomeSourcePossibleBiomesComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean possibleBiomesReadinessReady() {
        return true;
    }

    public static boolean possibleBiomesMethodShapePinned() {
        return true;
    }

    public static boolean candidateKeysSourcePinned() {
        return true;
    }

    public static boolean candidateEntryCountPinned() {
        return true;
    }

    public static boolean candidateOrderingPolicyPinned() {
        return true;
    }

    public static boolean candidateDeduplicationPolicyPinned() {
        return true;
    }

    public static boolean holderConversionDependencyPinned() {
        return true;
    }

    public static boolean fallbackPolicySourcePinned() {
        return true;
    }

    public static boolean fallbackIncludedIfEmptyPolicyPinned() {
        return true;
    }

    public static boolean runtimeHolderStreamReady() {
        return false;
    }

    public static boolean collectPossibleBiomesImplementationReady() {
        return false;
    }

    public static boolean registryLookupRequiredForRuntimePossibleBiomes() {
        return true;
    }

    public static boolean registryLookupAccessReady() {
        return false;
    }

    public static boolean possibleBiomesRuntimeReady() {
        return false;
    }

    public static boolean realPossibleBiomesStillDeferred() {
        return true;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static int candidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean candidateKeysStillStringOnly() {
        return true;
    }

    public static List<String> candidateModernBiomeKeys() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateModernBiomeKeys();
    }

    public static String candidateOrderingPolicy() {
        return LEGACY_INVENTORY_ORDER;
    }

    public static String candidateDeduplicationPolicy() {
        return HOLDER_DEDUPLICATION_POLICY;
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

    public static boolean fallbackIncludedIfEmpty() {
        return true;
    }

    public static boolean fallbackRuntimeReady() {
        return false;
    }

    public static boolean holderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderConversionReadinessReady();
    }

    public static boolean holderConversionRuntimeReady() {
        return false;
    }

    public static boolean holderResolutionReady() {
        return false;
    }

    public static boolean resourceKeyConversionReady() {
        return false;
    }

    public static boolean resourceLocationConversionReady() {
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

    public static boolean collectPossibleBiomesStubbed() {
        return true;
    }

    public static boolean getNoiseBiomeStubbed() {
        return true;
    }

    public static boolean codecMethodStubbed() {
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

    private static CaveniaRuntimeBiomeSourcePossibleBiomesEntry entry(
        CaveniaRuntimeBiomeSourcePossibleBiomesComponent component,
        String sourceContractName,
        String readinessDecision,
        String expectedFutureBehavior,
        boolean registryLookupRequired,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourcePossibleBiomesEntry(
            component,
            sourceContractName,
            readinessDecision,
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
