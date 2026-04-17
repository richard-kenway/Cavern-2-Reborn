package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

class PortalPlacementQualityScorerTest {
    @Test
    void prefersCloserCandidateWhenRiskProfileMatches() {
        PortalPlacementQualityScorer.PortalPlacementCandidate closer = new PortalPlacementQualityScorer.PortalPlacementCandidate(
            new BlockPos(0, 64, 0),
            Direction.Axis.X,
            1,
            0,
            4,
            0,
            0
        );
        PortalPlacementQualityScorer.PortalPlacementCandidate farther = new PortalPlacementQualityScorer.PortalPlacementCandidate(
            new BlockPos(4, 64, 4),
            Direction.Axis.X,
            6,
            0,
            4,
            0,
            0
        );

        assertTrue(PortalPlacementQualityScorer.isBetterCandidate(closer, farther));
        assertFalse(PortalPlacementQualityScorer.isBetterCandidate(farther, closer));
    }

    @Test
    void prefersSaferBetterSupportedCandidateOverSlightlyCloserOne() {
        PortalPlacementQualityScorer.PortalPlacementCandidate safer = new PortalPlacementQualityScorer.PortalPlacementCandidate(
            new BlockPos(2, 64, 2),
            Direction.Axis.X,
            3,
            0,
            4,
            0,
            0
        );
        PortalPlacementQualityScorer.PortalPlacementCandidate risky = new PortalPlacementQualityScorer.PortalPlacementCandidate(
            new BlockPos(1, 64, 1),
            Direction.Axis.X,
            2,
            0,
            1,
            1,
            0
        );

        assertTrue(PortalPlacementQualityScorer.isBetterCandidate(safer, risky));
        assertFalse(PortalPlacementQualityScorer.isBetterCandidate(risky, safer));
    }

    @Test
    void prefersLessDestructiveCandidateWhenDistanceMatches() {
        PortalPlacementQualityScorer.PortalPlacementCandidate cleaner = new PortalPlacementQualityScorer.PortalPlacementCandidate(
            new BlockPos(0, 64, 0),
            Direction.Axis.Z,
            2,
            1,
            3,
            0,
            0
        );
        PortalPlacementQualityScorer.PortalPlacementCandidate destructive = new PortalPlacementQualityScorer.PortalPlacementCandidate(
            new BlockPos(1, 65, 1),
            Direction.Axis.Z,
            2,
            1,
            3,
            0,
            4
        );

        assertTrue(PortalPlacementQualityScorer.isBetterCandidate(cleaner, destructive));
        assertFalse(PortalPlacementQualityScorer.isBetterCandidate(destructive, cleaner));
    }

    @Test
    void prefersPreferredAxisWhenReplacementCandidatesAreOtherwiseEqual() {
        PortalPlacementQualityScorer.PortalPlacementCandidate xAxis = new PortalPlacementQualityScorer.PortalPlacementCandidate(
            new BlockPos(0, 64, 0),
            Direction.Axis.X,
            2,
            0,
            4,
            0,
            0
        );
        PortalPlacementQualityScorer.PortalPlacementCandidate zAxis = new PortalPlacementQualityScorer.PortalPlacementCandidate(
            new BlockPos(0, 64, 0),
            Direction.Axis.Z,
            2,
            0,
            4,
            0,
            0
        );

        assertTrue(PortalPlacementQualityScorer.isBetterCandidate(zAxis, xAxis, Direction.Axis.Z));
        assertFalse(PortalPlacementQualityScorer.isBetterCandidate(xAxis, zAxis, Direction.Axis.Z));
    }

    @Test
    void requiresReplacementContractIncludesExplicitFrameBlockParameter() throws Exception {
        Method requiresReplacement = PortalPlacementQualityScorer.class.getDeclaredMethod(
            "requiresReplacement",
            BlockState.class,
            Block.class,
            Block.class
        );

        assertNotNull(requiresReplacement);
    }
}
