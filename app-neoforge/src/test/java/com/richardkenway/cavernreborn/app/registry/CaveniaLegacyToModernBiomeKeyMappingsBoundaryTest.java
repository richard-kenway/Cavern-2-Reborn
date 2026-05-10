package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaLegacyToModernBiomeKeyMappingsBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
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
    void docsPinTheLegacyToModernBiomeKeyMappingInventory() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
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

        assertTrue(inventoryDoc.contains("Cavenia Legacy-To-Modern Biome-Key Mapping Inventory MVP"));
        assertTrue(inventoryDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(inventoryDoc.contains("`CaveniaLegacyToModernBiomeKeyMappingEntry`"));
        assertTrue(inventoryDoc.contains("`CaveniaBiomeKeyMappingKind`"));
        assertTrue(inventoryDoc.contains("`BIOME_SOURCE_STRATEGY`"));
        assertTrue(inventoryDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(inventoryDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(inventoryDoc.contains("`CaveniaBiomeSelectionSkeleton`"));
        assertTrue(inventoryDoc.contains("`CaveniaBiomeSourceStrategyContracts`"));
        assertTrue(inventoryDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(inventoryDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(inventoryDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(inventoryDoc.contains("non-runtime"));
        assertTrue(inventoryDoc.contains("final runtime mapping is not ready"));
        assertTrue(inventoryDoc.contains("registry verification is not ready"));
        assertTrue(inventoryDoc.contains("runtime `BiomeSource` is not ready"));
        assertTrue(inventoryDoc.contains("codec/registration is not ready"));
        assertTrue(inventoryDoc.contains("registry lookup access is not ready"));
        assertTrue(inventoryDoc.contains("activation is not allowed in this slice"));
        assertTrue(inventoryDoc.contains("not a `BiomeSource`"));
        assertTrue(inventoryDoc.contains("not a `ChunkGenerator`"));
        assertTrue(inventoryDoc.contains("no codec or registry entry was added"));
        assertTrue(inventoryDoc.contains("no registry lookup access was added"));
        assertTrue(inventoryDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(inventoryDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(inventoryDoc.contains("no active Cavenia runtime level"));
        assertTrue(inventoryDoc.contains("no active Cavenia access or teleport"));
        assertTrue(inventoryDoc.contains("no active Cavenia spawning"));
        assertTrue(inventoryDoc.contains("no active Cavenia biome modifiers"));
        assertTrue(inventoryDoc.contains("no active Cavenia biome tags"));
        assertTrue(inventoryDoc.contains("no active Cavenia worldgen resources"));
        assertTrue(inventoryDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Legacy-To-Modern Biome-Key Mapping Inventory MVP"));
        assertTrue(readme.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Legacy-To-Modern Biome-Key Mapping Inventory MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(selectionDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(policyDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(providerBoundaryDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
    }

    @Test
    void legacyToModernMappingBoundaryKeepsActiveRuntimeSurfacesAbsent() throws IOException {
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
                "Expected the legacy-to-modern biome-key mapping inventory to avoid adding active Cavenia runtime classes"
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
                "Expected the legacy-to-modern biome-key mapping inventory to keep active Cavenia runtime resources absent"
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
