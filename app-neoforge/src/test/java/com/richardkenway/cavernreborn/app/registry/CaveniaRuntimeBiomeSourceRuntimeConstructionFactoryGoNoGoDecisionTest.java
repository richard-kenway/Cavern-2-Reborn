package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryNextDecision;

class CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecisionTest {
    private static final String DESIGNATED_BUILDER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java";
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path NEXT_DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryNextDecision.java"
    );
    private static final Path GUARDRAIL_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.java"
    );
    private static final Path GUARDRAIL_CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract.java"
    );
    private static final Path DECISION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.java"
    );
    private static final Path DESIGNATED_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSource.java"
    );
    private static final Path BUILDER_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        DESIGNATED_BUILDER_FILE_NAME
    );
    private static final Path CONVERTER_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        DESIGNATED_CONVERTER_FILE_NAME
    );
    private static final Path APP_WORLDGEN_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void runtimeConstructionFactoryGoNoGoDecisionKeepsRuntimeBlockedWhileSelectingReadinessOnlyNext()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail> expectedGuardrails = List.of(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_RUNTIME_CONSTRUCTION_FACTORY_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_PRIVATE_CONSTRUCTOR_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_FACTORY_METHOD_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_PRE_RESOLVED_HOLDER_STATE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_HOLDER_SET_BUILDER_DEPENDENCY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_LOOKUP_PROVIDER_INPUT_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_IMMUTABLE_STATE_STORAGE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_COLLECT_POSSIBLE_BIOMES_STREAM_SOURCE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_NO_ACTIVATION_BOUNDARY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .NO_RUNTIME_CONSTRUCTION_FACTORY_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_PUBLIC_CONSTRUCTOR,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_FACTORY_METHOD,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_PRE_RESOLVED_HOLDER_STATE,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_COLLECT_POSSIBLE_BIOMES_WIRING,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_GET_NOISE_BIOME_WIRING,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_GET_NOISE_BIOME_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .NO_CODEC_OR_BIOME_SOURCE_TYPE_REGISTRATION,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_GENERATOR_REGISTRATION,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_DIMENSION_ACTIVATION,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.NO_WORLDGEN_ACCESS_SPAWNING,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail.KEEP_RUNTIME_METHODS_UNSUPPORTED
        );
        Set<CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail> allowedReadinessActions = Set.of(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_RUNTIME_CONSTRUCTION_FACTORY_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_PRIVATE_CONSTRUCTOR_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_FACTORY_METHOD_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_PRE_RESOLVED_HOLDER_STATE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_HOLDER_SET_BUILDER_DEPENDENCY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_LOOKUP_PROVIDER_INPUT_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_IMMUTABLE_STATE_STORAGE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_COLLECT_POSSIBLE_BIOMES_STREAM_SOURCE_POLICY_PINNING_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrail
                .ALLOW_NO_ACTIVATION_BOUNDARY_PINNING_NEXT
        );
        List<CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract> guardrails =
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.guardrails();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        List<Path> runtimeBiomeSourceFiles;
        List<Path> decisionSources = List.of(
            NEXT_DECISION_SOURCE,
            GUARDRAIL_SOURCE,
            GUARDRAIL_CONTRACT_SOURCE,
            DECISION_SOURCE
        );

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            runtimeBiomeSourceFiles = sourceFiles
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();
        }

        assertEquals(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryNextDecision
                .PROCEED_WITH_RUNTIME_CONSTRUCTION_FACTORY_READINESS_NEXT,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.selectedDecision()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .decisionIsGoForRuntimeConstructionFactoryReadinessNext()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .decisionIsReadinessOnlyForNextSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .runtimeConstructionFactoryReadinessImplementedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .runtimeConstructionFactoryImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .runtimeConstructionFactoryRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .runtimeWiringAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .activationAllowedInThisSlice()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .canActivateCaveniaNow()
        );

        assertEquals(
            expectedGuardrails,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.guardrailValues()
        );
        assertEquals(22, CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.guardrailCount());
        assertTrue(guardrails.stream().allMatch(contract -> !contract.sourceContractName().isBlank()));
        assertTrue(guardrails.stream().allMatch(contract -> !contract.blocker().isBlank()));
        assertTrue(guardrails.stream().allMatch(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract::enforcedInThisSlice
        ));
        assertTrue(guardrails.stream().allMatch(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGuardrailContract::blocksActivation
        ));
        assertTrue(guardrails.stream().allMatch(
            contract -> !contract.allowsNextSliceImplementationAction()
        ));
        assertTrue(guardrails.stream().allMatch(
            contract -> !contract.allowsNextSliceRuntimeWiringAction()
        ));
        assertTrue(guardrails.stream().allMatch(
            contract -> !contract.allowsNextSliceActivationAction()
        ));
        guardrails.forEach(contract -> assertEquals(
            allowedReadinessActions.contains(contract.guardrail()),
            contract.allowsNextSliceReadinessAction()
        ));
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .allGuardrailsEnforcedInThisSlice()
        );
        assertEquals(
            9,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .allowedNextSliceReadinessActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .allowedNextSliceImplementationActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .allowedNextSliceRuntimeWiringActionCount()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .allowedNextSliceActivationActionCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .anyImplementationAllowedByGuardrails()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .anyRuntimeWiringAllowedByGuardrails()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .anyActivationAllowedByGuardrails()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddRuntimeConstructionFactoryReadiness()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayPinPrivateConstructorPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayPinFactoryMethodPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayPinPreResolvedHolderStatePolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayPinHolderSetBuilderDependency()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayPinLookupProviderInputPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayPinImmutableStateStoragePolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayPinCollectPossibleBiomesStreamSourcePolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayPinNoActivationBoundary()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayImplementRuntimeConstructionFactory()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddPublicConstructor()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddFactoryMethod()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddPreResolvedHolderState()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayWireBuilderIntoCollectPossibleBiomes()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayImplementCollectPossibleBiomes()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayWireBuilderIntoGetNoiseBiome()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayMakeGetNoiseBiomeUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayMakeCollectPossibleBiomesUsable()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddUsableCodecImplementation()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayRegisterCodec()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayRegisterBiomeSourceType()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayRegisterGenerator()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddDimensionJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddDimensionTypeJson()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayCreateActiveCaveniaLevel()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddWorldgenResources()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddAccessOrTeleport()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayAddSpawning()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .nextSliceMayRegisterCavemanEntity()
        );

        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .collectPossibleBiomesHolderSetBuilderConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .collectPossibleBiomesHolderSetBuilderConsolidationRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .builderConsolidationSelectedNextDecisionMatches()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .collectPossibleBiomesHolderSetBuilderReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .collectPossibleBiomesHolderSetBuilderRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .buildCandidateHolderListReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .candidateKeyToHolderConverterReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .candidateKeyToHolderConverterRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .holderForCandidateKeyReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .holderForCandidateKeyOrFallbackReady()
        );
        assertEquals(14, CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.candidateEntryCount());
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .candidateInventoryReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .candidateKeysStillStringOnlyOutsideBuilder()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .possibleBiomesReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .possibleBiomesRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .futurePossibleBiomesSourceIsCandidateInventory()
        );
        assertEquals(
            14,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .futurePossibleBiomesCandidateEntryCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .futurePossibleBiomesFallbackIfEmptyPinned()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .holderConversionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .holderConversionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .fallbackPolicyReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .fallbackPolicyRuntimeReady()
        );
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .fallbackCandidateModernBiomeKey()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .noiseBiomeSelectionReadinessReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .noiseBiomeSelectionRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .readinessChainConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .readinessChainRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .allCurrentReadinessLayersReady()
        );
        assertEquals(
            0,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.runtimeReadyLayerCount()
        );
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .consolidatedReadinessLayerCount()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .codecMethodShapeStubReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .codecMethodShapeRuntimeReady()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .designatedSubclassReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .designatedSubclassRuntimeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .normalRuntimeConstructionAllowed()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .publicConstructionAllowed()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .factoryMethodReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .preResolvedHolderStateReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .collectPossibleBiomesImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .getNoiseBiomeImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .usableRuntimeBiomeSourceReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .usableCodecImplementationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .codecRegistered()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .biomeSourceTypeRegistered()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .unsupportedMethodStubsOnly()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .collectPossibleBiomesStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .getNoiseBiomeStubbed()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .codecMethodStubbed()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .dimensionBindingReady()
        );
        assertEquals(
            68,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .selectedSurfaceReadinessItemCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.dimensionJsonPresent()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.dimensionTypeJsonPresent()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.cavemanRemainsDeferred()
        );

        assertTrue(
            designatedSource.contains(
                "public static boolean runtimeConstructionFactoryGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision\n            .decisionIsGoForRuntimeConstructionFactoryReadinessNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean runtimeConstructionFactoryRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean runtimeConstructionFactoryReadinessIsNext() {\n        return CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision\n            .nextSliceMayAddRuntimeConstructionFactoryReadiness();\n    }"
            )
        );

        assertImmutableList(guardrails, guardrails.getFirst());
        assertImmutableList(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.guardrailValues(),
            expectedGuardrails.getFirst()
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.candidateModernBiomeKeys(),
            CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision
                .candidateModernBiomeKeys()
                .getFirst()
        );
        assertThrows(
            UnsupportedOperationException.class,
            () -> CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryGoNoGoDecision.guardrails().add(null)
        );

        assertTrue(Files.exists(NEXT_DECISION_SOURCE));
        assertTrue(Files.exists(GUARDRAIL_SOURCE));
        assertTrue(Files.exists(GUARDRAIL_CONTRACT_SOURCE));
        assertTrue(Files.exists(DECISION_SOURCE));
        assertFalse(Files.exists(RESOURCES_ROOT.resolve("dimension").resolve("cavenia.json")));
        assertFalse(Files.exists(RESOURCES_ROOT.resolve("dimension_type").resolve("cavenia.json")));
        assertSourceSafety(runtimeBiomeSourceFiles, decisionSources);
    }

    private static void assertSourceSafety(List<Path> runtimeBiomeSourceFiles, List<Path> decisionSources)
        throws IOException {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        decisionSources.forEach(path -> {
            String source = read(path);
            assertFalse(source.contains("import net.minecraft"), () -> "Unexpected runtime import in " + path);
            assertFalse(source.contains("ResourceLocation"), () -> "Unexpected ResourceLocation in " + path);
            assertFalse(source.contains("ResourceKey<Biome>"), () -> "Unexpected ResourceKey<Biome> in " + path);
            assertFalse(source.contains("HolderLookup"), () -> "Unexpected HolderLookup in " + path);
            assertFalse(source.contains("Registries.BIOME"), () -> "Unexpected Registries.BIOME in " + path);
            assertFalse(source.contains("RegistryAccess"), () -> "Unexpected RegistryAccess in " + path);
            assertFalse(source.contains("MapCodec"), () -> "Unexpected MapCodec in " + path);
            assertFalse(source.contains("Codec<"), () -> "Unexpected Codec< in " + path);
            assertFalse(source.contains("RecordCodecBuilder"), () -> "Unexpected RecordCodecBuilder in " + path);
        });

        assertEquals(
            1L,
            runtimeBiomeSourceFiles.stream()
                .filter(path -> path.getFileName().toString().equals(DESIGNATED_BUILDER_FILE_NAME))
                .count()
        );
        assertEquals(
            1L,
            runtimeBiomeSourceFiles.stream()
                .filter(path -> path.getFileName().toString().equals(DESIGNATED_CONVERTER_FILE_NAME))
                .count()
        );

        assertOnlyFilesContain(
            runtimeBiomeSourceFiles,
            "HolderLookup",
            List.of(BUILDER_SOURCE, CONVERTER_SOURCE)
        );
        assertOnlyFilesContain(
            runtimeBiomeSourceFiles,
            "Holder<Biome>",
            List.of(DESIGNATED_SOURCE, BUILDER_SOURCE, CONVERTER_SOURCE)
        );
        assertOnlyFilesContain(
            runtimeBiomeSourceFiles,
            "import net.minecraft.world.level.biome.Biome;",
            List.of(DESIGNATED_SOURCE, BUILDER_SOURCE, CONVERTER_SOURCE)
        );
        assertOnlyFilesContain(runtimeBiomeSourceFiles, "ResourceLocation", List.of(CONVERTER_SOURCE));
        assertOnlyFilesContain(runtimeBiomeSourceFiles, "ResourceKey<Biome>", List.of(CONVERTER_SOURCE));
        assertOnlyFilesContain(
            runtimeBiomeSourceFiles,
            "lookupProvider.lookup(Registries.BIOME)",
            List.of(CONVERTER_SOURCE)
        );

        assertNoMainSourceContains(runtimeBiomeSourceFiles, "Registry.register");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "BuiltInRegistries.BIOME_SOURCE.register");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "DeferredRegister");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "RecordCodecBuilder");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "public static final MapCodec");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "static final MapCodec");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "CODEC =");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "return getNoiseBiome");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "return collectPossibleBiomes");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "registerConfiguredFeature(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "registerPlacedFeature(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "registerConfiguredCarver(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "addFreshEntity(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "changeDimension(");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, ".teleportTo(");

        assertFalse(designatedSource.contains("buildCandidateHolderList("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder."));
        assertFalse(designatedSource.contains("holderForCandidateKey("));
        assertFalse(designatedSource.contains("holderForCandidateKeyOrFallback("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter."));
        assertFalse(designatedSource.contains("public CaveniaRuntimeBiomeSource("));
        assertFalse(designatedSource.contains("public static CaveniaRuntimeBiomeSource "));
        assertFalse(designatedSource.contains("private final List<Holder<Biome>>"));

        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryReadiness.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryComponent.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryEntry.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceRuntimeFactory.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceRuntimeConstructionFactoryImplementation.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceFactory.java"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
            "CaveniaRuntimeBiomeSourceConstruction.java"
        );
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
    }

    @SuppressWarnings("unchecked")
    private static void assertImmutableList(List<?> list, Object probe) {
        assertThrows(UnsupportedOperationException.class, () -> ((List<Object>) list).add(probe));
    }

    private static void assertOnlyFilesContain(List<Path> files, String text, List<Path> expected) throws IOException {
        List<Path> matchingFiles = files.stream()
            .filter(file -> read(file).contains(text))
            .toList();

        assertEquals(
            expected.stream().map(path -> path.getFileName().toString()).toList(),
            matchingFiles.stream().map(path -> path.getFileName().toString()).toList()
        );
    }

    private static void assertNoMainSourceContains(List<Path> files, String text) throws IOException {
        Optional<Path> matchingFile = files.stream()
            .filter(file -> read(file).contains(text))
            .findFirst();

        assertTrue(matchingFile.isEmpty(), () -> "Unexpected fragment in " + matchingFile.orElseThrow());
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)));
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read source file: " + path, exception);
        }
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path path = resolveProjectPathOrSibling(first, more);
        assertTrue(Files.exists(path), () -> "Expected project file to exist: " + path);
        return path;
    }

    private static Path resolveProjectPathOrSibling(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        while (current != null) {
            Path candidate = current.resolve(first);
            for (String part : more) {
                candidate = candidate.resolve(part);
            }
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        Path fallback = Path.of(first);
        for (String part : more) {
            fallback = fallback.resolve(part);
        }
        return fallback.toAbsolutePath();
    }
}
