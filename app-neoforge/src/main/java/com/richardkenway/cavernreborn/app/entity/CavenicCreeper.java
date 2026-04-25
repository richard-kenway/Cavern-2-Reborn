package com.richardkenway.cavernreborn.app.entity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CavenicCreeper extends Creeper {
    public CavenicCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 13;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Creeper.createAttributes()
            .add(Attributes.MAX_HEALTH, 30.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.85D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.CREEPER.getDefaultLootTable();
    }
}
