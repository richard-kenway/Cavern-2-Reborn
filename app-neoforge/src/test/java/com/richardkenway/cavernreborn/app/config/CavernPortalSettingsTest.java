package com.richardkenway.cavernreborn.app.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

class CavernPortalSettingsTest {
    @Test
    void defaultsMatchRegressionBaseline() {
        CavernPortalSettings settings = CavernPortalSettings.defaults();

        assertEquals(32, settings.findPortalRange());
        assertEquals(16, settings.findPortalVerticalRange());
        assertEquals(20L, settings.successfulInteractionCooldownTicks());
        assertEquals(10L, settings.failedInteractionCooldownTicks());
        assertEquals(20L, settings.feedbackSuppressionTicks());
        assertEquals("minecraft:mossy_cobblestone", settings.frameBlockId());
    }

    @Test
    void explicitFrameBlockOverrideSupportsMossyStoneBricks() {
        Properties properties = new Properties();
        properties.setProperty("frameBlock", "minecraft:mossy_stone_bricks");

        CavernPortalSettings settings = CavernPortalSettings.fromProperties(properties);

        assertEquals("minecraft:mossy_stone_bricks", settings.frameBlockId());
    }

    @Test
    void invalidFrameBlockFallsBackToDefaultBlock() {
        Properties properties = new Properties();
        properties.setProperty("frameBlock", "bad frame id");

        CavernPortalSettings settings = CavernPortalSettings.fromProperties(properties);

        assertEquals(CavernPortalSettings.defaults().frameBlockId(), settings.frameBlockId());
    }
}
