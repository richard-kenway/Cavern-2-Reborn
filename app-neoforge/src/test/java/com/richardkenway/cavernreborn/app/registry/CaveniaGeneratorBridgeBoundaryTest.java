package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaGeneratorBridgeBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path BRIDGE_DOC = resolveProjectFile("docs", "cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md");
    private static final Path SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-active-generator-technical-scaffold-mvp.md");
    private static final Path REGISTRATION_DOC = resolveProjectFile("docs", "cavenia-active-generator-registration-inert-boundary-mvp.md");
    private static final Path SKELETON_DOC = resolveProjectFile("docs", "cavenia-generator-biome-source-unregistered-skeleton-mvp.md");
    private static final Path CONTRACTS_DOC = resolveProjectFile("docs", "cavenia-generator-runtime-contracts-non-registered-mvp.md");
    private static final Path ACTIVATION_HOSTS_DOC = resolveProjectFile("docs", "cavenia-generator-activation-readiness-host-contracts-mvp.md");
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
    void docsPinTheInertAppSideGeneratorBridge() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String bridgeDoc = Files.readString(BRIDGE_DOC);
        String scaffoldDoc = Files.readString(SCAFFOLD_DOC);
        String registrationDoc = Files.readString(REGISTRATION_DOC);
        String skeletonDoc = Files.readString(SKELETON_DOC);
        String contractsDoc = Files.readString(CONTRACTS_DOC);
        String activationHostsDoc = Files.readString(ACTIVATION_HOSTS_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);

        assertTrue(bridgeDoc.contains("Cavenia Active Generator Runtime Prototype / Inert App-Side Bridge MVP"));
        assertTrue(bridgeDoc.contains("`CaveniaGeneratorBridge`"));
        assertTrue(bridgeDoc.contains("`CaveniaGeneratorScaffold`"));
        assertTrue(bridgeDoc.contains("`CaveniaTerrainGeneratorPolicy`"));
        assertTrue(bridgeDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(bridgeDoc.contains("`CaveniaCaveCarverPolicy`"));
        assertTrue(bridgeDoc.contains("`CaveniaVeinsContentPolicy`"));
        assertTrue(bridgeDoc.contains("`CaveniaPopulationPolicy`"));
        assertTrue(bridgeDoc.contains("`CaveniaSpawnProviderPolicy`"));
        assertTrue(bridgeDoc.contains("not a `ChunkGenerator`"));
        assertTrue(bridgeDoc.contains("not a `BiomeSource`"));
        assertTrue(bridgeDoc.contains("no codec or registry entry was added"));
        assertTrue(bridgeDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(bridgeDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(bridgeDoc.contains("no active Cavenia runtime level"));
        assertTrue(bridgeDoc.contains("no active Cavenia spawning"));
        assertTrue(bridgeDoc.contains("no active crazy spawning"));
        assertTrue(bridgeDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(bridgeDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Active Generator Runtime Prototype / Inert App-Side Bridge MVP"));
        assertTrue(readme.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Active Generator Runtime Prototype / Inert App-Side Bridge MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
        assertTrue(bridgeDoc.contains("docs/cavenia-active-generator-registration-inert-boundary-mvp.md"));
        assertTrue(bridgeDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(bridgeDoc.contains("docs/cavenia-generator-runtime-contracts-non-registered-mvp.md"));
        assertTrue(bridgeDoc.contains("docs/cavenia-generator-activation-readiness-host-contracts-mvp.md"));
        assertTrue(registrationDoc.contains("`CaveniaGeneratorBridge`"));
        assertTrue(skeletonDoc.contains("`CaveniaGeneratorBridge`"));
        assertTrue(contractsDoc.contains("`CaveniaGeneratorBridge`"));
        assertTrue(activationHostsDoc.contains("`CaveniaGeneratorBridge`"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
    }

    @Test
    void inertBridgeBoundaryKeepsActiveGeneratorAndResourceSurfaceAbsent() throws IOException {
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
                "Expected the inert app-side bridge MVP to avoid adding any active Cavenia generator, biome-source or cave-carver runtime classes"
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
                "Expected the inert app-side bridge MVP to keep active Cavenia generator/worldgen resources absent"
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
