package com.richardkenway.cavernreborn.app.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

class CavernDimensionSpecialEffectsTest {
    @Test
    void usesSkylessCaveLikeVisualProfile() {
        CavernDimensionSpecialEffects effects = new CavernDimensionSpecialEffects();

        assertEquals(DimensionSpecialEffects.SkyType.NONE, effects.skyType());
        assertFalse(effects.forceBrightLightmap());
        assertFalse(effects.constantAmbientLight());
        assertNull(effects.getSunriseColor(0.0F, 0.0F));
        assertFalse(effects.isFoggyAt(0, 0));
    }

    @Test
    void usesFixedDarkCaveFogColor() {
        CavernDimensionSpecialEffects effects = new CavernDimensionSpecialEffects();
        Vec3 adjusted = effects.getBrightnessDependentFogColor(new Vec3(1.0D, 1.0D, 1.0D), 1.0F);

        assertEquals(0.02D, adjusted.x, 1.0E-9D);
        assertEquals(0.022D, adjusted.y, 1.0E-9D);
        assertEquals(0.024D, adjusted.z, 1.0E-9D);
    }
}
