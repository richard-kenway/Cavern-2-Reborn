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

class OreCompassResourcesTest {
    @Test
    void oreCompassIsRegisteredAsCustomItemAndPlacedNearMinerOrb() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );

        assertTrue(registriesSource.contains("ITEMS.register("));
        assertTrue(registriesSource.contains("\"ore_compass\""));
        assertTrue(registriesSource.contains("new OreCompassItem(new Item.Properties().stacksTo(1))"));
        assertInOrder(registriesSource, List.of(
            "output.accept(HEXCITE.get())",
            "output.accept(MINER_ORB.get())",
            "output.accept(ORE_COMPASS.get())",
            "output.accept(HEXCITE_PICKAXE.get())"
        ));
    }

    @Test
    void oreCompassResourcesAndDocsStayInSync() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject model = readJsonResource("assets/cavernreborn/models/item/ore_compass.json");
        JsonObject recipe = readJsonResource("data/cavernreborn/recipe/ore_compass.json");
        JsonObject singularTag = readJsonResource("data/cavernreborn/tags/block/ore_compass_targets.json");
        JsonObject pluralTag = readJsonResource("data/cavernreborn/tags/blocks/ore_compass_targets.json");
        String readme = Files.readString(resolveProjectFile("README.md"));
        String oreCompassDoc = Files.readString(resolveProjectFile("docs", "ore-compass-mvp.md"));
        String minerOrbDoc = Files.readString(resolveProjectFile("docs", "miner-orb-mvp.md"));
        String runtimeSmokeDoc = Files.readString(resolveProjectFile("docs", "runtime-smoke.md"));
        String itemSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "item", "OreCompassItem.java"
        );
        String scannerSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "compass", "OreCompassScanner.java"
        );
        String tagSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModBlockTags.java"
        );

        assertEquals("Ore Compass", lang.get("item.cavernreborn.ore_compass").getAsString());
        assertEquals("minecraft:item/generated", model.get("parent").getAsString());
        assertEquals("cavernreborn:item/ore_compass", model.getAsJsonObject("textures").get("layer0").getAsString());
        assertTrue(lang.has("message.cavernreborn.ore_compass.found"));
        assertTrue(lang.has("message.cavernreborn.ore_compass.no_target"));
        assertTrue(lang.has("message.cavernreborn.ore_compass.wrong_dimension"));
        assertTrue(lang.has("message.cavernreborn.ore_compass.cooldown"));

        assertEquals("minecraft:crafting_shaped", recipe.get("type").getAsString());
        assertEquals("equipment", recipe.get("category").getAsString());
        assertEquals("cavernreborn:ore_compass", recipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(1, recipe.getAsJsonObject("result").get("count").getAsInt());
        String recipeRaw = readTextResource("data/cavernreborn/recipe/ore_compass.json");
        assertTrue(recipeRaw.contains("minecraft:compass"));
        assertTrue(recipeRaw.contains("cavernreborn:miner_orb"));
        assertTrue(recipeRaw.contains("cavernreborn:hexcite"));
        assertTrue(recipeRaw.contains("minecraft:redstone"));

        List<String> singularValues = arrayStrings(singularTag.getAsJsonArray("values"));
        List<String> pluralValues = arrayStrings(pluralTag.getAsJsonArray("values"));
        assertFalse(singularTag.get("replace").getAsBoolean());
        assertEquals(singularValues, pluralValues);
        assertTrue(singularValues.contains("cavernreborn:hexcite_ore"));
        assertTrue(singularValues.contains("cavernreborn:randomite_ore"));
        assertTrue(singularValues.contains("cavernreborn:aquamarine_ore"));
        assertTrue(singularValues.contains("cavernreborn:magnite_ore"));
        assertTrue(singularValues.contains("minecraft:diamond_ore"));
        assertTrue(singularValues.contains("minecraft:deepslate_diamond_ore"));
        assertTrue(singularValues.contains("minecraft:emerald_ore"));
        assertTrue(singularValues.contains("minecraft:deepslate_emerald_ore"));
        assertFalse(singularValues.contains("cavernreborn:fissured_stone"));
        assertFalse(singularValues.contains("cavernreborn:hexcite_block"));
        assertFalse(singularValues.contains("cavernreborn:aquamarine_block"));
        assertFalse(singularValues.contains("cavernreborn:magnite_block"));
        assertFalse(singularValues.contains("cavernreborn:cavern_portal"));
        assertFalse(singularValues.contains("minecraft:coal_ore"));
        assertFalse(singularValues.contains("minecraft:iron_ore"));
        assertFalse(singularValues.contains("minecraft:copper_ore"));

        assertTrue(tagSource.contains("ORE_COMPASS_TARGETS"));
        assertTrue(itemSource.contains("player.getCooldowns().addCooldown(this, OreCompassScanPolicy.COOLDOWN_TICKS)"));
        assertTrue(itemSource.contains("CavernDimensions.CAVERN_DIMENSION_ID"));
        assertTrue(itemSource.contains("scanner.findNearestTarget("));
        assertFalse(itemSource.contains(".shrink("));
        assertFalse(itemSource.contains("hurtAndBreak("));
        assertTrue(scannerSource.contains("ModBlockTags.ORE_COMPASS_TARGETS"));
        assertTrue(scannerSource.contains("level.isLoaded("));
        assertFalse(scannerSource.contains("setChunkForced"));
        assertFalse(scannerSource.contains("loadChunk"));

        assertTrue(readme.contains("Ore Compass MVP"));
        assertTrue(readme.contains("miner_orb"));
        assertTrue(readme.contains("server-side `CAVERN` ore scan"));
        assertTrue(readme.contains("docs/ore-compass-mvp.md"));
        assertTrue(oreCompassDoc.contains("cavernreborn:ore_compass"));
        assertTrue(oreCompassDoc.contains("cavernreborn:miner_orb"));
        assertTrue(oreCompassDoc.contains("loaded blocks only"));
        assertTrue(oreCompassDoc.contains("32"));
        assertTrue(oreCompassDoc.contains("24"));
        assertTrue(oreCompassDoc.contains("ore_compass_targets"));
        assertTrue(oreCompassDoc.contains("no live compass needle"));
        assertTrue(oreCompassDoc.contains("no GUI"));
        assertTrue(oreCompassDoc.contains("no client packets"));
        assertTrue(minerOrbDoc.contains("Ore Compass MVP now exists"));
        assertTrue(minerOrbDoc.contains("recipe ingredient"));
        assertTrue(minerOrbDoc.contains("bonus behavior remains unchanged"));
        assertTrue(runtimeSmokeDoc.contains("ore_compass runtime registry availability"));
        assertTrue(runtimeSmokeDoc.contains("ore_compass target tag resolution"));
        assertTrue(runtimeSmokeDoc.contains("ore_compass scanner nearest-target behavior"));
        assertTrue(runtimeSmokeDoc.contains("unsupported/fissured/storage exclusion"));
    }

    @Test
    void oreCompassResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/ore_compass.json"), "assets/cavernreborn/models/item/ore_compass.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/ore_compass.png"), "assets/cavernreborn/textures/item/ore_compass.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/ore_compass.json"), "data/cavernreborn/recipe/ore_compass.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/block/ore_compass_targets.json"), "data/cavernreborn/tags/block/ore_compass_targets.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/blocks/ore_compass_targets.json"), "data/cavernreborn/tags/blocks/ore_compass_targets.json");
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
        InputStream inputStream = OreCompassResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = OreCompassResourcesTest.class.getClassLoader().getResource(path);
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
