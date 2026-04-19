package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.state.CavernPersistentStateData;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntry;
import com.richardkenway.cavernreborn.core.progression.CavernInteractionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;

class CavernMenuPersistenceTest {
    @Test
    void journeymanMenuStateSurvivesRestart() {
        UUID playerId = UUID.randomUUID();
        long baseTime = 2_000_000L;
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernProgressionService progressionService = progressionServiceFor(persistentState);
        CavernInteractionService interactionService = interactionServiceFor(persistentState);

        for (int i = 0; i < 15; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        assertTrue(interactionService.useCatalogEntry(snapshot, "journeyman_supply_cache", baseTime).orElseThrow().granted());
        assertTrue(interactionService.useCatalogEntry(snapshot, "climbing_supply", baseTime).orElseThrow().granted());

        CavernPersistentStateData restartedState = restart(persistentState);
        CavernProgressionService restartedProgressionService = progressionServiceFor(restartedState);
        CavernInteractionService restartedInteractionService = interactionServiceFor(restartedState);

        CavernProgressionSnapshot restartedSnapshot = restartedProgressionService.inspect(playerId);
        List<Component> lines = CavernPlayerMenuFormatter.format(
            "TestPlayer",
            restartedSnapshot,
            restartedInteractionService.inspectCatalog(restartedSnapshot, baseTime)
        );

        assertEquals("Rank journeyman | score 75 | available now 2 | next tier none", lines.get(1).getString());
        assertEquals("-- apprentice tier [unlocked] --", lines.get(4).getString());
        assertEquals(
            "[CLAIM] Apprentice Supply Cache | reward | available now | grants torch x16, bread x8",
            lines.get(5).getString()
        );
        assertEquals("/cavern menu use apprentice_supply_cache", clickCommand(lines.get(5), 0));
        assertEquals("-- journeyman tier [current] --", lines.get(7).getString());
        assertEquals(
            "[CLAIMED] Journeyman Supply Cache | reward | already claimed | grants torch x24, cooked_beef x8, water_bucket x1",
            lines.get(8).getString()
        );
        assertNull(clickCommand(lines.get(8), 0));
        assertEquals(
            "[COOLDOWN] Climbing Supply | service | ready in 20m 0s | grants ladder x16, cobblestone x32",
            lines.get(9).getString()
        );
        assertNull(clickCommand(lines.get(9), 0));
    }

    @Test
    void menuStateIsIsolatedPerPlayer() {
        UUID playerA = UUID.randomUUID();
        UUID playerB = UUID.randomUUID();
        long baseTime = 1_000_000L;
        CavernPersistentStateData persistentState = new CavernPersistentStateData();
        CavernProgressionService progressionService = progressionServiceFor(persistentState);
        CavernInteractionService interactionService = interactionServiceFor(persistentState);

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerA, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
            progressionService.recordMiningEvent(playerB, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernProgressionSnapshot snapshotA = progressionService.inspect(playerA);
        CavernProgressionSnapshot snapshotB = progressionService.inspect(playerB);
        assertTrue(interactionService.useCatalogEntry(snapshotA, "apprentice_supply_cache", baseTime).orElseThrow().granted());
        assertTrue(interactionService.useCatalogEntry(snapshotA, "torch_supply", baseTime).orElseThrow().granted());

        CavernPersistentStateData restartedState = restart(persistentState);
        CavernProgressionService restartedProgressionService = progressionServiceFor(restartedState);
        CavernInteractionService restartedInteractionService = interactionServiceFor(restartedState);

        List<Component> menuA = menuForPlayer("PlayerA", playerA, restartedProgressionService, restartedInteractionService, baseTime);
        List<Component> menuB = menuForPlayer("PlayerB", playerB, restartedProgressionService, restartedInteractionService, baseTime);

        assertEquals(
            "[CLAIMED] Apprentice Supply Cache | reward | already claimed | grants torch x16, bread x8",
            menuA.get(5).getString()
        );
        assertNull(clickCommand(menuA.get(5), 0));
        assertEquals(
            "[COOLDOWN] Torch Supply | service | ready in 10m 0s | grants torch x16",
            menuA.get(6).getString()
        );
        assertNull(clickCommand(menuA.get(6), 0));

        assertEquals(
            "[CLAIM] Apprentice Supply Cache | reward | available now | grants torch x16, bread x8",
            menuB.get(5).getString()
        );
        assertEquals("/cavern menu use apprentice_supply_cache", clickCommand(menuB.get(5), 0));
        assertEquals(
            "[USE] Torch Supply | service | available now | grants torch x16",
            menuB.get(6).getString()
        );
        assertEquals("/cavern menu use torch_supply", clickCommand(menuB.get(6), 0));
    }

    private static List<Component> menuForPlayer(
        String playerName,
        UUID playerId,
        CavernProgressionService progressionService,
        CavernInteractionService interactionService,
        long currentTimeMillis
    ) {
        CavernProgressionSnapshot snapshot = progressionService.inspect(playerId);
        List<CavernCatalogEntry> entries = interactionService.inspectCatalog(snapshot, currentTimeMillis);
        return CavernPlayerMenuFormatter.format(playerName, snapshot, entries);
    }

    private static String clickCommand(Component component, int siblingIndex) {
        ClickEvent clickEvent = component.getSiblings().get(siblingIndex).getStyle().getClickEvent();
        return clickEvent == null ? null : clickEvent.getValue();
    }

    private static CavernProgressionService progressionServiceFor(CavernPersistentStateData persistentState) {
        return new CavernProgressionService(
            new SavedDataBackedPlayerMiningProgressionRepository(
                () -> Optional.of(persistentState),
                new InMemoryPlayerMiningProgressionRepository()
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
