package com.richardkenway.cavernreborn.app.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.entity.CavenicSkeleton;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;

public final class CavenicSkeletonRenderer extends SkeletonRenderer<CavenicSkeleton> {
    private static final ResourceLocation CAVENIC_SKELETON_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        CavernReborn.MOD_ID,
        "textures/entity/cavenic_skeleton.png"
    );

    public CavenicSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CavenicSkeleton entity) {
        return CAVENIC_SKELETON_TEXTURE;
    }

    @Override
    protected void scale(CavenicSkeleton entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.1F, 1.1F, 1.1F);
    }
}
