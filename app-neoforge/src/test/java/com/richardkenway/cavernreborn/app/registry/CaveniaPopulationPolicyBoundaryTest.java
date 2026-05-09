package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaPopulationPolicy;

class CaveniaPopulationPolicyBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-population-policy-non-runtime-mvp.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-population-lakes-falls-shroom-contract-boundary.md");
    private static final Path TERRAIN_POLICY_DOC = resolveProjectFile("docs", "cavenia-terrain-generator-foundation-non-runtime-mvp.md");
    private static final Path BIOME_POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path CAVE_CARVER_POLICY_DOC = resolveProjectFile("docs", "cavenia-cave-carver-policy-non-runtime-mvp.md");
    private static final Path VEINS_POLICY_DOC = resolveProjectFile("docs", "cavenia-veins-content-policy-non-runtime-mvp.md");
    private static final Path TERRAIN_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path GENERATOR_SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-active-generator-technical-scaffold-mvp.md");
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void docsPinTheNonRuntimePopulationPolicy() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String terrainPolicyDoc = Files.readString(TERRAIN_POLICY_DOC);
        String biomePolicyDoc = Files.readString(BIOME_POLICY_DOC);
        String caveCarverPolicyDoc = Files.readString(CAVE_CARVER_POLICY_DOC);
        String veinsPolicyDoc = Files.readString(VEINS_POLICY_DOC);
        String terrainBoundaryDoc = Files.readString(TERRAIN_BOUNDARY_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String generatorScaffoldDoc = Files.readString(GENERATOR_SCAFFOLD_DOC);

        assertTrue(policyDoc.contains("Cavenia Population Policy / Non-Runtime MVP"));
        assertTrue(policyDoc.contains("`CaveniaPopulationPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaPopulationStep`"));
        assertTrue(policyDoc.contains("`CaveniaPopulationBranch`"));
        assertTrue(policyDoc.contains("`ChunkGeneratorCavenia#populate(...)`"));
        assertTrue(policyDoc.contains("`CaveniaConfig.generateLakes`"));
        assertTrue(policyDoc.contains("`WorldGenLakes`"));
        assertTrue(policyDoc.contains("`WorldGenLiquids`"));
        assertTrue(policyDoc.contains("`WorldGenBush(CAVENIC_SHROOM)`"));
        assertTrue(policyDoc.contains("`BiomeDecorator.generateFalls`"));
        assertTrue(policyDoc.contains("`CaveniaTerrainGeneratorPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaCaveCarverPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaVeinsContentPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaConfig.generateLakes = true`"));
        assertTrue(policyDoc.contains("`rand.nextInt(4) == 0`"));
        assertTrue(policyDoc.contains("`rand.nextInt(10) == 0`"));
        assertTrue(policyDoc.contains("`rand.nextInt(6) <= i`"));
        assertTrue(policyDoc.contains("`rand.nextInt(worldHeight - 32) + 16`"));
        assertTrue(policyDoc.contains("`rand.nextInt(worldHeight - 16)`"));
        assertTrue(policyDoc.contains("`rand.nextInt(worldHeight / 2 - 16) + 32`"));
        assertTrue(policyDoc.contains("`rand.nextInt(rand.nextInt(worldHeight - 16) + 10)`"));
        assertTrue(policyDoc.contains("`rand.nextInt(worldHeight / 2 - 32) + 20`"));
        assertTrue(policyDoc.contains("mushroom biomes `3 / 6`"));
        assertTrue(policyDoc.contains("Nether biomes `2 / 6`"));
        assertTrue(policyDoc.contains("all other biomes `1 / 6`"));
        assertTrue(policyDoc.contains("population must happen after final chunk construction"));
        assertTrue(policyDoc.contains("after VEINS mutation"));
        assertTrue(policyDoc.contains("no `dimension/cavenia.json`"));
        assertTrue(policyDoc.contains("no `dimension_type/cavenia.json`"));
        assertTrue(policyDoc.contains("no active Cavenia configured features"));
        assertTrue(policyDoc.contains("no active Cavenia placed features"));
        assertTrue(policyDoc.contains("no active Cavenia biome modifiers"));
        assertTrue(policyDoc.contains("no active Cavenia runtime level"));
        assertTrue(policyDoc.contains("no active Cavenia spawning"));
        assertTrue(policyDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(policyDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Population Policy / Non-Runtime MVP"));
        assertTrue(readme.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Population Policy / Non-Runtime MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("non-runtime Cavenia population policy MVP"));
        assertTrue(contractDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(terrainPolicyDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(biomePolicyDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(caveCarverPolicyDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(veinsPolicyDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(terrainBoundaryDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(generatorScaffoldDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
    }

    @Test
    void purePolicyExistsWithoutActivatingAnyRuntimePopulationResources() throws IOException {
        assertTrue(CaveniaPopulationPolicy.generateLakesDefaultEnabled());
        assertTrue(CaveniaPopulationPolicy.populationRunsAfterFinalChunkConstruction());
        assertTrue(CaveniaPopulationPolicy.populationRunsAfterVeinsMutation());
        assertEquals(1, CaveniaPopulationPolicy.waterLakeRules().size());
        assertEquals(2, CaveniaPopulationPolicy.lavaLakeRules().size());
        assertEquals(4, CaveniaPopulationPolicy.fallRules().size());
        assertEquals(1, CaveniaPopulationPolicy.cavenicShroomRules().size());

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia_lake.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia_lake.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia_shroom.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia_shroom.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaPopulationHooks.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "Caveman.java");

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/worldgen/biome/cavenia")
                            || path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/neoforge/biome_modifier/crazy_")
                            || path.contains("/tags/worldgen/biome/spawns_crazy_")
                    ),
                "Expected the non-runtime population policy to keep active Cavenia population/worldgen resources and fake normal CAVERN crazy-spawn resources absent"
            );
        }

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.equals("CaveniaChunkGenerator.java")
                            || name.equals("CaveniaBiomeSource.java")
                            || name.contains("CaveniaPopulationHooks")
                            || name.equals("Caveman.java")
                    ),
                "Expected the non-runtime population policy to avoid adding active Cavenia generator, biome-source, population-hook or Caveman runtime classes"
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
