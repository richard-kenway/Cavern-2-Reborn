package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaBiomeProviderContractBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path POPULATION_DOC = resolveProjectFile("docs", "cavenia-population-lakes-falls-shroom-contract-boundary.md");
    private static final Path ACCESS_DOC = resolveProjectFile("docs", "cavenia-mirage-entry-access-contract-boundary.md");
    private static final Path SPAWN_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md");
    private static final Path CRAZY_ROSTER_DOC = resolveProjectFile("docs", "cavenia-crazy-roster-natural-spawn-boundary.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path DIMENSIONS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CavernNeoForgeDimensions.java"
    );
    private static final Path DIMENSION_PACKAGE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheLegacyBiomeManagerAndInactiveRebornBoundary() throws IOException {
        String readme = Files.readString(README);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String terrainDoc = Files.readString(TERRAIN_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String populationDoc = Files.readString(POPULATION_DOC);
        String accessDoc = Files.readString(ACCESS_DOC);
        String spawnProviderDoc = Files.readString(SPAWN_PROVIDER_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Cavenia Biome Provider / Biome List Contract Boundary"));
        assertTrue(readme.contains("docs/cavenia-biome-provider-contract-boundary.md"));

        assertTrue(contractDoc.contains("`CaveniaConfig.BIOMES`"));
        assertTrue(contractDoc.contains("`WorldProviderCavenia#createBiomeProvider()`"));
        assertTrue(contractDoc.contains("`CaveBiomeProvider`"));
        assertTrue(contractDoc.contains("`ChunkGeneratorCavenia`"));
        assertTrue(contractDoc.contains("`CaveBiomeManager`"));
        assertTrue(contractDoc.contains("`terrainBlock`"));
        assertTrue(contractDoc.contains("`topBlock`"));
        assertTrue(contractDoc.contains("There is no separate Cavenia-specific stored `fillerBlock` contract."));
        assertTrue(contractDoc.contains("`OCEAN` weight `15`"));
        assertTrue(contractDoc.contains("`PLAINS` weight `100`"));
        assertTrue(contractDoc.contains("`DESERT` weight `70`"));
        assertTrue(contractDoc.contains("`DESERT_HILLS` weight `10`"));
        assertTrue(contractDoc.contains("`FOREST` weight `80`"));
        assertTrue(contractDoc.contains("`FOREST_HILLS` weight `10`"));
        assertTrue(contractDoc.contains("`TAIGA` weight `80`"));
        assertTrue(contractDoc.contains("`TAIGA_HILLS` weight `10`"));
        assertTrue(contractDoc.contains("`JUNGLE` weight `80`"));
        assertTrue(contractDoc.contains("`JUNGLE_HILLS` weight `10`"));
        assertTrue(contractDoc.contains("`SWAMPLAND` weight `60`"));
        assertTrue(contractDoc.contains("`EXTREME_HILLS` weight `50`"));
        assertTrue(contractDoc.contains("`SAVANNA` weight `50`"));
        assertTrue(contractDoc.contains("`MESA` weight `50`"));
        assertTrue(contractDoc.contains("`OCEAN` -> `minecraft:gravel`"));
        assertTrue(contractDoc.contains("`PLAINS` -> `minecraft:grass`"));
        assertTrue(contractDoc.contains("`DESERT` -> `minecraft:sand`"));
        assertTrue(contractDoc.contains("`DESERT_HILLS` -> `minecraft:sandstone`"));
        assertTrue(contractDoc.contains("`MESA` -> `minecraft:red_sandstone`"));
        assertTrue(contractDoc.contains("the default terrain/filter layer stays stone everywhere unless the config is edited"));
        assertTrue(contractDoc.contains("weighted biome-source strategy"));
        assertTrue(contractDoc.contains("custom biome-provider code"));
        assertTrue(contractDoc.contains("data-driven approximation of the legacy weighted-biome manager"));
        assertTrue(contractDoc.contains("terrain surface-rules or generator-side top/filter mapping"));
        assertTrue(contractDoc.contains("`CaveniaConfig.VEINS`"));
        assertTrue(contractDoc.contains("no active Cavenia biome-source resources"));
        assertTrue(contractDoc.contains("no active Cavenia biome tags"));
        assertTrue(contractDoc.contains("no active Cavenia worldgen resources"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));

        assertTrue(foundationDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(terrainDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(populationDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(accessDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(spawnProviderDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Cavenia biome-provider contract boundary"));
    }

    @Test
    void rebornStillKeepsCaveniaBiomeProviderAndResourcesInactive() throws IOException {
        String dimensionsSource = Files.readString(DIMENSIONS_SOURCE, StandardCharsets.UTF_8);

        assertTrue(dimensionsSource.contains("CAVENIA_LOCATION"));
        assertTrue(dimensionsSource.contains("CAVENIA_LEVEL_KEY"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");

        try (Stream<Path> dimensionFiles = Files.list(DIMENSION_PACKAGE)) {
            assertTrue(
                dimensionFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Cavenia")),
                "Expected the biome-provider boundary to keep Cavenia-specific runtime dimension classes absent"
            );
        }

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/worldgen/biome/cavenia")
                            || path.contains("/tags/worldgen/biome/cavenia")
                            || path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/cavenia/")
                            || path.contains("cavenia_")
                    ),
                "Expected the biome-provider boundary to keep active Cavenia biome/worldgen resources absent from checked-in data resources"
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
