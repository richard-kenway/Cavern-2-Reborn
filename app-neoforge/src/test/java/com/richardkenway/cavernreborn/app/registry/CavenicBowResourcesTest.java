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

class CavenicBowResourcesTest {
    @Test
    void cavenicBowRegistersWithDedicatedItemAndStableCreativePlacement() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String bowSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "item", "CavenicBowItem.java"
        );
        String modeSource = readProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "combat", "CavenicBowMode.java"
        );
        String snipePolicySource = readProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "combat", "CavenicBowSnipePolicy.java"
        );

        assertTrue(registriesSource.contains("ITEMS.register(\"cavenic_bow\""));
        assertTrue(registriesSource.contains("new CavenicBowItem("));
        assertTrue(registriesSource.contains(".durability(ModToolTiers.CAVENIC.getUses())"));
        assertTrue(registriesSource.contains(".stacksTo(1)"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_ORB.get())",
            "output.accept(CAVENIC_SWORD.get())",
            "output.accept(CAVENIC_AXE.get())",
            "output.accept(CAVENIC_BOW.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(bowSource.contains("extends BowItem"));
        assertTrue(bowSource.contains("public CavenicBowMode getMode(ItemStack stack)"));
        assertTrue(bowSource.contains("public void setMode(ItemStack stack, CavenicBowMode mode)"));
        assertTrue(bowSource.contains("public CavenicBowMode cycleMode(ItemStack stack)"));
        assertTrue(bowSource.contains("public float resolveProjectileVelocity(ItemStack stack, float baseVelocity, float power)"));
        assertTrue(bowSource.contains("public boolean applySnipeBoost(ItemStack stack, AbstractArrow arrow, float power)"));
        assertTrue(bowSource.contains("public int resolveAdditionalDurabilityCost(ItemStack stack, float power)"));
        assertTrue(bowSource.contains("DataComponents.CUSTOM_DATA"));
        assertTrue(bowSource.contains("player.isShiftKeyDown()"));
        assertTrue(bowSource.contains("player.displayClientMessage(Component.translatable(MODE_CHANGED_KEY, modeLabel(nextMode)), true)"));
        assertTrue(bowSource.contains("tooltipComponents.add(Component.translatable(MODE_LINE_KEY, modeLabel(getMode(stack))))"));
        assertTrue(bowSource.contains("public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft)"));
        assertTrue(bowSource.contains("public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack)"));
        assertTrue(bowSource.contains("CavenicBowSnipePolicy"));
        assertTrue(bowSource.contains("CURRENT_SHOT_CONTEXT"));
        assertTrue(bowSource.contains("repairCandidate.is(ModRegistries.CAVENIC_ORB.get())"));
        assertTrue(bowSource.contains("ModToolTiers.CAVENIC.getEnchantmentValue()"));
        assertTrue(modeSource.contains("NORMAL(\"normal\")"));
        assertTrue(modeSource.contains("RAPID(\"rapid\")"));
        assertTrue(modeSource.contains("SNIPE(\"snipe\")"));
        assertTrue(modeSource.contains("TORCH(\"torch\")"));
        assertTrue(modeSource.contains("return NORMAL;"));
        assertTrue(snipePolicySource.contains("MIN_POWER_THRESHOLD = 1.0F"));
        assertTrue(snipePolicySource.contains("VELOCITY_MULTIPLIER = 1.5F"));
        assertTrue(snipePolicySource.contains("BASE_DAMAGE_MULTIPLIER = 1.5D"));
        assertTrue(snipePolicySource.contains("EXTRA_DURABILITY_COST = 1"));
        assertFalse(bowSource.contains("EntityRapidArrow"));
        assertFalse(bowSource.contains("EntityTorchArrow"));
        assertFalse(registriesSource.contains("rapid_arrow"));
        assertFalse(registriesSource.contains("torch_arrow"));
    }

    @Test
    void cavenicBowResourcesCoverLangModelRecipeTagsAndNoCustomProjectileWiring() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject bowModel = readJsonResource("assets/cavernreborn/models/item/cavenic_bow.json");
        JsonObject pulling0Model = readJsonResource("assets/cavernreborn/models/item/cavenic_bow_pulling_0.json");
        JsonObject pulling1Model = readJsonResource("assets/cavernreborn/models/item/cavenic_bow_pulling_1.json");
        JsonObject pulling2Model = readJsonResource("assets/cavernreborn/models/item/cavenic_bow_pulling_2.json");
        JsonObject recipe = readJsonResource("data/cavernreborn/recipe/cavenic_bow.json");
        JsonObject cavenicItemsTag = readJsonResource("data/cavernreborn/tags/item/cavenic_items.json");
        JsonObject cavenicItemsCompatTag = readJsonResource("data/cavernreborn/tags/items/cavenic_items.json");
        JsonObject commonOrbTag = readJsonResource("data/c/tags/items/orbs/cavenic.json");
        JsonObject bowsTag = readJsonResource("data/minecraft/tags/item/bows.json");
        JsonObject bowEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/bow.json");
        JsonObject durabilityEnchantableTag = readJsonResource("data/minecraft/tags/item/enchantable/durability.json");

        assertEquals("Cavenic Bow", lang.get("item.cavernreborn.cavenic_bow").getAsString());
        assertEquals("Normal", lang.get("item.cavernreborn.cavenic_bow.mode.normal").getAsString());
        assertEquals("Rapid Fire", lang.get("item.cavernreborn.cavenic_bow.mode.rapid").getAsString());
        assertEquals("Sniper", lang.get("item.cavernreborn.cavenic_bow.mode.snipe").getAsString());
        assertEquals("Torch Shot", lang.get("item.cavernreborn.cavenic_bow.mode.torch").getAsString());
        assertEquals("Mode: %s", lang.get("item.cavernreborn.cavenic_bow.mode").getAsString());
        assertEquals("Cavenic Bow Mode: %s", lang.get("item.cavernreborn.cavenic_bow.mode_changed").getAsString());

        assertEquals("minecraft:item/generated", bowModel.get("parent").getAsString());
        assertEquals("cavernreborn:item/cavenic_bow", bowModel.getAsJsonObject("textures").get("layer0").getAsString());
        assertBowOverrides(bowModel.getAsJsonArray("overrides"));
        assertPullingModel(pulling0Model, "cavernreborn:item/cavenic_bow_pulling_0");
        assertPullingModel(pulling1Model, "cavernreborn:item/cavenic_bow_pulling_1");
        assertPullingModel(pulling2Model, "cavernreborn:item/cavenic_bow_pulling_2");
        assertFalse(bowModel.toString().contains("rapid"), "Bow model must not add rapid-mode predicates or assets in this slice");
        assertFalse(bowModel.toString().contains("snipe"), "Bow model must not add snipe-mode predicates or assets in this slice");
        assertFalse(bowModel.toString().contains("torch"), "Bow model must not add torch-mode predicates or assets in this slice");

        assertEquals("minecraft:crafting_shaped", recipe.get("type").getAsString());
        assertEquals("equipment", recipe.get("category").getAsString());
        assertEquals(List.of(" # ", "#X#", " # "), arrayStrings(recipe.getAsJsonArray("pattern")));
        JsonObject key = recipe.getAsJsonObject("key");
        assertEquals("c:orbs/cavenic", key.getAsJsonObject("#").get("tag").getAsString());
        assertEquals("minecraft:bow", key.getAsJsonObject("X").get("item").getAsString());
        assertEquals("cavernreborn:cavenic_bow", recipe.getAsJsonObject("result").get("id").getAsString());
        assertEquals(1, recipe.getAsJsonObject("result").get("count").getAsInt());

        List<String> cavenicItemValues = arrayStrings(cavenicItemsTag.getAsJsonArray("values"));
        assertEquals(cavenicItemValues, arrayStrings(cavenicItemsCompatTag.getAsJsonArray("values")));
        assertEquals(List.of(
            "cavernreborn:cavenic_shroom",
            "cavernreborn:cavenic_orb",
            "cavernreborn:cavenic_sword",
            "cavernreborn:cavenic_axe",
            "cavernreborn:cavenic_bow"
        ), cavenicItemValues);
        assertEquals(List.of("cavernreborn:cavenic_orb"), arrayStrings(commonOrbTag.getAsJsonArray("values")));
        assertTagContainsExactly(bowsTag, List.of("cavernreborn:cavenic_bow"));
        assertTagContainsExactly(bowEnchantableTag, List.of("cavernreborn:cavenic_bow"));
        assertTagContainsAll(durabilityEnchantableTag, List.of("cavernreborn:cavenic_bow"));
    }

    @Test
    void cavenicBowResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_bow.json"), "assets/cavernreborn/models/item/cavenic_bow.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_bow_pulling_0.json"), "assets/cavernreborn/models/item/cavenic_bow_pulling_0.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_bow_pulling_1.json"), "assets/cavernreborn/models/item/cavenic_bow_pulling_1.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_bow_pulling_2.json"), "assets/cavernreborn/models/item/cavenic_bow_pulling_2.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/cavenic_bow.png"), "assets/cavernreborn/textures/item/cavenic_bow.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/cavenic_bow_pulling_0.png"), "assets/cavernreborn/textures/item/cavenic_bow_pulling_0.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/cavenic_bow_pulling_1.png"), "assets/cavernreborn/textures/item/cavenic_bow_pulling_1.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/cavenic_bow_pulling_2.png"), "assets/cavernreborn/textures/item/cavenic_bow_pulling_2.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/recipe/cavenic_bow.json"), "data/cavernreborn/recipe/cavenic_bow.json");
        assertClassPathOrigin(resourceUrl("data/c/tags/items/orbs/cavenic.json"), "data/c/tags/items/orbs/cavenic.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/item/cavenic_items.json"), "data/cavernreborn/tags/item/cavenic_items.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/items/cavenic_items.json"), "data/cavernreborn/tags/items/cavenic_items.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/bows.json"), "data/minecraft/tags/item/bows.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/bow.json"), "data/minecraft/tags/item/enchantable/bow.json");
        assertClassPathOrigin(resourceUrl("data/minecraft/tags/item/enchantable/durability.json"), "data/minecraft/tags/item/enchantable/durability.json");
        assertMissingResource("assets/cavernreborn/models/item/cavenic_bow_rapid.json");
        assertMissingResource("assets/cavernreborn/models/item/cavenic_bow_snipe.json");
        assertMissingResource("assets/cavernreborn/models/item/cavenic_bow_torch.json");
        assertMissingResource("assets/cavernreborn/textures/item/cavenic_bow_rapid.png");
        assertMissingResource("assets/cavernreborn/textures/item/cavenic_bow_snipe.png");
        assertMissingResource("assets/cavernreborn/textures/item/cavenic_bow_torch.png");
    }

    private static void assertBowOverrides(JsonArray overrides) {
        assertEquals(3, overrides.size());
        assertEquals("cavernreborn:item/cavenic_bow_pulling_0", overrides.get(0).getAsJsonObject().get("model").getAsString());
        assertEquals("cavernreborn:item/cavenic_bow_pulling_1", overrides.get(1).getAsJsonObject().get("model").getAsString());
        assertEquals("cavernreborn:item/cavenic_bow_pulling_2", overrides.get(2).getAsJsonObject().get("model").getAsString());
    }

    private static void assertPullingModel(JsonObject model, String texturePath) {
        assertEquals("cavernreborn:item/cavenic_bow", model.get("parent").getAsString());
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
        InputStream inputStream = CavenicBowResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CavenicBowResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CavenicBowResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
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
