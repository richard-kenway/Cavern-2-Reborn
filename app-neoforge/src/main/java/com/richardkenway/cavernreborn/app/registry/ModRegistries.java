package com.richardkenway.cavernreborn.app.registry;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.block.CavernPortalBlock;
import com.richardkenway.cavernreborn.app.block.FissuredStoneBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

public final class ModRegistries {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CavernReborn.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CavernReborn.MOD_ID);
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
    public static final DeferredItem<BlockItem> FISSURED_STONE_ITEM = ITEMS.registerSimpleBlockItem(FISSURED_STONE);
    public static final DeferredItem<BlockItem> CAVERN_PORTAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CAVERN_PORTAL_BLOCK);
    public static final DeferredItem<Item> AQUAMARINE = ITEMS.register("aquamarine", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MAGNITE_INGOT = ITEMS.register("magnite_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HEXCITE = ITEMS.register("hexcite", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MINER_ORB = ITEMS.register("miner_orb", () -> new Item(new Item.Properties().stacksTo(16)));
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
                output.accept(MAGNITE_INGOT.get());
                output.accept(HEXCITE.get());
                output.accept(MINER_ORB.get());
                output.accept(HEXCITE_PICKAXE.get());
                output.accept(HEXCITE_AXE.get());
                output.accept(HEXCITE_SHOVEL.get());
                output.accept(HEXCITE_HOE.get());
                output.accept(HEXCITE_SWORD.get());
            })
            .build());

    private ModRegistries() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
