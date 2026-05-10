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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaAdapterCodecRegistrationReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaAdapterCodecRegistrationRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaAdapterCodecRegistrationRequirementContract;

class CaveniaAdapterCodecRegistrationReadinessTest {
    private static final Path REQUIREMENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaAdapterCodecRegistrationRequirement.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaAdapterCodecRegistrationRequirementContract.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaAdapterCodecRegistrationReadiness.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void adapterCodecRegistrationReadinessPinsBlockedUnregisteredRuntimePrerequisites() {
        List<CaveniaAdapterCodecRegistrationRequirement> expectedRequirements = List.of(
            CaveniaAdapterCodecRegistrationRequirement.ADAPTER_SHAPE_AVAILABLE,
            CaveniaAdapterCodecRegistrationRequirement.SERIALIZATION_MODEL_DECISION,
            CaveniaAdapterCodecRegistrationRequirement.CODEC_SHAPE_DECISION,
            CaveniaAdapterCodecRegistrationRequirement.CODEC_IMPLEMENTATION,
            CaveniaAdapterCodecRegistrationRequirement.BIOME_SOURCE_TYPE_KEY,
            CaveniaAdapterCodecRegistrationRequirement.BIOME_SOURCE_TYPE_REGISTRATION,
            CaveniaAdapterCodecRegistrationRequirement.REGISTRY_LOOKUP_ACCESS,
            CaveniaAdapterCodecRegistrationRequirement.RUNTIME_BIOME_SOURCE_CLASS,
            CaveniaAdapterCodecRegistrationRequirement.DIMENSION_BINDING_DEFERRED
        );
        List<CaveniaAdapterCodecRegistrationRequirementContract> contracts =
            CaveniaAdapterCodecRegistrationReadiness.contracts();

        assertEquals(expectedRequirements, CaveniaAdapterCodecRegistrationReadiness.requirements());
        assertEquals(9, contracts.size());
        assertEquals(9, CaveniaAdapterCodecRegistrationReadiness.requirementCount());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourceContractName() != null && !contract.sourceContractName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertEquals(
            1L,
            contracts.stream().filter(CaveniaAdapterCodecRegistrationRequirementContract::prerequisiteSatisfied).count()
        );
        assertTrue(
            CaveniaAdapterCodecRegistrationReadiness.contractFor(CaveniaAdapterCodecRegistrationRequirement.ADAPTER_SHAPE_AVAILABLE)
                .orElseThrow()
                .prerequisiteSatisfied()
        );
        contracts.stream()
            .filter(contract -> contract.requirement() != CaveniaAdapterCodecRegistrationRequirement.ADAPTER_SHAPE_AVAILABLE)
            .forEach(contract -> assertFalse(contract.prerequisiteSatisfied()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.readyForRuntime()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.codecImplemented()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.registered()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.registryLookupAvailable()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.activationAllowedInThisSlice()));
        assertTrue(CaveniaAdapterCodecRegistrationReadiness.adapterShapeReady());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.adapterRuntimeReady());
        assertTrue(CaveniaAdapterCodecRegistrationReadiness.codecRegistrationReadinessReady());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.codecRegistrationRuntimeReady());
        assertTrue(CaveniaAdapterCodecRegistrationReadiness.allRequirementsRuntimeBlocked());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.anyRequirementReadyForRuntime());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.anyCodecImplemented());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.anyRegistered());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.registryLookupAccessReady());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.runtimeBiomeSourceReady());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.runtimeBiomeSourceRegistered());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.codecRegistered());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.biomeSourceTypeKeyReady());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.dimensionBindingReady());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.canActivateCaveniaNow());
        assertTrue(CaveniaAdapterCodecRegistrationReadiness.consumesAdapterContract());
        assertEquals(14, CaveniaAdapterCodecRegistrationReadiness.adapterEntryCount());
        assertEquals(675, CaveniaAdapterCodecRegistrationReadiness.adapterTotalWeight());
        assertTrue(CaveniaAdapterCodecRegistrationReadiness.weightedSelectionAlgorithmReady());
        assertTrue(CaveniaAdapterCodecRegistrationReadiness.candidateInventoryReady());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.dimensionJsonPresent());
        assertFalse(CaveniaAdapterCodecRegistrationReadiness.dimensionTypeJsonPresent());
        assertTrue(CaveniaAdapterCodecRegistrationReadiness.cavemanRemainsDeferred());

        assertImmutableList(CaveniaAdapterCodecRegistrationReadiness.contracts(), new Object());
        assertImmutableList(
            CaveniaAdapterCodecRegistrationReadiness.requirements(),
            CaveniaAdapterCodecRegistrationRequirement.ADAPTER_SHAPE_AVAILABLE
        );
        contracts.forEach(contract -> assertNotNull(contract.requirement()));
    }

    @Test
    void adapterCodecRegistrationReadinessSourcesStayOutOfRuntimeCodecAndRegistrationPaths() throws IOException {
        String requirementSource = Files.readString(REQUIREMENT_SOURCE);
        String contractSource = Files.readString(CONTRACT_SOURCE);
        String readinessSource = Files.readString(READINESS_SOURCE);

        assertSourceStaysInert(requirementSource);
        assertSourceStaysInert(contractSource);
        assertSourceStaysInert(readinessSource);

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
                "Expected the adapter codec-registration readiness slice to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("RegistryLookup"));
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
