package com.richardkenway.cavernreborn.app.entity;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.app.item.CavenicBowItem;

import java.util.EnumSet;
import javax.annotation.Nullable;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.sounds.SoundEvents;

public final class CrazySkeleton extends Skeleton {
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;
    public static final float LEGACY_MAINHAND_DROP_CHANCE = 1.0F;
    public static final double LEGACY_RANGED_MOVEMENT_SPEED = 0.99D;
    public static final float LEGACY_RANGED_MAX_ATTACK_DISTANCE = 6.0F;
    public static final int LEGACY_RANGED_ATTACK_SPEED = 1;
    public static final int LEGACY_RANGED_START_ATTACK_COOLDOWN = 20;
    public static final int LEGACY_RANGED_RESET_ATTACK_COOLDOWN = 50;
    public static final int LEGACY_RANGED_MIN_SEE_TIME_TO_STRAFE = 15;
    public static final int LEGACY_RANGED_STRAFING_TOGGLE_INTERVAL = 5;
    public static final int LEGACY_RANGED_LOST_SIGHT_STOP_THRESHOLD = -20;
    public static final int LEGACY_RANGED_MAX_CONTINUOUS_ATTACK_TIME = 200;
    public static final int LEGACY_RANGED_DRAW_DURATION_TICKS = 5;
    public static final double LEGACY_MELEE_MOVEMENT_SPEED = 1.35D;
    public static final int LEGACY_PARTICLE_COUNT_PER_TICK = 3;
    public static final double LEGACY_PARTICLE_HORIZONTAL_OFFSET = 0.25D;
    public static final double LEGACY_PARTICLE_BASE_Y_OFFSET = 0.65D;
    public static final double LEGACY_PARTICLE_VERTICAL_MOTION_OFFSET = 0.25D;
    public static final double LEGACY_PARTICLE_VERTICAL_MOTION_SCALE = 0.125D;
    public static final double LEGACY_BOSS_BAR_VISIBILITY_DISTANCE = 20.0D;
    public static final double LEGACY_BOSS_BAR_DARKEN_SKY_DISTANCE = 30.0D;

    private final ServerBossEvent legacyBossEvent = new ServerBossEvent(
        this.getDisplayName(),
        BossEvent.BossBarColor.WHITE,
        BossEvent.BossBarOverlay.PROGRESS
    );
    @Nullable
    private Goal legacyRangedAttackGoal;
    @Nullable
    private Goal legacyMeleeAttackGoal;

