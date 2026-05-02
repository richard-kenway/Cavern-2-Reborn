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

class CrazySpiderResourcesTest {
    @Test
    void crazySpiderRegistersWithDedicatedEntitySpawnEggRendererAndExplicitLootAndDamageButDeferredCombatFollowUps() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CrazySpider.java"
        );
        String dropEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CrazySpiderLootEvents.java"
        );
        String dropPolicySource = readProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "loot", "CrazySpiderLootPolicy.java"
        );
        String cavenicSpiderSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicSpider.java"
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
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CrazySpiderRenderer.java"
        );
        String langSource = readProjectFile(
            "app-neoforge", "src", "main", "resources", "assets", "cavernreborn", "lang", "en_us.json"
        );

        assertEquals(
            List.of("crazy_zombie", "crazy_skeleton", "crazy_creeper", "crazy_spider"),
            extractMatches(registriesSource, Pattern.compile("ENTITY_TYPES\\.register\\(\\s*\"(crazy_[a-z_]+)\"", Pattern.MULTILINE))
        );
        assertEquals(
            List.of("crazy_zombie_spawn_egg", "crazy_skeleton_spawn_egg", "crazy_creeper_spawn_egg", "crazy_spider_spawn_egg"),
            extractMatches(registriesSource, Pattern.compile("ITEMS\\.register\\(\\s*\"(crazy_[a-z_]+_spawn_egg)\"", Pattern.MULTILINE))
        );
        assertTrue(registriesSource.contains("ENTITY_TYPES.register(\n        \"crazy_spider\""));
        assertTrue(registriesSource.contains("EntityType.Builder.of(CrazySpider::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains(".build(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, \"crazy_spider\").toString())"));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CRAZY_SPIDER.get(), 0x909090, 0x811F1F, new Item.Properties())"));
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
            "output.accept(CRAZY_SPIDER_SPAWN_EGG.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends Spider"));
        assertFalse(entitySource.contains("extends CavenicSpider"));
        assertTrue(entitySource.contains("this.xpReward = 50;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains("Spider.createAttributes()"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 1500.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.MOVEMENT_SPEED, 0.6D)"));
        assertTrue(entitySource.contains("return EntityType.SPIDER.getDefaultLootTable();"));
        assertFalse(entitySource.contains("FOLLOW_RANGE"));
        assertFalse(entitySource.contains("ATTACK_DAMAGE"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_WEIGHT"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_MIN_COUNT"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_MAX_COUNT"));
        assertFalse(entitySource.contains("canNaturallySpawnInDimension"));
        assertFalse(entitySource.contains("checkCrazySpiderSpawnRules"));
        assertFalse(entitySource.contains("LivingDropsEvent"));
        assertTrue(entitySource.contains("public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;"));
        assertTrue(entitySource.contains("public boolean hurt(DamageSource source, float damage)"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FALL)"));
        assertTrue(entitySource.contains("damage *= LEGACY_FALL_DAMAGE_MULTIPLIER;"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FIRE)"));
        assertTrue(entitySource.contains("return super.hurt(source, damage);"));
        assertFalse(entitySource.contains("ServerBossEvent"));
        assertFalse(entitySource.contains("BossEvent"));
        assertFalse(entitySource.contains("ParticleCrazyMob"));
        assertFalse(entitySource.contains("CRAZY_MOB_PARTICLE"));
        assertFalse(entitySource.contains("MobEffects.BLINDNESS"));
        assertFalse(entitySource.contains("MobEffects.POISON"));
        assertFalse(entitySource.contains("MobEffectInstance"));
        assertFalse(entitySource.contains("doHurtTarget("));
        assertFalse(entitySource.contains("registerGoals("));
        assertFalse(entitySource.toLowerCase().contains("cavenia"));
        assertFalse(entitySource.toLowerCase().contains("magic_book"));

        assertTrue(cavenicSpiderSource.contains("MobEffects.BLINDNESS"));
        assertTrue(cavenicSpiderSource.contains("public boolean doHurtTarget(Entity target)"));
        assertTrue(cavenicSpiderSource.contains("tryApplyLegacyBlindnessOnHit"));
        assertTrue(cavenicSpiderSource.contains("return EntityType.SPIDER.getDefaultLootTable();"));
        assertTrue(dropEventSource.contains("LivingDropsEvent"));
        assertTrue(dropEventSource.contains("event.getEntity() instanceof CrazySpider spider"));
        assertTrue(dropEventSource.contains("CrazySpiderLootPolicy.ORB_DROP_ROLL_BOUND"));
        assertTrue(dropEventSource.contains("CrazySpiderLootPolicy.shouldDropOrb(orbRoll)"));
        assertTrue(dropEventSource.contains("new ItemStack(ModRegistries.CAVENIC_ORB.get())"));
        assertTrue(dropEventSource.contains("spider.getY() + 0.5D"));
        assertTrue(dropEventSource.contains("orbDrop.setDefaultPickUpDelay();"));
        assertFalse(dropEventSource.toLowerCase().contains("economy"));
        assertFalse(dropEventSource.toLowerCase().contains("progression"));
        assertFalse(dropEventSource.contains("BossEvent"));
        assertFalse(dropEventSource.contains("ServerBossEvent"));
        assertFalse(dropEventSource.contains("DamageTypeTags"));
        assertFalse(dropEventSource.contains("ParticleCrazyMob"));
        assertFalse(dropEventSource.contains("MobEffects.BLINDNESS"));
        assertFalse(dropEventSource.contains("MobEffects.POISON"));
        assertFalse(dropEventSource.contains("doHurtTarget("));
        assertFalse(dropEventSource.contains("registerGoals("));
        assertTrue(dropPolicySource.contains("public static final int ORB_DROP_ROLL_BOUND = 8;"));
        assertTrue(dropPolicySource.contains("return roll >= 0 && roll < ORB_DROP_ROLL_BOUND && roll == 0;"));
        assertTrue(modSource.contains("NeoForge.EVENT_BUS.register(new CrazySpiderLootEvents());"));

        assertTrue(entityEventSource.contains("event.put(ModRegistries.CRAZY_SPIDER.get(), CrazySpider.createAttributes().build())"));
        assertFalse(entityEventSource.contains("ModRegistries.CRAZY_SPIDER.get(),\n            SpawnPlacementTypes"));

        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CRAZY_SPIDER.get(), CrazySpiderRenderer::new);"));
        assertTrue(rendererSource.contains("extends SpiderRenderer<CrazySpider>"));
        assertTrue(rendererSource.contains("textures/entity/crazy_spider.png"));
        assertFalse(rendererSource.contains("scale("));
        assertTrue(langSource.contains("\"entity.cavernreborn.crazy_spider\": \"Crazy Spider\""));
        assertTrue(langSource.contains("\"item.cavernreborn.crazy_spider_spawn_egg\": \"Crazy Spider Spawn Egg\""));

        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_spider_spawns.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_spider.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "loot_table", "entities", "crazy_spider.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "loot_tables", "entities", "crazy_spider.json"
        );
    }

    @Test
    void crazySpiderResourcesCoverLangSpawnEggModelAndTextureClasspath() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/crazy_spider_spawn_egg.json");

        assertEquals("Crazy Spider", lang.get("entity.cavernreborn.crazy_spider").getAsString());
        assertEquals("Crazy Spider Spawn Egg", lang.get("item.cavernreborn.crazy_spider_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());

        assertClassPathOrigin(
            resourceUrl("assets/cavernreborn/models/item/crazy_spider_spawn_egg.json"),
            "assets/cavernreborn/models/item/crazy_spider_spawn_egg.json"
        );
        assertClassPathOrigin(
            resourceUrl("assets/cavernreborn/textures/entity/crazy_spider.png"),
            "assets/cavernreborn/textures/entity/crazy_spider.png"
        );
        assertMissingResource("data/cavernreborn/neoforge/biome_modifier/crazy_spider_spawns.json");
        assertMissingResource("data/cavernreborn/tags/worldgen/biome/spawns_crazy_spider.json");
        assertMissingResource("data/cavernreborn/loot_table/entities/crazy_spider.json");
        assertMissingResource("data/cavernreborn/loot_tables/entities/crazy_spider.json");
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
        InputStream inputStream = CrazySpiderResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CrazySpiderResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static String readProjectFile(String first, String... more) throws IOException {
        return Files.readString(resolveProjectFile(first, more), StandardCharsets.UTF_8);
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

    private static void assertClassPathOrigin(URL resourceUrl, String expectedSuffix) {
        String externalForm = resourceUrl.toExternalForm();
        assertTrue(externalForm.contains(expectedSuffix), "Expected classpath resource URL to contain " + expectedSuffix + " but got " + externalForm);
    }

    private static void assertMissingResource(String path) {
        URL url = CrazySpiderResourcesTest.class.getClassLoader().getResource(path);
        assertTrue(url == null, "Expected resource to be absent: " + path);
    }
}
