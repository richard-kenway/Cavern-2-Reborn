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
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyContracts;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyRequirementContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRegistrationBoundary;

class CaveniaBiomeSourceStrategyContractsTest {
    private static final Path REQUIREMENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSourceStrategyRequirement.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSourceStrategyRequirementContract.java"
    );
    private static final Path CONTRACTS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSourceStrategyContracts.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void biomeSourceStrategyContractsExposeOnlyBlockedRequirementFacts() {
        List<CaveniaBiomeSourceStrategyRequirement> expectedRequirements = List.of(
            CaveniaBiomeSourceStrategyRequirement.LEGACY_WEIGHTED_BIOME_INPUTS,
            CaveniaBiomeSourceStrategyRequirement.LEGACY_TO_MODERN_BIOME_KEY_MAPPING,
            CaveniaBiomeSourceStrategyRequirement.WEIGHTED_SELECTION_ALGORITHM,
            CaveniaBiomeSourceStrategyRequirement.TOP_BLOCK_STRATEGY,
            CaveniaBiomeSourceStrategyRequirement.RUNTIME_BIOME_SOURCE_IMPLEMENTATION,
            CaveniaBiomeSourceStrategyRequirement.BIOME_SOURCE_CODEC_AND_REGISTRATION,
            CaveniaBiomeSourceStrategyRequirement.REGISTRY_LOOKUP_ACCESS
        );
        List<CaveniaBiomeSourceStrategyRequirementContract> contracts = CaveniaBiomeSourceStrategyContracts.contracts();

        assertEquals(expectedRequirements, CaveniaBiomeSourceStrategyContracts.requirements());
        assertEquals(7, contracts.size());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourcePolicyName() != null && !contract.sourcePolicyName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.ready()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeImplemented()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeBiomeSource()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.codecRegistered()));
        assertTrue(CaveniaBiomeSourceStrategyContracts.allRequirementsBlocked());
        assertFalse(CaveniaBiomeSourceStrategyContracts.anyRequirementReady());
        assertFalse(CaveniaBiomeSourceStrategyContracts.anyRequirementRuntimeImplemented());
        assertFalse(CaveniaBiomeSourceStrategyContracts.runtimeBiomeSourceReady());
        assertFalse(CaveniaBiomeSourceStrategyContracts.codecRegistered());
        assertFalse(CaveniaBiomeSourceStrategyContracts.biomeSelectionIsRuntimeBiomeSource());
        assertFalse(CaveniaBiomeSourceStrategyContracts.biomeSourceStrategyHostReady());
        assertFalse(CaveniaActivationReadinessHosts.biomeSourceStrategyHostReady());
        assertEquals(14, CaveniaBiomeSourceStrategyContracts.legacyBiomeEntryCount());
        assertEquals(675, CaveniaBiomeSourceStrategyContracts.legacyBiomeTotalWeight());
        assertTrue(CaveniaBiomeSourceStrategyContracts.requiresBiomeSourceStrategyBeforeActivation());
        assertEquals(CaveniaGeneratorRegistrationBoundary.activationRequirements(), CaveniaBiomeSourceStrategyContracts.activationRequirements());
        assertTrue(CaveniaBiomeSourceStrategyContracts.activationBlockedReason().contains("dimension JSON"));
        assertTrue(CaveniaBiomeSourceStrategyContracts.activationBlockedReason().contains("dimension type JSON"));
        assertTrue(CaveniaBiomeSourceStrategyContracts.activationBlockedReason().contains("biome source strategy"));
        assertTrue(CaveniaBiomeSourceStrategyContracts.activationBlockedReason().contains("generator codec/registration"));

        assertImmutableList(CaveniaBiomeSourceStrategyContracts.contracts(), new Object());
        assertImmutableList(
            CaveniaBiomeSourceStrategyContracts.requirements(),
            CaveniaBiomeSourceStrategyRequirement.LEGACY_WEIGHTED_BIOME_INPUTS
        );
        contracts.forEach(contract -> assertNotNull(contract.requirement()));
    }

    @Test
    void biomeSourceStrategySourcesStayOutOfRuntimeBiomeSourcePaths() throws IOException {
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
                "Expected the biome-source-strategy split-contract MVP to avoid adding active Cavenia generator, biome-source, teleport or spawn-host runtime classes"
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
