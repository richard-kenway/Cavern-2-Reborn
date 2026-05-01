package com.richardkenway.cavernreborn;

import java.util.Objects;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.richardkenway.cavernreborn.app.gametest.CavernSpecialOreGameTests;
import com.richardkenway.cavernreborn.app.entity.CavenicCreeperLootEvents;
import com.richardkenway.cavernreborn.app.entity.CavenicSkeletonLootEvents;
import com.richardkenway.cavernreborn.app.entity.CavenicSpiderLootEvents;
import com.richardkenway.cavernreborn.app.entity.CavenicWitchLootEvents;
import com.richardkenway.cavernreborn.app.entity.CavenicZombieLootEvents;
import com.richardkenway.cavernreborn.app.entity.CrazyCreeperLootEvents;
import com.richardkenway.cavernreborn.app.entity.CrazySkeletonLootEvents;
import com.richardkenway.cavernreborn.app.entity.CrazyZombieLootEvents;
import com.richardkenway.cavernreborn.app.item.CavenicBowTorchEvents;
import com.richardkenway.cavernreborn.app.mining.CavernAquamarineToolEvents;
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
import net.neoforged.neoforge.common.NeoForge;

@Mod(CavernReborn.MOD_ID)
public final class CavernReborn {
    public static final String MOD_ID = "cavernreborn";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static CavernStateBootstrap cavernStateBootstrap;

    static {
        GameTestRegistry.register(CavernSpecialOreGameTests.class);
    }

    public CavernReborn(IEventBus modEventBus) {
        ModRegistries.register(modEventBus);
        cavernStateBootstrap = new CavernStateBootstrap();
        NeoForge.EVENT_BUS.register(new CavernPortalFrameActivationEvents());
        NeoForge.EVENT_BUS.register(new CavenicBowTorchEvents());
        NeoForge.EVENT_BUS.register(new CavenicCreeperLootEvents());
        NeoForge.EVENT_BUS.register(new CavenicSkeletonLootEvents());
        NeoForge.EVENT_BUS.register(new CavenicSpiderLootEvents());
        NeoForge.EVENT_BUS.register(new CavenicWitchLootEvents());
        NeoForge.EVENT_BUS.register(new CavenicZombieLootEvents());
        NeoForge.EVENT_BUS.register(new CrazyCreeperLootEvents());
        NeoForge.EVENT_BUS.register(new CrazySkeletonLootEvents());
        NeoForge.EVENT_BUS.register(new CrazyZombieLootEvents());
        NeoForge.EVENT_BUS.register(new CavernAquamarineToolEvents());
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

    public static CavernStateBootstrap cavernStateBootstrap() {
        return Objects.requireNonNull(cavernStateBootstrap, "cavernStateBootstrap");
    }
}
