package com.richardkenway.cavernreborn.app.gametest;

import com.richardkenway.cavernreborn.CavernReborn;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

@EventBusSubscriber(modid = CavernReborn.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class CavernGameTestRegistrationEvents {
    private CavernGameTestRegistrationEvents() {
    }

    @SubscribeEvent
    public static void registerGameTests(RegisterGameTestsEvent event) {
        event.register(CavernSpecialOreGameTests.class);
    }
}
