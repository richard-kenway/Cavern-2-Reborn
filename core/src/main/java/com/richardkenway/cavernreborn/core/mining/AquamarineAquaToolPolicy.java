package com.richardkenway.cavernreborn.core.mining;

public final class AquamarineAquaToolPolicy {
    public static final float AQUA_BREAK_SPEED_MULTIPLIER = 10.0F;
    public static final float AQUA_AFFINITY_DAMPENER = 0.5F;

    private AquamarineAquaToolPolicy() {
    }

    public static AquamarineAquaToolResult evaluate(
        boolean hasAquamarineTool,
        boolean submerged,
        boolean hasAquaAffinity,
        float originalSpeed
    ) {
        if (!hasAquamarineTool) {
            return new AquamarineAquaToolResult(AquamarineAquaToolDecision.NO_TOOL, originalSpeed);
        }
        if (!submerged) {
            return new AquamarineAquaToolResult(AquamarineAquaToolDecision.NOT_SUBMERGED, originalSpeed);
        }
        if (!Float.isFinite(originalSpeed) || originalSpeed <= 0.0F) {
            return new AquamarineAquaToolResult(AquamarineAquaToolDecision.INVALID_SPEED, originalSpeed);
        }

        float adjustedSpeed = originalSpeed * AQUA_BREAK_SPEED_MULTIPLIER;
        if (hasAquaAffinity) {
            adjustedSpeed *= AQUA_AFFINITY_DAMPENER;
        }
        return new AquamarineAquaToolResult(AquamarineAquaToolDecision.BOOSTED, adjustedSpeed);
    }
}
