package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernInteractionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernRewardService;

import net.minecraft.world.MenuProvider;

class CavernProgressionCommandsTest {
    @Test
    void openGuiRoutesThroughSharedCatalogGuiOpener() {
        CavernProgressionService progressionService = new CavernProgressionService(new InMemoryPlayerMiningProgressionRepository());
        CavernRewardService rewardService = new CavernRewardService(new InMemoryPlayerClaimedRewardRepository());
        CavernInteractionService interactionService = new CavernInteractionService(
            new InMemoryPlayerClaimedRewardRepository(),
            new InMemoryPlayerServiceStateRepository()
        );
        CavernCatalogGuiOpener opener = new CavernCatalogGuiOpener(
            new CavernCatalogAccess(progressionService, interactionService, new CavernRewardGranter())
        );
        CavernProgressionCommands commands = new CavernProgressionCommands(
            progressionService,
            rewardService,
            new CavernRewardGranter(),
            interactionService,
            new InMemoryPlayerServiceStateRepository(),
            opener
        );
        AtomicInteger openCalls = new AtomicInteger();
        AtomicReference<MenuProvider> provider = new AtomicReference<>();

        int result = commands.openGui(menuProvider -> {
            openCalls.incrementAndGet();
            provider.set(menuProvider);
            return OptionalInt.of(7);
        });

        assertEquals(1, result);
        assertEquals(1, openCalls.get());
        assertNotNull(provider.get());
    }

    @Test
    void openGuiReturnsZeroWhenSharedCatalogGuiOpenerDoesNotOpenMenu() {
        CavernProgressionService progressionService = new CavernProgressionService(new InMemoryPlayerMiningProgressionRepository());
        CavernRewardService rewardService = new CavernRewardService(new InMemoryPlayerClaimedRewardRepository());
        CavernInteractionService interactionService = new CavernInteractionService(
            new InMemoryPlayerClaimedRewardRepository(),
            new InMemoryPlayerServiceStateRepository()
        );
        CavernCatalogGuiOpener opener = new CavernCatalogGuiOpener(
            new CavernCatalogAccess(progressionService, interactionService, new CavernRewardGranter())
        );
        CavernProgressionCommands commands = new CavernProgressionCommands(
            progressionService,
            rewardService,
            new CavernRewardGranter(),
            interactionService,
            new InMemoryPlayerServiceStateRepository(),
            opener
        );

        int result = commands.openGui(menuProvider -> OptionalInt.empty());

        assertEquals(0, result);
    }
}
