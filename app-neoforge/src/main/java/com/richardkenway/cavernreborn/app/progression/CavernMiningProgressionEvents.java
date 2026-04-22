package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;

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
import net.minecraft.world.entity.player.Inventory;
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
        boolean countedBlock = CavernProgressionPolicy.countsTowardProgression(dimensionId, blockId);
        int baseScore = CavernProgressionPolicy.scoreForBlock(blockId);
        CavernProgressionUpdateResult update = progressionService.recordMiningEvent(
            player.getUUID(),
            dimensionId,
            blockId,
            resolveMinerOrbBonus(player, dimensionId, blockId, baseScore, countedBlock, false)
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

    private int resolveMinerOrbBonus(
        ServerPlayer player,
        String dimensionId,
        String blockId,
        int baseScore,
        boolean countedBlock,
        boolean miningAssistSuppressed
    ) {
        boolean hasMinerOrb = hasMiningBonusOrb(player);
        int roll = shouldRollMinerOrbBonus(dimensionId, countedBlock, baseScore, hasMinerOrb, player.isCreative(), miningAssistSuppressed)
            ? minerOrbRollSource.nextRoll(MinerOrbBonusPolicy.BONUS_ROLL_BOUND)
            : 0;

        return MinerOrbBonusPolicy.evaluate(
            dimensionId,
            blockId,
            baseScore,
            countedBlock,
            hasMinerOrb,
            player.isCreative(),
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

    private static boolean hasMiningBonusOrb(ServerPlayer player) {
        Inventory inventory = player.getInventory();
        return containsMiningBonusOrb(inventory.items) || containsMiningBonusOrb(inventory.offhand);
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
