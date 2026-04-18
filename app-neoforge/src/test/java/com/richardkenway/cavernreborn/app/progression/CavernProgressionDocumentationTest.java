package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavernProgressionDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path PROGRESSION_BASELINE = resolveProjectFile("docs", "progression-baseline.md");

    @Test
    void readmeDocumentsCurrentProgressionBaselineAndInspectionPath() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("minimal server-side `CAVERN` progression shell"));
        assertTrue(readme.contains("first player-facing progression layer"));
        assertTrue(readme.contains("CavernProgressionPolicy.java"));
        assertTrue(readme.contains("/cavern progression"));
        assertTrue(readme.contains("/cavern rank"));
        assertTrue(readme.contains("Miner's Insight"));
        assertTrue(readme.contains("docs/progression-baseline.md"));
        assertTrue(readme.contains("uncanceled non-creative player block breaks"));
    }

    @Test
    void progressionBaselineDocCallsOutInputsPersistenceAndChecklist() throws IOException {
        String doc = Files.readString(PROGRESSION_BASELINE);

        assertTrue(doc.contains("BlockEvent.BreakEvent"));
        assertTrue(doc.contains("cavernreborn_control_plane"));
        assertTrue(doc.contains("CavernMiningProgressionEvents.java"));
        assertTrue(doc.contains("CavernProgressionCommands.java"));
        assertTrue(doc.contains("CavernProgressionUnlock.java"));
        assertTrue(doc.contains("Miner's Insight"));
        assertTrue(doc.contains("apprentice"));
        assertTrue(doc.contains("journeyman"));
        assertTrue(doc.contains("veteran"));
        assertTrue(doc.contains("master"));
        assertTrue(doc.contains("Mine the same ore outside `CAVERN`"));
        assertTrue(doc.contains("Restart the server"));
        assertTrue(doc.contains("/cavern rank"));
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
