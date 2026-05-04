package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CrazySkeletonDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BASELINE_DOC = resolveProjectFile("docs", "crazy-skeleton-baseline-mvp.md");
    private static final Path EQUIPMENT_DOC = resolveProjectFile("docs", "crazy-skeleton-cavenic-bow-equipment-mvp.md");
    private static final Path LOOT_DOC = resolveProjectFile("docs", "crazy-skeleton-loot-mvp.md");
    private static final Path DAMAGE_DOC = resolveProjectFile("docs", "crazy-skeleton-damage-behavior-mvp.md");
    private static final Path BOSS_DOC = resolveProjectFile("docs", "crazy-skeleton-boss-bar-mvp.md");
    private static final Path PARTICLE_DOC = resolveProjectFile("docs", "crazy-skeleton-particle-trail-mvp.md");
    private static final Path AI_MVP_DOC = resolveProjectFile("docs", "crazy-skeleton-ranged-ai-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazySkeletonFollowUpsIncludingRangedAiMvp() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Skeleton Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-skeleton-baseline-mvp.md"));
        assertTrue(readme.contains("docs/crazy-skeleton-cavenic-bow-equipment-mvp.md"));
        assertTrue(readme.contains("docs/crazy-skeleton-loot-mvp.md"));
        assertTrue(readme.contains("docs/crazy-skeleton-damage-behavior-mvp.md"));
        assertTrue(readme.contains("docs/crazy-skeleton-boss-bar-mvp.md"));
        assertTrue(readme.contains("docs/crazy-skeleton-particle-trail-mvp.md"));
        assertTrue(readme.contains("docs/crazy-skeleton-ranged-ai-mvp.md"));
        assertTrue(readme.contains("legacy `EntityAIAttackCavenicBow` path is now restored explicitly through a local Crazy Skeleton-only goal swap"));
        assertTrue(readme.contains("guaranteed `Cavenic Bow` mainhand, forced `Infinity` enchantment and `1.0F` mainhand drop chance are now restored explicitly"));
        assertTrue(readme.contains("legacy crazy-roster natural spawning stays deferred"));
        assertTrue(readme.contains("Crazy Spider Baseline MVP"));
    }

    @Test
    void crazySkeletonBaselineDocStatesLegacyReferencesAndLinksToRangedAiFollowUp() throws IOException {
        String doc = Files.readString(BASELINE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSkeleton`"));
        assertTrue(doc.contains("`cavern.entity.ai.EntityAIAttackCavenicBow`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`cavernreborn:crazy_skeleton`"));
        assertTrue(doc.contains("extends vanilla `Skeleton`"));
        assertTrue(doc.contains("does not extend the current Reborn `CavenicSkeleton` baseline"));
        assertTrue(doc.contains("max health: `2000.0`"));
        assertTrue(doc.contains("`1024.0`"));
        assertTrue(doc.contains("follow range: `22.0`"));
        assertTrue(doc.contains("legacy `1.1x` visual scale"));
        assertTrue(doc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(doc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-skeleton-ranged-ai-mvp.md` now restores the local Crazy Skeleton ranged/melee combat-goal branch while keeping `CrazySkeleton` on the vanilla `Skeleton` base."));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md` now restores the guaranteed legacy `Cavenic Bow` + `Infinity` mainhand identity"));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-skeleton-damage-behavior-mvp.md` now restores the inherited legacy fall-damage reduction and fire-damage immunity behavior explicitly while keeping the vanilla `Skeleton` base."));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-skeleton-boss-bar-mvp.md` now restores the legacy white progress boss-event / sky-darkening branch explicitly while keeping the vanilla `Skeleton` base."));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-skeleton-particle-trail-mvp.md` now restores the legacy client-only `ParticleCrazyMob` trail explicitly while keeping the vanilla `Skeleton` base."));
        assertTrue(doc.contains("no Cavenic Bow behavior changes"));
        assertTrue(doc.contains("Crazy Creeper"));
        assertTrue(doc.contains("Crazy Spider"));
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void crazySkeletonEquipmentDocStatesEquipmentMappingAndRangedAiSeparation() throws IOException {
        String doc = Files.readString(EQUIPMENT_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.ai.EntityAIAttackCavenicBow`"));
        assertTrue(doc.contains("`cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("`setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F)`"));
        assertTrue(doc.contains("`setEquipmentBasedOnDifficulty(...)`"));
        assertTrue(doc.contains("`CaveItems.CAVENIC_BOW`"));
        assertTrue(doc.contains("`Enchantments.INFINITY`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_bow`"));
        assertTrue(doc.contains("`populateDefaultEquipmentSlots(...)`"));
        assertTrue(doc.contains("`CrazySkeleton` still extends vanilla `Skeleton`"));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-skeleton-ranged-ai-mvp.md` now restores the local Crazy Skeleton combat-goal branch."));
        assertTrue(doc.contains("`Cavenic Bow` behavior remains unchanged"));
        assertTrue(doc.contains("mainhand drop chance `1.0F`"));
        assertTrue(doc.contains("Infinity enchantment"));
        assertTrue(doc.contains("no natural-spawn changes"));
    }

    @Test
    void crazySkeletonLootDocStatesInheritedOrbMappingAndRangedAiBoundarySeparation() throws IOException {
        String doc = Files.readString(LOOT_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSkeleton`"));
        assertTrue(doc.contains("`cavern.item.ItemCave`"));
        assertTrue(doc.contains("does not override `dropLoot(...)`"));
        assertTrue(doc.contains("inherits `EntityCavenicSkeleton#dropLoot"));
        assertTrue(doc.contains("`rand.nextInt(5) == 0`"));
        assertTrue(doc.contains("`ItemCave.EnumType.CAVENIC_ORB.getItemStack()`"));
        assertTrue(doc.contains("vanilla skeleton loot table as the baseline drop source"));
        assertTrue(doc.contains("CrazySkeletonLootEvents"));
        assertTrue(doc.contains("CrazySkeletonLootPolicy"));
        assertTrue(doc.contains("The winning roll is `0`, so the orb chance remains exactly `1/5`."));
        assertTrue(doc.contains("Looting does not affect the new orb drop."));
        assertTrue(doc.contains("A player kill is not required"));
        assertTrue(doc.contains("The restored local Crazy Skeleton ranged/melee goal branch is documented separately in `docs/crazy-skeleton-ranged-ai-mvp.md`."));
        assertTrue(doc.contains("Reborn `CrazySkeleton` intentionally extends vanilla `Skeleton`, not Reborn `CavenicSkeleton`."));
    }

    @Test
    void crazySkeletonDamageDocStatesInheritedFallFireMappingAndRangedAiSeparation() throws IOException {
        String doc = Files.readString(DAMAGE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSkeleton`"));
        assertTrue(doc.contains("does not override `attackEntityFrom(...)`"));
        assertTrue(doc.contains("inherits `EntityCavenicSkeleton#attackEntityFrom"));
        assertTrue(doc.contains("`damage *= 0.35F`"));
        assertTrue(doc.contains("`!source.isFireDamage()`"));
        assertTrue(doc.contains("`hurt(DamageSource source, float damage)`"));
        assertTrue(doc.contains("`LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`"));
        assertTrue(doc.contains("Generic non-fire, non-fall damage remains vanilla-like."));
        assertTrue(doc.contains("Equipment remains unchanged."));
        assertTrue(doc.contains("Loot/orb behavior remains unchanged."));
        assertTrue(doc.contains("The restored local Crazy Skeleton ranged/melee goal branch is documented separately in `docs/crazy-skeleton-ranged-ai-mvp.md`."));
        assertTrue(doc.contains("Reborn `CrazySkeleton` intentionally extends vanilla `Skeleton`, not Reborn `CavenicSkeleton`."));
        assertTrue(doc.contains("continued natural-spawn deferral"));
    }

    @Test
    void crazySkeletonBossDocStatesLegacyBossMappingAndRangedAiSeparation() throws IOException {
        String doc = Files.readString(BOSS_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`BossInfoServer`"));
        assertTrue(doc.contains("`BossInfo.Color.WHITE`"));
        assertTrue(doc.contains("`BossInfo.Overlay.PROGRESS`"));
        assertTrue(doc.contains("`isNonBoss()`"));
        assertTrue(doc.contains("`updateAITasks()`"));
        assertTrue(doc.contains("`bossInfo.setPercent(getHealth() / getMaxHealth())`"));
        assertTrue(doc.contains("`bossInfo.setDarkenSky(!canSee || distance < 30.0D)`"));
        assertTrue(doc.contains("`BossEvent.BossBarColor.WHITE`"));
        assertTrue(doc.contains("`BossEvent.BossBarOverlay.PROGRESS`"));
        assertTrue(doc.contains("`customServerAiStep()`"));
        assertTrue(doc.contains("`startSeenByPlayer(ServerPlayer player)`"));
        assertTrue(doc.contains("`stopSeenByPlayer(ServerPlayer player)`"));
        assertTrue(doc.contains("particles now remain documented separately in `docs/crazy-skeleton-particle-trail-mvp.md`"));
        assertTrue(doc.contains("The restored local Crazy Skeleton ranged/melee goal branch is documented separately in `docs/crazy-skeleton-ranged-ai-mvp.md`."));
        assertTrue(doc.contains("Equipment remains unchanged."));
        assertTrue(doc.contains("Loot remains unchanged."));
        assertTrue(doc.contains("Damage remains unchanged."));
    }

    @Test
    void crazySkeletonParticleTrailDocStatesSharedCrazyMobMappingAndRangedAiSeparation() throws IOException {
        String doc = Files.readString(PARTICLE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.client.particle.ParticleCrazyMob`"));
        assertTrue(doc.contains("`ParticleCrazyMob extends ParticlePortal`"));
        assertTrue(doc.contains("`world.isRemote`"));
        assertTrue(doc.contains("spawns `3` particles per tick"));
        assertTrue(doc.contains("`ptX = posX + 0.25D * var1`"));
        assertTrue(doc.contains("`ptY = posY + 0.65D + rand.nextFloat()`"));
        assertTrue(doc.contains("`motionX = rand.nextFloat() * 1.0F * var1`"));
        assertTrue(doc.contains("`cavernreborn:crazy_mob`"));
        assertTrue(doc.contains("`CrazySkeleton#aiStep()`"));
        assertTrue(doc.contains("client-only guard"));
        assertTrue(doc.contains("boss bar remains unchanged"));
        assertTrue(doc.contains("equipment remains unchanged"));
        assertTrue(doc.contains("damage remains unchanged"));
        assertTrue(doc.contains("loot remains unchanged"));
        assertTrue(doc.contains("The restored local Crazy Skeleton ranged/melee goal branch is documented separately in `docs/crazy-skeleton-ranged-ai-mvp.md`."));
        assertTrue(doc.contains("natural spawning remains deferred"));
        assertTrue(doc.contains("keeps `CrazySkeleton extends Skeleton`"));
    }

    @Test
    void crazySkeletonRangedAiMvpDocStatesLegacyGoalMappingAndBoundedRebornImplementation() throws IOException {
        String doc = Files.readString(AI_MVP_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSkeleton`"));
        assertTrue(doc.contains("`cavern.entity.ai.EntityAIAttackCavenicBow`"));
        assertTrue(doc.contains("`cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("`EntityAIAttackCavenicBow<>(this, 0.99D, 6.0F, 1)`"));
        assertTrue(doc.contains("melee fallback speed `1.35D`"));
        assertTrue(doc.contains("`setCombatTask()`"));
        assertTrue(doc.contains("priority `4`"));
        assertTrue(doc.contains("`attackCooldown = 20`"));
        assertTrue(doc.contains("`attackCooldown = 50`"));
        assertTrue(doc.contains("`seeTime >= 15`"));
        assertTrue(doc.contains("strafingTime >= `5`"));
        assertTrue(doc.contains("`attackTime > 200`"));
        assertTrue(doc.contains("`attackEntityWithRangedAttack(target, ItemBow.getArrowVelocity(5))`"));
        assertTrue(doc.contains("`EntityCavenicSkeleton#getArrow(float)`"));
        assertTrue(doc.contains("`EntityCavenicArrow`"));
        assertTrue(doc.contains("Reborn implements a local Crazy Skeleton-only goal swap on `CrazySkeleton`."));
        assertTrue(doc.contains("`registerGoals()`"));
        assertTrue(doc.contains("`reassessWeaponGoal()`"));
        assertTrue(doc.contains("`LegacyCrazySkeletonCavenicBowAttackGoal`"));
        assertTrue(doc.contains("`LegacyCrazySkeletonMeleeAttackGoal`"));
        assertTrue(doc.contains("`BowItem.getPowerForTime(5)`"));
        assertTrue(doc.contains("keeps the current Reborn vanilla-compatible `CavenicBowItem` projectile path"));
        assertTrue(doc.contains("does not port `EntityCavenicArrow`"));
        assertTrue(doc.contains("does not change vanilla `Skeleton`"));
        assertTrue(doc.contains("does not change Reborn `CavenicSkeleton`"));
        assertTrue(doc.contains("loot/orb unchanged"));
        assertTrue(doc.contains("damage unchanged"));
        assertTrue(doc.contains("boss unchanged"));
        assertTrue(doc.contains("particle unchanged"));
        assertTrue(doc.contains("natural spawning remains Cavenia-boundary-only"));
        assertTrue(doc.contains("Actual long-running ranged combat feel remains manual."));
    }

    @Test
    void runtimeSmokeMentionsCrazySkeletonRangedAiCoverage() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("crazy skeleton runtime registry id"));
        assertTrue(runtimeSmoke.contains("crazy skeleton guaranteed cavenic bow equipment smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton Infinity enchantment smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton mainhand drop chance smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton legacy cavenic bow ranged-goal wiring smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton legacy ranged-goal constants smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton local melee fallback switch smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton vanilla and cavenic skeleton no-global-ai-change smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton boss-event wiring smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton particle type registry id smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton explicit no-natural-spawn baseline boundary"));
        assertTrue(runtimeSmoke.contains("actual long-running Crazy Skeleton ranged combat feel remains manual"));
        assertTrue(runtimeSmoke.contains("natural spawning remains deferred through Cavenia boundary"));
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
