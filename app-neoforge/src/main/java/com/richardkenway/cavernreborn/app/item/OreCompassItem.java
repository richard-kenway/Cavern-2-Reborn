package com.richardkenway.cavernreborn.app.item;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.app.compass.OreCompassFeedbackFormatter;
import com.richardkenway.cavernreborn.app.compass.OreCompassScanner;
import com.richardkenway.cavernreborn.app.compass.OreCompassStateAccess;
import com.richardkenway.cavernreborn.app.compass.OreCompassTarget;
import com.richardkenway.cavernreborn.core.compass.OreCompassScanPolicy;
import com.richardkenway.cavernreborn.core.compass.OreCompassScanResult;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class OreCompassItem extends Item {
    private final OreCompassScanner scanner;

    public OreCompassItem(Properties properties) {
        this(properties, new OreCompassScanner());
    }

    OreCompassItem(Properties properties, OreCompassScanner scanner) {
        super(properties);
        this.scanner = Objects.requireNonNull(scanner, "scanner");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        if (player.getCooldowns().isOnCooldown(this)) {
            player.displayClientMessage(OreCompassFeedbackFormatter.cooldown(), true);
            return InteractionResultHolder.sidedSuccess(stack, false);
        }
        if (!CavernDimensions.CAVERN_DIMENSION_ID.equals(serverLevel.dimension().location().toString())) {
            player.displayClientMessage(OreCompassFeedbackFormatter.wrongDimension(), true);
            return InteractionResultHolder.sidedSuccess(stack, false);
        }

        Optional<OreCompassTarget> target = performServerScan(serverLevel, player.blockPosition(), stack);

        if (target.isPresent()) {
            OreCompassTarget resolvedTarget = target.get();
            BlockState state = serverLevel.getBlockState(resolvedTarget.pos());
            OreCompassScanResult result = OreCompassScanPolicy.evaluate(
                serverLevel.dimension().location().toString(),
                true,
                resolvedTarget.blockId(),
                resolvedTarget.pos().getX() - player.blockPosition().getX(),
                resolvedTarget.pos().getY() - player.blockPosition().getY(),
                resolvedTarget.pos().getZ() - player.blockPosition().getZ()
            );
            player.displayClientMessage(OreCompassFeedbackFormatter.found(state, result), true);
        } else {
            player.displayClientMessage(OreCompassFeedbackFormatter.noTarget(), true);
        }

        player.getCooldowns().addCooldown(this, OreCompassScanPolicy.COOLDOWN_TICKS);
        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    public Optional<OreCompassTarget> performServerScan(ServerLevel level, net.minecraft.core.BlockPos origin, ItemStack stack) {
        Optional<OreCompassTarget> target = scanner.findNearestTarget(
            level,
            origin,
            OreCompassScanPolicy.HORIZONTAL_RADIUS,
            OreCompassScanPolicy.VERTICAL_RADIUS
        );
        if (target.isPresent()) {
            OreCompassTarget resolvedTarget = target.get();
            OreCompassStateAccess.writeTarget(
                stack,
                level.dimension().location().toString(),
                resolvedTarget.blockId(),
                resolvedTarget.pos()
            );
            return target;
        }

        OreCompassStateAccess.clear(stack);
        return Optional.empty();
    }
}
