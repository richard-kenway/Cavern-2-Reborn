package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaFirstActiveSurfaceSelectionBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path SELECTION_DOC = resolveProjectFile("docs", "cavenia-deliberate-first-active-surface-selection-mvp.md");
    private static final Path MATRIX_DOC = resolveProjectFile("docs", "cavenia-activation-surface-final-inert-readiness-matrix-mvp.md");
    private static final Path WORLDGEN_DOC = resolveProjectFile("docs", "cavenia-worldgen-resource-host-split-contracts-mvp.md");
    private static final Path DIMENSION_ACCESS_SPAWN_DOC = resolveProjectFile("docs", "cavenia-dimension-access-spawn-split-contracts-mvp.md");
    private static final Path GENERATOR_HOST_DOC = resolveProjectFile("docs", "cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md");
    private static final Path ACTIVATION_HOSTS_DOC = resolveProjectFile("docs", "cavenia-generator-activation-readiness-host-contracts-mvp.md");
    private static final Path RUNTIME_CONTRACTS_DOC = resolveProjectFile("docs", "cavenia-generator-runtime-contracts-non-registered-mvp.md");
    private static final Path GENERATOR_SKELETON_DOC = resolveProjectFile("docs", "cavenia-generator-biome-source-unregistered-skeleton-mvp.md");
    private static final Path REGISTRATION_DOC = resolveProjectFile("docs", "cavenia-active-generator-registration-inert-boundary-mvp.md");
    private static final Path BRIDGE_DOC = resolveProjectFile("docs", "cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md");
    private static final Path TECHNICAL_SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-active-generator-technical-scaffold-mvp.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path RUNTIME_KEY_DOC = resolveProjectFile("docs", "cavenia-runtime-key-inactive-dimension-scaffold-mvp.md");
    private static final Path BIOME_SOURCE_PLAN_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md");
    private static final Path BIOME_MAPPING_DOC = resolveProjectFile("docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md");
    private static final Path WEIGHTED_SELECTION_DOC = resolveProjectFile("docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md");
    private static final Path ADAPTER_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md");
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheDecisionOnlyFirstActiveSurfaceSelection() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String selectionDoc = Files.readString(SELECTION_DOC);
        String matrixDoc = Files.readString(MATRIX_DOC);
        String worldgenDoc = Files.readString(WORLDGEN_DOC);
        String dimensionAccessSpawnDoc = Files.readString(DIMENSION_ACCESS_SPAWN_DOC);
        String generatorHostDoc = Files.readString(GENERATOR_HOST_DOC);
        String activationHostsDoc = Files.readString(ACTIVATION_HOSTS_DOC);
        String runtimeContractsDoc = Files.readString(RUNTIME_CONTRACTS_DOC);
        String generatorSkeletonDoc = Files.readString(GENERATOR_SKELETON_DOC);
        String registrationDoc = Files.readString(REGISTRATION_DOC);
        String bridgeDoc = Files.readString(BRIDGE_DOC);
        String technicalScaffoldDoc = Files.readString(TECHNICAL_SCAFFOLD_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);
        String biomeSourcePlanDoc = Files.readString(BIOME_SOURCE_PLAN_DOC);
        String biomeMappingDoc = Files.readString(BIOME_MAPPING_DOC);
        String weightedSelectionDoc = Files.readString(WEIGHTED_SELECTION_DOC);
        String adapterDoc = Files.readString(ADAPTER_DOC);

        assertTrue(selectionDoc.contains("Cavenia Deliberate First Active Surface Selection MVP"));
        assertTrue(selectionDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(selectionDoc.contains("`CaveniaFirstActiveSurfaceCandidate`"));
        assertTrue(selectionDoc.contains("`CaveniaFirstActiveSurfaceSelectionReason`"));
        assertTrue(selectionDoc.contains("`BIOME_SOURCE_STRATEGY`"));
        assertTrue(selectionDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(selectionDoc.contains("`CaveniaActivationSurface`"));
        assertTrue(selectionDoc.contains("`CaveniaBiomeSourceStrategyContracts`"));
        assertTrue(selectionDoc.contains("`CaveniaGeneratorHostContracts`"));
        assertTrue(selectionDoc.contains("`CaveniaDimensionResourceContracts`"));
        assertTrue(selectionDoc.contains("`CaveniaAccessTravelContracts`"));
        assertTrue(selectionDoc.contains("`CaveniaSpawnHostContracts`"));
        assertTrue(selectionDoc.contains("`CaveniaWorldgenResourceContracts`"));
        assertTrue(selectionDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(selectionDoc.contains("decision-only"));
        assertTrue(selectionDoc.contains("activation is not allowed in this slice"));
        assertTrue(selectionDoc.contains("not a `ChunkGenerator`"));
        assertTrue(selectionDoc.contains("not a `BiomeSource`"));
        assertTrue(selectionDoc.contains("no codec or registry entry was added"));
        assertTrue(selectionDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(selectionDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(selectionDoc.contains("no active Cavenia runtime level"));
        assertTrue(selectionDoc.contains("no active Cavenia access or teleport"));
        assertTrue(selectionDoc.contains("no active Cavenia spawning"));
        assertTrue(selectionDoc.contains("no active crazy spawning"));
        assertTrue(selectionDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(selectionDoc.contains("no CAVERN resource reuse is accepted as Cavenia parity"));
        assertTrue(selectionDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Deliberate First Active Surface Selection MVP"));
        assertTrue(readme.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Deliberate First Active Surface Selection MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(worldgenDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(dimensionAccessSpawnDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(activationHostsDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(runtimeContractsDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(generatorSkeletonDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(registrationDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(bridgeDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(technicalScaffoldDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(selectionDoc.contains("docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md"));
        assertTrue(biomeSourcePlanDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(selectionDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(biomeMappingDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(selectionDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(selectionDoc.contains("docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md"));
        assertTrue(adapterDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
    }

    @Test
    void selectionBoundaryKeepsActiveRuntimeSurfacesAbsent() throws IOException {
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
                "Expected the decision-only first-active-surface selection to avoid adding any active Cavenia runtime classes"
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
                "Expected the decision-only first-active-surface selection to keep active Cavenia runtime resources absent"
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
