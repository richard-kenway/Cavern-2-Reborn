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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceFallbackPolicyComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceFallbackPolicyEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceFallbackPolicyReadiness;

class CaveniaRuntimeBiomeSourceFallbackPolicyReadinessTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceFallbackPolicyComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceFallbackPolicyEntry.java"
    );
    private static final Path READINESS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.java"
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
    void fallbackPolicyReadinessPinsTheDeterministicFallbackChoiceWithoutImplementingIt() throws IOException {
        List<CaveniaRuntimeBiomeSourceFallbackPolicyComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_POLICY_SOURCE,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_UNPARSEABLE_CANDIDATE_ID,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_UNRESOLVED_RESOURCE_KEY,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_MISSING_HOLDER,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_EMPTY_POSSIBLE_BIOMES,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_LEGACY_BIOME_NAME,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_CANDIDATE_MODERN_KEY,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_REGISTRY_VERIFICATION_DEFERRED,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_HOLDER_RESOLUTION_DEFERRED,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_RUNTIME_USAGE_DEFERRED,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.NO_RUNTIME_FALLBACK_IMPLEMENTATION,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.REAL_FALLBACK_POLICY_STILL_DEFERRED
        );
        Set<CaveniaRuntimeBiomeSourceFallbackPolicyComponent> registryLookupRequiredComponents = Set.of(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_UNRESOLVED_RESOURCE_KEY,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_MISSING_HOLDER,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_TRIGGER_EMPTY_POSSIBLE_BIOMES,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_REGISTRY_VERIFICATION_DEFERRED,
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_HOLDER_RESOLUTION_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceFallbackPolicyEntry> entries =
            CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.entries();
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.components());
        assertEquals(12, CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.fallbackDecision() != null && !entry.fallbackDecision().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.expectedFutureBehavior() != null && !entry.expectedFutureBehavior().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceFallbackPolicyEntry::readinessPinned));
        assertTrue(entries.stream().allMatch(entry -> !entry.implementationReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.registryLookupAvailable()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeFallbackReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));
        entries.forEach(entry -> assertEquals(
            registryLookupRequiredComponents.contains(entry.component()),
            entry.registryLookupRequired()
        ));

        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.entryFor(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_LEGACY_BIOME_NAME
        ).orElseThrow().fallbackDecision().contains("PLAINS"));
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.entryFor(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_CANDIDATE_MODERN_KEY
        ).orElseThrow().fallbackDecision().contains("minecraft:plains"));
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.entryFor(
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_HOLDER_RESOLUTION_DEFERRED
        ).orElseThrow().fallbackDecision().contains("Holder<Biome>"));

        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicySourcePinned());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackTriggerUnparseableCandidateIdPinned());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackTriggerUnresolvedResourceKeyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackTriggerMissingHolderPinned());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackTriggerEmptyPossibleBiomesPinned());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeNamePinned());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernKeyPinned());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackRegistryVerificationDeferred());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackHolderResolutionDeferred());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackRuntimeUsageDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.runtimeFallbackImplementationReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.registryLookupRequiredForRuntimeFallback());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackRegistryVerified());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackHolderResolved());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.possibleBiomesFallbackReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.noiseBiomeFallbackReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.realFallbackPolicyStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.canActivateCaveniaNow());
        assertEquals("PLAINS", CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackLegacyBiomeName());
        assertEquals("minecraft:plains", CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateModernBiomeKey());
        assertEquals(
            "CaveniaLegacyToModernBiomeKeyMappings",
            CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackSourceContractName()
        );
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateExistsInInventory());
        assertEquals(14, CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackCandidateEntryCount());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.candidateInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.candidateKeysStillStringOnly());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.possibleBiomesReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.possibleBiomesRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.holderConversionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.noiseBiomeSelectionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.noiseBiomeSelectionRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.readinessChainConsolidationReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.readinessChainRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.codecMethodShapeStubReady());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.designatedSubclassReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.designatedSubclassRuntimeReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.usableCodecImplementationReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.biomeSourceTypeRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.normalRuntimeConstructionAllowed());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.unsupportedMethodStubsOnly());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.goNoGoGuardrailsEnforced());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.apiShapeInventoryReady());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.dimensionBindingReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.cavemanRemainsDeferred());

        assertTrue(
            designatedSource.contains(
                "public static boolean fallbackPolicyReadinessReady() {\n        return CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.fallbackPolicyReadinessReady();\n    }"
            )
        );
        assertTrue(designatedSource.contains("public static boolean fallbackPolicyRuntimeReady() {\n        return false;\n    }"));

        assertImmutableList(CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceFallbackPolicyReadiness.components(),
            CaveniaRuntimeBiomeSourceFallbackPolicyComponent.FALLBACK_POLICY_SOURCE
        );
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void fallbackPolicyReadinessSourcesKeepRuntimeFallbackAndLookupImplementationBlocked() throws IOException {
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
                "Expected the fallback-policy readiness slice to keep active Cavenia resources absent"
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
