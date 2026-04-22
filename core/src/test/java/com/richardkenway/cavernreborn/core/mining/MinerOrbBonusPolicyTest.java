package com.richardkenway.cavernreborn.core.mining;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class MinerOrbBonusPolicyTest {
    @Test
    void triggeredPathUsesBoundedHalfScoreBonus() {
        MinerOrbBonusResult result = MinerOrbBonusPolicy.evaluate(
            CavernDimensions.CAVERN_DIMENSION_ID,
            "cavernreborn:hexcite_ore",
            4,
            true,
            true,
            false,
            false,
            0
        );

        assertEquals(MinerOrbBonusDecision.TRIGGERED, result.decision());
        assertEquals(2, result.bonusScore());
    }

    @Test
    void rollMissProducesNoBonus() {
        MinerOrbBonusResult result = MinerOrbBonusPolicy.evaluate(
            CavernDimensions.CAVERN_DIMENSION_ID,
            "cavernreborn:aquamarine_ore",
            2,
            true,
            true,
            false,
            false,
            1
        );

        assertEquals(MinerOrbBonusDecision.ROLL_MISSED, result.decision());
        assertEquals(0, result.bonusScore());
    }

    @Test
    void wrongDimensionDisablesBonus() {
        assertEquals(
            MinerOrbBonusDecision.WRONG_DIMENSION,
            MinerOrbBonusPolicy.evaluate(
                CavernDimensions.OVERWORLD_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                4,
                true,
                true,
                false,
                false,
                0
            ).decision()
        );
    }

    @Test
    void uncountedBlockDisablesBonus() {
        assertEquals(
            MinerOrbBonusDecision.UNCOUNTED_BLOCK,
            MinerOrbBonusPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "minecraft:stone",
                0,
                false,
                true,
                false,
                false,
                0
            ).decision()
        );
    }

    @Test
    void noOrbDisablesBonus() {
        assertEquals(
            MinerOrbBonusDecision.NO_MINER_ORB,
            MinerOrbBonusPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "minecraft:diamond_ore",
                5,
                true,
                false,
                false,
                false,
                0
            ).decision()
        );
    }

    @Test
    void creativeDisablesBonus() {
        assertEquals(
            MinerOrbBonusDecision.CREATIVE_MODE,
            MinerOrbBonusPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "minecraft:diamond_ore",
                5,
                true,
                true,
                true,
                false,
                0
            ).decision()
        );
    }

    @Test
    void miningAssistSuppressedBreakDisablesBonus() {
        assertEquals(
            MinerOrbBonusDecision.MINING_ASSIST_SUPPRESSED,
            MinerOrbBonusPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                4,
                true,
                true,
                false,
                true,
                0
            ).decision()
        );
    }

    @Test
    void zeroBaseScoreDisablesBonus() {
        assertEquals(
            MinerOrbBonusDecision.NO_BASE_SCORE,
            MinerOrbBonusPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "minecraft:stone",
                0,
                true,
                true,
                false,
                false,
                0
            ).decision()
        );
    }

    @Test
    void baseScoreOneStillGrantsBonusOne() {
        assertEquals(
            1,
            MinerOrbBonusPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:magnite_ore",
                1,
                true,
                true,
                false,
                false,
                0
            ).bonusScore()
        );
    }

    @Test
    void invalidRollThrows() {
        assertThrows(
            IllegalArgumentException.class,
            () -> MinerOrbBonusPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                4,
                true,
                true,
                false,
                false,
                MinerOrbBonusPolicy.BONUS_ROLL_BOUND
            )
        );
    }

    @Test
    void bonusRollBoundRemainsTen() {
        assertEquals(10, MinerOrbBonusPolicy.BONUS_ROLL_BOUND);
    }
}
