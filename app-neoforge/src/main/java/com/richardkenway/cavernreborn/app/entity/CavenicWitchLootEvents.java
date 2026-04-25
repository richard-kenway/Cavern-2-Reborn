package com.richardkenway.cavernreborn.app.entity;

import java.util.Collection;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.core.loot.CavenicWitchLootPolicy;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public final class CavenicWitchLootEvents {
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof CavenicWitch witch) || witch.level().isClientSide()) {
            return;
        }

        tryAppendLegacyOrbDrop(
            witch,
            event.getDrops(),
            witch.getRandom().nextInt(CavenicWitchLootPolicy.ORB_DROP_ROLL_BOUND)
        );
    }

    public boolean tryAppendLegacyOrbDrop(CavenicWitch witch, Collection<ItemEntity> drops, int orbRoll) {
        if (!CavenicWitchLootPolicy.shouldDropOrb(orbRoll)) {
            return false;
        }

        ItemEntity orbDrop = new ItemEntity(
            witch.level(),
            witch.getX(),
            witch.getY() + 0.5D,
            witch.getZ(),
            new ItemStack(ModRegistries.CAVENIC_ORB.get())
        );
        orbDrop.setDefaultPickUpDelay();
        drops.add(orbDrop);
        return true;
    }
}
