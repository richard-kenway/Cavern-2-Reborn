package com.richardkenway.cavernreborn.app.client;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.compass.OreCompassClientAngleResolver;
import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;
import com.richardkenway.cavernreborn.app.registry.ModRegistries;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

@EventBusSubscriber(modid = CavernReborn.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class CavernClientModEvents {
    private static final OreCompassClientAngleResolver ORE_COMPASS_CLIENT_ANGLE_RESOLVER = new OreCompassClientAngleResolver();

    private CavernClientModEvents() {
    }

    @SubscribeEvent
    public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(CavernNeoForgeDimensions.CAVERN_LOCATION, new CavernDimensionSpecialEffects());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(
            ModRegistries.ORE_COMPASS.get(),
            ResourceLocation.withDefaultNamespace("angle"),
            ORE_COMPASS_CLIENT_ANGLE_RESOLVER::resolveAngle
        ));
    }
}
