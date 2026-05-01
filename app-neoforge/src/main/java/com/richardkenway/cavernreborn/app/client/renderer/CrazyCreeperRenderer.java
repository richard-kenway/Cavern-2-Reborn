package com.richardkenway.cavernreborn.app.client.renderer;

import com.richardkenway.cavernreborn.CavernReborn;

import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

public final class CrazyCreeperRenderer extends CreeperRenderer {
    private static final ResourceLocation CRAZY_CREEPER_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        CavernReborn.MOD_ID,
        "textures/entity/crazy_creeper.png"
    );

    public CrazyCreeperRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Creeper entity) {
        return CRAZY_CREEPER_TEXTURE;
    }
}
