package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecisionBoundaryTest {
    private static final String BROKEN_DOCUMENTED_IN_FRAGMENT = "documented in .";
    private static final String NEW_DOC_PATH =
        "docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md";
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path NEW_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md"
    );
    private static final Path CONSOLIDATION_DOC = resolveProjectFile(
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
    private static final Path DERIVATION_IMPLEMENTATION_DOC = resolveProjectFile(
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
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinCandidateKeyToHolderConversionGoNoGoAndKeepRuntimeBoundariesBlocked() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String newDoc = Files.readString(NEW_DOC);
        String consolidationDoc = Files.readString(CONSOLIDATION_DOC);
        String pureBridgeDoc = Files.readString(PURE_BRIDGE_DOC);
        String implementationGoNoGoDoc = Files.readString(IMPLEMENTATION_GO_NO_GO_DOC);
        String bridgeReadinessDoc = Files.readString(BRIDGE_READINESS_DOC);
        String bridgeGoNoGoDoc = Files.readString(BRIDGE_GO_NO_GO_DOC);
        String inputConsolidationDoc = Files.readString(INPUT_CONSOLIDATION_DOC);
        String algorithmDoc = Files.readString(ALGORITHM_DOC);
        String derivationImplementationDoc = Files.readString(DERIVATION_IMPLEMENTATION_DOC);
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

        assertTrue(newDoc.contains("candidate-key-to-holder conversion go/no-go decision, not readiness and not implementation"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionNextDecision`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrail`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGuardrailContract`"));
        assertTrue(newDoc.contains("`PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSource`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceHolderConversionReadiness`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`"));
        assertTrue(newDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(newDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(newDoc.contains("selected decision is `PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_READINESS_NEXT`"));
        assertTrue(newDoc.contains("next slice may add candidate-key-to-holder conversion readiness data"));
        assertTrue(newDoc.contains("next slice may pin string candidate key surface"));
        assertTrue(newDoc.contains("next slice may pin resource-location policy"));
        assertTrue(newDoc.contains("next slice may pin resource-key policy"));
        assertTrue(newDoc.contains("next slice may pin registry lookup policy"));
        assertTrue(newDoc.contains("next slice may pin holder resolution policy"));
        assertTrue(newDoc.contains("next slice may pin missing/fallback policy"));
        assertTrue(newDoc.contains("next slice may not implement candidate-key-to-holder conversion"));
        assertTrue(newDoc.contains("next slice may not use registry lookup access"));
        assertTrue(newDoc.contains("next slice may not resolve holders"));
        assertTrue(newDoc.contains("next slice may not implement resource-location conversion"));
        assertTrue(newDoc.contains("next slice may not implement resource-key conversion"));
        assertTrue(newDoc.contains("next slice may not wire into `getNoiseBiome(...)`"));
        assertTrue(newDoc.contains("next slice may not make `getNoiseBiome(...)` usable"));
        assertTrue(newDoc.contains("this slice does not add candidate-key-to-holder conversion readiness"));
        assertTrue(newDoc.contains("this slice does not implement candidate-key-to-holder conversion"));
        assertTrue(newDoc.contains("candidate keys remain string-only"));
        assertTrue(newDoc.contains("registry lookup access remains absent"));
        assertTrue(newDoc.contains("holder resolution remains absent"));
        assertTrue(newDoc.contains("resource-location conversion remains absent"));
        assertTrue(newDoc.contains("resource-key conversion remains absent"));
        assertTrue(newDoc.contains("`getNoiseBiome(...)` remains unsupported"));
        assertTrue(newDoc.contains("`collectPossibleBiomes()` remains unsupported"));
        assertTrue(newDoc.contains("`codec()` remains unsupported"));
        assertTrue(newDoc.contains("no fallback implementation exists"));
        assertTrue(newDoc.contains("no usable codec implementation exists"));
        assertTrue(newDoc.contains("no codec registration exists"));
        assertTrue(newDoc.contains("no biome-source type registration exists"));
        assertTrue(newDoc.contains("subclass remains guarded, unregistered, and non-activating"));
        assertTrue(newDoc.contains("normal runtime construction remains unavailable"));
        assertTrue(newDoc.contains("no dimension JSON/type JSON exist"));
        assertTrue(newDoc.contains("no active Cavenia level exists"));
        assertTrue(newDoc.contains("no worldgen resources exist"));
        assertTrue(newDoc.contains("no access/teleport exists"));
        assertTrue(newDoc.contains("no spawning exists"));
        assertTrue(newDoc.contains("`cavernreborn:caveman` remains absent"));
        assertTrue(newDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains(NEW_DOC_PATH));
        assertTrue(runtimeSmokeDoc.contains(NEW_DOC_PATH));
        assertTrue(consolidationDoc.contains(NEW_DOC_PATH));
        assertTrue(pureBridgeDoc.contains(NEW_DOC_PATH));
        assertTrue(implementationGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(bridgeReadinessDoc.contains(NEW_DOC_PATH));
        assertTrue(bridgeGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(inputConsolidationDoc.contains(NEW_DOC_PATH));
        assertTrue(algorithmDoc.contains(NEW_DOC_PATH));
        assertTrue(derivationImplementationDoc.contains(NEW_DOC_PATH));
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

        assertFalse(newDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(consolidationDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(pureBridgeDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(implementationGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertEquals(
                1L,
                sourceFiles
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                    .count()
            );
        }

        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertFalse(projectFileExists("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json"));

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
                    )
            );
        }
    }

    private static boolean projectFileExists(String first, String... more) {
        return Files.exists(resolveProjectPathOrSibling(first, more));
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(projectFileExists(first, more));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();
        for (int i = 0; i < 8 && current != null; i++) {
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
        for (int i = 0; i < 8 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || (candidate.getParent() != null && Files.exists(candidate.getParent()))) {
                return candidate;
            }
            current = current.getParent();
        }
        return Path.of(first, more).toAbsolutePath();
    }
}
