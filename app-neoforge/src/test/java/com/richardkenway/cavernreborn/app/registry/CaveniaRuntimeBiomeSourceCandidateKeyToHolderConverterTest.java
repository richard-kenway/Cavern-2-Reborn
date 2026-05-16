package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry;

class CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterTest {
    private static final String DESIGNATED_CONVERTER_FILE_NAME =
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java";
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry.java"
    );
    private static final Path CONVERTER_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        DESIGNATED_CONVERTER_FILE_NAME
    );
    private static final Path DESIGNATED_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSource.java"
    );
    private static final Path APP_WORLDGEN_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen"
    );
    private static final Path GUARDED_IMPLEMENTATION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-candidate-key-to-holder-conversion-guarded-implementation-mvp.md"
    );

    @Test
    void guardedConverterPinsConverterOnlyCandidateKeyToHolderBehavior() throws IOException {
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.DESIGNATED_CONVERTER_FILE,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.STRING_CANDIDATE_KEY_INPUT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.RESOURCE_LOCATION_PARSE_STEP,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.BIOME_RESOURCE_KEY_STEP,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.BIOME_REGISTRY_LOOKUP_STEP,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.HOLDER_RESOLUTION_STEP,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.OPTIONAL_HOLDER_RESULT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.FALLBACK_CANDIDATE_ATTEMPT,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.INVALID_OR_MISSING_KEY_HANDLING,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.NO_GET_NOISE_BIOME_WIRING,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.NO_COLLECT_POSSIBLE_BIOMES_WIRING,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.REAL_RUNTIME_BIOME_SOURCE_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry> entries =
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.entries();
        List<Path> runtimeBiomeSourceFiles;

        try (Stream<Path> sourceFiles = Files.walk(APP_WORLDGEN_SOURCE_ROOT)) {
            runtimeBiomeSourceFiles = sourceFiles
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().contains("CaveniaRuntimeBiomeSource"))
                .toList();
        }

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.components());
        assertEquals(12, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.entryCount());
        assertTrue(entries.stream().allMatch(entry -> !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.converterBehavior().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeBoundary().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterEntry::converterReady));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeBiomeSourceReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));

        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.converterReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.holderForCandidateKeyReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.holderForCandidateKeyOrFallbackReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.converterRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.getNoiseBiomeWiringReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.collectPossibleBiomesWiringReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.runtimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.canActivateCaveniaNow());
        assertEquals(
            "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.designatedConverterSimpleName()
        );
        assertEquals(
            DESIGNATED_CONVERTER_FILE_NAME,
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.designatedConverterFileName()
        );
        assertEquals(
            "string modern biome candidate key",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.candidateKeyInputShape()
        );
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.fallbackLegacyBiomeName());
        assertEquals(
            "minecraft:plains",
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.fallbackCandidateModernBiomeKey()
        );
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.candidateKeyToHolderConversionReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.implementationGoNoGoDecisionReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.collectPossibleBiomesHolderSetReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.collectPossibleBiomesHolderSetReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.selectorToWeightedCandidateBridgeReady());
        assertEquals("minecraft:taiga", CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.candidateKeyForSampleOrigin());
        assertEquals("minecraft:desert", CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.candidateKeyForSampleMixed());
        assertEquals("minecraft:desert", CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.candidateKeyForSampleNegative());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.sampleKeysExistInInventory());
        assertEquals(14, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.candidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.candidateKeysStillStringOnlyOutsideConverter());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.holderConversionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.allCurrentReadinessLayersReady());
        assertEquals(0, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.runtimeReadyLayerCount());
        assertEquals(6, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.consolidatedReadinessLayerCount());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.codecMethodShapeRuntimeReady());
        String converterSource = Files.readString(CONVERTER_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        assertTrue(
            converterSource.contains(
                "public static boolean designatedSubclassReady() {\n        return CaveniaRuntimeBiomeSource.guardedSubclassStubReady();\n    }"
            )
        );
        assertTrue(
            converterSource.contains(
                "public static boolean designatedSubclassRuntimeReady() {\n        return false;\n    }"
            )
        );
        assertTrue(
            converterSource.contains(
                "public static boolean runtimeHolderReturnReady() {\n        return false;\n    }"
            )
        );
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.collectPossibleBiomesImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.getNoiseBiomeImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.usableRuntimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.collectPossibleBiomesStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.getNoiseBiomeStubbed());
        assertTrue(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.codecMethodStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.globalReadinessMatrixBlockedRequirementCount());
        assertTrue(
            converterSource.contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            converterSource.contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSource.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            converterSource.contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSource.cavemanRemainsDeferred();\n    }"
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

        assertEquals(Optional.empty(), CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.holderForCandidateKey(null, null));
        assertEquals(Optional.empty(), CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.holderForCandidateKey("   ", null));
        assertEquals(
            Optional.empty(),
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.holderForCandidateKey("not a valid key", null)
        );
        assertEquals(
            Optional.empty(),
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter
                .holderForCandidateKey("cavernreborn:missing_biome", null)
        );
        assertEquals(
            Optional.empty(),
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter
                .holderForCandidateKeyOrFallback("not a valid key", null)
        );

        assertImmutableList(CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.entries(), entries.getFirst());
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.components(),
            CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverterComponent.DESIGNATED_CONVERTER_FILE
        );

        assertTrue(Files.exists(COMPONENT_SOURCE));
        assertTrue(Files.exists(ENTRY_SOURCE));
        assertTrue(Files.exists(CONVERTER_SOURCE));
        assertTrue(Files.exists(GUARDED_IMPLEMENTATION_DOC));
        assertSourceSafety(runtimeBiomeSourceFiles);
    }

    @Test
    void guardedImplementationDocDescribesConverterOnlyBehavior() throws IOException {
        String doc = Files.readString(GUARDED_IMPLEMENTATION_DOC);

        assertTrue(doc.contains("guarded candidate-key-to-holder converter implementation, not runtime biome-source behavior"));
        assertTrue(doc.contains("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"));
        assertTrue(doc.contains("Resource-location parsing is allowed only in the designated converter."));
        assertTrue(doc.contains("Resource-key construction is allowed only in the designated converter."));
        assertTrue(doc.contains("Biome registry lookup is allowed only in the designated converter."));
        assertTrue(doc.contains("Holder resolution is allowed only in the designated converter."));
        assertTrue(doc.contains("Fallback holder attempt is allowed only in the designated converter."));
        assertTrue(doc.contains("The converter requires caller-provided lookup/provider."));
        assertTrue(doc.contains("The converter does not access server/world globals."));
        assertTrue(doc.contains("converter is not wired into `getNoiseBiome(...)`"));
        assertTrue(doc.contains("converter is not wired into `collectPossibleBiomes()`"));
        assertTrue(doc.contains("converter consolidation next-decision note is documented in `docs/cavenia-runtime-biome-source-candidate-key-to-holder-converter-consolidation-next-decision-mvp.md`"));
        assertTrue(doc.contains("`getNoiseBiome(...)` remains unsupported"));
        assertTrue(doc.contains("`collectPossibleBiomes()` remains unsupported"));
        assertTrue(doc.contains("`codec()` remains unsupported"));
        assertTrue(doc.contains("`cavernreborn:caveman` remains absent"));
        assertFalse(doc.contains("documented in ."));
    }

    private static void assertSourceSafety(List<Path> runtimeBiomeSourceFiles) throws IOException {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        String converterSource = Files.readString(CONVERTER_SOURCE);

        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertFalse(designatedSource.contains("holderForCandidateKey("));
        assertFalse(designatedSource.contains("holderForCandidateKeyOrFallback("));
        assertFalse(designatedSource.contains("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter."));

        assertTrue(converterSource.contains("ResourceLocation"));
        assertTrue(converterSource.contains("ResourceKey<Biome>"));
        assertTrue(converterSource.contains("HolderLookup"));
        assertTrue(converterSource.contains("Holder<Biome>"));
        assertFalse(converterSource.contains("Registry.register"));
        assertFalse(converterSource.contains("BuiltInRegistries.BIOME_SOURCE.register"));
        assertFalse(converterSource.contains("DeferredRegister"));
        assertFalse(converterSource.contains("RecordCodecBuilder"));
        assertFalse(converterSource.contains("public static final MapCodec"));
        assertFalse(converterSource.contains("static final MapCodec"));
        assertFalse(converterSource.contains("CODEC ="));
        assertFalse(converterSource.contains("return getNoiseBiome"));
        assertFalse(converterSource.contains("return collectPossibleBiomes"));
        assertFalse(converterSource.contains("registerConfiguredFeature("));
        assertFalse(converterSource.contains("registerPlacedFeature("));
        assertFalse(converterSource.contains("registerConfiguredCarver("));
        assertFalse(converterSource.contains("addFreshEntity("));
        assertFalse(converterSource.contains("changeDimension("));
        assertFalse(converterSource.contains(".teleportTo("));

        assertEquals(
            1L,
            runtimeBiomeSourceFiles.stream()
                .filter(path -> path.getFileName().toString().equals(DESIGNATED_CONVERTER_FILE_NAME))
                .count()
        );
        assertOnlyFileContains(runtimeBiomeSourceFiles, "ResourceLocation", CONVERTER_SOURCE);
        assertOnlyFileContains(runtimeBiomeSourceFiles, "ResourceKey<Biome>", CONVERTER_SOURCE);
        assertOnlyFileContains(runtimeBiomeSourceFiles, "HolderLookup", CONVERTER_SOURCE);
        assertOnlyFilesContain(
            runtimeBiomeSourceFiles,
            "Holder<Biome>",
            List.of(
                DESIGNATED_SOURCE,
                resolveProjectFile(
                    "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app",
                    "worldgen", "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java"
                ),
                CONVERTER_SOURCE
            )
        );
        assertNoSourceContains(runtimeBiomeSourceFiles, "Registry.register");
        assertNoSourceContains(runtimeBiomeSourceFiles, "BuiltInRegistries.BIOME_SOURCE.register");
        assertNoSourceContains(runtimeBiomeSourceFiles, "DeferredRegister");
        assertNoSourceContains(runtimeBiomeSourceFiles, "RecordCodecBuilder");
        assertNoSourceContains(runtimeBiomeSourceFiles, "public static final MapCodec");
        assertNoSourceContains(runtimeBiomeSourceFiles, "static final MapCodec");
        assertNoSourceContains(runtimeBiomeSourceFiles, "CODEC =");
        assertNoSourceContains(runtimeBiomeSourceFiles, "return getNoiseBiome");
        assertNoSourceContains(runtimeBiomeSourceFiles, "return collectPossibleBiomes");
    }

    private static void assertOnlyFileContains(List<Path> files, String text, Path expected) throws IOException {
        List<Path> matchingFiles = files.stream()
            .filter(file -> read(file).contains(text))
            .toList();

        if (text.equals("HolderLookup")) {
            assertEquals(
                List.of(
                    "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java",
                    expected.getFileName().toString()
                ),
                matchingFiles.stream().map(path -> path.getFileName().toString()).toList()
            );
            return;
        }

        assertEquals(
            List.of(expected.getFileName().toString()),
            matchingFiles.stream().map(path -> path.getFileName().toString()).toList()
        );
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

    private static void assertNoSourceContains(List<Path> files, String text) throws IOException {
        assertTrue(files.stream().noneMatch(file -> read(file).contains(text)));
    }

    private static <T> void assertImmutableList(List<T> list, T element) {
        try {
            list.add(element);
            throw new AssertionError("Expected list to be immutable");
        } catch (UnsupportedOperationException expected) {
        }
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

    private static String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read " + path, exception);
        }
    }
}
