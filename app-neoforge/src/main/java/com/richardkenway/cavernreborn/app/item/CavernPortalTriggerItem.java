package com.richardkenway.cavernreborn.app.item;

import java.util.Objects;
import java.util.function.Supplier;

import com.richardkenway.cavernreborn.app.portal.CavernPortalFrameActivator;
import com.richardkenway.cavernreborn.app.portal.CavernPortalFrameDetector;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class CavernPortalTriggerItem extends Item {
    private final Supplier<Block> portalBlockSupplier;

    public CavernPortalTriggerItem(Properties properties, Supplier<Block> portalBlockSupplier) {
        super(properties);
        this.portalBlockSupplier = Objects.requireNonNull(portalBlockSupplier, "portalBlockSupplier");
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos origin = context.getClickedPos().relative(context.getClickedFace());

        CavernPortalFrameActivator activator = new CavernPortalFrameActivator(new WorldPortalAccess(level, portalBlockSupplier.get()));
        return activator.activate(origin)
            .map(frame -> activatePortal(level, frame, context))
            .orElse(InteractionResult.PASS);
    }

    private InteractionResult activatePortal(Level level, CavernPortalFrameDetector.PortalFrame frame, UseOnContext context) {
        level.playSound(
            null,
            frame.bottomLeft(),
            SoundEvents.GLASS_PLACE,
            SoundSource.BLOCKS,
            1.0F,
            2.0F
        );

        ItemStack itemInHand = context.getItemInHand();
        if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
            itemInHand.shrink(1);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private record WorldPortalAccess(Level level, Block portalBlock) implements CavernPortalFrameActivator.PortalAccess {
        @Override
        public boolean isFrame(BlockPos pos) {
            return level.getBlockState(pos).is(Blocks.OBSIDIAN);
        }

        @Override
        public boolean isInterior(BlockPos pos) {
            BlockState state = level.getBlockState(pos);
            return state.isAir() || state.is(portalBlock);
        }

        @Override
        public boolean isPortal(BlockPos pos) {
            return level.getBlockState(pos).is(portalBlock);
        }

        @Override
        public void setPortal(BlockPos pos) {
            level.setBlock(pos, portalBlock.defaultBlockState(), 2);
        }
    }
}
