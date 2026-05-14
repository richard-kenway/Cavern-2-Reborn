package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeBoundaryTest {
    private static final String BROKEN_DOCUMENTED_IN_FRAGMENT = "documented in .";
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path BRIDGE_DOC = resolveProjectFile(
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
    private static final Path CONSOLIDATION_DOC = resolveProjectFile(
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
    private static final Path SKELETON_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-skeleton-mvp.md");
    private static final Path FIRST_DECISION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md"
    );
    private static final Path MATRIX_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-final-readiness-matrix-mvp.md");
    private static final Path REGISTRY_LOOKUP_DOC = resolveProjectFile("docs", "cavenia-registry-lookup-readiness-contracts-mvp.md");
    private static final Path ADAPTER_CODEC_DOC = resolveProjectFile("docs", "cavenia-adapter-codec-registration-readiness-contracts-mvp.md");
    private static final Path ADAPTER_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md");
    private static final Path WEIGHTED_SELECTION_DOC = resolveProjectFile("docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md");
    private static final Path LEGACY_MAPPING_DOC = resolveProjectFile("docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md");
    private static final Path PLAN_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md");
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
    void docsPinThePureNonRuntimeSelectorToWeightedCandidateBridgeSlice() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String bridgeDoc = Files.readString(BRIDGE_DOC);
        String implementationGoNoGoDoc = Files.readString(IMPLEMENTATION_GO_NO_GO_DOC);
        String bridgeReadinessDoc = Files.readString(BRIDGE_READINESS_DOC);
        String bridgeGoNoGoDoc = Files.readString(BRIDGE_GO_NO_GO_DOC);
        String consolidationDoc = Files.readString(CONSOLIDATION_DOC);
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

        assertTrue(bridgeDoc.contains("pure non-runtime selector-to-weighted-candidate bridge implementation, not runtime selection"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSource`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceReadinessChainConsolidation`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceHolderConversionReadiness`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceCodecMethodShapeStub`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceApiShapeInventory`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSubclassDecision`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceSkeleton`"));
        assertTrue(bridgeDoc.contains("`CaveniaRuntimeBiomeSourceFirstImplementationDecision`"));
        assertTrue(bridgeDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrix`"));
        assertTrue(bridgeDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(bridgeDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(bridgeDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(bridgeDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(bridgeDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(bridgeDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(bridgeDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(bridgeDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(bridgeDoc.contains("bridge accepts signed int selector input"));
        assertTrue(bridgeDoc.contains("bridge returns string modern biome candidate key"));
        assertTrue(bridgeDoc.contains("bridge output exists in current candidate inventory"));
        assertTrue(bridgeDoc.contains("bridge imports no Minecraft runtime APIs"));
        assertTrue(bridgeDoc.contains("bridge does not use registry lookup"));
        assertTrue(bridgeDoc.contains("bridge does not resolve holders"));
        assertTrue(bridgeDoc.contains("bridge does not use resource locations or resource keys"));
        assertTrue(bridgeDoc.contains("bridge does not call fallback runtime behavior"));
        assertTrue(bridgeDoc.contains("bridge is not wired into `getNoiseBiome(...)`"));
        assertTrue(bridgeDoc.contains("runtime selection remains not ready"));
        assertTrue(bridgeDoc.contains("`getNoiseBiome(...)` remains unsupported"));
        assertTrue(bridgeDoc.contains("`collectPossibleBiomes()` remains unsupported"));
        assertTrue(bridgeDoc.contains("`codec()` remains unsupported"));
        assertTrue(bridgeDoc.contains("no usable codec implementation exists"));
        assertTrue(bridgeDoc.contains("no codec registration exists"));
        assertTrue(bridgeDoc.contains("no biome-source type registration exists"));
        assertTrue(bridgeDoc.contains("subclass remains guarded, unregistered, and non-activating"));
        assertTrue(bridgeDoc.contains("normal runtime construction remains unavailable"));
        assertTrue(bridgeDoc.contains("no dimension JSON/type JSON exist"));
        assertTrue(bridgeDoc.contains("no active Cavenia level exists"));
        assertTrue(bridgeDoc.contains("no worldgen resources exist"));
        assertTrue(bridgeDoc.contains("no access/teleport exists"));
        assertTrue(bridgeDoc.contains("no spawning exists"));
        assertTrue(bridgeDoc.contains("`cavernreborn:caveman` remains absent"));
        assertTrue(bridgeDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(implementationGoNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(bridgeReadinessDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(bridgeGoNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(consolidationDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(algorithmDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(derivationImplementationGoNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(derivationReadinessDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(derivationGoNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(chainDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(noiseDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(possibleBiomesDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(fallbackDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(holderConversionDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(codecMethodDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(realSubclassDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(realSubclassGoNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(apiInventoryDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(subclassDecisionDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(skeletonDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(firstDecisionDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(registryLookupDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(adapterCodecDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(adapterDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(legacyMappingDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"));

        assertFalse(bridgeDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(implementationGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(bridgeReadinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(bridgeGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(consolidationDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
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
    void bridgeBoundaryKeepsAllOtherRuntimeSurfacesAbsent() throws IOException {
        assertTrue(Files.exists(DESIGNATED_SOURCE));
        assertEquals(
            1L,
            Files.walk(APP_SOURCE_ROOT)
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                .count()
        );
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
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
