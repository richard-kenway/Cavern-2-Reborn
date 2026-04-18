package com.richardkenway.cavernreborn.app.portal;

import com.richardkenway.cavernreborn.CavernReborn;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class CavernPortalTags {
    public static final TagKey<Block> CAVERN_PORTAL_FRAMES = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavern_portal_frames")
    );
    public static final TagKey<Item> CAVERN_PORTAL_ACTIVATORS = TagKey.create(
        Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavern_portal_activators")
    );

    private CavernPortalTags() {
    }

    public static boolean isAllowedFrame(BlockState state) {
        return state != null
            && (
                state.is(CAVERN_PORTAL_FRAMES)
                || state.is(Blocks.MOSSY_COBBLESTONE)
                || state.is(Blocks.MOSSY_STONE_BRICKS)
            );
    }

    public static boolean isAllowedActivator(ItemStack stack) {
        return stack != null
            && !stack.isEmpty()
            && (
                stack.is(CAVERN_PORTAL_ACTIVATORS)
                || stack.is(Items.EMERALD)
            );
    }
}
