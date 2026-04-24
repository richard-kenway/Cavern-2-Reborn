package com.richardkenway.cavernreborn.app.item;

import java.util.List;

import com.richardkenway.cavernreborn.core.combat.CavenicAxeSmashPolicy;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public final class CavenicAxeItem extends AxeItem {
    public CavenicAxeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(target.level() instanceof ServerLevel level)) {
            return;
        }

        int affectedTargets = smashNearbyHostiles(level, target, attacker);
        stack.hurtAndBreak(CavenicAxeSmashPolicy.durabilityCost(affectedTargets), attacker, EquipmentSlot.MAINHAND);
    }

    private static int smashNearbyHostiles(ServerLevel level, LivingEntity target, LivingEntity attacker) {
        List<LivingEntity> candidates = level.getEntitiesOfClass(
            LivingEntity.class,
            target.getBoundingBox().inflate(CavenicAxeSmashPolicy.SMASH_RADIUS),
            candidate -> isEligibleSmashTarget(candidate, target, attacker)
        );
        int affectedTargets = 0;

        for (LivingEntity candidate : candidates) {
            if (affectedTargets >= CavenicAxeSmashPolicy.MAX_EXTRA_TARGETS) {
                break;
            }
            if (!candidate.hurt(meleeDamageSource(level, attacker), CavenicAxeSmashPolicy.ADDITIONAL_DAMAGE)) {
                continue;
            }

            applyKnockback(candidate, attacker);
            affectedTargets++;
        }

        return affectedTargets;
    }

    private static boolean isEligibleSmashTarget(LivingEntity candidate, LivingEntity target, LivingEntity attacker) {
        boolean primaryTarget = candidate.is(target);
        boolean attackingEntity = candidate.is(attacker);
        boolean hostile = candidate instanceof Enemy && candidate.getType().getCategory() == MobCategory.MONSTER;
        boolean ownedOrFriendly = isOwnedOrFriendly(candidate, attacker);

        return CavenicAxeSmashPolicy.isEligibleExtraTarget(
            true,
            hostile,
            primaryTarget,
            attackingEntity,
            ownedOrFriendly,
            candidate.distanceToSqr(target)
        );
    }

    private static boolean isOwnedOrFriendly(LivingEntity candidate, LivingEntity attacker) {
        if (candidate instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null) {
            return true;
        }
        if (candidate instanceof TamableAnimal tamable && tamable.isTame()) {
            return true;
        }

        return candidate.isAlliedTo(attacker);
    }

    private static DamageSource meleeDamageSource(ServerLevel level, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            return level.damageSources().playerAttack(player);
        }

        return level.damageSources().mobAttack(attacker);
    }

    private static void applyKnockback(LivingEntity target, LivingEntity attacker) {
        target.knockback(0.25D, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
    }
}
