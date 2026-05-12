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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationNextDecision;

class CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecisionTest {
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationNextDecision.java"
    );
    private static final Path GUARDRAIL_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.java"
    );
    private static final Path DESIGNATED_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSource.java"
    );
    private static final Path APP_WORLDGEN_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void selectorInputDerivationImplementationGoNoGoDecisionPinsPureNonRuntimeAlgorithmGuardrailsWithoutImplementingIt()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail> expectedGuardrails = List.of(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_PURE_NON_RUNTIME_ALGORITHM_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_INTEGER_COORDINATE_INPUTS_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_SIGNED_INT_SELECTOR_OUTPUT_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_EXISTING_SELECTOR_NORMALIZATION_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_CLIMATE_SAMPLER_METHOD_CALLS,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_MINECRAFT_RUNTIME_API_IMPORTS,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_GET_NOISE_BIOME_WIRING,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_HOLDER_RESOLUTION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_RESOURCE_LOCATION_OR_KEY_CONVERSION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_FALLBACK_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_USABLE_CODEC_OR_REGISTRATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_RANDOM_OR_MUTABLE_STATE,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_DIMENSION_ACTIVATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.NO_WORLDGEN_ACCESS_SPAWNING,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.KEEP_READINESS_CHAIN_INERT
        );
        Set<CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail> allowedPureAlgorithmActions = Set.of(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_PURE_NON_RUNTIME_ALGORITHM_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_INTEGER_COORDINATE_INPUTS_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_SIGNED_INT_SELECTOR_OUTPUT_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_EXISTING_SELECTOR_NORMALIZATION_NEXT
        );
        List<CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.guardrails();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationNextDecision
                .PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_INPUT_DERIVATION_ALGORITHM_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectedDecision()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.decisionIsGoForPureNonRuntimeAlgorithmNext());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.decisionIsImplementationOnlyForNextSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationAlgorithmImplementedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.runtimeApiAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.canActivateCaveniaNow());

        assertEquals(expectedGuardrails, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.guardrailValues());
        assertEquals(18, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.guardrailCount());
        assertTrue(guardrails.stream().allMatch(contract -> contract.sourceContractName() != null && !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertTrue(guardrails.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract::enforcedInThisSlice));
        assertTrue(guardrails.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract::blocksActivation));
        assertTrue(guardrails.stream().noneMatch(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrailContract::allowsNextSliceRuntimeAction));
        guardrails.forEach(contract ->
            assertEquals(
                allowedPureAlgorithmActions.contains(contract.guardrail()),
                contract.allowsNextSlicePureAlgorithmAction()
            )
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.allGuardrailsEnforcedInThisSlice());
        assertEquals(4, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.allowedNextSlicePureAlgorithmActionCount());
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.allowedNextSliceRuntimeActionCount());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.anyRuntimeActionAllowedByGuardrails());

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayAddPureNonRuntimeSelectorInputAlgorithm());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayUseIntegerCoordinateInputs());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayReturnSignedIntSelectorInput());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayDelegateNormalizationToExistingSelectorOrAdapter());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayCallClimateSamplerMethods());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayImportMinecraftRuntimeApis());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayWireIntoGetNoiseBiome());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayMakeGetNoiseBiomeUsable());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayMakeCollectPossibleBiomesUsable());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayUseRegistryLookupAccess());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayResolveHolders());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayImplementResourceLocationConversion());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayImplementResourceKeyConversion());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayAddFallbackImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayAddUsableCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayRegisterCodec());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayRegisterBiomeSourceType());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayUseRandomOrMutableState());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayAddDimensionJson());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayAddDimensionTypeJson());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayCreateActiveCaveniaLevel());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayAddWorldgenResources());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayAddAccessOrTeleport());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayAddSpawning());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.nextSliceMayRegisterCavemanEntity());

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationAlgorithmRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationImplementationReady());
        assertEquals(
            "signed int weight value accepted by the existing adapter and weighted selector path",
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputDerivationOutputShape()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.actualDerivationFormulaPinned());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.actualDerivationFormulaImplemented());
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.methodInputSource()
                .contains("getNoiseBiome(int x, int y, int z, Climate.Sampler sampler)")
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.coordinateInputPolicy().contains("x/y/z coordinate inputs are pinned"));
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.climateSamplerInputPolicy().contains("no sampler methods are called"));
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.normalizationPolicy().contains("delegated to the existing weighted selector or adapter path"));
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.deterministicDerivationPolicy().contains("must produce the same selector input"));
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.pureNonRuntimePolicy().contains("pure non-runtime calculation"));
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.noRandomOrMutableStatePolicy().contains("must not use random"));
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.goNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.goNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.consolidatedReadinessLayerCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.weightedSelectionAlgorithmReady());
        assertEquals(675, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.weightedSelectionTotalWeight());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.adapterShapeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.adapterRuntimeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.candidateKeysStillStringOnly());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.fallbackLegacyBiomeName());
        assertEquals("minecraft:plains", CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.fallbackCandidateModernBiomeKey());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.resourceKeyConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.codecMethodShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.runtimeHolderReturnReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.cavemanRemainsDeferred());

        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationImplementationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision\n            .decisionIsGoForPureNonRuntimeAlgorithmNext();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean selectorInputDerivationAlgorithmRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationPureAlgorithmIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision\n            .nextSliceMayAddPureNonRuntimeSelectorInputAlgorithm();\n    }"
            )
        );

        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.guardrails(),
            guardrails.get(0)
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.guardrailValues(),
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGuardrail.ALLOW_PURE_NON_RUNTIME_ALGORITHM_NEXT
        );
        guardrails.forEach(contract -> assertNotNull(contract.guardrail()));
    }

    @Test
    void selectorInputDerivationImplementationGoNoGoSourcesKeepRuntimeApiUsageAndFormulaImplementationBlocked() throws IOException {
        String nextDecisionSource = Files.readString(NEXT_DECISION_SOURCE);
        String guardrailSource = Files.readString(GUARDRAIL_SOURCE);
        String guardrailContractSource = Files.readString(GUARDRAIL_CONTRACT_SOURCE);
        String decisionSource = Files.readString(DECISION_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertSourceStaysInert(nextDecisionSource);
        assertSourceStaysInert(guardrailSource);
        assertSourceStaysInert(guardrailContractSource);
        assertSourceStaysInert(decisionSource);

        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertFalse(designatedSource.contains("ResourceLocation"));
        assertFalse(designatedSource.contains("ResourceKey<Biome>"));
        assertFalse(designatedSource.contains("RegistryLookup<"));
        assertFalse(designatedSource.contains("RegistryAccess"));
        assertFalse(designatedSource.contains("Registry.register"));
        assertFalse(designatedSource.contains("BuiltInRegistries.BIOME_SOURCE.register"));
        assertFalse(designatedSource.contains("DeferredRegister"));
        assertFalse(designatedSource.contains("RecordCodecBuilder"));
        assertFalse(designatedSource.contains("public static final MapCodec"));
        assertFalse(designatedSource.contains("static final MapCodec"));
        assertFalse(designatedSource.contains("CODEC ="));
        assertFalse(designatedSource.contains("return holder"));
        assertFalse(designatedSource.contains("return fallback"));
        assertFalse(designatedSource.contains("return getNoiseBiome"));
        assertFalse(designatedSource.contains("return collectPossibleBiomes"));
        assertFalse(designatedSource.contains("return Stream.of"));
        assertFalse(designatedSource.contains(".map(Holder"));
        assertFalse(designatedSource.contains(".collect(Collectors"));
        assertFalse(designatedSource.contains("random.next"));
        assertFalse(designatedSource.contains("new Random"));
        assertFalse(designatedSource.contains("ThreadLocalRandom"));
        assertFalse(designatedSource.contains("System.currentTimeMillis"));
        assertFalse(designatedSource.contains("System.nanoTime"));

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            List<Path> regularFiles = sourceFiles
                .filter(Files::isRegularFile)
                .toList();

            List<Path> designatedFiles = regularFiles.stream()
                .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                .toList();

            assertEquals(1, designatedFiles.size());
            assertEquals(DESIGNATED_SOURCE, designatedFiles.get(0));
            assertOnlyDesignatedFileContains(regularFiles, "class CaveniaRuntimeBiomeSource extends BiomeSource");
            assertOnlyDesignatedFileContains(regularFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertOnlyDesignatedFileContains(regularFiles, "Holder<Biome>");
            assertOnlyDesignatedFileContains(regularFiles, "import com.mojang.serialization.MapCodec;");
            assertOnlyDesignatedFileContains(regularFiles, "protected MapCodec<? extends BiomeSource> codec()");
            assertOnlyDesignatedFileContains(regularFiles, "Climate.Sampler");

            List<Path> runtimeBiomeSourceFiles = regularFiles.stream()
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();

            assertNoMainSourceContains(runtimeBiomeSourceFiles, "ResourceLocation");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "ResourceKey<Biome>");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "RegistryLookup<");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "RegistryAccess");
            assertNoMainSourceContains(regularFiles, "Registry.register");
            assertNoMainSourceContains(regularFiles, "BuiltInRegistries.BIOME_SOURCE.register");
            assertNoMainSourceContains(regularFiles, "DeferredRegister");
            assertNoMainSourceContains(regularFiles, "RecordCodecBuilder");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "public static final MapCodec");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "static final MapCodec");
            assertNoMainSourceContains(runtimeBiomeSourceFiles, "CODEC =");
            assertNoMainSourceContains(regularFiles, "return holder");
            assertNoMainSourceContains(regularFiles, "return fallback");
            assertNoMainSourceContains(regularFiles, "return getNoiseBiome");
            assertNoMainSourceContains(regularFiles, "return collectPossibleBiomes");
            assertNoMainSourceContains(regularFiles, "return Stream.of");
            assertNoMainSourceContains(regularFiles, ".map(Holder");
            assertNoMainSourceContains(regularFiles, ".collect(Collectors");
            assertNoMainSourceContains(regularFiles, "random.next");
            assertNoMainSourceContains(regularFiles, "new Random");
            assertNoMainSourceContains(regularFiles, "ThreadLocalRandom");
            assertNoMainSourceContains(regularFiles, "System.currentTimeMillis");
            assertNoMainSourceContains(regularFiles, "System.nanoTime");
            assertNoMainSourceContains(regularFiles, "registerConfiguredFeature(");
            assertNoMainSourceContains(regularFiles, "registerPlacedFeature(");
            assertNoMainSourceContains(regularFiles, "registerConfiguredCarver(");
            assertNoMainSourceContains(regularFiles, "addFreshEntity(");
            assertNoMainSourceContains(regularFiles, "changeDimension(");
            assertNoMainSourceContains(regularFiles, ".teleportTo(");
        }

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/tags/worldgen/biome/cavenia")
                    ),
                "Expected the selector-input derivation implementation go/no-go slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("Climate.Sampler"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("ResourceLocation"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("random.next"));
        assertFalse(source.contains("new Random"));
        assertFalse(source.contains("ThreadLocalRandom"));
        assertFalse(source.contains("System.currentTimeMillis"));
        assertFalse(source.contains("System.nanoTime"));
        assertFalse(source.contains("registerConfiguredFeature("));
        assertFalse(source.contains("registerPlacedFeature("));
        assertFalse(source.contains("registerConfiguredCarver("));
        assertFalse(source.contains("addFreshEntity("));
        assertFalse(source.contains("changeDimension("));
        assertFalse(source.contains(".teleportTo("));
    }

    private static void assertOnlyDesignatedFileContains(List<Path> sourceFiles, String fragment) throws IOException {
        List<Path> matchingFiles = sourceFiles.stream()
            .filter(path -> {
                try {
                    return Files.readString(path).contains(fragment);
                } catch (IOException exception) {
                    throw new IllegalStateException("Could not read source file: " + path, exception);
                }
            })
            .toList();

        assertEquals(List.of(DESIGNATED_SOURCE), matchingFiles, "Expected only the designated subclass file to contain: " + fragment);
    }

    private static void assertNoMainSourceContains(List<Path> sourceFiles, String fragment) throws IOException {
        assertTrue(
            sourceFiles.stream().noneMatch(path -> {
                try {
                    return Files.readString(path).contains(fragment);
                } catch (IOException exception) {
                    throw new IllegalStateException("Could not read source file: " + path, exception);
                }
            }),
            "Expected no main-source file to contain: " + fragment
        );
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
