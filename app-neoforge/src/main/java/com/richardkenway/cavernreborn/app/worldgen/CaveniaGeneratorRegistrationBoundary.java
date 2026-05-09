package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class CaveniaGeneratorRegistrationBoundary {
    private static final List<CaveniaGeneratorActivationRequirement> ACTIVATION_REQUIREMENTS = List.of(
        CaveniaGeneratorActivationRequirement.DIMENSION_JSON,
        CaveniaGeneratorActivationRequirement.DIMENSION_TYPE_JSON,
        CaveniaGeneratorActivationRequirement.CHUNK_GENERATOR_IMPLEMENTATION,
        CaveniaGeneratorActivationRequirement.BIOME_SOURCE_STRATEGY,
        CaveniaGeneratorActivationRequirement.GENERATOR_CODEC,
        CaveniaGeneratorActivationRequirement.GENERATOR_REGISTRY_ENTRY,
        CaveniaGeneratorActivationRequirement.SAFE_ACCESS_OR_TELEPORT,
        CaveniaGeneratorActivationRequirement.CAVENIA_ONLY_SPAWN_HOST,
        CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING
    );
    private static final String ACTIVATION_BLOCKED_REASON =
        "Cavenia generator activation stays blocked until dimension JSON, dimension type JSON, chunk generator implementation, biome source strategy, generator codec/registration, safe access, Cavenia-only spawn host and worldgen resource mapping decisions exist.";

    private CaveniaGeneratorRegistrationBoundary() {
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

    public static boolean bridgeRuntimeGeneratorRegistered() {
        return CaveniaGeneratorBridge.runtimeGeneratorRegistered();
    }

    public static boolean codecRegistered() {
        return false;
    }

    public static boolean generatorRegistryEntryRegistered() {
        return false;
    }

    public static boolean chunkGeneratorClassPresent() {
        return false;
    }

    public static boolean biomeSourceClassPresent() {
        return false;
    }

    public static boolean dimensionJsonPresent() {
        return false;
    }

    public static boolean dimensionTypeJsonPresent() {
        return false;
    }

    public static boolean safeAccessReady() {
        return false;
    }

    public static boolean spawnHostReady() {
        return false;
    }

    public static boolean canActivateRuntimeGenerator() {
        return false;
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return ACTIVATION_REQUIREMENTS;
    }

    public static String activationBlockedReason() {
        return ACTIVATION_BLOCKED_REASON;
    }
}
