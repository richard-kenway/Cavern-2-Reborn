package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavernWorldgenResourcesTest {
    @Test
    void cavernDimensionUsesCustomBiomeFamilyAndContainedCavesNoiseSettings() throws IOException {
        JsonObject cavernDimension = readJsonResource("data/cavernreborn/dimension/cavern.json");
        JsonObject cavernDimensionType = readJsonResource("data/cavernreborn/dimension_type/cavern.json");
        JsonObject containedCaves = readJsonResource("data/cavernreborn/worldgen/noise_settings/contained_caves.json");
        JsonObject tunnelNetwork = readJsonResource("data/cavernreborn/worldgen/density_function/cave_tunnel_network.json");
        JsonObject ravineNetwork = readJsonResource("data/cavernreborn/worldgen/density_function/cave_ravine_network.json");
        JsonObject extremeUpperNetwork = readJsonResource("data/cavernreborn/worldgen/density_function/cave_extreme_upper_network.json");
        JsonObject generator = cavernDimension.getAsJsonObject("generator");
        JsonObject biomeSource = generator.getAsJsonObject("biome_source");
        JsonArray biomes = biomeSource.getAsJsonArray("biomes");

        assertEquals("cavernreborn:cavern", cavernDimension.get("type").getAsString());
        assertEquals("minecraft:noise", generator.get("type").getAsString());
        assertEquals("cavernreborn:contained_caves", generator.get("settings").getAsString());
        assertEquals("minecraft:multi_noise", biomeSource.get("type").getAsString());
        assertEquals(10, biomes.size());

        long stoneDepthEntries = StreamSupport.stream(biomes.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .filter(entry -> "cavernreborn:stone_depths".equals(entry.get("biome").getAsString()))
            .count();
        long dripstoneEntries = StreamSupport.stream(biomes.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .filter(entry -> "cavernreborn:dripstone_grotto".equals(entry.get("biome").getAsString()))
            .count();
        long highlandEntries = StreamSupport.stream(biomes.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .filter(entry -> "cavernreborn:highland_hollows".equals(entry.get("biome").getAsString()))
            .count();

        assertEquals(4, stoneDepthEntries);
        assertEquals(2, dripstoneEntries);
        assertEquals(3, highlandEntries);
        assertTrue(hasBiomeEntry(biomes, "cavernreborn:lush_grotto"));
        assertTrue(hasBiomeEntry(biomes, "cavernreborn:dripstone_grotto"));
        assertTrue(hasBiomeEntry(biomes, "cavernreborn:highland_hollows"));

        assertContainedCavesNoiseSettings(containedCaves);
        assertCaveTunnelNetwork(tunnelNetwork);
        assertCaveRavineNetwork(ravineNetwork);
        assertCaveExtremeUpperNetwork(extremeUpperNetwork);

        assertEquals(192, cavernDimensionType.get("height").getAsInt());
        assertEquals(-64, cavernDimensionType.get("min_y").getAsInt());
        assertEquals("cavernreborn:cavern", cavernDimensionType.get("effects").getAsString());
        assertFalse(cavernDimensionType.get("has_skylight").getAsBoolean());
        assertFalse(cavernDimensionType.get("has_ceiling").getAsBoolean());
        assertFalse(cavernDimensionType.get("piglin_safe").getAsBoolean());
        assertFalse(cavernDimensionType.get("respawn_anchor_works").getAsBoolean());
        assertFalse(cavernDimensionType.get("ultrawarm").getAsBoolean());
        assertTrue(cavernDimensionType.get("infiniburn").getAsString().startsWith("#"));
        assertTrue(cavernDimensionType.get("logical_height").getAsInt() <= cavernDimensionType.get("height").getAsInt());
        assertFinalDensityBias(containedCaves);
    }

    @Test
    void cavernBiomeResourcesDefineLegacyLikeMiningSlices() throws IOException {
        JsonObject stoneDepths = readJsonResource("data/cavernreborn/worldgen/biome/stone_depths.json");
        JsonObject lushGrotto = readJsonResource("data/cavernreborn/worldgen/biome/lush_grotto.json");
        JsonObject dripstoneGrotto = readJsonResource("data/cavernreborn/worldgen/biome/dripstone_grotto.json");
        JsonObject highlandHollows = readJsonResource("data/cavernreborn/worldgen/biome/highland_hollows.json");

        assertBiomeFeatures(stoneDepths, 11, "cavernreborn:cavern_monster_room", "cavernreborn:cavern_ore_coal_dense");
        assertBiomeFeatures(lushGrotto, 11, "minecraft:cave_vines", "minecraft:lush_caves_vegetation");
        assertBiomeFeatures(dripstoneGrotto, 11, "minecraft:large_dripstone", "cavernreborn:cavern_ore_gold_dry");
        assertBiomeFeatures(highlandHollows, 11, "cavernreborn:cavern_ore_emerald_highlands", "minecraft:music.overworld.stony_peaks");

        assertTrue(flattenFeatures(stoneDepths).contains("cavernreborn:cavern_ore_iron_dense"));
        assertTrue(flattenFeatures(lushGrotto).contains("minecraft:lush_caves_ceiling_vegetation"));
        assertTrue(flattenFeatures(dripstoneGrotto).contains("minecraft:pointed_dripstone"));
        assertTrue(flattenFeatures(highlandHollows).contains("cavernreborn:cavern_monster_room"));
        assertBiomeOmitsVanillaBaggage(stoneDepths, false);
        assertBiomeOmitsVanillaBaggage(lushGrotto, true);
        assertBiomeOmitsVanillaBaggage(dripstoneGrotto, false);
        assertBiomeOmitsVanillaBaggage(highlandHollows, false);
    }

    @Test
    void cavernBiomeResourcesWirePopulationParitySignals() throws IOException {
        JsonObject stoneDepths = readJsonResource("data/cavernreborn/worldgen/biome/stone_depths.json");
        JsonObject lushGrotto = readJsonResource("data/cavernreborn/worldgen/biome/lush_grotto.json");
        JsonObject dripstoneGrotto = readJsonResource("data/cavernreborn/worldgen/biome/dripstone_grotto.json");
        JsonObject highlandHollows = readJsonResource("data/cavernreborn/worldgen/biome/highland_hollows.json");

        assertTrue(featureStep(stoneDepths, 3).contains("cavernreborn:cavern_monster_room"));
        assertTrue(featureStep(stoneDepths, 8).contains("cavernreborn:cavern_spring_water_falls"));
        assertTrue(featureStep(stoneDepths, 8).contains("cavernreborn:cavern_spring_lava_falls"));
        assertTrue(featureStep(stoneDepths, 9).contains("cavernreborn:cavern_brown_mushroom_patch"));
        assertTrue(featureStep(stoneDepths, 9).contains("cavernreborn:cavern_red_mushroom_patch"));

        assertTrue(featureStep(lushGrotto, 8).contains("cavernreborn:cavern_spring_water_falls"));
        assertFalse(featureStep(lushGrotto, 8).contains("cavernreborn:cavern_spring_lava_falls"));
        assertTrue(featureStep(lushGrotto, 9).contains("cavernreborn:cavern_brown_mushroom_patch"));
        assertTrue(featureStep(lushGrotto, 9).contains("cavernreborn:cavern_red_mushroom_patch"));
        assertTrue(featureStep(lushGrotto, 9).contains("minecraft:lush_caves_vegetation"));

        assertFalse(featureStep(dripstoneGrotto, 8).contains("cavernreborn:cavern_spring_water_falls"));
        assertTrue(featureStep(dripstoneGrotto, 8).contains("cavernreborn:cavern_spring_lava_falls"));
        assertFalse(featureStep(dripstoneGrotto, 9).contains("cavernreborn:cavern_brown_mushroom_patch"));
        assertTrue(featureStep(dripstoneGrotto, 7).contains("minecraft:dripstone_cluster"));

        assertTrue(featureStep(highlandHollows, 8).contains("cavernreborn:cavern_spring_water_falls"));
        assertTrue(featureStep(highlandHollows, 8).contains("cavernreborn:cavern_spring_lava_falls"));
        assertTrue(featureStep(highlandHollows, 9).contains("cavernreborn:cavern_brown_mushroom_patch"));
        assertFalse(featureStep(highlandHollows, 9).contains("cavernreborn:cavern_red_mushroom_patch"));
    }

    @Test
    void placedFeaturesAndMineshaftTagProtectWorldgenBaseline() throws IOException {
        JsonObject mineshaftTag = readJsonResource("data/minecraft/tags/worldgen/biome/has_structure/mineshaft.json");
        JsonObject monsterRoom = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_monster_room.json");
        JsonObject deepMonsterRoom = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_monster_room_deep.json");
        JsonObject coalDense = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_ore_coal_dense.json");
        JsonObject ironDense = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_ore_iron_dense.json");
        JsonObject goldDry = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_ore_gold_dry.json");
        JsonObject emeraldHighlands = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_ore_emerald_highlands.json");
        JsonObject springWaterFalls = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_spring_water_falls.json");
        JsonObject springLavaFalls = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_spring_lava_falls.json");
        JsonObject brownMushroomPatch = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_brown_mushroom_patch.json");
        JsonObject redMushroomPatch = readJsonResource("data/cavernreborn/worldgen/placed_feature/cavern_red_mushroom_patch.json");

        List<String> mineshaftBiomes = arrayStrings(mineshaftTag.getAsJsonArray("values"));
        assertFalse(mineshaftTag.get("replace").getAsBoolean());
        assertTrue(mineshaftBiomes.contains("cavernreborn:stone_depths"));
        assertTrue(mineshaftBiomes.contains("cavernreborn:lush_grotto"));
        assertTrue(mineshaftBiomes.contains("cavernreborn:dripstone_grotto"));
        assertTrue(mineshaftBiomes.contains("cavernreborn:highland_hollows"));

        assertPlacedFeature(monsterRoom, "minecraft:monster_room", 16, 96, 1);
        assertPlacedFeature(deepMonsterRoom, "minecraft:monster_room", 8, 0, 6);
        assertPlacedFeature(coalDense, "minecraft:ore_coal_buried", 22, 120, -16);
        assertPlacedFeature(ironDense, "minecraft:ore_iron", 18, 104, -48);
        assertPlacedFeature(goldDry, "minecraft:ore_gold_buried", 6, 40, -56);
        assertPlacedFeature(emeraldHighlands, "minecraft:ore_emerald", 14, 120, 16);
        assertPlacedFeature(springWaterFalls, "minecraft:spring_water", 14, 56, 16);
        assertPlacedFeature(springLavaFalls, "minecraft:spring_lava_overworld", 10, 24, 8);
        assertPlacedFeature(brownMushroomPatch, "minecraft:patch_brown_mushroom", 6, 88, 8);
        assertPlacedFeature(redMushroomPatch, "minecraft:patch_red_mushroom", 4, 72, 8);
    }

    @Test
    void generatedWorldgenResourcesResolveFromRuntimeClasspath() {
        URL stoneDepths = resourceUrl("data/cavernreborn/worldgen/biome/stone_depths.json");
        URL highlandHollows = resourceUrl("data/cavernreborn/worldgen/biome/highland_hollows.json");
        URL mineshaftTag = resourceUrl("data/minecraft/tags/worldgen/biome/has_structure/mineshaft.json");
        URL extremeUpperNetwork = resourceUrl("data/cavernreborn/worldgen/density_function/cave_extreme_upper_network.json");
        URL springWaterFalls = resourceUrl("data/cavernreborn/worldgen/placed_feature/cavern_spring_water_falls.json");
        URL brownMushroomPatch = resourceUrl("data/cavernreborn/worldgen/placed_feature/cavern_brown_mushroom_patch.json");

        assertClassPathOrigin(stoneDepths, "data/cavernreborn/worldgen/biome/stone_depths.json");
        assertClassPathOrigin(highlandHollows, "data/cavernreborn/worldgen/biome/highland_hollows.json");
        assertClassPathOrigin(mineshaftTag, "data/minecraft/tags/worldgen/biome/has_structure/mineshaft.json");
        assertClassPathOrigin(extremeUpperNetwork, "data/cavernreborn/worldgen/density_function/cave_extreme_upper_network.json");
        assertClassPathOrigin(springWaterFalls, "data/cavernreborn/worldgen/placed_feature/cavern_spring_water_falls.json");
        assertClassPathOrigin(brownMushroomPatch, "data/cavernreborn/worldgen/placed_feature/cavern_brown_mushroom_patch.json");
    }

    @Test
    void terrainSignatureResourcesEncodeDenseMassHotBandAndVerticalLayering() {
        String rawNoiseSettings = readResource("data/cavernreborn/worldgen/noise_settings/contained_caves.json");
        String rawExtremeUpper = readResource("data/cavernreborn/worldgen/density_function/cave_extreme_upper_network.json");

        assertTrue(rawNoiseSettings.contains("\"cavernreborn:cave_extreme_upper_network\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:overworld/caves/noodle\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:magma_block\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:basalt\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:blackstone\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:calcite\""));
        assertTrue(rawNoiseSettings.contains("\"absolute\": -48"));
        assertTrue(rawNoiseSettings.contains("\"absolute\": -32"));
        assertTrue(rawExtremeUpper.contains("\"minecraft:overworld/caves/pillars\""));
        assertTrue(rawExtremeUpper.contains("\"minecraft:overworld/caves/entrances\""));
    }

    private static void assertContainedCavesNoiseSettings(JsonObject noiseSettings) {
        assertNotNull(noiseSettings);
        assertFalse(noiseSettings.get("aquifers_enabled").getAsBoolean());
        assertTrue(noiseSettings.get("ore_veins_enabled").getAsBoolean());
        assertFalse(noiseSettings.get("disable_mob_generation").getAsBoolean());
        assertTrue(noiseSettings.get("legacy_random_source").getAsBoolean());

        JsonObject noise = noiseSettings.getAsJsonObject("noise");
        assertNotNull(noise);
        assertEquals(192, noise.get("height").getAsInt());
        assertEquals(-64, noise.get("min_y").getAsInt());
        assertEquals(1, noise.get("size_horizontal").getAsInt());
        assertEquals(2, noise.get("size_vertical").getAsInt());
        assertEquals(noise.get("min_y").getAsInt(), noiseSettings.get("sea_level").getAsInt());

        JsonObject defaultBlock = noiseSettings.getAsJsonObject("default_block");
        JsonObject defaultFluid = noiseSettings.getAsJsonObject("default_fluid");
        assertNotNull(defaultBlock);
        assertNotNull(defaultFluid);
        assertEquals("minecraft:stone", defaultBlock.get("Name").getAsString());
        assertEquals("minecraft:water", defaultFluid.get("Name").getAsString());

        JsonObject noiseRouter = noiseSettings.getAsJsonObject("noise_router");
        assertEquals("minecraft:noise", noiseRouter.getAsJsonObject("vein_gap").get("type").getAsString());
        assertEquals("minecraft:add", noiseRouter.getAsJsonObject("vein_ridged").get("type").getAsString());
        assertEquals("minecraft:interpolated", noiseRouter.getAsJsonObject("vein_toggle").get("type").getAsString());

        String rawNoiseSettings = readResource("data/cavernreborn/worldgen/noise_settings/contained_caves.json");
        assertTrue(rawNoiseSettings.contains("\"cavernreborn:lush_grotto\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:moss_block\""));
        assertTrue(rawNoiseSettings.contains("\"cavernreborn:dripstone_grotto\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:dripstone_block\""));
        assertTrue(rawNoiseSettings.contains("\"cavernreborn:highland_hollows\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:tuff\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:calcite\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:magma_block\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:basalt\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:blackstone\""));
        assertTrue(rawNoiseSettings.contains("\"cavernreborn:cave_extreme_upper_network\""));
        assertTrue(rawNoiseSettings.contains("\"minecraft:overworld/caves/noodle\""));
    }

    private static void assertFinalDensityBias(JsonObject noiseSettings) {
        JsonObject finalDensity = noiseSettings.getAsJsonObject("noise_router").getAsJsonObject("final_density");
        assertNotNull(finalDensity);

        assertEquals("minecraft:min", finalDensity.get("type").getAsString());
        JsonObject caveNetworks = finalDensity.getAsJsonObject("argument1");
        assertNotNull(caveNetworks);
        assertEquals("minecraft:min", caveNetworks.get("type").getAsString());
        assertEquals("cavernreborn:cave_ravine_network", caveNetworks.get("argument2").getAsString());

        JsonObject tunnelMin = caveNetworks.getAsJsonObject("argument1");
        assertNotNull(tunnelMin);
        assertEquals("minecraft:min", tunnelMin.get("type").getAsString());
        assertEquals("cavernreborn:cave_tunnel_network", tunnelMin.get("argument2").getAsString());

        JsonObject baseDensity = tunnelMin.getAsJsonObject("argument1");
        assertNotNull(baseDensity);
        assertEquals("minecraft:squeeze", baseDensity.get("type").getAsString());

        JsonObject densityMul = baseDensity.getAsJsonObject("argument");
        assertNotNull(densityMul);
        assertEquals("minecraft:mul", densityMul.get("type").getAsString());
        assertEquals(0.7, densityMul.get("argument1").getAsDouble(), 0.0);

        JsonObject upperVariation = finalDensity.getAsJsonObject("argument2");
        assertNotNull(upperVariation);
        assertEquals("minecraft:min", upperVariation.get("type").getAsString());
        assertEquals("cavernreborn:cave_extreme_upper_network", upperVariation.get("argument1").getAsString());
        assertEquals("minecraft:overworld/caves/noodle", upperVariation.get("argument2").getAsString());
    }

    private static void assertCaveTunnelNetwork(JsonObject tunnelNetwork) {
        assertNotNull(tunnelNetwork);
        assertEquals("minecraft:min", tunnelNetwork.get("type").getAsString());

        JsonObject spaghettiBranch = tunnelNetwork.getAsJsonObject("argument1");
        assertNotNull(spaghettiBranch);
        assertEquals("minecraft:add", spaghettiBranch.get("type").getAsString());
        assertEquals(-0.04, spaghettiBranch.get("argument1").getAsDouble(), 0.0);

        JsonObject weightedSpaghetti = spaghettiBranch.getAsJsonObject("argument2");
        assertNotNull(weightedSpaghetti);
        assertEquals("minecraft:mul", weightedSpaghetti.get("type").getAsString());
        assertEquals(1.0, weightedSpaghetti.get("argument1").getAsDouble(), 0.0);

        JsonObject spaghettiLayer = weightedSpaghetti.getAsJsonObject("argument2");
        assertNotNull(spaghettiLayer);
        assertEquals("minecraft:add", spaghettiLayer.get("type").getAsString());
        assertEquals("minecraft:overworld/caves/spaghetti_2d", spaghettiLayer.get("argument1").getAsString());
        assertEquals("minecraft:overworld/caves/spaghetti_roughness_function", spaghettiLayer.get("argument2").getAsString());

        JsonObject chamberBranch = tunnelNetwork.getAsJsonObject("argument2");
        assertNotNull(chamberBranch);
        assertEquals("minecraft:add", chamberBranch.get("type").getAsString());
        assertEquals(0.2, chamberBranch.get("argument1").getAsDouble(), 0.0);

        JsonObject chamberMul = chamberBranch.getAsJsonObject("argument2");
        assertNotNull(chamberMul);
        assertEquals("minecraft:mul", chamberMul.get("type").getAsString());
        assertEquals(0.45, chamberMul.get("argument1").getAsDouble(), 0.0);

        JsonObject bandedEntrances = chamberMul.getAsJsonObject("argument2");
        assertNotNull(bandedEntrances);
        assertEquals("minecraft:mul", bandedEntrances.get("type").getAsString());

        JsonObject heightBand = bandedEntrances.getAsJsonObject("argument1");
        assertNotNull(heightBand);
        assertEquals("minecraft:mul", heightBand.get("type").getAsString());

        JsonObject lowerBand = heightBand.getAsJsonObject("argument1");
        assertNotNull(lowerBand);
        assertEquals("minecraft:y_clamped_gradient", lowerBand.get("type").getAsString());
        assertEquals(-24, lowerBand.get("from_y").getAsInt());
        assertEquals(32, lowerBand.get("to_y").getAsInt());

        JsonObject upperBand = heightBand.getAsJsonObject("argument2");
        assertNotNull(upperBand);
        assertEquals("minecraft:y_clamped_gradient", upperBand.get("type").getAsString());
        assertEquals(32, upperBand.get("from_y").getAsInt());
        assertEquals(96, upperBand.get("to_y").getAsInt());

        assertEquals("minecraft:overworld/caves/entrances", bandedEntrances.get("argument2").getAsString());
    }

    private static void assertCaveRavineNetwork(JsonObject ravineNetwork) {
        assertNotNull(ravineNetwork);
        assertEquals("minecraft:add", ravineNetwork.get("type").getAsString());
        assertEquals(0.28, ravineNetwork.get("argument1").getAsDouble(), 0.0);

        JsonObject weightedLayer = ravineNetwork.getAsJsonObject("argument2");
        assertNotNull(weightedLayer);
        assertEquals("minecraft:mul", weightedLayer.get("type").getAsString());
        assertEquals(0.58, weightedLayer.get("argument1").getAsDouble(), 0.0);

        JsonObject connectorField = weightedLayer.getAsJsonObject("argument2");
        assertNotNull(connectorField);
        assertEquals("minecraft:mul", connectorField.get("type").getAsString());

        JsonObject heightBand = connectorField.getAsJsonObject("argument1");
        assertNotNull(heightBand);
        assertEquals("minecraft:mul", heightBand.get("type").getAsString());

        JsonObject lowerBand = heightBand.getAsJsonObject("argument1");
        assertNotNull(lowerBand);
        assertEquals("minecraft:y_clamped_gradient", lowerBand.get("type").getAsString());
        assertEquals(-56, lowerBand.get("from_y").getAsInt());
        assertEquals(12, lowerBand.get("to_y").getAsInt());
        assertEquals(0.0, lowerBand.get("from_value").getAsDouble(), 0.0);
        assertEquals(1.0, lowerBand.get("to_value").getAsDouble(), 0.0);

        JsonObject upperBand = heightBand.getAsJsonObject("argument2");
        assertNotNull(upperBand);
        assertEquals("minecraft:y_clamped_gradient", upperBand.get("type").getAsString());
        assertEquals(12, upperBand.get("from_y").getAsInt());
        assertEquals(104, upperBand.get("to_y").getAsInt());
        assertEquals(1.0, upperBand.get("from_value").getAsDouble(), 0.0);
        assertEquals(0.0, upperBand.get("to_value").getAsDouble(), 0.0);

        JsonObject roughSpaghettiLayer = connectorField.getAsJsonObject("argument2");
        assertNotNull(roughSpaghettiLayer);
        assertEquals("minecraft:add", roughSpaghettiLayer.get("type").getAsString());

        JsonObject cachedSpaghetti = roughSpaghettiLayer.getAsJsonObject("argument1");
        assertNotNull(cachedSpaghetti);
        assertEquals("minecraft:cache_once", cachedSpaghetti.get("type").getAsString());
        assertEquals("minecraft:overworld/caves/spaghetti_2d", cachedSpaghetti.get("argument").getAsString());

        JsonObject roughnessMul = roughSpaghettiLayer.getAsJsonObject("argument2");
        assertNotNull(roughnessMul);
        assertEquals("minecraft:mul", roughnessMul.get("type").getAsString());
        assertEquals(0.6, roughnessMul.get("argument1").getAsDouble(), 0.0);
        assertEquals("minecraft:overworld/caves/spaghetti_roughness_function", roughnessMul.get("argument2").getAsString());
    }

    private static void assertCaveExtremeUpperNetwork(JsonObject extremeUpperNetwork) {
        assertNotNull(extremeUpperNetwork);
        assertEquals("minecraft:max", extremeUpperNetwork.get("type").getAsString());

        JsonObject chamberBranch = extremeUpperNetwork.getAsJsonObject("argument1");
        assertNotNull(chamberBranch);
        assertEquals("minecraft:add", chamberBranch.get("type").getAsString());
        assertEquals(0.26, chamberBranch.get("argument1").getAsDouble(), 0.0);

        JsonObject weightedChamber = chamberBranch.getAsJsonObject("argument2");
        assertNotNull(weightedChamber);
        assertEquals("minecraft:mul", weightedChamber.get("type").getAsString());
        assertEquals(0.6, weightedChamber.get("argument1").getAsDouble(), 0.0);

        JsonObject bandedEntrances = weightedChamber.getAsJsonObject("argument2");
        assertNotNull(bandedEntrances);
        assertEquals("minecraft:mul", bandedEntrances.get("type").getAsString());

        JsonObject heightBand = bandedEntrances.getAsJsonObject("argument1");
        assertNotNull(heightBand);
        assertEquals("minecraft:mul", heightBand.get("type").getAsString());

        JsonObject lowerBand = heightBand.getAsJsonObject("argument1");
        assertNotNull(lowerBand);
        assertEquals("minecraft:y_clamped_gradient", lowerBand.get("type").getAsString());
        assertEquals(24, lowerBand.get("from_y").getAsInt());
        assertEquals(72, lowerBand.get("to_y").getAsInt());

        JsonObject upperBand = heightBand.getAsJsonObject("argument2");
        assertNotNull(upperBand);
        assertEquals("minecraft:y_clamped_gradient", upperBand.get("type").getAsString());
        assertEquals(72, upperBand.get("from_y").getAsInt());
        assertEquals(124, upperBand.get("to_y").getAsInt());

        assertEquals("minecraft:overworld/caves/entrances", bandedEntrances.get("argument2").getAsString());

        JsonObject pillarSupport = extremeUpperNetwork.getAsJsonObject("argument2");
        assertNotNull(pillarSupport);
        assertEquals("minecraft:range_choice", pillarSupport.get("type").getAsString());
        assertEquals("minecraft:overworld/caves/pillars", pillarSupport.get("input").getAsString());
        assertEquals("minecraft:overworld/caves/pillars", pillarSupport.get("when_out_of_range").getAsString());
    }

    private static void assertBiomeFeatures(JsonObject biome, int expectedSteps, String expectedFeature, String anotherExpectedFeature) {
        assertNotNull(biome);
        JsonArray features = biome.getAsJsonArray("features");
        assertEquals(expectedSteps, features.size());
        List<String> flattenedFeatures = flattenFeatures(biome);
        assertTrue(flattenedFeatures.contains(expectedFeature));
        assertTrue(flattenedFeatures.contains(anotherExpectedFeature) || readResourceFromJson(biome).contains(anotherExpectedFeature));
    }

    private static void assertBiomeOmitsVanillaBaggage(JsonObject biome, boolean allowLushWaterFauna) {
        assertFalse(biome.get("has_precipitation").getAsBoolean());

        String serializedBiome = readResourceFromJson(biome);
        assertFalse(serializedBiome.contains("minecraft:underwater_magma"));
        assertFalse(serializedBiome.contains("minecraft:disk_sand"));
        assertFalse(serializedBiome.contains("minecraft:disk_clay"));
        assertFalse(serializedBiome.contains("minecraft:disk_gravel"));
        assertFalse(serializedBiome.contains("minecraft:freeze_top_layer"));

        if (allowLushWaterFauna) {
            assertFalse(serializedBiome.contains("minecraft:tropical_fish"));
            return;
        }

        assertFalse(serializedBiome.contains("minecraft:drowned"));
    }

    private static void assertClassPathOrigin(URL resourceUrl, String resourcePath) {
        assertNotNull(resourceUrl, "Missing resource URL for " + resourcePath);
        String normalized = resourceUrl.toString().replace('\\', '/');
        assertTrue(
            normalized.contains("/build/resources/main/")
                || normalized.contains("/src/generated/resources/")
                || normalized.contains(".jar!/"),
            () -> "Unexpected resource origin for " + resourcePath + ": " + normalized
        );
    }

    private static void assertPlacedFeature(JsonObject placedFeature, String featureId, int count, int maxHeight, int minHeight) {
        assertEquals(featureId, placedFeature.get("feature").getAsString());
        JsonArray placement = placedFeature.getAsJsonArray("placement");
        assertEquals(count, placement.get(0).getAsJsonObject().get("count").getAsInt());

        JsonObject height = placement.get(2).getAsJsonObject().getAsJsonObject("height");
        assertEquals(maxHeight, extractHeightValue(height.getAsJsonObject("max_inclusive")));
        assertEquals(minHeight, extractHeightValue(height.getAsJsonObject("min_inclusive")));
    }

    private static int extractHeightValue(JsonObject height) {
        if (height.has("absolute")) {
            return height.get("absolute").getAsInt();
        }

        if (height.has("above_bottom")) {
            return height.get("above_bottom").getAsInt();
        }

        if (height.has("below_top")) {
            return height.get("below_top").getAsInt();
        }

        throw new IllegalArgumentException("Unsupported height anchor: " + height);
    }

    private static boolean hasBiomeEntry(JsonArray biomes, String biomeId) {
        return StreamSupport.stream(biomes.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .anyMatch(entry -> biomeId.equals(entry.get("biome").getAsString()));
    }

    private static List<String> flattenFeatures(JsonObject biome) {
        JsonArray features = biome.getAsJsonArray("features");
        return StreamSupport.stream(features.spliterator(), false)
            .map(JsonElement::getAsJsonArray)
            .flatMap(step -> StreamSupport.stream(step.spliterator(), false))
            .map(JsonElement::getAsString)
            .toList();
    }

    private static List<String> featureStep(JsonObject biome, int index) {
        JsonArray features = biome.getAsJsonArray("features");
        return arrayStrings(features.get(index).getAsJsonArray());
    }

    private static List<String> arrayStrings(JsonArray array) {
        return StreamSupport.stream(array.spliterator(), false)
            .map(JsonElement::getAsString)
            .toList();
    }

    private static String readResourceFromJson(JsonObject jsonObject) {
        return jsonObject.toString();
    }

    private static JsonObject readJsonResource(String resourcePath) throws IOException {
        ClassLoader classLoader = CavernWorldgenResourcesTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IllegalStateException("Missing test resource: " + resourcePath);
        }

        try (InputStream stream = inputStream) {
            String json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            return JsonParser.parseString(json).getAsJsonObject();
        }
    }

    private static String readResource(String resourcePath) {
        ClassLoader classLoader = CavernWorldgenResourcesTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IllegalStateException("Missing test resource: " + resourcePath);
        }

        try (InputStream stream = inputStream) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read resource: " + resourcePath, exception);
        }
    }

    private static URL resourceUrl(String resourcePath) {
        return CavernWorldgenResourcesTest.class.getClassLoader().getResource(resourcePath);
    }
}
