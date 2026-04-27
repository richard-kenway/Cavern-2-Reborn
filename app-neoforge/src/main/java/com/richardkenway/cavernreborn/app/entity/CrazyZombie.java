package com.richardkenway.cavernreborn.app.entity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CrazyZombie extends Zombie {
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;

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
}
