package com.richardkenway.cavernreborn.app.item;

import java.util.List;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.app.registry.ModToolTiers;
import com.richardkenway.cavernreborn.core.combat.CavenicBowMode;
import com.richardkenway.cavernreborn.core.combat.CavenicBowRapidPolicy;
import com.richardkenway.cavernreborn.core.combat.CavenicBowSnipePolicy;
import com.richardkenway.cavernreborn.core.combat.CavenicBowTorchPolicy;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

public final class CavenicBowItem extends BowItem {
    private static final String MODE_KEY = "cavernreborn:cavenic_bow_mode";
    private static final String MODE_KEY_PREFIX = "item.cavernreborn.cavenic_bow.mode.";
    private static final String MODE_LINE_KEY = "item.cavernreborn.cavenic_bow.mode";
    private static final String MODE_CHANGED_KEY = "item.cavernreborn.cavenic_bow.mode_changed";
    public static final String TORCH_ARROW_MARKER = "cavernreborn:cavenic_bow_torch";
    private static final ThreadLocal<ShotContext> CURRENT_SHOT_CONTEXT = new ThreadLocal<>();

    public CavenicBowItem(Properties properties) {
        super(properties);
    }

    public CavenicBowMode getMode(ItemStack stack) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (!customData.contains(MODE_KEY)) {
            return CavenicBowMode.NORMAL;
        }

        CompoundTag tag = customData.copyTag();
        if (!tag.contains(MODE_KEY, Tag.TAG_STRING)) {
            return CavenicBowMode.NORMAL;
        }

        return CavenicBowMode.fromSerializedId(tag.getString(MODE_KEY));
    }

    public void setMode(ItemStack stack, CavenicBowMode mode) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString(MODE_KEY, mode.serializedId()));
    }

    public CavenicBowMode cycleMode(ItemStack stack) {
        CavenicBowMode nextMode = getMode(stack).next();
        setMode(stack, nextMode);
        return nextMode;
    }

    public float resolveShotPower(ItemStack stack, float rawPower) {
        return CavenicBowRapidPolicy.adjustedPower(getMode(stack), rawPower);
    }

    public float resolveProjectileVelocity(ItemStack stack, float baseVelocity, float power) {
        return CavenicBowSnipePolicy.adjustedVelocity(getMode(stack), baseVelocity, power);
    }

    public boolean applySnipeBoost(ItemStack stack, AbstractArrow arrow, float power) {
        CavenicBowMode mode = getMode(stack);
        if (!CavenicBowSnipePolicy.applies(mode, power)) {
            return false;
        }

        arrow.setBaseDamage(CavenicBowSnipePolicy.adjustedBaseDamage(mode, arrow.getBaseDamage(), power));
        return true;
    }

    public int resolveAdditionalDurabilityCost(ItemStack stack, float power) {
        return CavenicBowSnipePolicy.applies(getMode(stack), power) ? CavenicBowSnipePolicy.EXTRA_DURABILITY_COST : 0;
    }

    public boolean hasTorchAmmo(Player player) {
        return !findTorchAmmo(player).isEmpty();
    }

    public boolean consumeTorchAmmo(Player player) {
        if (player.isCreative()) {
            return true;
        }

        ItemStack torchAmmo = findTorchAmmo(player);
        if (torchAmmo.isEmpty()) {
            return false;
        }

        torchAmmo.shrink(1);
        return true;
    }

    public boolean shouldMarkTorchShot(ItemStack stack, Player player, float shotPower) {
        return shotPower > 0.0F && CavenicBowTorchPolicy.shouldMarkShot(getMode(stack), player.isCreative(), hasTorchAmmo(player));
    }

    public static void markTorchArrow(AbstractArrow arrow) {
        arrow.addTag(TORCH_ARROW_MARKER);
    }

    public static boolean isTorchArrow(AbstractArrow arrow) {
        return arrow.getTags().contains(TORCH_ARROW_MARKER);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                CavenicBowMode nextMode = cycleMode(stack);
                player.displayClientMessage(Component.translatable(MODE_CHANGED_KEY, modeLabel(nextMode)), true);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        return super.use(level, player, usedHand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }

        ItemStack projectile = player.getProjectile(stack);
        if (projectile.isEmpty()) {
            return;
        }

        int charge = getUseDuration(stack, livingEntity) - timeLeft;
        charge = EventHooks.onArrowLoose(stack, level, player, charge, true);
        if (charge < 0) {
            return;
        }

        float rawPower = getPowerForTime(charge);
        if ((double) rawPower < 0.1D) {
            return;
        }

        float shotPower = resolveShotPower(stack, rawPower);

        List<ItemStack> drawnProjectiles = draw(stack, projectile, livingEntity);
        if (level instanceof ServerLevel serverLevel && !drawnProjectiles.isEmpty()) {
            boolean markTorchShot = shouldMarkTorchShot(stack, player, shotPower);
            if (markTorchShot && !consumeTorchAmmo(player)) {
                markTorchShot = false;
            }

            ShotContext shotContext = new ShotContext(shotPower, markTorchShot, livingEntity);
            CURRENT_SHOT_CONTEXT.set(shotContext);
            try {
                shoot(
                    serverLevel,
                    livingEntity,
                    player.getUsedItemHand(),
                    stack,
                    drawnProjectiles,
                    resolveProjectileVelocity(stack, shotPower * 3.0F, shotPower),
                    1.0F,
                    shotPower == 1.0F,
                    null
                );
            } finally {
                CURRENT_SHOT_CONTEXT.remove();
            }

            int additionalDurabilityCost = resolveAdditionalDurabilityCost(stack, shotPower);
            if (additionalDurabilityCost > 0 && !stack.isEmpty()) {
                stack.hurtAndBreak(additionalDurabilityCost, livingEntity, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            }
        }

        level.playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            SoundEvents.ARROW_SHOOT,
            SoundSource.PLAYERS,
            1.0F,
            1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + rawPower * 0.5F
        );
        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow, ItemStack projectileStack, ItemStack weaponStack) {
        AbstractArrow customizedArrow = super.customArrow(arrow, projectileStack, weaponStack);
        ShotContext shotContext = CURRENT_SHOT_CONTEXT.get();
        if (shotContext != null) {
            if (shotContext.shooter() != null) {
                customizedArrow.setOwner(shotContext.shooter());
            }
            if (shotContext.markTorchShot()) {
                markTorchArrow(customizedArrow);
            }
            applySnipeBoost(weaponStack, customizedArrow, shotContext.power());
        }
        return customizedArrow;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(ModRegistries.CAVENIC_ORB.get()) || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public int getEnchantmentValue() {
        return ModToolTiers.CAVENIC.getEnchantmentValue();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable(MODE_LINE_KEY, modeLabel(getMode(stack))));
    }

    private static Component modeLabel(CavenicBowMode mode) {
        return Component.translatable(MODE_KEY_PREFIX + mode.serializedId());
    }

    private static ItemStack findTorchAmmo(Player player) {
        ItemStack offhand = player.getOffhandItem();
        if (offhand.is(Items.TORCH)) {
            return offhand;
        }

        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.is(Items.TORCH)) {
            return mainHand;
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(Items.TORCH)) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    private record ShotContext(float power, boolean markTorchShot, LivingEntity shooter) {
    }
}
