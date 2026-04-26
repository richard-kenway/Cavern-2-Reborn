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
    private static final Path CAVENIC_WITCH_FRIENDSHIP_TARGETING_MVP = resolveProjectFile("docs", "cavenic-witch-friendship-targeting-mvp.md");
    private static final Path CAVENIC_WITCH_RANGED_POTION_MVP = resolveProjectFile("docs", "cavenic-witch-ranged-potion-mvp.md");
    private static final Path CAVENIC_WITCH_MAGIC_BOOK_DEFERRED = resolveProjectFile("docs", "cavenic-witch-magic-book-deferred.md");
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
        assertTrue(readme.contains("Cavenic Witch Friendship Targeting MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-friendship-targeting-mvp.md"));
        assertTrue(readme.contains("Cavenic Witch Custom Ranged Potion MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-ranged-potion-mvp.md"));
        assertTrue(readme.contains("docs/cavenic-witch-magic-book-deferred.md"));
        assertTrue(readme.contains("cavenic_witch"));
        assertTrue(readme.contains("fifth direct Cavenic mob foundation"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
        assertTrue(readme.contains("vanilla witch loot baseline"));
        assertTrue(readme.contains("legacy `1/5` `cavenic_orb` drop appended on top of the vanilla witch loot baseline"));
        assertTrue(readme.contains("legacy same-type/self source-immunity behavior"));
        assertTrue(readme.contains("legacy same-type friendship-target rejection behavior"));
        assertTrue(readme.contains("restored legacy ranged-potion behavior for normal combat targets"));
        assertTrue(readme.contains("the modern non-witch Raider-heal branch remains an intentional bounded bridge"));
        assertTrue(readme.contains("the inspected legacy magic-book branch remains explicitly deferred because `ItemMagicBook` is still a broader subtype/NBT/capability spellbook system"));
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
        assertTrue(doc.contains("The bounded friendship-targeting follow-up is now documented separately in `docs/cavenic-witch-friendship-targeting-mvp.md`."));
        assertTrue(doc.contains("The bounded custom ranged-potion follow-up is now documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`."));
        assertTrue(doc.contains("custom loot beyond the restored `1/5` `cavenic_orb` drop remains out of scope"));
        assertTrue(doc.contains("the deferred legacy magic-book branch is now documented separately in `docs/cavenic-witch-magic-book-deferred.md`."));
        assertTrue(doc.contains("legacy fall/fire damage behavior is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("legacy same-type friendship targeting is now documented separately in `docs/cavenic-witch-friendship-targeting-mvp.md`."));
        assertTrue(doc.contains("legacy custom ranged-potion behavior is now documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`."));
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
        assertTrue(doc.contains("The bounded friendship-targeting follow-up is now documented separately in `docs/cavenic-witch-friendship-targeting-mvp.md`."));
        assertTrue(doc.contains("The bounded custom ranged-potion follow-up is now documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`."));
        assertTrue(doc.contains("the restored legacy `1/5` `cavenic_orb` drop remains unchanged"));
        assertTrue(doc.contains("the deferred legacy magic-book branch is now documented separately in `docs/cavenic-witch-magic-book-deferred.md`."));
        assertTrue(doc.contains("the restored legacy fall-damage reduction and fire-damage immunity behavior remain unchanged"));
        assertTrue(doc.contains("the restored legacy same-type/self source-immunity behavior remains unchanged"));
        assertTrue(doc.contains("the restored same-type friendship-target rejection behavior is now documented separately in `docs/cavenic-witch-friendship-targeting-mvp.md`."));
        assertTrue(doc.contains("the restored legacy ranged-potion behavior is now documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`."));
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
        assertTrue(doc.contains("The detailed deferred analysis is now documented separately in `docs/cavenic-witch-magic-book-deferred.md`."));
        assertTrue(doc.contains("Legacy `ItemMagicBook.getRandomBook()` is not a plain loot-token factory"));
        assertTrue(doc.contains("chooses one of `12` subtype spellbooks"));
        assertTrue(doc.contains("writes random `Mana` NBT"));
        assertTrue(doc.contains("player and item capabilities"));
        assertTrue(doc.contains("Looting does not affect the orb branch"));
        assertTrue(doc.contains("Player kill is not required for the orb branch"));
        assertTrue(doc.contains("Progression, dimension and economy do not affect the orb branch"));
        assertTrue(doc.contains("Fall/fire damage behavior remains unchanged and is now documented separately in `docs/cavenic-witch-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("Same-type/self source immunity remains unchanged and is now documented separately in `docs/cavenic-witch-projectile-immunity-mvp.md`."));
        assertTrue(doc.contains("Friendship targeting remains unchanged and is now documented separately in `docs/cavenic-witch-friendship-targeting-mvp.md`."));
        assertTrue(doc.contains("The bounded custom ranged-potion follow-up is now documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`."));
    }

    @Test
    void cavenicWitchMagicBookDeferredDocStatesLegacyBehaviorAndBroadDependencyReason() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_MAGIC_BOOK_DEFERRED);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("`cavern.item.ItemMagicBook`"));
        assertTrue(doc.contains("`cavern.item.CaveItems`"));
        assertTrue(doc.contains("`cavern.capability.CaveCapabilities`"));
        assertTrue(doc.contains("`cavern.magic.MagicBook`"));
        assertTrue(doc.contains("registered as `cavern:magic_book`"));
        assertTrue(doc.contains("defines `12` subtype books"));
        assertTrue(doc.contains("writes random `Mana` NBT"));
        assertTrue(doc.contains("`LastUseTime` cooldown NBT"));
        assertTrue(doc.contains("player `MagicBook` capability"));
        assertTrue(doc.contains("storage-book `MAGIC_STORAGE` item capability"));
        assertTrue(doc.contains("only when that orb roll failed"));
        assertTrue(doc.contains("`0.25F` drop offset"));
        assertTrue(doc.contains("The branch remains deferred."));
        assertTrue(doc.contains("a fake placeholder `magic_book` item"));
        assertTrue(doc.contains("a dummy spellbook enum with no real meaning"));
        assertTrue(doc.contains("Reborn still has no `magic_book`, `MagicBook`, or `ItemMagicBook` registry entry"));
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
        assertTrue(doc.contains("The deferred legacy magic-book branch remains documented in `docs/cavenic-witch-magic-book-deferred.md`."));
        assertTrue(doc.contains("The bounded same-type/self source-immunity follow-up is documented separately in `docs/cavenic-witch-projectile-immunity-mvp.md`."));
        assertTrue(doc.contains("The bounded friendship-targeting follow-up is documented separately in `docs/cavenic-witch-friendship-targeting-mvp.md`."));
        assertTrue(doc.contains("The bounded custom ranged-potion follow-up is documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`."));
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
        assertTrue(doc.contains("friendship targeting remains a separate bounded follow-up"));
        assertTrue(doc.contains("The deferred legacy magic-book branch remains documented in `docs/cavenic-witch-magic-book-deferred.md`."));
        assertTrue(doc.contains("The bounded custom ranged-potion follow-up is documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`."));
        assertTrue(doc.contains("Cavenia and additional mobs remain out of scope."));
    }

    @Test
    void cavenicWitchFriendshipTargetingDocStatesLegacyBehaviorAndBoundedFollowUps() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_FRIENDSHIP_TARGETING_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("legacy `setAttackTarget(EntityLivingBase entity)` override"));
        assertTrue(doc.contains("legacy `isFriends(@Nullable Entity entity)` helper"));
        assertTrue(doc.contains("`isFriends(...)` returned `true` only for `EntityCavenicWitch`"));
        assertTrue(doc.contains("The modern hook is the entity-local `setTarget(@Nullable LivingEntity target)` override."));
        assertTrue(doc.contains("`isLegacyFriendTarget(@Nullable LivingEntity target)`"));
        assertTrue(doc.contains("Rejected same-type targets do not clear an existing non-friend target"));
        assertTrue(doc.contains("Vanilla `Witch` targets remain allowed."));
        assertTrue(doc.contains("Non-witch hostile targets such as `Zombie` remain allowed."));
        assertTrue(doc.contains("Explicit `null` target clearing remains vanilla-like."));
        assertTrue(doc.contains("Same-type/self source immunity remains unchanged and is still documented in `docs/cavenic-witch-projectile-immunity-mvp.md`."));
        assertTrue(doc.contains("Fall/fire damage behavior remains unchanged and is still documented in `docs/cavenic-witch-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("The deferred legacy magic-book branch remains documented in `docs/cavenic-witch-magic-book-deferred.md`."));
        assertTrue(doc.contains("The bounded custom ranged-potion follow-up is documented separately in `docs/cavenic-witch-ranged-potion-mvp.md`."));
        assertTrue(doc.contains("Cavenia and additional mobs remain out of scope."));
    }

    @Test
    void cavenicWitchRangedPotionDocStatesLegacyBehaviorModernBridgeAndBoundedFollowUps() throws IOException {
        String doc = Files.readString(CAVENIC_WITCH_RANGED_POTION_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicWitch`"));
        assertTrue(doc.contains("legacy `attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)` override"));
        assertTrue(doc.contains("`0 / 1 / 2 / 3`"));
        assertTrue(doc.contains("`world.getWorldInfo().getDifficulty().getDifficultyId()`"));
        assertTrue(doc.contains("`PotionTypes.HARMING`"));
        assertTrue(doc.contains("`PotionTypes.HEALING`"));
        assertTrue(doc.contains("`PotionTypes.POISON`"));
        assertTrue(doc.contains("`PotionTypes.WEAKNESS`"));
        assertTrue(doc.contains("`PotionTypes.SLOWNESS`"));
        assertTrue(doc.contains("The modern hook is the entity-local `performRangedAttack(LivingEntity target, float distanceFactor)` override."));
        assertTrue(doc.contains("`createLegacyThrownPotionsFor(LivingEntity target)`"));
        assertTrue(doc.contains("`getLegacyAttackPotionCount(Difficulty difficulty)`"));
        assertTrue(doc.contains("`selectLegacyRangedPotionFor(LivingEntity target, RandomSource random)`"));
        assertTrue(doc.contains("when the target is a non-witch `Raider`, `CavenicWitch` delegates to `super.performRangedAttack(...)`"));
        assertTrue(doc.contains("Vanilla `Witch` targets are not included in that bridge"));
        assertTrue(doc.contains("high-health `0.5F` threshold"));
        assertTrue(doc.contains("low-health `0.3F` threshold"));
        assertTrue(doc.contains("explicit weakness/slowness/harming fallback cases"));
        assertTrue(doc.contains("Same-type/self source immunity remains unchanged and is still documented in `docs/cavenic-witch-projectile-immunity-mvp.md`."));
        assertTrue(doc.contains("Friendship targeting remains unchanged and is still documented in `docs/cavenic-witch-friendship-targeting-mvp.md`."));
        assertTrue(doc.contains("Fall/fire damage behavior remains unchanged and is still documented in `docs/cavenic-witch-damage-behavior-mvp.md`."));
        assertTrue(doc.contains("The deferred legacy magic-book branch remains documented in `docs/cavenic-witch-magic-book-deferred.md`."));
    }

    @Test
    void runtimeSmokeMentionsCavenicWitchBaselineNaturalSpawnLootDamageProjectileImmunityFriendshipTargetingRangedPotionCoverageAndManualBoundary() throws IOException {
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
        assertTrue(runtimeSmoke.contains("cavenic witch same-type friendship target rejection smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch non-friend target baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch legacy ranged-potion runtime smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch deterministic legacy potion-selection smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch explicit high-health and low-health potion-threshold smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch explicit weakness/slowness/harming fallback smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch deterministic legacy thrown-potion construction smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch direct non-friend ranged-attack branch smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch preserved non-witch Raider bridge smoke"));
        assertTrue(runtimeSmoke.contains("legacy cavenic witch magic-book branch because the inspected legacy `ItemMagicBook` depends on subtype, NBT, capability and spellbook systems that Reborn does not yet implement honestly"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Witch renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Witch combat, potion-throw and pathfinding feel"));
        assertTrue(runtimeSmoke.contains("any broader Cavenic Witch AI-goal or target-selector rewrite beyond the restored ranged-potion slice"));
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
