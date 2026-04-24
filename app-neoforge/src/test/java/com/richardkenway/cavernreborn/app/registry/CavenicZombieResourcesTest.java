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

class CavenicZombieResourcesTest {
    @Test
    void cavenicZombieRegistersWithDedicatedEntitySpawnEggAndStableCreativePlacement() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicZombie.java"
        );
        String entityEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
        );
        String dropEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicZombieLootEvents.java"
        );
        String mainEntrypoint = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"
        );
        String clientSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        );
        String rendererSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CavenicZombieRenderer.java"
        );

        assertTrue(registriesSource.contains("DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, CavernReborn.MOD_ID);"));
        assertTrue(registriesSource.contains("ENTITY_TYPES.register(\n        \"cavenic_zombie\""));
        assertTrue(registriesSource.contains("EntityType.Builder.of(CavenicZombie::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains("ITEMS.register(\n        \"cavenic_zombie_spawn_egg\""));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CAVENIC_ZOMBIE.get(), 0xAAAAAA, 0x00A0A0, new Item.Properties())"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_ORB.get())",
            "output.accept(CAVENIC_SWORD.get())",
            "output.accept(CAVENIC_AXE.get())",
            "output.accept(CAVENIC_BOW.get())",
            "output.accept(CAVENIC_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends Zombie"));
        assertTrue(entitySource.contains("this.xpReward = 12;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 50.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.FOLLOW_RANGE, 50.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.ATTACK_DAMAGE, 5.0D)"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_WEIGHT = 30;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MIN_COUNT = 2;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MAX_COUNT = 2;"));
        assertTrue(entitySource.contains("public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;"));
        assertTrue(entitySource.contains("canNaturallySpawnInDimension"));
        assertTrue(entitySource.contains("checkCavenicZombieSpawnRules"));
        assertTrue(entitySource.contains("CavernNeoForgeDimensions.isCavern"));
        assertTrue(entitySource.contains("Monster.checkMonsterSpawnRules"));
        assertTrue(entitySource.contains("return EntityType.ZOMBIE.getDefaultLootTable();"));
        assertTrue(entitySource.contains("public boolean hurt(DamageSource source, float damage)"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FALL)"));
        assertTrue(entitySource.contains("damage *= LEGACY_FALL_DAMAGE_MULTIPLIER;"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FIRE)"));
        assertTrue(entitySource.contains("return super.hurt(source, damage);"));
        assertFalse(entitySource.contains("entities/cavenic_zombie"));
        assertFalse(entitySource.contains("cavenic_orb"));

        assertTrue(entityEventSource.contains("EntityAttributeCreationEvent"));
        assertTrue(entityEventSource.contains("event.put(ModRegistries.CAVENIC_ZOMBIE.get(), CavenicZombie.createAttributes().build())"));
        assertTrue(entityEventSource.contains("RegisterSpawnPlacementsEvent"));
        assertTrue(entityEventSource.contains("SpawnPlacementTypes.ON_GROUND"));
        assertTrue(entityEventSource.contains("Heightmap.Types.MOTION_BLOCKING_NO_LEAVES"));
        assertTrue(entityEventSource.contains("CavenicZombie::checkCavenicZombieSpawnRules"));
        assertTrue(entityEventSource.contains("RegisterSpawnPlacementsEvent.Operation.REPLACE"));
        assertTrue(mainEntrypoint.contains("NeoForge.EVENT_BUS.register(new CavenicZombieLootEvents());"));
        assertTrue(dropEventSource.contains("LivingDropsEvent"));
        assertTrue(dropEventSource.contains("event.getEntity() instanceof CavenicZombie zombie"));
        assertTrue(dropEventSource.contains("CavenicZombieLootPolicy.ORB_DROP_ROLL_BOUND"));
        assertTrue(dropEventSource.contains("CavenicZombieLootPolicy.shouldDropOrb(orbRoll)"));
        assertTrue(dropEventSource.contains("new ItemStack(ModRegistries.CAVENIC_ORB.get())"));
        assertTrue(dropEventSource.contains("zombie.getY() + 0.5D"));
        assertFalse(dropEventSource.contains("EntityRapidArrow"));
        assertFalse(dropEventSource.contains("EntityTorchArrow"));
        assertFalse(dropEventSource.toLowerCase().contains("economy"));

        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CAVENIC_ZOMBIE.get(), CavenicZombieRenderer::new);"));
        assertTrue(rendererSource.contains("extends ZombieRenderer"));
        assertTrue(rendererSource.contains("textures/entity/cavenic_zombie.png"));

        assertFalse(registriesSource.contains("cavenic_creeper"));
        assertFalse(registriesSource.contains("cavenic_spider"));
        assertFalse(registriesSource.contains("cavenic_witch"));
        assertFalse(registriesSource.contains("cavenic_bear"));
        assertFalse(registriesSource.toLowerCase().contains("cavenia"));
        assertFalse(generatedResourcesContain("cavenic_zombie"));
        assertFalse(registriesSource.contains("EntityRapidArrow"));
        assertFalse(registriesSource.contains("EntityTorchArrow"));
    }

    @Test
    void cavenicZombieResourcesCoverLangSpawnEggModelTextureAndNaturalSpawnDataClasspath() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/cavenic_zombie_spawn_egg.json");
        JsonObject biomeModifier = readJsonResource("data/cavernreborn/neoforge/biome_modifier/cavenic_zombie_spawns.json");
        JsonObject biomeTag = readJsonResource("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_zombie.json");

        assertEquals("Cavenic Zombie", lang.get("entity.cavernreborn.cavenic_zombie").getAsString());
        assertEquals("Cavenic Zombie Spawn Egg", lang.get("item.cavernreborn.cavenic_zombie_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());
        assertEquals("neoforge:add_spawns", biomeModifier.get("type").getAsString());
        assertEquals("#cavernreborn:spawns_cavenic_zombie", biomeModifier.get("biomes").getAsString());

        JsonObject spawner = biomeModifier.getAsJsonObject("spawners");
        assertEquals("cavernreborn:cavenic_zombie", spawner.get("type").getAsString());
        assertEquals(30, spawner.get("weight").getAsInt());
        assertEquals(2, spawner.get("minCount").getAsInt());
        assertEquals(2, spawner.get("maxCount").getAsInt());

        assertFalse(biomeTag.get("replace").getAsBoolean());
        List<String> biomeValues = StreamSupport.stream(biomeTag.getAsJsonArray("values").spliterator(), false)
            .map(element -> element.getAsString())
            .toList();
        assertEquals(List.of(
            "cavernreborn:stone_depths",
            "cavernreborn:lush_grotto",
            "cavernreborn:dripstone_grotto",
            "cavernreborn:highland_hollows"
        ), biomeValues);

        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_zombie_spawn_egg.json"), "assets/cavernreborn/models/item/cavenic_zombie_spawn_egg.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/entity/cavenic_zombie.png"), "assets/cavernreborn/textures/entity/cavenic_zombie.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/neoforge/biome_modifier/cavenic_zombie_spawns.json"), "data/cavernreborn/neoforge/biome_modifier/cavenic_zombie_spawns.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_zombie.json"), "data/cavernreborn/tags/worldgen/biome/spawns_cavenic_zombie.json");
        assertMissingResource("data/cavernreborn/loot_table/entities/cavenic_zombie.json");
        assertMissingResource("data/cavernreborn/loot_tables/entities/cavenic_zombie.json");
    }

    private static boolean generatedResourcesContain(String needle) throws IOException {
        Path generatedRoot = resolveProjectFile("app-neoforge", "src", "generated", "resources");

        try (var paths = Files.walk(generatedRoot)) {
            return paths
                .filter(Files::isRegularFile)
                .anyMatch(path -> fileContains(path, needle));
        }
    }

    private static boolean fileContains(Path path, String needle) {
        try {
            return Files.readString(path).contains(needle);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to scan generated resource: " + path, e);
        }
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CavenicZombieResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CavenicZombieResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CavenicZombieResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
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
