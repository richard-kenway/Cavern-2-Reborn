package com.richardkenway.cavernreborn.app.registry;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.entity.CavenicBear;
import com.richardkenway.cavernreborn.app.entity.CavenicCreeper;
import com.richardkenway.cavernreborn.app.entity.CavenicSkeleton;
import com.richardkenway.cavernreborn.app.entity.CavenicSpider;
import com.richardkenway.cavernreborn.app.entity.CavenicWitch;
import com.richardkenway.cavernreborn.app.entity.CavenicZombie;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;

@EventBusSubscriber(modid = CavernReborn.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEntityEvents {
    private ModEntityEvents() {
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModRegistries.CAVENIC_ZOMBIE.get(), CavenicZombie.createAttributes().build());
        event.put(ModRegistries.CAVENIC_SKELETON.get(), CavenicSkeleton.createAttributes().build());
        event.put(ModRegistries.CAVENIC_CREEPER.get(), CavenicCreeper.createAttributes().build());
        event.put(ModRegistries.CAVENIC_SPIDER.get(), CavenicSpider.createAttributes().build());
        event.put(ModRegistries.CAVENIC_WITCH.get(), CavenicWitch.createAttributes().build());
        event.put(ModRegistries.CAVENIC_BEAR.get(), CavenicBear.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
            ModRegistries.CAVENIC_ZOMBIE.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            CavenicZombie::checkCavenicZombieSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
            ModRegistries.CAVENIC_SKELETON.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            CavenicSkeleton::checkCavenicSkeletonSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
            ModRegistries.CAVENIC_CREEPER.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            CavenicCreeper::checkCavenicCreeperSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
            ModRegistries.CAVENIC_SPIDER.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            CavenicSpider::checkCavenicSpiderSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
            ModRegistries.CAVENIC_WITCH.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            CavenicWitch::checkCavenicWitchSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
            ModRegistries.CAVENIC_BEAR.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            CavenicBear::checkCavenicBearSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }
}
