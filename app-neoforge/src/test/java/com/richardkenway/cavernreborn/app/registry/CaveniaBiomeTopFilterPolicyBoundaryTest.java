package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaBiomeTopFilterPolicy;

class CaveniaBiomeTopFilterPolicyBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path TERRAIN_POLICY_DOC = resolveProjectFile("docs", "cavenia-terrain-generator-foundation-non-runtime-mvp.md");
    private static final Path CAVE_CARVER_POLICY_DOC = resolveProjectFile("docs", "cavenia-cave-carver-policy-non-runtime-mvp.md");
    private static final Path VEINS_POLICY_DOC = resolveProjectFile("docs", "cavenia-veins-content-policy-non-runtime-mvp.md");
    private static final Path TERRAIN_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path GENERATOR_SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-active-generator-technical-scaffold-mvp.md");
    private static final Path BIOME_SOURCE_PLAN_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md");
    private static final Path BIOME_MAPPING_DOC = resolveProjectFile("docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md");
    private static final Path WEIGHTED_SELECTION_DOC = resolveProjectFile("docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md");
    private static final Path ADAPTER_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md");
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void docsPinTheNonRuntimeBiomeTopFilterPolicy() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String terrainPolicyDoc = Files.readString(TERRAIN_POLICY_DOC);
        String caveCarverPolicyDoc = Files.readString(CAVE_CARVER_POLICY_DOC);
        String veinsPolicyDoc = Files.readString(VEINS_POLICY_DOC);
        String terrainBoundaryDoc = Files.readString(TERRAIN_BOUNDARY_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String generatorScaffoldDoc = Files.readString(GENERATOR_SCAFFOLD_DOC);
        String biomeSourcePlanDoc = Files.readString(BIOME_SOURCE_PLAN_DOC);
        String biomeMappingDoc = Files.readString(BIOME_MAPPING_DOC);
        String weightedSelectionDoc = Files.readString(WEIGHTED_SELECTION_DOC);
        String adapterDoc = Files.readString(ADAPTER_DOC);

        assertTrue(policyDoc.contains("Cavenia Biome Top/Filter Policy / Non-Runtime MVP"));
        assertTrue(policyDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaBiomeEntry`"));
        assertTrue(policyDoc.contains("`CaveniaConfig.BIOMES`"));
        assertTrue(policyDoc.contains("`CaveBiomeProvider`"));
        assertTrue(policyDoc.contains("`ChunkGeneratorCavenia#replaceBiomeBlocks(...)`"));
        assertTrue(policyDoc.contains("`CaveniaTerrainGeneratorPolicy`"));
        assertTrue(policyDoc.contains("`OCEAN`, weight `15`"));
        assertTrue(policyDoc.contains("`PLAINS`, weight `100`"));
        assertTrue(policyDoc.contains("`DESERT`, weight `70`"));
        assertTrue(policyDoc.contains("`DESERT_HILLS`, weight `10`"));
        assertTrue(policyDoc.contains("`FOREST`, weight `80`"));
        assertTrue(policyDoc.contains("`FOREST_HILLS`, weight `10`"));
        assertTrue(policyDoc.contains("`TAIGA`, weight `80`"));
        assertTrue(policyDoc.contains("`TAIGA_HILLS`, weight `10`"));
        assertTrue(policyDoc.contains("`JUNGLE`, weight `80`"));
        assertTrue(policyDoc.contains("`JUNGLE_HILLS`, weight `10`"));
        assertTrue(policyDoc.contains("`SWAMPLAND`, weight `60`"));
        assertTrue(policyDoc.contains("`EXTREME_HILLS`, weight `50`"));
        assertTrue(policyDoc.contains("`SAVANNA`, weight `50`"));
        assertTrue(policyDoc.contains("`MESA`, weight `50`"));
        assertTrue(policyDoc.contains("default terrain/filter block id is `minecraft:stone`"));
        assertTrue(policyDoc.contains("`OCEAN` -> `minecraft:gravel`"));
        assertTrue(policyDoc.contains("`PLAINS` -> `minecraft:grass`"));
        assertTrue(policyDoc.contains("`DESERT` -> `minecraft:sand`"));
        assertTrue(policyDoc.contains("`MESA` -> `minecraft:red_sandstone`"));
        assertTrue(policyDoc.contains("hybrid custom generator plus data-driven features"));
        assertTrue(policyDoc.contains("no `dimension/cavenia.json`"));
        assertTrue(policyDoc.contains("no `dimension_type/cavenia.json`"));
        assertTrue(policyDoc.contains("no active Cavenia runtime level"));
        assertTrue(policyDoc.contains("no active Cavenia spawning"));
        assertTrue(policyDoc.contains("no active crazy spawning"));
        assertTrue(policyDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(policyDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Biome Top/Filter Policy / Non-Runtime MVP"));
        assertTrue(readme.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Biome Top/Filter Policy / Non-Runtime MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("non-runtime Cavenia biome top/filter policy MVP"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(terrainPolicyDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(caveCarverPolicyDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(veinsPolicyDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(terrainBoundaryDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(generatorScaffoldDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(policyDoc.contains("docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md"));
        assertTrue(biomeSourcePlanDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(policyDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(biomeMappingDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(policyDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(policyDoc.contains("docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md"));
        assertTrue(adapterDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(policyDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
    }

    @Test
    void purePolicyExistsWithoutActivatingAnyRuntimeBiomeOrGeneratorResources() throws IOException {
        assertEquals(14, CaveniaBiomeTopFilterPolicy.entries().size());
        assertEquals(675, CaveniaBiomeTopFilterPolicy.totalWeight());
        assertEquals("minecraft:stone", CaveniaBiomeTopFilterPolicy.defaultTerrainBlockId());

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "Caveman.java");

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/biome/cavenia")
                            || path.contains("/tags/worldgen/biome/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/crazy_")
                            || path.contains("/tags/worldgen/biome/spawns_crazy_")
                    ),
                "Expected the non-runtime biome top/filter policy to keep active Cavenia biome/worldgen resources and fake normal CAVERN crazy-spawn resources absent"
            );
        }

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.equals("CaveniaBiomeSource.java")
                            || name.equals("CaveniaChunkGenerator.java")
                            || name.equals("Caveman.java")
                    ),
                "Expected the non-runtime biome top/filter policy to avoid adding active Cavenia biome-source, generator or Caveman runtime classes"
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
