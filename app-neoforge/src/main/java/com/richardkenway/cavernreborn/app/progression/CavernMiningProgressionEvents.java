package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;

import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;

import net.minecraft.core.registries.BuiltInRegistries;
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

        progressionService.recordMiningEvent(
            player.getUUID(),
            level.dimension().location().toString(),
            BuiltInRegistries.BLOCK.getKey(event.getState().getBlock()).toString()
        );
    }
}
