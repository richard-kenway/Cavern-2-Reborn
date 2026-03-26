package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

class CavernNeoForgeDimensionsTest {
    @Test
    void cavernLevelKeyMatchesRegisteredDimensionId() {
        ResourceKey<Level> key = CavernNeoForgeDimensions.levelKey(CavernDimensions.CAVERN_DIMENSION_ID);

        assertEquals(ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavern")), key);
        assertTrue(CavernNeoForgeDimensions.isCavern(key));
    }

    @Test
    void resourceLocationRejectsInvalidDimensionId() {
        assertThrows(IllegalArgumentException.class, () -> CavernNeoForgeDimensions.resourceLocation("cavern"));
    }
}
