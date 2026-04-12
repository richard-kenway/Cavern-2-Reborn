package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.minecraft.core.Direction;

class CollisionApproachFacingResolverTest {
    @Test
    void resolveUsesPortalNormalMotionForXAxisPortal() {
        String approachFacing = CollisionApproachFacingResolver.resolve(Direction.Axis.X, 0.0D, 0.12D, Direction.SOUTH);

        assertEquals("north", approachFacing);
    }

    @Test
    void resolveUsesPortalNormalMotionForZAxisPortal() {
        String approachFacing = CollisionApproachFacingResolver.resolve(Direction.Axis.Z, 0.15D, 0.0D, Direction.EAST);

        assertEquals("west", approachFacing);
    }

    @Test
    void resolveFallsBackToLookDirectionWhenMotionIsUnavailable() {
        String approachFacing = CollisionApproachFacingResolver.resolve(Direction.Axis.X, 0.0D, 0.0D, Direction.SOUTH);

        assertEquals("north", approachFacing);
    }
}
