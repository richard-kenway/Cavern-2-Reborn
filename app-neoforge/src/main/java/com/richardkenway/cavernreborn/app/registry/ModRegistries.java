package com.richardkenway.cavernreborn.app.registry;

import com.richardkenway.cavernreborn.CavernReborn;
import com.richardkenway.cavernreborn.app.block.CavernPortalBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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
    public static final DeferredBlock<Block> CAVERN_PORTAL_BLOCK = BLOCKS.register("cavern_portal",
            () -> new CavernPortalBlock(
            BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.GLASS).noCollission().noOcclusion(),
            () -> CavernReborn.cavernStateBootstrap().cavernPortalInteractionService(),
            () -> CavernReborn.cavernStateBootstrap().nonPlayerPortalInteractionService()));
    public static final DeferredItem<BlockItem> BOOTSTRAP_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(BOOTSTRAP_BLOCK);
    public static final DeferredItem<BlockItem> CAVERN_PORTAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CAVERN_PORTAL_BLOCK);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.cavernreborn.main"))
            .icon(() -> new ItemStack(BOOTSTRAP_BLOCK_ITEM.get()))
            .displayItems((parameters, output) -> {
                output.accept(BOOTSTRAP_BLOCK_ITEM.get());
                output.accept(CAVERN_PORTAL_BLOCK_ITEM.get());
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
