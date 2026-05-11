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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceHolderConversionComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceHolderConversionEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceHolderConversionReadiness;

class CaveniaRuntimeBiomeSourceHolderConversionReadinessTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceHolderConversionComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceHolderConversionEntry.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceHolderConversionReadiness.java"
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
    void holderConversionReadinessPinsTheFutureStringToHolderPathWithoutImplementingIt() throws IOException {
        List<CaveniaRuntimeBiomeSourceHolderConversionComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.STRING_CANDIDATE_KEYS_SOURCE,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.CANDIDATE_KEY_FORMAT_CONTRACT,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.RESOURCE_LOCATION_CONVERSION_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.RESOURCE_KEY_CONVERSION_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.BIOME_REGISTRY_LOOKUP_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.HOLDER_RESOLUTION_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.POSSIBLE_BIOMES_HOLDER_SET_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.NOISE_BIOME_HOLDER_RETURN_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.MISSING_BIOME_FALLBACK_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.NO_RUNTIME_CONVERSION_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.NO_REGISTRY_LOOKUP_ACCESS,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.REAL_HOLDER_CONVERSION_STILL_DEFERRED
        );
        Set<CaveniaRuntimeBiomeSourceHolderConversionComponent> registryLookupRequiredComponents = Set.of(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.BIOME_REGISTRY_LOOKUP_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.HOLDER_RESOLUTION_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.POSSIBLE_BIOMES_HOLDER_SET_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.NOISE_BIOME_HOLDER_RETURN_DECISION,
            CaveniaRuntimeBiomeSourceHolderConversionComponent.MISSING_BIOME_FALLBACK_DECISION
        );
        List<CaveniaRuntimeBiomeSourceHolderConversionEntry> entries =
            CaveniaRuntimeBiomeSourceHolderConversionReadiness.entries();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceHolderConversionReadiness.components());
        assertEquals(12, CaveniaRuntimeBiomeSourceHolderConversionReadiness.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.observedInput() != null && !entry.observedInput().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.futureDecision() != null && !entry.futureDecision().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceHolderConversionEntry::readinessPinned));
        assertTrue(entries.stream().allMatch(entry -> !entry.implementationReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.registryLookupAvailable()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));
        entries.forEach(entry -> assertEquals(
            registryLookupRequiredComponents.contains(entry.component()),
            entry.registryLookupRequired()
        ));

        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.entryFor(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.STRING_CANDIDATE_KEYS_SOURCE
        ).orElseThrow().observedInput().contains("candidateModernBiomeKeys()"));
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.entryFor(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.CANDIDATE_KEY_FORMAT_CONTRACT
        ).orElseThrow().observedInput().contains("minecraft:ocean"));
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.entryFor(
            CaveniaRuntimeBiomeSourceHolderConversionComponent.NOISE_BIOME_HOLDER_RETURN_DECISION
        ).orElseThrow().observedInput().contains("Holder<Biome>"));

        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderConversionReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.stringCandidateKeysSourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.candidateKeyFormatPinned());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.resourceLocationConversionDecisionPinned());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.resourceKeyConversionDecisionPinned());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.biomeRegistryLookupDecisionPinned());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderResolutionDecisionPinned());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.possibleBiomesHolderSetDecisionPinned());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.noiseBiomeHolderReturnDecisionPinned());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.missingBiomeFallbackDecisionPinned());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.runtimeConversionImplementationReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.registryLookupRequiredForRuntimeConversion());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.resourceKeyConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.resourceLocationConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.possibleBiomesRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.noiseBiomeRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.missingBiomeFallbackReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.realHolderConversionStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.canActivateCaveniaNow());
        assertEquals(14, CaveniaRuntimeBiomeSourceHolderConversionReadiness.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.candidateKeysStillStringOnly());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectorInputDerivationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectorInputDerivationGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.designatedSubclassRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.goNoGoGuardrailsEnforced());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.apiShapeInventoryReady());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceHolderConversionReadiness.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceHolderConversionReadiness.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceHolderConversionReadiness.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceHolderConversionReadiness.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceHolderConversionReadiness.cavemanRemainsDeferred());

        assertTrue(
            designatedSource.contains(
                "public static boolean holderConversionReadinessReady() {\n        return CaveniaRuntimeBiomeSourceHolderConversionReadiness.holderConversionReadinessReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean holderConversionRuntimeReady() {\n        return false;\n    }"));

        assertImmutableList(CaveniaRuntimeBiomeSourceHolderConversionReadiness.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceHolderConversionReadiness.components(),
            CaveniaRuntimeBiomeSourceHolderConversionComponent.STRING_CANDIDATE_KEYS_SOURCE
        );
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void holderConversionReadinessSourcesKeepRuntimeApiUsageAndConversionImplementationBlocked() throws IOException {
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
                "Expected the holder-conversion readiness slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("ResourceLocation"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
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

    private static void assertNoMainSourceContains(List<Path> sourceFiles, String fragment) {
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
