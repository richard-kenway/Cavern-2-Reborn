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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness;

class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadinessTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.java"
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
    void candidateKeyToHolderConversionReadinessPinsTheFutureStringToHolderPathWithoutImplementingIt()
        throws IOException {
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.STRING_CANDIDATE_KEY_INPUT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.CANDIDATE_KEY_INVENTORY_SOURCE,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.RESOURCE_LOCATION_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.RESOURCE_KEY_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.BIOME_REGISTRY_LOOKUP_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.HOLDER_RESOLUTION_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.HOLDER_RETURN_BOUNDARY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.MISSING_CANDIDATE_KEY_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.UNRESOLVED_RESOURCE_LOCATION_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.UNRESOLVED_RESOURCE_KEY_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.MISSING_HOLDER_FALLBACK_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.NO_CONVERSION_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent
                .REAL_CANDIDATE_KEY_TO_HOLDER_CONVERSION_STILL_DEFERRED
        );
        Set<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent> registryLookupRequiredComponents = Set.of(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.BIOME_REGISTRY_LOOKUP_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.HOLDER_RESOLUTION_POLICY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.HOLDER_RETURN_BOUNDARY,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionComponent.MISSING_HOLDER_FALLBACK_POLICY
        );
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry> entries =
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.entries();
        List<String> candidateKeys =
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateModernBiomeKeys();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        List<Path> mainSourceFiles;

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            mainSourceFiles = sourceFiles
                .filter(Files::isRegularFile)
                .toList();
        }

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.components());
        assertEquals(14, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.conversionSurface() != null && !entry.conversionSurface().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.futurePolicy() != null && !entry.futurePolicy().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionEntry::readinessPinned));
        assertTrue(entries.stream().allMatch(entry -> !entry.conversionImplementationReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.registryLookupAvailable()));
        assertTrue(entries.stream().allMatch(entry -> !entry.holderResolved()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));
        entries.forEach(entry -> assertEquals(
            registryLookupRequiredComponents.contains(entry.component()),
            entry.registryLookupRequired()
        ));

        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateKeyToHolderConversionReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.stringCandidateKeyInputPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateKeyInventorySourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.resourceLocationPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.resourceKeyPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.biomeRegistryLookupPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.holderResolutionPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.holderReturnBoundaryPinned());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.missingCandidateKeyPolicyPinned());
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.unresolvedResourceLocationPolicyPinned()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.unresolvedResourceKeyPolicyPinned());
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.missingHolderFallbackPolicyPinned()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.conversionImplementationReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.registryLookupRequiredForRuntimeConversion()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.holderReturnReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.resourceKeyConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.fallbackRuntimeReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
                .realCandidateKeyToHolderConversionStillDeferred()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.canActivateCaveniaNow());
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateKeyInputShape()
        );
        assertEquals(
            "future implementation may parse candidate key string into resource-location only after a separate implementation decision",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.futureResourceLocationPolicy()
        );
        assertEquals(
            "future implementation may construct biome resource key only after resource-location conversion and a separate implementation decision",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.futureResourceKeyPolicy()
        );
        assertEquals(
            "future implementation requires biome registry lookup but access is not available in this slice",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.futureRegistryLookupPolicy()
        );
        assertEquals(
            "future implementation may resolve biome holder only after registry lookup access is explicitly allowed",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.futureHolderResolutionPolicy()
        );
        assertEquals(
            "future holder output may feed getNoiseBiome and collectPossibleBiomes only after runtime wiring is explicitly allowed",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.futureHolderReturnBoundary()
        );
        assertEquals(
            "fallback remains PLAINS to minecraft:plains readiness data and is not runtime-resolved in this slice",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.futureMissingFallbackPolicy()
        );
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
                .selectorToWeightedCandidateBridgeConsolidationReady()
        );
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
                .selectorToWeightedCandidateBridgeConsolidationRuntimeReady()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.selectorToWeightedCandidateBridgeReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
                .selectorToWeightedCandidateBridgeRuntimeReady()
        );
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateKeyOutputShape()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateKeysRemainStringOnly());
        assertEquals(14, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateInventoryReady());
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateKeyForSampleOrigin()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateKeyForSampleMixed()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.candidateKeyForSampleNegative()
        );
        assertEquals(
            "minecraft:taiga",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.expectedSampleOriginCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.expectedSampleMixedCandidateKey()
        );
        assertEquals(
            "minecraft:desert",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.expectedSampleNegativeCandidateKey()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.sampleKeysExistInInventory());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.goNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.goNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.holderConversionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.fallbackPolicyRuntimeReady());
        assertEquals(
            "PLAINS",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.fallbackLegacyBiomeName()
        );
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.fallbackCandidateModernBiomeKey()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.possibleBiomesRuntimeReady());
        assertTrue(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.noiseBiomeSelectionReadinessReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.runtimeReadyLayerCount());
        assertEquals(
            6,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.consolidatedReadinessLayerCount()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.codecMethodShapeRuntimeReady());
        String readinessSource = Files.readString(READINESS_SOURCE);
        assertTrue(
            readinessSource.contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.runtimeHolderReturnReady());
        assertFalse(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.collectPossibleBiomesImplementationReady()
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.selectedSurfaceReadinessItemCount());
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
                .globalReadinessMatrixTotalRequirementCount()
        );
        assertEquals(
            46,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness
                .globalReadinessMatrixBlockedRequirementCount()
        );
        assertTrue(
            readinessSource.contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            readinessSource.contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            readinessSource.contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();\n    }"
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

        assertImmutableList(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.entries(),
            entries.get(0)
        );
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConversionReadiness.components(),
            expectedComponents.get(0)
        );
        assertImmutableList(candidateKeys, candidateKeys.get(0));
        entries.forEach(entry -> assertNotNull(entry.component()));

        assertOnlyDesignatedFileContains(mainSourceFiles, "extends BiomeSource");
        assertOnlyDesignatedFileContains(mainSourceFiles, "import net.minecraft.world.level.biome.BiomeSource;");
        assertOnlyDesignatedFileContains(mainSourceFiles, "Holder<Biome>");
        assertOnlyDesignatedFileContains(mainSourceFiles, "import com.mojang.serialization.MapCodec;");
        assertOnlyDesignatedFileContains(mainSourceFiles, "Climate.Sampler");

        assertReadinessSource(Files.readString(COMPONENT_SOURCE));
        assertReadinessSource(Files.readString(ENTRY_SOURCE));
        assertReadinessSource(readinessSource);

        List<Path> runtimeBiomeSourceFiles = mainSourceFiles.stream()
            .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
            .toList();
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "ResourceLocation");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "ResourceKey<Biome>");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "RegistryLookup<");
        assertNoMainSourceContains(runtimeBiomeSourceFiles, "RegistryAccess");
        assertNoMainSourceContains(mainSourceFiles, "Registry.register");
        assertNoMainSourceContains(mainSourceFiles, "BuiltInRegistries.BIOME_SOURCE.register");
        assertNoMainSourceContains(mainSourceFiles, "DeferredRegister");
        assertNoMainSourceContains(mainSourceFiles, "RecordCodecBuilder");
        assertNoMainSourceContains(mainSourceFiles, "public static final MapCodec");
        assertNoMainSourceContains(mainSourceFiles, "static final MapCodec");
        assertNoMainSourceContains(mainSourceFiles, "CODEC =");
        assertNoMainSourceContains(mainSourceFiles, "return holder");
        assertNoMainSourceContains(mainSourceFiles, "return fallback");
        assertNoMainSourceContains(mainSourceFiles, "return getNoiseBiome");
        assertNoMainSourceContains(mainSourceFiles, "return collectPossibleBiomes");
        assertNoMainSourceContains(mainSourceFiles, "return Stream.of");
        assertNoMainSourceContains(mainSourceFiles, ".map(Holder");
        assertNoMainSourceContains(mainSourceFiles, ".collect(Collectors");
        assertNoMainSourceContains(mainSourceFiles, "registerConfiguredFeature(");
        assertNoMainSourceContains(mainSourceFiles, "registerPlacedFeature(");
        assertNoMainSourceContains(mainSourceFiles, "registerConfiguredCarver(");
        assertNoMainSourceContains(mainSourceFiles, "addFreshEntity(");
        assertNoMainSourceContains(mainSourceFiles, "changeDimension(");
        assertNoMainSourceContains(mainSourceFiles, ".teleportTo(");

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
                    )
            );
        }
    }

    private static void assertReadinessSource(String source) {
        assertFalse(source.contains("import net.minecraft"));
        assertFalse(source.contains("Climate.Sampler"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("ResourceLocation"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("BuiltInRegistries"));
    }

    private static <T> void assertImmutableList(List<T> list, T element) {
        try {
            list.add(element);
            throw new AssertionError("Expected list to be immutable");
        } catch (UnsupportedOperationException expected) {
        }
    }

    private static void assertOnlyDesignatedFileContains(List<Path> files, String text) throws IOException {
        List<Path> matchingFiles = files.stream()
            .filter(file -> read(file).contains(text))
            .toList();

        assertEquals(List.of(DESIGNATED_SOURCE), matchingFiles);
    }

    private static void assertNoMainSourceContains(List<Path> files, String text) throws IOException {
        assertTrue(files.stream().noneMatch(file -> read(file).contains(text)));
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectPathOrSibling(first, more)));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();
        for (int i = 0; i < 8 && current != null; i++) {
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
        for (int i = 0; i < 8 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || (candidate.getParent() != null && Files.exists(candidate.getParent()))) {
                return candidate;
            }
            current = current.getParent();
        }
        return Path.of(first, more).toAbsolutePath();
    }

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read " + path, exception);
        }
    }
}
