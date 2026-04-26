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

class CavenicBearResourcesTest {
    @Test
    void cavenicBearRegistersWithDedicatedEntitySpawnEggRendererNaturalSpawnDamageHostileTargetingAndBoundedMeleeBehavior() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicBear.java"
        );
        String entityEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
        );
        String modSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"
        );
        String clientSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        );
        String rendererSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CavenicBearRenderer.java"
        );

        assertTrue(registriesSource.contains("ENTITY_TYPES.register(\n        \"cavenic_bear\""));
        assertTrue(registriesSource.contains("EntityType.Builder.of(CavenicBear::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains(".sized(1.4F, 1.4F)"));
        assertTrue(registriesSource.contains("ITEMS.register(\n        \"cavenic_bear_spawn_egg\""));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CAVENIC_BEAR.get(), 0xAAAAAA, 0xFFFFFF, new Item.Properties())"));
        assertInOrder(registriesSource, List.of(
            "output.accept(CAVENIC_BOW.get())",
            "output.accept(CAVENIC_ZOMBIE_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SKELETON_SPAWN_EGG.get())",
            "output.accept(CAVENIC_CREEPER_SPAWN_EGG.get())",
            "output.accept(CAVENIC_SPIDER_SPAWN_EGG.get())",
            "output.accept(CAVENIC_WITCH_SPAWN_EGG.get())",
            "output.accept(CAVENIC_BEAR_SPAWN_EGG.get())",
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends PolarBear"));
        assertTrue(entitySource.contains("this.xpReward = 13;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains("PolarBear.createAttributes()"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_WEIGHT = 30;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MIN_COUNT = 1;"));
        assertTrue(entitySource.contains("public static final int NATURAL_SPAWN_MAX_COUNT = 1;"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 60.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.MOVEMENT_SPEED, 0.3D)"));
        assertTrue(entitySource.contains(".add(Attributes.ATTACK_DAMAGE, 7.0D)"));
        assertTrue(entitySource.contains("return EntityType.POLAR_BEAR.getDefaultLootTable();"));
        assertTrue(entitySource.contains("public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;"));
        assertTrue(entitySource.contains("public boolean hurt(DamageSource source, float damage)"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FALL)"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FIRE)"));
        assertTrue(entitySource.contains("return super.hurt(source, damage);"));
        assertTrue(entitySource.contains("protected void registerGoals()"));
        assertTrue(entitySource.contains("super.registerGoals();"));
        assertTrue(entitySource.contains("this.goalSelector.removeAllGoals(this::isVanillaPolarBearMeleeGoal);"));
        assertTrue(entitySource.contains("this.goalSelector.addGoal(1, new LegacyCavenicBearMeleeAttackGoal())"));
        assertTrue(entitySource.contains("private boolean isVanillaPolarBearMeleeGoal(Goal goal)"));
        assertTrue(entitySource.contains("goal instanceof MeleeAttackGoal"));
        assertTrue(entitySource.contains("goal.getClass().getSimpleName().equals(\"PolarBearMeleeAttackGoal\")"));
        assertTrue(entitySource.contains("this.targetSelector.removeAllGoals"));
        assertTrue(entitySource.contains("goal instanceof HurtByTargetGoal"));
        assertTrue(entitySource.contains("goal instanceof NearestAttackableTargetGoal"));
        assertTrue(entitySource.contains("goal instanceof ResetUniversalAngerTargetGoal"));
        assertTrue(entitySource.contains("this.targetSelector.addGoal(1, new LegacyCavenicBearHurtByTargetGoal())"));
        assertTrue(entitySource.contains("this.targetSelector.addGoal(2, new LegacyNearestAttackablePlayerTargetGoal())"));
        assertTrue(entitySource.contains("public boolean isLegacyHostileTarget(LivingEntity target)"));
        assertTrue(entitySource.contains("return target instanceof Player;"));
        assertTrue(entitySource.contains("class LegacyCavenicBearHurtByTargetGoal extends HurtByTargetGoal"));
        assertTrue(entitySource.contains("this.alertOthers();"));
        assertTrue(entitySource.contains("mob instanceof PolarBear polarBear && !polarBear.isBaby()"));
        assertTrue(entitySource.contains("class LegacyNearestAttackablePlayerTargetGoal extends NearestAttackableTargetGoal<Player>"));
        assertTrue(entitySource.contains("super(CavenicBear.this, Player.class, 20, true, true, CavenicBear.this::isLegacyHostileTarget);"));
        assertTrue(entitySource.contains("class LegacyCavenicBearMeleeAttackGoal extends MeleeAttackGoal"));
        assertTrue(entitySource.contains("super(CavenicBear.this, 1.25D, true);"));
        assertTrue(entitySource.contains("protected void checkAndPerformAttack(LivingEntity target)"));
        assertTrue(entitySource.contains("double reachSq = this.getLegacyAttackReachSqr(target);"));
        assertTrue(entitySource.contains("distance <= reachSq * 2.0D"));
        assertTrue(entitySource.contains("CavenicBear.this.setStanding(true);"));
        assertTrue(entitySource.contains("CavenicBear.this.playWarningSound();"));
        assertTrue(entitySource.contains("public void stop()"));
        assertTrue(entitySource.contains("private boolean canPerformLegacyAttack(LivingEntity target, double distance, double reachSq)"));
        assertTrue(entitySource.contains("this.mob.getSensing().hasLineOfSight(target)"));
        assertTrue(entitySource.contains("private double getLegacyAttackReachSqr(LivingEntity target)"));
        assertTrue(entitySource.contains("return 4.0D + target.getBbWidth();"));
        assertTrue(entitySource.contains("public int getMaxSpawnClusterSize()"));
        assertTrue(entitySource.contains("return NATURAL_SPAWN_MAX_COUNT;"));
        assertTrue(entitySource.contains("canNaturallySpawnInDimension"));
        assertTrue(entitySource.contains("checkCavenicBearSpawnRules"));
        assertTrue(entitySource.contains("CavernNeoForgeDimensions.isCavern"));
        assertTrue(entitySource.contains("Monster.checkMonsterSpawnRules"));
        assertTrue(entitySource.contains("asMonsterSpawnType"));
        assertFalse(entitySource.contains("PanicGoal"));
        assertFalse(entitySource.contains("FollowParentGoal"));
        assertFalse(entitySource.contains("RandomStrollGoal"));
        assertFalse(entitySource.contains("LookAtPlayerGoal"));
        assertFalse(entitySource.contains("RandomLookAroundGoal"));
        assertFalse(entitySource.contains("Fox.class"));
        assertFalse(entitySource.contains("setTarget("));
        assertFalse(entitySource.toLowerCase().contains("tame"));
        assertFalse(entitySource.contains("cavenic_orb"));
        assertFalse(entitySource.contains("dropLoot("));
        assertFalse(entitySource.contains("ItemMagicBook"));
        assertFalse(entitySource.contains("magic_book"));
        assertFalse(entitySource.toLowerCase().contains("cavenia"));

        assertTrue(entityEventSource.contains("event.put(ModRegistries.CAVENIC_BEAR.get(), CavenicBear.createAttributes().build())"));
        assertTrue(entityEventSource.contains("CavenicBear::checkCavenicBearSpawnRules"));
        assertTrue(entityEventSource.contains("CAVENIC_BEAR.get(),\n            SpawnPlacementTypes.ON_GROUND"));
        assertTrue(entityEventSource.contains("Heightmap.Types.MOTION_BLOCKING_NO_LEAVES"));
        assertTrue(entityEventSource.contains("RegisterSpawnPlacementsEvent.Operation.REPLACE"));
        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CAVENIC_BEAR.get(), CavenicBearRenderer::new);"));
        assertTrue(rendererSource.contains("extends PolarBearRenderer"));
        assertTrue(rendererSource.contains("getTextureLocation(PolarBear entity)"));
        assertTrue(rendererSource.contains("textures/entity/cavenic_bear.png"));

        assertFalse(modSource.contains("CavenicBearLootEvents"));
        assertFalse(registriesSource.contains("magic_book"));
        assertFalse(registriesSource.contains("ItemMagicBook"));
        assertFalse(registriesSource.toLowerCase().contains("cavenia"));
        assertFalse(registriesSource.contains("EntityRapidArrow"));
        assertFalse(registriesSource.contains("EntityTorchArrow"));
        assertFalse(registriesSource.contains("EntityCavenicArrow"));
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CavenicBearLootEvents.java");
        assertMissingProjectFile("core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "loot", "CavenicBearLootPolicy.java");
        assertMissingProjectFile("core", "src", "test", "java", "com", "richardkenway", "cavernreborn", "core", "loot", "CavenicBearLootPolicyTest.java");
    }

    @Test
    void cavenicBearResourcesCoverLangSpawnEggModelTextureAndNaturalSpawnDataClasspath() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/cavenic_bear_spawn_egg.json");
        JsonObject biomeModifier = readJsonResource("data/cavernreborn/neoforge/biome_modifier/cavenic_bear_spawns.json");
        JsonObject biomeTag = readJsonResource("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_bear.json");

        assertEquals("Cavenic Bear", lang.get("entity.cavernreborn.cavenic_bear").getAsString());
        assertEquals("Cavenic Bear Spawn Egg", lang.get("item.cavernreborn.cavenic_bear_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());
        assertEquals("neoforge:add_spawns", biomeModifier.get("type").getAsString());
        assertEquals("#cavernreborn:spawns_cavenic_bear", biomeModifier.get("biomes").getAsString());

        JsonObject spawner = biomeModifier.getAsJsonObject("spawners");
        assertEquals("cavernreborn:cavenic_bear", spawner.get("type").getAsString());
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

        assertClassPathOrigin(resourceUrl("assets/cavernreborn/models/item/cavenic_bear_spawn_egg.json"), "assets/cavernreborn/models/item/cavenic_bear_spawn_egg.json");
        assertClassPathOrigin(resourceUrl("assets/cavernreborn/textures/entity/cavenic_bear.png"), "assets/cavernreborn/textures/entity/cavenic_bear.png");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/neoforge/biome_modifier/cavenic_bear_spawns.json"), "data/cavernreborn/neoforge/biome_modifier/cavenic_bear_spawns.json");
        assertClassPathOrigin(resourceUrl("data/cavernreborn/tags/worldgen/biome/spawns_cavenic_bear.json"), "data/cavernreborn/tags/worldgen/biome/spawns_cavenic_bear.json");
        assertMissingProjectFile("app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "item", "MagicBookItem.java");
        assertMissingResource("assets/cavernreborn/models/item/magic_book.json");
        assertMissingResource("data/cavernreborn/loot_table/entities/cavenic_bear.json");
        assertMissingResource("data/cavernreborn/loot_tables/entities/cavenic_bear.json");
    }

    private static JsonObject readJsonResource(String path) throws IOException {
        try (InputStream inputStream = resourceInputStream(path)) {
            return JsonParser.parseString(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }

    private static InputStream resourceInputStream(String path) {
        InputStream inputStream = CavenicBearResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CavenicBearResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CavenicBearResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
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

        throw new IllegalStateException("Could not resolve project file: " + Path.of(first, more));
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

    private static void assertInOrder(String source, List<String> snippets) {
        int currentIndex = -1;
        for (String snippet : snippets) {
            int nextIndex = source.indexOf(snippet, currentIndex + 1);
            assertTrue(nextIndex >= 0, "Missing snippet: " + snippet);
            assertTrue(nextIndex > currentIndex, "Snippet out of order: " + snippet);
            currentIndex = nextIndex;
        }
    }
}
