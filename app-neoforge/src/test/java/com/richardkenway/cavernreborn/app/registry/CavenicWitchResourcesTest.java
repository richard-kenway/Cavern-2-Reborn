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

class CavenicWitchResourcesTest {
    @Test
    void cavenicWitchRegistersWithDedicatedEntitySpawnEggRendererNaturalSpawnLootDamageAndVanillaWitchBaselineBoundaries() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String appSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicWitch.java"
        );
        String lootEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicWitchLootEvents.java"
        );
        String entityEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
        );
        String lootPolicySource = readProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "loot", "CavenicWitchLootPolicy.java"
        );
        String clientSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        );
        String rendererSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CavenicWitchRenderer.java"
        );

        assertTrue(registriesSource.contains("ENTITY_TYPES.register(\n        \"cavenic_witch\""));
        assertTrue(registriesSource.contains("EntityType.Builder.of(CavenicWitch::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains(".sized(0.6F, 1.95F)"));
        assertTrue(registriesSource.contains("ITEMS.register(\n        \"cavenic_witch_spawn_egg\""));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CAVENIC_WITCH.get(), 0xAAAAAA, 0x4A5348, new Item.Properties())"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_BOW.get())",
            "output.accept(CAVENIC_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SKELETON_SPAWN_EGG.get())",
            "output.accept(CAVENIC_CREEPER_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SPIDER_SPAWN_EGG.get())",
            "output.accept(CAVENIC_WITCH_SPAWN_EGG.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends Witch"));
        assertTrue(entitySource.contains("this.xpReward = 12;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains("Witch.createAttributes()"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_WEIGHT = 15;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MIN_COUNT = 1;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MAX_COUNT = 1;"));
        assertTrue(entitySource.contains("public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 50.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.FOLLOW_RANGE, 32.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)"));
        assertTrue(entitySource.contains("public boolean hurt(DamageSource source, float damage)"));
        assertTrue(entitySource.contains("DamageTypeTags.IS_FALL"));
        assertTrue(entitySource.contains("DamageTypeTags.IS_FIRE"));
        assertTrue(entitySource.contains("return super.hurt(source, damage);"));
        assertTrue(entitySource.contains("return EntityType.WITCH.getDefaultLootTable();"));
        assertTrue(entitySource.contains("canNaturallySpawnInDimension"));
        assertTrue(entitySource.contains("checkCavenicWitchSpawnRules"));
        assertTrue(entitySource.contains("CavernNeoForgeDimensions.isCavern"));
        assertTrue(entitySource.contains("Monster.checkMonsterSpawnRules"));
        assertFalse(entitySource.contains("registerGoals("));
        assertFalse(entitySource.contains("performRangedAttack("));
        assertFalse(entitySource.contains("aiStep("));
        assertFalse(entitySource.contains("isEntityInvulnerable("));
        assertFalse(entitySource.contains("setAttackTarget("));
        assertFalse(entitySource.contains("cavenic_orb"));
        assertFalse(entitySource.contains("ItemMagicBook"));
        assertFalse(entitySource.toLowerCase().contains("cavenia"));
        assertFalse(entitySource.contains("EntityRapidArrow"));
        assertFalse(entitySource.contains("EntityTorchArrow"));
        assertFalse(entitySource.contains("EntityCavenicArrow"));
        assertFalse(entitySource.contains("ItemMagicBook"));
        assertFalse(entitySource.contains("magic_book"));

        assertTrue(lootPolicySource.contains("public static final int ORB_DROP_ROLL_BOUND = 5;"));
        assertTrue(lootPolicySource.contains("public static boolean shouldDropOrb(int roll)"));
        assertFalse(lootPolicySource.contains("MagicBook"));

        assertTrue(lootEventSource.contains("LivingDropsEvent"));
        assertTrue(lootEventSource.contains("event.getEntity() instanceof CavenicWitch witch"));
        assertTrue(lootEventSource.contains("CavenicWitchLootPolicy.ORB_DROP_ROLL_BOUND"));
        assertTrue(lootEventSource.contains("witch.getRandom().nextInt(CavenicWitchLootPolicy.ORB_DROP_ROLL_BOUND)"));
        assertTrue(lootEventSource.contains("new ItemStack(ModRegistries.CAVENIC_ORB.get())"));
        assertTrue(lootEventSource.contains("witch.getY() + 0.5D"));
        assertFalse(lootEventSource.contains("ItemMagicBook"));
        assertFalse(lootEventSource.contains("magic_book"));
        assertFalse(lootEventSource.toLowerCase().contains("economy"));
        assertFalse(lootEventSource.toLowerCase().contains("progression"));
        assertFalse(lootEventSource.contains("lootingModifier"));

        assertTrue(entityEventSource.contains("event.put(ModRegistries.CAVENIC_WITCH.get(), CavenicWitch.createAttributes().build())"));
        assertTrue(entityEventSource.contains("RegisterSpawnPlacementsEvent"));
        assertTrue(entityEventSource.contains("CavenicWitch::checkCavenicWitchSpawnRules"));
        assertTrue(entityEventSource.contains("CAVENIC_WITCH.get(),\n            SpawnPlacementTypes.ON_GROUND"));
        assertTrue(entityEventSource.contains("Heightmap.Types.MOTION_BLOCKING_NO_LEAVES"));
        assertTrue(entityEventSource.contains("RegisterSpawnPlacementsEvent.Operation.REPLACE"));
        assertTrue(appSource.contains("NeoForge.EVENT_BUS.register(new CavenicWitchLootEvents());"));
        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CAVENIC_WITCH.get(), CavenicWitchRenderer::new);"));
        assertTrue(rendererSource.contains("extends WitchRenderer"));
        assertTrue(rendererSource.contains("getTextureLocation(Witch entity)"));
        assertTrue(rendererSource.contains("textures/entity/cavenic_witch.png"));

        assertFalse(registriesSource.contains("cavenic_bear"));
        assertFalse(registriesSource.contains("magic_book"));
        assertFalse(registriesSource.contains("ItemMagicBook"));
        assertFalse(registriesSource.toLowerCase().contains("cavenia"));
    }

    @Test
    void cavenicWitchResourcesCoverLangSpawnEggModelTextureAndNaturalSpawnDataClasspath() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/cavenic_witch_spawn_egg.json");
        JsonObject biomeModifier = readJsonResource("data/cavernreborn/neoforge/biome_modifier/cavenic_witch_spawns.json");
        JsonObject biomeTag = readJsonResource("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_witch.json");

        assertEquals("Cavenic Witch", lang.get("entity.cavernreborn.cavenic_witch").getAsString());
        assertEquals("Cavenic Witch Spawn Egg", lang.get("item.cavernreborn.cavenic_witch_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());
        assertEquals("neoforge:add_spawns", biomeModifier.get("type").getAsString());
        assertEquals("#cavernreborn:spawns_cavenic_witch", biomeModifier.get("biomes").getAsString());

        JsonObject spawner = biomeModifier.getAsJsonObject("spawners");
        assertEquals("cavernreborn:cavenic_witch", spawner.get("type").getAsString());
        assertEquals(15, spawner.get("weight").getAsInt());
        assertEquals(1, spawner.get("minCount").getAsInt());
        assertEquals(1, spawner.get("maxCount").getAsInt());

        assertFalse(biomeTag.get("replace").getAsBoolean());
        assertEquals(List.of(
            "cavernreborn:stone_depths",
            "cavernreborn:lush_grotto",
            "cavernreborn:dripstone_grotto",
            "cavernreborn:highland_hollows"
        ), StreamSupport.stream(biomeTag.getAsJsonArray("values").spliterator(), false).map(element -> element.getAsString()).toList());

        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_witch_spawn_egg.json"), "assets/cavernreborn/models/item/cavenic_witch_spawn_egg.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/entity/cavenic_witch.png"), "assets/cavernreborn/textures/entity/cavenic_witch.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/neoforge/biome_modifier/cavenic_witch_spawns.json"), "data/cavernreborn/neoforge/biome_modifier/cavenic_witch_spawns.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_witch.json"), "data/cavernreborn/tags/worldgen/biome/spawns_cavenic_witch.json");
        assertMissingResource("data/cavernreborn/loot_table/entities/cavenic_witch.json");
        assertMissingResource("data/cavernreborn/loot_tables/entities/cavenic_witch.json");
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CavenicWitchResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CavenicWitchResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CavenicWitchResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
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
