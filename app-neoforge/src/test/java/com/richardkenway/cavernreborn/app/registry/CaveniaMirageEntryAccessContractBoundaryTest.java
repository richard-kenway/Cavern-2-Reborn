package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CaveniaMirageEntryAccessContractBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path CONTRACT_DOC = resolveProjectFile("docs", "cavenia-mirage-entry-access-contract-boundary.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path KEY_TYPE_DOC = resolveProjectFile("docs", "cavenia-dimension-key-type-contract-boundary.md");
    private static final Path BIOME_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-biome-provider-contract-boundary.md");
    private static final Path VEINS_DOC = resolveProjectFile("docs", "cavenia-veins-content-pipeline-contract-boundary.md");
    private static final Path TERRAIN_DOC = resolveProjectFile("docs", "cavenia-chunk-generator-terrain-pipeline-contract-boundary.md");
    private static final Path CAVE_CARVER_DOC = resolveProjectFile("docs", "cavenia-cave-carver-mapgen-contract-boundary.md");
    private static final Path POPULATION_DOC = resolveProjectFile("docs", "cavenia-population-lakes-falls-shroom-contract-boundary.md");
    private static final Path SPAWN_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md");
    private static final Path CRAZY_ROSTER_DOC = resolveProjectFile("docs", "cavenia-crazy-roster-natural-spawn-boundary.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path DIMENSIONS_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CavernNeoForgeDimensions.java"
    );
    private static final Path REGISTRIES_SOURCE = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
    );
    private static final Path APP_SOURCE_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app"
    );
    private static final Path RESOURCES_ROOT = resolveProjectFile(
        "app-neoforge", "src", "main", "resources"
    );

    @Test
    void docsPinTheLegacyMirageAccessContractAndInactiveRebornBoundary() throws IOException {
        String readme = Files.readString(README);
        String contractDoc = Files.readString(CONTRACT_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String keyTypeDoc = Files.readString(KEY_TYPE_DOC);
        String biomeProviderDoc = Files.readString(BIOME_PROVIDER_DOC);
        String veinsDoc = Files.readString(VEINS_DOC);
        String terrainDoc = Files.readString(TERRAIN_DOC);
        String caveCarverDoc = Files.readString(CAVE_CARVER_DOC);
        String populationDoc = Files.readString(POPULATION_DOC);
        String spawnProviderDoc = Files.readString(SPAWN_PROVIDER_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Cavenia Mirage Entry / Access Path Contract Boundary"));
        assertTrue(readme.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));

        assertTrue(contractDoc.contains("`ItemMirageBook`"));
        assertTrue(contractDoc.contains("`EnumType.CAVENIA`"));
        assertTrue(contractDoc.contains("`CaveDimensions.CAVENIA`"));
        assertTrue(contractDoc.contains("`DimensionHandler`"));
        assertTrue(contractDoc.contains("`BlockPortalMirageWorlds`"));
        assertTrue(contractDoc.contains("`GuiSelectMirageWorld`"));
        assertTrue(contractDoc.contains("`MirageSelectMessage`"));
        assertTrue(contractDoc.contains("`MirageTeleportMessage`"));
        assertTrue(contractDoc.contains("`PortalCache`"));
        assertTrue(contractDoc.contains("`PlayerData`"));
        assertTrue(contractDoc.contains("`ITeleporter`"));
        assertTrue(contractDoc.contains("`EnumType.CAVELAND`"));
        assertTrue(contractDoc.contains("`EnumType.CAVENIA`"));
        assertTrue(contractDoc.contains("`EnumType.FROST_MOUNTAINS`"));
        assertTrue(contractDoc.contains("`EnumType.WIDE_DESERT`"));
        assertTrue(contractDoc.contains("`EnumType.THE_VOID`"));
        assertTrue(contractDoc.contains("`EnumType.DARK_FOREST`"));
        assertTrue(contractDoc.contains("`EnumType.CROWN_CLIFFS`"));
        assertTrue(contractDoc.contains("`EnumType.SKYLAND`"));
        assertTrue(contractDoc.contains("`CaveItems.MIRAGE_BOOK`"));
        assertTrue(contractDoc.contains("`cavern:mirage_book`"));
        assertTrue(contractDoc.contains("The exact Cavenia mapping is direct:"));
        assertTrue(contractDoc.contains("`BlockPortalMirageWorlds#onEntityCollidedWithBlock(...)`"));
        assertTrue(contractDoc.contains("`teleportTime <= 0L || teleportTime + 6000L < world.getTotalWorldTime()`"));
        assertTrue(contractDoc.contains("`CaveItems.MIRAGE_BOOK.transferTo(type, player)`"));
        assertTrue(contractDoc.contains("`ItemMirageBook#onItemRightClick(...)`"));
        assertTrue(contractDoc.contains("`consumeTicket(stack, 1)`"));
        assertTrue(contractDoc.contains("`new ItemStack(Items.BOOK)`"));
        assertTrue(contractDoc.contains("`transferTo(null, player)`"));
        assertTrue(contractDoc.contains("`player.changeDimension(dimNew.getId(), this)`"));
        assertTrue(contractDoc.contains("`cavern:mirage_worlds`"));
        assertTrue(contractDoc.contains("`attemptToLastPos(...)`"));
        assertTrue(contractDoc.contains("`attemptToSkyland(...)`"));
        assertTrue(contractDoc.contains("`attemptRandomly(...)`"));
        assertTrue(contractDoc.contains("`attemptToVoid(...)`"));
        assertTrue(contractDoc.contains("`MirageEventHooks#onLivingDeath(...)`"));
        assertTrue(contractDoc.contains("`MirageEventHooks#onLivingUpdate(...)`"));
        assertTrue(contractDoc.contains("no active `cavenia` dimension"));
        assertTrue(contractDoc.contains("no `mirage_book`"));
        assertTrue(contractDoc.contains("no Cavenia teleporter"));
        assertTrue(contractDoc.contains("no Cavenia portal"));
        assertTrue(contractDoc.contains("no economy or magic-book dependency"));
        assertTrue(contractDoc.contains("no invented progression or unlock system"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-provider-foundation-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-dimension-key-type-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-biome-provider-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-veins-content-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-cave-carver-mapgen-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-population-lakes-falls-shroom-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md"));
        assertTrue(contractDoc.contains("docs/cavenia-crazy-roster-natural-spawn-boundary.md"));

        assertTrue(foundationDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(keyTypeDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(biomeProviderDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(veinsDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(terrainDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(caveCarverDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(populationDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(spawnProviderDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(crazyRosterDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("docs/cavenia-mirage-entry-access-contract-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Cavenia mirage entry/access contract boundary"));
    }

    @Test
    void rebornStillKeepsMirageAccessResourcesInactive() throws IOException {
        String dimensionsSource = Files.readString(DIMENSIONS_SOURCE, StandardCharsets.UTF_8);
        String registriesSource = Files.readString(REGISTRIES_SOURCE, StandardCharsets.UTF_8);

        assertFalse(dimensionsSource.contains("CAVENIA_LOCATION"));
        assertFalse(dimensionsSource.contains("CAVENIA_LEVEL_KEY"));
        assertFalse(registriesSource.contains("mirage_book"));
        assertFalse(registriesSource.contains("MirageBook"));
        assertFalse(registriesSource.contains("CaveniaPortal"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "item", "ItemMirageBook.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "item", "MirageBookItem.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "dimension", "CaveniaTeleporter.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "portal", "CaveniaPortal.java");

        try (Stream<Path> sourceFiles = Files.walk(APP_SOURCE_ROOT)) {
            assertTrue(
                sourceFiles
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .noneMatch(name ->
                        name.contains("Mirage")
                            || name.contains("CaveniaTeleporter")
                            || name.contains("CaveniaPortal")
                            || name.contains("CaveniaAccess")
                    ),
                "Expected the mirage-access boundary to keep Reborn mirage/Cavenia access classes absent"
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
                            || path.contains("mirage_book")
                            || path.contains("cavenia_portal")
                            || path.contains("/cavenia/")
                            || path.contains("cavenia_")
                    ),
                "Expected the mirage-access boundary to keep active Cavenia access resources absent from checked-in resources"
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
