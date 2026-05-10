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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaActivationSurface;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyReadinessMatrix;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyReadinessMatrixEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyReadinessSurface;

class CaveniaBiomeSourceStrategyReadinessMatrixTest {
    private static final Path SURFACE_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeSourceStrategyReadinessSurface.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeSourceStrategyReadinessMatrixEntry.java"
    );
    private static final Path MATRIX_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeSourceStrategyReadinessMatrix.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void readinessMatrixPinsTheFinalSelectedSurfaceStatusAcrossAllReadinessLayers() {
        List<CaveniaBiomeSourceStrategyReadinessSurface> expectedSurfaces = List.of(
            CaveniaBiomeSourceStrategyReadinessSurface.SELECTED_SURFACE_PLAN,
            CaveniaBiomeSourceStrategyReadinessSurface.LEGACY_MAPPING_INVENTORY,
            CaveniaBiomeSourceStrategyReadinessSurface.WEIGHTED_SELECTION_ALGORITHM,
            CaveniaBiomeSourceStrategyReadinessSurface.BIOME_SELECTION_ADAPTER,
            CaveniaBiomeSourceStrategyReadinessSurface.ADAPTER_CODEC_REGISTRATION_READINESS,
            CaveniaBiomeSourceStrategyReadinessSurface.REGISTRY_LOOKUP_READINESS
        );
        List<CaveniaBiomeSourceStrategyReadinessMatrixEntry> entries = CaveniaBiomeSourceStrategyReadinessMatrix.entries();

        assertEquals(expectedSurfaces, CaveniaBiomeSourceStrategyReadinessMatrix.surfaces());
        assertEquals(6, entries.size());
        assertEquals(6, CaveniaBiomeSourceStrategyReadinessMatrix.entryCount());
        assertEquals(68, CaveniaBiomeSourceStrategyReadinessMatrix.totalReadinessItemCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaBiomeSourceStrategyReadinessMatrixEntry::readinessLayerReady));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeBiomeSourceReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.codecRegistered()));
        assertTrue(entries.stream().allMatch(entry -> !entry.registryLookupAccessReady()));

        assertEquals(
            8,
            CaveniaBiomeSourceStrategyReadinessMatrix.entryFor(
                CaveniaBiomeSourceStrategyReadinessSurface.SELECTED_SURFACE_PLAN
            ).orElseThrow().itemCount()
        );
        assertEquals(
            14,
            CaveniaBiomeSourceStrategyReadinessMatrix.entryFor(
                CaveniaBiomeSourceStrategyReadinessSurface.LEGACY_MAPPING_INVENTORY
            ).orElseThrow().itemCount()
        );
        assertEquals(
            14,
            CaveniaBiomeSourceStrategyReadinessMatrix.entryFor(
                CaveniaBiomeSourceStrategyReadinessSurface.WEIGHTED_SELECTION_ALGORITHM
            ).orElseThrow().itemCount()
        );
        assertEquals(
            14,
            CaveniaBiomeSourceStrategyReadinessMatrix.entryFor(
                CaveniaBiomeSourceStrategyReadinessSurface.BIOME_SELECTION_ADAPTER
            ).orElseThrow().itemCount()
        );
        assertEquals(
            9,
            CaveniaBiomeSourceStrategyReadinessMatrix.entryFor(
                CaveniaBiomeSourceStrategyReadinessSurface.ADAPTER_CODEC_REGISTRATION_READINESS
            ).orElseThrow().itemCount()
        );
        assertEquals(
            9,
            CaveniaBiomeSourceStrategyReadinessMatrix.entryFor(
                CaveniaBiomeSourceStrategyReadinessSurface.REGISTRY_LOOKUP_READINESS
            ).orElseThrow().itemCount()
        );
        assertEquals(CaveniaActivationSurface.BIOME_SOURCE_STRATEGY, CaveniaBiomeSourceStrategyReadinessMatrix.selectedSurface());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.selectedSurfaceIsBiomeSourceStrategy());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.allReadinessLayersReady());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.allRuntimeSurfacesBlocked());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.anyRuntimeReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.activationAllowedInThisSlice());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.canActivateCaveniaNow());
        assertEquals(8, CaveniaBiomeSourceStrategyReadinessMatrix.planStepCount());
        assertEquals(14, CaveniaBiomeSourceStrategyReadinessMatrix.legacyMappingEntryCount());
        assertEquals(14, CaveniaBiomeSourceStrategyReadinessMatrix.weightedSelectionEntryCount());
        assertEquals(14, CaveniaBiomeSourceStrategyReadinessMatrix.adapterEntryCount());
        assertEquals(9, CaveniaBiomeSourceStrategyReadinessMatrix.adapterCodecRegistrationRequirementCount());
        assertEquals(9, CaveniaBiomeSourceStrategyReadinessMatrix.registryLookupRequirementCount());
        assertEquals(675, CaveniaBiomeSourceStrategyReadinessMatrix.legacyBiomeTotalWeight());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.candidateInventoryReady());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.candidateKeysStillStringOnly());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.weightedSelectionAlgorithmReady());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.adapterShapeReady());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.codecRegistrationReadinessReady());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.registryLookupReadinessReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.finalRuntimeMappingReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.weightedSelectionRuntimeReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.adapterRuntimeReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.codecRegistrationRuntimeReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.registryLookupRuntimeReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.runtimeBiomeSourceReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.runtimeBiomeSourceRegistered());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.codecRegistered());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.registryLookupAccessReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.registryVerified());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.runtimeBiomeResolved());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.modernBiomeMappingReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.dimensionBindingReady());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.dimensionJsonPresent());
        assertFalse(CaveniaBiomeSourceStrategyReadinessMatrix.dimensionTypeJsonPresent());
        assertEquals(46, CaveniaBiomeSourceStrategyReadinessMatrix.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaBiomeSourceStrategyReadinessMatrix.globalReadinessMatrixBlockedRequirementCount());
        assertTrue(CaveniaBiomeSourceStrategyReadinessMatrix.cavemanRemainsDeferred());

        assertImmutableList(CaveniaBiomeSourceStrategyReadinessMatrix.entries(), new Object());
        assertImmutableList(
            CaveniaBiomeSourceStrategyReadinessMatrix.surfaces(),
            CaveniaBiomeSourceStrategyReadinessSurface.SELECTED_SURFACE_PLAN
        );
        entries.forEach(entry -> assertNotNull(entry.surface()));
    }

    @Test
    void readinessMatrixSourcesStayOutOfRuntimeBiomeSourceAndRegistryPaths() throws IOException {
        String surfaceSource = Files.readString(SURFACE_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String matrixSource = Files.readString(MATRIX_SOURCE);

        assertSourceStaysInert(surfaceSource);
        assertSourceStaysInert(entrySource);
        assertSourceStaysInert(matrixSource);

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.equals("CaveniaChunkGenerator.java")
                            || name.equals("ChunkGeneratorCavenia.java")
                            || name.equals("CaveniaBiomeSource.java")
                            || name.equals("MapGenCaveniaCaves.java")
                            || name.equals("CaveniaTeleporter.java")
                            || name.equals("CaveniaSpawnProvider.java")
                            || name.equals("CaveniaSpawnHandler.java")
                            || name.equals("CaveniaServerTickSpawner.java")
                    ),
                "Expected the final BIOME_SOURCE_STRATEGY readiness matrix slice to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("registerConfiguredFeature("));
        assertFalse(source.contains("registerPlacedFeature("));
        assertFalse(source.contains("registerConfiguredCarver("));
        assertFalse(source.contains("addFreshEntity("));
        assertFalse(source.contains("changeDimension("));
        assertFalse(source.contains(".teleportTo("));
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
