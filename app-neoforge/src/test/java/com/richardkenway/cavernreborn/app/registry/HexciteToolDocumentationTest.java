package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class HexciteToolDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path SPECIAL_ORE_PARITY = resolveProjectFile("docs", "cavern-special-ore-content-parity.md");
    private static final Path HEXCITE_TOOL_PARITY = resolveProjectFile("docs", "hexcite-tool-parity.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsHexciteToolSetMvp() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("first hexcite tool-set MVP"));
        assertTrue(readme.contains("hexcite_pickaxe"));
        assertTrue(readme.contains("hexcite_axe"));
        assertTrue(readme.contains("hexcite_shovel"));
        assertTrue(readme.contains("hexcite_hoe"));
        assertTrue(readme.contains("hexcite_sword"));
        assertTrue(readme.contains("docs/hexcite-tool-parity.md"));
    }

    @Test
    void hexciteToolParityDocStatesBoundedScopeAndAssetProvenance() throws IOException {
        String doc = Files.readString(HEXCITE_TOOL_PARITY);

        assertTrue(doc.contains("hexcite tool set"));
        assertTrue(doc.contains("Hexcite Pickaxe"));
        assertTrue(doc.contains("Hexcite Axe"));
        assertTrue(doc.contains("Hexcite Shovel"));
        assertTrue(doc.contains("Hexcite Hoe"));
        assertTrue(doc.contains("Hexcite Sword"));
        assertTrue(doc.contains("armor remains out of scope"));
        assertTrue(doc.contains("No special tool abilities"));
        assertTrue(doc.contains("cavernreborn:hexcite"));
        assertTrue(doc.contains("not netherite-tier"));
        assertTrue(doc.contains("docs/cavern-special-ore-content-parity.md"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));
    }

    @Test
    void relatedDocsLinkHexciteToolSliceAndRuntimeCoverage() throws IOException {
        String specialOreDoc = Files.readString(SPECIAL_ORE_PARITY);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE);

        assertTrue(specialOreDoc.contains("docs/hexcite-tool-parity.md"));
        assertTrue(specialOreDoc.contains("Armor remains out of scope"));
        assertTrue(specialOreDoc.contains("No special tool abilities"));
        assertTrue(runtimeSmokeDoc.contains("hexcite tool runtime registry ids"));
        assertTrue(runtimeSmokeDoc.contains("hexcite pickaxe normal mining path for special ores"));
        assertTrue(runtimeSmokeDoc.contains("hexcite pickaxe Silk Touch path for hexcite ore"));
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
