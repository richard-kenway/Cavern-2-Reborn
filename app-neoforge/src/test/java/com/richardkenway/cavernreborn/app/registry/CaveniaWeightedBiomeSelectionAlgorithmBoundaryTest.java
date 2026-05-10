package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaWeightedBiomeSelectionAlgorithmBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path ALGORITHM_DOC = resolveProjectFile("docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md");
    private static final Path INVENTORY_DOC = resolveProjectFile("docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md");
    private static final Path PLAN_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md");
    private static final Path SELECTION_DOC = resolveProjectFile("docs", "cavenia-deliberate-first-active-surface-selection-mvp.md");
    private static final Path MATRIX_DOC = resolveProjectFile("docs", "cavenia-activation-surface-final-inert-readiness-matrix-mvp.md");
    private static final Path GENERATOR_HOST_DOC = resolveProjectFile("docs", "cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path PROVIDER_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path RUNTIME_KEY_DOC = resolveProjectFile("docs", "cavenia-runtime-key-inactive-dimension-scaffold-mvp.md");
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinThePureNonRuntimeWeightedBiomeSelectionAlgorithm() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String algorithmDoc = Files.readString(ALGORITHM_DOC);
        String inventoryDoc = Files.readString(INVENTORY_DOC);
        String planDoc = Files.readString(PLAN_DOC);
        String selectionDoc = Files.readString(SELECTION_DOC);
        String matrixDoc = Files.readString(MATRIX_DOC);
        String generatorHostDoc = Files.readString(GENERATOR_HOST_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String providerBoundaryDoc = Files.readString(PROVIDER_BOUNDARY_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);

        assertTrue(algorithmDoc.contains("Cavenia Weighted Biome Selection Algorithm / Pure Non-Runtime MVP"));
        assertTrue(algorithmDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(algorithmDoc.contains("`CaveniaWeightedBiomeSelectionEntry`"));
        assertTrue(algorithmDoc.contains("`CaveniaWeightedBiomeSelectionResult`"));
        assertTrue(algorithmDoc.contains("`BIOME_SOURCE_STRATEGY`"));
        assertTrue(algorithmDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(algorithmDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(algorithmDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(algorithmDoc.contains("`CaveniaBiomeSelectionSkeleton`"));
        assertTrue(algorithmDoc.contains("`CaveniaBiomeSourceStrategyContracts`"));
        assertTrue(algorithmDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(algorithmDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(algorithmDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(algorithmDoc.contains("pure non-runtime"));
        assertTrue(algorithmDoc.contains("weighted selection runtime is not ready"));
        assertTrue(algorithmDoc.contains("final runtime mapping is not ready"));
        assertTrue(algorithmDoc.contains("registry verification is not ready"));
        assertTrue(algorithmDoc.contains("runtime `BiomeSource` is not ready"));
        assertTrue(algorithmDoc.contains("codec/registration is not ready"));
        assertTrue(algorithmDoc.contains("registry lookup access is not ready"));
        assertTrue(algorithmDoc.contains("activation is not allowed in this slice"));
        assertTrue(algorithmDoc.contains("not a `BiomeSource`"));
        assertTrue(algorithmDoc.contains("not a `ChunkGenerator`"));
        assertTrue(algorithmDoc.contains("no codec or registry entry was added"));
        assertTrue(algorithmDoc.contains("no registry lookup access was added"));
        assertTrue(algorithmDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(algorithmDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(algorithmDoc.contains("no active Cavenia runtime level"));
        assertTrue(algorithmDoc.contains("no active Cavenia access or teleport"));
        assertTrue(algorithmDoc.contains("no active Cavenia spawning"));
        assertTrue(algorithmDoc.contains("no active Cavenia worldgen resources"));
        assertTrue(algorithmDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Weighted Biome Selection Algorithm / Pure Non-Runtime MVP"));
        assertTrue(readme.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Weighted Biome Selection Algorithm / Pure Non-Runtime MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(inventoryDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(selectionDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(policyDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(providerBoundaryDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
    }

    @Test
    void weightedSelectionBoundaryKeepsActiveRuntimeSurfacesAbsent() throws IOException {
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.equals("CaveniaChunkGenerator.java")
                            || name.equals("ChunkGeneratorCavenia.java")
                            || name.equals("CaveniaBiomeSource.java")
                            || name.equals("MapGenCaveniaCaves.java")
                            || name.equals("CaveniaTeleporter.java")
                            || name.equals("CaveniaSpawnProvider.java")
                            || name.equals("CaveniaSpawnHandler.java")
                            || name.equals("CaveniaServerTickSpawner.java")
                    ),
                "Expected the weighted biome-selection algorithm to avoid adding active Cavenia runtime classes"
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
                "Expected the weighted biome-selection algorithm to keep active Cavenia runtime resources absent"
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
