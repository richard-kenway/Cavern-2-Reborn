package com.richardkenway.cavernreborn.app.dimension;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class CavernNeoForgeDimensions {
    public static final ResourceLocation CAVERN_LOCATION = ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavern");
    public static final ResourceKey<Level> CAVERN_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION, CAVERN_LOCATION);

    private CavernNeoForgeDimensions() {
    }

    public static ResourceLocation resourceLocation(String dimensionId) {
        String normalizedDimensionId = requireText(dimensionId, "dimensionId");
        int separatorIndex = normalizedDimensionId.indexOf(':');

        if (separatorIndex <= 0 || separatorIndex == normalizedDimensionId.length() - 1 || normalizedDimensionId.indexOf(':', separatorIndex + 1) >= 0) {
            throw new IllegalArgumentException("dimensionId must be in namespace:path form");
        }

        String namespace = normalizedDimensionId.substring(0, separatorIndex);
        String path = normalizedDimensionId.substring(separatorIndex + 1);
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    public static ResourceKey<Level> levelKey(String dimensionId) {
        return ResourceKey.create(Registries.DIMENSION, resourceLocation(dimensionId));
    }

    public static boolean isCavern(ResourceKey<Level> levelKey) {
        return CAVERN_LEVEL_KEY.equals(levelKey);
    }

    public static boolean isCavernDimensionId(String dimensionId) {
        return CavernDimensions.isCavern(dimensionId);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null) {
            throw new NullPointerException(fieldName);
        }

        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }
}
