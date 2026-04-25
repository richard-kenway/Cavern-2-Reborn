package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicWitchDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_WITCH_BASELINE_MVP = resolveProjectFile("docs", "cavenic-witch-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicWitchBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Witch Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-witch-baseline-mvp.md"));
        assertTrue(readme.contains("cavenic_witch"));
        assertTrue(readme.contains("fifth direct Cavenic mob foundation"));
        assertTrue(readme.contains("vanilla witch loot baseline"));
        assertTrue(readme.contains("custom potion logic, natural spawning, orb-drop parity and damage-behavior parity remain intentionally out of scope"));
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
        assertTrue(doc.contains("natural spawning intentionally remains out of scope"));
        assertTrue(doc.contains("custom loot and the legacy `1/5` `cavenic_orb` / magic-book drop branch remain out of scope"));
        assertTrue(doc.contains("legacy fall/fire damage behavior remains out of scope"));
        assertTrue(doc.contains("custom potion logic, friendship targeting and same-type projectile immunity remain out of scope"));
        assertTrue(doc.contains("the legacy `getMaxSpawnedInChunk()` Cavenia-specific behavior remains out of scope"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void runtimeSmokeMentionsCavenicWitchBaselineCoverageAndManualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic witch runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic witch attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch vanilla loot-table baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic witch spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic witch spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Witch renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("Cavenic Witch natural spawning, because that bounded follow-up is still out of scope for the current baseline slice"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Witch combat, potion-throw and pathfinding feel"));
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
