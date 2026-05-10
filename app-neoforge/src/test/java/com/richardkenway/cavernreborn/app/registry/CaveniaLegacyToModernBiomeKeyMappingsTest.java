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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeKeyMappingKind;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaLegacyToModernBiomeKeyMappingEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaLegacyToModernBiomeKeyMappings;

class CaveniaLegacyToModernBiomeKeyMappingsTest {
    private static final Path KIND_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaBiomeKeyMappingKind.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaLegacyToModernBiomeKeyMappingEntry.java"
    );
    private static final Path MAPPINGS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaLegacyToModernBiomeKeyMappings.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void legacyToModernBiomeKeyMappingsStayInventoryOnlyAndDeterministic() {
        List<CaveniaLegacyToModernBiomeKeyMappingEntry> entries = CaveniaLegacyToModernBiomeKeyMappings.entries();

        assertEquals(14, entries.size());
        assertEquals(14, CaveniaLegacyToModernBiomeKeyMappings.entryCount());
        assertEquals(14, CaveniaLegacyToModernBiomeKeyMappings.legacyBiomeEntryCount());
        assertEquals(675, CaveniaLegacyToModernBiomeKeyMappings.legacyBiomeTotalWeight());
        assertEquals(
            List.of(
                "OCEAN",
                "PLAINS",
                "DESERT",
                "DESERT_HILLS",
                "FOREST",
                "FOREST_HILLS",
                "TAIGA",
                "TAIGA_HILLS",
                "JUNGLE",
                "JUNGLE_HILLS",
                "SWAMPLAND",
                "EXTREME_HILLS",
                "SAVANNA",
                "MESA"
            ),
            CaveniaLegacyToModernBiomeKeyMappings.legacyBiomeNames()
        );
        assertEquals(
            CaveniaLegacyToModernBiomeKeyMappings.legacyBiomeNames(),
            com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSourceStrategyPlan.legacyBiomeNames()
        );
        assertTrue(entries.stream().allMatch(entry -> entry.candidateInventoried()));
        assertTrue(entries.stream().allMatch(entry -> !entry.finalRuntimeMapping()));
        assertTrue(entries.stream().allMatch(entry -> !entry.registryVerified()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeReady()));
        assertTrue(entries.stream().allMatch(entry -> entry.rationale() != null && !entry.rationale().isBlank()));

        assertEntry("OCEAN", "minecraft:ocean", CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT);
        assertEntry("PLAINS", "minecraft:plains", CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT);
        assertEntry("DESERT", "minecraft:desert", CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT);
        assertEntry("DESERT_HILLS", "minecraft:desert", CaveniaBiomeKeyMappingKind.COLLAPSED_LEGACY_VARIANT);
        assertEntry("FOREST", "minecraft:forest", CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT);
        assertEntry("FOREST_HILLS", "minecraft:forest", CaveniaBiomeKeyMappingKind.COLLAPSED_LEGACY_VARIANT);
        assertEntry("TAIGA", "minecraft:taiga", CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT);
        assertEntry("TAIGA_HILLS", "minecraft:taiga", CaveniaBiomeKeyMappingKind.COLLAPSED_LEGACY_VARIANT);
        assertEntry("JUNGLE", "minecraft:jungle", CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT);
        assertEntry("JUNGLE_HILLS", "minecraft:jungle", CaveniaBiomeKeyMappingKind.COLLAPSED_LEGACY_VARIANT);
        assertEntry("SWAMPLAND", "minecraft:swamp", CaveniaBiomeKeyMappingKind.RENAMED);
        assertEntry("EXTREME_HILLS", "minecraft:windswept_hills", CaveniaBiomeKeyMappingKind.RENAMED);
        assertEntry("SAVANNA", "minecraft:savanna", CaveniaBiomeKeyMappingKind.EXACT_OR_DIRECT);
        assertEntry("MESA", "minecraft:badlands", CaveniaBiomeKeyMappingKind.RENAMED);

        assertEquals(Optional.empty(), CaveniaLegacyToModernBiomeKeyMappings.entryForLegacyBiomeName("ICE_PLAINS"));
        assertEquals(Optional.empty(), CaveniaLegacyToModernBiomeKeyMappings.candidateKeyForLegacyBiomeName("ICE_PLAINS"));
        assertEquals(Optional.empty(), CaveniaLegacyToModernBiomeKeyMappings.mappingKindForLegacyBiomeName("ICE_PLAINS"));
        assertTrue(CaveniaLegacyToModernBiomeKeyMappings.allLegacyBiomesHaveCandidates());
        assertTrue(CaveniaLegacyToModernBiomeKeyMappings.allCandidatesInventoried());
        assertTrue(CaveniaLegacyToModernBiomeKeyMappings.candidateInventoryReady());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.anyFinalRuntimeMapping());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.allFinalRuntimeMappingsReady());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.anyRegistryVerified());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.registryLookupAccessReady());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.runtimeBiomeSourceReady());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.codecRegistered());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.modernBiomeMappingReady());
        assertTrue(CaveniaLegacyToModernBiomeKeyMappings.planIsNonRuntime());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.activationAllowedInThisSlice());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.canActivateCaveniaNow());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.dimensionJsonPresent());
        assertFalse(CaveniaLegacyToModernBiomeKeyMappings.dimensionTypeJsonPresent());
        assertTrue(CaveniaLegacyToModernBiomeKeyMappings.cavemanRemainsDeferred());

        assertImmutableList(CaveniaLegacyToModernBiomeKeyMappings.entries(), entries.get(0));
        assertImmutableList(CaveniaLegacyToModernBiomeKeyMappings.legacyBiomeNames(), "ICE_PLAINS");
        assertImmutableList(CaveniaLegacyToModernBiomeKeyMappings.candidateModernBiomeKeys(), "minecraft:ice_spikes");
    }

    @Test
    void legacyToModernBiomeKeyMappingSourcesStayOutOfRuntimePaths() throws IOException {
        String kindSource = Files.readString(KIND_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String mappingsSource = Files.readString(MAPPINGS_SOURCE);

        assertSourceStaysInert(kindSource);
        assertSourceStaysInert(entrySource);
        assertSourceStaysInert(mappingsSource);

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
                "Expected the legacy-to-modern biome-key mapping inventory to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertEntry(String legacyBiomeName, String candidateKey, CaveniaBiomeKeyMappingKind mappingKind) {
        assertEquals(
            Optional.of(candidateKey),
            CaveniaLegacyToModernBiomeKeyMappings.candidateKeyForLegacyBiomeName(legacyBiomeName)
        );
        assertEquals(
            Optional.of(mappingKind),
            CaveniaLegacyToModernBiomeKeyMappings.mappingKindForLegacyBiomeName(legacyBiomeName)
        );
        assertEquals(
            legacyBiomeName,
            CaveniaLegacyToModernBiomeKeyMappings.entryForLegacyBiomeName(legacyBiomeName).orElseThrow().legacyBiomeName()
        );
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
