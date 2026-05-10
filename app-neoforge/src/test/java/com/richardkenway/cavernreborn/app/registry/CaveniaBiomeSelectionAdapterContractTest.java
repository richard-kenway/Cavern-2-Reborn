package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSelectionAdapterContract;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSelectionAdapterQuery;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSelectionAdapterResult;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWeightedBiomeSelectionAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWeightedBiomeSelectionEntry;

class CaveniaBiomeSelectionAdapterContractTest {
    private static final Path QUERY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeSelectionAdapterQuery.java"
    );
    private static final Path RESULT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeSelectionAdapterResult.java"
    );
    private static final Path CONTRACT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeSelectionAdapterContract.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void biomeSelectionAdapterContractStaysInertAndDelegatesToThePureWeightedSelector() {
        CaveniaWeightedBiomeSelectionEntry firstEntry = CaveniaWeightedBiomeSelectionAlgorithm.entries().get(0);
        CaveniaWeightedBiomeSelectionEntry lastEntry =
            CaveniaWeightedBiomeSelectionAlgorithm.entries().get(CaveniaWeightedBiomeSelectionAlgorithm.entries().size() - 1);

        CaveniaBiomeSelectionAdapterQuery query = CaveniaBiomeSelectionAdapterContract.queryForWeightValue(675);

        assertEquals(675, query.weightValue());
        assertEquals(0, query.x());
        assertEquals(0, query.y());
        assertEquals(0, query.z());
        assertFalse(query.registryLookupAvailable());
        assertFalse(query.runtimeBiomeSourceAvailable());

        assertResult(CaveniaBiomeSelectionAdapterContract.selectCandidateByWeightValue(0), firstEntry, 0, 0);
        assertResult(CaveniaBiomeSelectionAdapterContract.selectCandidate(query), firstEntry, 675, 0);
        assertResult(CaveniaBiomeSelectionAdapterContract.selectCandidateByWeightValue(-1), lastEntry, -1, 674);

        assertEquals(14, CaveniaBiomeSelectionAdapterContract.entryCount());
        assertEquals(675, CaveniaBiomeSelectionAdapterContract.totalWeight());
        assertEquals(0, CaveniaBiomeSelectionAdapterContract.firstWeightValue());
        assertEquals(674, CaveniaBiomeSelectionAdapterContract.lastWeightValue());
        assertTrue(CaveniaBiomeSelectionAdapterContract.adapterShapeReady());
        assertFalse(CaveniaBiomeSelectionAdapterContract.adapterRuntimeReady());
        assertTrue(CaveniaBiomeSelectionAdapterContract.consumesWeightedSelector());
        assertTrue(CaveniaBiomeSelectionAdapterContract.weightedSelectionAlgorithmReady());
        assertFalse(CaveniaBiomeSelectionAdapterContract.weightedSelectionAlgorithmRuntimeReady());
        assertTrue(CaveniaBiomeSelectionAdapterContract.candidateInventoryReady());
        assertFalse(CaveniaBiomeSelectionAdapterContract.finalRuntimeMappingReady());
        assertFalse(CaveniaBiomeSelectionAdapterContract.registryLookupAccessReady());
        assertFalse(CaveniaBiomeSelectionAdapterContract.runtimeBiomeSourceReady());
        assertFalse(CaveniaBiomeSelectionAdapterContract.runtimeBiomeSourceRegistered());
        assertFalse(CaveniaBiomeSelectionAdapterContract.codecRegistered());
        assertFalse(CaveniaBiomeSelectionAdapterContract.modernBiomeMappingReady());
        assertTrue(CaveniaBiomeSelectionAdapterContract.planIsNonRuntime());
        assertFalse(CaveniaBiomeSelectionAdapterContract.activationAllowedInThisSlice());
        assertFalse(CaveniaBiomeSelectionAdapterContract.canActivateCaveniaNow());
        assertFalse(CaveniaBiomeSelectionAdapterContract.dimensionJsonPresent());
        assertFalse(CaveniaBiomeSelectionAdapterContract.dimensionTypeJsonPresent());
        assertTrue(CaveniaBiomeSelectionAdapterContract.cavemanRemainsDeferred());
    }

    @Test
    void biomeSelectionAdapterSourcesStayOutOfRuntimeBiomeSourcePaths() throws IOException {
        String querySource = Files.readString(QUERY_SOURCE);
        String resultSource = Files.readString(RESULT_SOURCE);
        String contractSource = Files.readString(CONTRACT_SOURCE);

        assertSourceStaysInert(querySource);
        assertSourceStaysInert(resultSource);
        assertSourceStaysInert(contractSource);

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
                "Expected the biome-selection adapter contract to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertResult(
        CaveniaBiomeSelectionAdapterResult result,
        CaveniaWeightedBiomeSelectionEntry expectedEntry,
        int expectedInputValue,
        int expectedNormalizedValue
    ) {
        assertEquals(expectedInputValue, result.inputWeightValue());
        assertEquals(expectedNormalizedValue, result.normalizedWeightValue());
        assertEquals(expectedEntry.legacyBiomeName(), result.legacyBiomeName());
        assertEquals(expectedEntry.candidateModernBiomeKey(), result.candidateModernBiomeKey());
        assertEquals(expectedEntry.weight(), result.weight());
        assertTrue(result.candidateOnly());
        assertFalse(result.finalRuntimeMapping());
        assertFalse(result.registryVerified());
        assertFalse(result.runtimeReady());
        assertFalse(result.runtimeBiomeSourceResult());
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("RegistryLookup"));
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
