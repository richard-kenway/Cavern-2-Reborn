package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaChunkGeneratorTerrainPipelineContractBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
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
    void docsPinTheLegacyTerrainPipelineAndInactiveRebornBoundary() throws IOException {
        String readme = Files.readString(README);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Cavenia Chunk Generator / Terrain Pipeline Contract Boundary"));
        assertTrue(readme.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));

        assertTrue(contractDoc.contains("`ChunkGeneratorCavenia`"));
        assertTrue(contractDoc.contains("`WorldProviderCavenia`"));
        assertTrue(contractDoc.contains("`MapGenCaveniaCaves`"));
        assertTrue(contractDoc.contains("`CaveniaConfig.worldHeight = 128`"));
        assertTrue(contractDoc.contains("`CaveniaConfig.generateCaves = true`"));
        assertTrue(contractDoc.contains("`CaveniaConfig.generateLakes = true`"));
        assertTrue(contractDoc.contains("`CaveniaConfig.BIOMES`"));
        assertTrue(contractDoc.contains("`CaveniaConfig.VEINS`"));
        assertTrue(contractDoc.contains("`setBlocksInChunk(...)`"));
        assertTrue(contractDoc.contains("`replaceBiomeBlocks(...)`"));
        assertTrue(contractDoc.contains("`veinGenerator.generate(world, rand, biomesForGeneration, primer)`"));
        assertTrue(contractDoc.contains("`WorldGenBush(CAVENIC_SHROOM)`"));
        assertTrue(contractDoc.contains("`ChunkGeneratorCavenia#generateChunk(int chunkX, int chunkZ)`"));
        assertTrue(contractDoc.contains("base stone fill"));
        assertTrue(contractDoc.contains("optional cave carving"));
        assertTrue(contractDoc.contains("biome top/filter replacement"));
        assertTrue(contractDoc.contains("VEINS mutation"));
        assertTrue(contractDoc.contains("final `Chunk` construction"));
        assertTrue(contractDoc.contains("fills the entire `ChunkPrimer` with stone"));
        assertTrue(contractDoc.contains("bottom bedrock sits at `y = 0`"));
        assertTrue(contractDoc.contains("top bedrock sits at `y = 127`"));
        assertTrue(contractDoc.contains("`y = 128..255` is cleared back to air"));
        assertTrue(contractDoc.contains("writes `gravel` when `y <= 2`"));
        assertTrue(contractDoc.contains("writes water when `y - 1 < 10`"));
        assertTrue(contractDoc.contains("`generateStructures(...) -> false`"));
        assertTrue(contractDoc.contains("`getPossibleCreatures(...) -> Collections.emptyList()`"));
        assertTrue(contractDoc.contains("`ForgeEventFactory.onChunkPopulate(true, ...)`"));
        assertTrue(contractDoc.contains("`DecorateBiomeEvent.Pre`"));
        assertTrue(contractDoc.contains("`OreGenEvent.Post`"));
        assertTrue(contractDoc.contains("`BiomeDecorator.generateFalls`"));
        assertTrue(contractDoc.contains("`WorldGenLakes(Blocks.WATER)`"));
        assertTrue(contractDoc.contains("`WorldGenLakes(Blocks.LAVA)`"));
        assertTrue(contractDoc.contains("`WorldGenLiquids(Blocks.FLOWING_WATER)`"));
        assertTrue(contractDoc.contains("`WorldGenLiquids(Blocks.FLOWING_LAVA)`"));
        assertTrue(contractDoc.contains("no active Cavenia noise settings"));
        assertTrue(contractDoc.contains("no active Cavenia density functions"));
        assertTrue(contractDoc.contains("no active Cavenia carvers"));
        assertTrue(contractDoc.contains("no active Cavenia surface rules"));
        assertTrue(contractDoc.contains("custom chunk generator"));
        assertTrue(contractDoc.contains("custom cave carver"));
        assertTrue(contractDoc.contains("surface rules or equivalent top/filter mapping"));
        assertTrue(contractDoc.contains("strict ordering between carve, top/filter replacement and VEINS mutation"));
        assertTrue(contractDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));

        assertTrue(foundationDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Cavenia chunk-generator/terrain-pipeline contract boundary"));
    }

    @Test
    void rebornStillKeepsCaveniaTerrainGeneratorResourcesInactive() throws IOException {
        String dimensionsSource = Files.readString(DIMENSIONS_SOURCE, StandardCharsets.UTF_8);

        assertFalse(dimensionsSource.contains("CAVENIA_LOCATION"));
        assertFalse(dimensionsSource.contains("CAVENIA_LEVEL_KEY"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");

        try (Stream<Path> dimensionFiles = Files.list(DIMENSION_PACKAGE)) {
            assertTrue(
                dimensionFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Cavenia")),
                "Expected the terrain-pipeline boundary to keep Cavenia-specific runtime dimension classes absent"
            );
        }

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/world_preset/cavenia")
                            || path.contains("/worldgen/density_function/cavenia")
                            || path.contains("/worldgen/noise/cavenia")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/cavenia/")
                            || path.contains("cavenia_")
                    ),
                "Expected the terrain-pipeline boundary to keep active Cavenia generator resources absent from checked-in data resources"
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
