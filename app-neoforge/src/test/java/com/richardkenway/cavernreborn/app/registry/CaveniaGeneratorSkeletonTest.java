package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorBridge;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRegistrationBoundary;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorSkeleton;

class CaveniaGeneratorSkeletonTest {
    private static final Path GENERATOR_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaGeneratorSkeleton.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void generatorSkeletonExposesOnlyInertGeneratorShapeFacts() {
        assertEquals("cavernreborn:cavenia", CaveniaGeneratorSkeleton.dimensionId());
        assertEquals("cavernreborn:cavenia", CaveniaGeneratorSkeleton.location().toString());
        assertEquals("cavernreborn:cavenia", CaveniaGeneratorSkeleton.levelKey().location().toString());
        assertEquals(128, CaveniaGeneratorSkeleton.worldHeight());
        assertEquals(CaveniaGeneratorBridge.scaffoldStages(), CaveniaGeneratorSkeleton.scaffoldStages());
        assertEquals(CaveniaGeneratorBridge.terrainStages(), CaveniaGeneratorSkeleton.terrainStages());
        assertEquals(14, CaveniaGeneratorSkeleton.biomeSelection().entryCount());
        assertEquals(675, CaveniaGeneratorSkeleton.biomeSelection().totalWeight());
        assertFalse(CaveniaGeneratorSkeleton.canCreateChunks());
        assertFalse(CaveniaGeneratorSkeleton.canMutatePrimer());
        assertFalse(CaveniaGeneratorSkeleton.runtimeGeneratorRegistered());
        assertFalse(CaveniaGeneratorSkeleton.codecRegistered());
        assertFalse(CaveniaGeneratorSkeleton.generatorRegistryEntryRegistered());
        assertEquals(CaveniaGeneratorRegistrationBoundary.activationRequirements(), CaveniaGeneratorSkeleton.activationRequirements());
        assertTrue(CaveniaGeneratorSkeleton.activationBlockedReason().contains("dimension JSON"));
        assertTrue(CaveniaGeneratorSkeleton.activationBlockedReason().contains("dimension type JSON"));
        assertTrue(CaveniaGeneratorSkeleton.activationBlockedReason().contains("chunk generator implementation"));
        assertTrue(CaveniaGeneratorSkeleton.activationBlockedReason().contains("biome source strategy"));
        assertTrue(CaveniaGeneratorSkeleton.activationBlockedReason().contains("generator codec/registration"));
        assertTrue(CaveniaGeneratorSkeleton.activationBlockedReason().contains("safe access"));
        assertTrue(CaveniaGeneratorSkeleton.activationBlockedReason().contains("Cavenia-only spawn host"));
    }

    @Test
    void generatorSkeletonSourceStaysOutOfRuntimeGeneratorPaths() throws IOException {
        String source = Files.readString(GENERATOR_SOURCE);

        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Registry.register"));

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
                "Expected the generator skeleton MVP to avoid adding any active Cavenia generator or biome-source runtime classes"
            );
        }
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
