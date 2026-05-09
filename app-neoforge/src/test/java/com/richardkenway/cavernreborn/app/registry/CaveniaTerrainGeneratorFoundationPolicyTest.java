package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaTerrainGeneratorPolicy;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaTerrainStep;

class CaveniaTerrainGeneratorFoundationPolicyTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-terrain-generator-foundation-non-runtime-mvp.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-runtime-key-inactive-dimension-scaffold-mvp.md");
    private static final Path TERRAIN_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path CAVE_CARVER_POLICY_DOC = resolveProjectFile("docs", "cavenia-cave-carver-policy-non-runtime-mvp.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path BIOME_TOP_FILTER_POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path VEINS_POLICY_DOC = resolveProjectFile("docs", "cavenia-veins-content-policy-non-runtime-mvp.md");
    private static final Path POLICY_SOURCE = resolveProjectFile(
        "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "worldgen", "CaveniaTerrainGeneratorPolicy.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheNonRuntimeTerrainGeneratorFoundation() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String scaffoldDoc = Files.readString(SCAFFOLD_DOC);
        String terrainBoundaryDoc = Files.readString(TERRAIN_BOUNDARY_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String caveCarverPolicyDoc = Files.readString(CAVE_CARVER_POLICY_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String biomeTopFilterPolicyDoc = Files.readString(BIOME_TOP_FILTER_POLICY_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String veinsPolicyDoc = Files.readString(VEINS_POLICY_DOC);

        assertTrue(policyDoc.contains("Cavenia Terrain Generator Foundation / Non-Runtime MVP"));
        assertTrue(policyDoc.contains("`CaveniaTerrainGeneratorPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaTerrainStep`"));
        assertTrue(policyDoc.contains("`WORLD_HEIGHT = 128`"));
        assertTrue(policyDoc.contains("base stone fill"));
        assertTrue(policyDoc.contains("optional cave carving"));
        assertTrue(policyDoc.contains("biome top/filter replacement"));
        assertTrue(policyDoc.contains("VEINS generator-side mutation"));
        assertTrue(policyDoc.contains("final chunk construction"));
        assertTrue(policyDoc.contains("later population stage for lakes/falls/shrooms"));
        assertTrue(policyDoc.contains("hybrid custom generator plus data-driven features"));
        assertTrue(policyDoc.contains("no `dimension/cavenia.json`"));
        assertTrue(policyDoc.contains("no `dimension_type/cavenia.json`"));
        assertTrue(policyDoc.contains("no active Cavenia runtime `ServerLevel`"));
        assertTrue(policyDoc.contains("no active Cavenia spawning"));
        assertTrue(policyDoc.contains("no active crazy spawning"));
        assertTrue(policyDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(policyDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Terrain Generator Foundation / Non-Runtime MVP"));
        assertTrue(readme.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Terrain Generator Foundation / Non-Runtime MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("non-runtime Cavenia terrain-generator foundation MVP"));
        assertTrue(policyDoc.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(terrainBoundaryDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(caveCarverPolicyDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(biomeTopFilterPolicyDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(veinsPolicyDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
    }

    @Test
    void purePolicyExistsWithoutActivatingAnyRuntimeGeneratorResources() throws IOException {
        String policySource = Files.readString(POLICY_SOURCE);

        assertEquals(128, CaveniaTerrainGeneratorPolicy.WORLD_HEIGHT);
        assertEquals(
            List.of(
                CaveniaTerrainStep.BASE_STONE_FILL,
                CaveniaTerrainStep.OPTIONAL_CAVE_CARVING,
                CaveniaTerrainStep.BIOME_TOP_FILTER_REPLACEMENT,
                CaveniaTerrainStep.VEINS_GENERATOR_SIDE_MUTATION,
                CaveniaTerrainStep.FINAL_CHUNK_CONSTRUCTION,
                CaveniaTerrainStep.LATER_POPULATION_STAGE
            ),
            CaveniaTerrainGeneratorPolicy.generationSteps()
        );
        assertTrue(policySource.contains("WORLD_HEIGHT = 128"));
        assertTrue(policySource.contains("BASE_STONE_FILL"));
        assertTrue(policySource.contains("LATER_POPULATION_STAGE"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "noise_settings", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "Caveman.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.contains("CaveniaChunkGenerator")
                            || name.contains("ChunkGeneratorCavenia")
                            || name.contains("CaveniaBiomeSource")
                            || name.equals("Caveman.java")
                    ),
                "Expected the non-runtime terrain foundation to avoid adding active Cavenia generator, biome-source or Caveman runtime classes"
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
                            || path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/crazy_")
                            || path.contains("/tags/worldgen/biome/spawns_crazy_")
                    ),
                "Expected the non-runtime terrain foundation to keep active Cavenia resources and fake normal CAVERN crazy-spawn resources absent"
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
