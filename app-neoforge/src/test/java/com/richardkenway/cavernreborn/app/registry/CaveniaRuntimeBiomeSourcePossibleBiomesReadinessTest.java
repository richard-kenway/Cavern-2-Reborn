package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourcePossibleBiomesComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourcePossibleBiomesEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourcePossibleBiomesReadiness;

class CaveniaRuntimeBiomeSourcePossibleBiomesReadinessTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourcePossibleBiomesComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourcePossibleBiomesEntry.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.java"
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
    void possibleBiomesReadinessPinsTheFutureCandidateInventoryPathWithoutImplementingIt() throws IOException {
        List<CaveniaRuntimeBiomeSourcePossibleBiomesComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.POSSIBLE_BIOMES_METHOD_SHAPE,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.CANDIDATE_KEYS_SOURCE,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.CANDIDATE_ENTRY_COUNT,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.CANDIDATE_ORDERING_POLICY,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.CANDIDATE_DEDUPLICATION_POLICY,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.HOLDER_CONVERSION_DEPENDENCY,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.FALLBACK_POLICY_SOURCE,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.FALLBACK_INCLUDED_IF_EMPTY_POLICY,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.NO_RUNTIME_HOLDER_STREAM,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.REAL_POSSIBLE_BIOMES_STILL_DEFERRED
        );
        Set<CaveniaRuntimeBiomeSourcePossibleBiomesComponent> registryLookupRequiredComponents = Set.of(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.HOLDER_CONVERSION_DEPENDENCY,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.FALLBACK_INCLUDED_IF_EMPTY_POLICY,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.NO_RUNTIME_HOLDER_STREAM,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.NO_COLLECT_POSSIBLE_BIOMES_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.REAL_POSSIBLE_BIOMES_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourcePossibleBiomesEntry> entries =
            CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.entries();
        List<String> candidateKeys = CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateModernBiomeKeys();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.components());
        assertEquals(12, CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.readinessDecision() != null && !entry.readinessDecision().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.expectedFutureBehavior() != null && !entry.expectedFutureBehavior().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourcePossibleBiomesEntry::readinessPinned));
        assertTrue(entries.stream().allMatch(entry -> !entry.implementationReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.registryLookupAvailable()));
        assertTrue(entries.stream().allMatch(entry -> !entry.possibleBiomesRuntimeReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));
        entries.forEach(entry -> assertEquals(
            registryLookupRequiredComponents.contains(entry.component()),
            entry.registryLookupRequired()
        ));

        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.entryFor(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.CANDIDATE_KEYS_SOURCE
        ).orElseThrow().readinessDecision().contains("candidateModernBiomeKeys()"));
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.entryFor(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.POSSIBLE_BIOMES_METHOD_SHAPE
        ).orElseThrow().readinessDecision().contains("collectPossibleBiomes()"));
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.entryFor(
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.NO_RUNTIME_HOLDER_STREAM
        ).orElseThrow().readinessDecision().contains("Holder<Biome>"));

        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesMethodShapePinned());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateKeysSourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateEntryCountPinned());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateOrderingPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateDeduplicationPolicyPinned());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.holderConversionDependencyPinned());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.fallbackPolicySourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.fallbackIncludedIfEmptyPolicyPinned());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.runtimeHolderStreamReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.collectPossibleBiomesImplementationReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.registryLookupRequiredForRuntimePossibleBiomes());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.realPossibleBiomesStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.canActivateCaveniaNow());
        assertEquals(14, CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateKeysStillStringOnly());
        assertEquals(
            List.of(
                "minecraft:ocean",
                "minecraft:plains",
                "minecraft:desert",
                "minecraft:desert",
                "minecraft:forest",
                "minecraft:forest",
                "minecraft:taiga",
                "minecraft:taiga",
                "minecraft:jungle",
                "minecraft:jungle",
                "minecraft:swamp",
                "minecraft:windswept_hills",
                "minecraft:savanna",
                "minecraft:badlands"
            ),
            candidateKeys
        );
        assertTrue(candidateKeys.contains("minecraft:plains"));
        assertEquals(2, Collections.frequency(candidateKeys, "minecraft:desert"));
        assertEquals("legacy inventory order", CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateOrderingPolicy());
        assertEquals(
            "deduplicate resolved holder-backed outputs by future resolved holder/key",
            CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateDeduplicationPolicy()
        );
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.fallbackPolicyRuntimeReady());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.fallbackLegacyBiomeName());
        assertEquals("minecraft:plains", CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.fallbackCandidateModernBiomeKey());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.fallbackIncludedIfEmpty());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputDerivationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputDerivationGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputDerivationReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputDerivationReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputDerivationImplementationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectorInputDerivationImplementationGoNoGoRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.fallbackRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.holderConversionRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.resourceKeyConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.resourceLocationConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.codecMethodShapeStubReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.codecMethodStubbed());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.goNoGoGuardrailsEnforced());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.apiShapeInventoryReady());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.cavemanRemainsDeferred());

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

        assertImmutableList(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.components(),
            CaveniaRuntimeBiomeSourcePossibleBiomesComponent.POSSIBLE_BIOMES_METHOD_SHAPE
        );
        assertImmutableList(CaveniaRuntimeBiomeSourcePossibleBiomesReadiness.candidateModernBiomeKeys(), "minecraft:plains");
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void possibleBiomesReadinessSourcesKeepRuntimeApiUsageAndPossibleBiomesImplementationBlocked() throws IOException {
        String componentSource = Files.readString(COMPONENT_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String readinessSource = Files.readString(READINESS_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertSourceStaysInert(componentSource);
        assertSourceStaysInert(entrySource);
        assertSourceStaysInert(readinessSource);

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
        assertFalse(designatedSource.contains(".map(Holder"));
        assertFalse(designatedSource.contains(".collect(Collectors"));
        assertFalse(designatedSource.contains("return Stream.of"));
        assertFalse(designatedSource.contains("return possibleBiomes"));

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
            assertNoMainSourceContains(regularFiles, ".map(Holder");
            assertNoMainSourceContains(regularFiles, ".collect(Collectors");
            assertNoMainSourceContains(regularFiles, "return Stream.of");
            assertNoMainSourceContains(regularFiles, "return possibleBiomes");
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
                "Expected the possible-biomes readiness slice to keep active Cavenia resources absent"
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

        assertEquals(List.of(DESIGNATED_SOURCE), matchingFiles, "Expected only the designated subclass file to contain: " + fragment);
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
