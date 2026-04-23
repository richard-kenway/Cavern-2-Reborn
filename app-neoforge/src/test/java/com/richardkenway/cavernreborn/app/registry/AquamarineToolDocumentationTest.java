package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class AquamarineToolDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path AQUAMARINE_TOOL_DOC = resolveProjectFile("docs", "aquamarine-tool-mvp.md");
    private static final Path CAVERN_ORE_CONTENT_DOC = resolveProjectFile("docs", "cavern-ore-content-parity.md");
    private static final Path HEXCITE_TOOL_DOC = resolveProjectFile("docs", "hexcite-tool-parity.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsAquamarineToolMvp() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Aquamarine Utility Tools MVP"));
        assertTrue(readme.contains("docs/aquamarine-tool-mvp.md"));
        assertTrue(readme.contains("aquamarine_pickaxe"));
        assertTrue(readme.contains("underwater break-speed utility only"));
    }

    @Test
    void aquamarineToolDocsDescribeBoundedScope() throws IOException {
        String doc = Files.readString(AQUAMARINE_TOOL_DOC);
        String oreContentDoc = Files.readString(CAVERN_ORE_CONTENT_DOC);
        String hexciteToolDoc = Files.readString(HEXCITE_TOOL_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(doc.contains("bounded aquamarine tool slice"));
        assertTrue(doc.contains("aquamarine_pickaxe"));
        assertTrue(doc.contains("aquamarine_axe"));
        assertTrue(doc.contains("aquamarine_shovel"));
        assertTrue(doc.contains("repairable with aquamarine"));
        assertTrue(doc.contains("iron-adjacent"));
        assertTrue(doc.contains("underwater break-speed only"));
        assertTrue(doc.contains("utility-oriented"));
        assertTrue(doc.contains("docs/magnite-tool-mvp.md"));
        assertTrue(doc.contains("No water breathing"));
        assertTrue(doc.contains("No swim-speed bonus"));
        assertTrue(doc.contains("No progression changes"));
        assertTrue(doc.contains("No aquamarine sword/hoe/armor"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));

        assertTrue(oreContentDoc.contains("docs/aquamarine-tool-mvp.md"));
        assertTrue(oreContentDoc.contains("aquamarine now also has a bounded tool follow-up"));
        assertTrue(oreContentDoc.contains("docs/magnite-tool-mvp.md"));
        assertTrue(hexciteToolDoc.contains("docs/aquamarine-tool-mvp.md"));
        assertTrue(hexciteToolDoc.contains("main combat/mining equipment line"));
        assertTrue(runtimeSmokeDoc.contains("aquamarine tool runtime registry ids"));
        assertTrue(runtimeSmokeDoc.contains("aquamarine tool repairability with `cavernreborn:aquamarine`"));
        assertTrue(runtimeSmokeDoc.contains("aquamarine underwater utility policy/runtime smoke"));
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
