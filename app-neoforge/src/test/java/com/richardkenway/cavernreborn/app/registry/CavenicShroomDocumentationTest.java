package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavenicShroomDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVENIC_MVP = resolveProjectFile("docs", "cavenic-shroom-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path CAVERN_ORE_PARITY = resolveProjectFile("docs", "cavern-ore-content-parity.md");
    private static final Path ACRESIA_MVP = resolveProjectFile("docs", "acresia-crop-mvp.md");

    @Test
    void readmeMentionsCavenicMvpAndItsBoundedIdentity() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Cavenic Shroom & Orb MVP"));
        assertTrue(readme.contains("docs/cavenic-shroom-mvp.md"));
        assertTrue(readme.contains("hazardous flora"));
        assertTrue(readme.contains("cavenic_orb"));
        assertTrue(readme.contains("cavenic weapons, mobs or Cavenia"));
    }

    @Test
    void cavenicDocStatesScopeBridgeRuntimeBoundaryAndProvenance() throws IOException {
        String doc = Files.readString(CAVENIC_MVP);

        assertTrue(doc.contains("first bounded cavenic content slice"));
        assertTrue(doc.contains("`cavernreborn:cavenic_shroom`"));
        assertTrue(doc.contains("`cavernreborn:cavenic_orb`"));
        assertTrue(doc.contains("brief nausea"));
        assertTrue(doc.contains("spreads slowly"));
        assertTrue(doc.contains("`stone_depths`"));
        assertTrue(doc.contains("bounded modern Reborn bridge"));
        assertTrue(doc.contains("No cavenic weapons yet"));
        assertTrue(doc.contains("No cavenic mobs yet"));
        assertTrue(doc.contains("No Cavenia yet"));
        assertTrue(doc.contains("NeoForge GameTest runtime smoke"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void relatedDocsMentionCavenicCoverageAndSeparateHazardousSlice() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);
        String oreParity = Files.readString(CAVERN_ORE_PARITY);
        String acresiaDoc = Files.readString(ACRESIA_MVP);

        assertTrue(runtimeSmoke.contains("cavenic shroom runtime registry ids"));
        assertTrue(runtimeSmoke.contains("cavenic shroom bounded collision nausea"));
        assertTrue(runtimeSmoke.contains("cavenic shroom bounded shear-harvest helper wiring"));
        assertTrue(runtimeSmoke.contains("cavenic orb drop policy/runtime smoke"));
        assertTrue(runtimeSmoke.contains("cavenic shroom worldgen configured/placed key resolution"));
        assertTrue(runtimeSmoke.contains("actual Cavenic Shroom hazard feel, sparse spread feel and harvest feel"));
        assertTrue(oreParity.contains("docs/cavenic-shroom-mvp.md"));
        assertTrue(acresiaDoc.contains("docs/cavenic-shroom-mvp.md"));
        assertTrue(acresiaDoc.contains("hazardous flora slice"));
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
