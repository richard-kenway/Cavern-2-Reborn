package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicCreeperDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_CREEPER_BASELINE_MVP = resolveProjectFile("docs", "cavenic-creeper-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicCreeperBaselineAndDocPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Creeper Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-creeper-baseline-mvp.md"));
        assertTrue(readme.contains("cavenic_creeper"));
        assertTrue(readme.contains("third direct Cavenic mob foundation"));
        assertTrue(readme.contains("custom explosion behavior"));
    }

    @Test
    void cavenicCreeperBaselineDocStatesLegacyReferencesRuntimeShapeAndBoundaries() throws IOException {
        String doc = Files.readString(CAVENIC_CREEPER_BASELINE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_creeper`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_creeper_spawn_egg`"));
        assertTrue(doc.contains("`cavern.entity.monster.EntityCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.client.renderer.RenderCavenicCreeper`"));
        assertTrue(doc.contains("`cavern.entity.CaveEntityRegistry`"));
        assertTrue(doc.contains("extends vanilla `Creeper`"));
        assertTrue(doc.contains("max health: `30.0`"));
        assertTrue(doc.contains("movement speed: `0.2`"));
        assertTrue(doc.contains("knockback resistance: `0.85`"));
        assertTrue(doc.contains("follow range intentionally stays on the vanilla creeper baseline"));
        assertTrue(doc.contains("XP reward is pinned to `13`"));
        assertTrue(doc.contains("base color: `0xAAAAAA`"));
        assertTrue(doc.contains("spot color: `0x2E8B57`"));
        assertTrue(doc.contains("reuses the vanilla creeper renderer/model path"));
        assertTrue(doc.contains("reuses the vanilla creeper loot table as its base drop source"));
        assertTrue(doc.contains("`fuseTime = 15`"));
        assertTrue(doc.contains("`explosionRadius = 5`"));
        assertTrue(doc.contains("Custom explosion behavior remains out of scope"));
        assertTrue(doc.contains("natural spawning intentionally remains out of scope"));
        assertTrue(doc.contains("custom loot and the legacy `1/5` `cavenic_orb` drop remain out of scope"));
        assertTrue(doc.contains("fall/fire damage behavior remains out of scope"));
        assertTrue(doc.contains("custom AI remains out of scope"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void runtimeSmokeMentionsCavenicCreeperCoverageAndManualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic creeper runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic creeper attribute registration smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper hostile runtime spawn smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper vanilla loot-table baseline smoke"));
        assertTrue(runtimeSmoke.contains("cavenic creeper spawn egg resolution"));
        assertTrue(runtimeSmoke.contains("cavenic creeper spawn egg entity-creation smoke"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Creeper renderer/model visual feel"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Creeper fuse and explosion feel"));
        assertTrue(runtimeSmoke.contains("natural spawning for Cavenic Creeper in this baseline slice"));
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
