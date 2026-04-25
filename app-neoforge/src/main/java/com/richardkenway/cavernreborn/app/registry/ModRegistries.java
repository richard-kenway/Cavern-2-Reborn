package com.richardkenway.cavernreborn.app.registry;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.block.AcresiaCropBlock;
import com.richardkenway.cavernreborn.app.block.CavenicShroomBlock;
import com.richardkenway.cavernreborn.app.block.CavernPortalBlock;
import com.richardkenway.cavernreborn.app.entity.CavenicCreeper;
import com.richardkenway.cavernreborn.app.entity.CavenicSkeleton;
import com.richardkenway.cavernreborn.app.entity.CavenicSpider;
import com.richardkenway.cavernreborn.app.entity.CavenicZombie;
import com.richardkenway.cavernreborn.app.block.FissuredStoneBlock;
import com.richardkenway.cavernreborn.app.item.CavenicAxeItem;
import com.richardkenway.cavernreborn.app.item.CavenicBowItem;
import com.richardkenway.cavernreborn.app.item.CavenicSwordItem;
import com.richardkenway.cavernreborn.app.item.OreCompassItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

public final class ModRegistries {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CavernReborn.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CavernReborn.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, CavernReborn.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CavernReborn.MOD_ID);

    public static final DeferredBlock<Block> BOOTSTRAP_BLOCK = BLOCKS.register("bootstrap_block",
        () -> new Block(BlockBehaviour.Properties.of().strength(1.5F).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> AQUAMARINE_ORE = BLOCKS.register("aquamarine_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(1, 3),
            BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> AQUAMARINE_BLOCK = BLOCKS.register("aquamarine_block",
        () -> new Block(BlockBehaviour.Properties.of().strength(3.5F).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> MAGNITE_ORE = BLOCKS.register("magnite_ore",
        () -> new Block(BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> MAGNITE_BLOCK = BLOCKS.register("magnite_block",
        () -> new Block(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> RANDOMITE_ORE = BLOCKS.register("randomite_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(1, 3),
            BlockBehaviour.Properties.of().strength(4.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> HEXCITE_ORE = BLOCKS.register("hexcite_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(3, 5),
            BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> HEXCITE_BLOCK = BLOCKS.register("hexcite_block",
        () -> new Block(BlockBehaviour.Properties.of().strength(3.5F).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> ACRESIA = BLOCKS.register("acresia",
        () -> new AcresiaCropBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.CROP).randomTicks()));
    public static final DeferredBlock<Block> CAVENIC_SHROOM = BLOCKS.register("cavenic_shroom",
        () -> new CavenicShroomBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).randomTicks()));
    public static final DeferredBlock<Block> FISSURED_STONE = BLOCKS.register("fissured_stone",
        () -> new FissuredStoneBlock(
            UniformInt.of(3, 5),
            BlockBehaviour.Properties.of().strength(1.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> CAVERN_PORTAL_BLOCK = BLOCKS.register("cavern_portal",
            () -> new CavernPortalBlock(
            BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.GLASS).noCollission().noOcclusion(),
            () -> CavernReborn.cavernStateBootstrap().cavernPortalInteractionService(),
            () -> CavernReborn.cavernStateBootstrap().nonPlayerPortalInteractionService(),
            () -> CavernReborn.cavernStateBootstrap().cavernCatalogGuiOpener()));
    public static final DeferredItem<BlockItem> BOOTSTRAP_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(BOOTSTRAP_BLOCK);
    public static final DeferredItem<BlockItem> AQUAMARINE_ORE_ITEM = ITEMS.registerSimpleBlockItem(AQUAMARINE_ORE);
    public static final DeferredItem<BlockItem> AQUAMARINE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(AQUAMARINE_BLOCK);
    public static final DeferredItem<BlockItem> MAGNITE_ORE_ITEM = ITEMS.registerSimpleBlockItem(MAGNITE_ORE);
    public static final DeferredItem<BlockItem> MAGNITE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(MAGNITE_BLOCK);
    public static final DeferredItem<BlockItem> RANDOMITE_ORE_ITEM = ITEMS.registerSimpleBlockItem(RANDOMITE_ORE);
    public static final DeferredItem<BlockItem> HEXCITE_ORE_ITEM = ITEMS.registerSimpleBlockItem(HEXCITE_ORE);
    public static final DeferredItem<BlockItem> HEXCITE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(HEXCITE_BLOCK);
    public static final DeferredItem<BlockItem> CAVENIC_SHROOM_ITEM = ITEMS.registerSimpleBlockItem(CAVENIC_SHROOM);
    public static final DeferredItem<BlockItem> FISSURED_STONE_ITEM = ITEMS.registerSimpleBlockItem(FISSURED_STONE);
    public static final DeferredItem<BlockItem> CAVERN_PORTAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CAVERN_PORTAL_BLOCK);
    public static final DeferredItem<Item> AQUAMARINE = ITEMS.register("aquamarine", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MAGNITE_INGOT = ITEMS.register("magnite_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HEXCITE = ITEMS.register("hexcite", () -> new Item(new Item.Properties()));
    public static final DeferredItem<ItemNameBlockItem> ACRESIA_SEEDS = ITEMS.register(
        "acresia_seeds",
        () -> new ItemNameBlockItem(ACRESIA.get(), new Item.Properties())
    );
    public static final DeferredItem<Item> ACRESIA_FRUITS = ITEMS.register(
        "acresia_fruits",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).build()))
    );
    public static final DeferredHolder<EntityType<?>, EntityType<CavenicZombie>> CAVENIC_ZOMBIE = ENTITY_TYPES.register(
        "cavenic_zombie",
        () -> EntityType.Builder.of(CavenicZombie::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .eyeHeight(1.74F)
            .passengerAttachments(2.0125F)
            .ridingOffset(-0.7F)
            .clientTrackingRange(8)
            .build(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavenic_zombie").toString())
    );
    public static final DeferredHolder<EntityType<?>, EntityType<CavenicSkeleton>> CAVENIC_SKELETON = ENTITY_TYPES.register(
        "cavenic_skeleton",
        () -> EntityType.Builder.of(CavenicSkeleton::new, MobCategory.MONSTER)
            .sized(0.68F, 2.0F)
            .eyeHeight(1.74F)
            .ridingOffset(-0.7F)
            .clientTrackingRange(8)
            .build(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavenic_skeleton").toString())
    );
    public static final DeferredHolder<EntityType<?>, EntityType<CavenicCreeper>> CAVENIC_CREEPER = ENTITY_TYPES.register(
        "cavenic_creeper",
        () -> EntityType.Builder.of(CavenicCreeper::new, MobCategory.MONSTER)
            .sized(0.6F, 1.7F)
            .clientTrackingRange(8)
            .build(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavenic_creeper").toString())
    );
    public static final DeferredHolder<EntityType<?>, EntityType<CavenicSpider>> CAVENIC_SPIDER = ENTITY_TYPES.register(
        "cavenic_spider",
        () -> EntityType.Builder.of(CavenicSpider::new, MobCategory.MONSTER)
            .sized(1.4F, 0.9F)
            .eyeHeight(0.65F)
            .passengerAttachments(0.765F)
            .clientTrackingRange(8)
            .build(ResourceLocation.fromNamespaceAndPath(CavernReborn.MOD_ID, "cavenic_spider").toString())
    );
    public static final DeferredItem<Item> MINER_ORB = ITEMS.register("miner_orb", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> CAVENIC_ORB = ITEMS.register("cavenic_orb", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> CAVENIC_SWORD = ITEMS.register("cavenic_sword",
        () -> new CavenicSwordItem(
            ModToolTiers.CAVENIC,
            new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.CAVENIC, 3, -1.0F))));
    public static final DeferredItem<Item> CAVENIC_AXE = ITEMS.register("cavenic_axe",
        () -> new CavenicAxeItem(
            ModToolTiers.CAVENIC,
            new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.CAVENIC, 8.0F, -3.05F))));
    public static final DeferredItem<Item> CAVENIC_BOW = ITEMS.register("cavenic_bow",
        () -> new CavenicBowItem(
            new Item.Properties()
                .durability(ModToolTiers.CAVENIC.getUses())
                .stacksTo(1)));
    public static final DeferredItem<Item> CAVENIC_ZOMBIE_SPAWN_EGG = ITEMS.register(
        "cavenic_zombie_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> CAVENIC_ZOMBIE.get(), 0xAAAAAA, 0x00A0A0, new Item.Properties())
    );
    public static final DeferredItem<Item> CAVENIC_SKELETON_SPAWN_EGG = ITEMS.register(
        "cavenic_skeleton_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> CAVENIC_SKELETON.get(), 0xAAAAAA, 0xDDDDDD, new Item.Properties())
    );
    public static final DeferredItem<Item> CAVENIC_CREEPER_SPAWN_EGG = ITEMS.register(
        "cavenic_creeper_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> CAVENIC_CREEPER.get(), 0xAAAAAA, 0x2E8B57, new Item.Properties())
    );
    public static final DeferredItem<Item> CAVENIC_SPIDER_SPAWN_EGG = ITEMS.register(
        "cavenic_spider_spawn_egg",
        () -> new DeferredSpawnEggItem(() -> CAVENIC_SPIDER.get(), 0xAAAAAA, 0x811F1F, new Item.Properties())
    );
    public static final DeferredItem<Item> ORE_COMPASS = ITEMS.register(
        "ore_compass",
        () -> new OreCompassItem(new Item.Properties().stacksTo(1))
    );
    public static final DeferredItem<Item> AQUAMARINE_PICKAXE = ITEMS.register("aquamarine_pickaxe",
        () -> new PickaxeItem(
            ModToolTiers.AQUAMARINE,
            new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.AQUAMARINE, 1.0F, -2.8F))));
    public static final DeferredItem<Item> AQUAMARINE_AXE = ITEMS.register("aquamarine_axe",
        () -> new AxeItem(
            ModToolTiers.AQUAMARINE,
            new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.AQUAMARINE, 5.0F, -3.0F))));
    public static final DeferredItem<Item> AQUAMARINE_SHOVEL = ITEMS.register("aquamarine_shovel",
        () -> new ShovelItem(
            ModToolTiers.AQUAMARINE,
            new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.AQUAMARINE, 1.5F, -3.0F))));
    public static final DeferredItem<Item> MAGNITE_PICKAXE = ITEMS.register("magnite_pickaxe",
        () -> new PickaxeItem(
            ModToolTiers.MAGNITE,
            new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.MAGNITE, 1.0F, -2.8F))));
    public static final DeferredItem<Item> MAGNITE_AXE = ITEMS.register("magnite_axe",
        () -> new AxeItem(
            ModToolTiers.MAGNITE,
            new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.MAGNITE, 7.0F, -3.1F))));
    public static final DeferredItem<Item> MAGNITE_SHOVEL = ITEMS.register("magnite_shovel",
        () -> new ShovelItem(
            ModToolTiers.MAGNITE,
            new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.MAGNITE, 1.5F, -3.0F))));
    public static final DeferredItem<Item> MAGNITE_SWORD = ITEMS.register("magnite_sword",
        () -> new SwordItem(
            ModToolTiers.MAGNITE,
            new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.MAGNITE, 3, -2.4F))));
    public static final DeferredItem<Item> MAGNITE_HELMET = ITEMS.register("magnite_helmet",
        () -> new ArmorItem(
            ModArmorMaterials.MAGNITE,
            ArmorItem.Type.HELMET,
            new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER))));
    public static final DeferredItem<Item> MAGNITE_CHESTPLATE = ITEMS.register("magnite_chestplate",
        () -> new ArmorItem(
            ModArmorMaterials.MAGNITE,
            ArmorItem.Type.CHESTPLATE,
            new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER))));
    public static final DeferredItem<Item> MAGNITE_LEGGINGS = ITEMS.register("magnite_leggings",
        () -> new ArmorItem(
            ModArmorMaterials.MAGNITE,
            ArmorItem.Type.LEGGINGS,
            new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER))));
    public static final DeferredItem<Item> MAGNITE_BOOTS = ITEMS.register("magnite_boots",
        () -> new ArmorItem(
            ModArmorMaterials.MAGNITE,
            ArmorItem.Type.BOOTS,
            new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(ModArmorMaterials.MAGNITE_DURABILITY_MULTIPLIER))));
    public static final DeferredItem<Item> HEXCITE_PICKAXE = ITEMS.register("hexcite_pickaxe",
        () -> new PickaxeItem(
            ModToolTiers.HEXCITE,
            new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.HEXCITE, 1.0F, -2.8F))));
    public static final DeferredItem<Item> HEXCITE_AXE = ITEMS.register("hexcite_axe",
        () -> new AxeItem(
            ModToolTiers.HEXCITE,
            new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.HEXCITE, 5.0F, -3.0F))));
    public static final DeferredItem<Item> HEXCITE_SHOVEL = ITEMS.register("hexcite_shovel",
        () -> new ShovelItem(
            ModToolTiers.HEXCITE,
            new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.HEXCITE, 1.5F, -3.0F))));
    public static final DeferredItem<Item> HEXCITE_HOE = ITEMS.register("hexcite_hoe",
        () -> new HoeItem(
            ModToolTiers.HEXCITE,
            new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.HEXCITE, -3.0F, 0.0F))));
    public static final DeferredItem<Item> HEXCITE_SWORD = ITEMS.register("hexcite_sword",
        () -> new SwordItem(
            ModToolTiers.HEXCITE,
            new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.HEXCITE, 3, -2.4F))));
    public static final DeferredItem<Item> HEXCITE_HELMET = ITEMS.register("hexcite_helmet",
        () -> new ArmorItem(
            ModArmorMaterials.HEXCITE,
            ArmorItem.Type.HELMET,
            new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(ModArmorMaterials.HEXCITE_DURABILITY_MULTIPLIER))));
    public static final DeferredItem<Item> HEXCITE_CHESTPLATE = ITEMS.register("hexcite_chestplate",
        () -> new ArmorItem(
            ModArmorMaterials.HEXCITE,
            ArmorItem.Type.CHESTPLATE,
            new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(ModArmorMaterials.HEXCITE_DURABILITY_MULTIPLIER))));
    public static final DeferredItem<Item> HEXCITE_LEGGINGS = ITEMS.register("hexcite_leggings",
        () -> new ArmorItem(
            ModArmorMaterials.HEXCITE,
            ArmorItem.Type.LEGGINGS,
            new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(ModArmorMaterials.HEXCITE_DURABILITY_MULTIPLIER))));
    public static final DeferredItem<Item> HEXCITE_BOOTS = ITEMS.register("hexcite_boots",
        () -> new ArmorItem(
            ModArmorMaterials.HEXCITE,
            ArmorItem.Type.BOOTS,
            new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(ModArmorMaterials.HEXCITE_DURABILITY_MULTIPLIER))));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.cavernreborn.main"))
            .icon(() -> new ItemStack(BOOTSTRAP_BLOCK_ITEM.get()))
            .displayItems((parameters, output) -> {
                output.accept(BOOTSTRAP_BLOCK_ITEM.get());
                output.accept(AQUAMARINE_ORE_ITEM.get());
                output.accept(AQUAMARINE_BLOCK_ITEM.get());
                output.accept(MAGNITE_ORE_ITEM.get());
                output.accept(MAGNITE_BLOCK_ITEM.get());
                output.accept(RANDOMITE_ORE_ITEM.get());
                output.accept(HEXCITE_ORE_ITEM.get());
                output.accept(HEXCITE_BLOCK_ITEM.get());
                output.accept(FISSURED_STONE_ITEM.get());
                output.accept(CAVERN_PORTAL_BLOCK_ITEM.get());
                output.accept(AQUAMARINE.get());
                output.accept(AQUAMARINE_PICKAXE.get());
                output.accept(AQUAMARINE_AXE.get());
                output.accept(AQUAMARINE_SHOVEL.get());
                output.accept(MAGNITE_INGOT.get());
                output.accept(MAGNITE_PICKAXE.get());
                output.accept(MAGNITE_AXE.get());
                output.accept(MAGNITE_SHOVEL.get());
                output.accept(MAGNITE_SWORD.get());
                output.accept(MAGNITE_HELMET.get());
                output.accept(MAGNITE_CHESTPLATE.get());
                output.accept(MAGNITE_LEGGINGS.get());
                output.accept(MAGNITE_BOOTS.get());
                output.accept(HEXCITE.get());
                output.accept(ACRESIA_SEEDS.get());
                output.accept(ACRESIA_FRUITS.get());
                output.accept(CAVENIC_SHROOM_ITEM.get());
                output.accept(MINER_ORB.get());
                output.accept(CAVENIC_ORB.get());
                output.accept(CAVENIC_SWORD.get());
                output.accept(CAVENIC_AXE.get());
                output.accept(CAVENIC_BOW.get());
                output.accept(CAVENIC_ZOMBIE_SPAWN_EGG.get());
                output.accept(CAVENIC_SKELETON_SPAWN_EGG.get());
                output.accept(CAVENIC_CREEPER_SPAWN_EGG.get());
                output.accept(CAVENIC_SPIDER_SPAWN_EGG.get());
                output.accept(ORE_COMPASS.get());
                output.accept(HEXCITE_PICKAXE.get());
                output.accept(HEXCITE_AXE.get());
                output.accept(HEXCITE_SHOVEL.get());
                output.accept(HEXCITE_HOE.get());
                output.accept(HEXCITE_SWORD.get());
                output.accept(HEXCITE_HELMET.get());
                output.accept(HEXCITE_CHESTPLATE.get());
                output.accept(HEXCITE_LEGGINGS.get());
                output.accept(HEXCITE_BOOTS.get());
            })
            .build());

    private ModRegistries() {
    }

    public static void register(IEventBus modEventBus) {
        ModArmorMaterials.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
