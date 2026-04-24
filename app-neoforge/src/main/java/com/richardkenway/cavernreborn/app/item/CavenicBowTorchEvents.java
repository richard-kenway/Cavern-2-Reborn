package com.richardkenway.cavernreborn.app.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

public final class CavenicBowTorchEvents {
    @SubscribeEvent
    public void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) {
            return;
        }
        if (!(arrow.level() instanceof ServerLevel level) || !CavenicBowItem.isTorchArrow(arrow)) {
            return;
        }
        if (event.getRayTraceResult().getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockHitResult hitResult = (BlockHitResult) event.getRayTraceResult();
        if (tryPlaceTorchFromMarkedArrow(level, arrow, hitResult)) {
            arrow.removeTag(CavenicBowItem.TORCH_ARROW_MARKER);
            arrow.discard();
            event.setCanceled(true);
        }
    }

    public boolean tryPlaceTorchFromMarkedArrow(ServerLevel level, AbstractArrow arrow, BlockHitResult hitResult) {
        if (!CavenicBowItem.isTorchArrow(arrow)) {
            return false;
        }

        Direction face = hitResult.getDirection();
        if (face == Direction.DOWN) {
            return false;
        }

        BlockPos targetPos = hitResult.getBlockPos().relative(face);
        if (!level.hasChunkAt(targetPos)) {
            return false;
        }

        BlockState targetState = level.getBlockState(targetPos);
        if (!targetState.isAir() && !targetState.canBeReplaced()) {
            return false;
        }
        if (!level.getFluidState(targetPos).isEmpty()) {
            return false;
        }

        BlockState placedState = resolvePlacedTorchState(level, targetPos, face);
        if (placedState == null || !placedState.canSurvive(level, targetPos)) {
            return false;
        }

        return level.setBlock(targetPos, placedState, net.minecraft.world.level.block.Block.UPDATE_ALL);
    }

    private static BlockState resolvePlacedTorchState(ServerLevel level, BlockPos targetPos, Direction face) {
        if (face == Direction.UP) {
            return Blocks.TORCH.defaultBlockState();
        }
        if (!face.getAxis().isHorizontal()) {
            return null;
        }

        BlockState directState = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, face);
        return directState.canSurvive(level, targetPos) ? directState : null;
    }
}
