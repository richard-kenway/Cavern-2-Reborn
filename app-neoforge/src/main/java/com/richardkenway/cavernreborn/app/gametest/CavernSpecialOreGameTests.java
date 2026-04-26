package com.richardkenway.cavernreborn.app.gametest;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.block.AcresiaCropBlock;
import com.richardkenway.cavernreborn.app.block.CavenicShroomBlock;
import com.richardkenway.cavernreborn.app.compass.OreCompassStateAccess;
import com.richardkenway.cavernreborn.app.compass.OreCompassScanner;
import com.richardkenway.cavernreborn.app.compass.OreCompassTarget;
import com.richardkenway.cavernreborn.app.compass.StoredOreCompassTarget;
import com.richardkenway.cavernreborn.app.dimension.CavernNeoForgeDimensions;
import com.richardkenway.cavernreborn.app.entity.CavenicBear;
import com.richardkenway.cavernreborn.app.entity.CavenicCreeper;
import com.richardkenway.cavernreborn.app.entity.CavenicCreeperLootEvents;
import com.richardkenway.cavernreborn.app.entity.CavenicSkeleton;
import com.richardkenway.cavernreborn.app.entity.CavenicSkeletonLootEvents;
import com.richardkenway.cavernreborn.app.entity.CavenicSpider;
import com.richardkenway.cavernreborn.app.entity.CavenicSpiderLootEvents;
import com.richardkenway.cavernreborn.app.entity.CavenicWitch;
import com.richardkenway.cavernreborn.app.entity.CavenicWitchLootEvents;
import com.richardkenway.cavernreborn.app.entity.CavenicZombie;
import com.richardkenway.cavernreborn.app.entity.CavenicZombieLootEvents;
import com.richardkenway.cavernreborn.app.item.CavenicBowTorchEvents;
import com.richardkenway.cavernreborn.app.item.CavenicBowItem;
import com.richardkenway.cavernreborn.app.item.OreCompassItem;
import com.richardkenway.cavernreborn.app.mining.CavernMiningAssistEvents;
import com.richardkenway.cavernreborn.app.registry.ModArmorMaterials;
import com.richardkenway.cavernreborn.app.registry.ModBlockTags;
import com.richardkenway.cavernreborn.app.registry.ModItemTags;
import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.app.registry.ModToolTiers;
import com.richardkenway.cavernreborn.core.combat.CavenicBowMode;
import com.richardkenway.cavernreborn.core.combat.CavenicBowRapidPolicy;
import com.richardkenway.cavernreborn.core.combat.CavenicBowSnipePolicy;
import com.richardkenway.cavernreborn.core.combat.CavenicBowTorchPolicy;
import com.richardkenway.cavernreborn.core.compass.OreCompassDirection;
import com.richardkenway.cavernreborn.core.compass.OreCompassScanDecision;
import com.richardkenway.cavernreborn.core.compass.OreCompassScanPolicy;
import com.richardkenway.cavernreborn.core.compass.OreCompassScanResult;
import com.richardkenway.cavernreborn.core.compass.OreCompassTrackingDecision;
import com.richardkenway.cavernreborn.core.compass.OreCompassTrackingPolicy;
import com.richardkenway.cavernreborn.core.compass.OreCompassTrackingResult;
import com.richardkenway.cavernreborn.core.flora.CavenicOrbDropPolicy;
import com.richardkenway.cavernreborn.core.loot.CavenicCreeperLootPolicy;
import com.richardkenway.cavernreborn.core.farming.AcresiaHarvestPolicy;
import com.richardkenway.cavernreborn.core.loot.CavenicSkeletonLootPolicy;
import com.richardkenway.cavernreborn.core.loot.CavenicSpiderLootPolicy;
import com.richardkenway.cavernreborn.core.loot.CavenicWitchLootPolicy;
import com.richardkenway.cavernreborn.core.loot.CavenicZombieLootPolicy;
import com.richardkenway.cavernreborn.core.mining.AquamarineAquaToolDecision;
import com.richardkenway.cavernreborn.core.mining.AquamarineAquaToolPolicy;
import com.richardkenway.cavernreborn.core.mining.AquamarineAquaToolResult;
import com.richardkenway.cavernreborn.core.mining.MiningAssistPolicy;
import com.richardkenway.cavernreborn.core.mining.MinerOrbBonusDecision;
import com.richardkenway.cavernreborn.core.mining.MinerOrbBonusPolicy;
import com.richardkenway.cavernreborn.core.mining.MinerOrbBonusResult;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionPolicy;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUnlock;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionUpdateResult;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@GameTestHolder(CavernReborn.MOD_ID)
@PrefixGameTestTemplate(false)
public final class CavernSpecialOreGameTests {
    private static final ReleasedArrowRecorder RELEASED_ARROW_RECORDER = registerReleasedArrowRecorder();
    private static final String TEMPLATE_NAMESPACE = "minecraft";
    private static final String EMPTY_TEMPLATE = "empty";
    private static final int DEFAULT_TIMEOUT_TICKS = 100;
    private static final BlockPos PRIMARY_BLOCK_POS = new BlockPos(1, 1, 1);
    private static final BlockPos MINING_ASSIST_ENABLED_ANCHOR = new BlockPos(0, 96, 0);
    private static final BlockPos MINING_ASSIST_NO_UNLOCK_ANCHOR = new BlockPos(32, 96, 0);
    private static final BlockPos MINING_ASSIST_SNEAKING_ANCHOR = new BlockPos(64, 96, 0);
    private static final BlockPos MINING_ASSIST_FISSURE_ANCHOR = new BlockPos(96, 96, 0);
    private static final BlockPos MINING_ASSIST_DURABILITY_ANCHOR = new BlockPos(128, 96, 0);
    private static final BlockPos MAGNITE_NO_ASSIST_ANCHOR = new BlockPos(160, 96, 0);
    private static final BlockPos ORE_COMPASS_NEAREST_ANCHOR = new BlockPos(256, 96, 0);
    private static final BlockPos ORE_COMPASS_FILTERED_ANCHOR = new BlockPos(352, 96, 0);
    private static final BlockPos ORE_COMPASS_EMPTY_ANCHOR = new BlockPos(448, 96, 0);
    private static final BlockPos ORE_COMPASS_USE_ANCHOR = new BlockPos(544, 96, 0);
    private static final BlockPos ORE_COMPASS_STATE_ANCHOR = new BlockPos(640, 96, 0);
    private static final BlockPos ACRESIA_PLANTING_ANCHOR = new BlockPos(736, 96, 0);
    private static final BlockPos ACRESIA_HARVEST_ANCHOR = new BlockPos(832, 96, 0);
    private static final BlockPos ACRESIA_SHEAR_ANCHOR = new BlockPos(928, 96, 0);
    private static final BlockPos CAVENIC_COLLISION_ANCHOR = new BlockPos(1024, 96, 0);
    private static final BlockPos CAVENIC_SHEAR_ANCHOR = new BlockPos(1120, 96, 0);
    private static final BlockPos CAVENIC_BOW_MODE_ANCHOR = new BlockPos(1216, 96, 0);
    private static final BlockPos CAVENIC_BOW_SNIPE_ANCHOR = new BlockPos(1312, 96, 0);
    private static final BlockPos CAVENIC_BOW_RAPID_ANCHOR = new BlockPos(1408, 96, 0);
    private static final BlockPos CAVENIC_BOW_TORCH_ANCHOR = new BlockPos(1504, 96, 0);
    private static final BlockPos CAVENIC_BOW_RELEASE_ANCHOR = new BlockPos(1600, 96, 0);
    private static final BlockPos CAVENIC_ZOMBIE_ANCHOR = new BlockPos(1696, 96, 0);
    private static final BlockPos CAVENIC_ZOMBIE_SPAWN_EGG_ANCHOR = new BlockPos(1792, 96, 0);
    private static final BlockPos CAVENIC_ZOMBIE_NATURAL_SPAWN_ANCHOR = new BlockPos(1888, 96, 0);
    private static final BlockPos CAVENIC_ZOMBIE_LOOT_ANCHOR = new BlockPos(1984, 96, 0);
    private static final BlockPos CAVENIC_ZOMBIE_DAMAGE_ANCHOR = new BlockPos(2080, 96, 0);
    private static final BlockPos CAVENIC_SKELETON_ANCHOR = new BlockPos(2176, 96, 0);
    private static final BlockPos CAVENIC_SKELETON_SPAWN_EGG_ANCHOR = new BlockPos(2272, 96, 0);
    private static final BlockPos CAVENIC_SKELETON_NATURAL_SPAWN_ANCHOR = new BlockPos(2368, 96, 0);
    private static final BlockPos CAVENIC_SKELETON_LOOT_ANCHOR = new BlockPos(2464, 96, 0);
    private static final BlockPos CAVENIC_SKELETON_DAMAGE_ANCHOR = new BlockPos(2560, 96, 0);
    private static final BlockPos CAVENIC_CREEPER_ANCHOR = new BlockPos(2656, 96, 0);
    private static final BlockPos CAVENIC_CREEPER_SPAWN_EGG_ANCHOR = new BlockPos(2752, 96, 0);
    private static final BlockPos CAVENIC_CREEPER_NATURAL_SPAWN_ANCHOR = new BlockPos(2848, 96, 0);
    private static final BlockPos CAVENIC_CREEPER_LOOT_ANCHOR = new BlockPos(2944, 96, 0);
    private static final BlockPos CAVENIC_CREEPER_DAMAGE_ANCHOR = new BlockPos(3040, 96, 0);
    private static final BlockPos CAVENIC_CREEPER_FUSE_ANCHOR = new BlockPos(3136, 96, 0);
    private static final BlockPos CAVENIC_SPIDER_ANCHOR = new BlockPos(3232, 96, 0);
    private static final BlockPos CAVENIC_SPIDER_SPAWN_EGG_ANCHOR = new BlockPos(3328, 96, 0);
    private static final BlockPos CAVENIC_SPIDER_NATURAL_SPAWN_ANCHOR = new BlockPos(3424, 96, 0);
    private static final BlockPos CAVENIC_SPIDER_LOOT_ANCHOR = new BlockPos(3520, 96, 0);
    private static final BlockPos CAVENIC_SPIDER_DAMAGE_ANCHOR = new BlockPos(3616, 96, 0);
    private static final BlockPos CAVENIC_SPIDER_BLINDNESS_ANCHOR = new BlockPos(3712, 96, 0);
    private static final BlockPos CAVENIC_WITCH_ANCHOR = new BlockPos(3808, 96, 0);
    private static final BlockPos CAVENIC_WITCH_SPAWN_EGG_ANCHOR = new BlockPos(3904, 96, 0);
    private static final BlockPos CAVENIC_WITCH_NATURAL_SPAWN_ANCHOR = new BlockPos(4000, 96, 0);
    private static final BlockPos CAVENIC_WITCH_LOOT_ANCHOR = new BlockPos(4096, 96, 0);
    private static final BlockPos CAVENIC_WITCH_DAMAGE_ANCHOR = new BlockPos(4192, 96, 0);
    private static final BlockPos CAVENIC_WITCH_PROJECTILE_IMMUNITY_ANCHOR = new BlockPos(4288, 96, 0);
    private static final BlockPos CAVENIC_WITCH_FRIENDSHIP_TARGETING_ANCHOR = new BlockPos(4384, 96, 0);
    private static final BlockPos CAVENIC_WITCH_RANGED_POTION_ANCHOR = new BlockPos(4480, 96, 0);
    private static final BlockPos CAVENIC_WITCH_RANGED_POTION_SELECTION_ANCHOR = new BlockPos(4576, 96, 0);
    private static final BlockPos CAVENIC_BEAR_ANCHOR = new BlockPos(4672, 96, 0);
    private static final BlockPos CAVENIC_BEAR_SPAWN_EGG_ANCHOR = new BlockPos(4768, 96, 0);
    private static final BlockPos CAVENIC_BEAR_NATURAL_SPAWN_ANCHOR = new BlockPos(4864, 96, 0);
    private static final BlockPos CAVENIC_BEAR_DAMAGE_ANCHOR = new BlockPos(4960, 96, 0);
    private static final BlockPos CAVENIC_BEAR_HOSTILE_TARGETING_ANCHOR = new BlockPos(5056, 96, 0);
    private static final BlockPos CAVENIC_BEAR_MELEE_ATTACK_ANCHOR = new BlockPos(5152, 96, 0);
    private static final BlockPos CAVENIC_BEAR_PANIC_ANCHOR = new BlockPos(5248, 96, 0);
    private static final Set<String> ALLOWED_RANDOMITE_DROPS = Set.of(
        "cavernreborn:aquamarine",
        "cavernreborn:magnite_ingot",
        "cavernreborn:hexcite",
        "cavernreborn:miner_orb",
        "minecraft:coal",
        "minecraft:iron_nugget",
        "minecraft:gold_nugget",
        "minecraft:redstone",
        "minecraft:lapis_lazuli",
        "minecraft:torch",
        "minecraft:string",
        "minecraft:bone",
        "minecraft:gunpowder",
        "minecraft:arrow",
        "minecraft:slime_ball",
        "minecraft:ender_pearl",
        "minecraft:bread",
        "minecraft:apple",
        "minecraft:cooked_beef",
        "minecraft:cooked_porkchop"
    );
    private static final Map<String, Integer> EXPECTED_PROGRESSION_SCORES = Map.of(
        "cavernreborn:magnite_ore", 1,
        "cavernreborn:aquamarine_ore", 2,
        "cavernreborn:randomite_ore", 2,
        "cavernreborn:fissured_stone", 3,
        "cavernreborn:hexcite_ore", 4
    );

    private CavernSpecialOreGameTests() {
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void specialOreRegistriesAreAvailableAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.HEXCITE_ORE.get() != null, "Missing hexcite ore block");
        helper.assertTrue(ModRegistries.HEXCITE_BLOCK.get() != null, "Missing hexcite block");
        helper.assertTrue(ModRegistries.RANDOMITE_ORE.get() != null, "Missing randomite ore block");
        helper.assertTrue(ModRegistries.FISSURED_STONE.get() != null, "Missing fissured stone block");
        helper.assertTrue(ModRegistries.HEXCITE.get() != null, "Missing hexcite item");
        helper.assertTrue(ModRegistries.HEXCITE_ORE_ITEM.get() != null, "Missing hexcite ore item");
        helper.assertTrue(ModRegistries.HEXCITE_BLOCK_ITEM.get() != null, "Missing hexcite block item");
        helper.assertTrue(ModRegistries.RANDOMITE_ORE_ITEM.get() != null, "Missing randomite ore item");
        helper.assertTrue(ModRegistries.FISSURED_STONE_ITEM.get() != null, "Missing fissured stone item");

