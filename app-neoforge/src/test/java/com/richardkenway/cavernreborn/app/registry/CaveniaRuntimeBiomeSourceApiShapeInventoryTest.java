package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceApiShapeComponent;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceApiShapeInventory;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceApiShapeInventoryEntry;
import com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSourceSubclassDecision;

class CaveniaRuntimeBiomeSourceApiShapeInventoryTest {
    private static final Path COMPONENT_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceApiShapeComponent.java"
    );
    private static final Path ENTRY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceApiShapeInventoryEntry.java"
    );
    private static final Path INVENTORY_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSourceApiShapeInventory.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );

    @Test
    void runtimeBiomeSourceApiShapeInventoryPinsTheLocallyObservedBiomeSourceShape() {
        List<CaveniaRuntimeBiomeSourceApiShapeComponent> expectedComponents = List.of(
            CaveniaRuntimeBiomeSourceApiShapeComponent.LOCAL_BIOME_SOURCE_CLASS,
            CaveniaRuntimeBiomeSourceApiShapeComponent.ABSTRACT_METHODS_INVENTORY,
            CaveniaRuntimeBiomeSourceApiShapeComponent.CODEC_DISPATCH_SHAPE,
            CaveniaRuntimeBiomeSourceApiShapeComponent.CODEC_METHOD_SHAPE,
            CaveniaRuntimeBiomeSourceApiShapeComponent.POSSIBLE_BIOMES_SHAPE,
            CaveniaRuntimeBiomeSourceApiShapeComponent.NOISE_BIOME_QUERY_SHAPE,
            CaveniaRuntimeBiomeSourceApiShapeComponent.CLIMATE_SAMPLER_SHAPE,
            CaveniaRuntimeBiomeSourceApiShapeComponent.HOLDER_RETURN_SHAPE,
            CaveniaRuntimeBiomeSourceApiShapeComponent.REGISTRY_CONTEXT_SHAPE,
            CaveniaRuntimeBiomeSourceApiShapeComponent.RESOURCE_KEY_CONVERSION_SHAPE,
            CaveniaRuntimeBiomeSourceApiShapeComponent.MISSING_BIOME_FALLBACK_SHAPE,
            CaveniaRuntimeBiomeSourceApiShapeComponent.REAL_SUBCLASS_STILL_DEFERRED
        );
        List<CaveniaRuntimeBiomeSourceApiShapeInventoryEntry> entries = CaveniaRuntimeBiomeSourceApiShapeInventory.entries();

        assertEquals(expectedComponents, CaveniaRuntimeBiomeSourceApiShapeInventory.components());
        assertEquals(12, CaveniaRuntimeBiomeSourceApiShapeInventory.entryCount());
        assertTrue(entries.stream().allMatch(entry -> entry.sourceReference() != null && !entry.sourceReference().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.observedShape() != null && !entry.observedShape().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.implementationImplication() != null && !entry.implementationImplication().isBlank()));
        assertTrue(entries.stream().allMatch(entry -> entry.blocker() != null && !entry.blocker().isBlank()));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceApiShapeInventoryEntry::locallyInspected));
        assertTrue(entries.stream().allMatch(CaveniaRuntimeBiomeSourceApiShapeInventoryEntry::shapePinned));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeApiImplemented()));
        assertTrue(entries.stream().allMatch(entry -> !entry.runtimeApiAllowedInThisSlice()));
        assertTrue(entries.stream().allMatch(entry -> !entry.activationAllowedInThisSlice()));
        assertTrue(entries.stream().allMatch(entry -> entry.sourceReference().contains("BiomeSource") || entry.component()
            == CaveniaRuntimeBiomeSourceApiShapeComponent.CLIMATE_SAMPLER_SHAPE
            || entry.component() == CaveniaRuntimeBiomeSourceApiShapeComponent.REAL_SUBCLASS_STILL_DEFERRED));
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.entryFor(
            CaveniaRuntimeBiomeSourceApiShapeComponent.LOCAL_BIOME_SOURCE_CLASS
        ).orElseThrow().sourceReference().contains("app-neoforge/build/neoForm/neoFormJoined1.21.1-20240808.144430"));
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.entryFor(
            CaveniaRuntimeBiomeSourceApiShapeComponent.LOCAL_BIOME_SOURCE_CLASS
        ).orElseThrow().observedShape().contains("net.minecraft.world.level.biome.BiomeSource"));
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.entryFor(
            CaveniaRuntimeBiomeSourceApiShapeComponent.CODEC_DISPATCH_SHAPE
        ).orElseThrow().observedShape().contains("BuiltInRegistries.BIOME_SOURCE.byNameCodec().dispatchStable"));
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.entryFor(
            CaveniaRuntimeBiomeSourceApiShapeComponent.CODEC_METHOD_SHAPE
        ).orElseThrow().observedShape().contains("protected abstract com.mojang.serialization.MapCodec"));
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.entryFor(
            CaveniaRuntimeBiomeSourceApiShapeComponent.NOISE_BIOME_QUERY_SHAPE
        ).orElseThrow().observedShape().contains("getNoiseBiome(int, int, int, net.minecraft.world.level.biome.Climate$Sampler)"));
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.entryFor(
            CaveniaRuntimeBiomeSourceApiShapeComponent.CLIMATE_SAMPLER_SHAPE
        ).orElseThrow().observedShape().contains("Climate$Sampler is a record"));

        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.localBiomeSourceClassLocated());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.allComponentsLocallyInspected());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.allShapesPinned());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.runtimeApiImplemented());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.runtimeApiAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.activationAllowedInThisSlice());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.canActivateCaveniaNow());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.abstractMethodsInventoryReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.codecDispatchShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.codecMethodShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.possibleBiomesShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.noiseBiomeQueryShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.climateSamplerShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.holderReturnShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.registryContextShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.resourceKeyConversionShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.missingBiomeFallbackShapeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.realSubclassStillDeferred());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.readyForRealSubclassImplementation());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayDecideRealSubclassImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayImplementRealBiomeSourceSubclassDirectly());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayAddCodecImplementation());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayRegisterCodec());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayUseRegistryLookupAccess());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayAddDimensionJson());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayAddDimensionTypeJson());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayCreateActiveCaveniaLevel());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayAddWorldgenResources());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayAddAccessOrTeleport());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayAddSpawning());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.nextSliceMayRegisterCavemanEntity());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.skeletonReady());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.skeletonRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.realSubclassGoNoGoDecisionReady());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.realSubclassGoNoGoRuntimeReady());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.guardedRealSubclassStubImplemented());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.guardedRealSubclassRuntimeReady());
        assertEquals(68, CaveniaRuntimeBiomeSourceApiShapeInventory.selectedSurfaceReadinessItemCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceApiShapeInventory.globalReadinessMatrixTotalRequirementCount());
        assertEquals(46, CaveniaRuntimeBiomeSourceApiShapeInventory.globalReadinessMatrixBlockedRequirementCount());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.runtimeBiomeSourceReady());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.runtimeBiomeSourceRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.codecRegistered());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.registryLookupAccessReady());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.dimensionJsonPresent());
        assertFalse(CaveniaRuntimeBiomeSourceApiShapeInventory.dimensionTypeJsonPresent());
        assertTrue(CaveniaRuntimeBiomeSourceApiShapeInventory.cavemanRemainsDeferred());
        assertTrue(CaveniaRuntimeBiomeSourceSubclassDecision.apiShapeDecisionsPinned());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.readyForRealSubclass());
        assertFalse(CaveniaRuntimeBiomeSourceSubclassDecision.realMinecraftBiomeSourceImplementedInThisSlice());

        assertImmutableList(CaveniaRuntimeBiomeSourceApiShapeInventory.entries(), entries.get(0));
        assertImmutableList(
            CaveniaRuntimeBiomeSourceApiShapeInventory.components(),
            CaveniaRuntimeBiomeSourceApiShapeComponent.LOCAL_BIOME_SOURCE_CLASS
        );
        entries.forEach(entry -> assertNotNull(entry.component()));
    }

    @Test
    void runtimeBiomeSourceApiShapeInventorySourcesStayOutOfRuntimeImplementationPaths() throws IOException {
        String componentSource = Files.readString(COMPONENT_SOURCE);
        String entrySource = Files.readString(ENTRY_SOURCE);
        String inventorySource = Files.readString(INVENTORY_SOURCE);

        assertSourceStaysInert(componentSource);
        assertSourceStaysInert(entrySource);
        assertSourceStaysInert(inventorySource);

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
                "Expected the runtime-biome-source API shape inventory to avoid adding active Cavenia runtime classes or resources"
            );
        }
    }

    private static void assertSourceStaysInert(String source) {
        assertFalse(source.contains("extends ChunkGenerator"));
        assertFalse(source.contains("extends BiomeSource"));
        assertFalse(source.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertFalse(source.contains("Holder<Biome>"));
        assertFalse(source.contains("RegistryLookup<"));
        assertFalse(source.contains("RegistryAccess"));
        assertFalse(source.contains("ResourceKey<Biome>"));
        assertFalse(source.contains("MapCodec"));
        assertFalse(source.contains("Codec<"));
        assertFalse(source.contains("RecordCodecBuilder"));
        assertFalse(source.contains("Registry.register"));
        assertFalse(source.contains("registerConfiguredFeature("));
        assertFalse(source.contains("registerPlacedFeature("));
        assertFalse(source.contains("registerConfiguredCarver("));
        assertFalse(source.contains("addFreshEntity("));
        assertFalse(source.contains("changeDimension("));
        assertFalse(source.contains(".teleportTo("));
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

    @SuppressWarnings("unchecked")
    private static void assertImmutableList(List<?> list, Object value) {
        List<Object> mutableView = (List<Object>) list;

        try {
            mutableView.add(value);
        } catch (UnsupportedOperationException expected) {
            return;
        }

        throw new AssertionError("Expected list to be immutable");
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
