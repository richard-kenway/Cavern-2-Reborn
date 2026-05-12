package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmBoundaryTest {
    private static final String BROKEN_DOCUMENTED_IN_FRAGMENT = "documented in .";
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path ALGORITHM_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"
    );
    private static final Path IMPLEMENTATION_GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-derivation-implementation-go-no-go-mvp.md"
    );
    private static final Path READINESS_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-derivation-readiness-mvp.md"
    );
    private static final Path GO_NO_GO_DOC = resolveProjectFile(
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
    private static final Path GO_NO_GO_REAL_SUBCLASS_DOC = resolveProjectFile(
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
    private static final Path ACTIVATION_MATRIX_DOC = resolveProjectFile(
        "docs", "cavenia-activation-surface-final-inert-readiness-matrix-mvp.md"
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
    void docsPinThePureNonRuntimeSelectorInputDerivationAlgorithmSlice() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String algorithmDoc = Files.readString(ALGORITHM_DOC);
        String implementationGoNoGoDoc = Files.readString(IMPLEMENTATION_GO_NO_GO_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String goNoGoDoc = Files.readString(GO_NO_GO_DOC);
        String chainDoc = Files.readString(CHAIN_DOC);
        String noiseDoc = Files.readString(NOISE_DOC);
        String possibleBiomesDoc = Files.readString(POSSIBLE_BIOMES_DOC);
        String fallbackDoc = Files.readString(FALLBACK_DOC);
        String holderConversionDoc = Files.readString(HOLDER_CONVERSION_DOC);
        String codecMethodDoc = Files.readString(CODEC_METHOD_DOC);
        String realSubclassDoc = Files.readString(REAL_SUBCLASS_DOC);
        String goNoGoRealSubclassDoc = Files.readString(GO_NO_GO_REAL_SUBCLASS_DOC);
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
        String activationMatrixDoc = Files.readString(ACTIVATION_MATRIX_DOC);

        assertTrue(algorithmDoc.contains("Cavenia Runtime BiomeSource Selector Input Derivation Pure Non-Runtime Algorithm MVP"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmComponent`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithmEntry`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSource`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceReadinessChainConsolidation`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceHolderConversionReadiness`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceCodecMethodShapeStub`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceApiShapeInventory`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceSubclassDecision`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceSkeleton`"));
        assertTrue(algorithmDoc.contains("`CaveniaRuntimeBiomeSourceFirstImplementationDecision`"));
        assertTrue(algorithmDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrix`"));
        assertTrue(algorithmDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(algorithmDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(algorithmDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(algorithmDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(algorithmDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(algorithmDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(algorithmDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(algorithmDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(algorithmDoc.contains("pure non-runtime selector-input derivation algorithm, not runtime selection"));
        assertTrue(algorithmDoc.contains("algorithm accepts only integer `x/y/z`"));
        assertTrue(algorithmDoc.contains("algorithm excludes `Climate.Sampler`"));
        assertTrue(algorithmDoc.contains("algorithm imports no Minecraft runtime APIs"));
        assertTrue(algorithmDoc.contains("algorithm returns signed int selector input"));
        assertTrue(algorithmDoc.contains("normalization remains delegated to existing selector/adapter"));
        assertTrue(algorithmDoc.contains("algorithm is deterministic"));
        assertTrue(algorithmDoc.contains("algorithm handles negative coordinates deterministically"));
        assertTrue(algorithmDoc.contains("algorithm uses no random, wall-clock, mutable global state, registry, world, server, or holder state"));
        assertTrue(algorithmDoc.contains("algorithm is not wired into `getNoiseBiome(...)`"));
        assertTrue(algorithmDoc.contains("runtime selection remains not ready"));
        assertTrue(algorithmDoc.contains("`getNoiseBiome(...)` remains unsupported"));
        assertTrue(algorithmDoc.contains("`collectPossibleBiomes()` remains unsupported"));
        assertTrue(algorithmDoc.contains("`codec()` remains unsupported"));
        assertTrue(algorithmDoc.contains("no registry lookup access exists"));
        assertTrue(algorithmDoc.contains("no holder resolution exists"));
        assertTrue(algorithmDoc.contains("no resource-location conversion implementation exists"));
        assertTrue(algorithmDoc.contains("no resource-key conversion implementation exists"));
        assertTrue(algorithmDoc.contains("no fallback implementation exists"));
        assertTrue(algorithmDoc.contains("no usable codec implementation exists"));
        assertTrue(algorithmDoc.contains("no codec registration exists"));
        assertTrue(algorithmDoc.contains("no biome-source type registration exists"));
        assertTrue(algorithmDoc.contains("subclass remains guarded, unregistered, and non-activating"));
        assertTrue(algorithmDoc.contains("normal runtime construction remains unavailable"));
        assertTrue(algorithmDoc.contains("no dimension JSON/type JSON exist"));
        assertTrue(algorithmDoc.contains("no active Cavenia level exists"));
        assertTrue(algorithmDoc.contains("no worldgen resources exist"));
        assertTrue(algorithmDoc.contains("no access/teleport exists"));
        assertTrue(algorithmDoc.contains("no spawning exists"));
        assertTrue(algorithmDoc.contains("`cavernreborn:caveman` remains absent"));
        assertTrue(algorithmDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(implementationGoNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(goNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(chainDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(noiseDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(possibleBiomesDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(fallbackDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(holderConversionDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(codecMethodDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(realSubclassDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(goNoGoRealSubclassDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(apiInventoryDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(subclassDecisionDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(skeletonDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(firstDecisionDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(registryLookupDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(adapterCodecDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(adapterDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(legacyMappingDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));
        assertTrue(activationMatrixDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"));

        assertFalse(algorithmDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(implementationGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(readinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(goNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(chainDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(noiseDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(possibleBiomesDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(fallbackDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(holderConversionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(codecMethodDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(realSubclassDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(goNoGoRealSubclassDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
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
        assertFalse(activationMatrixDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
    }

    @Test
    void selectorInputDerivationAlgorithmBoundaryKeepsAllOtherRuntimeSurfacesAbsent() throws IOException {
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
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/tags/worldgen/biome/cavenia")
                    ),
                "Expected the pure selector-input derivation algorithm slice to keep active Cavenia resources absent"
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
