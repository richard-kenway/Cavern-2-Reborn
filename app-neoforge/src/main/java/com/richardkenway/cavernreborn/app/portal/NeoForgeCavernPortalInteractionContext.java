package com.richardkenway.cavernreborn.app.portal;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;
import com.richardkenway.cavernreborn.app.dimension.OverworldFallbackReturnTargetResolver;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public final class NeoForgeCavernPortalInteractionContext implements CavernPortalInteractionContext, CavernArrivalPlacementProbe {
    private final ServerPlayer serverPlayer;
    private final Level level;
    private final BlockPos portalPosition;
    private final BlockHitResult hitResult;
    private final SafeArrivalWorldProbe safeArrivalWorldProbe;

    public NeoForgeCavernPortalInteractionContext(
        ServerPlayer serverPlayer,
        Level level,
        BlockPos portalPosition,
        BlockHitResult hitResult
    ) {
        this.serverPlayer = Objects.requireNonNull(serverPlayer, "serverPlayer");
        this.level = Objects.requireNonNull(level, "level");
        this.portalPosition = Objects.requireNonNull(portalPosition, "portalPosition");
        this.hitResult = Objects.requireNonNull(hitResult, "hitResult");
        this.safeArrivalWorldProbe = new SafeArrivalWorldProbe(serverPlayer.serverLevel());
    }

    @Override
    public boolean isClientSide() {
        return level.isClientSide();
    }

    @Override
    public long gameTime() {
        return level.getGameTime();
    }

    @Override
    public void showPortalFeedback(String translationKey) {
        serverPlayer.displayClientMessage(Component.translatable(translationKey), true);
    }

    @Override
    public String currentDimensionId() {
        return level.dimension().location().toString();
    }

    @Override
    public int portalX() {
        return portalPosition.getX();
    }

    @Override
    public int portalY() {
        return portalPosition.getY();
    }

    @Override
    public int portalZ() {
        return portalPosition.getZ();
    }

    @Override
    public double hitOffsetX() {
        return hitResult.getLocation().x - portalPosition.getX();
    }

    @Override
    public double hitOffsetY() {
        return hitResult.getLocation().y - portalPosition.getY();
    }

    @Override
    public double hitOffsetZ() {
        return hitResult.getLocation().z - portalPosition.getZ();
    }

    @Override
    public String approachFacing() {
        return hitResult.getDirection().getName();
    }

    @Override
    public UUID playerId() {
        return serverPlayer.getUUID();
    }

    @Override
    public float yaw() {
        return serverPlayer.getYRot();
    }

    @Override
    public float pitch() {
        return serverPlayer.getXRot();
    }

    @Override
    public Optional<CavernPlacementTarget> fallbackReturnTarget() {
        return new OverworldFallbackReturnTargetResolver(serverPlayer).resolve();
    }

    @Override
    public void teleportTo(String targetDimensionId, double x, double y, double z, float yaw, float pitch) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        serverPlayer.teleportTo(targetLevel, x, y, z, EnumSet.noneOf(RelativeMovement.class), yaw, pitch);
    }

    @Override
    public boolean isSafeArrivalAt(String targetDimensionId, int x, int y, int z) {
        return safeArrivalWorldProbe.isSafeArrivalAt(targetDimensionId, x, y, z);
    }

    private ServerLevel resolveLevel(String targetDimensionId) {
        ResourceLocation resourceLocation = CavernNeoForgeDimensions.resourceLocation(targetDimensionId);
        ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, resourceLocation);
        MinecraftServer server = serverPlayer.getServer();

        if (server == null) {
            throw new IllegalStateException("Server player is not attached to a server");
        }

        ServerLevel targetLevel = server.getLevel(levelKey);
        if (targetLevel == null) {
            throw new IllegalStateException("Missing target dimension: " + targetDimensionId);
        }

        return targetLevel;
    }
}
