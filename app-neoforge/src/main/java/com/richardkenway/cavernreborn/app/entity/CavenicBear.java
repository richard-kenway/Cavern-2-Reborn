package com.richardkenway.cavernreborn.app.entity;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;

public final class CavenicBear extends PolarBear {
    public static final int NATURAL_SPAWN_WEIGHT = 30;
    public static final int NATURAL_SPAWN_MIN_COUNT = 1;
    public static final int NATURAL_SPAWN_MAX_COUNT = 1;
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;

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
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(this::isVanillaPolarBearMeleeGoal);
        this.goalSelector.removeAllGoals(this::isVanillaPolarBearPanicGoal);
        this.goalSelector.addGoal(1, new LegacyCavenicBearMeleeAttackGoal());
        this.goalSelector.addGoal(1, new LegacyCavenicBearPanicGoal());
        this.targetSelector.removeAllGoals(
            goal -> goal instanceof HurtByTargetGoal || goal instanceof NearestAttackableTargetGoal || goal instanceof ResetUniversalAngerTargetGoal
        );
        this.targetSelector.addGoal(1, new LegacyCavenicBearHurtByTargetGoal());
        this.targetSelector.addGoal(2, new LegacyNearestAttackablePlayerTargetGoal());
    }

    private boolean isVanillaPolarBearMeleeGoal(Goal goal) {
        return goal instanceof MeleeAttackGoal && goal.getClass().getSimpleName().equals("PolarBearMeleeAttackGoal");
    }

    private boolean isVanillaPolarBearPanicGoal(Goal goal) {
        return goal instanceof PanicGoal && goal.getClass().equals(PanicGoal.class);
    }

    public boolean isLegacyHostileTarget(LivingEntity target) {
        return target instanceof Player;
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.POLAR_BEAR.getDefaultLootTable();
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

    private final class LegacyCavenicBearHurtByTargetGoal extends HurtByTargetGoal {
        private LegacyCavenicBearHurtByTargetGoal() {
            super(CavenicBear.this);
        }

        @Override
        public void start() {
            super.start();
            this.alertOthers();
        }

        @Override
        protected void alertOthers() {
            LivingEntity attacker = CavenicBear.this.getLastHurtByMob();
            if (attacker == null) {
                return;
            }

            double followDistance = this.getFollowDistance();
            AABB alertBox = AABB.unitCubeFromLowerCorner(CavenicBear.this.position()).inflate(followDistance, 10.0D, followDistance);

            for (CavenicBear nearbyBear : CavenicBear.this.level().getEntitiesOfClass(CavenicBear.class, alertBox, EntitySelector.NO_SPECTATORS)) {
                if (nearbyBear != CavenicBear.this && nearbyBear.getTarget() == null && !nearbyBear.isAlliedTo(attacker)) {
                    this.alertOther(nearbyBear, attacker);
                }
            }
        }

        @Override
        protected void alertOther(Mob mob, LivingEntity attacker) {
            if (mob instanceof PolarBear polarBear && !polarBear.isBaby()) {
                super.alertOther(mob, attacker);
            }
        }
    }

    private final class LegacyNearestAttackablePlayerTargetGoal extends NearestAttackableTargetGoal<Player> {
        private LegacyNearestAttackablePlayerTargetGoal() {
            super(CavenicBear.this, Player.class, 20, true, true, CavenicBear.this::isLegacyHostileTarget);
        }
    }

    private final class LegacyCavenicBearMeleeAttackGoal extends MeleeAttackGoal {
        private LegacyCavenicBearMeleeAttackGoal() {
            super(CavenicBear.this, 1.25D, true);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            double distance = this.mob.distanceToSqr(target);
            double reachSq = this.getLegacyAttackReachSqr(target);

            if (this.canPerformLegacyAttack(target, distance, reachSq)) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(target);
                CavenicBear.this.setStanding(false);
            } else if (distance <= reachSq * 2.0D) {
                if (this.isTimeToAttack()) {
                    CavenicBear.this.setStanding(false);
                    this.resetAttackCooldown();
                }

                if (this.getTicksUntilNextAttack() <= 10) {
                    CavenicBear.this.setStanding(true);
                    CavenicBear.this.playWarningSound();
                }
            } else {
                this.resetAttackCooldown();
                CavenicBear.this.setStanding(false);
            }
        }

        @Override
        public void stop() {
            CavenicBear.this.setStanding(false);
            super.stop();
        }

        private boolean canPerformLegacyAttack(LivingEntity target, double distance, double reachSq) {
            return this.isTimeToAttack() && distance <= reachSq && this.mob.getSensing().hasLineOfSight(target);
        }

        private double getLegacyAttackReachSqr(LivingEntity target) {
            return 4.0D + target.getBbWidth();
        }
    }

    private final class LegacyCavenicBearPanicGoal extends PanicGoal {
        private LegacyCavenicBearPanicGoal() {
            super(CavenicBear.this, 2.0D);
        }

        @Override
        protected boolean shouldPanic() {
            return CavenicBear.this.isOnFire();
        }
    }
}
