package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface PortalFrameMaterialPolicy {
    boolean isFrame(BlockState state);

    PortalFrameMaterialPolicy MOSSY_COBBLESTONE = state -> state.is(Blocks.MOSSY_COBBLESTONE);

    static PortalFrameMaterialPolicy of(Block frameMaterial) {
        Objects.requireNonNull(frameMaterial, "frameMaterial");
        return state -> state.is(frameMaterial);
    }
}
