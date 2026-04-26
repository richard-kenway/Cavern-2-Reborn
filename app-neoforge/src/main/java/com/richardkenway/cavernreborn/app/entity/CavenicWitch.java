package com.richardkenway.cavernreborn.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

public final class CavenicWitch extends Witch {
    public static final int NATURAL_SPAWN_WEIGHT = 15;
    public static final int NATURAL_SPAWN_MIN_COUNT = 1;
    public static final int NATURAL_SPAWN_MAX_COUNT = 1;
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;
    public static final float LEGACY_RANGED_POISON_OR_HEAL_HIGH_HEALTH_CHANCE = 0.5F;
    public static final float LEGACY_RANGED_POISON_OR_HEAL_LOW_HEALTH_CHANCE = 0.3F;
    public static final float LEGACY_RANGED_WEAKNESS_CHANCE = 0.25F;
    public static final float LEGACY_RANGED_SLOWNESS_CHANCE = 0.2F;
    public static final float LEGACY_RANGED_POTION_SPEED = 0.75F;
    public static final float LEGACY_RANGED_POTION_INACCURACY = 8.0F;
    public static final float LEGACY_RANGED_POTION_PITCH_OFFSET = 20.0F;

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
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (target instanceof Raider && !(target instanceof Witch)) {
            super.performRangedAttack(target, distanceFactor);
            return;
        }

        for (ThrownPotion thrownPotion : createLegacyThrownPotionsFor(target)) {
            if (!this.isSilent()) {
                this.level().playSound(
                    null,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    net.minecraft.sounds.SoundEvents.WITCH_THROW,
                    this.getSoundSource(),
                    1.0F,
                    0.8F + this.getRandom().nextFloat() * 0.4F
                );
            }

            this.level().addFreshEntity(thrownPotion);
        }
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

    public static int getLegacyAttackPotionCount(Difficulty difficulty) {
        return difficulty.getId();
    }

    public static Holder<Potion> selectLegacyRangedPotionFor(LivingEntity target, RandomSource random) {
        Holder<Potion> potion = Potions.HARMING;
        float poisonOrHealChance = target.getHealth() >= 8.0F
            ? LEGACY_RANGED_POISON_OR_HEAL_HIGH_HEALTH_CHANCE
            : LEGACY_RANGED_POISON_OR_HEAL_LOW_HEALTH_CHANCE;

        if (random.nextFloat() < poisonOrHealChance) {
            potion = target.isInvertedHealAndHarm() ? Potions.HEALING : Potions.POISON;
        } else if (random.nextFloat() < LEGACY_RANGED_WEAKNESS_CHANCE) {
            potion = Potions.WEAKNESS;
        } else if (random.nextFloat() < LEGACY_RANGED_SLOWNESS_CHANCE) {
            potion = Potions.SLOWNESS;
        }

        return potion;
    }

    public List<ThrownPotion> createLegacyThrownPotionsFor(LivingEntity target) {
        List<ThrownPotion> thrownPotions = new ArrayList<>();

        if (this.isDrinkingPotion()) {
            return thrownPotions;
        }

        for (int i = 0; i < getLegacyAttackPotionCount(this.level().getDifficulty()); i++) {
            Holder<Potion> potion = selectLegacyRangedPotionFor(target, this.getRandom());
            thrownPotions.add(createLegacyThrownPotion(target, potion));
        }

        return thrownPotions;
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

    private ThrownPotion createLegacyThrownPotion(LivingEntity target, Holder<Potion> potion) {
        Vec3 targetMovement = target.getDeltaMovement();
        double targetX = target.getX() + targetMovement.x - this.getX();
        double targetY = target.getEyeY() - 1.100000023841858D - this.getY();
        double targetZ = target.getZ() + targetMovement.z - this.getZ();
        double horizontalDistance = Math.sqrt(targetX * targetX + targetZ * targetZ);
        ThrownPotion thrownPotion = new ThrownPotion(this.level(), this);

        thrownPotion.setItem(PotionContents.createItemStack(Items.SPLASH_POTION, potion));
        thrownPotion.setXRot(thrownPotion.getXRot() - -LEGACY_RANGED_POTION_PITCH_OFFSET);
        thrownPotion.shoot(
            targetX,
            targetY + horizontalDistance * 0.2D,
            targetZ,
            LEGACY_RANGED_POTION_SPEED,
            LEGACY_RANGED_POTION_INACCURACY
        );
        return thrownPotion;
    }
}
