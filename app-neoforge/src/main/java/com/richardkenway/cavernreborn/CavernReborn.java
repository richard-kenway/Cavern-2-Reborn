package com.richardkenway.cavernreborn;

import java.util.Objects;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.richardkenway.cavernreborn.app.gametest.CavernSpecialOreGameTests;
import com.richardkenway.cavernreborn.app.mining.CavernMiningAssistEvents;
import com.richardkenway.cavernreborn.app.progression.CavernMiningProgressionEvents;
import com.richardkenway.cavernreborn.app.progression.CavernRewardGranter;
import com.richardkenway.cavernreborn.app.progression.CavernProgressionCommands;
import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.app.state.CavernStateBootstrap;
import com.richardkenway.cavernreborn.app.portal.CavernPortalFrameActivationEvents;
import com.richardkenway.cavernreborn.core.CavernProject;

import net.minecraft.gametest.framework.GameTestRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.gametest.GameTestHooks;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CavernReborn.MOD_ID)
public final class CavernReborn {
    public static final String MOD_ID = "cavernreborn";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static CavernStateBootstrap cavernStateBootstrap;

    public CavernReborn(IEventBus modEventBus) {
        ModRegistries.register(modEventBus);
        registerGameTests();
        cavernStateBootstrap = new CavernStateBootstrap();
        NeoForge.EVENT_BUS.register(new CavernPortalFrameActivationEvents());
        NeoForge.EVENT_BUS.register(new CavernMiningAssistEvents(cavernStateBootstrap.cavernProgressionService()));
        NeoForge.EVENT_BUS.register(new CavernMiningProgressionEvents(cavernStateBootstrap.cavernProgressionService()));
        NeoForge.EVENT_BUS.register(
            new CavernProgressionCommands(
                cavernStateBootstrap.cavernProgressionService(),
                cavernStateBootstrap.cavernRewardService(),
                new CavernRewardGranter(),
                cavernStateBootstrap.cavernInteractionService(),
                cavernStateBootstrap.playerServiceStateStore(),
                cavernStateBootstrap.cavernCatalogGuiOpener()
            )
        );
        LOGGER.info("Bootstrapped {} with {}", CavernProject.PROJECT_NAME, cavernStateBootstrap().describe());
    }

    private void registerGameTests() {
        if (GameTestHooks.isGametestEnabled()) {
            GameTestRegistry.register(CavernSpecialOreGameTests.class);
        }
    }

    public static CavernStateBootstrap cavernStateBootstrap() {
        return Objects.requireNonNull(cavernStateBootstrap, "cavernStateBootstrap");
    }
}
