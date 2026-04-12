package com.richardkenway.cavernreborn.app.portal;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.richardkenway.cavernreborn.app.block.CavernPortalBlock;
import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;
import com.richardkenway.cavernreborn.app.dimension.OverworldFallbackReturnTargetResolver;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public final class NeoForgeCavernPortalInteractionContext implements CavernPortalInteractionContext, CavernArrivalPlacementProbe {
    private final ServerPlayer serverPlayer;
    private final Level level;
    private final BlockPos portalPosition;
    private final double hitOffsetX;
    private final double hitOffsetY;
    private final double hitOffsetZ;
    private final String approachFacing;
    private final SafeArrivalWorldProbe safeArrivalWorldProbe;

    public NeoForgeCavernPortalInteractionContext(
        ServerPlayer serverPlayer,
        Level level,
        BlockPos portalPosition,
        BlockHitResult hitResult
    ) {
        this.serverPlayer = Objects.requireNonNull(serverPlayer, "serverPlayer");
        this.level = Objects.requireNonNull(level, "level");
        BlockPos touchedPortalPosition = Objects.requireNonNull(portalPosition, "portalPosition");
        BlockHitResult safeHitResult = Objects.requireNonNull(hitResult, "hitResult");
        PortalContactCanonicalizer.CanonicalPortalContact portalContact = PortalContactCanonicalizer.canonicalize(
            new WorldPortalFrameAccess(level, level.getBlockState(touchedPortalPosition).getBlock()),
            touchedPortalPosition,
            safeHitResult.getLocation().x,
            safeHitResult.getLocation().y,
            safeHitResult.getLocation().z,
            fallbackAxis(level, touchedPortalPosition)
        );
        this.portalPosition = portalContact.bottomLeft();
        this.hitOffsetX = portalContact.hitOffsetX();
        this.hitOffsetY = portalContact.hitOffsetY();
        this.hitOffsetZ = portalContact.hitOffsetZ();
        this.approachFacing = safeHitResult.getDirection().getName();
        this.safeArrivalWorldProbe = new SafeArrivalWorldProbe(serverPlayer.serverLevel());
    }

    public static NeoForgeCavernPortalInteractionContext forCollision(
        ServerPlayer serverPlayer,
        Level level,
        BlockPos portalPosition
    ) {
        Objects.requireNonNull(serverPlayer, "serverPlayer");
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(portalPosition, "portalPosition");

        PortalContactCanonicalizer.CanonicalPortalContact portalContact = PortalContactCanonicalizer.canonicalize(
            new WorldPortalFrameAccess(level, level.getBlockState(portalPosition).getBlock()),
            portalPosition,
            serverPlayer.getX(),
            serverPlayer.getY(),
            serverPlayer.getZ(),
            fallbackAxis(level, portalPosition)
        );
        Vec3 deltaMovement = serverPlayer.getDeltaMovement();
        String approachFacing = CollisionApproachFacingResolver.resolve(
            portalContact.axis(),
            deltaMovement.x,
            deltaMovement.z,
            serverPlayer.getDirection()
        );

        return new NeoForgeCavernPortalInteractionContext(
            serverPlayer,
            level,
            portalContact.bottomLeft(),
            portalContact.hitOffsetX(),
            portalContact.hitOffsetY(),
            portalContact.hitOffsetZ(),
            approachFacing
        );
    }

    private NeoForgeCavernPortalInteractionContext(
        ServerPlayer serverPlayer,
        Level level,
        BlockPos portalPosition,
        double hitOffsetX,
        double hitOffsetY,
        double hitOffsetZ,
        String approachFacing
    ) {
        this.serverPlayer = Objects.requireNonNull(serverPlayer, "serverPlayer");
        this.level = Objects.requireNonNull(level, "level");
        this.portalPosition = Objects.requireNonNull(portalPosition, "portalPosition");
        this.hitOffsetX = hitOffsetX;
        this.hitOffsetY = hitOffsetY;
        this.hitOffsetZ = hitOffsetZ;
        this.approachFacing = Objects.requireNonNull(approachFacing, "approachFacing");
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
    public String portalAxis() {
        return level.getBlockState(portalPosition).hasProperty(CavernPortalBlock.AXIS)
            ? axisId(level.getBlockState(portalPosition).getValue(CavernPortalBlock.AXIS))
            : PortalWorldIndex.PortalPlacement.AXIS_X;
    }

    @Override
    public double hitOffsetX() {
        return hitOffsetX;
    }

    @Override
    public double hitOffsetY() {
        return hitOffsetY;
    }

    @Override
    public double hitOffsetZ() {
        return hitOffsetZ;
    }

    @Override
    public String approachFacing() {
        return approachFacing;
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

    private static String axisId(Direction.Axis axis) {
        return axis == Direction.Axis.Z
            ? PortalWorldIndex.PortalPlacement.AXIS_Z
            : PortalWorldIndex.PortalPlacement.AXIS_X;
    }

    private static Direction.Axis fallbackAxis(Level level, BlockPos portalPosition) {
        return level.getBlockState(portalPosition).hasProperty(CavernPortalBlock.AXIS)
            ? level.getBlockState(portalPosition).getValue(CavernPortalBlock.AXIS)
            : Direction.Axis.X;
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
