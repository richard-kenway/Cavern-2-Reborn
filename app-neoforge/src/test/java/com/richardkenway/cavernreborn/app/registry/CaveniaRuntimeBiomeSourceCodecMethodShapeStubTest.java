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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCodecMethodShapeComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCodecMethodShapeEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceCodecMethodShapeStub;

class CaveniaRuntimeBiomeSourceCodecMethodShapeStubTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCodecMethodShapeEntry.java"
    );
    private static final Path STUB_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceCodecMethodShapeStub.java"
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
    void codecMethodShapeStubPinsTheGuardedCodecMethodContractWithoutEnablingCodecBehavior() {
        List<CaveniaRuntimeBiomeSourceCodecMethodShapeComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.LOCAL_CODEC_METHOD_SIGNATURE,
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.DESIGNATED_SUBCLASS_OVERRIDE,
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.UNSUPPORTED_CODEC_METHOD_BODY,
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.NO_USABLE_MAP_CODEC,
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.NO_STATIC_CODEC_FIELD,
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.NO_RECORD_CODEC_BUILDER,
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.NO_CODEC_REGISTRATION,
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.NO_BIOME_SOURCE_TYPE_REGISTRATION,
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.NO_REGISTRY_LOOKUP_DEPENDENCY,
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.REAL_CODEC_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceCodecMethodShapeEntry> entries =
            CaveniaRuntimeBiomeSourceCodecMethodShapeStub.entries();

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceCodecMethodShapeStub.components());
        assertEquals(10, CaveniaRuntimeBiomeSourceCodecMethodShapeStub.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.observedShape() != null && !entry.observedShape().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.expectedBehavior() != null && !entry.expectedBehavior().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceCodecMethodShapeEntry::shapePinned));
        assertTrue(entries.stream().allMatch(entry -> !entry.usableCodecImplementation()));
        assertTrue(entries.stream().allMatch(entry -> !entry.registered()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.entryFor(
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.LOCAL_CODEC_METHOD_SIGNATURE
        ).orElseThrow().observedShape().contains("MapCodec<? extends BiomeSource> codec()"));
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.entryFor(
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.UNSUPPORTED_CODEC_METHOD_BODY
        ).orElseThrow().observedShape().contains("UnsupportedOperationException"));
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.entryFor(
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.NO_RECORD_CODEC_BUILDER
        ).orElseThrow().observedShape().contains("RecordCodecBuilder"));

        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodShapeStubReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.localCodecMethodSignaturePinned());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.designatedSubclassOverridesCodecMethod());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodUnsupportedStubbed());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.usableMapCodecReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.staticCodecFieldPresent());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.recordCodecBuilderUsed());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.registryLookupDependencyReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.realCodecStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.canActivateCaveniaNow());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.designatedSubclassRuntimeReady());
        assertEquals("CaveniaRuntimeBiomeSource.java", CaveniaRuntimeBiomeSourceCodecMethodShapeStub.designatedSubclassFileName());
        assertEquals("CaveniaRuntimeBiomeSource", CaveniaRuntimeBiomeSourceCodecMethodShapeStub.designatedSubclassSimpleName());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.goNoGoGuardrailsEnforced());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.apiShapeInventoryReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.holderResourceKeyConversionReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.holderConversionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.fallbackPolicyReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.fallbackPolicyRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputDerivationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputDerivationGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputDerivationReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputDerivationReadinessRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputDerivationImplementationGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectorInputDerivationImplementationGoNoGoRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceCodecMethodShapeStub.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCodecMethodShapeStub.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceCodecMethodShapeStub.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.cavemanRemainsDeferred());
        assertTrue(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.codecMethodUnsupportedReason().contains("unsupported stub"));

        assertImmutableList(CaveniaRuntimeBiomeSourceCodecMethodShapeStub.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceCodecMethodShapeStub.components(),
            CaveniaRuntimeBiomeSourceCodecMethodShapeComponent.LOCAL_CODEC_METHOD_SIGNATURE
        );
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void codecMethodShapeStubSourcesKeepRuntimeApiUsageConstrainedToTheDesignatedSubclass() throws IOException {
        String componentSource = Files.readString(COMPONENT_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String stubSource = Files.readString(STUB_SOURCE);
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertSourceStaysInert(componentSource);
        assertSourceStaysInert(entrySource);
        assertSourceStaysInert(stubSource);

        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertFalse(designatedSource.contains("public static final MapCodec"));
        assertFalse(designatedSource.contains("static final MapCodec"));
        assertFalse(designatedSource.contains("CODEC ="));
        assertFalse(designatedSource.contains("RecordCodecBuilder"));
        assertFalse(designatedSource.contains("Registry.register"));
        assertFalse(designatedSource.contains("BuiltInRegistries.BIOME_SOURCE.register"));
        assertFalse(designatedSource.contains("DeferredRegister"));
        assertFalse(designatedSource.contains("RegistryLookup<"));
        assertFalse(designatedSource.contains("RegistryAccess"));
        assertFalse(designatedSource.contains("ResourceKey<Biome>"));

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
            assertNoMainSourceContains(regularFiles, "public static final MapCodec");
            assertNoMainSourceContains(regularFiles, "static final MapCodec");
            assertNoMainSourceContains(regularFiles, "CODEC =");
            assertNoMainSourceContains(regularFiles, "RecordCodecBuilder");
            assertNoMainSourceContains(regularFiles, "Registry.register");
            assertNoMainSourceContains(regularFiles, "BuiltInRegistries.BIOME_SOURCE.register");
            assertNoMainSourceContains(regularFiles, "DeferredRegister");
            assertNoMainSourceContains(regularFiles, "RegistryLookup<");
            assertNoMainSourceContains(regularFiles, "RegistryAccess");
            assertNoMainSourceContains(regularFiles, "ResourceKey<Biome>");
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
                "Expected the codec method shape stub slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("ResourceLocation"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("import com.mojang.serialization.MapCodec;"));
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

        if (fragment.equals("Holder<Biome>")) {
            assertEquals(
                List.of("CaveniaRuntimeBiomeSource.java", "CaveniaRuntimeBiomeSourceCollectPossibleBiomesHolderSetBuilder.java", "CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"),
                matchingFiles.stream().map(path -> path.getFileName().toString()).toList(),
                "Expected only the designated subclass, builder and converter files to contain: " + fragment
            );
            return;
        }

        assertEquals(List.of(DESIGNATED_SOURCE), matchingFiles, "Expected only the designated subclass file to contain: " + fragment);
    }

    private static void assertNoMainSourceContains(List<Path> sourceFiles, String fragment) {
        boolean allowConverterFile = fragment.equals("ResourceLocation")
            || fragment.equals("ResourceKey<Biome>")
            || fragment.equals("HolderLookup")
            || fragment.equals("Registries.BIOME")
            || fragment.equals("return holder");
        assertTrue(
            sourceFiles.stream().noneMatch(path -> {
                try {
                    return (!allowConverterFile
                        || !path.getFileName().toString().equals("CaveniaRuntimeBiomeSourceCandidateKeyToHolderConverter.java"))
                        && Files.readString(path).contains(fragment);
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
