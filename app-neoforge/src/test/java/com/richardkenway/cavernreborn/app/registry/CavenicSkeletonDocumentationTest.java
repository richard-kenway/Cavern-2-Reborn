package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicSkeletonDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_SKELETON_BASELINE_MVP = resolveProjectFile("docs", "cavenic-skeleton-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicSkeletonBaselineFollowUpAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Skeleton Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-skeleton-baseline-mvp.md"));
        assertTrue(readme.contains("cavenic_skeleton"));
        assertTrue(readme.contains("second direct Cavenic mob foundation"));
    }

    @Test
    void cavenicSkeletonBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_SKELETON_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_skeleton`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_skeleton_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicSkeleton`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicSkeleton`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `Skeleton`"));
        assertTrue(doc.contains("max health: `40.0`"));
        assertTrue(doc.contains("movement speed: `0.2`"));
        assertTrue(doc.contains("follow range: `21.0`"));
        assertTrue(doc.contains("knockback resistance: `1.0`"));
        assertTrue(doc.contains("attack damage intentionally stays on the vanilla skeleton baseline"));
        assertTrue(doc.contains("XP reward is pinned to `13`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0xDDDDDD`"));
        assertTrue(doc.contains("reuses the vanilla skeleton renderer/model path"));
        assertTrue(doc.contains("reuses the vanilla skeleton loot table as its base drop source"));
        assertTrue(doc.contains("Legacy `EntityCavenicSkeleton` also added a `1/5` `cavenic_orb` drop"));
        assertTrue(doc.contains("Legacy `EntityCavenicSkeleton` also included the same fall/fire damage behavior"));
        assertTrue(doc.contains("Natural spawning was intentionally left out of scope"));
        assertTrue(doc.contains("Custom AI and custom projectile behavior remain out of scope"));
        assertTrue(doc.contains("The original legacy visual/audio asset pack was distributed under CC-BY-NC 4.0"));
    }

    @Test
    void runtimeSmokeMentionsCavenicSkeletonCoverageAndBoundaries() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic skeleton runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic skeleton attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic skeleton hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic skeleton spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic skeleton spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Skeleton renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("cavenic skeleton natural spawning is intentionally not covered in this baseline slice"));
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
