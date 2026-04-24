package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicZombieDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_ZOMBIE_BASELINE_MVP = resolveProjectFile("docs", "cavenic-zombie-baseline-mvp.md");
    private static final Path CAVENIC_ZOMBIE_NATURAL_SPAWN_MVP = resolveProjectFile("docs", "cavenic-zombie-natural-spawn-mvp.md");
    private static final Path CAVENIC_ZOMBIE_ORB_DROP_MVP = resolveProjectFile("docs", "cavenic-zombie-orb-drop-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicZombieBaselineNaturalSpawnAndOrbDropFollowUps() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Zombie Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Zombie Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-natural-spawn-mvp.md"));
        assertTrue(readme.contains("Cavenic Zombie Legacy Orb Drop MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-orb-drop-mvp.md"));
        assertTrue(readme.contains("cavenic_zombie"));
        assertTrue(readme.contains("legacy `1/8` `cavenic_orb` drop"));
    }

    @Test
    void cavenicZombieBaselineDocStatesLegacyReferencesAttributesSpawnEggRendererAndFollowUpBoundary() throws IOException {
        String doc = Files.readString(CAVENIC_ZOMBIE_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_zombie`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_zombie_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicZombie`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicZombie`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `Zombie`"));
        assertTrue(doc.contains("max health: `50.0`"));
        assertTrue(doc.contains("follow range: `50.0`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("attack damage: `5.0`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0x00A0A0`"));
        assertTrue(doc.contains("reuses the vanilla zombie loot table as its base drop source"));
        assertTrue(doc.contains("Legacy orb-drop parity is now documented separately"));
        assertTrue(doc.contains("`docs/cavenic-zombie-orb-drop-mvp.md`"));
        assertTrue(doc.contains("Natural spawning is now documented separately"));
        assertTrue(doc.contains("`docs/cavenic-zombie-natural-spawn-mvp.md`"));
        assertTrue(doc.contains("Additional Cavenic mobs"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void cavenicZombieNaturalSpawnDocStatesSpawnRuleTargetingLegacyValuesAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_ZOMBIE_NATURAL_SPAWN_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicZombie`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("weight: `30`"));
        assertTrue(doc.contains("group size: `2..2`"));
        assertTrue(doc.contains("`RegisterSpawnPlacementsEvent`"));
        assertTrue(doc.contains("`SpawnPlacementTypes.ON_GROUND`"));
        assertTrue(doc.contains("`Heightmap.Types.MOTION_BLOCKING_NO_LEAVES`"));
        assertTrue(doc.contains("`Monster.checkMonsterSpawnRules`"));
        assertTrue(doc.contains("`cavernreborn:spawns_cavenic_zombie`"));
        assertTrue(doc.contains("`data/cavernreborn/neoforge/biome_modifier/cavenic_zombie_spawns.json`"));
        assertTrue(doc.contains("`cavernreborn:stone_depths`"));
        assertTrue(doc.contains("`cavernreborn:lush_grotto`"));
        assertTrue(doc.contains("`cavernreborn:dripstone_grotto`"));
        assertTrue(doc.contains("`cavernreborn:highland_hollows`"));
        assertTrue(doc.contains("CAVERN-only"));
        assertTrue(doc.contains("custom loot was out of scope in this natural-spawn slice"));
        assertTrue(doc.contains("`docs/cavenic-zombie-orb-drop-mvp.md`"));
        assertTrue(doc.contains("custom AI remains out of scope"));
    }

    @Test
    void cavenicZombieOrbDropDocStatesLegacyChanceVanillaLootPreservationAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_ZOMBIE_ORB_DROP_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicZombie`"));
        assertTrue(doc.contains("`dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)`"));
        assertTrue(doc.contains("`rand.nextInt(8) == 0`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_orb`"));
        assertTrue(doc.contains("`1/8`"));
        assertTrue(doc.contains("vanilla zombie loot table as the base drop source"));
        assertTrue(doc.contains("`LivingDropsEvent`"));
        assertTrue(doc.contains("`CavenicZombieLootPolicy.ORB_DROP_ROLL_BOUND = 8`"));
        assertTrue(doc.contains("Looting does not affect the orb chance"));
        assertTrue(doc.contains("The orb drop does not require a player kill"));
        assertTrue(doc.contains("No progression, dimension or economy hook changes"));
        assertTrue(doc.contains("Additional Cavenic mobs remain out of scope"));
        assertTrue(doc.contains("Cavenia remains out of scope"));
    }

    @Test
    void runtimeSmokeMentionsCavenicZombieOrbDropCoverageAndManualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic zombie runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic zombie attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic zombie hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic zombie spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic zombie spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("cavenic zombie natural spawn placement registration"));
        assertTrue(runtimeSmoke.contains("cavenic zombie CAVERN-only spawn predicate smoke"));
        assertTrue(runtimeSmoke.contains("cavenic zombie biome modifier registry smoke"));
        assertTrue(runtimeSmoke.contains("cavenic zombie biome tag resolution"));
        assertTrue(runtimeSmoke.contains("cavenic zombie vanilla loot-table baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic zombie legacy orb-drop event wiring smoke"));
        assertTrue(runtimeSmoke.contains("cavenic zombie legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(runtimeSmoke.contains("actual long-run cavenic zombie orb-drop rate balance"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Zombie renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("actual long-run Cavenic Zombie population balance inside CAVERN"));
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
}
