package com.richardkenway.cavernreborn.app.client.renderer;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.entity.CrazySpider;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;

public final class CrazySpiderRenderer extends SpiderRenderer<CrazySpider> {
    private static final ResourceLocation CRAZY_SPIDER_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        CavernReborn.MOD_ID,
        "textures/entity/crazy_spider.png"
    );

    public CrazySpiderRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CrazySpider entity) {
        return CRAZY_SPIDER_TEXTURE;
    }
}
