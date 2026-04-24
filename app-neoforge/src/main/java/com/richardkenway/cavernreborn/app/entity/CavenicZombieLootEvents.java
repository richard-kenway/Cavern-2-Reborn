package com.richardkenway.cavernreborn.app.entity;

import java.util.Collection;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.core.loot.CavenicZombieLootPolicy;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public final class CavenicZombieLootEvents {
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof CavenicZombie zombie) || zombie.level().isClientSide()) {
            return;
        }

        tryAppendLegacyOrbDrop(
            zombie,
            event.getDrops(),
            zombie.getRandom().nextInt(CavenicZombieLootPolicy.ORB_DROP_ROLL_BOUND)
        );
    }

    public boolean tryAppendLegacyOrbDrop(CavenicZombie zombie, Collection<ItemEntity> drops, int orbRoll) {
        if (!CavenicZombieLootPolicy.shouldDropOrb(orbRoll)) {
            return false;
        }

        ItemEntity orbDrop = new ItemEntity(
            zombie.level(),
            zombie.getX(),
            zombie.getY() + 0.5D,
            zombie.getZ(),
            new ItemStack(ModRegistries.CAVENIC_ORB.get())
        );
        orbDrop.setDefaultPickUpDelay();
        drops.add(orbDrop);
        return true;
    }
}
