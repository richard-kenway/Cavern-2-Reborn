package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicSpiderDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_SPIDER_BASELINE_MVP = resolveProjectFile("docs", "cavenic-spider-baseline-mvp.md");
    private static final Path CAVENIC_SPIDER_NATURAL_SPAWN_MVP = resolveProjectFile("docs", "cavenic-spider-natural-spawn-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicSpiderBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Spider Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-spider-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Spider Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-spider-natural-spawn-mvp.md"));
        assertTrue(readme.contains("cavenic_spider"));
        assertTrue(readme.contains("fourth direct Cavenic mob foundation"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
        assertTrue(readme.contains("vanilla spider loot baseline"));
    }

    @Test
    void cavenicSpiderBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_SPIDER_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_spider`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_spider_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicSpider`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `Spider`"));
        assertTrue(doc.contains("max health: `20.0`"));
        assertTrue(doc.contains("movement speed: `0.6`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("follow range intentionally stays on the vanilla spider baseline"));
        assertTrue(doc.contains("attack damage intentionally stays on the vanilla spider baseline"));
        assertTrue(doc.contains("XP reward is pinned to `12`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0x811F1F`"));
        assertTrue(doc.contains("reuses the vanilla spider renderer/model path"));
        assertTrue(doc.contains("reuses the vanilla spider loot table as its base drop source"));
        assertTrue(doc.contains("The bounded natural-spawn follow-up is now documented separately in `docs/cavenic-spider-natural-spawn-mvp.md`."));
        assertTrue(doc.contains("custom loot remains out of scope"));
        assertTrue(doc.contains("`1/8` `cavenic_orb` drop"));
        assertTrue(doc.contains("fall/fire damage behavior"));
        assertTrue(doc.contains("blindness-on-hit behavior"));
        assertTrue(doc.contains("poison behavior"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void cavenicSpiderNaturalSpawnDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_SPIDER_NATURAL_SPAWN_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("SpawnPlacementTypes.ON_GROUND"));
        assertTrue(doc.contains("Heightmap.Types.MOTION_BLOCKING_NO_LEAVES"));
        assertTrue(doc.contains("Monster.checkMonsterSpawnRules"));
        assertTrue(doc.contains("data/cavernreborn/neoforge/biome_modifier/cavenic_spider_spawns.json"));
        assertTrue(doc.contains("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_spider.json"));
        assertTrue(doc.contains("weight: `30`"));
        assertTrue(doc.contains("group size: `1..1`"));
        assertTrue(doc.contains("Natural spawning is deliberately limited to `CAVERN`"));
        assertTrue(doc.contains("the legacy `1/8` `cavenic_orb` drop follow-up remains out of scope"));
        assertTrue(doc.contains("custom loot beyond that orb-drop follow-up remains out of scope"));
        assertTrue(doc.contains("the legacy fall/fire damage-behavior follow-up remains out of scope"));
        assertTrue(doc.contains("blindness, poison and web-based special spider behavior remain out of scope"));
    }

    @Test
    void runtimeSmokeMentionsCavenicSpiderNaturalSpawnCoverageAndManualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic spider runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic spider attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider vanilla loot-table baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic spider spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider natural spawn placement registration"));
        assertTrue(runtimeSmoke.contains("cavenic spider CAVERN-only spawn predicate smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider biome modifier registry smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider biome tag resolution"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Spider renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("actual long-run Cavenic Spider population balance inside CAVERN"));
        assertFalse(runtimeSmoke.contains("Cavenic Spider natural spawning in this baseline slice"));
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
