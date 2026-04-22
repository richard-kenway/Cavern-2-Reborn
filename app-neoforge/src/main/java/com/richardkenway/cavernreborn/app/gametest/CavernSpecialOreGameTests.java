package com.richardkenway.cavernreborn.app.gametest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.registry.ModRegistries;
import com.richardkenway.cavernreborn.app.registry.ModToolTiers;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionPolicy;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(CavernReborn.MOD_ID)
@PrefixGameTestTemplate(false)
public final class CavernSpecialOreGameTests {
    private static final String TEMPLATE_NAMESPACE = "minecraft";
    private static final String EMPTY_TEMPLATE = "empty";
    private static final int DEFAULT_TIMEOUT_TICKS = 100;
    private static final BlockPos PRIMARY_BLOCK_POS = new BlockPos(1, 1, 1);
    private static final Set<String> ALLOWED_RANDOMITE_DROPS = Set.of(
        "cavernreborn:aquamarine",
        "cavernreborn:magnite_ingot",
        "cavernreborn:hexcite",
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
    public static void randomiteOreLootProducesAllowedCuratedDrop(GameTestHelper helper) {
        helper.killAllEntities();

        for (int sample = 0; sample < 8; sample++) {
            List<ItemStack> drops = dropsForBlock(helper, ModRegistries.RANDOMITE_ORE.get(), normalPickaxe(helper.getLevel()));
            helper.assertTrue(!drops.isEmpty(), "Randomite ore returned no drops on sample " + sample);

            for (ItemStack stack : drops) {
                String itemId = itemId(stack);
                helper.assertTrue(ALLOWED_RANDOMITE_DROPS.contains(itemId), "Randomite ore produced unexpected drop: " + itemId);
                helper.assertTrue(stack.getCount() >= 1 && stack.getCount() <= 4, "Randomite ore produced out-of-bounds stack size: " + stack);
                helper.assertFalse("cavernreborn:miner_orb".equals(itemId), "Randomite ore must not produce miner_orb");
            }
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

    private static ItemStack normalPickaxe(ServerLevel level) {
        return new ItemStack(Items.IRON_PICKAXE);
    }

    private static ItemStack silkTouchPickaxe(ServerLevel level) {
        ItemStack tool = normalPickaxe(level);
        HolderLookup.RegistryLookup<Enchantment> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        tool.enchant(enchantments.getOrThrow(Enchantments.SILK_TOUCH), 1);
        return tool;
    }

    private static ItemStack hexcitePickaxe() {
        return new ItemStack(ModRegistries.HEXCITE_PICKAXE.get());
    }

    private static ItemStack silkTouchHexcitePickaxe(ServerLevel level) {
        ItemStack tool = hexcitePickaxe();
        HolderLookup.RegistryLookup<Enchantment> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        tool.enchant(enchantments.getOrThrow(Enchantments.SILK_TOUCH), 1);
        return tool;
    }

    private static List<ItemStack> dropsForBlock(GameTestHelper helper, Block block, ItemStack tool) {
        ServerLevel level = helper.getLevel();
        helper.setBlock(PRIMARY_BLOCK_POS, block);
        BlockPos absolutePos = helper.absolutePos(PRIMARY_BLOCK_POS);
        BlockState state = level.getBlockState(absolutePos);
        Entity entity = helper.makeMockPlayer(GameType.SURVIVAL);
        return Block.getDrops(state, level, absolutePos, null, entity, tool);
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
}
