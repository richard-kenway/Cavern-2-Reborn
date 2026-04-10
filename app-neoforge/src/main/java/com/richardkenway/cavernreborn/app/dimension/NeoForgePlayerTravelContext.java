package com.richardkenway.cavernreborn.app.dimension;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.LinkedHashSet;

import com.richardkenway.cavernreborn.app.portal.CavernPortalFrameDetector;
import com.richardkenway.cavernreborn.app.portal.CavernPortalFrameActivator;
import com.richardkenway.cavernreborn.app.portal.WorldPortalFrameAccess;
import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;

public final class NeoForgePlayerTravelContext implements PlayerTravelContext {
    private static final int PORTAL_INNER_WIDTH = 2;
    private static final int PORTAL_INNER_HEIGHT = 3;
    private static final int PORTAL_CREATE_SEARCH_RADIUS = 4;
    private static final int PORTAL_CREATE_SEARCH_HEIGHT = 2;
    private static final int EXISTING_PORTAL_SEARCH_RADIUS = 8;
    private static final int EXISTING_PORTAL_SEARCH_HEIGHT = 6;

    private final ServerPlayer serverPlayer;

    public NeoForgePlayerTravelContext(ServerPlayer serverPlayer) {
        this.serverPlayer = Objects.requireNonNull(serverPlayer, "serverPlayer");
    }

    @Override
    public java.util.UUID playerId() {
        return serverPlayer.getUUID();
    }

    @Override
    public long gameTime() {
        return serverPlayer.level().getGameTime();
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
    public boolean hasPortalAt(String targetDimensionId, int x, int y, int z) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        return targetLevel.getBlockState(new BlockPos(x, y, z)).is(ModRegistries.CAVERN_PORTAL_BLOCK.get());
    }

    @Override
    public Optional<PortalWorldIndex.PortalPlacement> findPortalNear(String targetDimensionId, int x, int y, int z) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        Block portalBlock = ModRegistries.CAVERN_PORTAL_BLOCK.get();
        CavernPortalFrameDetector detector = new CavernPortalFrameDetector(new WorldPortalFrameAccess(targetLevel, portalBlock));
        Set<BlockPos> checkedOrigins = new LinkedHashSet<>();

        for (int dy = 0; dy <= EXISTING_PORTAL_SEARCH_HEIGHT; dy++) {
            Optional<PortalWorldIndex.PortalPlacement> sameHeight = findPortalAtYOffset(
                targetLevel,
                detector,
                portalBlock,
                x,
                y + dy,
                z,
                checkedOrigins
            );
            if (sameHeight.isPresent()) {
                return sameHeight;
            }

            if (dy == 0) {
                continue;
            }

            Optional<PortalWorldIndex.PortalPlacement> below = findPortalAtYOffset(
                targetLevel,
                detector,
                portalBlock,
                x,
                y - dy,
                z,
                checkedOrigins
            );
            if (below.isPresent()) {
                return below;
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<PortalWorldIndex.PortalPlacement> createPortalAt(String targetDimensionId, int x, int y, int z) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        Block portalBlock = ModRegistries.CAVERN_PORTAL_BLOCK.get();
        WorldPortalFrameAccess frameAccess = new WorldPortalFrameAccess(targetLevel, portalBlock);
        CavernPortalFrameActivator activator = new CavernPortalFrameActivator(frameAccess);
        PortalPlacementQualityScorer.PortalPlacementCandidate bestCandidate = null;

        for (int dy = 0; dy <= PORTAL_CREATE_SEARCH_HEIGHT; dy++) {
            for (int radius = 0; radius <= PORTAL_CREATE_SEARCH_RADIUS; radius++) {
                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        if (radius > 0 && Math.max(Math.abs(dx), Math.abs(dz)) != radius) {
                            continue;
                        }

                        BlockPos candidatePos = new BlockPos(x + dx, y + dy, z + dz);
                        for (Direction.Axis axis : new Direction.Axis[] {Direction.Axis.X, Direction.Axis.Z}) {
                            if (!canBuildPortalAt(targetLevel, portalBlock, candidatePos, axis)) {
                                continue;
                            }

                            PortalPlacementQualityScorer.PortalPlacementCandidate scoredCandidate = PortalPlacementQualityScorer.evaluate(
                                targetLevel,
                                portalBlock,
                                candidatePos,
                                axis,
                                x,
                                y,
                                z
                            );
                            if (PortalPlacementQualityScorer.isBetterCandidate(scoredCandidate, bestCandidate)) {
                                bestCandidate = scoredCandidate;
                            }
                        }
                    }
                }
            }
        }

        if (bestCandidate == null) {
            return Optional.empty();
        }

