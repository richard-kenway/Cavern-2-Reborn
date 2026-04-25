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

class CavenicCreeperResourcesTest {
    @Test
    void cavenicCreeperRegistersWithDedicatedEntitySpawnEggRendererNaturalSpawnLootDamageFuseExplosionAndStableCreativePlacement() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicCreeper.java"
        );
        String entityEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
        );
        String dropEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicCreeperLootEvents.java"
        );
        String lootPolicySource = readProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "loot", "CavenicCreeperLootPolicy.java"
        );
        String mainEntrypoint = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"
        );
        String clientSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        );
        String rendererSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CavenicCreeperRenderer.java"
        );

        assertTrue(registriesSource.contains("ENTITY_TYPES.register(\n        \"cavenic_creeper\""));
        assertTrue(registriesSource.contains("EntityType.Builder.of(CavenicCreeper::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains(".sized(0.6F, 1.7F)"));
        assertTrue(registriesSource.contains("ITEMS.register(\n        \"cavenic_creeper_spawn_egg\""));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CAVENIC_CREEPER.get(), 0xAAAAAA, 0x2E8B57, new Item.Properties())"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_BOW.get())",
            "output.accept(CAVENIC_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SKELETON_SPAWN_EGG.get())",
            "output.accept(CAVENIC_CREEPER_SPAWN_EGG.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends Creeper"));
        assertTrue(entitySource.contains("this.xpReward = 13;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains("Creeper.createAttributes()"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 30.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.KNOCKBACK_RESISTANCE, 0.85D)"));
        assertTrue(entitySource.contains(".add(Attributes.MOVEMENT_SPEED, 0.2D)"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_WEIGHT = 30;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MIN_COUNT = 1;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MAX_COUNT = 1;"));
        assertTrue(entitySource.contains("public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;"));
        assertTrue(entitySource.contains("public static final short LEGACY_FUSE_TIME = 15;"));
        assertTrue(entitySource.contains("public static final byte LEGACY_EXPLOSION_RADIUS = 5;"));
        assertTrue(entitySource.contains("public boolean hurt(DamageSource source, float damage)"));
        assertTrue(entitySource.contains("public void addAdditionalSaveData(CompoundTag tag)"));
        assertTrue(entitySource.contains("DamageTypeTags.IS_FALL"));
        assertTrue(entitySource.contains("DamageTypeTags.IS_FIRE"));
        assertTrue(entitySource.contains("applyLegacyFuseAndExplosionValues()"));
        assertTrue(entitySource.contains("tag.putShort(\"Fuse\", LEGACY_FUSE_TIME);"));
        assertTrue(entitySource.contains("tag.putByte(\"ExplosionRadius\", LEGACY_EXPLOSION_RADIUS);"));
        assertTrue(entitySource.contains("super.readAdditionalSaveData(legacyFuseAndExplosionData);"));
        assertTrue(entitySource.contains("return super.hurt(source, damage);"));
        assertTrue(entitySource.contains("canNaturallySpawnInDimension"));
        assertTrue(entitySource.contains("checkCavenicCreeperSpawnRules"));
        assertTrue(entitySource.contains("CavernNeoForgeDimensions.isCavern"));
        assertTrue(entitySource.contains("Monster.checkMonsterSpawnRules"));
        assertTrue(entitySource.contains("return EntityType.CREEPER.getDefaultLootTable();"));
        assertFalse(entitySource.contains("registerGoals("));
        assertFalse(entitySource.contains("explodeCreeper("));
        assertFalse(entitySource.contains("ExplosionEvent"));
        assertFalse(entitySource.contains("Level.ExplosionInteraction"));
        assertFalse(entitySource.contains("EntityCavenicArrow"));
        assertFalse(entitySource.toLowerCase().contains("cavenia"));

        assertTrue(entityEventSource.contains("event.put(ModRegistries.CAVENIC_CREEPER.get(), CavenicCreeper.createAttributes().build())"));
        assertTrue(entityEventSource.contains("RegisterSpawnPlacementsEvent"));
        assertTrue(entityEventSource.contains("CavenicCreeper::checkCavenicCreeperSpawnRules"));
        assertTrue(entityEventSource.contains("CAVENIC_CREEPER.get(),\n            SpawnPlacementTypes.ON_GROUND"));
        assertTrue(entityEventSource.contains("Heightmap.Types.MOTION_BLOCKING_NO_LEAVES"));
        assertTrue(entityEventSource.contains("RegisterSpawnPlacementsEvent.Operation.REPLACE"));
        assertTrue(mainEntrypoint.contains("NeoForge.EVENT_BUS.register(new CavenicCreeperLootEvents());"));
        assertTrue(dropEventSource.contains("LivingDropsEvent"));
        assertTrue(dropEventSource.contains("event.getEntity() instanceof CavenicCreeper creeper"));
        assertTrue(dropEventSource.contains("CavenicCreeperLootPolicy.ORB_DROP_ROLL_BOUND"));
        assertTrue(dropEventSource.contains("CavenicCreeperLootPolicy.shouldDropOrb(orbRoll)"));
        assertTrue(dropEventSource.contains("new ItemStack(ModRegistries.CAVENIC_ORB.get())"));
        assertTrue(dropEventSource.contains("creeper.getY() + 0.5D"));
        assertTrue(lootPolicySource.contains("public static final int ORB_DROP_ROLL_BOUND = 5;"));
        assertFalse(dropEventSource.toLowerCase().contains("economy"));
        assertFalse(dropEventSource.toLowerCase().contains("progress"));
        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CAVENIC_CREEPER.get(), CavenicCreeperRenderer::new);"));
        assertTrue(rendererSource.contains("extends CreeperRenderer"));
        assertTrue(rendererSource.contains("textures/entity/cavenic_creeper.png"));

        assertFalse(registriesSource.contains("cavenic_spider"));
        assertFalse(registriesSource.contains("cavenic_witch"));
        assertFalse(registriesSource.contains("cavenic_bear"));
        assertFalse(registriesSource.toLowerCase().contains("cavenia"));
        assertFalse(registriesSource.contains("EntityRapidArrow"));
        assertFalse(registriesSource.contains("EntityTorchArrow"));
        assertFalse(registriesSource.contains("EntityCavenicArrow"));
    }

    @Test
    void cavenicCreeperResourcesCoverLangSpawnEggModelTextureAndNaturalSpawnDataClasspath() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/cavenic_creeper_spawn_egg.json");
        JsonObject biomeModifier = readJsonResource("data/cavernreborn/neoforge/biome_modifier/cavenic_creeper_spawns.json");
        JsonObject biomeTag = readJsonResource("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_creeper.json");

        assertEquals("Cavenic Creeper", lang.get("entity.cavernreborn.cavenic_creeper").getAsString());
        assertEquals("Cavenic Creeper Spawn Egg", lang.get("item.cavernreborn.cavenic_creeper_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());
        assertEquals("neoforge:add_spawns", biomeModifier.get("type").getAsString());
        assertEquals("#cavernreborn:spawns_cavenic_creeper", biomeModifier.get("biomes").getAsString());

        JsonObject spawner = biomeModifier.getAsJsonObject("spawners");
        assertEquals("cavernreborn:cavenic_creeper", spawner.get("type").getAsString());
        assertEquals(30, spawner.get("weight").getAsInt());
        assertEquals(1, spawner.get("minCount").getAsInt());
        assertEquals(1, spawner.get("maxCount").getAsInt());

        assertFalse(biomeTag.get("replace").getAsBoolean());
        assertEquals(List.of(
            "cavernreborn:stone_depths",
            "cavernreborn:lush_grotto",
            "cavernreborn:dripstone_grotto",
            "cavernreborn:highland_hollows"
        ), StreamSupport.stream(biomeTag.getAsJsonArray("values").spliterator(), false).map(element -> element.getAsString()).toList());

        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_creeper_spawn_egg.json"), "assets/cavernreborn/models/item/cavenic_creeper_spawn_egg.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/entity/cavenic_creeper.png"), "assets/cavernreborn/textures/entity/cavenic_creeper.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/neoforge/biome_modifier/cavenic_creeper_spawns.json"), "data/cavernreborn/neoforge/biome_modifier/cavenic_creeper_spawns.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_creeper.json"), "data/cavernreborn/tags/worldgen/biome/spawns_cavenic_creeper.json");
        assertMissingResource("data/cavernreborn/loot_table/entities/cavenic_creeper.json");
        assertMissingResource("data/cavernreborn/loot_tables/entities/cavenic_creeper.json");
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CavenicCreeperResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CavenicCreeperResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CavenicCreeperResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
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
