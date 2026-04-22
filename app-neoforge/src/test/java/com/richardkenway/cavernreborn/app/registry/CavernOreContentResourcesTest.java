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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavernOreContentResourcesTest {
    @Test
    void oreFamilyRegistriesExposeExpectedIds() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );

        assertTrue(registriesSource.contains("BLOCKS.register(\"aquamarine_ore\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"aquamarine_block\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"magnite_ore\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"magnite_block\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"randomite_ore\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"hexcite_ore\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"hexcite_block\""));
        assertTrue(registriesSource.contains("BLOCKS.register(\"fissured_stone\""));
        assertTrue(registriesSource.contains("new FissuredStoneBlock("));
        assertTrue(registriesSource.contains("ITEMS.register(\"aquamarine\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_ingot\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"hexcite\""));
        assertTrue(registriesSource.contains("ITEMS.registerSimpleBlockItem(RANDOMITE_ORE)"));
        assertTrue(registriesSource.contains("ITEMS.registerSimpleBlockItem(HEXCITE_ORE)"));
        assertTrue(registriesSource.contains("ITEMS.registerSimpleBlockItem(HEXCITE_BLOCK)"));
        assertTrue(registriesSource.contains("ITEMS.registerSimpleBlockItem(FISSURED_STONE)"));

        assertInOrder(registriesSource, List.of(
            "output.accept(AQUAMARINE_ORE_ITEM.get())",
            "output.accept(AQUAMARINE_BLOCK_ITEM.get())",
            "output.accept(MAGNITE_ORE_ITEM.get())",
            "output.accept(MAGNITE_BLOCK_ITEM.get())",
            "output.accept(RANDOMITE_ORE_ITEM.get())",
            "output.accept(HEXCITE_ORE_ITEM.get())",
            "output.accept(HEXCITE_BLOCK_ITEM.get())",
            "output.accept(FISSURED_STONE_ITEM.get())",
            "output.accept(AQUAMARINE.get())",
            "output.accept(MAGNITE_INGOT.get())",
            "output.accept(HEXCITE.get())"
        ));
    }

    @Test
    void oreFamilyResourcesCoverLangModelsLootRecipesAndTags() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject aquamarineOreBlockstate = readJsonResource("assets/cavernreborn/blockstates/aquamarine_ore.json");
        JsonObject aquamarineBlockBlockstate = readJsonResource("assets/cavernreborn/blockstates/aquamarine_block.json");
        JsonObject magniteOreBlockstate = readJsonResource("assets/cavernreborn/blockstates/magnite_ore.json");
        JsonObject magniteBlockBlockstate = readJsonResource("assets/cavernreborn/blockstates/magnite_block.json");
        JsonObject hexciteOreBlockstate = readJsonResource("assets/cavernreborn/blockstates/hexcite_ore.json");
        JsonObject randomiteOreBlockstate = readJsonResource("assets/cavernreborn/blockstates/randomite_ore.json");
        JsonObject fissuredStoneBlockstate = readJsonResource("assets/cavernreborn/blockstates/fissured_stone.json");
        JsonObject aquamarineOreModel = readJsonResource("assets/cavernreborn/models/block/aquamarine_ore.json");
        JsonObject aquamarineBlockModel = readJsonResource("assets/cavernreborn/models/block/aquamarine_block.json");
        JsonObject magniteOreModel = readJsonResource("assets/cavernreborn/models/block/magnite_ore.json");
        JsonObject magniteBlockModel = readJsonResource("assets/cavernreborn/models/block/magnite_block.json");
        JsonObject hexciteOreModel = readJsonResource("assets/cavernreborn/models/block/hexcite_ore.json");
        JsonObject hexciteBlockModel = readJsonResource("assets/cavernreborn/models/block/hexcite_block.json");
        JsonObject fissuredStoneModel = readJsonResource("assets/cavernreborn/models/block/fissured_stone.json");
        JsonObject aquamarineItemModel = readJsonResource("assets/cavernreborn/models/item/aquamarine.json");
        JsonObject magniteIngotItemModel = readJsonResource("assets/cavernreborn/models/item/magnite_ingot.json");
        JsonObject hexciteItemModel = readJsonResource("assets/cavernreborn/models/item/hexcite.json");
        JsonObject aquamarineOreLoot = readJsonResource("data/cavernreborn/loot_table/blocks/aquamarine_ore.json");
        JsonObject aquamarineBlockLoot = readJsonResource("data/cavernreborn/loot_table/blocks/aquamarine_block.json");
        JsonObject magniteOreLoot = readJsonResource("data/cavernreborn/loot_table/blocks/magnite_ore.json");
        JsonObject magniteBlockLoot = readJsonResource("data/cavernreborn/loot_table/blocks/magnite_block.json");
        JsonObject hexciteOreLoot = readJsonResource("data/cavernreborn/loot_table/blocks/hexcite_ore.json");
        JsonObject randomiteOreLoot = readJsonResource("data/cavernreborn/loot_table/blocks/randomite_ore.json");
        JsonObject fissuredStoneLoot = readJsonResource("data/cavernreborn/loot_table/blocks/fissured_stone.json");
        JsonObject aquamarineBlockRecipe = readJsonResource("data/cavernreborn/recipe/aquamarine_block.json");
        JsonObject aquamarineFromBlockRecipe = readJsonResource("data/cavernreborn/recipe/aquamarine_from_block.json");
        JsonObject magniteBlockRecipe = readJsonResource("data/cavernreborn/recipe/magnite_block.json");
        JsonObject magniteFromBlockRecipe = readJsonResource("data/cavernreborn/recipe/magnite_from_block.json");
        JsonObject magniteSmeltingRecipe = readJsonResource("data/cavernreborn/recipe/magnite_ingot_from_smelting.json");
        JsonObject magniteBlastingRecipe = readJsonResource("data/cavernreborn/recipe/magnite_ingot_from_blasting.json");
        JsonObject hexciteBlockRecipe = readJsonResource("data/cavernreborn/recipe/hexcite_block.json");
        JsonObject hexciteFromBlockRecipe = readJsonResource("data/cavernreborn/recipe/hexcite_from_block.json");
        JsonObject hexciteSmeltingRecipe = readJsonResource("data/cavernreborn/recipe/hexcite_from_smelting.json");
        JsonObject hexciteBlastingRecipe = readJsonResource("data/cavernreborn/recipe/hexcite_from_blasting.json");
        JsonObject mineablePickaxe = readJsonResource("data/minecraft/tags/blocks/mineable/pickaxe.json");
        JsonObject needsIronTool = readJsonResource("data/minecraft/tags/blocks/needs_iron_tool.json");
        JsonObject aquamarineGemTag = readJsonResource("data/c/tags/items/gems/aquamarine.json");
        JsonObject aquamarineOreTag = readJsonResource("data/c/tags/blocks/ores/aquamarine.json");
        JsonObject aquamarineStorageTag = readJsonResource("data/c/tags/blocks/storage_blocks/aquamarine.json");
        JsonObject magniteIngotTag = readJsonResource("data/c/tags/items/ingots/magnite.json");
        JsonObject magniteOreTag = readJsonResource("data/c/tags/blocks/ores/magnite.json");
        JsonObject magniteStorageTag = readJsonResource("data/c/tags/blocks/storage_blocks/magnite.json");
        JsonObject hexciteOreTag = readJsonResource("data/c/tags/blocks/ores/hexcite.json");
        JsonObject hexciteGemTag = readJsonResource("data/c/tags/items/gems/hexcite.json");
        JsonObject hexciteStorageTag = readJsonResource("data/c/tags/blocks/storage_blocks/hexcite.json");
        JsonObject hexciteItemStorageTag = readJsonResource("data/c/tags/items/storage_blocks/hexcite.json");

        assertEquals("Aquamarine Ore", lang.get("block.cavernreborn.aquamarine_ore").getAsString());
        assertEquals("Block of Aquamarine", lang.get("block.cavernreborn.aquamarine_block").getAsString());
        assertEquals("Aquamarine", lang.get("item.cavernreborn.aquamarine").getAsString());
        assertEquals("Magnite Ore", lang.get("block.cavernreborn.magnite_ore").getAsString());
        assertEquals("Block of Magnite", lang.get("block.cavernreborn.magnite_block").getAsString());
        assertEquals("Magnite Ingot", lang.get("item.cavernreborn.magnite_ingot").getAsString());
        assertEquals("Hexcite Ore", lang.get("block.cavernreborn.hexcite_ore").getAsString());
        assertEquals("Block of Hexcite", lang.get("block.cavernreborn.hexcite_block").getAsString());
        assertEquals("Randomite Ore", lang.get("block.cavernreborn.randomite_ore").getAsString());
        assertEquals("Fissured Stone", lang.get("block.cavernreborn.fissured_stone").getAsString());
        assertEquals("Hexcite", lang.get("item.cavernreborn.hexcite").getAsString());

        assertEquals("cavernreborn:block/aquamarine_ore", variantModel(aquamarineOreBlockstate));
        assertEquals("cavernreborn:block/aquamarine_block", variantModel(aquamarineBlockBlockstate));
        assertEquals("cavernreborn:block/magnite_ore", variantModel(magniteOreBlockstate));
        assertEquals("cavernreborn:block/magnite_block", variantModel(magniteBlockBlockstate));
        assertEquals("cavernreborn:block/hexcite_ore", variantModel(hexciteOreBlockstate));
        assertEquals("cavernreborn:block/randomite_ore", variantModel(randomiteOreBlockstate));
        assertEquals("cavernreborn:block/fissured_stone", variantModel(fissuredStoneBlockstate));
        assertEquals("cavernreborn:block/cube_ore", aquamarineOreModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/aquamarine_ore", aquamarineOreModel.getAsJsonObject("textures").get("ore").getAsString());
        assertEquals("minecraft:block/cube_all", aquamarineBlockModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/aquamarine_block", aquamarineBlockModel.getAsJsonObject("textures").get("all").getAsString());
        assertEquals("cavernreborn:block/cube_ore", magniteOreModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/magnite_ore", magniteOreModel.getAsJsonObject("textures").get("ore").getAsString());
        assertEquals("minecraft:block/cube_all", magniteBlockModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/magnite_block", magniteBlockModel.getAsJsonObject("textures").get("all").getAsString());
        assertEquals("cavernreborn:block/cube_ore", hexciteOreModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/hexcite_ore", hexciteOreModel.getAsJsonObject("textures").get("ore").getAsString());
        assertEquals("minecraft:block/cube_all", hexciteBlockModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/hexcite_block", hexciteBlockModel.getAsJsonObject("textures").get("all").getAsString());
        assertEquals("cavernreborn:block/fissured_stone", fissuredStoneModel.getAsJsonObject("textures").get("all").getAsString());
        assertEquals("minecraft:item/generated", aquamarineItemModel.get("parent").getAsString());
        assertEquals("cavernreborn:item/aquamarine", aquamarineItemModel.getAsJsonObject("textures").get("layer0").getAsString());
        assertEquals("minecraft:item/generated", magniteIngotItemModel.get("parent").getAsString());
        assertEquals("cavernreborn:item/magnite_ingot", magniteIngotItemModel.getAsJsonObject("textures").get("layer0").getAsString());
        assertEquals("minecraft:item/generated", hexciteItemModel.get("parent").getAsString());
        assertEquals("cavernreborn:item/hexcite", hexciteItemModel.getAsJsonObject("textures").get("layer0").getAsString());

        JsonObject aquamarineLootEntry = firstLootEntry(aquamarineOreLoot);
        assertEquals("cavernreborn:aquamarine", aquamarineLootEntry.get("name").getAsString());
        assertTrue(functionNames(aquamarineLootEntry).contains("minecraft:apply_bonus"));
        assertTrue(functionNames(aquamarineLootEntry).contains("minecraft:explosion_decay"));

        JsonObject aquamarineBlockLootEntry = firstLootEntry(aquamarineBlockLoot);
        assertEquals("cavernreborn:aquamarine_block", aquamarineBlockLootEntry.get("name").getAsString());

        JsonObject magniteLootEntry = firstLootEntry(magniteOreLoot);
        assertEquals("cavernreborn:magnite_ore", magniteLootEntry.get("name").getAsString());

        JsonObject magniteBlockLootEntry = firstLootEntry(magniteBlockLoot);
        assertEquals("cavernreborn:magnite_block", magniteBlockLootEntry.get("name").getAsString());

        JsonObject hexciteLootEntry = firstLootEntry(hexciteOreLoot);
        assertEquals("minecraft:alternatives", hexciteLootEntry.get("type").getAsString());
        JsonArray hexciteChildren = hexciteLootEntry.getAsJsonArray("children");
        JsonObject hexciteSilkTouchChild = hexciteChildren.get(0).getAsJsonObject();
        JsonObject hexciteNormalChild = hexciteChildren.get(1).getAsJsonObject();
        assertEquals("cavernreborn:hexcite_ore", hexciteSilkTouchChild.get("name").getAsString());
        JsonObject hexciteSilkTouchCondition = hexciteSilkTouchChild.getAsJsonArray("conditions").get(0).getAsJsonObject();
        assertEquals("minecraft:match_tool", hexciteSilkTouchCondition.get("condition").getAsString());
        JsonObject hexcitePredicate = hexciteSilkTouchCondition.getAsJsonObject("predicate");
        JsonArray hexciteSilkEnchantments = hexcitePredicate
            .getAsJsonObject("predicates")
            .getAsJsonArray("minecraft:enchantments");
        assertEquals("minecraft:silk_touch", hexciteSilkEnchantments.get(0).getAsJsonObject().get("enchantments").getAsString());
        assertEquals("cavernreborn:hexcite", hexciteNormalChild.get("name").getAsString());
        assertTrue(functionNames(hexciteNormalChild).contains("minecraft:apply_bonus"));
        assertTrue(functionNames(hexciteNormalChild).contains("minecraft:explosion_decay"));

        List<JsonObject> randomiteEntries = lootEntries(randomiteOreLoot);
        List<String> randomiteNames = entryNames(randomiteEntries);
        String randomiteLootRaw = readTextResource("data/cavernreborn/loot_table/blocks/randomite_ore.json");
        assertFalse(randomiteEntries.isEmpty());
        assertTrue(randomiteNames.contains("cavernreborn:hexcite"));
        assertTrue(randomiteNames.contains("minecraft:bread"));
        assertTrue(randomiteNames.contains("minecraft:apple"));
        assertFalse(randomiteLootRaw.contains("miner_orb"));
        assertFalse(randomiteLootRaw.contains("RandomiteDropEvent"));
        assertFalse(randomiteLootRaw.contains("OreDictionary"));
        assertFalse(randomiteLootRaw.contains("getRandomItem"));

        assertTrue(fissuredStoneLoot.getAsJsonArray("pools").isEmpty());

        assertEquals("cavernreborn:aquamarine_block", aquamarineBlockRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(1, aquamarineBlockRecipe.getAsJsonObject("result").get("count").getAsInt());
        assertEquals("cavernreborn:aquamarine", aquamarineFromBlockRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(9, aquamarineFromBlockRecipe.getAsJsonObject("result").get("count").getAsInt());
        assertEquals("cavernreborn:magnite_block", magniteBlockRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(1, magniteBlockRecipe.getAsJsonObject("result").get("count").getAsInt());
        assertEquals("cavernreborn:magnite_ingot", magniteFromBlockRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(9, magniteFromBlockRecipe.getAsJsonObject("result").get("count").getAsInt());
        assertEquals("cavernreborn:magnite_ore", magniteSmeltingRecipe.getAsJsonObject("ingredient").get("item").getAsString());
        assertEquals("cavernreborn:magnite_ingot", magniteSmeltingRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals("minecraft:blasting", magniteBlastingRecipe.get("type").getAsString());
        assertEquals(100, magniteBlastingRecipe.get("cookingtime").getAsInt());
        assertEquals("cavernreborn:hexcite_block", hexciteBlockRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(1, hexciteBlockRecipe.getAsJsonObject("result").get("count").getAsInt());
        assertEquals("cavernreborn:hexcite", hexciteFromBlockRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(9, hexciteFromBlockRecipe.getAsJsonObject("result").get("count").getAsInt());
        assertEquals("cavernreborn:hexcite_ore", hexciteSmeltingRecipe.getAsJsonObject("ingredient").get("item").getAsString());
        assertEquals("cavernreborn:hexcite", hexciteSmeltingRecipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(200, hexciteSmeltingRecipe.get("cookingtime").getAsInt());
        assertEquals("minecraft:blasting", hexciteBlastingRecipe.get("type").getAsString());
        assertEquals(100, hexciteBlastingRecipe.get("cookingtime").getAsInt());

        List<String> mineableValues = arrayStrings(mineablePickaxe.getAsJsonArray("values"));
        List<String> ironToolValues = arrayStrings(needsIronTool.getAsJsonArray("values"));
        assertTrue(mineableValues.contains("cavernreborn:aquamarine_ore"));
        assertTrue(mineableValues.contains("cavernreborn:aquamarine_block"));
        assertTrue(mineableValues.contains("cavernreborn:magnite_ore"));
        assertTrue(mineableValues.contains("cavernreborn:magnite_block"));
        assertTrue(mineableValues.contains("cavernreborn:randomite_ore"));
        assertTrue(mineableValues.contains("cavernreborn:hexcite_ore"));
        assertTrue(mineableValues.contains("cavernreborn:hexcite_block"));
        assertTrue(mineableValues.contains("cavernreborn:fissured_stone"));
        assertTrue(ironToolValues.contains("cavernreborn:magnite_ore"));
        assertTrue(ironToolValues.contains("cavernreborn:magnite_block"));
        assertTrue(ironToolValues.contains("cavernreborn:hexcite_ore"));
        assertTrue(ironToolValues.contains("cavernreborn:hexcite_block"));
        assertFalse(ironToolValues.contains("cavernreborn:randomite_ore"));
        assertFalse(ironToolValues.contains("cavernreborn:fissured_stone"));
        assertTrue(arrayStrings(aquamarineGemTag.getAsJsonArray("values")).contains("cavernreborn:aquamarine"));
        assertTrue(arrayStrings(aquamarineOreTag.getAsJsonArray("values")).contains("cavernreborn:aquamarine_ore"));
        assertTrue(arrayStrings(aquamarineStorageTag.getAsJsonArray("values")).contains("cavernreborn:aquamarine_block"));
        assertTrue(arrayStrings(magniteIngotTag.getAsJsonArray("values")).contains("cavernreborn:magnite_ingot"));
        assertTrue(arrayStrings(magniteOreTag.getAsJsonArray("values")).contains("cavernreborn:magnite_ore"));
        assertTrue(arrayStrings(magniteStorageTag.getAsJsonArray("values")).contains("cavernreborn:magnite_block"));
        assertTrue(arrayStrings(hexciteOreTag.getAsJsonArray("values")).contains("cavernreborn:hexcite_ore"));
        assertTrue(arrayStrings(hexciteGemTag.getAsJsonArray("values")).contains("cavernreborn:hexcite"));
        assertTrue(arrayStrings(hexciteStorageTag.getAsJsonArray("values")).contains("cavernreborn:hexcite_block"));
        assertTrue(arrayStrings(hexciteItemStorageTag.getAsJsonArray("values")).contains("cavernreborn:hexcite_block"));
    }

    @Test
    void oreFamilyResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/blockstates/aquamarine_ore.json"), "assets/cavernreborn/blockstates/aquamarine_ore.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/blockstates/magnite_ore.json"), "assets/cavernreborn/blockstates/magnite_ore.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/block/aquamarine_block.json"), "assets/cavernreborn/models/block/aquamarine_block.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/aquamarine.json"), "assets/cavernreborn/models/item/aquamarine.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_ingot.json"), "assets/cavernreborn/models/item/magnite_ingot.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/blockstates/hexcite_ore.json"), "assets/cavernreborn/blockstates/hexcite_ore.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/blockstates/randomite_ore.json"), "assets/cavernreborn/blockstates/randomite_ore.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/block/hexcite_block.json"), "assets/cavernreborn/models/block/hexcite_block.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/hexcite.json"), "assets/cavernreborn/models/item/hexcite.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/loot_table/blocks/aquamarine_ore.json"), "data/cavernreborn/loot_table/blocks/aquamarine_ore.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/loot_table/blocks/magnite_ore.json"), "data/cavernreborn/loot_table/blocks/magnite_ore.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/loot_table/blocks/randomite_ore.json"), "data/cavernreborn/loot_table/blocks/randomite_ore.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/loot_table/blocks/fissured_stone.json"), "data/cavernreborn/loot_table/blocks/fissured_stone.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/aquamarine_block.json"), "data/cavernreborn/recipe/aquamarine_block.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/magnite_ingot_from_smelting.json"), "data/cavernreborn/recipe/magnite_ingot_from_smelting.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/hexcite_from_smelting.json"), "data/cavernreborn/recipe/hexcite_from_smelting.json");
        assertClassPathOrigin(resourceUrl("data/c/tags/items/gems/aquamarine.json"), "data/c/tags/items/gems/aquamarine.json");
        assertClassPathOrigin(resourceUrl("data/c/tags/items/ingots/magnite.json"), "data/c/tags/items/ingots/magnite.json");
        assertClassPathOrigin(resourceUrl("data/c/tags/items/gems/hexcite.json"), "data/c/tags/items/gems/hexcite.json");
        assertClassPathOrigin(resourceUrl("data/c/tags/items/storage_blocks/hexcite.json"), "data/c/tags/items/storage_blocks/hexcite.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/aquamarine_ore.png"), "assets/cavernreborn/textures/block/aquamarine_ore.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/magnite_ore.png"), "assets/cavernreborn/textures/block/magnite_ore.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/aquamarine.png"), "assets/cavernreborn/textures/item/aquamarine.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_ingot.png"), "assets/cavernreborn/textures/item/magnite_ingot.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/hexcite_ore.png"), "assets/cavernreborn/textures/block/hexcite_ore.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/randomite_ore.png"), "assets/cavernreborn/textures/block/randomite_ore.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/fissured_stone.png"), "assets/cavernreborn/textures/block/fissured_stone.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/hexcite.png"), "assets/cavernreborn/textures/item/hexcite.png");
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

    private static void assertInOrder(String source, List<String> snippets) {
        int previousIndex = -1;
        for (String snippet : snippets) {
            int currentIndex = source.indexOf(snippet);
            assertTrue(currentIndex >= 0, "Missing snippet: " + snippet);
            assertTrue(currentIndex > previousIndex, "Expected ordered snippet: " + snippet);
            previousIndex = currentIndex;
        }
    }

    private static String variantModel(JsonObject blockstate) {
        return blockstate.getAsJsonObject("variants").getAsJsonObject("").get("model").getAsString();
    }

    private static JsonObject firstLootEntry(JsonObject lootTable) {
        return lootEntries(lootTable).getFirst();
    }

    private static List<JsonObject> lootEntries(JsonObject lootTable) {
        return StreamSupport.stream(
                lootTable.getAsJsonArray("pools").get(0).getAsJsonObject().getAsJsonArray("entries").spliterator(),
                false
            )
            .map(JsonElement::getAsJsonObject)
            .toList();
    }

    private static List<String> entryNames(List<JsonObject> entries) {
        return entries.stream()
            .map(entry -> entry.get("name").getAsString())
            .toList();
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
