package com.richardkenway.cavernreborn.app.compass;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public final class OreCompassStateAccess {
    static final String TARGET_KEY = "cavernreborn_ore_compass_target";
    static final String DIMENSION_KEY = "dimension_id";
    static final String BLOCK_ID_KEY = "target_block_id";
    static final String TARGET_X_KEY = "target_x";
    static final String TARGET_Y_KEY = "target_y";
    static final String TARGET_Z_KEY = "target_z";

    private OreCompassStateAccess() {
    }

    public static void clear(ItemStack stack) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove(TARGET_KEY));
    }

    public static void writeTarget(ItemStack stack, String dimensionId, String blockId, BlockPos pos) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
            CompoundTag targetTag = new CompoundTag();
            targetTag.putString(DIMENSION_KEY, dimensionId);
            targetTag.putString(BLOCK_ID_KEY, blockId);
            targetTag.putInt(TARGET_X_KEY, pos.getX());
            targetTag.putInt(TARGET_Y_KEY, pos.getY());
            targetTag.putInt(TARGET_Z_KEY, pos.getZ());
            tag.put(TARGET_KEY, targetTag);
        });
    }

    public static Optional<StoredOreCompassTarget> readTarget(ItemStack stack) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (!customData.contains(TARGET_KEY)) {
            return Optional.empty();
        }

        CompoundTag rootTag = customData.copyTag();
        if (!rootTag.contains(TARGET_KEY, Tag.TAG_COMPOUND)) {
            return Optional.empty();
        }

        CompoundTag targetTag = rootTag.getCompound(TARGET_KEY);
        if (!targetTag.contains(DIMENSION_KEY, Tag.TAG_STRING)
            || !targetTag.contains(BLOCK_ID_KEY, Tag.TAG_STRING)
            || !targetTag.contains(TARGET_X_KEY, Tag.TAG_INT)
            || !targetTag.contains(TARGET_Y_KEY, Tag.TAG_INT)
            || !targetTag.contains(TARGET_Z_KEY, Tag.TAG_INT)) {
            return Optional.empty();
        }

        String dimensionId = targetTag.getString(DIMENSION_KEY);
        String blockId = targetTag.getString(BLOCK_ID_KEY);
        if (dimensionId.isBlank() || blockId.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(
            new StoredOreCompassTarget(
                dimensionId,
                blockId,
                new BlockPos(
                    targetTag.getInt(TARGET_X_KEY),
                    targetTag.getInt(TARGET_Y_KEY),
                    targetTag.getInt(TARGET_Z_KEY)
                )
            )
        );
    }
}
