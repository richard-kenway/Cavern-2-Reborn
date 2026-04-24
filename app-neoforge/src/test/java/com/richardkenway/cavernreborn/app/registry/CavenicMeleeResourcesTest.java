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
import java.util.Collection;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavenicMeleeResourcesTest {
    @Test
    void cavenicMeleeRegistersWithTierAndCreativeTabOrder() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String tiersSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModToolTiers.java"
        );

        assertTrue(registriesSource.contains("ITEMS.register(\"cavenic_sword\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"cavenic_axe\""));
        assertTrue(registriesSource.contains("new CavenicSwordItem("));
        assertTrue(registriesSource.contains("new CavenicAxeItem("));
        assertTrue(registriesSource.contains("ModToolTiers.CAVENIC"));
        assertTrue(registriesSource.contains("SwordItem.createAttributes(ModToolTiers.CAVENIC, 3, -1.0F)"));
        assertTrue(registriesSource.contains("AxeItem.createAttributes(ModToolTiers.CAVENIC, 8.0F, -3.05F)"));
        assertFalse(registriesSource.contains("cavenic_bow"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_ORB.get())",
            "output.accept(CAVENIC_SWORD.get())",
            "output.accept(CAVENIC_AXE.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(tiersSource.contains("INCORRECT_FOR_CAVENIC_TOOL"));
        assertTrue(tiersSource.contains("278"));
        assertTrue(tiersSource.contains("7.0F"));
        assertTrue(tiersSource.contains("2.5F"));
        assertTrue(tiersSource.contains("30"));
        assertTrue(tiersSource.contains("Ingredient.of(ModRegistries.CAVENIC_ORB.get())"));
    }

    @Test
    void cavenicMeleeResourcesCoverLangModelsRecipesAndTags() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject swordModel = readJsonResource("assets/cavernreborn/models/item/cavenic_sword.json");
        JsonObject axeModel = readJsonResource("assets/cavernreborn/models/item/cavenic_axe.json");
        JsonObject swordRecipe = readJsonResource("data/cavernreborn/recipe/cavenic_sword.json");
        JsonObject axeRecipe = readJsonResource("data/cavernreborn/recipe/cavenic_axe.json");
        JsonObject cavenicItemsTag = readJsonResource("data/cavernreborn/tags/item/cavenic_items.json");
        JsonObject cavenicItemsCompatTag = readJsonResource("data/cavernreborn/tags/items/cavenic_items.json");
        JsonObject commonOrbTag = readJsonResource("data/c/tags/items/orbs/cavenic.json");
        JsonObject incorrectToolTag = readJsonResource("data/cavernreborn/tags/block/incorrect_for_cavenic_tool.json");
        JsonObject incorrectToolCompatTag = readJsonResource("data/cavernreborn/tags/blocks/incorrect_for_cavenic_tool.json");
        JsonObject axesTag = readJsonResource("data/minecraft/tags/item/axes.json");
        JsonObject swordsTag = readJsonResource("data/minecraft/tags/item/swords.json");
        JsonObject miningEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/mining.json");
        JsonObject miningLootEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/mining_loot.json");
        JsonObject durabilityEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/durability.json");
        JsonObject swordEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/sword.json");
        JsonObject sharpWeaponEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/sharp_weapon.json");
        JsonObject weaponEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/weapon.json");

        assertEquals("Cavenic Sword", lang.get("item.cavernreborn.cavenic_sword").getAsString());
        assertEquals("Cavenic Axe", lang.get("item.cavernreborn.cavenic_axe").getAsString());

        assertHandheldModel(swordModel, "cavernreborn:item/cavenic_sword");
        assertHandheldModel(axeModel, "cavernreborn:item/cavenic_axe");
        assertCavenicRecipe(swordRecipe, "cavernreborn:cavenic_sword");
        assertCavenicRecipe(axeRecipe, "cavernreborn:cavenic_axe");

        List<String> cavenicItemValues = arrayStrings(cavenicItemsTag.getAsJsonArray("values"));
        assertEquals(cavenicItemValues, arrayStrings(cavenicItemsCompatTag.getAsJsonArray("values")));
        assertEquals(List.of(
            "cavernreborn:cavenic_shroom",
            "cavernreborn:cavenic_orb",
            "cavernreborn:cavenic_sword",
            "cavernreborn:cavenic_axe"
        ), cavenicItemValues);
        assertEquals(List.of("cavernreborn:cavenic_orb"), arrayStrings(commonOrbTag.getAsJsonArray("values")));

        List<String> incorrectToolValues = arrayStrings(incorrectToolTag.getAsJsonArray("values"));
        assertTrue(incorrectToolValues.contains("#minecraft:incorrect_for_iron_tool"));
        assertEquals(incorrectToolValues, arrayStrings(incorrectToolCompatTag.getAsJsonArray("values")));

        assertTagContainsAll(axesTag, List.of("cavernreborn:cavenic_axe"));
        assertTagContainsAll(swordsTag, List.of("cavernreborn:cavenic_sword"));
        assertTagContainsAll(miningEnchantableTag, List.of("cavernreborn:cavenic_axe"));
        assertTagContainsAll(miningLootEnchantableTag, List.of("cavernreborn:cavenic_axe"));
        assertTagContainsAll(durabilityEnchantableTag, List.of("cavernreborn:cavenic_axe", "cavernreborn:cavenic_sword"));
        assertTagContainsAll(swordEnchantableTag, List.of("cavernreborn:cavenic_sword"));
        assertTagContainsAll(sharpWeaponEnchantableTag, List.of("cavernreborn:cavenic_axe", "cavernreborn:cavenic_sword"));
        assertTagContainsAll(weaponEnchantableTag, List.of("cavernreborn:cavenic_axe", "cavernreborn:cavenic_sword"));
    }

    @Test
    void cavenicMeleeResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_sword.json"), "assets/cavernreborn/models/item/cavenic_sword.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_axe.json"), "assets/cavernreborn/models/item/cavenic_axe.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/cavenic_sword.png"), "assets/cavernreborn/textures/item/cavenic_sword.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/cavenic_axe.png"), "assets/cavernreborn/textures/item/cavenic_axe.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/cavenic_sword.json"), "data/cavernreborn/recipe/cavenic_sword.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/cavenic_axe.json"), "data/cavernreborn/recipe/cavenic_axe.json");
        assertClassPathOrigin(resourceUrl("data/c/tags/items/orbs/cavenic.json"), "data/c/tags/items/orbs/cavenic.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/item/cavenic_items.json"), "data/cavernreborn/tags/item/cavenic_items.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/items/cavenic_items.json"), "data/cavernreborn/tags/items/cavenic_items.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/block/incorrect_for_cavenic_tool.json"), "data/cavernreborn/tags/block/incorrect_for_cavenic_tool.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/blocks/incorrect_for_cavenic_tool.json"), "data/cavernreborn/tags/blocks/incorrect_for_cavenic_tool.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/axes.json"), "data/minecraft/tags/item/axes.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/swords.json"), "data/minecraft/tags/item/swords.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/mining.json"), "data/minecraft/tags/item/enchantable/mining.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/mining_loot.json"), "data/minecraft/tags/item/enchantable/mining_loot.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/durability.json"), "data/minecraft/tags/item/enchantable/durability.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/sword.json"), "data/minecraft/tags/item/enchantable/sword.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/sharp_weapon.json"), "data/minecraft/tags/item/enchantable/sharp_weapon.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/weapon.json"), "data/minecraft/tags/item/enchantable/weapon.json");
    }

    private static void assertCavenicRecipe(JsonObject recipe, String resultId) {
        assertEquals("minecraft:crafting_shaped", recipe.get("type").getAsString());
        assertEquals("equipment", recipe.get("category").getAsString());
        JsonObject key = recipe.getAsJsonObject("key");
        JsonObject orbIngredient = key.getAsJsonObject("#");
        boolean usesDirectOrb = orbIngredient.has("item") && "cavernreborn:cavenic_orb".equals(orbIngredient.get("item").getAsString());
        boolean usesCommonOrbTag = orbIngredient.has("tag") && "c:orbs/cavenic".equals(orbIngredient.get("tag").getAsString());
        assertTrue(usesDirectOrb || usesCommonOrbTag, "Expected cavenic recipe to use cavenic_orb directly or c:orbs/cavenic");
        assertEquals("minecraft:stick", key.getAsJsonObject("X").get("item").getAsString());
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
        InputStream inputStream = CavenicMeleeResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CavenicMeleeResourcesTest.class.getClassLoader().getResource(path);
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
