package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogUseResult;
import com.richardkenway.cavernreborn.core.progression.CavernInteractionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class CavernCatalogAccessTest {
    @Test
    void useRoutesIntoExistingRewardAndServiceSemantics() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionService progressionService = new CavernProgressionService(new InMemoryPlayerMiningProgressionRepository());
        CavernCatalogAccess catalogAccess = new CavernCatalogAccess(
            progressionService,
            new CavernInteractionService(
                new InMemoryPlayerClaimedRewardRepository(),
                new InMemoryPlayerServiceStateRepository()
            ),
            new CavernRewardGranter()
        );

        for (int i = 0; i < 5; i++) {
            progressionService.recordMiningEvent(playerId, CavernDimensions.CAVERN_DIMENSION_ID, "minecraft:diamond_ore");
        }

        CavernCatalogUseResult rewardUse = catalogAccess.use(playerId, "apprentice_supply_cache", 0L).orElseThrow();
        CavernCatalogUseResult rewardRetry = catalogAccess.use(playerId, "apprentice_supply_cache", 0L).orElseThrow();
        CavernCatalogUseResult serviceUse = catalogAccess.use(playerId, "torch_supply", 0L).orElseThrow();
        CavernCatalogUseResult serviceRetry = catalogAccess.use(playerId, "torch_supply", 0L).orElseThrow();

        assertEquals(CavernProgressionRank.APPRENTICE, catalogAccess.inspect(playerId, 0L).snapshot().rank());

        assertTrue(rewardUse.granted());
        assertTrue(rewardUse.rewardEntry());
        assertEquals(CavernCatalogAvailability.CLAIMED, rewardUse.entry().availability());

        assertFalse(rewardRetry.granted());
        assertTrue(rewardRetry.alreadyClaimed());

        assertTrue(serviceUse.granted());
        assertTrue(serviceUse.serviceEntry());
        assertEquals(CavernCatalogAvailability.ON_COOLDOWN, serviceUse.entry().availability());

        assertFalse(serviceRetry.granted());
        assertTrue(serviceRetry.onCooldown());
    }

    @Test
    void inspectAndUseStayEmptyForUnknownCatalogEntry() {
        UUID playerId = UUID.randomUUID();
        CavernCatalogAccess catalogAccess = new CavernCatalogAccess(
            new CavernProgressionService(new InMemoryPlayerMiningProgressionRepository()),
            new CavernInteractionService(
                new InMemoryPlayerClaimedRewardRepository(),
                new InMemoryPlayerServiceStateRepository()
            ),
            new CavernRewardGranter()
        );

        assertTrue(catalogAccess.inspect(playerId, 0L).entries().stream().allMatch(entry -> entry.locked()));
        assertTrue(catalogAccess.use(playerId, "missing_entry", 0L).isEmpty());
    }
}
