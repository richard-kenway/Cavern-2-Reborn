package com.richardkenway.cavernreborn.app.entity;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CavenicSpider extends Spider {
    public static final int NATURAL_SPAWN_WEIGHT = 30;
    public static final int NATURAL_SPAWN_MIN_COUNT = 1;
    public static final int NATURAL_SPAWN_MAX_COUNT = 1;
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;

    public CavenicSpider(EntityType<? extends Spider> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 12;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Spider.createAttributes()
            .add(Attributes.MAX_HEALTH, 20.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.6D);
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
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.SPIDER.getDefaultLootTable();
    }

    public static boolean canNaturallySpawnInDimension(ResourceKey<Level> levelKey) {
        return CavernNeoForgeDimensions.isCavern(levelKey);
    }

    public static boolean checkCavenicSpiderSpawnRules(
        EntityType<CavenicSpider> entityType,
        ServerLevelAccessor level,
        MobSpawnType spawnType,
        BlockPos pos,
        RandomSource random
    ) {
        return canNaturallySpawnInDimension(level.getLevel().dimension())
            && Monster.checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
    }
}
