package com.richardkenway.cavernreborn.app.state;

import java.util.Optional;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

final class CavernPersistentStateAccess {
    private CavernPersistentStateAccess() {
    }

    static Optional<CavernPersistentStateData> resolve() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return Optional.empty();
        }

        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld == null) {
            return Optional.empty();
        }

        return Optional.of(overworld.getDataStorage().computeIfAbsent(CavernPersistentStateData.factory(), CavernPersistentStateData.FILE_ID));
    }
}
