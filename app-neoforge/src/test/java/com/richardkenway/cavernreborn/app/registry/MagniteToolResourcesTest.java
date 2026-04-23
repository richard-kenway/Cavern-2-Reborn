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

class MagniteToolResourcesTest {
    @Test
    void magniteToolsRegisterWithBrittleTierAndCreativeTabOrder() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String tiersSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModToolTiers.java"
        );

        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_sword\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_pickaxe\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_axe\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_shovel\""));
        assertTrue(registriesSource.contains("ModToolTiers.MAGNITE"));
        assertInOrder(registriesSource, List.of(
            "output.accept(MAGNITE_INGOT.get())",
            "output.accept(MAGNITE_PICKAXE.get())",
            "output.accept(MAGNITE_AXE.get())",
            "output.accept(MAGNITE_SHOVEL.get())",
            "output.accept(MAGNITE_SWORD.get())",
            "output.accept(HEXCITE.get())"
        ));

        assertTrue(tiersSource.contains("INCORRECT_FOR_MAGNITE_TOOL"));
        assertTrue(tiersSource.contains("48"));
        assertTrue(tiersSource.contains("18.0F"));
        assertTrue(tiersSource.contains("2.5F"));
        assertTrue(tiersSource.contains("24"));
        assertTrue(tiersSource.contains("Ingredient.of(ModRegistries.MAGNITE_INGOT.get())"));
    }

    @Test
    void magniteToolResourcesCoverLangModelsRecipesAndTags() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject swordModel = readJsonResource("assets/cavernreborn/models/item/magnite_sword.json");
        JsonObject pickaxeModel = readJsonResource("assets/cavernreborn/models/item/magnite_pickaxe.json");
        JsonObject axeModel = readJsonResource("assets/cavernreborn/models/item/magnite_axe.json");
        JsonObject shovelModel = readJsonResource("assets/cavernreborn/models/item/magnite_shovel.json");
        JsonObject swordRecipe = readJsonResource("data/cavernreborn/recipe/magnite_sword.json");
        JsonObject pickaxeRecipe = readJsonResource("data/cavernreborn/recipe/magnite_pickaxe.json");
        JsonObject axeRecipe = readJsonResource("data/cavernreborn/recipe/magnite_axe.json");
        JsonObject shovelRecipe = readJsonResource("data/cavernreborn/recipe/magnite_shovel.json");
        JsonObject magniteToolsTag = readJsonResource("data/cavernreborn/tags/item/magnite_tools.json");
        JsonObject magniteToolsCompatTag = readJsonResource("data/cavernreborn/tags/items/magnite_tools.json");
        JsonObject incorrectToolTag = readJsonResource("data/cavernreborn/tags/block/incorrect_for_magnite_tool.json");
        JsonObject incorrectToolCompatTag = readJsonResource("data/cavernreborn/tags/blocks/incorrect_for_magnite_tool.json");
        JsonObject pickaxesTag = readJsonResource("data/minecraft/tags/item/pickaxes.json");
        JsonObject axesTag = readJsonResource("data/minecraft/tags/item/axes.json");
        JsonObject shovelsTag = readJsonResource("data/minecraft/tags/item/shovels.json");
        JsonObject swordsTag = readJsonResource("data/minecraft/tags/item/swords.json");
        JsonObject miningEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/mining.json");
        JsonObject miningLootEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/mining_loot.json");
        JsonObject durabilityEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/durability.json");
        JsonObject swordEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/sword.json");
        JsonObject sharpWeaponEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/sharp_weapon.json");
        JsonObject weaponEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/weapon.json");

        assertEquals("Magnite Sword", lang.get("item.cavernreborn.magnite_sword").getAsString());
        assertEquals("Magnite Pickaxe", lang.get("item.cavernreborn.magnite_pickaxe").getAsString());
        assertEquals("Magnite Axe", lang.get("item.cavernreborn.magnite_axe").getAsString());
        assertEquals("Magnite Shovel", lang.get("item.cavernreborn.magnite_shovel").getAsString());

        assertHandheldModel(swordModel, "cavernreborn:item/magnite_sword");
        assertHandheldModel(pickaxeModel, "cavernreborn:item/magnite_pickaxe");
        assertHandheldModel(axeModel, "cavernreborn:item/magnite_axe");
        assertHandheldModel(shovelModel, "cavernreborn:item/magnite_shovel");

        assertToolRecipe(swordRecipe, "cavernreborn:magnite_sword");
        assertToolRecipe(pickaxeRecipe, "cavernreborn:magnite_pickaxe");
        assertToolRecipe(axeRecipe, "cavernreborn:magnite_axe");
        assertToolRecipe(shovelRecipe, "cavernreborn:magnite_shovel");

        List<String> magniteToolValues = arrayStrings(magniteToolsTag.getAsJsonArray("values"));
        assertEquals(magniteToolValues, arrayStrings(magniteToolsCompatTag.getAsJsonArray("values")));
        assertEquals(List.of(
            "cavernreborn:magnite_sword",
            "cavernreborn:magnite_pickaxe",
            "cavernreborn:magnite_axe",
            "cavernreborn:magnite_shovel"
        ), magniteToolValues);

        List<String> incorrectToolValues = arrayStrings(incorrectToolTag.getAsJsonArray("values"));
        assertTrue(incorrectToolValues.contains("#minecraft:incorrect_for_diamond_tool"));
        assertEquals(incorrectToolValues, arrayStrings(incorrectToolCompatTag.getAsJsonArray("values")));

        assertTagContainsAll(pickaxesTag, List.of("cavernreborn:magnite_pickaxe"));
        assertTagContainsAll(axesTag, List.of("cavernreborn:magnite_axe"));
        assertTagContainsAll(shovelsTag, List.of("cavernreborn:magnite_shovel"));
        assertTagContainsAll(swordsTag, List.of("cavernreborn:magnite_sword"));
        assertTagContainsAll(miningEnchantableTag, List.of(
            "cavernreborn:magnite_pickaxe",
            "cavernreborn:magnite_axe",
            "cavernreborn:magnite_shovel"
        ));
        assertTagContainsAll(miningLootEnchantableTag, List.of(
            "cavernreborn:magnite_pickaxe",
            "cavernreborn:magnite_axe",
            "cavernreborn:magnite_shovel"
        ));
        assertTagContainsAll(durabilityEnchantableTag, List.of(
            "cavernreborn:magnite_pickaxe",
            "cavernreborn:magnite_axe",
            "cavernreborn:magnite_shovel",
            "cavernreborn:magnite_sword"
        ));
        assertTagContainsAll(swordEnchantableTag, List.of("cavernreborn:magnite_sword"));
        assertTagContainsAll(sharpWeaponEnchantableTag, List.of("cavernreborn:magnite_axe", "cavernreborn:magnite_sword"));
        assertTagContainsAll(weaponEnchantableTag, List.of("cavernreborn:magnite_axe", "cavernreborn:magnite_sword"));
    }

    @Test
    void magniteToolResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_sword.json"), "assets/cavernreborn/models/item/magnite_sword.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_pickaxe.json"), "assets/cavernreborn/models/item/magnite_pickaxe.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_axe.json"), "assets/cavernreborn/models/item/magnite_axe.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_shovel.json"), "assets/cavernreborn/models/item/magnite_shovel.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_sword.png"), "assets/cavernreborn/textures/item/magnite_sword.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_pickaxe.png"), "assets/cavernreborn/textures/item/magnite_pickaxe.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_axe.png"), "assets/cavernreborn/textures/item/magnite_axe.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_shovel.png"), "assets/cavernreborn/textures/item/magnite_shovel.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/magnite_sword.json"), "data/cavernreborn/recipe/magnite_sword.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/magnite_pickaxe.json"), "data/cavernreborn/recipe/magnite_pickaxe.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/magnite_axe.json"), "data/cavernreborn/recipe/magnite_axe.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/magnite_shovel.json"), "data/cavernreborn/recipe/magnite_shovel.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/item/magnite_tools.json"), "data/cavernreborn/tags/item/magnite_tools.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/items/magnite_tools.json"), "data/cavernreborn/tags/items/magnite_tools.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/block/incorrect_for_magnite_tool.json"), "data/cavernreborn/tags/block/incorrect_for_magnite_tool.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/blocks/incorrect_for_magnite_tool.json"), "data/cavernreborn/tags/blocks/incorrect_for_magnite_tool.json");
    }

    private static void assertToolRecipe(JsonObject recipe, String resultId) {
        assertEquals("minecraft:crafting_shaped", recipe.get("type").getAsString());
        assertEquals("equipment", recipe.get("category").getAsString());
        JsonObject key = recipe.getAsJsonObject("key");
        assertEquals("cavernreborn:magnite_ingot", key.getAsJsonObject("#").get("item").getAsString());
        assertEquals("minecraft:stick", key.getAsJsonObject("|").get("item").getAsString());
        assertEquals(resultId, recipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(1, recipe.getAsJsonObject("result").get("count").getAsInt());
    }

    private static void assertHandheldModel(JsonObject model, String texturePath) {
        assertEquals("minecraft:item/handheld", model.get("parent").getAsString());
        assertEquals(texturePath, model.getAsJsonObject("textures").get("layer0").getAsString());
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
        InputStream inputStream = MagniteToolResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = MagniteToolResourcesTest.class.getClassLoader().getResource(path);
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
