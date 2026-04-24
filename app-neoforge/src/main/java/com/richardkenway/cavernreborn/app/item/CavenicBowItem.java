package com.richardkenway.cavernreborn.app.item;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.app.registry.ModToolTiers;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public final class CavenicBowItem extends BowItem {
    public CavenicBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(ModRegistries.CAVENIC_ORB.get()) || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public int getEnchantmentValue() {
        return ModToolTiers.CAVENIC.getEnchantmentValue();
    }
}
