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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyContracts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaDimensionResourceContracts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaDimensionResourceRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaDimensionResourceRequirementContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorActivationRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorHostContracts;

class CaveniaDimensionResourceContractsTest {
    private static final Path REQUIREMENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaDimensionResourceRequirement.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaDimensionResourceRequirementContract.java"
    );
    private static final Path CONTRACTS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaDimensionResourceContracts.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void dimensionResourceContractsExposeOnlyBlockedRequirementFacts() {
        List<CaveniaDimensionResourceRequirement> expectedRequirements = List.of(
            CaveniaDimensionResourceRequirement.DIMENSION_JSON_SHAPE,
            CaveniaDimensionResourceRequirement.DIMENSION_TYPE_JSON_SHAPE,
            CaveniaDimensionResourceRequirement.HEIGHT_AND_LOGICAL_HEIGHT,
            CaveniaDimensionResourceRequirement.GENERATOR_BINDING,
            CaveniaDimensionResourceRequirement.BIOME_SOURCE_BINDING,
            CaveniaDimensionResourceRequirement.RESOURCE_ACTIVATION_GUARD
        );
        List<CaveniaDimensionResourceRequirementContract> contracts = CaveniaDimensionResourceContracts.contracts();

        assertEquals(expectedRequirements, CaveniaDimensionResourceContracts.requirements());
        assertEquals(6, contracts.size());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourcePolicyName() != null && !contract.sourcePolicyName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertEquals(
            CaveniaGeneratorActivationRequirement.DIMENSION_JSON,
            CaveniaDimensionResourceContracts.contractFor(CaveniaDimensionResourceRequirement.DIMENSION_JSON_SHAPE)
                .orElseThrow()
                .activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.DIMENSION_TYPE_JSON,
            CaveniaDimensionResourceContracts.contractFor(CaveniaDimensionResourceRequirement.DIMENSION_TYPE_JSON_SHAPE)
                .orElseThrow()
                .activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.DIMENSION_TYPE_JSON,
            CaveniaDimensionResourceContracts.contractFor(CaveniaDimensionResourceRequirement.HEIGHT_AND_LOGICAL_HEIGHT)
                .orElseThrow()
                .activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.GENERATOR_REGISTRY_ENTRY,
            CaveniaDimensionResourceContracts.contractFor(CaveniaDimensionResourceRequirement.GENERATOR_BINDING)
                .orElseThrow()
                .activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.BIOME_SOURCE_STRATEGY,
            CaveniaDimensionResourceContracts.contractFor(CaveniaDimensionResourceRequirement.BIOME_SOURCE_BINDING)
                .orElseThrow()
                .activationRequirement()
        );
        assertEquals(
            CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
            CaveniaDimensionResourceContracts.contractFor(CaveniaDimensionResourceRequirement.RESOURCE_ACTIVATION_GUARD)
                .orElseThrow()
                .activationRequirement()
        );
        assertTrue(contracts.stream().allMatch(contract -> !contract.ready()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.resourcePresent()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeBound()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.canCreateLevel()));
        assertTrue(CaveniaDimensionResourceContracts.allRequirementsBlocked());
        assertFalse(CaveniaDimensionResourceContracts.anyRequirementReady());
        assertFalse(CaveniaDimensionResourceContracts.anyResourcePresent());
        assertFalse(CaveniaDimensionResourceContracts.anyRuntimeBound());
        assertFalse(CaveniaDimensionResourceContracts.canCreateLevel());
        assertFalse(CaveniaDimensionResourceContracts.dimensionJsonPresent());
        assertFalse(CaveniaDimensionResourceContracts.dimensionTypeJsonPresent());
        assertFalse(CaveniaDimensionResourceContracts.dimensionResourceHostReady());
        assertFalse(CaveniaDimensionResourceContracts.generatorHostReady());
        assertFalse(CaveniaDimensionResourceContracts.biomeSourceStrategyHostReady());
        assertFalse(CaveniaGeneratorHostContracts.generatorHostReady());
        assertFalse(CaveniaBiomeSourceStrategyContracts.biomeSourceStrategyHostReady());

        assertImmutableList(CaveniaDimensionResourceContracts.contracts(), new Object());
        assertImmutableList(CaveniaDimensionResourceContracts.requirements(), CaveniaDimensionResourceRequirement.DIMENSION_JSON_SHAPE);
        contracts.forEach(contract -> {
            assertNotNull(contract.requirement());
            assertNotNull(contract.activationRequirement());
        });
    }

    @Test
    void dimensionResourceContractSourcesStayOutOfRuntimeLevelPaths() throws IOException {
        String requirementSource = Files.readString(REQUIREMENT_SOURCE);
        String contractSource = Files.readString(CONTRACT_SOURCE);
        String contractsSource = Files.readString(CONTRACTS_SOURCE);

        assertSourceStaysInert(requirementSource);
        assertSourceStaysInert(contractSource);
        assertSourceStaysInert(contractsSource);

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
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");

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
                            || name.equals("CaveniaSpawnHandler.java")
                            || name.equals("CaveniaServerTickSpawner.java")
                    ),
                "Expected the dimension-resource split-contract MVP to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("ITeleporter"));
        assertFalse(source.contains("changeDimension("));
        assertFalse(source.contains(".teleportTo("));
        assertFalse(source.contains("addFreshEntity("));
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
