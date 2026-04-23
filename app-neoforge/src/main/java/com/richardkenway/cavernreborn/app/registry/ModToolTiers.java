package com.richardkenway.cavernreborn.app.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;

import com.richardkenway.cavernreborn.CavernReborn;

public final class ModToolTiers {
    public static final TagKey<Block> INCORRECT_FOR_AQUAMARINE_TOOL = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.parse(CavernReborn.MOD_ID + ":incorrect_for_aquamarine_tool")
    );
    public static final TagKey<Block> INCORRECT_FOR_MAGNITE_TOOL = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.parse(CavernReborn.MOD_ID + ":incorrect_for_magnite_tool")
    );
    public static final TagKey<Block> INCORRECT_FOR_HEXCITE_TOOL = TagKey.create(
        Registries.BLOCK,
        ResourceLocation.parse(CavernReborn.MOD_ID + ":incorrect_for_hexcite_tool")
    );

    public static final Tier AQUAMARINE = new SimpleTier(
        INCORRECT_FOR_AQUAMARINE_TOOL,
        200,
        8.0F,
        1.5F,
        15,
        () -> Ingredient.of(ModRegistries.AQUAMARINE.get())
    );

    public static final Tier MAGNITE = new SimpleTier(
        INCORRECT_FOR_MAGNITE_TOOL,
        48,
        18.0F,
        2.5F,
        24,
        () -> Ingredient.of(ModRegistries.MAGNITE_INGOT.get())
    );

    public static final Tier HEXCITE = new SimpleTier(
        INCORRECT_FOR_HEXCITE_TOOL,
        1024,
        8.0F,
        3.0F,
        18,
        () -> Ingredient.of(ModRegistries.HEXCITE.get())
    );

    private ModToolTiers() {
    }
}
