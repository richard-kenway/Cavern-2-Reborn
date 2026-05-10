package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRegistryLookupReadinessBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path READINESS_DOC = resolveProjectFile("docs", "cavenia-registry-lookup-readiness-contracts-mvp.md");
    private static final Path ADAPTER_CODEC_DOC = resolveProjectFile("docs", "cavenia-adapter-codec-registration-readiness-contracts-mvp.md");
    private static final Path ADAPTER_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md");
    private static final Path WEIGHTED_SELECTION_DOC = resolveProjectFile("docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md");
    private static final Path INVENTORY_DOC = resolveProjectFile("docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md");
    private static final Path PLAN_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md");
    private static final Path SELECTION_DOC = resolveProjectFile("docs", "cavenia-deliberate-first-active-surface-selection-mvp.md");
    private static final Path MATRIX_DOC = resolveProjectFile("docs", "cavenia-activation-surface-final-inert-readiness-matrix-mvp.md");
    private static final Path GENERATOR_HOST_DOC = resolveProjectFile("docs", "cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-biome-top-filter-policy-non-runtime-mvp.md");
    private static final Path PROVIDER_BOUNDARY_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path TECHNICAL_SPIKE_DOC = resolveProjectFile("docs", "cavenia-active-foundation-technical-spike.md");
    private static final Path FOUNDATION_READINESS_DOC = resolveProjectFile("docs", "cavenia-active-foundation-readiness-plan.md");
    private static final Path RUNTIME_KEY_DOC = resolveProjectFile("docs", "cavenia-runtime-key-inactive-dimension-scaffold-mvp.md");
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheRegistryLookupReadinessContracts() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String readinessDoc = Files.readString(READINESS_DOC);
        String adapterCodecDoc = Files.readString(ADAPTER_CODEC_DOC);
        String adapterDoc = Files.readString(ADAPTER_DOC);
        String weightedSelectionDoc = Files.readString(WEIGHTED_SELECTION_DOC);
        String inventoryDoc = Files.readString(INVENTORY_DOC);
        String planDoc = Files.readString(PLAN_DOC);
        String selectionDoc = Files.readString(SELECTION_DOC);
        String matrixDoc = Files.readString(MATRIX_DOC);
        String generatorHostDoc = Files.readString(GENERATOR_HOST_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String providerBoundaryDoc = Files.readString(PROVIDER_BOUNDARY_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String foundationReadinessDoc = Files.readString(FOUNDATION_READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);

        assertTrue(readinessDoc.contains("Cavenia Registry Lookup Readiness Contracts MVP"));
        assertTrue(readinessDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(readinessDoc.contains("`CaveniaRegistryLookupRequirement`"));
        assertTrue(readinessDoc.contains("`CaveniaRegistryLookupRequirementContract`"));
        assertTrue(readinessDoc.contains("`BIOME_SOURCE_STRATEGY`"));
        assertTrue(readinessDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(readinessDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(readinessDoc.contains("`CaveniaBiomeSelectionAdapterQuery`"));
        assertTrue(readinessDoc.contains("`CaveniaBiomeSelectionAdapterResult`"));
        assertTrue(readinessDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(readinessDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(readinessDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(readinessDoc.contains("`CaveniaBiomeSourceStrategyContracts`"));
        assertTrue(readinessDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(readinessDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(readinessDoc.contains("readiness-only"));
        assertTrue(readinessDoc.contains("registry lookup runtime is not ready"));
        assertTrue(readinessDoc.contains("registry access source decision is not ready"));
        assertTrue(readinessDoc.contains("biome registry reference is not ready"));
        assertTrue(readinessDoc.contains("resource-key conversion is not ready"));
        assertTrue(readinessDoc.contains("holder resolution is not ready"));
        assertTrue(readinessDoc.contains("missing-biome fallback decision is not ready"));
        assertTrue(readinessDoc.contains("runtime lookup context is not ready"));
        assertTrue(readinessDoc.contains("adapter result to runtime biome conversion is not ready"));
        assertTrue(readinessDoc.contains("registry lookup access is not ready"));
        assertTrue(readinessDoc.contains("registry verification is not ready"));
        assertTrue(readinessDoc.contains("runtime `BiomeSource` is not ready"));
        assertTrue(readinessDoc.contains("codec implementation/registration is not ready"));
        assertTrue(readinessDoc.contains("activation is not allowed in this slice"));
        assertTrue(readinessDoc.contains("not a `BiomeSource`"));
        assertTrue(readinessDoc.contains("not a `ChunkGenerator`"));
        assertTrue(readinessDoc.contains("no registry lookup access was added"));
        assertTrue(readinessDoc.contains("no biome holder/resource-key conversion was added"));
        assertTrue(readinessDoc.contains("no codec implementation was added"));
        assertTrue(readinessDoc.contains("no codec or registry entry was added"));
        assertTrue(readinessDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(readinessDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(readinessDoc.contains("no active Cavenia runtime level"));
        assertTrue(readinessDoc.contains("no active Cavenia access or teleport"));
        assertTrue(readinessDoc.contains("no active Cavenia spawning"));
        assertTrue(readinessDoc.contains("no active Cavenia worldgen resources"));
        assertTrue(readinessDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(readinessDoc.contains("docs/cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md"));
        assertTrue(readinessDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));

        assertTrue(readme.contains("Cavenia Registry Lookup Readiness Contracts MVP"));
        assertTrue(readme.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Registry Lookup Readiness Contracts MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(adapterCodecDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(adapterDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(inventoryDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(selectionDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(policyDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(providerBoundaryDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(foundationReadinessDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-registry-lookup-readiness-contracts-mvp.md"));
    }

    @Test
    void registryLookupBoundaryKeepsActiveRuntimeSurfacesAbsent() throws IOException {
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");

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
                            || name.equals("CaveniaServerTickSpawner.java")
                    ),
                "Expected the registry-lookup readiness contracts to avoid adding active Cavenia runtime classes"
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
                            || path.contains("/tags/worldgen/biome/cavenia")
                    ),
                "Expected the registry-lookup readiness contracts to keep active Cavenia runtime resources absent"
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
