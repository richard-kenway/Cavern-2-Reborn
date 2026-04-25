package com.richardkenway.cavernreborn.app.entity;

import java.util.Collection;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.core.loot.CavenicCreeperLootPolicy;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public final class CavenicCreeperLootEvents {
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof CavenicCreeper creeper) || creeper.level().isClientSide()) {
            return;
        }

        tryAppendLegacyOrbDrop(
            creeper,
            event.getDrops(),
            creeper.getRandom().nextInt(CavenicCreeperLootPolicy.ORB_DROP_ROLL_BOUND)
        );
    }

    public boolean tryAppendLegacyOrbDrop(CavenicCreeper creeper, Collection<ItemEntity> drops, int orbRoll) {
        if (!CavenicCreeperLootPolicy.shouldDropOrb(orbRoll)) {
            return false;
        }

        ItemEntity orbDrop = new ItemEntity(
            creeper.level(),
            creeper.getX(),
            creeper.getY() + 0.5D,
            creeper.getZ(),
            new ItemStack(ModRegistries.CAVENIC_ORB.get())
        );
        orbDrop.setDefaultPickUpDelay();
        drops.add(orbDrop);
        return true;
    }
}
