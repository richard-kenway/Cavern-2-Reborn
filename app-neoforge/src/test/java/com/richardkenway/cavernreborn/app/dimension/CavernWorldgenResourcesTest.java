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
    void cavernDimensionUsesCaveLikeDimensionTypeSettings() throws IOException {
        JsonObject cavernDimension = readJsonResource("data/cavernreborn/dimension/cavern.json");
        JsonObject cavernDimensionType = readJsonResource("data/cavernreborn/dimension_type/cavern.json");
        JsonObject generator = cavernDimension.getAsJsonObject("generator");
        JsonObject biomeSource = generator.getAsJsonObject("biome_source");
        JsonArray biomes = biomeSource.getAsJsonArray("biomes");

        assertEquals("cavernreborn:cavern", cavernDimension.get("type").getAsString());
        assertEquals("minecraft:noise", generator.get("type").getAsString());
        assertEquals("minecraft:caves", generator.get("settings").getAsString());
        assertEquals("minecraft:multi_noise", biomeSource.get("type").getAsString());
        assertEquals(2, biomes.size());
        assertBiomeFamilyEntry(biomes.get(0).getAsJsonObject(), "minecraft:dripstone_caves");
        assertBiomeFamilyEntry(biomes.get(1).getAsJsonObject(), "minecraft:lush_caves");
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
