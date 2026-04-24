package com.richardkenway.cavernreborn.app.item;

import java.util.List;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.app.registry.ModToolTiers;
import com.richardkenway.cavernreborn.core.combat.CavenicBowMode;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public final class CavenicBowItem extends BowItem {
    private static final String MODE_KEY = "cavernreborn:cavenic_bow_mode";
    private static final String MODE_KEY_PREFIX = "item.cavernreborn.cavenic_bow.mode.";
    private static final String MODE_LINE_KEY = "item.cavernreborn.cavenic_bow.mode";
    private static final String MODE_CHANGED_KEY = "item.cavernreborn.cavenic_bow.mode_changed";

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
}
