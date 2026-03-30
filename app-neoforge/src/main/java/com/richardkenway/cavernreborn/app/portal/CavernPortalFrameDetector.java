package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public final class CavernPortalFrameDetector {
    public static final int MIN_INNER_WIDTH = 2;
    public static final int MAX_INNER_WIDTH = 21;
    public static final int MIN_INNER_HEIGHT = 3;
    public static final int MAX_INNER_HEIGHT = 21;

    private final FrameAccess frameAccess;

    public CavernPortalFrameDetector(FrameAccess frameAccess) {
        this.frameAccess = Objects.requireNonNull(frameAccess, "frameAccess");
    }

    public Optional<PortalFrame> detect(BlockPos origin) {
        Objects.requireNonNull(origin, "origin");

        BlockPos lowerOrigin = lowerInteriorOrigin(origin);
        for (Direction.Axis axis : new Direction.Axis[] {Direction.Axis.X, Direction.Axis.Z}) {
            Optional<PortalFrame> frame = detect(lowerOrigin, axis);
            if (frame.isPresent()) {
                return frame;
            }
        }

        return Optional.empty();
    }

    private BlockPos lowerInteriorOrigin(BlockPos origin) {
        BlockPos cursor = origin;
        for (int i = 0; i < MAX_INNER_HEIGHT; i++) {
            BlockPos below = cursor.below();
            if (!frameAccess.isInterior(below)) {
                break;
            }

            cursor = below;
        }

        return cursor;
    }

    private Optional<PortalFrame> detect(BlockPos origin, Direction.Axis axis) {
        Direction right = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        Direction left = right.getOpposite();
        BlockPos bottomLeft = findBottomLeft(origin, left);
        if (bottomLeft == null) {
            return Optional.empty();
        }

        int width = findInnerWidth(bottomLeft, right);
        if (width < MIN_INNER_WIDTH || width > MAX_INNER_WIDTH) {
            return Optional.empty();
        }

        int height = findInnerHeight(bottomLeft, right, width);
        if (height < MIN_INNER_HEIGHT || height > MAX_INNER_HEIGHT) {
            return Optional.empty();
        }

        int portalBlockCount = countPortalBlocks(bottomLeft, right, width, height);
        return Optional.of(new PortalFrame(bottomLeft, width, height, axis, portalBlockCount));
    }

    private BlockPos findBottomLeft(BlockPos origin, Direction left) {
        if (!frameAccess.isInterior(origin) || !frameAccess.isFrame(origin.below())) {
            return null;
        }

        BlockPos cursor = origin;
        while (frameAccess.isInterior(cursor.relative(left)) && frameAccess.isFrame(cursor.relative(left).below())) {
            cursor = cursor.relative(left);
        }

        return frameAccess.isFrame(cursor.relative(left)) ? cursor : null;
    }

    private int findInnerWidth(BlockPos bottomLeft, Direction right) {
        int width = 0;
        while (width <= MAX_INNER_WIDTH) {
            BlockPos interior = bottomLeft.relative(right, width);
            if (!frameAccess.isInterior(interior) || !frameAccess.isFrame(interior.below())) {
                return 0;
            }

            width++;
            if (frameAccess.isFrame(bottomLeft.relative(right, width))) {
                return width;
            }
        }

        return 0;
    }

    private int findInnerHeight(BlockPos bottomLeft, Direction right, int width) {
        int height = 0;
        while (height < MAX_INNER_HEIGHT) {
            for (int dx = 0; dx < width; dx++) {
                BlockPos interior = bottomLeft.relative(right, dx).above(height);
                if (!frameAccess.isInterior(interior)) {
                    return 0;
                }
            }

            BlockPos leftWall = bottomLeft.relative(right.getOpposite()).above(height);
            BlockPos rightWall = bottomLeft.relative(right, width).above(height);
            if (!frameAccess.isFrame(leftWall) || !frameAccess.isFrame(rightWall)) {
                return 0;
            }

            height++;
            if (hasTopFrame(bottomLeft, right, width, height)) {
                return height;
            }
        }

        return 0;
    }

    private boolean hasTopFrame(BlockPos bottomLeft, Direction right, int width, int height) {
        for (int dx = -1; dx <= width; dx++) {
            BlockPos top = bottomLeft.relative(right, dx).above(height);
            if (!frameAccess.isFrame(top)) {
                return false;
            }
        }

        return true;
    }

    private int countPortalBlocks(BlockPos bottomLeft, Direction right, int width, int height) {
        int portalBlockCount = 0;
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < height; dy++) {
                if (frameAccess.isPortal(bottomLeft.relative(right, dx).above(dy))) {
                    portalBlockCount++;
                }
            }
        }

        return portalBlockCount;
    }

    public interface FrameAccess {
        boolean isFrame(BlockPos pos);

        boolean isInterior(BlockPos pos);

        boolean isPortal(BlockPos pos);
    }

    public record PortalFrame(
        BlockPos bottomLeft,
        int width,
        int height,
        Direction.Axis axis,
        int portalBlockCount
    ) {
        public PortalFrame {
            bottomLeft = Objects.requireNonNull(bottomLeft, "bottomLeft");
            axis = Objects.requireNonNull(axis, "axis");
        }

        public boolean isEmpty() {
            return portalBlockCount == 0;
        }
    }
}
