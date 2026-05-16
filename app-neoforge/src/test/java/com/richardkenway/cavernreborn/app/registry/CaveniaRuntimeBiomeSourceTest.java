package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceTest {
    private static final String DESIGNATED_BUILDER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java";
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path DESIGNATED_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSource.java"
    );
    private static final Path BUILDER_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        DESIGNATED_BUILDER_FILE_NAME
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void guardedRuntimeBiomeSourceStubCompilesAgainstTheLocalAbstractShapeButStaysUnavailable() throws Exception {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> runtimeBiomeSourceClass = Class.forName(
            "com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource",
            false,
            classLoader
        );
        Class<?> biomeSourceClass = Class.forName("net.minecraft.world.level.biome.BiomeSource", false, classLoader);

        assertTrue(Files.exists(DESIGNATED_SOURCE));
        assertTrue(biomeSourceClass.isAssignableFrom(runtimeBiomeSourceClass));
        assertEquals("CaveniaRuntimeBiomeSource", runtimeBiomeSourceClass.getSimpleName());
        assertTrue(Modifier.isFinal(runtimeBiomeSourceClass.getModifiers()));
        assertEquals(1, runtimeBiomeSourceClass.getDeclaredConstructors().length);
        assertTrue(Modifier.isPrivate(runtimeBiomeSourceClass.getDeclaredConstructors()[0].getModifiers()));

        assertTrue(designatedSource.contains("public static boolean guardedSubclassStubReady() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean extendsMinecraftBiomeSource() {\n        return true;\n    }"));
        assertTrue(
            designatedSource.contains("public static String designatedSubclassFileName() {\n        return DESIGNATED_SUBCLASS_FILE_NAME;\n    }")
        );
        assertTrue(
            designatedSource.contains("public static String designatedSubclassSimpleName() {\n        return DESIGNATED_SUBCLASS_SIMPLE_NAME;\n    }")
        );
        assertTrue(designatedSource.contains("public static boolean normalRuntimeConstructionAllowed() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean usableRuntimeBehaviorReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean unsupportedMethodStubsOnly() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean codecMethodStubbed() {\n        return true;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean codecMethodShapeStubReady() {\n        return CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean collectPossibleBiomesStubbed() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean getNoiseBiomeStubbed() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean usableCodecImplementationReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean realCodecStillDeferred() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean staticCodecFieldPresent() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean recordCodecBuilderUsed() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static String codecMethodUnsupportedReason() {\n        return UNAVAILABLE_REASON;\n    }"));
        assertTrue(designatedSource.contains("public static boolean codecRegistered() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean biomeSourceTypeRegistered() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean registryLookupAccessReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean holderResourceKeyConversionReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean holderConversionReadinessReady() {\n        return CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderConversionReadinessReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean holderConversionRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean fallbackPolicyReadinessReady() {\n        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean fallbackPolicyRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean possibleBiomesReadinessReady() {\n        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesReadinessReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean possibleBiomesRuntimeReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean collectPossibleBiomesRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static int candidatePossibleBiomesEntryCount() {\n        return CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateEntryCount();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean noiseBiomeSelectionReadinessReady() {\n        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeSelectionReadinessReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean noiseBiomeSelectionRuntimeReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean getNoiseBiomeRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean noiseBiomeMethodShapePinned() {\n        return CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness.noiseBiomeMethodShapePinned();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean readinessChainConsolidationReady() {\n        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.readinessChainConsolidationReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean readinessChainRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationGoNoGoIsNext() {\n        return CaveniaRuntimeBiomeSourceReadinessChainConsolidation.selectorInputDerivationGoNoGoIsNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.decisionIsGoForSelectorInputDerivationReadinessNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationReadinessReady() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputDerivationReadinessReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean selectorInputDerivationImplementationReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean selectorInputDerivationRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationReadinessIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationGoNoGoDecision.nextSliceMayAddSelectorInputDerivationReadiness();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationImplementationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision\n            .decisionIsGoForPureNonRuntimeAlgorithmNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationAlgorithmReady() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputDerivationAlgorithmReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean selectorInputDerivationAlgorithmRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorInputDerivationPureAlgorithmIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationImplementationGoNoGoDecision\n            .nextSliceMayAddPureNonRuntimeSelectorInputAlgorithm();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String selectorInputDerivationAlgorithmOutputShape() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm.selectorInputOutputShape();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String selectorInputDerivationOutputShape() {\n        return CaveniaRuntimeBiomeSourceSelectorInputDerivationReadiness.selectorInputOutputShape();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision\n            .decisionIsGoForSelectorToWeightedCandidateBridgeReadinessNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeReadinessReady() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness\n            .selectorToWeightedCandidateBridgeReadinessReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeImplementationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision\n            .decisionIsGoForPureNonRuntimeBridgeNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeReady() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeImplementationReady() {\n        return false;\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeImplementationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeConsolidationReady() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation\n            .selectorToWeightedCandidateBridgeConsolidationReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeConsolidationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeReadinessIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeGoNoGoDecision\n            .nextSliceMayAddSelectorToWeightedCandidateBridgeReadiness();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgePureImplementationIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision\n            .nextSliceMayAddPureNonRuntimeSelectorToWeightedCandidateBridge();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String selectorToWeightedCandidateBridgeCandidateOutputShape() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyOutputShape();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionGoNoGoIsNext() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeConsolidation\n            .candidateKeyToHolderConversionGoNoGoIsNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision\n            .decisionIsGoForCandidateKeyToHolderConversionReadinessNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionReadinessReady() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness\n            .candidateKeyToHolderConversionReadinessReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionImplementationReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionReadinessIsNext() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionGoNoGoDecision\n            .nextSliceMayAddCandidateKeyToHolderConversionReadiness();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionImplementationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision\n            .decisionIsGoForGuardedCandidateKeyToHolderConversionImplementationNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionImplementationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConversionImplementationIsNext() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionImplementationGoNoGoDecision\n            .nextSliceMayAddGuardedConversionHelper();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConverterReady() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation\n            .candidateKeyToHolderConverterReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConverterRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String candidateKeyToHolderConverterFileName() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation.designatedConverterFileName();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConverterConsolidationReady() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation\n            .candidateKeyToHolderConverterConsolidationReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean candidateKeyToHolderConverterConsolidationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetGoNoGoIsNext() {\n        return CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterConsolidation\n            .collectPossibleBiomesHolderSetGoNoGoIsNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision\n            .decisionIsGoForCollectPossibleBiomesHolderSetReadinessNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetReadinessReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness\n            .collectPossibleBiomesHolderSetReadinessReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetImplementationGoNoGoDecisionReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision\n            .decisionIsGoForGuardedCollectPossibleBiomesHolderSetBuilderNext();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetImplementationReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetImplementationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision\n            .collectPossibleBiomesHolderSetBuilderReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetReadinessIsNext() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetGoNoGoDecision\n            .nextSliceMayAddCollectPossibleBiomesHolderSetReadiness();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderIsNext() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision\n            .nextSliceMayAddGuardedPreResolvedHolderSetBuilder();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String collectPossibleBiomesHolderSetOutputShape() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetReadiness.futureHolderSetOutputShape();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String collectPossibleBiomesHolderSetBuilderFileName() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetImplementationGoNoGoDecision\n            .nextSliceDesignatedBuilderFileName();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderConsolidationReady() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation\n            .collectPossibleBiomesHolderSetBuilderConsolidationReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean collectPossibleBiomesHolderSetBuilderConsolidationRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean runtimeConstructionFactoryGoNoGoIsNext() {\n        return CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilderConsolidation\n            .runtimeConstructionFactoryGoNoGoIsNext();\n    }"
            )
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
        assertTrue(
            designatedSource.contains(
                "public static String selectorToWeightedCandidateBridgeOutputShape() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.candidateKeyOutputShape();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String fallbackLegacyBiomeName() {\n        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeName();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static String fallbackCandidateModernBiomeKey() {\n        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernBiomeKey();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean dimensionBindingReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean activationAllowedInThisSlice() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean canActivateCaveniaNow() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean apiShapeInventoryReady() {\n        return CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean goNoGoDecisionAllowsSubclass() {\n        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddOneDesignatedRealSubclassFile();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean allGoNoGoGuardrailsEnforced() {\n        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.allGuardrailsEnforcedInThisSlice();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static int selectedSurfaceReadinessItemCount() {\n        return CaveniaBiomeSourceStrategyReadinessMatrix.totalReadinessItemCount();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static int globalReadinessMatrixTotalRequirementCount() {\n        return CaveniaActivationReadinessMatrix.totalRequirementCount();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static int globalReadinessMatrixBlockedRequirementCount() {\n        return CaveniaActivationReadinessMatrix.blockedRequirementCount();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSourceSkeleton.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSourceSkeleton.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSourceSkeleton.cavemanRemainsDeferred();\n    }"
            )
        );
        assertTrue(designatedSource.contains("throw unsupported(\"codec()\")"));
        assertTrue(designatedSource.contains("throw unsupported(\"collectPossibleBiomes()\")"));
        assertTrue(designatedSource.contains("throw unsupported(\"getNoiseBiome(int, int, int, Climate.Sampler)\")"));
        assertTrue(designatedSource.contains("not usable, registered, or activating yet"));
        assertFalse(designatedSource.contains("public static final MapCodec"));
        assertFalse(designatedSource.contains("static final MapCodec"));
        assertFalse(designatedSource.contains("CODEC ="));
        assertFalse(designatedSource.contains("RecordCodecBuilder"));
    }

    @Test
    void guardedRuntimeBiomeSourceStubKeepsRuntimeApiUsageScopedToTheDesignatedFile() throws IOException {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertTrue(designatedSource.contains("throw unsupported(\"codec()\")"));
        assertTrue(designatedSource.contains("throw unsupported(\"collectPossibleBiomes()\")"));
        assertTrue(designatedSource.contains("throw unsupported(\"getNoiseBiome(int, int, int, Climate.Sampler)\")"));
        assertFalse(designatedSource.contains("public static final MapCodec"));
        assertFalse(designatedSource.contains("static final MapCodec"));
        assertFalse(designatedSource.contains("CODEC ="));
        assertFalse(designatedSource.contains("Registry.register"));
        assertFalse(designatedSource.contains("RecordCodecBuilder"));
        assertFalse(designatedSource.contains("BuiltInRegistries.BIOME_SOURCE.register"));
        assertFalse(designatedSource.contains("DeferredRegister"));
        assertFalse(designatedSource.contains("RegistryLookup<"));
        assertFalse(designatedSource.contains("RegistryAccess"));
        assertFalse(designatedSource.contains("buildCandidateHolderList("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder."));
        assertFalse(designatedSource.contains("holderForCandidateKey("));
        assertFalse(designatedSource.contains("holderForCandidateKeyOrFallback("));

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            List<Path> regularFiles = sourceFiles
                .filter(Files::isRegularFile)
                .toList();

            List<Path> designatedFiles = regularFiles.stream()
                .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                .toList();
            List<Path> runtimeBiomeSourceFiles = regularFiles.stream()
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();

            assertEquals(1, designatedFiles.size());
            assertEquals(DESIGNATED_SOURCE, designatedFiles.get(0));
            assertOnlyDesignatedFileContains(regularFiles, "class CaveniaRuntimeBiomeSource extends BiomeSource");
            assertOnlyDesignatedFileContains(regularFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertOnlyDesignatedFileContains(regularFiles, "Holder<Biome>");
            assertOnlyDesignatedFileContains(regularFiles, "import com.mojang.serialization.MapCodec;");
            assertOnlyDesignatedFileContains(regularFiles, "protected MapCodec<? extends BiomeSource> codec()");
            assertOnlyDesignatedFileContains(regularFiles, "Climate.Sampler");
            assertOnlyConverterFileContains(runtimeBiomeSourceFiles, "ResourceLocation");
            assertOnlyConverterFileContains(runtimeBiomeSourceFiles, "ResourceKey<Biome>");
            assertOnlyFilesContain(
                runtimeBiomeSourceFiles,
                "HolderLookup",
                List.of(
                    BUILDER_SOURCE,
                    resolveProjectFile(
                        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app",
                        "worldgen", DESIGNATED_CONVERTER_FILE_NAME
                    )
                )
            );
            assertNoMainSourceContains(regularFiles, "Registry.register");
            assertNoMainSourceContains(regularFiles, "RecordCodecBuilder");
            assertNoMainSourceContains(regularFiles, "BuiltInRegistries.BIOME_SOURCE.register");
            assertNoMainSourceContains(regularFiles, "DeferredRegister");
            assertNoMainSourceContains(regularFiles, "RegistryLookup<");
            assertNoMainSourceContains(regularFiles, "RegistryAccess");
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
                "Expected the guarded runtime biome-source stub slice to keep active Cavenia resources absent"
            );
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
                List.of(
                    DESIGNATED_SOURCE,
                    BUILDER_SOURCE,
                    resolveProjectFile(
                        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app",
                        "worldgen", DESIGNATED_CONVERTER_FILE_NAME
                    )
                ),
                matchingFiles,
                "Expected only the designated subclass, builder and converter files to contain: " + fragment
            );
            return;
        }

        assertEquals(
            List.of(DESIGNATED_SOURCE),
            matchingFiles,
            "Expected only the designated subclass file to contain: " + fragment
        );
    }

    private static void assertOnlyConverterFileContains(List<Path> sourceFiles, String fragment) throws IOException {
        List<Path> matchingFiles = sourceFiles.stream()
            .filter(path -> {
                try {
                    return Files.readString(path).contains(fragment);
                } catch (IOException exception) {
                    throw new IllegalStateException("Could not read source file: " + path, exception);
                }
            })
            .toList();

        assertEquals(
            List.of(DESIGNATED_CONVERTER_FILE_NAME),
            matchingFiles.stream().map(path -> path.getFileName().toString()).toList(),
            "Expected only the designated converter file to contain: " + fragment
        );
    }

    private static void assertOnlyFilesContain(List<Path> sourceFiles, String fragment, List<Path> expected)
        throws IOException {
        List<Path> matchingFiles = sourceFiles.stream()
            .filter(path -> {
                try {
                    return Files.readString(path).contains(fragment);
                } catch (IOException exception) {
                    throw new IllegalStateException("Could not read source file: " + path, exception);
                }
            })
            .toList();

        assertEquals(expected, matchingFiles, "Unexpected files containing: " + fragment);
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
