package com.richardkenway.cavernreborn.app.entity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CrazyZombie extends Zombie {
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;
    public static final int LEGACY_KNOCKBACK_TRIGGER_ROLL_BOUND = 5;
    public static final int LEGACY_KNOCKBACK_POWER_VARIANT_COUNT = 3;
    public static final int LEGACY_KNOCKBACK_BASE_POWER = 3;
    public static final float LEGACY_KNOCKBACK_STRENGTH_MULTIPLIER = 0.5F;
    public static final double LEGACY_NON_LIVING_KNOCKBACK_VERTICAL_BOOST = 0.1D;

    public CrazyZombie(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 50;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
            .add(Attributes.MAX_HEALTH, 2000.0D)
            .add(Attributes.FOLLOW_RANGE, 50.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
            .add(Attributes.ATTACK_DAMAGE, 7.5D)
            .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.ZOMBIE.getDefaultLootTable();
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (source.is(DamageTypeTags.IS_FALL)) {
            damage *= LEGACY_FALL_DAMAGE_MULTIPLIER;
        }

        if (source.is(DamageTypeTags.IS_FIRE)) {
            return false;
        }

        return super.hurt(source, damage);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean attackSucceeded = super.doHurtTarget(target);
        int triggerRoll = random.nextInt(LEGACY_KNOCKBACK_TRIGGER_ROLL_BOUND);
        int power = triggerRoll == 0
            ? random.nextInt(LEGACY_KNOCKBACK_POWER_VARIANT_COUNT) + LEGACY_KNOCKBACK_BASE_POWER
            : 0;

        tryApplyLegacyKnockback(target, power);
        return attackSucceeded;
    }

    public static int getLegacyKnockbackPower(int triggerRoll, int magnitudeRoll) {
        if (triggerRoll != 0 || magnitudeRoll < 0 || magnitudeRoll >= LEGACY_KNOCKBACK_POWER_VARIANT_COUNT) {
            return 0;
        }

        return magnitudeRoll + LEGACY_KNOCKBACK_BASE_POWER;
    }

    public boolean tryApplyLegacyKnockback(Entity target, int power) {
        if (power <= 0) {
            return false;
        }

        float yawRadians = getYRot() * (float) (Math.PI / 180.0);
        double knockbackStrength = power * LEGACY_KNOCKBACK_STRENGTH_MULTIPLIER;

        if (target instanceof LivingEntity livingTarget) {
            livingTarget.knockback(knockbackStrength, Mth.sin(yawRadians), -Mth.cos(yawRadians));
            return true;
        }

        target.push(
            -Mth.sin(yawRadians) * knockbackStrength,
            LEGACY_NON_LIVING_KNOCKBACK_VERTICAL_BOOST,
            Mth.cos(yawRadians) * knockbackStrength
        );
        return true;
    }
}
