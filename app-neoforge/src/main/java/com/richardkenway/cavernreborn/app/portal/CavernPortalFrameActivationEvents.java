package com.richardkenway.cavernreborn.app.portal;

import java.util.Optional;

import com.richardkenway.cavernreborn.app.registry.ModRegistries;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class CavernPortalFrameActivationEvents {
    private final CavernPortalFrameActivationHandler activationHandler = new CavernPortalFrameActivationHandler();

    public CavernPortalFrameActivationEvents() {
    }

    @SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getLevel() instanceof Level level)) {
            return;
        }

        Optional<CavernPortalFrameActivationHandler.ActivationOutcome> activation = activationHandler.handle(
            level.isClientSide(),
            event.getEntity() != null && event.getEntity().isCreative(),
            event.getItemStack().is(Items.EMERALD),
            event.getPos(),
            event.getFace(),
            clickedPos -> isMossyCobblestoneFrame(level, clickedPos),
            (clickedPos, clickedFace) -> activateFrame(level, clickedPos, clickedFace),
            () -> event.getItemStack().shrink(1)
        );
        if (activation.isEmpty()) {
            return;
        }

        level.playSound(
            null,
            activation.get().frame().bottomLeft(),
            SoundEvents.GLASS_PLACE,
            SoundSource.BLOCKS,
            1.0F,
            2.0F
        );

        event.setCancellationResult(InteractionResult.CONSUME);
        event.setCanceled(true);
    }

    private static boolean isMossyCobblestoneFrame(Level level, BlockPos pos) {
        BlockState clickedState = level.getBlockState(pos);
        return clickedState.is(Blocks.MOSSY_COBBLESTONE);
    }

    private static Optional<CavernPortalFrameDetector.PortalFrame> activateFrame(LevelAccessor level, BlockPos pos, Direction face) {
        WorldPortalFrameAccess frameAccess = new WorldPortalFrameAccess(level, ModRegistries.CAVERN_PORTAL_BLOCK.get());
        return CavernPortalFrameActivationService.activate(frameAccess, pos, face);
    }
}
