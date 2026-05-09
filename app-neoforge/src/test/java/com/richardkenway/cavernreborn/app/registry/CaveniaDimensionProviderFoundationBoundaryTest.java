package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaDimensionProviderFoundationBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path BIOME_PROVIDER_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path VEINS_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path POPULATION_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-population-lakes-falls-shroom-contract-boundary.md");
    private static final Path ACCESS_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-mirage-entry-access-contract-boundary.md");
    private static final Path SPAWN_PROVIDER_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md");
    private static final Path CRAZY_ROSTER_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-crazy-roster-natural-spawn-boundary.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path DIMENSION_PACKAGE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension"
    );
    private static final Path ENTITY_EVENTS = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
    );
    private static final Path DIMENSIONS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CavernNeoForgeDimensions.java"
    );

    @Test
    void docsStateTheLegacyCaveniaFoundationAndCurrentRebornBoundary() throws IOException {
        String readme = Files.readString(README);
        String boundaryDoc = Files.readString(BOUNDARY_DOC);
        String keyTypeBoundaryDoc = Files.readString(KEY_TYPE_BOUNDARY_DOC);
        String biomeProviderBoundaryDoc = Files.readString(BIOME_PROVIDER_BOUNDARY_DOC);
        String veinsBoundaryDoc = Files.readString(VEINS_BOUNDARY_DOC);
        String terrainBoundaryDoc = Files.readString(TERRAIN_BOUNDARY_DOC);
        String caveCarverBoundaryDoc = Files.readString(CAVE_CARVER_BOUNDARY_DOC);
        String populationBoundaryDoc = Files.readString(POPULATION_BOUNDARY_DOC);
        String accessBoundaryDoc = Files.readString(ACCESS_BOUNDARY_DOC);
        String spawnProviderBoundaryDoc = Files.readString(SPAWN_PROVIDER_BOUNDARY_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_BOUNDARY_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Cavenia Dimension / Provider Foundation Boundary"));
        assertTrue(readme.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(readme.contains("`WorldProviderCavenia`"));
        assertTrue(readme.contains("`ChunkGeneratorCavenia`"));
        assertTrue(readme.contains("`CaveniaConfig`"));
        assertTrue(readme.contains("full mirage-world stack"));

        assertTrue(boundaryDoc.contains("`WorldProviderCavenia`"));
        assertTrue(boundaryDoc.contains("`ChunkGeneratorCavenia`"));
        assertTrue(boundaryDoc.contains("`CaveniaConfig`"));
        assertTrue(boundaryDoc.contains("`CaveDimensions`"));
        assertTrue(boundaryDoc.contains("`WorldProviderCavern`"));
        assertTrue(boundaryDoc.contains("`createSpawnCreature(...)`"));
        assertTrue(boundaryDoc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(boundaryDoc.contains("CaveniaConfig.crazySpawnChance"));
        assertTrue(boundaryDoc.contains("`ICavenicMob` whose `isNonBoss()` returns `false`"));
        assertTrue(boundaryDoc.contains("no `ICavenicMob` whose `isNonBoss()` returns `false`"));
        assertTrue(boundaryDoc.contains("`dimensionId = -54`"));
        assertTrue(boundaryDoc.contains("`worldHeight = 128`"));
        assertTrue(boundaryDoc.contains("`generateCaves = true`"));
        assertTrue(boundaryDoc.contains("`generateLakes = true`"));
        assertTrue(boundaryDoc.contains("`monsterSpawn = 200`"));
        assertTrue(boundaryDoc.contains("`crazySpawnChance = 0.1D`"));
        assertTrue(boundaryDoc.contains("`caveBrightness = 0.125D`"));
        assertTrue(boundaryDoc.contains("`autoVeins = false`"));
        assertTrue(boundaryDoc.contains("no active `cavenia` dimension"));
        assertTrue(boundaryDoc.contains("no `WorldProviderCavenia` equivalent"));
        assertTrue(boundaryDoc.contains("no active Cavenia crazy-roster spawn callback"));
        assertTrue(boundaryDoc.contains("This increment does not add:"));
        assertTrue(boundaryDoc.contains("`data/cavernreborn/dimension/cavenia.json`"));
        assertTrue(boundaryDoc.contains("`data/cavernreborn/dimension_type/cavenia.json`"));
        assertTrue(boundaryDoc.contains("mirage-world entry/access contract"));
        assertTrue(boundaryDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));

        assertTrue(keyTypeBoundaryDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(biomeProviderBoundaryDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(veinsBoundaryDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(terrainBoundaryDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(caveCarverBoundaryDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(populationBoundaryDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(accessBoundaryDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(spawnProviderBoundaryDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Cavenia dimension/provider foundation boundary"));
    }

    @Test
    void rebornStillKeepsTheCaveniaFoundationInactive() throws IOException {
        String entityEvents = Files.readString(ENTITY_EVENTS, StandardCharsets.UTF_8);
        String dimensionsSource = Files.readString(DIMENSIONS_SOURCE, StandardCharsets.UTF_8);

        assertFalse(entityEvents.toLowerCase().contains("cavenia"));
        assertFalse(entityEvents.contains("crazySpawnChance"));
        assertTrue(dimensionsSource.contains("CAVENIA_LOCATION"));
        assertTrue(dimensionsSource.contains("CAVENIA_LEVEL_KEY"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaConfig.java");

        try (Stream<Path> dimensionFiles = Files.list(DIMENSION_PACKAGE)) {
            assertTrue(
                dimensionFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Cavenia")),
                "Expected the boundary-only slice to keep Cavenia-specific classes absent from the Reborn dimension package"
            );
        }

        try (Stream<Path> resourceFiles = Files.walk(resolveProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn"))) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path -> path.contains("/cavenia") || path.contains("cavenia_")),
                "Expected the boundary-only slice to keep active Cavenia resources absent from checked-in data resources"
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
