package com.richardkenway.cavernreborn.app.mining;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class MiningAssistResourcesTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path MINING_ASSIST_DOC = resolveProjectFile("docs", "mining-assist-mvp.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");

    @Test
    void miningAssistEventIsRegisteredFromMainModBootstrap() {
        String modSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"
        );
        String eventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "mining", "CavernMiningAssistEvents.java"
        );

        assertTrue(modSource.contains("new CavernMiningAssistEvents("));
        assertTrue(eventSource.contains("MiningAssistPolicy.evaluate("));
        assertTrue(eventSource.contains("MiningAssistBreakContext"));
        assertTrue(eventSource.contains("MiningAssistPolicy.MAX_EXTRA_BLOCKS"));
        assertTrue(eventSource.contains("breakBlockWithTool("));
    }

    @Test
    void miningAssistTargetTagsStayNarrowAndConsistent() throws IOException {
        JsonObject singularTag = readJsonResource("data/cavernreborn/tags/block/mining_assist_targets.json");
        JsonObject pluralTag = readJsonResource("data/cavernreborn/tags/blocks/mining_assist_targets.json");

        List<String> singularValues = arrayStrings(singularTag.getAsJsonArray("values"));
        List<String> pluralValues = arrayStrings(pluralTag.getAsJsonArray("values"));

        assertTrue(!singularTag.get("replace").getAsBoolean());
        assertEquals(singularValues, pluralValues);
        assertTrue(singularValues.contains("minecraft:coal_ore"));
        assertTrue(singularValues.contains("minecraft:diamond_ore"));
        assertTrue(singularValues.contains("cavernreborn:aquamarine_ore"));
        assertTrue(singularValues.contains("cavernreborn:magnite_ore"));
        assertTrue(singularValues.contains("cavernreborn:randomite_ore"));
        assertTrue(singularValues.contains("cavernreborn:hexcite_ore"));
        assertTrue(!singularValues.contains("cavernreborn:fissured_stone"));
        assertTrue(!singularValues.contains("cavernreborn:hexcite_block"));
        assertTrue(!singularValues.contains("cavernreborn:aquamarine_block"));
        assertTrue(!singularValues.contains("cavernreborn:magnite_block"));
        assertTrue(!singularValues.contains("minecraft:raw_iron_block"));
        assertTrue(!singularValues.contains("minecraft:raw_gold_block"));
        assertTrue(!singularValues.contains("minecraft:raw_copper_block"));
        assertTrue(!singularValues.contains("cavernreborn:cavern_portal"));
    }

    @Test
    void miningAssistDocsAndRuntimeSmokeMentionsStayCurrent() throws IOException {
        String readme = Files.readString(README);
        String miningAssistDoc = Files.readString(MINING_ASSIST_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Mining Assist MVP"));
        assertTrue(readme.contains("progression-gated"));
        assertTrue(readme.contains("hexcite_pickaxe"));
        assertTrue(readme.contains("docs/mining-assist-mvp.md"));

        assertTrue(miningAssistDoc.contains("bounded first pass"));
        assertTrue(miningAssistDoc.contains("hexcite_pickaxe"));
        assertTrue(miningAssistDoc.contains("CAVERN`-only"));
        assertTrue(miningAssistDoc.contains("6"));
        assertTrue(miningAssistDoc.contains("Sneaking disables"));
        assertTrue(miningAssistDoc.contains("do not add extra progression score"));
        assertTrue(miningAssistDoc.contains("fissured_stone"));

        assertTrue(runtimeSmokeDoc.contains("Mining Assist policy/runtime ids"));
        assertTrue(runtimeSmokeDoc.contains("bounded same-block vein assist with hexcite_pickaxe"));
        assertTrue(runtimeSmokeDoc.contains("no unlock means no assist"));
        assertTrue(runtimeSmokeDoc.contains("sneaking disables assist"));
        assertTrue(runtimeSmokeDoc.contains("fissured_stone exclusion"));
    }

    @Test
    void miningAssistTagResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(
            resourceUrl("data/cavernreborn/tags/block/mining_assist_targets.json"),
            "data/cavernreborn/tags/block/mining_assist_targets.json"
        );
        assertClassPathOrigin(
            resourceUrl("data/cavernreborn/tags/blocks/mining_assist_targets.json"),
            "data/cavernreborn/tags/blocks/mining_assist_targets.json"
        );
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = MiningAssistResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = MiningAssistResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertClassPathOrigin(URL url, String expectedPathSuffix) {
        assertTrue(url.toString().contains(expectedPathSuffix), "Expected runtime classpath resource to contain " + expectedPathSuffix + " but got " + url);
    }

    private static List<String> arrayStrings(JsonArray array) {
        return StreamSupport.stream(array.spliterator(), false)
            .map(element -> element.getAsString())
            .toList();
    }

    private static String readProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate)) {
                try {
                    return Files.readString(candidate);
                } catch (IOException e) {
                    throw new IllegalStateException("Failed to read project file: " + candidate, e);
                }
            }
            current = current.getParent();
        }

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
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
