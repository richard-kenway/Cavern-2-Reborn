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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRegistryLookupReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRegistryLookupRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRegistryLookupRequirementContract;

class CaveniaRegistryLookupReadinessTest {
    private static final Path REQUIREMENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRegistryLookupRequirement.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRegistryLookupRequirementContract.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRegistryLookupReadiness.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void registryLookupReadinessPinsBlockedAdapterLookupPrerequisites() {
        List<CaveniaRegistryLookupRequirement> expectedRequirements = List.of(
            CaveniaRegistryLookupRequirement.CANDIDATE_KEYS_INVENTORIED,
            CaveniaRegistryLookupRequirement.CANDIDATE_KEYS_STILL_STRING_ONLY,
            CaveniaRegistryLookupRequirement.REGISTRY_ACCESS_SOURCE_DECISION,
            CaveniaRegistryLookupRequirement.BIOME_REGISTRY_REFERENCE,
            CaveniaRegistryLookupRequirement.RESOURCE_KEY_CONVERSION,
            CaveniaRegistryLookupRequirement.HOLDER_RESOLUTION,
            CaveniaRegistryLookupRequirement.MISSING_BIOME_FALLBACK_DECISION,
            CaveniaRegistryLookupRequirement.RUNTIME_LOOKUP_CONTEXT,
            CaveniaRegistryLookupRequirement.ADAPTER_RESULT_TO_RUNTIME_BIOME
        );
        List<CaveniaRegistryLookupRequirementContract> contracts = CaveniaRegistryLookupReadiness.contracts();

        assertEquals(expectedRequirements, CaveniaRegistryLookupReadiness.requirements());
        assertEquals(9, contracts.size());
        assertEquals(9, CaveniaRegistryLookupReadiness.requirementCount());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourceContractName() != null && !contract.sourceContractName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertEquals(
            2L,
            contracts.stream().filter(CaveniaRegistryLookupRequirementContract::prerequisiteSatisfied).count()
        );
        assertTrue(
            CaveniaRegistryLookupReadiness.contractFor(CaveniaRegistryLookupRequirement.CANDIDATE_KEYS_INVENTORIED)
                .orElseThrow()
                .prerequisiteSatisfied()
        );
        assertTrue(
            CaveniaRegistryLookupReadiness.contractFor(CaveniaRegistryLookupRequirement.CANDIDATE_KEYS_STILL_STRING_ONLY)
                .orElseThrow()
                .prerequisiteSatisfied()
        );
        contracts.stream()
            .filter(contract ->
                contract.requirement() != CaveniaRegistryLookupRequirement.CANDIDATE_KEYS_INVENTORIED
                    && contract.requirement() != CaveniaRegistryLookupRequirement.CANDIDATE_KEYS_STILL_STRING_ONLY
            )
            .forEach(contract -> assertFalse(contract.prerequisiteSatisfied()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.readyForRuntime()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.registryLookupAvailable()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.registryVerified()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.runtimeBiomeResolved()));
        assertTrue(contracts.stream().allMatch(contract -> !contract.activationAllowedInThisSlice()));
        assertTrue(CaveniaRegistryLookupReadiness.candidateKeysInventoried());
        assertTrue(CaveniaRegistryLookupReadiness.candidateKeysStillStringOnly());
        assertTrue(CaveniaRegistryLookupReadiness.registryLookupReadinessReady());
        assertFalse(CaveniaRegistryLookupReadiness.registryLookupRuntimeReady());
        assertTrue(CaveniaRegistryLookupReadiness.allRequirementsRuntimeBlocked());
        assertFalse(CaveniaRegistryLookupReadiness.anyRequirementReadyForRuntime());
        assertFalse(CaveniaRegistryLookupReadiness.anyRegistryLookupAvailable());
        assertFalse(CaveniaRegistryLookupReadiness.anyRegistryVerified());
        assertFalse(CaveniaRegistryLookupReadiness.anyRuntimeBiomeResolved());
        assertFalse(CaveniaRegistryLookupReadiness.registryAccessSourceReady());
        assertFalse(CaveniaRegistryLookupReadiness.biomeRegistryReferenceReady());
        assertFalse(CaveniaRegistryLookupReadiness.resourceKeyConversionReady());
        assertFalse(CaveniaRegistryLookupReadiness.holderResolutionReady());
        assertFalse(CaveniaRegistryLookupReadiness.missingBiomeFallbackReady());
        assertFalse(CaveniaRegistryLookupReadiness.runtimeLookupContextReady());
        assertFalse(CaveniaRegistryLookupReadiness.adapterResultToRuntimeBiomeReady());
        assertTrue(CaveniaRegistryLookupReadiness.adapterShapeReady());
        assertFalse(CaveniaRegistryLookupReadiness.adapterRuntimeReady());
        assertTrue(CaveniaRegistryLookupReadiness.codecRegistrationReadinessReady());
        assertFalse(CaveniaRegistryLookupReadiness.codecRegistrationRuntimeReady());
        assertFalse(CaveniaRegistryLookupReadiness.runtimeBiomeSourceReady());
        assertFalse(CaveniaRegistryLookupReadiness.runtimeBiomeSourceRegistered());
        assertFalse(CaveniaRegistryLookupReadiness.codecRegistered());
        assertFalse(CaveniaRegistryLookupReadiness.registryLookupAccessReady());
        assertFalse(CaveniaRegistryLookupReadiness.modernBiomeMappingReady());
        assertFalse(CaveniaRegistryLookupReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaRegistryLookupReadiness.canActivateCaveniaNow());
        assertEquals(14, CaveniaRegistryLookupReadiness.candidateEntryCount());
        assertEquals(14, CaveniaRegistryLookupReadiness.adapterEntryCount());
        assertEquals(675, CaveniaRegistryLookupReadiness.adapterTotalWeight());
        assertFalse(CaveniaRegistryLookupReadiness.dimensionJsonPresent());
        assertFalse(CaveniaRegistryLookupReadiness.dimensionTypeJsonPresent());
        assertTrue(CaveniaRegistryLookupReadiness.cavemanRemainsDeferred());

        assertImmutableList(CaveniaRegistryLookupReadiness.contracts(), new Object());
        assertImmutableList(
            CaveniaRegistryLookupReadiness.requirements(),
            CaveniaRegistryLookupRequirement.CANDIDATE_KEYS_INVENTORIED
        );
        contracts.forEach(contract -> assertNotNull(contract.requirement()));
    }

    @Test
    void registryLookupReadinessSourcesStayOutOfRuntimeLookupAndBiomeResolutionPaths() throws IOException {
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
                "Expected the registry-lookup readiness slice to avoid adding active Cavenia runtime classes or resources"
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
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("ResourceKey<Biome>"));
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
