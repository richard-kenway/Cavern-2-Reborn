package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicBearDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_BEAR_BASELINE_MVP = resolveProjectFile("docs", "cavenic-bear-baseline-mvp.md");
    private static final Path CAVENIC_BEAR_NATURAL_SPAWN_MVP = resolveProjectFile("docs", "cavenic-bear-natural-spawn-mvp.md");
    private static final Path CAVENIC_BEAR_LOOT_ABSENT_OR_DEFERRED = resolveProjectFile("docs", "cavenic-bear-loot-absent-or-deferred.md");
    private static final Path CAVENIC_BEAR_DAMAGE_BEHAVIOR_MVP = resolveProjectFile("docs", "cavenic-bear-damage-behavior-mvp.md");
    private static final Path CAVENIC_BEAR_HOSTILE_TARGETING_MVP = resolveProjectFile("docs", "cavenic-bear-hostile-targeting-mvp.md");
    private static final Path CAVENIC_BEAR_MELEE_ATTACK_MVP = resolveProjectFile("docs", "cavenic-bear-melee-attack-mvp.md");
    private static final Path CAVENIC_BEAR_PANIC_BEHAVIOR_MVP = resolveProjectFile("docs", "cavenic-bear-panic-behavior-mvp.md");
    private static final Path CAVENIC_BEAR_PASSIVE_AI_EQUIVALENT_OR_DEFERRED = resolveProjectFile(
        "docs", "cavenic-bear-passive-ai-equivalent-or-deferred.md"
    );
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicBearBaselineNaturalSpawnDamageHostileTargetingMeleePanicPassiveAiBoundaryAndDocPaths() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Bear Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-bear-baseline-mvp.md"));
        assertTrue(readme.contains("Cavenic Bear Natural Spawn MVP"));
        assertTrue(readme.contains("docs/cavenic-bear-natural-spawn-mvp.md"));
        assertTrue(readme.contains("Cavenic Bear Loot Boundary"));
        assertTrue(readme.contains("docs/cavenic-bear-loot-absent-or-deferred.md"));
        assertTrue(readme.contains("Cavenic Bear Legacy Damage Behavior MVP"));
        assertTrue(readme.contains("docs/cavenic-bear-damage-behavior-mvp.md"));
        assertTrue(readme.contains("Cavenic Bear Legacy Hostile Targeting MVP"));
        assertTrue(readme.contains("docs/cavenic-bear-hostile-targeting-mvp.md"));
        assertTrue(readme.contains("Cavenic Bear Legacy Melee Attack MVP"));
        assertTrue(readme.contains("docs/cavenic-bear-melee-attack-mvp.md"));
        assertTrue(readme.contains("Cavenic Bear Legacy Panic Behavior MVP"));
        assertTrue(readme.contains("docs/cavenic-bear-panic-behavior-mvp.md"));
        assertTrue(readme.contains("Cavenic Bear Passive / Movement AI Boundary MVP"));
        assertTrue(readme.contains("docs/cavenic-bear-passive-ai-equivalent-or-deferred.md"));
        assertTrue(readme.contains("cavenic_bear"));
        assertTrue(readme.contains("sixth direct Cavenic mob foundation"));
        assertTrue(readme.contains("legacy texture on the vanilla polar bear renderer path"));
        assertTrue(readme.contains("vanilla polar bear loot baseline"));
        assertTrue(readme.contains("bounded CAVERN-only natural spawning"));
        assertTrue(readme.contains("the inspected legacy bear loot line remains explicitly absent because `EntityCavenicBear` never overrides `dropLoot(...)`"));
        assertTrue(readme.contains("legacy fall-damage reduction and fire-damage immunity behavior"));
        assertTrue(readme.contains("bounded legacy hostile-targeting behavior"));
        assertTrue(readme.contains("bounded legacy melee-attack behavior"));
        assertTrue(readme.contains("bounded legacy panic behavior"));
        assertTrue(readme.contains("the remaining passive/movement bear goals stay on the inherited modern polar bear base"));
    }

    @Test
    void cavenicBearBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_bear`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_bear_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicBear`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `PolarBear`"));
        assertTrue(doc.contains("max health: `60.0`"));
        assertTrue(doc.contains("movement speed: `0.3`"));
        assertTrue(doc.contains("attack damage: `7.0`"));
        assertTrue(doc.contains("follow range intentionally stays on the vanilla polar bear baseline"));
        assertTrue(doc.contains("XP reward is pinned to `13`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0xFFFFFF`"));
        assertTrue(doc.contains("reuses the vanilla polar bear renderer/model path"));
        assertTrue(doc.contains("reuses the vanilla polar bear loot table as its base drop source"));
        assertTrue(doc.contains("Natural spawning was intentionally out of scope for the baseline slice and is now covered separately"));
        assertTrue(doc.contains("`docs/cavenic-bear-natural-spawn-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-damage-behavior-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-hostile-targeting-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-melee-attack-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-panic-behavior-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-loot-absent-or-deferred.md`"));
        assertTrue(doc.contains("Custom loot remains out of scope"));
        assertTrue(doc.contains("no direct legacy `dropLoot(...)` override"));
        assertTrue(doc.contains("no `cavenic_orb` bear branch to port"));
        assertTrue(doc.contains("fall/fire damage behavior was intentionally out of scope for the baseline slice and is now covered separately"));
        assertTrue(doc.contains("bounded hostile-targeting follow-up is now documented separately"));
        assertTrue(doc.contains("bounded melee-attack follow-up is now documented separately"));
        assertTrue(doc.contains("bounded panic-behavior follow-up is now documented separately"));
        assertTrue(doc.contains("broader custom bear AI, anger behavior, taming, riding and mount behavior remain out of scope"));
        assertTrue(doc.contains("Cavenia remains out of scope"));
        assertTrue(doc.contains("The magic-book system remains intentionally untouched."));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void cavenicBearNaturalSpawnDocStatesLegacyReferencesRuleAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_NATURAL_SPAWN_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_bear`"));
        assertTrue(doc.contains("`RegisterSpawnPlacementsEvent`"));
        assertTrue(doc.contains("`SpawnPlacementTypes.ON_GROUND`"));
        assertTrue(doc.contains("`Heightmap.Types.MOTION_BLOCKING_NO_LEAVES`"));
        assertTrue(doc.contains("`Monster.checkMonsterSpawnRules`"));
        assertTrue(doc.contains("`PolarBear.checkPolarBearSpawnRules`"));
        assertTrue(doc.contains("weight: `30`"));
        assertTrue(doc.contains("group size: `1..1`"));
        assertTrue(doc.contains("`getMaxSpawnedInChunk() = 1`"));
        assertTrue(doc.contains("`getMaxSpawnClusterSize() = 1`"));
        assertTrue(doc.contains("`data/cavernreborn/neoforge/biome_modifier/cavenic_bear_spawns.json`"));
        assertTrue(doc.contains("`data/cavernreborn/tags/worldgen/biome/spawns_cavenic_bear.json`"));
        assertTrue(doc.contains("Natural spawning is deliberately limited to `CAVERN`."));
        assertTrue(doc.contains("`docs/cavenic-bear-damage-behavior-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-hostile-targeting-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-melee-attack-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-panic-behavior-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-loot-absent-or-deferred.md`"));
        assertTrue(doc.contains("no direct legacy `cavenic_orb` bear drop was found"));
        assertTrue(doc.contains("the bounded damage follow-up is documented separately"));
        assertTrue(doc.contains("the bounded hostile-targeting follow-up is documented separately"));
        assertTrue(doc.contains("the bounded melee-attack follow-up is documented separately"));
        assertTrue(doc.contains("the bounded panic-behavior follow-up is documented separately"));
        assertTrue(doc.contains("custom loot, orb drop, broader custom bear AI, anger behavior, taming, riding and mount behavior remain out of scope"));
        assertTrue(doc.contains("Cavenia remains out of scope"));
        assertTrue(doc.contains("The magic-book system remains intentionally untouched."));
    }

    @Test
    void cavenicBearLootBoundaryDocStatesSourceFindingAndExplicitAbsence() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_LOOT_ABSENT_OR_DEFERRED);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`cavern.item.ItemCave`"));
        assertTrue(doc.contains("does not override `dropLoot(...)`"));
        assertTrue(doc.contains("does not override `getLootTable()`"));
        assertTrue(doc.contains("does not reference `ItemCave.EnumType.CAVENIC_ORB`"));
        assertTrue(doc.contains("no direct custom bear loot branch"));
        assertTrue(doc.contains("`CavenicBear#getDefaultLootTable()`"));
        assertTrue(doc.contains("`EntityType.POLAR_BEAR.getDefaultLootTable()`"));
        assertTrue(doc.contains("Reborn does not add a `LivingDropsEvent` hook for `CavenicBear`."));
        assertTrue(doc.contains("Reborn does not add a pure `CavenicBearLootPolicy`."));
        assertTrue(doc.contains("does not define a custom loot branch, so there is no custom bear drop path for looting to scale"));
        assertTrue(doc.contains("does not define a custom bear drop path gated on player kill"));
        assertTrue(doc.contains("does not define a custom bear drop path tied to difficulty, dimension, progression, economy or Cavenia"));
        assertTrue(doc.contains("The restored damage behavior is documented separately"));
        assertTrue(doc.contains("The bounded hostile-targeting follow-up is documented separately"));
        assertTrue(doc.contains("The bounded melee-attack follow-up is documented separately"));
        assertTrue(doc.contains("The bounded panic-behavior follow-up is documented separately"));
        assertTrue(doc.contains("The remaining passive/movement AI boundary is documented separately"));
        assertTrue(doc.contains("No legacy `cavenic_orb` bear branch was found."));
    }

    @Test
    void cavenicBearDamageBehaviorDocStatesLegacyReferencesMappingAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_DAMAGE_BEHAVIOR_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("legacy `attackEntityFrom(DamageSource source, float damage)` override"));
        assertTrue(doc.contains("`source == DamageSource.FALL`"));
        assertTrue(doc.contains("`damage *= 0.35F`"));
        assertTrue(doc.contains("`!source.isFireDamage()`"));
        assertTrue(doc.contains("`hurt(DamageSource source, float damage)`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FALL`"));
        assertTrue(doc.contains("`DamageTypeTags.IS_FIRE`"));
        assertTrue(doc.contains("`LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F`"));
        assertTrue(doc.contains("lava because lava uses a fire-tagged damage source"));
        assertTrue(doc.contains("broader vanilla polar bear movement base remains unchanged in this slice"));
        assertTrue(doc.contains("`docs/cavenic-bear-hostile-targeting-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-melee-attack-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-panic-behavior-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`"));
        assertTrue(doc.contains("anger behavior remains otherwise unchanged"));
        assertTrue(doc.contains("natural spawning remains unchanged"));
        assertTrue(doc.contains("max spawn cluster size remains unchanged"));
        assertTrue(doc.contains("vanilla polar bear loot baseline remains unchanged"));
        assertTrue(doc.contains("source-confirmed bear custom loot/orb-drop absence remains unchanged"));
        assertTrue(doc.contains("cavenic bear legacy fall-damage reduction smoke"));
        assertTrue(doc.contains("cavenic bear legacy fire-damage immunity smoke"));
        assertTrue(doc.contains("cavenic bear generic-damage baseline smoke"));
        assertTrue(doc.contains("bounded hostile-targeting follow-up"));
        assertTrue(doc.contains("custom bear AI and anger rewrites"));
        assertTrue(doc.contains("Cavenia-specific behavior"));
    }

    @Test
    void cavenicBearHostileTargetingDocStatesLegacyReferencesMappingAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_HOSTILE_TARGETING_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("`initEntityAI()`"));
        assertTrue(doc.contains("does not call `super.initEntityAI()`"));
        assertTrue(doc.contains("`EntityAIHurtByTarget`"));
        assertTrue(doc.contains("`EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 20, true, true, null)`"));
        assertTrue(doc.contains("legacy player-target and hurt-by-target hostility slice"));
        assertTrue(doc.contains("`registerGoals()`"));
        assertTrue(doc.contains("`LegacyCavenicBearHurtByTargetGoal`"));
        assertTrue(doc.contains("`LegacyNearestAttackablePlayerTargetGoal`"));
        assertTrue(doc.contains("`isLegacyHostileTarget(LivingEntity target)`"));
        assertTrue(doc.contains("accepted as nearest hostile targets"));
        assertTrue(doc.contains("Fox targets are not restored"));
        assertTrue(doc.contains("baby cavenic bears keep retaliating after alerting nearby adults"));
        assertTrue(doc.contains("the hostile-targeting slice itself left the vanilla panic goal in place"));
        assertTrue(doc.contains("`docs/cavenic-bear-panic-behavior-mvp.md`"));
        assertTrue(doc.contains("`docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`"));
        assertTrue(doc.contains("bounded melee-attack follow-up is documented separately"));
        assertTrue(doc.contains("anger behavior remains otherwise unchanged"));
        assertTrue(doc.contains("vanilla polar bear loot baseline remains unchanged"));
        assertTrue(doc.contains("source-confirmed bear custom loot/orb-drop absence remains unchanged"));
        assertTrue(doc.contains("fall/fire damage behavior remains unchanged"));
        assertTrue(doc.contains("actual long-running anger/pathfinding feel remains manual"));
    }

    @Test
    void cavenicBearMeleeAttackDocStatesLegacyReferencesMappingAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_MELEE_ATTACK_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("legacy inner `AIMeleeAttack`"));
        assertTrue(doc.contains("extends `EntityAIAttackMelee`"));
        assertTrue(doc.contains("`super(EntityCavenicBear.this, 1.25D, true)`"));
        assertTrue(doc.contains("`checkAndPerformAttack(...)`"));
        assertTrue(doc.contains("`getAttackReachSqr(...)`"));
        assertTrue(doc.contains("`return 4.0F + attackTarget.width`"));
        assertTrue(doc.contains("`setStanding(true)`"));
        assertTrue(doc.contains("`playWarningSound()`"));
        assertTrue(doc.contains("modern vanilla `PolarBearMeleeAttackGoal`"));
        assertTrue(doc.contains("`distance <= reachSq * 2.0D`"));
        assertTrue(doc.contains("`LegacyCavenicBearMeleeAttackGoal extends MeleeAttackGoal`"));
        assertTrue(doc.contains("`goalSelector`"));
        assertTrue(doc.contains("`PolarBearMeleeAttackGoal`"));
        assertTrue(doc.contains("`1.25D`"));
        assertTrue(doc.contains("standing/warning behavior"));
        assertTrue(doc.contains("attack reach"));
        assertTrue(doc.contains("Hostile targeting remains unchanged."));
        assertTrue(doc.contains("Panic behavior is now documented separately in `docs/cavenic-bear-panic-behavior-mvp.md`."));
        assertTrue(doc.contains("The remaining passive/movement AI boundary is now documented separately in `docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`."));
        assertTrue(doc.contains("Anger behavior remains unchanged."));
        assertTrue(doc.contains("Natural spawning remains unchanged."));
        assertTrue(doc.contains("Max spawn cluster size remains unchanged."));
        assertTrue(doc.contains("Vanilla polar bear loot baseline remains unchanged."));
        assertTrue(doc.contains("Source-confirmed bear custom loot/orb-drop absence remains unchanged."));
        assertTrue(doc.contains("Fall/fire damage behavior remains unchanged."));
        assertTrue(doc.contains("actual long-running pathfinding/anger feel remains manual"));
    }

    @Test
    void cavenicBearPanicBehaviorDocStatesLegacyReferencesMappingAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_PANIC_BEHAVIOR_MVP);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("legacy inner `AIPanic`"));
        assertTrue(doc.contains("extends `EntityAIPanic`"));
        assertTrue(doc.contains("`super(EntityCavenicBear.this, 2.0D)`"));
        assertTrue(doc.contains("`shouldExecute()`"));
        assertTrue(doc.contains("`EntityCavenicBear.this.isBurning() && super.shouldExecute()`"));
        assertTrue(doc.contains("modern vanilla `PanicGoal(this, 2.0, ... DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES)`"));
        assertTrue(doc.contains("adult vanilla polar bears panic for broader environmental causes such as freeze"));
        assertTrue(doc.contains("`LegacyCavenicBearPanicGoal extends PanicGoal`"));
        assertTrue(doc.contains("`shouldPanic()`"));
        assertTrue(doc.contains("`return CavenicBear.this.isOnFire();`"));
        assertTrue(doc.contains("panic is burning-only"));
        assertTrue(doc.contains("panic speed stays at `2.0D`"));
        assertTrue(doc.contains("movement/pathfinding remains on the vanilla `PanicGoal` base"));
        assertTrue(doc.contains("Hostile targeting remains unchanged."));
        assertTrue(doc.contains("Melee behavior remains unchanged."));
        assertTrue(doc.contains("The remaining passive/movement AI boundary is now documented separately in `docs/cavenic-bear-passive-ai-equivalent-or-deferred.md`."));
        assertTrue(doc.contains("Anger behavior remains unchanged."));
        assertTrue(doc.contains("Natural spawning remains unchanged."));
        assertTrue(doc.contains("Max spawn cluster size remains unchanged."));
        assertTrue(doc.contains("Vanilla polar bear loot baseline remains unchanged."));
        assertTrue(doc.contains("Source-confirmed bear custom loot/orb-drop absence remains unchanged."));
        assertTrue(doc.contains("Fall/fire damage behavior remains unchanged."));
        assertTrue(doc.contains("cavenic bear legacy panic-goal registration smoke"));
        assertTrue(doc.contains("cavenic bear legacy burning-only panic-trigger smoke"));
        assertTrue(doc.contains("cavenic bear freeze panic rejection smoke"));
        assertTrue(doc.contains("actual long-running panic/pathfinding feel remains manual"));
    }

    @Test
    void cavenicBearPassiveAiBoundaryDocStatesLegacyReferencesComparisonAndNoCodeNeededBoundary() throws IOException {
        String doc = Files.readString(CAVENIC_BEAR_PASSIVE_AI_EQUIVALENT_OR_DEFERRED);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicBear`"));
        assertTrue(doc.contains("`initEntityAI()`"));
        assertTrue(doc.contains("does not call `super.initEntityAI()`"));
        assertTrue(doc.contains("`EntityAISwimming(this)`"));
        assertTrue(doc.contains("`EntityAIFollowParent(this, 1.25D)`"));
        assertTrue(doc.contains("`EntityAIWander(this, 1.0D)`"));
        assertTrue(doc.contains("`EntityAIWatchClosest(this, EntityPlayer.class, 6.0F)`"));
        assertTrue(doc.contains("`EntityAILookIdle(this)`"));
        assertTrue(doc.contains("`FloatGoal(this)`"));
        assertTrue(doc.contains("`FollowParentGoal(this, 1.25)`"));
        assertTrue(doc.contains("`RandomStrollGoal(this, 1.0)`"));
        assertTrue(doc.contains("`LookAtPlayerGoal(this, Player.class, 6.0F)`"));
        assertTrue(doc.contains("`RandomLookAroundGoal(this)`"));
        assertTrue(doc.contains("equivalent enough"));
        assertTrue(doc.contains("No gameplay code is added in this slice."));
        assertTrue(doc.contains("`super.registerGoals()`"));
        assertTrue(doc.contains("hostile targeting remains unchanged"));
        assertTrue(doc.contains("melee behavior remains unchanged"));
        assertTrue(doc.contains("panic behavior remains unchanged"));
        assertTrue(doc.contains("fall/fire damage behavior remains unchanged"));
        assertTrue(doc.contains("vanilla polar bear loot baseline remains unchanged"));
        assertTrue(doc.contains("actual long-running passive pathfinding feel remains manual"));
    }

    @Test
    void runtimeSmokeDocMentionsCavenicBearCoverageLootBoundaryDamageTargetingMeleePanicAndPassiveAiBoundary() throws IOException {
        String doc = Files.readString(RUNTIME_SMOKE);

        assertTrue(doc.contains("cavenic bear runtime registry id"));
        assertTrue(doc.contains("cavenic bear attribute registration smoke"));
        assertTrue(doc.contains("cavenic bear runtime entity spawn smoke"));
        assertTrue(doc.contains("cavenic bear vanilla polar bear loot-table baseline smoke"));
        assertTrue(doc.contains("cavenic bear explicit no-custom-loot boundary from the inspected legacy source"));
        assertTrue(doc.contains("cavenic bear spawn egg resolution"));
        assertTrue(doc.contains("cavenic bear spawn egg entity-creation smoke"));
        assertTrue(doc.contains("cavenic bear natural spawn placement registration"));
        assertTrue(doc.contains("cavenic bear CAVERN-only spawn predicate smoke"));
        assertTrue(doc.contains("cavenic bear biome modifier registry smoke"));
        assertTrue(doc.contains("cavenic bear biome tag resolution"));
        assertTrue(doc.contains("cavenic bear legacy fall-damage reduction smoke"));
        assertTrue(doc.contains("cavenic bear legacy fire-damage immunity smoke"));
        assertTrue(doc.contains("cavenic bear generic-damage baseline smoke"));
        assertTrue(doc.contains("cavenic bear legacy hostile player-target goal smoke"));
        assertTrue(doc.contains("cavenic bear legacy hurt-by-target retaliation smoke"));
        assertTrue(doc.contains("cavenic bear non-player target baseline smoke"));
        assertTrue(doc.contains("cavenic bear legacy melee-goal registration smoke"));
        assertTrue(doc.contains("cavenic bear legacy melee reach smoke"));
        assertTrue(doc.contains("cavenic bear legacy standing-warning threshold smoke"));
        assertTrue(doc.contains("cavenic bear legacy panic-goal registration smoke"));
        assertTrue(doc.contains("cavenic bear legacy burning-only panic-trigger smoke"));
        assertTrue(doc.contains("cavenic bear freeze panic rejection smoke"));
        assertTrue(doc.contains("cavenic bear explicit passive/movement AI equivalence boundary against the inherited modern polar bear base"));
        assertTrue(doc.contains("actual Cavenic Bear renderer/model visual feel"));
        assertTrue(doc.contains("actual long-run Cavenic Bear drop-rate balance, although the current source inspection found no direct custom bear loot branch beyond the vanilla polar bear baseline"));
        assertTrue(doc.contains("actual long-running Cavenic Bear fire/lava gameplay feel"));
        assertTrue(doc.contains("actual long-running Cavenic Bear melee/pathfinding feel"));
        assertTrue(doc.contains("actual long-running Cavenic Bear panic/pathfinding feel"));
        assertTrue(doc.contains("actual long-running Cavenic Bear passive follow/wander/look pathfinding feel"));
        assertTrue(doc.contains("actual Cavenic Bear anger/pathfinding feel"));
        assertTrue(doc.contains("actual long-run Cavenic Bear population balance inside CAVERN"));
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
