package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CrazySkeletonDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BASELINE_DOC = resolveProjectFile("docs", "crazy-skeleton-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCrazySkeletonBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Crazy Skeleton Baseline MVP"));
        assertTrue(readme.contains("docs/crazy-skeleton-baseline-mvp.md"));
        assertTrue(readme.contains("second crazy-variant foundation follow-up"));
        assertTrue(readme.contains("`crazy_skeleton`"));
        assertTrue(readme.contains("vanilla skeleton loot baseline"));
        assertTrue(readme.contains("legacy `2000.0` max-health request explicitly clamped by the modern `generic.max_health` runtime ceiling of `1024.0`"));
        assertTrue(readme.contains("legacy `EntityAIAttackCavenicBow` path"));
        assertTrue(readme.contains("guaranteed `Cavenic Bow` + `Infinity` equipment path remains intentionally deferred"));
        assertTrue(readme.contains("crazy creeper and crazy spider remain follow-up candidates"));
    }

    @Test
    void crazySkeletonBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
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
        assertTrue(doc.contains("`CaveEntityRegistry.CRAZY_SPAWNS`"));
        assertTrue(doc.contains("weight `1` and group size `1 / 1`"));
        assertTrue(doc.contains("`WorldProviderCavenia#createSpawnCreature(...)`"));
        assertTrue(doc.contains("does not add a fake `CAVERN` spawn placement or biome modifier"));
        assertTrue(doc.contains("`EntityAIAttackCavenicBow<>(this, 0.99D, 6.0F, 1)`"));
        assertTrue(doc.contains("speed `1.35D`"));
        assertTrue(doc.contains("always equips `CaveItems.CAVENIC_BOW`"));
        assertTrue(doc.contains("forced to carry `Enchantments.INFINITY`"));
        assertTrue(doc.contains("no `EntityAIAttackCavenicBow`"));
        assertTrue(doc.contains("no guaranteed `Cavenic Bow`"));
        assertTrue(doc.contains("no guaranteed `Infinity`"));
        assertTrue(doc.contains("boss bar / sky-darkening behavior"));
        assertTrue(doc.contains("client-only particle trail"));
        assertTrue(doc.contains("Crazy Creeper"));
        assertTrue(doc.contains("Crazy Spider"));
        assertTrue(doc.contains("Cavenia"));
    }

    @Test
    void runtimeSmokeMentionsCrazySkeletonCoverageAndBoundaries() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("crazy skeleton runtime registry id"));
        assertTrue(runtimeSmoke.contains("crazy skeleton attribute registration"));
        assertTrue(runtimeSmoke.contains("crazy skeleton hostile runtime spawn"));
        assertTrue(runtimeSmoke.contains("crazy skeleton spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("crazy skeleton spawn egg entity creation"));
        assertTrue(runtimeSmoke.contains("crazy skeleton vanilla skeleton loot-table baseline"));
        assertTrue(runtimeSmoke.contains("crazy skeleton explicit no-natural-spawn baseline boundary"));
        assertTrue(runtimeSmoke.contains("actual Crazy Skeleton renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("Crazy Skeleton natural spawning"));
        assertTrue(runtimeSmoke.contains("Crazy Skeleton custom ranged Cavenic Bow / Infinity equipment behavior"));
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
