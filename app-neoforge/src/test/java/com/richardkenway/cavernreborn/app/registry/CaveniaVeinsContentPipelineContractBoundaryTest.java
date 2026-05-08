package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaVeinsContentPipelineContractBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path TERRAIN_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
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
    void docsPinTheLegacyVeinsContractAndInactiveRebornBoundary() throws IOException {
        String readme = Files.readString(README);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String terrainDoc = Files.readString(TERRAIN_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String populationDoc = Files.readString(POPULATION_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Cavenia VEINS / Content Pipeline Contract Boundary"));
        assertTrue(readme.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));

        assertTrue(contractDoc.contains("`CaveniaConfig.VEINS`"));
        assertTrue(contractDoc.contains("`syncVeinsConfig()`"));
        assertTrue(contractDoc.contains("`ChunkGeneratorCavenia`"));
        assertTrue(contractDoc.contains("`WorldProviderCavenia`"));
        assertTrue(contractDoc.contains("CaveVeinManager"));
        assertTrue(contractDoc.contains("CaveVein"));
        assertTrue(contractDoc.contains("CaveVeinProvider"));
        assertTrue(contractDoc.contains("VeinGenerator"));
        assertTrue(contractDoc.contains("`autoVeins = false`"));
        assertTrue(contractDoc.contains("`block`"));
        assertTrue(contractDoc.contains("`targetBlock`"));
        assertTrue(contractDoc.contains("`weight`"));
        assertTrue(contractDoc.contains("`chance`"));
        assertTrue(contractDoc.contains("`size`"));
        assertTrue(contractDoc.contains("`minHeight`"));
        assertTrue(contractDoc.contains("`maxHeight`"));
        assertTrue(contractDoc.contains("`biomes`"));
        assertTrue(contractDoc.contains("granite via `minecraft:stone` meta `1`"));
        assertTrue(contractDoc.contains("diorite via `minecraft:stone` meta `3`"));
        assertTrue(contractDoc.contains("andesite via `minecraft:stone` meta `5`"));
        assertTrue(contractDoc.contains("coal ore via `minecraft:coal_ore` meta `0`, target `minecraft:stone` meta `0`, weight `50`, chance `1.0D`, size `17`, minY `1`, maxY `127`"));
        assertTrue(contractDoc.contains("iron ore via `minecraft:iron_ore` meta `0`, target `minecraft:stone` meta `0`, weight `40`, chance `1.0D`, size `10`, minY `1`, maxY `127`"));
        assertTrue(contractDoc.contains("aquamarine ore via `cavern:cave_block` meta `0`, target `minecraft:stone` meta `0`, weight `12`, chance `1.0D`, size `8`, minY `20`, maxY `127`, biome filters expanded from `Type.COLD`, `Type.WATER` and `Type.WET`"));
        assertTrue(contractDoc.contains("magnite ore via `cavern:cave_block` meta `2`, target `minecraft:stone` meta `0`, weight `30`, chance `1.0D`, size `10`, minY `1`, maxY `127`"));
        assertTrue(contractDoc.contains("randomite ore via `cavern:cave_block` meta `4`, target `minecraft:stone` meta `0`, weight `24`, chance `1.0D`, size `4`, minY `1`, maxY `127`"));
        assertTrue(contractDoc.contains("hexcite ore via `cavern:cave_block` meta `5`, target `minecraft:stone` meta `0`, weight `4`, chance `1.0D`, size `5`, minY `1`, maxY `30`"));
        assertTrue(contractDoc.contains("fissured stone via `cavern:cave_block` meta `7`, target `minecraft:stone` meta `0`, weight `150`, chance `1.0D`, size `2`, minY `1`, maxY `127`"));
        assertTrue(contractDoc.contains("dirt via `minecraft:dirt` meta `0`, target `minecraft:stone` meta `0`, weight `20`, chance `1.0D`, size `25`, minY `1`, maxY `127`"));
        assertTrue(contractDoc.contains("gravel via `minecraft:gravel` meta `0`, target `minecraft:stone` meta `0`, weight `10`, chance `1.0D`, size `20`, minY `1`, maxY `127`"));
        assertTrue(contractDoc.contains("sand via `minecraft:sand` meta `0`, target `minecraft:stone` meta `0`, weight `10`, chance `1.0D`, size `20`, minY `1`, maxY `127`, biome filters expanded from `Type.SANDY`"));
        assertTrue(contractDoc.contains("gold ore"));
        assertTrue(contractDoc.contains("redstone ore"));
        assertTrue(contractDoc.contains("lapis ore"));
        assertTrue(contractDoc.contains("diamond ore"));
        assertTrue(contractDoc.contains("emerald ore"));
        assertTrue(contractDoc.contains("skips placement when the current biome matches one of the stored biome ids"));
        assertTrue(contractDoc.contains("after initial stone fill"));
        assertTrue(contractDoc.contains("after optional cave carving"));
        assertTrue(contractDoc.contains("after biome top/filter replacement"));
        assertTrue(contractDoc.contains("before the final `Chunk` instance is built"));
        assertTrue(contractDoc.contains("`generateLakes` is a dedicated config gate, not part of `VEINS`"));
        assertTrue(contractDoc.contains("`cavenic_shroom` is not part of `CaveniaConfig.VEINS`"));
        assertTrue(contractDoc.contains("There is no separate `generateCavenicShroom` config flag"));
        assertTrue(contractDoc.contains("`BiomeDecorator.generateFalls`"));
        assertTrue(contractDoc.contains("`cavernreborn:aquamarine_ore`"));
        assertTrue(contractDoc.contains("`cavernreborn:magnite_ore`"));
        assertTrue(contractDoc.contains("`cavernreborn:randomite_ore`"));
        assertTrue(contractDoc.contains("`cavernreborn:hexcite_ore`"));
        assertTrue(contractDoc.contains("`cavernreborn:fissured_stone`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavenic_shroom`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavern_ore_aquamarine`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavern_ore_magnite`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavern_ore_hexcite_deep`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavern_ore_hexcite_upper_rare`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavern_ore_randomite`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavern_fissured_stone`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavenic_shroom_patch`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavern_cavenic_shroom_patch`"));
        assertTrue(contractDoc.contains("configured/placed features"));
        assertTrue(contractDoc.contains("generator-side replacement"));
        assertTrue(contractDoc.contains("Cavenia-specific biome tags"));
        assertTrue(contractDoc.contains("feature ordering"));
        assertTrue(contractDoc.contains("content distribution rebalance"));
        assertTrue(contractDoc.contains("no active Cavenia configured features"));
        assertTrue(contractDoc.contains("no active Cavenia placed features"));
        assertTrue(contractDoc.contains("no active Cavenia biome modifiers"));
        assertTrue(contractDoc.contains("no active Cavenia noise settings, density functions, carvers, processor lists, structures or template pools"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));

        assertTrue(foundationDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(terrainDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(populationDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Cavenia VEINS/content-pipeline contract boundary"));
    }

    @Test
    void rebornStillKeepsCaveniaVeinsContentResourcesInactive() throws IOException {
        String dimensionsSource = Files.readString(DIMENSIONS_SOURCE, StandardCharsets.UTF_8);

        assertFalse(dimensionsSource.contains("CAVENIA_LOCATION"));
        assertFalse(dimensionsSource.contains("CAVENIA_LEVEL_KEY"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");

        try (Stream<Path> dimensionFiles = Files.list(DIMENSION_PACKAGE)) {
            assertTrue(
                dimensionFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Cavenia")),
                "Expected the VEINS/content boundary to keep Cavenia-specific runtime dimension classes absent"
            );
        }

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/processor_list/cavenia")
                            || path.contains("/worldgen/structure/cavenia")
                            || path.contains("/worldgen/template_pool/cavenia")
                            || path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/cavenia/")
                            || path.contains("cavenia_")
                    ),
                "Expected the VEINS/content boundary to keep active Cavenia content-pipeline resources absent from checked-in data resources"
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
