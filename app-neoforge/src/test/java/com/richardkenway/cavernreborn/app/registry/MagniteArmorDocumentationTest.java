package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class MagniteArmorDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CAVERN_ORE_PARITY = resolveProjectFile("docs", "cavern-ore-content-parity.md");
    private static final Path MAGNITE_TOOL_MVP = resolveProjectFile("docs", "magnite-tool-mvp.md");
    private static final Path MAGNITE_ARMOR_MVP = resolveProjectFile("docs", "magnite-armor-mvp.md");
    private static final Path HEXCITE_TOOL_PARITY = resolveProjectFile("docs", "hexcite-tool-parity.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsMagniteArmorMvp() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Magnite Armor MVP"));
        assertTrue(readme.contains("docs/magnite-armor-mvp.md"));
        assertTrue(readme.contains("brittle/enchant-friendly side-grade"));
        assertTrue(readme.contains("no set bonus"));
        assertTrue(readme.contains("hexcite remains the main armor/combat line"));
    }

    @Test
    void magniteArmorDocStatesBoundedScopeRuntimeCoverageAndPlaceholderProvenance() throws IOException {
        String doc = Files.readString(MAGNITE_ARMOR_MVP);

        assertTrue(doc.contains("first bounded magnite armor slice"));
        assertTrue(doc.contains("magnite_helmet"));
        assertTrue(doc.contains("magnite_chestplate"));
        assertTrue(doc.contains("magnite_leggings"));
        assertTrue(doc.contains("magnite_boots"));
        assertTrue(doc.contains("repairable with `magnite_ingot`"));
        assertTrue(doc.contains("brittle / lower-durability"));
        assertTrue(doc.contains("enchant-friendly"));
        assertTrue(doc.contains("side-grade, not the main armor line"));
        assertTrue(doc.contains("No magnite set bonus"));
        assertTrue(doc.contains("No passive potion effects"));
        assertTrue(doc.contains("NeoForge GameTest runtime smoke"));
        assertTrue(doc.contains("placeholder"));
        assertTrue(doc.contains("hexcite armor art"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void relatedDocsLinkMagniteArmorSliceAndRuntimeCoverage() throws IOException {
        String oreDoc = Files.readString(CAVERN_ORE_PARITY);
        String toolDoc = Files.readString(MAGNITE_TOOL_MVP);
        String hexciteToolDoc = Files.readString(HEXCITE_TOOL_PARITY);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE);

        assertTrue(oreDoc.contains("docs/magnite-armor-mvp.md"));
        assertTrue(oreDoc.contains("magnite now also has a bounded armor follow-up"));
        assertTrue(toolDoc.contains("docs/magnite-armor-mvp.md"));
        assertTrue(toolDoc.contains("side-grade, not the main combat/mining line"));
        assertTrue(hexciteToolDoc.contains("docs/magnite-armor-mvp.md"));
        assertTrue(hexciteToolDoc.contains("main combat/mining equipment line"));
        assertTrue(runtimeSmokeDoc.contains("magnite armor runtime registry ids"));
        assertTrue(runtimeSmokeDoc.contains("magnite armor repairability with `cavernreborn:magnite_ingot`"));
        assertTrue(runtimeSmokeDoc.contains("magnite armor enchantment applicability"));
        assertTrue(runtimeSmokeDoc.contains("magnite armor durability and slot/equippable runtime behavior"));
        assertTrue(runtimeSmokeDoc.contains("actual worn magnite armor appearance"));
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
