package com.richardkenway.cavernreborn.app.client;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

class CavernClientModEventsTest {
    @Test
    void registersCustomEffectsForCavernDimensionType() {
        HashMap<net.minecraft.resources.ResourceLocation, DimensionSpecialEffects> effects = new HashMap<>();
        RegisterDimensionSpecialEffectsEvent event = new RegisterDimensionSpecialEffectsEvent(effects);

        CavernClientModEvents.registerDimensionSpecialEffects(event);

        assertInstanceOf(CavernDimensionSpecialEffects.class, effects.get(CavernNeoForgeDimensions.CAVERN_LOCATION));
    }
}
