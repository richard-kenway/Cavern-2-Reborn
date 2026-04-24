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

class CavenicShroomResourcesTest {
    @Test
    void cavenicRegistersWithHazardBlockItemOrbAndStableCreativePlacement() {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String blockSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "block", "CavenicShroomBlock.java"
        );
        String policySource = readProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "flora", "CavenicOrbDropPolicy.java"
        );
        String randomiteLoot = readProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "loot_table", "blocks", "randomite_ore.json"
        );
        String miningAssistSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "mining", "CavernMiningAssistEvents.java"
        );
        String progressionSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "progression", "CavernMiningProgressionEvents.java"
        );
        String compassSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "item", "OreCompassItem.java"
        );

        assertTrue(registriesSource.contains("BLOCKS.register(\"cavenic_shroom\""));
        assertTrue(registriesSource.contains("ITEMS.registerSimpleBlockItem(CAVENIC_SHROOM)"));
        assertTrue(registriesSource.contains("ITEMS.register(\"cavenic_orb\""));
        assertTrue(registriesSource.contains("new CavenicShroomBlock("));
        assertTrue(registriesSource.contains("new Item(new Item.Properties().stacksTo(16))"));
        assertInOrder(registriesSource, List.of(
            "output.accept(ACRESIA_FRUITS.get())",
            "output.accept(CAVENIC_SHROOM_ITEM.get())",
            "output.accept(MINER_ORB.get())",
            "output.accept(CAVENIC_ORB.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(blockSource.contains("extends BushBlock"));
        assertTrue(blockSource.contains("state.is(ModBlockTags.CAVENIC_SHROOM_PLACEABLE_ON)"));
        assertTrue(blockSource.contains("MobEffects.CONFUSION"));
        assertTrue(blockSource.contains("random.nextInt(25) != 0"));
        assertTrue(blockSource.contains("BlockPos.betweenClosed("));
        assertTrue(blockSource.contains("MAX_NEARBY_SHROOMS = 4"));
        assertTrue(blockSource.contains("SPREAD_ATTEMPTS = 4"));
        assertTrue(blockSource.contains("tryShearHarvest("));
        assertTrue(blockSource.contains("CavenicOrbDropPolicy.evaluate("));
        assertTrue(blockSource.contains("tool.is(Items.SHEARS)"));
        assertTrue(blockSource.contains("level.removeBlock(pos, false)"));
        assertTrue(blockSource.contains("new ItemStack(ModRegistries.CAVENIC_SHROOM_ITEM.get())"));
        assertTrue(blockSource.contains("new ItemStack(ModRegistries.CAVENIC_ORB.get(), result.orbCount())"));
        assertTrue(policySource.contains("SHEAR_DROP_ROLL_BOUND = 10"));
        assertTrue(policySource.contains("NORMAL_DROP_ROLL_BOUND = 50"));

        assertFalse(registriesSource.contains("cavenic_zombie"));
        assertFalse(registriesSource.contains("cavenic_skeleton"));
        assertFalse(registriesSource.contains("Cavenia"));
        assertFalse(randomiteLoot.contains("cavernreborn:cavenic_orb"));
        assertFalse(miningAssistSource.contains("cavenic"));
        assertFalse(progressionSource.contains("cavenic"));
        assertFalse(compassSource.contains("cavenic"));
    }

    @Test
    void cavenicResourcesCoverLangModelsLootTagsAndWorldgen() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject blockstate = readJsonResource("assets/cavernreborn/blockstates/cavenic_shroom.json");
        JsonObject blockModel = readJsonResource("assets/cavernreborn/models/block/cavenic_shroom.json");
        JsonObject shroomItemModel = readJsonResource("assets/cavernreborn/models/item/cavenic_shroom.json");
        JsonObject orbItemModel = readJsonResource("assets/cavernreborn/models/item/cavenic_orb.json");
        JsonObject lootTable = readJsonResource("data/cavernreborn/loot_table/blocks/cavenic_shroom.json");
        JsonObject cavenicItemsTag = readJsonResource("data/cavernreborn/tags/item/cavenic_items.json");
        JsonObject cavenicItemsCompatTag = readJsonResource("data/cavernreborn/tags/items/cavenic_items.json");
        JsonObject placeableTag = readJsonResource("data/cavernreborn/tags/block/cavenic_shroom_placeable_on.json");
        JsonObject placeableCompatTag = readJsonResource("data/cavernreborn/tags/blocks/cavenic_shroom_placeable_on.json");
        JsonObject configuredFeature = readJsonResource("data/cavernreborn/worldgen/configured_feature/cavenic_shroom_patch.json");
        JsonObject placedFeature = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_cavenic_shroom_patch.json");
        JsonObject stoneDepths = readJsonResource("data/cavernreborn/worldgen/biome/stone_depths.json");
        JsonObject highlandHollows = readJsonResource("data/cavernreborn/worldgen/biome/highland_hollows.json");
        JsonObject lushGrotto = readJsonResource("data/cavernreborn/worldgen/biome/lush_grotto.json");
        JsonObject dripstoneGrotto = readJsonResource("data/cavernreborn/worldgen/biome/dripstone_grotto.json");

        assertEquals("Cavenic Shroom", lang.get("block.cavernreborn.cavenic_shroom").getAsString());
        assertEquals("Cavenic Orb", lang.get("item.cavernreborn.cavenic_orb").getAsString());
        assertEquals("Cavenic Shroom", lang.get("item.cavernreborn.cavenic_shroom").getAsString());

        assertEquals("cavernreborn:block/cavenic_shroom", blockstate.getAsJsonObject("variants").getAsJsonObject("").get("model").getAsString());
        assertEquals("minecraft:block/cross", blockModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/cavenic_shroom", blockModel.getAsJsonObject("textures").get("cross").getAsString());
        assertEquals("minecraft:item/generated", shroomItemModel.get("parent").getAsString());
        assertEquals("cavernreborn:block/cavenic_shroom", shroomItemModel.getAsJsonObject("textures").get("layer0").getAsString());
        assertEquals("minecraft:item/generated", orbItemModel.get("parent").getAsString());
        assertEquals("cavernreborn:item/cavenic_orb", orbItemModel.getAsJsonObject("textures").get("layer0").getAsString());

        assertEquals("minecraft:block", lootTable.get("type").getAsString());
        assertEquals("cavernreborn:cavenic_shroom", lootTable.getAsJsonArray("pools").get(0).getAsJsonObject()
            .getAsJsonArray("entries").get(0).getAsJsonObject().get("name").getAsString());

        List<String> cavenicItemValues = arrayStrings(cavenicItemsTag.getAsJsonArray("values"));
        assertEquals(cavenicItemValues, arrayStrings(cavenicItemsCompatTag.getAsJsonArray("values")));
        assertEquals(List.of(
            "cavernreborn:cavenic_shroom",
            "cavernreborn:cavenic_orb",
            "cavernreborn:cavenic_sword",
            "cavernreborn:cavenic_axe",
            "cavernreborn:cavenic_bow"
        ), cavenicItemValues);

        List<String> placeableValues = arrayStrings(placeableTag.getAsJsonArray("values"));
        assertEquals(placeableValues, arrayStrings(placeableCompatTag.getAsJsonArray("values")));
        assertEquals(List.of(
            "minecraft:stone",
            "minecraft:mycelium",
            "minecraft:podzol",
            "minecraft:moss_block",
            "minecraft:dirt",
            "minecraft:coarse_dirt"
        ), placeableValues);

        assertEquals("minecraft:random_patch", configuredFeature.get("type").getAsString());
        JsonObject config = configuredFeature.getAsJsonObject("config");
        assertEquals(24, config.get("tries").getAsInt());
        assertEquals(4, config.get("xz_spread").getAsInt());
        assertEquals(1, config.get("y_spread").getAsInt());
        JsonObject feature = config.getAsJsonObject("feature");
        assertEquals("minecraft:simple_block", feature.getAsJsonObject("feature").get("type").getAsString());
        assertEquals("cavernreborn:cavenic_shroom", feature.getAsJsonObject("feature").getAsJsonObject("config")
            .getAsJsonObject("to_place").getAsJsonObject("state").get("Name").getAsString());

        assertEquals("cavernreborn:cavenic_shroom_patch", placedFeature.get("feature").getAsString());
        List<String> placementTypes = StreamSupport.stream(placedFeature.getAsJsonArray("placement").spliterator(), false)
            .map(element -> element.getAsJsonObject().get("type").getAsString())
            .toList();
        assertEquals(List.of(
            "minecraft:count",
            "minecraft:in_square",
            "minecraft:height_range",
            "minecraft:biome"
        ), placementTypes);

        assertTrue(featureStep(stoneDepths, 9).contains("cavernreborn:cavern_cavenic_shroom_patch"));
        assertFalse(flattenFeatures(highlandHollows).contains("cavernreborn:cavern_cavenic_shroom_patch"));
        assertFalse(flattenFeatures(lushGrotto).contains("cavernreborn:cavern_cavenic_shroom_patch"));
        assertFalse(flattenFeatures(dripstoneGrotto).contains("cavernreborn:cavern_cavenic_shroom_patch"));
    }

    @Test
    void cavenicResourcesResolveFromRuntimeClasspath() {
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/blockstates/cavenic_shroom.json"), "assets/cavernreborn/blockstates/cavenic_shroom.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/block/cavenic_shroom.json"), "assets/cavernreborn/models/block/cavenic_shroom.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_shroom.json"), "assets/cavernreborn/models/item/cavenic_shroom.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_orb.json"), "assets/cavernreborn/models/item/cavenic_orb.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/block/cavenic_shroom.png"), "assets/cavernreborn/textures/block/cavenic_shroom.png");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/item/cavenic_orb.png"), "assets/cavernreborn/textures/item/cavenic_orb.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/loot_table/blocks/cavenic_shroom.json"), "data/cavernreborn/loot_table/blocks/cavenic_shroom.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/item/cavenic_items.json"), "data/cavernreborn/tags/item/cavenic_items.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/items/cavenic_items.json"), "data/cavernreborn/tags/items/cavenic_items.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/block/cavenic_shroom_placeable_on.json"), "data/cavernreborn/tags/block/cavenic_shroom_placeable_on.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/blocks/cavenic_shroom_placeable_on.json"), "data/cavernreborn/tags/blocks/cavenic_shroom_placeable_on.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/worldgen/configured_feature/cavenic_shroom_patch.json"), "data/cavernreborn/worldgen/configured_feature/cavenic_shroom_patch.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/worldgen/placed_feature/cavern_cavenic_shroom_patch.json"), "data/cavernreborn/worldgen/placed_feature/cavern_cavenic_shroom_patch.json");
    }

    private static List<String> featureStep(JsonObject biome, int index) {
        return arrayStrings(biome.getAsJsonArray("features").get(index).getAsJsonArray());
    }

    private static List<String> flattenFeatures(JsonObject biome) {
        return StreamSupport.stream(biome.getAsJsonArray("features").spliterator(), false)
            .flatMap(step -> StreamSupport.stream(step.getAsJsonArray().spliterator(), false))
            .map(element -> element.getAsString())
            .toList();
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CavenicShroomResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CavenicShroomResourcesTest.class.getClassLoader().getResource(path);
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
