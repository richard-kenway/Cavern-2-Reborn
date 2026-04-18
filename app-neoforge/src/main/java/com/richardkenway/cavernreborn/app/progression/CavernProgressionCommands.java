package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class CavernProgressionCommands {
    private static final SimpleCommandExceptionType PLAYER_TARGET_REQUIRED = new SimpleCommandExceptionType(
        Component.literal("Console must specify a player target.")
    );

    private final CavernProgressionService progressionService;

    public CavernProgressionCommands(CavernProgressionService progressionService) {
        this.progressionService = Objects.requireNonNull(progressionService, "progressionService");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("cavern")
                .then(
                    Commands.literal("progression")
                        .executes(context -> showOwnProgression(context.getSource()))
                        .then(
                            Commands.argument("player", EntityArgument.player())
                                .requires(source -> source.hasPermission(2))
                                .executes(context -> showProgression(
                                    context.getSource(),
                                    EntityArgument.getPlayer(context, "player")
                                ))
                        )
                )
        );
    }

    private int showOwnProgression(CommandSourceStack source) throws CommandSyntaxException {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            throw PLAYER_TARGET_REQUIRED.create();
        }
        return showProgression(source, player);
    }

    private int showProgression(CommandSourceStack source, ServerPlayer player) {
        CavernProgressionSnapshot snapshot = progressionService.inspect(player.getUUID());
        source.sendSuccess(
            () -> Component.literal(CavernProgressionDebugSummaryFormatter.format(player.getGameProfile().getName(), snapshot)),
            false
        );
        return snapshot.progressionScore();
    }
}
