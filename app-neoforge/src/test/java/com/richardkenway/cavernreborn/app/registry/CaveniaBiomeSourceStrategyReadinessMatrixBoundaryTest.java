package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaBiomeSourceStrategyReadinessMatrixBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path MATRIX_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-final-readiness-matrix-mvp.md");
    private static final Path REGISTRY_LOOKUP_DOC = resolveProjectFile("docs", "cavenia-registry-lookup-readiness-contracts-mvp.md");
    private static final Path ADAPTER_CODEC_DOC = resolveProjectFile("docs", "cavenia-adapter-codec-registration-readiness-contracts-mvp.md");
    private static final Path ADAPTER_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md");
    private static final Path WEIGHTED_SELECTION_DOC = resolveProjectFile("docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md");
    private static final Path INVENTORY_DOC = resolveProjectFile("docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md");
    private static final Path PLAN_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md");
    private static final Path SELECTION_DOC = resolveProjectFile("docs", "cavenia-deliberate-first-active-surface-selection-mvp.md");
    private static final Path GLOBAL_MATRIX_DOC = resolveProjectFile("docs", "cavenia-activation-surface-final-inert-readiness-matrix-mvp.md");
    private static final Path GENERATOR_HOST_DOC = resolveProjectFile("docs", "cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path PROVIDER_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path FOUNDATION_READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path RUNTIME_KEY_DOC = resolveProjectFile("docs", "cavenia-runtime-key-inactive-dimension-scaffold-mvp.md");
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheFinalBiomeSourceStrategyReadinessMatrix() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String matrixDoc = Files.readString(MATRIX_DOC);
        String registryLookupDoc = Files.readString(REGISTRY_LOOKUP_DOC);
        String adapterCodecDoc = Files.readString(ADAPTER_CODEC_DOC);
        String adapterDoc = Files.readString(ADAPTER_DOC);
        String weightedSelectionDoc = Files.readString(WEIGHTED_SELECTION_DOC);
        String inventoryDoc = Files.readString(INVENTORY_DOC);
        String planDoc = Files.readString(PLAN_DOC);
        String selectionDoc = Files.readString(SELECTION_DOC);
        String globalMatrixDoc = Files.readString(GLOBAL_MATRIX_DOC);
        String generatorHostDoc = Files.readString(GENERATOR_HOST_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String providerBoundaryDoc = Files.readString(PROVIDER_BOUNDARY_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String foundationReadinessDoc = Files.readString(FOUNDATION_READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);

        assertTrue(matrixDoc.contains("Cavenia BIOME_SOURCE_STRATEGY Final Readiness Matrix MVP"));
        assertTrue(matrixDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrix`"));
        assertTrue(matrixDoc.contains("`CaveniaBiomeSourceStrategyReadinessSurface`"));
        assertTrue(matrixDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrixEntry`"));
        assertTrue(matrixDoc.contains("`BIOME_SOURCE_STRATEGY`"));
        assertTrue(matrixDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(matrixDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(matrixDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(matrixDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(matrixDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(matrixDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(matrixDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(matrixDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(matrixDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(matrixDoc.contains("final selected-surface readiness matrix"));
        assertTrue(matrixDoc.contains("all six readiness layers are ready as contracts/data"));
        assertTrue(matrixDoc.contains("all runtime surfaces are blocked"));
        assertTrue(matrixDoc.contains("registry lookup runtime is not ready"));
        assertTrue(matrixDoc.contains("registry lookup access is not ready"));
        assertTrue(matrixDoc.contains("registry verification is not ready"));
        assertTrue(matrixDoc.contains("runtime biome resolution is not ready"));
        assertTrue(matrixDoc.contains("runtime `BiomeSource` is not ready"));
        assertTrue(matrixDoc.contains("codec implementation/registration is not ready"));
        assertTrue(matrixDoc.contains("activation is not allowed in this slice"));
        assertTrue(matrixDoc.contains("not a `BiomeSource`"));
        assertTrue(matrixDoc.contains("not a `ChunkGenerator`"));
        assertTrue(matrixDoc.contains("no registry lookup access was added"));
        assertTrue(matrixDoc.contains("no biome holder/resource-key conversion was added"));
        assertTrue(matrixDoc.contains("no codec implementation was added"));
        assertTrue(matrixDoc.contains("no codec or registry entry was added"));
        assertTrue(matrixDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(matrixDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(matrixDoc.contains("no active Cavenia runtime level"));
        assertTrue(matrixDoc.contains("no active Cavenia access or teleport"));
        assertTrue(matrixDoc.contains("no active Cavenia spawning"));
        assertTrue(matrixDoc.contains("no active Cavenia worldgen resources"));
        assertTrue(matrixDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia BIOME_SOURCE_STRATEGY Final Readiness Matrix MVP"));
        assertTrue(readme.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia BIOME_SOURCE_STRATEGY Final Readiness Matrix MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(registryLookupDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(adapterCodecDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(adapterDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(inventoryDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(selectionDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(globalMatrixDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(policyDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(providerBoundaryDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(foundationReadinessDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
    }

    @Test
    void finalReadinessMatrixBoundaryKeepsActiveRuntimeSurfacesAbsent() throws IOException {
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
                "Expected the final BIOME_SOURCE_STRATEGY readiness matrix to avoid adding active Cavenia runtime classes"
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
                "Expected the final BIOME_SOURCE_STRATEGY readiness matrix to keep active Cavenia runtime resources absent"
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
