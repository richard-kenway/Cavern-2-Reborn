package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntry;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogUseResult;
import com.richardkenway.cavernreborn.core.progression.CavernInteractionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardService;
import com.richardkenway.cavernreborn.core.progression.CavernServiceStatus;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.nbt.CompoundTag;

class CavernCatalogPersistenceTest {
    @Test
    void apprenticeCatalogStateStaysAlignedAfterRestart() {
        UUID playerId = UUID.randomUUID();
        long baseTime = 1_000_000L;
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernProgressionService progressionService = progressionServiceFor(persistentState);
        CavernRewardService rewardService = rewardServiceFor(persistentState);
        CavernInteractionService interactionService = interactionServiceFor(persistentState);

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        CavernCatalogUseResult rewardUse = interactionService.useCatalogEntry(snapshot, "apprentice_supply_cache", baseTime).orElseThrow();
        CavernCatalogUseResult serviceUse = interactionService.useCatalogEntry(snapshot, "torch_supply", baseTime).orElseThrow();
        assertTrue(rewardUse.granted());
        assertTrue(serviceUse.granted());

        CavernPersistentStateData restartedState = restart(persistentState);
        CavernProgressionService restartedProgressionService = progressionServiceFor(restartedState);
        CavernRewardService restartedRewardService = rewardServiceFor(restartedState);
        CavernInteractionService restartedInteractionService = interactionServiceFor(restartedState);

        CavernProgressionSnapshot restartedSnapshot = restartedProgressionService.inspect(playerId);
        List<CavernCatalogEntry> catalogEntries = restartedInteractionService.inspectCatalog(restartedSnapshot, baseTime);
        List<CavernServiceStatus> serviceStatuses = restartedInteractionService.inspectServices(restartedSnapshot, baseTime);

        assertEquals(
            "CAVERN catalog for TestPlayer: rank=apprentice, available=0, next=journeyman, tiers="
                + "apprentice -> apprentice_supply_cache [reward, claimed: already claimed, grants torch x16, bread x8], "
                + "torch_supply [service, on cooldown: ready in 10m 0s, grants torch x16]; "
                + "journeyman -> journeyman_supply_cache [reward, locked: requires journeyman, current apprentice, grants torch x24, cooked_beef x8, water_bucket x1], "
                + "climbing_supply [service, locked: requires journeyman, current apprentice, grants ladder x16, cobblestone x32]",
            CavernPlayerCatalogStatusFormatter.format("TestPlayer", restartedSnapshot, catalogEntries)
        );
        assertEquals(
            "CAVERN rewards for TestPlayer: apprentice_supply_cache [claimed: grants torch x16, bread x8]; "
                + "journeyman_supply_cache [locked: requires journeyman, current apprentice, grants torch x24, cooked_beef x8, water_bucket x1]",
            CavernPlayerRewardStatusFormatter.format("TestPlayer", restartedSnapshot, restartedRewardService.inspect(restartedSnapshot))
        );
        assertEquals(
            """
                TestPlayer | Services
                Rank: apprentice (25 pts)
                [COOLDOWN] Torch Supply (10m 0s)
                [LOCKED] Climbing Supply (unlocks at journeyman)
                """.trim(),
            CavernPlayerServiceStatusFormatter.format(
                "TestPlayer",
                restartedSnapshot,
                serviceStatuses,
                restartedState.loadPlayerServiceState(playerId),
                baseTime
            )
        );
    }

    @Test
    void journeymanCatalogEntriesStayConsistentAfterRestart() {
        UUID playerId = UUID.randomUUID();
        long baseTime = 2_000_000L;
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernProgressionService progressionService = progressionServiceFor(persistentState);
        CavernRewardService rewardService = rewardServiceFor(persistentState);
        CavernInteractionService interactionService = interactionServiceFor(persistentState);

        for (int i = 0; i < 15; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        assertTrue(interactionService.useCatalogEntry(snapshot, "journeyman_supply_cache", baseTime).orElseThrow().granted());
        assertTrue(interactionService.useCatalogEntry(snapshot, "climbing_supply", baseTime).orElseThrow().granted());

        CavernPersistentStateData restartedState = restart(persistentState);
        CavernProgressionService restartedProgressionService = progressionServiceFor(restartedState);
        CavernRewardService restartedRewardService = rewardServiceFor(restartedState);
        CavernInteractionService restartedInteractionService = interactionServiceFor(restartedState);

        CavernProgressionSnapshot restartedSnapshot = restartedProgressionService.inspect(playerId);
        List<CavernCatalogEntry> catalogEntries = restartedInteractionService.inspectCatalog(restartedSnapshot, baseTime);
        List<CavernServiceStatus> serviceStatuses = restartedInteractionService.inspectServices(restartedSnapshot, baseTime);

        assertEquals(
            "CAVERN catalog for TestPlayer: rank=journeyman, available=2, next=none, tiers="
                + "apprentice -> apprentice_supply_cache [reward, available: use /cavern use apprentice_supply_cache, grants torch x16, bread x8], "
                + "torch_supply [service, available: use /cavern use torch_supply, grants torch x16]; "
                + "journeyman -> journeyman_supply_cache [reward, claimed: already claimed, grants torch x24, cooked_beef x8, water_bucket x1], "
                + "climbing_supply [service, on cooldown: ready in 20m 0s, grants ladder x16, cobblestone x32]",
            CavernPlayerCatalogStatusFormatter.format("TestPlayer", restartedSnapshot, catalogEntries)
        );
        assertEquals(
            "CAVERN rewards for TestPlayer: apprentice_supply_cache [available: claim with /cavern claim apprentice_supply_cache, grants torch x16, bread x8]; "
                + "journeyman_supply_cache [claimed: grants torch x24, cooked_beef x8, water_bucket x1]",
            CavernPlayerRewardStatusFormatter.format("TestPlayer", restartedSnapshot, restartedRewardService.inspect(restartedSnapshot))
        );
        assertEquals(
            """
                TestPlayer | Services
                Rank: journeyman (75 pts)
                [READY] Torch Supply
                [COOLDOWN] Climbing Supply (20m 0s)
                """.trim(),
            CavernPlayerServiceStatusFormatter.format(
                "TestPlayer",
                restartedSnapshot,
                serviceStatuses,
                restartedState.loadPlayerServiceState(playerId),
                baseTime
            )
        );
    }

    private static CavernProgressionService progressionServiceFor(CavernPersistentStateData persistentState) {
        return new CavernProgressionService(
            new SavedDataBackedPlayerMiningProgressionRepository(
                () -> Optional.of(persistentState),
                new InMemoryPlayerMiningProgressionRepository()
            )
        );
    }

    private static CavernRewardService rewardServiceFor(CavernPersistentStateData persistentState) {
        return new CavernRewardService(
            new SavedDataBackedPlayerClaimedRewardRepository(
                () -> Optional.of(persistentState),
                new InMemoryPlayerClaimedRewardRepository()
            )
        );
    }

    private static CavernInteractionService interactionServiceFor(CavernPersistentStateData persistentState) {
        return new CavernInteractionService(
            new SavedDataBackedPlayerClaimedRewardRepository(
                () -> Optional.of(persistentState),
                new InMemoryPlayerClaimedRewardRepository()
            ),
            new SavedDataBackedPlayerServiceStateRepository(
                () -> Optional.of(persistentState),
                new InMemoryPlayerServiceStateRepository()
            )
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
}
