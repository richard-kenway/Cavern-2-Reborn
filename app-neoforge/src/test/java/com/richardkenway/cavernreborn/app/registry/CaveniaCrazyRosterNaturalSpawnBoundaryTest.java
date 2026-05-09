package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaCrazyRosterNaturalSpawnBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-crazy-roster-natural-spawn-boundary.md");
    private static final Path ACCESS_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-mirage-entry-access-contract-boundary.md");
    private static final Path SPAWN_PROVIDER_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-policy-mvp.md");
    private static final Path CAVEMAN_BOUNDARY_DOC = resolveProjectFile("docs", "caveman-cavenia-normal-roster-boundary.md");
    private static final Path ZOMBIE_NATURAL_SPAWN_DOC = resolveProjectFile("docs", "crazy-zombie-natural-spawn-absent-or-deferred.md");
    private static final Path ZOMBIE_BASELINE_DOC = resolveProjectFile("docs", "crazy-zombie-baseline-mvp.md");
    private static final Path SKELETON_BASELINE_DOC = resolveProjectFile("docs", "crazy-skeleton-baseline-mvp.md");
    private static final Path CREEPER_BASELINE_DOC = resolveProjectFile("docs", "crazy-creeper-baseline-mvp.md");
    private static final Path SPIDER_BASELINE_DOC = resolveProjectFile("docs", "crazy-spider-baseline-mvp.md");
    private static final Path ENTITY_EVENTS = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
    );
    private static final Path DIMENSION_DIR = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension"
    );

    @Test
    void readmeAndCrazyMobDocsMentionTheSharedCaveniaCrazyRosterBoundary() throws IOException {
        String readme = Files.readString(README);
        String boundaryDoc = Files.readString(BOUNDARY_DOC);
        String accessBoundaryDoc = Files.readString(ACCESS_BOUNDARY_DOC);
        String spawnProviderBoundaryDoc = Files.readString(SPAWN_PROVIDER_BOUNDARY_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String cavemanBoundaryDoc = Files.readString(CAVEMAN_BOUNDARY_DOC);
        String zombieNaturalSpawnDoc = Files.readString(ZOMBIE_NATURAL_SPAWN_DOC);
        String zombieBaselineDoc = Files.readString(ZOMBIE_BASELINE_DOC);
        String skeletonBaselineDoc = Files.readString(SKELETON_BASELINE_DOC);
        String creeperBaselineDoc = Files.readString(CREEPER_BASELINE_DOC);
        String spiderBaselineDoc = Files.readString(SPIDER_BASELINE_DOC);

        assertTrue(readme.contains("Cavenia Crazy Roster Natural Spawn Boundary MVP"));
        assertTrue(readme.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(readme.contains("Cavenia Spawn Provider / Crazy Roster Activation Contract Boundary"));
        assertTrue(readme.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(readme.contains("Cavenia Spawn Provider Policy / Non-Runtime MVP"));
        assertTrue(readme.contains("docs/cavenia-spawn-provider-policy-mvp.md"));
        assertTrue(readme.contains("Caveman Baseline / Cavenia Normal Spawn Roster Boundary"));
        assertTrue(readme.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(readme.contains("the old Cavenia-only `crazySpawnChance` plus a nearby `ICavenicMob` scan for `!entity.isNonBoss()`"));

        assertTrue(boundaryDoc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(boundaryDoc.contains("`WorldProviderCavern#createSpawnCreature(...)`"));
        assertTrue(boundaryDoc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(boundaryDoc.contains("`CaveniaConfig.crazySpawnChance`"));
        assertTrue(boundaryDoc.contains("`ICavenicMob`"));
        assertTrue(boundaryDoc.contains("`world.getEntitiesWithinAABB(..., entity -> entity instanceof ICavenicMob && !entity.isNonBoss()).isEmpty()`"));
        assertTrue(boundaryDoc.contains("a nearby `ICavenicMob` scan for `!entity.isNonBoss()`"));
        assertTrue(boundaryDoc.contains("`new SpawnListEntry(EntityCrazySkeleton.class, 1, 1, 1)`"));
        assertTrue(boundaryDoc.contains("`new SpawnListEntry(EntityCrazyCreeper.class, 1, 1, 1)`"));
        assertTrue(boundaryDoc.contains("`new SpawnListEntry(EntityCrazyZombie.class, 1, 1, 1)`"));
        assertTrue(boundaryDoc.contains("`new SpawnListEntry(EntityCrazySpider.class, 1, 1, 1)`"));
        assertTrue(boundaryDoc.contains("`EntityCrazyZombie#getMaxSpawnedInChunk()`"));
        assertTrue(boundaryDoc.contains("`EntityCrazySkeleton#getMaxSpawnedInChunk()`"));
        assertTrue(boundaryDoc.contains("`EntityCrazyCreeper#getMaxSpawnedInChunk()`"));
        assertTrue(boundaryDoc.contains("`EntityCrazySpider#getMaxSpawnedInChunk()`"));
        assertTrue(boundaryDoc.contains("`chance <= 0.1D` -> `range = 50`"));
        assertTrue(boundaryDoc.contains("`chance <= 0.2D` -> `range = 32`"));
        assertTrue(boundaryDoc.contains("`chance <= 0.4D` -> `range = 16`"));
        assertTrue(boundaryDoc.contains("`chance <= 0.6D` -> `range = 8`"));
        assertTrue(boundaryDoc.contains("`chance <= 0.8D` -> `range = 4`"));
        assertTrue(boundaryDoc.contains("does not contain summon variants"));
        assertTrue(boundaryDoc.contains("does not contain direct Cavenic mobs"));
        assertTrue(boundaryDoc.contains("does not contain non-Crazy mobs"));
        assertTrue(boundaryDoc.contains("Reborn does not add fake normal `CAVERN` natural spawning for any of them."));
        assertTrue(boundaryDoc.contains("Reborn also does not register normal spawn placement for:"));
        assertTrue(boundaryDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(boundaryDoc.contains("docs/cavenia-spawn-provider-policy-mvp.md"));
        assertTrue(boundaryDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(boundaryDoc.contains("`CRAZY_ZOMBIE`"));
        assertTrue(boundaryDoc.contains("`CRAZY_SKELETON`"));
        assertTrue(boundaryDoc.contains("`CRAZY_CREEPER`"));
        assertTrue(boundaryDoc.contains("`CRAZY_SPIDER`"));

        assertTrue(zombieNaturalSpawnDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(zombieBaselineDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(skeletonBaselineDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(creeperBaselineDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(spiderBaselineDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(accessBoundaryDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(spawnProviderBoundaryDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(spawnProviderBoundaryDoc.contains("docs/cavenia-spawn-provider-policy-mvp.md"));
        assertTrue(spawnProviderBoundaryDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(policyDoc.contains("`EntityCrazySkeleton` -> `cavernreborn:crazy_skeleton`, weight `1`, group `1 / 1`"));
        assertTrue(policyDoc.contains("`EntityCrazyCreeper` -> `cavernreborn:crazy_creeper`, weight `1`, group `1 / 1`"));
        assertTrue(policyDoc.contains("`EntityCrazyZombie` -> `cavernreborn:crazy_zombie`, weight `1`, group `1 / 1`"));
        assertTrue(policyDoc.contains("`EntityCrazySpider` -> `cavernreborn:crazy_spider`, weight `1`, group `1 / 1`"));
        assertTrue(policyDoc.contains("`EntityCaveman` -> deferred marker `deferred:caveman`"));
        assertTrue(cavemanBoundaryDoc.contains("Legacy `CaveEntityRegistry.SPAWNS` includes:"));
    }

    @Test
    void rebornStillKeepsAllCrazyMobNaturalSpawnWiringAbsentOutsideTheSharedBoundaryDoc() throws IOException {
        String entityEventSource = Files.readString(ENTITY_EVENTS, StandardCharsets.UTF_8);

        assertFalse(entityEventSource.contains("CrazyZombie::check"));
        assertFalse(entityEventSource.contains("CrazySkeleton::check"));
        assertFalse(entityEventSource.contains("CrazyCreeper::check"));
        assertFalse(entityEventSource.contains("CrazySpider::check"));
        assertFalse(entityEventSource.contains("ModRegistries.CRAZY_ZOMBIE.get(),\n            SpawnPlacementTypes"));
        assertFalse(entityEventSource.contains("ModRegistries.CRAZY_SKELETON.get(),\n            SpawnPlacementTypes"));
        assertFalse(entityEventSource.contains("ModRegistries.CRAZY_CREEPER.get(),\n            SpawnPlacementTypes"));
        assertFalse(entityEventSource.contains("ModRegistries.CRAZY_SPIDER.get(),\n            SpawnPlacementTypes"));

        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_zombie_spawns.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_skeleton_spawns.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_creeper_spawns.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_spider_spawns.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_zombie.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_skeleton.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_creeper.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_spider.json"
        );
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");

        try (Stream<Path> dimensionFiles = Files.list(DIMENSION_DIR)) {
            assertTrue(
                dimensionFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Cavenia")),
                "Expected the boundary-only slice to keep Cavenia dimension/provider code absent from the Reborn dimension package"
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
