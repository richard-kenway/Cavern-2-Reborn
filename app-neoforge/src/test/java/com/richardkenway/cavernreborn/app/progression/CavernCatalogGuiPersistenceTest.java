package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernInteractionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.nbt.CompoundTag;
class CavernCatalogGuiPersistenceTest {
    @Test
    void guiStateSurvivesRestartForJourneymanEntries() {
        UUID playerId = UUID.randomUUID();
        long baseTime = 2_000_000L;
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernCatalogAccess catalogAccess = catalogAccessFor(persistentState);
        CavernProgressionService progressionService = progressionServiceFor(persistentState);

        for (int i = 0; i < 15; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        assertTrue(catalogAccess.use(playerId, "journeyman_supply_cache", baseTime).orElseThrow().granted());
        assertTrue(catalogAccess.use(playerId, "climbing_supply", baseTime).orElseThrow().granted());

        CavernPersistentStateData restartedState = restart(persistentState);
        CavernCatalogAccess restartedCatalogAccess = catalogAccessFor(restartedState);
        CavernCatalogAccess.CavernCatalogView view = restartedCatalogAccess.inspect(playerId, baseTime);
        CavernCatalogGuiLayout.Layout layout = CavernCatalogGuiLayout.create(view.snapshot(), view.entries());

        assertEquals("Rank: journeyman", itemName(layout.slotItems().get(CavernCatalogGuiLayout.RANK_SLOT)));
        assertEquals("Next tier: none", itemName(layout.slotItems().get(CavernCatalogGuiLayout.NEXT_TIER_SLOT)));
        assertEquals("Available now: 2", itemName(layout.slotItems().get(CavernCatalogGuiLayout.AVAILABLE_SLOT)));
        assertEquals("[AVAILABLE] Apprentice Supply Cache", itemName(layout.slotItems().get(10)));
        assertEquals("[AVAILABLE] Torch Supply", itemName(layout.slotItems().get(11)));
        assertEquals("[CLAIMED] Journeyman Supply Cache", itemName(layout.slotItems().get(19)));
        assertEquals("[COOLDOWN] Climbing Supply", itemName(layout.slotItems().get(20)));
    }

    @Test
    void guiStateRemainsIsolatedPerPlayerAfterRestart() {
        UUID playerA = UUID.randomUUID();
        UUID playerB = UUID.randomUUID();
        long baseTime = 1_000_000L;
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernCatalogAccess catalogAccess = catalogAccessFor(persistentState);
        CavernProgressionService progressionService = progressionServiceFor(persistentState);

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerA, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
            progressionService.recordMiningEvent(playerB, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        assertTrue(catalogAccess.use(playerA, "apprentice_supply_cache", baseTime).orElseThrow().granted());
        assertTrue(catalogAccess.use(playerA, "torch_supply", baseTime).orElseThrow().granted());

        CavernPersistentStateData restartedState = restart(persistentState);
        CavernCatalogAccess restartedCatalogAccess = catalogAccessFor(restartedState);

        CavernCatalogGuiLayout.Layout playerALayout = layoutFor(restartedCatalogAccess, playerA, baseTime);
        CavernCatalogGuiLayout.Layout playerBLayout = layoutFor(restartedCatalogAccess, playerB, baseTime);

        assertEquals("[CLAIMED] Apprentice Supply Cache", itemName(playerALayout.slotItems().get(10)));
        assertEquals("[COOLDOWN] Torch Supply", itemName(playerALayout.slotItems().get(11)));
        assertEquals("[AVAILABLE] Apprentice Supply Cache", itemName(playerBLayout.slotItems().get(10)));
        assertEquals("[AVAILABLE] Torch Supply", itemName(playerBLayout.slotItems().get(11)));
    }

    private static CavernCatalogGuiLayout.Layout layoutFor(CavernCatalogAccess catalogAccess, UUID playerId, long currentTimeMillis) {
        CavernCatalogAccess.CavernCatalogView view = catalogAccess.inspect(playerId, currentTimeMillis);
        return CavernCatalogGuiLayout.create(view.snapshot(), view.entries());
    }

    private static String itemName(CavernCatalogGuiLayout.SlotView slotView) {
        return slotView.title();
    }

    private static CavernProgressionService progressionServiceFor(CavernPersistentStateData persistentState) {
        return new CavernProgressionService(
            new SavedDataBackedPlayerMiningProgressionRepository(
                () -> java.util.Optional.of(persistentState),
                new InMemoryPlayerMiningProgressionRepository()
            )
        );
    }

    private static CavernCatalogAccess catalogAccessFor(CavernPersistentStateData persistentState) {
        return new CavernCatalogAccess(
            progressionServiceFor(persistentState),
            new CavernInteractionService(
                new SavedDataBackedPlayerClaimedRewardRepository(
                    () -> java.util.Optional.of(persistentState),
                    new InMemoryPlayerClaimedRewardRepository()
                ),
                new SavedDataBackedPlayerServiceStateRepository(
                    () -> java.util.Optional.of(persistentState),
                    new InMemoryPlayerServiceStateRepository()
                )
            ),
            new CavernRewardGranter()
        );
    }

    private static CavernPersistentStateData restart(CavernPersistentStateData persistentState) {
        CompoundTag serialized = persistentState.save(new CompoundTag(), null);
        try {
            Method loadMethod = CavernPersistentStateData.class.getDeclaredMethod(
                "load",
                CompoundTag.class,
                net.minecraft.core.HolderLookup.Provider.class
            );
            loadMethod.setAccessible(true);
            return (CavernPersistentStateData) loadMethod.invoke(null, serialized, null);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("Failed to restore persistent state snapshot", exception);
        }
    }
}
