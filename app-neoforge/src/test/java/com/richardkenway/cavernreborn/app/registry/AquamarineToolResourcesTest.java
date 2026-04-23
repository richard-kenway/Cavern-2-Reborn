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

class AquamarineToolResourcesTest {
    @Test
    void aquamarineToolsRegisterWithBoundedTierAndCreativeTabOrder() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String tiersSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModToolTiers.java"
        );

        assertTrue(registriesSource.contains("ITEMS.register(\"aquamarine_pickaxe\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"aquamarine_axe\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"aquamarine_shovel\""));
        assertTrue(registriesSource.contains("ModToolTiers.AQUAMARINE"));
        assertInOrder(registriesSource, List.of(
            "output.accept(AQUAMARINE.get())",
            "output.accept(AQUAMARINE_PICKAXE.get())",
            "output.accept(AQUAMARINE_AXE.get())",
            "output.accept(AQUAMARINE_SHOVEL.get())",
            "output.accept(MAGNITE_INGOT.get())"
        ));

        assertTrue(tiersSource.contains("INCORRECT_FOR_AQUAMARINE_TOOL"));
        assertTrue(tiersSource.contains("200"));
        assertTrue(tiersSource.contains("8.0F"));
        assertTrue(tiersSource.contains("1.5F"));
        assertTrue(tiersSource.contains("15"));
        assertTrue(tiersSource.contains("Ingredient.of(ModRegistries.AQUAMARINE.get())"));
    }

    @Test
    void aquamarineToolResourcesCoverLangModelsRecipesAndTags() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject pickaxeModel = readJsonResource("assets/cavernreborn/models/item/aquamarine_pickaxe.json");
        JsonObject axeModel = readJsonResource("assets/cavernreborn/models/item/aquamarine_axe.json");
        JsonObject shovelModel = readJsonResource("assets/cavernreborn/models/item/aquamarine_shovel.json");
        JsonObject pickaxeRecipe = readJsonResource("data/cavernreborn/recipe/aquamarine_pickaxe.json");
        JsonObject axeRecipe = readJsonResource("data/cavernreborn/recipe/aquamarine_axe.json");
        JsonObject shovelRecipe = readJsonResource("data/cavernreborn/recipe/aquamarine_shovel.json");
        JsonObject aquaToolsTag = readJsonResource("data/cavernreborn/tags/item/aqua_tools.json");
        JsonObject aquaToolsCompatTag = readJsonResource("data/cavernreborn/tags/items/aqua_tools.json");
        JsonObject incorrectToolTag = readJsonResource("data/cavernreborn/tags/block/incorrect_for_aquamarine_tool.json");
        JsonObject incorrectToolCompatTag = readJsonResource("data/cavernreborn/tags/blocks/incorrect_for_aquamarine_tool.json");
        JsonObject pickaxesTag = readJsonResource("data/minecraft/tags/item/pickaxes.json");
        JsonObject axesTag = readJsonResource("data/minecraft/tags/item/axes.json");
        JsonObject shovelsTag = readJsonResource("data/minecraft/tags/item/shovels.json");
        JsonObject miningEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/mining.json");
        JsonObject miningLootEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/mining_loot.json");
        JsonObject durabilityEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/durability.json");

        assertEquals("Aquamarine Pickaxe", lang.get("item.cavernreborn.aquamarine_pickaxe").getAsString());
        assertEquals("Aquamarine Axe", lang.get("item.cavernreborn.aquamarine_axe").getAsString());
        assertEquals("Aquamarine Shovel", lang.get("item.cavernreborn.aquamarine_shovel").getAsString());

        assertHandheldModel(pickaxeModel, "cavernreborn:item/aquamarine_pickaxe");
        assertHandheldModel(axeModel, "cavernreborn:item/aquamarine_axe");
        assertHandheldModel(shovelModel, "cavernreborn:item/aquamarine_shovel");

        assertToolRecipe(pickaxeRecipe, "cavernreborn:aquamarine_pickaxe");
        assertToolRecipe(axeRecipe, "cavernreborn:aquamarine_axe");
        assertToolRecipe(shovelRecipe, "cavernreborn:aquamarine_shovel");

        List<String> aquaToolValues = arrayStrings(aquaToolsTag.getAsJsonArray("values"));
        assertEquals(aquaToolValues, arrayStrings(aquaToolsCompatTag.getAsJsonArray("values")));
        assertEquals(List.of(
            "cavernreborn:aquamarine_pickaxe",
            "cavernreborn:aquamarine_axe",
            "cavernreborn:aquamarine_shovel"
        ), aquaToolValues);

        List<String> incorrectToolValues = arrayStrings(incorrectToolTag.getAsJsonArray("values"));
        assertTrue(incorrectToolValues.contains("#minecraft:incorrect_for_iron_tool"));
        assertEquals(incorrectToolValues, arrayStrings(incorrectToolCompatTag.getAsJsonArray("values")));

        assertTagContainsAll(pickaxesTag, List.of("cavernreborn:aquamarine_pickaxe"));
        assertTagContainsAll(axesTag, List.of("cavernreborn:aquamarine_axe"));
        assertTagContainsAll(shovelsTag, List.of("cavernreborn:aquamarine_shovel"));
        assertTagContainsAll(miningEnchantableTag, List.of(
            "cavernreborn:aquamarine_pickaxe",
            "cavernreborn:aquamarine_axe",
            "cavernreborn:aquamarine_shovel"
        ));
        assertTagContainsAll(miningLootEnchantableTag, List.of(
            "cavernreborn:aquamarine_pickaxe",
            "cavernreborn:aquamarine_axe",
            "cavernreborn:aquamarine_shovel"
        ));
        assertTagContainsAll(durabilityEnchantableTag, List.of(
            "cavernreborn:aquamarine_pickaxe",
            "cavernreborn:aquamarine_axe",
            "cavernreborn:aquamarine_shovel"
        ));
    }

    @Test
    void aquamarineToolResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/aquamarine_pickaxe.json"), "assets/cavernreborn/models/item/aquamarine_pickaxe.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/aquamarine_axe.json"), "assets/cavernreborn/models/item/aquamarine_axe.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/aquamarine_shovel.json"), "assets/cavernreborn/models/item/aquamarine_shovel.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/aquamarine_pickaxe.png"), "assets/cavernreborn/textures/item/aquamarine_pickaxe.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/aquamarine_axe.png"), "assets/cavernreborn/textures/item/aquamarine_axe.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/aquamarine_shovel.png"), "assets/cavernreborn/textures/item/aquamarine_shovel.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/aquamarine_pickaxe.json"), "data/cavernreborn/recipe/aquamarine_pickaxe.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/aquamarine_axe.json"), "data/cavernreborn/recipe/aquamarine_axe.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/aquamarine_shovel.json"), "data/cavernreborn/recipe/aquamarine_shovel.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/item/aqua_tools.json"), "data/cavernreborn/tags/item/aqua_tools.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/items/aqua_tools.json"), "data/cavernreborn/tags/items/aqua_tools.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/block/incorrect_for_aquamarine_tool.json"), "data/cavernreborn/tags/block/incorrect_for_aquamarine_tool.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/blocks/incorrect_for_aquamarine_tool.json"), "data/cavernreborn/tags/blocks/incorrect_for_aquamarine_tool.json");
    }

    private static void assertToolRecipe(JsonObject recipe, String resultId) {
        assertEquals("minecraft:crafting_shaped", recipe.get("type").getAsString());
        assertEquals("equipment", recipe.get("category").getAsString());
        JsonObject key = recipe.getAsJsonObject("key");
        assertEquals("cavernreborn:aquamarine", key.getAsJsonObject("#").get("item").getAsString());
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
        InputStream inputStream = AquamarineToolResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = AquamarineToolResourcesTest.class.getClassLoader().getResource(path);
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
