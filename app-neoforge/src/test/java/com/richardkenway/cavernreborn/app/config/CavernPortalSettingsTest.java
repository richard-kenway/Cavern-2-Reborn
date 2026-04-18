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
    }

    @Test
    void explicitNumericOverridesAreParsed() {
        Properties properties = new Properties();
        properties.setProperty("findPortalRange", "48");
        properties.setProperty("findPortalVerticalRange", "24");
        properties.setProperty("successfulInteractionCooldownTicks", "30");
        properties.setProperty("failedInteractionCooldownTicks", "12");
        properties.setProperty("feedbackSuppressionTicks", "40");

        CavernPortalSettings settings = CavernPortalSettings.fromProperties(properties);

        assertEquals(48, settings.findPortalRange());
        assertEquals(24, settings.findPortalVerticalRange());
        assertEquals(30L, settings.successfulInteractionCooldownTicks());
        assertEquals(12L, settings.failedInteractionCooldownTicks());
        assertEquals(40L, settings.feedbackSuppressionTicks());
    }

    @Test
    void invalidNumericSettingsFallBackToDefaults() {
        Properties properties = new Properties();
        properties.setProperty("findPortalRange", "bad");
        properties.setProperty("successfulInteractionCooldownTicks", " ");

        CavernPortalSettings settings = CavernPortalSettings.fromProperties(properties);

        assertEquals(CavernPortalSettings.defaults().findPortalRange(), settings.findPortalRange());
        assertEquals(
            CavernPortalSettings.defaults().successfulInteractionCooldownTicks(),
            settings.successfulInteractionCooldownTicks()
        );
    }
}
