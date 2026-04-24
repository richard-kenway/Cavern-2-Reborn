package com.richardkenway.cavernreborn.app.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavenicSkeletonResourcesTest {
    @Test
    void cavenicSkeletonRegistersWithDedicatedEntitySpawnEggRendererAndStableCreativePlacement() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicSkeleton.java"
        );
        String entityEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
        );
        String clientSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        );
        String rendererSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CavenicSkeletonRenderer.java"
        );

        assertTrue(registriesSource.contains("ENTITY_TYPES.register(\n        \"cavenic_skeleton\""));
        assertTrue(registriesSource.contains("EntityType.Builder.of(CavenicSkeleton::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains(".sized(0.68F, 2.0F)"));
        assertTrue(registriesSource.contains("ITEMS.register(\n        \"cavenic_skeleton_spawn_egg\""));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CAVENIC_SKELETON.get(), 0xAAAAAA, 0xDDDDDD, new Item.Properties())"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_BOW.get())",
            "output.accept(CAVENIC_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SKELETON_SPAWN_EGG.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends Skeleton"));
        assertTrue(entitySource.contains("this.xpReward = 13;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains("Skeleton.createAttributes()"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 40.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.MOVEMENT_SPEED, 0.2D)"));
        assertTrue(entitySource.contains(".add(Attributes.FOLLOW_RANGE, 21.0D)"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_WEIGHT = 20;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MIN_COUNT = 1;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MAX_COUNT = 1;"));
        assertTrue(entitySource.contains("canNaturallySpawnInDimension"));
        assertTrue(entitySource.contains("checkCavenicSkeletonSpawnRules"));
        assertTrue(entitySource.contains("CavernNeoForgeDimensions.isCavern"));
        assertTrue(entitySource.contains("Monster.checkMonsterSpawnRules"));
        assertTrue(entitySource.contains("return EntityType.SKELETON.getDefaultLootTable();"));
        assertFalse(entitySource.contains("cavenic_orb"));
        assertFalse(entitySource.contains("DamageTypeTags.IS_FALL"));
        assertFalse(entitySource.contains("DamageTypeTags.IS_FIRE"));

        assertTrue(entityEventSource.contains("event.put(ModRegistries.CAVENIC_SKELETON.get(), CavenicSkeleton.createAttributes().build())"));
        assertTrue(entityEventSource.contains("RegisterSpawnPlacementsEvent"));
        assertTrue(entityEventSource.contains("SpawnPlacementTypes.ON_GROUND"));
        assertTrue(entityEventSource.contains("Heightmap.Types.MOTION_BLOCKING_NO_LEAVES"));
        assertTrue(entityEventSource.contains("CavenicSkeleton::checkCavenicSkeletonSpawnRules"));
        assertTrue(entityEventSource.contains("RegisterSpawnPlacementsEvent.Operation.REPLACE"));

        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CAVENIC_SKELETON.get(), CavenicSkeletonRenderer::new);"));
        assertTrue(rendererSource.contains("extends SkeletonRenderer<CavenicSkeleton>"));
        assertTrue(rendererSource.contains("textures/entity/cavenic_skeleton.png"));
        assertTrue(rendererSource.contains("poseStack.scale(1.1F, 1.1F, 1.1F);"));

        assertFalse(registriesSource.contains("cavenic_creeper"));
        assertFalse(registriesSource.contains("cavenic_spider"));
        assertFalse(registriesSource.contains("cavenic_witch"));
        assertFalse(registriesSource.contains("cavenic_bear"));
        assertFalse(registriesSource.toLowerCase().contains("cavenia"));
        assertFalse(registriesSource.contains("EntityRapidArrow"));
        assertFalse(registriesSource.contains("EntityTorchArrow"));
        assertFalse(registriesSource.toLowerCase().contains("economy"));
    }

    @Test
    void cavenicSkeletonResourcesCoverLangSpawnEggModelTextureAndNaturalSpawnDataClasspath() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/cavenic_skeleton_spawn_egg.json");
        JsonObject biomeModifier = readJsonResource("data/cavernreborn/neoforge/biome_modifier/cavenic_skeleton_spawns.json");
        JsonObject biomeTag = readJsonResource("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_skeleton.json");

        assertEquals("Cavenic Skeleton", lang.get("entity.cavernreborn.cavenic_skeleton").getAsString());
        assertEquals("Cavenic Skeleton Spawn Egg", lang.get("item.cavernreborn.cavenic_skeleton_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());
        assertEquals("neoforge:add_spawns", biomeModifier.get("type").getAsString());
        assertEquals("#cavernreborn:spawns_cavenic_skeleton", biomeModifier.get("biomes").getAsString());

        JsonObject spawner = biomeModifier.getAsJsonObject("spawners");
        assertEquals("cavernreborn:cavenic_skeleton", spawner.get("type").getAsString());
        assertEquals(20, spawner.get("weight").getAsInt());
        assertEquals(1, spawner.get("minCount").getAsInt());
        assertEquals(1, spawner.get("maxCount").getAsInt());

        assertFalse(biomeTag.get("replace").getAsBoolean());
        assertEquals(List.of(
            "cavernreborn:stone_depths",
            "cavernreborn:lush_grotto",
            "cavernreborn:dripstone_grotto",
            "cavernreborn:highland_hollows"
        ), StreamSupport.stream(biomeTag.getAsJsonArray("values").spliterator(), false).map(element -> element.getAsString()).toList());

        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_skeleton_spawn_egg.json"), "assets/cavernreborn/models/item/cavenic_skeleton_spawn_egg.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/entity/cavenic_skeleton.png"), "assets/cavernreborn/textures/entity/cavenic_skeleton.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/neoforge/biome_modifier/cavenic_skeleton_spawns.json"), "data/cavernreborn/neoforge/biome_modifier/cavenic_skeleton_spawns.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_skeleton.json"), "data/cavernreborn/tags/worldgen/biome/spawns_cavenic_skeleton.json");
        assertMissingResource("data/cavernreborn/loot_table/entities/cavenic_skeleton.json");
        assertMissingResource("data/cavernreborn/loot_tables/entities/cavenic_skeleton.json");
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CavenicSkeletonResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CavenicSkeletonResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CavenicSkeletonResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
    }

    private static void assertClassPathOrigin(URL url, String expectedPathSuffix) {
        assertTrue(url.toString().contains(expectedPathSuffix), "Expected runtime classpath resource to contain " + expectedPathSuffix + " but got " + url);
    }

    private static String readProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate)) {
                try {
                    return Files.readString(candidate);
                } catch (IOException e) {
                    throw new IllegalStateException("Failed to read project file: " + candidate, e);
                }
            }
            current = current.getParent();
        }

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
    }

    private static void assertInOrder(String source, List<String> needles) {
        int index = -1;

        for (String needle : needles) {
            int next = source.indexOf(needle, index + 1);
            assertTrue(next >= 0, "Missing source fragment: " + needle);
            assertTrue(next > index, "Expected source fragment order for: " + needle);
            index = next;
        }
    }
}
