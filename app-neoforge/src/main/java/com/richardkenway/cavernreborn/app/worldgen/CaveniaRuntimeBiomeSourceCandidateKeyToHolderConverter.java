package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public final class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter {
    private static final String CANDIDATE_KEY_INPUT_SHAPE = "string modern biome candidate key";
    private static final String FALLBACK_LEGACY_BIOME_NAME = "PLAINS";
    private static final String FALLBACK_CANDIDATE_MODERN_BIOME_KEY = "minecraft:plains";
    private static final String DESIGNATED_CONVERTER_SIMPLE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter";
    private static final String DESIGNATED_CONVERTER_FILE_NAME = DESIGNATED_CONVERTER_SIMPLE_NAME + ".java";
    private static final String EXPECTED_SAMPLE_ORIGIN_CANDIDATE_KEY = "minecraft:taiga";
    private static final String EXPECTED_SAMPLE_MIXED_CANDIDATE_KEY = "minecraft:desert";
    private static final String EXPECTED_SAMPLE_NEGATIVE_CANDIDATE_KEY = "minecraft:desert";
    private static final List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry> ENTRIES = List.of(
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.DESIGNATED_CONVERTER_FILE,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "The designated converter file exists as the only guarded main-source helper that may parse candidate keys and resolve biome holders.",
            "The converter remains unwired from runtime biome-source methods and does not activate Cavenia.",
            "The guarded converter does not make the runtime biome source usable in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.STRING_CANDIDATE_KEY_INPUT,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness",
            "The converter accepts a string modern biome candidate key as its only input key surface.",
            "Candidate keys remain string-only outside the designated converter.",
            "String candidate keys are still not consumed by runtime biome-source methods in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.RESOURCE_LOCATION_PARSE_STEP,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "The converter may parse the incoming candidate key into a ResourceLocation only inside this designated helper.",
            "Resource-location parsing is not available anywhere else in the guarded runtime-biome-source surface.",
            "Resource-location parsing alone does not enable runtime biome-source behavior."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.BIOME_RESOURCE_KEY_STEP,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "The converter may construct a biome ResourceKey from the parsed resource location only inside this designated helper.",
            "Biome resource-key construction remains confined to the designated converter.",
            "Biome resource-key construction alone does not enable runtime biome-source behavior."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.BIOME_REGISTRY_LOOKUP_STEP,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "The converter uses only the caller-provided biome registry lookup provider to attempt biome holder resolution.",
            "The converter does not access server or world globals and remains unwired from runtime biome-source methods.",
            "Biome registry lookup remains confined to the designated converter in this slice."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.HOLDER_RESOLUTION_STEP,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "The converter resolves a biome holder only after candidate-key parsing, biome-key construction and caller-provided biome lookup succeed.",
            "Holder resolution remains confined to the designated converter and does not reach runtime biome-source methods in this slice.",
            "Holder resolution alone does not make getNoiseBiome or collectPossibleBiomes usable."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.OPTIONAL_HOLDER_RESULT,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "The converter returns Optional<Holder<Biome>> and uses Optional.empty() for invalid or unresolved candidate keys.",
            "Optional holder results remain internal helper output and are not returned from runtime biome-source methods in this slice.",
            "Optional holder results do not activate or register the runtime biome source."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.FALLBACK_CANDIDATE_ATTEMPT,
            "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness",
            "The fallback helper attempts only the pinned fallback candidate key minecraft:plains after primary candidate conversion fails.",
            "Fallback holder attempts remain confined to the designated converter and are not wired into runtime biome-source methods.",
            "Fallback attempts do not add runtime fallback behavior outside the designated converter."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.INVALID_OR_MISSING_KEY_HANDLING,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "The converter returns Optional.empty() for null, blank, invalid or missing candidate keys and does not throw for invalid candidate-key strings.",
            "Invalid-or-missing key handling remains confined to the designated converter and does not enable runtime biome-source behavior.",
            "Invalid or missing keys still keep the guarded runtime biome source inactive."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.NO_GET_NOISE_BIOME_WIRING,
            "CaveniaRuntimeBiomeSource",
            "The converter is not called by getNoiseBiome(...).",
            "getNoiseBiome(...) remains unsupported and unwired in this slice.",
            "The guarded converter does not open runtime getNoiseBiome wiring."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.NO_COLLECT_POSSIBLE_BIOMES_WIRING,
            "CaveniaRuntimeBiomeSource",
            "The converter is not called by collectPossibleBiomes().",
            "collectPossibleBiomes() remains unsupported and unwired in this slice.",
            "The guarded converter does not open runtime collectPossibleBiomes wiring."
        ),
        entry(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.REAL_RUNTIME_BIOME_SOURCE_STILL_DEFERRED,
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            "Real runtime biome-source behavior remains deferred even though the guarded converter helper now exists.",
            "The designated converter does not register codec or biome-source type, does not activate Cavenia and does not create a usable runtime biome source.",
            "Real runtime biome-source behavior remains outside this bounded guarded-converter slice."
        )
    );

    private CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter() {
    }

    public static Optional<Holder<Biome>> holderForCandidateKey(
        String candidateKey,
        HolderLookup.Provider lookupProvider
    ) {
        if (candidateKey == null || candidateKey.isBlank() || lookupProvider == null) {
            return Optional.empty();
        }

        ResourceLocation candidateLocation = ResourceLocation.tryParse(candidateKey);
        if (candidateLocation == null) {
            return Optional.empty();
        }

        ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, candidateLocation);
        return lookupProvider.lookup(Registries.BIOME)
            .flatMap(biomeLookup -> biomeLookup.get(biomeKey))
            .map(reference -> (Holder<Biome>) reference);
    }

    public static Optional<Holder<Biome>> holderForCandidateKeyOrFallback(
        String candidateKey,
        HolderLookup.Provider lookupProvider
    ) {
        Optional<Holder<Biome>> resolvedHolder = holderForCandidateKey(candidateKey, lookupProvider);
        if (resolvedHolder.isPresent()) {
            return resolvedHolder;
        }

        return holderForCandidateKey(FALLBACK_CANDIDATE_MODERN_BIOME_KEY, lookupProvider);
    }

    public static String candidateKeyInputShape() {
        return CANDIDATE_KEY_INPUT_SHAPE;
    }

    public static String fallbackCandidateModernBiomeKey() {
        return FALLBACK_CANDIDATE_MODERN_BIOME_KEY;
    }

    public static String fallbackLegacyBiomeName() {
        return FALLBACK_LEGACY_BIOME_NAME;
    }

    public static String designatedConverterSimpleName() {
        return DESIGNATED_CONVERTER_SIMPLE_NAME;
    }

    public static String designatedConverterFileName() {
        return DESIGNATED_CONVERTER_FILE_NAME;
    }

    public static boolean holderForCandidateKeyReady() {
        return true;
    }

    public static boolean holderForCandidateKeyOrFallbackReady() {
        return true;
    }

    public static boolean converterReady() {
        return true;
    }

    public static boolean converterRuntimeBiomeSourceReady() {
        return false;
    }

    public static boolean getNoiseBiomeWiringReady() {
        return false;
    }

    public static boolean collectPossibleBiomesWiringReady() {
        return false;
    }

    public static boolean runtimeBiomeSourceReady() {
        return false;
    }

    public static boolean activationAllowedInThisSlice() {
        return false;
    }

    public static boolean canActivateCaveniaNow() {
        return false;
    }

    public static List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent> components() {
        return ENTRIES.stream()
            .map(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry::component)
            .toList();
    }

    public static Optional<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry> entryFor(
        CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent component
    ) {
        return ENTRIES.stream()
            .filter(entry -> entry.component() == component)
            .findFirst();
    }

    public static int entryCount() {
        return ENTRIES.size();
    }

    public static boolean candidateKeyToHolderConversionReadinessReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
            .candidateKeyToHolderConversionReadinessReady();
    }

    public static boolean candidateKeyToHolderConversionReadinessRuntimeReady() {
        return false;
    }

    public static boolean implementationGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision
            .decisionIsGoForGuardedCandidateKeyToHolderConversionImplementationNext();
    }

    public static boolean implementationGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean candidateKeyToHolderConverterConsolidationReady() {
        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation
            .candidateKeyToHolderConverterConsolidationReady();
    }

    public static boolean candidateKeyToHolderConverterConsolidationRuntimeReady() {
        return false;
    }

    public static boolean collectPossibleBiomesHolderSetGoNoGoDecisionReady() {
        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision
            .decisionIsGoForCollectPossibleBiomesHolderSetReadinessNext();
    }

    public static boolean collectPossibleBiomesHolderSetGoNoGoRuntimeReady() {
        return false;
    }

    public static boolean selectorToWeightedCandidateBridgeReady() {
        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();
    }

    public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {
        return false;
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

    public static int candidateEntryCount() {
        return CaveniaLegacyToModernBiomeKeyMappings.entryCount();
    }

    public static boolean candidateInventoryReady() {
        return CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady();
    }

    public static boolean candidateKeysStillStringOnlyOutsideConverter() {
        return true;
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

    private static CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry entry(
        CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent component,
        String sourceContractName,
        String converterBehavior,
        String runtimeBoundary,
        String blocker
    ) {
        return new CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry(
            component,
            sourceContractName,
            converterBehavior,
            runtimeBoundary,
            true,
            false,
            false,
            blocker
        );
    }
}
