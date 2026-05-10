package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaDimensionAccessSpawnSplitContractsBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path SPLIT_DOC = resolveProjectFile("docs", "cavenia-dimension-access-spawn-split-contracts-mvp.md");
    private static final Path ACTIVATION_HOSTS_DOC = resolveProjectFile("docs", "cavenia-generator-activation-readiness-host-contracts-mvp.md");
    private static final Path REGISTRATION_DOC = resolveProjectFile("docs", "cavenia-active-generator-registration-inert-boundary-mvp.md");
    private static final Path GENERATOR_HOST_DOC = resolveProjectFile("docs", "cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md");
    private static final Path BIOME_RUNTIME_DOC = resolveProjectFile("docs", "cavenia-generator-runtime-contracts-non-registered-mvp.md");
    private static final Path BRIDGE_DOC = resolveProjectFile("docs", "cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md");
    private static final Path TECHNICAL_SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-active-generator-technical-scaffold-mvp.md");
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
    void docsPinTheDimensionAccessAndSpawnSplitContracts() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String splitDoc = Files.readString(SPLIT_DOC);
        String activationHostsDoc = Files.readString(ACTIVATION_HOSTS_DOC);
        String registrationDoc = Files.readString(REGISTRATION_DOC);
        String generatorHostDoc = Files.readString(GENERATOR_HOST_DOC);
        String biomeRuntimeDoc = Files.readString(BIOME_RUNTIME_DOC);
        String bridgeDoc = Files.readString(BRIDGE_DOC);
        String technicalScaffoldDoc = Files.readString(TECHNICAL_SCAFFOLD_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);

        assertTrue(splitDoc.contains("Cavenia Dimension-Resource / Access-Travel / Spawn-Host Split Contracts MVP"));
        assertTrue(splitDoc.contains("`CaveniaDimensionResourceContracts`"));
        assertTrue(splitDoc.contains("`CaveniaDimensionResourceRequirement`"));
        assertTrue(splitDoc.contains("`CaveniaDimensionResourceRequirementContract`"));
        assertTrue(splitDoc.contains("`CaveniaAccessTravelContracts`"));
        assertTrue(splitDoc.contains("`CaveniaAccessTravelRequirement`"));
        assertTrue(splitDoc.contains("`CaveniaAccessTravelRequirementContract`"));
        assertTrue(splitDoc.contains("`CaveniaSpawnHostContracts`"));
        assertTrue(splitDoc.contains("`CaveniaSpawnHostRequirement`"));
        assertTrue(splitDoc.contains("`CaveniaSpawnHostRequirementContract`"));
        assertTrue(splitDoc.contains("`CaveniaActivationReadinessHosts`"));
        assertTrue(splitDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(splitDoc.contains("`CaveniaGeneratorHostContracts`"));
        assertTrue(splitDoc.contains("`CaveniaBiomeSourceStrategyContracts`"));
        assertTrue(splitDoc.contains("`CaveniaGeneratorRuntimeContracts`"));
        assertTrue(splitDoc.contains("`CaveniaGeneratorBridge`"));
        assertTrue(splitDoc.contains("`CaveniaSpawnProviderPolicy`"));
        assertTrue(splitDoc.contains("`CaveniaSpawnEntry`"));
        assertTrue(splitDoc.contains("`CaveniaTerrainGeneratorPolicy`"));
        assertTrue(splitDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(splitDoc.contains("`CaveniaCaveCarverPolicy`"));
        assertTrue(splitDoc.contains("`CaveniaVeinsContentPolicy`"));
        assertTrue(splitDoc.contains("`CaveniaPopulationPolicy`"));
        assertTrue(splitDoc.contains("not a `ChunkGenerator`"));
        assertTrue(splitDoc.contains("not a `BiomeSource`"));
        assertTrue(splitDoc.contains("no codec or registry entry was added"));
        assertTrue(splitDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(splitDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(splitDoc.contains("no active Cavenia runtime level"));
        assertTrue(splitDoc.contains("no active Cavenia access or teleport"));
        assertTrue(splitDoc.contains("no active Cavenia spawning"));
        assertTrue(splitDoc.contains("no active crazy spawning"));
        assertTrue(splitDoc.contains("fake normal `CAVERN` crazy spawning"));
        assertTrue(splitDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Dimension-Resource / Access-Travel / Spawn-Host Split Contracts MVP"));
        assertTrue(readme.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Dimension-Resource / Access-Travel / Spawn-Host Split Contracts MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(activationHostsDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(registrationDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(biomeRuntimeDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(bridgeDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(technicalScaffoldDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
    }

    @Test
    void splitContractsBoundaryKeepsActiveDimensionAccessAndSpawnSurfaceAbsent() throws IOException {
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
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");

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
                    ),
                "Expected the dimension/access/spawn split-contract MVP to avoid adding any active Cavenia runtime classes"
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
                "Expected the dimension/access/spawn split-contract MVP to keep active Cavenia runtime resources absent"
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
