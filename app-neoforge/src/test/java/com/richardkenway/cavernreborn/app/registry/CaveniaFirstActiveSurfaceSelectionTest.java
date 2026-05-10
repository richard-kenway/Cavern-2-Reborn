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
import com.richardkenway.cavernreborn.app.worldgen.CaveniaFirstActiveSurfaceCandidate;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaFirstActiveSurfaceSelection;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaFirstActiveSurfaceSelectionReason;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRegistrationBoundary;

class CaveniaFirstActiveSurfaceSelectionTest {
    private static final Path CANDIDATE_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaFirstActiveSurfaceCandidate.java"
    );
    private static final Path REASON_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaFirstActiveSurfaceSelectionReason.java"
    );
    private static final Path SELECTION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaFirstActiveSurfaceSelection.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void firstActiveSurfaceSelectionPinsBiomeSourceStrategyAsTheDecisionOnlyNextSlice() {
        List<CaveniaActivationSurface> expectedSurfaces = List.of(
            CaveniaActivationSurface.GENERATOR_HOST,
            CaveniaActivationSurface.BIOME_SOURCE_STRATEGY,
            CaveniaActivationSurface.DIMENSION_RESOURCE,
            CaveniaActivationSurface.ACCESS_TRAVEL,
            CaveniaActivationSurface.SPAWN_HOST,
            CaveniaActivationSurface.WORLDGEN_RESOURCE
        );
        List<CaveniaFirstActiveSurfaceCandidate> candidates = CaveniaFirstActiveSurfaceSelection.candidates();
        CaveniaFirstActiveSurfaceCandidate selectedCandidate = CaveniaFirstActiveSurfaceSelection.selectedCandidate();

        assertEquals(6, candidates.size());
        assertEquals(expectedSurfaces, candidates.stream().map(CaveniaFirstActiveSurfaceCandidate::surface).toList());
        assertEquals(1L, candidates.stream().filter(CaveniaFirstActiveSurfaceCandidate::selected).count());
        assertEquals(CaveniaActivationSurface.BIOME_SOURCE_STRATEGY, CaveniaFirstActiveSurfaceSelection.selectedSurface());
        assertEquals("BIOME_SOURCE_STRATEGY", CaveniaFirstActiveSurfaceSelection.selectedSurfaceName());
        assertEquals(7, CaveniaFirstActiveSurfaceSelection.selectedRequirementCount());
        assertTrue(CaveniaFirstActiveSurfaceSelection.selectedSurfaceIsBiomeSourceStrategy());
        assertEquals("CaveniaBiomeSourceStrategyContracts", selectedCandidate.sourceContractName());
        assertEquals(1, selectedCandidate.selectionRank());
        assertTrue(
            selectedCandidate.reasons().containsAll(List.of(
                CaveniaFirstActiveSurfaceSelectionReason.PREREQUISITE_FOR_GENERATOR_HOST,
                CaveniaFirstActiveSurfaceSelectionReason.PREREQUISITE_FOR_DIMENSION_BINDING,
                CaveniaFirstActiveSurfaceSelectionReason.LOWER_RUNTIME_SIDE_EFFECT_RISK,
                CaveniaFirstActiveSurfaceSelectionReason.DOES_NOT_REQUIRE_DIMENSION_JSON_YET,
                CaveniaFirstActiveSurfaceSelectionReason.KEEPS_ACCESS_AND_SPAWN_BLOCKED,
                CaveniaFirstActiveSurfaceSelectionReason.REQUIRES_FUTURE_EXPLICIT_RUNTIME_BIOME_SOURCE_MVP
            ))
        );
        assertTrue(CaveniaFirstActiveSurfaceSelection.selectionIsDecisionOnly());
        assertFalse(CaveniaFirstActiveSurfaceSelection.activationAllowedInThisSlice());
        assertTrue(candidates.stream().noneMatch(CaveniaFirstActiveSurfaceCandidate::activationAllowedInThisSlice));
        assertTrue(candidates.stream().allMatch(CaveniaFirstActiveSurfaceCandidate::remainsBlocked));
        assertTrue(candidates.stream().allMatch(candidate -> candidate.blocker() != null && !candidate.blocker().isBlank()));
        assertTrue(candidates.stream().allMatch(candidate -> !candidate.reasons().isEmpty()));
        assertTrue(CaveniaFirstActiveSurfaceSelection.selectedSurfaceRemainsBlocked());
        assertTrue(CaveniaFirstActiveSurfaceSelection.allCandidatesRemainBlocked());
        assertTrue(CaveniaFirstActiveSurfaceSelection.allCandidatesDisallowActivationInThisSlice());
        assertEquals(46, CaveniaFirstActiveSurfaceSelection.readinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaFirstActiveSurfaceSelection.readinessMatrixBlockedRequirementCount());
        assertTrue(CaveniaFirstActiveSurfaceSelection.allReadinessSurfacesBlocked());
        assertFalse(CaveniaFirstActiveSurfaceSelection.canActivateCaveniaNow());
        assertFalse(CaveniaFirstActiveSurfaceSelection.biomeSourceRuntimeReady());
        assertFalse(CaveniaFirstActiveSurfaceSelection.dimensionJsonPresent());
        assertFalse(CaveniaFirstActiveSurfaceSelection.dimensionTypeJsonPresent());
        assertFalse(CaveniaFirstActiveSurfaceSelection.accessCanTeleport());
        assertFalse(CaveniaFirstActiveSurfaceSelection.spawnHostCanSpawn());
        assertFalse(CaveniaFirstActiveSurfaceSelection.worldgenCanAffectWorldgen());
        assertTrue(CaveniaFirstActiveSurfaceSelection.cavemanRemainsDeferred());
        assertEquals(
            CaveniaGeneratorRegistrationBoundary.activationRequirements(),
            CaveniaFirstActiveSurfaceSelection.activationRequirements()
        );
        assertTrue(
            CaveniaFirstActiveSurfaceSelection.activationBlockedReason()
                .contains(CaveniaGeneratorRegistrationBoundary.activationBlockedReason())
        );

        assertImmutableList(CaveniaFirstActiveSurfaceSelection.candidates(), selectedCandidate);
        assertImmutableList(CaveniaFirstActiveSurfaceSelection.selectionReasons(), CaveniaFirstActiveSurfaceSelectionReason.LOWER_RUNTIME_SIDE_EFFECT_RISK);
        candidates.forEach(candidate -> {
            assertNotNull(candidate.surface());
            assertNotNull(candidate.sourceContractName());
        });
    }

    @Test
    void selectionSourcesStayOutOfRuntimeActivationPaths() throws IOException {
        String candidateSource = Files.readString(CANDIDATE_SOURCE);
        String reasonSource = Files.readString(REASON_SOURCE);
        String selectionSource = Files.readString(SELECTION_SOURCE);

        assertSourceStaysInert(candidateSource);
        assertSourceStaysInert(reasonSource);
        assertSourceStaysInert(selectionSource);

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
                "Expected the decision-only first-active-surface selection to avoid adding active Cavenia runtime classes or resources"
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
