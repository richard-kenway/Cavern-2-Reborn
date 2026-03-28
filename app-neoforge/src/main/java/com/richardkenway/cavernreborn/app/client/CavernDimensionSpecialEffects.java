package com.richardkenway.cavernreborn.app.client;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

public final class CavernDimensionSpecialEffects extends DimensionSpecialEffects {
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
        double redGreenScale = brightness * 0.18F + 0.04F;
        double blueScale = brightness * 0.2F + 0.05F;
        return fogColor.multiply(redGreenScale, redGreenScale, blueScale);
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }
}
