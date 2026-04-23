package com.richardkenway.cavernreborn.app.mining;

import com.richardkenway.cavernreborn.app.registry.ModItemTags;
import com.richardkenway.cavernreborn.core.mining.AquamarineAquaToolPolicy;
import com.richardkenway.cavernreborn.core.mining.AquamarineAquaToolResult;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class CavernAquamarineToolEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        AquamarineAquaToolResult result = AquamarineAquaToolPolicy.evaluate(
            player.getMainHandItem().is(ModItemTags.AQUA_TOOLS),
            player.isEyeInFluid(FluidTags.WATER),
            hasAquaAffinity(player),
            event.getNewSpeed()
        );
        if (result.boosted()) {
            event.setNewSpeed(result.adjustedSpeed());
        }
    }

    float adjustedBreakSpeed(boolean hasAquamarineTool, boolean submerged, boolean hasAquaAffinity, float currentSpeed) {
        return AquamarineAquaToolPolicy.evaluate(
            hasAquamarineTool,
            submerged,
            hasAquaAffinity,
            currentSpeed
        ).adjustedSpeed();
    }

    private static boolean hasAquaAffinity(Player player) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        return EnchantmentHelper.getItemEnchantmentLevel(
            enchantments.getOrThrow(Enchantments.AQUA_AFFINITY),
            player.getItemBySlot(EquipmentSlot.HEAD)
        ) > 0;
    }
}
