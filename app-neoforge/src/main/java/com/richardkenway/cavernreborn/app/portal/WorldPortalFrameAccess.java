package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;

import com.richardkenway.cavernreborn.app.block.CavernPortalBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public record WorldPortalFrameAccess(LevelAccessor level, Block portalBlock) implements CavernPortalFrameActivator.PortalAccess {
    public WorldPortalFrameAccess {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(portalBlock, "portalBlock");
    }

    @Override
    public boolean isFrame(BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.OBSIDIAN);
    }

    @Override
    public boolean isInterior(BlockPos pos) {
        return isInteriorState(level.getBlockState(pos), portalBlock);
    }

    @Override
    public boolean isPortal(BlockPos pos) {
        return level.getBlockState(pos).is(portalBlock);
    }

    @Override
    public void setPortal(BlockPos pos, Direction.Axis axis) {
        BlockState portalState = portalBlock.defaultBlockState();
        if (portalState.hasProperty(CavernPortalBlock.AXIS)) {
            portalState = portalState.setValue(CavernPortalBlock.AXIS, axis);
        }

        level.setBlock(pos, portalState, 2);
    }

    public static boolean isInteriorState(BlockState state, Block portalBlock) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(portalBlock, "portalBlock");
        return state.isAir() || state.is(portalBlock);
    }
}
