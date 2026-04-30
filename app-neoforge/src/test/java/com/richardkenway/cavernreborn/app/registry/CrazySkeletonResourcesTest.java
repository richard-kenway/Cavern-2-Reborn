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

class CrazySkeletonResourcesTest {
    @Test
    void crazySkeletonRegistersWithDedicatedEntitySpawnEggRendererAndBossDamageLootEquipmentFollowUpsWhileKeepingRangedAiDeferred() throws IOException {
        String registriesSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModRegistries.java"
        );
        String entitySource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CrazySkeleton.java"
        );
        String bowSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "item", "CavenicBowItem.java"
        );
        String appSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "CavernReborn.java"
        );
        String entityEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "registry", "ModEntityEvents.java"
        );
        String clientSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "CavernClientModEvents.java"
        );
        String rendererSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "client", "renderer", "CrazySkeletonRenderer.java"
        );
        String lootEventSource = readProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "CrazySkeletonLootEvents.java"
        );
        String lootPolicySource = readProjectFile(
            "core", "src", "main", "java", "com", "richardkenway", "cavernreborn", "core", "loot", "CrazySkeletonLootPolicy.java"
        );

        assertEquals(
            List.of("crazy_zombie", "crazy_skeleton"),
            extractMatches(registriesSource, Pattern.compile("ENTITY_TYPES\\.register\\(\\s*\"(crazy_[a-z_]+)\"", Pattern.MULTILINE))
        );
        assertEquals(
            List.of("crazy_zombie_spawn_egg", "crazy_skeleton_spawn_egg"),
            extractMatches(registriesSource, Pattern.compile("ITEMS\\.register\\(\\s*\"(crazy_[a-z_]+_spawn_egg)\"", Pattern.MULTILINE))
        );
        assertTrue(registriesSource.contains("ENTITY_TYPES.register(\n        \"crazy_skeleton\""));
        assertTrue(registriesSource.contains("EntityType.Builder.of(CrazySkeleton::new, MobCategory.MONSTER)"));
        assertTrue(registriesSource.contains(".build(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, \"crazy_skeleton\").toString())"));
        assertTrue(registriesSource.contains("new DeferredSpawnEggItem(() -> CRAZY_SKELETON.get(), 0x909090, 0xDDDDDD, new Item.Properties())"));
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
            "output.accept(ORE_COMPASS.get())"
        ));

        assertTrue(entitySource.contains("extends Skeleton"));
        assertFalse(entitySource.contains("extends CavenicSkeleton"));
        assertTrue(entitySource.contains("this.xpReward = 50;"));
        assertTrue(entitySource.contains("public static AttributeSupplier.Builder createAttributes()"));
        assertTrue(entitySource.contains("Skeleton.createAttributes()"));
        assertTrue(entitySource.contains(".add(Attributes.MAX_HEALTH, 2000.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)"));
        assertTrue(entitySource.contains(".add(Attributes.MOVEMENT_SPEED, 0.25D)"));
        assertTrue(entitySource.contains(".add(Attributes.FOLLOW_RANGE, 22.0D)"));
        assertTrue(entitySource.contains("public static final float LEGACY_FALL_DAMAGE_MULTIPLIER = 0.35F;"));
        assertTrue(entitySource.contains("public static final float LEGACY_MAINHAND_DROP_CHANCE = 1.0F;"));
        assertTrue(entitySource.contains("public static final double LEGACY_BOSS_BAR_VISIBILITY_DISTANCE = 20.0D;"));
        assertTrue(entitySource.contains("public static final double LEGACY_BOSS_BAR_DARKEN_SKY_DISTANCE = 30.0D;"));
        assertTrue(entitySource.contains("private final ServerBossEvent legacyBossEvent = new ServerBossEvent("));
        assertTrue(entitySource.contains("BossEvent.BossBarColor.WHITE"));
        assertTrue(entitySource.contains("BossEvent.BossBarOverlay.PROGRESS"));
        assertTrue(entitySource.contains("this.setDropChance(EquipmentSlot.MAINHAND, LEGACY_MAINHAND_DROP_CHANCE);"));
        assertTrue(entitySource.contains("public static ItemStack createLegacyCrazySkeletonBow(RegistryAccess registryAccess)"));
        assertTrue(entitySource.contains("new ItemStack(ModRegistries.CAVENIC_BOW.get())"));
        assertTrue(entitySource.contains("lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY)"));
        assertTrue(entitySource.contains("stack.enchant("));
        assertTrue(entitySource.contains("protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty)"));
        assertTrue(entitySource.contains("super.populateDefaultEquipmentSlots(random, difficulty);"));
        assertTrue(entitySource.contains("this.setItemSlot(EquipmentSlot.MAINHAND, createLegacyCrazySkeletonBow(this.registryAccess()));"));
        assertTrue(entitySource.contains("public void readAdditionalSaveData(CompoundTag compound)"));
        assertTrue(entitySource.contains("public void setCustomName(@Nullable Component name)"));
        assertTrue(entitySource.contains("public boolean hurt(DamageSource source, float damage)"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FALL)"));
        assertTrue(entitySource.contains("damage *= LEGACY_FALL_DAMAGE_MULTIPLIER;"));
        assertTrue(entitySource.contains("source.is(DamageTypeTags.IS_FIRE)"));
        assertTrue(entitySource.contains("return super.hurt(source, damage);"));
        assertTrue(entitySource.contains("protected void customServerAiStep()"));
        assertTrue(entitySource.contains("public void startSeenByPlayer(ServerPlayer player)"));
        assertTrue(entitySource.contains("public void stopSeenByPlayer(ServerPlayer player)"));
        assertTrue(entitySource.contains("public ServerBossEvent getLegacyCrazyBossEventForTests()"));
        assertTrue(entitySource.contains("public boolean shouldShowLegacyBossBarTo(ServerPlayer player)"));
        assertTrue(entitySource.contains("public boolean shouldDarkenLegacyBossSkyFor(ServerPlayer player)"));
        assertTrue(entitySource.contains("public void updateLegacyBossEvent()"));
        assertTrue(entitySource.contains("this.legacyBossEvent.setDarkenScreen(!canSee || distance < LEGACY_BOSS_BAR_DARKEN_SKY_DISTANCE);"));
        assertTrue(entitySource.contains("this.legacyBossEvent.setVisible(canSee);"));
        assertTrue(entitySource.contains("this.legacyBossEvent.setProgress(this.getHealth() / this.getMaxHealth());"));
        assertTrue(entitySource.contains("return EntityType.SKELETON.getDefaultLootTable();"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_WEIGHT"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_MIN_COUNT"));
        assertFalse(entitySource.contains("NATURAL_SPAWN_MAX_COUNT"));
        assertFalse(entitySource.contains("canNaturallySpawnInDimension"));
        assertFalse(entitySource.contains("checkCrazySkeletonSpawnRules"));
        assertFalse(entitySource.contains("public void aiStep()"));
        assertFalse(entitySource.contains("addParticle("));
        assertFalse(entitySource.contains("ParticleCrazyMob"));
        assertFalse(entitySource.contains("doHurtTarget("));
        assertFalse(entitySource.contains("EntityAIAttackCavenicBow"));
        assertFalse(entitySource.contains("registerGoals()"));
        assertFalse(entitySource.contains("reassessWeaponGoal"));
        assertFalse(entitySource.contains("performRangedAttack"));
        assertFalse(entitySource.toLowerCase().contains("cavenia"));
        assertFalse(entitySource.contains("magic_book"));
        assertFalse(bowSource.contains("CrazySkeleton"));
        assertTrue(lootPolicySource.contains("public static final int ORB_DROP_ROLL_BOUND = 5;"));
        assertTrue(lootPolicySource.contains("return roll >= 0 && roll < ORB_DROP_ROLL_BOUND && roll == 0;"));
        assertTrue(lootEventSource.contains("class CrazySkeletonLootEvents"));
        assertTrue(lootEventSource.contains("LivingDropsEvent"));
        assertTrue(lootEventSource.contains("event.getEntity() instanceof CrazySkeleton skeleton"));
        assertTrue(lootEventSource.contains("CrazySkeletonLootPolicy.ORB_DROP_ROLL_BOUND"));
        assertTrue(lootEventSource.contains("CrazySkeletonLootPolicy.shouldDropOrb(orbRoll)"));
        assertTrue(lootEventSource.contains("new ItemStack(ModRegistries.CAVENIC_ORB.get())"));
        assertTrue(lootEventSource.contains("skeleton.getY() + 0.5D"));
        assertFalse(lootEventSource.toLowerCase().contains("economy"));
        assertFalse(lootEventSource.toLowerCase().contains("progression"));
        assertFalse(lootEventSource.contains("ServerBossEvent"));
        assertFalse(lootEventSource.contains("ParticleCrazyMob"));
        assertFalse(lootEventSource.contains("EntityAIAttackCavenicBow"));
        assertTrue(appSource.contains("NeoForge.EVENT_BUS.register(new CrazySkeletonLootEvents());"));

        assertTrue(entityEventSource.contains("event.put(ModRegistries.CRAZY_SKELETON.get(), CrazySkeleton.createAttributes().build())"));
        assertFalse(entityEventSource.contains("ModRegistries.CRAZY_SKELETON.get(),\n            SpawnPlacementTypes"));

        assertTrue(clientSource.contains("event.registerEntityRenderer(ModRegistries.CRAZY_SKELETON.get(), CrazySkeletonRenderer::new);"));
        assertTrue(rendererSource.contains("extends SkeletonRenderer<CrazySkeleton>"));
        assertTrue(rendererSource.contains("textures/entity/crazy_skeleton.png"));
        assertTrue(rendererSource.contains("poseStack.scale(1.1F, 1.1F, 1.1F);"));

        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "neoforge", "biome_modifier", "crazy_skeleton_spawns.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "tags", "worldgen", "biome", "spawns_crazy_skeleton.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "loot_table", "entities", "crazy_skeleton.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "resources", "data", "cavernreborn", "loot_tables", "entities", "crazy_skeleton.json"
        );
        assertMissingProjectFile(
            "app-neoforge", "src", "main", "java", "com", "richardkenway", "cavernreborn", "app", "entity", "ai", "EntityAIAttackCavenicBow.java"
        );
    }

    @Test
    void crazySkeletonResourcesCoverLangSpawnEggModelAndTextureClasspath() throws IOException {
        JsonObject lang = readJsonResource("assets/cavernreborn/lang/en_us.json");
        JsonObject spawnEggModel = readJsonResource("assets/cavernreborn/models/item/crazy_skeleton_spawn_egg.json");

        assertEquals("Crazy Skeleton", lang.get("entity.cavernreborn.crazy_skeleton").getAsString());
        assertEquals("Crazy Skeleton Spawn Egg", lang.get("item.cavernreborn.crazy_skeleton_spawn_egg").getAsString());
        assertEquals("minecraft:item/template_spawn_egg", spawnEggModel.get("parent").getAsString());

        assertClassPathOrigin(
            resourceUrl("assets/cavernreborn/models/item/crazy_skeleton_spawn_egg.json"),
            "assets/cavernreborn/models/item/crazy_skeleton_spawn_egg.json"
        );
        assertClassPathOrigin(
            resourceUrl("assets/cavernreborn/textures/entity/crazy_skeleton.png"),
            "assets/cavernreborn/textures/entity/crazy_skeleton.png"
        );
        assertMissingResource("data/cavernreborn/neoforge/biome_modifier/crazy_skeleton_spawns.json");
        assertMissingResource("data/cavernreborn/tags/worldgen/biome/spawns_crazy_skeleton.json");
        assertMissingResource("data/cavernreborn/loot_table/entities/crazy_skeleton.json");
        assertMissingResource("data/cavernreborn/loot_tables/entities/crazy_skeleton.json");
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
        InputStream inputStream = CrazySkeletonResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(inputStream, "Missing resource: " + path);
        return inputStream;
    }

    private static URL resourceUrl(String path) {
        URL url = CrazySkeletonResourcesTest.class.getClassLoader().getResource(path);
        assertNotNull(url, "Missing resource URL: " + path);
        return url;
    }

    private static void assertMissingResource(String path) {
        assertTrue(CrazySkeletonResourcesTest.class.getClassLoader().getResource(path) == null, "Did not expect resource: " + path);
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
            Path candidate = current.resolve(Path.of(first, more));
            if (Files.exists(candidate)) {
                throw new AssertionError("Did not expect project file: " + candidate);
            }
            current = current.getParent();
        }
    }
}
