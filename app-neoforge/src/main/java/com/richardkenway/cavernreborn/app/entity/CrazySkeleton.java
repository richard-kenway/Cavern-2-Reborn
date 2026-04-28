package com.richardkenway.cavernreborn.app.entity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CrazySkeleton extends Skeleton {
    public CrazySkeleton(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 50;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Skeleton.createAttributes()
            .add(Attributes.MAX_HEALTH, 2000.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 22.0D);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.SKELETON.getDefaultLootTable();
    }
}
