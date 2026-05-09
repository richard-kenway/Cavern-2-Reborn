package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaGeneratorScaffold;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaGeneratorScaffoldStage;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaTerrainStep;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class CaveniaGeneratorBridge {
    private static final String ACTIVATION_BLOCKED_REASON =
        "Cavenia generator bridge is inert until dimension JSON, dimension type JSON, generator registration, safe access and spawn-host decisions exist.";

    private CaveniaGeneratorBridge() {
    }

    public static ResourceLocation location() {
        return CavernNeoForgeDimensions.CAVENIA_LOCATION;
    }

    public static ResourceKey<Level> levelKey() {
        return CavernNeoForgeDimensions.CAVENIA_LEVEL_KEY;
    }

    public static String dimensionId() {
        return CaveniaGeneratorScaffold.dimensionId();
    }

    public static int worldHeight() {
        return CaveniaGeneratorScaffold.worldHeight();
    }

    public static List<CaveniaGeneratorScaffoldStage> scaffoldStages() {
        return CaveniaGeneratorScaffold.stages();
    }

    public static List<CaveniaTerrainStep> terrainStages() {
        return CaveniaGeneratorScaffold.terrainStages();
    }

    public static int biomeEntryCount() {
        return CaveniaGeneratorScaffold.biomeEntryCount();
    }

    public static int veinEntryCount() {
        return CaveniaGeneratorScaffold.veinEntryCount();
    }

    public static boolean runtimeGeneratorRegistered() {
        return false;
    }

    public static boolean runtimeDimensionResourcesPresent() {
        return false;
    }

    public static boolean requiresDimensionJsonBeforeActivation() {
        return true;
    }

    public static boolean requiresDimensionTypeJsonBeforeActivation() {
        return true;
    }

    public static String activationBlockedReason() {
        return ACTIVATION_BLOCKED_REASON;
    }
}
