package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSubclassDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSubclassNextStepDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSubclassRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSubclassRequirementContract;

class CaveniaRuntimeBiomeSourceSubclassDecisionTest {
    private static final Path DECISION_ENUM_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSubclassNextStepDecision.java"
    );
    private static final Path REQUIREMENT_ENUM_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSubclassRequirement.java"
    );
    private static final Path REQUIREMENT_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSubclassRequirementContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSubclassDecision.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void subclassDecisionDefersTheRealSubclassUntilApiShapeWorkIsPinned() {
        List<CaveniaRuntimeBiomeSourceSubclassRequirement> expectedRequirements = List.of(
            CaveniaRuntimeBiomeSourceSubclassRequirement.SKELETON_READY,
            CaveniaRuntimeBiomeSourceSubclassRequirement.REAL_SUBCLASS_DEFERRED,
            CaveniaRuntimeBiomeSourceSubclassRequirement.ABSTRACT_METHODS_INVENTORY,
            CaveniaRuntimeBiomeSourceSubclassRequirement.CODEC_METHOD_SHAPE_DECISION,
            CaveniaRuntimeBiomeSourceSubclassRequirement.POSSIBLE_BIOMES_SHAPE_DECISION,
            CaveniaRuntimeBiomeSourceSubclassRequirement.NOISE_BIOME_QUERY_SHAPE_DECISION,
            CaveniaRuntimeBiomeSourceSubclassRequirement.HOLDER_RETURN_PATH_DECISION,
            CaveniaRuntimeBiomeSourceSubclassRequirement.REGISTRY_CONTEXT_DECISION,
            CaveniaRuntimeBiomeSourceSubclassRequirement.RESOURCE_KEY_CONVERSION_DECISION,
            CaveniaRuntimeBiomeSourceSubclassRequirement.MISSING_BIOME_FALLBACK_DECISION,
            CaveniaRuntimeBiomeSourceSubclassRequirement.NO_DIMENSION_ACTIVATION,
            CaveniaRuntimeBiomeSourceSubclassRequirement.NO_CODEC_REGISTRATION,
            CaveniaRuntimeBiomeSourceSubclassRequirement.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceSubclassRequirement.NO_WORLDGEN_ACCESS_SPAWNING
        );
        Set<CaveniaRuntimeBiomeSourceSubclassRequirement> pinnedRequirements = EnumSet.allOf(
            CaveniaRuntimeBiomeSourceSubclassRequirement.class
        );
        List<CaveniaRuntimeBiomeSourceSubclassRequirementContract> contracts =
            CaveniaRuntimeBiomeSourceSubclassDecision.contracts();

        assertEquals(
            CaveniaRuntimeBiomeSourceSubclassNextStepDecision
                .DEFER_REAL_BIOME_SOURCE_SUBCLASS_UNTIL_CODEC_HOLDER_REGISTRY_SHAPE_IS_PINNED,
            CaveniaRuntimeBiomeSourceSubclassDecision.selectedDecision()
        );
        assertEquals(
            CaveniaRuntimeBiomeSourceSubclassNextStepDecision.PROCEED_WITH_CODEC_HOLDER_REGISTRY_SHAPE_DECISION_NEXT,
            CaveniaRuntimeBiomeSourceSubclassDecision.allowedNextDecision()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.realSubclassDeferred());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.codecHolderRegistryShapeDecisionIsNext());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.realMinecraftBiomeSourceImplementedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.runtimeApiAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.canActivateCaveniaNow());
        assertEquals(expectedRequirements, CaveniaRuntimeBiomeSourceSubclassDecision.requirements());
        assertEquals(14, CaveniaRuntimeBiomeSourceSubclassDecision.requirementCount());
        assertTrue(contracts.stream().allMatch(contract -> contract.sourceContractName() != null && !contract.sourceContractName().isBlank()));
        assertTrue(contracts.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        contracts.forEach(contract -> {
            assertEquals(pinnedRequirements.contains(contract.requirement()), contract.prerequisiteSatisfied());
            assertEquals(pinnedRequirements.contains(contract.requirement()), contract.decisionPinned());
            assertFalse(contract.readyForRealSubclass());
            assertFalse(contract.runtimeApiAllowedInThisSlice());
            assertFalse(contract.activationAllowedInThisSlice());
            assertNotNull(contract.requirement());
        });

        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.skeletonReady());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.skeletonRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.allHardGuardrailsStillPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.apiShapeDecisionsPinned());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.readyForRealSubclass());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.abstractMethodsInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.codecMethodShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.possibleBiomesShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.noiseBiomeQueryShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.holderReturnPathReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.registryContextReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.resourceKeyConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.missingBiomeFallbackReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayInventoryRuntimeBiomeSourceApiShape());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.runtimeBiomeSourceApiShapeInventoryReady());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.runtimeBiomeSourceApiShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.realSubclassGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.realSubclassGoNoGoRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayImplementRealBiomeSourceSubclass());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayAddCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayRegisterCodec());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayUseRegistryLookupAccess());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayAddDimensionJson());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayAddDimensionTypeJson());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayCreateActiveCaveniaLevel());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayAddWorldgenResources());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayAddAccessOrTeleport());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayAddSpawning());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.nextSliceMayRegisterCavemanEntity());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.skeletonShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.skeletonConsumesAdapter());
        assertEquals(68, CaveniaRuntimeBiomeSourceSubclassDecision.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSubclassDecision.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSubclassDecision.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.runtimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.runtimeBiomeSourceRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.cavemanRemainsDeferred());

        assertImmutableList(CaveniaRuntimeBiomeSourceSubclassDecision.contracts(), new Object());
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSubclassDecision.requirements(),
            CaveniaRuntimeBiomeSourceSubclassRequirement.SKELETON_READY
        );
    }

    @Test
    void subclassDecisionSourcesStayOutOfRuntimeBiomeSourceApisAndRegistrationPaths() throws IOException {
        String decisionEnumSource = Files.readString(DECISION_ENUM_SOURCE);
        String requirementEnumSource = Files.readString(REQUIREMENT_ENUM_SOURCE);
        String requirementContractSource = Files.readString(REQUIREMENT_CONTRACT_SOURCE);
        String decisionSource = Files.readString(DECISION_SOURCE);

        assertSourceStaysInert(decisionEnumSource);
        assertSourceStaysInert(requirementEnumSource);
        assertSourceStaysInert(requirementContractSource);
        assertSourceStaysInert(decisionSource);

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
                "Expected the runtime-biome-source subclass decision layer to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
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
