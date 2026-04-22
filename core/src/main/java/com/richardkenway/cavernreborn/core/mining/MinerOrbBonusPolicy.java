package com.richardkenway.cavernreborn.core.mining;

import java.util.Objects;

import com.richardkenway.cavernreborn.core.state.CavernDimensions;

public final class MinerOrbBonusPolicy {
    public static final int BONUS_ROLL_BOUND = 10;

    private MinerOrbBonusPolicy() {
    }

    public static MinerOrbBonusResult evaluate(
        String dimensionId,
        String blockId,
        int baseScore,
        boolean countedBlock,
        boolean hasMinerOrb,
        boolean creative,
        boolean miningAssistSuppressed,
        int roll
    ) {
        requireText(dimensionId, "dimensionId");
        requireText(blockId, "blockId");
        if (roll < 0 || roll >= BONUS_ROLL_BOUND) {
            throw new IllegalArgumentException("roll must be between 0 and " + (BONUS_ROLL_BOUND - 1));
        }

        if (!CavernDimensions.isCavern(dimensionId)) {
            return new MinerOrbBonusResult(MinerOrbBonusDecision.WRONG_DIMENSION, 0);
        }
        if (!countedBlock) {
            return new MinerOrbBonusResult(MinerOrbBonusDecision.UNCOUNTED_BLOCK, 0);
        }
        if (baseScore <= 0) {
            return new MinerOrbBonusResult(MinerOrbBonusDecision.NO_BASE_SCORE, 0);
        }
        if (!hasMinerOrb) {
            return new MinerOrbBonusResult(MinerOrbBonusDecision.NO_MINER_ORB, 0);
        }
        if (creative) {
            return new MinerOrbBonusResult(MinerOrbBonusDecision.CREATIVE_MODE, 0);
        }
        if (miningAssistSuppressed) {
            return new MinerOrbBonusResult(MinerOrbBonusDecision.MINING_ASSIST_SUPPRESSED, 0);
        }
        if (roll != 0) {
            return new MinerOrbBonusResult(MinerOrbBonusDecision.ROLL_MISSED, 0);
        }

        return new MinerOrbBonusResult(MinerOrbBonusDecision.TRIGGERED, Math.max(baseScore / 2, 1));
    }

    private static String requireText(String value, String fieldName) {
        String normalized = Objects.requireNonNull(value, fieldName);
        if (normalized.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return normalized;
    }
}
