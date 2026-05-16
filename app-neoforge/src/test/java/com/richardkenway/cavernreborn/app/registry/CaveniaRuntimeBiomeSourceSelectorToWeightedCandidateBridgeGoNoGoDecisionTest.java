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
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeNextDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSkeleton;

class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecisionTest {
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeNextDecision.java"
    );
    private static final Path GUARDRAIL_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.java"
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
    void selectorToWeightedCandidateBridgeGoNoGoDecisionPinsReadinessOnlyGuardrailsAndKeepsBridgeBehaviorAbsent()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail> expectedGuardrails = List.of(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_SELECTOR_INPUT_SURFACE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_WEIGHTED_SELECTOR_QUERY_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_ADAPTER_QUERY_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_CANDIDATE_KEY_OUTPUT_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_WEIGHTED_SELECTOR_CALL_WITH_DERIVED_INPUT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_ADAPTER_CALL_WITH_DERIVED_INPUT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_GET_NOISE_BIOME_WIRING,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_HOLDER_RESOLUTION,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .NO_RESOURCE_LOCATION_OR_KEY_CONVERSION,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_FALLBACK_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_USABLE_CODEC_OR_REGISTRATION,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_DIMENSION_ACTIVATION,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.NO_WORLDGEN_ACCESS_SPAWNING,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail.KEEP_SELECTOR_INPUT_ALGORITHM_INERT
        );
        Set<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail> allowedReadinessActions = Set.of(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_SELECTOR_INPUT_SURFACE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_WEIGHTED_SELECTOR_QUERY_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_ADAPTER_QUERY_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_CANDIDATE_KEY_OUTPUT_POLICY_PINNING_NEXT
        );
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.guardrails();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeNextDecision
                .PROCEED_WITH_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.selectedDecision()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .decisionIsGoForSelectorToWeightedCandidateBridgeReadinessNext()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.decisionIsReadinessOnlyForNextSlice());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectorToWeightedCandidateBridgeReadinessImplementedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectorToWeightedCandidateBridgeRuntimeReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.runtimeApiAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.canActivateCaveniaNow());

        assertEquals(expectedGuardrails, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.guardrailValues());
        assertEquals(19, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.guardrailCount());
        assertTrue(guardrails.stream().allMatch(contract -> contract.sourceContractName() != null && !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertTrue(guardrails.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract::enforcedInThisSlice));
        assertTrue(guardrails.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract::blocksActivation));
        assertTrue(
            guardrails.stream().noneMatch(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract::allowsNextSliceBridgeImplementation
            )
        );
        assertTrue(
            guardrails.stream().noneMatch(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrailContract::allowsNextSliceRuntimeAction
            )
        );
        guardrails.forEach(contract ->
            assertEquals(
                allowedReadinessActions.contains(contract.guardrail()),
                contract.allowsNextSliceReadinessAction()
            )
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.allGuardrailsEnforcedInThisSlice());
        assertEquals(5, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.allowedNextSliceReadinessActionCount());
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .allowedNextSliceBridgeImplementationActionCount()
        );
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.allowedNextSliceRuntimeActionCount());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .anyBridgeImplementationAllowedByGuardrails()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.anyRuntimeActionAllowedByGuardrails()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayAddSelectorToWeightedCandidateBridgeReadiness()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayPinSelectorInputSurfacePolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayPinWeightedSelectorQueryPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayPinAdapterQueryPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayPinCandidateKeyOutputPolicy()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayImplementSelectorToWeightedCandidateBridge()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayCallWeightedSelectorWithDerivedInput()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayCallAdapterWithDerivedInput()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.nextSliceMayWireIntoGetNoiseBiome());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayMakeGetNoiseBiomeUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayMakeCollectPossibleBiomesUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayUseRegistryLookupAccess()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.nextSliceMayResolveHolders()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayImplementResourceLocationConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayImplementResourceKeyConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayAddFallbackImplementation()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayAddUsableCodecImplementation()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.nextSliceMayRegisterCodec());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayRegisterBiomeSourceType()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.nextSliceMayAddDimensionJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.nextSliceMayAddDimensionTypeJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayCreateActiveCaveniaLevel()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayAddWorldgenResources()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayAddAccessOrTeleport()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.nextSliceMayAddSpawning());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .nextSliceMayRegisterCavemanEntity()
        );

        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.selectorInputAlgorithmConsolidationReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectorInputAlgorithmConsolidationRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectorInputAlgorithmSelectedNextDecisionMatches()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.selectorInputAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.selectorInputAlgorithmRuntimeReady());
        assertEquals(
            "signed int selector input",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.selectorInputOutputShape()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .weightedSelectorCompatibilityReady()
        );
        assertEquals(
            675,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.weightedSelectionTotalWeight()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.adapterCompatibilityReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.adapterRuntimeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.candidateInventoryReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.candidateKeysStillStringOnly()
        );
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .fallbackCandidateModernBiomeKey()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.implementationGoNoGoDecisionReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.implementationGoNoGoRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.runtimeReadyLayerCount());
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .consolidatedReadinessLayerCount()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.noiseBiomeSelectionReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.noiseBiomeSelectionRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.holderConversionReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.holderConversionRuntimeReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.holderResolutionReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.resourceLocationConversionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.resourceKeyConversionReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.possibleBiomesReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.possibleBiomesRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.fallbackPolicyReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.fallbackPolicyRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.codecMethodShapeStubReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.codecMethodShapeRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.designatedSubclassReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.designatedSubclassRuntimeReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.runtimeHolderReturnReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.getNoiseBiomeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.usableRuntimeBiomeSourceReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.registryLookupAccessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.usableCodecImplementationReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.biomeSourceTypeRegistered());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.normalRuntimeConstructionAllowed()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.dimensionBindingReady());
        assertEquals(
            68,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectedSurfaceReadinessItemCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.dimensionJsonPresent());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.dimensionTypeJsonPresent()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.cavemanRemainsDeferred());

        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeGoNoGoRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.selectorToWeightedCandidateBridgeGoNoGoDecisionReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.selectorToWeightedCandidateBridgeGoNoGoRuntimeReady());

        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision\n            .decisionIsGoForSelectorToWeightedCandidateBridgeReadinessNext();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeReadinessIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision\n            .nextSliceMayAddSelectorToWeightedCandidateBridgeReadiness();\n    }"
            )
        );

        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.guardrails(),
            guardrails.get(0)
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision.guardrailValues(),
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGuardrail
                .ALLOW_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READINESS_NEXT
        );
        guardrails.forEach(contract -> assertNotNull(contract.guardrail()));
    }

    @Test
    void selectorToWeightedCandidateBridgeGoNoGoSourcesStayNonRuntimeAndDoNotImplementAnyBridgeBehavior()
        throws IOException {
        String nextDecisionSource = Files.readString(NEXT_DECISION_SOURCE);
        String guardrailSource = Files.readString(GUARDRAIL_SOURCE);
        String guardrailContractSource = Files.readString(GUARDRAIL_CONTRACT_SOURCE);
        String decisionSource = Files.readString(DECISION_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertBridgeDecisionSource(nextDecisionSource);
        assertBridgeDecisionSource(guardrailSource);
        assertBridgeDecisionSource(guardrailContractSource);
        assertBridgeDecisionSource(decisionSource);

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
            List<Path> decisionFiles = List.of(
                NEXT_DECISION_SOURCE,
                GUARDRAIL_SOURCE,
                GUARDRAIL_CONTRACT_SOURCE,
                DECISION_SOURCE
            );

            assertEquals(1, designatedFiles.size());
            assertEquals(DESIGNATED_SOURCE, designatedFiles.get(0));
            assertOnlyDesignatedFileContains(regularFiles, "class CaveniaRuntimeBiomeSource extends BiomeSource");
            assertOnlyDesignatedFileContains(regularFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertOnlyDesignatedFileContains(regularFiles, "Holder<Biome>");
            assertOnlyDesignatedFileContains(regularFiles, "import com.mojang.serialization.MapCodec;");
            assertOnlyDesignatedFileContains(regularFiles, "protected MapCodec<? extends BiomeSource> codec()");
            assertOnlyDesignatedFileContains(regularFiles, "Climate.Sampler");

            assertNoMainSourceContains(decisionFiles, "import net.minecraft");
            assertNoMainSourceContains(decisionFiles, "import net.minecraft.world.level.biome.Climate;");
            assertNoMainSourceContains(decisionFiles, "Climate.Sampler");
            assertNoMainSourceContains(decisionFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertNoMainSourceContains(decisionFiles, "Holder<Biome>");
            assertNoMainSourceContains(decisionFiles, "ResourceLocation");
            assertNoMainSourceContains(decisionFiles, "ResourceKey<Biome>");
            assertNoMainSourceContains(decisionFiles, "RegistryLookup<");
            assertNoMainSourceContains(decisionFiles, "RegistryAccess");
            assertNoMainSourceContains(decisionFiles, "MapCodec");
            assertNoMainSourceContains(decisionFiles, "Codec<");
            assertNoMainSourceContains(decisionFiles, "RecordCodecBuilder");
            assertNoMainSourceContains(decisionFiles, "Random");
            assertNoMainSourceContains(decisionFiles, "ThreadLocalRandom");
            assertNoMainSourceContains(decisionFiles, "System.currentTimeMillis");
            assertNoMainSourceContains(decisionFiles, "System.nanoTime");
            assertNoMainSourceContains(decisionFiles, "deriveSelectorInput(");
            assertNoMainSourceContains(decisionFiles, ".select(");
            assertNoMainSourceContains(decisionFiles, ".adapt(");

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
                "Expected the selector-to-weighted-candidate bridge go/no-go slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertBridgeDecisionSource(String source) {
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
        assertFalse(source.contains("deriveSelectorInput("));
        assertFalse(source.contains(".select("));
        assertFalse(source.contains(".adapt("));
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
