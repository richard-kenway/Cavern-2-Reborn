package com.richardkenway.cavernreborn.app.compass;

import net.minecraft.core.BlockPos;

public record OreCompassTarget(
    BlockPos pos,
    String blockId
) {
}
