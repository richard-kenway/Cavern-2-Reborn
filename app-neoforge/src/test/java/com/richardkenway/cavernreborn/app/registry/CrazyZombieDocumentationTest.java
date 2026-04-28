package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CrazyZombieDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BASELINE_DOC = resolveProjectFile("docs", "crazy-zombie-baseline-mvp.md");
    private static final Path DAMAGE_BEHAVIOR_DOC = resolveProjectFile("docs", "crazy-zombie-damage-behavior-mvp.md");
    private static final Path KNOCKBACK_DOC = resolveProjectFile("docs", "crazy-zombie-knockback-on-hit-mvp.md");
    private static final Path LOOT_DOC = resolveProjectFile("docs", "crazy-zombie-loot-mvp.md");
    private static final Path NATURAL_SPAWN_DOC = resolveProjectFile("docs", "crazy-zombie-natural-spawn-absent-or-deferred.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazyZombieBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Zombie Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-zombie-baseline-mvp.md"));
        assertTrue(readme.contains("first legacy crazy-variant foundation follow-up"));
        assertTrue(readme.contains("`crazy_zombie`"));
        assertTrue(readme.contains("vanilla zombie loot baseline"));
        assertTrue(readme.contains("docs/crazy-zombie-loot-mvp.md"));
        assertTrue(readme.contains("inherited legacy `1/8` `cavenic_orb` drop appended on top of the vanilla zombie loot baseline"));
        assertTrue(readme.contains("docs/crazy-zombie-damage-behavior-mvp.md"));
        assertTrue(readme.contains("inherited legacy fall-damage reduction and fire-damage immunity behavior"));
        assertTrue(readme.contains("docs/crazy-zombie-knockback-on-hit-mvp.md"));
        assertTrue(readme.contains("legacy roll-gated melee-knockback branch"));
        assertTrue(readme.contains("docs/crazy-zombie-natural-spawn-absent-or-deferred.md"));
        assertTrue(readme.contains("Cavenia-only crazy-roster provider/config branch"));
        assertTrue(readme.contains("crazy skeleton, crazy creeper and crazy spider remain follow-up candidates"));
    }

    @Test
    void crazyZombieBaselineDocStatesLegacyReferencesSelectionRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(BASELINE_DOC);

        assertTrue(doc.contains("`EntityCrazyZombie`"));
        assertTrue(doc.contains("`EntityCrazySkeleton`"));
        assertTrue(doc.contains("`EntityCrazyCreeper`"));
        assertTrue(doc.contains("`EntityCrazySpider`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCrazyZombie`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`assets/cavern/textures/entity/crazy_zombie.png`"));
        assertTrue(doc.contains("`assets/cavern/lang/en_us.lang`"));
        assertTrue(doc.contains("`cavernreborn:crazy_zombie`"));
        assertTrue(doc.contains("`cavernreborn:crazy_zombie_spawn_egg`"));
        assertTrue(doc.contains("extends vanilla `Zombie`"));
        assertTrue(doc.contains("Legacy `EntityCrazyZombie` extends `EntityCavenicZombie`"));
        assertTrue(doc.contains("does not extend the current Reborn `CavenicZombie` baseline"));
        assertTrue(doc.contains("docs/crazy-zombie-damage-behavior-mvp.md"));
        assertTrue(doc.contains("docs/crazy-zombie-knockback-on-hit-mvp.md"));
        assertTrue(doc.contains("max health: `2000.0`"));
        assertTrue(doc.contains("generic.max_health"));
        assertTrue(doc.contains("cap"));
        assertTrue(doc.contains("`1024.0`"));
        assertTrue(doc.contains("follow range: `50.0`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("attack damage: `7.5`"));
        assertTrue(doc.contains("Movement speed intentionally stays on the vanilla zombie baseline"));
        assertTrue(doc.contains("XP reward is pinned to `50`"));
        assertTrue(doc.contains("base color: `0x909090`"));
        assertTrue(doc.contains("spot color: `0x00A0A0`"));
        assertTrue(doc.contains("reuses the vanilla zombie renderer/model path"));
        assertTrue(doc.contains("vanilla zombie loot table as its base drop source"));
        assertTrue(doc.contains("Natural spawning is intentionally out of scope"));
        assertTrue(doc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(doc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(doc.contains("weight `1` and group size `1 / 1`"));
        assertTrue(doc.contains("does not add a fake `CAVERN`-only spawn rule"));
        assertTrue(doc.contains("docs/crazy-zombie-natural-spawn-absent-or-deferred.md"));
        assertTrue(doc.contains("The inherited legacy orb-drop follow-up is now documented in `docs/crazy-zombie-loot-mvp.md`."));
        assertTrue(doc.contains("The separate inherited damage behavior follow-up is documented in `docs/crazy-zombie-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("The legacy Crazy Zombie knockback-on-hit follow-up is now restored explicitly on Reborn `CrazyZombie` and documented in `docs/crazy-zombie-knockback-on-hit-mvp.md`."));
        assertTrue(doc.contains("boss bar / sky-darkening"));
        assertTrue(doc.contains("crazy particle trail"));
        assertTrue(doc.contains("Crazy Skeleton remains a follow-up because it depends on the legacy `EntityAIAttackCavenicBow` path"));
        assertTrue(doc.contains("Crazy Creeper remains a follow-up because its direct legacy identity depends on a huge fuse/explosion branch"));
        assertTrue(doc.contains("Crazy Spider remains a follow-up because it carries stronger blindness/poison combat hooks"));
        assertTrue(doc.contains("Cavenia remains out of scope"));
        assertTrue(doc.contains("The magic-book system remains intentionally untouched."));
    }

    @Test
    void crazyZombieLootDocStatesInheritedLegacyMappingBoundariesAndTests() throws IOException {
        String doc = Files.readString(LOOT_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyZombie`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicZombie`"));
        assertTrue(doc.contains("`cavern.item.ItemCave`"));
        assertTrue(doc.contains("does not override `dropLoot(...)`"));
        assertTrue(doc.contains("inherits `EntityCavenicZombie#dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)`"));
        assertTrue(doc.contains("`rand.nextInt(8) == 0`"));
        assertTrue(doc.contains("`ItemCave.EnumType.CAVENIC_ORB.getItemStack()`"));
        assertTrue(doc.contains("`0.5F`"));
        assertTrue(doc.contains("does not use `lootingModifier`"));
        assertTrue(doc.contains("does not check `wasRecentlyHit`"));
        assertTrue(doc.contains("vanilla zombie loot table as its baseline"));
        assertTrue(doc.contains("docs/crazy-zombie-knockback-on-hit-mvp.md"));
        assertTrue(doc.contains("`CrazyZombieLootPolicy.ORB_DROP_ROLL_BOUND = 8`"));
        assertTrue(doc.contains("`CrazyZombieLootEvents`"));
        assertTrue(doc.contains("intentionally extends vanilla `Zombie`, not Reborn `CavenicZombie`"));
        assertTrue(doc.contains("copies only the confirmed inherited loot branch explicitly onto `CrazyZombie`"));
        assertTrue(doc.contains("natural spawning remains explicitly deferred"));
        assertTrue(doc.contains("boss bar / sky-darkening"));
        assertTrue(doc.contains("particle trail"));
        assertTrue(doc.contains("Crazy Skeleton / Crazy Creeper / Crazy Spider"));
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void crazyZombieDamageBehaviorDocStatesInheritedLegacyMappingBoundariesAndTests() throws IOException {
        String doc = Files.readString(DAMAGE_BEHAVIOR_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyZombie`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicZombie`"));
        assertTrue(doc.contains("does not override `attackEntityFrom(...)`"));
        assertTrue(doc.contains("inherits `EntityCavenicZombie#attackEntityFrom(DamageSource source, float damage)`"));
        assertTrue(doc.contains("`source == DamageSource.FALL`"));
        assertTrue(doc.contains("`damage *= 0.35F`"));
        assertTrue(doc.contains("`!source.isFireDamage()`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FALL`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FIRE`"));
        assertTrue(doc.contains("`LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`"));
        assertTrue(doc.contains("intentionally extends vanilla `Zombie`, not Reborn `CavenicZombie`"));
        assertTrue(doc.contains("copies only the confirmed incoming-damage behavior explicitly onto `CrazyZombie`"));
        assertTrue(doc.contains("docs/crazy-zombie-knockback-on-hit-mvp.md"));
        assertTrue(doc.contains("lava and burning damage"));
        assertTrue(doc.contains("Generic non-fire, non-fall damage remains vanilla-like."));
        assertTrue(doc.contains("boss bar / sky-darkening"));
        assertTrue(doc.contains("particle trail"));
        assertTrue(doc.contains("runtime effective max health remains clamped to `1024.0`"));
        assertTrue(doc.contains("natural spawning remains explicitly deferred"));
        assertTrue(doc.contains("Crazy Skeleton / Crazy Creeper / Crazy Spider"));
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void crazyZombieKnockbackDocStatesLegacyBossParticleAndOutgoingCombatMapping() throws IOException {
        String doc = Files.readString(KNOCKBACK_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyZombie`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicZombie`"));
        assertTrue(doc.contains("`cavern.client.particle.ParticleCrazyMob`"));
        assertTrue(doc.contains("`BossInfoServer`"));
        assertTrue(doc.contains("color `BossInfo.Color.BLUE`"));
        assertTrue(doc.contains("overlay `BossInfo.Overlay.PROGRESS`"));
        assertTrue(doc.contains("`EntityCrazyZombie#isNonBoss()` returns `false`"));
        assertTrue(doc.contains("`bossInfo.setDarkenSky(!canSee || distance < 30.0D)`"));
        assertTrue(doc.contains("`bossInfo.setVisible(canSee)`"));
        assertTrue(doc.contains("`bossInfo.setPercent(getHealth() / getMaxHealth())`"));
        assertTrue(doc.contains("`canEntityBeSeen(player)`"));
        assertTrue(doc.contains("within `20.0D`"));
        assertTrue(doc.contains("client-only"));
        assertTrue(doc.contains("spawns `3` `ParticleCrazyMob` particles per tick"));
        assertTrue(doc.contains("`ParticleCrazyMob` extends `ParticlePortal`"));
        assertTrue(doc.contains("`attackEntityAsMob(Entity entity)`"));
        assertTrue(doc.contains("`rand.nextInt(5) == 0`"));
        assertTrue(doc.contains("`rand.nextInt(3) + 3`"));
        assertTrue(doc.contains("`target.knockBack(this, power * 0.5F"));
        assertTrue(doc.contains("`LEGACY_KNOCKBACK_STRENGTH_MULTIPLIER = 0.5F`"));
        assertTrue(doc.contains("not gated on the `super.attackEntityAsMob(...)` return value"));
        assertTrue(doc.contains("`CrazyZombie#doHurtTarget(Entity target)`"));
        assertTrue(doc.contains("`getLegacyKnockbackPower(int triggerRoll, int magnitudeRoll)`"));
        assertTrue(doc.contains("`tryApplyLegacyKnockback(Entity target, int power)`"));
        assertTrue(doc.contains("keeps `CrazyZombie extends Zombie`"));
        assertTrue(doc.contains("boss bar and sky-darkening path is a separate boss-event family"));
        assertTrue(doc.contains("particle trail is a separate client-only visual system"));
        assertTrue(doc.contains("Crazy Zombie boss bar / sky-darkening"));
        assertTrue(doc.contains("Crazy Zombie particle trail"));
        assertTrue(doc.contains("Crazy Skeleton / Crazy Creeper / Crazy Spider"));
    }

    @Test
    void crazyZombieNaturalSpawnBoundaryDocStatesLegacyFindingAndDeferralReason() throws IOException {
        String doc = Files.readString(NATURAL_SPAWN_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazyZombie`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`cavern.world.WorldProviderCavern`"));
        assertTrue(doc.contains("`cavern.world.mirage.WorldProviderCavenia`"));
        assertTrue(doc.contains("`cavern.config.CaveniaConfig`"));
        assertTrue(doc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(doc.contains("`new SpawnListEntry(EntityCrazyZombie.class, 1, 1, 1)`"));
        assertTrue(doc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(doc.contains("`CaveniaConfig.crazySpawnChance`"));
        assertTrue(doc.contains("no nearby `ICavenicMob` entity with `!entity.isNonBoss()`"));
        assertTrue(doc.contains("Legacy `EntityCrazyZombie#getMaxSpawnedInChunk()` returns `1`"));
        assertTrue(doc.contains("Reborn does not register natural spawn placement for `crazy_zombie`"));
        assertTrue(doc.contains("does not add a fake `CAVERN` biome modifier or normal monster spawn rule"));
        assertTrue(doc.contains("`data/cavernreborn/neoforge/biome_modifier/crazy_zombie_spawns.json`"));
        assertTrue(doc.contains("`data/cavernreborn/tags/worldgen/biome/spawns_crazy_zombie.json`"));
        assertTrue(doc.contains("docs/crazy-zombie-loot-mvp.md"));
        assertTrue(doc.contains("docs/crazy-zombie-damage-behavior-mvp.md"));
        assertTrue(doc.contains("docs/crazy-zombie-knockback-on-hit-mvp.md"));
        assertTrue(doc.contains("loot follow-up stays explicit and independent from natural spawning"));
        assertTrue(doc.contains("damage-behavior follow-up stays explicit and independent from natural spawning"));
        assertTrue(doc.contains("weighted crazy-roster switching across Crazy Skeleton, Crazy Creeper, Crazy Zombie and Crazy Spider"));
    }

    @Test
    void runtimeSmokeDocMentionsCrazyZombieBaselineDamageAndNaturalSpawnBoundaryCoverage() throws IOException {
        String doc = Files.readString(RUNTIME_SMOKE);

        assertTrue(doc.contains("crazy zombie runtime registry id"));
        assertTrue(doc.contains("crazy zombie attribute registration smoke"));
        assertTrue(doc.contains("crazy zombie hostile runtime spawn smoke"));
        assertTrue(doc.contains("crazy zombie spawn egg resolution"));
        assertTrue(doc.contains("crazy zombie spawn egg entity-creation smoke"));
        assertTrue(doc.contains("crazy zombie vanilla loot-table baseline smoke"));
        assertTrue(doc.contains("crazy zombie legacy orb-drop event wiring smoke"));
        assertTrue(doc.contains("crazy zombie legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(doc.contains("crazy zombie legacy fall-damage reduction smoke"));
        assertTrue(doc.contains("crazy zombie legacy fire-damage immunity smoke"));
        assertTrue(doc.contains("crazy zombie generic-damage baseline smoke"));
        assertTrue(doc.contains("crazy zombie legacy knockback-on-hit melee smoke"));
        assertTrue(doc.contains("crazy zombie deterministic legacy knockback helper smoke"));
        assertTrue(doc.contains("explicit Crazy Zombie natural-spawn deferred boundary"));
        assertTrue(doc.contains("Cavenia-only crazy-roster provider/config path"));
        assertTrue(doc.contains("actual Crazy Zombie renderer/model visual feel"));
        assertTrue(doc.contains("actual long-running Crazy Zombie fire/lava gameplay feel"));
        assertTrue(doc.contains("actual Crazy Zombie boss-bar and particle feel"));
        assertTrue(doc.contains("actual long-running Crazy Zombie melee-knockback feel beyond the deterministic helper/runtime smoke"));
        assertTrue(doc.contains("Crazy Zombie natural spawning, because the inspected legacy path is tied to the old Cavenia-only crazy-roster provider/config branch"));
        assertTrue(doc.contains("actual long-run Crazy Zombie orb-drop rate balance"));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || i == 4) {
                return candidate;
            }
            current = current.getParent();
        }

        return Path.of(first, more);
    }
}
