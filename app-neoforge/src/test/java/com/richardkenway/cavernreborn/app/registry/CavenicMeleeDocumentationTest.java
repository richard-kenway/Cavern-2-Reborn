package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicMeleeDocumentationTest {
    private static final Path CAVENIC_MELEE_MVP = resolveProjectFile("docs", "cavenic-melee-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void cavenicMeleeDocStatesLegacyMappingBoundedScopeAndTests() throws IOException {
        String doc = Files.readString(CAVENIC_MELEE_MVP);

        assertTrue(doc.contains("`cavernreborn:cavenic_sword`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_axe`"));
        assertTrue(doc.contains("Legacy `ItemSwordCavenic`"));
        assertTrue(doc.contains("Legacy `ItemAxeCavenic`"));
        assertTrue(doc.contains("server-side reset"));
        assertTrue(doc.contains("cap of six extra hostile targets"));
        assertTrue(doc.contains("docs/cavenic-bow-baseline-mvp.md"));
        assertTrue(doc.contains("NeoForge GameTest runtime smoke"));
    }

    @Test
    void runtimeSmokeMentionsCavenicMeleeCoverageAndVisualBoundary() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);

        assertTrue(runtimeSmoke.contains("bounded Cavenic Melee MVP"));
        assertTrue(runtimeSmoke.contains("cavenic sword and cavenic axe runtime registry ids"));
        assertTrue(runtimeSmoke.contains("bounded cavenic sword cooldown-reset runtime smoke"));
        assertTrue(runtimeSmoke.contains("bounded cavenic axe hostile-nearby-target runtime smoke"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Sword and Cavenic Axe visual/client combat feel"));
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
