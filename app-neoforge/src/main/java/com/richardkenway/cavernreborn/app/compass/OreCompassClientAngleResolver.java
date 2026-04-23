package com.richardkenway.cavernreborn.app.compass;

import java.util.Optional;

import javax.annotation.Nullable;

import com.richardkenway.cavernreborn.core.compass.OreCompassTrackingPolicy;
import com.richardkenway.cavernreborn.core.compass.OreCompassTrackingResult;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class OreCompassClientAngleResolver {
    private final CompassItemPropertyFunction propertyFunction = new CompassItemPropertyFunction(this::resolveGlobalTarget);

    public float resolveAngle(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity livingEntity, int seed) {
        return propertyFunction.unclampedCall(stack, level, livingEntity, seed);
    }

    public OreCompassTrackingResult resolveTracking(ItemStack stack, @Nullable ClientLevel level, @Nullable Entity entity) {
        Optional<StoredOreCompassTarget> storedTarget = OreCompassStateAccess.readTarget(stack);
        if (storedTarget.isEmpty()) {
            return OreCompassTrackingPolicy.evaluate(false, true, "", "", 0, 0, 0, false);
        }
        if (level == null || entity == null) {
            return OreCompassTrackingPolicy.evaluate(true, false, "", storedTarget.get().dimensionId(), 0, 0, 0, false);
        }

        StoredOreCompassTarget target = storedTarget.get();
        int dx = target.pos().getX() - entity.blockPosition().getX();
        int dy = target.pos().getY() - entity.blockPosition().getY();
        int dz = target.pos().getZ() - entity.blockPosition().getZ();

        boolean blockMatches = false;
        if (level.isLoaded(target.pos())) {
            BlockState state = level.getBlockState(target.pos());
            blockMatches = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString().equals(target.blockId());
        }

        return OreCompassTrackingPolicy.evaluate(
            true,
            true,
            level.dimension().location().toString(),
            target.dimensionId(),
            dx,
            dy,
            dz,
            blockMatches
        );
    }

    @Nullable
    private GlobalPos resolveGlobalTarget(ClientLevel level, ItemStack stack, Entity entity) {
        OreCompassTrackingResult result = resolveTracking(stack, level, entity);
        if (!result.tracking()) {
            return null;
        }

        return OreCompassStateAccess.readTarget(stack)
            .map(target -> GlobalPos.of(level.dimension(), target.pos()))
            .orElse(null);
    }
}
