package com.richardkenway.cavernreborn.core.mining;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

class MiningAssistPolicyTest {
    @Test
    void enabledPathRequiresCavernUnlockToolAndAllowedTarget() {
        assertEquals(
            MiningAssistDecision.ENABLED,
            MiningAssistPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                MiningAssistPolicy.REQUIRED_TOOL_ID,
                true,
                false,
                false,
                true,
                true
            )
        );
    }

    @Test
    void wrongDimensionDisablesAssist() {
        assertEquals(
            MiningAssistDecision.WRONG_DIMENSION,
            MiningAssistPolicy.evaluate(
                CavernDimensions.OVERWORLD_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                MiningAssistPolicy.REQUIRED_TOOL_ID,
                true,
                false,
                false,
                true,
                true
            )
        );
    }

    @Test
    void noPlayerContextDisablesAssist() {
        assertEquals(
            MiningAssistDecision.NO_PLAYER_CONTEXT,
            MiningAssistPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                MiningAssistPolicy.REQUIRED_TOOL_ID,
                false,
                false,
                false,
                true,
                true
            )
        );
    }

    @Test
    void creativeModeDisablesAssist() {
        assertEquals(
            MiningAssistDecision.CREATIVE_MODE,
            MiningAssistPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                MiningAssistPolicy.REQUIRED_TOOL_ID,
                true,
                true,
                false,
                true,
                true
            )
        );
    }

    @Test
    void sneakingDisablesAssist() {
        assertEquals(
            MiningAssistDecision.SNEAKING_DISABLED,
            MiningAssistPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                MiningAssistPolicy.REQUIRED_TOOL_ID,
                true,
                false,
                true,
                true,
                true
            )
        );
    }

    @Test
    void missingUnlockDisablesAssist() {
        assertEquals(
            MiningAssistDecision.MISSING_UNLOCK,
            MiningAssistPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                MiningAssistPolicy.REQUIRED_TOOL_ID,
                true,
                false,
                false,
                false,
                true
            )
        );
    }

    @Test
    void wrongToolDisablesAssist() {
        assertEquals(
            MiningAssistDecision.WRONG_TOOL,
            MiningAssistPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:hexcite_ore",
                "minecraft:diamond_pickaxe",
                true,
                false,
                false,
                true,
                true
            )
        );
    }

    @Test
    void unsupportedBlockDisablesAssist() {
        assertEquals(
            MiningAssistDecision.UNSUPPORTED_BLOCK,
            MiningAssistPolicy.evaluate(
                CavernDimensions.CAVERN_DIMENSION_ID,
                "cavernreborn:fissured_stone",
                MiningAssistPolicy.REQUIRED_TOOL_ID,
                true,
                false,
                false,
                true,
                false
            )
        );
    }

    @Test
    void maxExtraBlockCapRemainsBounded() {
        assertEquals(6, MiningAssistPolicy.MAX_EXTRA_BLOCKS);
    }

    @Test
    void decisionOrderRemainsDeterministic() {
        assertEquals(
            MiningAssistDecision.WRONG_DIMENSION,
            MiningAssistPolicy.evaluate(
                CavernDimensions.OVERWORLD_DIMENSION_ID,
                "cavernreborn:fissured_stone",
                "minecraft:wooden_pickaxe",
                false,
                true,
                true,
                false,
                false
            )
        );
    }
}
