package com.richardkenway.cavernreborn.app.worldgen;

import java.util.List;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaGeneratorScaffold;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaGeneratorScaffoldStage;

public final class CaveniaGeneratorRuntimeContracts {
    private static final List<CaveniaGeneratorRuntimeOperationContract> CONTRACTS = List.of(
        contract(
            CaveniaGeneratorRuntimeOperation.IDENTITY,
            CaveniaGeneratorScaffoldStage.IDENTITY,
            "CavernDimensions / CavernNeoForgeDimensions"
        ),
        contract(
            CaveniaGeneratorRuntimeOperation.BASE_TERRAIN_FILL,
            CaveniaGeneratorScaffoldStage.BASE_TERRAIN,
            "CaveniaTerrainGeneratorPolicy"
        ),
        contract(
            CaveniaGeneratorRuntimeOperation.CAVE_CARVING,
            CaveniaGeneratorScaffoldStage.CAVE_CARVING,
            "CaveniaCaveCarverPolicy"
        ),
        contract(
            CaveniaGeneratorRuntimeOperation.BIOME_TOP_FILTER_REPLACEMENT,
            CaveniaGeneratorScaffoldStage.BIOME_TOP_FILTER_REPLACEMENT,
            "CaveniaBiomeTopFilterPolicy"
        ),
        contract(
            CaveniaGeneratorRuntimeOperation.VEINS_MUTATION,
            CaveniaGeneratorScaffoldStage.VEINS_MUTATION,
            "CaveniaVeinsContentPolicy"
        ),
        contract(
            CaveniaGeneratorRuntimeOperation.FINAL_CHUNK_CONSTRUCTION,
            CaveniaGeneratorScaffoldStage.FINAL_CHUNK_CONSTRUCTION,
            "CaveniaGeneratorScaffold"
        ),
        contract(
            CaveniaGeneratorRuntimeOperation.POPULATION_INTEGRATION,
            CaveniaGeneratorScaffoldStage.POPULATION_INTEGRATION,
            "CaveniaPopulationPolicy"
        ),
        contract(
            CaveniaGeneratorRuntimeOperation.SPAWN_PROVIDER_INTEGRATION_DEFERRED,
            CaveniaGeneratorScaffoldStage.SPAWN_PROVIDER_INTEGRATION_DEFERRED,
            "CaveniaSpawnProviderPolicy"
        ),
        contract(
            CaveniaGeneratorRuntimeOperation.ENTRY_ACCESS_DEFERRED,
            CaveniaGeneratorScaffoldStage.ENTRY_ACCESS_DEFERRED,
            "CaveniaGeneratorRegistrationBoundary / CaveniaGeneratorBridge"
        )
    );

    private CaveniaGeneratorRuntimeContracts() {
    }

    public static List<CaveniaGeneratorRuntimeOperationContract> contracts() {
        return CONTRACTS;
    }

    public static List<CaveniaGeneratorRuntimeOperation> operations() {
        return CONTRACTS.stream()
            .map(CaveniaGeneratorRuntimeOperationContract::operation)
            .toList();
    }

    public static Optional<CaveniaGeneratorRuntimeOperationContract> contractFor(CaveniaGeneratorRuntimeOperation operation) {
        return CONTRACTS.stream()
            .filter(contract -> contract.operation() == operation)
            .findFirst();
    }

    public static boolean allRuntimeOperationsBlocked() {
        return CONTRACTS.stream().noneMatch(CaveniaGeneratorRuntimeOperationContract::runtimeImplemented);
    }

    public static boolean anyOperationCanCreateChunks() {
        return CONTRACTS.stream().anyMatch(CaveniaGeneratorRuntimeOperationContract::canCreateChunks);
    }

    public static boolean anyOperationCanMutatePrimer() {
        return CONTRACTS.stream().anyMatch(CaveniaGeneratorRuntimeOperationContract::canMutatePrimer);
    }

    public static boolean allRequireActivationBeforeRuntime() {
        return CONTRACTS.stream().allMatch(contract ->
            contract.requiresRegisteredGenerator()
                && contract.requiresDimensionResources()
                && !contract.runtimeImplemented()
        );
    }

    public static boolean matchesScaffoldStageOrder() {
        return CONTRACTS.stream()
            .map(CaveniaGeneratorRuntimeOperationContract::scaffoldStage)
            .toList()
            .equals(CaveniaGeneratorScaffold.stages());
    }

    public static List<CaveniaGeneratorActivationRequirement> activationRequirements() {
        return CaveniaGeneratorRegistrationBoundary.activationRequirements();
    }

    public static String activationBlockedReason() {
        return CaveniaGeneratorRegistrationBoundary.activationBlockedReason();
    }

    public static boolean generatorSkeletonCanCreateChunks() {
        return CaveniaGeneratorSkeleton.canCreateChunks();
    }

    public static boolean biomeSelectionIsRuntimeBiomeSource() {
        return CaveniaBiomeSelectionSkeleton.create().isRuntimeBiomeSource();
    }

    private static CaveniaGeneratorRuntimeOperationContract contract(
        CaveniaGeneratorRuntimeOperation operation,
        CaveniaGeneratorScaffoldStage scaffoldStage,
        String sourcePolicyName
    ) {
        return new CaveniaGeneratorRuntimeOperationContract(
            operation,
            scaffoldStage,
            sourcePolicyName,
            false,
            false,
            false,
            true,
            true
        );
    }
}
