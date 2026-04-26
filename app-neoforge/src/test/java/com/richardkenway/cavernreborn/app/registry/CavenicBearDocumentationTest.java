package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicBearDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_BEAR_BASELINE_MVP = resolveProjectFile("docs", "cavenic-bear-baseline-mvp.md");
    private static final Path CAVENIC_BEAR_NATURAL_SPAWN_MVP = resolveProjectFile("docs", "cavenic-bear-natural-spawn-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicBearBaselineNaturalSpawnAndDocPaths() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Bear Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-bear-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Bear Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-bear-natural-spawn-mvp.md"));
        assertTrue(readme.contains("cavenic_bear"));
        assertTrue(readme.contains("sixth direct Cavenic mob foundation"));
        assertTrue(readme.contains("legacy texture on the vanilla polar bear renderer path"));
        assertTrue(readme.contains("vanilla polar bear loot baseline"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
    }

    @Test
    void cavenicBearBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_bear`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_bear_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicBear`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `PolarBear`"));
        assertTrue(doc.contains("max health: `60.0`"));
        assertTrue(doc.contains("movement speed: `0.3`"));
        assertTrue(doc.contains("attack damage: `7.0`"));
        assertTrue(doc.contains("follow range intentionally stays on the vanilla polar bear baseline"));
        assertTrue(doc.contains("XP reward is pinned to `13`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0xFFFFFF`"));
        assertTrue(doc.contains("reuses the vanilla polar bear renderer/model path"));
        assertTrue(doc.contains("reuses the vanilla polar bear loot table as its base drop source"));
        assertTrue(doc.contains("Natural spawning was intentionally out of scope for the baseline slice"));
        assertTrue(doc.contains("`docs/cavenic-bear-natural-spawn-mvp.md`"));
        assertTrue(doc.contains("Custom loot remains out of scope"));
        assertTrue(doc.contains("fall/fire damage behavior remains out of scope"));
        assertTrue(doc.contains("custom bear AI, anger behavior, taming, riding and mount behavior remain out of scope"));
        assertTrue(doc.contains("Cavenia remains out of scope"));
        assertTrue(doc.contains("The magic-book system remains intentionally untouched."));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void cavenicBearNaturalSpawnDocStatesLegacyReferencesRuleAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_NATURAL_SPAWN_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_bear`"));
        assertTrue(doc.contains("`RegisterSpawnPlacementsEvent`"));
        assertTrue(doc.contains("`SpawnPlacementTypes.ON_GROUND`"));
        assertTrue(doc.contains("`Heightmap.Types.MOTION_BLOCKING_NO_LEAVES`"));
        assertTrue(doc.contains("`Monster.checkMonsterSpawnRules`"));
        assertTrue(doc.contains("`PolarBear.checkPolarBearSpawnRules`"));
        assertTrue(doc.contains("weight: `30`"));
        assertTrue(doc.contains("group size: `1..1`"));
        assertTrue(doc.contains("`getMaxSpawnedInChunk() = 1`"));
        assertTrue(doc.contains("`getMaxSpawnClusterSize() = 1`"));
        assertTrue(doc.contains("`data/cavernreborn/neoforge/biome_modifier/cavenic_bear_spawns.json`"));
        assertTrue(doc.contains("`data/cavernreborn/tags/worldgen/biome/spawns_cavenic_bear.json`"));
        assertTrue(doc.contains("Natural spawning is deliberately limited to `CAVERN`."));
        assertTrue(doc.contains("custom loot, orb drop, fall/fire damage behavior, custom bear AI, anger behavior, taming, riding and mount behavior remain out of scope"));
        assertTrue(doc.contains("Cavenia remains out of scope"));
        assertTrue(doc.contains("The magic-book system remains intentionally untouched."));
    }

    @Test
    void runtimeSmokeDocMentionsCavenicBearCoverage() throws IOException {
        String doc = Files.readString(RUNTIME_SMOKE);

        assertTrue(doc.contains("cavenic bear runtime registry id"));
        assertTrue(doc.contains("cavenic bear attribute registration smoke"));
        assertTrue(doc.contains("cavenic bear runtime entity spawn smoke"));
        assertTrue(doc.contains("cavenic bear vanilla polar bear loot-table baseline smoke"));
        assertTrue(doc.contains("cavenic bear spawn egg resolution"));
        assertTrue(doc.contains("cavenic bear spawn egg entity-creation smoke"));
        assertTrue(doc.contains("cavenic bear natural spawn placement registration"));
        assertTrue(doc.contains("cavenic bear CAVERN-only spawn predicate smoke"));
        assertTrue(doc.contains("cavenic bear biome modifier registry smoke"));
        assertTrue(doc.contains("cavenic bear biome tag resolution"));
        assertTrue(doc.contains("actual Cavenic Bear renderer/model visual feel"));
        assertTrue(doc.contains("actual long-run Cavenic Bear population balance inside CAVERN"));
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
