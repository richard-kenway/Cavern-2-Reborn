package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRealSubclassGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRealSubclassNextStepDecision;

class CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecisionTest {
    private static final Path DECISION_ENUM_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceRealSubclassNextStepDecision.java"
    );
    private static final Path GUARDRAIL_ENUM_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceRealSubclassGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void realSubclassGoNoGoDecisionPinsTheSingleGuardedSubclassDirection() {
        List<CaveniaRuntimeBiomeSourceRealSubclassGuardrail> expectedGuardrails = List.of(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_ONE_DESIGNATED_REAL_SUBCLASS_FILE_NEXT,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_BIOME_SOURCE_EXTENDS_ONLY_IN_DESIGNATED_SUBCLASS,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_ABSTRACT_METHOD_SIGNATURE_TYPES_ONLY,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_UNSUPPORTED_METHOD_STUBS_ONLY,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_USABLE_CODEC_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_CODEC_REGISTRATION,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_BIOME_SOURCE_TYPE_REGISTRATION,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_HOLDER_RESOURCE_KEY_CONVERSION_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_DIMENSION_JSON,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_DIMENSION_TYPE_JSON,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_ACTIVE_CAVENIA_LEVEL,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_WORLDGEN_RESOURCES,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_ACCESS_OR_TELEPORT,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_SPAWNING,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.NO_CAVEMAN_ENTITY_REGISTRATION,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.KEEP_SUBCLASS_UNREGISTERED_AND_UNREFERENCED_BY_RUNTIME,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.KEEP_EXISTING_SKELETON_AND_API_INVENTORY_INTACT
        );
        Set<CaveniaRuntimeBiomeSourceRealSubclassGuardrail> allowedNextSliceActionGuardrails = Set.of(
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_ONE_DESIGNATED_REAL_SUBCLASS_FILE_NEXT,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_BIOME_SOURCE_EXTENDS_ONLY_IN_DESIGNATED_SUBCLASS,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_ABSTRACT_METHOD_SIGNATURE_TYPES_ONLY,
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_UNSUPPORTED_METHOD_STUBS_ONLY
        );
        List<CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.guardrails();

        assertEquals(
            CaveniaRuntimeBiomeSourceRealSubclassNextStepDecision
                .PROCEED_WITH_GUARDED_UNREGISTERED_REAL_BIOME_SOURCE_SUBCLASS_NEXT,
            CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.selectedDecision()
        );
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.decisionIsGoForGuardedUnregisteredRealSubclassNext());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.decisionIsImplementationOnlyForNextSlice());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.realSubclassImplementedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.runtimeApiAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.canActivateCaveniaNow());
        assertEquals(expectedGuardrails, CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.guardrailValues());
        assertEquals(18, CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.guardrailCount());
        assertTrue(guardrails.stream().allMatch(contract -> contract.sourceContractName() != null && !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        guardrails.forEach(contract -> {
            assertTrue(contract.enforcedInThisSlice());
            assertEquals(
                allowedNextSliceActionGuardrails.contains(contract.guardrail()),
                contract.allowsNextSliceAction()
            );
            assertFalse(contract.runtimeActivationAllowed());
            assertNotNull(contract.guardrail());
        });

        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.allGuardrailsEnforcedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.anyRuntimeActivationAllowedByGuardrails());
        assertEquals(4, CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.allowedNextSliceActionCount());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddOneDesignatedRealSubclassFile());
        assertEquals(
            "CaveniaRuntimeBiomeSource",
            CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceDesignatedSubclassSimpleName()
        );
        assertEquals(
            "CaveniaRuntimeBiomeSource.java",
            CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceDesignatedSubclassFileName()
        );
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayExtendBiomeSourceOnlyInDesignatedSubclass());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayUseAbstractMethodSignatureTypesOnly());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayUseUnsupportedMethodStubsOnly());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddUsableCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayRegisterCodec());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayRegisterBiomeSourceType());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayUseRegistryLookupAccess());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayImplementHolderResourceKeyConversion());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddDimensionJson());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddDimensionTypeJson());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayCreateActiveCaveniaLevel());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddWorldgenResources());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddAccessOrTeleport());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddSpawning());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayRegisterCavemanEntity());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.apiShapeInventoryReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.apiShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.skeletonReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.skeletonRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.subclassDecisionApiShapePinned());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.subclassDecisionReadyForRealSubclass());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.guardedRealSubclassStubImplemented());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.guardedRealSubclassRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.codecMethodShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.holderConversionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.readinessChainRuntimeReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.runtimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.runtimeBiomeSourceRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.cavemanRemainsDeferred());

        assertImmutableList(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.guardrails(), guardrails.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.guardrailValues(),
            CaveniaRuntimeBiomeSourceRealSubclassGuardrail.ALLOW_ONE_DESIGNATED_REAL_SUBCLASS_FILE_NEXT
        );
    }

    @Test
    void realSubclassGoNoGoDecisionSourcesStayOutOfRuntimeImplementationPaths() throws IOException {
        String decisionEnumSource = Files.readString(DECISION_ENUM_SOURCE);
        String guardrailEnumSource = Files.readString(GUARDRAIL_ENUM_SOURCE);
        String guardrailContractSource = Files.readString(GUARDRAIL_CONTRACT_SOURCE);
        String decisionSource = Files.readString(DECISION_SOURCE);

        assertSourceStaysInert(decisionEnumSource);
        assertSourceStaysInert(guardrailEnumSource);
        assertSourceStaysInert(guardrailContractSource);
        assertSourceStaysInert(decisionSource);

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertTrue(
            Files.exists(resolveProjectPathOrSibling(
                "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
                "CaveniaRuntimeBiomeSource.java"
            ))
        );
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
                "Expected the real-subclass go/no-go decision layer sources to stay inert while the later designated subclass stub exists separately"
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
