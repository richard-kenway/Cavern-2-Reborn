package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

class MinerOrbResourcesTest {
    @Test
    void minerOrbIsRegisteredAndVisibleInCreativeTab() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );

        assertTrue(registriesSource.contains("ITEMS.register(\"miner_orb\""));
        assertTrue(registriesSource.contains("new Item(new Item.Properties().stacksTo(16))"));
        assertInOrder(registriesSource, List.of(
            "output.accept(HEXCITE.get())",
            "output.accept(MINER_ORB.get())",
            "output.accept(HEXCITE_PICKAXE.get())"
        ));
    }

    @Test
    void minerOrbResourcesAndDocsStayInSync() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject model = readJsonResource("assets/cavernreborn/models/item/miner_orb.json");
        JsonObject singularTag = readJsonResource("data/cavernreborn/tags/item/mining_bonus_orbs.json");
        JsonObject pluralTag = readJsonResource("data/cavernreborn/tags/items/mining_bonus_orbs.json");
        JsonObject commonTag = readJsonResource("data/c/tags/items/orbs/miner.json");
        JsonObject randomiteLoot = readJsonResource("data/cavernreborn/loot_table/blocks/randomite_ore.json");
        String randomiteLootRaw = readTextResource("data/cavernreborn/loot_table/blocks/randomite_ore.json");
        String readme = Files.readString(resolveProjectFile("README.md"));
        String doc = Files.readString(resolveProjectFile("docs", "miner-orb-mvp.md"));
        String runtimeSmokeDoc = Files.readString(resolveProjectFile("docs", "runtime-smoke.md"));
        String progressionDoc = Files.readString(resolveProjectFile("docs", "progression-baseline.md"));
        String specialOreDoc = Files.readString(resolveProjectFile("docs", "cavern-special-ore-content-parity.md"));
        String miningAssistDoc = Files.readString(resolveProjectFile("docs", "mining-assist-mvp.md"));

        assertEquals("Miner's Orb", lang.get("item.cavernreborn.miner_orb").getAsString());
        assertEquals("minecraft:item/generated", model.get("parent").getAsString());
        assertEquals("cavernreborn:item/miner_orb", model.getAsJsonObject("textures").get("layer0").getAsString());

        List<String> singularValues = arrayStrings(singularTag.getAsJsonArray("values"));
        List<String> pluralValues = arrayStrings(pluralTag.getAsJsonArray("values"));
        assertFalse(singularTag.get("replace").getAsBoolean());
        assertEquals(singularValues, pluralValues);
        assertTrue(singularValues.contains("cavernreborn:miner_orb"));
        assertTrue(arrayStrings(commonTag.getAsJsonArray("values")).contains("cavernreborn:miner_orb"));

        JsonObject minerOrbEntry = lootEntry(randomiteLoot, "cavernreborn:miner_orb");
        assertEquals(1, minerOrbEntry.get("weight").getAsInt());
        assertEquals(1, functionByName(minerOrbEntry, "minecraft:set_count").get("count").getAsInt());
        assertFalse(functionNames(minerOrbEntry).contains("minecraft:apply_bonus"));
        assertFalse(randomiteLootRaw.contains("cavernreborn:cavenic_orb"));
        assertFalse(randomiteLootRaw.contains("RandomiteDropEvent"));
        assertFalse(randomiteLootRaw.contains("OreDictionary"));
        assertFalse(randomiteLootRaw.contains("getRandomItem"));

        assertTrue(readme.contains("Miner's Orb MVP"));
        assertTrue(readme.contains("randomite_ore"));
        assertTrue(readme.contains("docs/miner-orb-mvp.md"));
        assertTrue(doc.contains("cavernreborn:miner_orb"));
        assertTrue(doc.contains("not consumed"));
        assertTrue(doc.contains("Multiple `miner_orb` items do not stack"));
        assertTrue(doc.contains("10%"));
        assertTrue(doc.contains("max(base score / 2, 1)"));
        assertTrue(doc.contains("No `cavenic_orb`"));
        assertTrue(doc.contains("No Ore Compass"));
        assertTrue(runtimeSmokeDoc.contains("miner_orb runtime registry availability"));
        assertTrue(runtimeSmokeDoc.contains("randomite allowed-drop runtime coverage that now includes miner_orb in the curated pool"));
        assertTrue(runtimeSmokeDoc.contains("miner_orb bonus policy/runtime smoke"));
        assertTrue(progressionDoc.contains("item-based score bonus"));
        assertTrue(progressionDoc.contains("does not add extra counted-block records"));
        assertTrue(specialOreDoc.contains("very rare `miner_orb`"));
        assertTrue(miningAssistDoc.contains("excluded from the Miner's Orb score bonus path"));
    }

    @Test
    void minerOrbResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/miner_orb.json"), "assets/cavernreborn/models/item/miner_orb.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/miner_orb.png"), "assets/cavernreborn/textures/item/miner_orb.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/item/mining_bonus_orbs.json"), "data/cavernreborn/tags/item/mining_bonus_orbs.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/items/mining_bonus_orbs.json"), "data/cavernreborn/tags/items/mining_bonus_orbs.json");
        assertClassPathOrigin(resourceUrl("data/c/tags/items/orbs/miner.json"), "data/c/tags/items/orbs/miner.json");
    }

    private static JsonObject functionByName(JsonObject entry, String functionName) {
        return StreamSupport.stream(entry.getAsJsonArray("functions").spliterator(), false)
            .map(JsonObject.class::cast)
            .filter(function -> functionName.equals(function.get("function").getAsString()))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Missing function " + functionName + " in " + entry));
    }

    private static List<String> functionNames(JsonObject entry) {
        return StreamSupport.stream(entry.getAsJsonArray("functions").spliterator(), false)
            .map(JsonObject.class::cast)
            .map(function -> function.get("function").getAsString())
            .toList();
    }

    private static JsonObject lootEntry(JsonObject lootTable, String itemId) {
        return StreamSupport.stream(lootTable.getAsJsonArray("pools").spliterator(), false)
            .map(JsonObject.class::cast)
            .flatMap(pool -> StreamSupport.stream(pool.getAsJsonArray("entries").spliterator(), false))
            .map(JsonObject.class::cast)
            .filter(entry -> itemId.equals(entry.get("name").getAsString()))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Missing loot entry for " + itemId));
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static String readTextResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = MinerOrbResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = MinerOrbResourcesTest.class.getClassLoader().getResource(path);
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

    private static void assertInOrder(String source, List<String> snippets) {
        int previousIndex = -1;
        for (String snippet : snippets) {
            int currentIndex = source.indexOf(snippet);
            assertTrue(currentIndex >= 0, "Missing snippet: " + snippet);
            assertTrue(currentIndex > previousIndex, "Snippet order regression around: " + snippet);
            previousIndex = currentIndex;
        }
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
