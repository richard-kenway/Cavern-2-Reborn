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

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CavenicSpiderResourcesTest {
    @Test
    void cavenicSpiderRegistersWithDedicatedEntitySpawnEggRendererBaselineLootAndStableCreativePlacement() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicSpider.java"
        );
        String entityEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
        );
        String clientSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        );
        String rendererSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CavenicSpiderRenderer.java"
        );
        String mainEntrypoint = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"
        );

        assertTrue(registriesSource.contains("ENTITY_TYPES.register(\n        \"cavenic_spider\""));
        assertTrue(registriesSource.contains("EntityType.Builder.of(CavenicSpider::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains(".sized(1.4F, 0.9F)"));
        assertTrue(registriesSource.contains(".eyeHeight(0.65F)"));
        assertTrue(registriesSource.contains(".passengerAttachments(0.765F)"));
        assertTrue(registriesSource.contains("ITEMS.register(\n        \"cavenic_spider_spawn_egg\""));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CAVENIC_SPIDER.get(), 0xAAAAAA, 0x811F1F, new Item.Properties())"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_BOW.get())",
            "output.accept(CAVENIC_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SKELETON_SPAWN_EGG.get())",
            "output.accept(CAVENIC_CREEPER_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SPIDER_SPAWN_EGG.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends Spider"));
        assertTrue(entitySource.contains("this.xpReward = 12;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains("Spider.createAttributes()"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 20.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.MOVEMENT_SPEED, 0.6D)"));
        assertTrue(entitySource.contains("return EntityType.SPIDER.getDefaultLootTable();"));
        assertFalse(entitySource.contains("canNaturallySpawnInDimension"));
        assertFalse(entitySource.contains("checkCavenicSpiderSpawnRules"));
        assertFalse(entitySource.contains("LEGACY_FALL_DAMAGE_MULTIPLIER"));
        assertFalse(entitySource.contains("DamageTypeTags"));
        assertFalse(entitySource.contains("cavenic_orb"));
        assertFalse(entitySource.contains("MobEffects.BLINDNESS"));
        assertFalse(entitySource.contains("MobEffects.POISON"));
        assertFalse(entitySource.contains("registerGoals("));
        assertFalse(entitySource.contains("attackEntityAsMob("));
        assertFalse(entitySource.toLowerCase().contains("cavenia"));

        assertTrue(entityEventSource.contains("event.put(ModRegistries.CAVENIC_SPIDER.get(), CavenicSpider.createAttributes().build())"));
        assertFalse(entityEventSource.contains("CavenicSpider::checkCavenicSpiderSpawnRules"));
        assertFalse(mainEntrypoint.contains("CavenicSpiderLootEvents"));
        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CAVENIC_SPIDER.get(), CavenicSpiderRenderer::new);"));
        assertTrue(rendererSource.contains("extends SpiderRenderer<CavenicSpider>"));
        assertTrue(rendererSource.contains("textures/entity/cavenic_spider.png"));

        assertFalse(registriesSource.contains("cavenic_witch"));
        assertFalse(registriesSource.contains("cavenic_bear"));
        assertFalse(registriesSource.toLowerCase().contains("cavenia"));
        assertFalse(registriesSource.contains("EntityRapidArrow"));
        assertFalse(registriesSource.contains("EntityTorchArrow"));
        assertFalse(registriesSource.contains("EntityCavenicArrow"));
    }

    @Test
    void cavenicSpiderResourcesCoverLangSpawnEggModelTextureAndNoNaturalSpawnOrLootOverride() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/cavenic_spider_spawn_egg.json");

        assertEquals("Cavenic Spider", lang.get("entity.cavernreborn.cavenic_spider").getAsString());
        assertEquals("Cavenic Spider Spawn Egg", lang.get("item.cavernreborn.cavenic_spider_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());

        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_spider_spawn_egg.json"), "assets/cavernreborn/models/item/cavenic_spider_spawn_egg.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/entity/cavenic_spider.png"), "assets/cavernreborn/textures/entity/cavenic_spider.png");
        assertMissingResource("data/cavernreborn/loot_table/entities/cavenic_spider.json");
        assertMissingResource("data/cavernreborn/loot_tables/entities/cavenic_spider.json");
        assertMissingResource("data/cavernreborn/neoforge/biome_modifier/cavenic_spider_spawns.json");
        assertMissingResource("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_spider.json");
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CavenicSpiderResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CavenicSpiderResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CavenicSpiderResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
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
