package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class HexciteArmorDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path SPECIAL_ORE_PARITY = resolveProjectFile("docs", "cavern-special-ore-content-parity.md");
    private static final Path HEXCITE_TOOL_PARITY = resolveProjectFile("docs", "hexcite-tool-parity.md");
    private static final Path HEXCITE_ARMOR_MVP = resolveProjectFile("docs", "hexcite-armor-mvp.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsHexciteArmorMvp() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Hexcite Armor MVP"));
        assertTrue(readme.contains("hexcite_helmet"));
        assertTrue(readme.contains("hexcite_chestplate"));
        assertTrue(readme.contains("hexcite_leggings"));
        assertTrue(readme.contains("hexcite_boots"));
        assertTrue(readme.contains("docs/hexcite-armor-mvp.md"));
        assertTrue(readme.contains("does not add a set bonus"));
    }

    @Test
    void hexciteArmorDocStatesBoundedScopeRuntimeCoverageAndProvenance() throws IOException {
        String doc = Files.readString(HEXCITE_ARMOR_MVP);

        assertTrue(doc.contains("first bounded `hexcite` armor slice"));
        assertTrue(doc.contains("hexcite_helmet"));
        assertTrue(doc.contains("hexcite_chestplate"));
        assertTrue(doc.contains("hexcite_leggings"));
        assertTrue(doc.contains("hexcite_boots"));
        assertTrue(doc.contains("repair ingredient is `cavernreborn:hexcite`"));
        assertTrue(doc.contains("not netherite-tier"));
        assertTrue(doc.contains("No special set bonus"));
        assertTrue(doc.contains("No passive potion effects"));
        assertTrue(doc.contains("NeoForge GameTest runtime smoke"));
        assertTrue(doc.contains("textures/models/armor/hexcite_layer_1.png"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void relatedDocsLinkArmorSliceAndRuntimeCoverage() throws IOException {
        String specialOreDoc = Files.readString(SPECIAL_ORE_PARITY);
        String toolDoc = Files.readString(HEXCITE_TOOL_PARITY);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE);

        assertTrue(specialOreDoc.contains("docs/hexcite-armor-mvp.md"));
        assertTrue(toolDoc.contains("docs/hexcite-armor-mvp.md"));
        assertTrue(toolDoc.contains("tool-only"));
        assertTrue(runtimeSmokeDoc.contains("hexcite armor runtime registry ids"));
        assertTrue(runtimeSmokeDoc.contains("hexcite armor repairability with `cavernreborn:hexcite`"));
        assertTrue(runtimeSmokeDoc.contains("hexcite armor durability and slot/equippable runtime behavior"));
        assertTrue(runtimeSmokeDoc.contains("hexcite armor enchantment applicability"));
        assertTrue(runtimeSmokeDoc.contains("actual worn hexcite armor appearance"));
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
