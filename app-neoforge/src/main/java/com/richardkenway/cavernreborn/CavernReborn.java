package com.richardkenway.cavernreborn;

import java.util.Objects;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.app.state.CavernStateBootstrap;
import com.richardkenway.cavernreborn.core.CavernProject;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CavernReborn.MOD_ID)
public final class CavernReborn {
    public static final String MOD_ID = "cavernreborn";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static CavernStateBootstrap cavernStateBootstrap;

    public CavernReborn(IEventBus modEventBus) {
        ModRegistries.register(modEventBus);
        cavernStateBootstrap = new CavernStateBootstrap();
        LOGGER.info("Bootstrapped {} with {}", CavernProject.PROJECT_NAME, cavernStateBootstrap().describe());
    }

    public static CavernStateBootstrap cavernStateBootstrap() {
        return Objects.requireNonNull(cavernStateBootstrap, "cavernStateBootstrap");
    }
}
