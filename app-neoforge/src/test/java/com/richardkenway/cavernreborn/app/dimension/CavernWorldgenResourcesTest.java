package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavernWorldgenResourcesTest {
    @Test
    void cavernDimensionUsesContainedCavesNoiseSettings() throws IOException {
        JsonObject cavernDimension = readJsonResource("data/cavernreborn/dimension/cavern.json");
        JsonObject cavernDimensionType = readJsonResource("data/cavernreborn/dimension_type/cavern.json");
        JsonObject containedCaves = readJsonResource("data/cavernreborn/worldgen/noise_settings/contained_caves.json");
        JsonObject generator = cavernDimension.getAsJsonObject("generator");
        JsonObject biomeSource = generator.getAsJsonObject("biome_source");
        JsonArray biomes = biomeSource.getAsJsonArray("biomes");

        assertEquals("cavernreborn:cavern", cavernDimension.get("type").getAsString());
        assertEquals("minecraft:noise", generator.get("type").getAsString());
        assertEquals("cavernreborn:contained_caves", generator.get("settings").getAsString());
        assertEquals("minecraft:multi_noise", biomeSource.get("type").getAsString());
        assertEquals(2, biomes.size());
        assertBiomeFamilyEntry(biomes.get(0).getAsJsonObject(), "minecraft:dripstone_caves");
        assertBiomeFamilyEntry(biomes.get(1).getAsJsonObject(), "minecraft:lush_caves");
        assertContainedCavesNoiseSettings(containedCaves);
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

    private static void assertContainedCavesNoiseSettings(JsonObject noiseSettings) {
        assertNotNull(noiseSettings);
        assertFalse(noiseSettings.get("aquifers_enabled").getAsBoolean());
        assertFalse(noiseSettings.get("ore_veins_enabled").getAsBoolean());
        assertFalse(noiseSettings.get("disable_mob_generation").getAsBoolean());
        assertTrue(noiseSettings.get("legacy_random_source").getAsBoolean());
        assertEquals(16, noiseSettings.get("sea_level").getAsInt());

        JsonObject noise = noiseSettings.getAsJsonObject("noise");
        assertNotNull(noise);
        assertEquals(192, noise.get("height").getAsInt());
        assertEquals(-64, noise.get("min_y").getAsInt());
        assertEquals(1, noise.get("size_horizontal").getAsInt());
        assertEquals(1, noise.get("size_vertical").getAsInt());

        JsonObject defaultBlock = noiseSettings.getAsJsonObject("default_block");
        JsonObject defaultFluid = noiseSettings.getAsJsonObject("default_fluid");
        assertNotNull(defaultBlock);
        assertNotNull(defaultFluid);
        assertEquals("minecraft:stone", defaultBlock.get("Name").getAsString());
        assertEquals("minecraft:water", defaultFluid.get("Name").getAsString());
    }

    private static void assertFinalDensityBias(JsonObject noiseSettings) {
        JsonObject finalDensity = noiseSettings.getAsJsonObject("noise_router").getAsJsonObject("final_density");
        assertNotNull(finalDensity);

        JsonObject densityMul = finalDensity.getAsJsonObject("argument");
        assertNotNull(densityMul);
        assertEquals("minecraft:squeeze", finalDensity.get("type").getAsString());
        assertEquals("minecraft:mul", densityMul.get("type").getAsString());
        assertEquals(0.64, densityMul.get("argument1").getAsDouble(), 0.0);

        JsonObject interpolated = densityMul.getAsJsonObject("argument2");
        assertNotNull(interpolated);
        assertEquals("minecraft:interpolated", interpolated.get("type").getAsString());

        JsonObject blendedDensity = interpolated.getAsJsonObject("argument");
        assertNotNull(blendedDensity);
        assertEquals("minecraft:blend_density", blendedDensity.get("type").getAsString());

        JsonObject densityAdd = blendedDensity.getAsJsonObject("argument");
        assertNotNull(densityAdd);
        assertEquals("minecraft:add", densityAdd.get("type").getAsString());
        assertEquals(3.0, densityAdd.get("argument1").getAsDouble(), 0.0);

        JsonObject densityMultiplier = densityAdd.getAsJsonObject("argument2");
        assertNotNull(densityMultiplier);
        assertEquals("minecraft:mul", densityMultiplier.get("type").getAsString());

        JsonObject verticalBias = densityMultiplier.getAsJsonObject("argument2");
        assertNotNull(verticalBias);
        assertEquals("minecraft:add", verticalBias.get("type").getAsString());
        assertEquals(-2.0, verticalBias.get("argument1").getAsDouble(), 0.0);
    }

    private static void assertBiomeFamilyEntry(JsonObject biomeEntry, String expectedBiome) {
        assertNotNull(biomeEntry);
        assertEquals(expectedBiome, biomeEntry.get("biome").getAsString());

        JsonObject parameters = biomeEntry.getAsJsonObject("parameters");
        assertNotNull(parameters);
        assertTrue(parameters.has("temperature"));
        assertTrue(parameters.has("humidity"));
        assertTrue(parameters.has("continentalness"));
        assertTrue(parameters.has("erosion"));
        assertTrue(parameters.has("depth"));
        assertTrue(parameters.has("weirdness"));
        assertTrue(parameters.has("offset"));
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
}
