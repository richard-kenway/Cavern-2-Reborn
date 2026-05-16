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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationNextDecision;

class CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecisionTest {
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationNextDecision.java"
    );
    private static final Path GUARDRAIL_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.java"
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
    void selectorInputDerivationGoNoGoDecisionPinsReadinessOnlyNextSliceGuardrailsWithoutImplementingRuntimeBehavior()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail> expectedGuardrails = List.of(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_SELECTOR_INPUT_DERIVATION_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_COORDINATE_INPUT_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_CLIMATE_SAMPLER_INPUT_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_WEIGHT_VALUE_DERIVATION_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_SELECTOR_INPUT_DERIVATION_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_COORDINATE_RUNTIME_SELECTION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_CLIMATE_SAMPLER_RUNTIME_SELECTION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_HOLDER_RESOLUTION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_RESOURCE_LOCATION_OR_KEY_CONVERSION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_FALLBACK_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_USABLE_CODEC_OR_REGISTRATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_DIMENSION_ACTIVATION,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.NO_WORLDGEN_ACCESS_SPAWNING,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.KEEP_READINESS_CHAIN_INERT
        );
        Set<CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail> allowedReadinessActions = Set.of(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_SELECTOR_INPUT_DERIVATION_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_COORDINATE_INPUT_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_CLIMATE_SAMPLER_INPUT_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_WEIGHT_VALUE_DERIVATION_POLICY_PINNING_NEXT
        );
        List<CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.guardrails();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationNextDecision.PROCEED_WITH_SELECTOR_INPUT_DERIVATION_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectedDecision()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.decisionIsGoForSelectorInputDerivationReadinessNext());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.decisionIsReadinessOnlyForNextSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationReadinessImplementedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationPolicyPinnedInThisSlice());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationImplementationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationImplementationGoNoGoRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputDerivationRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.runtimeApiAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.canActivateCaveniaNow());

        assertEquals(expectedGuardrails, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.guardrailValues());
        assertEquals(17, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.guardrailCount());
        assertTrue(guardrails.stream().allMatch(contract -> contract.sourceContractName() != null && !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertTrue(guardrails.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract::enforcedInThisSlice));
        assertTrue(guardrails.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract::blocksActivation));
        assertTrue(guardrails.stream().noneMatch(CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrailContract::allowsNextSliceRuntimeAction));
        guardrails.forEach(contract ->
            assertEquals(
                allowedReadinessActions.contains(contract.guardrail()),
                contract.allowsNextSliceReadinessAction()
            )
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.allGuardrailsEnforcedInThisSlice());
        assertEquals(4, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.allowedNextSliceReadinessActionCount());
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.allowedNextSliceRuntimeActionCount());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.anyRuntimeActionAllowedByGuardrails());

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddSelectorInputDerivationReadiness());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayPinCoordinateInputPolicy());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayPinClimateSamplerInputPolicy());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayPinWeightValueDerivationPolicy());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayImplementSelectorInputDerivation());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayUseCoordinatesForRuntimeSelection());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayUseClimateSamplerForRuntimeSelection());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayMakeGetNoiseBiomeUsable());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayMakeCollectPossibleBiomesUsable());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayUseRegistryLookupAccess());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayResolveHolders());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayImplementResourceLocationConversion());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayImplementResourceKeyConversion());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddFallbackImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddUsableCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayRegisterCodec());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayRegisterBiomeSourceType());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddDimensionJson());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddDimensionTypeJson());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayCreateActiveCaveniaLevel());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddWorldgenResources());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddAccessOrTeleport());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddSpawning());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayRegisterCavemanEntity());

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.readinessChainSelectedNextDecisionMatches());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.consolidatedReadinessLayerCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.coordinateInputShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.climateSamplerInputShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.weightValueDerivationDecisionPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.weightValueDerivationPolicy().contains("derivation remains deferred"));
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.weightedSelectionAlgorithmReady());
        assertEquals(675, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.weightedSelectionTotalWeight());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.adapterShapeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.candidateKeysStillStringOnly());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.fallbackLegacyBiomeName());
        assertEquals("minecraft:plains", CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.fallbackCandidateModernBiomeKey());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.resourceKeyConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.codecMethodShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.runtimeHolderReturnReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.cavemanRemainsDeferred());

        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.decisionIsGoForSelectorInputDerivationReadinessNext();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean selectorInputDerivationRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationReadinessIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddSelectorInputDerivationReadiness();\n    }"
            )
        );

        assertImmutableList(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.guardrails(), guardrails.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.guardrailValues(),
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGuardrail.ALLOW_SELECTOR_INPUT_DERIVATION_READINESS_NEXT
        );
        guardrails.forEach(contract -> assertNotNull(contract.guardrail()));
    }

    @Test
    void selectorInputDerivationGoNoGoSourcesKeepRuntimeApiUsageAndActivationBlocked() throws IOException {
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
            assertNoMainSourceContains(regularFiles, "public static final MapCodec");
            assertNoMainSourceContains(regularFiles, "static final MapCodec");
            assertNoMainSourceContains(regularFiles, "CODEC =");
            assertNoMainSourceContains(regularFiles, "return holder");
            assertNoMainSourceContains(regularFiles, "return fallback");
            assertNoMainSourceContains(regularFiles, "return getNoiseBiome");
            assertNoMainSourceContains(regularFiles, "return collectPossibleBiomes");
            assertNoMainSourceContains(regularFiles, "return Stream.of");
            assertNoMainSourceContains(regularFiles, ".map(Holder");
            assertNoMainSourceContains(regularFiles, ".collect(Collectors");
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
                "Expected the selector-input derivation go/no-go slice to keep active Cavenia resources absent"
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

        if (fragment.equals("Holder<Biome>")) {
            assertEquals(
                List.of("CaveniaRuntimeBiomeSource.java", "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"),
                matchingFiles.stream().map(path -> path.getFileName().toString()).toList(),
                "Expected only the designated subclass and converter files to contain: " + fragment
            );
            return;
        }

        assertEquals(List.of(DESIGNATED_SOURCE), matchingFiles, "Expected only the designated subclass file to contain: " + fragment);
    }

    private static void assertNoMainSourceContains(List<Path> sourceFiles, String fragment) throws IOException {
        boolean allowConverterFile = fragment.equals("ResourceLocation")
            || fragment.equals("ResourceKey<Biome>")
            || fragment.equals("HolderLookup")
            || fragment.equals("Registries.BIOME")
            || fragment.equals("return holder");
        assertTrue(
            sourceFiles.stream().noneMatch(path -> {
                try {
                    return (!allowConverterFile
                        || !path.getFileName().toString().equals("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"))
                        && Files.readString(path).contains(fragment);
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
