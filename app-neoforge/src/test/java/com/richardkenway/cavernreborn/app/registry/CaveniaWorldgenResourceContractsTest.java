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
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorActivationRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorHostContracts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWorldgenResourceContracts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWorldgenResourceRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWorldgenResourceRequirementContract;

class CaveniaWorldgenResourceContractsTest {
    private static final Path REQUIREMENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaWorldgenResourceRequirement.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaWorldgenResourceRequirementContract.java"
    );
    private static final Path CONTRACTS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaWorldgenResourceContracts.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void worldgenResourceContractsExposeOnlyBlockedRequirementFacts() {
        List<CaveniaWorldgenResourceRequirement> expectedRequirements = List.of(
            CaveniaWorldgenResourceRequirement.CONFIGURED_CARVER_MAPPING,
            CaveniaWorldgenResourceRequirement.CONFIGURED_FEATURE_MAPPING,
            CaveniaWorldgenResourceRequirement.PLACED_FEATURE_MAPPING,
            CaveniaWorldgenResourceRequirement.BIOME_MODIFIER_MAPPING,
            CaveniaWorldgenResourceRequirement.BIOME_TAG_MAPPING,
            CaveniaWorldgenResourceRequirement.VEINS_RESOURCE_MAPPING,
            CaveniaWorldgenResourceRequirement.POPULATION_RESOURCE_MAPPING,
            CaveniaWorldgenResourceRequirement.CAVENIC_SHROOM_CAVENIA_MAPPING,
            CaveniaWorldgenResourceRequirement.RESOURCE_ORDERING_GUARD,
            CaveniaWorldgenResourceRequirement.NO_CAVERN_RESOURCE_REUSE_AS_PARITY
        );
        List<CaveniaWorldgenResourceRequirementContract> contracts = CaveniaWorldgenResourceContracts.contracts();

        assertEquals(expectedRequirements, CaveniaWorldgenResourceContracts.requirements());
        assertEquals(10, contracts.size());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourcePolicyName() != null && !contract.sourcePolicyName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        expectedRequirements.forEach(requirement ->
            assertEquals(
                CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING,
                CaveniaWorldgenResourceContracts.contractFor(requirement).orElseThrow().activationRequirement()
            )
        );
        assertTrue(contracts.stream().allMatch(contract -> !contract.ready()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.resourcePresent()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeBound()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.canAffectWorldgen()));
        assertTrue(CaveniaWorldgenResourceContracts.allRequirementsBlocked());
        assertFalse(CaveniaWorldgenResourceContracts.anyRequirementReady());
        assertFalse(CaveniaWorldgenResourceContracts.anyResourcePresent());
        assertFalse(CaveniaWorldgenResourceContracts.anyRuntimeBound());
        assertFalse(CaveniaWorldgenResourceContracts.canAffectWorldgen());
        assertFalse(CaveniaWorldgenResourceContracts.configuredCarverResourcesPresent());
        assertFalse(CaveniaWorldgenResourceContracts.configuredFeatureResourcesPresent());
        assertFalse(CaveniaWorldgenResourceContracts.placedFeatureResourcesPresent());
        assertFalse(CaveniaWorldgenResourceContracts.biomeModifierResourcesPresent());
        assertFalse(CaveniaWorldgenResourceContracts.biomeTagResourcesPresent());
        assertFalse(CaveniaWorldgenResourceContracts.worldgenResourceHostReady());
        assertFalse(CaveniaWorldgenResourceContracts.dimensionResourceHostReady());
        assertFalse(CaveniaWorldgenResourceContracts.generatorHostReady());
        assertFalse(CaveniaWorldgenResourceContracts.biomeSourceStrategyHostReady());
        assertFalse(CaveniaDimensionResourceContracts.dimensionResourceHostReady());
        assertFalse(CaveniaGeneratorHostContracts.generatorHostReady());
        assertFalse(CaveniaBiomeSourceStrategyContracts.biomeSourceStrategyHostReady());
        assertEquals(13, CaveniaWorldgenResourceContracts.veinsPolicyEntryCount());

        assertImmutableList(CaveniaWorldgenResourceContracts.contracts(), new Object());
        assertImmutableList(CaveniaWorldgenResourceContracts.requirements(), CaveniaWorldgenResourceRequirement.CONFIGURED_CARVER_MAPPING);
        contracts.forEach(contract -> {
            assertNotNull(contract.requirement());
            assertNotNull(contract.activationRequirement());
        });
    }

    @Test
    void worldgenResourceContractSourcesStayOutOfRuntimeWorldgenPaths() throws IOException {
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
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
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
                "Expected the worldgen-resource split-contract MVP to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("BiomeModifier"));
        assertFalse(source.contains("registerConfiguredFeature("));
        assertFalse(source.contains("registerPlacedFeature("));
        assertFalse(source.contains("registerConfiguredCarver("));
        assertFalse(source.contains("addFreshEntity("));
        assertFalse(source.contains("changeDimension("));
        assertFalse(source.contains(".teleportTo("));
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
