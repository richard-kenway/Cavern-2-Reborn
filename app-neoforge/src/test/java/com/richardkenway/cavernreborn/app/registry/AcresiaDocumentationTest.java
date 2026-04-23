package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class AcresiaDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path ACRESIA_MVP = resolveProjectFile("docs", "acresia-crop-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path CAVERN_ORE_PARITY = resolveProjectFile("docs", "cavern-ore-content-parity.md");

    @Test
    void readmeMentionsAcresiaMvpAndItsBoundedIdentity() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Acresia Crop & Food MVP"));
        assertTrue(readme.contains("docs/acresia-crop-mvp.md"));
        assertTrue(readme.contains("renewable cave snack crop"));
        assertTrue(readme.contains("full farming system"));
    }

    @Test
    void acresiaDocStatesScopeHarvestWorldgenAndProvenance() throws IOException {
        String doc = Files.readString(ACRESIA_MVP);

        assertTrue(doc.contains("first bounded `Acresia` crop-and-food slice"));
        assertTrue(doc.contains("`cavernreborn:acresia`"));
        assertTrue(doc.contains("`cavernreborn:acresia_seeds`"));
        assertTrue(doc.contains("`cavernreborn:acresia_fruits`"));
        assertTrue(doc.contains("ages `0..4`"));
        assertTrue(doc.contains("`acresia_seeds` plants the crop"));
        assertTrue(doc.contains("`acresia_fruits` is edible"));
        assertTrue(doc.contains("shears for bounded extra fruit and a regrowth reset"));
        assertTrue(doc.contains("resets the crop to age `2`"));
        assertTrue(doc.contains("small `lush_grotto` patches"));
        assertTrue(doc.contains("No mob AI"));
        assertTrue(doc.contains("No advancement or economy integration"));
        assertTrue(doc.contains("No new progression unlock"));
        assertTrue(doc.contains("No GUI, packets or client-side farming systems"));
        assertTrue(doc.contains("NeoForge GameTest runtime smoke"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void relatedDocsMentionAcresiaRuntimeCoverageAndNonEquipmentFollowUp() throws IOException {
        String runtimeSmoke = Files.readString(RUNTIME_SMOKE);
        String oreParity = Files.readString(CAVERN_ORE_PARITY);

        assertTrue(runtimeSmoke.contains("acresia runtime registry ids"));
        assertTrue(runtimeSmoke.contains("acresia seed planting smoke"));
        assertTrue(runtimeSmoke.contains("acresia mature normal harvest smoke"));
        assertTrue(runtimeSmoke.contains("acresia shear-harvest regrowth smoke"));
        assertTrue(runtimeSmoke.contains("acresia edible fruit wiring"));
        assertTrue(runtimeSmoke.contains("acresia worldgen configured/placed key resolution"));
        assertTrue(runtimeSmoke.contains("actual Acresia in-world farming feel and growth cadence"));
        assertTrue(oreParity.contains("docs/acresia-crop-mvp.md"));
        assertTrue(oreParity.contains("later non-equipment cave-content follow-ups"));
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
