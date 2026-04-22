package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;
import java.util.UUID;

import com.richardkenway.cavernreborn.app.mining.MiningAssistBreakContext;
import com.richardkenway.cavernreborn.app.registry.ModItemTags;
import com.richardkenway.cavernreborn.core.mining.MinerOrbBonusPolicy;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionConsequences;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionPolicy;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUpdateResult;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public final class CavernMiningProgressionEvents {
    private final CavernProgressionService progressionService;
    private final MinerOrbRollSource minerOrbRollSource;

    public CavernMiningProgressionEvents(CavernProgressionService progressionService) {
        this(progressionService, RandomSource.create()::nextInt);
    }

    CavernMiningProgressionEvents(CavernProgressionService progressionService, MinerOrbRollSource minerOrbRollSource) {
        this.progressionService = Objects.requireNonNull(progressionService, "progressionService");
        this.minerOrbRollSource = Objects.requireNonNull(minerOrbRollSource, "minerOrbRollSource");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (!(event.getPlayer() instanceof ServerPlayer player) || player.isCreative()) {
            return;
        }
        if (!(event.getLevel() instanceof Level level) || level.isClientSide()) {
            return;
        }
        if (MiningAssistBreakContext.isSuppressed(player.getUUID(), event.getPos())) {
            return;
        }

        String dimensionId = level.dimension().location().toString();
        String blockId = BuiltInRegistries.BLOCK.getKey(event.getState().getBlock()).toString();
        CavernProgressionUpdateResult update = recordMiningProgression(
            player.getUUID(),
            dimensionId,
            blockId,
            player.isCreative(),
            hasMiningBonusOrb(player.getInventory().items, player.getInventory().offhand),
            false
        );
        if (!update.counted()) {
            return;
        }

        int bonusExperience = CavernProgressionConsequences.bonusExperienceFor(update.currentSnapshot());
        if (bonusExperience > 0) {
            player.giveExperiencePoints(bonusExperience);
        }

        CavernProgressionRankUpFeedbackFormatter.formatActionBar(update)
            .ifPresent(message -> player.displayClientMessage(Component.literal(message), true));
        CavernProgressionRankUpFeedbackFormatter.formatUnlockMessage(update)
            .ifPresent(message -> player.sendSystemMessage(Component.literal(message)));
    }

    CavernProgressionUpdateResult recordMiningProgression(
        UUID playerId,
        String dimensionId,
        String blockId,
        boolean creative,
        boolean hasMinerOrb,
        boolean miningAssistSuppressed
    ) {
        boolean countedBlock = CavernProgressionPolicy.countsTowardProgression(dimensionId, blockId);
        int baseScore = CavernProgressionPolicy.scoreForBlock(blockId);
        return progressionService.recordMiningEvent(
            playerId,
            dimensionId,
            blockId,
            resolveMinerOrbBonus(
                dimensionId,
                blockId,
                baseScore,
                countedBlock,
                hasMinerOrb,
                creative,
                miningAssistSuppressed
            )
        );
    }

    private int resolveMinerOrbBonus(
        String dimensionId,
        String blockId,
        int baseScore,
        boolean countedBlock,
        boolean hasMinerOrb,
        boolean creative,
        boolean miningAssistSuppressed
    ) {
        int roll = shouldRollMinerOrbBonus(dimensionId, countedBlock, baseScore, hasMinerOrb, creative, miningAssistSuppressed)
            ? minerOrbRollSource.nextRoll(MinerOrbBonusPolicy.BONUS_ROLL_BOUND)
            : 0;

        return MinerOrbBonusPolicy.evaluate(
            dimensionId,
            blockId,
            baseScore,
            countedBlock,
            hasMinerOrb,
            creative,
            miningAssistSuppressed,
            roll
        ).bonusScore();
    }

    private static boolean shouldRollMinerOrbBonus(
        String dimensionId,
        boolean countedBlock,
        int baseScore,
        boolean hasMinerOrb,
        boolean creative,
        boolean miningAssistSuppressed
    ) {
        return countedBlock
            && baseScore > 0
            && hasMinerOrb
            && !creative
            && !miningAssistSuppressed
            && CavernDimensions.CAVERN_DIMENSION_ID.equals(dimensionId);
    }

    private static boolean hasMiningBonusOrb(Iterable<ItemStack> inventoryItems, Iterable<ItemStack> offhandItems) {
        return containsMiningBonusOrb(inventoryItems) || containsMiningBonusOrb(offhandItems);
    }

    private static boolean containsMiningBonusOrb(Iterable<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            if (stack.is(ModItemTags.MINING_BONUS_ORBS)) {
                return true;
            }
        }
        return false;
    }

    @FunctionalInterface
    interface MinerOrbRollSource {
        int nextRoll(int bound);
    }
}
