package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecisionBoundaryTest {
    private static final String BROKEN_DOCUMENTED_IN_FRAGMENT = "documented in .";
    private static final String NEW_DOC_PATH =
        "docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-implementation-go-no-go-mvp.md";
    private static final String GUARDED_IMPLEMENTATION_DOC_PATH =
        "docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md";
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path NEW_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-candidate-key-to-holder-conversion-implementation-go-no-go-mvp.md"
    );
    private static final Path READINESS_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-candidate-key-to-holder-conversion-readiness-mvp.md"
    );
    private static final Path GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-candidate-key-to-holder-conversion-go-no-go-mvp.md"
    );
    private static final Path CONSOLIDATION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-consolidation-next-decision-mvp.md"
    );
    private static final Path PURE_BRIDGE_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-pure-non-runtime-implementation-mvp.md"
    );
    private static final Path BRIDGE_IMPLEMENTATION_GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-implementation-go-no-go-mvp.md"
    );
    private static final Path BRIDGE_READINESS_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-readiness-mvp.md"
    );
    private static final Path BRIDGE_GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-selector-to-weighted-candidate-bridge-go-no-go-mvp.md"
    );
    private static final Path HOLDER_CONVERSION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-holder-resource-key-conversion-readiness-mvp.md"
    );
    private static final Path FALLBACK_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-fallback-policy-readiness-mvp.md"
    );
    private static final Path POSSIBLE_BIOMES_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-possible-biomes-readiness-mvp.md"
    );
    private static final Path NOISE_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-noise-biome-selection-readiness-mvp.md"
    );
    private static final Path CODEC_METHOD_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-codec-method-shape-stub-mvp.md"
    );
    private static final Path REAL_SUBCLASS_DOC = resolveProjectFile(
        "docs", "cavenia-guarded-unregistered-real-biome-source-subclass-stub-mvp.md"
    );
    private static final Path API_INVENTORY_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-api-shape-inventory-mvp.md"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinCandidateKeyToHolderConversionImplementationGoNoGoAndKeepRuntimeBoundariesBlocked()
        throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String newDoc = Files.readString(NEW_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String goNoGoDoc = Files.readString(GO_NO_GO_DOC);
        String consolidationDoc = Files.readString(CONSOLIDATION_DOC);
        String pureBridgeDoc = Files.readString(PURE_BRIDGE_DOC);
        String bridgeImplementationGoNoGoDoc = Files.readString(BRIDGE_IMPLEMENTATION_GO_NO_GO_DOC);
        String bridgeReadinessDoc = Files.readString(BRIDGE_READINESS_DOC);
        String bridgeGoNoGoDoc = Files.readString(BRIDGE_GO_NO_GO_DOC);
        String holderConversionDoc = Files.readString(HOLDER_CONVERSION_DOC);
        String fallbackDoc = Files.readString(FALLBACK_DOC);
        String possibleBiomesDoc = Files.readString(POSSIBLE_BIOMES_DOC);
        String noiseDoc = Files.readString(NOISE_DOC);
        String codecMethodDoc = Files.readString(CODEC_METHOD_DOC);
        String realSubclassDoc = Files.readString(REAL_SUBCLASS_DOC);
        String apiInventoryDoc = Files.readString(API_INVENTORY_DOC);

        assertTrue(newDoc.contains("candidate-key-to-holder conversion implementation go/no-go decision, not implementation"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationNextDecision`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrail`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGuardrailContract`"));
        assertTrue(newDoc.contains("`PROCEED_WITH_GUARDED_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION_NEXT`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSource`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceHolderConversionReadiness`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceFallbackPolicyReadiness`"));
        assertTrue(newDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(newDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(newDoc.contains("selected decision is `PROCEED_WITH_GUARDED_CANDIDATE_KEY_TO_HOLDER_CONVERSION_IMPLEMENTATION_NEXT`"));
        assertTrue(newDoc.contains("next slice may add guarded conversion helper"));
        assertTrue(newDoc.contains("next slice designated converter simple name is `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter`"));
        assertTrue(newDoc.contains("next slice designated converter file name is `CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java`"));
        assertTrue(newDoc.contains("next slice may use string candidate key input"));
        assertTrue(newDoc.contains("next slice may parse resource locations only in the designated helper"));
        assertTrue(newDoc.contains("next slice may construct resource keys only in the designated helper"));
        assertTrue(newDoc.contains("next slice may use biome registry lookup only in the designated helper"));
        assertTrue(newDoc.contains("next slice may resolve holders only in the designated helper"));
        assertTrue(newDoc.contains("next slice may handle missing/fallback only in the designated helper"));
        assertTrue(newDoc.contains("next slice may not wire into `getNoiseBiome(...)`"));
        assertTrue(newDoc.contains("next slice may not make `getNoiseBiome(...)` usable"));
        assertTrue(newDoc.contains("next slice may not wire into `collectPossibleBiomes()`"));
        assertTrue(newDoc.contains("next slice may not make `collectPossibleBiomes()` usable"));
        assertTrue(newDoc.contains("this slice does not implement candidate-key-to-holder conversion"));
        assertTrue(newDoc.contains("this slice does not create the designated converter file"));
        assertTrue(newDoc.contains("candidate-key-to-holder conversion readiness remains ready"));
        assertTrue(newDoc.contains("registry lookup access remains absent"));
        assertTrue(newDoc.contains("holder resolution remains absent"));
        assertTrue(newDoc.contains("resource-location conversion remains absent"));
        assertTrue(newDoc.contains("resource-key conversion remains absent"));
        assertTrue(newDoc.contains("`getNoiseBiome(...)` remains unsupported"));
        assertTrue(newDoc.contains("`collectPossibleBiomes()` remains unsupported"));
        assertTrue(newDoc.contains("`codec()` remains unsupported"));
        assertTrue(newDoc.contains("no usable codec implementation exists"));
        assertTrue(newDoc.contains("no codec registration exists"));
        assertTrue(newDoc.contains("no biome-source type registration exists"));
        assertTrue(newDoc.contains("no generator registration exists"));
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
        assertTrue(readme.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(runtimeSmokeDoc.contains(NEW_DOC_PATH));
        assertTrue(runtimeSmokeDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(readinessDoc.contains(NEW_DOC_PATH));
        assertTrue(readinessDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(goNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(goNoGoDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(consolidationDoc.contains(NEW_DOC_PATH));
        assertTrue(consolidationDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(pureBridgeDoc.contains(NEW_DOC_PATH));
        assertTrue(pureBridgeDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(bridgeImplementationGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(bridgeReadinessDoc.contains(NEW_DOC_PATH));
        assertTrue(bridgeGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(holderConversionDoc.contains(NEW_DOC_PATH));
        assertTrue(holderConversionDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(fallbackDoc.contains(NEW_DOC_PATH));
        assertTrue(fallbackDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(possibleBiomesDoc.contains(NEW_DOC_PATH));
        assertTrue(possibleBiomesDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(noiseDoc.contains(NEW_DOC_PATH));
        assertTrue(noiseDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(codecMethodDoc.contains(NEW_DOC_PATH));
        assertTrue(codecMethodDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(realSubclassDoc.contains(NEW_DOC_PATH));
        assertTrue(realSubclassDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(apiInventoryDoc.contains(NEW_DOC_PATH));
        assertTrue(apiInventoryDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));

        assertFalse(newDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(readinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(goNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(consolidationDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertEquals(
                1L,
                sourceFiles
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                    .count()
            );
        }

        assertTrue(Files.exists(resolveProjectPathOrSibling(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"
        )));
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaBiomeSource.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaChunkGenerator.java"
        );

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

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)));
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
