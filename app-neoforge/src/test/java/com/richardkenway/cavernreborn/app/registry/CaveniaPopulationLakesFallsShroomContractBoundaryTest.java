package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaPopulationLakesFallsShroomContractBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-population-lakes-falls-shroom-contract-boundary.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path CAVENIC_SHROOM_DOC = resolveProjectFile("docs", "cavenic-shroom-mvp.md");
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
    void docsPinTheLegacyPopulationContractAndInactiveRebornBoundary() throws IOException {
        String readme = Files.readString(README);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String terrainDoc = Files.readString(TERRAIN_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String cavenicShroomDoc = Files.readString(CAVENIC_SHROOM_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Cavenia Population / Lakes / Falls / Cavenic Shroom Contract Boundary"));
        assertTrue(readme.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));

        assertTrue(contractDoc.contains("`ChunkGeneratorCavenia#populate(...)`"));
        assertTrue(contractDoc.contains("`CaveniaConfig.generateLakes = true`"));
        assertTrue(contractDoc.contains("`ForgeEventFactory.onChunkPopulate(true, this, world, rand, chunkX, chunkZ, false)`"));
        assertTrue(contractDoc.contains("`DecorateBiomeEvent.Pre`"));
        assertTrue(contractDoc.contains("`OreGenEvent.Post`"));
        assertTrue(contractDoc.contains("`MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(world, rand, chunkPos))`"));
        assertTrue(contractDoc.contains("`ForgeEventFactory.onChunkPopulate(false, this, world, rand, chunkX, chunkZ, false)`"));
        assertTrue(contractDoc.contains("`BlockFalling.fallInstantly = true`"));
        assertTrue(contractDoc.contains("`BlockFalling.fallInstantly = false`"));
        assertTrue(contractDoc.contains("world.provider.getActualHeight()"));
        assertTrue(contractDoc.contains("`WorldGenLakes(Blocks.WATER)`"));
        assertTrue(contractDoc.contains("`WorldGenLakes(Blocks.LAVA)`"));
        assertTrue(contractDoc.contains("`WorldGenBush(CaveBlocks.CAVENIC_SHROOM)`"));
        assertTrue(contractDoc.contains("`WorldGenLiquids(Blocks.FLOWING_WATER)`"));
        assertTrue(contractDoc.contains("`WorldGenLiquids(Blocks.FLOWING_LAVA)`"));
        assertTrue(contractDoc.contains("outside `CaveniaConfig.VEINS`"));
        assertTrue(contractDoc.contains("`rand.nextInt(4) == 0`"));
        assertTrue(contractDoc.contains("`rand.nextInt(10) == 0`"));
        assertTrue(contractDoc.contains("`rand.nextInt(6) <= i`"));
        assertTrue(contractDoc.contains("rand.nextInt(worldHeight - 32) + 16"));
        assertTrue(contractDoc.contains("rand.nextInt(worldHeight - 16)"));
        assertTrue(contractDoc.contains("rand.nextInt(worldHeight / 2 - 16) + 32"));
        assertTrue(contractDoc.contains("rand.nextInt(rand.nextInt(worldHeight - 16) + 10)"));
        assertTrue(contractDoc.contains("rand.nextInt(worldHeight / 2 - 32) + 20"));
        assertTrue(contractDoc.contains("mushroom biomes: `3 / 6`"));
        assertTrue(contractDoc.contains("Nether biomes: `2 / 6`"));
        assertTrue(contractDoc.contains("all other biomes: `1 / 6`"));
        assertTrue(contractDoc.contains("There is no separate inspected config flag named:"));
        assertTrue(contractDoc.contains("`generateCavenicShroom`"));
        assertTrue(contractDoc.contains("`generateFalls`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavenic_shroom_patch`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavern_cavenic_shroom_patch`"));
        assertTrue(contractDoc.contains("configured/placed features"));
        assertTrue(contractDoc.contains("custom population hooks"));
        assertTrue(contractDoc.contains("strict feature ordering"));
        assertTrue(contractDoc.contains("no active Cavenia lake configured/placed features"));
        assertTrue(contractDoc.contains("no active Cavenia fall configured/placed features"));
        assertTrue(contractDoc.contains("no active Cavenia shroom configured/placed features"));
        assertTrue(contractDoc.contains("no active Cavenia biome modifiers"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenic-shroom-mvp.md"));

        assertTrue(foundationDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(terrainDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(cavenicShroomDoc.contains("No Cavenia yet."));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Cavenia population / lakes / falls / `cavenic_shroom` contract boundary"));
    }

    @Test
    void rebornStillKeepsCaveniaPopulationResourcesInactive() throws IOException {
        String dimensionsSource = Files.readString(DIMENSIONS_SOURCE, StandardCharsets.UTF_8);

        assertFalse(dimensionsSource.contains("CAVENIA_LOCATION"));
        assertFalse(dimensionsSource.contains("CAVENIA_LEVEL_KEY"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaPopulationHooks.java");

        try (Stream<Path> dimensionFiles = Files.list(DIMENSION_PACKAGE)) {
            assertTrue(
                dimensionFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Cavenia")),
                "Expected the population boundary to keep Cavenia-specific runtime dimension classes absent"
            );
        }

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/worldgen/configured_feature/cavenia_lake")
                            || path.contains("/worldgen/placed_feature/cavenia_lake")
                            || path.contains("/worldgen/configured_feature/cavenia_fall")
                            || path.contains("/worldgen/placed_feature/cavenia_fall")
                            || path.contains("/worldgen/configured_feature/cavenia_shroom")
                            || path.contains("/worldgen/placed_feature/cavenia_shroom")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/cavenia/")
                            || path.contains("cavenia_")
                    ),
                "Expected the population boundary to keep active Cavenia population resources absent from checked-in data resources"
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
