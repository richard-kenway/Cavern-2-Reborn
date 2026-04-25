package com.richardkenway.cavernreborn.app.entity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CavenicWitch extends Witch {
    public CavenicWitch(EntityType<? extends Witch> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 12;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Witch.createAttributes()
            .add(Attributes.MAX_HEALTH, 50.0D)
            .add(Attributes.FOLLOW_RANGE, 32.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.WITCH.getDefaultLootTable();
    }
}
