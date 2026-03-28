package com.richardkenway.cavernreborn.app.client;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

@EventBusSubscriber(modid = CavernReborn.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class CavernClientModEvents {
    private CavernClientModEvents() {
    }

    @SubscribeEvent
    public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(CavernNeoForgeDimensions.CAVERN_LOCATION, new CavernDimensionSpecialEffects());
    }
}
