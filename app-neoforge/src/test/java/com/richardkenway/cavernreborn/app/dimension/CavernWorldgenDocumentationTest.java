package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavernWorldgenDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path WORLDGEN_BASELINE = resolveProjectFile("docs", "worldgen-baseline.md");
    private static final Path WORLDGEN_RUNTIME_NOTE = resolveProjectFile("docs", "worldgen-runtime-validation-2026-04-18.md");

    @Test
    void readmeDocumentsCurrentWorldgenBaselineAndSourceOfTruth() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("stone_depths"));
        assertTrue(readme.contains("lush_grotto"));
        assertTrue(readme.contains("dripstone_grotto"));
        assertTrue(readme.contains("highland_hollows"));
        assertTrue(readme.contains("ore veins"));
        assertTrue(readme.contains("mineshafts"));
        assertTrue(readme.contains("docs/worldgen-baseline.md"));
        assertTrue(readme.contains("docs/worldgen-runtime-validation-2026-04-18.md"));
        assertTrue(readme.contains("dedicated-server validation note"));
        assertTrue(readme.contains("setworldspawn 0 70 0"));
    }

    @Test
    void worldgenBaselineDocCallsOutCompromisesAndChecklist() throws IOException {
        String doc = Files.readString(WORLDGEN_BASELINE);

        assertTrue(doc.contains("not a claim of full legacy parity"));
        assertTrue(doc.contains("app-neoforge/src/generated/resources"));
        assertTrue(doc.contains("mineshaft.json"));
        assertTrue(doc.contains("dense coal and iron"));
        assertTrue(doc.contains("dripstone_grotto"));
        assertTrue(doc.contains("highland_hollows"));
        assertTrue(doc.contains("docs/worldgen-runtime-validation-2026-04-18.md"));
        assertTrue(doc.contains("generated resources resolve from the runtime classpath"));
        assertTrue(doc.contains("setworldspawn 0 70 0"));
        assertTrue(doc.contains("historical log"));
        assertTrue(doc.contains("monster room"));
        assertTrue(doc.contains("Generate a fresh world and enter `CAVERN`"));
        assertTrue(doc.contains("Restart the server"));
    }

    @Test
    void runtimeValidationNoteStoresDatedServerObservationsOutsideBaselineDoc() throws IOException {
        String note = Files.readString(WORLDGEN_RUNTIME_NOTE);

        assertTrue(note.contains("2026-04-18"));
        assertTrue(note.contains("setworldspawn 0 70 0"));
        assertTrue(note.contains("Empty height range"));
        assertTrue(note.contains("ore_coal_upper"));
        assertTrue(note.contains("monster room"));
        assertTrue(note.contains("one-off headless dedicated-server validation pass"));
        assertTrue(note.contains("**not** the regression-protected baseline itself"));
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
