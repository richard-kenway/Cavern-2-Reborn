package com.richardkenway.cavernreborn.app.progression;

import java.util.Objects;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.richardkenway.cavernreborn.core.progression.CavernInteractionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionReward;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardClaimResult;
import com.richardkenway.cavernreborn.core.progression.CavernRewardService;
import com.richardkenway.cavernreborn.core.progression.CavernRewardStatus;
import com.richardkenway.cavernreborn.core.progression.CavernServiceAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernServiceEntry;
import com.richardkenway.cavernreborn.core.progression.CavernServiceRequestResult;
import com.richardkenway.cavernreborn.core.progression.CavernServiceStatus;
import com.richardkenway.cavernreborn.core.progression.PlayerServiceStateStore;

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
    private static final DynamicCommandExceptionType UNKNOWN_SERVICE = new DynamicCommandExceptionType(
        serviceId -> Component.literal("Unknown CAVERN service: " + serviceId)
    );

    private final CavernProgressionService progressionService;
    private final CavernRewardService rewardService;
    private final CavernRewardGranter rewardGranter;
    private final CavernInteractionService interactionService;
    private final PlayerServiceStateStore serviceStateStore;

    public CavernProgressionCommands(CavernProgressionService progressionService, CavernRewardService rewardService) {
        this(progressionService, rewardService, new CavernRewardGranter(), null, null);
    }

    public CavernProgressionCommands(
        CavernProgressionService progressionService,
        CavernRewardService rewardService,
        CavernRewardGranter rewardGranter,
        CavernInteractionService interactionService,
        PlayerServiceStateStore serviceStateStore
    ) {
        this.progressionService = Objects.requireNonNull(progressionService, "progressionService");
        this.rewardService = Objects.requireNonNull(rewardService, "rewardService");
        this.rewardGranter = Objects.requireNonNull(rewardGranter, "rewardGranter");
        this.interactionService = Objects.requireNonNull(interactionService, "interactionService");
        this.serviceStateStore = Objects.requireNonNull(serviceStateStore, "serviceStateStore");
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
                    Commands.literal("services")
                        .executes(context -> showOwnServices(context.getSource()))
                        .then(
                            Commands.argument("player", EntityArgument.player())
                                .requires(source -> source.hasPermission(2))
                                .executes(context -> showServices(
                                    context.getSource(),
                                    EntityArgument.getPlayer(context, "player")
                                ))
                        )
                )
                .then(
                    Commands.literal("request")
                        .then(
                            Commands.argument("service", StringArgumentType.word())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(
                                    java.util.Arrays.stream(CavernServiceEntry.values()).map(CavernServiceEntry::id),
                                    builder
                                ))
                                .executes(context -> requestOwnService(
                                    context.getSource(),
                                    StringArgumentType.getString(context, "service")
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

    private int showOwnServices(CommandSourceStack source) throws CommandSyntaxException {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            throw PLAYER_TARGET_REQUIRED.create();
        }
        return showServices(source, player);
    }

    private int showServices(CommandSourceStack source, ServerPlayer player) {
        long currentTimeMillis = player.getServer().getTickCount() * 50L;
        CavernProgressionSnapshot snapshot = progressionService.inspect(player.getUUID());
        java.util.List<CavernServiceStatus> serviceStatuses = interactionService.inspectServices(snapshot, currentTimeMillis);
        com.richardkenway.cavernreborn.core.progression.CavernPlayerServiceState serviceState = serviceStateStore.load(player.getUUID());
        source.sendSuccess(
            () -> Component.literal(CavernPlayerServiceStatusFormatter.format(
                player.getGameProfile().getName(), snapshot, serviceStatuses, serviceState, currentTimeMillis
            )),
            false
        );
        return serviceStatuses.stream().mapToInt(s -> s.availableToUse() ? 1 : 0).sum();
    }

    private int requestOwnService(CommandSourceStack source, String serviceId) throws CommandSyntaxException {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            throw PLAYER_TARGET_REQUIRED.create();
        }
        CavernServiceEntry service = CavernServiceEntry.findById(serviceId);
        if (service == null) {
            throw UNKNOWN_SERVICE.create(serviceId);
        }
        long currentTimeMillis = player.getServer().getTickCount() * 50L;
        CavernProgressionSnapshot snapshot = progressionService.inspect(player.getUUID());
        CavernServiceRequestResult result = interactionService.requestService(snapshot, service, currentTimeMillis);
        if (result.granted()) {
            rewardGranter.grantService(player, service);
            source.sendSuccess(() -> Component.literal(CavernServiceRequestFeedbackFormatter.format(result)), false);
            return 1;
        }
        source.sendFailure(Component.literal(CavernServiceRequestFeedbackFormatter.format(result)));
        return 0;
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
