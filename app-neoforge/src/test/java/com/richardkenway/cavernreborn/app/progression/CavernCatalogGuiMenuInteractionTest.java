package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogUseResult;
import com.richardkenway.cavernreborn.core.progression.CavernInteractionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernRewardStatus;
import com.richardkenway.cavernreborn.core.progression.CavernServiceStatus;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.world.inventory.ClickType;

class CavernCatalogGuiMenuInteractionTest {
    @Test
    void rewardClickRoutesIntoClaimOnceAndRefreshesGuiState() {
        MenuHarness harness = MenuHarness.apprenticeHarness();
        int rewardSlot = harness.slotFor("apprentice_supply_cache");

        assertEquals("[AVAILABLE] Apprentice Supply Cache", harness.itemName(rewardSlot));

        assertTrue(harness.clickSlot(rewardSlot));
        assertEquals(1, harness.actor.results().size());
        assertTrue(harness.actor.results().get(0).rewardEntry());
        assertTrue(harness.actor.results().get(0).granted());
        assertEquals("[CLAIMED] Apprentice Supply Cache", harness.itemName(rewardSlot));

        assertTrue(harness.clickSlot(rewardSlot));
        assertEquals(2, harness.actor.results().size());
        assertTrue(harness.actor.results().get(1).alreadyClaimed());
        assertEquals("[CLAIMED] Apprentice Supply Cache", harness.itemName(rewardSlot));
    }

    @Test
    void serviceClickRoutesIntoCooldownAndRefreshesGuiState() {
        MenuHarness harness = MenuHarness.apprenticeHarness();
        int serviceSlot = harness.slotFor("torch_supply");

        assertEquals("[AVAILABLE] Torch Supply", harness.itemName(serviceSlot));

        assertTrue(harness.clickSlot(serviceSlot));
        assertEquals(1, harness.actor.results().size());
        assertTrue(harness.actor.results().get(0).serviceEntry());
        assertTrue(harness.actor.results().get(0).granted());
        assertEquals("[COOLDOWN] Torch Supply", harness.itemName(serviceSlot));

        assertTrue(harness.clickSlot(serviceSlot));
        assertEquals(2, harness.actor.results().size());
        assertTrue(harness.actor.results().get(1).onCooldown());
        assertEquals("[COOLDOWN] Torch Supply", harness.itemName(serviceSlot));
    }

    @Test
    void lockedSlotDoesNotGrantAndKeepsGuiStateLocked() {
        MenuHarness harness = MenuHarness.noviceHarness();
        int rewardSlot = harness.slotFor("apprentice_supply_cache");

        assertEquals("[LOCKED] Apprentice Supply Cache", harness.itemName(rewardSlot));

        assertTrue(harness.clickSlot(rewardSlot));
        assertEquals(1, harness.actor.results().size());
        assertTrue(harness.actor.results().get(0).locked());
        assertFalse(harness.actor.results().get(0).granted());
        assertEquals("[LOCKED] Apprentice Supply Cache", harness.itemName(rewardSlot));

        CavernProgressionSnapshot snapshot = harness.catalogAccess.inspect(harness.playerId, harness.currentTimeMillis.get()).snapshot();
        List<CavernRewardStatus> rewards = harness.interactionService.inspectRewards(snapshot);
        List<CavernServiceStatus> services = harness.interactionService.inspectServices(snapshot, harness.currentTimeMillis.get());
        assertTrue(rewards.stream().allMatch(CavernRewardStatus::locked));
        assertTrue(services.stream().allMatch(CavernServiceStatus::locked));
    }

