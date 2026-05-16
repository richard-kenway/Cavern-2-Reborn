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
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSkeleton;

class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadinessTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeEntry.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.java"
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
    void selectorToWeightedCandidateBridgeReadinessPinsTheFutureBridgeSurfaceAndKeepsRuntimeBehaviorAbsent()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.SELECTOR_INPUT_SOURCE,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.WEIGHTED_SELECTOR_SOURCE,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.ADAPTER_SOURCE,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.CANDIDATE_INVENTORY_SOURCE,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.SELECTOR_INPUT_SHAPE,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.TOTAL_WEIGHT_CONTRACT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.NORMALIZATION_POLICY,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.CANDIDATE_KEY_OUTPUT_SHAPE,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.CANDIDATE_ENTRY_COUNT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.FALLBACK_POLICY_DEPENDENCY,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.POSSIBLE_BIOMES_CONSTRAINT,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.NOISE_BIOME_SELECTION_DEPENDENCY,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.NO_BRIDGE_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.NO_SELECTOR_OR_ADAPTER_CALLS,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.NO_RUNTIME_WIRING,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent
                .REAL_SELECTOR_TO_WEIGHTED_CANDIDATE_BRIDGE_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeEntry> entries =
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.entries();

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.components());
        assertEquals(16, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.bridgeSurface() != null && !entry.bridgeSurface().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.futurePolicy() != null && !entry.futurePolicy().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeEntry::readinessPinned));
        assertTrue(entries.stream().noneMatch(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeEntry::bridgeImplementationReady));
        assertTrue(entries.stream().noneMatch(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeEntry::runtimeReady));
        assertTrue(
            entries.stream().noneMatch(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeEntry::activationAllowedInThisSlice
            )
        );

        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
            .selectorToWeightedCandidateBridgeReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectorInputSourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.weightedSelectorSourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.adapterSourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateInventorySourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectorInputShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.totalWeightContractPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.normalizationPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateKeyOutputShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateEntryCountPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.fallbackPolicyDependencyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.possibleBiomesConstraintPinned());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.noiseBiomeSelectionDependencyPinned());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.bridgeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectorOrAdapterCallsReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.runtimeWiringReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
                .realSelectorToWeightedCandidateBridgeStillDeferred()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.canActivateCaveniaNow());

        assertEquals(
            "CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectorInputSource()
        );
        assertEquals(
            "signed int selector input",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectorInputOutputShape()
        );
        assertEquals(
            "CaveniaWeightedBiomeSelectionAlgorithm",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.weightedSelectorSource()
        );
        assertEquals(
            "CaveniaBiomeSelectionAdapterContract",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.adapterSource()
        );
        assertEquals(
            "CaveniaLegacyToModernBiomeKeyMappings",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateInventorySource()
        );
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateKeyOutputShape()
        );
        assertEquals(
            "normalization and wrapping remain delegated to the existing selector or adapter path",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.normalizationPolicy()
        );
        assertEquals(
            "not wired into getNoiseBiome",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.bridgeRuntimeBoundary()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectorInputAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectorInputAlgorithmRuntimeReady());
        assertEquals(-104499101, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.deriveSelectorInputSampleOrigin());
        assertEquals(700186390, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.deriveSelectorInputSampleMixed());
        assertEquals(
            -1039724642,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.deriveSelectorInputSampleNegative()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.weightedSelectorCompatibilityReady());
        assertEquals(675, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.weightedSelectionTotalWeight());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.adapterCompatibilityReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.adapterRuntimeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateKeysStillStringOnly());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.fallbackLegacyBiomeName());
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.fallbackCandidateModernBiomeKey()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.bridgeGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.bridgeGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectorInputAlgorithmConsolidationReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
                .selectorInputAlgorithmConsolidationRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
                .selectorInputAlgorithmSelectedNextDecisionMatches()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.implementationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.implementationGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.runtimeReadyLayerCount());
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.consolidatedReadinessLayerCount()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.resourceKeyConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.codecMethodShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.runtimeHolderReturnReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectedSurfaceReadinessItemCount());
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.cavemanRemainsDeferred());

        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectorToWeightedCandidateBridgeReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputAlgorithmConsolidation
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceReadinessChainConsolidation
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourcePossibleBiomesReadiness
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceFallbackPolicyReadiness
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceHolderConversionReadiness
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceCodecMethodShapeStub
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceApiShapeInventory
                .selectorToWeightedCandidateBridgeReadinessRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.selectorToWeightedCandidateBridgeReadinessRuntimeReady());
        assertTrue(CaveniaBiomeSourceStrategyPlan.selectorToWeightedCandidateBridgeReadinessReady());
        assertFalse(CaveniaBiomeSourceStrategyPlan.selectorToWeightedCandidateBridgeReadinessRuntimeReady());

        assertImmutableList(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.components(),
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeComponent.SELECTOR_INPUT_SOURCE
        );
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void selectorToWeightedCandidateBridgeReadinessSourcesStayNonRuntimeAndKeepActivationBlocked()
        throws IOException {
        String componentSource = Files.readString(COMPONENT_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String readinessSource = Files.readString(READINESS_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertPureBridgeReadinessSource(componentSource);
        assertPureBridgeReadinessSource(entrySource);
        assertPureBridgeReadinessSource(readinessSource);

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
            List<Path> readinessFiles = List.of(COMPONENT_SOURCE, ENTRY_SOURCE, READINESS_SOURCE);

            assertEquals(1, designatedFiles.size());
            assertEquals(DESIGNATED_SOURCE, designatedFiles.get(0));
            assertOnlyDesignatedFileContains(regularFiles, "class CaveniaRuntimeBiomeSource extends BiomeSource");
            assertOnlyDesignatedFileContains(regularFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertOnlyDesignatedFileContains(regularFiles, "Holder<Biome>");
            assertOnlyDesignatedFileContains(regularFiles, "import com.mojang.serialization.MapCodec;");
            assertOnlyDesignatedFileContains(regularFiles, "protected MapCodec<? extends BiomeSource> codec()");
            assertOnlyDesignatedFileContains(regularFiles, "Climate.Sampler");

            assertNoMainSourceContains(readinessFiles, "import net.minecraft.world.level.biome.Climate;");
            assertNoMainSourceContains(readinessFiles, "Climate.Sampler");
            assertNoMainSourceContains(readinessFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertNoMainSourceContains(readinessFiles, "Holder<Biome>");
            assertNoMainSourceContains(readinessFiles, "ResourceLocation");
            assertNoMainSourceContains(readinessFiles, "ResourceKey<Biome>");
            assertNoMainSourceContains(readinessFiles, "RegistryLookup<");
            assertNoMainSourceContains(readinessFiles, "RegistryAccess");
            assertNoMainSourceContains(readinessFiles, "MapCodec");
            assertNoMainSourceContains(readinessFiles, "Codec<");
            assertNoMainSourceContains(readinessFiles, "RecordCodecBuilder");
            assertNoMainSourceContains(readinessFiles, "Random");
            assertNoMainSourceContains(readinessFiles, "ThreadLocalRandom");
            assertNoMainSourceContains(readinessFiles, "System.currentTimeMillis");
            assertNoMainSourceContains(readinessFiles, "System.nanoTime");
            assertNoMainSourceContains(readinessFiles, "deriveSelectorInput(");
            assertNoMainSourceContains(readinessFiles, ".select(");
            assertNoMainSourceContains(readinessFiles, ".adapt(");

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
                "Expected the selector-to-weighted-candidate bridge readiness slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertPureBridgeReadinessSource(String source) {
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