    public CrazySkeleton(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 50;
        this.setDropChance(EquipmentSlot.MAINHAND, LEGACY_MAINHAND_DROP_CHANCE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Skeleton.createAttributes()
            .add(Attributes.MAX_HEALTH, 2000.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 22.0D);
    }

    public static ItemStack createLegacyCrazySkeletonBow(RegistryAccess registryAccess) {
        ItemStack stack = new ItemStack(ModRegistries.CAVENIC_BOW.get());
        stack.enchant(registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY), 1);
        return stack;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, createLegacyCrazySkeletonBow(this.registryAccess()));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.ensureLegacyCombatGoalsInitialized();
        this.swapLegacyCombatGoal(this.getMainHandItem());
    }

    @Override
    public void reassessWeaponGoal() {
        this.ensureLegacyCombatGoalsInitialized();

        if (this.level() != null && !this.level().isClientSide) {
            this.swapLegacyCombatGoal(this.getMainHandItem());
        }
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

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack mainHandItem = this.getMainHandItem();

        if (!(mainHandItem.getItem() instanceof BowItem bowItem)) {
            super.performRangedAttack(target, distanceFactor);
            return;
        }

        ItemStack projectileStack = this.getProjectile(mainHandItem);
        if (projectileStack.isEmpty()) {
            projectileStack = new ItemStack(Items.ARROW);
        }

        ArrowItem arrowItem = projectileStack.getItem() instanceof ArrowItem candidate ? candidate : (ArrowItem) Items.ARROW;
        AbstractArrow arrow = arrowItem.createArrow(this.level(), projectileStack, this, mainHandItem);
        arrow = bowItem.customArrow(arrow, projectileStack, mainHandItem);
        arrow.setOwner(this);

        double deltaX = target.getX() - this.getX();
        double deltaY = target.getY(0.3333333333333333D) - arrow.getY();
        double deltaZ = target.getZ() - this.getZ();
        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        arrow.shoot(deltaX, deltaY + horizontalDistance * 0.20000000298023224D, deltaZ, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(arrow);
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

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.SKELETON.getDefaultLootTable();
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

    private void ensureLegacyCombatGoalsInitialized() {
        if (this.legacyRangedAttackGoal == null) {
            this.legacyRangedAttackGoal = new LegacyCrazySkeletonCavenicBowAttackGoal();
        }

        if (this.legacyMeleeAttackGoal == null) {
            this.legacyMeleeAttackGoal = new LegacyCrazySkeletonMeleeAttackGoal();
        }
    }

    private void swapLegacyCombatGoal(ItemStack mainHandItem) {
        this.goalSelector.removeAllGoals(this::isVanillaOrLegacyCrazySkeletonCombatGoal);

        if (this.isLegacyBowWeapon(mainHandItem)) {
            this.goalSelector.addGoal(4, this.legacyRangedAttackGoal);
        } else {
            this.goalSelector.addGoal(4, this.legacyMeleeAttackGoal);
        }
    }

    private boolean isVanillaOrLegacyCrazySkeletonCombatGoal(Goal goal) {
        return goal instanceof RangedBowAttackGoal
            || goal instanceof LegacyCrazySkeletonCavenicBowAttackGoal
            || goal instanceof MeleeAttackGoal;
    }

    private boolean isLegacyBowWeapon(ItemStack mainHandItem) {
        return !mainHandItem.isEmpty() && mainHandItem.getItem() instanceof BowItem;
    }

    private final class LegacyCrazySkeletonMeleeAttackGoal extends MeleeAttackGoal {
        private LegacyCrazySkeletonMeleeAttackGoal() {
            super(CrazySkeleton.this, LEGACY_MELEE_MOVEMENT_SPEED, false);
        }

        @Override
        public void start() {
            super.start();
            CrazySkeleton.this.setAggressive(true);
        }

        @Override
        public void stop() {
            super.stop();
            CrazySkeleton.this.setAggressive(false);
        }
    }

    private final class LegacyCrazySkeletonCavenicBowAttackGoal extends Goal {
        private int seeTime;
        private int attackTime;
        private int attackCooldown;
        private int attackRapid;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime = -1;

        private LegacyCrazySkeletonCavenicBowAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return CrazySkeleton.this.getTarget() != null && this.isBowInMainhand();
        }

        @Override
        public boolean canContinueToUse() {
            return (this.canUse() || !CrazySkeleton.this.getNavigation().isDone()) && this.isBowInMainhand();
        }

        @Override
        public void start() {
            CrazySkeleton.this.setAggressive(true);
            this.attackCooldown = LEGACY_RANGED_START_ATTACK_COOLDOWN;
        }

        @Override
        public void stop() {
            CrazySkeleton.this.setAggressive(false);
            this.seeTime = 0;
            this.attackTime = 0;
            CrazySkeleton.this.stopUsingItem();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = CrazySkeleton.this.getTarget();

            if (target == null) {
                return;
            }

            double distance = CrazySkeleton.this.distanceToSqr(target.getX(), target.getBoundingBox().minY, target.getZ());
            boolean canSee = CrazySkeleton.this.getSensing().hasLineOfSight(target);
            boolean seeing = this.seeTime > 0;

            if (canSee != seeing) {
                this.seeTime = 0;
            }

            if (canSee) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (distance <= LEGACY_RANGED_MAX_ATTACK_DISTANCE * LEGACY_RANGED_MAX_ATTACK_DISTANCE && this.seeTime >= LEGACY_RANGED_MIN_SEE_TIME_TO_STRAFE) {
                CrazySkeleton.this.getNavigation().stop();
                ++this.strafingTime;
            } else {
                CrazySkeleton.this.getNavigation().moveTo(target, LEGACY_RANGED_MOVEMENT_SPEED);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= LEGACY_RANGED_STRAFING_TOGGLE_INTERVAL) {
                if (CrazySkeleton.this.getRandom().nextFloat() < 0.3D) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if (CrazySkeleton.this.getRandom().nextFloat() < 0.3D) {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (distance > LEGACY_RANGED_MAX_ATTACK_DISTANCE * LEGACY_RANGED_MAX_ATTACK_DISTANCE * 0.75F) {
                    this.strafingBackwards = false;
                } else if (distance < LEGACY_RANGED_MAX_ATTACK_DISTANCE * LEGACY_RANGED_MAX_ATTACK_DISTANCE * 0.25F) {
                    this.strafingBackwards = true;
                }

                CrazySkeleton.this.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                CrazySkeleton.this.lookAt(target, 30.0F, 30.0F);
            } else {
                CrazySkeleton.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            if (CrazySkeleton.this.isUsingItem()) {
                if ((!canSee && this.seeTime < LEGACY_RANGED_LOST_SIGHT_STOP_THRESHOLD) || this.attackTime > LEGACY_RANGED_MAX_CONTINUOUS_ATTACK_TIME) {
                    CrazySkeleton.this.stopUsingItem();
                    this.attackTime = 0;
                    this.attackCooldown = LEGACY_RANGED_RESET_ATTACK_COOLDOWN;
                } else if (canSee && --this.attackCooldown <= 0) {
                    if (++this.attackRapid >= this.getAttackSpeed()) {
                        CrazySkeleton.this.performRangedAttack(target, BowItem.getPowerForTime(LEGACY_RANGED_DRAW_DURATION_TICKS));
                        this.attackRapid = 0;
                    }

                    ++this.attackTime;
                }
            } else if (this.seeTime >= LEGACY_RANGED_LOST_SIGHT_STOP_THRESHOLD) {
                CrazySkeleton.this.startUsingItem(net.minecraft.world.InteractionHand.MAIN_HAND);
                this.attackTime = 0;
            }
        }

        private boolean isBowInMainhand() {
            return CrazySkeleton.this.isLegacyBowWeapon(CrazySkeleton.this.getMainHandItem());
        }

        private int getAttackSpeed() {
            ItemStack mainHandItem = CrazySkeleton.this.getMainHandItem();

            if (!mainHandItem.isEmpty() && mainHandItem.getItem() instanceof CavenicBowItem) {
                return Math.max(LEGACY_RANGED_ATTACK_SPEED / 2, 1);
            }

            return LEGACY_RANGED_ATTACK_SPEED;
        }
    }
}
