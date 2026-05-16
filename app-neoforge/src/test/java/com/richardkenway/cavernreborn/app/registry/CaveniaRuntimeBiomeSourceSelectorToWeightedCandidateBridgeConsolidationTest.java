package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCodecMethodShapeStub;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceFallbackPolicyReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceHolderConversionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourcePossibleBiomesReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceReadinessChainConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationNextDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWeightedBiomeSelectionAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSelectionAdapterContract;

class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationEntry.java"
    );
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationNextDecision.java"
    );
    private static final Path CONSOLIDATION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.java"
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
    void selectorToWeightedCandidateBridgeConsolidationPinsTheCurrentBridgeStateAndTheNextDecision()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent> expectedComponents =
            List.of(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .PURE_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .SIGNED_INT_SELECTOR_INPUT_READY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .WEIGHTED_SELECTOR_OR_ADAPTER_PATH_READY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .STRING_CANDIDATE_KEY_OUTPUT_READY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .CANDIDATE_INVENTORY_COMPATIBILITY_READY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .SAMPLE_CANDIDATE_KEYS_VERIFIED,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .CANDIDATE_KEYS_REMAIN_STRING_ONLY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .NO_GET_NOISE_BIOME_WIRING,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .NO_HOLDER_CONVERSION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .NO_REGISTRY_LOOKUP_ACCESS,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .NO_RESOURCE_LOCATION_OR_KEY_CONVERSION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .RUNTIME_SELECTION_BLOCKED,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .CODEC_AND_REGISTRATION_BLOCKED,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .DIMENSION_WORLDGEN_ACCESS_SPAWNING_BLOCKED,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .NEXT_DECISION_SELECTED
            );
        Set<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent> expectedNextDecisionInputs =
            Set.of(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .PURE_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .STRING_CANDIDATE_KEY_OUTPUT_READY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .CANDIDATE_INVENTORY_COMPATIBILITY_READY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .SAMPLE_CANDIDATE_KEYS_VERIFIED,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .CANDIDATE_KEYS_REMAIN_STRING_ONLY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .NO_HOLDER_CONVERSION,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .NO_REGISTRY_LOOKUP_ACCESS,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                    .NEXT_DECISION_SELECTED
            );
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationEntry> entries =
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.entries();
        List<String> candidateKeys =
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateModernBiomeKeys();
        List<Path> mainSourceFiles;

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            mainSourceFiles = sourceFiles
                .filter(Files::isRegularFile)
                .toList();
        }

        assertEquals(
            expectedComponents,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.components()
        );
        assertEquals(15, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.entryCount());
        expectedComponents.forEach(component ->
            assertTrue(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.entryFor(component).isPresent()
            )
        );
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.consolidatedState() != null && !entry.consolidatedState().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.nextBoundary() != null && !entry.nextBoundary().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().noneMatch(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationEntry::runtimeReady));
        entries.forEach(entry ->
            assertEquals(
                expectedNextDecisionInputs.contains(entry.component()),
                entry.nextDecisionInput()
            )
        );

        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.selectorToWeightedCandidateBridgeConsolidationReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.selectorToWeightedCandidateBridgeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.selectorToWeightedCandidateBridgeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.pureNonRuntimeBridgeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.signedIntSelectorInputReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.weightedSelectorOrAdapterPathReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.stringCandidateKeyOutputReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateInventoryCompatibilityReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.sampleCandidateKeysVerified());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateKeysRemainStringOnly());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateKeyToHolderConversionGoNoGoIsNext());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateKeyToHolderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateKeyToHolderConversionImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateKeyToHolderConversionRuntimeReady());
        assertEquals(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationNextDecision
                .PROCEED_WITH_CANDIDATE_KEY_TO_HOLDER_CONVERSION_GO_NO_GO_NEXT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.selectedNextDecision()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .nextSliceMayAddCandidateKeyToHolderConversionGoNoGoDecision()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .nextSliceMayAddCandidateKeyToHolderConversionReadiness()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .nextSliceMayImplementCandidateKeyToHolderConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayUseRegistryLookupAccess()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayResolveHolders()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .nextSliceMayImplementResourceLocationConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .nextSliceMayImplementResourceKeyConversion()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayWireIntoGetNoiseBiome()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .nextSliceMayMakeGetNoiseBiomeUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .nextSliceMayMakeCollectPossibleBiomesUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayAddFallbackImplementation()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .nextSliceMayAddUsableCodecImplementation()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayRegisterCodec());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayRegisterBiomeSourceType()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayAddDimensionJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayAddDimensionTypeJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayCreateActiveCaveniaLevel()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayAddWorldgenResources()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayAddAccessOrTeleport()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayAddSpawning());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.nextSliceMayRegisterCavemanEntity()
        );
        assertEquals(
            "signed int selector input",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.selectorInputOutputShape()
        );
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateKeyOutputShape()
        );
        assertEquals(675, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.weightedSelectionTotalWeight());
        assertEquals(14, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateInventoryReady());
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateKeyForSampleOrigin()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateKeyForSampleMixed()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.candidateKeyForSampleNegative()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.sampleKeysExistInInventory());
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.expectedSampleOriginCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.expectedSampleMixedCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.expectedSampleNegativeCandidateKey()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.selectorInputAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.selectorInputAlgorithmRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.weightedSelectorCompatibilityReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.adapterCompatibilityReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.adapterRuntimeReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .bridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .bridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.bridgeReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.bridgeReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.bridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.bridgeGoNoGoRuntimeReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .selectorInputAlgorithmConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .selectorInputAlgorithmConsolidationRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.readinessChainConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.readinessChainRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.allCurrentReadinessLayersReady()
        );
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.runtimeReadyLayerCount());
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.consolidatedReadinessLayerCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.possibleBiomesReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.possibleBiomesRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .noiseBiomeSelectionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .noiseBiomeSelectionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.fallbackPolicyReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.fallbackPolicyRuntimeReady()
        );
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .fallbackCandidateModernBiomeKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .holderConversionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .holderConversionRuntimeReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.holderResolutionReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .resourceLocationConversionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.resourceKeyConversionReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.codecMethodShapeStubReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.codecMethodShapeRuntimeReady()
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean designatedSubclassRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.runtimeHolderReturnReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .getNoiseBiomeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.usableRuntimeBiomeSourceReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.registryLookupAccessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation
                .usableCodecImplementationReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.biomeSourceTypeRegistered());
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean normalRuntimeConstructionAllowed() {\n        return false;\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean unsupportedMethodStubsOnly() {\n        return true;\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean collectPossibleBiomesStubbed() {\n        return true;\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean getNoiseBiomeStubbed() {\n        return true;\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean codecMethodStubbed() {\n        return true;\n    }"
            )
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.dimensionBindingReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.activationAllowedInThisSlice()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.canActivateCaveniaNow());
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static int selectedSurfaceReadinessItemCount() {\n        return CaveniaRuntimeBiomeSource.selectedSurfaceReadinessItemCount();\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static int globalReadinessMatrixTotalRequirementCount() {\n        return CaveniaRuntimeBiomeSource.globalReadinessMatrixTotalRequirementCount();\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static int globalReadinessMatrixBlockedRequirementCount() {\n        return CaveniaRuntimeBiomeSource.globalReadinessMatrixBlockedRequirementCount();\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            readString(CONSOLIDATION_SOURCE).contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();\n    }"
            )
        );
        assertTrue(
            readString(DESIGNATED_SOURCE).contains(
                "public static boolean selectorToWeightedCandidateBridgeConsolidationReady() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation\n            .selectorToWeightedCandidateBridgeConsolidationReady();\n    }"
            )
        );
        assertTrue(
            readString(DESIGNATED_SOURCE).contains(
                "public static boolean selectorToWeightedCandidateBridgeConsolidationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            readString(DESIGNATED_SOURCE).contains(
                "public static boolean candidateKeyToHolderConversionGoNoGoIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation\n            .candidateKeyToHolderConversionGoNoGoIsNext();\n    }"
            )
        );

        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.components(),
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidationComponent
                .PURE_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_READY
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation.entries(),
            entries.get(0)
        );
        assertImmutableList(candidateKeys, candidateKeys.get(0));

        assertConsolidationSource(readString(COMPONENT_SOURCE));
        assertConsolidationSource(readString(ENTRY_SOURCE));
        assertConsolidationSource(readString(NEXT_DECISION_SOURCE));
        assertConsolidationSource(readString(CONSOLIDATION_SOURCE));

        assertEquals(
            1L,
            Files.walk(APP_WORLDGEN_SOURCE_ROOT)
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                .count()
        );
        assertOnlyDesignatedFileContains(mainSourceFiles, "extends BiomeSource");
        assertOnlyDesignatedFileContains(
            mainSourceFiles,
            "import net.minecraft.world.level.biome.BiomeSource;"
        );
        assertOnlyDesignatedFileContains(mainSourceFiles, "Holder<Biome>");
        assertOnlyDesignatedFileContains(mainSourceFiles, "Climate.Sampler");

        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaBiomeSource.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaChunkGenerator.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "ChunkGeneratorCavenia.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "MapGenCaveniaCaves.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaTeleporter.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaSpawnProvider.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaSpawnHandler.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaServerTickSpawner.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json"
        );
        try (var resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(
                        normalizedPath -> normalizedPath.contains("cavenia")
                            && (
                                normalizedPath.contains("/worldgen/")
                                    || normalizedPath.contains("/tags/worldgen/")
                                    || normalizedPath.contains("/biome_modifier/")
                            )
                    )
            );
        }
    }

    private static void assertConsolidationSource(String source) {
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
