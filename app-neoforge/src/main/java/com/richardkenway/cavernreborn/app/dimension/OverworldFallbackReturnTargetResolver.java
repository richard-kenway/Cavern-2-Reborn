package com.richardkenway.cavernreborn.app.dimension;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public final class OverworldFallbackReturnTargetResolver {
    private final ServerPlayer serverPlayer;

    public OverworldFallbackReturnTargetResolver(ServerPlayer serverPlayer) {
        this.serverPlayer = Objects.requireNonNull(serverPlayer, "serverPlayer");
    }

    public Optional<CavernPlacementTarget> resolve() {
        MinecraftServer server = serverPlayer.getServer();
        if (server == null) {
            return Optional.empty();
        }

        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) {
            return Optional.empty();
        }

        BlockPos sharedSpawn = overworld.getSharedSpawnPos();
        return Optional.of(new CavernPlacementTarget(
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            sharedSpawn.getX(),
            sharedSpawn.getY(),
            sharedSpawn.getZ()
        ));
    }
}
