package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavernWorldgenResourcesTest {
    @Test
    void cavernDimensionUsesCaveOrientedBaselineProfile() throws IOException {
        JsonObject cavernDimension = readJsonResource("data/cavernreborn/dimension/cavern.json");
        JsonObject cavernDimensionType = readJsonResource("data/cavernreborn/dimension_type/cavern.json");
        JsonObject generator = cavernDimension.getAsJsonObject("generator");
        JsonObject biomeSource = generator.getAsJsonObject("biome_source");

        assertEquals("minecraft:noise", generator.get("type").getAsString());
        assertEquals("minecraft:caves", generator.get("settings").getAsString());
        assertEquals("minecraft:fixed", biomeSource.get("type").getAsString());
        assertEquals("minecraft:dripstone_caves", biomeSource.get("biome").getAsString());
        assertEquals(192, cavernDimensionType.get("height").getAsInt());
        assertEquals(-64, cavernDimensionType.get("min_y").getAsInt());
        assertTrue(cavernDimensionType.has("has_skylight"));
        assertTrue(cavernDimensionType.has("has_ceiling"));
        assertTrue(cavernDimensionType.get("infiniburn").getAsString().startsWith("#"));
        assertTrue(cavernDimensionType.get("logical_height").getAsInt() <= cavernDimensionType.get("height").getAsInt());
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
