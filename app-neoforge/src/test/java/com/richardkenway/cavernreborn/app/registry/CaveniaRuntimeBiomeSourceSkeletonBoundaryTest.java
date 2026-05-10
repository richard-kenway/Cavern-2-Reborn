package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceSkeletonBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path SKELETON_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-skeleton-mvp.md");
    private static final Path DECISION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md"
    );
    private static final Path MATRIX_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-final-readiness-matrix-mvp.md");
    private static final Path REGISTRY_LOOKUP_DOC = resolveProjectFile("docs", "cavenia-registry-lookup-readiness-contracts-mvp.md");
    private static final Path ADAPTER_CODEC_DOC = resolveProjectFile("docs", "cavenia-adapter-codec-registration-readiness-contracts-mvp.md");
    private static final Path ADAPTER_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md");
    private static final Path WEIGHTED_SELECTION_DOC = resolveProjectFile("docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md");
    private static final Path INVENTORY_DOC = resolveProjectFile("docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md");
    private static final Path PLAN_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md");
    private static final Path SELECTION_DOC = resolveProjectFile("docs", "cavenia-deliberate-first-active-surface-selection-mvp.md");
    private static final Path GLOBAL_MATRIX_DOC = resolveProjectFile("docs", "cavenia-activation-surface-final-inert-readiness-matrix-mvp.md");
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
    void docsPinTheUnregisteredRuntimeBiomeSourceSkeletonSlice() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String skeletonDoc = Files.readString(SKELETON_DOC);
        String decisionDoc = Files.readString(DECISION_DOC);
        String matrixDoc = Files.readString(MATRIX_DOC);
        String registryLookupDoc = Files.readString(REGISTRY_LOOKUP_DOC);
        String adapterCodecDoc = Files.readString(ADAPTER_CODEC_DOC);
        String adapterDoc = Files.readString(ADAPTER_DOC);
        String weightedSelectionDoc = Files.readString(WEIGHTED_SELECTION_DOC);
        String inventoryDoc = Files.readString(INVENTORY_DOC);
        String planDoc = Files.readString(PLAN_DOC);
        String selectionDoc = Files.readString(SELECTION_DOC);
        String globalMatrixDoc = Files.readString(GLOBAL_MATRIX_DOC);
        String generatorHostDoc = Files.readString(GENERATOR_HOST_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String providerBoundaryDoc = Files.readString(PROVIDER_BOUNDARY_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String foundationReadinessDoc = Files.readString(FOUNDATION_READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);

        assertTrue(skeletonDoc.contains("Cavenia Unregistered Runtime BiomeSource Skeleton MVP"));
        assertTrue(skeletonDoc.contains("`CaveniaRuntimeBiomeSourceSkeleton`"));
        assertTrue(skeletonDoc.contains("`CaveniaRuntimeBiomeSourceSkeletonQuery`"));
        assertTrue(skeletonDoc.contains("`CaveniaRuntimeBiomeSourceSkeletonResult`"));
        assertTrue(skeletonDoc.contains("`CaveniaRuntimeBiomeSourceFirstImplementationDecision`"));
        assertTrue(skeletonDoc.contains("`CaveniaRuntimeBiomeSourceGuardrail`"));
        assertTrue(skeletonDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrix`"));
        assertTrue(skeletonDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(skeletonDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(skeletonDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(skeletonDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(skeletonDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(skeletonDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(skeletonDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(skeletonDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(skeletonDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(skeletonDoc.contains("unregistered runtime-biome-source skeleton"));
        assertTrue(skeletonDoc.contains("not a real Minecraft `BiomeSource` subclass"));
        assertTrue(skeletonDoc.contains("skeleton shape is ready"));
        assertTrue(skeletonDoc.contains("unregistered skeleton is ready"));
        assertTrue(skeletonDoc.contains("every guardrail remains enforced"));
        assertTrue(skeletonDoc.contains("runtime `BiomeSource` is still not implemented"));
        assertTrue(skeletonDoc.contains("runtime biome-source registration is still absent"));
        assertTrue(skeletonDoc.contains("codec implementation/registration is still absent"));
        assertTrue(skeletonDoc.contains("registry lookup access is still absent"));
        assertTrue(skeletonDoc.contains("biome holder/resource-key conversion is still absent"));
        assertTrue(skeletonDoc.contains("dimension JSON/type JSON are absent"));
        assertTrue(skeletonDoc.contains("active Cavenia level is absent"));
        assertTrue(skeletonDoc.contains("active access/teleport is absent"));
        assertTrue(skeletonDoc.contains("active spawning is absent"));
        assertTrue(skeletonDoc.contains("active Cavenia worldgen resources are absent"));
        assertTrue(skeletonDoc.contains("`cavernreborn:caveman` is absent"));
        assertTrue(skeletonDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(skeletonDoc.contains("docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md"));

        assertTrue(readme.contains("Cavenia Unregistered Runtime BiomeSource Skeleton MVP"));
        assertTrue(readme.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Unregistered Runtime BiomeSource Skeleton MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(decisionDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(registryLookupDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(adapterCodecDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(adapterDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(inventoryDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(selectionDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(globalMatrixDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(policyDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(providerBoundaryDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(foundationReadinessDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-unregistered-runtime-biome-source-skeleton-mvp.md"));
        assertTrue(readme.contains("docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md"));
    }

    @Test
    void skeletonBoundaryKeepsActiveRuntimeSurfacesAbsent() throws IOException {
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
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
                "Expected the runtime-biome-source skeleton layer to avoid adding active Cavenia runtime classes"
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
                "Expected the runtime-biome-source skeleton layer to keep active Cavenia runtime resources absent"
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
