package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class CaveniaActiveFoundationTechnicalSpikeTest {
    private static final Path SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path POPULATION_DOC = resolveProjectFile("docs", "cavenia-population-lakes-falls-shroom-contract-boundary.md");
    private static final Path ACCESS_DOC = resolveProjectFile("docs", "cavenia-mirage-entry-access-contract-boundary.md");
    private static final Path SPAWN_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-policy-mvp.md");
    private static final Path CRAZY_ROSTER_DOC = resolveProjectFile("docs", "cavenia-crazy-roster-natural-spawn-boundary.md");
    private static final Path CAVEMAN_DOC = resolveProjectFile("docs", "caveman-cavenia-normal-roster-boundary.md");
    private static final Path SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-runtime-key-inactive-dimension-scaffold-mvp.md");
    private static final Path TERRAIN_POLICY_DOC = resolveProjectFile("docs", "cavenia-terrain-generator-foundation-non-runtime-mvp.md");
    private static final Path CAVE_CARVER_POLICY_DOC = resolveProjectFile("docs", "cavenia-cave-carver-policy-non-runtime-mvp.md");
    private static final Path BIOME_TOP_FILTER_POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path VEINS_POLICY_DOC = resolveProjectFile("docs", "cavenia-veins-content-policy-non-runtime-mvp.md");
    private static final Path POPULATION_POLICY_DOC = resolveProjectFile("docs", "cavenia-population-policy-non-runtime-mvp.md");
    private static final Path GENERATOR_SCAFFOLD_DOC = resolveProjectFile("docs", "cavenia-active-generator-technical-scaffold-mvp.md");
    private static final Path GENERATOR_BRIDGE_DOC = resolveProjectFile("docs", "cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md");
    private static final Path GENERATOR_REGISTRATION_DOC = resolveProjectFile("docs", "cavenia-active-generator-registration-inert-boundary-mvp.md");
    private static final Path GENERATOR_SKELETON_DOC = resolveProjectFile("docs", "cavenia-generator-biome-source-unregistered-skeleton-mvp.md");
    private static final Path GENERATOR_RUNTIME_CONTRACTS_DOC = resolveProjectFile("docs", "cavenia-generator-runtime-contracts-non-registered-mvp.md");
    private static final Path ACTIVATION_HOSTS_DOC = resolveProjectFile("docs", "cavenia-generator-activation-readiness-host-contracts-mvp.md");
    private static final Path SPLIT_CONTRACTS_DOC = resolveProjectFile("docs", "cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md");
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

    @Test
    void technicalSpikeExistsAndPinsTheFirstActiveFoundationDecision() throws IOException {
        String spikeDoc = Files.readString(SPIKE_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String terrainDoc = Files.readString(TERRAIN_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String populationDoc = Files.readString(POPULATION_DOC);
        String accessDoc = Files.readString(ACCESS_DOC);
        String spawnProviderDoc = Files.readString(SPAWN_PROVIDER_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String cavemanDoc = Files.readString(CAVEMAN_DOC);
        String scaffoldDoc = Files.readString(SCAFFOLD_DOC);
        String terrainPolicyDoc = Files.readString(TERRAIN_POLICY_DOC);
        String caveCarverPolicyDoc = Files.readString(CAVE_CARVER_POLICY_DOC);
        String biomeTopFilterPolicyDoc = Files.readString(BIOME_TOP_FILTER_POLICY_DOC);
        String veinsPolicyDoc = Files.readString(VEINS_POLICY_DOC);
        String populationPolicyDoc = Files.readString(POPULATION_POLICY_DOC);
        String generatorScaffoldDoc = Files.readString(GENERATOR_SCAFFOLD_DOC);
        String generatorBridgeDoc = Files.readString(GENERATOR_BRIDGE_DOC);
        String adapterDoc = Files.readString(ADAPTER_DOC);
        String adapterReadinessDoc = Files.readString(ADAPTER_READINESS_DOC);

        assertTrue(spikeDoc.contains("Cavenia Active Foundation Technical Spike"));
        assertTrue(spikeDoc.contains("docs/cavenia-active-foundation-readiness-plan.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-spawn-provider-policy-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));
        assertTrue(spikeDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-runtime-key-inactive-dimension-scaffold-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-terrain-generator-foundation-non-runtime-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-cave-carver-policy-non-runtime-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-biome-top-filter-policy-non-runtime-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-veins-content-policy-non-runtime-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-population-policy-non-runtime-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-active-generator-technical-scaffold-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-active-generator-runtime-prototype-inert-bridge-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-active-generator-registration-inert-boundary-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-generator-biome-source-unregistered-skeleton-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-generator-runtime-contracts-non-registered-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-generator-activation-readiness-host-contracts-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-dimension-access-spawn-split-contracts-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-worldgen-resource-host-split-contracts-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-activation-surface-final-inert-readiness-matrix-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-deliberate-first-active-surface-selection-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-biome-source-strategy-narrow-non-runtime-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-adapter-codec-registration-readiness-contracts-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-biome-source-strategy-final-readiness-matrix-mvp.md"));
        assertTrue(spikeDoc.contains("docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md"));
        assertTrue(spikeDoc.contains("`CAVENIA_LOCATION`"));
        assertTrue(spikeDoc.contains("`CAVENIA_LEVEL_KEY`"));
        assertTrue(spikeDoc.contains("`dimension/cavenia.json`"));
        assertTrue(spikeDoc.contains("`dimension_type/cavenia.json`"));
        assertTrue(spikeDoc.contains("`worldHeight = 128`"));
        assertTrue(spikeDoc.contains("Custom chunk generator"));
        assertTrue(spikeDoc.contains("Data-driven approximation"));
        assertTrue(spikeDoc.contains("hybrid custom generator plus data-driven features"));
        assertTrue(spikeDoc.contains("safe entry/access"));
        assertTrue(spikeDoc.contains("no active Cavenia spawning"));
        assertTrue(spikeDoc.contains("no active crazy spawning"));
        assertTrue(spikeDoc.contains("no fake normal `CAVERN` crazy spawning"));
        assertTrue(spikeDoc.contains("no Mirage Book"));
        assertTrue(spikeDoc.contains("no portal"));
        assertTrue(spikeDoc.contains("no player access or teleport"));
        assertTrue(spikeDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(spikeDoc.contains("`Cavenia Runtime Key / Inactive Dimension Scaffold MVP`"));

        assertTrue(readinessDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(readinessDoc.contains("`Cavenia Runtime Key / Inactive Dimension Scaffold MVP`"));
        assertTrue(adapterDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(foundationDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(terrainDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(populationDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(accessDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(spawnProviderDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(policyDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(cavemanDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(scaffoldDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(terrainPolicyDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(caveCarverPolicyDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(biomeTopFilterPolicyDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(veinsPolicyDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(populationPolicyDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(generatorScaffoldDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(generatorBridgeDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(GENERATOR_REGISTRATION_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(GENERATOR_SKELETON_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(GENERATOR_RUNTIME_CONTRACTS_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(ACTIVATION_HOSTS_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(SPLIT_CONTRACTS_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(DIMENSION_ACCESS_SPAWN_SPLIT_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(WORLDGEN_RESOURCE_SPLIT_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(MATRIX_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(REGISTRY_LOOKUP_READINESS_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(SELECTION_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(BIOME_SOURCE_PLAN_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(BIOME_MAPPING_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(WEIGHTED_SELECTION_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(adapterReadinessDoc.contains("docs/cavenia-active-foundation-technical-spike.md"));
        assertTrue(Files.readString(FINAL_SELECTED_SURFACE_MATRIX_DOC).contains("docs/cavenia-active-foundation-technical-spike.md"));
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
}
