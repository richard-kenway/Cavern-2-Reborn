package com.richardkenway.cavernreborn.app.client;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

public final class CavernDimensionSpecialEffects extends DimensionSpecialEffects {
    private static final Vec3 CAVERN_FOG_COLOR = new Vec3(0.02D, 0.022D, 0.024D);

    public CavernDimensionSpecialEffects() {
        super(Float.NaN, true, SkyType.NONE, false, false);
    }

    @Nullable
    @Override
    public float[] getSunriseColor(float timeOfDay, float partialTick) {
        return null;
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
        return CAVERN_FOG_COLOR;
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }
}
