package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationBoundaryTest {
    private static final String BROKEN_DOCUMENTED_IN_FRAGMENT = "documented in .";
    private static final String NEW_DOC_PATH =
        "docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md";
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path CONSOLIDATION_BRIDGE_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md"
    );
    private static final Path PURE_BRIDGE_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"
    );
    private static final Path IMPLEMENTATION_GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"
    );
    private static final Path BRIDGE_READINESS_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-readiness-mvp.md"
    );
    private static final Path BRIDGE_GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-go-no-go-mvp.md"
    );
    private static final Path INPUT_CONSOLIDATION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-algorithm-consolidation-next-decision-mvp.md"
    );
    private static final Path ALGORITHM_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"
    );
    private static final Path DERIVATION_IMPLEMENTATION_GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-derivation-implementation-go-no-go-mvp.md"
    );
    private static final Path DERIVATION_READINESS_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-derivation-readiness-mvp.md"
    );
    private static final Path DERIVATION_GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"
    );
    private static final Path CHAIN_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-readiness-chain-consolidation-next-decision-mvp.md"
    );
    private static final Path NOISE_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md"
    );
    private static final Path POSSIBLE_BIOMES_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-possible-biomes-readiness-mvp.md"
    );
    private static final Path FALLBACK_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-fallback-policy-readiness-mvp.md"
    );
    private static final Path HOLDER_CONVERSION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-holder-resource-key-conversion-readiness-mvp.md"
    );
    private static final Path CODEC_METHOD_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-codec-method-shape-stub-mvp.md"
    );
    private static final Path REAL_SUBCLASS_DOC = resolveProjectFile(
        "docs", "cavenia-guarded-unregistered-real-biome-source-subclass-stub-mvp.md"
    );
    private static final Path REAL_SUBCLASS_GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"
    );
    private static final Path API_INVENTORY_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-api-shape-inventory-mvp.md"
    );
    private static final Path SUBCLASS_DECISION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md"
    );
    private static final Path SKELETON_DOC = resolveProjectFile(
        "docs", "cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"
    );
    private static final Path FIRST_DECISION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md"
    );
    private static final Path MATRIX_DOC = resolveProjectFile(
        "docs", "cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"
    );
    private static final Path REGISTRY_LOOKUP_DOC = resolveProjectFile(
        "docs", "cavenia-registry-lookup-readiness-contracts-mvp.md"
    );
    private static final Path ADAPTER_CODEC_DOC = resolveProjectFile(
        "docs", "cavenia-adapter-codec-registration-readiness-contracts-mvp.md"
    );
    private static final Path ADAPTER_DOC = resolveProjectFile(
        "docs", "cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md"
    );
    private static final Path WEIGHTED_SELECTION_DOC = resolveProjectFile(
        "docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"
    );
    private static final Path LEGACY_MAPPING_DOC = resolveProjectFile(
        "docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"
    );
    private static final Path PLAN_DOC = resolveProjectFile(
        "docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path DESIGNATED_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSource.java"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheSelectorBridgeConsolidationAndNextDecisionSlice() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String consolidationBridgeDoc = Files.readString(CONSOLIDATION_BRIDGE_DOC);
        String pureBridgeDoc = Files.readString(PURE_BRIDGE_DOC);
        String implementationGoNoGoDoc = Files.readString(IMPLEMENTATION_GO_NO_GO_DOC);
        String bridgeReadinessDoc = Files.readString(BRIDGE_READINESS_DOC);
        String bridgeGoNoGoDoc = Files.readString(BRIDGE_GO_NO_GO_DOC);
        String inputConsolidationDoc = Files.readString(INPUT_CONSOLIDATION_DOC);
        String algorithmDoc = Files.readString(ALGORITHM_DOC);
        String derivationImplementationGoNoGoDoc = Files.readString(DERIVATION_IMPLEMENTATION_GO_NO_GO_DOC);
        String derivationReadinessDoc = Files.readString(DERIVATION_READINESS_DOC);
        String derivationGoNoGoDoc = Files.readString(DERIVATION_GO_NO_GO_DOC);
        String chainDoc = Files.readString(CHAIN_DOC);
        String noiseDoc = Files.readString(NOISE_DOC);
        String possibleBiomesDoc = Files.readString(POSSIBLE_BIOMES_DOC);
        String fallbackDoc = Files.readString(FALLBACK_DOC);
        String holderConversionDoc = Files.readString(HOLDER_CONVERSION_DOC);
        String codecMethodDoc = Files.readString(CODEC_METHOD_DOC);
        String realSubclassDoc = Files.readString(REAL_SUBCLASS_DOC);
        String realSubclassGoNoGoDoc = Files.readString(REAL_SUBCLASS_GO_NO_GO_DOC);
        String apiInventoryDoc = Files.readString(API_INVENTORY_DOC);
        String subclassDecisionDoc = Files.readString(SUBCLASS_DECISION_DOC);
        String skeletonDoc = Files.readString(SKELETON_DOC);
        String firstDecisionDoc = Files.readString(FIRST_DECISION_DOC);
        String matrixDoc = Files.readString(MATRIX_DOC);
        String registryLookupDoc = Files.readString(REGISTRY_LOOKUP_DOC);
        String adapterCodecDoc = Files.readString(ADAPTER_CODEC_DOC);
        String adapterDoc = Files.readString(ADAPTER_DOC);
        String weightedSelectionDoc = Files.readString(WEIGHTED_SELECTION_DOC);
        String legacyMappingDoc = Files.readString(LEGACY_MAPPING_DOC);
        String planDoc = Files.readString(PLAN_DOC);

        assertTrue(
            consolidationBridgeDoc.contains(
                "selector-to-weighted-candidate bridge consolidation and next-decision, not holder conversion"
            )
        );
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationEntry`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationNextDecision`"));
        assertTrue(consolidationBridgeDoc.contains("`PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_GO_NO_GO_NEXT`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSource`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceReadinessChainConsolidation`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceHolderConversionReadiness`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceCodecMethodShapeStub`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceApiShapeInventory`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSubclassDecision`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceSkeleton`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRuntimeBiomeSourceFirstImplementationDecision`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrix`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(consolidationBridgeDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(consolidationBridgeDoc.contains("pure selector-to-weighted-candidate bridge is ready"));
        assertTrue(consolidationBridgeDoc.contains("bridge output remains string modern biome candidate key"));
        assertTrue(consolidationBridgeDoc.contains("sample candidate keys are verified"));
        assertTrue(consolidationBridgeDoc.contains("candidate keys remain string-only"));
        assertTrue(
            consolidationBridgeDoc.contains(
                "selected next decision is `PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_GO_NO_GO_NEXT`"
            )
        );
        assertTrue(
            consolidationBridgeDoc.contains(
                "next slice may add candidate-key-to-holder conversion go/no-go decision"
            )
        );
        assertTrue(
            consolidationBridgeDoc.contains(
                "next slice may not add candidate-key-to-holder conversion readiness"
            )
        );
        assertTrue(
            consolidationBridgeDoc.contains(
                "next slice may not implement candidate-key-to-holder conversion"
            )
        );
        assertTrue(consolidationBridgeDoc.contains("next slice may not use registry lookup access"));
        assertTrue(consolidationBridgeDoc.contains("next slice may not resolve holders"));
        assertTrue(consolidationBridgeDoc.contains("next slice may not implement resource-location conversion"));
        assertTrue(consolidationBridgeDoc.contains("next slice may not implement resource-key conversion"));
        assertTrue(consolidationBridgeDoc.contains("next slice may not wire into `getNoiseBiome(...)`"));
        assertTrue(consolidationBridgeDoc.contains("next slice may not make `getNoiseBiome(...)` usable"));
        assertTrue(consolidationBridgeDoc.contains("`getNoiseBiome(...)` remains unsupported"));
        assertTrue(consolidationBridgeDoc.contains("`collectPossibleBiomes()` remains unsupported"));
        assertTrue(consolidationBridgeDoc.contains("`codec()` remains unsupported"));
        assertTrue(consolidationBridgeDoc.contains("no registry lookup access exists"));
        assertTrue(consolidationBridgeDoc.contains("no holder resolution exists"));
        assertTrue(consolidationBridgeDoc.contains("no resource-location conversion implementation exists"));
        assertTrue(consolidationBridgeDoc.contains("no resource-key conversion implementation exists"));
        assertTrue(consolidationBridgeDoc.contains("no fallback implementation exists"));
        assertTrue(consolidationBridgeDoc.contains("no usable codec implementation exists"));
        assertTrue(consolidationBridgeDoc.contains("no codec registration exists"));
        assertTrue(consolidationBridgeDoc.contains("no biome-source type registration exists"));
        assertTrue(consolidationBridgeDoc.contains("subclass remains guarded, unregistered, and non-activating"));
        assertTrue(consolidationBridgeDoc.contains("normal runtime construction remains unavailable"));
        assertTrue(consolidationBridgeDoc.contains("no dimension JSON/type JSON exist"));
        assertTrue(consolidationBridgeDoc.contains("no active Cavenia level exists"));
        assertTrue(consolidationBridgeDoc.contains("no worldgen resources exist"));
        assertTrue(consolidationBridgeDoc.contains("no access/teleport exists"));
        assertTrue(consolidationBridgeDoc.contains("no spawning exists"));
        assertTrue(consolidationBridgeDoc.contains("`cavernreborn:caveman` remains absent"));
        assertTrue(consolidationBridgeDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains(NEW_DOC_PATH));
        assertTrue(runtimeSmokeDoc.contains(NEW_DOC_PATH));
        assertTrue(pureBridgeDoc.contains(NEW_DOC_PATH));
        assertTrue(implementationGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(bridgeReadinessDoc.contains(NEW_DOC_PATH));
        assertTrue(bridgeGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(inputConsolidationDoc.contains(NEW_DOC_PATH));
        assertTrue(algorithmDoc.contains(NEW_DOC_PATH));
        assertTrue(derivationImplementationGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(derivationReadinessDoc.contains(NEW_DOC_PATH));
        assertTrue(derivationGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(chainDoc.contains(NEW_DOC_PATH));
        assertTrue(noiseDoc.contains(NEW_DOC_PATH));
        assertTrue(possibleBiomesDoc.contains(NEW_DOC_PATH));
        assertTrue(fallbackDoc.contains(NEW_DOC_PATH));
        assertTrue(holderConversionDoc.contains(NEW_DOC_PATH));
        assertTrue(codecMethodDoc.contains(NEW_DOC_PATH));
        assertTrue(realSubclassDoc.contains(NEW_DOC_PATH));
        assertTrue(realSubclassGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(apiInventoryDoc.contains(NEW_DOC_PATH));
        assertTrue(subclassDecisionDoc.contains(NEW_DOC_PATH));
        assertTrue(skeletonDoc.contains(NEW_DOC_PATH));
        assertTrue(firstDecisionDoc.contains(NEW_DOC_PATH));
        assertTrue(matrixDoc.contains(NEW_DOC_PATH));
        assertTrue(registryLookupDoc.contains(NEW_DOC_PATH));
        assertTrue(adapterCodecDoc.contains(NEW_DOC_PATH));
        assertTrue(adapterDoc.contains(NEW_DOC_PATH));
        assertTrue(weightedSelectionDoc.contains(NEW_DOC_PATH));
        assertTrue(legacyMappingDoc.contains(NEW_DOC_PATH));
        assertTrue(planDoc.contains(NEW_DOC_PATH));

        assertFalse(consolidationBridgeDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(pureBridgeDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(implementationGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(bridgeReadinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(bridgeGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(inputConsolidationDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(algorithmDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(derivationImplementationGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(derivationReadinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(derivationGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(chainDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(noiseDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(possibleBiomesDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(fallbackDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(holderConversionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(codecMethodDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(realSubclassDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(realSubclassGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(apiInventoryDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(subclassDecisionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(skeletonDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(firstDecisionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(matrixDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(registryLookupDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(adapterCodecDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(adapterDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(weightedSelectionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(legacyMappingDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(planDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
    }

    @Test
    void consolidationBoundaryKeepsAllOtherRuntimeSurfacesAbsent() throws IOException {
        assertTrue(Files.exists(DESIGNATED_SOURCE));
        assertEquals(
            1L,
            Files.walk(APP_SOURCE_ROOT)
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                .count()
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaChunkGenerator.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaBiomeSource.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "MapGenCaveniaCaves.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaTeleporter.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaSpawnProvider.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaSpawnHandler.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaServerTickSpawner.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json"
        );
        try (var resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(
                        normalizedPath -> normalizedPath.contains("cavenia")
                            && (
                                normalizedPath.contains("/worldgen/")
                                    || normalizedPath.contains("/tags/worldgen/")
                                    || normalizedPath.contains("/biome_modifier/")
                            )
                    )
            );
        }
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)), "Expected missing project file: " + Path.of(first, more));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();
        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(first).resolve(Path.of("", more));
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }
        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
    }

    private static Path resolveProjectPathOrSibling(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();
        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || Files.exists(candidate.getParent())) {
                return candidate;
            }
            current = current.getParent();
        }
        return Path.of(first, more).toAbsolutePath();
    }
}
