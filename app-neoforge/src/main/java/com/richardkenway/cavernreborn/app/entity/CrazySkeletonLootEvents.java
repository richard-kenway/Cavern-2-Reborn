package com.richardkenway.cavernreborn.app.entity;

import java.util.Collection;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.core.loot.CrazySkeletonLootPolicy;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public final class CrazySkeletonLootEvents {
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof CrazySkeleton skeleton) || skeleton.level().isClientSide()) {
            return;
        }

        tryAppendLegacyOrbDrop(
            skeleton,
            event.getDrops(),
            skeleton.getRandom().nextInt(CrazySkeletonLootPolicy.ORB_DROP_ROLL_BOUND)
        );
    }

    public boolean tryAppendLegacyOrbDrop(CrazySkeleton skeleton, Collection<ItemEntity> drops, int orbRoll) {
        if (!CrazySkeletonLootPolicy.shouldDropOrb(orbRoll)) {
            return false;
        }

        ItemEntity orbDrop = new ItemEntity(
            skeleton.level(),
            skeleton.getX(),
            skeleton.getY() + 0.5D,
            skeleton.getZ(),
            new ItemStack(ModRegistries.CAVENIC_ORB.get())
        );
        orbDrop.setDefaultPickUpDelay();
        drops.add(orbDrop);
        return true;
    }
}
