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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaBiomeSelectionSkeleton;

class CaveniaBiomeSelectionSkeletonTest {
    private static final Path BIOME_SELECTION_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSelectionSkeleton.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void biomeSelectionSkeletonExposesOnlyInertBiomeInputFacts() {
        CaveniaBiomeSelectionSkeleton skeleton = CaveniaBiomeSelectionSkeleton.create();

        assertEquals(14, skeleton.entryCount());
        assertEquals(675, skeleton.totalWeight());
        assertTrue(skeleton.legacyBiomeNames().containsAll(List.of("OCEAN", "PLAINS", "DESERT", "MESA")));
        assertTrue(skeleton.hasLegacyBiome("PLAINS"));
        assertFalse(skeleton.hasLegacyBiome("ICE_PLAINS"));
        assertEquals(Optional.of("minecraft:grass"), skeleton.topBlockForLegacyBiomeName("PLAINS"));
        assertEquals(Optional.of("minecraft:red_sandstone"), skeleton.topBlockForLegacyBiomeName("MESA"));
        assertEquals(Optional.empty(), skeleton.topBlockForLegacyBiomeName("ICE_PLAINS"));
        assertFalse(skeleton.isRuntimeBiomeSource());
        assertFalse(skeleton.codecRegistered());
        assertTrue(skeleton.requiresBiomeSourceStrategyBeforeActivation());
    }

    @Test
    void biomeSelectionSkeletonKeepsNamesImmutableAndStaysOutOfRuntimeBiomeSourcePaths() throws IOException {
        CaveniaBiomeSelectionSkeleton skeleton = CaveniaBiomeSelectionSkeleton.create();
        List<String> legacyBiomeNames = skeleton.legacyBiomeNames();
        String source = Files.readString(BIOME_SELECTION_SOURCE);

        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Registry.register"));
        assertThrowsUnsupportedOperation(() -> legacyBiomeNames.add("ICE_PLAINS"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");

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
                    ),
                "Expected the biome-selection skeleton MVP to avoid adding any active Cavenia generator or biome-source runtime classes"
            );
        }
    }

    private static void assertThrowsUnsupportedOperation(Runnable operation) {
        try {
            operation.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }

        throw new AssertionError("Expected an immutable biome-name view");
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
