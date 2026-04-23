package com.richardkenway.cavernreborn.app.gametest;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.block.AcresiaCropBlock;
import com.richardkenway.cavernreborn.app.block.CavenicShroomBlock;
import com.richardkenway.cavernreborn.app.compass.OreCompassStateAccess;
import com.richardkenway.cavernreborn.app.compass.OreCompassScanner;
import com.richardkenway.cavernreborn.app.compass.OreCompassTarget;
import com.richardkenway.cavernreborn.app.compass.StoredOreCompassTarget;
import com.richardkenway.cavernreborn.app.item.OreCompassItem;
import com.richardkenway.cavernreborn.app.mining.CavernMiningAssistEvents;
import com.richardkenway.cavernreborn.app.registry.ModArmorMaterials;
import com.richardkenway.cavernreborn.app.registry.ModBlockTags;
import com.richardkenway.cavernreborn.app.registry.ModItemTags;
import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.app.registry.ModToolTiers;
import com.richardkenway.cavernreborn.core.compass.OreCompassDirection;
import com.richardkenway.cavernreborn.core.compass.OreCompassScanDecision;
import com.richardkenway.cavernreborn.core.compass.OreCompassScanPolicy;
import com.richardkenway.cavernreborn.core.compass.OreCompassScanResult;
import com.richardkenway.cavernreborn.core.compass.OreCompassTrackingDecision;
import com.richardkenway.cavernreborn.core.compass.OreCompassTrackingPolicy;
import com.richardkenway.cavernreborn.core.compass.OreCompassTrackingResult;
import com.richardkenway.cavernreborn.core.flora.CavenicOrbDropPolicy;
import com.richardkenway.cavernreborn.core.farming.AcresiaHarvestPolicy;
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
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(CavernReborn.MOD_ID)
@PrefixGameTestTemplate(false)
public final class CavernSpecialOreGameTests {
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
        return new BlockHitResult(Vec3.atCenterOf(pos), net.minecraft.core.Direction.UP, pos, false);
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
        player.setItemInHand(InteractionHand.MAIN_HAND, tool);
        player.teleportTo(level, origin.getX() + 0.5D, origin.getY() + 1.0D, origin.getZ() + 0.5D, EnumSet.noneOf(RelativeMovement.class), 0.0F, 0.0F);
        return player;
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
}
