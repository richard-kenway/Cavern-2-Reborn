package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionReward;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardClaimResult;
import com.richardkenway.cavernreborn.core.progression.CavernRewardService;
import com.richardkenway.cavernreborn.core.progression.CavernRewardStatus;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class CavernProgressionCommands {
    private static final SimpleCommandExceptionType PLAYER_TARGET_REQUIRED = new SimpleCommandExceptionType(
        Component.literal("Console must specify a player target.")
    );
    private static final DynamicCommandExceptionType UNKNOWN_REWARD = new DynamicCommandExceptionType(
        rewardId -> Component.literal("Unknown CAVERN reward: " + rewardId)
    );

    private final CavernProgressionService progressionService;
    private final CavernRewardService rewardService;
    private final CavernRewardGranter rewardGranter;

    public CavernProgressionCommands(CavernProgressionService progressionService, CavernRewardService rewardService) {
        this(progressionService, rewardService, new CavernRewardGranter());
    }

    CavernProgressionCommands(
        CavernProgressionService progressionService,
        CavernRewardService rewardService,
        CavernRewardGranter rewardGranter
    ) {
        this.progressionService = Objects.requireNonNull(progressionService, "progressionService");
        this.rewardService = Objects.requireNonNull(rewardService, "rewardService");
        this.rewardGranter = Objects.requireNonNull(rewardGranter, "rewardGranter");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("cavern")
                .then(
                    Commands.literal("rank")
                        .executes(context -> showOwnRank(context.getSource()))
                        .then(
                            Commands.argument("player", EntityArgument.player())
                                .requires(source -> source.hasPermission(2))
                                .executes(context -> showRank(
                                    context.getSource(),
                                    EntityArgument.getPlayer(context, "player")
                                ))
                        )
                )
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
                .then(
                    Commands.literal("rewards")
                        .executes(context -> showOwnRewards(context.getSource()))
                        .then(
                            Commands.argument("player", EntityArgument.player())
                                .requires(source -> source.hasPermission(2))
                                .executes(context -> showRewards(
                                    context.getSource(),
                                    EntityArgument.getPlayer(context, "player")
                                ))
                        )
                )
                .then(
                    Commands.literal("claim")
                        .then(
                            Commands.argument("reward", StringArgumentType.word())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(
                                    java.util.Arrays.stream(CavernProgressionReward.values()).map(CavernProgressionReward::id),
                                    builder
                                ))
                                .executes(context -> claimOwnReward(
                                    context.getSource(),
                                    StringArgumentType.getString(context, "reward")
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

    private int showOwnRank(CommandSourceStack source) throws CommandSyntaxException {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            throw PLAYER_TARGET_REQUIRED.create();
        }
        return showRank(source, player);
    }

    private int showOwnRewards(CommandSourceStack source) throws CommandSyntaxException {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            throw PLAYER_TARGET_REQUIRED.create();
        }
        return showRewards(source, player);
    }

    private int claimOwnReward(CommandSourceStack source, String rewardId) throws CommandSyntaxException {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            throw PLAYER_TARGET_REQUIRED.create();
        }
        CavernProgressionReward reward = CavernProgressionReward.findById(rewardId)
            .orElseThrow(() -> UNKNOWN_REWARD.create(rewardId));
        CavernProgressionSnapshot snapshot = progressionService.inspect(player.getUUID());
        CavernRewardClaimResult claimResult = rewardService.claim(snapshot, reward);
        if (claimResult.claimed()) {
            rewardGranter.grant(player, reward);
            source.sendSuccess(() -> Component.literal(CavernRewardClaimFeedbackFormatter.format(claimResult)), false);
            return 1;
        }

        source.sendFailure(Component.literal(CavernRewardClaimFeedbackFormatter.format(claimResult)));
        return 0;
    }

    private int showRank(CommandSourceStack source, ServerPlayer player) {
        CavernProgressionSnapshot snapshot = progressionService.inspect(player.getUUID());
        source.sendSuccess(
            () -> Component.literal(CavernPlayerProgressionStatusFormatter.format(player.getGameProfile().getName(), snapshot)),
            false
        );
        return snapshot.progressionScore();
    }

    private int showRewards(CommandSourceStack source, ServerPlayer player) {
        CavernProgressionSnapshot snapshot = progressionService.inspect(player.getUUID());
        java.util.List<CavernRewardStatus> rewardStatuses = rewardService.inspect(snapshot);
        source.sendSuccess(
            () -> Component.literal(CavernPlayerRewardStatusFormatter.format(player.getGameProfile().getName(), snapshot, rewardStatuses)),
            false
        );
        return rewardStatuses.stream().mapToInt(status -> status.availableToClaim() ? 1 : 0).sum();
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
