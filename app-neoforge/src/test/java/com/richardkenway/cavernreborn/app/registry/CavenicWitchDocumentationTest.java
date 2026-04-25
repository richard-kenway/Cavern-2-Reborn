package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicWitchDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_WITCH_BASELINE_MVP = resolveProjectFile("docs", "cavenic-witch-baseline-mvp.md");
    private static final Path CAVENIC_WITCH_NATURAL_SPAWN_MVP = resolveProjectFile("docs", "cavenic-witch-natural-spawn-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicWitchBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Witch Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Witch Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-natural-spawn-mvp.md"));
        assertTrue(readme.contains("cavenic_witch"));
        assertTrue(readme.contains("fifth direct Cavenic mob foundation"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
        assertTrue(readme.contains("vanilla witch loot baseline"));
        assertTrue(readme.contains("custom potion logic, orb-drop parity, the magic-book drop branch and damage-behavior parity remain intentionally out of scope"));
    }

    @Test
    void cavenicWitchBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_witch`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_witch_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicWitch`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `Witch`"));
        assertTrue(doc.contains("max health: `50.0`"));
        assertTrue(doc.contains("follow range: `32.0`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("movement speed intentionally stays on the vanilla witch baseline"));
        assertTrue(doc.contains("potion-throw behavior intentionally stays on the vanilla witch baseline"));
        assertTrue(doc.contains("XP reward is pinned to `12`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0x4A5348`"));
        assertTrue(doc.contains("reuses the vanilla witch renderer/model path"));
        assertTrue(doc.contains("reuses the vanilla witch loot table as its base drop source"));
        assertTrue(doc.contains("The bounded natural-spawn follow-up is now documented separately in `docs/cavenic-witch-natural-spawn-mvp.md`."));
        assertTrue(doc.contains("custom loot and the legacy `1/5` `cavenic_orb` / magic-book drop branch remain out of scope"));
        assertTrue(doc.contains("legacy fall/fire damage behavior remains out of scope"));
        assertTrue(doc.contains("custom potion logic, friendship targeting and same-type projectile immunity remain out of scope"));
        assertTrue(doc.contains("the legacy `getMaxSpawnedInChunk()` Cavenia-specific behavior remains out of scope"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void cavenicWitchNaturalSpawnDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_NATURAL_SPAWN_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("SpawnPlacementTypes.ON_GROUND"));
        assertTrue(doc.contains("Heightmap.Types.MOTION_BLOCKING_NO_LEAVES"));
        assertTrue(doc.contains("Monster.checkMonsterSpawnRules"));
        assertTrue(doc.contains("data/cavernreborn/neoforge/biome_modifier/cavenic_witch_spawns.json"));
        assertTrue(doc.contains("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_witch.json"));
        assertTrue(doc.contains("weight: `15`"));
        assertTrue(doc.contains("group size: `1..1`"));
        assertTrue(doc.contains("Natural spawning is deliberately limited to `CAVERN`"));
        assertTrue(doc.contains("custom loot, the legacy `1/5` `cavenic_orb` drop and the magic-book drop branch remain out of scope"));
        assertTrue(doc.contains("custom potion logic, custom ranged attack behavior, friendship targeting and same-type projectile immunity remain out of scope"));
        assertTrue(doc.contains("the legacy `getMaxSpawnedInChunk()` Cavenia-specific behavior remains out of scope"));
    }

    @Test
    void runtimeSmokeMentionsCavenicWitchBaselineAndNaturalSpawnCoverageAndManualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic witch runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic witch attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch vanilla loot-table baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic witch spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch natural spawn placement registration"));
        assertTrue(runtimeSmoke.contains("cavenic witch CAVERN-only spawn predicate smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch biome modifier registry smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch biome tag resolution"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Witch renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Witch combat, potion-throw and pathfinding feel"));
        assertTrue(runtimeSmoke.contains("actual long-run Cavenic Witch population balance inside CAVERN"));
        assertTrue(!runtimeSmoke.contains("Cavenic Witch natural spawning, because that bounded follow-up is still out of scope for the current baseline slice"));
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
