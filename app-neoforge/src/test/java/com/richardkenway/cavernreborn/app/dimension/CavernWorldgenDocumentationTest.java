package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavernWorldgenDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path WORLDGEN_BASELINE = resolveProjectFile("docs", "worldgen-baseline.md");
    private static final Path WORLDGEN_PARITY_NOTE = resolveProjectFile("docs", "cavern-worldgen-parity.md");
    private static final Path POPULATION_PARITY_NOTE = resolveProjectFile("docs", "cavern-population-parity.md");
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
        assertTrue(readme.contains("cave_extreme_upper_network.json"));
        assertTrue(readme.contains("docs/cavern-worldgen-parity.md"));
        assertTrue(readme.contains("docs/cavern-population-parity.md"));
        assertTrue(readme.contains("lower `Y < -32` hot band"));
        assertTrue(readme.contains("monster-room pressure"));
        assertTrue(readme.contains("mushroom scatter"));
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
        assertTrue(doc.contains("upper extreme-like chamber band"));
        assertTrue(doc.contains("lower hot band"));
        assertTrue(doc.contains("cave_extreme_upper_network.json"));
        assertTrue(doc.contains("docs/cavern-worldgen-parity.md"));
        assertTrue(doc.contains("docs/cavern-population-parity.md"));
        assertTrue(doc.contains("docs/worldgen-runtime-validation-2026-04-18.md"));
        assertTrue(doc.contains("generated resources resolve from the runtime classpath"));
        assertTrue(doc.contains("spring/fall feel"));
        assertTrue(doc.contains("mushroom patch scatter"));
        assertTrue(doc.contains("setworldspawn 0 70 0"));
        assertTrue(doc.contains("historical log"));
        assertTrue(doc.contains("monster room"));
        assertTrue(doc.contains("Generate a fresh world and enter `CAVERN`"));
        assertTrue(doc.contains("Restart the server"));
    }

    @Test
    void parityNoteDocumentsNarrowTerrainSignatureScope() throws IOException {
        String note = Files.readString(WORLDGEN_PARITY_NOTE);

        assertTrue(note.contains("Terrain-Signature Parity"));
        assertTrue(note.contains("solid stone world first"));
        assertTrue(note.contains("upper extreme-like cavern band"));
        assertTrue(note.contains("lower hot/lava-oriented band"));
        assertTrue(note.contains("Deliberately Not In This Tranche"));
        assertTrue(note.contains("Literal 1:1 port"));
        assertTrue(note.contains("surface band below the portal safe-arrival search floor"));
    }

    @Test
    void populationParityNoteDocumentsNarrowPopulationScope() throws IOException {
        String note = Files.readString(POPULATION_PARITY_NOTE);

        assertTrue(note.contains("Population Parity"));
        assertTrue(note.contains("monster-room pressure"));
        assertTrue(note.contains("Cave fluid activity"));
        assertTrue(note.contains("Visible mushroom and cave-dressing scatter"));
        assertTrue(note.contains("Deliberately Not In This Tranche"));
        assertTrue(note.contains("Tower dungeons"));
        assertTrue(note.contains("current portal and terrain baselines"));
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
