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

import com.richardkenway.cavernreborn.core.worldgen.CaveniaGeneratorScaffold;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaGeneratorScaffoldStage;

class CaveniaActiveGeneratorTechnicalScaffoldBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-active-generator-technical-scaffold-mvp.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path RUNTIME_KEY_DOC = resolveProjectFile("docs", "cavenia-runtime-key-inactive-dimension-scaffold-mvp.md");
    private static final Path TERRAIN_POLICY_DOC = resolveProjectFile("docs", "cavenia-terrain-generator-foundation-non-runtime-mvp.md");
    private static final Path BIOME_POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path CAVE_CARVER_POLICY_DOC = resolveProjectFile("docs", "cavenia-cave-carver-policy-non-runtime-mvp.md");
    private static final Path VEINS_POLICY_DOC = resolveProjectFile("docs", "cavenia-veins-content-policy-non-runtime-mvp.md");
    private static final Path POPULATION_POLICY_DOC = resolveProjectFile("docs", "cavenia-population-policy-non-runtime-mvp.md");
    private static final Path TERRAIN_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path GENERATOR_BRIDGE_DOC = resolveProjectFile("docs", "cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md");
    private static final Path GENERATOR_REGISTRATION_DOC = resolveProjectFile("docs", "cavenia-active-generator-registration-inert-boundary-mvp.md");
    private static final Path GENERATOR_SKELETON_DOC = resolveProjectFile("docs", "cavenia-generator-biome-source-unregistered-skeleton-mvp.md");
    private static final Path GENERATOR_RUNTIME_CONTRACTS_DOC = resolveProjectFile("docs", "cavenia-generator-runtime-contracts-non-registered-mvp.md");
    private static final Path ACTIVATION_HOSTS_DOC = resolveProjectFile("docs", "cavenia-generator-activation-readiness-host-contracts-mvp.md");
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheNonRegisteredActiveGeneratorScaffold() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String scaffoldDoc = Files.readString(SCAFFOLD_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);
        String terrainPolicyDoc = Files.readString(TERRAIN_POLICY_DOC);
        String biomePolicyDoc = Files.readString(BIOME_POLICY_DOC);
        String caveCarverPolicyDoc = Files.readString(CAVE_CARVER_POLICY_DOC);
        String veinsPolicyDoc = Files.readString(VEINS_POLICY_DOC);
        String populationPolicyDoc = Files.readString(POPULATION_POLICY_DOC);
        String terrainBoundaryDoc = Files.readString(TERRAIN_BOUNDARY_DOC);
        String generatorBridgeDoc = Files.readString(GENERATOR_BRIDGE_DOC);
        String generatorRegistrationDoc = Files.readString(GENERATOR_REGISTRATION_DOC);
        String generatorSkeletonDoc = Files.readString(GENERATOR_SKELETON_DOC);
        String generatorRuntimeContractsDoc = Files.readString(GENERATOR_RUNTIME_CONTRACTS_DOC);
        String activationHostsDoc = Files.readString(ACTIVATION_HOSTS_DOC);

        assertTrue(scaffoldDoc.contains("Cavenia Active Generator Technical Scaffold / MVP"));
        assertTrue(scaffoldDoc.contains("`CaveniaGeneratorScaffold`"));
        assertTrue(scaffoldDoc.contains("`CaveniaGeneratorScaffoldStage`"));
        assertTrue(scaffoldDoc.contains("`CaveniaTerrainGeneratorPolicy`"));
        assertTrue(scaffoldDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(scaffoldDoc.contains("`CaveniaCaveCarverPolicy`"));
        assertTrue(scaffoldDoc.contains("`CaveniaVeinsContentPolicy`"));
        assertTrue(scaffoldDoc.contains("`CaveniaPopulationPolicy`"));
        assertTrue(scaffoldDoc.contains("`CaveniaSpawnProviderPolicy`"));
        assertTrue(scaffoldDoc.contains("not a `ChunkGenerator`"));
        assertTrue(scaffoldDoc.contains("not a `BiomeSource`"));
        assertTrue(scaffoldDoc.contains("does not add a codec, registry entry"));
        assertTrue(scaffoldDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(scaffoldDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(scaffoldDoc.contains("no active Cavenia runtime level"));
        assertTrue(scaffoldDoc.contains("no active Cavenia spawning"));
        assertTrue(scaffoldDoc.contains("no active crazy spawning"));
        assertTrue(scaffoldDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(scaffoldDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-active-generator-registration-inert-boundary-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-generator-runtime-contracts-non-registered-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-generator-activation-readiness-host-contracts-mvp.md"));

        assertTrue(readme.contains("Cavenia Active Generator Technical Scaffold / MVP"));
        assertTrue(readme.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Active Generator Technical Scaffold / MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("non-registered Cavenia active-generator technical scaffold MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-active-generator-registration-inert-boundary-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(terrainPolicyDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(biomePolicyDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(caveCarverPolicyDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(veinsPolicyDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(populationPolicyDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(terrainBoundaryDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(generatorBridgeDoc.contains("`CaveniaGeneratorScaffold`"));
        assertTrue(generatorRegistrationDoc.contains("`CaveniaGeneratorScaffold`"));
        assertTrue(generatorSkeletonDoc.contains("`CaveniaGeneratorScaffold`"));
        assertTrue(generatorRuntimeContractsDoc.contains("`CaveniaGeneratorScaffold`"));
        assertTrue(activationHostsDoc.contains("`CaveniaGeneratorScaffold`"));
    }

    @Test
    void scaffoldConsumesOnlyPurePolicyValuesAndKeepsRuntimeInfrastructureAbsent() throws IOException {
        assertEquals("cavernreborn:cavenia", CaveniaGeneratorScaffold.dimensionId());
        assertEquals(128, CaveniaGeneratorScaffold.worldHeight());
        assertEquals(14, CaveniaGeneratorScaffold.biomeEntryCount());
        assertEquals(675, CaveniaGeneratorScaffold.biomeTotalWeight());
        assertEquals(13, CaveniaGeneratorScaffold.veinEntryCount());
        assertEquals(436, CaveniaGeneratorScaffold.veinTotalWeight());
        assertTrue(CaveniaGeneratorScaffold.generateLakesDefaultEnabled());
        assertTrue(CaveniaGeneratorScaffold.populationRunsAfterVeinsMutation());
        assertTrue(CaveniaGeneratorScaffold.caveCarvingRunsBeforeBiomeTopFilterReplacement());
        assertFalse(CaveniaGeneratorScaffold.isRuntimeGeneratorRegistered());
        assertTrue(CaveniaGeneratorScaffold.requiresDimensionJsonBeforeActivation());
        assertTrue(CaveniaGeneratorScaffold.requiresDimensionTypeJsonBeforeActivation());
        assertEquals(
            List.of(
                CaveniaGeneratorScaffoldStage.IDENTITY,
                CaveniaGeneratorScaffoldStage.BASE_TERRAIN,
                CaveniaGeneratorScaffoldStage.CAVE_CARVING,
                CaveniaGeneratorScaffoldStage.BIOME_TOP_FILTER_REPLACEMENT,
                CaveniaGeneratorScaffoldStage.VEINS_MUTATION,
                CaveniaGeneratorScaffoldStage.FINAL_CHUNK_CONSTRUCTION,
                CaveniaGeneratorScaffoldStage.POPULATION_INTEGRATION,
                CaveniaGeneratorScaffoldStage.SPAWN_PROVIDER_INTEGRATION_DEFERRED,
                CaveniaGeneratorScaffoldStage.ENTRY_ACCESS_DEFERRED
            ),
            CaveniaGeneratorScaffold.stages()
        );

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "Caveman.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.equals("CaveniaChunkGenerator.java")
                            || name.equals("ChunkGeneratorCavenia.java")
                            || name.equals("CaveniaBiomeSource.java")
                            || name.equals("Caveman.java")
                    ),
                "Expected the active-generator scaffold MVP to avoid adding any active Cavenia generator, biome-source or Caveman runtime classes"
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
                "Expected the active-generator scaffold MVP to keep all active Cavenia generator/worldgen resources absent"
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
