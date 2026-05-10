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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaActivationReadinessMatrix;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaActivationReadinessMatrixEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaActivationSurface;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRegistrationBoundary;

class CaveniaActivationReadinessMatrixTest {
    private static final Path SURFACE_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaActivationSurface.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaActivationReadinessMatrixEntry.java"
    );
    private static final Path MATRIX_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaActivationReadinessMatrix.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void activationReadinessMatrixExposesOnlyBlockedSurfaceFacts() {
        List<CaveniaActivationSurface> expectedSurfaces = List.of(
            CaveniaActivationSurface.GENERATOR_HOST,
            CaveniaActivationSurface.BIOME_SOURCE_STRATEGY,
            CaveniaActivationSurface.DIMENSION_RESOURCE,
            CaveniaActivationSurface.ACCESS_TRAVEL,
            CaveniaActivationSurface.SPAWN_HOST,
            CaveniaActivationSurface.WORLDGEN_RESOURCE
        );
        List<CaveniaActivationReadinessMatrixEntry> entries = CaveniaActivationReadinessMatrix.entries();

        assertEquals(expectedSurfaces, CaveniaActivationReadinessMatrix.surfaces());
        assertEquals(6, entries.size());
        assertEquals(46, CaveniaActivationReadinessMatrix.totalRequirementCount());
        assertEquals(46, CaveniaActivationReadinessMatrix.blockedRequirementCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceContractName() != null && !entry.sourceContractName().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaActivationReadinessMatrixEntry::allRequirementsBlocked));
        assertTrue(entries.stream().allMatch(entry -> !entry.anyRequirementReady()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeImplemented()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activeRuntimeSurfacePresent()));
        assertEquals(9, CaveniaActivationReadinessMatrix.generatorHostRequirementCount());
        assertEquals(7, CaveniaActivationReadinessMatrix.biomeSourceStrategyRequirementCount());
        assertEquals(6, CaveniaActivationReadinessMatrix.dimensionResourceRequirementCount());
        assertEquals(6, CaveniaActivationReadinessMatrix.accessTravelRequirementCount());
        assertEquals(8, CaveniaActivationReadinessMatrix.spawnHostRequirementCount());
        assertEquals(10, CaveniaActivationReadinessMatrix.worldgenResourceRequirementCount());
        assertTrue(CaveniaActivationReadinessMatrix.allSurfacesBlocked());
        assertFalse(CaveniaActivationReadinessMatrix.anySurfaceReady());
        assertFalse(CaveniaActivationReadinessMatrix.anyRuntimeImplemented());
        assertFalse(CaveniaActivationReadinessMatrix.activeRuntimeSurfacePresent());
        assertFalse(CaveniaActivationReadinessMatrix.canActivateCavenia());
        assertFalse(CaveniaActivationReadinessMatrix.generatorCanCreateChunks());
        assertFalse(CaveniaActivationReadinessMatrix.biomeSourceRuntimeReady());
        assertFalse(CaveniaActivationReadinessMatrix.dimensionCanCreateLevel());
        assertFalse(CaveniaActivationReadinessMatrix.accessCanTeleport());
        assertFalse(CaveniaActivationReadinessMatrix.spawnHostCanSpawn());
        assertFalse(CaveniaActivationReadinessMatrix.worldgenCanAffectWorldgen());
        assertFalse(CaveniaActivationReadinessMatrix.dimensionJsonPresent());
        assertFalse(CaveniaActivationReadinessMatrix.dimensionTypeJsonPresent());
        assertFalse(CaveniaActivationReadinessMatrix.configuredCarverResourcesPresent());
        assertFalse(CaveniaActivationReadinessMatrix.configuredFeatureResourcesPresent());
        assertFalse(CaveniaActivationReadinessMatrix.placedFeatureResourcesPresent());
        assertFalse(CaveniaActivationReadinessMatrix.biomeModifierResourcesPresent());
        assertFalse(CaveniaActivationReadinessMatrix.biomeTagResourcesPresent());
        assertTrue(CaveniaActivationReadinessMatrix.cavemanRemainsDeferred());
        assertEquals(
            CaveniaGeneratorRegistrationBoundary.activationRequirements(),
            CaveniaActivationReadinessMatrix.activationRequirements()
        );
        assertTrue(
            CaveniaActivationReadinessMatrix.activationBlockedReason()
                .contains(CaveniaGeneratorRegistrationBoundary.activationBlockedReason())
        );

        assertImmutableList(CaveniaActivationReadinessMatrix.entries(), new Object());
        assertImmutableList(CaveniaActivationReadinessMatrix.surfaces(), CaveniaActivationSurface.GENERATOR_HOST);
        entries.forEach(entry -> {
            assertNotNull(entry.surface());
            assertNotNull(entry.sourceContractName());
        });
    }

    @Test
    void activationReadinessMatrixSourcesStayOutOfRuntimeActivationPaths() throws IOException {
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
                "Expected the final inert readiness matrix to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("BiomeModifier"));
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
