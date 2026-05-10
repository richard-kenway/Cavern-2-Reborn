package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecisionBoundaryTest {
    private static final String BROKEN_DOCUMENTED_IN_FRAGMENT = "documented in .";
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path GO_NO_GO_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"
    );
    private static final Path API_INVENTORY_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-api-shape-inventory-mvp.md"
    );
    private static final Path SUBCLASS_DECISION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-codec-holder-registry-decision-mvp.md"
    );
    private static final Path SKELETON_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-skeleton-mvp.md");
    private static final Path FIRST_DECISION_DOC = resolveProjectFile(
        "docs", "cavenia-runtime-biome-source-first-implementation-decision-guardrails-mvp.md"
    );
    private static final Path MATRIX_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-final-readiness-matrix-mvp.md");
    private static final Path REGISTRY_LOOKUP_DOC = resolveProjectFile("docs", "cavenia-registry-lookup-readiness-contracts-mvp.md");
    private static final Path ADAPTER_CODEC_DOC = resolveProjectFile("docs", "cavenia-adapter-codec-registration-readiness-contracts-mvp.md");
    private static final Path ADAPTER_DOC = resolveProjectFile("docs", "cavenia-unregistered-runtime-biome-source-shape-adapter-contract-mvp.md");
    private static final Path WEIGHTED_SELECTION_DOC = resolveProjectFile("docs", "cavenia-weighted-biome-selection-algorithm-pure-non-runtime-mvp.md");
    private static final Path LEGACY_MAPPING_DOC = resolveProjectFile("docs", "cavenia-legacy-to-modern-biome-key-mapping-inventory-mvp.md");
    private static final Path PLAN_DOC = resolveProjectFile("docs", "cavenia-biome-source-strategy-narrow-non-runtime-mvp.md");
    private static final Path SELECTION_DOC = resolveProjectFile("docs", "cavenia-deliberate-first-active-surface-selection-mvp.md");
    private static final Path GLOBAL_MATRIX_DOC = resolveProjectFile(
        "docs", "cavenia-activation-surface-final-inert-readiness-matrix-mvp.md"
    );
    private static final Path GENERATOR_HOST_DOC = resolveProjectFile(
        "docs", "cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md"
    );
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
    void docsPinTheRealSubclassGoNoGoDecision() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String goNoGoDoc = Files.readString(GO_NO_GO_DOC);
        String apiInventoryDoc = Files.readString(API_INVENTORY_DOC);
        String subclassDecisionDoc = Files.readString(SUBCLASS_DECISION_DOC);
        String skeletonDoc = Files.readString(SKELETON_DOC);
        String firstDecisionDoc = Files.readString(FIRST_DECISION_DOC);
        String matrixDoc = Files.readString(MATRIX_DOC);
        String registryLookupDoc = Files.readString(REGISTRY_LOOKUP_DOC);
        String adapterCodecDoc = Files.readString(ADAPTER_CODEC_DOC);
        String adapterDoc = Files.readString(ADAPTER_DOC);
        String weightedSelectionDoc = Files.readString(WEIGHTED_SELECTION_DOC);
        String legacyMappingDoc = Files.readString(LEGACY_MAPPING_DOC);
        String planDoc = Files.readString(PLAN_DOC);
        String selectionDoc = Files.readString(SELECTION_DOC);
        String globalMatrixDoc = Files.readString(GLOBAL_MATRIX_DOC);
        String generatorHostDoc = Files.readString(GENERATOR_HOST_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String providerBoundaryDoc = Files.readString(PROVIDER_BOUNDARY_DOC);
        String technicalSpikeDoc = Files.readString(TECHNICAL_SPIKE_DOC);
        String foundationReadinessDoc = Files.readString(FOUNDATION_READINESS_DOC);
        String runtimeKeyDoc = Files.readString(RUNTIME_KEY_DOC);

        assertTrue(goNoGoDoc.contains("Cavenia Runtime BiomeSource Real Subclass Go/No-Go Decision MVP"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSourceRealSubclassNextStepDecision`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSourceRealSubclassGuardrail`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSourceRealSubclassGuardrailContract`"));
        assertTrue(goNoGoDoc.contains("`PROCEED_WITH_GUARDED_UNREGISTERED_REAL_BIOME_SOURCE_SUBCLASS_NEXT`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSource`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSource.java`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSourceApiShapeInventory`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSourceSubclassDecision`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSourceSkeleton`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRuntimeBiomeSourceFirstImplementationDecision`"));
        assertTrue(goNoGoDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrix`"));
        assertTrue(goNoGoDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(goNoGoDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(goNoGoDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(goNoGoDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(goNoGoDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(goNoGoDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(goNoGoDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(goNoGoDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(goNoGoDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(goNoGoDoc.contains("go/no-go decision layer"));
        assertTrue(goNoGoDoc.contains("API shape inventory is ready"));
        assertTrue(goNoGoDoc.contains("unregistered skeleton is ready"));
        assertTrue(goNoGoDoc.contains("this current slice does not create the real subclass"));
        assertTrue(goNoGoDoc.contains("this current slice does not use runtime API types"));
        assertTrue(goNoGoDoc.contains("next slice may add exactly one designated real subclass file"));
        assertTrue(goNoGoDoc.contains("next slice may extend Minecraft `BiomeSource` only in that designated file"));
        assertTrue(goNoGoDoc.contains("next slice may use abstract method signature types only"));
        assertTrue(goNoGoDoc.contains("next slice may use unsupported/stub method bodies only"));
        assertTrue(goNoGoDoc.contains("next slice may not add usable codec implementation"));
        assertTrue(goNoGoDoc.contains("next slice may not register codec"));
        assertTrue(goNoGoDoc.contains("next slice may not register biome-source type"));
        assertTrue(goNoGoDoc.contains("next slice may not use registry lookup access"));
        assertTrue(goNoGoDoc.contains("next slice may not implement holder/resource-key conversion"));
        assertTrue(goNoGoDoc.contains("next slice may not add dimension JSON/type JSON"));
        assertTrue(goNoGoDoc.contains("next slice may not create active Cavenia level"));
        assertTrue(goNoGoDoc.contains("next slice may not add worldgen resources"));
        assertTrue(goNoGoDoc.contains("next slice may not add access/teleport"));
        assertTrue(goNoGoDoc.contains("next slice may not add spawning"));
        assertTrue(goNoGoDoc.contains("next slice may not register `cavernreborn:caveman`"));
        assertTrue(goNoGoDoc.contains("this current slice is still not a real `BiomeSource`"));
        assertTrue(goNoGoDoc.contains("this current slice is still not a `ChunkGenerator`"));
        assertTrue(goNoGoDoc.contains("no registry lookup access was added"));
        assertTrue(goNoGoDoc.contains("no biome holder/resource-key conversion implementation was added"));
        assertTrue(goNoGoDoc.contains("no codec implementation was added"));
        assertTrue(goNoGoDoc.contains("no codec/registry entry was added"));
        assertTrue(goNoGoDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(goNoGoDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(goNoGoDoc.contains("no active Cavenia runtime level"));
        assertTrue(goNoGoDoc.contains("no active Cavenia access or teleport"));
        assertTrue(goNoGoDoc.contains("no active Cavenia spawning"));
        assertTrue(goNoGoDoc.contains("no active Cavenia worldgen resources"));
        assertTrue(goNoGoDoc.contains("`EntityCaveman -> deferred:caveman`"));

        assertTrue(readme.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(apiInventoryDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(subclassDecisionDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(skeletonDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(firstDecisionDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(registryLookupDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(adapterCodecDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(adapterDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(legacyMappingDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(selectionDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(globalMatrixDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(policyDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(providerBoundaryDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(technicalSpikeDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(foundationReadinessDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));
        assertTrue(runtimeKeyDoc.contains("docs/cavenia-runtime-biome-source-real-subclass-go-no-go-decision-mvp.md"));

        assertFalse(goNoGoDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(apiInventoryDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(subclassDecisionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(skeletonDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(firstDecisionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(matrixDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(registryLookupDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(adapterCodecDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(adapterDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(weightedSelectionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(legacyMappingDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(planDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(selectionDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(globalMatrixDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(generatorHostDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(policyDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(providerBoundaryDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(technicalSpikeDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(foundationReadinessDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
        assertFalse(runtimeKeyDoc.contains(BROKEN_DOCUMENTED_IN_FRAGMENT));
    }

    @Test
    void goNoGoDecisionBoundaryKeepsActiveRuntimeSurfacesAbsent() throws IOException {
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaRuntimeBiomeSource.java");
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
                        name.equals("CaveniaRuntimeBiomeSource.java")
                            || name.equals("CaveniaChunkGenerator.java")
                            || name.equals("ChunkGeneratorCavenia.java")
                            || name.equals("CaveniaBiomeSource.java")
                            || name.equals("MapGenCaveniaCaves.java")
                            || name.equals("CaveniaTeleporter.java")
                            || name.equals("CaveniaSpawnProvider.java")
                            || name.equals("CaveniaSpawnHandler.java")
                            || name.equals("CaveniaServerTickSpawner.java")
                    ),
                "Expected the real-subclass go/no-go decision layer to keep active Cavenia runtime classes absent"
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
                "Expected the real-subclass go/no-go decision layer to keep active Cavenia runtime resources absent"
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
