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

class MagniteArmorResourcesTest {
    @Test
    void magniteArmorRegistersWithBoundedMaterialAndCreativeTabOrder() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String armorMaterialsSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModArmorMaterials.java"
        );

        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_helmet\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_chestplate\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_leggings\""));
        assertTrue(registriesSource.contains("ITEMS.register(\"magnite_boots\""));
        assertTrue(registriesSource.contains("new ArmorItem("));
        assertTrue(registriesSource.contains("ModArmorMaterials.MAGNITE"));
        assertTrue(registriesSource.contains("ArmorItem.Type.HELMET.getDurability(ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER)"));
        assertTrue(registriesSource.contains("ArmorItem.Type.CHESTPLATE.getDurability(ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER)"));
        assertTrue(registriesSource.contains("ArmorItem.Type.LEGGINGS.getDurability(ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER)"));
        assertTrue(registriesSource.contains("ArmorItem.Type.BOOTS.getDurability(ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER)"));
        assertInOrder(registriesSource, List.of(
            "output.accept(MAGNITE_INGOT.get())",
            "output.accept(MAGNITE_PICKAXE.get())",
            "output.accept(MAGNITE_AXE.get())",
            "output.accept(MAGNITE_SHOVEL.get())",
            "output.accept(MAGNITE_SWORD.get())",
            "output.accept(MAGNITE_HELMET.get())",
            "output.accept(MAGNITE_CHESTPLATE.get())",
            "output.accept(MAGNITE_LEGGINGS.get())",
            "output.accept(MAGNITE_BOOTS.get())",
            "output.accept(HEXCITE.get())"
        ));

        assertTrue(armorMaterialsSource.contains("ARMOR_MATERIALS.register(\"magnite\""));
        assertTrue(armorMaterialsSource.contains("MAGNITE_DURABILITY_MULTIPLIER = 12"));
        assertTrue(armorMaterialsSource.contains("defense.put(ArmorItem.Type.HELMET, 2);"));
        assertTrue(armorMaterialsSource.contains("defense.put(ArmorItem.Type.CHESTPLATE, 5);"));
        assertTrue(armorMaterialsSource.contains("defense.put(ArmorItem.Type.LEGGINGS, 4);"));
        assertTrue(armorMaterialsSource.contains("defense.put(ArmorItem.Type.BOOTS, 2);"));
        assertTrue(armorMaterialsSource.contains("24,"));
        assertTrue(armorMaterialsSource.contains("SoundEvents.ARMOR_EQUIP_CHAIN"));
        assertTrue(armorMaterialsSource.contains("Ingredient.of(ModRegistries.MAGNITE_INGOT.get())"));
        assertTrue(armorMaterialsSource.contains("List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, \"magnite\")))"));
        assertTrue(armorMaterialsSource.contains("0.0F"));
    }

    @Test
    void magniteArmorResourcesCoverLangModelsRecipesEquipmentAssetsAndTags() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject helmetModel = readJsonResource("assets/cavernreborn/models/item/magnite_helmet.json");
        JsonObject chestplateModel = readJsonResource("assets/cavernreborn/models/item/magnite_chestplate.json");
        JsonObject leggingsModel = readJsonResource("assets/cavernreborn/models/item/magnite_leggings.json");
        JsonObject bootsModel = readJsonResource("assets/cavernreborn/models/item/magnite_boots.json");
        JsonObject equipmentAsset = readJsonResource("assets/cavernreborn/equipment/magnite.json");
        JsonObject helmetRecipe = readJsonResource("data/cavernreborn/recipe/magnite_helmet.json");
        JsonObject chestplateRecipe = readJsonResource("data/cavernreborn/recipe/magnite_chestplate.json");
        JsonObject leggingsRecipe = readJsonResource("data/cavernreborn/recipe/magnite_leggings.json");
        JsonObject bootsRecipe = readJsonResource("data/cavernreborn/recipe/magnite_boots.json");
        JsonObject headArmorTag = readJsonResource("data/minecraft/tags/item/head_armor.json");
        JsonObject chestArmorTag = readJsonResource("data/minecraft/tags/item/chest_armor.json");
        JsonObject legArmorTag = readJsonResource("data/minecraft/tags/item/leg_armor.json");
        JsonObject footArmorTag = readJsonResource("data/minecraft/tags/item/foot_armor.json");
        JsonObject trimmableArmorTag = readJsonResource("data/minecraft/tags/item/trimmable_armor.json");
        JsonObject armorEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/armor.json");
        JsonObject headEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/head_armor.json");
        JsonObject chestEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/chest_armor.json");
        JsonObject legEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/leg_armor.json");
        JsonObject footEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/foot_armor.json");
        JsonObject durabilityEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/durability.json");
        JsonObject equippableEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/equippable.json");
        JsonObject vanishingEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/vanishing.json");

        assertEquals("Magnite Helmet", lang.get("item.cavernreborn.magnite_helmet").getAsString());
        assertEquals("Magnite Chestplate", lang.get("item.cavernreborn.magnite_chestplate").getAsString());
        assertEquals("Magnite Leggings", lang.get("item.cavernreborn.magnite_leggings").getAsString());
        assertEquals("Magnite Boots", lang.get("item.cavernreborn.magnite_boots").getAsString());

        assertGeneratedModel(helmetModel, "cavernreborn:item/magnite_helmet");
        assertGeneratedModel(chestplateModel, "cavernreborn:item/magnite_chestplate");
        assertGeneratedModel(leggingsModel, "cavernreborn:item/magnite_leggings");
        assertGeneratedModel(bootsModel, "cavernreborn:item/magnite_boots");

        JsonObject equipmentLayers = equipmentAsset.getAsJsonObject("layers");
        assertEquals("cavernreborn:magnite/outer", equipmentLayers.getAsJsonArray("humanoid").get(0).getAsJsonObject().get("texture").getAsString());
        assertEquals("cavernreborn:magnite/inner", equipmentLayers.getAsJsonArray("humanoid_leggings").get(0).getAsJsonObject().get("texture").getAsString());

        assertArmorRecipe(helmetRecipe, "cavernreborn:magnite_helmet");
        assertArmorRecipe(chestplateRecipe, "cavernreborn:magnite_chestplate");
        assertArmorRecipe(leggingsRecipe, "cavernreborn:magnite_leggings");
        assertArmorRecipe(bootsRecipe, "cavernreborn:magnite_boots");

        assertTagContainsExactly(headArmorTag, List.of("cavernreborn:hexcite_helmet", "cavernreborn:magnite_helmet"));
        assertTagContainsExactly(chestArmorTag, List.of("cavernreborn:hexcite_chestplate", "cavernreborn:magnite_chestplate"));
        assertTagContainsExactly(legArmorTag, List.of("cavernreborn:hexcite_leggings", "cavernreborn:magnite_leggings"));
        assertTagContainsExactly(footArmorTag, List.of("cavernreborn:hexcite_boots", "cavernreborn:magnite_boots"));
        assertTagContainsExactly(trimmableArmorTag, List.of(
            "cavernreborn:hexcite_helmet",
            "cavernreborn:hexcite_chestplate",
            "cavernreborn:hexcite_leggings",
            "cavernreborn:hexcite_boots",
            "cavernreborn:magnite_helmet",
            "cavernreborn:magnite_chestplate",
            "cavernreborn:magnite_leggings",
            "cavernreborn:magnite_boots"
        ));
        assertTagContainsExactly(armorEnchantableTag, List.of(
            "cavernreborn:hexcite_helmet",
            "cavernreborn:hexcite_chestplate",
            "cavernreborn:hexcite_leggings",
            "cavernreborn:hexcite_boots",
            "cavernreborn:magnite_helmet",
            "cavernreborn:magnite_chestplate",
            "cavernreborn:magnite_leggings",
            "cavernreborn:magnite_boots"
        ));
        assertTagContainsExactly(headEnchantableTag, List.of("cavernreborn:hexcite_helmet", "cavernreborn:magnite_helmet"));
        assertTagContainsExactly(chestEnchantableTag, List.of("cavernreborn:hexcite_chestplate", "cavernreborn:magnite_chestplate"));
        assertTagContainsExactly(legEnchantableTag, List.of("cavernreborn:hexcite_leggings", "cavernreborn:magnite_leggings"));
        assertTagContainsExactly(footEnchantableTag, List.of("cavernreborn:hexcite_boots", "cavernreborn:magnite_boots"));
        assertTagContainsAll(durabilityEnchantableTag, List.of(
            "cavernreborn:magnite_helmet",
            "cavernreborn:magnite_chestplate",
            "cavernreborn:magnite_leggings",
            "cavernreborn:magnite_boots"
        ));
        assertTagContainsExactly(equippableEnchantableTag, List.of(
            "cavernreborn:hexcite_helmet",
            "cavernreborn:hexcite_chestplate",
            "cavernreborn:hexcite_leggings",
            "cavernreborn:hexcite_boots",
            "cavernreborn:magnite_helmet",
            "cavernreborn:magnite_chestplate",
            "cavernreborn:magnite_leggings",
            "cavernreborn:magnite_boots"
        ));
        assertTagContainsExactly(vanishingEnchantableTag, List.of(
            "cavernreborn:hexcite_helmet",
            "cavernreborn:hexcite_chestplate",
            "cavernreborn:hexcite_leggings",
            "cavernreborn:hexcite_boots",
            "cavernreborn:magnite_helmet",
            "cavernreborn:magnite_chestplate",
            "cavernreborn:magnite_leggings",
            "cavernreborn:magnite_boots"
        ));
    }

    @Test
    void magniteArmorResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_helmet.json"), "assets/cavernreborn/models/item/magnite_helmet.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_chestplate.json"), "assets/cavernreborn/models/item/magnite_chestplate.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_leggings.json"), "assets/cavernreborn/models/item/magnite_leggings.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/magnite_boots.json"), "assets/cavernreborn/models/item/magnite_boots.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_helmet.png"), "assets/cavernreborn/textures/item/magnite_helmet.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_chestplate.png"), "assets/cavernreborn/textures/item/magnite_chestplate.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_leggings.png"), "assets/cavernreborn/textures/item/magnite_leggings.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/magnite_boots.png"), "assets/cavernreborn/textures/item/magnite_boots.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/equipment/magnite.json"), "assets/cavernreborn/equipment/magnite.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/entity/equipment/humanoid/magnite/outer.png"), "assets/cavernreborn/textures/entity/equipment/humanoid/magnite/outer.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/entity/equipment/humanoid_leggings/magnite/inner.png"), "assets/cavernreborn/textures/entity/equipment/humanoid_leggings/magnite/inner.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/models/armor/magnite_layer_1.png"), "assets/cavernreborn/textures/models/armor/magnite_layer_1.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/models/armor/magnite_layer_2.png"), "assets/cavernreborn/textures/models/armor/magnite_layer_2.png");
    }

    private static void assertArmorRecipe(JsonObject recipe, String resultId) {
        assertEquals("minecraft:crafting_shaped", recipe.get("type").getAsString());
        assertEquals("equipment", recipe.get("category").getAsString());
        JsonObject key = recipe.getAsJsonObject("key");
        assertEquals("cavernreborn:magnite_ingot", key.getAsJsonObject("#").get("item").getAsString());
        assertEquals(resultId, recipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(1, recipe.getAsJsonObject("result").get("count").getAsInt());
    }

    private static void assertGeneratedModel(JsonObject model, String texturePath) {
        assertEquals("minecraft:item/generated", model.get("parent").getAsString());
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
        InputStream inputStream = MagniteArmorResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = MagniteArmorResourcesTest.class.getClassLoader().getResource(path);
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