        return tryCreatePortalAt(bestCandidate.bottomLeft(), bestCandidate.axis(), targetLevel, activator);
    }

    private Optional<PortalWorldIndex.PortalPlacement> findPortalAtYOffset(
        ServerLevel targetLevel,
        CavernPortalFrameDetector detector,
        Block portalBlock,
        int centerX,
        int centerY,
        int centerZ,
        Set<BlockPos> checkedOrigins
    ) {
        for (int radius = 0; radius <= EXISTING_PORTAL_SEARCH_RADIUS; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (radius > 0 && Math.max(Math.abs(dx), Math.abs(dz)) != radius) {
                        continue;
                    }

                    BlockPos candidate = new BlockPos(centerX + dx, centerY, centerZ + dz);
                    if (!targetLevel.getBlockState(candidate).is(portalBlock)) {
                        continue;
                    }

                    if (!checkedOrigins.add(candidate)) {
                        continue;
                    }

                    Optional<CavernPortalFrameDetector.PortalFrame> frame = detector.detect(candidate);
                    if (frame.isPresent() && !frame.get().isEmpty()) {
                        BlockPos bottomLeft = frame.get().bottomLeft();
                        return Optional.of(new PortalWorldIndex.PortalPlacement(
                            bottomLeft.getX(),
                            bottomLeft.getY(),
                            bottomLeft.getZ(),
                            axisId(frame.get().axis())
                        ));
                    }
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public void teleportTo(String targetDimensionId, double x, double y, double z, float yaw, float pitch) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        serverPlayer.teleportTo(targetLevel, x, y, z, EnumSet.noneOf(RelativeMovement.class), yaw, pitch);
    }

    private Optional<PortalWorldIndex.PortalPlacement> tryCreatePortalAt(
        BlockPos bottomLeft,
        Direction.Axis axis,
        ServerLevel targetLevel,
        CavernPortalFrameActivator activator
    ) {
        placeFrame(targetLevel, bottomLeft, axis);
        Optional<com.richardkenway.cavernreborn.app.portal.CavernPortalFrameDetector.PortalFrame> activatedFrame = activator.activate(bottomLeft);
        if (activatedFrame.isPresent()) {
            return Optional.of(new PortalWorldIndex.PortalPlacement(
                bottomLeft.getX(),
                bottomLeft.getY(),
                bottomLeft.getZ(),
                axisId(activatedFrame.get().axis())
            ));
        }

        return Optional.empty();
    }

    private static boolean canBuildPortalAt(ServerLevel level, Block portalBlock, BlockPos bottomLeft, Direction.Axis axis) {
        Direction right = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        Direction left = right.getOpposite();

        for (int dx = 0; dx < PORTAL_INNER_WIDTH; dx++) {
            for (int dy = 0; dy < PORTAL_INNER_HEIGHT; dy++) {
                BlockPos interiorPos = bottomLeft.relative(right, dx).above(dy);
                if (!isInteriorReplaceable(level.getBlockState(interiorPos), portalBlock)) {
                    return false;
                }
            }
        }

        for (int dx = -1; dx <= PORTAL_INNER_WIDTH; dx++) {
            if (!isFrameReplaceable(level.getBlockState(bottomLeft.relative(right, dx).below()))) {
                return false;
            }

            if (!isFrameReplaceable(level.getBlockState(bottomLeft.relative(right, dx).above(PORTAL_INNER_HEIGHT)))) {
                return false;
            }
        }

        for (int dy = 0; dy < PORTAL_INNER_HEIGHT; dy++) {
            if (!isFrameReplaceable(level.getBlockState(bottomLeft.relative(left).above(dy)))) {
                return false;
            }

            if (!isFrameReplaceable(level.getBlockState(bottomLeft.relative(right, PORTAL_INNER_WIDTH).above(dy)))) {
                return false;
            }
        }

        return true;
    }

    private static void placeFrame(ServerLevel level, BlockPos bottomLeft, Direction.Axis axis) {
        Direction right = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        Direction left = right.getOpposite();

        for (int dx = -1; dx <= PORTAL_INNER_WIDTH; dx++) {
            level.setBlock(bottomLeft.relative(right, dx).below(), Blocks.OBSIDIAN.defaultBlockState(), 3);
            level.setBlock(bottomLeft.relative(right, dx).above(PORTAL_INNER_HEIGHT), Blocks.OBSIDIAN.defaultBlockState(), 3);
        }

        for (int dy = 0; dy < PORTAL_INNER_HEIGHT; dy++) {
            level.setBlock(bottomLeft.relative(left).above(dy), Blocks.OBSIDIAN.defaultBlockState(), 3);
            level.setBlock(bottomLeft.relative(right, PORTAL_INNER_WIDTH).above(dy), Blocks.OBSIDIAN.defaultBlockState(), 3);
        }
    }

    private static boolean isInteriorReplaceable(BlockState state, Block portalBlock) {
        return state.isAir() || state.is(portalBlock) || state.canBeReplaced();
    }

    private static boolean isFrameReplaceable(BlockState state) {
        return state.isAir() || state.is(Blocks.OBSIDIAN) || state.canBeReplaced();
    }

    private static String axisId(Direction.Axis axis) {
        return axis == Direction.Axis.Z
            ? PortalWorldIndex.PortalPlacement.AXIS_Z
            : PortalWorldIndex.PortalPlacement.AXIS_X;
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
