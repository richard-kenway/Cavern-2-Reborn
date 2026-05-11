package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecisionBoundaryTest {
    private static final String BROKEN_DOCUMENTED_IN_FRAGMENT = "documented in .";
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path DECISION_DOC = resolveProjectFile(
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
    private static final Path GO_NO_GO_DOC = resolveProjectFile(
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
    private static final Path GENERATOR_HOST_DOC = resolveProjectFile(
        "docs", "cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md"
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
    void docsPinTheSelectorInputDerivationGoNoGoDecisionSlice() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String decisionDoc = Files.readString(DECISION_DOC);
        String chainDoc = Files.readString(CHAIN_DOC);
        String noiseDoc = Files.readString(NOISE_DOC);
        String possibleBiomesDoc = Files.readString(POSSIBLE_BIOMES_DOC);
        String fallbackDoc = Files.readString(FALLBACK_DOC);
        String holderConversionDoc = Files.readString(HOLDER_CONVERSION_DOC);
        String codecMethodDoc = Files.readString(CODEC_METHOD_DOC);
        String realSubclassDoc = Files.readString(REAL_SUBCLASS_DOC);
        String goNoGoDoc = Files.readString(GO_NO_GO_DOC);
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
        String generatorHostDoc = Files.readString(GENERATOR_HOST_DOC);

        assertTrue(decisionDoc.contains("Cavenia Runtime BiomeSource Selector Input Derivation Go/No-Go MVP"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationNextDecision`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract`"));
        assertTrue(decisionDoc.contains("`PROCEED_WITH_SELECTOR_INPUT_DERIVATION_READINESS_NEXT`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSource`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceReadinessChainConsolidation`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceHolderConversionReadiness`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceCodecMethodShapeStub`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceApiShapeInventory`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceSubclassDecision`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceSkeleton`"));
        assertTrue(decisionDoc.contains("`CaveniaRuntimeBiomeSourceFirstImplementationDecision`"));
        assertTrue(decisionDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrix`"));
        assertTrue(decisionDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(decisionDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(decisionDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(decisionDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(decisionDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(decisionDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(decisionDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(decisionDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(decisionDoc.contains("selector-input derivation go/no-go decision, not readiness and not implementation"));
        assertTrue(decisionDoc.contains("selected decision is `PROCEED_WITH_SELECTOR_INPUT_DERIVATION_READINESS_NEXT`"));
        assertTrue(decisionDoc.contains("next slice may add selector-input derivation readiness data"));
        assertTrue(decisionDoc.contains("next slice may pin coordinate input policy"));
        assertTrue(decisionDoc.contains("next slice may pin climate sampler input policy"));
        assertTrue(decisionDoc.contains("next slice may pin weight-value derivation policy"));
        assertTrue(decisionDoc.contains("next slice may not implement selector-input derivation"));
        assertTrue(decisionDoc.contains("next slice may not use coordinates for runtime selection"));
        assertTrue(decisionDoc.contains("next slice may not use climate sampler for runtime selection"));
        assertTrue(decisionDoc.contains("next slice may not make `getNoiseBiome(...)` usable"));
        assertTrue(decisionDoc.contains("next slice may not make `collectPossibleBiomes()` usable"));
        assertTrue(decisionDoc.contains("readiness-chain consolidation remains ready"));
        assertTrue(decisionDoc.contains("all current readiness layers are ready"));
        assertTrue(decisionDoc.contains("no runtime layer is ready"));
        assertTrue(decisionDoc.contains("selector-input derivation policy is not pinned in this slice"));
        assertTrue(decisionDoc.contains("selector-input derivation implementation is not ready"));
        assertTrue(decisionDoc.contains("selector-input derivation runtime behavior is not ready"));
        assertTrue(decisionDoc.contains("`getNoiseBiome(...)` remains unsupported"));
        assertTrue(decisionDoc.contains("`collectPossibleBiomes()` remains unsupported"));
        assertTrue(decisionDoc.contains("`codec()` remains unsupported"));
        assertTrue(decisionDoc.contains("no registry lookup access exists"));
        assertTrue(decisionDoc.contains("no holder resolution exists"));
        assertTrue(decisionDoc.contains("no resource-location conversion implementation exists"));
        assertTrue(decisionDoc.contains("no resource-key conversion implementation exists"));
        assertTrue(decisionDoc.contains("no fallback implementation exists"));
        assertTrue(decisionDoc.contains("no usable codec implementation exists"));
        assertTrue(decisionDoc.contains("no codec registration exists"));
        assertTrue(decisionDoc.contains("no biome-source type registration exists"));
        assertTrue(decisionDoc.contains("subclass remains guarded, unregistered, and non-activating"));
        assertTrue(decisionDoc.contains("normal runtime construction remains unavailable"));
        assertTrue(decisionDoc.contains("no dimension JSON/type JSON exist"));
        assertTrue(decisionDoc.contains("no active Cavenia level exists"));
        assertTrue(decisionDoc.contains("no worldgen resources exist"));
        assertTrue(decisionDoc.contains("no access/teleport exists"));
        assertTrue(decisionDoc.contains("no spawning exists"));
        assertTrue(decisionDoc.contains("`cavernreborn:caveman` remains absent"));
        assertTrue(decisionDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(chainDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(noiseDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(possibleBiomesDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(fallbackDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(holderConversionDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(codecMethodDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(realSubclassDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(goNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(apiInventoryDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(subclassDecisionDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(skeletonDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(firstDecisionDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(registryLookupDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(adapterCodecDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(adapterDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(legacyMappingDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(activationMatrixDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-runtime-biome-source-selector-input-derivation-go-no-go-mvp.md"));

        assertFalse(decisionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(chainDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(noiseDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(possibleBiomesDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(fallbackDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(holderConversionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(codecMethodDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(realSubclassDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(goNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
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
        assertFalse(generatorHostDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
    }

    @Test
    void selectorInputDerivationBoundaryKeepsAllOtherRuntimeSurfacesAbsent() throws IOException {
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
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.equals("CaveniaChunkGenerator.java")
                            || name.equals("CaveniaBiomeSource.java")
                            || name.equals("MapGenCaveniaCaves.java")
                            || name.equals("CaveniaTeleporter.java")
                            || name.equals("CaveniaSpawnProvider.java")
                            || name.equals("CaveniaSpawnHandler.java")
                            || name.equals("CaveniaServerTickSpawner.java")
                    ),
                "Expected the selector-input derivation go/no-go slice to keep all other active Cavenia runtime classes absent"
            );
        }

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/tags/worldgen/biome/cavenia")
                    ),
                "Expected the selector-input derivation go/no-go slice to keep active Cavenia runtime resources absent"
            );
        }
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)), "Expected file to be absent: " + Path.of(first, more));
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