    @Test
    void nonEntrySlotIsIgnoredAndRefreshSlotRebuildsGuiState() {
        MenuHarness harness = MenuHarness.noviceHarness();

        assertEquals("[LOCKED] Apprentice Supply Cache", harness.itemName(harness.slotFor("apprentice_supply_cache")));
        assertFalse(harness.clickSlot(CavernCatalogGuiLayout.RANK_SLOT));
        assertTrue(harness.actor.results().isEmpty());
        assertEquals("[LOCKED] Apprentice Supply Cache", harness.itemName(harness.slotFor("apprentice_supply_cache")));

        harness.mineToApprentice();
        assertEquals("[LOCKED] Apprentice Supply Cache", harness.itemName(harness.slotFor("apprentice_supply_cache")));

        assertTrue(harness.clickSlot(CavernCatalogGuiLayout.REFRESH_SLOT));
        assertTrue(harness.actor.results().isEmpty());
        assertEquals("[AVAILABLE] Apprentice Supply Cache", harness.itemName(harness.slotFor("apprentice_supply_cache")));
        assertEquals("[AVAILABLE] Torch Supply", harness.itemName(harness.slotFor("torch_supply")));
    }

    private static final class MenuHarness {
        private final UUID playerId;
        private final AtomicLong currentTimeMillis;
        private final CavernProgressionService progressionService;
        private final CavernInteractionService interactionService;
        private final CavernCatalogAccess catalogAccess;
        private final RecordingClickActor actor;
        private final AtomicReference<CavernCatalogGuiLayout.Layout> layout;

        private MenuHarness(int diamondOreCount, long currentTimeMillis) {
            this.playerId = UUID.randomUUID();
            this.currentTimeMillis = new AtomicLong(currentTimeMillis);
            this.progressionService = new CavernProgressionService(new InMemoryPlayerMiningProgressionRepository());
            this.interactionService = new CavernInteractionService(
                new InMemoryPlayerClaimedRewardRepository(),
                new InMemoryPlayerServiceStateRepository()
            );
            this.catalogAccess = new CavernCatalogAccess(
                progressionService,
                interactionService,
                new CavernRewardGranter()
            );
            this.actor = new RecordingClickActor(playerId);
            this.layout = new AtomicReference<>();

            for (int i = 0; i < diamondOreCount; i++) {
                this.progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
            }
            refresh();
        }

        private static MenuHarness noviceHarness() {
            return new MenuHarness(0, 1_000_000L);
        }

        private static MenuHarness apprenticeHarness() {
            return new MenuHarness(5, 1_000_000L);
        }

        private boolean clickSlot(int slotId) {
            return CavernCatalogGuiMenu.routeClick(
                playerId,
                slotId,
                ClickType.PICKUP,
                actor,
                () -> layout.get().entryIdsBySlot(),
                catalogAccess,
                currentTimeMillis::get,
                this::refresh
            );
        }

        private void mineToApprentice() {
            while (progressionService.inspect(playerId).rank().ordinal()
                < com.richardkenway.cavernreborn.core.progression.CavernProgressionRank.APPRENTICE.ordinal()) {
                progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
            }
        }

        private void refresh() {
            CavernCatalogAccess.CavernCatalogView view = catalogAccess.inspect(playerId, currentTimeMillis.get());
            layout.set(CavernCatalogGuiLayout.create(view.snapshot(), view.entries()));
        }

        private int slotFor(String entryId) {
            return layout.get().entryIdsBySlot().entrySet().stream()
                .filter(entry -> entry.getValue().equals(entryId))
                .mapToInt(java.util.Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
        }

        private String itemName(int slotId) {
            return layout.get().slotItems().get(slotId).title();
        }
    }

    private static final class RecordingClickActor implements CavernCatalogGuiMenu.ClickActor {
        private final UUID playerId;
        private final List<CavernCatalogUseResult> results = new ArrayList<>();

        private RecordingClickActor(UUID playerId) {
            this.playerId = playerId;
        }

        @Override
        public UUID playerId() {
            return playerId;
        }

        @Override
        public void onResult(CavernCatalogUseResult result) {
            results.add(result);
        }

        private List<CavernCatalogUseResult> results() {
            return results;
        }
    }
}
