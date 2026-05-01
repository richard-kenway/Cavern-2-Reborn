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

class CrazyCreeperResourcesTest {
    @Test
    void crazyCreeperRegistersWithDedicatedEntitySpawnEggRendererAndExplicitLootBoundaries() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CrazyCreeper.java"
        );
        String dropEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CrazyCreeperLootEvents.java"
        );
        String dropPolicySource = readProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "loot", "CrazyCreeperLootPolicy.java"
        );
        String cavenicCreeperSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicCreeper.java"
        );
        String modSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"
        );
        String entityEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
        );
        String clientSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        );
        String rendererSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CrazyCreeperRenderer.java"
        );
        String langSource = readProjectFile(
            "app-neoforge", "src", "main", "resources", "assets", "cavernreborn", "lang", "en_us.json"
        );

        assertEquals(
            List.of("crazy_zombie", "crazy_skeleton", "crazy_creeper"),
            extractMatches(registriesSource, Pattern.compile("ENTITY_TYPES\\.register\\(\\s*\"(crazy_[a-z_]+)\"", Pattern.MULTILINE))
        );
        assertEquals(
            List.of("crazy_zombie_spawn_egg", "crazy_skeleton_spawn_egg", "crazy_creeper_spawn_egg"),
            extractMatches(registriesSource, Pattern.compile("ITEMS\\.register\\(\\s*\"(crazy_[a-z_]+_spawn_egg)\"", Pattern.MULTILINE))
        );
        assertTrue(registriesSource.contains("ENTITY_TYPES.register(\n        \"crazy_creeper\""));
        assertTrue(registriesSource.contains("EntityType.Builder.of(CrazyCreeper::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains(".build(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, \"crazy_creeper\").toString())"));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CRAZY_CREEPER.get(), 0x909090, 0x2E8B57, new Item.Properties())"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_BOW.get())",
            "output.accept(CAVENIC_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SKELETON_SPAWN_EGG.get())",
            "output.accept(CAVENIC_CREEPER_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SPIDER_SPAWN_EGG.get())",
            "output.accept(CAVENIC_WITCH_SPAWN_EGG.get())",
            "output.accept(CAVENIC_BEAR_SPAWN_EGG.get())",
            "output.accept(CRAZY_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(CRAZY_SKELETON_SPAWN_EGG.get())",
            "output.accept(CRAZY_CREEPER_SPAWN_EGG.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends Creeper"));
        assertFalse(entitySource.contains("extends CavenicCreeper"));
        assertTrue(entitySource.contains("this.xpReward = 50;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains("Creeper.createAttributes()"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 1500.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.MOVEMENT_SPEED, 0.23D)"));
        assertTrue(entitySource.contains("return EntityType.CREEPER.getDefaultLootTable();"));
        assertFalse(entitySource.contains("FOLLOW_RANGE"));
        assertFalse(entitySource.contains("ATTACK_DAMAGE"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_WEIGHT"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_MIN_COUNT"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_MAX_COUNT"));
        assertFalse(entitySource.contains("canNaturallySpawnInDimension"));
        assertFalse(entitySource.contains("checkCrazyCreeperSpawnRules"));
        assertFalse(entitySource.contains("ServerBossEvent"));
        assertFalse(entitySource.contains("BossEvent"));
        assertFalse(entitySource.contains("ParticleCrazyMob"));
        assertFalse(entitySource.contains("CRAZY_MOB_PARTICLE"));
        assertFalse(entitySource.contains("hurt(DamageSource source, float damage)"));
        assertFalse(entitySource.contains("aiStep()"));
        assertFalse(entitySource.contains("customServerAiStep()"));
        assertFalse(entitySource.contains("startSeenByPlayer"));
        assertFalse(entitySource.contains("stopSeenByPlayer"));
        assertFalse(entitySource.contains("explodeCreeper"));
        assertFalse(entitySource.contains("Explosion"));
        assertFalse(entitySource.contains("fuseTime"));
        assertFalse(entitySource.contains("timeSinceIgnited"));
        assertFalse(entitySource.contains("lastActiveTime"));
        assertFalse(entitySource.contains("explosionRadius"));
        assertFalse(entitySource.contains("thunderHit"));
        assertFalse(entitySource.contains("powered"));
        assertFalse(entitySource.contains("charged"));
        assertFalse(entitySource.contains("lightning"));
        assertFalse(entitySource.contains("registerGoals("));
        assertFalse(entitySource.toLowerCase().contains("cavenia"));
        assertFalse(entitySource.toLowerCase().contains("magic_book"));

        assertTrue(cavenicCreeperSource.contains("public static final short LEGACY_FUSE_TIME = 15;"));
        assertTrue(cavenicCreeperSource.contains("public static final byte LEGACY_EXPLOSION_RADIUS = 5;"));
        assertTrue(dropEventSource.contains("LivingDropsEvent"));
        assertTrue(dropEventSource.contains("event.getEntity() instanceof CrazyCreeper creeper"));
        assertTrue(dropEventSource.contains("CrazyCreeperLootPolicy.ORB_DROP_ROLL_BOUND"));
        assertTrue(dropEventSource.contains("CrazyCreeperLootPolicy.shouldDropOrb(orbRoll)"));
        assertTrue(dropEventSource.contains("new ItemStack(ModRegistries.CAVENIC_ORB.get())"));
        assertTrue(dropEventSource.contains("creeper.getY() + 0.5D"));
        assertTrue(dropEventSource.contains("orbDrop.setDefaultPickUpDelay();"));
        assertFalse(dropEventSource.toLowerCase().contains("economy"));
        assertFalse(dropEventSource.toLowerCase().contains("progression"));
        assertFalse(dropEventSource.contains("BossEvent"));
        assertFalse(dropEventSource.contains("ParticleCrazyMob"));
        assertFalse(dropEventSource.contains("Explosion"));
        assertFalse(dropEventSource.toLowerCase().contains("charged"));
        assertTrue(dropPolicySource.contains("public static final int ORB_DROP_ROLL_BOUND = 5;"));
        assertTrue(dropPolicySource.contains("return roll >= 0 && roll < ORB_DROP_ROLL_BOUND && roll == 0;"));
        assertTrue(modSource.contains("NeoForge.EVENT_BUS.register(new CrazyCreeperLootEvents());"));
        assertTrue(entityEventSource.contains("event.put(ModRegistries.CRAZY_CREEPER.get(), CrazyCreeper.createAttributes().build())"));
        assertFalse(entityEventSource.contains("ModRegistries.CRAZY_CREEPER.get(),\n            SpawnPlacementTypes"));

        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CRAZY_CREEPER.get(), CrazyCreeperRenderer::new);"));
        assertTrue(rendererSource.contains("extends CreeperRenderer"));
        assertTrue(rendererSource.contains("textures/entity/crazy_creeper.png"));
        assertFalse(rendererSource.contains("scale("));
        assertTrue(langSource.contains("\"entity.cavernreborn.crazy_creeper\": \"Crazy Creeper\""));
        assertTrue(langSource.contains("\"item.cavernreborn.crazy_creeper_spawn_egg\": \"Crazy Creeper Spawn Egg\""));

        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_creeper_spawns.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_creeper.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "loot_table", "entities", "crazy_creeper.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "loot_tables", "entities", "crazy_creeper.json"
        );
    }

    @Test
    void crazyCreeperResourcesCoverLangSpawnEggModelAndTextureClasspath() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/crazy_creeper_spawn_egg.json");

        assertEquals("Crazy Creeper", lang.get("entity.cavernreborn.crazy_creeper").getAsString());
        assertEquals("Crazy Creeper Spawn Egg", lang.get("item.cavernreborn.crazy_creeper_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());

        assertClassPathOrigin(
            resourceUrl("assets/cavernreborn/models/item/crazy_creeper_spawn_egg.json"),
            "assets/cavernreborn/models/item/crazy_creeper_spawn_egg.json"
        );
        assertClassPathOrigin(
            resourceUrl("assets/cavernreborn/textures/entity/crazy_creeper.png"),
            "assets/cavernreborn/textures/entity/crazy_creeper.png"
        );
        assertMissingResource("data/cavernreborn/neoforge/biome_modifier/crazy_creeper_spawns.json");
        assertMissingResource("data/cavernreborn/tags/worldgen/biome/spawns_crazy_creeper.json");
        assertMissingResource("data/cavernreborn/loot_table/entities/crazy_creeper.json");
        assertMissingResource("data/cavernreborn/loot_tables/entities/crazy_creeper.json");
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
        InputStream inputStream = CrazyCreeperResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CrazyCreeperResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CrazyCreeperResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
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

    private static void assertMissingProjectFile(String first, String... more) {
        Path current = Path.of("").toAbsolutePath();

        for (int i = 0; i < 5 && current != null; i++) {
            if (Files.exists(current.resolve(Path.of(first, more)))) {
                throw new AssertionError("Did not expect project file: " + current.resolve(Path.of(first, more)));
            }
            current = current.getParent();
        }
    }
}
