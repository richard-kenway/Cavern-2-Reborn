package com.richardkenway.cavernreborn.app.compass;

import net.minecraft.core.BlockPos;

public record StoredOreCompassTarget(
    String dimensionId,
    String blockId,
    BlockPos pos
) {
}
