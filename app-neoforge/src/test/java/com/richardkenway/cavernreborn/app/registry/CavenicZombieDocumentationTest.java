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
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicZombieBaselineAndNaturalSpawnFollowUp() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Zombie Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Zombie Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-zombie-natural-spawn-mvp.md"));
        assertTrue(readme.contains("cavenic_zombie"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning follow-up"));
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
        assertTrue(doc.contains("reuses the vanilla zombie loot table"));
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
        assertTrue(doc.contains("custom loot remains out of scope"));
        assertTrue(doc.contains("custom AI remains out of scope"));
    }

    @Test
    void runtimeSmokeMentionsCavenicZombieNaturalSpawnCoverageAndManualBoundary() throws IOException {
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