        assertRegistryId(helper, ModRegistries.HEXCITE_ORE.get(), "cavernreborn:hexcite_ore");
        assertRegistryId(helper, ModRegistries.HEXCITE_BLOCK.get(), "cavernreborn:hexcite_block");
        assertRegistryId(helper, ModRegistries.RANDOMITE_ORE.get(), "cavernreborn:randomite_ore");
        assertRegistryId(helper, ModRegistries.FISSURED_STONE.get(), "cavernreborn:fissured_stone");
        assertRegistryId(helper, ModRegistries.HEXCITE.get(), "cavernreborn:hexcite");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void acresiaRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.ACRESIA.get() != null, "Missing acresia crop block");
        helper.assertTrue(ModRegistries.ACRESIA_SEEDS.get() != null, "Missing acresia seeds item");
        helper.assertTrue(ModRegistries.ACRESIA_FRUITS.get() != null, "Missing acresia fruits item");

        assertRegistryId(helper, ModRegistries.ACRESIA.get(), "cavernreborn:acresia");
        assertRegistryId(helper, ModRegistries.ACRESIA_SEEDS.get(), "cavernreborn:acresia_seeds");
        assertRegistryId(helper, ModRegistries.ACRESIA_FRUITS.get(), "cavernreborn:acresia_fruits");

        helper.assertTrue(!acresiaSeeds().isEmpty(), "Expected acresia seeds stack to be constructible");
        helper.assertTrue(!acresiaFruits().isEmpty(), "Expected acresia fruits stack to be constructible");
        helper.assertTrue(acresiaSeeds().is(ModItemTags.ACRESIA_ITEMS), "Expected acresia seeds to resolve through acresia_items");
        helper.assertTrue(acresiaFruits().is(ModItemTags.ACRESIA_ITEMS), "Expected acresia fruits to resolve through acresia_items");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void acresiaSeedsPlantCrop(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos soilPos = ACRESIA_PLANTING_ANCHOR;
        BlockPos cropPos = soilPos.above();

        resetMiningArea(level, soilPos, 6.0D);
        level.setBlock(soilPos, Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);

        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, acresiaSeeds(), soilPos);
        InteractionResult result = player.getMainHandItem().useOn(new UseOnContext(player, InteractionHand.MAIN_HAND, hitResult(soilPos)));

        helper.assertTrue(result.consumesAction(), "Expected acresia seeds to plant the crop");
        helper.assertTrue(level.getBlockState(cropPos).is(ModRegistries.ACRESIA.get()), "Expected acresia seeds to place the crop block");
        helper.assertTrue(level.getBlockState(cropPos).getValue(AcresiaCropBlock.AGE) == 0, "Expected newly planted acresia to start at age 0");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void acresiaMatureCropDropsFruitAndSeeds(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos soilPos = ACRESIA_HARVEST_ANCHOR;
        BlockPos cropPos = soilPos.above();

        resetMiningArea(level, soilPos, 6.0D);
        level.setBlock(soilPos, Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(cropPos, matureAcresiaState(), Block.UPDATE_ALL);

        List<ItemStack> drops = dropsForState(helper, matureAcresiaState(), cropPos, ItemStack.EMPTY);

        helper.assertTrue(!drops.isEmpty(), "Expected mature acresia to produce drops");
        assertContainsItem(helper, drops, "cavernreborn:acresia_seeds");
        assertContainsItem(helper, drops, "cavernreborn:acresia_fruits");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void acresiaShearHarvestYieldsBonusFruitAndRegrows(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos playerAnchor = ACRESIA_SHEAR_ANCHOR;
        BlockPos soilPos = playerAnchor.east(2);
        BlockPos cropPos = soilPos.above();
        ItemStack shears = new ItemStack(Items.SHEARS);

        resetMiningArea(level, playerAnchor, 8.0D);
        level.setBlock(soilPos, Blocks.MOSS_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(cropPos, matureAcresiaState(), Block.UPDATE_ALL);

        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, shears, playerAnchor);
        AcresiaCropBlock cropBlock = (AcresiaCropBlock) ModRegistries.ACRESIA.get();
        InteractionResult result = cropBlock.tryShearHarvest(
            shears,
            level.getBlockState(cropPos),
            level,
            cropPos,
            player,
            InteractionHand.MAIN_HAND
        ).result();

        helper.assertTrue(result.consumesAction(), "Expected mature acresia to support the shear-harvest path");
        helper.runAfterDelay(5, () -> {
            BlockState cropState = level.getBlockState(cropPos);
            helper.assertTrue(cropState.is(ModRegistries.ACRESIA.get()), "Expected shear harvest to keep the crop placed");
            helper.assertTrue(cropState.getValue(AcresiaCropBlock.AGE) == AcresiaHarvestPolicy.REGROWTH_AGE, "Expected shear harvest to reset acresia to age 2");
            helper.assertTrue(shears.getDamageValue() == 1, "Expected shear harvest to damage shears by one");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void acresiaFruitIsEdibleAndNonSpecial(GameTestHelper helper) {
        ItemStack fruits = acresiaFruits();
        FoodProperties food = fruits.get(DataComponents.FOOD);

        helper.assertTrue(food != null, "Expected acresia fruits to expose a food component");
        helper.assertTrue(food.nutrition() == 2, "Expected acresia fruits to stay a small snack");
        helper.assertTrue(Math.abs(food.saturation() - 0.4F) < 1.0e-6F, "Expected acresia fruits to keep bounded saturation");
        helper.assertTrue(food.effects().isEmpty(), "Expected acresia fruits to avoid potion-effect side behavior");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void acresiaPatchWorldgenKeysResolve(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<ConfiguredFeature<?, ?>> configuredFeatures = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        Registry<PlacedFeature> placedFeatures = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registries.BIOME);

        assertRegistryKeyPresent(helper, configuredFeatures, Registries.CONFIGURED_FEATURE, "cavernreborn:acresia_patch");
        assertRegistryKeyPresent(helper, placedFeatures, Registries.PLACED_FEATURE, "cavernreborn:cavern_acresia_patch_lush");
        helper.assertTrue(
            biomes.containsKey(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("cavernreborn:lush_grotto"))),
            "Expected lush_grotto biome key to resolve at runtime with Acresia patch wiring"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicShroomRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.CAVENIC_SHROOM.get() != null, "Missing cavenic shroom block");
        helper.assertTrue(ModRegistries.CAVENIC_SHROOM_ITEM.get() != null, "Missing cavenic shroom block item");
        helper.assertTrue(ModRegistries.CAVENIC_ORB.get() != null, "Missing cavenic orb item");

        assertRegistryId(helper, ModRegistries.CAVENIC_SHROOM.get(), "cavernreborn:cavenic_shroom");
        assertRegistryId(helper, ModRegistries.CAVENIC_SHROOM_ITEM.get(), "cavernreborn:cavenic_shroom");
        assertRegistryId(helper, ModRegistries.CAVENIC_ORB.get(), "cavernreborn:cavenic_orb");

        helper.assertTrue(!cavenicShroomItem().isEmpty(), "Expected cavenic shroom item stack to be constructible");
        helper.assertTrue(!cavenicOrb().isEmpty(), "Expected cavenic orb stack to be constructible");
        helper.assertTrue(cavenicShroomItem().is(ModItemTags.CAVENIC_ITEMS), "Expected cavenic shroom item to resolve through cavenic_items");
        helper.assertTrue(cavenicOrb().is(ModItemTags.CAVENIC_ITEMS), "Expected cavenic orb to resolve through cavenic_items");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicShroomCollisionAppliesBoundedNausea(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos supportPos = CAVENIC_COLLISION_ANCHOR;
        BlockPos shroomPos = supportPos.above();

        resetMiningArea(level, supportPos, 6.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(shroomPos, ModRegistries.CAVENIC_SHROOM.get().defaultBlockState(), Block.UPDATE_ALL);

        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, ItemStack.EMPTY, shroomPos);
        ((CavenicShroomBlock) ModRegistries.CAVENIC_SHROOM.get()).applyCollisionEffect(level, player);

        helper.runAfterDelay(2, () -> {
            helper.assertTrue(player.hasEffect(MobEffects.CONFUSION), "Expected cavenic shroom collision to apply bounded nausea");
            helper.assertFalse(player.hasEffect(MobEffects.POISON), "Cavenic shroom must not apply poison");
            helper.assertTrue(player.getHealth() == player.getMaxHealth(), "Cavenic shroom collision must stay non-damaging");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicShroomOrbHarvestPathIsBounded(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos playerAnchor = CAVENIC_SHEAR_ANCHOR;
        BlockPos supportPos = playerAnchor.east(2);
        BlockPos shroomPos = supportPos.above();
        ItemStack shears = new ItemStack(Items.SHEARS);

        resetMiningArea(level, playerAnchor, 8.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(shroomPos, ModRegistries.CAVENIC_SHROOM.get().defaultBlockState(), Block.UPDATE_ALL);

        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, shears, playerAnchor);
        CavenicShroomBlock shroomBlock = (CavenicShroomBlock) ModRegistries.CAVENIC_SHROOM.get();
        InteractionResult result = shroomBlock.tryShearHarvest(
            shears,
            level.getBlockState(shroomPos),
            level,
            shroomPos,
            player,
            InteractionHand.MAIN_HAND,
            0
        ).result();

        helper.assertTrue(result.consumesAction(), "Expected cavenic shroom to support the bounded shears harvest path");
        helper.runAfterDelay(5, () -> {
            helper.assertBlockNotPresent(ModRegistries.CAVENIC_SHROOM.get(), shroomPos);
            helper.assertTrue(shears.getDamageValue() == 1, "Expected cavenic shroom shears harvest to damage shears by one");
            helper.assertTrue(countEntities(level, EntityType.EXPERIENCE_ORB, shroomPos, 4.0D) == 0, "Cavenic shroom harvest must not create XP");
            helper.assertTrue(countEntities(level, EntityType.ITEM, shroomPos, 4.0D) <= 2, "Cavenic shroom harvest must stay bounded to the shroom drop and at most one orb");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicShroomPatchWorldgenKeysResolve(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<ConfiguredFeature<?, ?>> configuredFeatures = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        Registry<PlacedFeature> placedFeatures = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registries.BIOME);

        assertRegistryKeyPresent(helper, configuredFeatures, Registries.CONFIGURED_FEATURE, "cavernreborn:cavenic_shroom_patch");
        assertRegistryKeyPresent(helper, placedFeatures, Registries.PLACED_FEATURE, "cavernreborn:cavern_cavenic_shroom_patch");
        helper.assertTrue(
            biomes.containsKey(ResourceKey.create(Registries.BIOME, ResourceLocation.parse("cavernreborn:stone_depths"))),
            "Expected stone_depths biome key to resolve at runtime with cavenic shroom patch wiring"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicZombieRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.CAVENIC_ZOMBIE.get() != null, "Missing cavenic zombie entity type");
        helper.assertTrue(ModRegistries.CAVENIC_ZOMBIE_SPAWN_EGG.get() != null, "Missing cavenic zombie spawn egg");

        assertRegistryId(helper, ModRegistries.CAVENIC_ZOMBIE.get(), "cavernreborn:cavenic_zombie");
        assertRegistryId(helper, ModRegistries.CAVENIC_ZOMBIE_SPAWN_EGG.get(), "cavernreborn:cavenic_zombie_spawn_egg");

        ItemStack spawnEgg = cavenicZombieSpawnEgg();
        helper.assertTrue(!spawnEgg.isEmpty(), "Expected cavenic zombie spawn egg to be constructible");
        helper.assertTrue(spawnEgg.getItem() instanceof SpawnEggItem, "Expected cavenic zombie spawn egg runtime item");
        helper.assertTrue(
            ((SpawnEggItem) spawnEgg.getItem()).spawnsEntity(spawnEgg, ModRegistries.CAVENIC_ZOMBIE.get()),
            "Expected cavenic zombie spawn egg to resolve the cavenic zombie entity type"
        );
        helper.assertTrue(
            ModRegistries.CAVENIC_ZOMBIE.get().getCategory() == MobCategory.MONSTER,
            "Expected cavenic zombie type category to stay MONSTER"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicZombieSpawnsWithExpectedAttributesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_ZOMBIE_ANCHOR;

        resetMiningArea(level, origin, 8.0D);
        CavenicZombie zombie = spawnLivingEntity(helper, ModRegistries.CAVENIC_ZOMBIE.get(), origin);

        helper.assertTrue(zombie instanceof Monster, "Expected cavenic zombie to remain a hostile monster");
        helper.assertTrue(zombie instanceof Zombie, "Expected cavenic zombie to keep vanilla zombie behavior");
        helper.assertTrue(zombie.getType().getCategory() == MobCategory.MONSTER, "Expected cavenic zombie type category to stay MONSTER");
        helper.assertTrue(Math.abs(zombie.getMaxHealth() - 50.0D) < 1.0E-6D, "Expected cavenic zombie max health to map to legacy 50.0");
        helper.assertTrue(Math.abs(zombie.getAttributeValue(Attributes.MAX_HEALTH) - 50.0D) < 1.0E-6D, "Expected cavenic zombie MAX_HEALTH attribute to be 50.0");
        helper.assertTrue(Math.abs(zombie.getAttributeValue(Attributes.MOVEMENT_SPEED) - 0.23D) < 1.0E-6D, "Expected cavenic zombie movement speed to keep the vanilla zombie baseline");
        helper.assertTrue(Math.abs(zombie.getAttributeValue(Attributes.FOLLOW_RANGE) - 50.0D) < 1.0E-6D, "Expected cavenic zombie follow range to map to legacy 50.0");
        helper.assertTrue(Math.abs(zombie.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) - 1.0D) < 1.0E-6D, "Expected cavenic zombie knockback resistance to map to legacy 1.0");
        helper.assertTrue(Math.abs(zombie.getAttributeValue(Attributes.ATTACK_DAMAGE) - 5.0D) < 1.0E-6D, "Expected cavenic zombie attack damage to map to legacy 5.0");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicZombieSpawnEggCreatesRuntimeEntity(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos supportPos = CAVENIC_ZOMBIE_SPAWN_EGG_ANCHOR;

        resetMiningArea(level, supportPos, 8.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        ItemStack spawnEgg = cavenicZombieSpawnEgg();
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, spawnEgg.copy(), supportPos.south(2));
        Entity spawnedEntity = ModRegistries.CAVENIC_ZOMBIE.get().spawn(level, spawnEgg, player, supportPos.above(), MobSpawnType.SPAWN_EGG, true, true);

        helper.assertTrue(spawnedEntity instanceof CavenicZombie, "Expected spawn egg to create a CavenicZombie runtime entity");
        helper.assertTrue(spawnedEntity != null && spawnedEntity.isAlive(), "Expected spawned cavenic zombie to be alive");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicZombieNaturalSpawnPlacementIsRegisteredAndCavernOnly(GameTestHelper helper) {
        ServerLevel overworldLevel = helper.getLevel().getServer().getLevel(Level.OVERWORLD);
        BlockPos spawnPos = CAVENIC_ZOMBIE_NATURAL_SPAWN_ANCHOR;
        RandomSource random = RandomSource.create(1234L);

        helper.assertTrue(overworldLevel != null, "Expected overworld level to resolve for spawn predicate smoke");
        helper.assertTrue(
            SpawnPlacements.getPlacementType(ModRegistries.CAVENIC_ZOMBIE.get()) == SpawnPlacementTypes.ON_GROUND,
            "Expected cavenic zombie spawn placement type to mirror zombie ground spawning"
        );
        helper.assertTrue(
            SpawnPlacements.getHeightmapType(ModRegistries.CAVENIC_ZOMBIE.get()) == Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            "Expected cavenic zombie spawn heightmap to mirror zombie spawning"
        );
        helper.assertTrue(
            CavenicZombie.canNaturallySpawnInDimension(CavernNeoForgeDimensions.CAVERN_LEVEL_KEY),
            "Expected cavenic zombie natural-spawn helper to allow the configured CAVERN level key"
        );
        helper.assertFalse(
            CavenicZombie.canNaturallySpawnInDimension(Level.OVERWORLD),
            "Expected cavenic zombie natural-spawn helper to reject overworld spawning"
        );

        prepareNaturalSpawnPlatform(overworldLevel, spawnPos);
        helper.assertTrue(
            SpawnPlacements.isSpawnPositionOk(ModRegistries.CAVENIC_ZOMBIE.get(), overworldLevel, spawnPos),
            "Expected prepared spawn position to satisfy ON_GROUND placement"
        );
        helper.assertFalse(
            SpawnPlacements.checkSpawnRules(ModRegistries.CAVENIC_ZOMBIE.get(), overworldLevel, MobSpawnType.SPAWNER, spawnPos, random),
            "Expected registered cavenic zombie spawn rules to reject overworld spawning"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicZombieNaturalSpawnDataResolvesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<BiomeModifier> biomeModifiers = level.registryAccess().registryOrThrow(NeoForgeRegistries.Keys.BIOME_MODIFIERS);
        Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registries.BIOME);
        TagKey<Biome> spawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "spawns_cavenic_zombie"));

        assertRegistryKeyPresent(helper, biomeModifiers, NeoForgeRegistries.Keys.BIOME_MODIFIERS, "cavernreborn:cavenic_zombie_spawns");
        helper.assertTrue(biomes.getTag(spawnTag).isPresent(), "Expected cavenic zombie spawn biome tag to resolve at runtime");

        Set<String> taggedBiomes = biomes.getTag(spawnTag)
            .orElseThrow()
            .stream()
            .flatMap(holder -> holder.unwrapKey().stream())
            .map(key -> key.location().toString())
            .collect(java.util.stream.Collectors.toSet());

        helper.assertTrue(
            taggedBiomes.equals(Set.of(
                "cavernreborn:stone_depths",
                "cavernreborn:lush_grotto",
                "cavernreborn:dripstone_grotto",
                "cavernreborn:highland_hollows"
            )),
            "Expected cavenic zombie natural-spawn tag to stay scoped to the four CAVERN biomes"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicZombieLegacyOrbDropWiresAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_ZOMBIE_LOOT_ANCHOR;
        CavenicZombieLootEvents lootEvents = new CavenicZombieLootEvents();
        List<ItemEntity> drops = new ArrayList<>();

        resetMiningArea(level, origin, 8.0D);
        CavenicZombie zombie = spawnLivingEntity(helper, ModRegistries.CAVENIC_ZOMBIE.get(), origin);

        helper.assertTrue(
            zombie.getLootTable().equals(EntityType.ZOMBIE.getDefaultLootTable()),
            "Expected cavenic zombie to keep the vanilla zombie loot table as its baseline"
        );
        helper.assertTrue(
            CavenicZombieLootPolicy.ORB_DROP_ROLL_BOUND == 8,
            "Expected cavenic zombie orb drop roll bound to stay pinned to the legacy 1/8 chance"
        );
        helper.assertFalse(
            lootEvents.tryAppendLegacyOrbDrop(zombie, drops, 1),
            "Non-winning orb roll must not append a cavenic orb drop"
        );
        helper.assertTrue(drops.isEmpty(), "Non-winning orb roll must leave the drop list unchanged");
        helper.assertTrue(
            lootEvents.tryAppendLegacyOrbDrop(zombie, drops, 0),
            "Winning orb roll must append a cavenic orb drop"
        );
        helper.assertTrue(drops.size() == 1, "Winning orb roll must append exactly one cavenic orb drop");
        helper.assertTrue(
            drops.getFirst().getItem().is(ModRegistries.CAVENIC_ORB.get()),
            "Winning orb roll must append cavernreborn:cavenic_orb"
        );
        helper.assertTrue(
            Math.abs(drops.getFirst().getY() - (zombie.getY() + 0.5D)) < 1.0E-6D,
            "Expected cavenic orb drop Y offset to stay aligned with the legacy 0.5 offset"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicZombieLegacyFallAndFireDamageBehaviorAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_ZOMBIE_DAMAGE_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicZombie cavenicFallZombie = spawnLivingEntity(helper, ModRegistries.CAVENIC_ZOMBIE.get(), origin);
        Zombie vanillaFallZombie = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.east(4));
        clearEquipment(cavenicFallZombie);
        clearEquipment(vanillaFallZombie);
        float fallDamage = 10.0F;
        float cavenicFallHealthBefore = cavenicFallZombie.getHealth();
        float vanillaFallHealthBefore = vanillaFallZombie.getHealth();

        helper.assertTrue(
            cavenicFallZombie.hurt(level.damageSources().fall(), fallDamage),
            "Expected cavenic zombie to still accept fall damage hits through the normal server damage path"
        );
        helper.assertTrue(
            vanillaFallZombie.hurt(level.damageSources().fall(), fallDamage),
            "Expected vanilla zombie baseline to accept fall damage hits"
        );
        helper.assertTrue(
            Math.abs((cavenicFallHealthBefore - cavenicFallZombie.getHealth()) - (fallDamage * CavenicZombie.LEGACY_FALL_DAMAGE_MULTIPLIER)) < 1.0E-6F,
            "Expected cavenic zombie fall damage to stay pinned to the legacy 0.35 multiplier"
        );
        helper.assertTrue(
            Math.abs((vanillaFallHealthBefore - vanillaFallZombie.getHealth()) - fallDamage) < 1.0E-6F,
            "Expected vanilla zombie baseline to keep full fall damage"
        );

        CavenicZombie cavenicFireZombie = spawnLivingEntity(helper, ModRegistries.CAVENIC_ZOMBIE.get(), origin.south(8));
        Zombie vanillaFireZombie = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.south(8).east(4));
        clearEquipment(cavenicFireZombie);
        clearEquipment(vanillaFireZombie);
        float fireDamage = 4.0F;
        float cavenicFireHealthBefore = cavenicFireZombie.getHealth();
        float vanillaFireHealthBefore = vanillaFireZombie.getHealth();

        helper.assertFalse(
            cavenicFireZombie.hurt(level.damageSources().lava(), fireDamage),
            "Expected cavenic zombie to reject fire-tagged damage sources like lava"
        );
        helper.assertTrue(
            vanillaFireZombie.hurt(level.damageSources().lava(), fireDamage),
            "Expected vanilla zombie baseline to take lava damage"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - cavenicFireZombie.getHealth()) < 1.0E-6F,
            "Expected cavenic zombie health to stay unchanged after fire-tagged damage"
        );
        helper.assertTrue(
            vanillaFireZombie.getHealth() < vanillaFireHealthBefore,
            "Expected vanilla zombie baseline to lose health from lava damage"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicZombieLegacyDamageBehaviorKeepsGenericDamageVanillaLikeAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_ZOMBIE_DAMAGE_ANCHOR.north(12);

        resetMiningArea(level, origin, 16.0D);

        CavenicZombie cavenicZombie = spawnLivingEntity(helper, ModRegistries.CAVENIC_ZOMBIE.get(), origin);
        Zombie vanillaZombie = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.east(4));
        clearEquipment(cavenicZombie);
        clearEquipment(vanillaZombie);
        float genericDamage = 6.0F;
        float cavenicHealthBefore = cavenicZombie.getHealth();
        float vanillaHealthBefore = vanillaZombie.getHealth();

        helper.assertTrue(
            cavenicZombie.hurt(level.damageSources().generic(), genericDamage),
            "Expected cavenic zombie to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            vanillaZombie.hurt(level.damageSources().generic(), genericDamage),
            "Expected vanilla zombie baseline to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            Math.abs((cavenicHealthBefore - cavenicZombie.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected cavenic zombie generic damage intake to stay vanilla-like"
        );
        helper.assertTrue(
            Math.abs((vanillaHealthBefore - vanillaZombie.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected vanilla zombie generic damage intake to stay unchanged"
        );
        helper.assertTrue(
            cavenicZombie.getLootTable().equals(EntityType.ZOMBIE.getDefaultLootTable()),
            "Expected cavenic zombie damage follow-up to keep the vanilla zombie loot-table baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSkeletonRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.CAVENIC_SKELETON.get() != null, "Missing cavenic skeleton entity type");
        helper.assertTrue(ModRegistries.CAVENIC_SKELETON_SPAWN_EGG.get() != null, "Missing cavenic skeleton spawn egg");

        assertRegistryId(helper, ModRegistries.CAVENIC_SKELETON.get(), "cavernreborn:cavenic_skeleton");
        assertRegistryId(helper, ModRegistries.CAVENIC_SKELETON_SPAWN_EGG.get(), "cavernreborn:cavenic_skeleton_spawn_egg");

        ItemStack spawnEgg = cavenicSkeletonSpawnEgg();
        helper.assertTrue(!spawnEgg.isEmpty(), "Expected cavenic skeleton spawn egg to be constructible");
        helper.assertTrue(spawnEgg.getItem() instanceof SpawnEggItem, "Expected cavenic skeleton spawn egg runtime item");
        helper.assertTrue(
            ((SpawnEggItem) spawnEgg.getItem()).spawnsEntity(spawnEgg, ModRegistries.CAVENIC_SKELETON.get()),
            "Expected cavenic skeleton spawn egg to resolve the cavenic skeleton entity type"
        );
        helper.assertTrue(
            ModRegistries.CAVENIC_SKELETON.get().getCategory() == MobCategory.MONSTER,
            "Expected cavenic skeleton type category to stay MONSTER"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSkeletonSpawnsWithExpectedAttributesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SKELETON_ANCHOR;

        resetMiningArea(level, origin, 8.0D);
        CavenicSkeleton cavenicSkeleton = spawnLivingEntity(helper, ModRegistries.CAVENIC_SKELETON.get(), origin);
        Skeleton vanillaSkeleton = spawnLivingEntity(helper, EntityType.SKELETON, origin.east(4));

        helper.assertTrue(cavenicSkeleton instanceof Monster, "Expected cavenic skeleton to remain a hostile monster");
        helper.assertTrue(cavenicSkeleton instanceof Skeleton, "Expected cavenic skeleton to keep vanilla skeleton behavior");
        helper.assertTrue(cavenicSkeleton.getType().getCategory() == MobCategory.MONSTER, "Expected cavenic skeleton type category to stay MONSTER");
        helper.assertTrue(Math.abs(cavenicSkeleton.getMaxHealth() - 40.0D) < 1.0E-6D, "Expected cavenic skeleton max health to map to legacy 40.0");
        helper.assertTrue(Math.abs(cavenicSkeleton.getAttributeValue(Attributes.MAX_HEALTH) - 40.0D) < 1.0E-6D, "Expected cavenic skeleton MAX_HEALTH attribute to be 40.0");
        helper.assertTrue(Math.abs(cavenicSkeleton.getAttributeValue(Attributes.MOVEMENT_SPEED) - 0.2D) < 1.0E-6D, "Expected cavenic skeleton movement speed to map to legacy 0.2");
        helper.assertTrue(Math.abs(cavenicSkeleton.getAttributeValue(Attributes.FOLLOW_RANGE) - 21.0D) < 1.0E-6D, "Expected cavenic skeleton follow range to map to legacy 21.0");
        helper.assertTrue(Math.abs(cavenicSkeleton.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) - 1.0D) < 1.0E-6D, "Expected cavenic skeleton knockback resistance to map to legacy 1.0");
        helper.assertTrue(
            Math.abs(cavenicSkeleton.getAttributeValue(Attributes.ATTACK_DAMAGE) - vanillaSkeleton.getAttributeValue(Attributes.ATTACK_DAMAGE)) < 1.0E-6D,
            "Expected cavenic skeleton attack damage to stay on the vanilla skeleton baseline"
        );
        helper.assertTrue(
            cavenicSkeleton.getLootTable().equals(EntityType.SKELETON.getDefaultLootTable()),
            "Expected cavenic skeleton to keep the vanilla skeleton loot table as its baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSkeletonSpawnEggCreatesRuntimeEntity(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos supportPos = CAVENIC_SKELETON_SPAWN_EGG_ANCHOR;

        resetMiningArea(level, supportPos, 8.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        ItemStack spawnEgg = cavenicSkeletonSpawnEgg();
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, spawnEgg.copy(), supportPos.south(2));
        Entity spawnedEntity = ModRegistries.CAVENIC_SKELETON.get().spawn(level, spawnEgg, player, supportPos.above(), MobSpawnType.SPAWN_EGG, true, true);

        helper.assertTrue(spawnedEntity instanceof CavenicSkeleton, "Expected spawn egg to create a CavenicSkeleton runtime entity");
        helper.assertTrue(spawnedEntity != null && spawnedEntity.isAlive(), "Expected spawned cavenic skeleton to be alive");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSkeletonNaturalSpawnPlacementIsRegisteredAndCavernOnly(GameTestHelper helper) {
        ServerLevel overworldLevel = helper.getLevel().getServer().getLevel(Level.OVERWORLD);
        BlockPos spawnPos = CAVENIC_SKELETON_NATURAL_SPAWN_ANCHOR;
        RandomSource random = RandomSource.create(5678L);

        helper.assertTrue(overworldLevel != null, "Expected overworld level to resolve for spawn predicate smoke");
        helper.assertTrue(
            SpawnPlacements.getPlacementType(ModRegistries.CAVENIC_SKELETON.get()) == SpawnPlacementTypes.ON_GROUND,
            "Expected cavenic skeleton spawn placement type to mirror skeleton ground spawning"
        );
        helper.assertTrue(
            SpawnPlacements.getHeightmapType(ModRegistries.CAVENIC_SKELETON.get()) == Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            "Expected cavenic skeleton spawn heightmap to mirror skeleton spawning"
        );
        helper.assertTrue(
            CavenicSkeleton.canNaturallySpawnInDimension(CavernNeoForgeDimensions.CAVERN_LEVEL_KEY),
            "Expected cavenic skeleton natural-spawn helper to allow the configured CAVERN level key"
        );
        helper.assertFalse(
            CavenicSkeleton.canNaturallySpawnInDimension(Level.OVERWORLD),
            "Expected cavenic skeleton natural-spawn helper to reject overworld spawning"
        );

        prepareNaturalSpawnPlatform(overworldLevel, spawnPos);
        helper.assertTrue(
            SpawnPlacements.isSpawnPositionOk(ModRegistries.CAVENIC_SKELETON.get(), overworldLevel, spawnPos),
            "Expected prepared spawn position to satisfy ON_GROUND placement"
        );
        helper.assertFalse(
            SpawnPlacements.checkSpawnRules(ModRegistries.CAVENIC_SKELETON.get(), overworldLevel, MobSpawnType.SPAWNER, spawnPos, random),
            "Expected registered cavenic skeleton spawn rules to reject overworld spawning"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSkeletonNaturalSpawnDataResolvesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<BiomeModifier> biomeModifiers = level.registryAccess().registryOrThrow(NeoForgeRegistries.Keys.BIOME_MODIFIERS);
        Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registries.BIOME);
        TagKey<Biome> spawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "spawns_cavenic_skeleton"));

        assertRegistryKeyPresent(helper, biomeModifiers, NeoForgeRegistries.Keys.BIOME_MODIFIERS, "cavernreborn:cavenic_skeleton_spawns");
        helper.assertTrue(biomes.getTag(spawnTag).isPresent(), "Expected cavenic skeleton spawn biome tag to resolve at runtime");

        Set<String> taggedBiomes = biomes.getTag(spawnTag)
            .orElseThrow()
            .stream()
            .flatMap(holder -> holder.unwrapKey().stream())
            .map(key -> key.location().toString())
            .collect(java.util.stream.Collectors.toSet());

        helper.assertTrue(
            taggedBiomes.equals(Set.of(
                "cavernreborn:stone_depths",
                "cavernreborn:lush_grotto",
                "cavernreborn:dripstone_grotto",
                "cavernreborn:highland_hollows"
            )),
            "Expected cavenic skeleton natural-spawn tag to stay scoped to the four CAVERN biomes"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSkeletonLegacyOrbDropWiresAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SKELETON_LOOT_ANCHOR;
        CavenicSkeletonLootEvents lootEvents = new CavenicSkeletonLootEvents();
        List<ItemEntity> drops = new ArrayList<>();

        resetMiningArea(level, origin, 8.0D);
        CavenicSkeleton skeleton = spawnLivingEntity(helper, ModRegistries.CAVENIC_SKELETON.get(), origin);

        helper.assertTrue(
            skeleton.getLootTable().equals(EntityType.SKELETON.getDefaultLootTable()),
            "Expected cavenic skeleton to keep the vanilla skeleton loot table as its baseline"
        );
        helper.assertTrue(
            CavenicSkeletonLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic skeleton orb drop roll bound to stay pinned to the legacy 1/5 chance"
        );
        helper.assertTrue(
            CavenicSkeleton.NATURAL_SPAWN_WEIGHT == 20
                && CavenicSkeleton.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicSkeleton.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic skeleton natural spawn constants to stay pinned while wiring the orb drop follow-up"
        );
        helper.assertFalse(
            lootEvents.tryAppendLegacyOrbDrop(skeleton, drops, 1),
            "Non-winning orb roll must not append a cavenic orb drop"
        );
        helper.assertTrue(drops.isEmpty(), "Non-winning orb roll must leave the drop list unchanged");
        helper.assertTrue(
            lootEvents.tryAppendLegacyOrbDrop(skeleton, drops, 0),
            "Winning orb roll must append a cavenic orb drop"
        );
        helper.assertTrue(drops.size() == 1, "Winning orb roll must append exactly one cavenic orb drop");
        helper.assertTrue(
            drops.getFirst().getItem().is(ModRegistries.CAVENIC_ORB.get()),
            "Winning orb roll must append cavernreborn:cavenic_orb"
        );
        helper.assertTrue(
            Math.abs(drops.getFirst().getY() - (skeleton.getY() + 0.5D)) < 1.0E-6D,
            "Expected cavenic orb drop Y offset to stay aligned with the legacy 0.5 offset"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSkeletonLegacyFallAndFireDamageBehaviorAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SKELETON_DAMAGE_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicSkeleton cavenicFallSkeleton = spawnLivingEntity(helper, ModRegistries.CAVENIC_SKELETON.get(), origin);
        Skeleton vanillaFallSkeleton = spawnLivingEntity(helper, EntityType.SKELETON, origin.east(4));
        clearEquipment(cavenicFallSkeleton);
        clearEquipment(vanillaFallSkeleton);
        float fallDamage = 10.0F;
        float cavenicFallHealthBefore = cavenicFallSkeleton.getHealth();
        float vanillaFallHealthBefore = vanillaFallSkeleton.getHealth();

        helper.assertTrue(
            cavenicFallSkeleton.hurt(level.damageSources().fall(), fallDamage),
            "Expected cavenic skeleton to still accept fall damage hits through the normal server damage path"
        );
        helper.assertTrue(
            vanillaFallSkeleton.hurt(level.damageSources().fall(), fallDamage),
            "Expected vanilla skeleton baseline to accept fall damage hits"
        );
        helper.assertTrue(
            Math.abs((cavenicFallHealthBefore - cavenicFallSkeleton.getHealth()) - (fallDamage * CavenicSkeleton.LEGACY_FALL_DAMAGE_MULTIPLIER)) < 1.0E-6F,
            "Expected cavenic skeleton fall damage to stay pinned to the legacy 0.35 multiplier"
        );
        helper.assertTrue(
            Math.abs((vanillaFallHealthBefore - vanillaFallSkeleton.getHealth()) - fallDamage) < 1.0E-6F,
            "Expected vanilla skeleton baseline to keep full fall damage"
        );

        CavenicSkeleton cavenicFireSkeleton = spawnLivingEntity(helper, ModRegistries.CAVENIC_SKELETON.get(), origin.south(8));
        Skeleton vanillaFireSkeleton = spawnLivingEntity(helper, EntityType.SKELETON, origin.south(8).east(4));
        clearEquipment(cavenicFireSkeleton);
        clearEquipment(vanillaFireSkeleton);
        float fireDamage = 4.0F;
        float cavenicFireHealthBefore = cavenicFireSkeleton.getHealth();
        float vanillaFireHealthBefore = vanillaFireSkeleton.getHealth();

        helper.assertFalse(
            cavenicFireSkeleton.hurt(level.damageSources().lava(), fireDamage),
            "Expected cavenic skeleton to reject fire-tagged damage sources like lava"
        );
        helper.assertTrue(
            vanillaFireSkeleton.hurt(level.damageSources().lava(), fireDamage),
            "Expected vanilla skeleton baseline to take lava damage"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - cavenicFireSkeleton.getHealth()) < 1.0E-6F,
            "Expected cavenic skeleton health to stay unchanged after fire-tagged damage"
        );
        helper.assertTrue(
            vanillaFireSkeleton.getHealth() < vanillaFireHealthBefore,
            "Expected vanilla skeleton baseline to lose health from lava damage"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSkeletonLegacyDamageBehaviorKeepsGenericDamageVanillaLikeAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SKELETON_DAMAGE_ANCHOR.north(12);

        resetMiningArea(level, origin, 16.0D);

        CavenicSkeleton cavenicSkeleton = spawnLivingEntity(helper, ModRegistries.CAVENIC_SKELETON.get(), origin);
        Skeleton vanillaSkeleton = spawnLivingEntity(helper, EntityType.SKELETON, origin.east(4));
        clearEquipment(cavenicSkeleton);
        clearEquipment(vanillaSkeleton);
        float genericDamage = 6.0F;
        float cavenicHealthBefore = cavenicSkeleton.getHealth();
        float vanillaHealthBefore = vanillaSkeleton.getHealth();

        helper.assertTrue(
            cavenicSkeleton.hurt(level.damageSources().generic(), genericDamage),
            "Expected cavenic skeleton to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            vanillaSkeleton.hurt(level.damageSources().generic(), genericDamage),
            "Expected vanilla skeleton baseline to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            Math.abs((cavenicHealthBefore - cavenicSkeleton.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected cavenic skeleton generic damage intake to stay vanilla-like"
        );
        helper.assertTrue(
            Math.abs((vanillaHealthBefore - vanillaSkeleton.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected vanilla skeleton generic damage intake to stay unchanged"
        );
        helper.assertTrue(
            cavenicSkeleton.getLootTable().equals(EntityType.SKELETON.getDefaultLootTable()),
            "Expected cavenic skeleton damage follow-up to keep the vanilla skeleton loot-table baseline"
        );
        helper.assertTrue(
            CavenicSkeletonLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic skeleton orb drop roll bound to remain pinned while restoring legacy damage behavior"
        );
        helper.assertTrue(
            CavenicSkeleton.NATURAL_SPAWN_WEIGHT == 20
                && CavenicSkeleton.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicSkeleton.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic skeleton natural spawn constants to stay unchanged while restoring legacy damage behavior"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.CAVENIC_CREEPER.get() != null, "Missing cavenic creeper entity type");
        helper.assertTrue(ModRegistries.CAVENIC_CREEPER_SPAWN_EGG.get() != null, "Missing cavenic creeper spawn egg");

        assertRegistryId(helper, ModRegistries.CAVENIC_CREEPER.get(), "cavernreborn:cavenic_creeper");
        assertRegistryId(helper, ModRegistries.CAVENIC_CREEPER_SPAWN_EGG.get(), "cavernreborn:cavenic_creeper_spawn_egg");

        ItemStack spawnEgg = cavenicCreeperSpawnEgg();
        helper.assertTrue(!spawnEgg.isEmpty(), "Expected cavenic creeper spawn egg to be constructible");
        helper.assertTrue(spawnEgg.getItem() instanceof SpawnEggItem, "Expected cavenic creeper spawn egg runtime item");
        helper.assertTrue(
            ((SpawnEggItem) spawnEgg.getItem()).spawnsEntity(spawnEgg, ModRegistries.CAVENIC_CREEPER.get()),
            "Expected cavenic creeper spawn egg to resolve the cavenic creeper entity type"
        );
        helper.assertTrue(
            ModRegistries.CAVENIC_CREEPER.get().getCategory() == MobCategory.MONSTER,
            "Expected cavenic creeper type category to stay MONSTER"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperSpawnsWithExpectedAttributesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_CREEPER_ANCHOR;

        resetMiningArea(level, origin, 8.0D);
        CavenicCreeper cavenicCreeper = spawnLivingEntity(helper, ModRegistries.CAVENIC_CREEPER.get(), origin);
        Creeper vanillaCreeper = spawnLivingEntity(helper, EntityType.CREEPER, origin.east(4));

        helper.assertTrue(cavenicCreeper instanceof Monster, "Expected cavenic creeper to remain a hostile monster");
        helper.assertTrue(cavenicCreeper instanceof Creeper, "Expected cavenic creeper to keep vanilla creeper behavior");
        helper.assertTrue(cavenicCreeper.getType().getCategory() == MobCategory.MONSTER, "Expected cavenic creeper type category to stay MONSTER");
        helper.assertTrue(Math.abs(cavenicCreeper.getMaxHealth() - 30.0D) < 1.0E-6D, "Expected cavenic creeper max health to map to legacy 30.0");
        helper.assertTrue(Math.abs(cavenicCreeper.getAttributeValue(Attributes.MAX_HEALTH) - 30.0D) < 1.0E-6D, "Expected cavenic creeper MAX_HEALTH attribute to be 30.0");
        helper.assertTrue(Math.abs(cavenicCreeper.getAttributeValue(Attributes.MOVEMENT_SPEED) - 0.2D) < 1.0E-6D, "Expected cavenic creeper movement speed to map to legacy 0.2");
        helper.assertTrue(Math.abs(cavenicCreeper.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) - 0.85D) < 1.0E-6D, "Expected cavenic creeper knockback resistance to map to legacy 0.85");
        helper.assertTrue(
            Math.abs(cavenicCreeper.getAttributeValue(Attributes.FOLLOW_RANGE) - vanillaCreeper.getAttributeValue(Attributes.FOLLOW_RANGE)) < 1.0E-6D,
            "Expected cavenic creeper follow range to stay on the vanilla creeper baseline"
        );
        helper.assertTrue(
            cavenicCreeper.getLootTable().equals(EntityType.CREEPER.getDefaultLootTable()),
            "Expected cavenic creeper to keep the vanilla creeper loot table as its baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperSpawnEggCreatesRuntimeEntity(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos supportPos = CAVENIC_CREEPER_SPAWN_EGG_ANCHOR;

        resetMiningArea(level, supportPos, 8.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        ItemStack spawnEgg = cavenicCreeperSpawnEgg();
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, spawnEgg.copy(), supportPos.south(2));
        Entity spawnedEntity = ModRegistries.CAVENIC_CREEPER.get().spawn(level, spawnEgg, player, supportPos.above(), MobSpawnType.SPAWN_EGG, true, true);

        helper.assertTrue(spawnedEntity instanceof CavenicCreeper, "Expected spawn egg to create a CavenicCreeper runtime entity");
        helper.assertTrue(spawnedEntity != null && spawnedEntity.isAlive(), "Expected spawned cavenic creeper to be alive");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperNaturalSpawnPlacementIsRegisteredAndCavernOnly(GameTestHelper helper) {
        ServerLevel overworldLevel = helper.getLevel().getServer().getLevel(Level.OVERWORLD);
        BlockPos spawnPos = CAVENIC_CREEPER_NATURAL_SPAWN_ANCHOR;
        RandomSource random = RandomSource.create(9012L);

        helper.assertTrue(overworldLevel != null, "Expected overworld level to resolve for spawn predicate smoke");
        helper.assertTrue(
            SpawnPlacements.getPlacementType(ModRegistries.CAVENIC_CREEPER.get()) == SpawnPlacementTypes.ON_GROUND,
            "Expected cavenic creeper spawn placement type to mirror creeper ground spawning"
        );
        helper.assertTrue(
            SpawnPlacements.getHeightmapType(ModRegistries.CAVENIC_CREEPER.get()) == Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            "Expected cavenic creeper spawn heightmap to mirror creeper spawning"
        );
        helper.assertTrue(
            CavenicCreeper.canNaturallySpawnInDimension(CavernNeoForgeDimensions.CAVERN_LEVEL_KEY),
            "Expected cavenic creeper natural-spawn helper to allow the configured CAVERN level key"
        );
        helper.assertFalse(
            CavenicCreeper.canNaturallySpawnInDimension(Level.OVERWORLD),
            "Expected cavenic creeper natural-spawn helper to reject overworld spawning"
        );

        prepareNaturalSpawnPlatform(overworldLevel, spawnPos);
        helper.assertTrue(
            SpawnPlacements.isSpawnPositionOk(ModRegistries.CAVENIC_CREEPER.get(), overworldLevel, spawnPos),
            "Expected prepared spawn position to satisfy ON_GROUND placement"
        );
        helper.assertFalse(
            SpawnPlacements.checkSpawnRules(ModRegistries.CAVENIC_CREEPER.get(), overworldLevel, MobSpawnType.SPAWNER, spawnPos, random),
            "Expected registered cavenic creeper spawn rules to reject overworld spawning"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperNaturalSpawnDataResolvesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<BiomeModifier> biomeModifiers = level.registryAccess().registryOrThrow(NeoForgeRegistries.Keys.BIOME_MODIFIERS);
        Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registries.BIOME);
        TagKey<Biome> spawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "spawns_cavenic_creeper"));

        assertRegistryKeyPresent(helper, biomeModifiers, NeoForgeRegistries.Keys.BIOME_MODIFIERS, "cavernreborn:cavenic_creeper_spawns");
        helper.assertTrue(biomes.getTag(spawnTag).isPresent(), "Expected cavenic creeper spawn biome tag to resolve at runtime");

        Set<String> taggedBiomes = biomes.getTag(spawnTag)
            .orElseThrow()
            .stream()
            .flatMap(holder -> holder.unwrapKey().stream())
            .map(key -> key.location().toString())
            .collect(java.util.stream.Collectors.toSet());

        helper.assertTrue(
            taggedBiomes.equals(Set.of(
                "cavernreborn:stone_depths",
                "cavernreborn:lush_grotto",
                "cavernreborn:dripstone_grotto",
                "cavernreborn:highland_hollows"
            )),
            "Expected cavenic creeper natural-spawn tag to stay scoped to the four CAVERN biomes"
        );
        helper.assertTrue(
            CavenicCreeper.NATURAL_SPAWN_WEIGHT == 30
                && CavenicCreeper.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicCreeper.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic creeper natural spawn constants to stay pinned to the legacy spawn entry"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperLegacyOrbDropWiresAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_CREEPER_LOOT_ANCHOR;
        CavenicCreeperLootEvents lootEvents = new CavenicCreeperLootEvents();
        List<ItemEntity> drops = new ArrayList<>();

        resetMiningArea(level, origin, 8.0D);
        CavenicCreeper creeper = spawnLivingEntity(helper, ModRegistries.CAVENIC_CREEPER.get(), origin);

        helper.assertTrue(
            creeper.getLootTable().equals(EntityType.CREEPER.getDefaultLootTable()),
            "Expected cavenic creeper to keep the vanilla creeper loot table as its baseline"
        );
        helper.assertTrue(
            CavenicCreeperLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic creeper orb drop roll bound to stay pinned to the legacy 1/5 chance"
        );
        helper.assertTrue(
            CavenicCreeper.NATURAL_SPAWN_WEIGHT == 30
                && CavenicCreeper.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicCreeper.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic creeper natural spawn constants to stay pinned while wiring the orb drop follow-up"
        );
        helper.assertFalse(
            lootEvents.tryAppendLegacyOrbDrop(creeper, drops, 1),
            "Non-winning orb roll must not append a cavenic orb drop"
        );
        helper.assertTrue(drops.isEmpty(), "Non-winning orb roll must leave the drop list unchanged");
        helper.assertTrue(
            lootEvents.tryAppendLegacyOrbDrop(creeper, drops, 0),
            "Winning orb roll must append a cavenic orb drop"
        );
        helper.assertTrue(drops.size() == 1, "Winning orb roll must append exactly one cavenic orb drop");
        helper.assertTrue(
            drops.getFirst().getItem().is(ModRegistries.CAVENIC_ORB.get()),
            "Winning orb roll must append cavernreborn:cavenic_orb"
        );
        helper.assertTrue(
            Math.abs(drops.getFirst().getY() - (creeper.getY() + 0.5D)) < 1.0E-6D,
            "Expected cavenic orb drop Y offset to stay aligned with the legacy 0.5 offset"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperLegacyFallAndFireDamageBehaviorAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_CREEPER_DAMAGE_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicCreeper cavenicFallCreeper = spawnLivingEntity(helper, ModRegistries.CAVENIC_CREEPER.get(), origin);
        Creeper vanillaFallCreeper = spawnLivingEntity(helper, EntityType.CREEPER, origin.east(4));
        clearEquipment(cavenicFallCreeper);
        clearEquipment(vanillaFallCreeper);
        float fallDamage = 10.0F;
        float cavenicFallHealthBefore = cavenicFallCreeper.getHealth();
        float vanillaFallHealthBefore = vanillaFallCreeper.getHealth();

        helper.assertTrue(
            cavenicFallCreeper.hurt(level.damageSources().fall(), fallDamage),
            "Expected cavenic creeper to still accept fall damage hits through the normal server damage path"
        );
        helper.assertTrue(
            vanillaFallCreeper.hurt(level.damageSources().fall(), fallDamage),
            "Expected vanilla creeper baseline to accept fall damage hits"
        );
        helper.assertTrue(
            Math.abs((cavenicFallHealthBefore - cavenicFallCreeper.getHealth()) - (fallDamage * CavenicCreeper.LEGACY_FALL_DAMAGE_MULTIPLIER)) < 1.0E-6F,
            "Expected cavenic creeper fall damage to stay pinned to the legacy 0.35 multiplier"
        );
        helper.assertTrue(
            Math.abs((vanillaFallHealthBefore - vanillaFallCreeper.getHealth()) - fallDamage) < 1.0E-6F,
            "Expected vanilla creeper baseline to keep full fall damage"
        );

        CavenicCreeper cavenicFireCreeper = spawnLivingEntity(helper, ModRegistries.CAVENIC_CREEPER.get(), origin.south(8));
        Creeper vanillaFireCreeper = spawnLivingEntity(helper, EntityType.CREEPER, origin.south(8).east(4));
        clearEquipment(cavenicFireCreeper);
        clearEquipment(vanillaFireCreeper);
        float fireDamage = 4.0F;
        float cavenicFireHealthBefore = cavenicFireCreeper.getHealth();
        float vanillaFireHealthBefore = vanillaFireCreeper.getHealth();

        helper.assertFalse(
            cavenicFireCreeper.hurt(level.damageSources().lava(), fireDamage),
            "Expected cavenic creeper to reject fire-tagged damage sources like lava"
        );
        helper.assertTrue(
            vanillaFireCreeper.hurt(level.damageSources().lava(), fireDamage),
            "Expected vanilla creeper baseline to take lava damage"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - cavenicFireCreeper.getHealth()) < 1.0E-6F,
            "Expected cavenic creeper health to stay unchanged after fire-tagged damage"
        );
        helper.assertTrue(
            vanillaFireCreeper.getHealth() < vanillaFireHealthBefore,
            "Expected vanilla creeper baseline to lose health from lava damage"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperLegacyDamageBehaviorKeepsGenericDamageVanillaLikeAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_CREEPER_DAMAGE_ANCHOR.north(12);

        resetMiningArea(level, origin, 16.0D);

        CavenicCreeper cavenicCreeper = spawnLivingEntity(helper, ModRegistries.CAVENIC_CREEPER.get(), origin);
        Creeper vanillaCreeper = spawnLivingEntity(helper, EntityType.CREEPER, origin.east(4));
        clearEquipment(cavenicCreeper);
        clearEquipment(vanillaCreeper);
        float genericDamage = 6.0F;
        float cavenicHealthBefore = cavenicCreeper.getHealth();
        float vanillaHealthBefore = vanillaCreeper.getHealth();

        helper.assertTrue(
            cavenicCreeper.hurt(level.damageSources().generic(), genericDamage),
            "Expected cavenic creeper to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            vanillaCreeper.hurt(level.damageSources().generic(), genericDamage),
            "Expected vanilla creeper baseline to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            Math.abs((cavenicHealthBefore - cavenicCreeper.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected cavenic creeper generic damage intake to stay vanilla-like"
        );
        helper.assertTrue(
            Math.abs((vanillaHealthBefore - vanillaCreeper.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected vanilla creeper generic damage intake to stay unchanged"
        );
        helper.assertTrue(
            cavenicCreeper.getLootTable().equals(EntityType.CREEPER.getDefaultLootTable()),
            "Expected cavenic creeper damage follow-up to keep the vanilla creeper loot-table baseline"
        );
        helper.assertTrue(
            CavenicCreeperLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic creeper orb drop roll bound to remain pinned while restoring legacy damage behavior"
        );
        helper.assertTrue(
            CavenicCreeper.NATURAL_SPAWN_WEIGHT == 30
                && CavenicCreeper.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicCreeper.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic creeper natural spawn constants to stay unchanged while restoring legacy damage behavior"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperLegacyFuseAndExplosionValuesApplyAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_CREEPER_FUSE_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicCreeper cavenicCreeper = spawnLivingEntity(helper, ModRegistries.CAVENIC_CREEPER.get(), origin);
        Creeper vanillaCreeper = spawnLivingEntity(helper, EntityType.CREEPER, origin.east(4));

        CompoundTag cavenicData = cavenicCreeper.saveWithoutId(new CompoundTag());
        CompoundTag vanillaData = vanillaCreeper.saveWithoutId(new CompoundTag());

        helper.assertTrue(
            cavenicData.getShort("Fuse") == CavenicCreeper.LEGACY_FUSE_TIME,
            "Expected cavenic creeper saved Fuse value to stay pinned to the legacy 15-tick fuse"
        );
        helper.assertTrue(
            cavenicData.getByte("ExplosionRadius") == CavenicCreeper.LEGACY_EXPLOSION_RADIUS,
            "Expected cavenic creeper saved ExplosionRadius value to stay pinned to the legacy radius 5"
        );
        helper.assertTrue(
            vanillaData.getShort("Fuse") != cavenicData.getShort("Fuse"),
            "Expected vanilla creeper baseline to keep a different fuse length than the legacy cavenic creeper"
        );
        helper.assertTrue(
            vanillaData.getByte("ExplosionRadius") != cavenicData.getByte("ExplosionRadius"),
            "Expected vanilla creeper baseline to keep a different explosion radius than the legacy cavenic creeper"
        );

        CavenicCreeper roundTripCreeper = new CavenicCreeper(ModRegistries.CAVENIC_CREEPER.get(), level);
        roundTripCreeper.load(cavenicData.copy());
        CompoundTag roundTripData = roundTripCreeper.saveWithoutId(new CompoundTag());

        helper.assertTrue(
            roundTripData.getShort("Fuse") == CavenicCreeper.LEGACY_FUSE_TIME,
            "Expected cavenic creeper Fuse to survive a save/load style round trip"
        );
        helper.assertTrue(
            roundTripData.getByte("ExplosionRadius") == CavenicCreeper.LEGACY_EXPLOSION_RADIUS,
            "Expected cavenic creeper ExplosionRadius to survive a save/load style round trip"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicCreeperLegacyFuseAndExplosionValuesDoNotChangeOtherSlicesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_CREEPER_FUSE_ANCHOR.north(12);

        resetMiningArea(level, origin, 16.0D);

        CavenicCreeper cavenicCreeper = spawnLivingEntity(helper, ModRegistries.CAVENIC_CREEPER.get(), origin);
        CompoundTag cavenicData = cavenicCreeper.saveWithoutId(new CompoundTag());
        float healthBeforeFireHit = cavenicCreeper.getHealth();

        helper.assertTrue(
            cavenicCreeper.getLootTable().equals(EntityType.CREEPER.getDefaultLootTable()),
            "Expected cavenic creeper fuse/explosion follow-up to keep the vanilla creeper loot-table baseline"
        );
        helper.assertTrue(
            CavenicCreeperLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic creeper orb drop roll bound to remain pinned while restoring legacy fuse and explosion values"
        );
        helper.assertTrue(
            CavenicCreeper.NATURAL_SPAWN_WEIGHT == 30
                && CavenicCreeper.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicCreeper.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic creeper natural spawn constants to stay unchanged while restoring legacy fuse and explosion values"
        );
        helper.assertTrue(
            Math.abs(CavenicCreeper.LEGACY_FALL_DAMAGE_MULTIPLIER - 0.35F) < 1.0E-6F,
            "Expected cavenic creeper legacy fall-damage multiplier to remain pinned while restoring legacy fuse and explosion values"
        );
        helper.assertFalse(
            cavenicCreeper.hurt(level.damageSources().lava(), 2.0F),
            "Expected cavenic creeper fuse/explosion follow-up to keep the legacy fire-damage immunity behavior"
        );
        helper.assertTrue(
            Math.abs(healthBeforeFireHit - cavenicCreeper.getHealth()) < 1.0E-6F,
            "Expected cavenic creeper fuse/explosion follow-up to leave fire-damage immunity unchanged"
        );
        helper.assertTrue(
            cavenicData.getShort("Fuse") == CavenicCreeper.LEGACY_FUSE_TIME
                && cavenicData.getByte("ExplosionRadius") == CavenicCreeper.LEGACY_EXPLOSION_RADIUS,
            "Expected cavenic creeper fuse/explosion follow-up to keep the saved legacy values pinned"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.CAVENIC_SPIDER.get() != null, "Missing cavenic spider entity type");
        helper.assertTrue(ModRegistries.CAVENIC_SPIDER_SPAWN_EGG.get() != null, "Missing cavenic spider spawn egg");

        assertRegistryId(helper, ModRegistries.CAVENIC_SPIDER.get(), "cavernreborn:cavenic_spider");
        assertRegistryId(helper, ModRegistries.CAVENIC_SPIDER_SPAWN_EGG.get(), "cavernreborn:cavenic_spider_spawn_egg");

        ItemStack spawnEgg = cavenicSpiderSpawnEgg();
        helper.assertTrue(!spawnEgg.isEmpty(), "Expected cavenic spider spawn egg to be constructible");
        helper.assertTrue(spawnEgg.getItem() instanceof SpawnEggItem, "Expected cavenic spider spawn egg runtime item");
        helper.assertTrue(
            ((SpawnEggItem) spawnEgg.getItem()).spawnsEntity(spawnEgg, ModRegistries.CAVENIC_SPIDER.get()),
            "Expected cavenic spider spawn egg to resolve the cavenic spider entity type"
        );
        helper.assertTrue(
            ModRegistries.CAVENIC_SPIDER.get().getCategory() == MobCategory.MONSTER,
            "Expected cavenic spider type category to stay MONSTER"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderSpawnsWithExpectedAttributesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SPIDER_ANCHOR;

        resetMiningArea(level, origin, 8.0D);
        CavenicSpider cavenicSpider = spawnLivingEntity(helper, ModRegistries.CAVENIC_SPIDER.get(), origin);
        Spider vanillaSpider = spawnLivingEntity(helper, EntityType.SPIDER, origin.east(4));

        helper.assertTrue(cavenicSpider instanceof Monster, "Expected cavenic spider to remain a hostile monster");
        helper.assertTrue(cavenicSpider instanceof Spider, "Expected cavenic spider to keep vanilla spider behavior");
        helper.assertTrue(cavenicSpider.getType().getCategory() == MobCategory.MONSTER, "Expected cavenic spider type category to stay MONSTER");
        helper.assertTrue(Math.abs(cavenicSpider.getMaxHealth() - 20.0D) < 1.0E-6D, "Expected cavenic spider max health to map to legacy 20.0");
        helper.assertTrue(Math.abs(cavenicSpider.getAttributeValue(Attributes.MAX_HEALTH) - 20.0D) < 1.0E-6D, "Expected cavenic spider MAX_HEALTH attribute to be 20.0");
        helper.assertTrue(Math.abs(cavenicSpider.getAttributeValue(Attributes.MOVEMENT_SPEED) - 0.6D) < 1.0E-6D, "Expected cavenic spider movement speed to map to legacy 0.6");
        helper.assertTrue(Math.abs(cavenicSpider.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) - 1.0D) < 1.0E-6D, "Expected cavenic spider knockback resistance to map to legacy 1.0");
        helper.assertTrue(
            Math.abs(cavenicSpider.getAttributeValue(Attributes.FOLLOW_RANGE) - vanillaSpider.getAttributeValue(Attributes.FOLLOW_RANGE)) < 1.0E-6D,
            "Expected cavenic spider follow range to stay on the vanilla spider baseline"
        );
        helper.assertTrue(
            Math.abs(cavenicSpider.getAttributeValue(Attributes.ATTACK_DAMAGE) - vanillaSpider.getAttributeValue(Attributes.ATTACK_DAMAGE)) < 1.0E-6D,
            "Expected cavenic spider attack damage to stay on the vanilla spider baseline"
        );
        helper.assertTrue(
            cavenicSpider.getLootTable().equals(EntityType.SPIDER.getDefaultLootTable()),
            "Expected cavenic spider to keep the vanilla spider loot table as its baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderSpawnEggCreatesRuntimeEntity(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos supportPos = CAVENIC_SPIDER_SPAWN_EGG_ANCHOR;

        resetMiningArea(level, supportPos, 8.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        ItemStack spawnEgg = cavenicSpiderSpawnEgg();
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, spawnEgg.copy(), supportPos.south(2));
        Entity spawnedEntity = ModRegistries.CAVENIC_SPIDER.get().spawn(level, spawnEgg, player, supportPos.above(), MobSpawnType.SPAWN_EGG, true, true);

        helper.assertTrue(spawnedEntity instanceof CavenicSpider, "Expected spawn egg to create a CavenicSpider runtime entity");
        helper.assertTrue(spawnedEntity != null && spawnedEntity.isAlive(), "Expected spawned cavenic spider to be alive");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderNaturalSpawnPlacementIsRegisteredAndCavernOnly(GameTestHelper helper) {
        ServerLevel overworldLevel = helper.getLevel().getServer().getLevel(Level.OVERWORLD);
        BlockPos spawnPos = CAVENIC_SPIDER_NATURAL_SPAWN_ANCHOR;
        RandomSource random = RandomSource.create(3456L);

        helper.assertTrue(overworldLevel != null, "Expected overworld level to resolve for spawn predicate smoke");
        helper.assertTrue(
            SpawnPlacements.getPlacementType(ModRegistries.CAVENIC_SPIDER.get()) == SpawnPlacementTypes.ON_GROUND,
            "Expected cavenic spider spawn placement type to mirror spider ground spawning"
        );
        helper.assertTrue(
            SpawnPlacements.getHeightmapType(ModRegistries.CAVENIC_SPIDER.get()) == Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            "Expected cavenic spider spawn heightmap to mirror spider spawning"
        );
        helper.assertTrue(
            CavenicSpider.canNaturallySpawnInDimension(CavernNeoForgeDimensions.CAVERN_LEVEL_KEY),
            "Expected cavenic spider natural-spawn helper to allow the configured CAVERN level key"
        );
        helper.assertFalse(
            CavenicSpider.canNaturallySpawnInDimension(Level.OVERWORLD),
            "Expected cavenic spider natural-spawn helper to reject overworld spawning"
        );
        helper.assertTrue(
            CavenicSpider.NATURAL_SPAWN_WEIGHT == 30
                && CavenicSpider.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicSpider.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic spider natural-spawn constants to stay pinned to legacy 30/1/1 values"
        );

        prepareNaturalSpawnPlatform(overworldLevel, spawnPos);
        helper.assertTrue(
            SpawnPlacements.isSpawnPositionOk(ModRegistries.CAVENIC_SPIDER.get(), overworldLevel, spawnPos),
            "Expected prepared spawn position to satisfy ON_GROUND placement"
        );
        helper.assertFalse(
            SpawnPlacements.checkSpawnRules(ModRegistries.CAVENIC_SPIDER.get(), overworldLevel, MobSpawnType.SPAWNER, spawnPos, random),
            "Expected registered cavenic spider spawn rules to reject overworld spawning"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderNaturalSpawnDataResolvesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<BiomeModifier> biomeModifiers = level.registryAccess().registryOrThrow(NeoForgeRegistries.Keys.BIOME_MODIFIERS);
        Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registries.BIOME);
        TagKey<Biome> spawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "spawns_cavenic_spider"));

        assertRegistryKeyPresent(helper, biomeModifiers, NeoForgeRegistries.Keys.BIOME_MODIFIERS, "cavernreborn:cavenic_spider_spawns");
        helper.assertTrue(biomes.getTag(spawnTag).isPresent(), "Expected cavenic spider spawn biome tag to resolve at runtime");

        Set<String> taggedBiomes = biomes.getTag(spawnTag)
            .orElseThrow()
            .stream()
            .flatMap(holder -> holder.unwrapKey().stream())
            .map(key -> key.location().toString())
            .collect(java.util.stream.Collectors.toSet());

        helper.assertTrue(
            taggedBiomes.equals(Set.of(
                "cavernreborn:stone_depths",
                "cavernreborn:lush_grotto",
                "cavernreborn:dripstone_grotto",
                "cavernreborn:highland_hollows"
            )),
            "Expected cavenic spider natural-spawn tag to stay scoped to the four CAVERN biomes"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderLegacyOrbDropWiresAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SPIDER_LOOT_ANCHOR;
        CavenicSpiderLootEvents lootEvents = new CavenicSpiderLootEvents();
        List<ItemEntity> drops = new ArrayList<>();

        resetMiningArea(level, origin, 8.0D);
        CavenicSpider spider = spawnLivingEntity(helper, ModRegistries.CAVENIC_SPIDER.get(), origin);

        helper.assertTrue(
            spider.getLootTable().equals(EntityType.SPIDER.getDefaultLootTable()),
            "Expected cavenic spider to keep the vanilla spider loot table as its baseline"
        );
        helper.assertTrue(
            CavenicSpiderLootPolicy.ORB_DROP_ROLL_BOUND == 8,
            "Expected cavenic spider orb drop roll bound to stay pinned to the legacy 1/8 chance"
        );
        helper.assertTrue(
            CavenicSpider.NATURAL_SPAWN_WEIGHT == 30
                && CavenicSpider.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicSpider.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic spider natural spawn constants to stay pinned while wiring the orb drop follow-up"
        );
        helper.assertFalse(
            lootEvents.tryAppendLegacyOrbDrop(spider, drops, 1),
            "Non-winning orb roll must not append a cavenic orb drop"
        );
        helper.assertTrue(drops.isEmpty(), "Non-winning orb roll must leave the drop list unchanged");
        helper.assertTrue(
            lootEvents.tryAppendLegacyOrbDrop(spider, drops, 0),
            "Winning orb roll must append a cavenic orb drop"
        );
        helper.assertTrue(drops.size() == 1, "Winning orb roll must append exactly one cavenic orb drop");
        helper.assertTrue(
            drops.getFirst().getItem().is(ModRegistries.CAVENIC_ORB.get()),
            "Winning orb roll must append cavernreborn:cavenic_orb"
        );
        helper.assertTrue(
            Math.abs(drops.getFirst().getY() - (spider.getY() + 0.5D)) < 1.0E-6D,
            "Expected cavenic orb drop Y offset to stay aligned with the legacy 0.5 offset"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderLegacyFallAndFireDamageBehaviorAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SPIDER_DAMAGE_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicSpider cavenicFallSpider = spawnLivingEntity(helper, ModRegistries.CAVENIC_SPIDER.get(), origin);
        Spider vanillaFallSpider = spawnLivingEntity(helper, EntityType.SPIDER, origin.east(4));
        clearEquipment(cavenicFallSpider);
        clearEquipment(vanillaFallSpider);
        float fallDamage = 10.0F;
        float cavenicFallHealthBefore = cavenicFallSpider.getHealth();
        float vanillaFallHealthBefore = vanillaFallSpider.getHealth();

        helper.assertTrue(
            cavenicFallSpider.hurt(level.damageSources().fall(), fallDamage),
            "Expected cavenic spider to still accept fall damage hits through the normal server damage path"
        );
        helper.assertTrue(
            vanillaFallSpider.hurt(level.damageSources().fall(), fallDamage),
            "Expected vanilla spider baseline to accept fall damage hits"
        );
        helper.assertTrue(
            Math.abs((cavenicFallHealthBefore - cavenicFallSpider.getHealth()) - (fallDamage * CavenicSpider.LEGACY_FALL_DAMAGE_MULTIPLIER)) < 1.0E-6F,
            "Expected cavenic spider fall damage to stay pinned to the legacy 0.35 multiplier"
        );
        helper.assertTrue(
            Math.abs((vanillaFallHealthBefore - vanillaFallSpider.getHealth()) - fallDamage) < 1.0E-6F,
            "Expected vanilla spider baseline to keep full fall damage"
        );

        CavenicSpider cavenicFireSpider = spawnLivingEntity(helper, ModRegistries.CAVENIC_SPIDER.get(), origin.south(8));
        Spider vanillaFireSpider = spawnLivingEntity(helper, EntityType.SPIDER, origin.south(8).east(4));
        clearEquipment(cavenicFireSpider);
        clearEquipment(vanillaFireSpider);
        float fireDamage = 4.0F;
        float cavenicFireHealthBefore = cavenicFireSpider.getHealth();
        float vanillaFireHealthBefore = vanillaFireSpider.getHealth();

        helper.assertFalse(
            cavenicFireSpider.hurt(level.damageSources().lava(), fireDamage),
            "Expected cavenic spider to reject fire-tagged damage sources like lava"
        );
        helper.assertTrue(
            vanillaFireSpider.hurt(level.damageSources().lava(), fireDamage),
            "Expected vanilla spider baseline to take lava damage"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - cavenicFireSpider.getHealth()) < 1.0E-6F,
            "Expected cavenic spider health to stay unchanged after fire-tagged damage"
        );
        helper.assertTrue(
            vanillaFireSpider.getHealth() < vanillaFireHealthBefore,
            "Expected vanilla spider baseline to lose health from lava damage"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderLegacyDamageBehaviorKeepsGenericDamageVanillaLikeAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SPIDER_DAMAGE_ANCHOR.north(12);

        resetMiningArea(level, origin, 16.0D);

        CavenicSpider cavenicSpider = spawnLivingEntity(helper, ModRegistries.CAVENIC_SPIDER.get(), origin);
        Spider vanillaSpider = spawnLivingEntity(helper, EntityType.SPIDER, origin.east(4));
        clearEquipment(cavenicSpider);
        clearEquipment(vanillaSpider);
        float genericDamage = 6.0F;
        float cavenicHealthBefore = cavenicSpider.getHealth();
        float vanillaHealthBefore = vanillaSpider.getHealth();

        helper.assertTrue(
            cavenicSpider.hurt(level.damageSources().generic(), genericDamage),
            "Expected cavenic spider to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            vanillaSpider.hurt(level.damageSources().generic(), genericDamage),
            "Expected vanilla spider baseline to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            Math.abs((cavenicHealthBefore - cavenicSpider.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected cavenic spider generic damage intake to stay vanilla-like"
        );
        helper.assertTrue(
            Math.abs((vanillaHealthBefore - vanillaSpider.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected vanilla spider generic damage intake to stay unchanged"
        );
        helper.assertTrue(
            cavenicSpider.getLootTable().equals(EntityType.SPIDER.getDefaultLootTable()),
            "Expected cavenic spider damage follow-up to keep the vanilla spider loot-table baseline"
        );
        helper.assertTrue(
            CavenicSpiderLootPolicy.ORB_DROP_ROLL_BOUND == 8,
            "Expected cavenic spider orb drop roll bound to remain pinned while restoring legacy damage behavior"
        );
        helper.assertTrue(
            CavenicSpider.NATURAL_SPAWN_WEIGHT == 30
                && CavenicSpider.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicSpider.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic spider natural spawn constants to stay unchanged while restoring legacy damage behavior"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderBlindnessOnSuccessfulHitAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SPIDER_BLINDNESS_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicSpider cavenicSpider = spawnLivingEntity(helper, ModRegistries.CAVENIC_SPIDER.get(), origin);
        Zombie cavenicTarget = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.east(2));
        clearEquipment(cavenicSpider);
        clearEquipment(cavenicTarget);
        float cavenicTargetHealthBefore = cavenicTarget.getHealth();

        helper.assertTrue(
            cavenicSpider.doHurtTarget(cavenicTarget),
            "Expected cavenic spider melee attack to succeed before applying the legacy blindness-on-hit effect"
        );
        helper.assertTrue(
            cavenicTarget.getHealth() < cavenicTargetHealthBefore,
            "Expected successful cavenic spider melee hit to still deal vanilla-like melee damage"
        );
        helper.assertTrue(
            cavenicTarget.hasEffect(MobEffects.BLINDNESS),
            "Expected successful cavenic spider melee hit to apply blindness"
        );
        int expectedBlindnessDuration = CavenicSpider.getLegacyBlindnessDurationTicks(level.getDifficulty());
        int actualBlindnessDuration = cavenicTarget.getEffect(MobEffects.BLINDNESS).getDuration();
        helper.assertTrue(
            actualBlindnessDuration <= expectedBlindnessDuration && actualBlindnessDuration >= expectedBlindnessDuration - 1,
            "Expected cavenic spider blindness duration to stay pinned to the legacy difficulty-scaled mapping"
        );
        helper.assertTrue(
            cavenicTarget.getEffect(MobEffects.BLINDNESS).getAmplifier() == CavenicSpider.LEGACY_BLINDNESS_AMPLIFIER,
            "Expected cavenic spider blindness amplifier to stay pinned to the legacy zero-amplifier mapping"
        );
        helper.assertFalse(
            cavenicTarget.hasEffect(MobEffects.POISON),
            "Expected bounded cavenic spider blindness-on-hit follow-up to leave poison behavior disabled"
        );
        helper.assertFalse(
            hasCobwebInArea(level, origin.east(2), 1),
            "Expected bounded cavenic spider blindness-on-hit follow-up to avoid any web-placement behavior"
        );

        Spider vanillaSpider = spawnLivingEntity(helper, EntityType.SPIDER, origin.south(8));
        Zombie vanillaTarget = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.south(8).east(2));
        clearEquipment(vanillaSpider);
        clearEquipment(vanillaTarget);

        helper.assertTrue(
            vanillaSpider.doHurtTarget(vanillaTarget),
            "Expected vanilla spider melee baseline to still succeed under the same deterministic runtime setup"
        );
        helper.assertFalse(
            vanillaTarget.hasEffect(MobEffects.BLINDNESS),
            "Expected vanilla spider melee baseline to keep not applying blindness"
        );
        helper.assertFalse(
            vanillaTarget.hasEffect(MobEffects.POISON),
            "Expected vanilla spider melee baseline to keep not applying poison here"
        );
        helper.assertFalse(
            hasCobwebInArea(level, origin.south(8).east(2), 1),
            "Expected vanilla spider baseline to keep not placing cobweb blocks in this deterministic runtime setup"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSpiderBlindnessFollowUpKeepsExistingSlicesStableAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_SPIDER_BLINDNESS_ANCHOR.north(12);

        resetMiningArea(level, origin, 16.0D);

        CavenicSpider cavenicSpider = spawnLivingEntity(helper, ModRegistries.CAVENIC_SPIDER.get(), origin);
        Zombie target = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.east(2));
        clearEquipment(cavenicSpider);
        clearEquipment(target);

        helper.assertFalse(
            cavenicSpider.tryApplyLegacyBlindnessOnHit(target, false),
            "Expected failed cavenic spider attacks to keep not applying blindness"
        );
        helper.assertFalse(target.hasEffect(MobEffects.BLINDNESS), "Expected failed cavenic spider attacks to leave blindness absent");
        helper.assertFalse(target.hasEffect(MobEffects.POISON), "Expected failed cavenic spider attacks to leave poison absent");
        helper.assertFalse(
            hasCobwebInArea(level, origin.east(2), 1),
            "Expected failed cavenic spider attacks to keep not placing cobweb blocks"
        );
        helper.assertTrue(
            CavenicSpider.getLegacyBlindnessDurationTicks(Difficulty.PEACEFUL) == CavenicSpider.LEGACY_BLINDNESS_DURATION_DEFAULT_TICKS
                && CavenicSpider.getLegacyBlindnessDurationTicks(Difficulty.EASY) == CavenicSpider.LEGACY_BLINDNESS_DURATION_DEFAULT_TICKS
                && CavenicSpider.getLegacyBlindnessDurationTicks(Difficulty.NORMAL) == CavenicSpider.LEGACY_BLINDNESS_DURATION_NORMAL_TICKS
                && CavenicSpider.getLegacyBlindnessDurationTicks(Difficulty.HARD) == CavenicSpider.LEGACY_BLINDNESS_DURATION_HARD_TICKS,
            "Expected cavenic spider blindness durations to stay pinned to the legacy 3s/5s/10s difficulty mapping"
        );
        helper.assertTrue(
            CavenicSpider.LEGACY_BLINDNESS_AMPLIFIER == 0,
            "Expected cavenic spider blindness amplifier to stay pinned to the legacy zero-amplifier mapping"
        );
        helper.assertTrue(
            CavenicSpider.NATURAL_SPAWN_WEIGHT == 30
                && CavenicSpider.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicSpider.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic spider natural spawn constants to stay unchanged while restoring blindness-on-hit"
        );
        helper.assertTrue(
            CavenicSpiderLootPolicy.ORB_DROP_ROLL_BOUND == 8,
            "Expected cavenic spider orb drop roll bound to remain pinned while restoring blindness-on-hit"
        );
        helper.assertTrue(
            Math.abs(CavenicSpider.LEGACY_FALL_DAMAGE_MULTIPLIER - 0.35F) < 1.0E-6F,
            "Expected cavenic spider fall-damage multiplier to remain pinned while restoring blindness-on-hit"
        );
        helper.assertTrue(
            cavenicSpider.getLootTable().equals(EntityType.SPIDER.getDefaultLootTable()),
            "Expected cavenic spider blindness-on-hit follow-up to keep the vanilla spider loot-table baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.CAVENIC_WITCH.get() != null, "Missing cavenic witch entity type");
        helper.assertTrue(ModRegistries.CAVENIC_WITCH_SPAWN_EGG.get() != null, "Missing cavenic witch spawn egg");

        assertRegistryId(helper, ModRegistries.CAVENIC_WITCH.get(), "cavernreborn:cavenic_witch");
        assertRegistryId(helper, ModRegistries.CAVENIC_WITCH_SPAWN_EGG.get(), "cavernreborn:cavenic_witch_spawn_egg");

        ItemStack spawnEgg = cavenicWitchSpawnEgg();
        helper.assertTrue(!spawnEgg.isEmpty(), "Expected cavenic witch spawn egg to be constructible");
        helper.assertTrue(spawnEgg.getItem() instanceof SpawnEggItem, "Expected cavenic witch spawn egg runtime item");
        helper.assertTrue(
            ((SpawnEggItem) spawnEgg.getItem()).spawnsEntity(spawnEgg, ModRegistries.CAVENIC_WITCH.get()),
            "Expected cavenic witch spawn egg to resolve the cavenic witch entity type"
        );
        helper.assertTrue(
            ModRegistries.CAVENIC_WITCH.get().getCategory() == MobCategory.MONSTER,
            "Expected cavenic witch type category to stay MONSTER"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchSpawnsWithExpectedAttributesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_ANCHOR;

        resetMiningArea(level, origin, 8.0D);
        CavenicWitch cavenicWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);
        Witch vanillaWitch = spawnLivingEntity(helper, EntityType.WITCH, origin.east(4));

        helper.assertTrue(cavenicWitch instanceof Monster, "Expected cavenic witch to remain a hostile monster");
        helper.assertTrue(cavenicWitch instanceof Witch, "Expected cavenic witch to keep vanilla witch behavior");
        helper.assertTrue(cavenicWitch.getType().getCategory() == MobCategory.MONSTER, "Expected cavenic witch type category to stay MONSTER");
        helper.assertTrue(Math.abs(cavenicWitch.getMaxHealth() - 50.0D) < 1.0E-6D, "Expected cavenic witch max health to map to legacy 50.0");
        helper.assertTrue(Math.abs(cavenicWitch.getAttributeValue(Attributes.MAX_HEALTH) - 50.0D) < 1.0E-6D, "Expected cavenic witch MAX_HEALTH attribute to be 50.0");
        helper.assertTrue(Math.abs(cavenicWitch.getAttributeValue(Attributes.FOLLOW_RANGE) - 32.0D) < 1.0E-6D, "Expected cavenic witch follow range to map to legacy 32.0");
        helper.assertTrue(Math.abs(cavenicWitch.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) - 1.0D) < 1.0E-6D, "Expected cavenic witch knockback resistance to map to legacy 1.0");
        helper.assertTrue(
            Math.abs(cavenicWitch.getAttributeValue(Attributes.MOVEMENT_SPEED) - vanillaWitch.getAttributeValue(Attributes.MOVEMENT_SPEED)) < 1.0E-6D,
            "Expected cavenic witch movement speed to stay on the vanilla witch baseline"
        );
        helper.assertTrue(
            cavenicWitch.getLootTable().equals(EntityType.WITCH.getDefaultLootTable()),
            "Expected cavenic witch to keep the vanilla witch loot table as its baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchSpawnEggCreatesRuntimeEntity(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos supportPos = CAVENIC_WITCH_SPAWN_EGG_ANCHOR;

        resetMiningArea(level, supportPos, 8.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        ItemStack spawnEgg = cavenicWitchSpawnEgg();
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, spawnEgg.copy(), supportPos.south(2));
        Entity spawnedEntity = ModRegistries.CAVENIC_WITCH.get().spawn(level, spawnEgg, player, supportPos.above(), MobSpawnType.SPAWN_EGG, true, true);

        helper.assertTrue(spawnedEntity instanceof CavenicWitch, "Expected spawn egg to create a CavenicWitch runtime entity");
        helper.assertTrue(spawnedEntity != null && spawnedEntity.isAlive(), "Expected spawned cavenic witch to be alive");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchNaturalSpawnPlacementIsRegisteredAndCavernOnly(GameTestHelper helper) {
        ServerLevel overworldLevel = helper.getLevel().getServer().getLevel(Level.OVERWORLD);
        BlockPos spawnPos = CAVENIC_WITCH_NATURAL_SPAWN_ANCHOR;
        RandomSource random = RandomSource.create(4567L);

        helper.assertTrue(overworldLevel != null, "Expected overworld level to resolve for spawn predicate smoke");
        helper.assertTrue(
            SpawnPlacements.getPlacementType(ModRegistries.CAVENIC_WITCH.get()) == SpawnPlacementTypes.ON_GROUND,
            "Expected cavenic witch spawn placement type to mirror witch ground spawning"
        );
        helper.assertTrue(
            SpawnPlacements.getHeightmapType(ModRegistries.CAVENIC_WITCH.get()) == Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            "Expected cavenic witch spawn heightmap to mirror witch spawning"
        );
        helper.assertTrue(
            CavenicWitch.canNaturallySpawnInDimension(CavernNeoForgeDimensions.CAVERN_LEVEL_KEY),
            "Expected cavenic witch natural-spawn helper to allow the configured CAVERN level key"
        );
        helper.assertFalse(
            CavenicWitch.canNaturallySpawnInDimension(Level.OVERWORLD),
            "Expected cavenic witch natural-spawn helper to reject overworld spawning"
        );
        helper.assertTrue(
            CavenicWitch.NATURAL_SPAWN_WEIGHT == 15
                && CavenicWitch.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicWitch.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic witch natural-spawn constants to stay pinned to legacy 15/1/1 values"
        );

        prepareNaturalSpawnPlatform(overworldLevel, spawnPos);
        helper.assertTrue(
            SpawnPlacements.isSpawnPositionOk(ModRegistries.CAVENIC_WITCH.get(), overworldLevel, spawnPos),
            "Expected prepared spawn position to satisfy ON_GROUND placement"
        );
        helper.assertFalse(
            SpawnPlacements.checkSpawnRules(ModRegistries.CAVENIC_WITCH.get(), overworldLevel, MobSpawnType.SPAWNER, spawnPos, random),
            "Expected registered cavenic witch spawn rules to reject overworld spawning"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchNaturalSpawnDataResolvesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<BiomeModifier> biomeModifiers = level.registryAccess().registryOrThrow(NeoForgeRegistries.Keys.BIOME_MODIFIERS);
        Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registries.BIOME);
        TagKey<Biome> spawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "spawns_cavenic_witch"));

        assertRegistryKeyPresent(helper, biomeModifiers, NeoForgeRegistries.Keys.BIOME_MODIFIERS, "cavernreborn:cavenic_witch_spawns");
        helper.assertTrue(biomes.getTag(spawnTag).isPresent(), "Expected cavenic witch spawn biome tag to resolve at runtime");

        Set<String> taggedBiomes = biomes.getTag(spawnTag)
            .orElseThrow()
            .stream()
            .flatMap(holder -> holder.unwrapKey().stream())
            .map(key -> key.location().toString())
            .collect(java.util.stream.Collectors.toSet());

        helper.assertTrue(
            taggedBiomes.equals(Set.of(
                "cavernreborn:stone_depths",
                "cavernreborn:lush_grotto",
                "cavernreborn:dripstone_grotto",
                "cavernreborn:highland_hollows"
            )),
            "Expected cavenic witch natural-spawn tag to stay scoped to the four CAVERN biomes"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchLegacyOrbDropWiresAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_LOOT_ANCHOR;
        CavenicWitchLootEvents lootEvents = new CavenicWitchLootEvents();
        List<ItemEntity> drops = new ArrayList<>();

        resetMiningArea(level, origin, 8.0D);
        CavenicWitch witch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);

        helper.assertTrue(
            witch.getLootTable().equals(EntityType.WITCH.getDefaultLootTable()),
            "Expected cavenic witch to keep the vanilla witch loot table as its baseline"
        );
        helper.assertTrue(
            CavenicWitchLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic witch orb drop roll bound to stay pinned to the legacy 1/5 chance"
        );
        helper.assertTrue(
            CavenicWitch.NATURAL_SPAWN_WEIGHT == 15
                && CavenicWitch.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicWitch.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic witch natural spawn constants to stay pinned while wiring the orb drop follow-up"
        );
        helper.assertFalse(
            lootEvents.tryAppendLegacyOrbDrop(witch, drops, 1),
            "Non-winning orb roll must not append a cavenic orb drop"
        );
        helper.assertTrue(drops.isEmpty(), "Non-winning orb roll must leave the drop list unchanged");
        helper.assertTrue(
            lootEvents.tryAppendLegacyOrbDrop(witch, drops, 0),
            "Winning orb roll must append a cavenic orb drop"
        );
        helper.assertTrue(drops.size() == 1, "Winning orb roll must append exactly one cavenic orb drop");
        helper.assertTrue(
            drops.getFirst().getItem().is(ModRegistries.CAVENIC_ORB.get()),
            "Winning orb roll must append cavernreborn:cavenic_orb"
        );
        helper.assertTrue(
            Math.abs(drops.getFirst().getY() - (witch.getY() + 0.5D)) < 1.0E-6D,
            "Expected cavenic orb drop Y offset to stay aligned with the legacy 0.5 offset"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchLegacyFallAndFireDamageBehaviorAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_DAMAGE_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicWitch cavenicFallWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);
        Witch vanillaFallWitch = spawnLivingEntity(helper, EntityType.WITCH, origin.east(4));
        clearEquipment(cavenicFallWitch);
        clearEquipment(vanillaFallWitch);
        float fallDamage = 10.0F;
        float cavenicFallHealthBefore = cavenicFallWitch.getHealth();
        float vanillaFallHealthBefore = vanillaFallWitch.getHealth();

        helper.assertTrue(
            cavenicFallWitch.hurt(level.damageSources().fall(), fallDamage),
            "Expected cavenic witch to still accept fall damage hits through the normal server damage path"
        );
        helper.assertTrue(
            vanillaFallWitch.hurt(level.damageSources().fall(), fallDamage),
            "Expected vanilla witch baseline to accept fall damage hits"
        );
        helper.assertTrue(
            Math.abs((cavenicFallHealthBefore - cavenicFallWitch.getHealth()) - (fallDamage * CavenicWitch.LEGACY_FALL_DAMAGE_MULTIPLIER)) < 1.0E-6F,
            "Expected cavenic witch fall damage to stay pinned to the legacy 0.35 multiplier"
        );
        helper.assertTrue(
            Math.abs((vanillaFallHealthBefore - vanillaFallWitch.getHealth()) - fallDamage) < 1.0E-6F,
            "Expected vanilla witch baseline to keep full fall damage"
        );

        CavenicWitch cavenicFireWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.south(8));
        Witch vanillaFireWitch = spawnLivingEntity(helper, EntityType.WITCH, origin.south(8).east(4));
        clearEquipment(cavenicFireWitch);
        clearEquipment(vanillaFireWitch);
        float fireDamage = 4.0F;
        float cavenicFireHealthBefore = cavenicFireWitch.getHealth();
        float vanillaFireHealthBefore = vanillaFireWitch.getHealth();

        helper.assertFalse(
            cavenicFireWitch.hurt(level.damageSources().lava(), fireDamage),
            "Expected cavenic witch to reject fire-tagged damage sources like lava"
        );
        helper.assertTrue(
            vanillaFireWitch.hurt(level.damageSources().lava(), fireDamage),
            "Expected vanilla witch baseline to take lava damage"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - cavenicFireWitch.getHealth()) < 1.0E-6F,
            "Expected cavenic witch health to stay unchanged after fire-tagged damage"
        );
        helper.assertTrue(
            vanillaFireWitch.getHealth() < vanillaFireHealthBefore,
            "Expected vanilla witch baseline to lose health from lava damage"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchLegacyDamageBehaviorKeepsGenericDamageVanillaLikeAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_DAMAGE_ANCHOR.north(12);

        resetMiningArea(level, origin, 16.0D);

        CavenicWitch cavenicWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);
        Witch vanillaWitch = spawnLivingEntity(helper, EntityType.WITCH, origin.east(4));
        clearEquipment(cavenicWitch);
        clearEquipment(vanillaWitch);
        float genericDamage = 6.0F;
        float cavenicHealthBefore = cavenicWitch.getHealth();
        float vanillaHealthBefore = vanillaWitch.getHealth();

        helper.assertTrue(
            cavenicWitch.hurt(level.damageSources().generic(), genericDamage),
            "Expected cavenic witch to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            vanillaWitch.hurt(level.damageSources().generic(), genericDamage),
            "Expected vanilla witch baseline to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            Math.abs((cavenicHealthBefore - cavenicWitch.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected cavenic witch generic damage intake to stay vanilla-like"
        );
        helper.assertTrue(
            Math.abs((vanillaHealthBefore - vanillaWitch.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected vanilla witch generic damage intake to stay unchanged"
        );
        helper.assertTrue(
            cavenicWitch.getLootTable().equals(EntityType.WITCH.getDefaultLootTable()),
            "Expected cavenic witch damage follow-up to keep the vanilla witch loot-table baseline"
        );
        helper.assertTrue(
            CavenicWitchLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic witch orb drop roll bound to remain pinned while restoring legacy damage behavior"
        );
        helper.assertTrue(
            CavenicWitch.NATURAL_SPAWN_WEIGHT == 15
                && CavenicWitch.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicWitch.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic witch natural spawn constants to stay unchanged while restoring legacy damage behavior"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchLegacySameTypeSourceImmunityRejectsConfirmedSourcesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_PROJECTILE_IMMUNITY_ANCHOR;

        resetMiningArea(level, origin, 24.0D);

        CavenicWitch cavenicProjectileVictim = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);
        CavenicWitch cavenicProjectileOwner = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.west(4));
        Witch vanillaProjectileVictim = spawnLivingEntity(helper, EntityType.WITCH, origin.east(4));
        clearEquipment(cavenicProjectileVictim);
        clearEquipment(cavenicProjectileOwner);
        clearEquipment(vanillaProjectileVictim);
        float projectileDamage = 6.0F;
        float cavenicProjectileHealthBefore = cavenicProjectileVictim.getHealth();
        float vanillaProjectileHealthBefore = vanillaProjectileVictim.getHealth();

        helper.assertFalse(
            cavenicProjectileVictim.hurt(witchOwnedPotionDamageSource(level, cavenicProjectileOwner), projectileDamage),
            "Expected cavenic witch to reject same-type witch-owned projectile damage through the legacy source-immunity rule"
        );
        helper.assertTrue(
            vanillaProjectileVictim.hurt(witchOwnedPotionDamageSource(level, cavenicProjectileOwner), projectileDamage),
            "Expected vanilla witch baseline to remain vulnerable to cavenic witch-owned projectile damage"
        );
        helper.assertTrue(
            Math.abs(cavenicProjectileHealthBefore - cavenicProjectileVictim.getHealth()) < 1.0E-6F,
            "Expected cavenic witch health to stay unchanged after same-type witch-owned projectile damage"
        );
        helper.assertTrue(
            vanillaProjectileVictim.getHealth() < vanillaProjectileHealthBefore,
            "Expected vanilla witch baseline to lose health from cavenic witch-owned projectile damage"
        );

        CavenicWitch selfProjectileVictim = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.south(8));
        clearEquipment(selfProjectileVictim);
        float selfProjectileHealthBefore = selfProjectileVictim.getHealth();

        helper.assertFalse(
            selfProjectileVictim.hurt(witchOwnedPotionDamageSource(level, selfProjectileVictim), projectileDamage),
            "Expected cavenic witch to reject self-owned projectile damage through the legacy source-immunity rule"
        );
        helper.assertTrue(
            Math.abs(selfProjectileHealthBefore - selfProjectileVictim.getHealth()) < 1.0E-6F,
            "Expected cavenic witch health to stay unchanged after self-owned projectile damage"
        );

        CavenicWitch cavenicDirectVictim = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.north(8));
        CavenicWitch cavenicDirectAttacker = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.north(8).west(4));
        Witch vanillaDirectVictim = spawnLivingEntity(helper, EntityType.WITCH, origin.north(8).east(4));
        clearEquipment(cavenicDirectVictim);
        clearEquipment(cavenicDirectAttacker);
        clearEquipment(vanillaDirectVictim);
        float directDamage = 4.0F;
        float cavenicDirectHealthBefore = cavenicDirectVictim.getHealth();
        float vanillaDirectHealthBefore = vanillaDirectVictim.getHealth();

        helper.assertFalse(
            cavenicDirectVictim.hurt(level.damageSources().mobAttack(cavenicDirectAttacker), directDamage),
            "Expected cavenic witch to reject direct same-type witch damage because the legacy invulnerability hook also checked same-type direct sources"
        );
        helper.assertTrue(
            vanillaDirectVictim.hurt(level.damageSources().mobAttack(cavenicDirectAttacker), directDamage),
            "Expected vanilla witch baseline to remain vulnerable to direct cavenic witch damage"
        );
        helper.assertTrue(
            Math.abs(cavenicDirectHealthBefore - cavenicDirectVictim.getHealth()) < 1.0E-6F,
            "Expected cavenic witch health to stay unchanged after direct same-type witch damage"
        );
        helper.assertTrue(
            vanillaDirectVictim.getHealth() < vanillaDirectHealthBefore,
            "Expected vanilla witch baseline to lose health from direct cavenic witch damage"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchLegacySameTypeSourceImmunityKeepsOtherDamageVanillaLikeAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_PROJECTILE_IMMUNITY_ANCHOR.south(16);

        resetMiningArea(level, origin, 24.0D);

        CavenicWitch cavenicIndirectVictim = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);
        Witch vanillaIndirectVictim = spawnLivingEntity(helper, EntityType.WITCH, origin.east(4));
        Witch vanillaProjectileOwner = spawnLivingEntity(helper, EntityType.WITCH, origin.west(4));
        clearEquipment(cavenicIndirectVictim);
        clearEquipment(vanillaIndirectVictim);
        clearEquipment(vanillaProjectileOwner);
        float indirectDamage = 6.0F;
        float cavenicIndirectHealthBefore = cavenicIndirectVictim.getHealth();
        float vanillaIndirectHealthBefore = vanillaIndirectVictim.getHealth();

        helper.assertTrue(
            cavenicIndirectVictim.hurt(witchOwnedPotionDamageSource(level, vanillaProjectileOwner), indirectDamage),
            "Expected cavenic witch to remain vulnerable to non-cavenic witch-owned projectile damage"
        );
        helper.assertTrue(
            vanillaIndirectVictim.hurt(witchOwnedPotionDamageSource(level, vanillaProjectileOwner), indirectDamage),
            "Expected vanilla witch baseline to remain vulnerable to vanilla witch-owned projectile damage"
        );
        helper.assertTrue(
            cavenicIndirectVictim.getHealth() < cavenicIndirectHealthBefore,
            "Expected cavenic witch to lose health from non-cavenic witch-owned projectile damage"
        );
        helper.assertTrue(
            Math.abs((cavenicIndirectHealthBefore - cavenicIndirectVictim.getHealth()) - (vanillaIndirectHealthBefore - vanillaIndirectVictim.getHealth())) < 1.0E-4F,
            "Expected cavenic witch to take the same non-cavenic witch-owned projectile damage as vanilla witch when the legacy same-type immunity does not apply"
        );

        CavenicWitch cavenicGenericWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.south(8));
        Witch vanillaGenericWitch = spawnLivingEntity(helper, EntityType.WITCH, origin.south(8).east(4));
        clearEquipment(cavenicGenericWitch);
        clearEquipment(vanillaGenericWitch);
        float genericDamage = 6.0F;
        float cavenicGenericHealthBefore = cavenicGenericWitch.getHealth();
        float vanillaGenericHealthBefore = vanillaGenericWitch.getHealth();

        helper.assertTrue(
            cavenicGenericWitch.hurt(level.damageSources().generic(), genericDamage),
            "Expected cavenic witch generic damage intake to stay enabled after the same-type source-immunity follow-up"
        );
        helper.assertTrue(
            vanillaGenericWitch.hurt(level.damageSources().generic(), genericDamage),
            "Expected vanilla witch generic damage intake to stay enabled"
        );
        helper.assertTrue(
            Math.abs((cavenicGenericHealthBefore - cavenicGenericWitch.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected cavenic witch generic damage intake to stay vanilla-like"
        );
        helper.assertTrue(
            Math.abs((vanillaGenericHealthBefore - vanillaGenericWitch.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected vanilla witch generic damage intake to stay unchanged"
        );

        CavenicWitch cavenicFallWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.north(8));
        clearEquipment(cavenicFallWitch);
        float fallDamage = 10.0F;
        float cavenicFallHealthBefore = cavenicFallWitch.getHealth();

        helper.assertTrue(
            cavenicFallWitch.hurt(level.damageSources().fall(), fallDamage),
            "Expected same-type source-immunity follow-up to keep the legacy fall-damage path active"
        );
        helper.assertTrue(
            Math.abs((cavenicFallHealthBefore - cavenicFallWitch.getHealth()) - (fallDamage * CavenicWitch.LEGACY_FALL_DAMAGE_MULTIPLIER)) < 1.0E-6F,
            "Expected cavenic witch fall damage to stay pinned to the legacy 0.35 multiplier"
        );

        CavenicWitch cavenicFireWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.north(8).east(4));
        clearEquipment(cavenicFireWitch);
        float fireDamage = 4.0F;
        float cavenicFireHealthBefore = cavenicFireWitch.getHealth();

        helper.assertFalse(
            cavenicFireWitch.hurt(level.damageSources().lava(), fireDamage),
            "Expected same-type source-immunity follow-up to keep legacy fire-tagged damage rejection"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - cavenicFireWitch.getHealth()) < 1.0E-6F,
            "Expected cavenic witch health to stay unchanged after fire-tagged damage"
        );

        helper.assertTrue(
            cavenicGenericWitch.getLootTable().equals(EntityType.WITCH.getDefaultLootTable()),
            "Expected cavenic witch same-type source-immunity follow-up to keep the vanilla witch loot-table baseline"
        );
        helper.assertTrue(
            CavenicWitchLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic witch orb drop roll bound to remain pinned while restoring same-type source immunity"
        );
        helper.assertTrue(
            CavenicWitch.NATURAL_SPAWN_WEIGHT == 15
                && CavenicWitch.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicWitch.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic witch natural spawn constants to stay unchanged while restoring same-type source immunity"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchFriendshipTargetingRejectsSameTypeTargetsAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_FRIENDSHIP_TARGETING_ANCHOR;

        resetMiningArea(level, origin, 24.0D);

        CavenicWitch actor = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);
        CavenicWitch friendTarget = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.east(4));
        Zombie zombieTarget = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.west(4));

        helper.assertTrue(actor.getTarget() == null, "Expected fresh cavenic witch actor to start without an attack target");
        helper.assertTrue(actor.isLegacyFriendTarget(friendTarget), "Expected cavenic witch friend helper to treat same-type cavenic witch targets as friends");
        helper.assertFalse(actor.isLegacyFriendTarget(zombieTarget), "Expected cavenic witch friend helper to keep non-witch hostile targets out of the friend set");

        actor.setTarget(friendTarget);
        helper.assertTrue(
            actor.getTarget() == null,
            "Expected cavenic witch friendship targeting to reject same-type targets when no previous target is present"
        );

        actor.setTarget(zombieTarget);
        helper.assertTrue(
            actor.getTarget() == zombieTarget,
            "Expected cavenic witch friendship targeting to keep non-friend living targets"
        );

        actor.setTarget(friendTarget);
        helper.assertTrue(
            actor.getTarget() == zombieTarget,
            "Expected cavenic witch friendship targeting to reject same-type targets without clearing the existing non-friend target"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchFriendshipTargetingKeepsOtherTargetsAndSlicesStableAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_FRIENDSHIP_TARGETING_ANCHOR.south(16);

        resetMiningArea(level, origin, 24.0D);

        CavenicWitch actor = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);
        Witch vanillaWitchTarget = spawnLivingEntity(helper, EntityType.WITCH, origin.east(4));
        Zombie zombieTarget = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.west(4));
        CavenicWitch cavenicProjectileOwner = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.north(8));
        Witch vanillaProjectileOwner = spawnLivingEntity(helper, EntityType.WITCH, origin.north(8).east(4));

        actor.setTarget(vanillaWitchTarget);
        helper.assertTrue(
            actor.getTarget() == vanillaWitchTarget,
            "Expected vanilla witch targets to remain allowed because legacy isFriends only matched EntityCavenicWitch"
        );

        actor.setTarget(zombieTarget);
        helper.assertTrue(actor.getTarget() == zombieTarget, "Expected non-friend zombie targets to remain allowed");

        actor.setTarget(null);
        helper.assertTrue(actor.getTarget() == null, "Expected explicit null target clearing to keep working normally");

        helper.assertTrue(
            actor.isLegacyCavenicWitchSourceImmuneTo(witchOwnedPotionDamageSource(level, cavenicProjectileOwner)),
            "Expected same-type/self source-immunity helper to remain true for cavenic witch-owned projectile sources"
        );
        helper.assertFalse(
            actor.isLegacyCavenicWitchSourceImmuneTo(witchOwnedPotionDamageSource(level, vanillaProjectileOwner)),
            "Expected same-type/self source-immunity helper to remain false for vanilla witch-owned projectile sources"
        );

        CavenicWitch cavenicFallWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.south(8));
        clearEquipment(cavenicFallWitch);
        float fallDamage = 10.0F;
        float cavenicFallHealthBefore = cavenicFallWitch.getHealth();

        helper.assertTrue(
            cavenicFallWitch.hurt(level.damageSources().fall(), fallDamage),
            "Expected friendship-targeting follow-up to keep the legacy fall-damage path active"
        );
        helper.assertTrue(
            Math.abs((cavenicFallHealthBefore - cavenicFallWitch.getHealth()) - (fallDamage * CavenicWitch.LEGACY_FALL_DAMAGE_MULTIPLIER)) < 1.0E-6F,
            "Expected cavenic witch friendship-targeting follow-up to keep the legacy 0.35 fall-damage multiplier"
        );

        CavenicWitch cavenicFireWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.south(8).east(4));
        clearEquipment(cavenicFireWitch);
        float fireDamage = 4.0F;
        float cavenicFireHealthBefore = cavenicFireWitch.getHealth();

        helper.assertFalse(
            cavenicFireWitch.hurt(level.damageSources().lava(), fireDamage),
            "Expected friendship-targeting follow-up to keep fire-tagged damage rejection"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - cavenicFireWitch.getHealth()) < 1.0E-6F,
            "Expected cavenic witch health to stay unchanged after fire-tagged damage"
        );

        helper.assertTrue(
            actor.getLootTable().equals(EntityType.WITCH.getDefaultLootTable()),
            "Expected cavenic witch friendship-targeting follow-up to keep the vanilla witch loot-table baseline"
        );
        helper.assertTrue(
            CavenicWitchLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic witch orb drop roll bound to remain pinned while restoring friendship targeting"
        );
        helper.assertTrue(
            CavenicWitch.NATURAL_SPAWN_WEIGHT == 15
                && CavenicWitch.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicWitch.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic witch natural spawn constants to stay unchanged while restoring friendship targeting"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchLegacyRangedPotionBehaviorAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_RANGED_POTION_ANCHOR;
        CavenicWitch cavenicWitch;
        Zombie cavenicTarget;
        List<ThrownPotion> legacyPotions;
        int expectedPotionCount;
        long seed = 12345L;
        RandomSource expectedRandom = RandomSource.create(seed);
        List<Holder<Potion>> expectedPotions = new ArrayList<>();

        resetMiningArea(level, origin, 24.0D);

        cavenicWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);
        cavenicTarget = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.east(16));
        expectedPotionCount = CavenicWitch.getLegacyAttackPotionCount(level.getDifficulty());

        helper.assertTrue(expectedPotionCount > 0, "Expected GameTest difficulty to allow at least one legacy cavenic witch ranged-potion throw");

        for (int i = 0; i < expectedPotionCount; i++) {
            expectedPotions.add(CavenicWitch.selectLegacyRangedPotionFor(cavenicTarget, expectedRandom));
        }

        cavenicWitch.setSilent(true);
        cavenicWitch.setUsingItem(false);

        cavenicWitch.getRandom().setSeed(seed);
        legacyPotions = cavenicWitch.createLegacyThrownPotionsFor(cavenicTarget);

        helper.assertTrue(
            legacyPotions.size() == expectedPotionCount,
            "Expected cavenic witch helper construction to preserve the legacy difficulty-scaled potion count"
        );

        for (int i = 0; i < legacyPotions.size(); i++) {
            ThrownPotion potion = legacyPotions.get(i);
            Holder<Potion> expectedPotion = expectedPotions.get(i);

            helper.assertTrue(potion.getOwner() == cavenicWitch, "Expected cavenic witch ranged potions to preserve the owner/source");
            helper.assertTrue(potion.getItem().is(Items.SPLASH_POTION), "Expected cavenic witch ranged attack to throw splash-potion projectiles");
            helper.assertTrue(
                potionContents(potion).is(expectedPotion),
                "Expected cavenic witch ranged attack to use the legacy seeded potion-selection result for each throw"
            );
            helper.assertTrue(
                potion.getDeltaMovement().length() > 0.0D,
                "Expected constructed legacy ranged potions to keep a non-zero throw trajectory"
            );
        }

        cavenicWitch.setTarget(cavenicTarget);
        cavenicWitch.getRandom().setSeed(seed);
        cavenicWitch.performRangedAttack(cavenicTarget, 1.0F);
        helper.assertTrue(
            cavenicWitch.getTarget() == cavenicTarget,
            "Expected direct legacy ranged-attack calls to keep the current non-friend combat target"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchLegacyRangedPotionSelectionThresholdsResolveAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_RANGED_POTION_SELECTION_ANCHOR;
        Cow highHealthNonUndeadTarget;
        Cow lowHealthNonUndeadTarget;
        Zombie undeadTarget;

        resetMiningArea(level, origin, 16.0D);

        highHealthNonUndeadTarget = spawnLivingEntity(helper, EntityType.COW, origin.east(4));
        lowHealthNonUndeadTarget = spawnLivingEntity(helper, EntityType.COW, origin.east(8));
        undeadTarget = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.west(4));
        highHealthNonUndeadTarget.setHealth(8.0F);
        lowHealthNonUndeadTarget.setHealth(7.0F);

        helper.assertTrue(
            CavenicWitch.selectLegacyRangedPotionFor(highHealthNonUndeadTarget, queuedFloatRandomSource(0.49F)).is(Potions.POISON),
            "Expected high-health non-undead targets to use the legacy 0.5 first-branch threshold"
        );
        helper.assertTrue(
            CavenicWitch.selectLegacyRangedPotionFor(highHealthNonUndeadTarget, queuedFloatRandomSource(0.5F, 0.9F, 0.9F)).is(Potions.HARMING),
            "Expected the legacy 0.5 first-branch threshold to reject equal-boundary rolls for high-health targets"
        );
        helper.assertTrue(
            CavenicWitch.selectLegacyRangedPotionFor(lowHealthNonUndeadTarget, queuedFloatRandomSource(0.29F)).is(Potions.POISON),
            "Expected low-health non-undead targets to use the legacy 0.3 first-branch threshold"
        );
        helper.assertTrue(
            CavenicWitch.selectLegacyRangedPotionFor(lowHealthNonUndeadTarget, queuedFloatRandomSource(0.3F, 0.9F, 0.9F)).is(Potions.HARMING),
            "Expected the legacy 0.3 first-branch threshold to reject equal-boundary rolls for low-health targets"
        );
        helper.assertTrue(
            CavenicWitch.selectLegacyRangedPotionFor(undeadTarget, queuedFloatRandomSource(0.49F)).is(Potions.HEALING),
            "Expected winning first-branch rolls to map undead targets to HEALING"
        );
        helper.assertTrue(
            CavenicWitch.selectLegacyRangedPotionFor(highHealthNonUndeadTarget, queuedFloatRandomSource(0.9F, 0.24F)).is(Potions.WEAKNESS),
            "Expected first-branch failure plus weakness-roll success to map to WEAKNESS"
        );
        helper.assertTrue(
            CavenicWitch.selectLegacyRangedPotionFor(highHealthNonUndeadTarget, queuedFloatRandomSource(0.9F, 0.25F, 0.19F)).is(Potions.SLOWNESS),
            "Expected first-branch and weakness failure plus slowness-roll success to map to SLOWNESS"
        );
        helper.assertTrue(
            CavenicWitch.selectLegacyRangedPotionFor(highHealthNonUndeadTarget, queuedFloatRandomSource(0.9F, 0.9F, 0.9F)).is(Potions.HARMING),
            "Expected all failed branch rolls to leave the default HARMING potion selected"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicWitchLegacyRangedPotionKeepsExistingSlicesStableAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_WITCH_RANGED_POTION_ANCHOR.south(16);
        CavenicWitch actor;
        CavenicWitch friendTarget;
        Zombie zombieTarget;
        Witch vanillaWitchTarget;
        LivingEntity pillagerTarget;
        CavenicWitch cavenicProjectileOwner;
        Witch vanillaProjectileOwner;
        List<ThrownPotion> combatPotions;

        resetMiningArea(level, origin, 32.0D);

        actor = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin);
        friendTarget = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.east(4));
        zombieTarget = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.west(4));
        vanillaWitchTarget = spawnLivingEntity(helper, EntityType.WITCH, origin.west(8));
        pillagerTarget = spawnLivingEntity(helper, EntityType.PILLAGER, origin.east(16));
        cavenicProjectileOwner = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.north(8));
        vanillaProjectileOwner = spawnLivingEntity(helper, EntityType.WITCH, origin.north(8).east(4));
        actor.setSilent(true);
        actor.setUsingItem(false);

        helper.assertTrue(
            CavenicWitch.getLegacyAttackPotionCount(Difficulty.PEACEFUL) == 0
                && CavenicWitch.getLegacyAttackPotionCount(Difficulty.EASY) == 1
                && CavenicWitch.getLegacyAttackPotionCount(Difficulty.NORMAL) == 2
                && CavenicWitch.getLegacyAttackPotionCount(Difficulty.HARD) == 3,
            "Expected cavenic witch legacy ranged-potion count to stay pinned to the old difficulty-id mapping"
        );

        actor.setTarget(pillagerTarget);
        actor.performRangedAttack(pillagerTarget, 1.0F);
        helper.assertTrue(
            actor.getTarget() == null,
            "Expected non-witch Raider targets to keep the vanilla ranged-attack bridge that clears the active target"
        );

        actor.setTarget(zombieTarget);
        combatPotions = actor.createLegacyThrownPotionsFor(zombieTarget);
        helper.assertTrue(
            combatPotions.size() == CavenicWitch.getLegacyAttackPotionCount(level.getDifficulty()),
            "Expected normal combat targets to keep the legacy difficulty-scaled ranged-potion count"
        );
        helper.assertTrue(
            combatPotions.stream().allMatch(potion -> potion.getOwner() == actor),
            "Expected helper-constructed legacy ranged potions to keep the cavenic witch owner/source"
        );
        actor.performRangedAttack(zombieTarget, 1.0F);
        helper.assertTrue(
            actor.getTarget() == zombieTarget,
            "Expected direct legacy ranged-attack calls against non-friend combat targets to keep the current target"
        );

        actor.setTarget(friendTarget);
        helper.assertTrue(
            actor.getTarget() == zombieTarget,
            "Expected same-type friendship-target rejection to remain unchanged after the ranged-potion follow-up"
        );
        actor.setTarget(vanillaWitchTarget);
        helper.assertTrue(
            actor.getTarget() == vanillaWitchTarget,
            "Expected vanilla witch targets to remain allowed because friendship targeting still only rejects CavenicWitch"
        );
        actor.setTarget(null);
        helper.assertTrue(actor.getTarget() == null, "Expected explicit null target clearing to remain vanilla-like");

        helper.assertTrue(
            actor.isLegacyCavenicWitchSourceImmuneTo(witchOwnedPotionDamageSource(level, cavenicProjectileOwner)),
            "Expected same-type/self source immunity to remain unchanged after restoring legacy ranged-potion behavior"
        );
        helper.assertFalse(
            actor.isLegacyCavenicWitchSourceImmuneTo(witchOwnedPotionDamageSource(level, vanillaProjectileOwner)),
            "Expected non-cavenic witch-owned projectile sources to remain non-immune"
        );

        CavenicWitch cavenicFallWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.south(8));
        float fallDamage = 10.0F;
        float cavenicFallHealthBefore = cavenicFallWitch.getHealth();

        helper.assertTrue(
            cavenicFallWitch.hurt(level.damageSources().fall(), fallDamage),
            "Expected ranged-potion follow-up to keep the legacy fall-damage path active"
        );
        helper.assertTrue(
            Math.abs((cavenicFallHealthBefore - cavenicFallWitch.getHealth()) - (fallDamage * CavenicWitch.LEGACY_FALL_DAMAGE_MULTIPLIER)) < 1.0E-6F,
            "Expected cavenic witch fall damage to stay pinned to the legacy 0.35 multiplier"
        );

        CavenicWitch cavenicFireWitch = spawnLivingEntity(helper, ModRegistries.CAVENIC_WITCH.get(), origin.south(8).east(4));
        float fireDamage = 4.0F;
        float cavenicFireHealthBefore = cavenicFireWitch.getHealth();

        helper.assertFalse(
            cavenicFireWitch.hurt(level.damageSources().lava(), fireDamage),
            "Expected ranged-potion follow-up to keep legacy fire-tagged damage rejection"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - cavenicFireWitch.getHealth()) < 1.0E-6F,
            "Expected cavenic witch health to stay unchanged after fire-tagged damage"
        );

        helper.assertTrue(
            actor.getLootTable().equals(EntityType.WITCH.getDefaultLootTable()),
            "Expected cavenic witch ranged-potion follow-up to keep the vanilla witch loot-table baseline"
        );
        helper.assertTrue(
            CavenicWitchLootPolicy.ORB_DROP_ROLL_BOUND == 5,
            "Expected cavenic witch orb drop roll bound to remain pinned while restoring legacy ranged-potion behavior"
        );
        helper.assertTrue(
            CavenicWitch.NATURAL_SPAWN_WEIGHT == 15
                && CavenicWitch.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicWitch.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic witch natural spawn constants to stay unchanged while restoring legacy ranged-potion behavior"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.CAVENIC_BEAR.get() != null, "Missing cavenic bear entity type");
        helper.assertTrue(ModRegistries.CAVENIC_BEAR_SPAWN_EGG.get() != null, "Missing cavenic bear spawn egg");

        assertRegistryId(helper, ModRegistries.CAVENIC_BEAR.get(), "cavernreborn:cavenic_bear");
        assertRegistryId(helper, ModRegistries.CAVENIC_BEAR_SPAWN_EGG.get(), "cavernreborn:cavenic_bear_spawn_egg");

        ItemStack spawnEgg = cavenicBearSpawnEgg();
        helper.assertTrue(!spawnEgg.isEmpty(), "Expected cavenic bear spawn egg to be constructible");
        helper.assertTrue(spawnEgg.getItem() instanceof SpawnEggItem, "Expected cavenic bear spawn egg runtime item");
        helper.assertTrue(
            ((SpawnEggItem) spawnEgg.getItem()).spawnsEntity(spawnEgg, ModRegistries.CAVENIC_BEAR.get()),
            "Expected cavenic bear spawn egg to resolve the cavenic bear entity type"
        );
        helper.assertTrue(
            ModRegistries.CAVENIC_BEAR.get().getCategory() == MobCategory.MONSTER,
            "Expected cavenic bear type category to stay MONSTER"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearSpawnsWithExpectedAttributesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BEAR_ANCHOR;

        resetMiningArea(level, origin, 8.0D);
        CavenicBear cavenicBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin);
        PolarBear vanillaPolarBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.east(4));

        helper.assertTrue(cavenicBear instanceof PolarBear, "Expected cavenic bear to keep vanilla polar bear behavior");
        helper.assertTrue(cavenicBear.getType().getCategory() == MobCategory.MONSTER, "Expected cavenic bear type category to stay MONSTER");
        helper.assertTrue(cavenicBear.isAlive(), "Expected spawned cavenic bear to be alive");
        helper.assertTrue(Math.abs(cavenicBear.getMaxHealth() - 60.0D) < 1.0E-6D, "Expected cavenic bear max health to map to legacy 60.0");
        helper.assertTrue(Math.abs(cavenicBear.getAttributeValue(Attributes.MAX_HEALTH) - 60.0D) < 1.0E-6D, "Expected cavenic bear MAX_HEALTH attribute to be 60.0");
        helper.assertTrue(Math.abs(cavenicBear.getAttributeValue(Attributes.MOVEMENT_SPEED) - 0.3D) < 1.0E-6D, "Expected cavenic bear movement speed to map to legacy 0.3");
        helper.assertTrue(Math.abs(cavenicBear.getAttributeValue(Attributes.ATTACK_DAMAGE) - 7.0D) < 1.0E-6D, "Expected cavenic bear attack damage to map to legacy 7.0");
        helper.assertTrue(
            Math.abs(cavenicBear.getAttributeValue(Attributes.FOLLOW_RANGE) - vanillaPolarBear.getAttributeValue(Attributes.FOLLOW_RANGE)) < 1.0E-6D,
            "Expected cavenic bear follow range to stay on the vanilla polar bear baseline"
        );
        helper.assertTrue(
            cavenicBear.getLootTable().equals(EntityType.POLAR_BEAR.getDefaultLootTable()),
            "Expected cavenic bear to keep the vanilla polar bear loot table as its baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearSpawnEggCreatesRuntimeEntity(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos supportPos = CAVENIC_BEAR_SPAWN_EGG_ANCHOR;

        resetMiningArea(level, supportPos, 8.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        ItemStack spawnEgg = cavenicBearSpawnEgg();
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, spawnEgg.copy(), supportPos.south(2));
        Entity spawnedEntity = ModRegistries.CAVENIC_BEAR.get().spawn(level, spawnEgg, player, supportPos.above(), MobSpawnType.SPAWN_EGG, true, true);

        helper.assertTrue(spawnedEntity instanceof CavenicBear, "Expected spawn egg to create a CavenicBear runtime entity");
        helper.assertTrue(spawnedEntity != null && spawnedEntity.isAlive(), "Expected spawned cavenic bear to be alive");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearNaturalSpawnPlacementIsRegisteredAndCavernOnly(GameTestHelper helper) {
        ServerLevel overworldLevel = helper.getLevel().getServer().getLevel(Level.OVERWORLD);
        BlockPos spawnPos = CAVENIC_BEAR_NATURAL_SPAWN_ANCHOR;
        RandomSource random = RandomSource.create(5678L);

        helper.assertTrue(overworldLevel != null, "Expected overworld level to resolve for bear spawn predicate smoke");
        helper.assertTrue(
            SpawnPlacements.getPlacementType(ModRegistries.CAVENIC_BEAR.get()) == SpawnPlacementTypes.ON_GROUND,
            "Expected cavenic bear spawn placement type to use ON_GROUND placement"
        );
        helper.assertTrue(
            SpawnPlacements.getHeightmapType(ModRegistries.CAVENIC_BEAR.get()) == Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            "Expected cavenic bear spawn heightmap to use MOTION_BLOCKING_NO_LEAVES"
        );
        helper.assertTrue(
            CavenicBear.canNaturallySpawnInDimension(CavernNeoForgeDimensions.CAVERN_LEVEL_KEY),
            "Expected cavenic bear natural-spawn helper to allow the configured CAVERN level key"
        );
        helper.assertFalse(
            CavenicBear.canNaturallySpawnInDimension(Level.OVERWORLD),
            "Expected cavenic bear natural-spawn helper to reject overworld spawning"
        );
        helper.assertTrue(
            CavenicBear.NATURAL_SPAWN_WEIGHT == 30
                && CavenicBear.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicBear.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic bear natural-spawn constants to stay pinned to legacy 30/1/1 values"
        );

        CavenicBear spawnedBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), spawnPos.east(4));
        helper.assertTrue(
            spawnedBear.getMaxSpawnClusterSize() == 1,
            "Expected cavenic bear max spawn cluster size to stay pinned to the legacy per-chunk limit of 1"
        );

        prepareNaturalSpawnPlatform(overworldLevel, spawnPos);
        helper.assertTrue(
            SpawnPlacements.isSpawnPositionOk(ModRegistries.CAVENIC_BEAR.get(), overworldLevel, spawnPos),
            "Expected prepared bear spawn position to satisfy ON_GROUND placement"
        );
        helper.assertFalse(
            SpawnPlacements.checkSpawnRules(ModRegistries.CAVENIC_BEAR.get(), overworldLevel, MobSpawnType.SPAWNER, spawnPos, random),
            "Expected registered cavenic bear spawn rules to reject overworld spawning"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearNaturalSpawnDataResolvesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<BiomeModifier> biomeModifiers = level.registryAccess().registryOrThrow(NeoForgeRegistries.Keys.BIOME_MODIFIERS);
        Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registries.BIOME);
        TagKey<Biome> spawnTag = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "spawns_cavenic_bear"));

        assertRegistryKeyPresent(helper, biomeModifiers, NeoForgeRegistries.Keys.BIOME_MODIFIERS, "cavernreborn:cavenic_bear_spawns");
        helper.assertTrue(biomes.getTag(spawnTag).isPresent(), "Expected cavenic bear spawn biome tag to resolve at runtime");

        Set<String> taggedBiomes = biomes.getTag(spawnTag)
            .orElseThrow()
            .stream()
            .flatMap(holder -> holder.unwrapKey().stream())
            .map(key -> key.location().toString())
            .collect(java.util.stream.Collectors.toSet());

        helper.assertTrue(
            taggedBiomes.equals(Set.of(
                "cavernreborn:stone_depths",
                "cavernreborn:lush_grotto",
                "cavernreborn:dripstone_grotto",
                "cavernreborn:highland_hollows"
            )),
            "Expected cavenic bear natural-spawn tag to stay scoped to the four CAVERN biomes"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearLegacyHostileTargetingAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BEAR_HOSTILE_TARGETING_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicBear cavenicBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin);
        PolarBear vanillaBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.east(8));
        Player playerTarget = makeMockPlayer(helper, level, GameType.SURVIVAL, ItemStack.EMPTY, origin.south(4));
        Cow cowTarget = spawnLivingEntity(helper, EntityType.COW, origin.west(4));
        Fox foxTarget = spawnLivingEntity(helper, EntityType.FOX, origin.west(8));

        helper.assertTrue(cavenicBear.isLegacyHostileTarget(playerTarget), "Expected legacy hostile-target helper to accept players");
        helper.assertFalse(cavenicBear.isLegacyHostileTarget(cowTarget), "Expected legacy hostile-target helper to reject passive non-player targets");
        helper.assertFalse(cavenicBear.isLegacyHostileTarget(foxTarget), "Expected legacy hostile-target helper to reject fox targets");

        helper.assertTrue(
            countGoals(cavenicBear, HurtByTargetGoal.class) == 1L,
            "Expected cavenic bear to keep exactly one hurt-by-target goal in the bounded hostile-targeting slice"
        );
        helper.assertTrue(
            countGoals(cavenicBear, NearestAttackableTargetGoal.class) == 1L,
            "Expected cavenic bear to keep exactly one nearest-attackable target goal after removing the vanilla fox and anger branches"
        );
        helper.assertTrue(
            countGoals(cavenicBear, ResetUniversalAngerTargetGoal.class) == 0L,
            "Expected cavenic bear to remove the vanilla reset-universal-anger target goal in the legacy hostile-targeting slice"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBear, "LegacyCavenicBearHurtByTargetGoal").isPresent(),
            "Expected cavenic bear target selector to register the legacy hurt-by-target goal"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBear, "LegacyNearestAttackablePlayerTargetGoal").isPresent(),
            "Expected cavenic bear target selector to register the legacy nearest-player target goal"
        );
        helper.assertTrue(
            findGoalBySimpleName(vanillaBear, "PolarBearAttackPlayersGoal").isPresent(),
            "Expected vanilla polar bear to keep its vanilla baby-protection player target goal"
        );
        helper.assertTrue(
            countGoals(vanillaBear, ResetUniversalAngerTargetGoal.class) == 1L,
            "Expected vanilla polar bear to keep its reset-universal-anger target goal"
        );
        helper.assertTrue(
            countGoals(vanillaBear, NearestAttackableTargetGoal.class) > 1L,
            "Expected vanilla polar bear to keep more than one nearest-attackable target goal"
        );

        WrappedGoal playerGoal = findGoalBySimpleName(cavenicBear, "LegacyNearestAttackablePlayerTargetGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy player target goal"));
        NearestAttackableTargetGoal<?> nearestPlayerTargetGoal = (NearestAttackableTargetGoal<?>)playerGoal.getGoal();
        nearestPlayerTargetGoal.setTarget(playerTarget);
        playerGoal.start();

        helper.assertTrue(
            cavenicBear.getTarget() == playerTarget,
            "Expected the legacy nearest-player target goal to keep an accepted player target when started directly"
        );
        helper.assertTrue(
            cavenicBear.getLootTable().equals(EntityType.POLAR_BEAR.getDefaultLootTable()),
            "Expected cavenic bear hostile-targeting follow-up to keep the vanilla polar bear loot-table baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearLegacyHostileTargetingKeepsExistingSlicesStableAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BEAR_HOSTILE_TARGETING_ANCHOR.north(24);

        resetMiningArea(level, origin, 48.0D);

        CavenicBear adultCavenicBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin);
        CavenicBear adultCavenicAlly = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin.east(4));
        Zombie adultAttacker = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.south(4));
        WrappedGoal adultCavenicHurtGoal = findGoalBySimpleName(adultCavenicBear, "LegacyCavenicBearHurtByTargetGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear hurt goal"));
        PolarBear adultVanillaBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.west(28));
        PolarBear adultVanillaAlly = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.west(24));
        Zombie adultVanillaAttacker = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.west(28).south(4));
        WrappedGoal adultVanillaHurtGoal = findGoalBySimpleName(adultVanillaBear, "PolarBearHurtByTargetGoal")
            .orElseThrow(() -> new IllegalStateException("Missing vanilla polar bear hurt goal"));
        CavenicBear babyCavenicBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin.north(24));
        babyCavenicBear.setBaby(true);
        CavenicBear babyCavenicEscort = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin.north(24).east(4));
        Zombie babyAttacker = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.north(24).south(4));
        WrappedGoal babyCavenicHurtGoal = findGoalBySimpleName(babyCavenicBear, "LegacyCavenicBearHurtByTargetGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear hurt goal"));
        PolarBear babyVanillaBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.north(24).west(28));
        babyVanillaBear.setBaby(true);
        PolarBear babyVanillaEscort = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.north(24).west(24));
        Zombie babyVanillaAttacker = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.north(24).west(28).south(4));
        WrappedGoal babyVanillaHurtGoal = findGoalBySimpleName(babyVanillaBear, "PolarBearHurtByTargetGoal")
            .orElseThrow(() -> new IllegalStateException("Missing vanilla polar bear hurt goal"));

        adultCavenicBear.setLastHurtByMob(adultAttacker);
        adultCavenicHurtGoal.start();

        helper.assertTrue(adultCavenicBear.getTarget() == adultAttacker, "Expected adult cavenic bear to retaliate against its attacker");
        invokeGoalMethod(adultCavenicHurtGoal.getGoal(), "alertOther", adultCavenicAlly, adultAttacker);
        helper.assertTrue(adultCavenicAlly.getTarget() == adultAttacker, "Expected adult cavenic allies to inherit the hurt-by-target attacker");

        adultVanillaBear.setLastHurtByMob(adultVanillaAttacker);
        adultVanillaHurtGoal.start();

        helper.assertTrue(adultVanillaBear.getTarget() == adultVanillaAttacker, "Expected vanilla adult polar bear to retaliate against its attacker");
        helper.assertTrue(adultVanillaAlly.getTarget() == null, "Expected vanilla adult polar bear allies to stay idle without the legacy shared-retaliation change");

        babyCavenicBear.setLastHurtByMob(babyAttacker);
        babyCavenicHurtGoal.start();

        helper.assertTrue(
            babyCavenicBear.getTarget() == babyAttacker,
            "Expected baby cavenic bear to keep retaliating after alerting adults, matching the legacy hurt-by-target behavior"
        );
        invokeGoalMethod(babyCavenicHurtGoal.getGoal(), "alertOther", babyCavenicEscort, babyAttacker);
        helper.assertTrue(
            babyCavenicEscort.getTarget() == babyAttacker,
            "Expected nearby adult cavenic bears to inherit a baby cavenic bear hurt target"
        );

        babyVanillaBear.setLastHurtByMob(babyVanillaAttacker);
        babyVanillaHurtGoal.start();

        helper.assertTrue(
            babyVanillaBear.getTarget() == null,
            "Expected vanilla baby polar bear hurt behavior to stop after alerting adults instead of keeping its own target"
        );
        invokeGoalMethod(babyVanillaHurtGoal.getGoal(), "alertOther", babyVanillaEscort, babyVanillaAttacker);
        helper.assertTrue(
            babyVanillaEscort.getTarget() == babyVanillaAttacker,
            "Expected vanilla baby polar bear hurt behavior to alert nearby adults"
        );

        float cavenicFireHealthBefore = adultCavenicBear.getHealth();
        helper.assertFalse(
            adultCavenicBear.hurt(level.damageSources().lava(), 4.0F),
            "Expected hostile-targeting follow-up to keep legacy fire-tagged damage rejection"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - adultCavenicBear.getHealth()) < 1.0E-6F,
            "Expected hostile-targeting follow-up to keep cavenic bear health unchanged after lava damage"
        );
        helper.assertTrue(
            CavenicBear.LEGACY_FALL_DAMAGE_MULTIPLIER == 0.35F,
            "Expected hostile-targeting follow-up to keep the legacy 0.35 fall-damage multiplier"
        );
        helper.assertTrue(
            CavenicBear.NATURAL_SPAWN_WEIGHT == 30
                && CavenicBear.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicBear.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected hostile-targeting follow-up to keep the legacy 30/1/1 natural-spawn constants"
        );
        helper.assertTrue(
            adultCavenicBear.getMaxSpawnClusterSize() == 1,
            "Expected hostile-targeting follow-up to keep the legacy max spawn cluster size of 1"
        );
        helper.assertTrue(
            adultCavenicBear.getLootTable().equals(EntityType.POLAR_BEAR.getDefaultLootTable()),
            "Expected hostile-targeting follow-up to keep the vanilla polar bear loot-table baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearLegacyFallAndFireDamageBehaviorAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BEAR_DAMAGE_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicBear cavenicFallBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin);
        PolarBear vanillaFallBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.east(4));
        clearEquipment(cavenicFallBear);
        clearEquipment(vanillaFallBear);
        float fallDamage = 10.0F;
        float cavenicFallHealthBefore = cavenicFallBear.getHealth();
        float vanillaFallHealthBefore = vanillaFallBear.getHealth();

        helper.assertTrue(
            cavenicFallBear.hurt(level.damageSources().fall(), fallDamage),
            "Expected cavenic bear to still accept fall damage hits through the normal server damage path"
        );
        helper.assertTrue(
            vanillaFallBear.hurt(level.damageSources().fall(), fallDamage),
            "Expected vanilla polar bear baseline to accept fall damage hits"
        );
        helper.assertTrue(
            Math.abs((cavenicFallHealthBefore - cavenicFallBear.getHealth()) - (fallDamage * CavenicBear.LEGACY_FALL_DAMAGE_MULTIPLIER)) < 1.0E-6F,
            "Expected cavenic bear fall damage to stay pinned to the legacy 0.35 multiplier"
        );
        helper.assertTrue(
            Math.abs((vanillaFallHealthBefore - vanillaFallBear.getHealth()) - fallDamage) < 1.0E-6F,
            "Expected vanilla polar bear baseline to keep full fall damage"
        );

        CavenicBear cavenicFireBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin.south(8));
        PolarBear vanillaFireBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.south(8).east(4));
        clearEquipment(cavenicFireBear);
        clearEquipment(vanillaFireBear);
        float fireDamage = 4.0F;
        float cavenicFireHealthBefore = cavenicFireBear.getHealth();
        float vanillaFireHealthBefore = vanillaFireBear.getHealth();

        helper.assertFalse(
            cavenicFireBear.hurt(level.damageSources().lava(), fireDamage),
            "Expected cavenic bear to reject fire-tagged damage sources like lava"
        );
        helper.assertTrue(
            vanillaFireBear.hurt(level.damageSources().lava(), fireDamage),
            "Expected vanilla polar bear baseline to take lava damage"
        );
        helper.assertTrue(
            Math.abs(cavenicFireHealthBefore - cavenicFireBear.getHealth()) < 1.0E-6F,
            "Expected cavenic bear health to stay unchanged after fire-tagged damage"
        );
        helper.assertTrue(
            vanillaFireBear.getHealth() < vanillaFireHealthBefore,
            "Expected vanilla polar bear baseline to lose health from lava damage"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearLegacyDamageBehaviorKeepsGenericDamageVanillaLikeAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BEAR_DAMAGE_ANCHOR.north(12);

        resetMiningArea(level, origin, 16.0D);

        CavenicBear cavenicBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin);
        PolarBear vanillaBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.east(4));
        clearEquipment(cavenicBear);
        clearEquipment(vanillaBear);
        float genericDamage = 6.0F;
        float cavenicHealthBefore = cavenicBear.getHealth();
        float vanillaHealthBefore = vanillaBear.getHealth();

        helper.assertTrue(
            cavenicBear.hurt(level.damageSources().generic(), genericDamage),
            "Expected cavenic bear to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            vanillaBear.hurt(level.damageSources().generic(), genericDamage),
            "Expected vanilla polar bear baseline to remain vulnerable to generic non-fire damage"
        );
        helper.assertTrue(
            Math.abs((cavenicHealthBefore - cavenicBear.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected cavenic bear generic damage intake to stay vanilla-like"
        );
        helper.assertTrue(
            Math.abs((vanillaHealthBefore - vanillaBear.getHealth()) - genericDamage) < 1.0E-6F,
            "Expected vanilla polar bear generic damage intake to stay unchanged"
        );
        helper.assertTrue(
            cavenicBear.getLootTable().equals(EntityType.POLAR_BEAR.getDefaultLootTable()),
            "Expected cavenic bear damage follow-up to keep the vanilla polar bear loot-table baseline"
        );
        helper.assertTrue(
            CavenicBear.NATURAL_SPAWN_WEIGHT == 30
                && CavenicBear.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicBear.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected cavenic bear damage follow-up to keep the legacy 30/1/1 natural-spawn constants"
        );
        helper.assertTrue(
            cavenicBear.getMaxSpawnClusterSize() == 1,
            "Expected cavenic bear damage follow-up to keep the legacy max spawn cluster size of 1"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearLegacyMeleeAttackBehaviorAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BEAR_MELEE_ATTACK_ANCHOR;

        resetMiningArea(level, origin, 16.0D);

        CavenicBear cavenicBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin);
        PolarBear vanillaBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.east(8));
        Player cavenicTarget = makeMockPlayer(helper, level, GameType.SURVIVAL, ItemStack.EMPTY, origin.south(4));
        Player vanillaTarget = makeMockPlayer(helper, level, GameType.SURVIVAL, ItemStack.EMPTY, origin.east(8).south(4));

        positionTargetAroundMob(cavenicTarget, cavenicBear, 2.0D);
        positionTargetAroundMob(vanillaTarget, vanillaBear, 2.0D);

        WrappedGoal cavenicMeleeGoal = findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearMeleeAttackGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear melee goal"));
        WrappedGoal vanillaMeleeGoal = findGoalBySimpleName(vanillaBear.goalSelector.getAvailableGoals(), "PolarBearMeleeAttackGoal")
            .orElseThrow(() -> new IllegalStateException("Missing vanilla polar bear melee goal"));

        helper.assertTrue(
            countGoals(cavenicBear.goalSelector.getAvailableGoals(), MeleeAttackGoal.class) == 1L,
            "Expected cavenic bear to keep exactly one melee attack goal in the bounded melee slice"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "PolarBearMeleeAttackGoal").isEmpty(),
            "Expected cavenic bear to remove the vanilla polar bear melee goal"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearMeleeAttackGoal").isPresent(),
            "Expected cavenic bear to register the legacy melee goal"
        );
        helper.assertTrue(
            findGoalBySimpleName(vanillaBear.goalSelector.getAvailableGoals(), "PolarBearMeleeAttackGoal").isPresent(),
            "Expected vanilla polar bear to keep its vanilla melee goal"
        );

        cavenicBear.setTarget(cavenicTarget);
        vanillaBear.setTarget(vanillaTarget);

        float cavenicTargetHealthBefore = cavenicTarget.getHealth();
        float vanillaTargetHealthBefore = vanillaTarget.getHealth();

        invokeMeleeAttackCheck(cavenicMeleeGoal.getGoal(), cavenicTarget);
        invokeMeleeAttackCheck(vanillaMeleeGoal.getGoal(), vanillaTarget);

        helper.assertTrue(
            cavenicTarget.getHealth() < cavenicTargetHealthBefore,
            "Expected legacy cavenic bear melee reach to hit a player target at two blocks"
        );
        helper.assertTrue(
            Math.abs(vanillaTarget.getHealth() - vanillaTargetHealthBefore) < 1.0E-6F,
            "Expected vanilla polar bear melee reach baseline not to hit a player target at the same two-block spacing"
        );
        helper.assertFalse(cavenicBear.isStanding(), "Expected successful legacy melee hits to clear the standing pose");
        helper.assertFalse(vanillaBear.isStanding(), "Expected unchanged vanilla polar bear melee baseline to stay non-standing after the out-of-range check");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearLegacyMeleeAttackKeepsExistingSlicesStableAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BEAR_MELEE_ATTACK_ANCHOR.north(20);

        resetMiningArea(level, origin, 24.0D);

        CavenicBear cavenicBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin);
        PolarBear vanillaBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.east(8));
        Player cavenicTarget = makeMockPlayer(helper, level, GameType.SURVIVAL, ItemStack.EMPTY, origin.south(4));
        Player vanillaTarget = makeMockPlayer(helper, level, GameType.SURVIVAL, ItemStack.EMPTY, origin.east(8).south(4));
        Cow passiveTarget = spawnLivingEntity(helper, EntityType.COW, origin.west(4));
        Zombie attacker = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.south(8));

        positionTargetAroundMob(cavenicTarget, cavenicBear, 3.1D);
        positionTargetAroundMob(vanillaTarget, vanillaBear, 3.1D);

        WrappedGoal cavenicMeleeGoal = findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearMeleeAttackGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear melee goal"));
        WrappedGoal vanillaMeleeGoal = findGoalBySimpleName(vanillaBear.goalSelector.getAvailableGoals(), "PolarBearMeleeAttackGoal")
            .orElseThrow(() -> new IllegalStateException("Missing vanilla polar bear melee goal"));
        WrappedGoal cavenicHurtGoal = findGoalBySimpleName(cavenicBear.targetSelector.getAvailableGoals(), "LegacyCavenicBearHurtByTargetGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear hurt goal"));

        helper.assertTrue(cavenicBear.isLegacyHostileTarget(cavenicTarget), "Expected melee follow-up to keep players as legacy hostile targets");
        helper.assertFalse(cavenicBear.isLegacyHostileTarget(passiveTarget), "Expected melee follow-up to keep passive non-player targets outside the legacy hostile-target helper");

        cavenicBear.setLastHurtByMob(attacker);
        cavenicHurtGoal.start();
        helper.assertTrue(cavenicBear.getTarget() == attacker, "Expected melee follow-up to keep legacy hurt-by-target retaliation");

        cavenicBear.setTarget(cavenicTarget);
        vanillaBear.setTarget(vanillaTarget);
        cavenicBear.setStanding(false);
        vanillaBear.setStanding(false);
        setGoalField(cavenicMeleeGoal.getGoal(), "ticksUntilNextAttack", 10);
        setGoalField(vanillaMeleeGoal.getGoal(), "ticksUntilNextAttack", 10);

        invokeMeleeAttackCheck(cavenicMeleeGoal.getGoal(), cavenicTarget);
        invokeMeleeAttackCheck(vanillaMeleeGoal.getGoal(), vanillaTarget);

        helper.assertFalse(
            cavenicBear.isStanding(),
            "Expected legacy cavenic bear warning posture to stop before the broader vanilla three-point-one-block player spacing"
        );
        helper.assertTrue(
            vanillaBear.isStanding(),
            "Expected vanilla polar bear warning posture baseline to stay broader than the restored legacy cavenic bear melee threshold"
        );

        float fireHealthBefore = cavenicBear.getHealth();
        helper.assertFalse(
            cavenicBear.hurt(level.damageSources().lava(), 4.0F),
            "Expected melee follow-up to keep legacy fire-tagged damage rejection"
        );
        helper.assertTrue(
            Math.abs(fireHealthBefore - cavenicBear.getHealth()) < 1.0E-6F,
            "Expected melee follow-up to keep cavenic bear health unchanged after lava damage"
        );
        helper.assertTrue(
            CavenicBear.LEGACY_FALL_DAMAGE_MULTIPLIER == 0.35F,
            "Expected melee follow-up to keep the legacy 0.35 fall-damage multiplier"
        );
        helper.assertTrue(
            CavenicBear.NATURAL_SPAWN_WEIGHT == 30
                && CavenicBear.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicBear.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected melee follow-up to keep the legacy 30/1/1 natural-spawn constants"
        );
        helper.assertTrue(
            cavenicBear.getMaxSpawnClusterSize() == 1,
            "Expected melee follow-up to keep the legacy max spawn cluster size of 1"
        );
        helper.assertTrue(
            cavenicBear.getLootTable().equals(EntityType.POLAR_BEAR.getDefaultLootTable()),
            "Expected melee follow-up to keep the vanilla polar bear loot-table baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearLegacyPanicBehaviorAppliesAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BEAR_PANIC_ANCHOR;

        resetMiningArea(level, origin, 24.0D);

        CavenicBear cavenicBurningBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin);
        PolarBear vanillaBurningBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.east(8));
        cavenicBurningBear.moveTo(origin.getX() + 0.5D, origin.getY() + 1.0D, origin.getZ() + 0.5D, 0.0F, 0.0F);
        vanillaBurningBear.moveTo(origin.east(8).getX() + 0.5D, origin.east(8).getY() + 1.0D, origin.east(8).getZ() + 0.5D, 0.0F, 0.0F);
        level.setBlockAndUpdate(origin.south(2).above(), Blocks.WATER.defaultBlockState());
        level.setBlockAndUpdate(origin.east(8).south(2).above(), Blocks.WATER.defaultBlockState());

        WrappedGoal cavenicPanicGoal = findGoalBySimpleName(cavenicBurningBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearPanicGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear panic goal"));
        WrappedGoal vanillaPanicGoal = findGoalBySimpleName(vanillaBurningBear.goalSelector.getAvailableGoals(), "PanicGoal")
            .orElseThrow(() -> new IllegalStateException("Missing vanilla panic goal"));

        helper.assertTrue(
            countGoals(cavenicBurningBear.goalSelector.getAvailableGoals(), PanicGoal.class) == 1L,
            "Expected cavenic bear to keep exactly one panic goal in the bounded panic slice"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBurningBear.goalSelector.getAvailableGoals(), "PanicGoal").isEmpty(),
            "Expected cavenic bear to remove the vanilla adult polar bear panic goal"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBurningBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearPanicGoal").isPresent(),
            "Expected cavenic bear to register the legacy burning-only panic goal"
        );

        cavenicBurningBear.igniteForSeconds(5.0F);
        vanillaBurningBear.igniteForSeconds(5.0F);

        helper.assertTrue(invokePanicShouldPanic(cavenicPanicGoal.getGoal()), "Expected legacy cavenic bear panic to trigger while burning");
        helper.assertFalse(
            invokePanicShouldPanic(vanillaPanicGoal.getGoal()),
            "Expected vanilla adult polar bear panic not to trigger from burning alone without an environmental panic damage source"
        );
        helper.assertTrue(
            invokeGoalCanUse(cavenicPanicGoal.getGoal()),
            "Expected legacy cavenic bear panic canUse to succeed while burning with nearby water"
        );
        helper.assertFalse(
            invokeGoalCanUse(vanillaPanicGoal.getGoal()),
            "Expected vanilla adult polar bear panic canUse to stay inactive without a tagged environmental panic source"
        );

        CavenicBear cavenicFrozenBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin.north(12));
        PolarBear vanillaFrozenBear = spawnLivingEntity(helper, EntityType.POLAR_BEAR, origin.east(8).north(12));
        WrappedGoal cavenicFrozenPanicGoal = findGoalBySimpleName(cavenicFrozenBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearPanicGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear panic goal"));
        WrappedGoal vanillaFrozenPanicGoal = findGoalBySimpleName(vanillaFrozenBear.goalSelector.getAvailableGoals(), "PanicGoal")
            .orElseThrow(() -> new IllegalStateException("Missing vanilla panic goal"));

        cavenicFrozenBear.handleDamageEvent(level.damageSources().freeze());
        vanillaFrozenBear.handleDamageEvent(level.damageSources().freeze());

        helper.assertFalse(
            invokePanicShouldPanic(cavenicFrozenPanicGoal.getGoal()),
            "Expected legacy cavenic bear panic to ignore non-burning freeze panic causes"
        );
        helper.assertTrue(
            invokePanicShouldPanic(vanillaFrozenPanicGoal.getGoal()),
            "Expected vanilla adult polar bear panic to treat freeze as an environmental panic cause"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBearLegacyPanicKeepsExistingSlicesStableAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BEAR_PANIC_ANCHOR.north(24);

        resetMiningArea(level, origin, 24.0D);

        CavenicBear cavenicBear = spawnLivingEntity(helper, ModRegistries.CAVENIC_BEAR.get(), origin);
        Player playerTarget = makeMockPlayer(helper, level, GameType.SURVIVAL, ItemStack.EMPTY, origin.south(4));
        Cow passiveTarget = spawnLivingEntity(helper, EntityType.COW, origin.west(4));
        Zombie attacker = spawnLivingEntity(helper, EntityType.ZOMBIE, origin.south(8));

        WrappedGoal panicGoal = findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearPanicGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear panic goal"));
        WrappedGoal meleeGoal = findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearMeleeAttackGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear melee goal"));
        WrappedGoal hurtGoal = findGoalBySimpleName(cavenicBear.targetSelector.getAvailableGoals(), "LegacyCavenicBearHurtByTargetGoal")
            .orElseThrow(() -> new IllegalStateException("Missing legacy cavenic bear hurt-by-target goal"));

        helper.assertTrue(cavenicBear.isLegacyHostileTarget(playerTarget), "Expected panic follow-up to keep players as legacy hostile targets");
        helper.assertFalse(cavenicBear.isLegacyHostileTarget(passiveTarget), "Expected panic follow-up to keep passive non-player targets outside the legacy hostile-target helper");
        helper.assertTrue(
            countGoals(cavenicBear.goalSelector.getAvailableGoals(), MeleeAttackGoal.class) == 1L,
            "Expected panic follow-up to keep exactly one cavenic bear melee goal"
        );
        helper.assertTrue(
            countGoals(cavenicBear.goalSelector.getAvailableGoals(), PanicGoal.class) == 1L,
            "Expected panic follow-up to keep exactly one cavenic bear panic goal"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "PolarBearMeleeAttackGoal").isEmpty(),
            "Expected panic follow-up to keep the vanilla polar bear melee goal removed"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "PanicGoal").isEmpty(),
            "Expected panic follow-up to keep the vanilla panic goal removed"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearMeleeAttackGoal").isPresent(),
            "Expected panic follow-up to keep the legacy melee goal registered"
        );
        helper.assertTrue(
            findGoalBySimpleName(cavenicBear.goalSelector.getAvailableGoals(), "LegacyCavenicBearPanicGoal").isPresent(),
            "Expected panic follow-up to keep the legacy panic goal registered"
        );

        cavenicBear.setLastHurtByMob(attacker);
        hurtGoal.start();
        helper.assertTrue(cavenicBear.getTarget() == attacker, "Expected panic follow-up to keep legacy hurt-by-target retaliation");
        helper.assertFalse(
            invokePanicShouldPanic(panicGoal.getGoal()),
            "Expected legacy panic helper to stay inactive before the cavenic bear is burning"
        );

        float fireHealthBefore = cavenicBear.getHealth();
        helper.assertFalse(
            cavenicBear.hurt(level.damageSources().lava(), 4.0F),
            "Expected panic follow-up to keep legacy fire-tagged damage rejection"
        );
        helper.assertTrue(
            Math.abs(fireHealthBefore - cavenicBear.getHealth()) < 1.0E-6F,
            "Expected panic follow-up to keep cavenic bear health unchanged after lava damage"
        );
        helper.assertTrue(
            CavenicBear.LEGACY_FALL_DAMAGE_MULTIPLIER == 0.35F,
            "Expected panic follow-up to keep the legacy 0.35 fall-damage multiplier"
        );
        helper.assertTrue(
            CavenicBear.NATURAL_SPAWN_WEIGHT == 30
                && CavenicBear.NATURAL_SPAWN_MIN_COUNT == 1
                && CavenicBear.NATURAL_SPAWN_MAX_COUNT == 1,
            "Expected panic follow-up to keep the legacy 30/1/1 natural-spawn constants"
        );
        helper.assertTrue(
            cavenicBear.getMaxSpawnClusterSize() == 1,
            "Expected panic follow-up to keep the legacy max spawn cluster size of 1"
        );
        helper.assertTrue(
            cavenicBear.getLootTable().equals(EntityType.POLAR_BEAR.getDefaultLootTable()),
            "Expected panic follow-up to keep the vanilla polar bear loot-table baseline"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicMeleeRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.CAVENIC_SWORD.get() != null, "Missing cavenic sword");
        helper.assertTrue(ModRegistries.CAVENIC_AXE.get() != null, "Missing cavenic axe");

        assertRegistryId(helper, ModRegistries.CAVENIC_SWORD.get(), "cavernreborn:cavenic_sword");
        assertRegistryId(helper, ModRegistries.CAVENIC_AXE.get(), "cavernreborn:cavenic_axe");

        ItemStack sword = cavenicSword();
        ItemStack axe = cavenicAxe();
        helper.assertTrue(!sword.isEmpty(), "Expected cavenic sword stack to be constructible");
        helper.assertTrue(!axe.isEmpty(), "Expected cavenic axe stack to be constructible");
        helper.assertTrue(sword.isDamageableItem(), "Expected cavenic sword to be damageable");
        helper.assertTrue(axe.isDamageableItem(), "Expected cavenic axe to be damageable");
        helper.assertTrue(sword.getMaxDamage() == ModToolTiers.CAVENIC.getUses(), "Unexpected cavenic sword durability");
        helper.assertTrue(axe.getMaxDamage() == ModToolTiers.CAVENIC.getUses(), "Unexpected cavenic axe durability");
        helper.assertTrue(sword.is(ModItemTags.CAVENIC_ITEMS), "Expected cavenic sword to resolve through cavenic_items");
        helper.assertTrue(axe.is(ModItemTags.CAVENIC_ITEMS), "Expected cavenic axe to resolve through cavenic_items");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicMeleeRepairAndRecipesResolveAtRuntime(GameTestHelper helper) {
        ItemStack repairStack = cavenicOrb();
        ItemStack wrongRepairStack = new ItemStack(Items.STICK);
        List<ItemStack> tools = List.of(cavenicSword(), cavenicAxe());

        for (ItemStack tool : tools) {
            helper.assertTrue(tool.getItem().isValidRepairItem(tool, repairStack), "Expected cavenic_orb repair support for " + itemId(tool));
            helper.assertFalse(tool.getItem().isValidRepairItem(tool, wrongRepairStack), "Stick must not repair " + itemId(tool));
        }

        helper.assertTrue(
            helper.getLevel().getRecipeManager().byKey(ResourceLocation.parse("cavernreborn:cavenic_sword")).isPresent(),
            "Expected runtime recipe manager to resolve cavenic_sword"
        );
        helper.assertTrue(
            helper.getLevel().getRecipeManager().byKey(ResourceLocation.parse("cavernreborn:cavenic_axe")).isPresent(),
            "Expected runtime recipe manager to resolve cavenic_axe"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.CAVENIC_BOW.get() != null, "Missing cavenic bow");
        assertRegistryId(helper, ModRegistries.CAVENIC_BOW.get(), "cavernreborn:cavenic_bow");

        ItemStack bow = cavenicBow();
        helper.assertTrue(!bow.isEmpty(), "Expected cavenic bow stack to be constructible");
        helper.assertTrue(bow.isDamageableItem(), "Expected cavenic bow to be damageable");
        helper.assertTrue(bow.getMaxDamage() == ModToolTiers.CAVENIC.getUses(), "Unexpected cavenic bow durability");
        helper.assertTrue(bow.is(ModItemTags.CAVENIC_ITEMS), "Expected cavenic bow to resolve through cavenic_items");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowRepairsWithOrbAndRecipeResolvesAtRuntime(GameTestHelper helper) {
        ItemStack bow = cavenicBow();
        ItemStack repairStack = cavenicOrb();
        ItemStack wrongRepairStack = new ItemStack(Items.STICK);

        helper.assertTrue(bow.getItem().isValidRepairItem(bow, repairStack), "Expected cavenic_orb repair support for cavenic bow");
        helper.assertFalse(bow.getItem().isValidRepairItem(bow, wrongRepairStack), "Stick must not repair cavenic bow");
        helper.assertTrue(
            helper.getLevel().getRecipeManager().byKey(ResourceLocation.parse("cavernreborn:cavenic_bow")).isPresent(),
            "Expected runtime recipe manager to resolve cavenic_bow"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowModeStateCyclesAndPersistsAtRuntime(GameTestHelper helper) {
        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();
        ItemStack bow = cavenicBow();

        helper.assertTrue(bowItem.getMode(bow) == CavenicBowMode.NORMAL, "New cavenic bow stacks must default to NORMAL mode");
        helper.assertTrue(bowItem.cycleMode(bow) == CavenicBowMode.RAPID, "Expected NORMAL to cycle into RAPID");
        helper.assertTrue(bowItem.getMode(bow) == CavenicBowMode.RAPID, "Expected RAPID mode to persist on the stack");
        helper.assertTrue(bowItem.cycleMode(bow) == CavenicBowMode.SNIPE, "Expected RAPID to cycle into SNIPE");
        helper.assertTrue(bowItem.cycleMode(bow) == CavenicBowMode.TORCH, "Expected SNIPE to cycle into TORCH");
        helper.assertTrue(bowItem.cycleMode(bow) == CavenicBowMode.NORMAL, "Expected TORCH to wrap back to NORMAL");
        helper.assertTrue(bowItem.getMode(bow) == CavenicBowMode.NORMAL, "Expected wrapped NORMAL mode to persist on the stack");
        helper.assertTrue(bow.getDamageValue() == 0, "Mode cycling must not damage the cavenic bow");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowSneakUseCyclesModeWithoutArrows(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BOW_MODE_ANCHOR;
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), origin);
        CavenicBowItem bowItem = (CavenicBowItem) player.getMainHandItem().getItem();

        player.setShiftKeyDown(true);
        InteractionResultHolder<ItemStack> result = bowItem.use(level, player, InteractionHand.MAIN_HAND);
        player.setShiftKeyDown(false);

        helper.assertTrue(result.getResult().consumesAction(), "Sneak + use should consume the interaction for mode cycling");
        helper.assertTrue(bowItem.getMode(player.getMainHandItem()) == CavenicBowMode.RAPID, "Sneak + use must cycle the cavenic bow mode");
        helper.assertTrue(player.getMainHandItem().getDamageValue() == 0, "Sneak + use must not damage the bow");
        helper.assertTrue(!player.isUsingItem(), "Sneak + use must not start the vanilla draw path");
        helper.assertTrue(itemIdsAround(level, origin, 6.0D).stream().noneMatch(id -> id.equals("minecraft:arrow")), "Mode cycling must not create or consume arrows");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowSupportsExpectedEnchantments(GameTestHelper helper) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        ItemStack bow = cavenicBow();

        assertSupportsEnchantment(helper, bow, enchantments.getOrThrow(Enchantments.POWER), true);
        assertSupportsEnchantment(helper, bow, enchantments.getOrThrow(Enchantments.PUNCH), true);
        assertSupportsEnchantment(helper, bow, enchantments.getOrThrow(Enchantments.FLAME), true);
        assertSupportsEnchantment(helper, bow, enchantments.getOrThrow(Enchantments.INFINITY), true);
        assertSupportsEnchantment(helper, bow, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, bow, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, bow, enchantments.getOrThrow(Enchantments.SHARPNESS), false);
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowNormalReleaseSemanticsStayVanillaAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();
        BlockPos survivalOrigin = CAVENIC_BOW_RELEASE_ANCHOR;
        BlockPos noArrowOrigin = CAVENIC_BOW_RELEASE_ANCHOR.east(32);
        BlockPos creativeOrigin = CAVENIC_BOW_RELEASE_ANCHOR.east(64);

        resetMiningArea(level, survivalOrigin, 10.0D);
        resetMiningArea(level, noArrowOrigin, 10.0D);
        resetMiningArea(level, creativeOrigin, 10.0D);

        Player survivalPlayer = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), survivalOrigin);
        survivalPlayer.getInventory().add(new ItemStack(Items.ARROW, 2));
        double defaultArrowDamage = createRuntimeArrow(level, survivalPlayer, survivalPlayer.getMainHandItem()).getBaseDamage();

        BowReleaseResult survivalResult = releaseBowThroughRealUse(helper, survivalPlayer, BowItem.MAX_DRAW_DURATION);
        AbstractArrow survivalArrow = singleSpawnedArrow(helper, survivalResult, "Expected NORMAL survival release to spawn exactly one arrow");

        helper.assertTrue(survivalResult.useResult().consumesAction(), "Survival NORMAL use must enter the vanilla draw path when arrows are available");
        helper.assertTrue(survivalResult.usingStarted(), "Survival NORMAL use must start item use before release");
        helper.assertTrue("minecraft:arrow".equals(entityTypeId(survivalArrow)), "Survival NORMAL release must spawn a vanilla arrow");
        helper.assertTrue(survivalResult.arrowCountBefore() - survivalResult.arrowCountAfter() == 1, "Survival NORMAL release must consume exactly one arrow");
        helper.assertTrue(survivalResult.bowDamageAfter() - survivalResult.bowDamageBefore() == 1, "Survival NORMAL release must apply the vanilla single durability cost");
        helper.assertTrue(survivalResult.torchCountBefore() == survivalResult.torchCountAfter(), "Survival NORMAL release must not consume torches");
        helper.assertFalse(CavenicBowItem.isTorchArrow(survivalArrow), "Survival NORMAL release must not Torch-mark the projectile");
        helper.assertTrue(Math.abs(survivalArrow.getBaseDamage() - defaultArrowDamage) < 1.0E-6D, "Survival NORMAL release must keep vanilla arrow base damage");
        helper.assertTrue(survivalResult.modeAfter() == CavenicBowMode.NORMAL, "Survival NORMAL release must keep NORMAL mode stored");

        Player noArrowPlayer = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), noArrowOrigin);
        BowReleaseResult noArrowResult = releaseBowThroughRealUse(helper, noArrowPlayer, BowItem.MAX_DRAW_DURATION);

        helper.assertTrue(noArrowResult.useResult() == InteractionResult.FAIL, "Survival NORMAL use without arrows must fail like vanilla BowItem");
        helper.assertFalse(noArrowResult.usingStarted(), "Survival NORMAL use without arrows must not start using");
        helper.assertTrue(newArrowCount(noArrowResult) == 0, "Survival NORMAL use without arrows must not spawn an arrow");
        helper.assertTrue(noArrowResult.bowDamageBefore() == noArrowResult.bowDamageAfter(), "Survival NORMAL use without arrows must not damage the bow");
        helper.assertTrue(noArrowResult.torchCountBefore() == noArrowResult.torchCountAfter(), "Survival NORMAL use without arrows must not consume torches");
        helper.assertTrue(noArrowResult.modeAfter() == CavenicBowMode.NORMAL, "Survival NORMAL use without arrows must keep NORMAL mode stored");

        Player creativePlayer = makeMockPlayer(helper, level, GameType.CREATIVE, cavenicBow(), creativeOrigin);
        BowReleaseResult creativeResult = releaseBowThroughRealUse(helper, creativePlayer, BowItem.MAX_DRAW_DURATION);

        helper.assertTrue(creativeResult.useResult().consumesAction(), "Creative NORMAL use without arrows must still start the vanilla draw path");
        helper.assertTrue(creativeResult.usingStarted(), "Creative NORMAL use without arrows must start using");
        helper.runAfterDelay(1, () -> {
            AbstractArrow creativeArrow = singleSpawnedArrow(helper, creativeResult, "Creative NORMAL release without arrows must still fire exactly one vanilla arrow");
            helper.assertTrue("minecraft:arrow".equals(entityTypeId(creativeArrow)), "Creative NORMAL release without arrows must keep the vanilla arrow path");
            helper.assertFalse(CavenicBowItem.isTorchArrow(creativeArrow), "Creative NORMAL release without arrows must not Torch-mark the projectile");
            helper.assertTrue(creativeResult.arrowCountBefore() == creativeResult.arrowCountAfter(), "Creative NORMAL release without arrows must not change arrow inventory");
            helper.assertTrue(creativeResult.bowDamageBefore() == creativeResult.bowDamageAfter(), "Creative NORMAL release without arrows must not damage the bow");
            helper.assertTrue(creativeResult.torchCountBefore() == creativeResult.torchCountAfter(), "Creative NORMAL release without arrows must not consume torches");
            helper.assertTrue(creativeResult.modeAfter() == CavenicBowMode.NORMAL, "Creative NORMAL release without arrows must keep NORMAL mode stored");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowInfinityNormalReleaseSemanticsStayVanillaAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        HolderLookup.RegistryLookup<Enchantment> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        BlockPos normalOrigin = CAVENIC_BOW_RELEASE_ANCHOR.east(96);

        resetMiningArea(level, normalOrigin, 10.0D);

        Player normalPlayer = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), normalOrigin);
        ItemStack normalBow = normalPlayer.getMainHandItem();
        normalBow.enchant(enchantments.getOrThrow(Enchantments.INFINITY), 1);
        normalPlayer.getInventory().add(new ItemStack(Items.ARROW));

        BowReleaseResult normalResult = releaseBowThroughRealUse(helper, normalPlayer, BowItem.MAX_DRAW_DURATION);

        helper.runAfterDelay(1, () -> {
            AbstractArrow normalArrow = singleSpawnedArrow(helper, normalResult, "Infinity NORMAL release must still fire exactly one arrow");
            helper.assertTrue("minecraft:arrow".equals(entityTypeId(normalArrow)), "Infinity NORMAL release must keep the vanilla arrow entity");
            helper.assertTrue(normalResult.arrowCountBefore() == normalResult.arrowCountAfter(), "Infinity NORMAL release must keep the single arrow stack intact");
            helper.assertTrue(normalResult.bowDamageAfter() - normalResult.bowDamageBefore() == 1, "Infinity NORMAL release must still apply normal bow durability");
            helper.assertFalse(CavenicBowItem.isTorchArrow(normalArrow), "Infinity NORMAL release must not Torch-mark the arrow");
            helper.assertTrue(normalResult.modeAfter() == CavenicBowMode.NORMAL, "Infinity NORMAL release must keep NORMAL mode stored");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowInfinityTorchWithoutTorchStaysUnmarkedAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        HolderLookup.RegistryLookup<Enchantment> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();
        CavenicBowTorchEvents torchEvents = new CavenicBowTorchEvents();
        BlockPos noTorchOrigin = CAVENIC_BOW_RELEASE_ANCHOR.east(128);
        BlockPos torchlessSupportPos = noTorchOrigin.east(4);
        BlockPos torchlessTargetPos = torchlessSupportPos.above();

        resetMiningArea(level, noTorchOrigin, 10.0D);
        level.setBlock(torchlessSupportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        Player noTorchPlayer = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), noTorchOrigin);
        ItemStack noTorchBow = noTorchPlayer.getMainHandItem();
        bowItem.setMode(noTorchBow, CavenicBowMode.TORCH);
        noTorchBow.enchant(enchantments.getOrThrow(Enchantments.INFINITY), 1);
        noTorchPlayer.getInventory().add(new ItemStack(Items.ARROW));

        helper.assertFalse(
            bowItem.shouldMarkTorchShot(noTorchBow, noTorchPlayer, 1.0F),
            "Infinity TORCH mode without torch ammo must stay in the unmarked vanilla-arrow path"
        );

        BowReleaseResult noTorchResult = releaseBowThroughRealUse(helper, noTorchPlayer, BowItem.MAX_DRAW_DURATION);

        helper.runAfterDelay(1, () -> {
            AbstractArrow noTorchArrow = singleSpawnedArrow(helper, noTorchResult, "Infinity TORCH release without torches must still fire one vanilla arrow");
            helper.assertTrue("minecraft:arrow".equals(entityTypeId(noTorchArrow)), "Infinity TORCH release without torches must keep the vanilla arrow entity");
            helper.assertFalse(CavenicBowItem.isTorchArrow(noTorchArrow), "Infinity must not create a free Torch-marked shot when no torch item is present");
            helper.assertTrue(noTorchResult.useResult().consumesAction(), "Infinity TORCH release without torches must still consume the normal bow-use action");
            helper.assertTrue(noTorchResult.usingStarted(), "Infinity TORCH release without torches must still start the vanilla draw path");
            helper.assertTrue(noTorchResult.arrowCountBefore() == noTorchResult.arrowCountAfter(), "Infinity TORCH release without torches must still keep arrow count intact");
            helper.assertTrue(noTorchResult.torchCountBefore() == noTorchResult.torchCountAfter(), "Infinity TORCH release without torches must not consume torches");
            helper.assertTrue(noTorchResult.bowDamageAfter() - noTorchResult.bowDamageBefore() == 1, "Infinity TORCH release without torches must keep the normal single durability cost");
            torchEvents.onProjectileImpact(new ProjectileImpactEvent(noTorchArrow, hitResult(torchlessSupportPos, Direction.UP)));
            helper.assertFalse(level.getBlockState(torchlessTargetPos).is(Blocks.TORCH), "Unmarked Infinity TORCH shots must not place torches");
            helper.assertTrue(noTorchResult.modeAfter() == CavenicBowMode.TORCH, "Infinity TORCH release without torches must keep TORCH mode stored");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowInfinityTorchWithTorchConsumesTorchAndPlacesTorchAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        HolderLookup.RegistryLookup<Enchantment> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();
        CavenicBowTorchEvents torchEvents = new CavenicBowTorchEvents();
        BlockPos torchOrigin = CAVENIC_BOW_RELEASE_ANCHOR.east(160);
        BlockPos torchSupportPos = torchOrigin.east(4);
        BlockPos torchTargetPos = torchSupportPos.above();

        resetMiningArea(level, torchOrigin, 10.0D);
        level.setBlock(torchSupportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        Player torchPlayer = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), torchOrigin);
        ItemStack torchBow = torchPlayer.getMainHandItem();
        bowItem.setMode(torchBow, CavenicBowMode.TORCH);
        torchBow.enchant(enchantments.getOrThrow(Enchantments.INFINITY), 1);
        torchPlayer.getInventory().add(new ItemStack(Items.ARROW));
        torchPlayer.getInventory().add(new ItemStack(Items.TORCH));

        BowReleaseResult torchResult = releaseBowThroughRealUse(helper, torchPlayer, BowItem.MAX_DRAW_DURATION);

        helper.runAfterDelay(1, () -> {
            AbstractArrow torchArrow = singleSpawnedArrow(helper, torchResult, "Infinity TORCH release with one torch must still fire one vanilla arrow");
            helper.assertTrue("minecraft:arrow".equals(entityTypeId(torchArrow)), "Infinity TORCH release with one torch must keep the vanilla arrow entity");
            helper.assertTrue(CavenicBowItem.isTorchArrow(torchArrow), "Infinity TORCH release with one torch must mark the arrow");
            helper.assertTrue(torchResult.arrowCountBefore() == torchResult.arrowCountAfter(), "Infinity TORCH release must keep arrow count intact");
            helper.assertTrue(torchResult.torchCountBefore() - torchResult.torchCountAfter() == 1, "Infinity TORCH release must still consume one torch");
            helper.assertTrue(torchResult.bowDamageAfter() - torchResult.bowDamageBefore() == 1, "Infinity TORCH release must keep the normal single durability cost");
            torchEvents.onProjectileImpact(new ProjectileImpactEvent(torchArrow, hitResult(torchSupportPos, Direction.UP)));
            helper.assertTrue(level.getBlockState(torchTargetPos).is(Blocks.TORCH), "Marked Infinity TORCH shots must still place a standing torch");
            helper.assertTrue(torchResult.modeAfter() == CavenicBowMode.TORCH, "Infinity TORCH release with one torch must keep TORCH mode stored");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowRapidModeBoostsShotPowerAndKeepsVanillaArrowAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos rapidOrigin = CAVENIC_BOW_RAPID_ANCHOR.east(32);
        int drawTicks = 6;
        float rawPower = BowItem.getPowerForTime(drawTicks);
        float cappedRawPower = 0.5F;
        float expectedVanillaVelocity = rawPower * 3.0F;

        resetMiningArea(level, rapidOrigin, 10.0D);

        Player rapidPlayer = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), rapidOrigin);
        rapidPlayer.getInventory().add(new ItemStack(Items.ARROW, 2));
        ItemStack rapidBow = rapidPlayer.getMainHandItem();
        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();
        bowItem.setMode(rapidBow, CavenicBowMode.RAPID);

        float rapidShotPower = bowItem.resolveShotPower(rapidBow, rawPower);
        double defaultArrowDamage = createRuntimeArrow(level, rapidPlayer, rapidBow).getBaseDamage();

        BowReleaseResult rapidResult = releaseBowThroughRealUse(helper, rapidPlayer, drawTicks);

        helper.runAfterDelay(1, () -> {
            AbstractArrow rapidArrow = singleSpawnedArrow(helper, rapidResult, "Expected RAPID release to spawn exactly one vanilla arrow");
            helper.assertTrue(rapidShotPower > rawPower, "RAPID mode must increase shot power for the same draw");
            helper.assertTrue(
                Math.abs(rapidShotPower - rawPower * CavenicBowRapidPolicy.POWER_MULTIPLIER) < 1.0e-6F,
                "RAPID mode must use the bounded power multiplier"
            );
            helper.assertTrue(
                Math.abs(bowItem.resolveShotPower(rapidBow, cappedRawPower) - CavenicBowRapidPolicy.MAX_POWER) < 1.0e-6F,
                "RAPID mode must cap adjusted shot power at 1.0F"
            );
            helper.assertTrue("minecraft:arrow".equals(entityTypeId(rapidArrow)), "RAPID mode must keep spawning a vanilla arrow");
            helper.assertFalse(
                bowItem.applySnipeBoost(rapidBow, rapidArrow, rapidShotPower),
                "RAPID mode must not apply a SNIPE-style damage boost to the arrow"
            );
            helper.assertTrue(
                Math.abs(rapidArrow.getBaseDamage() - defaultArrowDamage) < 1.0E-6D,
                "RAPID mode must not add a SNIPE-style damage multiplier"
            );
            helper.assertTrue(
                Math.abs(bowItem.resolveProjectileVelocity(rapidBow, rapidShotPower * 3.0F, rapidShotPower) - rapidShotPower * 3.0F) < 1.0E-6F,
                "RAPID mode must keep vanilla velocity math on top of the boosted shot power"
            );
            helper.assertTrue(
                rapidArrow.getDeltaMovement().length() > expectedVanillaVelocity,
                "RAPID mode must increase projectile velocity for the same raw draw power"
            );
            helper.assertFalse(CavenicBowItem.isTorchArrow(rapidArrow), "RAPID mode must not Torch-mark the arrow");
            helper.assertTrue(rapidResult.arrowCountBefore() - rapidResult.arrowCountAfter() == 1, "RAPID release must consume one arrow");
            helper.assertTrue(rapidResult.bowDamageAfter() - rapidResult.bowDamageBefore() == 1, "RAPID release must not add extra durability cost");
            helper.assertTrue(rapidResult.modeAfter() == CavenicBowMode.RAPID, "RAPID mode must remain stored after real release");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowSnipeModeBoostsFullChargeShotsAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos snipeOrigin = CAVENIC_BOW_SNIPE_ANCHOR.east(32);
        float fullChargePower = 1.0F;
        float expectedVanillaVelocity = 3.0F;

        resetMiningArea(level, snipeOrigin, 10.0D);

        Player snipePlayer = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), snipeOrigin);
        snipePlayer.getInventory().add(new ItemStack(Items.ARROW, 2));
        ItemStack snipeBow = snipePlayer.getMainHandItem();
        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();

        bowItem.setMode(snipeBow, CavenicBowMode.SNIPE);
        double defaultArrowDamage = createRuntimeArrow(level, snipePlayer, snipeBow).getBaseDamage();

        BowReleaseResult snipeResult = releaseBowThroughRealUse(helper, snipePlayer, BowItem.MAX_DRAW_DURATION);

        helper.runAfterDelay(1, () -> {
            AbstractArrow snipeArrow = singleSpawnedArrow(helper, snipeResult, "Expected SNIPE full-charge release to spawn exactly one arrow");
            helper.assertTrue("minecraft:arrow".equals(entityTypeId(snipeArrow)), "SNIPE mode must still spawn a vanilla arrow");
            helper.assertTrue(
                snipeResult.bowDamageAfter() - snipeResult.bowDamageBefore() == 1 + CavenicBowSnipePolicy.EXTRA_DURABILITY_COST,
                "SNIPE shots must apply the vanilla bow durability cost plus the bounded Snipe surcharge"
            );
            helper.assertTrue(snipeResult.arrowCountBefore() - snipeResult.arrowCountAfter() == 1, "SNIPE full-charge release must consume one arrow");
            helper.assertTrue(snipeResult.useResult().consumesAction(), "SNIPE full-charge release must still consume the normal bow-use action");
            helper.assertTrue(snipeResult.usingStarted(), "SNIPE full-charge release must still start the vanilla draw path");
            helper.assertFalse(CavenicBowItem.isTorchArrow(snipeArrow), "SNIPE full-charge release must not Torch-mark the arrow");
            helper.assertTrue(snipeResult.modeAfter() == CavenicBowMode.SNIPE, "SNIPE mode must remain stored after real release");
            helper.assertTrue(
                Math.abs(snipeArrow.getBaseDamage() - CavenicBowSnipePolicy.adjustedBaseDamage(CavenicBowMode.SNIPE, defaultArrowDamage, fullChargePower)) < 1.0E-6D,
                "SNIPE mode must increase arrow base damage on full-charge shots"
            );
            helper.assertTrue(
                snipeArrow.getDeltaMovement().length() > expectedVanillaVelocity,
                "SNIPE mode must increase projectile velocity on full-charge shots"
            );
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowTorchModeMarksVanillaArrowsConsumesTorchAndPlacesStandingTorchAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BOW_TORCH_ANCHOR;
        BlockPos supportPos = origin.east(4);
        BlockPos torchPos = supportPos.above();

        resetMiningArea(level, origin, 10.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), origin);
        player.getInventory().add(new ItemStack(Items.ARROW));
        player.getInventory().add(new ItemStack(Items.TORCH, 2));

        ItemStack bow = player.getMainHandItem();
        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();
        bowItem.setMode(bow, CavenicBowMode.TORCH);
        helper.assertTrue(
            bowItem.shouldMarkTorchShot(bow, player, 1.0F),
            "Full-charge survival TORCH mode with torch ammo must stay eligible for Torch-arrow marking"
        );

        BowReleaseResult torchResult = releaseBowThroughRealUse(helper, player, BowItem.MAX_DRAW_DURATION);

        helper.runAfterDelay(1, () -> {
            helper.assertTrue(torchResult.modeAfter() == CavenicBowMode.TORCH, "TORCH mode must remain stored on the stack");
            helper.assertTrue(torchResult.arrowCountBefore() - torchResult.arrowCountAfter() == 1, "TORCH release must consume one arrow by normal vanilla rules");
            helper.assertTrue(torchResult.torchCountBefore() - torchResult.torchCountAfter() == 1, "Marked TORCH shots must consume exactly one torch in survival");
            helper.assertTrue(level.getBlockState(torchPos).is(Blocks.TORCH), "TORCH mode should place a standing torch on a valid top-face hit");
            helper.assertTrue(torchResult.bowDamageAfter() - torchResult.bowDamageBefore() == 1, "TORCH mode must keep the vanilla single durability cost");
            helper.assertTrue(CavenicBowTorchPolicy.EXTRA_DURABILITY_COST == 0, "TORCH mode must not add extra durability cost");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowTorchModeMarkedVanillaArrowSemanticsStayBoundedAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BOW_TORCH_ANCHOR.north(32);
        BlockPos supportPos = origin.east(4);
        BlockPos torchPos = supportPos.above();

        resetMiningArea(level, origin, 10.0D);
        level.setBlock(supportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), origin);
        player.getInventory().add(new ItemStack(Items.ARROW));
        player.getInventory().add(new ItemStack(Items.TORCH, 2));

        ItemStack bow = player.getMainHandItem();
        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();
        CavenicBowTorchEvents torchEvents = new CavenicBowTorchEvents();
        bowItem.setMode(bow, CavenicBowMode.TORCH);

        AbstractArrow arrow = createRuntimeArrow(level, player, bow);
        double defaultArrowDamage = arrow.getBaseDamage();
        float velocity = bowItem.resolveProjectileVelocity(bow, 3.0F, 1.0F);

        helper.assertTrue("minecraft:arrow".equals(entityTypeId(arrow)), "TORCH mode must still use a vanilla arrow entity");
        helper.assertFalse(
            bowItem.applySnipeBoost(bow, arrow, 1.0F),
            "TORCH mode must not apply a SNIPE-style damage boost to the arrow"
        );
        helper.assertTrue(
            Math.abs(arrow.getBaseDamage() - defaultArrowDamage) < 1.0E-6D,
            "TORCH mode must keep vanilla arrow base damage"
        );
        helper.assertTrue(
            Math.abs(velocity - 3.0F) < 1.0E-6F,
            "TORCH mode must keep the vanilla full-charge projectile velocity"
        );

        arrow.setDeltaMovement(velocity, 0.0D, 0.0D);
        CavenicBowItem.markTorchArrow(arrow);

        helper.assertTrue(CavenicBowItem.isTorchArrow(arrow), "TORCH mode must mark the arrow as a Torch arrow");
        helper.assertTrue(arrow.getDeltaMovement().length() > 2.5D, "TORCH mode must keep a normal full-charge vanilla-arrow speed band");
        helper.assertTrue(arrow.getDeltaMovement().length() < 3.2D, "TORCH mode must not add RAPID or SNIPE projectile speed");

        torchEvents.onProjectileImpact(new ProjectileImpactEvent(arrow, hitResult(supportPos, Direction.UP)));

        helper.assertTrue(level.getBlockState(torchPos).is(Blocks.TORCH), "TORCH mode should place a standing torch on a valid top-face hit");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowTorchModePlacementRulesStayBoundedAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BOW_TORCH_ANCHOR.east(32);
        BlockPos wallSupportPos = origin.east(4);
        BlockPos wallTorchPos = wallSupportPos.east();
        BlockPos invalidSupportPos = origin.east(10);
        BlockPos invalidTargetPos = invalidSupportPos.east();
        BlockPos oppositeSupportPos = invalidTargetPos.east();
        BlockPos occupiedSupportPos = origin.east(16);
        BlockPos occupiedTargetPos = occupiedSupportPos.above();
        BlockPos wetSupportPos = origin.east(22);
        BlockPos wetTargetPos = wetSupportPos.above();
        BlockPos downSupportPos = origin.east(28);
        BlockPos downTargetPos = downSupportPos.below();
        BlockPos entityAnchorPos = origin.east(34);
        BlockPos noTorchSupportPos = origin.east(40);
        BlockPos noTorchTargetPos = noTorchSupportPos.above();

        resetMiningArea(level, origin, 24.0D);
        level.setBlock(wallSupportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(invalidSupportPos, Blocks.COBWEB.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(oppositeSupportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(occupiedSupportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(occupiedTargetPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(wetSupportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(wetTargetPos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(downSupportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(noTorchSupportPos, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();
        CavenicBowTorchEvents torchEvents = new CavenicBowTorchEvents();

        Player creativePlayer = makeMockPlayer(helper, level, GameType.CREATIVE, cavenicBow(), origin);
        ItemStack creativeBow = creativePlayer.getMainHandItem();
        bowItem.setMode(creativeBow, CavenicBowMode.TORCH);
        creativePlayer.getInventory().add(new ItemStack(Items.ARROW));
        helper.assertFalse(bowItem.hasTorchAmmo(creativePlayer), "Creative TORCH-mode smoke should not require a torch stack in inventory");
        helper.assertTrue(bowItem.shouldMarkTorchShot(creativeBow, creativePlayer, 1.0F), "Creative TORCH shots should still be eligible for marking");

        BowReleaseResult wallResult = releaseBowThroughRealUse(helper, creativePlayer, BowItem.MAX_DRAW_DURATION);
        AbstractArrow wallArrow = singleSpawnedArrow(helper, wallResult, "Creative TORCH release must spawn one marked vanilla arrow");
        torchEvents.onProjectileImpact(new ProjectileImpactEvent(wallArrow, hitResult(wallSupportPos, Direction.EAST)));

        helper.assertTrue("minecraft:arrow".equals(entityTypeId(wallArrow)), "Creative TORCH mode must still use a vanilla arrow entity");
        helper.assertTrue(CavenicBowItem.isTorchArrow(wallArrow), "Creative TORCH release must mark the actual vanilla arrow");
        helper.assertTrue(level.getBlockState(wallTorchPos).is(Blocks.WALL_TORCH), "TORCH mode should place a wall torch on a valid horizontal-face hit");
        helper.assertTrue(level.getBlockState(wallTorchPos).getValue(WallTorchBlock.FACING) == Direction.EAST, "Wall torch orientation must match the impacted horizontal face");
        helper.assertTrue(countPlayerItem(creativePlayer, Items.TORCH) == 0, "Creative TORCH shots must not consume torches");

        BowReleaseResult invalidSupportResult = releaseBowThroughRealUse(helper, creativePlayer, BowItem.MAX_DRAW_DURATION);
        AbstractArrow invalidSupportArrow = singleSpawnedArrow(helper, invalidSupportResult, "Creative TORCH release should keep spawning vanilla arrows for invalid support checks");
        torchEvents.onProjectileImpact(new ProjectileImpactEvent(invalidSupportArrow, hitResult(invalidSupportPos, Direction.EAST)));
        helper.assertTrue(level.getBlockState(invalidTargetPos).isAir(), "TORCH placement must not fallback-attach to an unrelated opposite support block");

        BowReleaseResult occupiedResult = releaseBowThroughRealUse(helper, creativePlayer, BowItem.MAX_DRAW_DURATION);
        AbstractArrow occupiedArrow = singleSpawnedArrow(helper, occupiedResult, "Creative TORCH release should keep spawning vanilla arrows for occupied-target checks");
        torchEvents.onProjectileImpact(new ProjectileImpactEvent(occupiedArrow, hitResult(occupiedSupportPos, Direction.UP)));
        helper.assertTrue(level.getBlockState(occupiedTargetPos).is(Blocks.STONE), "TORCH mode must not replace occupied non-replaceable targets");

        BowReleaseResult wetResult = releaseBowThroughRealUse(helper, creativePlayer, BowItem.MAX_DRAW_DURATION);
        AbstractArrow wetArrow = singleSpawnedArrow(helper, wetResult, "Creative TORCH release should keep spawning vanilla arrows for liquid-target checks");
        torchEvents.onProjectileImpact(new ProjectileImpactEvent(wetArrow, hitResult(wetSupportPos, Direction.UP)));
        helper.assertTrue(level.getBlockState(wetTargetPos).is(Blocks.WATER), "TORCH mode must not replace liquids");

        BowReleaseResult downResult = releaseBowThroughRealUse(helper, creativePlayer, BowItem.MAX_DRAW_DURATION);
        AbstractArrow downArrow = singleSpawnedArrow(helper, downResult, "Creative TORCH release should keep spawning vanilla arrows for bottom-face checks");
        torchEvents.onProjectileImpact(new ProjectileImpactEvent(downArrow, hitResult(downSupportPos, Direction.DOWN)));
        helper.assertTrue(level.getBlockState(downTargetPos).isAir(), "TORCH mode must ignore bottom-face hits");

        BowReleaseResult entityHitResult = releaseBowThroughRealUse(helper, creativePlayer, BowItem.MAX_DRAW_DURATION);
        AbstractArrow entityHitArrow = singleSpawnedArrow(helper, entityHitResult, "Creative TORCH release should keep spawning vanilla arrows for entity-hit checks");
        torchEvents.onProjectileImpact(new ProjectileImpactEvent(entityHitArrow, new EntityHitResult(creativePlayer)));
        helper.assertTrue(level.getBlockState(entityAnchorPos.above()).isAir(), "Entity hits must not place torches");

        Player survivalPlayer = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), origin.north(6));
        ItemStack survivalBow = survivalPlayer.getMainHandItem();
        bowItem.setMode(survivalBow, CavenicBowMode.TORCH);
        survivalPlayer.getInventory().add(new ItemStack(Items.ARROW));
        helper.assertFalse(bowItem.shouldMarkTorchShot(survivalBow, survivalPlayer, 1.0F), "Survival TORCH shots without torch ammo must stay unmarked");

        BowReleaseResult unmarkedResult = releaseBowThroughRealUse(helper, survivalPlayer, BowItem.MAX_DRAW_DURATION);
        AbstractArrow unmarkedArrow = singleSpawnedArrow(helper, unmarkedResult, "Survival TORCH release without torch ammo must still fire one vanilla arrow");
        torchEvents.onProjectileImpact(new ProjectileImpactEvent(unmarkedArrow, hitResult(noTorchSupportPos, Direction.UP)));

        helper.assertTrue(unmarkedResult.arrowCountBefore() - unmarkedResult.arrowCountAfter() == 1, "Survival TORCH release without torch ammo must still consume one arrow");
        helper.assertFalse(CavenicBowItem.isTorchArrow(unmarkedArrow), "No-torch survival shots must not be Torch-marked");
        helper.assertFalse(level.getBlockState(noTorchTargetPos).is(Blocks.TORCH), "No-torch survival TORCH shots must not place a torch");
        helper.assertTrue(bowItem.resolveShotPower(survivalBow, 0.5F) == 0.5F, "TORCH mode must not inherit RAPID shot power behavior");
        helper.assertTrue(bowItem.resolveProjectileVelocity(survivalBow, 3.0F, 1.0F) == 3.0F, "TORCH mode must not inherit SNIPE velocity behavior");
        helper.assertTrue(bowItem.resolveAdditionalDurabilityCost(survivalBow, 1.0F) == 0, "TORCH mode must not add extra durability cost");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicBowOnlyAppliesSnipeBoostForSnipeModeAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = CAVENIC_BOW_SNIPE_ANCHOR.east(64);

        resetMiningArea(level, origin, 8.0D);

        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, cavenicBow(), origin);
        CavenicBowItem bowItem = (CavenicBowItem) ModRegistries.CAVENIC_BOW.get();

        ItemStack normalBow = cavenicBow();
        ItemStack rapidBow = cavenicBow();
        ItemStack lowChargeSnipeBow = cavenicBow();
        ItemStack torchBow = cavenicBow();

        bowItem.setMode(rapidBow, CavenicBowMode.RAPID);
        bowItem.setMode(lowChargeSnipeBow, CavenicBowMode.SNIPE);
        bowItem.setMode(torchBow, CavenicBowMode.TORCH);

        AbstractArrow normalArrow = createRuntimeArrow(level, player, normalBow);
        AbstractArrow rapidArrow = createRuntimeArrow(level, player, rapidBow);
        AbstractArrow lowChargeSnipeArrow = createRuntimeArrow(level, player, lowChargeSnipeBow);
        AbstractArrow torchArrow = createRuntimeArrow(level, player, torchBow);

        double normalDamage = normalArrow.getBaseDamage();
        double rapidDamage = rapidArrow.getBaseDamage();
        double lowChargeSnipeDamage = lowChargeSnipeArrow.getBaseDamage();
        double torchDamage = torchArrow.getBaseDamage();

        helper.assertFalse(bowItem.applySnipeBoost(normalBow, normalArrow, 1.0F), "NORMAL mode must not apply Snipe damage");
        helper.assertFalse(bowItem.applySnipeBoost(rapidBow, rapidArrow, 1.0F), "RAPID mode must remain vanilla for now");
        helper.assertFalse(bowItem.applySnipeBoost(lowChargeSnipeBow, lowChargeSnipeArrow, 0.95F), "Undercharged SNIPE shots must remain vanilla");
        helper.assertFalse(bowItem.applySnipeBoost(torchBow, torchArrow, 1.0F), "TORCH mode must remain vanilla for now");

        helper.assertTrue(normalArrow.getBaseDamage() == normalDamage, "NORMAL mode must not change arrow base damage");
        helper.assertTrue(rapidArrow.getBaseDamage() == rapidDamage, "RAPID mode must not change arrow base damage");
        helper.assertTrue(lowChargeSnipeArrow.getBaseDamage() == lowChargeSnipeDamage, "Undercharged SNIPE shots must not change arrow base damage");
        helper.assertTrue(torchArrow.getBaseDamage() == torchDamage, "TORCH mode must not change arrow base damage");
        helper.assertTrue(
            Math.abs(bowItem.resolveShotPower(rapidBow, 0.5F) - CavenicBowRapidPolicy.MAX_POWER) < 1.0e-6F,
            "RAPID mode must cap adjusted shot power independently from SNIPE"
        );
        helper.assertTrue(bowItem.resolveAdditionalDurabilityCost(rapidBow, 1.0F) == CavenicBowRapidPolicy.EXTRA_DURABILITY_COST, "RAPID mode must not add extra durability cost");

        helper.assertTrue(
            bowItem.resolveProjectileVelocity(normalBow, 3.0F, 1.0F) == 3.0F,
            "NORMAL mode must keep vanilla projectile velocity"
        );
        helper.assertTrue(
            bowItem.resolveProjectileVelocity(lowChargeSnipeBow, 3.0F, 0.95F) == 3.0F,
            "Undercharged SNIPE shots must keep vanilla projectile velocity"
        );
        helper.assertTrue(
            bowItem.resolveProjectileVelocity(torchBow, 3.0F, 1.0F) == 3.0F,
            "TORCH mode must keep vanilla projectile velocity outside the Torch-placement slice"
        );
        helper.assertTrue(bowItem.resolveShotPower(torchBow, 0.5F) == 0.5F, "TORCH mode must keep vanilla shot power outside the Rapid slice");
        helper.assertTrue(bowItem.resolveAdditionalDurabilityCost(torchBow, 1.0F) == CavenicBowTorchPolicy.EXTRA_DURABILITY_COST, "TORCH mode must not add extra durability cost");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSwordCooldownResetRuntimeSmoke(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos playerPos = helper.absolutePos(new BlockPos(1, 1, 1));

        helper.killAllEntities();
        ItemStack sword = cavenicSword();
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, sword, playerPos);
        LivingEntity target = spawnLivingEntity(helper, EntityType.ZOMBIE, new BlockPos(3, 1, 1));
        target.invulnerableTime = 12;
        target.hurtTime = 6;
        target.hurtDuration = 6;

        sword.getItem().postHurtEnemy(sword, target, player);

        helper.assertTrue(target.invulnerableTime == 0, "Expected cavenic sword to reset target invulnerability cooldown");
        helper.assertTrue(target.hurtTime == 0, "Expected cavenic sword to reset target hurt cooldown");
        helper.assertTrue(target.hurtDuration == 0, "Expected cavenic sword to reset target hurt duration");
        helper.assertTrue(sword.getDamageValue() == 1, "Expected cavenic sword to take one durability when reset applies");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicSwordDurabilityLossWithoutCooldownRuntimeSmoke(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos playerPos = helper.absolutePos(new BlockPos(1, 1, 1));

        helper.killAllEntities();
        ItemStack sword = cavenicSword();
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, sword, playerPos);
        LivingEntity target = spawnLivingEntity(helper, EntityType.ZOMBIE, new BlockPos(3, 1, 1));
        target.invulnerableTime = 0;
        target.hurtTime = 0;
        target.hurtDuration = 0;

        sword.getItem().postHurtEnemy(sword, target, player);

        helper.assertTrue(target.invulnerableTime == 0, "Expected cavenic sword to leave inactive invulnerability cooldown unchanged");
        helper.assertTrue(target.hurtTime == 0, "Expected cavenic sword to leave inactive hurt cooldown unchanged");
        helper.assertTrue(target.hurtDuration == 0, "Expected cavenic sword to leave inactive hurt duration unchanged");
        helper.assertTrue(sword.getDamageValue() == 1, "Expected cavenic sword to take one durability on a server-side hit without cooldown reset");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void cavenicAxeSmashRuntimeSmoke(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos playerPos = helper.absolutePos(new BlockPos(1, 1, 1));

        helper.killAllEntities();
        ItemStack axe = cavenicAxe();
        Player player = makeMockPlayer(helper, level, GameType.SURVIVAL, axe, playerPos);
        LivingEntity primary = spawnLivingEntity(helper, EntityType.ZOMBIE, new BlockPos(3, 1, 1));
        LivingEntity nearbyHostile = spawnLivingEntity(helper, EntityType.ZOMBIE, new BlockPos(4, 1, 1));
        LivingEntity nearbyPassive = spawnLivingEntity(helper, EntityType.COW, new BlockPos(4, 1, 2));
        LivingEntity distantHostile = spawnLivingEntity(helper, EntityType.ZOMBIE, new BlockPos(7, 1, 1));

        helper.runAfterDelay(2, () -> {
            float primaryHealth = primary.getHealth();
            float nearbyHostileHealth = nearbyHostile.getHealth();
            float nearbyPassiveHealth = nearbyPassive.getHealth();
            float distantHostileHealth = distantHostile.getHealth();

            axe.getItem().postHurtEnemy(axe, primary, player);

            helper.assertTrue(primary.getHealth() == primaryHealth, "Cavenic axe smash must not damage the primary target twice");
            helper.assertTrue(
                nearbyHostile.getHealth() < nearbyHostileHealth,
                "Expected cavenic axe smash to damage a nearby hostile mob"
            );
            helper.assertTrue(nearbyPassive.getHealth() == nearbyPassiveHealth, "Cavenic axe smash must not damage nearby passive mobs");
            helper.assertTrue(distantHostile.getHealth() == distantHostileHealth, "Cavenic axe smash must respect the bounded radius");
            helper.assertTrue(axe.getDamageValue() >= 1 && axe.getDamageValue() <= 3, "Cavenic axe durability cost must stay bounded");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void aquamarineToolsRegisterAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.AQUAMARINE_PICKAXE.get() != null, "Missing aquamarine pickaxe");
        helper.assertTrue(ModRegistries.AQUAMARINE_AXE.get() != null, "Missing aquamarine axe");
        helper.assertTrue(ModRegistries.AQUAMARINE_SHOVEL.get() != null, "Missing aquamarine shovel");

        assertRegistryId(helper, ModRegistries.AQUAMARINE_PICKAXE.get(), "cavernreborn:aquamarine_pickaxe");
        assertRegistryId(helper, ModRegistries.AQUAMARINE_AXE.get(), "cavernreborn:aquamarine_axe");
        assertRegistryId(helper, ModRegistries.AQUAMARINE_SHOVEL.get(), "cavernreborn:aquamarine_shovel");

        helper.assertTrue(!new ItemStack(ModRegistries.AQUAMARINE_PICKAXE.get()).isEmpty(), "Expected aquamarine pickaxe stack to be constructible");
        helper.assertTrue(!new ItemStack(ModRegistries.AQUAMARINE_AXE.get()).isEmpty(), "Expected aquamarine axe stack to be constructible");
        helper.assertTrue(!new ItemStack(ModRegistries.AQUAMARINE_SHOVEL.get()).isEmpty(), "Expected aquamarine shovel stack to be constructible");
        helper.assertTrue(new ItemStack(ModRegistries.AQUAMARINE_PICKAXE.get()).is(ModItemTags.AQUA_TOOLS), "Expected aquamarine pickaxe to resolve through aqua_tools");
        helper.assertTrue(new ItemStack(ModRegistries.AQUAMARINE_AXE.get()).is(ModItemTags.AQUA_TOOLS), "Expected aquamarine axe to resolve through aqua_tools");
        helper.assertTrue(new ItemStack(ModRegistries.AQUAMARINE_SHOVEL.get()).is(ModItemTags.AQUA_TOOLS), "Expected aquamarine shovel to resolve through aqua_tools");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void aquamarineToolsAreRepairableWithAquamarine(GameTestHelper helper) {
        ItemStack repairStack = new ItemStack(ModRegistries.AQUAMARINE.get());
        ItemStack wrongRepairStack = new ItemStack(Items.STICK);
        List<ItemStack> tools = List.of(
            new ItemStack(ModRegistries.AQUAMARINE_PICKAXE.get()),
            new ItemStack(ModRegistries.AQUAMARINE_AXE.get()),
            new ItemStack(ModRegistries.AQUAMARINE_SHOVEL.get())
        );

        for (ItemStack tool : tools) {
            helper.assertTrue(tool.getItem().isValidRepairItem(tool, repairStack), "Expected aquamarine repair support for " + itemId(tool));
            helper.assertFalse(tool.getItem().isValidRepairItem(tool, wrongRepairStack), "Stick must not repair " + itemId(tool));
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void aquamarineToolsExposeDurabilityAndRuntimeIds(GameTestHelper helper) {
        List<ItemStack> tools = List.of(aquamarinePickaxe(), aquamarineAxe(), aquamarineShovel());

        for (ItemStack tool : tools) {
            helper.assertTrue(tool.isDamageableItem(), "Expected damageable aquamarine tool: " + itemId(tool));
            helper.assertTrue(tool.getMaxDamage() == ModToolTiers.AQUAMARINE.getUses(), "Unexpected max damage for " + itemId(tool));
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void aquamarineToolsSupportExpectedEnchantments(GameTestHelper helper) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        ItemStack pickaxe = aquamarinePickaxe();
        ItemStack axe = aquamarineAxe();
        ItemStack shovel = aquamarineShovel();

        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.EFFICIENCY), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.SILK_TOUCH), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.FORTUNE), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.PROTECTION), false);

        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.EFFICIENCY), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.SILK_TOUCH), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.FORTUNE), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.SHARPNESS), true);

        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.EFFICIENCY), true);
        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.SILK_TOUCH), true);
        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.PROTECTION), false);
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void aquamarineAquaToolPolicyRuntimeSmoke(GameTestHelper helper) {
        AquamarineAquaToolResult boosted = AquamarineAquaToolPolicy.evaluate(true, true, false, 2.0F);
        AquamarineAquaToolResult boostedWithAffinity = AquamarineAquaToolPolicy.evaluate(true, true, true, 2.0F);
        AquamarineAquaToolResult notSubmerged = AquamarineAquaToolPolicy.evaluate(true, false, false, 2.0F);

        helper.assertTrue(boosted.decision() == AquamarineAquaToolDecision.BOOSTED, "Expected submerged aquamarine tool to get a speed boost");
        helper.assertTrue(boosted.adjustedSpeed() == 20.0F, "Expected submerged aquamarine tool speed to be multiplied by 10");
        helper.assertTrue(boostedWithAffinity.decision() == AquamarineAquaToolDecision.BOOSTED, "Expected Aqua Affinity aquamarine tool path to stay boosted");
        helper.assertTrue(boostedWithAffinity.adjustedSpeed() == 10.0F, "Expected Aqua Affinity dampener to halve the aquamarine boost");
        helper.assertTrue(notSubmerged.decision() == AquamarineAquaToolDecision.NOT_SUBMERGED, "Expected dry aquamarine tool use to stay unchanged");
        helper.assertTrue(notSubmerged.adjustedSpeed() == 2.0F, "Expected dry aquamarine tool use to keep the incoming speed");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magniteToolsRegisterAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.MAGNITE_SWORD.get() != null, "Missing magnite sword");
        helper.assertTrue(ModRegistries.MAGNITE_PICKAXE.get() != null, "Missing magnite pickaxe");
        helper.assertTrue(ModRegistries.MAGNITE_AXE.get() != null, "Missing magnite axe");
        helper.assertTrue(ModRegistries.MAGNITE_SHOVEL.get() != null, "Missing magnite shovel");

        assertRegistryId(helper, ModRegistries.MAGNITE_SWORD.get(), "cavernreborn:magnite_sword");
        assertRegistryId(helper, ModRegistries.MAGNITE_PICKAXE.get(), "cavernreborn:magnite_pickaxe");
        assertRegistryId(helper, ModRegistries.MAGNITE_AXE.get(), "cavernreborn:magnite_axe");
        assertRegistryId(helper, ModRegistries.MAGNITE_SHOVEL.get(), "cavernreborn:magnite_shovel");

        helper.assertTrue(!magniteSword().isEmpty(), "Expected magnite sword stack to be constructible");
        helper.assertTrue(!magnitePickaxe().isEmpty(), "Expected magnite pickaxe stack to be constructible");
        helper.assertTrue(!magniteAxe().isEmpty(), "Expected magnite axe stack to be constructible");
        helper.assertTrue(!magniteShovel().isEmpty(), "Expected magnite shovel stack to be constructible");
        helper.assertTrue(magniteSword().is(ModItemTags.MAGNITE_TOOLS), "Expected magnite sword to resolve through magnite_tools");
        helper.assertTrue(magnitePickaxe().is(ModItemTags.MAGNITE_TOOLS), "Expected magnite pickaxe to resolve through magnite_tools");
        helper.assertTrue(magniteAxe().is(ModItemTags.MAGNITE_TOOLS), "Expected magnite axe to resolve through magnite_tools");
        helper.assertTrue(magniteShovel().is(ModItemTags.MAGNITE_TOOLS), "Expected magnite shovel to resolve through magnite_tools");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magniteToolsAreRepairableWithMagnite(GameTestHelper helper) {
        ItemStack repairStack = new ItemStack(ModRegistries.MAGNITE_INGOT.get());
        ItemStack wrongRepairStack = new ItemStack(Items.STICK);
        List<ItemStack> tools = List.of(magniteSword(), magnitePickaxe(), magniteAxe(), magniteShovel());

        for (ItemStack tool : tools) {
            helper.assertTrue(tool.getItem().isValidRepairItem(tool, repairStack), "Expected magnite repair support for " + itemId(tool));
            helper.assertFalse(tool.getItem().isValidRepairItem(tool, wrongRepairStack), "Stick must not repair " + itemId(tool));
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magniteToolsExposeBrittleDurabilityAndRuntimeIds(GameTestHelper helper) {
        List<ItemStack> tools = List.of(magniteSword(), magnitePickaxe(), magniteAxe(), magniteShovel());

        for (ItemStack tool : tools) {
            helper.assertTrue(tool.isDamageableItem(), "Expected damageable magnite tool: " + itemId(tool));
            helper.assertTrue(tool.getMaxDamage() == ModToolTiers.MAGNITE.getUses(), "Unexpected max damage for " + itemId(tool));
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magniteToolsSupportExpectedEnchantments(GameTestHelper helper) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        ItemStack sword = magniteSword();
        ItemStack pickaxe = magnitePickaxe();
        ItemStack axe = magniteAxe();
        ItemStack shovel = magniteShovel();

        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.EFFICIENCY), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.SILK_TOUCH), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.FORTUNE), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, pickaxe, enchantments.getOrThrow(Enchantments.PROTECTION), false);

        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.EFFICIENCY), true);
        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.SILK_TOUCH), true);
        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, shovel, enchantments.getOrThrow(Enchantments.PROTECTION), false);

        assertSupportsEnchantment(helper, sword, enchantments.getOrThrow(Enchantments.SHARPNESS), true);
        assertSupportsEnchantment(helper, sword, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, sword, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, sword, enchantments.getOrThrow(Enchantments.PROTECTION), false);

        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.EFFICIENCY), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.SILK_TOUCH), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.FORTUNE), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.SHARPNESS), true);
        assertSupportsEnchantment(helper, axe, enchantments.getOrThrow(Enchantments.PROTECTION), false);
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magniteArmorRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.MAGNITE_HELMET.get() != null, "Missing magnite helmet");
        helper.assertTrue(ModRegistries.MAGNITE_CHESTPLATE.get() != null, "Missing magnite chestplate");
        helper.assertTrue(ModRegistries.MAGNITE_LEGGINGS.get() != null, "Missing magnite leggings");
        helper.assertTrue(ModRegistries.MAGNITE_BOOTS.get() != null, "Missing magnite boots");

        assertRegistryId(helper, ModRegistries.MAGNITE_HELMET.get(), "cavernreborn:magnite_helmet");
        assertRegistryId(helper, ModRegistries.MAGNITE_CHESTPLATE.get(), "cavernreborn:magnite_chestplate");
        assertRegistryId(helper, ModRegistries.MAGNITE_LEGGINGS.get(), "cavernreborn:magnite_leggings");
        assertRegistryId(helper, ModRegistries.MAGNITE_BOOTS.get(), "cavernreborn:magnite_boots");

        helper.assertTrue(!new ItemStack(ModRegistries.MAGNITE_HELMET.get()).isEmpty(), "Expected magnite helmet stack to be constructible");
        helper.assertTrue(!new ItemStack(ModRegistries.MAGNITE_CHESTPLATE.get()).isEmpty(), "Expected magnite chestplate stack to be constructible");
        helper.assertTrue(!new ItemStack(ModRegistries.MAGNITE_LEGGINGS.get()).isEmpty(), "Expected magnite leggings stack to be constructible");
        helper.assertTrue(!new ItemStack(ModRegistries.MAGNITE_BOOTS.get()).isEmpty(), "Expected magnite boots stack to be constructible");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteToolsRegisterAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.HEXCITE_PICKAXE.get() != null, "Missing hexcite pickaxe");
        helper.assertTrue(ModRegistries.HEXCITE_AXE.get() != null, "Missing hexcite axe");
        helper.assertTrue(ModRegistries.HEXCITE_SHOVEL.get() != null, "Missing hexcite shovel");
        helper.assertTrue(ModRegistries.HEXCITE_HOE.get() != null, "Missing hexcite hoe");
        helper.assertTrue(ModRegistries.HEXCITE_SWORD.get() != null, "Missing hexcite sword");

        assertRegistryId(helper, ModRegistries.HEXCITE_PICKAXE.get(), "cavernreborn:hexcite_pickaxe");
        assertRegistryId(helper, ModRegistries.HEXCITE_AXE.get(), "cavernreborn:hexcite_axe");
        assertRegistryId(helper, ModRegistries.HEXCITE_SHOVEL.get(), "cavernreborn:hexcite_shovel");
        assertRegistryId(helper, ModRegistries.HEXCITE_HOE.get(), "cavernreborn:hexcite_hoe");
        assertRegistryId(helper, ModRegistries.HEXCITE_SWORD.get(), "cavernreborn:hexcite_sword");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteArmorRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.HEXCITE_HELMET.get() != null, "Missing hexcite helmet");
        helper.assertTrue(ModRegistries.HEXCITE_CHESTPLATE.get() != null, "Missing hexcite chestplate");
        helper.assertTrue(ModRegistries.HEXCITE_LEGGINGS.get() != null, "Missing hexcite leggings");
        helper.assertTrue(ModRegistries.HEXCITE_BOOTS.get() != null, "Missing hexcite boots");

        assertRegistryId(helper, ModRegistries.HEXCITE_HELMET.get(), "cavernreborn:hexcite_helmet");
        assertRegistryId(helper, ModRegistries.HEXCITE_CHESTPLATE.get(), "cavernreborn:hexcite_chestplate");
        assertRegistryId(helper, ModRegistries.HEXCITE_LEGGINGS.get(), "cavernreborn:hexcite_leggings");
        assertRegistryId(helper, ModRegistries.HEXCITE_BOOTS.get(), "cavernreborn:hexcite_boots");

        helper.assertTrue(!new ItemStack(ModRegistries.HEXCITE_HELMET.get()).isEmpty(), "Expected hexcite helmet stack to be constructible");
        helper.assertTrue(!new ItemStack(ModRegistries.HEXCITE_CHESTPLATE.get()).isEmpty(), "Expected hexcite chestplate stack to be constructible");
        helper.assertTrue(!new ItemStack(ModRegistries.HEXCITE_LEGGINGS.get()).isEmpty(), "Expected hexcite leggings stack to be constructible");
        helper.assertTrue(!new ItemStack(ModRegistries.HEXCITE_BOOTS.get()).isEmpty(), "Expected hexcite boots stack to be constructible");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void minerOrbRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.MINER_ORB.get() != null, "Missing miner_orb item");
        assertRegistryId(helper, ModRegistries.MINER_ORB.get(), "cavernreborn:miner_orb");

        ItemStack stack = new ItemStack(ModRegistries.MINER_ORB.get());
        helper.assertTrue(!stack.isEmpty(), "Expected miner_orb stack to be constructible");
        helper.assertTrue(stack.is(ModItemTags.MINING_BONUS_ORBS), "miner_orb must resolve through the mining bonus orb tag");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassRegistersAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.ORE_COMPASS.get() != null, "Missing ore_compass item");
        assertRegistryId(helper, ModRegistries.ORE_COMPASS.get(), "cavernreborn:ore_compass");

        ItemStack stack = new ItemStack(ModRegistries.ORE_COMPASS.get());
        helper.assertTrue(!stack.isEmpty(), "Expected ore_compass stack to be constructible");
        helper.assertTrue(stack.getMaxStackSize() == 1, "Ore Compass must stay non-stackable in this MVP");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassStoredTargetRoundTripsAtRuntime(GameTestHelper helper) {
        ItemStack stack = new ItemStack(ModRegistries.ORE_COMPASS.get());
        BlockPos pos = new BlockPos(9, 33, -12);

        OreCompassStateAccess.writeTarget(stack, CavernDimensions.CAVERN_DIMENSION_ID, "cavernreborn:hexcite_ore", pos);
        Optional<StoredOreCompassTarget> target = OreCompassStateAccess.readTarget(stack);

        helper.assertTrue(target.isPresent(), "Expected ore_compass stack to store a target");
        helper.assertTrue(target.get().dimensionId().equals(CavernDimensions.CAVERN_DIMENSION_ID), "Expected stored dimension id to round-trip");
        helper.assertTrue(target.get().blockId().equals("cavernreborn:hexcite_ore"), "Expected stored block id to round-trip");
        helper.assertTrue(target.get().pos().equals(pos), "Expected stored target position to round-trip");

        OreCompassStateAccess.clear(stack);
        helper.assertTrue(OreCompassStateAccess.readTarget(stack).isEmpty(), "Expected clear() to remove the stored target");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassTrackingPolicyRuntimeSmoke(GameTestHelper helper) {
        OreCompassTrackingResult tracking = OreCompassTrackingPolicy.evaluate(
            true,
            true,
            CavernDimensions.CAVERN_DIMENSION_ID,
            CavernDimensions.CAVERN_DIMENSION_ID,
            4,
            -6,
            -4,
            true
        );
        OreCompassTrackingResult wrongDimension = OreCompassTrackingPolicy.evaluate(
            true,
            true,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            CavernDimensions.CAVERN_DIMENSION_ID,
            4,
            -6,
            -4,
            true
        );
        OreCompassTrackingResult outOfRange = OreCompassTrackingPolicy.evaluate(
            true,
            true,
            CavernDimensions.CAVERN_DIMENSION_ID,
            CavernDimensions.CAVERN_DIMENSION_ID,
            0,
            0,
            -(OreCompassTrackingPolicy.TRACKING_RADIUS + 1),
            true
        );
        OreCompassTrackingResult blockMismatch = OreCompassTrackingPolicy.evaluate(
            true,
            true,
            CavernDimensions.CAVERN_DIMENSION_ID,
            CavernDimensions.CAVERN_DIMENSION_ID,
            4,
            -6,
            -4,
            false
        );

        helper.assertTrue(tracking.decision() == OreCompassTrackingDecision.TRACKING, "Expected valid stored target to keep tracking");
        helper.assertTrue(tracking.horizontalDistance() == 6, "Expected tracking result to keep the rounded horizontal distance");
        helper.assertTrue(tracking.verticalOffset() == -6, "Expected tracking result to keep the vertical offset");
        helper.assertTrue(tracking.direction() == OreCompassDirection.NORTH_EAST, "Expected tracking direction to match dx/dz");
        helper.assertTrue(tracking.angleFrame() == 4, "Expected north-east offset to map to frame 4");
        helper.assertTrue(wrongDimension.decision() == OreCompassTrackingDecision.WRONG_DIMENSION, "Expected wrong dimension to invalidate tracking");
        helper.assertTrue(outOfRange.decision() == OreCompassTrackingDecision.OUT_OF_RANGE, "Expected distant target to invalidate tracking");
        helper.assertTrue(blockMismatch.decision() == OreCompassTrackingDecision.BLOCK_MISMATCH, "Expected block mismatch to invalidate tracking");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassTargetsResolveAtRuntime(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.HEXCITE_ORE.get().defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "hexcite ore must be a compass target");
        helper.assertTrue(ModRegistries.RANDOMITE_ORE.get().defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "randomite ore must be a compass target");
        helper.assertTrue(ModRegistries.AQUAMARINE_ORE.get().defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "aquamarine ore must be a compass target");
        helper.assertTrue(ModRegistries.MAGNITE_ORE.get().defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "magnite ore must be a compass target");
        helper.assertTrue(Blocks.DIAMOND_ORE.defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "diamond ore must be a compass target");
        helper.assertFalse(ModRegistries.FISSURED_STONE.get().defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "fissured stone must stay outside Ore Compass targets");
        helper.assertFalse(ModRegistries.HEXCITE_BLOCK.get().defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "storage blocks must stay outside Ore Compass targets");
        helper.assertFalse(ModRegistries.CAVERN_PORTAL_BLOCK.get().defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "cavern portal must stay outside Ore Compass targets");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void miningAssistPolicyRuntimeIdsResolve(GameTestHelper helper) {
        helper.assertTrue(ModRegistries.HEXCITE_PICKAXE.get() != null, "Missing hexcite pickaxe");
        helper.assertTrue(ModRegistries.HEXCITE_ORE.get().defaultBlockState().is(ModBlockTags.MINING_ASSIST_TARGETS), "hexcite ore must be a mining assist target");
        helper.assertTrue(ModRegistries.RANDOMITE_ORE.get().defaultBlockState().is(ModBlockTags.MINING_ASSIST_TARGETS), "randomite ore must be a mining assist target");
        helper.assertTrue(Blocks.COAL_ORE.defaultBlockState().is(ModBlockTags.MINING_ASSIST_TARGETS), "coal ore must be a mining assist target");
        helper.assertFalse(ModRegistries.FISSURED_STONE.get().defaultBlockState().is(ModBlockTags.MINING_ASSIST_TARGETS), "fissured stone must stay outside mining assist targets");
        helper.assertTrue(MiningAssistPolicy.MAX_EXTRA_BLOCKS == 6, "Mining Assist cap must stay at six extra blocks");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteOreNormalLootDropsHexcite(GameTestHelper helper) {
        helper.killAllEntities();

        List<ItemStack> drops = dropsForBlock(helper, ModRegistries.HEXCITE_ORE.get(), normalPickaxe(helper.getLevel()));

        helper.assertTrue(!drops.isEmpty(), "Expected hexcite ore to produce drops for a normal pickaxe");
        assertContainsItem(helper, drops, "cavernreborn:hexcite");
        assertDoesNotContainItem(helper, drops, "cavernreborn:hexcite_ore");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteOreSilkTouchLootDropsOreBlock(GameTestHelper helper) {
        helper.killAllEntities();

        List<ItemStack> drops = dropsForBlock(helper, ModRegistries.HEXCITE_ORE.get(), silkTouchPickaxe(helper.getLevel()));

        helper.assertTrue(!drops.isEmpty(), "Expected hexcite ore to produce drops for a Silk Touch pickaxe");
        assertContainsItem(helper, drops, "cavernreborn:hexcite_ore");
        assertDoesNotContainItem(helper, drops, "cavernreborn:hexcite");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexcitePickaxeCanMineSpecialCavernOres(GameTestHelper helper) {
        helper.killAllEntities();

        List<ItemStack> hexciteDrops = dropsForBlock(helper, ModRegistries.HEXCITE_ORE.get(), hexcitePickaxe());
        helper.assertTrue(!hexciteDrops.isEmpty(), "Expected hexcite pickaxe to mine hexcite ore");
        assertContainsItem(helper, hexciteDrops, "cavernreborn:hexcite");
        assertDoesNotContainItem(helper, hexciteDrops, "cavernreborn:hexcite_ore");

        List<ItemStack> randomiteDrops = dropsForBlock(helper, ModRegistries.RANDOMITE_ORE.get(), hexcitePickaxe());
        helper.assertTrue(!randomiteDrops.isEmpty(), "Expected hexcite pickaxe to mine randomite ore");
        for (ItemStack stack : randomiteDrops) {
            String itemId = itemId(stack);
            helper.assertTrue(ALLOWED_RANDOMITE_DROPS.contains(itemId), "Unexpected randomite drop for hexcite pickaxe: " + itemId);
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magnitePickaxeCanMineSpecialCavernOres(GameTestHelper helper) {
        helper.killAllEntities();

        List<ItemStack> hexciteDrops = dropsForBlock(helper, ModRegistries.HEXCITE_ORE.get(), magnitePickaxe());
        helper.assertTrue(!hexciteDrops.isEmpty(), "Expected magnite pickaxe to mine hexcite ore");
        assertContainsItem(helper, hexciteDrops, "cavernreborn:hexcite");

        List<ItemStack> randomiteDrops = dropsForBlock(helper, ModRegistries.RANDOMITE_ORE.get(), magnitePickaxe());
        helper.assertTrue(!randomiteDrops.isEmpty(), "Expected magnite pickaxe to mine randomite ore");
        for (ItemStack stack : randomiteDrops) {
            String itemId = itemId(stack);
            helper.assertTrue(ALLOWED_RANDOMITE_DROPS.contains(itemId), "Unexpected randomite drop for magnite pickaxe: " + itemId);
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexcitePickaxeSilkTouchStillTriggersOreDrop(GameTestHelper helper) {
        helper.killAllEntities();

        List<ItemStack> drops = dropsForBlock(helper, ModRegistries.HEXCITE_ORE.get(), silkTouchHexcitePickaxe(helper.getLevel()));

        helper.assertTrue(!drops.isEmpty(), "Expected Silk Touch hexcite pickaxe to produce drops");
        assertContainsItem(helper, drops, "cavernreborn:hexcite_ore");
        assertDoesNotContainItem(helper, drops, "cavernreborn:hexcite");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexcitePickaxeSupportsMiningEnchantments(GameTestHelper helper) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        ItemStack tool = hexcitePickaxe();

        assertSupportsEnchantment(helper, tool, enchantments.getOrThrow(Enchantments.EFFICIENCY), true);
        assertSupportsEnchantment(helper, tool, enchantments.getOrThrow(Enchantments.SILK_TOUCH), true);
        assertSupportsEnchantment(helper, tool, enchantments.getOrThrow(Enchantments.FORTUNE), true);
        assertSupportsEnchantment(helper, tool, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, tool, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, tool, enchantments.getOrThrow(Enchantments.SHARPNESS), false);
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteToolsExposeToolComponents(GameTestHelper helper) {
        List<ItemStack> tools = List.of(
            new ItemStack(ModRegistries.HEXCITE_PICKAXE.get()),
            new ItemStack(ModRegistries.HEXCITE_AXE.get()),
            new ItemStack(ModRegistries.HEXCITE_SHOVEL.get()),
            new ItemStack(ModRegistries.HEXCITE_HOE.get()),
            new ItemStack(ModRegistries.HEXCITE_SWORD.get())
        );

        for (ItemStack tool : tools) {
            helper.assertTrue(tool.isDamageableItem(), "Expected damageable hexcite tool: " + itemId(tool));
            helper.assertTrue(tool.getMaxDamage() == ModToolTiers.HEXCITE.getUses(), "Unexpected max damage for " + itemId(tool));
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteToolsAreRepairableWithHexcite(GameTestHelper helper) {
        ItemStack repairStack = new ItemStack(ModRegistries.HEXCITE.get());
        ItemStack wrongRepairStack = new ItemStack(Items.STICK);
        List<ItemStack> tools = List.of(
            new ItemStack(ModRegistries.HEXCITE_PICKAXE.get()),
            new ItemStack(ModRegistries.HEXCITE_AXE.get()),
            new ItemStack(ModRegistries.HEXCITE_SHOVEL.get()),
            new ItemStack(ModRegistries.HEXCITE_HOE.get()),
            new ItemStack(ModRegistries.HEXCITE_SWORD.get())
        );

        for (ItemStack tool : tools) {
            helper.assertTrue(tool.getItem().isValidRepairItem(tool, repairStack), "Expected hexcite repair support for " + itemId(tool));
            helper.assertFalse(tool.getItem().isValidRepairItem(tool, wrongRepairStack), "Stick must not repair " + itemId(tool));
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteArmorIsRepairableWithHexcite(GameTestHelper helper) {
        ItemStack repairStack = new ItemStack(ModRegistries.HEXCITE.get());
        ItemStack wrongRepairStack = new ItemStack(Items.STICK);
        List<ItemStack> armor = List.of(
            new ItemStack(ModRegistries.HEXCITE_HELMET.get()),
            new ItemStack(ModRegistries.HEXCITE_CHESTPLATE.get()),
            new ItemStack(ModRegistries.HEXCITE_LEGGINGS.get()),
            new ItemStack(ModRegistries.HEXCITE_BOOTS.get())
        );

        for (ItemStack stack : armor) {
            helper.assertTrue(stack.getItem().isValidRepairItem(stack, repairStack), "Expected hexcite repair support for " + itemId(stack));
            helper.assertFalse(stack.getItem().isValidRepairItem(stack, wrongRepairStack), "Stick must not repair " + itemId(stack));
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magniteArmorIsRepairableWithMagnite(GameTestHelper helper) {
        ItemStack repairStack = new ItemStack(ModRegistries.MAGNITE_INGOT.get());
        ItemStack wrongRepairStack = new ItemStack(Items.STICK);
        List<ItemStack> armor = List.of(
            new ItemStack(ModRegistries.MAGNITE_HELMET.get()),
            new ItemStack(ModRegistries.MAGNITE_CHESTPLATE.get()),
            new ItemStack(ModRegistries.MAGNITE_LEGGINGS.get()),
            new ItemStack(ModRegistries.MAGNITE_BOOTS.get())
        );

        for (ItemStack stack : armor) {
            helper.assertTrue(stack.getItem().isValidRepairItem(stack, repairStack), "Expected magnite repair support for " + itemId(stack));
            helper.assertFalse(stack.getItem().isValidRepairItem(stack, wrongRepairStack), "Stick must not repair " + itemId(stack));
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteArmorExposesDurabilityAndEquippableComponents(GameTestHelper helper) {
        assertArmorRuntime(helper, new ItemStack(ModRegistries.HEXCITE_HELMET.get()), ArmorItem.Type.HELMET, EquipmentSlot.HEAD, ModArmorMaterials.HEXCITE_DURABILITY_MULTIPLIER);
        assertArmorRuntime(helper, new ItemStack(ModRegistries.HEXCITE_CHESTPLATE.get()), ArmorItem.Type.CHESTPLATE, EquipmentSlot.CHEST, ModArmorMaterials.HEXCITE_DURABILITY_MULTIPLIER);
        assertArmorRuntime(helper, new ItemStack(ModRegistries.HEXCITE_LEGGINGS.get()), ArmorItem.Type.LEGGINGS, EquipmentSlot.LEGS, ModArmorMaterials.HEXCITE_DURABILITY_MULTIPLIER);
        assertArmorRuntime(helper, new ItemStack(ModRegistries.HEXCITE_BOOTS.get()), ArmorItem.Type.BOOTS, EquipmentSlot.FEET, ModArmorMaterials.HEXCITE_DURABILITY_MULTIPLIER);
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magniteArmorExposesDurabilityAndEquippableComponents(GameTestHelper helper) {
        assertArmorRuntime(helper, new ItemStack(ModRegistries.MAGNITE_HELMET.get()), ArmorItem.Type.HELMET, EquipmentSlot.HEAD, ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER);
        assertArmorRuntime(helper, new ItemStack(ModRegistries.MAGNITE_CHESTPLATE.get()), ArmorItem.Type.CHESTPLATE, EquipmentSlot.CHEST, ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER);
        assertArmorRuntime(helper, new ItemStack(ModRegistries.MAGNITE_LEGGINGS.get()), ArmorItem.Type.LEGGINGS, EquipmentSlot.LEGS, ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER);
        assertArmorRuntime(helper, new ItemStack(ModRegistries.MAGNITE_BOOTS.get()), ArmorItem.Type.BOOTS, EquipmentSlot.FEET, ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER);
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteArmorSupportsExpectedEnchantments(GameTestHelper helper) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        ItemStack helmet = new ItemStack(ModRegistries.HEXCITE_HELMET.get());
        ItemStack chestplate = new ItemStack(ModRegistries.HEXCITE_CHESTPLATE.get());
        ItemStack leggings = new ItemStack(ModRegistries.HEXCITE_LEGGINGS.get());
        ItemStack boots = new ItemStack(ModRegistries.HEXCITE_BOOTS.get());

        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.PROTECTION), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.RESPIRATION), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.AQUA_AFFINITY), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.SHARPNESS), false);

        assertSupportsEnchantment(helper, chestplate, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, chestplate, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, chestplate, enchantments.getOrThrow(Enchantments.PROTECTION), true);
        assertSupportsEnchantment(helper, chestplate, enchantments.getOrThrow(Enchantments.SHARPNESS), false);

        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.PROTECTION), true);
        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.SWIFT_SNEAK), true);
        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.SHARPNESS), false);

        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.PROTECTION), true);
        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.FEATHER_FALLING), true);
        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.SHARPNESS), false);
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magniteArmorSupportsExpectedEnchantments(GameTestHelper helper) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        ItemStack helmet = new ItemStack(ModRegistries.MAGNITE_HELMET.get());
        ItemStack chestplate = new ItemStack(ModRegistries.MAGNITE_CHESTPLATE.get());
        ItemStack leggings = new ItemStack(ModRegistries.MAGNITE_LEGGINGS.get());
        ItemStack boots = new ItemStack(ModRegistries.MAGNITE_BOOTS.get());

        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.PROTECTION), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.RESPIRATION), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.AQUA_AFFINITY), true);
        assertSupportsEnchantment(helper, helmet, enchantments.getOrThrow(Enchantments.SHARPNESS), false);

        assertSupportsEnchantment(helper, chestplate, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, chestplate, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, chestplate, enchantments.getOrThrow(Enchantments.PROTECTION), true);
        assertSupportsEnchantment(helper, chestplate, enchantments.getOrThrow(Enchantments.SHARPNESS), false);

        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.PROTECTION), true);
        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.SWIFT_SNEAK), true);
        assertSupportsEnchantment(helper, leggings, enchantments.getOrThrow(Enchantments.SHARPNESS), false);

        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.UNBREAKING), true);
        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.MENDING), true);
        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.PROTECTION), true);
        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.FEATHER_FALLING), true);
        assertSupportsEnchantment(helper, boots, enchantments.getOrThrow(Enchantments.SHARPNESS), false);
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void hexciteArmorEquipmentAssetsResolveAtRuntime(GameTestHelper helper) {
        helper.assertTrue(
            ModArmorMaterials.HEXCITE.get().layers().getFirst().texture(false).toString().endsWith("textures/models/armor/hexcite_layer_1.png"),
            "Expected current humanoid armor runtime to resolve hexcite layer 1 texture"
        );
        helper.assertTrue(
            ModArmorMaterials.HEXCITE.get().layers().getFirst().texture(true).toString().endsWith("textures/models/armor/hexcite_layer_2.png"),
            "Expected current humanoid leggings runtime to resolve hexcite layer 2 texture"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magniteArmorEquipmentAssetsResolveAtRuntime(GameTestHelper helper) {
        helper.assertTrue(
            ModArmorMaterials.MAGNITE.get().layers().getFirst().texture(false).toString().endsWith("textures/models/armor/magnite_layer_1.png"),
            "Expected current humanoid armor runtime to resolve magnite layer 1 texture"
        );
        helper.assertTrue(
            ModArmorMaterials.MAGNITE.get().layers().getFirst().texture(true).toString().endsWith("textures/models/armor/magnite_layer_2.png"),
            "Expected current humanoid leggings runtime to resolve magnite layer 2 texture"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void randomiteOreLootProducesAllowedCuratedDrop(GameTestHelper helper) {
        helper.killAllEntities();

        for (int sample = 0; sample < 8; sample++) {
            List<ItemStack> drops = dropsForBlock(helper, ModRegistries.RANDOMITE_ORE.get(), normalPickaxe(helper.getLevel()));
            helper.assertTrue(!drops.isEmpty(), "Randomite ore returned no drops on sample " + sample);

            for (ItemStack stack : drops) {
                String itemId = itemId(stack);
                helper.assertTrue(ALLOWED_RANDOMITE_DROPS.contains(itemId), "Randomite ore produced unexpected drop: " + itemId);
                helper.assertTrue(stack.getCount() >= 1 && stack.getCount() <= 4, "Randomite ore produced out-of-bounds stack size: " + stack);
            }
        }

        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void randomiteRuntimeAllowedDropsIncludeMinerOrb(GameTestHelper helper) {
        helper.assertTrue(ALLOWED_RANDOMITE_DROPS.contains("cavernreborn:miner_orb"), "Randomite runtime allow-list must include miner_orb");

        List<ItemStack> drops = dropsForBlock(helper, ModRegistries.RANDOMITE_ORE.get(), normalPickaxe(helper.getLevel()));
        helper.assertTrue(!drops.isEmpty(), "Expected randomite ore to produce a curated runtime drop");
        for (ItemStack stack : drops) {
            helper.assertTrue(ALLOWED_RANDOMITE_DROPS.contains(itemId(stack)), "Unexpected runtime randomite drop: " + itemId(stack));
        }
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void fissuredStoneDropsNoNormalItem(GameTestHelper helper) {
        helper.killAllEntities();

        List<ItemStack> drops = dropsForBlock(helper, ModRegistries.FISSURED_STONE.get(), normalPickaxe(helper.getLevel()));

        helper.assertTrue(drops.isEmpty(), "Fissured stone should not have a normal item drop");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void fissuredStoneSurvivalBreakCreatesBoundedEffect(GameTestHelper helper) {
        helper.killAllEntities();
        helper.setBlock(PRIMARY_BLOCK_POS, ModRegistries.FISSURED_STONE.get());

        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        destroyBlockWithPlayer(helper, PRIMARY_BLOCK_POS, player, normalPickaxe(helper.getLevel()));

        helper.runAfterDelay(5, () -> {
            helper.assertEntityPresent(EntityType.AREA_EFFECT_CLOUD, PRIMARY_BLOCK_POS, 3.0D);
            helper.assertItemEntityNotPresent(ModRegistries.FISSURED_STONE_ITEM.get(), PRIMARY_BLOCK_POS, 3.0D);
            helper.assertBlockNotPresent(ModRegistries.FISSURED_STONE.get(), PRIMARY_BLOCK_POS);
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void fissuredStoneCreativeBreakDoesNotTriggerEffect(GameTestHelper helper) {
        helper.killAllEntities();
        helper.setBlock(PRIMARY_BLOCK_POS, ModRegistries.FISSURED_STONE.get());

        Player player = helper.makeMockPlayer(GameType.CREATIVE);
        destroyBlockWithPlayer(helper, PRIMARY_BLOCK_POS, player, normalPickaxe(helper.getLevel()));

        helper.runAfterDelay(5, () -> {
            helper.assertEntityNotPresent(EntityType.AREA_EFFECT_CLOUD);
            helper.assertItemEntityNotPresent(ModRegistries.FISSURED_STONE_ITEM.get());
            helper.assertFalse(player.hasEffect(net.minecraft.world.effect.MobEffects.NIGHT_VISION), "Creative break must not apply night vision");
            helper.assertFalse(player.hasEffect(net.minecraft.world.effect.MobEffects.REGENERATION), "Creative break must not apply regeneration");
            helper.assertFalse(player.hasEffect(net.minecraft.world.effect.MobEffects.ABSORPTION), "Creative break must not apply absorption");
            helper.assertFalse(player.hasEffect(net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE), "Creative break must not apply resistance");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void fissuredStoneBreakIsNonDestructive(GameTestHelper helper) {
        helper.killAllEntities();
        helper.setBlock(PRIMARY_BLOCK_POS, ModRegistries.FISSURED_STONE.get());

        List<BlockPos> surroundingPositions = List.of(
            new BlockPos(0, 1, 0),
            new BlockPos(0, 1, 1),
            new BlockPos(0, 1, 2),
            new BlockPos(1, 1, 0),
            new BlockPos(1, 1, 2),
            new BlockPos(2, 1, 0),
            new BlockPos(2, 1, 1),
            new BlockPos(2, 1, 2)
        );
        surroundingPositions.forEach(pos -> helper.setBlock(pos, Blocks.STONE));

        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        destroyBlockWithPlayer(helper, PRIMARY_BLOCK_POS, player, normalPickaxe(helper.getLevel()));

        helper.runAfterDelay(5, () -> {
            assertNoUnexpectedTerrainDamage(helper, surroundingPositions);
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void customCavernOreProgressionPolicyStillMatchesRuntimeIds(GameTestHelper helper) {
        assertProgressionEntry(helper, ModRegistries.MAGNITE_ORE.get(), 1);
        assertProgressionEntry(helper, ModRegistries.AQUAMARINE_ORE.get(), 2);
        assertProgressionEntry(helper, ModRegistries.RANDOMITE_ORE.get(), 2);
        assertProgressionEntry(helper, ModRegistries.FISSURED_STONE.get(), 3);
        assertProgressionEntry(helper, ModRegistries.HEXCITE_ORE.get(), 4);

        ResourceKey<Level> cavernLevelKey = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(CavernDimensions.CAVERN_DIMENSION_ID));
        helper.assertTrue(
            cavernLevelKey.location().toString().equals(CavernDimensions.CAVERN_DIMENSION_ID),
            "CAVERN dimension key must match the configured runtime id"
        );

        ServerLevel cavernLevel = helper.getLevel().getServer().getLevel(cavernLevelKey);
        if (cavernLevel != null) {
            helper.assertTrue(
                cavernLevel.dimension().location().toString().equals(CavernDimensions.CAVERN_DIMENSION_ID),
                "Resolved CAVERN level must keep the configured runtime id"
            );
        }
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void minerOrbBonusPolicyRuntimeSmoke(GameTestHelper helper) {
        String blockId = "cavernreborn:hexcite_ore";
        int baseScore = CavernProgressionPolicy.scoreForBlock(blockId);

        MinerOrbBonusResult triggered = MinerOrbBonusPolicy.evaluate(
            CavernDimensions.CAVERN_DIMENSION_ID,
            blockId,
            baseScore,
            true,
            true,
            false,
            false,
            0
        );
        MinerOrbBonusResult missed = MinerOrbBonusPolicy.evaluate(
            CavernDimensions.CAVERN_DIMENSION_ID,
            blockId,
            baseScore,
            true,
            true,
            false,
            false,
            1
        );

        helper.assertTrue(triggered.triggered(), "Expected miner_orb roll 0 to trigger");
        helper.assertTrue(triggered.bonusScore() == 2, "Expected hexcite ore to grant +2 orb score");
        helper.assertTrue(missed.decision() == MinerOrbBonusDecision.ROLL_MISSED, "Expected non-zero roll to miss");
        helper.assertTrue(missed.bonusScore() == 0, "Roll miss must not grant a bonus");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void minerOrbProgressionBonusDoesNotIncreaseCountedBlocks(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        clearProgression(player);

        String blockId = "cavernreborn:hexcite_ore";
        int bonusScore = MinerOrbBonusPolicy.evaluate(
            CavernDimensions.CAVERN_DIMENSION_ID,
            blockId,
            CavernProgressionPolicy.scoreForBlock(blockId),
            true,
            true,
            false,
            false,
            0
        ).bonusScore();
        CavernProgressionUpdateResult update = CavernReborn.cavernStateBootstrap().cavernProgressionService().recordMiningEvent(
            player.getUUID(),
            CavernDimensions.CAVERN_DIMENSION_ID,
            blockId,
            bonusScore
        );

        helper.assertTrue(update.counted(), "Expected the origin block to count toward progression");
        helper.assertTrue(update.currentSnapshot().countedBlocks() == 1, "Miner's Orb must not add extra counted blocks");
        helper.assertTrue(update.currentSnapshot().progressionScore() == 6, "Expected base + orb bonus score");
        helper.assertTrue(update.currentSnapshot().minedBlocksById().get(blockId) == 1, "Per-block count must stay at one");
        clearProgression(player);
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void minerOrbDoesNotApplyToMiningAssistSuppressedBreak(GameTestHelper helper) {
        MinerOrbBonusResult result = MinerOrbBonusPolicy.evaluate(
            CavernDimensions.CAVERN_DIMENSION_ID,
            "cavernreborn:hexcite_ore",
            CavernProgressionPolicy.scoreForBlock("cavernreborn:hexcite_ore"),
            true,
            true,
            false,
            true,
            0
        );

        helper.assertTrue(result.decision() == MinerOrbBonusDecision.MINING_ASSIST_SUPPRESSED, "Suppressed Mining Assist breaks must skip miner_orb bonus");
        helper.assertTrue(result.bonusScore() == 0, "Suppressed Mining Assist breaks must not grant extra score");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassScannerFindsNearestTarget(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = ORE_COMPASS_NEAREST_ANCHOR;
        BlockPos nearerHexcite = origin.east(4);
        BlockPos fartherDiamond = origin.east(9).north(2);
        BlockPos unsupportedStone = origin.north(1);

        resetMiningArea(level, origin, 10.0D);
        level.setBlock(nearerHexcite, ModRegistries.HEXCITE_ORE.get().defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(fartherDiamond, Blocks.DIAMOND_ORE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(unsupportedStone, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);

        Optional<OreCompassTarget> target = oreCompassScanner().findNearestTarget(
            level,
            origin,
            OreCompassScanPolicy.HORIZONTAL_RADIUS,
            OreCompassScanPolicy.VERTICAL_RADIUS
        );

        helper.assertTrue(target.isPresent(), "Expected Ore Compass scanner to find a target");
        helper.assertTrue(target.get().blockId().equals("cavernreborn:hexcite_ore"), "Expected nearest target to be hexcite ore");
        helper.assertTrue(target.get().pos().equals(nearerHexcite), "Expected scanner to choose the nearest target position");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassScannerIgnoresFissuredStoneAndStorageBlocks(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = ORE_COMPASS_FILTERED_ANCHOR;
        BlockPos fissured = origin.east(2);
        BlockPos storage = origin.west(2);
        BlockPos targetPos = origin.south(5);

        resetMiningArea(level, origin, 10.0D);
        level.setBlock(fissured, ModRegistries.FISSURED_STONE.get().defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(storage, ModRegistries.HEXCITE_BLOCK.get().defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(targetPos, ModRegistries.RANDOMITE_ORE.get().defaultBlockState(), Block.UPDATE_ALL);

        Optional<OreCompassTarget> target = oreCompassScanner().findNearestTarget(
            level,
            origin,
            OreCompassScanPolicy.HORIZONTAL_RADIUS,
            OreCompassScanPolicy.VERTICAL_RADIUS
        );

        helper.assertTrue(target.isPresent(), "Expected filtered Ore Compass scan to find a target");
        helper.assertTrue(target.get().blockId().equals("cavernreborn:randomite_ore"), "Expected scanner to ignore fissured stone and storage blocks");
        helper.assertTrue(target.get().pos().equals(targetPos), "Expected scanner to resolve the remaining valid ore target");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassScannerReportsNoTargetWhenOnlyUnsupportedBlocksExist(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = ORE_COMPASS_EMPTY_ANCHOR;

        resetMiningArea(level, origin, 10.0D);
        level.setBlock(origin.east(2), Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(origin.west(2), Blocks.COAL_ORE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(origin.north(2), Blocks.IRON_ORE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(origin.south(2), Blocks.COPPER_ORE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(origin.above(), ModRegistries.FISSURED_STONE.get().defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(origin.below(), ModRegistries.HEXCITE_BLOCK.get().defaultBlockState(), Block.UPDATE_ALL);

        helper.assertTrue(
            oreCompassScanner().findNearestTarget(level, origin, OreCompassScanPolicy.HORIZONTAL_RADIUS, OreCompassScanPolicy.VERTICAL_RADIUS).isEmpty(),
            "Expected no target when only unsupported blocks are nearby"
        );
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassNearestScanStoresFoundTarget(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = ORE_COMPASS_USE_ANCHOR;
        ItemStack stack = new ItemStack(ModRegistries.ORE_COMPASS.get());

        resetMiningArea(level, origin, 10.0D);
        level.setBlock(origin.east(3), ModRegistries.HEXCITE_ORE.get().defaultBlockState(), Block.UPDATE_ALL);

        Optional<OreCompassTarget> scan = oreCompassItem().performServerScan(level, origin, stack);
        Optional<StoredOreCompassTarget> storedTarget = OreCompassStateAccess.readTarget(stack);

        helper.assertTrue(scan.isPresent(), "Expected server scan to find a target");
        helper.assertTrue(storedTarget.isPresent(), "Expected found scan to persist the target on the stack");
        helper.assertTrue(storedTarget.get().dimensionId().equals(level.dimension().location().toString()), "Expected stored dimension id to match the scan level");
        helper.assertTrue(storedTarget.get().blockId().equals("cavernreborn:hexcite_ore"), "Expected stored target block id to match the resolved ore");
        helper.assertTrue(storedTarget.get().pos().equals(origin.east(3)), "Expected stored target position to match the resolved ore");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassNoTargetClearsStoredTarget(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos origin = ORE_COMPASS_STATE_ANCHOR;
        ItemStack stack = new ItemStack(ModRegistries.ORE_COMPASS.get());

        resetMiningArea(level, origin, 10.0D);
        OreCompassStateAccess.writeTarget(stack, CavernDimensions.CAVERN_DIMENSION_ID, "cavernreborn:hexcite_ore", origin.east(3));

        Optional<OreCompassTarget> scan = oreCompassItem().performServerScan(level, origin, stack);

        helper.assertTrue(scan.isEmpty(), "Expected empty scan in an unsupported area");
        helper.assertTrue(OreCompassStateAccess.readTarget(stack).isEmpty(), "Expected empty scan to clear the stored target");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassTrackingExcludesWrongDimension(GameTestHelper helper) {
        ItemStack stack = new ItemStack(ModRegistries.ORE_COMPASS.get());
        OreCompassStateAccess.writeTarget(stack, CavernDimensions.CAVERN_DIMENSION_ID, "cavernreborn:hexcite_ore", new BlockPos(3, 96, 0));

        OreCompassTrackingResult result = OreCompassTrackingPolicy.evaluate(
            OreCompassStateAccess.readTarget(stack).isPresent(),
            true,
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            CavernDimensions.CAVERN_DIMENSION_ID,
            3,
            0,
            0,
            true
        );

        helper.assertTrue(result.decision() == OreCompassTrackingDecision.WRONG_DIMENSION, "Expected wrong-dimension context to invalidate tracking");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassScannerAndTrackingStayBounded(GameTestHelper helper) {
        helper.assertTrue(OreCompassScanPolicy.HORIZONTAL_RADIUS == 32, "Scan radius must remain bounded at 32 horizontal blocks");
        helper.assertTrue(OreCompassScanPolicy.VERTICAL_RADIUS == 24, "Scan radius must remain bounded at 24 vertical blocks");
        helper.assertTrue(OreCompassTrackingPolicy.TRACKING_RADIUS == 50, "Tracking radius must remain bounded at 50 horizontal blocks");
        helper.assertTrue(OreCompassTrackingPolicy.ANGLE_FRAME_COUNT == 32, "Tracking model must remain a 32-frame compass");
        helper.assertFalse(Blocks.COAL_ORE.defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "Coal ore must remain outside Ore Compass targets");
        helper.assertFalse(ModRegistries.FISSURED_STONE.get().defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "Fissured stone must remain outside Ore Compass targets");
        helper.assertFalse(ModRegistries.HEXCITE_BLOCK.get().defaultBlockState().is(ModBlockTags.ORE_COMPASS_TARGETS), "Storage blocks must remain outside Ore Compass targets");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void oreCompassPolicyRuntimeSmoke(GameTestHelper helper) {
        OreCompassScanResult wrongDimension = OreCompassScanPolicy.evaluate(
            CavernDimensions.OVERWORLD_DIMENSION_ID,
            true,
            "cavernreborn:hexcite_ore",
            0,
            0,
            -4
        );
        OreCompassScanResult found = OreCompassScanPolicy.evaluate(
            CavernDimensions.CAVERN_DIMENSION_ID,
            true,
            "cavernreborn:hexcite_ore",
            0,
            -6,
            -5
        );

        helper.assertTrue(wrongDimension.decision() == OreCompassScanDecision.WRONG_DIMENSION, "Expected wrong dimension to skip the scan");
        helper.assertTrue(found.decision() == OreCompassScanDecision.FOUND, "Expected cavern target to be reported as found");
        helper.assertTrue(found.horizontalDistance() == 5, "Expected north target to report a five-block horizontal distance");
        helper.assertTrue(found.verticalOffset() == -6, "Expected vertical relation to stay negative for targets below");
        helper.assertTrue(found.direction() == OreCompassDirection.NORTH, "Expected dz<0 target to report north");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void specialOreWorldgenKeysResolveAtRuntime(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<ConfiguredFeature<?, ?>> configuredFeatures = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        Registry<PlacedFeature> placedFeatures = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);

        assertRegistryKeyPresent(helper, configuredFeatures, Registries.CONFIGURED_FEATURE, "cavernreborn:hexcite_ore");
        assertRegistryKeyPresent(helper, configuredFeatures, Registries.CONFIGURED_FEATURE, "cavernreborn:randomite_ore");
        assertRegistryKeyPresent(helper, configuredFeatures, Registries.CONFIGURED_FEATURE, "cavernreborn:fissured_stone");
        assertRegistryKeyPresent(helper, placedFeatures, Registries.PLACED_FEATURE, "cavernreborn:cavern_ore_hexcite_deep");
        assertRegistryKeyPresent(helper, placedFeatures, Registries.PLACED_FEATURE, "cavernreborn:cavern_ore_hexcite_upper_rare");
        assertRegistryKeyPresent(helper, placedFeatures, Registries.PLACED_FEATURE, "cavernreborn:cavern_ore_randomite");
        assertRegistryKeyPresent(helper, placedFeatures, Registries.PLACED_FEATURE, "cavernreborn:cavern_fissured_stone");
        helper.succeed();
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void miningAssistBreaksBoundedSameBlockVein(GameTestHelper helper) {
        ServerLevel cavernLevel = helper.getLevel();
        BlockPos origin = MINING_ASSIST_ENABLED_ANCHOR;
        List<BlockPos> vein = hexciteVein(origin);
        List<BlockPos> protectedStone = List.of(origin.north().north(), origin.south().south());
        BlockPos unrelatedRandomite = origin.east().east();

        resetMiningArea(cavernLevel, origin, 8.0D);
        placeBlockSet(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get());
        placeBlockSet(cavernLevel, protectedStone, Blocks.STONE);
        cavernLevel.setBlock(unrelatedRandomite, ModRegistries.RANDOMITE_ORE.get().defaultBlockState(), Block.UPDATE_ALL);

        Player player = makeMockPlayer(helper, cavernLevel, GameType.SURVIVAL, hexcitePickaxe(), origin);
        grantMiningAssistUnlock(helper, player);
        int assistedBlocks = applyMiningAssist(helper, cavernLevel, player, origin);
        CavernMiningAssistEvents.breakBlockWithTool(cavernLevel, player, origin, cavernLevel.getBlockState(origin), player.getMainHandItem(), true);

        helper.runAfterDelay(5, () -> {
            long remainingOreBlocks = countBlocks(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get());
            helper.assertTrue(assistedBlocks <= MiningAssistPolicy.MAX_EXTRA_BLOCKS, "Mining Assist must stay within the configured cap");
            helper.assertTrue(remainingOreBlocks >= 1, "Bounded Mining Assist must leave at least one hexcite ore beyond the cap, but remaining=" + remainingOreBlocks);
            helper.assertTrue(remainingOreBlocks == vein.size() - 1L - assistedBlocks, "Mining Assist removed an unexpected number of hexcite ore blocks; assisted=" + assistedBlocks + ", remaining=" + remainingOreBlocks);
            helper.assertTrue(cavernLevel.getBlockState(unrelatedRandomite).is(ModRegistries.RANDOMITE_ORE.get()), "Unrelated randomite ore must remain");
            for (BlockPos stonePos : protectedStone) {
                helper.assertTrue(cavernLevel.getBlockState(stonePos).is(Blocks.STONE), "Nearby stone must remain untouched at " + stonePos);
            }
            helper.assertTrue(itemIdsAround(cavernLevel, origin, 6.0D).contains("cavernreborn:hexcite"), "Expected hexcite drops from assisted vein");
            clearProgression(player);
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void miningAssistDoesNotRunWithoutUnlock(GameTestHelper helper) {
        ServerLevel cavernLevel = helper.getLevel();
        BlockPos origin = MINING_ASSIST_NO_UNLOCK_ANCHOR;
        List<BlockPos> vein = hexciteVein(origin);

        resetMiningArea(cavernLevel, origin, 8.0D);
        placeBlockSet(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get());

        Player player = makeMockPlayer(helper, cavernLevel, GameType.SURVIVAL, hexcitePickaxe(), origin);
        clearProgression(player);
        helper.assertTrue(applyMiningAssist(helper, cavernLevel, player, origin) == 0, "Without the unlock Mining Assist must not break extras");
        CavernMiningAssistEvents.breakBlockWithTool(cavernLevel, player, origin, cavernLevel.getBlockState(origin), player.getMainHandItem(), true);

        helper.runAfterDelay(5, () -> {
            helper.assertTrue(countBlocks(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get()) == 7, "Without unlock only the origin block should break");
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void miningAssistSneakingDisablesAssist(GameTestHelper helper) {
        ServerLevel cavernLevel = helper.getLevel();
        BlockPos origin = MINING_ASSIST_SNEAKING_ANCHOR;
        List<BlockPos> vein = hexciteVein(origin);

        resetMiningArea(cavernLevel, origin, 8.0D);
        placeBlockSet(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get());

        Player player = makeMockPlayer(helper, cavernLevel, GameType.SURVIVAL, hexcitePickaxe(), origin);
        grantMiningAssistUnlock(helper, player);
        player.setShiftKeyDown(true);
        helper.assertTrue(applyMiningAssist(helper, cavernLevel, player, origin) == 0, "Sneaking must disable Mining Assist");
        CavernMiningAssistEvents.breakBlockWithTool(cavernLevel, player, origin, cavernLevel.getBlockState(origin), player.getMainHandItem(), true);
        player.setShiftKeyDown(false);

        helper.runAfterDelay(5, () -> {
            helper.assertTrue(countBlocks(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get()) == 7, "Sneaking must disable assist for that break");
            clearProgression(player);
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void miningAssistExcludesFissuredStone(GameTestHelper helper) {
        ServerLevel cavernLevel = helper.getLevel();
        BlockPos origin = MINING_ASSIST_FISSURE_ANCHOR;
        List<BlockPos> fissuredCluster = List.of(origin, origin.above(), origin.north());

        resetMiningArea(cavernLevel, origin, 8.0D);
        placeBlockSet(cavernLevel, fissuredCluster, ModRegistries.FISSURED_STONE.get());

        Player player = makeMockPlayer(helper, cavernLevel, GameType.SURVIVAL, hexcitePickaxe(), origin);
        grantMiningAssistUnlock(helper, player);
        helper.assertTrue(applyMiningAssist(helper, cavernLevel, player, origin) == 0, "Fissured stone must stay outside Mining Assist targets");
        CavernMiningAssistEvents.breakBlockWithTool(cavernLevel, player, origin, cavernLevel.getBlockState(origin), player.getMainHandItem(), true);

        helper.runAfterDelay(5, () -> {
            helper.assertTrue(countBlocks(cavernLevel, fissuredCluster, ModRegistries.FISSURED_STONE.get()) == 2, "Fissured stone must not chain-break through Mining Assist");
            helper.assertTrue(countEntities(cavernLevel, EntityType.AREA_EFFECT_CLOUD, origin, 6.0D) <= 1, "Fissured stone exclusion must avoid extra effect spam");
            clearProgression(player);
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void miningAssistPreservesDurabilityBound(GameTestHelper helper) {
        ServerLevel cavernLevel = helper.getLevel();
        BlockPos origin = MINING_ASSIST_DURABILITY_ANCHOR;
        List<BlockPos> vein = hexciteVein(origin);
        ItemStack damagedPickaxe = hexcitePickaxe();
        damagedPickaxe.setDamageValue(damagedPickaxe.getMaxDamage() - 2);

        resetMiningArea(cavernLevel, origin, 8.0D);
        placeBlockSet(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get());

        Player player = makeMockPlayer(helper, cavernLevel, GameType.SURVIVAL, damagedPickaxe, origin);
        grantMiningAssistUnlock(helper, player);
        int assistedBlocks = applyMiningAssist(helper, cavernLevel, player, origin);
        CavernMiningAssistEvents.breakBlockWithTool(cavernLevel, player, origin, cavernLevel.getBlockState(origin), player.getMainHandItem(), true);

        helper.runAfterDelay(5, () -> {
            helper.assertTrue(assistedBlocks <= 1, "Near-broken tool must not break more extras than remaining durability allows");
            helper.assertTrue(countBlocks(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get()) >= 6, "Near-broken tool must stop Mining Assist early");
            ItemStack currentTool = player.getMainHandItem();
            helper.assertTrue(currentTool.isEmpty() || currentTool.getDamageValue() < currentTool.getMaxDamage(), "Tool durability must stay bounded");
            clearProgression(player);
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = TEMPLATE_NAMESPACE, template = EMPTY_TEMPLATE, timeoutTicks = DEFAULT_TIMEOUT_TICKS)
    public static void magnitePickaxeDoesNotTriggerMiningAssist(GameTestHelper helper) {
        ServerLevel cavernLevel = helper.getLevel();
        BlockPos origin = MAGNITE_NO_ASSIST_ANCHOR;
        List<BlockPos> vein = hexciteVein(origin);

        resetMiningArea(cavernLevel, origin, 8.0D);
        placeBlockSet(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get());

        Player player = makeMockPlayer(helper, cavernLevel, GameType.SURVIVAL, magnitePickaxe(), origin);
        grantMiningAssistUnlock(helper, player);
        helper.assertTrue(applyMiningAssist(helper, cavernLevel, player, origin) == 0, "magnite_pickaxe must not trigger Mining Assist");
        CavernMiningAssistEvents.breakBlockWithTool(cavernLevel, player, origin, cavernLevel.getBlockState(origin), player.getMainHandItem(), true);

        helper.runAfterDelay(5, () -> {
            helper.assertTrue(countBlocks(cavernLevel, vein, ModRegistries.HEXCITE_ORE.get()) == 7, "magnite_pickaxe must only break the origin block");
            clearProgression(player);
            helper.succeed();
        });
    }

    private static ItemStack normalPickaxe(ServerLevel level) {
        return new ItemStack(Items.IRON_PICKAXE);
    }

    private static ItemStack silkTouchPickaxe(ServerLevel level) {
        ItemStack tool = normalPickaxe(level);
        HolderLookup.RegistryLookup<Enchantment> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        tool.enchant(enchantments.getOrThrow(Enchantments.SILK_TOUCH), 1);
        return tool;
    }

    private static ItemStack aquamarinePickaxe() {
        return new ItemStack(ModRegistries.AQUAMARINE_PICKAXE.get());
    }

    private static ItemStack acresiaSeeds() {
        return new ItemStack(ModRegistries.ACRESIA_SEEDS.get());
    }

    private static ItemStack acresiaFruits() {
        return new ItemStack(ModRegistries.ACRESIA_FRUITS.get());
    }

    private static ItemStack cavenicShroomItem() {
        return new ItemStack(ModRegistries.CAVENIC_SHROOM_ITEM.get());
    }

    private static ItemStack cavenicOrb() {
        return new ItemStack(ModRegistries.CAVENIC_ORB.get());
    }

    private static ItemStack cavenicSword() {
        return new ItemStack(ModRegistries.CAVENIC_SWORD.get());
    }

    private static ItemStack cavenicAxe() {
        return new ItemStack(ModRegistries.CAVENIC_AXE.get());
    }

    private static ItemStack cavenicBow() {
        return new ItemStack(ModRegistries.CAVENIC_BOW.get());
    }

    private static ItemStack cavenicZombieSpawnEgg() {
        return new ItemStack(ModRegistries.CAVENIC_ZOMBIE_SPAWN_EGG.get());
    }

    private static ItemStack cavenicSkeletonSpawnEgg() {
        return new ItemStack(ModRegistries.CAVENIC_SKELETON_SPAWN_EGG.get());
    }

    private static ItemStack cavenicCreeperSpawnEgg() {
        return new ItemStack(ModRegistries.CAVENIC_CREEPER_SPAWN_EGG.get());
    }

    private static ItemStack cavenicSpiderSpawnEgg() {
        return new ItemStack(ModRegistries.CAVENIC_SPIDER_SPAWN_EGG.get());
    }

    private static ItemStack cavenicWitchSpawnEgg() {
        return new ItemStack(ModRegistries.CAVENIC_WITCH_SPAWN_EGG.get());
    }

    private static ItemStack cavenicBearSpawnEgg() {
        return new ItemStack(ModRegistries.CAVENIC_BEAR_SPAWN_EGG.get());
    }

    private static void positionTargetAroundMob(LivingEntity target, Mob mob, double xOffset) {
        target.teleportTo((ServerLevel)mob.level(), mob.getX() + xOffset, mob.getY(), mob.getZ(), EnumSet.noneOf(RelativeMovement.class), 0.0F, 0.0F);
    }

    private static DamageSource witchOwnedPotionDamageSource(ServerLevel level, Witch owner) {
        ThrownPotion potion = new ThrownPotion(level, owner);
        potion.setPos(owner.getX(), owner.getEyeY(), owner.getZ());
        return level.damageSources().indirectMagic(potion, owner);
    }

    private static PotionContents potionContents(ThrownPotion potion) {
        return potion.getItem().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    private static AbstractArrow createRuntimeArrow(ServerLevel level, Player player, ItemStack bow) {
        return ((ArrowItem) Items.ARROW).createArrow(level, new ItemStack(Items.ARROW), player, bow);
    }

    private static ReleasedArrowRecorder registerReleasedArrowRecorder() {
        ReleasedArrowRecorder recorder = new ReleasedArrowRecorder();
        NeoForge.EVENT_BUS.register(recorder);
        return recorder;
    }

    private static BowReleaseResult releaseBowThroughRealUse(GameTestHelper helper, Player player, int chargeTicks) {
        ServerLevel level = helper.getLevel();
        ItemStack bow = player.getMainHandItem();
        CavenicBowItem bowItem = (CavenicBowItem) bow.getItem();
        BlockPos origin = player.blockPosition();
        Set<UUID> beforeArrowIds = arrowsAround(level, origin, 16.0D).stream()
            .map(Entity::getUUID)
            .collect(java.util.stream.Collectors.toSet());
        RELEASED_ARROW_RECORDER.clearShooter(player.getId());
        int arrowCountBefore = countPlayerItem(player, Items.ARROW);
        int torchCountBefore = countPlayerItem(player, Items.TORCH);
        int bowDamageBefore = bow.getDamageValue();
        InteractionResultHolder<ItemStack> useResult = bowItem.use(level, player, InteractionHand.MAIN_HAND);
        boolean usingStarted = player.isUsingItem();

        if (usingStarted) {
            int timeLeft = bowItem.getUseDuration(bow, player) - chargeTicks;
            bowItem.releaseUsing(bow, level, player, timeLeft);
            player.stopUsingItem();
        }

        return new BowReleaseResult(
            level,
            origin,
            player.getId(),
            player.getUUID(),
            beforeArrowIds,
            useResult.getResult(),
            usingStarted,
            arrowCountBefore,
            countPlayerItem(player, Items.ARROW),
            torchCountBefore,
            countPlayerItem(player, Items.TORCH),
            bowDamageBefore,
            bow.getDamageValue(),
            bowItem.getMode(bow)
        );
    }

    private static AbstractArrow singleSpawnedArrow(GameTestHelper helper, BowReleaseResult result, String message) {
        List<AbstractArrow> spawnedArrows = newArrows(result);
        helper.assertTrue(
            spawnedArrows.size() == 1,
            message
                + " but got "
                + spawnedArrows.size()
                + " (useResult="
                + result.useResult()
                + ", usingStarted="
                + result.usingStarted()
                + ", arrows="
                + result.arrowCountBefore()
                + "->"
                + result.arrowCountAfter()
                + ", torches="
                + result.torchCountBefore()
                + "->"
                + result.torchCountAfter()
                + ", bowDamage="
                + result.bowDamageBefore()
                + "->"
                + result.bowDamageAfter()
                + ", modeAfter="
                + result.modeAfter()
                + ")"
        );
        return spawnedArrows.getFirst();
    }

    private static int newArrowCount(BowReleaseResult result) {
        return newArrows(result).size();
    }

    private static List<AbstractArrow> newArrows(BowReleaseResult result) {
        return RELEASED_ARROW_RECORDER.arrowsFor(result.shooterEntityId()).stream()
            .filter(AbstractArrow::isAlive)
            .filter(arrow -> arrow.level() == result.level())
            .toList();
    }

    private static List<AbstractArrow> arrowsAround(ServerLevel level, BlockPos center, double radius) {
        return level.getEntitiesOfClass(AbstractArrow.class, new AABB(center).inflate(radius));
    }

    private static ItemStack aquamarineAxe() {
        return new ItemStack(ModRegistries.AQUAMARINE_AXE.get());
    }

    private static ItemStack aquamarineShovel() {
        return new ItemStack(ModRegistries.AQUAMARINE_SHOVEL.get());
    }

    private static ItemStack magniteSword() {
        return new ItemStack(ModRegistries.MAGNITE_SWORD.get());
    }

    private static ItemStack magnitePickaxe() {
        return new ItemStack(ModRegistries.MAGNITE_PICKAXE.get());
    }

    private static ItemStack magniteAxe() {
        return new ItemStack(ModRegistries.MAGNITE_AXE.get());
    }

    private static ItemStack magniteShovel() {
        return new ItemStack(ModRegistries.MAGNITE_SHOVEL.get());
    }

    private static ItemStack hexcitePickaxe() {
        return new ItemStack(ModRegistries.HEXCITE_PICKAXE.get());
    }

    private static OreCompassScanner oreCompassScanner() {
        return new OreCompassScanner();
    }

    private static OreCompassItem oreCompassItem() {
        return (OreCompassItem) ModRegistries.ORE_COMPASS.get();
    }

    private static ItemStack silkTouchHexcitePickaxe(ServerLevel level) {
        ItemStack tool = hexcitePickaxe();
        HolderLookup.RegistryLookup<Enchantment> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        tool.enchant(enchantments.getOrThrow(Enchantments.SILK_TOUCH), 1);
        return tool;
    }

    private static BlockState matureAcresiaState() {
        return ModRegistries.ACRESIA.get().defaultBlockState().setValue(AcresiaCropBlock.AGE, AcresiaHarvestPolicy.MAX_AGE);
    }

    private static BlockHitResult hitResult(BlockPos pos) {
        return hitResult(pos, Direction.UP);
    }

    private static BlockHitResult hitResult(BlockPos pos, Direction face) {
        return new BlockHitResult(Vec3.atCenterOf(pos), face, pos, false);
    }

    private static List<ItemStack> dropsForBlock(GameTestHelper helper, Block block, ItemStack tool) {
        ServerLevel level = helper.getLevel();
        helper.setBlock(PRIMARY_BLOCK_POS, block);
        BlockPos absolutePos = helper.absolutePos(PRIMARY_BLOCK_POS);
        BlockState state = level.getBlockState(absolutePos);
        Entity entity = helper.makeMockPlayer(GameType.SURVIVAL);
        return Block.getDrops(state, level, absolutePos, null, entity, tool);
    }

    private static List<ItemStack> dropsForState(GameTestHelper helper, BlockState state, BlockPos pos, ItemStack tool) {
        ServerLevel level = helper.getLevel();
        level.setBlock(pos, state, Block.UPDATE_ALL);
        Entity entity = helper.makeMockPlayer(GameType.SURVIVAL);
        return Block.getDrops(state, level, pos, null, entity, tool);
    }

    private static void destroyBlockWithPlayer(GameTestHelper helper, BlockPos relativePos, Player player, ItemStack tool) {
        ServerLevel level = helper.getLevel();
        BlockPos absolutePos = helper.absolutePos(relativePos);
        BlockState state = level.getBlockState(absolutePos);
        state.getBlock().playerDestroy(level, player, absolutePos, state, null, tool);
        level.setBlock(absolutePos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
    }

    private static void assertContainsItem(GameTestHelper helper, List<ItemStack> drops, String itemId) {
        helper.assertTrue(itemIds(drops).contains(itemId), "Expected drops to contain " + itemId + " but got " + drops);
    }

    private static void assertDoesNotContainItem(GameTestHelper helper, List<ItemStack> drops, String itemId) {
        helper.assertFalse(itemIds(drops).contains(itemId), "Expected drops not to contain " + itemId + " but got " + drops);
    }

    private static Set<String> itemIds(List<ItemStack> drops) {
        return drops.stream().map(CavernSpecialOreGameTests::itemId).collect(java.util.stream.Collectors.toSet());
    }

    private static String itemId(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
    }

    private static String entityTypeId(Entity entity) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
    }

    private static void assertNoUnexpectedTerrainDamage(GameTestHelper helper, List<BlockPos> surroundingPositions) {
        for (BlockPos pos : surroundingPositions) {
            helper.assertBlockPresent(Blocks.STONE, pos);
        }
    }

    private static void assertProgressionEntry(GameTestHelper helper, Block block, int expectedScore) {
        String blockId = BuiltInRegistries.BLOCK.getKey(block).toString();
        helper.assertTrue(EXPECTED_PROGRESSION_SCORES.containsKey(blockId), "Missing expected score mapping for " + blockId);
        helper.assertTrue(CavernProgressionPolicy.scoreForBlock(blockId) == expectedScore, "Unexpected progression score for " + blockId);
        helper.assertTrue(CavernProgressionPolicy.countsTowardProgression(CavernDimensions.CAVERN_DIMENSION_ID, blockId), blockId + " must count in CAVERN");
        helper.assertFalse(CavernProgressionPolicy.countsTowardProgression(CavernDimensions.OVERWORLD_DIMENSION_ID, blockId), blockId + " must not count outside CAVERN");
    }

    private static <T> void assertRegistryKeyPresent(
        GameTestHelper helper,
        Registry<T> registry,
        ResourceKey<? extends Registry<T>> registryKey,
        String id
    ) {
        ResourceKey<T> key = ResourceKey.create(registryKey, ResourceLocation.parse(id));
        helper.assertTrue(registry.containsKey(key), "Missing runtime registry key " + key.location());
    }

    private static void assertRegistryId(GameTestHelper helper, Block block, String expectedId) {
        ResourceLocation actual = BuiltInRegistries.BLOCK.getKey(block);
        helper.assertTrue(expectedId.equals(actual.toString()), "Expected block id " + expectedId + " but got " + actual);
    }

    private static void assertRegistryId(GameTestHelper helper, Item item, String expectedId) {
        ResourceLocation actual = BuiltInRegistries.ITEM.getKey(item);
        helper.assertTrue(expectedId.equals(actual.toString()), "Expected item id " + expectedId + " but got " + actual);
    }

    private static void assertRegistryId(GameTestHelper helper, EntityType<?> entityType, String expectedId) {
        ResourceLocation actual = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
        helper.assertTrue(expectedId.equals(actual.toString()), "Expected entity type id " + expectedId + " but got " + actual);
    }

    private static void assertSupportsEnchantment(GameTestHelper helper, ItemStack stack, Holder<Enchantment> enchantment, boolean expected) {
        boolean actual = stack.supportsEnchantment(enchantment);
        ResourceLocation enchantmentId = enchantment.getKey().location();

        if (expected) {
            helper.assertTrue(actual, "Expected " + itemId(stack) + " to support " + enchantmentId);
        } else {
            helper.assertFalse(actual, "Expected " + itemId(stack) + " not to support " + enchantmentId);
        }
    }

    private static void assertArmorRuntime(
        GameTestHelper helper,
        ItemStack stack,
        ArmorItem.Type expectedType,
        EquipmentSlot expectedSlot,
        int expectedDurabilityMultiplier
    ) {
        helper.assertTrue(stack.isDamageableItem(), "Expected damageable armor: " + itemId(stack));
        helper.assertTrue(stack.getMaxDamage() == expectedType.getDurability(expectedDurabilityMultiplier), "Unexpected max damage for " + itemId(stack));
        helper.assertTrue(stack.getItem() instanceof ArmorItem, "Expected armor item runtime for " + itemId(stack));
        ArmorItem armorItem = (ArmorItem) stack.getItem();
        helper.assertTrue(armorItem.getType() == expectedType, "Unexpected armor type for " + itemId(stack));
        helper.assertTrue(armorItem.getEquipmentSlot() == expectedSlot, "Unexpected equipment slot for " + itemId(stack));
    }

    private static Player makeMockPlayer(GameTestHelper helper, ServerLevel level, GameType gameType, ItemStack tool, BlockPos origin) {
        Player player = helper.makeMockPlayer(gameType);
        player.getAbilities().instabuild = gameType.isCreative();
        player.getAbilities().mayfly = gameType.isCreative();
        player.getAbilities().flying = false;
        player.getAbilities().invulnerable = gameType.isCreative();
        player.setItemInHand(InteractionHand.MAIN_HAND, tool);
        player.teleportTo(level, origin.getX() + 0.5D, origin.getY() + 1.0D, origin.getZ() + 0.5D, EnumSet.noneOf(RelativeMovement.class), 0.0F, 0.0F);
        return player;
    }

    private static int countPlayerItem(Player player, Item item) {
        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(item)) {
                count += stack.getCount();
            }
        }
        if (player.getOffhandItem().is(item)) {
            count += player.getOffhandItem().getCount();
        }
        if (player.getMainHandItem().is(item)) {
            count += player.getMainHandItem().getCount();
        }
        return count;
    }

    private static <T extends LivingEntity> T spawnLivingEntity(GameTestHelper helper, EntityType<T> type, BlockPos pos) {
        T entity = helper.spawn(type, pos);
        helper.assertTrue(entity.isAlive(), "Expected spawned entity to be alive: " + BuiltInRegistries.ENTITY_TYPE.getKey(type));
        return entity;
    }

    private static long countGoals(Mob mob, Class<? extends Goal> goalClass) {
        return countGoals(mob.targetSelector.getAvailableGoals(), goalClass);
    }

    private static Optional<WrappedGoal> findGoalBySimpleName(Mob mob, String goalSimpleName) {
        return findGoalBySimpleName(mob.targetSelector.getAvailableGoals(), goalSimpleName);
    }

    private static long countGoals(Iterable<WrappedGoal> goals, Class<? extends Goal> goalClass) {
        return java.util.stream.StreamSupport.stream(goals.spliterator(), false).map(WrappedGoal::getGoal).filter(goalClass::isInstance).count();
    }

    private static Optional<WrappedGoal> findGoalBySimpleName(Iterable<WrappedGoal> goals, String goalSimpleName) {
        return java.util.stream.StreamSupport.stream(goals.spliterator(), false)
            .filter(goal -> goal.getGoal().getClass().getSimpleName().equals(goalSimpleName))
            .findFirst();
    }

    private static void invokeGoalMethod(Goal goal, String methodName, Mob targetMob, LivingEntity attacker) {
        try {
            Method method = findDeclaredMethod(goal.getClass(), methodName, Mob.class, LivingEntity.class);
            method.setAccessible(true);
            method.invoke(goal, targetMob, attacker);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to invoke goal method " + methodName + " on " + goal.getClass().getName(), exception);
        }
    }

    private static void invokeMeleeAttackCheck(Goal goal, LivingEntity target) {
        try {
            Method method = findDeclaredMethod(goal.getClass(), "checkAndPerformAttack", LivingEntity.class);
            method.setAccessible(true);
            method.invoke(goal, target);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to invoke melee attack check on " + goal.getClass().getName(), exception);
        }
    }

    private static boolean invokePanicShouldPanic(Goal goal) {
        try {
            Method method = findDeclaredMethod(goal.getClass(), "shouldPanic");
            method.setAccessible(true);
            return (boolean)method.invoke(goal);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to invoke panic trigger check on " + goal.getClass().getName(), exception);
        }
    }

    private static boolean invokeGoalCanUse(Goal goal) {
        try {
            Method method = findDeclaredMethod(goal.getClass(), "canUse");
            method.setAccessible(true);
            return (boolean)method.invoke(goal);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to invoke canUse on " + goal.getClass().getName(), exception);
        }
    }

    private static void setGoalField(Goal goal, String fieldName, int value) {
        try {
            Field field = findDeclaredField(goal.getClass(), fieldName);
            field.setAccessible(true);
            field.setInt(goal, value);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to set field " + fieldName + " on " + goal.getClass().getName(), exception);
        }
    }

    private static Method findDeclaredMethod(Class<?> type, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> current = type;

        while (current != null) {
            try {
                return current.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException exception) {
                current = current.getSuperclass();
            }
        }

        throw new NoSuchMethodException(methodName);
    }

    private static Field findDeclaredField(Class<?> type, String fieldName) throws NoSuchFieldException {
        Class<?> current = type;

        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException exception) {
                current = current.getSuperclass();
            }
        }

        throw new NoSuchFieldException(fieldName);
    }

    private static RandomSource queuedFloatRandomSource(float... values) {
        return new QueuedFloatRandomSource(values);
    }

    private static void clearEquipment(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            entity.setItemSlot(slot, ItemStack.EMPTY);
        }
    }


    private static int applyMiningAssist(GameTestHelper helper, ServerLevel level, Player player, BlockPos origin) {
        CavernMiningAssistEvents events = new CavernMiningAssistEvents(CavernReborn.cavernStateBootstrap().cavernProgressionService());
        int assistedBlocks = events.tryApplyMiningAssist(
            level,
            player,
            origin,
            level.getBlockState(origin),
            CavernDimensions.CAVERN_DIMENSION_ID
        );
        helper.assertTrue(assistedBlocks >= 0, "Mining Assist must never return a negative assisted block count");
        return assistedBlocks;
    }

    private static void grantMiningAssistUnlock(GameTestHelper helper, Player player) {
        clearProgression(player);
        for (int i = 0; i < 15; i++) {
            CavernReborn.cavernStateBootstrap().cavernProgressionService().recordMiningEvent(
                player.getUUID(),
                CavernDimensions.CAVERN_DIMENSION_ID,
                "minecraft:diamond_ore"
            );
        }
        helper.assertTrue(
            CavernReborn.cavernStateBootstrap().cavernProgressionService().inspect(player.getUUID()).hasUnlocked(CavernProgressionUnlock.MINING_ASSIST),
            "Expected direct progression setup to unlock Mining Assist for the mock player"
        );
    }

    private static void clearProgression(Player player) {
        CavernReborn.cavernStateBootstrap().playerMiningProgressionStore().clear(player.getUUID());
    }

    private static List<BlockPos> hexciteVein(BlockPos origin) {
        return List.of(
            origin,
            origin.above(),
            origin.below(),
            origin.north(),
            origin.south(),
            origin.west(),
            origin.east(),
            origin.above().above()
        );
    }

    private static void placeBlockSet(ServerLevel level, List<BlockPos> positions, Block block) {
        for (BlockPos pos : positions) {
            level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    private static long countBlocks(ServerLevel level, List<BlockPos> positions, Block block) {
        return positions.stream().filter(pos -> level.getBlockState(pos).is(block)).count();
    }

    private static Set<String> itemIdsAround(ServerLevel level, BlockPos center, double radius) {
        return level.getEntitiesOfClass(ItemEntity.class, new AABB(center).inflate(radius)).stream()
            .map(itemEntity -> itemId(itemEntity.getItem()))
            .collect(java.util.stream.Collectors.toSet());
    }

    private static long countEntities(ServerLevel level, EntityType<?> entityType, BlockPos center, double radius) {
        return level.getEntities((Entity) null, new AABB(center).inflate(radius), entity -> entity.getType() == entityType).size();
    }

    private static boolean hasCobwebInArea(ServerLevel level, BlockPos center, int radius) {
        for (int x = center.getX() - radius; x <= center.getX() + radius; x++) {
            for (int y = center.getY() - radius; y <= center.getY() + radius; y++) {
                for (int z = center.getZ() - radius; z <= center.getZ() + radius; z++) {
                    if (level.getBlockState(new BlockPos(x, y, z)).is(Blocks.COBWEB)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static void resetMiningArea(ServerLevel level, BlockPos center, double radius) {
        AABB bounds = new AABB(center).inflate(radius);
        level.getEntities((Entity) null, bounds, entity -> true).forEach(Entity::discard);
        for (int x = center.getX() - 3; x <= center.getX() + 3; x++) {
            for (int y = center.getY() - 3; y <= center.getY() + 3; y++) {
                for (int z = center.getZ() - 3; z <= center.getZ() + 3; z++) {
                    level.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }
    }

    private static void prepareNaturalSpawnPlatform(ServerLevel level, BlockPos spawnPos) {
        resetMiningArea(level, spawnPos, 6.0D);
        level.setBlock(spawnPos.below(), Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(spawnPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(spawnPos.above(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
    }

    private record BowReleaseResult(
        ServerLevel level,
        BlockPos origin,
        int shooterEntityId,
        UUID shooterId,
        Set<UUID> beforeArrowIds,
        InteractionResult useResult,
        boolean usingStarted,
        int arrowCountBefore,
        int arrowCountAfter,
        int torchCountBefore,
        int torchCountAfter,
        int bowDamageBefore,
        int bowDamageAfter,
        CavenicBowMode modeAfter
    ) {
    }

    private static final class ReleasedArrowRecorder {
        private final Map<Integer, List<AbstractArrow>> arrowsByShooter = new HashMap<>();

        private synchronized void clearShooter(int shooterEntityId) {
            arrowsByShooter.remove(shooterEntityId);
        }

        private synchronized List<AbstractArrow> arrowsFor(int shooterEntityId) {
            return List.copyOf(arrowsByShooter.getOrDefault(shooterEntityId, List.of()));
        }

        @SubscribeEvent
        public synchronized void onEntityJoinLevel(EntityJoinLevelEvent event) {
            if (event.getLevel().isClientSide()) {
                return;
            }
            if (!(event.getEntity() instanceof AbstractArrow arrow) || !(arrow.getOwner() instanceof Player owner)) {
                return;
            }
            arrowsByShooter.computeIfAbsent(owner.getId(), ignored -> new ArrayList<>()).add(arrow);
        }
    }

    private static final class QueuedFloatRandomSource implements RandomSource {
        private final float[] values;
        private final RandomSource delegate = RandomSource.create(0L);
        private int index;

        private QueuedFloatRandomSource(float... values) {
            this.values = values.clone();
        }

        @Override
        public RandomSource fork() {
            return delegate.fork();
        }

        @Override
        public net.minecraft.world.level.levelgen.PositionalRandomFactory forkPositional() {
            return delegate.forkPositional();
        }

        @Override
        public void setSeed(long seed) {
            delegate.setSeed(seed);
        }

        @Override
        public int nextInt() {
            return delegate.nextInt();
        }

        @Override
        public int nextInt(int bound) {
            return delegate.nextInt(bound);
        }

        @Override
        public long nextLong() {
            return delegate.nextLong();
        }

        @Override
        public boolean nextBoolean() {
            return delegate.nextBoolean();
        }

        @Override
        public float nextFloat() {
            if (index >= values.length) {
                throw new IllegalStateException("QueuedFloatRandomSource ran out of configured float values");
            }

            return values[index++];
        }

        @Override
        public double nextDouble() {
            return delegate.nextDouble();
        }

        @Override
        public double nextGaussian() {
            return delegate.nextGaussian();
        }
    }
}
