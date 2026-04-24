package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicBowDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_BOW = resolveProjectFile("docs", "cavenic-bow-baseline-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsCavenicBowBaselineSlice() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Bow Baseline MVP"));
        assertTrue(readme.contains("docs/cavenic-bow-baseline-mvp.md"));
        assertTrue(readme.contains("cavenic_bow"));
        assertTrue(readme.contains("legacy mode cycling, custom arrows"));
    }

    @Test
    void cavenicBowDocStatesLegacyReferencesBoundedScopeAndProvenance() throws IOException {
        String doc = Files.readString(CAVENIC_BOW);

        assertTrue(doc.contains("`cavernreborn:cavenic_bow`"));
        assertTrue(doc.contains("Legacy `cavern.item.ItemBowCavenic`"));
        assertTrue(doc.contains("`EntityRapidArrow`"));
        assertTrue(doc.contains("`EntityTorchArrow`"));
        assertTrue(doc.contains("vanilla bow baseline"));
        assertTrue(doc.contains("does not repair with `minecraft:stick`"));
        assertTrue(doc.contains("Legacy custom bow behavior remains follow-up work"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void runtimeSmokeMentionsCavenicBowCoverageAndBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("cavenic bow runtime registry id"));
        assertTrue(runtimeSmoke.contains("cavenic bow max-damage and damageable-item smoke"));
        assertTrue(runtimeSmoke.contains("cavenic bow repairability with `cavernreborn:cavenic_orb`"));
        assertTrue(runtimeSmoke.contains("cavenic bow recipe manager resolution"));
        assertTrue(runtimeSmoke.contains("legacy Cavenic Bow modes and custom projectile behavior"));
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
