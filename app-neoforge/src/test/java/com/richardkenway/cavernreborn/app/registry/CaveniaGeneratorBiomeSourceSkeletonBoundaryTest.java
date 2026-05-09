package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaGeneratorBiomeSourceSkeletonBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path SKELETON_DOC = resolveProjectFile("docs", "cavenia-generator-biome-source-unregistered-skeleton-mvp.md");
    private static final Path REGISTRATION_DOC = resolveProjectFile("docs", "cavenia-active-generator-registration-inert-boundary-mvp.md");
    private static final Path BRIDGE_DOC = resolveProjectFile("docs", "cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md");
    private static final Path SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-active-generator-technical-scaffold-mvp.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path RUNTIME_KEY_DOC = resolveProjectFile("docs", "cavenia-runtime-key-inactive-dimension-scaffold-mvp.md");
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheUnregisteredGeneratorAndBiomeSelectionSkeletons() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String skeletonDoc = Files.readString(SKELETON_DOC);
        String registrationDoc = Files.readString(REGISTRATION_DOC);
        String bridgeDoc = Files.readString(BRIDGE_DOC);
        String scaffoldDoc = Files.readString(SCAFFOLD_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);

        assertTrue(skeletonDoc.contains("Cavenia Chunk Generator / Biome Source Unregistered Skeleton MVP"));
        assertTrue(skeletonDoc.contains("`CaveniaGeneratorSkeleton`"));
        assertTrue(skeletonDoc.contains("`CaveniaBiomeSelectionSkeleton`"));
        assertTrue(skeletonDoc.contains("`CaveniaGeneratorBridge`"));
        assertTrue(skeletonDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(skeletonDoc.contains("`CaveniaGeneratorScaffold`"));
        assertTrue(skeletonDoc.contains("`CaveniaTerrainGeneratorPolicy`"));
        assertTrue(skeletonDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(skeletonDoc.contains("`CaveniaCaveCarverPolicy`"));
        assertTrue(skeletonDoc.contains("`CaveniaVeinsContentPolicy`"));
        assertTrue(skeletonDoc.contains("`CaveniaPopulationPolicy`"));
        assertTrue(skeletonDoc.contains("`CaveniaSpawnProviderPolicy`"));
        assertTrue(skeletonDoc.contains("not a `ChunkGenerator`"));
        assertTrue(skeletonDoc.contains("not a `BiomeSource`"));
        assertTrue(skeletonDoc.contains("no codec or registry entry was added"));
        assertTrue(skeletonDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(skeletonDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(skeletonDoc.contains("no active Cavenia runtime level"));
        assertTrue(skeletonDoc.contains("no active Cavenia spawning"));
        assertTrue(skeletonDoc.contains("no active crazy spawning"));
        assertTrue(skeletonDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(skeletonDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Chunk Generator / Biome Source Unregistered Skeleton MVP"));
        assertTrue(readme.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Chunk Generator / Biome Source Unregistered Skeleton MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(registrationDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(bridgeDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
    }

    @Test
    void unregisteredSkeletonsKeepActiveGeneratorAndWorldgenSurfaceAbsent() throws IOException {
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");

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
                "Expected the unregistered skeleton MVP to avoid adding any active Cavenia generator, biome-source or cave-carver runtime classes"
            );
        }

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/cavenia")
                            || path.contains("/worldgen/biome/cavenia")
                    ),
                "Expected the unregistered skeleton MVP to keep active Cavenia generator/worldgen resources absent"
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
