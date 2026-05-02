package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CrazySpiderDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BASELINE_DOC = resolveProjectFile("docs", "crazy-spider-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazySpiderBaselineDocPathAndDeferredCombatBoundaries() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Spider Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-spider-baseline-mvp.md"));
        assertTrue(readme.contains("fourth crazy-variant foundation follow-up"));
        assertTrue(readme.contains("`crazy_spider`"));
        assertTrue(readme.contains("vanilla spider loot baseline"));
        assertTrue(readme.contains("legacy `1500.0` max-health request explicitly clamped by the modern `generic.max_health` runtime ceiling of `1024.0`"));
        assertTrue(readme.contains("direct blindness/poison combat hooks plus inherited loot, damage, boss and particle branches remain intentionally deferred"));
    }

    @Test
    void crazySpiderBaselineDocStatesLegacyReferencesRuntimeShapeAndDeferredCombatIdentity() throws IOException {
        String doc = Files.readString(BASELINE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySpider`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCrazySpider`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`assets/cavern/textures/entity/crazy_spider.png`"));
        assertTrue(doc.contains("`assets/cavern/lang/en_us.lang`"));
        assertTrue(doc.contains("`cavernreborn:crazy_spider`"));
        assertTrue(doc.contains("`cavernreborn:crazy_spider_spawn_egg`"));
        assertTrue(doc.contains("extends vanilla `Spider`"));
        assertTrue(doc.contains("does not extend the current Reborn `CavenicSpider` baseline"));
        assertTrue(doc.contains("closer to vanilla `Spider` than `CaveSpider`"));
        assertTrue(doc.contains("max health: `1500.0`"));
        assertTrue(doc.contains("generic.max_health"));
        assertTrue(doc.contains("`1024.0`"));
        assertTrue(doc.contains("movement speed: `0.6`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("Follow range intentionally stays on the vanilla spider baseline"));
        assertTrue(doc.contains("Attack damage intentionally stays on the vanilla spider baseline"));
        assertTrue(doc.contains("XP reward is pinned to `50`"));
        assertTrue(doc.contains("base color: `0x909090`"));
        assertTrue(doc.contains("spot color: `0x811F1F`"));
        assertTrue(doc.contains("reuses the vanilla spider renderer/model path"));
        assertTrue(doc.contains("vanilla spider loot table as its base drop source"));
        assertTrue(doc.contains("does not add a custom Crazy Spider loot table JSON"));
        assertTrue(doc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(doc.contains("weight `1` and group size `1 / 1`"));
        assertTrue(doc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(doc.contains("does not add a fake `CAVERN` spawn placement or biome modifier"));
        assertTrue(doc.contains("`getBlindnessAttackPower()`"));
        assertTrue(doc.contains("default: `5`"));
        assertTrue(doc.contains("normal: `10`"));
        assertTrue(doc.contains("hard: `20`"));
        assertTrue(doc.contains("`getPoisonAttackPower()`"));
        assertTrue(doc.contains("default: `3`"));
        assertTrue(doc.contains("normal: `5`"));
        assertTrue(doc.contains("hard: `8`"));
        assertTrue(doc.contains("`EntityCavenicSpider#attackEntityAsMob(Entity entity)`"));
        assertTrue(doc.contains("`sec * 20` tick durations"));
        assertTrue(doc.contains("does not restore"));
        assertTrue(doc.contains("blindness-on-hit"));
        assertTrue(doc.contains("poison-on-hit"));
        assertTrue(doc.contains("potion effects"));
        assertTrue(doc.contains("boss-event / sky-darkening behavior"));
        assertTrue(doc.contains("client-only `ParticleCrazyMob` trail"));
        assertTrue(doc.contains("`onStruckByLightning(...)`"));
        assertTrue(doc.contains("`canBeRidden(...)`"));
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void runtimeSmokeDocMentionsCrazySpiderRegistrySpawnEggLootAndDeferredCombatBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("crazy spider runtime registry id"));
        assertTrue(runtimeSmoke.contains("crazy spider attribute registration"));
        assertTrue(runtimeSmoke.contains("crazy spider hostile runtime spawn"));
        assertTrue(runtimeSmoke.contains("crazy spider spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("crazy spider spawn egg entity creation"));
        assertTrue(runtimeSmoke.contains("crazy spider vanilla spider loot-table baseline"));
        assertTrue(runtimeSmoke.contains("crazy spider explicit no-natural-spawn baseline boundary"));
        assertTrue(runtimeSmoke.contains("crazy spider explicit no-custom-combat / blindness / poison baseline boundary"));
        assertTrue(runtimeSmoke.contains("actual Crazy Spider renderer/model visual feel"));
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
