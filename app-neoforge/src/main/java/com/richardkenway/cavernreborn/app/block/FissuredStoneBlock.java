package com.richardkenway.cavernreborn.app.block;

import java.util.List;

import javax.annotation.Nullable;

import com.richardkenway.cavernreborn.core.fissure.FissuredStoneBuff;
import com.richardkenway.cavernreborn.core.fissure.FissuredStoneEffectPlan;
import com.richardkenway.cavernreborn.core.fissure.FissuredStoneEffectPolicy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public final class FissuredStoneBlock extends DropExperienceBlock {
    public FissuredStoneBlock(IntProvider experienceRange, BlockBehaviour.Properties properties) {
        super(experienceRange, properties);
    }

    @Override
    public void playerDestroy(
        Level level,
        Player player,
        BlockPos pos,
        BlockState state,
        @Nullable BlockEntity blockEntity,
        ItemStack tool
    ) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (player.isCreative()) {
            return;
        }

        applyEffect(serverLevel, pos, player, FissuredStoneEffectPolicy.effectForBreak(true, fortuneLevel(level, tool)));
    }

    static void applyEffect(ServerLevel level, BlockPos pos, @Nullable Player player, FissuredStoneEffectPlan plan) {
        if (plan.spawnsAreaEffectCloud()) {
            AreaEffectCloud cloud = new AreaEffectCloud(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
            cloud.setOwner(player);
            cloud.setRadius(plan.radius());
            cloud.setWaitTime(0);
            cloud.setDuration(plan.durationTicks());
            firstBuff(plan.buffs()).ifPresent(buff -> cloud.addEffect(new MobEffectInstance(resolveEffect(buff), plan.durationTicks(), plan.amplifier())));
            level.addFreshEntity(cloud);
            return;
        }

        if (plan.appliesDirectPlayerBuff() && player != null) {
            for (FissuredStoneBuff buff : plan.buffs()) {
                player.addEffect(new MobEffectInstance(resolveEffect(buff), plan.durationTicks(), plan.amplifier()));
            }
        }
    }

    private static java.util.Optional<FissuredStoneBuff> firstBuff(List<FissuredStoneBuff> buffs) {
        return buffs.stream().findFirst();
    }

    private static Holder<MobEffect> resolveEffect(FissuredStoneBuff buff) {
        return switch (buff) {
            case NIGHT_VISION -> MobEffects.NIGHT_VISION;
            case REGENERATION -> MobEffects.REGENERATION;
            case ABSORPTION -> MobEffects.ABSORPTION;
            case RESISTANCE -> MobEffects.DAMAGE_RESISTANCE;
        };
    }

    private static int fortuneLevel(Level level, ItemStack tool) {
        if (tool.isEmpty()) {
            return 0;
        }

        HolderLookup.RegistryLookup<Enchantment> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        return EnchantmentHelper.getItemEnchantmentLevel(enchantments.getOrThrow(Enchantments.FORTUNE), tool);
    }
}
