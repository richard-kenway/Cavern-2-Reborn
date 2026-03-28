package com.richardkenway.cavernreborn.app.block;

import java.util.Objects;
import java.util.function.Supplier;

import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionContext;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionOutcome;
import com.richardkenway.cavernreborn.app.portal.CavernPortalInteractionService;
import com.richardkenway.cavernreborn.app.portal.NeoForgeCavernPortalInteractionContext;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.server.level.ServerPlayer;

public final class CavernPortalBlock extends Block {
    private final Supplier<CavernPortalInteractionService> interactionServiceSupplier;

    public CavernPortalBlock(BlockBehaviour.Properties properties, Supplier<CavernPortalInteractionService> interactionServiceSupplier) {
        super(properties);
        this.interactionServiceSupplier = Objects.requireNonNull(interactionServiceSupplier, "interactionServiceSupplier");
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.PASS;
        }

        CavernPortalInteractionContext context = new NeoForgeCavernPortalInteractionContext(serverPlayer, level, pos, hitResult);
        CavernPortalInteractionOutcome outcome = interactionServiceSupplier.get().use(context);
        return outcome.handled() ? InteractionResult.CONSUME : InteractionResult.PASS;
    }
}
