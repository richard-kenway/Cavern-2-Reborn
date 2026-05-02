package com.richardkenway.cavernreborn.app.entity;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CrazyCreeper extends Creeper {
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;
    public static final double LEGACY_BOSS_BAR_VISIBILITY_DISTANCE = 20.0D;
    public static final double LEGACY_BOSS_BAR_DARKEN_SKY_DISTANCE = 30.0D;

    private final ServerBossEvent legacyBossEvent = new ServerBossEvent(
        this.getDisplayName(),
        BossEvent.BossBarColor.GREEN,
        BossEvent.BossBarOverlay.PROGRESS
    );

    public CrazyCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 50;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Creeper.createAttributes()
            .add(Attributes.MAX_HEALTH, 1500.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.23D);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.CREEPER.getDefaultLootTable();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (this.hasCustomName()) {
            this.legacyBossEvent.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.legacyBossEvent.setName(this.getDisplayName());
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
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.updateLegacyBossEvent();
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.legacyBossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.legacyBossEvent.removePlayer(player);
    }

    public ServerBossEvent getLegacyCrazyBossEventForTests() {
        return this.legacyBossEvent;
    }

    public boolean shouldShowLegacyBossBarTo(ServerPlayer player) {
        return this.hasLineOfSight(player) && this.distanceTo(player) < LEGACY_BOSS_BAR_VISIBILITY_DISTANCE;
    }

    public boolean shouldDarkenLegacyBossSkyFor(ServerPlayer player) {
        double distance = this.distanceTo(player);
        boolean canSee = this.hasLineOfSight(player) && distance < LEGACY_BOSS_BAR_VISIBILITY_DISTANCE;
        return !canSee || distance < LEGACY_BOSS_BAR_DARKEN_SKY_DISTANCE;
    }

    public void updateLegacyBossEvent() {
        boolean canSee = false;
        double distance = -1.0D;

        for (ServerPlayer player : this.legacyBossEvent.getPlayers()) {
            distance = this.distanceTo(player);

            if (this.shouldShowLegacyBossBarTo(player)) {
                canSee = true;
                break;
            }
        }

        this.legacyBossEvent.setDarkenScreen(!canSee || distance < LEGACY_BOSS_BAR_DARKEN_SKY_DISTANCE);
        this.legacyBossEvent.setVisible(canSee);
        this.legacyBossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }
}
