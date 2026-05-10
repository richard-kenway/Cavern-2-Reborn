package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaLegacyToModernBiomeKeyMappings;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWeightedBiomeSelectionAlgorithm;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWeightedBiomeSelectionEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaWeightedBiomeSelectionResult;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaBiomeEntry;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaBiomeTopFilterPolicy;

class CaveniaWeightedBiomeSelectionAlgorithmTest {
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaWeightedBiomeSelectionEntry.java"
    );
    private static final Path RESULT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaWeightedBiomeSelectionResult.java"
    );
    private static final Path ALGORITHM_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaWeightedBiomeSelectionAlgorithm.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void weightedBiomeSelectionAlgorithmStaysDeterministicAndInventoryOnly() {
        List<CaveniaWeightedBiomeSelectionEntry> entries = CaveniaWeightedBiomeSelectionAlgorithm.entries();
        List<CaveniaBiomeEntry> legacyPolicyEntries = CaveniaBiomeTopFilterPolicy.entries();

        assertEquals(14, entries.size());
        assertEquals(14, CaveniaWeightedBiomeSelectionAlgorithm.entryCount());
        assertEquals(675, CaveniaWeightedBiomeSelectionAlgorithm.totalWeight());
        assertEquals(0, CaveniaWeightedBiomeSelectionAlgorithm.firstWeightValue());
        assertEquals(674, CaveniaWeightedBiomeSelectionAlgorithm.lastWeightValue());
        assertEquals(CaveniaLegacyToModernBiomeKeyMappings.legacyBiomeNames(), CaveniaWeightedBiomeSelectionAlgorithm.legacyBiomeNames());
        assertEquals(
            CaveniaLegacyToModernBiomeKeyMappings.candidateModernBiomeKeys(),
            CaveniaWeightedBiomeSelectionAlgorithm.candidateModernBiomeKeys()
        );
        assertEquals(
            legacyPolicyEntries.stream().map(CaveniaBiomeEntry::legacyBiomeName).toList(),
            entries.stream().map(CaveniaWeightedBiomeSelectionEntry::legacyBiomeName).toList()
        );
        assertTrue(entries.stream().allMatch(entry -> entry.weight() > 0));
        assertTrue(CaveniaWeightedBiomeSelectionAlgorithm.allEntriesHavePositiveWeights());
        assertTrue(CaveniaWeightedBiomeSelectionAlgorithm.allWeightRangesContiguous());
        assertEquals(0, entries.get(0).inclusiveStartWeight());
        assertEquals(675, entries.get(entries.size() - 1).exclusiveEndWeight());
        assertTrue(entries.stream().allMatch(CaveniaWeightedBiomeSelectionEntry::candidateInventoryOnly));
        assertTrue(entries.stream().allMatch(entry -> !entry.finalRuntimeMapping()));
        assertTrue(entries.stream().allMatch(entry -> !entry.registryVerified()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeReady()));
        assertTrue(CaveniaWeightedBiomeSelectionAlgorithm.allLegacyBiomesHaveCandidateKeys());
        assertTrue(CaveniaWeightedBiomeSelectionAlgorithm.candidateInventoryReady());
        assertTrue(CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmReady());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.weightedSelectionAlgorithmRuntimeReady());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.anyFinalRuntimeMapping());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.anyRegistryVerified());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.runtimeBiomeSourceReady());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.codecRegistered());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.registryLookupAccessReady());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.modernBiomeMappingReady());
        assertTrue(CaveniaWeightedBiomeSelectionAlgorithm.planIsNonRuntime());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.activationAllowedInThisSlice());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.canActivateCaveniaNow());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.dimensionJsonPresent());
        assertFalse(CaveniaWeightedBiomeSelectionAlgorithm.dimensionTypeJsonPresent());
        assertTrue(CaveniaWeightedBiomeSelectionAlgorithm.cavemanRemainsDeferred());

        for (int i = 0; i < entries.size(); i++) {
            CaveniaWeightedBiomeSelectionEntry selectionEntry = entries.get(i);
            CaveniaBiomeEntry legacyEntry = legacyPolicyEntries.get(i);

            assertEquals(legacyEntry.legacyBiomeName(), selectionEntry.legacyBiomeName());
            assertEquals(legacyEntry.weight(), selectionEntry.weight());
        }

        CaveniaWeightedBiomeSelectionEntry plainsEntry = CaveniaWeightedBiomeSelectionAlgorithm.entryForLegacyBiomeName("PLAINS").orElseThrow();
        CaveniaWeightedBiomeSelectionEntry mesaEntry = CaveniaWeightedBiomeSelectionAlgorithm.entryForLegacyBiomeName("MESA").orElseThrow();
        CaveniaWeightedBiomeSelectionEntry previousToMesa = entries.get(entries.indexOf(mesaEntry) - 1);

        assertSelection(0, entries.get(0));
        assertSelection(674, entries.get(entries.size() - 1));
        assertSelection(675, entries.get(0));
        assertSelection(-1, entries.get(entries.size() - 1));
        assertSelection(plainsEntry.inclusiveStartWeight(), plainsEntry);
        assertSelection(mesaEntry.inclusiveStartWeight(), mesaEntry);
        assertSelection(mesaEntry.inclusiveStartWeight() - 1, previousToMesa);

        assertEquals(Optional.empty(), CaveniaWeightedBiomeSelectionAlgorithm.entryForLegacyBiomeName("ICE_PLAINS"));

        assertImmutableList(CaveniaWeightedBiomeSelectionAlgorithm.entries(), entries.get(0));
        assertImmutableList(CaveniaWeightedBiomeSelectionAlgorithm.legacyBiomeNames(), "ICE_PLAINS");
        assertImmutableList(CaveniaWeightedBiomeSelectionAlgorithm.candidateModernBiomeKeys(), "minecraft:ice_spikes");
    }

    @Test
    void weightedBiomeSelectionSourcesStayOutOfRuntimeBiomeSourcePaths() throws IOException {
        String entrySource = Files.readString(ENTRY_SOURCE);
        String resultSource = Files.readString(RESULT_SOURCE);
        String algorithmSource = Files.readString(ALGORITHM_SOURCE);

        assertSourceStaysInert(entrySource);
        assertSourceStaysInert(resultSource);
        assertSourceStaysInert(algorithmSource);

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
                "Expected the weighted biome-selection algorithm to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSelection(int inputValue, CaveniaWeightedBiomeSelectionEntry expectedEntry) {
        CaveniaWeightedBiomeSelectionResult result = CaveniaWeightedBiomeSelectionAlgorithm.selectByWeightValue(inputValue);

        assertEquals(expectedEntry.legacyBiomeName(), result.legacyBiomeName());
        assertEquals(expectedEntry.candidateModernBiomeKey(), result.candidateModernBiomeKey());
        assertEquals(expectedEntry.weight(), result.weight());
        assertFalse(result.finalRuntimeMapping());
        assertFalse(result.registryVerified());
        assertFalse(result.runtimeReady());
        assertTrue(
            expectedEntry.inclusiveStartWeight() <= result.normalizedWeightValue()
                && result.normalizedWeightValue() < expectedEntry.exclusiveEndWeight()
        );
        assertEquals(Optional.of(expectedEntry), CaveniaWeightedBiomeSelectionAlgorithm.entryForWeightValue(inputValue));
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
