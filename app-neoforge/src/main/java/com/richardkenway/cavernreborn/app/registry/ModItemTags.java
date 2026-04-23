package com.richardkenway.cavernreborn.app.registry;

import com.richardkenway.cavernreborn.CavernReborn;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {
    public static final TagKey<Item> ACRESIA_ITEMS = TagKey.create(
        Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "acresia_items")
    );
    public static final TagKey<Item> CAVENIC_ITEMS = TagKey.create(
        Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavenic_items")
    );
    public static final TagKey<Item> AQUA_TOOLS = TagKey.create(
        Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "aqua_tools")
    );
    public static final TagKey<Item> MAGNITE_TOOLS = TagKey.create(
        Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "magnite_tools")
    );
    public static final TagKey<Item> MINING_BONUS_ORBS = TagKey.create(
        Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "mining_bonus_orbs")
    );

    private ModItemTags() {
    }
}
