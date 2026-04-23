package com.richardkenway.cavernreborn.app.compass;

import com.richardkenway.cavernreborn.core.compass.OreCompassDirection;
import com.richardkenway.cavernreborn.core.compass.OreCompassScanResult;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public final class OreCompassFeedbackFormatter {
    private static final String FOUND_KEY = "message.cavernreborn.ore_compass.found";
    private static final String NO_TARGET_KEY = "message.cavernreborn.ore_compass.no_target";
    private static final String WRONG_DIMENSION_KEY = "message.cavernreborn.ore_compass.wrong_dimension";
    private static final String COOLDOWN_KEY = "message.cavernreborn.ore_compass.cooldown";

    private OreCompassFeedbackFormatter() {
    }

    public static Component found(BlockState state, OreCompassScanResult result) {
        return Component.translatable(
            FOUND_KEY,
            Component.translatable(state.getBlock().getDescriptionId()),
            result.horizontalDistance(),
            Component.translatable(directionKey(result.direction())),
            verticalRelation(result.verticalOffset())
        );
    }

    public static Component noTarget() {
        return Component.translatable(NO_TARGET_KEY);
    }

    public static Component wrongDimension() {
        return Component.translatable(WRONG_DIMENSION_KEY);
    }

    public static Component cooldown() {
        return Component.translatable(COOLDOWN_KEY);
    }

    private static Component verticalRelation(int verticalOffset) {
        if (verticalOffset > 0) {
            return Component.translatable("message.cavernreborn.ore_compass.vertical.above", verticalOffset);
        }
        if (verticalOffset < 0) {
            return Component.translatable("message.cavernreborn.ore_compass.vertical.below", Math.abs(verticalOffset));
        }
        return Component.translatable("message.cavernreborn.ore_compass.vertical.same_level");
    }

    private static String directionKey(OreCompassDirection direction) {
        return switch (direction) {
            case SAME_POSITION -> "message.cavernreborn.ore_compass.direction.same_position";
            case NORTH -> "message.cavernreborn.ore_compass.direction.north";
            case NORTH_EAST -> "message.cavernreborn.ore_compass.direction.north_east";
            case EAST -> "message.cavernreborn.ore_compass.direction.east";
            case SOUTH_EAST -> "message.cavernreborn.ore_compass.direction.south_east";
            case SOUTH -> "message.cavernreborn.ore_compass.direction.south";
            case SOUTH_WEST -> "message.cavernreborn.ore_compass.direction.south_west";
            case WEST -> "message.cavernreborn.ore_compass.direction.west";
            case NORTH_WEST -> "message.cavernreborn.ore_compass.direction.north_west";
        };
    }
}
