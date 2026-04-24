package com.richardkenway.cavernreborn.app.item;

import com.richardkenway.cavernreborn.core.combat.CavenicSwordHitPolicy;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public final class CavenicSwordItem extends SwordItem {
    public CavenicSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.level().isClientSide()) {
            return;
        }
        if (CavenicSwordHitPolicy.shouldResetCooldown(target.invulnerableTime, target.hurtTime)) {
            target.invulnerableTime = 0;
            target.hurtTime = 0;
            target.hurtDuration = 0;
        }

        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }
}
