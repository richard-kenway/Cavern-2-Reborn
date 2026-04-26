package com.richardkenway.cavernreborn.app.entity;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CavenicBear extends PolarBear {
    public static final int NATURAL_SPAWN_WEIGHT = 30;
    public static final int NATURAL_SPAWN_MIN_COUNT = 1;
    public static final int NATURAL_SPAWN_MAX_COUNT = 1;

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

    @Override
    public int getMaxSpawnClusterSize() {
        return NATURAL_SPAWN_MAX_COUNT;
    }

    public static boolean canNaturallySpawnInDimension(ResourceKey<Level> levelKey) {
        return CavernNeoForgeDimensions.isCavern(levelKey);
    }

    public static boolean checkCavenicBearSpawnRules(
        EntityType<CavenicBear> entityType,
        ServerLevelAccessor level,
        MobSpawnType spawnType,
        BlockPos pos,
        RandomSource random
    ) {
        return canNaturallySpawnInDimension(level.getLevel().dimension())
            && Monster.checkMonsterSpawnRules(asMonsterSpawnType(entityType), level, spawnType, pos, random);
    }

    @SuppressWarnings("unchecked")
    private static EntityType<? extends Monster> asMonsterSpawnType(EntityType<CavenicBear> entityType) {
        return (EntityType<? extends Monster>)(EntityType<?>)entityType;
    }
}
