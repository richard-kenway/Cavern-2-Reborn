package com.richardkenway.cavernreborn.app.gametest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CavernRuntimeSmokeDocumentationTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path SCRIPT = resolveProjectFile("scripts", "runtime-smoke.sh");

    @Test
    void readmeDocumentsAutomatedRuntimeSmokeEntryPoint() throws IOException {
        String readme = Files.readString(README);

        assertTrue(readme.contains("Automated Runtime Validation"));
        assertTrue(readme.contains("NeoForge GameTest"));
        assertTrue(readme.contains("docs/runtime-smoke.md"));
        assertTrue(readme.contains("./gradlew --no-daemon :app-neoforge:runGameTestServer"));
        assertTrue(readme.contains("scripts/runtime-smoke.sh"));
        assertTrue(readme.contains("hexcite"));
        assertTrue(readme.contains("randomite"));
        assertTrue(readme.contains("fissured_stone"));
        assertTrue(readme.contains("Mining Assist"));
    }

    @Test
    void runtimeSmokeDocCoversCommandsAndScope() throws IOException {
        String doc = Files.readString(RUNTIME_SMOKE);

        assertTrue(doc.contains("NeoForge GameTest runtime smoke"));
        assertTrue(doc.contains("runtime registry availability for tranche 2 content"));
        assertTrue(doc.contains("hexcite normal and Silk Touch loot paths"));
        assertTrue(doc.contains("randomite curated runtime loot output"));
        assertTrue(doc.contains("hexcite pickaxe enchantment applicability"));
        assertTrue(doc.contains("Mining Assist policy/runtime ids"));
        assertTrue(doc.contains("bounded same-block vein assist with hexcite_pickaxe"));
        assertTrue(doc.contains("no unlock means no assist"));
        assertTrue(doc.contains("sneaking disables assist"));
        assertTrue(doc.contains("fissured_stone exclusion"));
        assertTrue(doc.contains("fissured stone no-drop behavior"));
        assertTrue(doc.contains("fissured stone survival effect behavior"));
        assertTrue(doc.contains("fissured stone creative guard"));
        assertTrue(doc.contains("fissured stone non-destructive behavior"));
        assertTrue(doc.contains("progression policy ids/scores"));
        assertTrue(doc.contains("worldgen configured/placed feature runtime resolution"));
        assertTrue(doc.contains("./gradlew --no-daemon :app-neoforge:runGameTestServer"));
        assertTrue(doc.contains("docker compose run --rm gradle ./gradlew --no-daemon :app-neoforge:runGameTestServer"));
        assertTrue(doc.contains("scripts/runtime-smoke.sh"));
        assertTrue(doc.contains("minecraft:empty"));
        assertTrue(doc.contains("app-neoforge/src/gameteststructures/empty.snbt"));
        assertTrue(doc.contains("manual client smoke is still needed"));
        assertTrue(doc.contains("portal UX"));
        assertTrue(doc.contains("rendering"));
    }

    @Test
    void runtimeSmokeScriptExists() {
        assertTrue(Files.exists(SCRIPT));
        assertTrue(Files.isExecutable(SCRIPT));
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
