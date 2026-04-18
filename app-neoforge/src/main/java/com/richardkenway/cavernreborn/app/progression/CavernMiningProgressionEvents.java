package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionConsequences;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUpdateResult;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public final class CavernMiningProgressionEvents {
    private final CavernProgressionService progressionService;

    public CavernMiningProgressionEvents(CavernProgressionService progressionService) {
        this.progressionService = Objects.requireNonNull(progressionService, "progressionService");
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

        CavernProgressionUpdateResult update = progressionService.recordMiningEvent(
            player.getUUID(),
            level.dimension().location().toString(),
            BuiltInRegistries.BLOCK.getKey(event.getState().getBlock()).toString()
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
}
