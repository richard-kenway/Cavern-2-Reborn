package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CrazySpiderDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BASELINE_DOC = resolveProjectFile("docs", "crazy-spider-baseline-mvp.md");
    private static final Path DAMAGE_DOC = resolveProjectFile("docs", "crazy-spider-damage-behavior-mvp.md");
    private static final Path LOOT_DOC = resolveProjectFile("docs", "crazy-spider-loot-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazySpiderBaselineLootAndDamageDocPathsAndDeferredCombatBoundaries() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Spider Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-spider-baseline-mvp.md"));
        assertTrue(readme.contains("docs/crazy-spider-loot-mvp.md"));
        assertTrue(readme.contains("docs/crazy-spider-damage-behavior-mvp.md"));
        assertTrue(readme.contains("fourth crazy-variant foundation follow-up"));
        assertTrue(readme.contains("`crazy_spider`"));
        assertTrue(readme.contains("vanilla spider loot baseline"));
        assertTrue(readme.contains("legacy `1500.0` max-health request explicitly clamped by the modern `generic.max_health` runtime ceiling of `1024.0`"));
        assertTrue(readme.contains("inherited legacy `1/8` `cavenic_orb` drop is now restored explicitly on top of that same vanilla spider loot baseline"));
        assertTrue(readme.contains("inherited legacy fall-damage reduction and fire-damage immunity behavior are now restored explicitly on top of that same vanilla spider baseline"));
        assertTrue(readme.contains("direct blindness/poison combat hooks plus inherited boss and particle branches remain intentionally deferred"));
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
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-spider-loot-mvp.md` now restores that inherited orb branch explicitly while keeping the vanilla spider loot table as the baseline drop source."));
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
        assertTrue(doc.contains("The inherited loot/orb branch is now restored explicitly in `docs/crazy-spider-loot-mvp.md` while the rest of the baseline stays narrow."));
        assertTrue(doc.contains("The inherited damage branch is now restored explicitly in `docs/crazy-spider-damage-behavior-mvp.md` while the rest of the baseline stays narrow."));
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void crazySpiderLootDocStatesInheritedOrbMappingAndTestingBoundaries() throws IOException {
        String doc = Files.readString(LOOT_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySpider`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`cavern.item.ItemCave`"));
        assertTrue(doc.contains("does not override `dropLoot(...)`"));
        assertTrue(doc.contains("inherits `EntityCavenicSpider#dropLoot"));
        assertTrue(doc.contains("`rand.nextInt(8) == 0`"));
        assertTrue(doc.contains("`ItemCave.EnumType.CAVENIC_ORB.getItemStack()`"));
        assertTrue(doc.contains("offset `0.5F`"));
        assertTrue(doc.contains("vanilla spider loot table as the baseline drop source"));
        assertTrue(doc.contains("CrazySpiderLootEvents"));
        assertTrue(doc.contains("CrazySpiderLootPolicy"));
        assertTrue(doc.contains("`CrazySpiderLootPolicy.ORB_DROP_ROLL_BOUND = 8`"));
        assertTrue(doc.contains("The winning roll is `0`, so the orb chance remains exactly `1/8`."));
        assertTrue(doc.contains("Looting does not affect the new orb drop."));
        assertTrue(doc.contains("A player kill is not required"));
        assertTrue(doc.contains("No combat state, blindness state or poison state affects the inherited orb branch."));
        assertTrue(doc.contains("Reborn `CrazySpider` still extends vanilla `Spider`, not Reborn `CavenicSpider`."));
        assertTrue(doc.contains("Natural spawning remains deferred."));
        assertTrue(doc.contains("Damage behavior now remains documented separately in `docs/crazy-spider-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("Boss bar / sky-darkening remains unchanged in this loot slice."));
        assertTrue(doc.contains("Particle trail remains unchanged in this loot slice."));
        assertTrue(doc.contains("Custom combat, blindness and poison behavior remains unchanged in this loot slice."));
    }

    @Test
    void crazySpiderDamageDocStatesInheritedFallFireMappingAndPreservedBoundaries() throws IOException {
        String doc = Files.readString(DAMAGE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySpider`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("does not override `attackEntityFrom(...)`"));
        assertTrue(doc.contains("inherits `EntityCavenicSpider#attackEntityFrom"));
        assertTrue(doc.contains("`source == DamageSource.FALL`"));
        assertTrue(doc.contains("`damage *= 0.35F`"));
        assertTrue(doc.contains("`!source.isFireDamage()`"));
        assertTrue(doc.contains("`LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FALL`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FIRE`"));
        assertTrue(doc.contains("extends vanilla `Spider`, not Reborn `CavenicSpider`"));
        assertTrue(doc.contains("Generic non-fire, non-fall damage remains vanilla-like."));
        assertTrue(doc.contains("Loot/orb behavior remains unchanged."));
        assertTrue(doc.contains("Custom combat, blindness and poison behavior remains deferred."));
        assertTrue(doc.contains("Boss bar, sky-darkening and particle trail remain out of scope."));
        assertTrue(doc.contains("Natural-spawn deferral remains unchanged."));
        assertTrue(doc.contains("vanilla spider loot baseline remains unchanged"));
    }

    @Test
    void runtimeSmokeDocMentionsCrazySpiderRegistrySpawnEggLootDamageAndDeferredCombatBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("crazy spider runtime registry id"));
        assertTrue(runtimeSmoke.contains("crazy spider attribute registration"));
        assertTrue(runtimeSmoke.contains("crazy spider hostile runtime spawn"));
        assertTrue(runtimeSmoke.contains("crazy spider spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("crazy spider spawn egg entity creation"));
        assertTrue(runtimeSmoke.contains("crazy spider vanilla spider loot-table baseline"));
        assertTrue(runtimeSmoke.contains("crazy spider legacy orb-drop event wiring smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider legacy fall-damage reduction smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider legacy fire-damage immunity smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider generic-damage baseline smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider explicit no-natural-spawn baseline boundary"));
        assertTrue(runtimeSmoke.contains("crazy spider explicit no-custom-combat / blindness / poison baseline boundary"));
        assertTrue(runtimeSmoke.contains("actual long-run crazy spider orb drop-rate balance is not covered by server GameTests"));
        assertTrue(runtimeSmoke.contains("actual long-running Crazy Spider fire/lava gameplay feel remains manual"));
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
