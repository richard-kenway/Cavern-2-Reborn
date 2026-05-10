package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

public final class CaveniaActivationReadinessMatrix {
    private static final List<CaveniaActivationReadinessMatrixEntry> ENTRIES = List.of(
        entry(
            CaveniaActivationSurface.GENERATOR_HOST,
            "CaveniaGeneratorHostContracts",
            CaveniaGeneratorHostContracts.contracts().size(),
            CaveniaGeneratorHostContracts.allRequirementsBlocked(),
            CaveniaGeneratorHostContracts.anyRequirementReady(),
            CaveniaGeneratorHostContracts.anyRequirementRuntimeImplemented(),
            CaveniaGeneratorHostContracts.canCreateChunks(),
            "Generator-host activation remains blocked by missing runtime host, codec/registration and dimension-resource binding."
        ),
        entry(
            CaveniaActivationSurface.BIOME_SOURCE_STRATEGY,
            "CaveniaBiomeSourceStrategyContracts",
            CaveniaBiomeSourceStrategyContracts.contracts().size(),
            CaveniaBiomeSourceStrategyContracts.allRequirementsBlocked(),
            CaveniaBiomeSourceStrategyContracts.anyRequirementReady(),
            CaveniaBiomeSourceStrategyContracts.anyRequirementRuntimeImplemented(),
            CaveniaBiomeSourceStrategyContracts.runtimeBiomeSourceReady(),
            "Biome-source activation remains blocked by missing runtime biome source, codec/registration and registry lookup access."
        ),
        entry(
            CaveniaActivationSurface.DIMENSION_RESOURCE,
            "CaveniaDimensionResourceContracts",
            CaveniaDimensionResourceContracts.contracts().size(),
            CaveniaDimensionResourceContracts.allRequirementsBlocked(),
            CaveniaDimensionResourceContracts.anyRequirementReady(),
            CaveniaDimensionResourceContracts.anyRuntimeBound(),
            CaveniaDimensionResourceContracts.canCreateLevel(),
            "Dimension-resource activation remains blocked by absent dimension JSON, dimension type JSON and runtime binding."
        ),
        entry(
            CaveniaActivationSurface.ACCESS_TRAVEL,
            "CaveniaAccessTravelContracts",
            CaveniaAccessTravelContracts.contracts().size(),
            CaveniaAccessTravelContracts.allRequirementsBlocked(),
            CaveniaAccessTravelContracts.anyRequirementReady(),
            CaveniaAccessTravelContracts.anyRequirementRuntimeImplemented(),
            CaveniaAccessTravelContracts.canTeleport(),
            "Access/travel activation remains blocked by missing safe arrival, return-path and travel-host implementation."
        ),
        entry(
            CaveniaActivationSurface.SPAWN_HOST,
            "CaveniaSpawnHostContracts",
            CaveniaSpawnHostContracts.contracts().size(),
            CaveniaSpawnHostContracts.allRequirementsBlocked(),
            CaveniaSpawnHostContracts.anyRequirementReady(),
            CaveniaSpawnHostContracts.anyRequirementRuntimeImplemented(),
            CaveniaSpawnHostContracts.canSpawn(),
            "Spawn-host activation remains blocked by missing Cavenia-only host, runtime loop and level availability."
        ),
        entry(
            CaveniaActivationSurface.WORLDGEN_RESOURCE,
            "CaveniaWorldgenResourceContracts",
            CaveniaWorldgenResourceContracts.contracts().size(),
            CaveniaWorldgenResourceContracts.allRequirementsBlocked(),
            CaveniaWorldgenResourceContracts.anyRequirementReady(),
            CaveniaWorldgenResourceContracts.anyRuntimeBound(),
            CaveniaWorldgenResourceContracts.canAffectWorldgen(),
            "Worldgen-resource activation remains blocked by absent configured resources, biome resources and ordering guards."
        )
    );

    private CaveniaActivationReadinessMatrix() {
    }

    public static List<CaveniaActivationReadinessMatrixEntry> entries() {
        return ENTRIES;
    }

    public static List<CaveniaActivationSurface> surfaces() {
        return ENTRIES.stream()
            .map(CaveniaActivationReadinessMatrixEntry::surface)
            .toList();
    }

    public static Optional<CaveniaActivationReadinessMatrixEntry> entryFor(CaveniaActivationSurface surface) {
        return ENTRIES.stream()
            .filter(entry -> entry.surface() == surface)
            .findFirst();
    }

