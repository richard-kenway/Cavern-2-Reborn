package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSkeleton;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSkeletonQuery;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSkeletonResult;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWeightedBiomeSelectionAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWeightedBiomeSelectionEntry;

class CaveniaRuntimeBiomeSourceSkeletonTest {
    private static final Path QUERY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSkeletonQuery.java"
    );
    private static final Path RESULT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSkeletonResult.java"
    );
    private static final Path SKELETON_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceSkeleton.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void unregisteredRuntimeBiomeSourceSkeletonStaysInertAndDelegatesToTheAdapterPath() {
        CaveniaWeightedBiomeSelectionEntry firstEntry = CaveniaWeightedBiomeSelectionAlgorithm.entries().get(0);
        CaveniaWeightedBiomeSelectionEntry lastEntry =
            CaveniaWeightedBiomeSelectionAlgorithm.entries().get(CaveniaWeightedBiomeSelectionAlgorithm.entries().size() - 1);

        CaveniaRuntimeBiomeSourceSkeletonQuery weightOnlyQuery =
            CaveniaRuntimeBiomeSourceSkeleton.queryForWeightValue(675);
        CaveniaRuntimeBiomeSourceSkeletonQuery coordinateQuery =
            CaveniaRuntimeBiomeSourceSkeleton.queryForCoordinatesAndWeightValue(12, 34, 56, 0);

        assertEquals(675, weightOnlyQuery.weightValue());
        assertEquals(0, weightOnlyQuery.x());
        assertEquals(0, weightOnlyQuery.y());
        assertEquals(0, weightOnlyQuery.z());
        assertFalse(weightOnlyQuery.climateSamplerAvailable());
        assertFalse(weightOnlyQuery.registryLookupAvailable());
        assertFalse(weightOnlyQuery.runtimeBiomeSourceAvailable());

        assertEquals(12, coordinateQuery.x());
        assertEquals(34, coordinateQuery.y());
        assertEquals(56, coordinateQuery.z());
        assertEquals(0, coordinateQuery.weightValue());
        assertFalse(coordinateQuery.climateSamplerAvailable());
        assertFalse(coordinateQuery.registryLookupAvailable());
        assertFalse(coordinateQuery.runtimeBiomeSourceAvailable());

        assertResult(CaveniaRuntimeBiomeSourceSkeleton.selectCandidateByWeightValue(0), firstEntry, 0, 0, 0, 0, 0);
        assertResult(CaveniaRuntimeBiomeSourceSkeleton.selectCandidate(weightOnlyQuery), firstEntry, 0, 0, 0, 675, 0);
        assertResult(CaveniaRuntimeBiomeSourceSkeleton.selectCandidateByWeightValue(-1), lastEntry, 0, 0, 0, -1, 674);
        assertResult(CaveniaRuntimeBiomeSourceSkeleton.selectCandidate(coordinateQuery), firstEntry, 12, 34, 56, 0, 0);

        CaveniaRuntimeBiomeSourceSkeletonResult coordinateResult =
            CaveniaRuntimeBiomeSourceSkeleton.selectCandidate(coordinateQuery);
        CaveniaRuntimeBiomeSourceSkeletonResult weightOnlyResult =
            CaveniaRuntimeBiomeSourceSkeleton.selectCandidateByWeightValue(0);

        assertEquals(coordinateResult.legacyBiomeName(), weightOnlyResult.legacyBiomeName());
        assertEquals(coordinateResult.candidateModernBiomeKey(), weightOnlyResult.candidateModernBiomeKey());
        assertEquals(coordinateResult.normalizedWeightValue(), weightOnlyResult.normalizedWeightValue());

        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.skeletonShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.unregisteredSkeletonReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.extendsMinecraftBiomeSource());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.runtimeBiomeSourceImplemented());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.runtimeBiomeSourceRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.codecImplemented());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.biomeHolderResolutionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.resourceKeyConversionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.dimensionBindingReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.canActivateCaveniaNow());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.consumesBiomeSelectionAdapter());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.decisionAllowsUnregisteredSkeleton());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.decisionAllowsCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.decisionAllowsRegistryLookupAccess());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.decisionAllowsDimensionActivation());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.realSubclassDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.realSubclassReady());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.runtimeBiomeSourceApiShapeInventoryReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.runtimeBiomeSourceApiShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.realSubclassGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.realSubclassGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.guardedRealSubclassStubImplemented());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.guardedRealSubclassRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.codecMethodShapeStubReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.codecMethodShapeRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.holderConversionReadinessReady());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.holderConversionRuntimeReady());
        assertEquals(14, CaveniaRuntimeBiomeSourceSkeleton.guardrailCount());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.allGuardrailsEnforced());
        assertEquals(14, CaveniaRuntimeBiomeSourceSkeleton.adapterEntryCount());
        assertEquals(675, CaveniaRuntimeBiomeSourceSkeleton.adapterTotalWeight());
        assertEquals(68, CaveniaRuntimeBiomeSourceSkeleton.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSkeleton.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceSkeleton.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceSkeleton.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceSkeleton.cavemanRemainsDeferred());
    }

    @Test
    void runtimeBiomeSourceSkeletonSourcesStayOutOfRuntimeBiomeSourceApis() throws IOException {
        String querySource = Files.readString(QUERY_SOURCE);
        String resultSource = Files.readString(RESULT_SOURCE);
        String skeletonSource = Files.readString(SKELETON_SOURCE);

        assertSourceStaysInert(querySource);
        assertSourceStaysInert(resultSource);
        assertSourceStaysInert(skeletonSource);

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
                "Expected the runtime-biome-source skeleton to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertResult(
        CaveniaRuntimeBiomeSourceSkeletonResult result,
        CaveniaWeightedBiomeSelectionEntry expectedEntry,
        int expectedX,
        int expectedY,
        int expectedZ,
        int expectedInputValue,
        int expectedNormalizedValue
    ) {
        assertEquals(expectedX, result.x());
        assertEquals(expectedY, result.y());
        assertEquals(expectedZ, result.z());
        assertEquals(expectedInputValue, result.inputWeightValue());
        assertEquals(expectedNormalizedValue, result.normalizedWeightValue());
        assertEquals(expectedEntry.legacyBiomeName(), result.legacyBiomeName());
        assertEquals(expectedEntry.candidateModernBiomeKey(), result.candidateModernBiomeKey());
        assertEquals(expectedEntry.weight(), result.weight());
        assertTrue(result.candidateOnly());
        assertFalse(result.finalRuntimeMapping());
        assertFalse(result.registryVerified());
        assertFalse(result.runtimeBiomeResolved());
        assertFalse(result.runtimeBiomeSourceResult());
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
