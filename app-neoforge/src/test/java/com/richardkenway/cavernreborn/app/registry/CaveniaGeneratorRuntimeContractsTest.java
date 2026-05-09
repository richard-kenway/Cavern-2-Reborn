package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorActivationRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRuntimeContracts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRuntimeOperation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRuntimeOperationContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRegistrationBoundary;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaGeneratorScaffoldStage;

class CaveniaGeneratorRuntimeContractsTest {
    private static final Path OPERATIONS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaGeneratorRuntimeOperation.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaGeneratorRuntimeOperationContract.java"
    );
    private static final Path CONTRACTS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaGeneratorRuntimeContracts.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void runtimeContractsExposeOnlyBlockedFutureOperationFacts() {
        List<CaveniaGeneratorRuntimeOperation> expectedOperations = List.of(
            CaveniaGeneratorRuntimeOperation.IDENTITY,
            CaveniaGeneratorRuntimeOperation.BASE_TERRAIN_FILL,
            CaveniaGeneratorRuntimeOperation.CAVE_CARVING,
            CaveniaGeneratorRuntimeOperation.BIOME_TOP_FILTER_REPLACEMENT,
            CaveniaGeneratorRuntimeOperation.VEINS_MUTATION,
            CaveniaGeneratorRuntimeOperation.FINAL_CHUNK_CONSTRUCTION,
            CaveniaGeneratorRuntimeOperation.POPULATION_INTEGRATION,
            CaveniaGeneratorRuntimeOperation.SPAWN_PROVIDER_INTEGRATION_DEFERRED,
            CaveniaGeneratorRuntimeOperation.ENTRY_ACCESS_DEFERRED
        );
        List<CaveniaGeneratorScaffoldStage> expectedStages = List.of(
            CaveniaGeneratorScaffoldStage.IDENTITY,
            CaveniaGeneratorScaffoldStage.BASE_TERRAIN,
            CaveniaGeneratorScaffoldStage.CAVE_CARVING,
            CaveniaGeneratorScaffoldStage.BIOME_TOP_FILTER_REPLACEMENT,
            CaveniaGeneratorScaffoldStage.VEINS_MUTATION,
            CaveniaGeneratorScaffoldStage.FINAL_CHUNK_CONSTRUCTION,
            CaveniaGeneratorScaffoldStage.POPULATION_INTEGRATION,
            CaveniaGeneratorScaffoldStage.SPAWN_PROVIDER_INTEGRATION_DEFERRED,
            CaveniaGeneratorScaffoldStage.ENTRY_ACCESS_DEFERRED
        );
        List<CaveniaGeneratorRuntimeOperationContract> contracts = CaveniaGeneratorRuntimeContracts.contracts();

        assertEquals(expectedOperations, CaveniaGeneratorRuntimeContracts.operations());
        assertEquals(9, contracts.size());
        assertEquals(expectedStages, contracts.stream().map(CaveniaGeneratorRuntimeOperationContract::scaffoldStage).toList());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourcePolicyName() != null && !contract.sourcePolicyName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeImplemented()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.canCreateChunks()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.canMutatePrimer()));
        assertTrue(CaveniaGeneratorRuntimeContracts.contractFor(CaveniaGeneratorRuntimeOperation.IDENTITY).isPresent());
        assertEquals(
            "CavernDimensions / CavernNeoForgeDimensions",
            CaveniaGeneratorRuntimeContracts.contractFor(CaveniaGeneratorRuntimeOperation.IDENTITY).orElseThrow().sourcePolicyName()
        );
        assertEquals(
            "CaveniaPopulationPolicy",
            CaveniaGeneratorRuntimeContracts.contractFor(CaveniaGeneratorRuntimeOperation.POPULATION_INTEGRATION).orElseThrow().sourcePolicyName()
        );
        assertEquals(
            "CaveniaSpawnProviderPolicy",
            CaveniaGeneratorRuntimeContracts.contractFor(CaveniaGeneratorRuntimeOperation.SPAWN_PROVIDER_INTEGRATION_DEFERRED).orElseThrow().sourcePolicyName()
        );
        assertEquals(
            "CaveniaGeneratorRegistrationBoundary / CaveniaGeneratorBridge",
            CaveniaGeneratorRuntimeContracts.contractFor(CaveniaGeneratorRuntimeOperation.ENTRY_ACCESS_DEFERRED).orElseThrow().sourcePolicyName()
        );
        assertTrue(CaveniaGeneratorRuntimeContracts.allRuntimeOperationsBlocked());
        assertFalse(CaveniaGeneratorRuntimeContracts.anyOperationCanCreateChunks());
        assertFalse(CaveniaGeneratorRuntimeContracts.anyOperationCanMutatePrimer());
        assertTrue(CaveniaGeneratorRuntimeContracts.allRequireActivationBeforeRuntime());
        assertTrue(CaveniaGeneratorRuntimeContracts.matchesScaffoldStageOrder());
        assertEquals(CaveniaGeneratorRegistrationBoundary.activationRequirements(), CaveniaGeneratorRuntimeContracts.activationRequirements());
        assertEquals(CaveniaGeneratorRegistrationBoundary.activationBlockedReason(), CaveniaGeneratorRuntimeContracts.activationBlockedReason());
        assertFalse(CaveniaGeneratorRuntimeContracts.generatorSkeletonCanCreateChunks());
        assertFalse(CaveniaGeneratorRuntimeContracts.biomeSelectionIsRuntimeBiomeSource());
        assertEquals(
            List.of(
                CaveniaGeneratorActivationRequirement.DIMENSION_JSON,
                CaveniaGeneratorActivationRequirement.DIMENSION_TYPE_JSON,
                CaveniaGeneratorActivationRequirement.CHUNK_GENERATOR_IMPLEMENTATION,
                CaveniaGeneratorActivationRequirement.BIOME_SOURCE_STRATEGY,
                CaveniaGeneratorActivationRequirement.GENERATOR_CODEC,
                CaveniaGeneratorActivationRequirement.GENERATOR_REGISTRY_ENTRY,
                CaveniaGeneratorActivationRequirement.SAFE_ACCESS_OR_TELEPORT,
                CaveniaGeneratorActivationRequirement.CAVENIA_ONLY_SPAWN_HOST,
                CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING
            ),
            CaveniaGeneratorRuntimeContracts.activationRequirements()
        );

        assertImmutableList(CaveniaGeneratorRuntimeContracts.contracts(), new Object());
        assertImmutableList(CaveniaGeneratorRuntimeContracts.operations(), CaveniaGeneratorRuntimeOperation.IDENTITY);
        contracts.forEach(contract -> {
            assertNotNull(contract.operation());
            assertNotNull(contract.scaffoldStage());
        });
    }

    @Test
    void runtimeContractSourcesStayOutOfRuntimeGeneratorPaths() throws IOException {
        String operationsSource = Files.readString(OPERATIONS_SOURCE);
        String contractSource = Files.readString(CONTRACT_SOURCE);
        String contractsSource = Files.readString(CONTRACTS_SOURCE);

        assertFalse(operationsSource.contains("extends ChunkGenerator"));
        assertFalse(operationsSource.contains("extends BiomeSource"));
        assertFalse(operationsSource.contains("MapCodec"));
        assertFalse(operationsSource.contains("Registry.register"));

        assertFalse(contractSource.contains("extends ChunkGenerator"));
        assertFalse(contractSource.contains("extends BiomeSource"));
        assertFalse(contractSource.contains("MapCodec"));
        assertFalse(contractSource.contains("Registry.register"));

        assertFalse(contractsSource.contains("extends ChunkGenerator"));
        assertFalse(contractsSource.contains("extends BiomeSource"));
        assertFalse(contractsSource.contains("MapCodec"));
        assertFalse(contractsSource.contains("Registry.register"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.equals("CaveniaChunkGenerator.java")
                            || name.equals("ChunkGeneratorCavenia.java")
                            || name.equals("CaveniaBiomeSource.java")
                            || name.equals("MapGenCaveniaCaves.java")
                    ),
                "Expected the non-registered runtime-contract MVP to avoid adding any active Cavenia generator, biome-source or cave-carver runtime classes"
            );
        }
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)), "Expected file to be absent: " + Path.of(first, more));
    }

    @SuppressWarnings("unchecked")
    private static void assertImmutableList(List<?> list, Object value) {
        List<Object> mutableView = (List<Object>) list;

        try {
            mutableView.add(value);
        } catch (UnsupportedOperationException expected) {
            return;
        }

        throw new AssertionError("Expected list to be immutable");
    }

    private static Path resolveProjectPathOrSibling(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || Files.exists(candidate.getParent())) {
                return candidate;
            }
            current = current.getParent();
        }

        return Path.of(first, more).toAbsolutePath();
    }
}
