package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecisionBoundaryTest {
    private static final String BROKEN_DOCUMENTED_IN_FRAGMENT = "documented in .";
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path IMPLEMENTATION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"
    );
    private static final Path READINESS_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-readiness-mvp.md"
    );
    private static final Path GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-go-no-go-mvp.md"
    );
    private static final Path CONSOLIDATION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-algorithm-consolidation-next-decision-mvp.md"
    );
    private static final Path ALGORITHM_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-input-derivation-pure-non-runtime-algorithm-mvp.md"
    );
    private static final Path IMPLEMENTATION_ALGORITHM_DOC = resolveProjectFile(
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
    void docsPinTheSelectorToWeightedCandidateBridgeImplementationGoNoGoDecisionSlice() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String implementationDoc = Files.readString(IMPLEMENTATION_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String goNoGoDoc = Files.readString(GO_NO_GO_DOC);
        String consolidationDoc = Files.readString(CONSOLIDATION_DOC);
        String algorithmDoc = Files.readString(ALGORITHM_DOC);
        String implementationAlgorithmDoc = Files.readString(IMPLEMENTATION_ALGORITHM_DOC);
        String derivationReadinessDoc = Files.readString(DERIVATION_READINESS_DOC);
        String derivationGoNoGoDoc = Files.readString(DERIVATION_GO_NO_GO_DOC);
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

        assertTrue(implementationDoc.contains("selector-to-weighted-candidate bridge implementation go/no-go decision, not implementation"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationNextDecision`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract`"));
        assertTrue(implementationDoc.contains("`PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_NEXT`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSource`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceReadinessChainConsolidation`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourcePossibleBiomesReadiness`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceHolderConversionReadiness`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceCodecMethodShapeStub`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceApiShapeInventory`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSubclassDecision`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceSkeleton`"));
        assertTrue(implementationDoc.contains("`CaveniaRuntimeBiomeSourceFirstImplementationDecision`"));
        assertTrue(implementationDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrix`"));
        assertTrue(implementationDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(implementationDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(implementationDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(implementationDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(implementationDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(implementationDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(implementationDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(implementationDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(implementationDoc.contains("selected decision is `PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_NEXT`"));
        assertTrue(implementationDoc.contains("next slice may add a pure non-runtime selector-to-weighted-candidate bridge"));
        assertTrue(implementationDoc.contains("next slice may accept signed int selector input"));
        assertTrue(implementationDoc.contains("next slice may use existing weighted selector/adapter if they remain pure and non-runtime"));
        assertTrue(implementationDoc.contains("next slice may return string modern biome candidate key"));
        assertTrue(implementationDoc.contains("next slice may use candidate inventory data"));
        assertTrue(implementationDoc.contains("next slice may not import Minecraft runtime APIs"));
        assertTrue(implementationDoc.contains("next slice may not wire into `getNoiseBiome(...)`"));
        assertTrue(implementationDoc.contains("next slice may not make `getNoiseBiome(...)` usable"));
        assertTrue(implementationDoc.contains("next slice may not make `collectPossibleBiomes()` usable"));
        assertTrue(implementationDoc.contains("next slice may not use registry lookup access"));
        assertTrue(implementationDoc.contains("next slice may not resolve holders"));
        assertTrue(implementationDoc.contains("next slice may not implement resource-location conversion"));
        assertTrue(implementationDoc.contains("next slice may not implement resource-key conversion"));
        assertTrue(implementationDoc.contains("next slice may not add fallback implementation"));
        assertTrue(implementationDoc.contains("this slice does not implement the bridge"));
        assertTrue(implementationDoc.contains("this slice does not produce output candidate keys"));
        assertTrue(implementationDoc.contains("selector-to-weighted-candidate bridge readiness remains ready"));
        assertTrue(implementationDoc.contains("selector-to-weighted-candidate bridge implementation is not added in this slice"));
        assertTrue(implementationDoc.contains("bridge runtime behavior remains not ready"));
        assertTrue(implementationDoc.contains("`getNoiseBiome(...)` remains unsupported"));
        assertTrue(implementationDoc.contains("`collectPossibleBiomes()` remains unsupported"));
        assertTrue(implementationDoc.contains("`codec()` remains unsupported"));
        assertTrue(implementationDoc.contains("no registry lookup access exists"));
        assertTrue(implementationDoc.contains("no holder resolution exists"));
        assertTrue(implementationDoc.contains("no resource-location conversion implementation exists"));
        assertTrue(implementationDoc.contains("no resource-key conversion implementation exists"));
        assertTrue(implementationDoc.contains("no fallback implementation exists"));
        assertTrue(implementationDoc.contains("no usable codec implementation exists"));
        assertTrue(implementationDoc.contains("no codec registration exists"));
        assertTrue(implementationDoc.contains("no biome-source type registration exists"));
        assertTrue(implementationDoc.contains("subclass remains guarded, unregistered, and non-activating"));
        assertTrue(implementationDoc.contains("normal runtime construction remains unavailable"));
        assertTrue(implementationDoc.contains("no dimension JSON/type JSON exist"));
        assertTrue(implementationDoc.contains("no active Cavenia level exists"));
        assertTrue(implementationDoc.contains("no worldgen resources exist"));
        assertTrue(implementationDoc.contains("no access/teleport exists"));
        assertTrue(implementationDoc.contains("no spawning exists"));
        assertTrue(implementationDoc.contains("`cavernreborn:caveman` remains absent"));
        assertTrue(implementationDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(goNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(consolidationDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(algorithmDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(implementationAlgorithmDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(derivationReadinessDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(derivationGoNoGoDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(chainDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(noiseDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(possibleBiomesDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(fallbackDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(holderConversionDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(codecMethodDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(realSubclassDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(goNoGoRealSubclassDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(apiInventoryDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(subclassDecisionDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(skeletonDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(firstDecisionDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(registryLookupDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(adapterCodecDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(adapterDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(legacyMappingDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"));

        assertFalse(implementationDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(readinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(goNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(consolidationDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(algorithmDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(implementationAlgorithmDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(derivationReadinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(derivationGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
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

        assertTrue(Files.exists(DESIGNATED_SOURCE));
        assertTrue(Files.exists(IMPLEMENTATION_DOC));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaBiomeSource.java"
        )));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaChunkGenerator.java"
        )));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json"
        )));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json"
        )));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json"
        )));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json"
        )));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json"
        )));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json"
        )));
        assertFalse(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json"
        )));
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
