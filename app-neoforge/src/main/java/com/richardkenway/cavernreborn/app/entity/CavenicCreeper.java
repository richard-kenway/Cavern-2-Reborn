package com.richardkenway.cavernreborn.app.entity;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CavenicCreeper extends Creeper {
    public static final int NATURAL_SPAWN_WEIGHT = 30;
    public static final int NATURAL_SPAWN_MIN_COUNT = 1;
    public static final int NATURAL_SPAWN_MAX_COUNT = 1;
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;
    public static final short LEGACY_FUSE_TIME = 15;
    public static final byte LEGACY_EXPLOSION_RADIUS = 5;

    public CavenicCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 13;
        this.applyLegacyFuseAndExplosionValues();
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

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("Fuse", LEGACY_FUSE_TIME);
        tag.putByte("ExplosionRadius", LEGACY_EXPLOSION_RADIUS);
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

    public static boolean canNaturallySpawnInDimension(ResourceKey<Level> levelKey) {
        return CavernNeoForgeDimensions.isCavern(levelKey);
    }

    private void applyLegacyFuseAndExplosionValues() {
        CompoundTag legacyFuseAndExplosionData = new CompoundTag();
        legacyFuseAndExplosionData.putShort("Fuse", LEGACY_FUSE_TIME);
        legacyFuseAndExplosionData.putByte("ExplosionRadius", LEGACY_EXPLOSION_RADIUS);
        super.readAdditionalSaveData(legacyFuseAndExplosionData);
    }

    public static boolean checkCavenicCreeperSpawnRules(
        EntityType<CavenicCreeper> entityType,
        ServerLevelAccessor level,
        MobSpawnType spawnType,
        BlockPos pos,
        RandomSource random
    ) {
        return canNaturallySpawnInDimension(level.getLevel().dimension())
            && Monster.checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
    }
}
