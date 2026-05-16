package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationBoundaryTest {
    private static final String BROKEN_DOCUMENTED_IN_FRAGMENT = "documented in .";
    private static final String NEW_DOC_PATH =
        "docs/cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md";
    private static final String GUARDED_IMPLEMENTATION_DOC_PATH =
        "docs/cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md";
    private static final String HOLDER_SET_GO_NO_GO_DOC_PATH =
        "docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-go-no-go-mvp.md";
    private static final String HOLDER_SET_READINESS_DOC_PATH =
        "docs/cavenia-runtime-biome-source-collect-possible-biomes-holder-set-readiness-mvp.md";
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path NEW_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md"
    );
    private static final Path HOLDER_SET_READINESS_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-collect-possible-biomes-holder-set-readiness-mvp.md"
    );
    private static final Path GUARDED_IMPLEMENTATION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md"
    );
    private static final Path IMPLEMENTATION_GO_NO_GO_DOC = resolveProjectFile(
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
    void docsCrossLinkConverterConsolidationAndKeepRuntimeBoundariesBlocked() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String newDoc = Files.readString(NEW_DOC);
        String holderSetReadinessDoc = Files.readString(HOLDER_SET_READINESS_DOC);
        String guardedImplementationDoc = Files.readString(GUARDED_IMPLEMENTATION_DOC);
        String implementationGoNoGoDoc = Files.readString(IMPLEMENTATION_GO_NO_GO_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String goNoGoDoc = Files.readString(GO_NO_GO_DOC);
        String consolidationDoc = Files.readString(CONSOLIDATION_DOC);
        String pureBridgeDoc = Files.readString(PURE_BRIDGE_DOC);
        String holderConversionDoc = Files.readString(HOLDER_CONVERSION_DOC);
        String fallbackDoc = Files.readString(FALLBACK_DOC);
        String possibleBiomesDoc = Files.readString(POSSIBLE_BIOMES_DOC);
        String noiseDoc = Files.readString(NOISE_DOC);
        String codecMethodDoc = Files.readString(CODEC_METHOD_DOC);
        String realSubclassDoc = Files.readString(REAL_SUBCLASS_DOC);
        String apiInventoryDoc = Files.readString(API_INVENTORY_DOC);

        assertTrue(newDoc.contains("converter consolidation and next-decision, not runtime method implementation"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationComponent`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationEntry`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidationNextDecision`"));
        assertTrue(newDoc.contains("`CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter`"));
        assertTrue(newDoc.contains("selected next decision is `PROCEED_WITH_COLLECT_POSSIBLE_BIOMES_HOLDER_SET_GO_NO_GO_NEXT`"));
        assertTrue(newDoc.contains("next slice may add only `collectPossibleBiomes()` holder-set go/no-go decision"));
        assertTrue(newDoc.contains("next slice may not add holder-set readiness"));
        assertTrue(newDoc.contains("next slice may not implement `collectPossibleBiomes()`"));
        assertTrue(newDoc.contains("next slice may not wire converter into `collectPossibleBiomes()`"));
        assertTrue(newDoc.contains("next slice may not wire converter into `getNoiseBiome(...)`"));
        assertTrue(newDoc.contains("converter does not access server/world globals"));
        assertTrue(newDoc.contains("`getNoiseBiome(...)` remains unsupported"));
        assertTrue(newDoc.contains("`collectPossibleBiomes()` remains unsupported"));
        assertTrue(newDoc.contains("`codec()` remains unsupported"));
        assertTrue(newDoc.contains("no usable codec implementation exists"));
        assertTrue(newDoc.contains("no codec registration exists"));
        assertTrue(newDoc.contains("no biome-source type registration exists"));
        assertTrue(newDoc.contains("no generator registration exists"));
        assertTrue(newDoc.contains("no dimension JSON/type JSON exist"));
        assertTrue(newDoc.contains("no active Cavenia level exists"));
        assertTrue(newDoc.contains("no worldgen resources exist"));
        assertTrue(newDoc.contains("no access/teleport exists"));
        assertTrue(newDoc.contains("no spawning exists"));
        assertTrue(newDoc.contains("`cavernreborn:caveman` remains absent"));
        assertTrue(newDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(holderSetReadinessDoc.contains("collect-possible-biomes holder-set readiness, not implementation"));

        assertTrue(readme.contains(NEW_DOC_PATH));
        assertTrue(readme.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(readme.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(readme.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(runtimeSmokeDoc.contains(NEW_DOC_PATH));
        assertTrue(runtimeSmokeDoc.contains(GUARDED_IMPLEMENTATION_DOC_PATH));
        assertTrue(runtimeSmokeDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(runtimeSmokeDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(guardedImplementationDoc.contains(NEW_DOC_PATH));
        assertTrue(guardedImplementationDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(guardedImplementationDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(implementationGoNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(implementationGoNoGoDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(implementationGoNoGoDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(readinessDoc.contains(NEW_DOC_PATH));
        assertTrue(readinessDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(readinessDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(goNoGoDoc.contains(NEW_DOC_PATH));
        assertTrue(goNoGoDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(consolidationDoc.contains(NEW_DOC_PATH));
        assertTrue(consolidationDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(consolidationDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(pureBridgeDoc.contains(NEW_DOC_PATH));
        assertTrue(pureBridgeDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(holderConversionDoc.contains(NEW_DOC_PATH));
        assertTrue(holderConversionDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(holderConversionDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(fallbackDoc.contains(NEW_DOC_PATH));
        assertTrue(fallbackDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(fallbackDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(possibleBiomesDoc.contains(NEW_DOC_PATH));
        assertTrue(possibleBiomesDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(possibleBiomesDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(noiseDoc.contains(NEW_DOC_PATH));
        assertTrue(noiseDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(codecMethodDoc.contains(NEW_DOC_PATH));
        assertTrue(codecMethodDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(codecMethodDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(realSubclassDoc.contains(NEW_DOC_PATH));
        assertTrue(realSubclassDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(realSubclassDoc.contains(HOLDER_SET_READINESS_DOC_PATH));
        assertTrue(apiInventoryDoc.contains(NEW_DOC_PATH));
        assertTrue(apiInventoryDoc.contains(HOLDER_SET_GO_NO_GO_DOC_PATH));
        assertTrue(apiInventoryDoc.contains(HOLDER_SET_READINESS_DOC_PATH));

        assertFalse(newDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(holderSetReadinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(guardedImplementationDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(implementationGoNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(readinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));

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
            "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.java"
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
