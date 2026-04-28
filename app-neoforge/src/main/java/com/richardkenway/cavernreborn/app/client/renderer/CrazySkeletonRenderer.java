package com.richardkenway.cavernreborn.app.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.entity.CrazySkeleton;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;

public final class CrazySkeletonRenderer extends SkeletonRenderer<CrazySkeleton> {
    private static final ResourceLocation CRAZY_SKELETON_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        CavernReborn.MOD_ID,
        "textures/entity/crazy_skeleton.png"
    );

    public CrazySkeletonRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CrazySkeleton entity) {
        return CRAZY_SKELETON_TEXTURE;
    }

    @Override
    protected void scale(CrazySkeleton entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.1F, 1.1F, 1.1F);
    }
}
