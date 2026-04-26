package com.richardkenway.cavernreborn.app.client.renderer;

import com.richardkenway.cavernreborn.CavernReborn;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;

public final class CavenicBearRenderer extends PolarBearRenderer {
    private static final ResourceLocation CAVENIC_BEAR_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        CavernReborn.MOD_ID,
        "textures/entity/cavenic_bear.png"
    );

    public CavenicBearRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(PolarBear entity) {
        return CAVENIC_BEAR_TEXTURE;
    }
}
