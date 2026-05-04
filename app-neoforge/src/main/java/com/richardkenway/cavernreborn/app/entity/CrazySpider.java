package com.richardkenway.cavernreborn.app.entity;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CrazySpider extends Spider {
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;
    public static final int LEGACY_BLINDNESS_DURATION_DEFAULT_TICKS = 100;
    public static final int LEGACY_BLINDNESS_DURATION_NORMAL_TICKS = 200;
    public static final int LEGACY_BLINDNESS_DURATION_HARD_TICKS = 400;
    public static final int LEGACY_BLINDNESS_AMPLIFIER = 0;
    public static final int LEGACY_POISON_DURATION_DEFAULT_TICKS = 60;
    public static final int LEGACY_POISON_DURATION_NORMAL_TICKS = 100;
    public static final int LEGACY_POISON_DURATION_HARD_TICKS = 160;
    public static final int LEGACY_POISON_AMPLIFIER = 0;
    public static final int LEGACY_PARTICLE_COUNT_PER_TICK = 3;
    public static final double LEGACY_PARTICLE_HORIZONTAL_OFFSET = 0.25D;
    public static final double LEGACY_PARTICLE_BASE_Y_OFFSET = 0.65D;
    public static final double LEGACY_PARTICLE_VERTICAL_MOTION_OFFSET = 0.25D;
    public static final double LEGACY_PARTICLE_VERTICAL_MOTION_SCALE = 0.125D;
    public static final double LEGACY_BOSS_BAR_VISIBILITY_DISTANCE = 20.0D;
    public static final double LEGACY_BOSS_BAR_DARKEN_SKY_DISTANCE = 30.0D;

    private final ServerBossEvent legacyBossEvent = new ServerBossEvent(
        this.getDisplayName(),
        BossEvent.BossBarColor.RED,
        BossEvent.BossBarOverlay.PROGRESS
    );

    public CrazySpider(EntityType<? extends Spider> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 50;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Spider.createAttributes()
            .add(Attributes.MAX_HEALTH, 1500.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.6D);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.SPIDER.getDefaultLootTable();
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean attackSucceeded = super.doHurtTarget(target);

        if (target instanceof LivingEntity livingTarget) {
            tryApplyLegacyCombatEffects(livingTarget, attackSucceeded);
        }

        return attackSucceeded;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().isClientSide()) {
            for (int i = 0; i < LEGACY_PARTICLE_COUNT_PER_TICK; ++i) {
                int particleXDirection = this.random.nextInt(2) * 2 - 1;
                int particleZDirection = this.random.nextInt(2) * 2 - 1;
                double particleX = this.getX() + LEGACY_PARTICLE_HORIZONTAL_OFFSET * particleXDirection;
                double particleY = this.getY() + LEGACY_PARTICLE_BASE_Y_OFFSET + this.random.nextFloat();
                double particleZ = this.getZ() + LEGACY_PARTICLE_HORIZONTAL_OFFSET * particleZDirection;
                double motionX = this.random.nextFloat() * 1.0F * particleXDirection;
                double motionY = (this.random.nextFloat() - LEGACY_PARTICLE_VERTICAL_MOTION_OFFSET) * LEGACY_PARTICLE_VERTICAL_MOTION_SCALE;
                double motionZ = this.random.nextFloat() * 1.0F * particleZDirection;

                this.level().addParticle(ModRegistries.CRAZY_MOB_PARTICLE.get(), particleX, particleY, particleZ, motionX, motionY, motionZ);
            }
        }
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

    public boolean tryApplyLegacyCombatEffects(LivingEntity target, boolean attackSucceeded) {
        if (!attackSucceeded) {
            return false;
        }

        boolean appliedBlindness = tryApplyLegacyBlindnessOnHit(target);
        boolean appliedPoison = tryApplyLegacyPoisonOnHit(target);
        return appliedBlindness || appliedPoison;
    }

    public boolean tryApplyLegacyBlindnessOnHit(LivingEntity target) {
        if (target.hasEffect(MobEffects.BLINDNESS)) {
            return false;
        }

        int durationTicks = getLegacyBlindnessDurationTicks(this.level().getDifficulty());
        if (durationTicks <= 0) {
            return false;
        }

        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, durationTicks, LEGACY_BLINDNESS_AMPLIFIER));
        return true;
    }

    public boolean tryApplyLegacyPoisonOnHit(LivingEntity target) {
        if (target.hasEffect(MobEffects.POISON)) {
            return false;
        }

        int durationTicks = getLegacyPoisonDurationTicks(this.level().getDifficulty());
        if (durationTicks <= 0) {
            return false;
        }

        target.addEffect(new MobEffectInstance(MobEffects.POISON, durationTicks, LEGACY_POISON_AMPLIFIER));
        return true;
    }

    public static int getLegacyBlindnessDurationTicks(Difficulty difficulty) {
        return switch (difficulty) {
            case NORMAL -> LEGACY_BLINDNESS_DURATION_NORMAL_TICKS;
            case HARD -> LEGACY_BLINDNESS_DURATION_HARD_TICKS;
            default -> LEGACY_BLINDNESS_DURATION_DEFAULT_TICKS;
        };
    }

    public static int getLegacyPoisonDurationTicks(Difficulty difficulty) {
        return switch (difficulty) {
            case NORMAL -> LEGACY_POISON_DURATION_NORMAL_TICKS;
            case HARD -> LEGACY_POISON_DURATION_HARD_TICKS;
            default -> LEGACY_POISON_DURATION_DEFAULT_TICKS;
        };
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
