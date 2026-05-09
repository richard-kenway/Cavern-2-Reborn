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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaActivationReadinessHosts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorHostContracts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorHostRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorHostRequirementContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRegistrationBoundary;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRuntimeOperation;

class CaveniaGeneratorHostContractsTest {
    private static final Path REQUIREMENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaGeneratorHostRequirement.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaGeneratorHostRequirementContract.java"
    );
    private static final Path CONTRACTS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaGeneratorHostContracts.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void generatorHostContractsExposeOnlyBlockedRequirementFacts() {
        List<CaveniaGeneratorHostRequirement> expectedRequirements = List.of(
            CaveniaGeneratorHostRequirement.RUNTIME_CONTEXT,
            CaveniaGeneratorHostRequirement.BASE_TERRAIN_FILL,
            CaveniaGeneratorHostRequirement.CAVE_CARVER_EXECUTION,
            CaveniaGeneratorHostRequirement.BIOME_TOP_FILTER_EXECUTION,
            CaveniaGeneratorHostRequirement.VEINS_MUTATION_EXECUTION,
            CaveniaGeneratorHostRequirement.FINAL_CHUNK_CONSTRUCTION,
            CaveniaGeneratorHostRequirement.POPULATION_DELEGATION,
            CaveniaGeneratorHostRequirement.CODEC_AND_REGISTRATION,
            CaveniaGeneratorHostRequirement.DIMENSION_RESOURCE_BINDING
        );
        List<CaveniaGeneratorHostRequirementContract> contracts = CaveniaGeneratorHostContracts.contracts();

        assertEquals(expectedRequirements, CaveniaGeneratorHostContracts.requirements());
        assertEquals(9, contracts.size());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourcePolicyName() != null && !contract.sourcePolicyName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertEquals(
            CaveniaGeneratorRuntimeOperation.IDENTITY,
            CaveniaGeneratorHostContracts.contractFor(CaveniaGeneratorHostRequirement.RUNTIME_CONTEXT).orElseThrow().runtimeOperation()
        );
        assertEquals(
            CaveniaGeneratorRuntimeOperation.BASE_TERRAIN_FILL,
            CaveniaGeneratorHostContracts.contractFor(CaveniaGeneratorHostRequirement.BASE_TERRAIN_FILL).orElseThrow().runtimeOperation()
        );
        assertEquals(
            CaveniaGeneratorRuntimeOperation.CAVE_CARVING,
            CaveniaGeneratorHostContracts.contractFor(CaveniaGeneratorHostRequirement.CAVE_CARVER_EXECUTION).orElseThrow().runtimeOperation()
        );
        assertEquals(
            CaveniaGeneratorRuntimeOperation.BIOME_TOP_FILTER_REPLACEMENT,
            CaveniaGeneratorHostContracts.contractFor(CaveniaGeneratorHostRequirement.BIOME_TOP_FILTER_EXECUTION).orElseThrow().runtimeOperation()
        );
        assertEquals(
            CaveniaGeneratorRuntimeOperation.VEINS_MUTATION,
            CaveniaGeneratorHostContracts.contractFor(CaveniaGeneratorHostRequirement.VEINS_MUTATION_EXECUTION).orElseThrow().runtimeOperation()
        );
        assertEquals(
            CaveniaGeneratorRuntimeOperation.FINAL_CHUNK_CONSTRUCTION,
            CaveniaGeneratorHostContracts.contractFor(CaveniaGeneratorHostRequirement.FINAL_CHUNK_CONSTRUCTION).orElseThrow().runtimeOperation()
        );
        assertEquals(
            CaveniaGeneratorRuntimeOperation.POPULATION_INTEGRATION,
            CaveniaGeneratorHostContracts.contractFor(CaveniaGeneratorHostRequirement.POPULATION_DELEGATION).orElseThrow().runtimeOperation()
        );
        assertEquals(
            CaveniaGeneratorRuntimeOperation.IDENTITY,
            CaveniaGeneratorHostContracts.contractFor(CaveniaGeneratorHostRequirement.CODEC_AND_REGISTRATION).orElseThrow().runtimeOperation()
        );
        assertEquals(
            CaveniaGeneratorRuntimeOperation.IDENTITY,
            CaveniaGeneratorHostContracts.contractFor(CaveniaGeneratorHostRequirement.DIMENSION_RESOURCE_BINDING).orElseThrow().runtimeOperation()
        );
        assertTrue(contracts.stream().allMatch(contract -> !contract.ready()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeImplemented()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.canCreateChunks()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.canMutatePrimer()));
        assertTrue(CaveniaGeneratorHostContracts.allRequirementsBlocked());
        assertFalse(CaveniaGeneratorHostContracts.anyRequirementReady());
        assertFalse(CaveniaGeneratorHostContracts.anyRequirementRuntimeImplemented());
        assertFalse(CaveniaGeneratorHostContracts.canCreateChunks());
        assertFalse(CaveniaGeneratorHostContracts.canMutatePrimer());
        assertTrue(CaveniaGeneratorHostContracts.runtimeOperationsBlocked());
        assertFalse(CaveniaGeneratorHostContracts.generatorSkeletonCanCreateChunks());
        assertFalse(CaveniaGeneratorHostContracts.generatorHostReady());
        assertFalse(CaveniaActivationReadinessHosts.generatorHostReady());
        assertEquals(CaveniaGeneratorRegistrationBoundary.activationRequirements(), CaveniaGeneratorHostContracts.activationRequirements());
        assertTrue(CaveniaGeneratorHostContracts.activationBlockedReason().contains("dimension JSON"));
        assertTrue(CaveniaGeneratorHostContracts.activationBlockedReason().contains("dimension type JSON"));
        assertTrue(CaveniaGeneratorHostContracts.activationBlockedReason().contains("chunk generator implementation"));
        assertTrue(CaveniaGeneratorHostContracts.activationBlockedReason().contains("biome source strategy"));
        assertTrue(CaveniaGeneratorHostContracts.activationBlockedReason().contains("generator codec/registration"));
        assertTrue(CaveniaGeneratorHostContracts.activationBlockedReason().contains("safe access"));
        assertTrue(CaveniaGeneratorHostContracts.activationBlockedReason().contains("Cavenia-only spawn host"));

        assertImmutableList(CaveniaGeneratorHostContracts.contracts(), new Object());
        assertImmutableList(CaveniaGeneratorHostContracts.requirements(), CaveniaGeneratorHostRequirement.RUNTIME_CONTEXT);
        contracts.forEach(contract -> {
            assertNotNull(contract.requirement());
            assertNotNull(contract.runtimeOperation());
        });
    }

    @Test
    void generatorHostContractSourcesStayOutOfRuntimeGeneratorPaths() throws IOException {
        String requirementSource = Files.readString(REQUIREMENT_SOURCE);
        String contractSource = Files.readString(CONTRACT_SOURCE);
        String contractsSource = Files.readString(CONTRACTS_SOURCE);

        assertFalse(requirementSource.contains("extends ChunkGenerator"));
        assertFalse(requirementSource.contains("extends BiomeSource"));
        assertFalse(requirementSource.contains("MapCodec"));
        assertFalse(requirementSource.contains("Registry.register"));

        assertFalse(contractSource.contains("extends ChunkGenerator"));
        assertFalse(contractSource.contains("extends BiomeSource"));
        assertFalse(contractSource.contains("MapCodec"));
        assertFalse(contractSource.contains("Registry.register"));

        assertFalse(contractsSource.contains("extends ChunkGenerator"));
        assertFalse(contractsSource.contains("extends BiomeSource"));
        assertFalse(contractsSource.contains("MapCodec"));
        assertFalse(contractsSource.contains("Registry.register"));
        assertFalse(contractsSource.contains("CaveniaTeleporter"));
        assertFalse(contractsSource.contains("CaveniaSpawnProvider"));

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
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");

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
                            || name.equals("CaveniaTeleporter.java")
                            || name.equals("CaveniaSpawnProvider.java")
                    ),
                "Expected the generator-host split-contract MVP to avoid adding active Cavenia generator, biome-source, teleport or spawn-host runtime classes"
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
