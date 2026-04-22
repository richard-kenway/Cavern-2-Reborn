package com.richardkenway.cavernreborn.app.registry;

import com.richardkenway.cavernreborn.CavernReborn;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModBlockTags {
    public static final TagKey<Block> MINING_ASSIST_TARGETS = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "mining_assist_targets")
    );

    private ModBlockTags() {
    }
}
