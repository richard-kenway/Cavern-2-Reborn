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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyPlan;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceApiShapeInventory;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCodecMethodShapeStub;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceFallbackPolicyReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceHolderConversionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourcePossibleBiomesReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceReadinessChainConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationNextDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSkeleton;

class CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationEntry.java"
    );
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationNextDecision.java"
    );
    private static final Path CONSOLIDATION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.java"
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
    void selectorInputAlgorithmConsolidationPinsTheCurrentAlgorithmStateAndTheNextDecisionBoundary() throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.PURE_SELECTOR_INPUT_ALGORITHM_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.INTEGER_COORDINATE_INPUTS_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.CLIMATE_SAMPLER_EXCLUDED,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.SIGNED_INT_SELECTOR_OUTPUT_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.DETERMINISTIC_FORMULA_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.NORMALIZATION_DELEGATED,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.NEGATIVE_COORDINATE_STABILITY_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.NO_RANDOM_OR_MUTABLE_STATE,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.WEIGHTED_SELECTOR_COMPATIBILITY_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.ADAPTER_COMPATIBILITY_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_NOT_IMPLEMENTED,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.GET_NOISE_BIOME_WIRING_BLOCKED,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.RUNTIME_SELECTION_BLOCKED,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.REGISTRY_AND_HOLDER_SURFACES_BLOCKED,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.NEXT_DECISION_SELECTED
        );
        Set<CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent> expectedDecisionInputs = Set.of(
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.PURE_SELECTOR_INPUT_ALGORITHM_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.SIGNED_INT_SELECTOR_OUTPUT_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.NORMALIZATION_DELEGATED,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.WEIGHTED_SELECTOR_COMPATIBILITY_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.ADAPTER_COMPATIBILITY_READY,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_NOT_IMPLEMENTED,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.NEXT_DECISION_SELECTED
        );
        List<CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationEntry> entries =
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.entries();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.components());
        assertEquals(15, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.consolidatedState() != null && !entry.consolidatedState().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.nextBoundary() != null && !entry.nextBoundary().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeReady()));
        entries.forEach(entry ->
            assertEquals(expectedDecisionInputs.contains(entry.component()), entry.nextDecisionInput())
        );

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorInputAlgorithmConsolidationReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorInputAlgorithmReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.pureNonRuntimeAlgorithmReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.integerCoordinateInputsReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.climateSamplerExcluded());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.climateSamplerMethodsCalled());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.signedIntSelectorOutputReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.deterministicFormulaReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.normalizationDelegated());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.negativeCoordinateStable());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.randomOrMutableStateDependencyPresent());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.weightedSelectorCompatibilityReady());
        assertEquals(675, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.weightedSelectionTotalWeight());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.adapterCompatibilityReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorToWeightedCandidateBridgeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorToWeightedCandidateBridgeImplemented());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorToWeightedCandidateBridgeRuntimeReady());
        assertEquals(
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationNextDecision
                .PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_GO_NO_GO_NEXT,
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectedNextDecision()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorToWeightedCandidateBridgeGoNoGoIsNext());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayAddSelectorToWeightedCandidateBridgeGoNoGoDecision());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayImplementSelectorToWeightedCandidateBridge());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayCallWeightedSelectorWithDerivedInput());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayCallAdapterWithDerivedInput());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayWireIntoGetNoiseBiome());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayMakeGetNoiseBiomeUsable());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayMakeCollectPossibleBiomesUsable());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayUseRegistryLookupAccess());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayResolveHolders());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayImplementResourceLocationConversion());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayImplementResourceKeyConversion());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayAddFallbackImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayAddUsableCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayRegisterCodec());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayRegisterBiomeSourceType());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayAddDimensionJson());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayAddDimensionTypeJson());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayCreateActiveCaveniaLevel());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayAddWorldgenResources());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayAddAccessOrTeleport());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayAddSpawning());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.nextSliceMayRegisterCavemanEntity());
        assertEquals(-104499101, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.deriveSelectorInputSampleOrigin());
        assertEquals(700186390, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.deriveSelectorInputSampleMixed());
        assertEquals(-1039724642, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.deriveSelectorInputSampleNegative());
        assertEquals("signed int selector input", CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorInputOutputShape());
        assertEquals(
            "normalization and wrapping remain delegated to the existing selector or adapter path",
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.normalizationPolicy()
        );
        assertEquals("not wired into getNoiseBiome", CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.algorithmRuntimeBoundary());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.implementationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.implementationGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorInputDerivationReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorInputDerivationReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.consolidatedReadinessLayerCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.noiseBiomeSelectionRuntimeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.candidateKeysStillStringOnly());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.fallbackLegacyBiomeName());
        assertEquals("minecraft:plains", CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.fallbackCandidateModernBiomeKey());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.resourceKeyConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.codecMethodShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.runtimeHolderReturnReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.dimensionBindingReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.canActivateCaveniaNow());
        assertEquals(68, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.cavemanRemainsDeferred());

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.selectorInputAlgorithmConsolidationRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.selectorInputAlgorithmConsolidationReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.selectorInputAlgorithmConsolidationRuntimeReady());

        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputAlgorithmConsolidationReady() {\n        return CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorInputAlgorithmConsolidationReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean selectorInputAlgorithmRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeGoNoGoIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorToWeightedCandidateBridgeGoNoGoIsNext();\n    }"
            )
        );

        assertImmutableList(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.components(),
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidationComponent.PURE_SELECTOR_INPUT_ALGORITHM_READY
        );
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void selectorInputAlgorithmConsolidationSourcesStayNonRuntimeAndKeepActivationBlocked() throws IOException {
        String componentSource = Files.readString(COMPONENT_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String nextDecisionSource = Files.readString(NEXT_DECISION_SOURCE);
        String consolidationSource = Files.readString(CONSOLIDATION_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertPureConsolidationSource(componentSource);
        assertPureConsolidationSource(entrySource);
        assertPureConsolidationSource(nextDecisionSource);
        assertPureConsolidationSource(consolidationSource);

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
            List<Path> consolidationFiles = List.of(COMPONENT_SOURCE, ENTRY_SOURCE, NEXT_DECISION_SOURCE, CONSOLIDATION_SOURCE);

            assertEquals(1, designatedFiles.size());
            assertEquals(DESIGNATED_SOURCE, designatedFiles.get(0));
            assertOnlyDesignatedFileContains(regularFiles, "class CaveniaRuntimeBiomeSource extends BiomeSource");
            assertOnlyDesignatedFileContains(regularFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertOnlyDesignatedFileContains(regularFiles, "Holder<Biome>");
            assertOnlyDesignatedFileContains(regularFiles, "import com.mojang.serialization.MapCodec;");
            assertOnlyDesignatedFileContains(regularFiles, "protected MapCodec<? extends BiomeSource> codec()");
            assertOnlyDesignatedFileContains(regularFiles, "Climate.Sampler");

            assertNoMainSourceContains(consolidationFiles, "import net.minecraft.world.level.biome.Climate;");
            assertNoMainSourceContains(consolidationFiles, "Climate.Sampler");
            assertNoMainSourceContains(consolidationFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertNoMainSourceContains(consolidationFiles, "Holder<Biome>");
            assertNoMainSourceContains(consolidationFiles, "ResourceLocation");
            assertNoMainSourceContains(consolidationFiles, "ResourceKey<Biome>");
            assertNoMainSourceContains(consolidationFiles, "RegistryLookup<");
            assertNoMainSourceContains(consolidationFiles, "RegistryAccess");
            assertNoMainSourceContains(consolidationFiles, "MapCodec");
            assertNoMainSourceContains(consolidationFiles, "Codec<");
            assertNoMainSourceContains(consolidationFiles, "RecordCodecBuilder");
            assertNoMainSourceContains(consolidationFiles, "Random");
            assertNoMainSourceContains(consolidationFiles, "ThreadLocalRandom");
            assertNoMainSourceContains(consolidationFiles, "System.currentTimeMillis");
            assertNoMainSourceContains(consolidationFiles, "System.nanoTime");

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

        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/tags/worldgen/biome/cavenia")
                    ),
                "Expected the selector-input algorithm consolidation slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertPureConsolidationSource(String source) {
        assertFalse(source.contains("import net.minecraft"));
        assertFalse(source.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("import net.minecraft.world.level.biome.Climate;"));
        assertFalse(source.contains("Climate.Sampler"));
        assertFalse(source.contains("ResourceLocation"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Random"));
        assertFalse(source.contains("ThreadLocalRandom"));
        assertFalse(source.contains("System.currentTimeMillis"));
        assertFalse(source.contains("System.nanoTime"));
    }

    private static <T> void assertImmutableList(List<T> list, T element) {
        try {
            list.add(element);
            throw new AssertionError("Expected list to be immutable");
        } catch (UnsupportedOperationException expected) {
        }
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
                List.of("CaveniaRuntimeBiomeSource.java", "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java", "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"),
                matchingFiles.stream().map(path -> path.getFileName().toString()).toList(),
                "Expected only the designated subclass, builder and converter files to contain: " + fragment
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

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)), "Expected missing project file: " + Path.of(first, more));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(first).resolve(Path.of("", more));
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
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
