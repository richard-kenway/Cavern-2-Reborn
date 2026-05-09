package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaSpawnProviderCrazyRosterActivationContractBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path POPULATION_DOC = resolveProjectFile("docs", "cavenia-population-lakes-falls-shroom-contract-boundary.md");
    private static final Path ACCESS_DOC = resolveProjectFile("docs", "cavenia-mirage-entry-access-contract-boundary.md");
    private static final Path CRAZY_ROSTER_DOC = resolveProjectFile("docs", "cavenia-crazy-roster-natural-spawn-boundary.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-policy-mvp.md");
    private static final Path CAVEMAN_BOUNDARY_DOC = resolveProjectFile("docs", "caveman-cavenia-normal-roster-boundary.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path DIMENSIONS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CavernNeoForgeDimensions.java"
    );
    private static final Path ENTITY_EVENTS = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheLegacySpawnProviderContractAndInactiveRebornBoundary() throws IOException {
        String readme = Files.readString(README);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String terrainDoc = Files.readString(TERRAIN_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String populationDoc = Files.readString(POPULATION_DOC);
        String accessDoc = Files.readString(ACCESS_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String cavemanBoundaryDoc = Files.readString(CAVEMAN_BOUNDARY_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Cavenia Spawn Provider / Crazy Roster Activation Contract Boundary"));
        assertTrue(readme.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(readme.contains("Cavenia Spawn Provider Policy / Non-Runtime MVP"));
        assertTrue(readme.contains("docs/cavenia-spawn-provider-policy-mvp.md"));
        assertTrue(readme.contains("Caveman Baseline / Cavenia Normal Spawn Roster Boundary"));
        assertTrue(readme.contains("docs/caveman-cavenia-normal-roster-boundary.md"));

        assertTrue(contractDoc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(contractDoc.contains("`WorldProviderCavern`"));
        assertTrue(contractDoc.contains("`WorldProviderCavern#onWorldUpdateEntities()`"));
        assertTrue(contractDoc.contains("`CaveEntitySpawner`"));
        assertTrue(contractDoc.contains("`entitySpawner.findChunksForSpawning(worldServer, spawnHostileMobs, spawnPeacefulMobs, worldServer.getWorldInfo().getWorldTotalTime() % 400L == 0L)`"));
        assertTrue(contractDoc.contains("`world.countEntities(type, true) > maxNumber * playerCount / MOB_COUNT_DIV`"));
        assertTrue(contractDoc.contains("`CaveniaConfig.monsterSpawn`"));
        assertTrue(contractDoc.contains("`monsterSpawn = 200`"));
        assertTrue(contractDoc.contains("`CaveniaConfig.crazySpawnChance`"));
        assertTrue(contractDoc.contains("`crazySpawnChance = 0.1D`"));
        assertTrue(contractDoc.contains("`0.0D .. 1.0D`"));
        assertTrue(contractDoc.contains("`CaveEntityRegistry.SPAWNS`"));
        assertTrue(contractDoc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCavenicSkeleton.class, 20, 1, 1)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCavenicCreeper.class, 30, 1, 1)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCavenicZombie.class, 30, 2, 2)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCavenicSpider.class, 30, 1, 1)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCavenicWitch.class, 15, 1, 1)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCavenicBear.class, 30, 1, 1)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCaveman.class, 35, 1, 1)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCrazySkeleton.class, 1, 1, 1)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCrazyCreeper.class, 1, 1, 1)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCrazyZombie.class, 1, 1, 1)`"));
        assertTrue(contractDoc.contains("`new SpawnListEntry(EntityCrazySpider.class, 1, 1, 1)`"));
        assertTrue(contractDoc.contains("`chance <= 0.1D` -> `range = 50`"));
        assertTrue(contractDoc.contains("`chance <= 0.2D` -> `range = 32`"));
        assertTrue(contractDoc.contains("`chance <= 0.4D` -> `range = 16`"));
        assertTrue(contractDoc.contains("`chance <= 0.6D` -> `range = 8`"));
        assertTrue(contractDoc.contains("`chance <= 0.8D` -> `range = 4`"));
        assertTrue(contractDoc.contains("otherwise `range = 0`"));
        assertTrue(contractDoc.contains("`rangeY = range > 1 ? range / 2 : range`"));
        assertTrue(contractDoc.contains("`ICavenicMob`"));
        assertTrue(contractDoc.contains("`isNonBoss()`"));
        assertTrue(contractDoc.contains("`entity instanceof ICavenicMob && !entity.isNonBoss()`"));
        assertTrue(contractDoc.contains("source-literally, the scan is for nearby `ICavenicMob` entities whose `isNonBoss()` returns `false`"));
        assertTrue(contractDoc.contains("`EntityCavenicSkeleton`"));
        assertTrue(contractDoc.contains("`EntityCavenicCreeper`"));
        assertTrue(contractDoc.contains("`EntityCavenicZombie`"));
        assertTrue(contractDoc.contains("`EntityCavenicSpider`"));
        assertTrue(contractDoc.contains("`EntityCavenicWitch`"));
        assertTrue(contractDoc.contains("`EntityCavenicBear`"));
        assertTrue(contractDoc.contains("`EntityCaveman`"));
        assertTrue(contractDoc.contains("`EntityCrazySkeleton`"));
        assertTrue(contractDoc.contains("`EntityCrazyCreeper`"));
        assertTrue(contractDoc.contains("`EntityCrazyZombie`"));
        assertTrue(contractDoc.contains("`EntityCrazySpider`"));
        assertTrue(contractDoc.contains("I did not find any other `ICavenicMob` implementations in the legacy source tree."));
        assertTrue(contractDoc.contains("The direct Cavenic classes inspected for the normal roster do implement `ICavenicMob`"));
        assertTrue(contractDoc.contains("they do not override `isNonBoss()` in their own class bodies"));
        assertTrue(contractDoc.contains("`getMaxSpawnedInChunk() == 1`"));
        assertTrue(contractDoc.contains("no active Cavenia provider spawn loop"));
        assertTrue(contractDoc.contains("no active crazy natural spawning"));
        assertTrue(contractDoc.contains("no fake normal `CAVERN` crazy biome modifiers, biome tags or spawn placements"));
        assertTrue(contractDoc.contains("docs/cavenia-spawn-provider-policy-mvp.md"));
        assertTrue(contractDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));

        assertTrue(foundationDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(terrainDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(populationDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(accessDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-spawn-provider-policy-mvp.md"));
        assertTrue(policyDoc.contains("`CaveniaSpawnProviderPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaSpawnEntry`"));
        assertTrue(policyDoc.contains("`DEFAULT_MONSTER_SPAWN = 200`"));
        assertTrue(policyDoc.contains("`DEFAULT_CRAZY_SPAWN_CHANCE = 0.1D`"));
        assertTrue(policyDoc.contains("`chance <= 0.1D` -> `range = 50`"));
        assertTrue(policyDoc.contains("`chance <= 0.2D` -> `range = 32`"));
        assertTrue(policyDoc.contains("`chance <= 0.4D` -> `range = 16`"));
        assertTrue(policyDoc.contains("`chance <= 0.6D` -> `range = 8`"));
        assertTrue(policyDoc.contains("`chance <= 0.8D` -> `range = 4`"));
        assertTrue(policyDoc.contains("otherwise `range = 0`"));
        assertTrue(policyDoc.contains("`rangeY = range > 1 ? range / 2 : range`"));
        assertTrue(policyDoc.contains("nearby `ICavenicMob` whose `isNonBoss()` returns `false`"));
        assertTrue(policyDoc.contains("`EntityCaveman` -> deferred marker `deferred:caveman`"));
        assertTrue(policyDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(policyDoc.contains("no active Cavenia spawning"));
        assertTrue(policyDoc.contains("no active crazy natural spawning"));
        assertTrue(policyDoc.contains("no Caveman entity implementation"));
        assertTrue(cavemanBoundaryDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(cavemanBoundaryDoc.contains("`player.displayGUIChest(backpackInventory)`"));
        assertTrue(cavemanBoundaryDoc.contains("`ItemMirageBook.getRandomBook()`"));
        assertTrue(cavemanBoundaryDoc.contains("`ItemMagicBook.getRandomBook()`"));
        assertTrue(cavemanBoundaryDoc.contains("no registered `cavernreborn:caveman` entity type"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Cavenia spawn-provider / crazy-roster activation contract boundary"));
        assertTrue(runtimeSmokeDoc.contains("inactive Caveman / Cavenia normal-roster boundary"));
        assertTrue(runtimeSmokeDoc.contains("non-runtime Cavenia spawn-provider policy constant and roster smoke"));
    }

    @Test
    void rebornStillKeepsCaveniaSpawnProviderResourcesInactive() throws IOException {
        String dimensionsSource = Files.readString(DIMENSIONS_SOURCE, StandardCharsets.UTF_8);
        String entityEvents = Files.readString(ENTITY_EVENTS, StandardCharsets.UTF_8);

        assertFalse(dimensionsSource.contains("CAVENIA_LOCATION"));
        assertFalse(dimensionsSource.contains("CAVENIA_LEVEL_KEY"));
        assertFalse(entityEvents.contains("CrazyZombie::check"));
        assertFalse(entityEvents.contains("CrazySkeleton::check"));
        assertFalse(entityEvents.contains("CrazyCreeper::check"));
        assertFalse(entityEvents.contains("CrazySpider::check"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_zombie_spawns.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_skeleton_spawns.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_creeper_spawns.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_spider_spawns.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_zombie.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_skeleton.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_creeper.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_spider.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnCallback.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name -> name.contains("CaveniaSpawn") || name.contains("CrazySpawn")),
                "Expected the spawn-provider boundary to keep Cavenia-specific spawn host/callback classes absent"
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
                            || path.contains("/neoforge/biome_modifier/crazy_")
                            || path.contains("/tags/worldgen/biome/spawns_crazy_")
                            || path.contains("/cavenia/")
                            || path.contains("cavenia_")
                    ),
                "Expected the spawn-provider boundary to keep active Cavenia and fake normal CAVERN crazy-spawn resources absent"
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
