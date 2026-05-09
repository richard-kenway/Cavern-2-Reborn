package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaVeinsContentPolicy;

class CaveniaVeinsContentPolicyBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-veins-content-policy-non-runtime-mvp.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_POLICY_DOC = resolveProjectFile("docs", "cavenia-terrain-generator-foundation-non-runtime-mvp.md");
    private static final Path BIOME_POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path CAVE_CARVER_POLICY_DOC = resolveProjectFile("docs", "cavenia-cave-carver-policy-non-runtime-mvp.md");
    private static final Path TERRAIN_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void docsPinTheNonRuntimeVeinsContentPolicy() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String terrainPolicyDoc = Files.readString(TERRAIN_POLICY_DOC);
        String biomePolicyDoc = Files.readString(BIOME_POLICY_DOC);
        String caveCarverPolicyDoc = Files.readString(CAVE_CARVER_POLICY_DOC);
        String terrainBoundaryDoc = Files.readString(TERRAIN_BOUNDARY_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);

        assertTrue(policyDoc.contains("Cavenia VEINS / Content Policy / Non-Runtime MVP"));
        assertTrue(policyDoc.contains("`CaveniaVeinsContentPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaVeinEntry`"));
        assertTrue(policyDoc.contains("`CaveniaVeinBiomeFilterMode`"));
        assertTrue(policyDoc.contains("`CaveniaConfig.VEINS`"));
        assertTrue(policyDoc.contains("`CaveVeinManager`"));
        assertTrue(policyDoc.contains("`VeinGenerator`"));
        assertTrue(policyDoc.contains("`ChunkGeneratorCavenia`"));
        assertTrue(policyDoc.contains("`CaveniaTerrainGeneratorPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaCaveCarverPolicy`"));
        assertTrue(policyDoc.contains("default target block id `minecraft:stone`"));
        assertTrue(policyDoc.contains("default chance `1.0D`"));
        assertTrue(policyDoc.contains("`autoVeins = false`"));
        assertTrue(policyDoc.contains("granite via `minecraft:stone` meta `1`"));
        assertTrue(policyDoc.contains("diorite via `minecraft:stone` meta `3`"));
        assertTrue(policyDoc.contains("andesite via `minecraft:stone` meta `5`"));
        assertTrue(policyDoc.contains("coal ore via `minecraft:coal_ore` meta `0`"));
        assertTrue(policyDoc.contains("iron ore via `minecraft:iron_ore` meta `0`"));
        assertTrue(policyDoc.contains("aquamarine ore via `cavern:cave_block` meta `0`"));
        assertTrue(policyDoc.contains("magnite ore via `cavern:cave_block` meta `2`"));
        assertTrue(policyDoc.contains("randomite ore via `cavern:cave_block` meta `4`"));
        assertTrue(policyDoc.contains("hexcite ore via `cavern:cave_block` meta `5`"));
        assertTrue(policyDoc.contains("fissured stone via `cavern:cave_block` meta `7`"));
        assertTrue(policyDoc.contains("dirt via `minecraft:dirt` meta `0`"));
        assertTrue(policyDoc.contains("gravel via `minecraft:gravel` meta `0`"));
        assertTrue(policyDoc.contains("sand via `minecraft:sand` meta `0`"));
        assertTrue(policyDoc.contains("`minecraft:gold_ore`"));
        assertTrue(policyDoc.contains("`minecraft:redstone_ore`"));
        assertTrue(policyDoc.contains("`minecraft:lapis_ore`"));
        assertTrue(policyDoc.contains("`minecraft:diamond_ore`"));
        assertTrue(policyDoc.contains("`minecraft:emerald_ore`"));
        assertTrue(policyDoc.contains("VEINS mutation must happen after cave carving"));
        assertTrue(policyDoc.contains("VEINS mutation must happen after biome top/filter replacement"));
        assertTrue(policyDoc.contains("VEINS must still remain generator-side mutation"));
        assertTrue(policyDoc.contains("no `dimension/cavenia.json`"));
        assertTrue(policyDoc.contains("no `dimension_type/cavenia.json`"));
        assertTrue(policyDoc.contains("no active Cavenia configured features"));
        assertTrue(policyDoc.contains("no active Cavenia placed features"));
        assertTrue(policyDoc.contains("no active Cavenia biome modifiers"));
        assertTrue(policyDoc.contains("no active Cavenia runtime level"));
        assertTrue(policyDoc.contains("no active Cavenia spawning"));
        assertTrue(policyDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(policyDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia VEINS / Content Policy / Non-Runtime MVP"));
        assertTrue(readme.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia VEINS / Content Policy / Non-Runtime MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("non-runtime Cavenia VEINS/content policy MVP"));
        assertTrue(contractDoc.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
        assertTrue(terrainPolicyDoc.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
        assertTrue(biomePolicyDoc.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
        assertTrue(caveCarverPolicyDoc.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
        assertTrue(terrainBoundaryDoc.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
    }

    @Test
    void purePolicyExistsWithoutActivatingAnyRuntimeVeinsOrGeneratorResources() throws IOException {
        assertEquals(13, CaveniaVeinsContentPolicy.entries().size());
        assertEquals(436, CaveniaVeinsContentPolicy.totalWeight());
        assertEquals("minecraft:stone", CaveniaVeinsContentPolicy.defaultTargetBlockId());
        assertEquals(1.0D, CaveniaVeinsContentPolicy.defaultChance());
        assertFalse(CaveniaVeinsContentPolicy.autoVeinsDefaultEnabled());
        assertTrue(CaveniaVeinsContentPolicy.containsLegacyBlockId("minecraft:coal_ore"));
        assertTrue(CaveniaVeinsContentPolicy.containsLegacyBlockId("cavern:cave_block"));
        assertTrue(CaveniaVeinsContentPolicy.runsAfterCaveCarving());
        assertTrue(CaveniaVeinsContentPolicy.runsAfterBiomeTopFilterReplacement());
        assertTrue(CaveniaVeinsContentPolicy.runsBeforeFinalChunkConstruction());

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "Caveman.java");

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/worldgen/biome/cavenia")
                            || path.contains("/neoforge/biome_modifier/crazy_")
                            || path.contains("/tags/worldgen/biome/spawns_crazy_")
                    ),
                "Expected the non-runtime VEINS/content policy to keep active Cavenia features/worldgen resources and fake normal CAVERN crazy-spawn resources absent"
            );
        }

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.contains("CaveniaChunkGenerator")
                            || name.contains("CaveniaBiomeSource")
                            || name.equals("Caveman.java")
                    ),
                "Expected the non-runtime VEINS/content policy to avoid adding active Cavenia generator, biome-source or Caveman runtime classes"
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
