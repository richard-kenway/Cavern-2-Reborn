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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSelectionAdapterContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaLegacyToModernBiomeKeyMappings;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorInputDerivationAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCodecMethodShapeStub;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceFallbackPolicyReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceHolderConversionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceNoiseBiomeSelectionReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourcePossibleBiomesReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWeightedBiomeSelectionAlgorithm;

class CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry.java"
    );
    private static final Path BRIDGE_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.java"
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
    void selectorToWeightedCandidateBridgeImplementsPureDeterministicCandidateSelectionOnly() throws IOException {
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent> expectedComponents =
            List.of(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .SIGNED_INT_SELECTOR_INPUT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .PURE_NON_RUNTIME_BRIDGE,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .WEIGHTED_SELECTOR_OR_ADAPTER_PATH,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .STRING_CANDIDATE_KEY_OUTPUT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .CANDIDATE_INVENTORY_COMPATIBILITY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NORMALIZATION_DELEGATED_TO_SELECTOR_PATH,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .DETERMINISTIC_BRIDGE_OUTPUT,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NO_MINECRAFT_RUNTIME_API_DEPENDENCY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NO_REGISTRY_OR_HOLDER_DEPENDENCY,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NO_FALLBACK_RUNTIME_USAGE,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .NO_GET_NOISE_BIOME_WIRING,
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                    .REAL_RUNTIME_SELECTION_STILL_DEFERRED
            );
        List<CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry> entries =
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.entries();
        String originKey = CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyForSampleOrigin();
        String mixedKey = CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyForSampleMixed();
        String negativeKey = CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyForSampleNegative();
        List<String> inventoryKeys = CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateModernBiomeKeys();
        List<Path> mainSourceFiles;

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            mainSourceFiles = sourceFiles
                .filter(Files::isRegularFile)
                .toList();
        }

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.components());
        assertEquals(12, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.bridgeDecision() != null && !entry.bridgeDecision().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.runtimeBoundary() != null && !entry.runtimeBoundary().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry::bridgeReady));
        assertTrue(entries.stream().noneMatch(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry::runtimeReady));
        assertTrue(
            entries.stream().noneMatch(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry
                    ::minecraftRuntimeApiRequired
            )
        );
        assertTrue(
            entries.stream().noneMatch(
                CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationEntry
                    ::activationAllowedInThisSlice
            )
        );

        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.pureNonRuntimeBridgeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.signedIntSelectorInputReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.weightedSelectorOrAdapterPathReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.stringCandidateKeyOutputReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateInventoryCompatibilityReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.normalizationDelegatedToSelectorPath());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.deterministicBridgeOutputReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.minecraftRuntimeApiDependencyPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.registryOrHolderDependencyPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.fallbackRuntimeUsagePresent());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.getNoiseBiomeWiringReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.runtimeSelectionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.realRuntimeSelectionStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.canActivateCaveniaNow());

        assertEquals("minecraft:taiga", originKey);
        assertEquals("minecraft:desert", mixedKey);
        assertEquals("minecraft:desert", negativeKey);
        assertFalse(originKey.isBlank());
        assertFalse(mixedKey.isBlank());
        assertFalse(negativeKey.isBlank());
        assertTrue(inventoryKeys.contains(originKey));
        assertTrue(inventoryKeys.contains(mixedKey));
        assertTrue(inventoryKeys.contains(negativeKey));
        assertEquals(
            originKey,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge
                .candidateKeyForSelectorInput(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge
                    .deriveSelectorInputSampleOrigin())
        );
        assertEquals(
            mixedKey,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge
                .candidateKeyForSelectorInput(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge
                    .deriveSelectorInputSampleMixed())
        );
        assertEquals(
            negativeKey,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge
                .candidateKeyForSelectorInput(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge
                    .deriveSelectorInputSampleNegative())
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.sampleKeysExistInInventory());

        assertEquals(
            "signed int selector input",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorInputOutputShape()
        );
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeyOutputShape()
        );
        assertEquals(
            "not wired into getNoiseBiome",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.bridgeRuntimeBoundary()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.bridgeImplementationGoNoGoDecisionReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.bridgeImplementationGoNoGoRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.bridgeReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.bridgeReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorInputAlgorithmReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorInputAlgorithmRuntimeReady());
        assertEquals(-104499101, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.deriveSelectorInputSampleOrigin());
        assertEquals(700186390, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.deriveSelectorInputSampleMixed());
        assertEquals(
            -1039724642,
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.deriveSelectorInputSampleNegative()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.weightedSelectorCompatibilityReady());
        assertEquals(675, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.weightedSelectionTotalWeight());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.adapterCompatibilityReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.adapterRuntimeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateKeysStillStringOnly());
        assertEquals(
            CaveniaLegacyToModernBiomeKeyMappings.candidateModernBiomeKeys(),
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateModernBiomeKeys()
        );
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.fallbackLegacyBiomeName());
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.fallbackCandidateModernBiomeKey()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.resourceKeyConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.codecMethodShapeRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.runtimeHolderReturnReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.dimensionBindingReady());

        assertTrue(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationGoNoGoDecision
                .selectorToWeightedCandidateBridgeImplementationRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness.selectorToWeightedCandidateBridgeReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeReadiness
                .selectorToWeightedCandidateBridgeImplementationRuntimeReady()
        );

        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.components(),
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridgeImplementationComponent
                .SIGNED_INT_SELECTOR_INPUT
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.entries(),
            entries.get(0)
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.candidateModernBiomeKeys(),
            inventoryKeys.get(0)
        );

        assertBridgeImplementationSource(readString(COMPONENT_SOURCE));
        assertBridgeImplementationSource(readString(ENTRY_SOURCE));
        assertBridgeImplementationSource(readString(BRIDGE_SOURCE));

        assertEquals(
            1L,
            mainSourceFiles.stream()
                .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                .count()
        );
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

        String designatedSource = readString(DESIGNATED_SOURCE);
        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("import com.mojang.serialization.MapCodec;"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeReady() {\n        return CaveniaRuntimeBiomeSourceSelectorToWeightedCandidateBridge.selectorToWeightedCandidateBridgeReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean selectorToWeightedCandidateBridgeRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            readString(BRIDGE_SOURCE).contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertTrue(
            readString(BRIDGE_SOURCE).contains(
                "public static int selectedSurfaceReadinessItemCount() {\n        return CaveniaRuntimeBiomeSource.selectedSurfaceReadinessItemCount();\n    }"
            )
        );
        assertTrue(
            readString(BRIDGE_SOURCE).contains(
                "public static int globalReadinessMatrixTotalRequirementCount() {\n        return CaveniaRuntimeBiomeSource.globalReadinessMatrixTotalRequirementCount();\n    }"
            )
        );
        assertTrue(
            readString(BRIDGE_SOURCE).contains(
                "public static int globalReadinessMatrixBlockedRequirementCount() {\n        return CaveniaRuntimeBiomeSource.globalReadinessMatrixBlockedRequirementCount();\n    }"
            )
        );
        assertTrue(
            readString(BRIDGE_SOURCE).contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            readString(BRIDGE_SOURCE).contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            readString(BRIDGE_SOURCE).contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();\n    }"
            )
        );
    }

    private static void assertBridgeImplementationSource(String source) {
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
        assertEquals(List.of(DESIGNATED_SOURCE), containingFiles, "Unexpected designated-file ownership for: " + text);
    }

    private static void assertNoMainSourceContains(List<Path> files, String text) throws IOException {
        List<Path> containingFiles = files.stream()
            .filter(path -> readString(path).contains(text))
            .toList();
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
