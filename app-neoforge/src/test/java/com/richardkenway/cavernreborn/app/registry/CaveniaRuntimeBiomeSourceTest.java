package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaRuntimeBiomeSourceTest {
    private static final Path DESIGNATED_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen",
        "CaveniaRuntimeBiomeSource.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void guardedRuntimeBiomeSourceStubCompilesAgainstTheLocalAbstractShapeButStaysUnavailable() throws Exception {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> runtimeBiomeSourceClass = Class.forName(
            "com.richardkenway.cavernreborn.app.worldgen.CaveniaRuntimeBiomeSource",
            false,
            classLoader
        );
        Class<?> biomeSourceClass = Class.forName("net.minecraft.world.level.biome.BiomeSource", false, classLoader);

        assertTrue(Files.exists(DESIGNATED_SOURCE));
        assertTrue(biomeSourceClass.isAssignableFrom(runtimeBiomeSourceClass));
        assertEquals("CaveniaRuntimeBiomeSource", runtimeBiomeSourceClass.getSimpleName());
        assertTrue(Modifier.isFinal(runtimeBiomeSourceClass.getModifiers()));
        assertEquals(1, runtimeBiomeSourceClass.getDeclaredConstructors().length);
        assertTrue(Modifier.isPrivate(runtimeBiomeSourceClass.getDeclaredConstructors()[0].getModifiers()));

        assertTrue(designatedSource.contains("public static boolean guardedSubclassStubReady() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean extendsMinecraftBiomeSource() {\n        return true;\n    }"));
        assertTrue(
            designatedSource.contains("public static String designatedSubclassFileName() {\n        return DESIGNATED_SUBCLASS_FILE_NAME;\n    }")
        );
        assertTrue(
            designatedSource.contains("public static String designatedSubclassSimpleName() {\n        return DESIGNATED_SUBCLASS_SIMPLE_NAME;\n    }")
        );
        assertTrue(designatedSource.contains("public static boolean normalRuntimeConstructionAllowed() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean usableRuntimeBehaviorReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean unsupportedMethodStubsOnly() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean codecMethodStubbed() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean collectPossibleBiomesStubbed() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean getNoiseBiomeStubbed() {\n        return true;\n    }"));
        assertTrue(designatedSource.contains("public static boolean usableCodecImplementationReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean codecRegistered() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean biomeSourceTypeRegistered() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean registryLookupAccessReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean holderResourceKeyConversionReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean dimensionBindingReady() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean activationAllowedInThisSlice() {\n        return false;\n    }"));
        assertTrue(designatedSource.contains("public static boolean canActivateCaveniaNow() {\n        return false;\n    }"));
        assertTrue(
            designatedSource.contains(
                "public static boolean apiShapeInventoryReady() {\n        return CaveniaRuntimeBiomeSourceApiShapeInventory.apiShapeInventoryReady();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean goNoGoDecisionAllowsSubclass() {\n        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.nextSliceMayAddOneDesignatedRealSubclassFile();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean allGoNoGoGuardrailsEnforced() {\n        return CaveniaRuntimeBiomeSourceRealSubclassGoNoGoDecision.allGuardrailsEnforcedInThisSlice();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static int selectedSurfaceReadinessItemCount() {\n        return CaveniaBiomeSourceStrategyReadinessMatrix.totalReadinessItemCount();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static int globalReadinessMatrixTotalRequirementCount() {\n        return CaveniaActivationReadinessMatrix.totalRequirementCount();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static int globalReadinessMatrixBlockedRequirementCount() {\n        return CaveniaActivationReadinessMatrix.blockedRequirementCount();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean dimensionJsonPresent() {\n        return CaveniaRuntimeBiomeSourceSkeleton.dimensionJsonPresent();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean dimensionTypeJsonPresent() {\n        return CaveniaRuntimeBiomeSourceSkeleton.dimensionTypeJsonPresent();\n    }"
            )
        );
        assertTrue(
            designatedSource.contains(
                "public static boolean cavemanRemainsDeferred() {\n        return CaveniaRuntimeBiomeSourceSkeleton.cavemanRemainsDeferred();\n    }"
            )
        );
        assertTrue(designatedSource.contains("throw unsupported(\"codec()\")"));
        assertTrue(designatedSource.contains("throw unsupported(\"collectPossibleBiomes()\")"));
        assertTrue(designatedSource.contains("throw unsupported(\"getNoiseBiome(int, int, int, Climate.Sampler)\")"));
        assertTrue(designatedSource.contains("not usable, registered, or activating yet"));
    }

    @Test
    void guardedRuntimeBiomeSourceStubKeepsRuntimeApiUsageScopedToTheDesignatedFile() throws IOException {
        String designatedSource = Files.readString(DESIGNATED_SOURCE);

        assertTrue(designatedSource.contains("extends BiomeSource"));
        assertTrue(designatedSource.contains("import net.minecraft.world.level.biome.BiomeSource;"));
        assertTrue(designatedSource.contains("Holder<Biome>"));
        assertTrue(designatedSource.contains("MapCodec"));
        assertTrue(designatedSource.contains("Climate.Sampler"));
        assertTrue(designatedSource.contains("throw unsupported(\"codec()\")"));
        assertTrue(designatedSource.contains("throw unsupported(\"collectPossibleBiomes()\")"));
        assertTrue(designatedSource.contains("throw unsupported(\"getNoiseBiome(int, int, int, Climate.Sampler)\")"));
        assertFalse(designatedSource.contains("Registry.register"));
        assertFalse(designatedSource.contains("RecordCodecBuilder"));
        assertFalse(designatedSource.contains("BuiltInRegistries.BIOME_SOURCE.register"));
        assertFalse(designatedSource.contains("DeferredRegister"));
        assertFalse(designatedSource.contains("RegistryLookup<"));
        assertFalse(designatedSource.contains("RegistryAccess"));
        assertFalse(designatedSource.contains("ResourceKey<Biome>"));

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            List<Path> regularFiles = sourceFiles
                .filter(Files::isRegularFile)
                .toList();

            List<Path> designatedFiles = regularFiles.stream()
                .filter(path -> path.getFileName().toString().equals("CaveniaRuntimeBiomeSource.java"))
                .toList();

            assertEquals(1, designatedFiles.size());
            assertEquals(DESIGNATED_SOURCE, designatedFiles.get(0));
            assertOnlyDesignatedFileContains(regularFiles, "extends BiomeSource");
            assertOnlyDesignatedFileContains(regularFiles, "import net.minecraft.world.level.biome.BiomeSource;");
            assertOnlyDesignatedFileContains(regularFiles, "Holder<Biome>");
            assertOnlyDesignatedFileContains(regularFiles, "MapCodec");
            assertOnlyDesignatedFileContains(regularFiles, "Climate.Sampler");
            assertNoMainSourceContains(regularFiles, "Registry.register");
            assertNoMainSourceContains(regularFiles, "RecordCodecBuilder");
            assertNoMainSourceContains(regularFiles, "BuiltInRegistries.BIOME_SOURCE.register");
            assertNoMainSourceContains(regularFiles, "DeferredRegister");
            assertNoMainSourceContains(regularFiles, "RegistryLookup<");
            assertNoMainSourceContains(regularFiles, "RegistryAccess");
            assertNoMainSourceContains(regularFiles, "ResourceKey<Biome>");
            assertNoMainSourceContains(regularFiles, "registerConfiguredFeature(");
            assertNoMainSourceContains(regularFiles, "registerPlacedFeature(");
            assertNoMainSourceContains(regularFiles, "registerConfiguredCarver(");
            assertNoMainSourceContains(regularFiles, "addFreshEntity(");
            assertNoMainSourceContains(regularFiles, "changeDimension(");
            assertNoMainSourceContains(regularFiles, ".teleportTo(");
        }

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_carver", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "configured_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "worldgen", "placed_feature", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaBiomeSource.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaChunkGenerator.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "ChunkGeneratorCavenia.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "MapGenCaveniaCaves.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnProvider.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaSpawnHandler.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "worldgen", "CaveniaServerTickSpawner.java");

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
                "Expected the guarded runtime biome-source stub slice to keep active Cavenia resources absent"
            );
        }
    }

    private static void assertOnlyDesignatedFileContains(List<Path> sourceFiles, String fragment) throws IOException {
        List<Path> matchingFiles = sourceFiles.stream()
            .filter(path -> {
                try {
                    return Files.readString(path).contains(fragment);
                } catch (IOException exception) {
                    throw new IllegalStateException("Could not read source file: " + path, exception);
                }
            })
            .toList();

        assertEquals(List.of(DESIGNATED_SOURCE), matchingFiles, "Expected only the designated subclass file to contain: " + fragment);
    }

    private static void assertNoMainSourceContains(List<Path> sourceFiles, String fragment) throws IOException {
        assertTrue(
            sourceFiles.stream().noneMatch(path -> {
                try {
                    return Files.readString(path).contains(fragment);
                } catch (IOException exception) {
                    throw new IllegalStateException("Could not read source file: " + path, exception);
                }
            }),
            "Expected no main-source file to contain: " + fragment
        );
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
