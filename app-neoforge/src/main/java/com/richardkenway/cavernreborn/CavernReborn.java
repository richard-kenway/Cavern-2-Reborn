package com.richardkenway.cavernreborn;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.core.CavernProject;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CavernReborn.MOD_ID)
public final class CavernReborn {
    public static final String MOD_ID = "cavernreborn";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CavernReborn(IEventBus modEventBus) {
        ModRegistries.register(modEventBus);
        LOGGER.info("Bootstrapped {}", CavernProject.PROJECT_NAME);
    }
}
