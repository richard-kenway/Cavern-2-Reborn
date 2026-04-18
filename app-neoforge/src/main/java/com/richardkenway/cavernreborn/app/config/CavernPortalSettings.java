package com.richardkenway.cavernreborn.app.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

import com.mojang.logging.LogUtils;

import org.slf4j.Logger;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public record CavernPortalSettings(
    int findPortalRange,
    int findPortalVerticalRange,
    long successfulInteractionCooldownTicks,
    long failedInteractionCooldownTicks,
    long feedbackSuppressionTicks,
    String frameBlockId
) {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Path CONFIG_PATH = Path.of("config", "cavernreborn-portal.properties");
    private static final String FRAME_BLOCK_KEY = "frameBlock";
    private static final String FIND_PORTAL_RANGE_KEY = "findPortalRange";
    private static final String FIND_PORTAL_VERTICAL_RANGE_KEY = "findPortalVerticalRange";
    private static final String SUCCESSFUL_COOLDOWN_KEY = "successfulInteractionCooldownTicks";
    private static final String FAILED_COOLDOWN_KEY = "failedInteractionCooldownTicks";
    private static final String FEEDBACK_SUPPRESSION_KEY = "feedbackSuppressionTicks";
    private static final String DEFAULT_FRAME_BLOCK_ID = "minecraft:mossy_cobblestone";
    private static final CavernPortalSettings DEFAULTS = new CavernPortalSettings(
        32,
        16,
        20L,
        10L,
        20L,
        DEFAULT_FRAME_BLOCK_ID
    );

    public CavernPortalSettings {
        findPortalRange = requirePositive(findPortalRange, FIND_PORTAL_RANGE_KEY);
        findPortalVerticalRange = requirePositive(findPortalVerticalRange, FIND_PORTAL_VERTICAL_RANGE_KEY);
        successfulInteractionCooldownTicks = requirePositive(successfulInteractionCooldownTicks, SUCCESSFUL_COOLDOWN_KEY);
        failedInteractionCooldownTicks = requirePositive(failedInteractionCooldownTicks, FAILED_COOLDOWN_KEY);
        feedbackSuppressionTicks = requirePositive(feedbackSuppressionTicks, FEEDBACK_SUPPRESSION_KEY);
        frameBlockId = requireText(frameBlockId, FRAME_BLOCK_KEY);
    }

    public static CavernPortalSettings get() {
        return Holder.INSTANCE;
    }

    static CavernPortalSettings load(Path path) {
        Objects.requireNonNull(path, "path");
        if (!Files.exists(path)) {
            return DEFAULTS;
        }

        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            properties.load(reader);
            return fromProperties(properties);
        } catch (IOException | RuntimeException exception) {
            LOGGER.warn("Falling back to default portal settings after config read failure: {}", path, exception);
            return DEFAULTS;
        }
    }

    static CavernPortalSettings fromProperties(Properties properties) {
        Objects.requireNonNull(properties, "properties");

        return new CavernPortalSettings(
            intProperty(properties, FIND_PORTAL_RANGE_KEY, DEFAULTS.findPortalRange()),
            intProperty(properties, FIND_PORTAL_VERTICAL_RANGE_KEY, DEFAULTS.findPortalVerticalRange()),
            longProperty(properties, SUCCESSFUL_COOLDOWN_KEY, DEFAULTS.successfulInteractionCooldownTicks()),
            longProperty(properties, FAILED_COOLDOWN_KEY, DEFAULTS.failedInteractionCooldownTicks()),
            longProperty(properties, FEEDBACK_SUPPRESSION_KEY, DEFAULTS.feedbackSuppressionTicks()),
            normalizedFrameBlockId(properties.getProperty(FRAME_BLOCK_KEY))
        );
    }

    static CavernPortalSettings defaults() {
        return DEFAULTS;
    }

    public Block frameBlock() {
        try {
            ResourceLocation resourceLocation = ResourceLocation.parse(frameBlockId);
            return BuiltInRegistries.BLOCK.getOptional(resourceLocation)
                .filter(block -> block != Blocks.AIR)
                .orElseGet(() -> {
                    LOGGER.warn("Unknown portal frame block '{}', using {}", frameBlockId, DEFAULT_FRAME_BLOCK_ID);
                    return Blocks.MOSSY_COBBLESTONE;
                });
        } catch (RuntimeException exception) {
            LOGGER.warn("Invalid portal frame block '{}', using {}", frameBlockId, DEFAULT_FRAME_BLOCK_ID, exception);
            return Blocks.MOSSY_COBBLESTONE;
        }
    }

    private static String normalizedFrameBlockId(String configuredValue) {
        if (configuredValue == null || configuredValue.isBlank()) {
            return DEFAULTS.frameBlockId();
        }

        try {
            return ResourceLocation.parse(configuredValue.trim()).toString();
        } catch (RuntimeException exception) {
            LOGGER.warn("Invalid portal frame block '{}', using {}", configuredValue, DEFAULT_FRAME_BLOCK_ID, exception);
            return DEFAULTS.frameBlockId();
        }
    }

    private static int intProperty(Properties properties, String key, int fallbackValue) {
        String configuredValue = properties.getProperty(key);
        if (configuredValue == null || configuredValue.isBlank()) {
            return fallbackValue;
        }

        try {
            return Integer.parseInt(configuredValue.trim());
        } catch (NumberFormatException exception) {
            LOGGER.warn("Invalid integer portal setting '{}={}', using {}", key, configuredValue, fallbackValue, exception);
            return fallbackValue;
        }
    }

    private static long longProperty(Properties properties, String key, long fallbackValue) {
        String configuredValue = properties.getProperty(key);
        if (configuredValue == null || configuredValue.isBlank()) {
            return fallbackValue;
        }

        try {
            return Long.parseLong(configuredValue.trim());
        } catch (NumberFormatException exception) {
            LOGGER.warn("Invalid long portal setting '{}={}', using {}", key, configuredValue, fallbackValue, exception);
            return fallbackValue;
        }
    }

    private static int requirePositive(int value, String key) {
        if (value <= 0) {
            throw new IllegalArgumentException(key + " must be positive");
        }

        return value;
    }

    private static long requirePositive(long value, String key) {
        if (value <= 0L) {
            throw new IllegalArgumentException(key + " must be positive");
        }

        return value;
    }

    private static String requireText(String value, String key) {
        Objects.requireNonNull(value, key);
        if (value.isBlank()) {
            throw new IllegalArgumentException(key + " must not be blank");
        }

        return value;
    }

    private static final class Holder {
        private static final CavernPortalSettings INSTANCE = load(CONFIG_PATH);

        private Holder() {
        }
    }
}
