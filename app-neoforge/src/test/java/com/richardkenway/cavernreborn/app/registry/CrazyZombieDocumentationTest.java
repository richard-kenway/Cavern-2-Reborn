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
        assertTrue(readme.contains("docs/crazy-zombie-damage-behavior-mvp.md"));
        assertTrue(readme.contains("inherited legacy fall-damage reduction and fire-damage immunity behavior"));
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
        assertTrue(doc.contains("Custom loot, `cavenic_orb` drops and inherited Cavenic boss-like follow-ups remain out of scope"));
        assertTrue(doc.contains("The separate inherited damage behavior follow-up is documented in `docs/crazy-zombie-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("boss bar / sky-darkening"));
        assertTrue(doc.contains("crazy particle trail"));
        assertTrue(doc.contains("knockback-on-hit behavior"));
        assertTrue(doc.contains("Crazy Skeleton remains a follow-up because it depends on the legacy `EntityAIAttackCavenicBow` path"));
        assertTrue(doc.contains("Crazy Creeper remains a follow-up because its direct legacy identity depends on a huge fuse/explosion branch"));
        assertTrue(doc.contains("Crazy Spider remains a follow-up because it carries stronger blindness/poison combat hooks"));
        assertTrue(doc.contains("Cavenia remains out of scope"));
        assertTrue(doc.contains("The magic-book system remains intentionally untouched."));
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
        assertTrue(doc.contains("lava and burning damage"));
        assertTrue(doc.contains("Generic non-fire, non-fall damage remains vanilla-like."));
        assertTrue(doc.contains("boss bar / sky-darkening"));
        assertTrue(doc.contains("particle trail"));
        assertTrue(doc.contains("knockback-on-hit behavior"));
        assertTrue(doc.contains("runtime effective max health remains clamped to `1024.0`"));
        assertTrue(doc.contains("natural spawning remains explicitly deferred"));
        assertTrue(doc.contains("Crazy Skeleton / Crazy Creeper / Crazy Spider"));
        assertTrue(doc.contains("Cavenia"));
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
        assertTrue(doc.contains("docs/crazy-zombie-damage-behavior-mvp.md"));
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
        assertTrue(doc.contains("crazy zombie legacy fall-damage reduction smoke"));
        assertTrue(doc.contains("crazy zombie legacy fire-damage immunity smoke"));
        assertTrue(doc.contains("crazy zombie generic-damage baseline smoke"));
        assertTrue(doc.contains("explicit Crazy Zombie natural-spawn deferred boundary"));
        assertTrue(doc.contains("Cavenia-only crazy-roster provider/config path"));
        assertTrue(doc.contains("actual Crazy Zombie renderer/model visual feel"));
        assertTrue(doc.contains("actual long-running Crazy Zombie fire/lava gameplay feel"));
        assertTrue(doc.contains("actual Crazy Zombie boss-bar, particle and melee-knockback feel"));
        assertTrue(doc.contains("Crazy Zombie natural spawning, because the inspected legacy path is tied to the old Cavenia-only crazy-roster provider/config branch"));
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
