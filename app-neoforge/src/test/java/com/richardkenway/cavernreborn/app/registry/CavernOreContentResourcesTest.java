package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavernOreContentResourcesTest {
    @Test
    void oreFamilyRegistriesExposeExpectedIds() {
        String registriesSource = readProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java");

        assertTrue(registriesSource.contains("BLOCKS.register(\"aquamarine_ore\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"aquamarine_block\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"magnite_ore\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"magnite_block\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"aquamarine\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_ingot\""));
        assertTrue(registriesSource.contains("ITEMS.registerSimpleBlockItem(AQUAMARINE_ORE)"));
        assertTrue(registriesSource.contains("ITEMS.registerSimpleBlockItem(MAGNITE_ORE)"));
    }

    @Test
    void oreFamilyResourcesCoverLangModelsLootRecipesAndTags() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject aquamarineOreBlockstate = readJsonResource("assets/cavernreborn/blockstates/aquamarine_ore.json");
        JsonObject magniteOreBlockstate = readJsonResource("assets/cavernreborn/blockstates/magnite_ore.json");
        JsonObject aquamarineOreModel = readJsonResource("assets/cavernreborn/models/block/aquamarine_ore.json");
        JsonObject magniteBlockModel = readJsonResource("assets/cavernreborn/models/block/magnite_block.json");
        JsonObject aquamarineItemModel = readJsonResource("assets/cavernreborn/models/item/aquamarine.json");
        JsonObject magniteIngotItemModel = readJsonResource("assets/cavernreborn/models/item/magnite_ingot.json");
        JsonObject aquamarineOreLoot = readJsonResource("data/cavernreborn/loot_table/blocks/aquamarine_ore.json");
        JsonObject magniteOreLoot = readJsonResource("data/cavernreborn/loot_table/blocks/magnite_ore.json");
        JsonObject aquamarineBlockRecipe = readJsonResource("data/cavernreborn/recipe/aquamarine_block.json");
        JsonObject magniteSmeltingRecipe = readJsonResource("data/cavernreborn/recipe/magnite_ingot_from_smelting.json");
        JsonObject magniteBlastingRecipe = readJsonResource("data/cavernreborn/recipe/magnite_ingot_from_blasting.json");
        JsonObject mineablePickaxe = readJsonResource("data/minecraft/tags/blocks/mineable/pickaxe.json");
        JsonObject needsIronTool = readJsonResource("data/minecraft/tags/blocks/needs_iron_tool.json");
        JsonObject aquamarineGemTag = readJsonResource("data/c/tags/items/gems/aquamarine.json");
        JsonObject magniteIngotTag = readJsonResource("data/c/tags/items/ingots/magnite.json");

        assertEquals("Aquamarine Ore", lang.get("block.cavernreborn.aquamarine_ore").getAsString());
        assertEquals("Magnite Ingot", lang.get("item.cavernreborn.magnite_ingot").getAsString());

        assertEquals("cavernreborn:block/aquamarine_ore", variantModel(aquamarineOreBlockstate));
        assertEquals("cavernreborn:block/magnite_ore", variantModel(magniteOreBlockstate));
        assertEquals("cavernreborn:block/cube_ore", aquamarineOreModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/aquamarine_ore", aquamarineOreModel.getAsJsonObject("textures").get("ore").getAsString());
        assertEquals("minecraft:block/cube_all", magniteBlockModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/magnite_block", magniteBlockModel.getAsJsonObject("textures").get("all").getAsString());
        assertEquals("minecraft:item/generated", aquamarineItemModel.get("parent").getAsString());
        assertEquals("cavernreborn:item/aquamarine", aquamarineItemModel.getAsJsonObject("textures").get("layer0").getAsString());
        assertEquals("cavernreborn:item/magnite_ingot", magniteIngotItemModel.getAsJsonObject("textures").get("layer0").getAsString());

        JsonObject aquamarineLootEntry = firstLootEntry(aquamarineOreLoot);
        assertEquals("cavernreborn:aquamarine", aquamarineLootEntry.get("name").getAsString());
        assertTrue(functionNames(aquamarineLootEntry).contains("minecraft:apply_bonus"));
        assertTrue(functionNames(aquamarineLootEntry).contains("minecraft:explosion_decay"));

        JsonObject magniteLootEntry = firstLootEntry(magniteOreLoot);
        assertEquals("cavernreborn:magnite_ore", magniteLootEntry.get("name").getAsString());

        assertEquals("cavernreborn:aquamarine_block", aquamarineBlockRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(1, aquamarineBlockRecipe.getAsJsonObject("result").get("count").getAsInt());
        assertEquals("cavernreborn:magnite_ore", magniteSmeltingRecipe.getAsJsonObject("ingredient").get("item").getAsString());
        assertEquals("cavernreborn:magnite_ingot", magniteSmeltingRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals("minecraft:blasting", magniteBlastingRecipe.get("type").getAsString());
        assertEquals(100, magniteBlastingRecipe.get("cookingtime").getAsInt());

        List<String> mineableValues = arrayStrings(mineablePickaxe.getAsJsonArray("values"));
        List<String> ironToolValues = arrayStrings(needsIronTool.getAsJsonArray("values"));
        assertTrue(mineableValues.contains("cavernreborn:aquamarine_ore"));
        assertTrue(mineableValues.contains("cavernreborn:magnite_block"));
        assertTrue(ironToolValues.contains("cavernreborn:magnite_ore"));
        assertTrue(arrayStrings(aquamarineGemTag.getAsJsonArray("values")).contains("cavernreborn:aquamarine"));
        assertTrue(arrayStrings(magniteIngotTag.getAsJsonArray("values")).contains("cavernreborn:magnite_ingot"));
    }

    @Test
    void oreFamilyResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/blockstates/aquamarine_ore.json"), "assets/cavernreborn/blockstates/aquamarine_ore.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/block/magnite_ore.json"), "assets/cavernreborn/models/block/magnite_ore.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_ingot.json"), "assets/cavernreborn/models/item/magnite_ingot.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/loot_table/blocks/aquamarine_ore.json"), "data/cavernreborn/loot_table/blocks/aquamarine_ore.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/magnite_ingot_from_smelting.json"), "data/cavernreborn/recipe/magnite_ingot_from_smelting.json");
        assertClassPathOrigin(resourceUrl("data/c/tags/items/gems/aquamarine.json"), "data/c/tags/items/gems/aquamarine.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/aquamarine_ore.png"), "assets/cavernreborn/textures/block/aquamarine_ore.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_ingot.png"), "assets/cavernreborn/textures/item/magnite_ingot.png");
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CavernOreContentResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
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

    private static URL resourceUrl(String path) {
        URL url = CavernOreContentResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertClassPathOrigin(URL url, String expectedSuffix) {
        assertNotNull(url);
        assertTrue(url.toString().contains(expectedSuffix), "Expected classpath URL to contain " + expectedSuffix + " but was " + url);
    }

    private static String variantModel(JsonObject blockstate) {
        return blockstate.getAsJsonObject("variants").getAsJsonObject("").get("model").getAsString();
    }

    private static JsonObject firstLootEntry(JsonObject lootTable) {
        return lootTable.getAsJsonArray("pools")
            .get(0).getAsJsonObject()
            .getAsJsonArray("entries")
            .get(0).getAsJsonObject();
    }

    private static List<String> functionNames(JsonObject entry) {
        JsonArray functions = entry.getAsJsonArray("functions");
        return StreamSupport.stream(functions.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .map(function -> function.get("function").getAsString())
            .toList();
    }

    private static List<String> arrayStrings(JsonArray array) {
        return StreamSupport.stream(array.spliterator(), false)
            .map(JsonElement::getAsString)
            .toList();
    }
}
