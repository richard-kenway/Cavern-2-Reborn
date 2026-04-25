package com.richardkenway.cavernreborn.app.client.renderer;

import com.richardkenway.cavernreborn.CavernReborn;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitchRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Witch;

public final class CavenicWitchRenderer extends WitchRenderer {
    private static final ResourceLocation CAVENIC_WITCH_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        CavernReborn.MOD_ID,
        "textures/entity/cavenic_witch.png"
    );

    public CavenicWitchRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Witch entity) {
        return CAVENIC_WITCH_TEXTURE;
    }
}
