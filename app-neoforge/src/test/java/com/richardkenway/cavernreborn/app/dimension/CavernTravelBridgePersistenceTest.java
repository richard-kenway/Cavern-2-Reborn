package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.portal.CavernArrivalPlacementProbe;
import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.app.state.CavernStateBootstrap;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;
import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;
import com.richardkenway.cavernreborn.core.state.PlayerReturnStateStore;
import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;
import com.richardkenway.cavernreborn.core.state.WorldPortalIndexStore;

import net.minecraft.nbt.CompoundTag;

class CavernTravelBridgePersistenceTest {
    @Test
    void returnHomeStillUsesPersistedReturnStateAndWorldIndexAfterRestart() {
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernStateBootstrap firstBootstrap = bootstrapFor(persistentState);
        UUID playerId = UUID.randomUUID();

        Optional<CavernTravelPlan> entryPlan = firstBootstrap.cavernTravelBridge().travelToCavern(
            new FakePlayerTravelContext(
                playerId,
                100L,
                45.0F,
                15.0F,
                Set.of(new SafeArrival(0, 64, 0)),
                Optional.empty(),
                Optional.of(new PortalWorldIndex.PortalPlacement(2, 64, 0, PortalWorldIndex.PortalPlacement.AXIS_X))
            ),
            new PortalReturnState("portal-a", CavernDimensions.OVERWORLD_DIMENSION_ID, 11, 70, 11),
            new TeleportContext("portal-a", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(11, 70, 11, PortalWorldIndex.PortalPlacement.AXIS_X)
        );

        assertTrue(entryPlan.isPresent());

        CavernStateBootstrap restartedBootstrap = bootstrapFor(restart(persistentState));
        FakePlayerTravelContext returnContext = new FakePlayerTravelContext(
            playerId,
            140L,
            45.0F,
            15.0F,
            Set.of(new SafeArrival(0, 64, 0))
        );
        returnContext.existingPortals.add(new PortalLocation(
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            11,
            70,
            11,
            PortalWorldIndex.PortalPlacement.AXIS_X
        ));

        Optional<CavernTravelPlan> returnPlan = restartedBootstrap.cavernTravelBridge().returnHome(returnContext);

        assertTrue(returnPlan.isPresent());
        assertEquals(CavernDimensions.OVERWORLD_DIMENSION_ID, returnContext.lastTargetDimensionId);
        assertEquals(12.0D, returnContext.lastX);
        assertEquals(70.0D, returnContext.lastY);
        assertEquals(11.5D, returnContext.lastZ);
    }

    @Test
    void relinkStillWorksAgainstPersistedPortalIndexAfterRestart() {
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        persistentState.saveWorldPortalIndex(
            CavernDimensions.CAVERN_DIMENSION_ID,
            PortalWorldIndex.empty().withPortal(
                "cavern",
                new PortalWorldIndex.PortalPlacement(30, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z)
            )
        );

        CavernStateBootstrap restartedBootstrap = bootstrapFor(restart(persistentState));
        FakePlayerTravelContext player = new FakePlayerTravelContext(
            UUID.randomUUID(),
            200L,
            90.0F,
            30.0F,
            Set.of(new SafeArrival(0, 64, 0))
        );
        player.existingPortals.add(new PortalLocation(
            CavernDimensions.CAVERN_DIMENSION_ID,
            31,
            70,
            30,
            PortalWorldIndex.PortalPlacement.AXIS_Z
        ));

        Optional<CavernTravelPlan> plan = restartedBootstrap.cavernTravelBridge().travelToCavern(
            player,
            new PortalReturnState("cavern", CavernDimensions.OVERWORLD_DIMENSION_ID, 12, 64, 12),
            new TeleportContext("cavern", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(8, 70, 8)
        );

        assertTrue(plan.isPresent());
        assertEquals(
            new PortalWorldIndex.PortalPlacement(31, 70, 30, PortalWorldIndex.PortalPlacement.AXIS_Z),
            restartedBootstrap.worldPortalIndexStore().load(CavernDimensions.CAVERN_DIMENSION_ID).firstPlacementFor("cavern").orElseThrow()
        );
        assertEquals(0, player.createPortalCalls);
        assertEquals(0, player.createReplacementPortalCalls);
    }

    @Test
    void failedSafeArrivalDoesNotPersistBrokenPortalState() {
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernStateBootstrap bootstrap = bootstrapFor(persistentState);
        UUID playerId = UUID.randomUUID();

        Optional<CavernTravelPlan> plan = bootstrap.cavernTravelBridge().travelToCavern(
            new FakePlayerTravelContext(playerId, 300L, 90.0F, 30.0F, Set.of()),
            new PortalReturnState("portal-a", CavernDimensions.OVERWORLD_DIMENSION_ID, 11, 70, 11),
            new TeleportContext("portal-a", 0.25D, 0.5D, 0.75D, "north"),
            new PortalWorldIndex.PortalPlacement(11, 70, 11, PortalWorldIndex.PortalPlacement.AXIS_X)
        );

        assertTrue(plan.isEmpty());
        assertTrue(persistentState.loadPlayerReturnState(playerId).isEmpty());
        assertEquals(PortalWorldIndex.empty(), persistentState.loadWorldPortalIndex(CavernDimensions.OVERWORLD_DIMENSION_ID));
    }

    private static CavernStateBootstrap bootstrapFor(CavernPersistentStateData persistentState) {
        return new CavernStateBootstrap(
            new PersistentPlayerReturnStateStore(persistentState),
            new PersistentWorldPortalIndexStore(persistentState)
        );
    }

    private static CavernPersistentStateData restart(CavernPersistentStateData persistentState) {
        CompoundTag serialized = persistentState.save(new CompoundTag(), null);
        try {
            Method loadMethod = CavernPersistentStateData.class.getDeclaredMethod("load", CompoundTag.class, net.minecraft.core.HolderLookup.Provider.class);
            loadMethod.setAccessible(true);
            return (CavernPersistentStateData) loadMethod.invoke(null, serialized, null);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("Failed to restore persistent state snapshot", exception);
        }
    }

    private static final class PersistentPlayerReturnStateStore implements PlayerReturnStateStore {
        private final CavernPersistentStateData persistentState;

        private PersistentPlayerReturnStateStore(CavernPersistentStateData persistentState) {
            this.persistentState = persistentState;
        }

        @Override
        public Optional<PortalReturnState> load(UUID playerId) {
            return persistentState.loadPlayerReturnState(playerId);
        }

        @Override
        public void save(UUID playerId, PortalReturnState returnState) {
            persistentState.savePlayerReturnState(playerId, returnState);
        }

        @Override
        public void clear(UUID playerId) {
            persistentState.clearPlayerReturnState(playerId);
        }
    }

    private static final class PersistentWorldPortalIndexStore implements WorldPortalIndexStore {
        private final CavernPersistentStateData persistentState;

        private PersistentWorldPortalIndexStore(CavernPersistentStateData persistentState) {
            this.persistentState = persistentState;
        }

        @Override
        public PortalWorldIndex load(String worldKey) {
            return persistentState.loadWorldPortalIndex(worldKey);
        }

        @Override
        public void save(String worldKey, PortalWorldIndex worldIndex) {
            persistentState.saveWorldPortalIndex(worldKey, worldIndex);
        }
    }

    private static final class FakePlayerTravelContext implements PlayerTravelContext, CavernArrivalPlacementProbe {
        private final UUID playerId;
        private final long gameTime;
        private final float yaw;
        private final float pitch;
        private final Set<SafeArrival> safeArrivals;
        private final Optional<CavernPlacementTarget> fallbackReturnTarget;
        private final Optional<PortalWorldIndex.PortalPlacement> createdPortalPlacement;
        private final Set<PortalLocation> existingPortals = new HashSet<>();
        private int createPortalCalls;
        private int createReplacementPortalCalls;
        private String lastTargetDimensionId;
        private double lastX;
        private double lastY;
        private double lastZ;

        private FakePlayerTravelContext(UUID playerId, long gameTime, float yaw, float pitch, Set<SafeArrival> safeArrivals) {
            this(playerId, gameTime, yaw, pitch, safeArrivals, Optional.empty(), Optional.empty());
        }

        private FakePlayerTravelContext(
            UUID playerId,
            long gameTime,
            float yaw,
            float pitch,
            Set<SafeArrival> safeArrivals,
            Optional<CavernPlacementTarget> fallbackReturnTarget,
            Optional<PortalWorldIndex.PortalPlacement> createdPortalPlacement
        ) {
            this.playerId = playerId;
            this.gameTime = gameTime;
            this.yaw = yaw;
            this.pitch = pitch;
            this.safeArrivals = safeArrivals;
            this.fallbackReturnTarget = fallbackReturnTarget;
            this.createdPortalPlacement = createdPortalPlacement;
        }

        @Override
        public UUID playerId() {
            return playerId;
        }

        @Override
        public long gameTime() {
            return gameTime;
        }

        @Override
        public float yaw() {
            return yaw;
        }

        @Override
        public float pitch() {
            return pitch;
        }

        @Override
        public Optional<CavernPlacementTarget> fallbackReturnTarget() {
            return fallbackReturnTarget;
        }

        @Override
        public boolean hasPortalAt(String targetDimensionId, int x, int y, int z) {
            return existingPortals.stream()
                .anyMatch(portal -> portal.dimensionId().equals(targetDimensionId)
                    && portal.x() == x
                    && portal.y() == y
                    && portal.z() == z);
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> resolvePortalAt(String targetDimensionId, int x, int y, int z) {
            return existingPortals.stream()
                .filter(portal -> portal.dimensionId().equals(targetDimensionId))
                .filter(portal -> portal.x() == x)
                .filter(portal -> portal.y() == y)
                .filter(portal -> portal.z() == z)
                .findFirst()
                .map(portal -> new PortalWorldIndex.PortalPlacement(portal.x(), portal.y(), portal.z(), portal.axis()));
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> findPortalNear(String targetDimensionId, int x, int y, int z) {
            return existingPortals.stream()
                .filter(portal -> portal.dimensionId().equals(targetDimensionId))
                .filter(portal -> Math.abs(portal.x() - x) <= 8)
                .filter(portal -> Math.abs(portal.y() - y) <= 6)
                .filter(portal -> Math.abs(portal.z() - z) <= 8)
                .findFirst()
                .map(portal -> new PortalWorldIndex.PortalPlacement(portal.x(), portal.y(), portal.z(), portal.axis()));
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> createPortalAt(String targetDimensionId, int x, int y, int z) {
            createPortalCalls += 1;
            createdPortalPlacement.ifPresent(placement -> existingPortals.add(new PortalLocation(
                targetDimensionId,
                placement.x(),
                placement.y(),
                placement.z(),
                placement.axis()
            )));
            return createdPortalPlacement;
        }

        @Override
        public Optional<PortalWorldIndex.PortalPlacement> createReplacementPortalAt(
            String targetDimensionId,
            PortalWorldIndex.PortalPlacement stalePlacement
        ) {
            createReplacementPortalCalls += 1;
            return Optional.empty();
        }

        @Override
        public void teleportTo(String targetDimensionId, double x, double y, double z, float yaw, float pitch) {
            this.lastTargetDimensionId = targetDimensionId;
            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;
        }

        @Override
        public boolean isSafeArrivalAt(String targetDimensionId, int x, int y, int z) {
            return CavernDimensions.CAVERN_DIMENSION_ID.equals(targetDimensionId)
                && safeArrivals.contains(new SafeArrival(x, y, z));
        }
    }

    private record SafeArrival(int x, int y, int z) {
    }

    private record PortalLocation(String dimensionId, int x, int y, int z, String axis) {
    }
}
