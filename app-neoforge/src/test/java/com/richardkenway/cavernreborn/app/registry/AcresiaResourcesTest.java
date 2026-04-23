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

class AcresiaResourcesTest {
    @Test
    void acresiaRegistersWithCropSeedsFruitAndStableCreativeTabPlacement() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String blockSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "block", "AcresiaCropBlock.java"
        );

        assertTrue(registriesSource.contains("BLOCKS.register(\"acresia\""));
        assertTrue(registriesSource.contains("ITEMS.register(\n        \"acresia_seeds\""));
        assertTrue(registriesSource.contains("ITEMS.register(\n        \"acresia_fruits\""));
        assertTrue(registriesSource.contains("new AcresiaCropBlock("));
        assertTrue(registriesSource.contains("new ItemNameBlockItem(ACRESIA.get(), new Item.Properties())"));
        assertTrue(registriesSource.contains("new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build()))"));
        assertInOrder(registriesSource, List.of(
            "output.accept(HEXCITE.get())",
            "output.accept(ACRESIA_SEEDS.get())",
            "output.accept(ACRESIA_FRUITS.get())",
            "output.accept(MINER_ORB.get())"
        ));

        assertTrue(blockSource.contains("extends CropBlock"));
        assertTrue(blockSource.contains("IntegerProperty.create(\"age\", 0, AcresiaHarvestPolicy.MAX_AGE)"));
        assertTrue(blockSource.contains("return state.is(ModBlockTags.ACRESIA_PLANTABLE_ON);"));
        assertTrue(blockSource.contains("!stack.is(Items.SHEARS) || !isMaxAge(state)"));
        assertTrue(blockSource.contains("AcresiaHarvestPolicy.evaluateShearHarvest("));
        assertTrue(blockSource.contains("new ItemStack(ModRegistries.ACRESIA_FRUITS.get())"));
        assertTrue(blockSource.contains("getStateForAge(result.resetAge())"));
        assertTrue(blockSource.contains("SoundEvents.SHEEP_SHEAR"));
        assertTrue(blockSource.contains("return ModRegistries.ACRESIA_SEEDS.get();"));
    }

    @Test
    void acresiaResourcesCoverLangBlockstateModelsLootTagsAndWorldgen() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject blockstate = readJsonResource("assets/cavernreborn/blockstates/acresia.json");
        JsonObject stage0 = readJsonResource("assets/cavernreborn/models/block/acresia_stage0.json");
        JsonObject stage1 = readJsonResource("assets/cavernreborn/models/block/acresia_stage1.json");
        JsonObject stage2 = readJsonResource("assets/cavernreborn/models/block/acresia_stage2.json");
        JsonObject stage3 = readJsonResource("assets/cavernreborn/models/block/acresia_stage3.json");
        JsonObject stage4 = readJsonResource("assets/cavernreborn/models/block/acresia_stage4.json");
        JsonObject seedsModel = readJsonResource("assets/cavernreborn/models/item/acresia_seeds.json");
        JsonObject fruitsModel = readJsonResource("assets/cavernreborn/models/item/acresia_fruits.json");
        JsonObject lootTable = readJsonResource("data/cavernreborn/loot_table/blocks/acresia.json");
        JsonObject itemsTag = readJsonResource("data/cavernreborn/tags/item/acresia_items.json");
        JsonObject itemsCompatTag = readJsonResource("data/cavernreborn/tags/items/acresia_items.json");
        JsonObject plantableTag = readJsonResource("data/cavernreborn/tags/block/acresia_plantable_on.json");
        JsonObject plantableCompatTag = readJsonResource("data/cavernreborn/tags/blocks/acresia_plantable_on.json");
        JsonObject configuredFeature = readJsonResource("data/cavernreborn/worldgen/configured_feature/acresia_patch.json");
        JsonObject placedFeature = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_acresia_patch_lush.json");
        JsonObject lushGrotto = readJsonResource("data/cavernreborn/worldgen/biome/lush_grotto.json");
        JsonObject stoneDepths = readJsonResource("data/cavernreborn/worldgen/biome/stone_depths.json");
        JsonObject dripstoneGrotto = readJsonResource("data/cavernreborn/worldgen/biome/dripstone_grotto.json");
        JsonObject highlandHollows = readJsonResource("data/cavernreborn/worldgen/biome/highland_hollows.json");

        assertEquals("Acresia", lang.get("block.cavernreborn.acresia").getAsString());
        assertEquals("Acresia Seeds", lang.get("item.cavernreborn.acresia_seeds").getAsString());
        assertEquals("Acresia Fruits", lang.get("item.cavernreborn.acresia_fruits").getAsString());

        JsonObject variants = blockstate.getAsJsonObject("variants");
        assertEquals("cavernreborn:block/acresia_stage0", variants.getAsJsonObject("age=0").get("model").getAsString());
        assertEquals("cavernreborn:block/acresia_stage1", variants.getAsJsonObject("age=1").get("model").getAsString());
        assertEquals("cavernreborn:block/acresia_stage2", variants.getAsJsonObject("age=2").get("model").getAsString());
        assertEquals("cavernreborn:block/acresia_stage3", variants.getAsJsonObject("age=3").get("model").getAsString());
        assertEquals("cavernreborn:block/acresia_stage4", variants.getAsJsonObject("age=4").get("model").getAsString());

        assertCropStageModel(stage0, "cavernreborn:block/acresia_stage_0");
        assertCropStageModel(stage1, "cavernreborn:block/acresia_stage_1");
        assertCropStageModel(stage2, "cavernreborn:block/acresia_stage_2");
        assertCropStageModel(stage3, "cavernreborn:block/acresia_stage_3");
        assertCropStageModel(stage4, "cavernreborn:block/acresia_stage_4");
        assertGeneratedItemModel(seedsModel, "cavernreborn:item/acresia_seeds");
        assertGeneratedItemModel(fruitsModel, "cavernreborn:item/acresia_fruits");

        assertEquals("minecraft:block", lootTable.get("type").getAsString());
        JsonArray pools = lootTable.getAsJsonArray("pools");
        assertEquals(2, pools.size());
        String rawLoot = lootTable.toString();
        assertTrue(rawLoot.contains("\"cavernreborn:acresia_fruits\""));
        assertTrue(rawLoot.contains("\"cavernreborn:acresia_seeds\""));
        assertTrue(rawLoot.contains("\"age\":\"4\""));
        assertTrue(rawLoot.contains("\"min\":1.0"));
        assertTrue(rawLoot.contains("\"max\":2.0"));

        List<String> acresiaItemValues = arrayStrings(itemsTag.getAsJsonArray("values"));
        assertEquals(acresiaItemValues, arrayStrings(itemsCompatTag.getAsJsonArray("values")));
        assertEquals(List.of(
            "cavernreborn:acresia_seeds",
            "cavernreborn:acresia_fruits"
        ), acresiaItemValues);

        List<String> plantableValues = arrayStrings(plantableTag.getAsJsonArray("values"));
        assertEquals(plantableValues, arrayStrings(plantableCompatTag.getAsJsonArray("values")));
        assertEquals(List.of(
            "minecraft:farmland",
            "minecraft:grass_block",
            "minecraft:dirt",
            "minecraft:coarse_dirt",
            "minecraft:rooted_dirt",
            "minecraft:moss_block",
            "minecraft:mycelium",
            "minecraft:podzol"
        ), plantableValues);

        assertEquals("minecraft:random_patch", configuredFeature.get("type").getAsString());
        JsonObject configuredConfig = configuredFeature.getAsJsonObject("config");
        assertEquals(32, configuredConfig.get("tries").getAsInt());
        assertEquals(4, configuredConfig.get("xz_spread").getAsInt());
        assertEquals(2, configuredConfig.get("y_spread").getAsInt());
        JsonObject feature = configuredConfig.getAsJsonObject("feature");
        assertEquals("minecraft:simple_block", feature.getAsJsonObject("feature").get("type").getAsString());
        JsonObject state = feature.getAsJsonObject("feature").getAsJsonObject("config")
            .getAsJsonObject("to_place")
            .getAsJsonObject("state");
        assertEquals("cavernreborn:acresia", state.get("Name").getAsString());
        assertEquals("4", state.getAsJsonObject("Properties").get("age").getAsString());
        JsonObject predicateFilter = feature.getAsJsonArray("placement").get(0).getAsJsonObject();
        assertEquals("minecraft:block_predicate_filter", predicateFilter.get("type").getAsString());
        assertEquals("minecraft:all_of", predicateFilter.getAsJsonObject("predicate").get("type").getAsString());

        assertEquals("cavernreborn:acresia_patch", placedFeature.get("feature").getAsString());
        List<String> placementTypes = StreamSupport.stream(placedFeature.getAsJsonArray("placement").spliterator(), false)
            .map(element -> element.getAsJsonObject().get("type").getAsString())
            .toList();
        assertEquals(List.of(
            "minecraft:count",
            "minecraft:in_square",
            "minecraft:height_range",
            "minecraft:biome"
        ), placementTypes);
        assertTrue(lushVegetationStep(lushGrotto).contains("cavernreborn:cavern_acresia_patch_lush"));
        assertFalse(flattenFeatures(stoneDepths).contains("cavernreborn:cavern_acresia_patch_lush"));
        assertFalse(flattenFeatures(dripstoneGrotto).contains("cavernreborn:cavern_acresia_patch_lush"));
        assertFalse(flattenFeatures(highlandHollows).contains("cavernreborn:cavern_acresia_patch_lush"));

        assertFalse(Files.exists(resolveProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "recipe", "acresia_seeds.json"
        )));
        assertFalse(Files.exists(resolveProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "recipe", "acresia_fruits.json"
        )));
    }

    @Test
    void acresiaResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/blockstates/acresia.json"), "assets/cavernreborn/blockstates/acresia.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/block/acresia_stage0.json"), "assets/cavernreborn/models/block/acresia_stage0.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/block/acresia_stage1.json"), "assets/cavernreborn/models/block/acresia_stage1.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/block/acresia_stage2.json"), "assets/cavernreborn/models/block/acresia_stage2.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/block/acresia_stage3.json"), "assets/cavernreborn/models/block/acresia_stage3.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/block/acresia_stage4.json"), "assets/cavernreborn/models/block/acresia_stage4.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/acresia_seeds.json"), "assets/cavernreborn/models/item/acresia_seeds.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/acresia_fruits.json"), "assets/cavernreborn/models/item/acresia_fruits.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/acresia_stage_0.png"), "assets/cavernreborn/textures/block/acresia_stage_0.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/acresia_stage_1.png"), "assets/cavernreborn/textures/block/acresia_stage_1.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/acresia_stage_2.png"), "assets/cavernreborn/textures/block/acresia_stage_2.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/acresia_stage_3.png"), "assets/cavernreborn/textures/block/acresia_stage_3.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/acresia_stage_4.png"), "assets/cavernreborn/textures/block/acresia_stage_4.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/acresia_seeds.png"), "assets/cavernreborn/textures/item/acresia_seeds.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/acresia_fruits.png"), "assets/cavernreborn/textures/item/acresia_fruits.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/loot_table/blocks/acresia.json"), "data/cavernreborn/loot_table/blocks/acresia.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/item/acresia_items.json"), "data/cavernreborn/tags/item/acresia_items.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/items/acresia_items.json"), "data/cavernreborn/tags/items/acresia_items.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/block/acresia_plantable_on.json"), "data/cavernreborn/tags/block/acresia_plantable_on.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/blocks/acresia_plantable_on.json"), "data/cavernreborn/tags/blocks/acresia_plantable_on.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/worldgen/configured_feature/acresia_patch.json"), "data/cavernreborn/worldgen/configured_feature/acresia_patch.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/worldgen/placed_feature/cavern_acresia_patch_lush.json"), "data/cavernreborn/worldgen/placed_feature/cavern_acresia_patch_lush.json");
    }

    private static void assertCropStageModel(JsonObject model, String texturePath) {
        assertEquals("minecraft:block/crop", model.get("parent").getAsString());
        assertEquals(texturePath, model.getAsJsonObject("textures").get("crop").getAsString());
    }

    private static void assertGeneratedItemModel(JsonObject model, String texturePath) {
        assertEquals("minecraft:item/generated", model.get("parent").getAsString());
        assertEquals(texturePath, model.getAsJsonObject("textures").get("layer0").getAsString());
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = AcresiaResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = AcresiaResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static List<String> lushVegetationStep(JsonObject biome) {
        return arrayStrings(biome.getAsJsonArray("features").get(9).getAsJsonArray());
    }

    private static List<String> flattenFeatures(JsonObject biome) {
        return StreamSupport.stream(biome.getAsJsonArray("features").spliterator(), false)
            .flatMap(step -> StreamSupport.stream(step.getAsJsonArray().spliterator(), false))
            .map(element -> element.getAsString())
            .toList();
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

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || candidate.getParent() != null && Files.exists(candidate.getParent())) {
                return candidate;
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
