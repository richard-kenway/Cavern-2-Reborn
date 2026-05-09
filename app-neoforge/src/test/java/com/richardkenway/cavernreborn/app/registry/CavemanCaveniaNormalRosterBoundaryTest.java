package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.richardkenway.cavernreborn.core.spawn.CaveniaSpawnEntry;
import com.richardkenway.cavernreborn.core.spawn.CaveniaSpawnProviderPolicy;

class CavemanCaveniaNormalRosterBoundaryTest {
    private static final Path README = resolveProjectFile("README.md");
    private static final Path BOUNDARY_DOC = resolveProjectFile("docs", "caveman-cavenia-normal-roster-boundary.md");
    private static final Path POLICY_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-policy-mvp.md");
    private static final Path SPAWN_PROVIDER_DOC = resolveProjectFile("docs", "cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md");
    private static final Path CRAZY_ROSTER_DOC = resolveProjectFile("docs", "cavenia-crazy-roster-natural-spawn-boundary.md");
    private static final Path FOUNDATION_DOC = resolveProjectFile("docs", "cavenia-dimension-provider-foundation-boundary.md");
    private static final Path RUNTIME_SMOKE_DOC = resolveProjectFile("docs", "runtime-smoke.md");
    private static final Path MOD_REGISTRIES = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
    );
    private static final Path ENTITY_EVENTS = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
    );
    private static final Path CLIENT_EVENTS = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
    );
    private static final Path ENTITY_DIR = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity"
    );
    private static final Path RENDERER_DIR = resolveProjectFile(
        "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer"
    );

    @Test
    void docsPinTheLegacyCavemanSurfaceAndCurrentDeferral() throws IOException {
        String readme = Files.readString(README);
        String boundaryDoc = Files.readString(BOUNDARY_DOC);
        String policyDoc = Files.readString(POLICY_DOC);
        String spawnProviderDoc = Files.readString(SPAWN_PROVIDER_DOC);
        String crazyRosterDoc = Files.readString(CRAZY_ROSTER_DOC);
        String foundationDoc = Files.readString(FOUNDATION_DOC);
        String runtimeSmokeDoc = Files.readString(RUNTIME_SMOKE_DOC);

        assertTrue(readme.contains("Caveman Baseline / Cavenia Normal Spawn Roster Boundary"));
        assertTrue(readme.contains("docs/caveman-cavenia-normal-roster-boundary.md"));

        assertTrue(boundaryDoc.contains("`cavern.entity.monster.EntityCaveman`"));
        assertTrue(boundaryDoc.contains("`extends EntityMob`"));
        assertTrue(boundaryDoc.contains("`implements ICavenicMob`"));
        assertTrue(boundaryDoc.contains("`new InventoryBasic(\"entity.Caveman.name\", false, 9 * 3)`"));
        assertTrue(boundaryDoc.contains("`MAX_HEALTH = 30.0D`"));
        assertTrue(boundaryDoc.contains("`FOLLOW_RANGE = 35.0D`"));
        assertTrue(boundaryDoc.contains("`KNOCKBACK_RESISTANCE = 0.5D`"));
        assertTrue(boundaryDoc.contains("`MOVEMENT_SPEED = 0.2875D`"));
        assertTrue(boundaryDoc.contains("`player.displayGUIChest(backpackInventory)`"));
        assertTrue(boundaryDoc.contains("`EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.15F, false)`"));
        assertTrue(boundaryDoc.contains("`new SpawnListEntry(EntityCaveman.class, 35, 1, 1)`"));
        assertTrue(boundaryDoc.contains("`registry.register(createEntry(EntityCaveman.class, \"caveman\", \"Caveman\", 0xAAAAAA, 0xCCCCCC))`"));
        assertTrue(boundaryDoc.contains("`ItemMirageBook.getRandomBook()` at `5%`"));
        assertTrue(boundaryDoc.contains("`ItemMagicBook.getRandomBook()` at `5%`"));
        assertTrue(boundaryDoc.contains("`RenderCaveman`"));
        assertTrue(boundaryDoc.contains("`ModelCaveman`"));
        assertTrue(boundaryDoc.contains("`textures/entity/caveman.png`"));
        assertTrue(boundaryDoc.contains("`assets/cavern/advancements/touch_caveman.json`"));
        assertTrue(boundaryDoc.contains("`MCEPluginWrapper` adds `EntityCaveman.class` as a purchase entity with value `15`"));
        assertTrue(boundaryDoc.contains("`EntityCaveman -> deferred:caveman`"));
        assertTrue(boundaryDoc.contains("no registered `cavernreborn:caveman` entity type"));
        assertTrue(boundaryDoc.contains("Mirage Book or Magic Book support just to satisfy Caveman inventory seeding"));
        assertTrue(boundaryDoc.contains("active Cavenia runtime"));

        assertTrue(policyDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(spawnProviderDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(crazyRosterDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(foundationDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
        assertTrue(runtimeSmokeDoc.contains("inactive Caveman / Cavenia normal-roster boundary"));
        assertTrue(runtimeSmokeDoc.contains("docs/caveman-cavenia-normal-roster-boundary.md"));
    }

    @Test
    void rebornKeepsCavemanDeferredAndUnregistered() throws IOException {
        String registriesSource = Files.readString(MOD_REGISTRIES, StandardCharsets.UTF_8);
        String entityEvents = Files.readString(ENTITY_EVENTS, StandardCharsets.UTF_8);
        String clientEvents = Files.readString(CLIENT_EVENTS, StandardCharsets.UTF_8);
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");

        CaveniaSpawnEntry cavemanEntry = CaveniaSpawnProviderPolicy.normalRoster().stream()
            .filter(entry -> entry.legacyClassName().equals("EntityCaveman"))
            .findFirst()
            .orElseThrow();

        assertEquals(CaveniaSpawnProviderPolicy.DEFERRED_CAVEMAN_REBORN_ENTITY_ID, cavemanEntry.rebornEntityId());
        assertFalse(cavemanEntry.hasActiveRebornEntityId());

        assertFalse(registriesSource.contains("\"caveman\""));
        assertFalse(registriesSource.contains("CAVEMAN"));
        assertFalse(entityEvents.contains("Caveman::"));
        assertFalse(clientEvents.contains("CavemanRenderer"));
        assertFalse(lang.has("entity.cavernreborn.caveman"));
        assertFalse(lang.has("item.cavernreborn.caveman_spawn_egg"));

        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "Caveman.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavemanEntity.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CavemanRenderer.java");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "assets", "cavernreborn", "textures", "entity", "caveman.png");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "assets", "cavernreborn", "models", "item", "caveman_spawn_egg.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "caveman_spawns.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_caveman.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension", "cavenia.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "dimension_type", "cavenia.json");

        try (Stream<Path> entityFiles = Files.list(ENTITY_DIR)) {
            assertTrue(
                entityFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Caveman")),
                "Expected the Caveman boundary to keep runtime entity classes absent"
            );
        }

        try (Stream<Path> rendererFiles = Files.list(RENDERER_DIR)) {
            assertTrue(
                rendererFiles.map(path -> path.getFileName().toString()).noneMatch(name -> name.contains("Caveman")),
                "Expected the Caveman boundary to keep runtime renderer classes absent"
            );
        }
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CavemanCaveniaNormalRosterBoundaryTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
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
