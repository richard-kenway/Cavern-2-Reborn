package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicWitchDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_WITCH_BASELINE_MVP = resolveProjectFile("docs", "cavenic-witch-baseline-mvp.md");
    private static final Path CAVENIC_WITCH_NATURAL_SPAWN_MVP = resolveProjectFile("docs", "cavenic-witch-natural-spawn-mvp.md");
    private static final Path CAVENIC_WITCH_LOOT_MVP = resolveProjectFile("docs", "cavenic-witch-loot-mvp.md");
    private static final Path CAVENIC_WITCH_DAMAGE_BEHAVIOR_MVP = resolveProjectFile("docs", "cavenic-witch-damage-behavior-mvp.md");
    private static final Path CAVENIC_WITCH_PROJECTILE_IMMUNITY_MVP = resolveProjectFile("docs", "cavenic-witch-projectile-immunity-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicWitchBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Witch Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Witch Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-natural-spawn-mvp.md"));
        assertTrue(readme.contains("Cavenic Witch Legacy Loot MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-loot-mvp.md"));
        assertTrue(readme.contains("Cavenic Witch Legacy Damage Behavior MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-damage-behavior-mvp.md"));
        assertTrue(readme.contains("Cavenic Witch Same-Type Projectile Immunity MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-projectile-immunity-mvp.md"));
        assertTrue(readme.contains("cavenic_witch"));
        assertTrue(readme.contains("fifth direct Cavenic mob foundation"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
        assertTrue(readme.contains("vanilla witch loot baseline"));
        assertTrue(readme.contains("legacy `1/5` `cavenic_orb` drop appended on top of the vanilla witch loot baseline"));
        assertTrue(readme.contains("legacy same-type/self source-immunity behavior"));
        assertTrue(readme.contains("the deferred magic-book branch, custom potion logic and friendship targeting remain intentionally out of scope"));
    }

    @Test
    void cavenicWitchBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_witch`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_witch_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicWitch`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `Witch`"));
        assertTrue(doc.contains("max health: `50.0`"));
        assertTrue(doc.contains("follow range: `32.0`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("movement speed intentionally stays on the vanilla witch baseline"));
        assertTrue(doc.contains("potion-throw behavior intentionally stays on the vanilla witch baseline"));
        assertTrue(doc.contains("XP reward is pinned to `12`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0x4A5348`"));
        assertTrue(doc.contains("reuses the vanilla witch renderer/model path"));
        assertTrue(doc.contains("reuses the vanilla witch loot table as its base drop source"));
        assertTrue(doc.contains("The bounded natural-spawn follow-up is now documented separately in `docs/cavenic-witch-natural-spawn-mvp.md`."));
        assertTrue(doc.contains("The bounded legacy loot follow-up is now documented separately in `docs/cavenic-witch-loot-mvp.md`."));
        assertTrue(doc.contains("The bounded legacy damage-behavior follow-up is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("The bounded legacy same-type/self source-immunity follow-up is now documented separately in `docs/cavenic-witch-projectile-immunity-mvp.md`."));
        assertTrue(doc.contains("custom loot beyond the restored `1/5` `cavenic_orb` drop remains out of scope"));
        assertTrue(doc.contains("the legacy magic-book branch remains deferred because Reborn does not yet have a magic-book foundation"));
        assertTrue(doc.contains("legacy fall/fire damage behavior is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("custom potion logic and friendship targeting remain out of scope"));
        assertTrue(doc.contains("the legacy `getMaxSpawnedInChunk()` Cavenia-specific behavior remains out of scope"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void cavenicWitchNaturalSpawnDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_NATURAL_SPAWN_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("SpawnPlacementTypes.ON_GROUND"));
        assertTrue(doc.contains("Heightmap.Types.MOTION_BLOCKING_NO_LEAVES"));
        assertTrue(doc.contains("Monster.checkMonsterSpawnRules"));
        assertTrue(doc.contains("data/cavernreborn/neoforge/biome_modifier/cavenic_witch_spawns.json"));
        assertTrue(doc.contains("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_witch.json"));
        assertTrue(doc.contains("weight: `15`"));
        assertTrue(doc.contains("group size: `1..1`"));
        assertTrue(doc.contains("Natural spawning is deliberately limited to `CAVERN`"));
        assertTrue(doc.contains("The bounded legacy loot follow-up is now documented separately in `docs/cavenic-witch-loot-mvp.md`."));
        assertTrue(doc.contains("The bounded legacy damage-behavior follow-up is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("The bounded legacy same-type/self source-immunity follow-up is now documented separately in `docs/cavenic-witch-projectile-immunity-mvp.md`."));
        assertTrue(doc.contains("the restored legacy `1/5` `cavenic_orb` drop remains unchanged"));
        assertTrue(doc.contains("the deferred magic-book branch still needs a Reborn magic-book foundation"));
        assertTrue(doc.contains("the restored legacy fall-damage reduction and fire-damage immunity behavior remain unchanged"));
        assertTrue(doc.contains("the restored legacy same-type/self source-immunity behavior remains unchanged"));
        assertTrue(doc.contains("custom potion logic, custom ranged attack behavior and friendship targeting remain out of scope"));
        assertTrue(doc.contains("the legacy `getMaxSpawnedInChunk()` Cavenia-specific behavior remains out of scope"));
    }

    @Test
    void cavenicWitchLootDocStatesLegacyBehaviorDeferredMagicBookAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_LOOT_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`cavern.item.ItemCave`"));
        assertTrue(doc.contains("`cavern.item.ItemMagicBook`"));
        assertTrue(doc.contains("`rand.nextInt(5) == 0`"));
        assertTrue(doc.contains("orb chance is `1/5`"));
        assertTrue(doc.contains("fallback magic-book branch"));
        assertTrue(doc.contains("`ItemMagicBook.getRandomBook()`"));
        assertTrue(doc.contains("Legacy `EntityCavenicWitch` first calls `super.dropLoot(...)`"));
        assertTrue(doc.contains("`0.5F`"));
        assertTrue(doc.contains("`0.25F`"));
        assertTrue(doc.contains("Reborn preserves the vanilla witch loot table as the baseline"));
        assertTrue(doc.contains("The magic-book branch is deferred"));
        assertTrue(doc.contains("Reborn does not currently contain a `magic_book`, `MagicBook` or `ItemMagicBook` foundation"));
        assertTrue(doc.contains("Looting does not affect the orb branch"));
        assertTrue(doc.contains("Player kill is not required for the orb branch"));
        assertTrue(doc.contains("Progression, dimension and economy do not affect the orb branch"));
        assertTrue(doc.contains("Fall/fire damage behavior remains unchanged and is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("Same-type/self source immunity remains unchanged and is now documented separately in `docs/cavenic-witch-projectile-immunity-mvp.md`."));
        assertTrue(doc.contains("Custom potion logic, custom ranged attack behavior and friendship targeting remain out of scope."));
    }

    @Test
    void cavenicWitchDamageDocStatesLegacyBehaviorAndBoundedFollowUps() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_DAMAGE_BEHAVIOR_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("legacy `attackEntityFrom(DamageSource source, float damage)` override"));
        assertTrue(doc.contains("legacy `isEntityInvulnerable(DamageSource source)` override"));
        assertTrue(doc.contains("`source == DamageSource.FALL`"));
        assertTrue(doc.contains("`!source.isFireDamage()`"));
        assertTrue(doc.contains("The modern hook is the entity-local `hurt(DamageSource source, float damage)` override."));
        assertTrue(doc.contains("`LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FALL`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FIRE`"));
        assertTrue(doc.contains("Fall damage is reduced to `35%`"));
        assertTrue(doc.contains("All modern fire-tagged damage sources are ignored."));
        assertTrue(doc.contains("That bounded modern mapping includes lava"));
        assertTrue(doc.contains("vanilla witch AI remains unchanged."));
        assertTrue(doc.contains("vanilla drinking and potion-throw behavior remain unchanged."));
        assertTrue(doc.contains("attributes, natural spawning and `1/5` orb-drop behavior remain unchanged."));
        assertTrue(doc.contains("The deferred magic-book branch remains documented in `docs/cavenic-witch-loot-mvp.md`."));
        assertTrue(doc.contains("The bounded same-type/self source-immunity follow-up is documented separately in `docs/cavenic-witch-projectile-immunity-mvp.md`."));
        assertTrue(doc.contains("Custom potion logic, custom ranged attack behavior and friendship targeting remain out of scope."));
    }

    @Test
    void cavenicWitchProjectileImmunityDocStatesLegacyBehaviorAndBoundedFollowUps() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_PROJECTILE_IMMUNITY_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("legacy `isEntityInvulnerable(DamageSource source)` override"));
        assertTrue(doc.contains("legacy `setAttackTarget(EntityLivingBase entity)` override"));
        assertTrue(doc.contains("`source.getTrueSource() == this || source.getImmediateSource() == this`"));
        assertTrue(doc.contains("`isFriends(source.getTrueSource()) || isFriends(source.getImmediateSource())`"));
        assertTrue(doc.contains("The modern hook is the entity-local `isInvulnerableTo(DamageSource source)` override."));
        assertTrue(doc.contains("`source.getEntity()`"));
        assertTrue(doc.contains("`source.getDirectEntity()`"));
        assertTrue(doc.contains("direct same-type entity sources"));
        assertTrue(doc.contains("Vanilla `Witch` sources are not included."));
        assertTrue(doc.contains("fall/fire damage behavior remains unchanged"));
        assertTrue(doc.contains("vanilla drinking and potion-throw behavior remain unchanged"));
        assertTrue(doc.contains("friendship targeting remains out of scope"));
        assertTrue(doc.contains("The deferred magic-book branch remains documented in `docs/cavenic-witch-loot-mvp.md`."));
        assertTrue(doc.contains("custom potion/ranged behavior, Cavenia and additional mobs remain out of scope"));
    }

    @Test
    void runtimeSmokeMentionsCavenicWitchBaselineNaturalSpawnLootDamageProjectileImmunityCoverageAndManualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic witch runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic witch attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch vanilla loot-table baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic witch spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch natural spawn placement registration"));
        assertTrue(runtimeSmoke.contains("cavenic witch CAVERN-only spawn predicate smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch biome modifier registry smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch biome tag resolution"));
        assertTrue(runtimeSmoke.contains("cavenic witch legacy orb-drop event wiring smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch deterministic winning/losing roll smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch legacy fall-damage reduction smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch legacy fire-damage immunity smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch generic-damage baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch legacy same-type/self source-immunity smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch non-immune source baseline smoke"));
        assertTrue(runtimeSmoke.contains("legacy cavenic witch magic-book branch because Reborn has no magic-book foundation yet"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Witch renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Witch combat, potion-throw and pathfinding feel"));
        assertTrue(runtimeSmoke.contains("custom Cavenic Witch potion/ranged behavior and friendship targeting remain out of scope until dedicated slices"));
        assertTrue(runtimeSmoke.contains("actual long-running Cavenic Witch fire/lava gameplay feel"));
        assertTrue(runtimeSmoke.contains("actual long-run cavenic witch orb-drop rate balance"));
        assertTrue(runtimeSmoke.contains("actual long-run Cavenic Witch population balance inside CAVERN"));
        assertTrue(!runtimeSmoke.contains("Cavenic Witch natural spawning, because that bounded follow-up is still out of scope for the current baseline slice"));
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
