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
    private static final Path AI_BOUNDARY_DOC = resolveProjectFile("docs", "crazy-skeleton-ranged-ai-boundary.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazySkeletonBaselineEquipmentLootFollowUpsAndAiBoundaryDocs() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Skeleton Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-skeleton-baseline-mvp.md"));
        assertTrue(readme.contains("docs/crazy-skeleton-cavenic-bow-equipment-mvp.md"));
        assertTrue(readme.contains("docs/crazy-skeleton-loot-mvp.md"));
        assertTrue(readme.contains("docs/crazy-skeleton-ranged-ai-boundary.md"));
        assertTrue(readme.contains("second crazy-variant foundation follow-up"));
        assertTrue(readme.contains("`crazy_skeleton`"));
        assertTrue(readme.contains("vanilla skeleton loot baseline"));
        assertTrue(readme.contains("inherited legacy `1/5` `cavenic_orb` drop is now restored explicitly on top of that same vanilla skeleton loot baseline"));
        assertTrue(readme.contains("legacy `2000.0` max-health request explicitly clamped by the modern `generic.max_health` runtime ceiling of `1024.0`"));
        assertTrue(readme.contains("legacy `EntityAIAttackCavenicBow` path"));
        assertTrue(readme.contains("guaranteed `Cavenic Bow` mainhand, forced `Infinity` enchantment and `1.0F` mainhand drop chance are now restored explicitly"));
        assertTrue(readme.contains("custom ranged AI identity stays intentionally deferred"));
        assertTrue(readme.contains("crazy creeper and crazy spider remain follow-up candidates"));
    }

    @Test
    void crazySkeletonBaselineDocStatesLegacyReferencesRuntimeShapeAndCurrentFollowUpBoundaries() throws IOException {
        String doc = Files.readString(BASELINE_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSkeleton`"));
        assertTrue(doc.contains("`cavern.entity.ai.EntityAIAttackCavenicBow`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("`assets/cavern/textures/entity/crazy_skeleton.png`"));
        assertTrue(doc.contains("`assets/cavern/lang/en_us.lang`"));
        assertTrue(doc.contains("`cavernreborn:crazy_skeleton`"));
        assertTrue(doc.contains("`cavernreborn:crazy_skeleton_spawn_egg`"));
        assertTrue(doc.contains("extends vanilla `Skeleton`"));
        assertTrue(doc.contains("does not extend the current Reborn `CavenicSkeleton` baseline"));
        assertTrue(doc.contains("max health: `2000.0`"));
        assertTrue(doc.contains("generic.max_health"));
        assertTrue(doc.contains("`1024.0`"));
        assertTrue(doc.contains("movement speed: `0.25`"));
        assertTrue(doc.contains("follow range: `22.0`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("Attack damage intentionally stays on the vanilla skeleton baseline"));
        assertTrue(doc.contains("XP reward is pinned to `50`"));
        assertTrue(doc.contains("base color: `0x909090`"));
        assertTrue(doc.contains("spot color: `0xDDDDDD`"));
        assertTrue(doc.contains("reuses the vanilla skeleton renderer/model path"));
        assertTrue(doc.contains("legacy `1.1x` visual scale"));
        assertTrue(doc.contains("vanilla skeleton loot table as its base drop source"));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-skeleton-loot-mvp.md` now restores that inherited orb branch explicitly while keeping the vanilla skeleton loot table as the baseline drop source."));
        assertTrue(doc.contains("Reborn still does not add a custom Crazy Skeleton loot table JSON."));
        assertTrue(doc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(doc.contains("weight `1` and group size `1 / 1`"));
        assertTrue(doc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(doc.contains("does not add a fake `CAVERN` spawn placement or biome modifier"));
        assertTrue(doc.contains("`EntityAIAttackCavenicBow<>(this, 0.99D, 6.0F, 1)`"));
        assertTrue(doc.contains("speed `1.35D`"));
        assertTrue(doc.contains("always equips `CaveItems.CAVENIC_BOW`"));
        assertTrue(doc.contains("forced to carry `Enchantments.INFINITY`"));
        assertTrue(doc.contains("constructor sets mainhand drop chance to `1.0F`"));
        assertTrue(doc.contains("The dedicated follow-up documented in `docs/crazy-skeleton-cavenic-bow-equipment-mvp.md` now restores the guaranteed legacy `Cavenic Bow` + `Infinity` mainhand identity"));
        assertTrue(doc.contains("The custom ranged AI identity is still deferred and documented in `docs/crazy-skeleton-ranged-ai-boundary.md`."));
        assertTrue(doc.contains("inherited `cavenic_orb` drop behavior from `EntityCavenicSkeleton`, now restored explicitly in `docs/crazy-skeleton-loot-mvp.md`"));
        assertTrue(doc.contains("no `EntityAIAttackCavenicBow`"));
        assertTrue(doc.contains("no custom ranged-combat loop"));
        assertTrue(doc.contains("no Cavenic Bow behavior changes"));
        assertTrue(doc.contains("boss bar / sky-darkening behavior"));
        assertTrue(doc.contains("client-only particle trail"));
        assertTrue(doc.contains("Crazy Creeper"));
        assertTrue(doc.contains("Crazy Spider"));
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void crazySkeletonEquipmentDocStatesLegacyEquipmentMappingAndTestCoverage() throws IOException {
        String doc = Files.readString(EQUIPMENT_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSkeleton`"));
        assertTrue(doc.contains("`cavern.entity.ai.EntityAIAttackCavenicBow`"));
        assertTrue(doc.contains("`cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("`cavern.item.CaveItems`"));
        assertTrue(doc.contains("`setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F)`"));
        assertTrue(doc.contains("`setEquipmentBasedOnDifficulty(...)`"));
        assertTrue(doc.contains("`CaveItems.CAVENIC_BOW`"));
        assertTrue(doc.contains("`Enchantments.INFINITY`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_bow`"));
        assertTrue(doc.contains("`populateDefaultEquipmentSlots(...)`"));
        assertTrue(doc.contains("`CrazySkeleton` still extends vanilla `Skeleton`"));
        assertTrue(doc.contains("`Cavenic Bow` behavior remains unchanged"));
        assertTrue(doc.contains("`EntityAIAttackCavenicBow` remains deferred"));
        assertTrue(doc.contains("mainhand drop chance `1.0F`"));
        assertTrue(doc.contains("Infinity enchantment"));
        assertTrue(doc.contains("no natural-spawn changes"));
        assertTrue(doc.contains("no new loot/orb behavior is introduced in this equipment slice; the inherited orb-drop follow-up is documented separately in `docs/crazy-skeleton-loot-mvp.md`"));
        assertTrue(doc.contains("no damage changes"));
    }

    @Test
    void crazySkeletonLootDocStatesInheritedOrbMappingAndTestingBoundaries() throws IOException {
        String doc = Files.readString(LOOT_DOC);

        assertTrue(doc.contains("`cavern.entity.monster.EntityCrazySkeleton`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSkeleton`"));
        assertTrue(doc.contains("`cavern.item.ItemCave`"));
        assertTrue(doc.contains("does not override `dropLoot(...)`"));
        assertTrue(doc.contains("inherits `EntityCavenicSkeleton#dropLoot"));
        assertTrue(doc.contains("`rand.nextInt(5) == 0`"));
        assertTrue(doc.contains("`ItemCave.EnumType.CAVENIC_ORB.getItemStack()`"));
        assertTrue(doc.contains("offset `0.5F`"));
        assertTrue(doc.contains("vanilla skeleton loot table as the baseline drop source"));
        assertTrue(doc.contains("CrazySkeletonLootEvents"));
        assertTrue(doc.contains("CrazySkeletonLootPolicy"));
        assertTrue(doc.contains("`CrazySkeletonLootPolicy.ORB_DROP_ROLL_BOUND = 5`"));
        assertTrue(doc.contains("The winning roll is `0`, so the orb chance remains exactly `1/5`."));
        assertTrue(doc.contains("Looting does not affect the new orb drop."));
        assertTrue(doc.contains("A player kill is not required"));
        assertTrue(doc.contains("guaranteed `Cavenic Bow` equipment do not affect this restored orb drop"));
        assertTrue(doc.contains("Reborn `CrazySkeleton` intentionally extends vanilla `Skeleton`, not Reborn `CavenicSkeleton`."));
        assertTrue(doc.contains("the preserved guaranteed `Cavenic Bow` + `Infinity` equipment hook and `1.0F` mainhand drop chance"));
        assertTrue(doc.contains("continued absence of natural-spawn wiring, damage behavior, boss-event state, particles and custom ranged-AI overrides"));
    }

    @Test
    void crazySkeletonRangedAiBoundaryDocStatesWhyCustomGoalStillRemainsDeferred() throws IOException {
        String doc = Files.readString(AI_BOUNDARY_DOC);

        assertTrue(doc.contains("`EntityAIAttackCavenicBow<>(this, 0.99D, 6.0F, 1)`"));
        assertTrue(doc.contains("melee fallback speed `1.35D`"));
        assertTrue(doc.contains("`setCombatTask()`"));
        assertTrue(doc.contains("when no bow is held, it switches to melee"));
        assertTrue(doc.contains("`ItemBowCavenic`"));
        assertTrue(doc.contains("custom ranged AI remains intentionally deferred"));
        assertTrue(doc.contains("equipment-only follow-up restores the held bow identity without porting the custom goal"));
        assertTrue(doc.contains("Actual ranged combat feel remains manual / follow-up."));
    }

    @Test
    void runtimeSmokeMentionsCrazySkeletonEquipmentCoverageAndAiBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("crazy skeleton runtime registry id"));
        assertTrue(runtimeSmoke.contains("crazy skeleton attribute registration"));
        assertTrue(runtimeSmoke.contains("crazy skeleton hostile runtime spawn"));
        assertTrue(runtimeSmoke.contains("crazy skeleton spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("crazy skeleton spawn egg entity creation"));
        assertTrue(runtimeSmoke.contains("crazy skeleton vanilla skeleton loot-table baseline"));
        assertTrue(runtimeSmoke.contains("crazy skeleton guaranteed cavenic bow equipment smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton Infinity enchantment smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton mainhand drop chance smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton legacy orb-drop event wiring smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton legacy orb-drop deterministic winning/losing roll smoke"));
        assertTrue(runtimeSmoke.contains("crazy skeleton explicit no-natural-spawn baseline boundary"));
        assertTrue(runtimeSmoke.contains("crazy skeleton explicit custom ranged AI / `EntityAIAttackCavenicBow` deferred boundary"));
        assertTrue(runtimeSmoke.contains("actual Crazy Skeleton renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("actual Crazy Skeleton ranged combat feel remains manual / follow-up"));
        assertTrue(runtimeSmoke.contains("Crazy Skeleton natural spawning"));
        assertTrue(runtimeSmoke.contains("Crazy Skeleton custom ranged Cavenic Bow behavior"));
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
