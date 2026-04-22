package com.richardkenway.cavernreborn.core.mining;

import java.util.Objects;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

public final class MiningAssistPolicy {
    public static final String REQUIRED_TOOL_ID = "cavernreborn:hexcite_pickaxe";
    public static final int MAX_EXTRA_BLOCKS = 6;

    private MiningAssistPolicy() {
    }

    public static MiningAssistDecision evaluate(
        String dimensionId,
        String blockId,
        String toolId,
        boolean playerContext,
        boolean creative,
        boolean sneaking,
        boolean hasMiningAssistUnlock,
        boolean targetBlockAllowed
    ) {
        requireText(dimensionId, "dimensionId");
        requireText(blockId, "blockId");
        requireText(toolId, "toolId");

        if (!CavernDimensions.isCavern(dimensionId)) {
            return MiningAssistDecision.WRONG_DIMENSION;
        }
        if (!playerContext) {
            return MiningAssistDecision.NO_PLAYER_CONTEXT;
        }
        if (creative) {
            return MiningAssistDecision.CREATIVE_MODE;
        }
        if (sneaking) {
            return MiningAssistDecision.SNEAKING_DISABLED;
        }
        if (!hasMiningAssistUnlock) {
            return MiningAssistDecision.MISSING_UNLOCK;
        }
        if (!REQUIRED_TOOL_ID.equals(toolId)) {
            return MiningAssistDecision.WRONG_TOOL;
        }
        if (!targetBlockAllowed) {
            return MiningAssistDecision.UNSUPPORTED_BLOCK;
        }

        return MiningAssistDecision.ENABLED;
    }

    private static String requireText(String value, String fieldName) {
        String normalized = Objects.requireNonNull(value, fieldName);
        if (normalized.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return normalized;
    }
}
