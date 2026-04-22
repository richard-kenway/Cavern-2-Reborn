package com.richardkenway.cavernreborn.app.mining;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.richardkenway.cavernreborn.app.registry.ModBlockTags;
import com.richardkenway.cavernreborn.core.mining.MiningAssistDecision;
import com.richardkenway.cavernreborn.core.mining.MiningAssistPolicy;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUnlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public final class CavernMiningAssistEvents {
    private final CavernProgressionService progressionService;
    private final MiningAssistBlockCollector blockCollector;

    public CavernMiningAssistEvents(CavernProgressionService progressionService) {
        this(progressionService, new MiningAssistBlockCollector());
    }

    CavernMiningAssistEvents(CavernProgressionService progressionService, MiningAssistBlockCollector blockCollector) {
        this.progressionService = Objects.requireNonNull(progressionService, "progressionService");
        this.blockCollector = Objects.requireNonNull(blockCollector, "blockCollector");
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (!(event.getPlayer() instanceof ServerPlayer player) || player.isCreative() || player.isSpectator()) {
            return;
        }
        if (!(event.getLevel() instanceof net.minecraft.server.level.ServerLevel level) || level.isClientSide()) {
            return;
        }

        UUID playerId = player.getUUID();
        BlockPos originPos = event.getPos().immutable();
        if (MiningAssistBreakContext.isAssistActive(playerId) || MiningAssistBreakContext.isSuppressed(playerId, originPos)) {
            return;
        }

        tryApplyMiningAssist(level, player, originPos, event.getState(), level.dimension().location().toString());
    }

    public int tryApplyMiningAssist(
        net.minecraft.server.level.ServerLevel level,
        Player player,
        BlockPos originPos,
        BlockState originState,
        String dimensionId
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(player, "player");
        BlockPos normalizedOriginPos = Objects.requireNonNull(originPos, "originPos").immutable();
        BlockState normalizedOriginState = Objects.requireNonNull(originState, "originState");

        ItemStack tool = player.getMainHandItem();
        UUID playerId = player.getUUID();
        MiningAssistDecision decision = MiningAssistPolicy.evaluate(
            dimensionId,
            BuiltInRegistries.BLOCK.getKey(normalizedOriginState.getBlock()).toString(),
            BuiltInRegistries.ITEM.getKey(tool.getItem()).toString(),
            true,
            player.isCreative(),
            player.isShiftKeyDown(),
            progressionService.inspect(playerId).hasUnlocked(CavernProgressionUnlock.MINING_ASSIST),
            normalizedOriginState.is(ModBlockTags.MINING_ASSIST_TARGETS)
        );
        if (decision != MiningAssistDecision.ENABLED) {
            return 0;
        }

        int maxExtraBreaks = Math.min(MiningAssistPolicy.MAX_EXTRA_BLOCKS, remainingExtraBreakCapacity(tool));
        if (maxExtraBreaks <= 0) {
            return 0;
        }
        if (!canAssistBreak(level, normalizedOriginPos, normalizedOriginState, player, tool)) {
            return 0;
        }

        List<BlockPos> extraBlocks = blockCollector.collectSameBlockVein(
            level,
            normalizedOriginPos,
            normalizedOriginState,
            (candidatePos, candidateState) -> canCollectAssistTarget(level, candidatePos, candidateState, normalizedOriginState, player, tool),
            maxExtraBreaks
        );
        if (extraBlocks.isEmpty()) {
            return 0;
        }

        return MiningAssistBreakContext.withActiveAssist(playerId, () -> {
            int brokenBlocks = 0;
            for (BlockPos extraPos : extraBlocks) {
                ItemStack currentTool = player.getMainHandItem();
                if (remainingExtraBreakCapacity(currentTool) <= 0) {
                    break;
                }
                if (!canAssistBreak(level, extraPos, level.getBlockState(extraPos), player, currentTool)) {
                    continue;
                }

                if (MiningAssistBreakContext.withSuppressedBreak(
                    playerId,
                    extraPos,
                    () -> breakBlockWithTool(level, player, extraPos, level.getBlockState(extraPos), currentTool, true)
                )) {
                    brokenBlocks++;
                }
            }
            return brokenBlocks;
        });
    }

    static int remainingExtraBreakCapacity(ItemStack tool) {
        if (tool == null || tool.isEmpty() || !tool.isDamageableItem()) {
            return 0;
        }

        int remainingDurability = tool.getMaxDamage() - tool.getDamageValue();
        return Math.max(0, remainingDurability - 1);
    }

    private static boolean canCollectAssistTarget(
        net.minecraft.server.level.ServerLevel level,
        BlockPos pos,
        BlockState candidateState,
        BlockState originState,
        Player player,
        ItemStack tool
    ) {
        return candidateState.is(originState.getBlock()) && canAssistBreak(level, pos, candidateState, player, tool);
    }

    private static boolean canAssistBreak(
        net.minecraft.server.level.ServerLevel level,
        BlockPos pos,
        BlockState state,
        Player player,
        ItemStack tool
    ) {
        if (!state.is(ModBlockTags.MINING_ASSIST_TARGETS)) {
            return false;
        }
        if (tool.isEmpty() || !tool.isDamageableItem() || !player.hasCorrectToolForDrops(state)) {
            return false;
        }
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity == null;
    }

    public static boolean breakBlockWithTool(
        net.minecraft.server.level.ServerLevel level,
        Player player,
        BlockPos pos,
        BlockState state,
        ItemStack tool,
        boolean applyDurability
    ) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        state.getBlock().playerDestroy(level, player, pos, state, blockEntity, tool);
        boolean removed = level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        if (removed && applyDurability && tool.isDamageableItem()) {
            tool.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
        }
        return removed;
    }
}
