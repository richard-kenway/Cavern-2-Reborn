package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceApiShapeInventoryBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path INVENTORY_DOC = resolveProjectFile("docs", "cavenia-runtime-biome-source-api-shape-inventory-mvp.md");
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
    private static final Path ACTIVATION_MATRIX_DOC = resolveProjectFile(
        "docs", "cavenia-activation-surface-final-inert-readiness-matrix-mvp.md"
    );
    private static final Path GENERATOR_HOST_DOC = resolveProjectFile(
        "docs", "cavenia-generator-host-biome-source-strategy-split-contracts-mvp.md"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheRuntimeBiomeSourceApiShapeInventorySlice() throws IOException {
        String readme = Files.readString(README);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);
        String inventoryDoc = Files.readString(INVENTORY_DOC);
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
        String activationMatrixDoc = Files.readString(ACTIVATION_MATRIX_DOC);
        String generatorHostDoc = Files.readString(GENERATOR_HOST_DOC);

        assertTrue(inventoryDoc.contains("Cavenia Runtime BiomeSource API Shape Inventory MVP"));
        assertTrue(inventoryDoc.contains("`CaveniaRuntimeBiomeSourceApiShapeInventory`"));
        assertTrue(inventoryDoc.contains("`CaveniaRuntimeBiomeSourceApiShapeComponent`"));
        assertTrue(inventoryDoc.contains("`CaveniaRuntimeBiomeSourceApiShapeInventoryEntry`"));
        assertTrue(inventoryDoc.contains("`CaveniaRuntimeBiomeSourceSubclassDecision`"));
        assertTrue(inventoryDoc.contains("`CaveniaRuntimeBiomeSourceSkeleton`"));
        assertTrue(inventoryDoc.contains("`CaveniaRuntimeBiomeSourceFirstImplementationDecision`"));
        assertTrue(inventoryDoc.contains("`CaveniaBiomeSourceStrategyReadinessMatrix`"));
        assertTrue(inventoryDoc.contains("`CaveniaRegistryLookupReadiness`"));
        assertTrue(inventoryDoc.contains("`CaveniaAdapterCodecRegistrationReadiness`"));
        assertTrue(inventoryDoc.contains("`CaveniaBiomeSelectionAdapterContract`"));
        assertTrue(inventoryDoc.contains("`CaveniaWeightedBiomeSelectionAlgorithm`"));
        assertTrue(inventoryDoc.contains("`CaveniaLegacyToModernBiomeKeyMappings`"));
        assertTrue(inventoryDoc.contains("`CaveniaBiomeSourceStrategyPlan`"));
        assertTrue(inventoryDoc.contains("`CaveniaFirstActiveSurfaceSelection`"));
        assertTrue(inventoryDoc.contains("`CaveniaActivationReadinessMatrix`"));
        assertTrue(inventoryDoc.contains("`CaveniaGeneratorRegistrationBoundary`"));
        assertTrue(inventoryDoc.contains("API-shape inventory layer"));
        assertTrue(inventoryDoc.contains("inspected from local dependencies"));
        assertTrue(inventoryDoc.contains("the unregistered skeleton is ready"));
        assertTrue(inventoryDoc.contains("the API shape inventory is ready"));
        assertTrue(inventoryDoc.contains("the real Minecraft `BiomeSource` subclass is still deferred"));
        assertTrue(inventoryDoc.contains("API shape decisions are now pinned as inventory/data"));
        assertTrue(inventoryDoc.contains("no real `BiomeSource` subclass is added in this slice"));
        assertTrue(inventoryDoc.contains("no runtime API usage is allowed in main source in this slice"));
        assertTrue(inventoryDoc.contains("next slice may decide whether to implement a real subclass"));
        assertTrue(inventoryDoc.contains("next slice may not implement real subclass directly without a separate explicit decision"));
        assertTrue(inventoryDoc.contains("next slice may not add codec implementation/registration"));
        assertTrue(inventoryDoc.contains("next slice may not use registry lookup access"));
        assertTrue(inventoryDoc.contains("next slice may not add dimension JSON/type JSON"));
        assertTrue(inventoryDoc.contains("next slice may not create active Cavenia level"));
        assertTrue(inventoryDoc.contains("next slice may not add worldgen resources"));
        assertTrue(inventoryDoc.contains("next slice may not add access/teleport"));
        assertTrue(inventoryDoc.contains("next slice may not add spawning"));
        assertTrue(inventoryDoc.contains("next slice may not register `cavernreborn:caveman`"));
        assertTrue(inventoryDoc.contains("not a real `BiomeSource`"));
        assertTrue(inventoryDoc.contains("not a `ChunkGenerator`"));
        assertTrue(inventoryDoc.contains("no registry lookup access was added"));
        assertTrue(inventoryDoc.contains("no biome holder/resource-key conversion implementation was added"));
        assertTrue(inventoryDoc.contains("no codec implementation was added"));
        assertTrue(inventoryDoc.contains("no codec or registry entry was added"));
        assertTrue(inventoryDoc.contains("no active `dimension/cavenia.json`"));
        assertTrue(inventoryDoc.contains("no active `dimension_type/cavenia.json`"));
        assertTrue(inventoryDoc.contains("no active Cavenia runtime level"));
        assertTrue(inventoryDoc.contains("no active Cavenia access or teleport"));
        assertTrue(inventoryDoc.contains("no active Cavenia spawning"));
        assertTrue(inventoryDoc.contains("no active Cavenia worldgen resources"));
        assertTrue(inventoryDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(inventoryDoc.contains("app-neoforge/build/neoForm/neoFormJoined1.21.1-20240808.144430"));
        assertTrue(inventoryDoc.contains("net.minecraft.world.level.biome.BiomeSource"));
        assertTrue(inventoryDoc.contains("BuiltInRegistries.BIOME_SOURCE.byNameCodec().dispatchStable"));
        assertTrue(inventoryDoc.contains("Climate$Sampler"));

        assertTrue(readme.contains("Cavenia Runtime BiomeSource API Shape Inventory MVP"));
        assertTrue(readme.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(runtimeSmokeDoc.contains("Cavenia Runtime BiomeSource API Shape Inventory MVP"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(subclassDecisionDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(skeletonDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(firstDecisionDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(matrixDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(registryLookupDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(adapterCodecDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(adapterDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(weightedSelectionDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(legacyMappingDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(planDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(selectionDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(activationMatrixDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
        assertTrue(generatorHostDoc.contains("docs/cavenia-runtime-biome-source-api-shape-inventory-mvp.md"));
    }

    @Test
    void apiShapeInventoryBoundaryKeepsActiveRuntimeSurfacesAbsent() throws IOException {
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
                "Expected the runtime-biome-source API shape inventory layer to avoid adding active Cavenia runtime classes"
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
                "Expected the runtime-biome-source API shape inventory layer to keep active Cavenia runtime resources absent"
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
