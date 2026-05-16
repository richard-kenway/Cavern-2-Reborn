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
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationNextDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSkeleton;

class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecisionTest {
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationNextDecision.java"
    );
    private static final Path GUARDRAIL_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.java"
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
    void selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionPinsPureNonRuntimeOnlyBoundaries()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail> expectedGuardrails =
            List.of(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_PURE_NON_RUNTIME_BRIDGE_NEXT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_SIGNED_INT_SELECTOR_INPUT_NEXT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_EXISTING_WEIGHTED_SELECTOR_OR_ADAPTER_NEXT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_STRING_CANDIDATE_KEY_OUTPUT_NEXT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_CANDIDATE_INVENTORY_DATA_NEXT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_MINECRAFT_RUNTIME_API_IMPORTS,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_GET_NOISE_BIOME_WIRING,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_GET_NOISE_BIOME_IMPLEMENTATION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_REGISTRY_LOOKUP_ACCESS,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_HOLDER_RESOLUTION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_RESOURCE_LOCATION_OR_KEY_CONVERSION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_FALLBACK_IMPLEMENTATION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_USABLE_CODEC_OR_REGISTRATION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_DIMENSION_ACTIVATION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .NO_WORLDGEN_ACCESS_SPAWNING,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .KEEP_RUNTIME_METHODS_UNSUPPORTED
            );
        Set<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail> allowedPureBridgeActions =
            Set.of(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_PURE_NON_RUNTIME_BRIDGE_NEXT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_SIGNED_INT_SELECTOR_INPUT_NEXT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_EXISTING_WEIGHTED_SELECTOR_OR_ADAPTER_NEXT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_STRING_CANDIDATE_KEY_OUTPUT_NEXT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrail
                    .ALLOW_CANDIDATE_INVENTORY_DATA_NEXT
            );
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.guardrails();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationNextDecision
                .PROCEED_WITH_PURE_NON_RUNTIME_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.selectedDecision()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .decisionIsGoForPureNonRuntimeBridgeNext()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .decisionIsImplementationOnlyForNextSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .runtimeApiAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .activationAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.canActivateCaveniaNow()
        );

        assertEquals(
            expectedGuardrails,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.guardrailValues()
        );
        assertEquals(
            17,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.guardrailCount()
        );
        assertTrue(guardrails.stream().allMatch(contract -> contract.sourceContractName() != null && !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> contract.blocker() != null && !contract.blocker().isBlank()));
        assertTrue(
            guardrails.stream().allMatch(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract
                    ::enforcedInThisSlice
            )
        );
        assertTrue(
            guardrails.stream().allMatch(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract
                    ::blocksActivation
            )
        );
        assertTrue(
            guardrails.stream().noneMatch(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGuardrailContract
                    ::allowsNextSliceRuntimeAction
            )
        );
        guardrails.forEach(contract ->
            assertEquals(
                allowedPureBridgeActions.contains(contract.guardrail()),
                contract.allowsNextSlicePureBridgeAction()
            )
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .allGuardrailsEnforcedInThisSlice()
        );
        assertEquals(
            5,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .allowedNextSlicePureBridgeActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .allowedNextSliceRuntimeActionCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .anyRuntimeActionAllowedByGuardrails()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayAddPureNonRuntimeSelectorToWeightedCandidateBridge()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayUseSignedIntSelectorInput()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayUseExistingWeightedSelectorOrAdapter()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayReturnStringCandidateKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayUseCandidateInventoryData()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayImportMinecraftRuntimeApis()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayWireIntoGetNoiseBiome()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayMakeGetNoiseBiomeUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayMakeCollectPossibleBiomesUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayUseRegistryLookupAccess()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayResolveHolders()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayImplementResourceLocationConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayImplementResourceKeyConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayAddFallbackImplementation()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayAddUsableCodecImplementation()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayRegisterCodec()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayRegisterBiomeSourceType()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayAddDimensionJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayAddDimensionTypeJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayCreateActiveCaveniaLevel()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayAddWorldgenResources()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayAddAccessOrTeleport()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayAddSpawning()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .nextSliceMayRegisterCavemanEntity()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .bridgeReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .bridgeReadinessRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .bridgeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.bridgeRuntimeReady()
        );
        assertEquals(
            "signed int selector input",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectorInputOutputShape()
        );
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .candidateKeyOutputShape()
        );
        assertEquals(
            675,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .weightedSelectionTotalWeight()
        );
        assertEquals(
            14,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .candidateEntryCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .candidateInventoryReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .candidateKeysStillStringOnly()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .weightedSelectorCompatibilityReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .adapterCompatibilityReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.adapterRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectorInputAlgorithmReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectorInputAlgorithmRuntimeReady()
        );
        assertEquals(
            -104499101,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .deriveSelectorInputSampleOrigin()
        );
        assertEquals(
            700186390,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .deriveSelectorInputSampleMixed()
        );
        assertEquals(
            -1039724642,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .deriveSelectorInputSampleNegative()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .bridgeGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .bridgeGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectorInputAlgorithmConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectorInputAlgorithmConsolidationRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .implementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .implementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .readinessChainConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .readinessChainRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .allCurrentReadinessLayersReady()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .runtimeReadyLayerCount()
        );
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .consolidatedReadinessLayerCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .noiseBiomeSelectionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .noiseBiomeSelectionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .possibleBiomesReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .possibleBiomesRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .fallbackPolicyReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .fallbackPolicyRuntimeReady()
        );
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .fallbackCandidateModernBiomeKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .holderConversionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .holderConversionRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .holderResolutionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .resourceLocationConversionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .resourceKeyConversionReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .codecMethodShapeStubReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .codecMethodShapeRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .designatedSubclassReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .designatedSubclassRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .runtimeHolderReturnReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .getNoiseBiomeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .usableRuntimeBiomeSourceReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .registryLookupAccessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .usableCodecImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.codecRegistered()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .biomeSourceTypeRegistered()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .normalRuntimeConstructionAllowed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .unsupportedMethodStubsOnly()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .collectPossibleBiomesStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .getNoiseBiomeStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .codecMethodStubbed()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .dimensionBindingReady()
        );
        assertEquals(
            68,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectedSurfaceReadinessItemCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .dimensionJsonPresent()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .dimensionTypeJsonPresent()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .cavemanRemainsDeferred()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceReadinessChainConsolidation
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceReadinessChainConsolidation
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourcePossibleBiomesReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourcePossibleBiomesReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceFallbackPolicyReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceFallbackPolicyReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceHolderConversionReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceHolderConversionReadiness
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCodecMethodShapeStub
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCodecMethodShapeStub
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceApiShapeInventory
                .selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceApiShapeInventory
                .selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.selectorToWeightedCandidateBridgeImplementationGoNoGoRuntimeReady());

        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision\n            .decisionIsGoForPureNonRuntimeBridgeNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeImplementationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgePureImplementationIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision\n            .nextSliceMayAddPureNonRuntimeSelectorToWeightedCandidateBridge();\n    }"
            )
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.guardrails(),
            guardrails.get(0)
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision.guardrailValues(),
            expectedGuardrails.get(0)
        );
        assertNotNull(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .guardrailFor(expectedGuardrails.get(0))
                .orElseThrow()
        );
    }

    @Test
    void selectorToWeightedCandidateBridgeImplementationGoNoGoSourcesStayNonRuntimeAndDoNotImplementBridgeBehavior()
        throws IOException {
        String nextDecisionSource = Files.readString(NEXT_DECISION_SOURCE);
        String guardrailSource = Files.readString(GUARDRAIL_SOURCE);
        String guardrailContractSource = Files.readString(GUARDRAIL_CONTRACT_SOURCE);
        String decisionSource = Files.readString(DECISION_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertBridgeImplementationDecisionSource(nextDecisionSource);
        assertBridgeImplementationDecisionSource(guardrailSource);
        assertBridgeImplementationDecisionSource(guardrailContractSource);
        assertBridgeImplementationDecisionSource(decisionSource);

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
                "Expected the selector-to-weighted-candidate bridge implementation go/no-go slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertBridgeImplementationDecisionSource(String source) {
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

    private static <T> void assertImmutableList(List<T> list, T element) {
        try {
            list.add(element);
            throw new AssertionError("Expected list to be immutable");
        } catch (UnsupportedOperationException expected) {
        }
    }

    private static void assertOnlyDesignatedFileContains(List<Path> files, String text) throws IOException {
        List<Path> containingFiles = files.stream()
            .filter(path -> readString(path).contains(text))
            .toList();
        if (text.equals("Holder<Biome>")) {
            assertEquals(
                List.of("CaveniaRuntimeBiomeSource.java", "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"),
                containingFiles.stream().map(path -> path.getFileName().toString()).toList(),
                "Unexpected designated-file ownership for: " + text
            );
            return;
        }
        assertEquals(List.of(DESIGNATED_SOURCE), containingFiles, "Unexpected designated-file ownership for: " + text);
    }

    private static void assertNoMainSourceContains(List<Path> files, String text) throws IOException {
        List<Path> containingFiles = files.stream()
            .filter(path -> readString(path).contains(text))
            .toList();
        boolean allowConverterFile = text.equals("ResourceLocation")
            || text.equals("ResourceKey<Biome>")
            || text.equals("HolderLookup")
            || text.equals("Registries.BIOME")
            || text.equals("return holder");
        if (allowConverterFile) {
            containingFiles = containingFiles.stream()
                .filter(path -> !path.getFileName().toString().equals("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"))
                .toList();
        }
        assertTrue(containingFiles.isEmpty(), "Unexpected source token '" + text + "' in: " + containingFiles);
    }

    private static String readString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read source file: " + path, exception);
        }
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
