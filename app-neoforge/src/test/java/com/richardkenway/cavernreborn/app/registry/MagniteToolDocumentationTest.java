package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class MagniteToolDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path MAGNITE_TOOL_DOC = resolveProjectFile("docs", "magnite-tool-mvp.md");
    private static final Path CAVERN_ORE_CONTENT_DOC = resolveProjectFile("docs", "cavern-ore-content-parity.md");
    private static final Path AQUAMARINE_TOOL_DOC = resolveProjectFile("docs", "aquamarine-tool-mvp.md");
    private static final Path HEXCITE_TOOL_DOC = resolveProjectFile("docs", "hexcite-tool-parity.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void readmeMentionsMagniteToolSetMvp() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Magnite Tool Set MVP"));
        assertTrue(readme.contains("docs/magnite-tool-mvp.md"));
        assertTrue(readme.contains("brittle/high-speed specialist"));
        assertTrue(readme.contains("Magnite Armor MVP"));
    }

    @Test
    void magniteToolDocsDescribeBoundedScopeAndRuntimeCoverage() throws IOException {
        String doc = Files.readString(MAGNITE_TOOL_DOC);
        String oreContentDoc = Files.readString(CAVERN_ORE_CONTENT_DOC);
        String aquamarineDoc = Files.readString(AQUAMARINE_TOOL_DOC);
        String hexciteDoc = Files.readString(HEXCITE_TOOL_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(doc.contains("bounded magnite tool slice"));
        assertTrue(doc.contains("magnite_sword"));
        assertTrue(doc.contains("magnite_pickaxe"));
        assertTrue(doc.contains("magnite_axe"));
        assertTrue(doc.contains("magnite_shovel"));
        assertTrue(doc.contains("repairable with `magnite_ingot`"));
        assertTrue(doc.contains("brittle and low-durability"));
        assertTrue(doc.contains("very fast and overclocked"));
        assertTrue(doc.contains("specialist side-grade"));
        assertTrue(doc.contains("No magnite hoe"));
        assertTrue(doc.contains("docs/magnite-armor-mvp.md"));
        assertTrue(doc.contains("No Mining Assist changes"));
        assertTrue(doc.contains("No Miner's Orb changes"));
        assertTrue(doc.contains("No Ore Compass changes"));
        assertTrue(doc.contains("CC-BY-NC 4.0"));

        assertTrue(oreContentDoc.contains("docs/magnite-tool-mvp.md"));
        assertTrue(oreContentDoc.contains("magnite now also has a bounded tool follow-up"));
        assertTrue(oreContentDoc.contains("docs/magnite-armor-mvp.md"));
        assertTrue(aquamarineDoc.contains("docs/magnite-tool-mvp.md"));
        assertTrue(aquamarineDoc.contains("utility-oriented"));
        assertTrue(hexciteDoc.contains("docs/magnite-tool-mvp.md"));
        assertTrue(hexciteDoc.contains("docs/magnite-armor-mvp.md"));
        assertTrue(hexciteDoc.contains("main combat/mining equipment line"));
        assertTrue(runtimeSmokeDoc.contains("magnite tool runtime registry ids"));
        assertTrue(runtimeSmokeDoc.contains("magnite tool repairability with `cavernreborn:magnite_ingot`"));
        assertTrue(runtimeSmokeDoc.contains("magnite tool enchantment applicability"));
        assertTrue(runtimeSmokeDoc.contains("magnite brittle max-damage/runtime smoke"));
        assertTrue(runtimeSmokeDoc.contains("magnite no-unintended-Mining-Assist runtime smoke"));
        assertTrue(runtimeSmokeDoc.contains("actual brittle magnite mining feel"));
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
