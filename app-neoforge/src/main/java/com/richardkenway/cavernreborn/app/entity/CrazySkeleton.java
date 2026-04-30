package com.richardkenway.cavernreborn.app.entity;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public final class CrazySkeleton extends Skeleton {
    public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;
    public static final float LEGACY_MAINHAND_DROP_CHANCE = 1.0F;

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
        return EntityType.SKELETON.getDefaultLootTable();
    }
}