    public static int totalRequirementCount() {
        return ENTRIES.stream()
            .mapToInt(CaveniaActivationReadinessMatrixEntry::requirementCount)
            .sum();
    }

    public static int blockedRequirementCount() {
        return totalRequirementCount();
    }

    public static boolean allSurfacesBlocked() {
        return ENTRIES.stream().allMatch(CaveniaActivationReadinessMatrixEntry::allRequirementsBlocked);
    }

    public static boolean anySurfaceReady() {
        return ENTRIES.stream().anyMatch(CaveniaActivationReadinessMatrixEntry::anyRequirementReady);
    }

    public static boolean anyRuntimeImplemented() {
        return ENTRIES.stream().anyMatch(CaveniaActivationReadinessMatrixEntry::runtimeImplemented);
    }

    public static boolean activeRuntimeSurfacePresent() {
        return ENTRIES.stream().anyMatch(CaveniaActivationReadinessMatrixEntry::activeRuntimeSurfacePresent);
    }

    public static boolean canActivateCavenia() {
        return false;
    }

    public static int generatorHostRequirementCount() {
        return countFor(CaveniaActivationSurface.GENERATOR_HOST);
    }

    public static int biomeSourceStrategyRequirementCount() {
        return countFor(CaveniaActivationSurface.BIOME_SOURCE_STRATEGY);
    }

    public static int dimensionResourceRequirementCount() {
        return countFor(CaveniaActivationSurface.DIMENSION_RESOURCE);
    }

    public static int accessTravelRequirementCount() {
        return countFor(CaveniaActivationSurface.ACCESS_TRAVEL);
    }

    public static int spawnHostRequirementCount() {
        return countFor(CaveniaActivationSurface.SPAWN_HOST);
    }

    public static int worldgenResourceRequirementCount() {
        return countFor(CaveniaActivationSurface.WORLDGEN_RESOURCE);
    }

    public static boolean generatorCanCreateChunks() {
        return CaveniaGeneratorHostContracts.canCreateChunks();
    }

    public static boolean biomeSourceRuntimeReady() {
        return CaveniaBiomeSourceStrategyContracts.runtimeBiomeSourceReady();
    }

    public static boolean dimensionCanCreateLevel() {
        return CaveniaDimensionResourceContracts.canCreateLevel();
    }

    public static boolean accessCanTeleport() {
        return CaveniaAccessTravelContracts.canTeleport();
    }

    public static boolean spawnHostCanSpawn() {
        return CaveniaSpawnHostContracts.canSpawn();
    }

    public static boolean worldgenCanAffectWorldgen() {
        return CaveniaWorldgenResourceContracts.canAffectWorldgen();
    }

    public static boolean dimensionJsonPresent() {
        return CaveniaDimensionResourceContracts.dimensionJsonPresent();
    }

    public static boolean dimensionTypeJsonPresent() {
        return CaveniaDimensionResourceContracts.dimensionTypeJsonPresent();
    }

    public static boolean configuredCarverResourcesPresent() {
        return CaveniaWorldgenResourceContracts.configuredCarverResourcesPresent();
    }

    public static boolean configuredFeatureResourcesPresent() {
        return CaveniaWorldgenResourceContracts.configuredFeatureResourcesPresent();
    }

    public static boolean placedFeatureResourcesPresent() {
        return CaveniaWorldgenResourceContracts.placedFeatureResourcesPresent();
    }

    public static boolean biomeModifierResourcesPresent() {
        return CaveniaWorldgenResourceContracts.biomeModifierResourcesPresent();
    }

    public static boolean biomeTagResourcesPresent() {
        return CaveniaWorldgenResourceContracts.biomeTagResourcesPresent();
    }

    public static boolean cavemanRemainsDeferred() {
        return CaveniaSpawnHostContracts.cavemanRemainsDeferred();
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    private static int countFor(CaveniaActivationSurface surface) {
        return entryFor(surface)
            .map(CaveniaActivationReadinessMatrixEntry::requirementCount)
            .orElse(0);
    }

    private static CaveniaActivationReadinessMatrixEntry entry(
        CaveniaActivationSurface surface,
        String sourceContractName,
        int requirementCount,
        boolean allRequirementsBlocked,
        boolean anyRequirementReady,
        boolean runtimeImplemented,
        boolean activeRuntimeSurfacePresent,
        String blocker
    ) {
        return new CaveniaActivationReadinessMatrixEntry(
            surface,
            sourceContractName,
            requirementCount,
            allRequirementsBlocked,
            anyRequirementReady,
            runtimeImplemented,
            activeRuntimeSurfacePresent,
            blocker
        );
    }
}
