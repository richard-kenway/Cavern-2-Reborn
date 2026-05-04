package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CrazySpiderDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BASELINE_DOC = resolveProjectFile("docs", "crazy-spider-baseline-mvp.md");
    private static final Path LOOT_DOC = resolveProjectFile("docs", "crazy-spider-loot-mvp.md");
    private static final Path DAMAGE_DOC = resolveProjectFile("docs", "crazy-spider-damage-behavior-mvp.md");
    private static final Path BOSS_DOC = resolveProjectFile("docs", "crazy-spider-boss-bar-mvp.md");
    private static final Path PARTICLE_DOC = resolveProjectFile("docs", "crazy-spider-particle-trail-mvp.md");
    private static final Path COMBAT_DOC = resolveProjectFile("docs", "crazy-spider-combat-effects-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazySpiderBaselineLootDamageBossParticleAndCombatDocPaths() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Spider Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-spider-baseline-mvp.md"));
        assertTrue(readme.contains("docs/crazy-spider-loot-mvp.md"));
        assertTrue(readme.contains("docs/crazy-spider-damage-behavior-mvp.md"));
        assertTrue(readme.contains("docs/crazy-spider-boss-bar-mvp.md"));
        assertTrue(readme.contains("docs/crazy-spider-particle-trail-mvp.md"));
        assertTrue(readme.contains("docs/crazy-spider-combat-effects-mvp.md"));
        assertTrue(readme.contains("fourth crazy-variant foundation follow-up"));
        assertTrue(readme.contains("`crazy_spider`"));
        assertTrue(readme.contains("vanilla spider loot baseline"));
        assertTrue(readme.contains("inherited legacy `1/8` `cavenic_orb` drop is now restored explicitly"));
        assertTrue(readme.contains("inherited legacy fall-damage reduction and fire-damage immunity behavior are now restored explicitly"));
        assertTrue(readme.contains("legacy red progress boss bar and sky-darkening boss-event branch are now restored explicitly"));
        assertTrue(readme.contains("legacy client-only portal-tinted particle trail is now restored explicitly"));
        assertTrue(readme.contains("legacy successful-hit blindness/poison combat branch is now restored explicitly"));
    }

    @Test
    void crazySpiderBaselineDocStatesLegacyReferencesRuntimeShapeAndCombatFollowUp() throws IOException {
        String doc = Files.readString(BASELINE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySpider`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCrazySpider`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`cavernreborn:crazy_spider`"));
        assertTrue(doc.contains("`cavernreborn:crazy_spider_spawn_egg`"));
        assertTrue(doc.contains("extends vanilla `Spider`"));
        assertTrue(doc.contains("does not extend the current Reborn `CavenicSpider` baseline"));
        assertTrue(doc.contains("max health: `1500.0`"));
        assertTrue(doc.contains("`1024.0`"));
        assertTrue(doc.contains("movement speed: `0.6`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("Follow range intentionally stays on the vanilla spider baseline"));
        assertTrue(doc.contains("Attack damage intentionally stays on the vanilla spider baseline"));
        assertTrue(doc.contains("XP reward is pinned to `50`"));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-spider-loot-mvp.md` now restores that inherited orb branch explicitly"));
        assertTrue(doc.contains("The inherited damage branch is now restored explicitly in `docs/crazy-spider-damage-behavior-mvp.md`"));
        assertTrue(doc.contains("The inherited boss-event / sky-darkening branch is now restored explicitly in `docs/crazy-spider-boss-bar-mvp.md`"));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-spider-particle-trail-mvp.md` now restores the legacy client-only `ParticleCrazyMob` trail explicitly"));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-spider-combat-effects-mvp.md` now restores the legacy successful-hit blindness/poison branch explicitly while keeping the rest of the baseline narrow."));
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
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void crazySpiderLootDocStatesInheritedOrbMappingAndCombatBoundary() throws IOException {
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
        assertTrue(doc.contains("`CrazySpiderLootPolicy.ORB_DROP_ROLL_BOUND = 8`"));
        assertTrue(doc.contains("The winning roll is `0`, so the orb chance remains exactly `1/8`."));
        assertTrue(doc.contains("Looting does not affect the new orb drop."));
        assertTrue(doc.contains("A player kill is not required"));
        assertTrue(doc.contains("No combat state, blindness state or poison state affects the inherited orb branch."));
        assertTrue(doc.contains("Custom combat, blindness and poison behavior remains unchanged in this loot slice."));
        assertTrue(doc.contains("Combat/effect behavior now remains documented separately in `docs/crazy-spider-combat-effects-mvp.md`."));
    }

    @Test
    void crazySpiderDamageDocStatesInheritedFallFireMappingAndPreservedCombatSeparation() throws IOException {
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
        assertTrue(doc.contains("Generic non-fire, non-fall damage remains vanilla-like."));
        assertTrue(doc.contains("Loot/orb behavior remains unchanged."));
        assertTrue(doc.contains("Custom combat, blindness and poison behavior now remains documented separately in `docs/crazy-spider-combat-effects-mvp.md`."));
        assertTrue(doc.contains("Boss bar and sky-darkening now remain documented separately in `docs/crazy-spider-boss-bar-mvp.md`."));
        assertTrue(doc.contains("Particle trail now remains documented separately in `docs/crazy-spider-particle-trail-mvp.md`."));
        assertTrue(doc.contains("Natural-spawn deferral remains unchanged."));
    }

    @Test
    void crazySpiderBossDocStatesRedBossMappingAndPreservedCombatSeparation() throws IOException {
        String doc = Files.readString(BOSS_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySpider`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("declares its own `BossInfoServer` directly"));
        assertTrue(doc.contains("`BossInfo.Color.RED`"));
        assertTrue(doc.contains("`BossInfo.Overlay.PROGRESS`"));
        assertTrue(doc.contains("`EntityCrazySpider#isNonBoss()` returns `false`"));
        assertTrue(doc.contains("`bossInfo.setPercent(getHealth() / getMaxHealth())`"));
        assertTrue(doc.contains("`bossInfo.setDarkenSky(!canSee || distance < 30.0D)`"));
        assertTrue(doc.contains("Reborn keeps `CrazySpider extends Spider`."));
        assertTrue(doc.contains("`BossEvent.BossBarColor.RED`"));
        assertTrue(doc.contains("`BossEvent.BossBarOverlay.PROGRESS`"));
        assertTrue(doc.contains("`customServerAiStep()`"));
        assertTrue(doc.contains("Particles now remain documented separately in `docs/crazy-spider-particle-trail-mvp.md`."));
        assertTrue(doc.contains("Loot remains unchanged."));
        assertTrue(doc.contains("Damage remains unchanged."));
        assertTrue(doc.contains("Custom combat, blindness and poison behavior now remains documented separately in `docs/crazy-spider-combat-effects-mvp.md`."));
        assertTrue(doc.contains("single global boss event"));
    }

    @Test
    void crazySpiderParticleDocStatesLegacySharedTrailMappingAndPreservedCombatSeparation() throws IOException {
        String doc = Files.readString(PARTICLE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySpider`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`cavern.client.particle.ParticleCrazyMob`"));
        assertTrue(doc.contains("matches Crazy Zombie, Crazy Skeleton and Crazy Creeper exactly"));
        assertTrue(doc.contains("spawns `3` particles per tick"));
        assertTrue(doc.contains("`ptX = posX + 0.25D * var1`"));
        assertTrue(doc.contains("`ptY = posY + 0.65D + rand.nextFloat()`"));
        assertTrue(doc.contains("`ptZ = posZ + 0.25D * var2`"));
        assertTrue(doc.contains("`motionY = (rand.nextFloat() - 0.25D) * 0.125D`"));
        assertTrue(doc.contains("shared particle id: `cavernreborn:crazy_mob`"));
        assertTrue(doc.contains("`CrazySpider#aiStep()`"));
        assertTrue(doc.contains("No packets are involved."));
        assertTrue(doc.contains("Boss bar remains unchanged."));
        assertTrue(doc.contains("Damage remains unchanged."));
        assertTrue(doc.contains("Loot remains unchanged."));
        assertTrue(doc.contains("Natural spawning remains deferred."));
        assertTrue(doc.contains("Custom combat, blindness and poison behavior now remains documented separately in `docs/crazy-spider-combat-effects-mvp.md`."));
    }

    @Test
    void crazySpiderCombatDocStatesInheritedEffectMappingAndPreservedBoundaries() throws IOException {
        String doc = Files.readString(COMBAT_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySpider`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSpider`"));
        assertTrue(doc.contains("`attackEntityAsMob(Entity entity)`"));
        assertTrue(doc.contains("`getBlindnessAttackPower()`"));
        assertTrue(doc.contains("`getPoisonAttackPower()`"));
        assertTrue(doc.contains("`doHurtTarget(Entity target)`"));
        assertTrue(doc.contains("`tryApplyLegacyCombatEffects(LivingEntity target, boolean attackSucceeded)`"));
        assertTrue(doc.contains("`tryApplyLegacyBlindnessOnHit(LivingEntity target)`"));
        assertTrue(doc.contains("`tryApplyLegacyPoisonOnHit(LivingEntity target)`"));
        assertTrue(doc.contains("`getLegacyBlindnessDurationTicks(Difficulty difficulty)`"));
        assertTrue(doc.contains("`getLegacyPoisonDurationTicks(Difficulty difficulty)`"));
        assertTrue(doc.contains("`MobEffects.BLINDNESS`"));
        assertTrue(doc.contains("`MobEffects.POISON`"));
        assertTrue(doc.contains("`LEGACY_BLINDNESS_DURATION_DEFAULT_TICKS = 100`"));
        assertTrue(doc.contains("`LEGACY_BLINDNESS_DURATION_NORMAL_TICKS = 200`"));
        assertTrue(doc.contains("`LEGACY_BLINDNESS_DURATION_HARD_TICKS = 400`"));
        assertTrue(doc.contains("`LEGACY_POISON_DURATION_DEFAULT_TICKS = 60`"));
        assertTrue(doc.contains("`LEGACY_POISON_DURATION_NORMAL_TICKS = 100`"));
        assertTrue(doc.contains("`LEGACY_POISON_DURATION_HARD_TICKS = 160`"));
        assertTrue(doc.contains("`LEGACY_BLINDNESS_AMPLIFIER = 0`"));
        assertTrue(doc.contains("`LEGACY_POISON_AMPLIFIER = 0`"));
        assertTrue(doc.contains("Blindness and poison are applied only after a successful melee hit."));
        assertTrue(doc.contains("Only living targets can receive the effects."));
        assertTrue(doc.contains("Easy and peaceful difficulty fall back to `100` ticks (`5` seconds) for blindness."));
        assertTrue(doc.contains("Normal difficulty uses `200` ticks (`10` seconds) for blindness."));
        assertTrue(doc.contains("Hard difficulty uses `400` ticks (`20` seconds) for blindness."));
        assertTrue(doc.contains("Easy and peaceful difficulty fall back to `60` ticks (`3` seconds) for poison."));
        assertTrue(doc.contains("Normal difficulty uses `100` ticks (`5` seconds) for poison."));
        assertTrue(doc.contains("Hard difficulty uses `160` ticks (`8` seconds) for poison."));
        assertTrue(doc.contains("Targets that already have blindness are left unchanged."));
        assertTrue(doc.contains("Targets that already have poison are left unchanged."));
        assertTrue(doc.contains("Reborn keeps `CrazySpider extends Spider`."));
        assertTrue(doc.contains("Loot/orb behavior remains unchanged."));
        assertTrue(doc.contains("Damage-intake behavior remains unchanged."));
        assertTrue(doc.contains("Boss bar / sky-darkening remains unchanged."));
        assertTrue(doc.contains("Particle trail remains unchanged."));
        assertTrue(doc.contains("Natural-spawn deferral remains unchanged."));
    }

    @Test
    void runtimeSmokeDocMentionsCrazySpiderRegistrySpawnEggLootDamageBossParticleAndCombatCoverage() throws IOException {
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
        assertTrue(runtimeSmoke.contains("crazy spider boss-event wiring smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider boss-bar color/overlay smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider boss-percent update smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider tracked-player add/remove smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider sky-darkening smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider particle type registry id smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider particle provider registration source smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider particle description resource smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider client-only particle spawn-source smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider successful-hit blindness/poison runtime smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider exact difficulty-duration mapping smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider existing-effect skip smoke"));
        assertTrue(runtimeSmoke.contains("vanilla spider no-blindness/no-poison comparison smoke"));
        assertTrue(runtimeSmoke.contains("crazy spider explicit no-natural-spawn baseline boundary"));
        assertTrue(runtimeSmoke.contains("actual long-run crazy spider orb drop-rate balance is not covered by server GameTests"));
        assertTrue(runtimeSmoke.contains("actual long-running Crazy Spider fire/lava gameplay feel remains manual"));
        assertTrue(runtimeSmoke.contains("actual client visual crazy spider boss-bar feel remains manual"));
        assertTrue(runtimeSmoke.contains("actual Crazy Spider client particle-trail visual feel"));
        assertTrue(runtimeSmoke.contains("actual long-running Crazy Spider combat/pathfinding feel remains manual"));
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
