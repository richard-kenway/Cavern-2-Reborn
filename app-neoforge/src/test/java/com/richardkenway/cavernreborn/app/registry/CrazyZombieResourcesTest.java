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
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class CrazyZombieResourcesTest {
    @Test
    void crazyZombieRegistersWithDedicatedEntitySpawnEggRendererAndBoundedDamageLootAndKnockbackFollowUps() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CrazyZombie.java"
        );
        String entityEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
        );
        String dropEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CrazyZombieLootEvents.java"
        );
        String mainEntrypoint = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"
        );
        String lootPolicySource = readProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "loot", "CrazyZombieLootPolicy.java"
        );
        String clientSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        );
        String rendererSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CrazyZombieRenderer.java"
        );

        assertEquals(
            List.of("crazy_zombie"),
            extractMatches(registriesSource, Pattern.compile("ENTITY_TYPES\\.register\\(\\s*\"(crazy_[a-z_]+)\"", Pattern.MULTILINE))
        );
        assertEquals(
            List.of("crazy_zombie_spawn_egg"),
            extractMatches(registriesSource, Pattern.compile("ITEMS\\.register\\(\\s*\"(crazy_[a-z_]+_spawn_egg)\"", Pattern.MULTILINE))
        );
        assertTrue(registriesSource.contains("EntityType.Builder.of(CrazyZombie::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains(".build(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, \"crazy_zombie\").toString())"));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CRAZY_ZOMBIE.get(), 0x909090, 0x00A0A0, new Item.Properties())"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_BOW.get())",
            "output.accept(CAVENIC_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SKELETON_SPAWN_EGG.get())",
            "output.accept(CAVENIC_CREEPER_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SPIDER_SPAWN_EGG.get())",
            "output.accept(CAVENIC_WITCH_SPAWN_EGG.get())",
            "output.accept(CAVENIC_BEAR_SPAWN_EGG.get())",
            "output.accept(CRAZY_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends Zombie"));
        assertFalse(entitySource.contains("extends CavenicZombie"));
        assertTrue(entitySource.contains("this.xpReward = 50;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains("Zombie.createAttributes()"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 2000.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.FOLLOW_RANGE, 50.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.ATTACK_DAMAGE, 7.5D)"));
        assertTrue(entitySource.contains(".add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)"));
        assertTrue(entitySource.contains("public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;"));
        assertTrue(entitySource.contains("return EntityType.ZOMBIE.getDefaultLootTable();"));
        assertTrue(entitySource.contains("public boolean hurt(DamageSource source, float damage)"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FALL)"));
        assertTrue(entitySource.contains("damage *= LEGACY_FALL_DAMAGE_MULTIPLIER;"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FIRE)"));
        assertTrue(entitySource.contains("return super.hurt(source, damage);"));
        assertTrue(entitySource.contains("public static final int LEGACY_KNOCKBACK_TRIGGER_ROLL_BOUND = 5;"));
        assertTrue(entitySource.contains("public static final int LEGACY_KNOCKBACK_POWER_VARIANT_COUNT = 3;"));
        assertTrue(entitySource.contains("public static final int LEGACY_KNOCKBACK_BASE_POWER = 3;"));
        assertTrue(entitySource.contains("public static final float LEGACY_KNOCKBACK_STRENGTH_MULTIPLIER = 0.5F;"));
        assertTrue(entitySource.contains("public static final double LEGACY_NON_LIVING_KNOCKBACK_VERTICAL_BOOST = 0.1D;"));
        assertTrue(entitySource.contains("public boolean doHurtTarget(Entity target)"));
        assertTrue(entitySource.contains("int triggerRoll = random.nextInt(LEGACY_KNOCKBACK_TRIGGER_ROLL_BOUND);"));
        assertTrue(entitySource.contains("random.nextInt(LEGACY_KNOCKBACK_POWER_VARIANT_COUNT) + LEGACY_KNOCKBACK_BASE_POWER"));
        assertTrue(entitySource.contains("public static int getLegacyKnockbackPower(int triggerRoll, int magnitudeRoll)"));
        assertTrue(entitySource.contains("public boolean tryApplyLegacyKnockback(Entity target, int power)"));
        assertTrue(entitySource.contains("target instanceof LivingEntity livingTarget"));
        assertTrue(entitySource.contains("livingTarget.knockback(knockbackStrength, Mth.sin(yawRadians), -Mth.cos(yawRadians));"));
        assertTrue(entitySource.contains("target.push("));
        assertTrue(entitySource.contains("LEGACY_NON_LIVING_KNOCKBACK_VERTICAL_BOOST"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_WEIGHT"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_MIN_COUNT"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_MAX_COUNT"));
        assertFalse(entitySource.contains("canNaturallySpawnInDimension"));
        assertFalse(entitySource.contains("checkCrazyZombieSpawnRules"));
        assertFalse(entitySource.contains("getMaxSpawnClusterSize()"));
        assertFalse(entitySource.contains("registerGoals()"));
        assertFalse(entitySource.contains("onUpdate()"));
        assertFalse(entitySource.contains("updateAITasks()"));
        assertFalse(entitySource.contains("attackEntityAsMob("));
        assertFalse(entitySource.contains("BossEvent"));
        assertFalse(entitySource.contains("ServerBossEvent"));
        assertFalse(entitySource.contains("setDarkenSky"));
        assertFalse(entitySource.contains("ParticleCrazyMob"));
        assertFalse(entitySource.contains("dropLoot("));
        assertFalse(entitySource.contains("ItemMagicBook"));
        assertFalse(entitySource.contains("magic_book"));
        assertFalse(entitySource.toLowerCase().contains("cavenia"));

        assertTrue(entityEventSource.contains("event.put(ModRegistries.CRAZY_ZOMBIE.get(), CrazyZombie.createAttributes().build())"));
        assertFalse(entityEventSource.contains("CrazyZombie::check"));
        assertFalse(entityEventSource.contains("ModRegistries.CRAZY_ZOMBIE.get(),\n            SpawnPlacementTypes"));
        assertTrue(mainEntrypoint.contains("NeoForge.EVENT_BUS.register(new CrazyZombieLootEvents());"));
        assertTrue(lootPolicySource.contains("public static final int ORB_DROP_ROLL_BOUND = 8;"));
        assertTrue(lootPolicySource.contains("return roll >= 0 && roll < ORB_DROP_ROLL_BOUND && roll == 0;"));
        assertTrue(dropEventSource.contains("LivingDropsEvent"));
        assertTrue(dropEventSource.contains("event.getEntity() instanceof CrazyZombie zombie"));
        assertTrue(dropEventSource.contains("CrazyZombieLootPolicy.ORB_DROP_ROLL_BOUND"));
        assertTrue(dropEventSource.contains("CrazyZombieLootPolicy.shouldDropOrb(orbRoll)"));
        assertTrue(dropEventSource.contains("new ItemStack(ModRegistries.CAVENIC_ORB.get())"));
        assertTrue(dropEventSource.contains("zombie.getY() + 0.5D"));
        assertFalse(dropEventSource.toLowerCase().contains("economy"));
        assertFalse(dropEventSource.toLowerCase().contains("progress"));
        assertFalse(dropEventSource.contains("BossEvent"));
        assertFalse(dropEventSource.contains("ServerBossEvent"));
        assertFalse(dropEventSource.contains("ParticleCrazyMob"));
        assertFalse(dropEventSource.contains("knockBack("));
        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CRAZY_ZOMBIE.get(), CrazyZombieRenderer::new);"));
        assertTrue(rendererSource.contains("extends ZombieRenderer"));
        assertTrue(rendererSource.contains("getTextureLocation(Zombie entity)"));
        assertTrue(rendererSource.contains("textures/entity/crazy_zombie.png"));

        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_zombie_spawns.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_zombie.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "loot_table", "entities", "crazy_zombie.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "resources", "data", "cavernreborn", "loot_tables", "entities", "crazy_zombie.json");
    }

    @Test
    void crazyZombieResourcesCoverLangSpawnEggModelAndTextureClasspath() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/crazy_zombie_spawn_egg.json");

        assertEquals("Crazy Zombie", lang.get("entity.cavernreborn.crazy_zombie").getAsString());
        assertEquals("Crazy Zombie Spawn Egg", lang.get("item.cavernreborn.crazy_zombie_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());

        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/crazy_zombie_spawn_egg.json"), "assets/cavernreborn/models/item/crazy_zombie_spawn_egg.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/entity/crazy_zombie.png"), "assets/cavernreborn/textures/entity/crazy_zombie.png");
        assertMissingResource("data/cavernreborn/neoforge/biome_modifier/crazy_zombie_spawns.json");
        assertMissingResource("data/cavernreborn/tags/worldgen/biome/spawns_crazy_zombie.json");
    }

    private static List<String> extractMatches(String input, Pattern pattern) {
        return pattern.matcher(input).results().map(match -> match.group(1)).toList();
    }

    private static void assertInOrder(String input, List<String> snippets) {
        int start = 0;

        for (String snippet : snippets) {
            int index = input.indexOf(snippet, start);
            assertTrue(index >= start, "Expected to find snippet after previous entry: " + snippet);
            start = index + snippet.length();
        }
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CrazyZombieResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CrazyZombieResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CrazyZombieResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
    }

    private static void assertMissingProjectFile(String first, String... more) {
        assertFalse(Files.exists(resolveProjectFile(first, more)), "Did not expect project file: " + Path.of(first, more));
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

        throw new IllegalStateException("Could not find project file: " + Path.of(first, more));
    }

    private static Path resolveProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate) || i == 4) {
                return candidate;
            }
            current = current.getParent();
        }

        return Path.of(first, more);
    }
}
