package com.richardkenway.cavernreborn.app.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public final class CavenicBowRapidEvents {
    public static final int LEGACY_RAPID_LOW_ARMOR_THRESHOLD = 20;

    @SubscribeEvent
    public void onLivingDamagePost(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) {
            return;
        }

        tryResetRapidArrowLowArmorInvulnerability(event.getEntity(), arrow);
    }

    public boolean tryResetRapidArrowLowArmorInvulnerability(LivingEntity target, AbstractArrow arrow) {
        if (target.level().isClientSide()) {
            return false;
        }
        if (!CavenicBowItem.isRapidArrow(arrow)) {
            return false;
        }
        if (target.getArmorValue() >= LEGACY_RAPID_LOW_ARMOR_THRESHOLD) {
            return false;
        }

        target.invulnerableTime = 0;
        return true;
    }
}
