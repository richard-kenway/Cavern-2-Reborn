package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavernPortalTagResourcesTest {
    @Test
    void portalFrameTagIncludesBothSupportedLegacyBlocks() throws IOException {
        JsonObject blockTag = readJsonResource("data/cavernreborn/tags/blocks/cavern_portal_frames.json");
        Set<String> values = values(blockTag.getAsJsonArray("values"));

        assertTrue(values.contains("minecraft:mossy_cobblestone"));
        assertTrue(values.contains("minecraft:mossy_stone_bricks"));
    }

    @Test
    void portalActivatorTagIncludesEmeraldBaselineActivator() throws IOException {
        JsonObject itemTag = readJsonResource("data/cavernreborn/tags/items/cavern_portal_activators.json");
        Set<String> values = values(itemTag.getAsJsonArray("values"));

        assertTrue(values.contains("minecraft:emerald"));
    }

    private static Set<String> values(JsonArray values) {
        return StreamSupport.stream(values.spliterator(), false)
            .map(element -> element.getAsString())
            .collect(Collectors.toSet());
    }

    private static JsonObject readJsonResource(String resourcePath) throws IOException {
        InputStream inputStream = CavernPortalTagResourcesTest.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalStateException("Missing test resource: " + resourcePath);
        }

        try (InputStream stream = inputStream) {
            String json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            return JsonParser.parseString(json).getAsJsonObject();
        }
    }
}
