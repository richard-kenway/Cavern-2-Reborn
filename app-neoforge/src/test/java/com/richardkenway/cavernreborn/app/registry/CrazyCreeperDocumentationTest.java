package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CrazyCreeperDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BASELINE_DOC = resolveProjectFile("docs", "crazy-creeper-baseline-mvp.md");
    private static final Path BOSS_DOC = resolveProjectFile("docs", "crazy-creeper-boss-bar-mvp.md");
    private static final Path DAMAGE_DOC = resolveProjectFile("docs", "crazy-creeper-damage-behavior-mvp.md");
    private static final Path LOOT_DOC = resolveProjectFile("docs", "crazy-creeper-loot-mvp.md");
    private static final Path PARTICLE_DOC = resolveProjectFile("docs", "crazy-creeper-particle-trail-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazyCreeperBaselineLootDamageBossAndParticleDocsAndFuseBoundary() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Creeper Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-creeper-baseline-mvp.md"));
        assertTrue(readme.contains("docs/crazy-creeper-loot-mvp.md"));
        assertTrue(readme.contains("docs/crazy-creeper-damage-behavior-mvp.md"));
        assertTrue(readme.contains("docs/crazy-creeper-boss-bar-mvp.md"));
        assertTrue(readme.contains("docs/crazy-creeper-particle-trail-mvp.md"));
        assertTrue(readme.contains("third crazy-variant foundation follow-up"));
        assertTrue(readme.contains("`crazy_creeper`"));
        assertTrue(readme.contains("vanilla creeper loot baseline"));
        assertTrue(readme.contains("legacy `1500.0` max-health request explicitly clamped by the modern `generic.max_health` runtime ceiling of `1024.0`"));
        assertTrue(readme.contains("inherited legacy `1/5` `cavenic_orb` drop is now restored explicitly on top of that same vanilla creeper loot baseline"));
        assertTrue(readme.contains("inherited legacy fall-damage reduction and fire-damage immunity behavior are now restored explicitly on top of that same vanilla creeper baseline"));
        assertTrue(readme.contains("legacy green progress boss bar and sky-darkening boss-event branch are now restored explicitly on top of that same vanilla creeper baseline"));
        assertTrue(readme.contains("legacy client-only portal-tinted particle trail is now restored explicitly on top of that same vanilla creeper baseline through the shared `crazy_mob` particle mapping"));
        assertTrue(readme.contains("Crazy Creeper fuse/explosion and lightning/charged/swelling follow-ups remain intentionally deferred"));
        assertTrue(readme.contains("Crazy Spider Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-spider-baseline-mvp.md"));
        assertTrue(readme.contains("Crazy Spider now has its own bounded baseline documented separately"));
    }

    @Test
    void crazyCreeperBaselineDocStatesLegacyReferencesRuntimeShapeAndDeferredExplosionIdentity() throws IOException {
        String doc = Files.readString(BASELINE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyCreeper`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCrazyCreeper`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`assets/cavern/textures/entity/crazy_creeper.png`"));
        assertTrue(doc.contains("`assets/cavern/lang/en_us.lang`"));
        assertTrue(doc.contains("`cavernreborn:crazy_creeper`"));
        assertTrue(doc.contains("`cavernreborn:crazy_creeper_spawn_egg`"));
        assertTrue(doc.contains("extends vanilla `Creeper`"));
        assertTrue(doc.contains("does not extend the current Reborn `CavenicCreeper` baseline"));
        assertTrue(doc.contains("max health: `1500.0`"));
        assertTrue(doc.contains("generic.max_health"));
        assertTrue(doc.contains("`1024.0`"));
        assertTrue(doc.contains("movement speed: `0.23`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("Follow range intentionally stays on the vanilla creeper baseline"));
        assertTrue(doc.contains("Attack damage intentionally stays on the vanilla creeper baseline"));
        assertTrue(doc.contains("XP reward is pinned to `50`"));
        assertTrue(doc.contains("base color: `0x909090`"));
        assertTrue(doc.contains("spot color: `0x2E8B57`"));
        assertTrue(doc.contains("reuses the vanilla creeper renderer/model path"));
        assertTrue(doc.contains("vanilla creeper loot table as its base drop source"));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-creeper-loot-mvp.md` now restores that inherited orb branch explicitly while keeping the vanilla creeper loot table as the baseline drop source."));
        assertTrue(doc.contains("Reborn does not add a custom Crazy Creeper loot table JSON."));
        assertTrue(doc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(doc.contains("weight `1` and group size `1 / 1`"));
        assertTrue(doc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(doc.contains("does not add a fake `CAVERN` spawn placement or biome modifier"));
        assertTrue(doc.contains("`fuseTime = 150`"));
        assertTrue(doc.contains("`explosionRadius = 30`"));
        assertTrue(doc.contains("`BossInfo.Color.GREEN`"));
        assertTrue(doc.contains("`ParticleCrazyMob`"));
        assertTrue(doc.contains("`canBeRidden(Entity entity)`"));
        assertTrue(doc.contains("does not restore"));
        assertTrue(doc.contains("custom fuse/explosion behavior"));
        assertTrue(doc.contains("lightning/charged/swelling"));
        assertTrue(doc.contains("damage behavior boundary"));
        assertTrue(doc.contains("The inherited loot/orb branch is now restored explicitly in `docs/crazy-creeper-loot-mvp.md` while the rest of the baseline stays narrow."));
        assertTrue(doc.contains("The inherited damage branch is now restored explicitly in `docs/crazy-creeper-damage-behavior-mvp.md` while the rest of the baseline stays narrow."));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-creeper-boss-bar-mvp.md` now restores the legacy green progress boss-event / sky-darkening branch explicitly while keeping the vanilla `Creeper` base."));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-creeper-particle-trail-mvp.md` now restores the legacy client-only `ParticleCrazyMob` trail explicitly while reusing the shared `cavernreborn:crazy_mob` particle mapping."));
        assertTrue(doc.contains("`Crazy Spider` now has its own bounded baseline documented in `docs/crazy-spider-baseline-mvp.md`"));
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void crazyCreeperLootDocStatesInheritedOrbMappingAndTestingBoundaries() throws IOException {
        String doc = Files.readString(LOOT_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyCreeper`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.item.ItemCave`"));
        assertTrue(doc.contains("does not override `dropLoot(...)`"));
        assertTrue(doc.contains("inherits `EntityCavenicCreeper#dropLoot"));
        assertTrue(doc.contains("`rand.nextInt(5) == 0`"));
        assertTrue(doc.contains("`ItemCave.EnumType.CAVENIC_ORB.getItemStack()`"));
        assertTrue(doc.contains("offset `0.5F`"));
        assertTrue(doc.contains("vanilla creeper loot table as the baseline drop source"));
        assertTrue(doc.contains("CrazyCreeperLootEvents"));
        assertTrue(doc.contains("CrazyCreeperLootPolicy"));
        assertTrue(doc.contains("`CrazyCreeperLootPolicy.ORB_DROP_ROLL_BOUND = 5`"));
        assertTrue(doc.contains("The winning roll is `0`, so the orb chance remains exactly `1/5`."));
        assertTrue(doc.contains("Looting does not affect the new orb drop."));
        assertTrue(doc.contains("A player kill is not required"));
        assertTrue(doc.contains("No fuse state, charged state or lightning state affects the inherited orb branch."));
        assertTrue(doc.contains("Reborn `CrazyCreeper` still extends vanilla `Creeper`, not Reborn `CavenicCreeper`."));
        assertTrue(doc.contains("Natural spawning remains deferred."));
        assertTrue(doc.contains("Damage behavior remains unchanged in this loot slice."));
        assertTrue(doc.contains("Boss bar / sky-darkening now remains documented separately in `docs/crazy-creeper-boss-bar-mvp.md`."));
        assertTrue(doc.contains("Particle trail now remains documented separately in `docs/crazy-creeper-particle-trail-mvp.md`."));
        assertTrue(doc.contains("Custom fuse/explosion behavior remains unchanged in this loot slice."));
        assertTrue(doc.contains("Lightning / charged / swelling behavior remains unchanged in this loot slice."));
    }

    @Test
    void crazyCreeperDamageDocStatesInheritedFallFireMappingAndPreservedBoundaries() throws IOException {
        String doc = Files.readString(DAMAGE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyCreeper`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("does not override `attackEntityFrom(...)`"));
        assertTrue(doc.contains("inherits `EntityCavenicCreeper#attackEntityFrom"));
        assertTrue(doc.contains("`source == DamageSource.FALL`"));
        assertTrue(doc.contains("`damage *= 0.35F`"));
        assertTrue(doc.contains("`!source.isFireDamage()`"));
        assertTrue(doc.contains("`LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FALL`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FIRE`"));
        assertTrue(doc.contains("extends vanilla `Creeper`, not Reborn `CavenicCreeper`"));
        assertTrue(doc.contains("Generic non-fire, non-fall damage remains vanilla-like."));
        assertTrue(doc.contains("Loot/orb behavior remains unchanged."));
        assertTrue(doc.contains("Custom fuse/explosion behavior remains deferred."));
        assertTrue(doc.contains("Lightning/charged/swelling behavior remains deferred."));
        assertTrue(doc.contains("Boss bar / sky-darkening now remains documented separately in `docs/crazy-creeper-boss-bar-mvp.md`."));
        assertTrue(doc.contains("Particle trail now remains documented separately in `docs/crazy-creeper-particle-trail-mvp.md`."));
        assertTrue(doc.contains("Natural-spawn deferral remains unchanged."));
        assertTrue(doc.contains("vanilla creeper loot baseline remains unchanged"));
    }

    @Test
    void crazyCreeperBossDocStatesLegacyBossEventMappingBoundariesAndTests() throws IOException {
        String doc = Files.readString(BOSS_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyCreeper`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("`ParticleCrazyMob`"));
        assertTrue(doc.contains("does not inherit the boss event from `EntityCavenicCreeper`"));
        assertTrue(doc.contains("`BossInfo.Color.GREEN`"));
        assertTrue(doc.contains("`BossInfo.Overlay.PROGRESS`"));
        assertTrue(doc.contains("`bossInfo.setPercent(getHealth() / getMaxHealth())`"));
        assertTrue(doc.contains("`bossInfo.setDarkenSky(!canSee || distance < 30.0D)`"));
        assertTrue(doc.contains("`bossInfo.setVisible(canSee)`"));
        assertTrue(doc.contains("`canEntityBeSeen(player)`"));
        assertTrue(doc.contains("`20.0D`"));
        assertTrue(doc.contains("`30.0D`"));
        assertTrue(doc.contains("`ServerBossEvent`"));
        assertTrue(doc.contains("`BossEvent.BossBarColor.GREEN`"));
        assertTrue(doc.contains("`BossEvent.BossBarOverlay.PROGRESS`"));
        assertTrue(doc.contains("`customServerAiStep()`"));
        assertTrue(doc.contains("`startSeenByPlayer(ServerPlayer player)`"));
        assertTrue(doc.contains("`stopSeenByPlayer(ServerPlayer player)`"));
        assertTrue(doc.contains("`setCustomName(@Nullable Component name)`"));
        assertTrue(doc.contains("`readAdditionalSaveData(CompoundTag compound)`"));
        assertTrue(doc.contains("`getLegacyCrazyBossEventForTests()`"));
        assertTrue(doc.contains("`shouldShowLegacyBossBarTo(ServerPlayer player)`"));
        assertTrue(doc.contains("`shouldDarkenLegacyBossSkyFor(ServerPlayer player)`"));
        assertTrue(doc.contains("`updateLegacyBossEvent()`"));
        assertTrue(doc.contains("single global boss event"));
        assertTrue(doc.contains("Particles now remain documented separately in `docs/crazy-creeper-particle-trail-mvp.md`."));
        assertTrue(doc.contains("Loot remains unchanged."));
        assertTrue(doc.contains("Damage remains unchanged."));
        assertTrue(doc.contains("Natural spawning remains deferred."));
        assertTrue(doc.contains("Fuse/explosion behavior remains deferred."));
        assertTrue(doc.contains("Lightning/charged/swelling behavior remains deferred."));
    }

    @Test
    void crazyCreeperParticleDocStatesLegacySharedTrailMappingBoundariesAndTests() throws IOException {
        String doc = Files.readString(PARTICLE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyCreeper`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.client.particle.ParticleCrazyMob`"));
        assertTrue(doc.contains("matches Crazy Zombie and Crazy Skeleton exactly"));
        assertTrue(doc.contains("spawns `3` particles per tick"));
        assertTrue(doc.contains("`ptX = posX + 0.25D * var1`"));
        assertTrue(doc.contains("`ptY = posY + 0.65D + rand.nextFloat()`"));
        assertTrue(doc.contains("`ptZ = posZ + 0.25D * var2`"));
        assertTrue(doc.contains("`motionY = (rand.nextFloat() - 0.25D) * 0.125D`"));
        assertTrue(doc.contains("`ParticleCrazyMob extends ParticlePortal`"));
        assertTrue(doc.contains("shared particle id: `cavernreborn:crazy_mob`"));
        assertTrue(doc.contains("`CrazyMobParticle`"));
        assertTrue(doc.contains("`RegisterParticleProvidersEvent`"));
        assertTrue(doc.contains("`assets/cavernreborn/particles/crazy_mob.json`"));
        assertTrue(doc.contains("`CrazyCreeper#aiStep()`"));
        assertTrue(doc.contains("`if (this.level().isClientSide())`"));
        assertTrue(doc.contains("No second Crazy Creeper-specific particle id"));
        assertTrue(doc.contains("No packets are involved."));
        assertTrue(doc.contains("Boss bar remains unchanged."));
        assertTrue(doc.contains("Damage remains unchanged."));
        assertTrue(doc.contains("Loot remains unchanged."));
        assertTrue(doc.contains("Natural spawning remains deferred."));
        assertTrue(doc.contains("Fuse/explosion behavior remains deferred."));
        assertTrue(doc.contains("Lightning/charged/swelling behavior remains deferred."));
    }

    @Test
    void runtimeSmokeDocMentionsCrazyCreeperRegistryAttributeSpawnEggLootDamageBossParticleAndDeferredExplosionBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("crazy creeper runtime registry id"));
        assertTrue(runtimeSmoke.contains("crazy creeper attribute registration"));
        assertTrue(runtimeSmoke.contains("crazy creeper hostile runtime spawn"));
        assertTrue(runtimeSmoke.contains("crazy creeper spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("crazy creeper spawn egg entity creation"));
        assertTrue(runtimeSmoke.contains("crazy creeper vanilla creeper loot-table baseline"));
        assertTrue(runtimeSmoke.contains("crazy creeper legacy orb-drop event wiring smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper legacy fall-damage reduction smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper legacy fire-damage immunity smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper generic-damage baseline smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper boss-event wiring smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper boss-bar color/overlay smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper boss-percent update smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper tracked-player add/remove smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper sky-darkening smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper particle type registry id smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper particle provider registration source smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper particle description resource smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper client-only particle spawn-source smoke"));
        assertTrue(runtimeSmoke.contains("crazy creeper explicit no-natural-spawn baseline boundary"));
        assertTrue(runtimeSmoke.contains("crazy creeper custom fuse/explosion branch remains follow-up"));
        assertTrue(runtimeSmoke.contains("crazy creeper custom lightning/charged/swelling behavior remains follow-up"));
        assertTrue(runtimeSmoke.contains("actual client visual crazy creeper particle feel remains manual"));
        assertTrue(runtimeSmoke.contains("actual client-visible Crazy Creeper boss-bar feel remains manual"));
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
