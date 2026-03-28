package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class CavernWorldgenResourcesTest {
    @Test
    void cavernDimensionUsesCaveOrientedBaselineProfile() throws IOException {
        String cavernDimension = readResource("data/cavernreborn/dimension/cavern.json");
        String cavernDimensionType = readResource("data/cavernreborn/dimension_type/cavern.json");

        assertTrue(cavernDimension.contains("\"settings\": \"minecraft:caves\""));
        assertTrue(cavernDimension.contains("\"biome\": \"minecraft:dripstone_caves\""));
        assertTrue(cavernDimensionType.contains("\"height\": 192"));
        assertTrue(cavernDimensionType.contains("\"min_y\": -64"));
    }

    private static String readResource(String resourcePath) throws IOException {
        ClassLoader classLoader = CavernWorldgenResourcesTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IllegalStateException("Missing test resource: " + resourcePath);
        }

        try (InputStream stream = inputStream) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
