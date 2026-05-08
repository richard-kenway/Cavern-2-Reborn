package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaDimensionKeyTypeContractBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path DIMENSIONS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CavernNeoForgeDimensions.java"
    );
    private static final Path DIMENSION_PACKAGE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources", "data", "cavernreborn"
    );

    @Test
    void docsPinTheLegacyIdentityAndFutureContractOnlyBoundary() throws IOException {
        String readme = Files.readString(README);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String terrainDoc = Files.readString(TERRAIN_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Cavenia Dimension Key / Type Contract Boundary"));
        assertTrue(readme.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));

        assertTrue(contractDoc.contains("`CaveniaConfig.dimensionId = -54`"));
        assertTrue(contractDoc.contains("`WorldProviderCavenia`"));
        assertTrue(contractDoc.contains("`WorldProviderCavern`"));
        assertTrue(contractDoc.contains("`worldHeight = 128`"));
        assertTrue(contractDoc.contains("`caveBrightness = 0.125D`"));
        assertTrue(contractDoc.contains("`monsterSpawn = 200`"));
        assertTrue(contractDoc.contains("`cavernreborn:cavenia`"));
        assertTrue(contractDoc.contains("future identity only"));
        assertTrue(contractDoc.contains("no `CAVENIA_LOCATION`"));
        assertTrue(contractDoc.contains("no `CAVENIA_LEVEL_KEY`"));
        assertTrue(contractDoc.contains("`data/cavernreborn/dimension/cavenia.json`"));
        assertTrue(contractDoc.contains("`data/cavernreborn/dimension_type/cavenia.json`"));
        assertTrue(contractDoc.contains("`has_skylight: false`"));
        assertTrue(contractDoc.contains("`natural` decision"));
        assertTrue(contractDoc.contains("`height` / `logical_height`"));
        assertTrue(contractDoc.contains("`ambient_light`"));
        assertTrue(contractDoc.contains("`effects`"));
        assertTrue(contractDoc.contains("actual `dimension/cavenia.json` generator object"));
        assertTrue(contractDoc.contains("biome source"));
        assertTrue(contractDoc.contains("entry/access flow"));
        assertTrue(contractDoc.contains("provider-owned spawn callback"));
        assertTrue(contractDoc.contains("client visual/fog/lighting parity"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));

        assertTrue(foundationDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(terrainDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
    }

    @Test
    void rebornStillKeepsTheKeyTypeContractInactiveAtRuntimeAndInResources() throws IOException {
        String dimensionsSource = Files.readString(DIMENSIONS_SOURCE, StandardCharsets.UTF_8);

        assertFalse(dimensionsSource.contains("CAVENIA_LOCATION"));
        assertFalse(dimensionsSource.contains("CAVENIA_LEVEL_KEY"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");

        try (Stream<Path> dimensionFiles = Files.list(DIMENSION_PACKAGE)) {
            assertTrue(
                dimensionFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Cavenia")),
                "Expected the key/type boundary to keep Cavenia-specific runtime dimension classes absent"
            );
        }

        try (Stream<Path> resourceFiles = Files.walk(RESOURCES_ROOT)) {
            assertTrue(
                resourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.toString().replace('\\', '/'))
                    .noneMatch(path -> path.contains("/dimension/cavenia.json") || path.contains("/dimension_type/cavenia.json") || path.contains("/cavenia/") || path.contains("cavenia_")),
                "Expected the key/type boundary to keep active Cavenia resources absent from checked-in data resources"
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
