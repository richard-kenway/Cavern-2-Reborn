package com.richardkenway.cavernreborn.app.entity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CavenicBear extends PolarBear {
    public CavenicBear(EntityType<? extends PolarBear> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 13;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PolarBear.createAttributes()
            .add(Attributes.MAX_HEALTH, 60.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.3D)
            .add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.POLAR_BEAR.getDefaultLootTable();
    }
}
