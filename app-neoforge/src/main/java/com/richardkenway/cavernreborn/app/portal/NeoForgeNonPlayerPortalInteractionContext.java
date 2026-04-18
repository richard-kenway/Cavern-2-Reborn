package com.richardkenway.cavernreborn.app.portal;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.richardkenway.cavernreborn.app.block.CavernPortalBlock;
import com.richardkenway.cavernreborn.app.config.CavernPortalSettings;
import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;
import com.richardkenway.cavernreborn.app.dimension.PortalPlacementQualityScorer;
import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.core.state.PortalEntryReceipt;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public final class NeoForgeNonPlayerPortalInteractionContext implements NonPlayerPortalInteractionContext {
    private static final int PORTAL_INNER_WIDTH = 2;
    private static final int PORTAL_INNER_HEIGHT = 3;

    private final Entity entity;
    private final Level sourceLevel;
    private final BlockPos portalPosition;
    private final double hitOffsetX;
    private final double hitOffsetY;
    private final double hitOffsetZ;
    private final String approachFacing;
    private final SafeArrivalWorldProbe safeArrivalWorldProbe;

    public NeoForgeNonPlayerPortalInteractionContext(
        Entity entity,
        Level level,
        BlockPos portalPosition,
        double hitOffsetX,
        double hitOffsetY,
        double hitOffsetZ,
        String approachFacing
    ) {
        this.entity = Objects.requireNonNull(entity, "entity");
        this.sourceLevel = Objects.requireNonNull(level, "level");
        this.portalPosition = Objects.requireNonNull(portalPosition, "portalPosition");
        this.hitOffsetX = hitOffsetX;
        this.hitOffsetY = hitOffsetY;
        this.hitOffsetZ = hitOffsetZ;
        this.approachFacing = Objects.requireNonNull(approachFacing, "approachFacing");
        this.safeArrivalWorldProbe = new SafeArrivalWorldProbe(toServerLevel(entity.level()));
    }

    public static NeoForgeNonPlayerPortalInteractionContext forCollision(Entity entity, Level level, BlockPos portalPosition) {
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(portalPosition, "portalPosition");

        PortalContactCanonicalizer.CanonicalPortalContact portalContact = PortalContactCanonicalizer.canonicalize(
            new WorldPortalFrameAccess(level, level.getBlockState(portalPosition).getBlock()),
            portalPosition,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            fallbackAxis(level, portalPosition)
        );
        String approachFacing = CollisionApproachFacingResolver.resolve(
            portalContact.axis(),
            entity.getDeltaMovement().x,
            entity.getDeltaMovement().z,
            entity.getDirection()
        );

        return new NeoForgeNonPlayerPortalInteractionContext(
            entity,
            level,
            portalContact.bottomLeft(),
            portalContact.hitOffsetX(),
            portalContact.hitOffsetY(),
            portalContact.hitOffsetZ(),
            approachFacing
        );
    }

    @Override
    public boolean isClientSide() {
        return sourceLevel.isClientSide();
    }

    @Override
    public long gameTime() {
        return sourceLevel.getGameTime();
    }

    @Override
    public void showPortalFeedback(String translationKey) {
    }

    @Override
    public String currentDimensionId() {
        return sourceLevel.dimension().location().toString();
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
        return sourceLevel.getBlockState(portalPosition).hasProperty(CavernPortalBlock.AXIS)
            ? axisId(sourceLevel.getBlockState(portalPosition).getValue(CavernPortalBlock.AXIS))
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
        return entity.getUUID();
    }

    @Override
    public float yaw() {
        return entity.getYRot();
    }

    @Override
    public float pitch() {
        return entity.getXRot();
    }

    @Override
    public boolean hasPortalAt(String targetDimensionId, int x, int y, int z) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        return targetLevel.getBlockState(new BlockPos(x, y, z)).is(ModRegistries.CAVERN_PORTAL_BLOCK.get());
    }

    @Override
    public Optional<PortalWorldIndex.PortalPlacement> resolvePortalAt(String targetDimensionId, int x, int y, int z) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        Block portalBlock = ModRegistries.CAVERN_PORTAL_BLOCK.get();
        BlockPos portalPos = new BlockPos(x, y, z);
        if (!targetLevel.getBlockState(portalPos).is(portalBlock)) {
            return Optional.empty();
        }

        WorldPortalFrameAccess portalFrameAccess = new WorldPortalFrameAccess(
            targetLevel,
            portalBlock,
            frameMaterialPolicy()
        );
        CavernPortalFrameDetector detector = new CavernPortalFrameDetector(portalFrameAccess);
        return detector.detect(portalPos)
            .filter(frame -> !frame.isEmpty())
            .map(frame -> new PortalWorldIndex.PortalPlacement(
                frame.bottomLeft().getX(),
                frame.bottomLeft().getY(),
                frame.bottomLeft().getZ(),
                axisId(frame.axis())
            ));
    }

    @Override
    public Optional<PortalWorldIndex.PortalPlacement> findPortalNear(String targetDimensionId, int x, int y, int z) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        Block portalBlock = ModRegistries.CAVERN_PORTAL_BLOCK.get();
        WorldPortalFrameAccess portalFrameAccess = new WorldPortalFrameAccess(
            targetLevel,
            portalBlock,
            frameMaterialPolicy()
        );
        CavernPortalFrameDetector detector = new CavernPortalFrameDetector(portalFrameAccess);
        Set<BlockPos> checkedOrigins = new LinkedHashSet<>();
        int searchHeight = settings().findPortalVerticalRange();

        for (int dy = 0; dy <= searchHeight; dy++) {
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
        CavernPortalSettings settings = settings();
        return searchBestPortalCandidate(
            targetLevel,
            x,
            y,
            z,
            settings.findPortalRange(),
            settings.findPortalVerticalRange(),
            Optional.empty()
        ).flatMap(candidate -> tryCreatePortalAt(
            candidate.bottomLeft(),
            candidate.axis(),
            targetLevel,
            new CavernPortalFrameActivator(
                new WorldPortalFrameAccess(
                    targetLevel,
                    ModRegistries.CAVERN_PORTAL_BLOCK.get(),
                    frameMaterialPolicy()
                )
            )
        ));
    }

    @Override
    public Optional<PortalWorldIndex.PortalPlacement> createReplacementPortalAt(
        String targetDimensionId,
        PortalWorldIndex.PortalPlacement stalePlacement
    ) {
        Objects.requireNonNull(stalePlacement, "stalePlacement");
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        CavernPortalSettings settings = settings();

        return searchBestPortalCandidate(
            targetLevel,
            stalePlacement.x(),
            stalePlacement.y(),
            stalePlacement.z(),
            settings.findPortalRange(),
            settings.findPortalVerticalRange(),
            axisFromId(stalePlacement.axis())
        ).flatMap(candidate -> tryCreatePortalAt(
            candidate.bottomLeft(),
            candidate.axis(),
            targetLevel,
            new CavernPortalFrameActivator(
                new WorldPortalFrameAccess(
                    targetLevel,
                    ModRegistries.CAVERN_PORTAL_BLOCK.get(),
                    frameMaterialPolicy()
                )
            )
        ));
    }

    @Override
    public void teleportTo(String targetDimensionId, double x, double y, double z, float yaw, float pitch) {
        ServerLevel targetLevel = resolveLevel(targetDimensionId);
        if (entity.level() == targetLevel) {
            entity.teleportTo(targetLevel, x, y, z, EnumSet.noneOf(RelativeMovement.class), yaw, pitch);
            return;
        }

        entity.changeDimension(
            new DimensionTransition(
                targetLevel,
                new Vec3(x, y, z),
                entity.getDeltaMovement(),
                yaw,
                pitch,
                DimensionTransition.PLACE_PORTAL_TICKET
            )
        );
    }

    @Override
    public boolean isSafeArrivalAt(String targetDimensionId, int x, int y, int z) {
        return safeArrivalWorldProbe.isSafeArrivalAt(targetDimensionId, x, y, z);
    }

    @Override
    public Optional<PortalEntryReceipt> loadPortalEntryReceipt() {
        return PortalEntryReceiptStateMapper.load(entity.getPersistentData());
    }

    @Override
    public void savePortalEntryReceipt(PortalEntryReceipt entryReceipt) {
        PortalEntryReceiptStateMapper.save(entity.getPersistentData(), entryReceipt);
    }

    @Override
    public void clearPortalEntryReceipt() {
        PortalEntryReceiptStateMapper.clear(entity.getPersistentData());
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
        MinecraftServer server = entity.getServer();

        if (server == null) {
            throw new IllegalStateException("Server entity is not attached to a server");
        }

        ServerLevel targetLevel = server.getLevel(levelKey);
        if (targetLevel == null) {
            throw new IllegalStateException("Missing target dimension: " + targetDimensionId);
        }

        return targetLevel;
    }

    private static ServerLevel toServerLevel(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel;
        }

        throw new IllegalStateException("Entity is not in a server-level");
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
        int searchRadius = settings().findPortalRange();
        for (int radius = 0; radius <= searchRadius; radius++) {
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

    private Optional<PortalPlacementQualityScorer.PortalPlacementCandidate> searchBestPortalCandidate(
        ServerLevel targetLevel,
        int x,
        int y,
        int z,
        int searchRadius,
        int searchHeight,
        Optional<Direction.Axis> preferredAxis
    ) {
        Block portalBlock = ModRegistries.CAVERN_PORTAL_BLOCK.get();
        Block generatedFrameBlock = generatedFrameBlock();
        PortalPlacementQualityScorer.PortalPlacementCandidate bestCandidate = null;

        for (int dy = 0; dy <= searchHeight; dy++) {
            bestCandidate = evaluatePlacementCandidateAtY(
                targetLevel,
                portalBlock,
                generatedFrameBlock,
                x,
                y,
                y + dy,
                z,
                searchRadius,
                preferredAxis,
                bestCandidate
            );
            if (dy == 0) {
                continue;
            }

            bestCandidate = evaluatePlacementCandidateAtY(
                targetLevel,
                portalBlock,
                generatedFrameBlock,
                x,
                y,
                y - dy,
                z,
                searchRadius,
                preferredAxis,
                bestCandidate
            );
        }

        return Optional.ofNullable(bestCandidate);
    }

    private PortalPlacementQualityScorer.PortalPlacementCandidate evaluatePlacementCandidateAtY(
        ServerLevel targetLevel,
        Block portalBlock,
        Block frameBlock,
        int x,
        int targetY,
        int candidateY,
        int z,
        int searchRadius,
        Optional<Direction.Axis> preferredAxis,
        PortalPlacementQualityScorer.PortalPlacementCandidate currentBest
    ) {
        PortalPlacementQualityScorer.PortalPlacementCandidate bestCandidate = currentBest;

        for (int radius = 0; radius <= searchRadius; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (radius > 0 && Math.max(Math.abs(dx), Math.abs(dz)) != radius) {
                        continue;
                    }

                    BlockPos candidatePos = new BlockPos(x + dx, candidateY, z + dz);
                    for (Direction.Axis axis : orderedAxes(preferredAxis)) {
                        if (!canBuildPortalAt(targetLevel, portalBlock, candidatePos, axis)) {
                            continue;
                        }

                        PortalPlacementQualityScorer.PortalPlacementCandidate scoredCandidate = PortalPlacementQualityScorer.evaluate(
                            targetLevel,
                            frameBlock,
                            portalBlock,
                            candidatePos,
                            axis,
                            x,
                            targetY,
                            z
                        );
                        if (PortalPlacementQualityScorer.isBetterCandidate(
                            scoredCandidate,
                            bestCandidate,
                            preferredAxis.orElse(null)
                        )) {
                            bestCandidate = scoredCandidate;
                        }
                    }
                }
            }
        }

        return bestCandidate;
    }

    private Optional<PortalWorldIndex.PortalPlacement> tryCreatePortalAt(
        BlockPos bottomLeft,
        Direction.Axis axis,
        ServerLevel targetLevel,
        CavernPortalFrameActivator activator
    ) {
        placeFrame(targetLevel, bottomLeft, axis);
        Optional<CavernPortalFrameDetector.PortalFrame> activatedFrame = activator.activate(bottomLeft);
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
        Block generatedFrameBlock = generatedFrameBlock();

        for (int dx = -1; dx <= PORTAL_INNER_WIDTH; dx++) {
            level.setBlock(bottomLeft.relative(right, dx).below(), generatedFrameBlock.defaultBlockState(), 3);
            level.setBlock(bottomLeft.relative(right, dx).above(PORTAL_INNER_HEIGHT), generatedFrameBlock.defaultBlockState(), 3);
        }

        for (int dy = 0; dy < PORTAL_INNER_HEIGHT; dy++) {
            level.setBlock(bottomLeft.relative(left).above(dy), generatedFrameBlock.defaultBlockState(), 3);
            level.setBlock(bottomLeft.relative(right, PORTAL_INNER_WIDTH).above(dy), generatedFrameBlock.defaultBlockState(), 3);
        }
    }

    private static boolean isInteriorReplaceable(BlockState state, Block portalBlock) {
        return WorldPortalFrameAccess.isInteriorState(state, portalBlock);
    }

    private static boolean isFrameReplaceable(BlockState state) {
        return PortalFrameMaterialPolicy.CAVERN_DEFAULT.isFrame(state) || state.canBeReplaced();
    }

    private static Direction.Axis[] orderedAxes(Optional<Direction.Axis> preferredAxis) {
        if (preferredAxis.isEmpty()) {
            return new Direction.Axis[] {Direction.Axis.X, Direction.Axis.Z};
        }

        return preferredAxis.get() == Direction.Axis.X
            ? new Direction.Axis[] {Direction.Axis.X, Direction.Axis.Z}
            : new Direction.Axis[] {Direction.Axis.Z, Direction.Axis.X};
    }

    private static Optional<Direction.Axis> axisFromId(String axisId) {
        return PortalWorldIndex.PortalPlacement.AXIS_Z.equals(axisId)
            ? Optional.of(Direction.Axis.Z)
            : Optional.of(Direction.Axis.X);
    }

    private static CavernPortalSettings settings() {
        return CavernPortalSettings.get();
    }

    private static PortalFrameMaterialPolicy frameMaterialPolicy() {
        return PortalFrameMaterialPolicy.CAVERN_DEFAULT;
    }

    private static Block generatedFrameBlock() {
        return CavernPortalTags.defaultGeneratedFrameBlock();
    }
}
