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
    private static final Path CAVENIC_SPIDER_ORB_DROP_MVP = resolveProjectFile("docs", "cavenic-spider-orb-drop-mvp.md");
    private static final Path CAVENIC_SPIDER_DAMAGE_BEHAVIOR_MVP = resolveProjectFile("docs", "cavenic-spider-damage-behavior-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicSpiderBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Spider Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-spider-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Spider Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-spider-natural-spawn-mvp.md"));
        assertTrue(readme.contains("Cavenic Spider Legacy Orb Drop MVP"));
        assertTrue(readme.contains("docs/cavenic-spider-orb-drop-mvp.md"));
        assertTrue(readme.contains("Cavenic Spider Legacy Damage Behavior MVP"));
        assertTrue(readme.contains("docs/cavenic-spider-damage-behavior-mvp.md"));
        assertTrue(readme.contains("cavenic_spider"));
        assertTrue(readme.contains("fourth direct Cavenic mob foundation"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
        assertTrue(readme.contains("vanilla spider loot baseline"));
        assertTrue(readme.contains("legacy `1/8` `cavenic_orb` drop"));
        assertTrue(readme.contains("legacy fall-damage reduction and fire-damage immunity"));
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
        assertTrue(doc.contains("The bounded orb-drop follow-up is now documented separately in `docs/cavenic-spider-orb-drop-mvp.md`."));
        assertTrue(doc.contains("The bounded damage-behavior follow-up is now documented separately in `docs/cavenic-spider-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("custom loot beyond the bounded orb-drop follow-up remains out of scope"));
        assertTrue(doc.contains("`1/8` `cavenic_orb` drop"));
        assertTrue(doc.contains("blindness-on-hit behavior"));
        assertTrue(doc.contains("poison behavior"));
        assertFalse(doc.contains("`1/8` `cavenic_orb` drop parity remains out of scope"));
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
        assertTrue(doc.contains("The bounded orb-drop follow-up is now documented separately in `docs/cavenic-spider-orb-drop-mvp.md`."));
        assertTrue(doc.contains("The bounded damage-behavior follow-up is now documented separately in `docs/cavenic-spider-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("custom loot beyond that orb-drop follow-up remains out of scope"));
        assertTrue(doc.contains("blindness, poison and web-based special spider behavior remain out of scope"));
    }

    @Test
    void cavenicSpiderOrbDropDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_SPIDER_ORB_DROP_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`cavern.item.ItemCave`"));
        assertTrue(doc.contains("`dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)`"));
        assertTrue(doc.contains("`rand.nextInt(8) == 0`"));
        assertTrue(doc.contains("`CavenicSpiderLootPolicy.ORB_DROP_ROLL_BOUND = 8`"));
        assertTrue(doc.contains("winning roll is `0`"));
        assertTrue(doc.contains("vanilla spider loot table as the base drop source"));
        assertTrue(doc.contains("small `LivingDropsEvent` listener"));
        assertTrue(doc.contains("Looting does not affect the orb chance"));
        assertTrue(doc.contains("The orb drop does not require a player kill"));
        assertTrue(doc.contains("No progression, dimension or economy hook changes affect the orb drop"));
        assertTrue(doc.contains("The separate legacy damage-behavior follow-up is now documented separately in `docs/cavenic-spider-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("blindness, poison, web and other special spider behavior do not change"));
        assertTrue(doc.contains("Long-run orb-drop rate balance still needs manual gameplay validation"));
    }

    @Test
    void cavenicSpiderDamageBehaviorDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_SPIDER_DAMAGE_BEHAVIOR_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`attackEntityFrom(DamageSource source, float damage)`"));
        assertTrue(doc.contains("`attackEntityAsMob(Entity entity)`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FALL`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FIRE`"));
        assertTrue(doc.contains("`LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`"));
        assertTrue(doc.contains("Fall damage is reduced to `35%`"));
        assertTrue(doc.contains("All modern fire-tagged damage sources are ignored."));
        assertTrue(doc.contains("vanilla spider AI remains unchanged."));
        assertTrue(doc.contains("attributes, natural spawning and `1/8` orb-drop behavior remain unchanged."));
        assertTrue(doc.contains("Blindness-on-hit, poison, web and other special spider behavior remain out of scope"));
    }

    @Test
    void runtimeSmokeMentionsCavenicSpiderNaturalSpawnOrbDropAndDamageCoverageAndManualBoundary() throws IOException {
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
        assertTrue(runtimeSmoke.contains("cavenic spider legacy orb-drop event wiring smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider legacy fall-damage reduction smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider legacy fire-damage immunity smoke"));
        assertTrue(runtimeSmoke.contains("cavenic spider generic-damage baseline smoke"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Spider renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("actual long-run Cavenic Spider population balance inside CAVERN"));
        assertTrue(runtimeSmoke.contains("actual long-run cavenic spider orb-drop rate balance"));
        assertTrue(runtimeSmoke.contains("actual long-running Cavenic Spider fire/lava gameplay feel"));
        assertTrue(runtimeSmoke.contains("actual blindness/poison/web-based Cavenic Spider gameplay behavior"));
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
