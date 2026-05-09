package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaGeneratorScaffoldStage;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaTerrainStep;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class CaveniaGeneratorSkeleton {
    private CaveniaGeneratorSkeleton() {
    }

    public static String dimensionId() {
        return CaveniaGeneratorBridge.dimensionId();
    }

    public static ResourceLocation location() {
        return CaveniaGeneratorBridge.location();
    }

    public static ResourceKey<Level> levelKey() {
        return CaveniaGeneratorBridge.levelKey();
    }

    public static int worldHeight() {
        return CaveniaGeneratorBridge.worldHeight();
    }

    public static List<CaveniaGeneratorScaffoldStage> scaffoldStages() {
        return CaveniaGeneratorBridge.scaffoldStages();
    }

    public static List<CaveniaTerrainStep> terrainStages() {
        return CaveniaGeneratorBridge.terrainStages();
    }

    public static CaveniaBiomeSelectionSkeleton biomeSelection() {
        return CaveniaBiomeSelectionSkeleton.create();
    }

    public static boolean canCreateChunks() {
        return false;
    }

    public static boolean canMutatePrimer() {
        return false;
    }

    public static boolean runtimeGeneratorRegistered() {
        return CaveniaGeneratorRegistrationBoundary.bridgeRuntimeGeneratorRegistered();
    }

    public static boolean codecRegistered() {
        return CaveniaGeneratorRegistrationBoundary.codecRegistered();
    }

    public static boolean generatorRegistryEntryRegistered() {
        return CaveniaGeneratorRegistrationBoundary.generatorRegistryEntryRegistered();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }
}
