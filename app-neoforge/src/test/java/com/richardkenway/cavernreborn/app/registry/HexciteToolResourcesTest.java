package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class HexciteToolResourcesTest {
    @Test
    void hexciteToolsRegisterWithBoundedTierAndCreativeTabOrder() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String tiersSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModToolTiers.java"
        );

        assertTrue(registriesSource.contains("ITEMS.register(\"hexcite_pickaxe\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"hexcite_axe\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"hexcite_shovel\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"hexcite_hoe\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"hexcite_sword\""));
        assertTrue(registriesSource.contains("new PickaxeItem("));
        assertTrue(registriesSource.contains("new AxeItem("));
        assertTrue(registriesSource.contains("new ShovelItem("));
        assertTrue(registriesSource.contains("new HoeItem("));
        assertTrue(registriesSource.contains("new SwordItem("));

        assertTrue(tiersSource.contains("INCORRECT_FOR_HEXCITE_TOOL"));
        assertTrue(tiersSource.contains("1024"));
        assertTrue(tiersSource.contains("8.0F"));
        assertTrue(tiersSource.contains("3.0F"));
        assertTrue(tiersSource.contains("18"));
        assertTrue(tiersSource.contains("Ingredient.of(ModRegistries.HEXCITE.get())"));

        assertInOrder(registriesSource, List.of(
            "output.accept(HEXCITE.get())",
            "output.accept(HEXCITE_PICKAXE.get())",
            "output.accept(HEXCITE_AXE.get())",
            "output.accept(HEXCITE_SHOVEL.get())",
            "output.accept(HEXCITE_HOE.get())",
            "output.accept(HEXCITE_SWORD.get())"
        ));
    }

    @Test
    void hexciteToolResourcesCoverLangModelsRecipesAndTags() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject pickaxeModel = readJsonResource("assets/cavernreborn/models/item/hexcite_pickaxe.json");
        JsonObject axeModel = readJsonResource("assets/cavernreborn/models/item/hexcite_axe.json");
        JsonObject shovelModel = readJsonResource("assets/cavernreborn/models/item/hexcite_shovel.json");
        JsonObject hoeModel = readJsonResource("assets/cavernreborn/models/item/hexcite_hoe.json");
        JsonObject swordModel = readJsonResource("assets/cavernreborn/models/item/hexcite_sword.json");
        JsonObject pickaxeRecipe = readJsonResource("data/cavernreborn/recipe/hexcite_pickaxe.json");
        JsonObject axeRecipe = readJsonResource("data/cavernreborn/recipe/hexcite_axe.json");
        JsonObject shovelRecipe = readJsonResource("data/cavernreborn/recipe/hexcite_shovel.json");
        JsonObject hoeRecipe = readJsonResource("data/cavernreborn/recipe/hexcite_hoe.json");
        JsonObject swordRecipe = readJsonResource("data/cavernreborn/recipe/hexcite_sword.json");
        JsonObject incorrectForHexciteTool = readJsonResource("data/cavernreborn/tags/blocks/incorrect_for_hexcite_tool.json");
        JsonObject incorrectForHexciteToolCompat = readJsonResource("data/cavernreborn/tags/block/incorrect_for_hexcite_tool.json");
        JsonObject pickaxesTag = readJsonResource("data/minecraft/tags/item/pickaxes.json");
        JsonObject axesTag = readJsonResource("data/minecraft/tags/item/axes.json");
        JsonObject shovelsTag = readJsonResource("data/minecraft/tags/item/shovels.json");
        JsonObject hoesTag = readJsonResource("data/minecraft/tags/item/hoes.json");
        JsonObject swordsTag = readJsonResource("data/minecraft/tags/item/swords.json");
        JsonObject miningEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/mining.json");
        JsonObject miningLootEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/mining_loot.json");
        JsonObject durabilityEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/durability.json");
        JsonObject swordEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/sword.json");
        JsonObject sharpWeaponEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/sharp_weapon.json");
        JsonObject weaponEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/weapon.json");

        assertEquals("Hexcite Pickaxe", lang.get("item.cavernreborn.hexcite_pickaxe").getAsString());
        assertEquals("Hexcite Axe", lang.get("item.cavernreborn.hexcite_axe").getAsString());
        assertEquals("Hexcite Shovel", lang.get("item.cavernreborn.hexcite_shovel").getAsString());
        assertEquals("Hexcite Hoe", lang.get("item.cavernreborn.hexcite_hoe").getAsString());
        assertEquals("Hexcite Sword", lang.get("item.cavernreborn.hexcite_sword").getAsString());

        assertHandheldModel(pickaxeModel, "cavernreborn:item/hexcite_pickaxe");
        assertHandheldModel(axeModel, "cavernreborn:item/hexcite_axe");
        assertHandheldModel(shovelModel, "cavernreborn:item/hexcite_shovel");
        assertHandheldModel(hoeModel, "cavernreborn:item/hexcite_hoe");
        assertHandheldModel(swordModel, "cavernreborn:item/hexcite_sword");

        assertToolRecipe(pickaxeRecipe, "cavernreborn:hexcite_pickaxe");
        assertToolRecipe(axeRecipe, "cavernreborn:hexcite_axe");
        assertToolRecipe(shovelRecipe, "cavernreborn:hexcite_shovel");
        assertToolRecipe(hoeRecipe, "cavernreborn:hexcite_hoe");
        assertToolRecipe(swordRecipe, "cavernreborn:hexcite_sword");

        List<String> incorrectTagValues = arrayStrings(incorrectForHexciteTool.getAsJsonArray("values"));
        assertTrue(incorrectTagValues.contains("#minecraft:incorrect_for_diamond_tool"));
        assertEquals(incorrectTagValues, arrayStrings(incorrectForHexciteToolCompat.getAsJsonArray("values")));

        assertTagContainsExactly(pickaxesTag, List.of("cavernreborn:aquamarine_pickaxe", "cavernreborn:hexcite_pickaxe"));
        assertTagContainsExactly(axesTag, List.of("cavernreborn:aquamarine_axe", "cavernreborn:hexcite_axe"));
        assertTagContainsExactly(shovelsTag, List.of("cavernreborn:aquamarine_shovel", "cavernreborn:hexcite_shovel"));
        assertTagContainsExactly(hoesTag, List.of("cavernreborn:hexcite_hoe"));
        assertTagContainsExactly(swordsTag, List.of("cavernreborn:hexcite_sword"));
        assertTagContainsExactly(miningEnchantableTag, List.of(
            "cavernreborn:aquamarine_pickaxe",
            "cavernreborn:aquamarine_axe",
            "cavernreborn:aquamarine_shovel",
            "cavernreborn:hexcite_pickaxe",
            "cavernreborn:hexcite_axe",
            "cavernreborn:hexcite_shovel",
            "cavernreborn:hexcite_hoe"
        ));
        assertTagContainsExactly(miningLootEnchantableTag, List.of(
            "cavernreborn:aquamarine_pickaxe",
            "cavernreborn:aquamarine_axe",
            "cavernreborn:aquamarine_shovel",
            "cavernreborn:hexcite_pickaxe",
            "cavernreborn:hexcite_axe",
            "cavernreborn:hexcite_shovel",
            "cavernreborn:hexcite_hoe"
        ));
        assertTagContainsAll(durabilityEnchantableTag, List.of(
            "cavernreborn:aquamarine_pickaxe",
            "cavernreborn:aquamarine_axe",
            "cavernreborn:aquamarine_shovel",
            "cavernreborn:hexcite_pickaxe",
            "cavernreborn:hexcite_axe",
            "cavernreborn:hexcite_shovel",
            "cavernreborn:hexcite_hoe",
            "cavernreborn:hexcite_sword"
        ));
        assertTagContainsExactly(swordEnchantableTag, List.of("cavernreborn:hexcite_sword"));
        assertTagContainsExactly(sharpWeaponEnchantableTag, List.of("cavernreborn:hexcite_axe", "cavernreborn:hexcite_sword"));
        assertTagContainsExactly(weaponEnchantableTag, List.of("cavernreborn:hexcite_axe", "cavernreborn:hexcite_sword"));
    }

    @Test
    void hexciteToolResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/hexcite_pickaxe.json"), "assets/cavernreborn/models/item/hexcite_pickaxe.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/hexcite_axe.json"), "assets/cavernreborn/models/item/hexcite_axe.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/hexcite_shovel.json"), "assets/cavernreborn/models/item/hexcite_shovel.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/hexcite_hoe.json"), "assets/cavernreborn/models/item/hexcite_hoe.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/hexcite_sword.json"), "assets/cavernreborn/models/item/hexcite_sword.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/hexcite_pickaxe.png"), "assets/cavernreborn/textures/item/hexcite_pickaxe.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/hexcite_axe.png"), "assets/cavernreborn/textures/item/hexcite_axe.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/hexcite_shovel.png"), "assets/cavernreborn/textures/item/hexcite_shovel.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/hexcite_hoe.png"), "assets/cavernreborn/textures/item/hexcite_hoe.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/hexcite_sword.png"), "assets/cavernreborn/textures/item/hexcite_sword.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/hexcite_pickaxe.json"), "data/cavernreborn/recipe/hexcite_pickaxe.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/hexcite_axe.json"), "data/cavernreborn/recipe/hexcite_axe.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/hexcite_shovel.json"), "data/cavernreborn/recipe/hexcite_shovel.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/hexcite_hoe.json"), "data/cavernreborn/recipe/hexcite_hoe.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/hexcite_sword.json"), "data/cavernreborn/recipe/hexcite_sword.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/blocks/incorrect_for_hexcite_tool.json"), "data/cavernreborn/tags/blocks/incorrect_for_hexcite_tool.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/block/incorrect_for_hexcite_tool.json"), "data/cavernreborn/tags/block/incorrect_for_hexcite_tool.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/pickaxes.json"), "data/minecraft/tags/item/pickaxes.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/axes.json"), "data/minecraft/tags/item/axes.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/shovels.json"), "data/minecraft/tags/item/shovels.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/hoes.json"), "data/minecraft/tags/item/hoes.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/swords.json"), "data/minecraft/tags/item/swords.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/mining.json"), "data/minecraft/tags/item/enchantable/mining.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/mining_loot.json"), "data/minecraft/tags/item/enchantable/mining_loot.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/durability.json"), "data/minecraft/tags/item/enchantable/durability.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/sword.json"), "data/minecraft/tags/item/enchantable/sword.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/sharp_weapon.json"), "data/minecraft/tags/item/enchantable/sharp_weapon.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/weapon.json"), "data/minecraft/tags/item/enchantable/weapon.json");
    }

    private static void assertToolRecipe(JsonObject recipe, String resultId) {
        assertEquals("minecraft:crafting_shaped", recipe.get("type").getAsString());
        assertEquals("equipment", recipe.get("category").getAsString());
        JsonObject key = recipe.getAsJsonObject("key");
        assertEquals("cavernreborn:hexcite", key.getAsJsonObject("#").get("item").getAsString());
        assertEquals("minecraft:stick", key.getAsJsonObject("|").get("item").getAsString());
        assertEquals(resultId, recipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(1, recipe.getAsJsonObject("result").get("count").getAsInt());
    }

    private static void assertHandheldModel(JsonObject model, String texturePath) {
        assertEquals("minecraft:item/handheld", model.get("parent").getAsString());
        assertEquals(texturePath, model.getAsJsonObject("textures").get("layer0").getAsString());
    }

    private static void assertTagContainsExactly(JsonObject tag, Collection<String> expectedValues) {
        assertTrue(!tag.get("replace").getAsBoolean(), "Expected additive tag merge");
        assertEquals(List.copyOf(expectedValues), arrayStrings(tag.getAsJsonArray("values")));
    }

    private static void assertTagContainsAll(JsonObject tag, Collection<String> expectedValues) {
        assertTrue(!tag.get("replace").getAsBoolean(), "Expected additive tag merge");
        List<String> actualValues = arrayStrings(tag.getAsJsonArray("values"));
        assertTrue(actualValues.containsAll(expectedValues), "Expected tag values " + actualValues + " to contain " + expectedValues);
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = HexciteToolResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = HexciteToolResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertClassPathOrigin(URL url, String expectedPathSuffix) {
        assertTrue(url.toString().contains(expectedPathSuffix), "Expected runtime classpath resource to contain " + expectedPathSuffix + " but got " + url);
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

    private static void assertInOrder(String source, List<String> needles) {
        int index = -1;

        for (String needle : needles) {
            int next = source.indexOf(needle, index + 1);
            assertTrue(next >= 0, "Missing source fragment: " + needle);
            assertTrue(next > index, "Expected source fragment order for: " + needle);
            index = next;
        }
    }

    private static List<String> arrayStrings(JsonArray array) {
        return StreamSupport.stream(array.spliterator(), false)
            .map(element -> element.getAsString())
            .toList();
    }
}
