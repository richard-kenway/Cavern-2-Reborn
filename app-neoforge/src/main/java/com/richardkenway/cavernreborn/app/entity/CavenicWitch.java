package com.richardkenway.cavernreborn.app.entity;

import javax.annotation.Nullable;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CavenicWitch extends Witch {
    public static final int NATURAL_SPAWN_WEIGHT = 15;
    public static final int NATURAL_SPAWN_MIN_COUNT = 1;
    public static final int NATURAL_SPAWN_MAX_COUNT = 1;
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;

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
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || isLegacyCavenicWitchSourceImmuneTo(source);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (isLegacyFriendTarget(target)) {
            return;
        }

        super.setTarget(target);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.WITCH.getDefaultLootTable();
    }

    public boolean isLegacyFriendTarget(@Nullable LivingEntity target) {
        return target instanceof CavenicWitch;
    }

    public boolean isLegacyCavenicWitchSourceImmuneTo(DamageSource source) {
        return isLegacyCavenicWitchSource(source.getEntity()) || isLegacyCavenicWitchSource(source.getDirectEntity());
    }

    public static boolean canNaturallySpawnInDimension(ResourceKey<Level> levelKey) {
        return CavernNeoForgeDimensions.isCavern(levelKey);
    }

    public static boolean checkCavenicWitchSpawnRules(
        EntityType<CavenicWitch> entityType,
        ServerLevelAccessor level,
        MobSpawnType spawnType,
        BlockPos pos,
        RandomSource random
    ) {
        return canNaturallySpawnInDimension(level.getLevel().dimension())
            && Monster.checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
    }

    private boolean isLegacyCavenicWitchSource(Entity entity) {
        return entity == this || entity instanceof CavenicWitch;
    }
}
