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

import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorActivationRequirement;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaGeneratorRegistrationBoundary;

class CaveniaGeneratorRegistrationBoundaryTest {
    private static final Path BOUNDARY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaGeneratorRegistrationBoundary.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void boundaryExposesOnlyInertRegistrationFacts() {
        assertEquals("cavernreborn:cavenia", CaveniaGeneratorRegistrationBoundary.location().toString());
        assertEquals("cavernreborn:cavenia", CaveniaGeneratorRegistrationBoundary.levelKey().location().toString());
        assertEquals("cavernreborn:cavenia", CaveniaGeneratorRegistrationBoundary.dimensionId());
        assertFalse(CaveniaGeneratorRegistrationBoundary.bridgeRuntimeGeneratorRegistered());
        assertFalse(CaveniaGeneratorRegistrationBoundary.codecRegistered());
        assertFalse(CaveniaGeneratorRegistrationBoundary.generatorRegistryEntryRegistered());
        assertFalse(CaveniaGeneratorRegistrationBoundary.chunkGeneratorClassPresent());
        assertFalse(CaveniaGeneratorRegistrationBoundary.biomeSourceClassPresent());
        assertFalse(CaveniaGeneratorRegistrationBoundary.dimensionJsonPresent());
        assertFalse(CaveniaGeneratorRegistrationBoundary.dimensionTypeJsonPresent());
        assertFalse(CaveniaGeneratorRegistrationBoundary.safeAccessReady());
        assertFalse(CaveniaGeneratorRegistrationBoundary.spawnHostReady());
        assertFalse(CaveniaGeneratorRegistrationBoundary.canActivateRuntimeGenerator());
        assertEquals(
            List.of(
                CaveniaGeneratorActivationRequirement.DIMENSION_JSON,
                CaveniaGeneratorActivationRequirement.DIMENSION_TYPE_JSON,
                CaveniaGeneratorActivationRequirement.CHUNK_GENERATOR_IMPLEMENTATION,
                CaveniaGeneratorActivationRequirement.BIOME_SOURCE_STRATEGY,
                CaveniaGeneratorActivationRequirement.GENERATOR_CODEC,
                CaveniaGeneratorActivationRequirement.GENERATOR_REGISTRY_ENTRY,
                CaveniaGeneratorActivationRequirement.SAFE_ACCESS_OR_TELEPORT,
                CaveniaGeneratorActivationRequirement.CAVENIA_ONLY_SPAWN_HOST,
                CaveniaGeneratorActivationRequirement.WORLDGEN_RESOURCE_MAPPING
            ),
            CaveniaGeneratorRegistrationBoundary.activationRequirements()
        );
        assertTrue(CaveniaGeneratorRegistrationBoundary.activationBlockedReason().contains("dimension JSON"));
        assertTrue(CaveniaGeneratorRegistrationBoundary.activationBlockedReason().contains("dimension type JSON"));
        assertTrue(CaveniaGeneratorRegistrationBoundary.activationBlockedReason().contains("chunk generator implementation"));
        assertTrue(CaveniaGeneratorRegistrationBoundary.activationBlockedReason().contains("biome source strategy"));
        assertTrue(CaveniaGeneratorRegistrationBoundary.activationBlockedReason().contains("generator codec/registration"));
        assertTrue(CaveniaGeneratorRegistrationBoundary.activationBlockedReason().contains("safe access"));
        assertTrue(CaveniaGeneratorRegistrationBoundary.activationBlockedReason().contains("Cavenia-only spawn host"));
    }

    @Test
    void boundarySourceStaysOutOfRuntimeRegistrationPaths() throws IOException {
        String source = Files.readString(BOUNDARY_SOURCE);

        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("implements BiomeSource"));
        assertFalse(source.contains("implements ChunkGenerator"));
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
                "Expected the inert registration boundary MVP to avoid adding any active Cavenia generator or biome-source runtime classes"
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
