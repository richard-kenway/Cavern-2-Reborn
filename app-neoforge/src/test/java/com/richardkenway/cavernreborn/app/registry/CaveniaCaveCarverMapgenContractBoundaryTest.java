package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaCaveCarverMapgenContractBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path POPULATION_DOC = resolveProjectFile("docs", "cavenia-population-lakes-falls-shroom-contract-boundary.md");
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
    void docsPinTheLegacyCaveCarverContractAndInactiveRebornBoundary() throws IOException {
        String readme = Files.readString(README);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String terrainDoc = Files.readString(TERRAIN_DOC);
        String populationDoc = Files.readString(POPULATION_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Cavenia Cave Carver / MapGenCaveniaCaves Contract Boundary"));
        assertTrue(readme.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));

        assertTrue(contractDoc.contains("`MapGenCaveniaCaves`"));
        assertTrue(contractDoc.contains("`MapGenCavernCaves`"));
        assertTrue(contractDoc.contains("`MapGenCaves`"));
        assertTrue(contractDoc.contains("`CaveniaConfig.generateCaves = true`"));
        assertTrue(contractDoc.contains("`ChunkGeneratorCavenia`"));
        assertTrue(contractDoc.contains("`addTunnel(...)`"));
        assertTrue(contractDoc.contains("`recursiveGenerate(...)`"));
        assertTrue(contractDoc.contains("`digBlock(...)`"));
        assertTrue(contractDoc.contains("`addRoom(...)`"));
        assertTrue(contractDoc.contains("`blockY = 20 + rand.nextInt(5)`"));
        assertTrue(contractDoc.contains("`world.getActualHeight()`"));
        assertTrue(contractDoc.contains("`world.provider.getActualHeight()`"));
        assertTrue(contractDoc.contains("`y <= 2`"));
        assertTrue(contractDoc.contains("`y - 1 < 10`"));
        assertTrue(contractDoc.contains("`Blocks.GRAVEL`"));
        assertTrue(contractDoc.contains("`Blocks.WATER`"));
        assertTrue(contractDoc.contains("`Blocks.AIR`"));
        assertTrue(contractDoc.contains("write `Blocks.GRAVEL`"));
        assertTrue(contractDoc.contains("write `Blocks.WATER`"));
        assertTrue(contractDoc.contains("write `Blocks.AIR`"));
        assertTrue(contractDoc.contains("rand.nextInt(rand.nextInt(rand.nextInt(10) + 1) + 1)"));
        assertTrue(contractDoc.contains("`rand.nextInt(3) == 0`"));
        assertTrue(contractDoc.contains("rand.nextInt(5) == 0"));
        assertTrue(contractDoc.contains("`scaleHeight = 1.35D`"));
        assertTrue(contractDoc.contains("scale + 18.0F"));
        assertTrue(contractDoc.contains("random.nextInt(6) == 0"));
        assertTrue(contractDoc.contains("after the full stone base fill"));
        assertTrue(contractDoc.contains("before biome top/filter replacement"));
        assertTrue(contractDoc.contains("before `CaveniaConfig.VEINS`"));
        assertTrue(contractDoc.contains("custom carver"));
        assertTrue(contractDoc.contains("configured-carver data alone is likely not enough"));
        assertTrue(contractDoc.contains("no active Cavenia configured carver resources"));
        assertTrue(contractDoc.contains("no active Cavenia noise settings"));
        assertTrue(contractDoc.contains("no active Cavenia density functions"));
        assertTrue(contractDoc.contains("no active Cavenia chunk generator"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));

        assertTrue(foundationDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(terrainDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(populationDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Cavenia cave-carver / `MapGenCaveniaCaves` contract boundary"));
    }

    @Test
    void rebornStillKeepsCaveniaCaveCarverResourcesInactive() throws IOException {
        String dimensionsSource = Files.readString(DIMENSIONS_SOURCE, StandardCharsets.UTF_8);

        assertFalse(dimensionsSource.contains("CAVENIA_LOCATION"));
        assertFalse(dimensionsSource.contains("CAVENIA_LEVEL_KEY"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCavernCaves.java");

        try (Stream<Path> dimensionFiles = Files.list(DIMENSION_PACKAGE)) {
            assertTrue(
                dimensionFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Cavenia") || name.contains("CavernCaves")),
                "Expected the cave-carver boundary to keep Cavenia-specific runtime dimension and carver classes absent"
            );
        }

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/density_function/cavenia")
                            || path.contains("/worldgen/noise/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/cavenia/")
                            || path.contains("cavenia_")
                    ),
                "Expected the cave-carver boundary to keep active Cavenia carver/worldgen resources absent from checked-in data resources"
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
