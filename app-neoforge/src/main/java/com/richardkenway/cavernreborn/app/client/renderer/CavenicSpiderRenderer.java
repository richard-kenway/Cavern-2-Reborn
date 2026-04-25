package com.richardkenway.cavernreborn.app.client.renderer;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.entity.CavenicSpider;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;

public final class CavenicSpiderRenderer extends SpiderRenderer<CavenicSpider> {
    private static final ResourceLocation CAVENIC_SPIDER_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        CavernReborn.MOD_ID,
        "textures/entity/cavenic_spider.png"
    );

    public CavenicSpiderRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CavenicSpider entity) {
        return CAVENIC_SPIDER_TEXTURE;
    }
}
