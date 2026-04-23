package com.richardkenway.cavernreborn.app.registry;

import com.richardkenway.cavernreborn.CavernReborn;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModBlockTags {
    public static final TagKey<Block> ACRESIA_PLANTABLE_ON = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "acresia_plantable_on")
    );
    public static final TagKey<Block> CAVENIC_SHROOM_PLACEABLE_ON = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavenic_shroom_placeable_on")
    );
    public static final TagKey<Block> MINING_ASSIST_TARGETS = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "mining_assist_targets")
    );
    public static final TagKey<Block> ORE_COMPASS_TARGETS = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "ore_compass_targets")
    );

    private ModBlockTags() {
    }
}
