package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.worldgen.CaveniaCaveCarverPolicy;
import com.richardkenway.cavernreborn.core.worldgen.CaveniaCaveCarverReplacement;

class CaveniaCaveCarverPolicyBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-cave-carver-policy-non-runtime-mvp.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path TERRAIN_POLICY_DOC = resolveProjectFile("docs", "cavenia-terrain-generator-foundation-non-runtime-mvp.md");
    private static final Path BIOME_POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path TERRAIN_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void docsPinTheNonRuntimeCaveCarverPolicy() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String terrainPolicyDoc = Files.readString(TERRAIN_POLICY_DOC);
        String biomePolicyDoc = Files.readString(BIOME_POLICY_DOC);
        String terrainBoundaryDoc = Files.readString(TERRAIN_BOUNDARY_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);

        assertTrue(policyDoc.contains("Cavenia Cave Carver Policy / Non-Runtime MVP"));
        assertTrue(policyDoc.contains("`CaveniaCaveCarverPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaCaveCarverReplacement`"));
        assertTrue(policyDoc.contains("`MapGenCaveniaCaves`"));
        assertTrue(policyDoc.contains("`MapGenCavernCaves`"));
        assertTrue(policyDoc.contains("`CaveniaTerrainGeneratorPolicy`"));
        assertTrue(policyDoc.contains("`CaveniaBiomeTopFilterPolicy`"));
        assertTrue(policyDoc.contains("`DEFAULT_GENERATE_CAVES = true`"));
        assertTrue(policyDoc.contains("tunnel-origin range `20..24`"));
        assertTrue(policyDoc.contains("`y <= 2`"));
        assertTrue(policyDoc.contains("`y - 1 < 10`"));
        assertTrue(policyDoc.contains("`minecraft:gravel`"));
        assertTrue(policyDoc.contains("`minecraft:water`"));
        assertTrue(policyDoc.contains("`minecraft:air`"));
        assertTrue(policyDoc.contains("cave carving happens before biome top/filter replacement"));
        assertTrue(policyDoc.contains("cave carving happens before VEINS mutation"));
        assertTrue(policyDoc.contains("no `dimension/cavenia.json`"));
        assertTrue(policyDoc.contains("no `dimension_type/cavenia.json`"));
        assertTrue(policyDoc.contains("no active Cavenia configured carver resource"));
        assertTrue(policyDoc.contains("no active Cavenia runtime level"));
        assertTrue(policyDoc.contains("no active Cavenia spawning"));
        assertTrue(policyDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(policyDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("Cavenia Cave Carver Policy / Non-Runtime MVP"));
        assertTrue(readme.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Cave Carver Policy / Non-Runtime MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("non-runtime Cavenia cave-carver policy MVP"));
        assertTrue(contractDoc.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
        assertTrue(terrainPolicyDoc.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
        assertTrue(biomePolicyDoc.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
        assertTrue(terrainBoundaryDoc.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
    }

    @Test
    void purePolicyExistsWithoutActivatingAnyRuntimeCarverOrGeneratorResources() throws IOException {
        assertTrue(CaveniaCaveCarverPolicy.DEFAULT_GENERATE_CAVES);
        assertEquals(20, CaveniaCaveCarverPolicy.minTunnelOriginY());
        assertEquals(24, CaveniaCaveCarverPolicy.maxTunnelOriginY());
        assertEquals(CaveniaCaveCarverReplacement.GRAVEL, CaveniaCaveCarverPolicy.replacementForY(2));
        assertEquals(CaveniaCaveCarverReplacement.WATER, CaveniaCaveCarverPolicy.replacementForY(10));
        assertEquals(CaveniaCaveCarverReplacement.AIR, CaveniaCaveCarverPolicy.replacementForY(11));
        assertTrue(CaveniaCaveCarverPolicy.runsBeforeBiomeTopFilterReplacement());
        assertTrue(CaveniaCaveCarverPolicy.runsBeforeVeinsMutation());

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "noise_settings", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "Caveman.java");

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path ->
                        path.contains("/dimension/cavenia.json")
                            || path.contains("/dimension_type/cavenia.json")
                            || path.contains("/worldgen/configured_carver/cavenia")
                            || path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/biome/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/crazy_")
                            || path.contains("/tags/worldgen/biome/spawns_crazy_")
                    ),
                "Expected the non-runtime cave-carver policy to keep active Cavenia carver/worldgen resources and fake normal CAVERN crazy-spawn resources absent"
            );
        }

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.contains("CaveniaChunkGenerator")
                            || name.contains("CaveniaBiomeSource")
                            || name.contains("MapGenCaveniaCaves")
                            || name.equals("Caveman.java")
                    ),
                "Expected the non-runtime cave-carver policy to avoid adding active Cavenia carver, generator, biome-source or Caveman runtime classes"
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
