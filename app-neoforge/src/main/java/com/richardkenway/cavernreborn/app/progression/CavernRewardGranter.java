package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionReward;
import com.richardkenway.cavernreborn.core.progression.CavernServiceEntry;
import com.richardkenway.cavernreborn.core.progression.CavernRewardGrant;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class CavernRewardGranter {
    public void grant(ServerPlayer player, CavernProgressionReward reward) {
        ServerPlayer normalizedPlayer = Objects.requireNonNull(player, "player");
        for (CavernRewardGrant grant : Objects.requireNonNull(reward, "reward").grants()) {
            Item item = resolveItem(grant);
            ItemStack stack = new ItemStack(item, grant.count());
            normalizedPlayer.getInventory().add(stack);
            if (!stack.isEmpty()) {
                normalizedPlayer.drop(stack, false);
            }
        }
    }

    public void grantService(ServerPlayer player, CavernServiceEntry service) {
        ServerPlayer normalizedPlayer = Objects.requireNonNull(player, "player");
        for (CavernRewardGrant grant : Objects.requireNonNull(service, "service").grants()) {
            Item item = resolveItem(grant);
            ItemStack stack = new ItemStack(item, grant.count());
            normalizedPlayer.getInventory().add(stack);
            if (!stack.isEmpty()) {
                normalizedPlayer.drop(stack, false);
            }
        }
    }

    private static Item resolveItem(CavernRewardGrant grant) {
        ResourceLocation itemId = ResourceLocation.parse(grant.itemId());
        if (!BuiltInRegistries.ITEM.containsKey(itemId)) {
            throw new IllegalStateException("Unknown reward item id: " + grant.itemId());
        }
        return BuiltInRegistries.ITEM.get(itemId);
    }
}
