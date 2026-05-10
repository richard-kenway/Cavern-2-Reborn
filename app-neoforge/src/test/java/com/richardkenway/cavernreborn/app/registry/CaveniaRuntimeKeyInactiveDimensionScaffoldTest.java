package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class CaveniaRuntimeKeyInactiveDimensionScaffoldTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-runtime-key-inactive-dimension-scaffold-mvp.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path TERRAIN_POLICY_DOC = resolveProjectFile("docs", "cavenia-terrain-generator-foundation-non-runtime-mvp.md");
    private static final Path GENERATOR_SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-active-generator-technical-scaffold-mvp.md");
    private static final Path GENERATOR_BRIDGE_DOC = resolveProjectFile("docs", "cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md");
    private static final Path GENERATOR_REGISTRATION_DOC = resolveProjectFile("docs", "cavenia-active-generator-registration-inert-boundary-mvp.md");
    private static final Path GENERATOR_SKELETON_DOC = resolveProjectFile("docs", "cavenia-generator-biome-source-unregistered-skeleton-mvp.md");
    private static final Path GENERATOR_RUNTIME_CONTRACTS_DOC = resolveProjectFile("docs", "cavenia-generator-runtime-contracts-non-registered-mvp.md");
    private static final Path ACTIVATION_HOSTS_DOC = resolveProjectFile("docs", "cavenia-generator-activation-readiness-host-contracts-mvp.md");
    private static final Path DIMENSION_ACCESS_SPAWN_SPLIT_DOC = resolveProjectFile("docs", "cavenia-dimension-access-spawn-split-contracts-mvp.md");
    private static final Path WORLDGEN_RESOURCE_SPLIT_DOC = resolveProjectFile("docs", "cavenia-worldgen-resource-host-split-contracts-mvp.md");
    private static final Path MATRIX_DOC = resolveProjectFile("docs", "cavenia-activation-surface-final-inert-readiness-matrix-mvp.md");
    private static final Path SELECTION_DOC = resolveProjectFile("docs", "cavenia-deliberate-first-active-surface-selection-mvp.md");
    private static final Path BIOME_SOURCE_PLAN_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md");
    private static final Path BIOME_MAPPING_DOC = resolveProjectFile("docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md");
    private static final Path WEIGHTED_SELECTION_DOC = resolveProjectFile("docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md");
    private static final Path ADAPTER_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md");
    private static final Path ADAPTER_READINESS_DOC = resolveProjectFile("docs", "cavenia-adapter-codec-registration-readiness-contracts-mvp.md");
    private static final Path REGISTRY_LOOKUP_READINESS_DOC = resolveProjectFile("docs", "cavenia-registry-lookup-readiness-contracts-mvp.md");
    private static final Path FINAL_SELECTED_SURFACE_MATRIX_DOC = resolveProjectFile(
        "docs", "cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"
    );
    private static final Path RUNTIME_BIOME_SOURCE_DECISION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md"
    );
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path CORE_DIMENSIONS_SOURCE = resolveProjectFile(
        "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "state", "CavernDimensions.java"
    );
    private static final Path NEOFORGE_DIMENSIONS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CavernNeoForgeDimensions.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheInertCaveniaIdentityScaffold() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String scaffoldDoc = Files.readString(SCAFFOLD_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String adapterDoc = Files.readString(ADAPTER_DOC);
        String terrainPolicyDoc = Files.readString(TERRAIN_POLICY_DOC);
        String generatorScaffoldDoc = Files.readString(GENERATOR_SCAFFOLD_DOC);
        String generatorBridgeDoc = Files.readString(GENERATOR_BRIDGE_DOC);
        String generatorRegistrationDoc = Files.readString(GENERATOR_REGISTRATION_DOC);
        String activationHostsDoc = Files.readString(ACTIVATION_HOSTS_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);

        assertTrue(scaffoldDoc.contains("Cavenia Runtime Key / Inactive Dimension Scaffold MVP"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-spawn-provider-policy-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(scaffoldDoc.contains("`CavernDimensions.CAVENIA_DIMENSION_ID = \"cavernreborn:cavenia\"`"));
        assertTrue(scaffoldDoc.contains("`CavernNeoForgeDimensions.CAVENIA_LOCATION`"));
        assertTrue(scaffoldDoc.contains("`CavernNeoForgeDimensions.CAVENIA_LEVEL_KEY`"));
        assertTrue(scaffoldDoc.contains("Legacy `dimensionId = -54` remains historical identity only."));
        assertTrue(scaffoldDoc.contains("It does not create an active runtime `ServerLevel`."));
        assertTrue(scaffoldDoc.contains("no checked-in dimension JSON exists"));
        assertTrue(scaffoldDoc.contains("no checked-in dimension-type JSON exists"));
        assertTrue(scaffoldDoc.contains("no active Cavenia spawning"));
        assertTrue(scaffoldDoc.contains("no active crazy spawning"));
        assertTrue(scaffoldDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(scaffoldDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(scaffoldDoc.contains("nearby `ICavenicMob` whose `isNonBoss()` returns `false`"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-active-generator-registration-inert-boundary-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-generator-runtime-contracts-non-registered-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-generator-activation-readiness-host-contracts-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-worldgen-resource-host-split-contracts-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md"));

        assertTrue(readme.contains("Cavenia Runtime Key / Inactive Dimension Scaffold MVP"));
        assertTrue(readme.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Runtime Key / Inactive Dimension Scaffold MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("inert Cavenia runtime-key / inactive-dimension scaffold MVP"));
        assertTrue(readinessDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(terrainPolicyDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(generatorScaffoldDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(generatorBridgeDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(generatorRegistrationDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(GENERATOR_SKELETON_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(GENERATOR_RUNTIME_CONTRACTS_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(activationHostsDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(DIMENSION_ACCESS_SPAWN_SPLIT_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(adapterDoc.contains("dimension JSON/type JSON are absent"));
        assertTrue(Files.readString(WORLDGEN_RESOURCE_SPLIT_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(MATRIX_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(SELECTION_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(BIOME_SOURCE_PLAN_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(BIOME_MAPPING_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(WEIGHTED_SELECTION_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(ADAPTER_READINESS_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(REGISTRY_LOOKUP_READINESS_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(FINAL_SELECTED_SURFACE_MATRIX_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(Files.readString(RUNTIME_BIOME_SOURCE_DECISION_DOC).contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(foundationDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
    }

    @Test
    void codeAddsOnlyInertIdentityConstantsAndKeepsRuntimeResourcesAbsent() throws IOException {
        String coreDimensionsSource = Files.readString(CORE_DIMENSIONS_SOURCE);
        String neoForgeDimensionsSource = Files.readString(NEOFORGE_DIMENSIONS_SOURCE);

        assertEquals("cavernreborn:cavenia", CavernDimensions.CAVENIA_DIMENSION_ID);
        assertEquals("cavernreborn:cavenia", CavernNeoForgeDimensions.CAVENIA_LOCATION.toString());
        assertEquals("cavernreborn:cavenia", CavernNeoForgeDimensions.CAVENIA_LEVEL_KEY.location().toString());
        assertTrue(coreDimensionsSource.contains("CAVENIA_DIMENSION_ID"));
        assertTrue(neoForgeDimensionsSource.contains("CAVENIA_LOCATION"));
        assertTrue(neoForgeDimensionsSource.contains("CAVENIA_LEVEL_KEY"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "noise_settings", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_zombie_spawns.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_zombie.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnCallback.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "Caveman.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavemanEntity.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name -> name.equals("CaveniaSpawnProvider.java") || name.equals("CaveniaSpawnCallback.java") || name.contains("Caveman")),
                "Expected the inert scaffold to avoid adding active Cavenia spawn-provider runtime classes or a Caveman entity"
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
                            || path.contains("/worldgen/biome/cavenia")
                            || path.contains("/worldgen/noise_settings/cavenia")
                            || path.contains("/worldgen/configured_feature/cavenia")
                            || path.contains("/worldgen/placed_feature/cavenia")
                            || path.contains("/neoforge/biome_modifier/crazy_")
                            || path.contains("/tags/worldgen/biome/spawns_crazy_")
                    ),
                "Expected the inert scaffold to keep Cavenia runtime resources and fake normal CAVERN crazy-spawn resources absent"
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
