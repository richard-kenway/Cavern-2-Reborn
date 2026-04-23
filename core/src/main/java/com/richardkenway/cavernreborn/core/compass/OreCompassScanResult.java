package com.richardkenway.cavernreborn.core.compass;

public record OreCompassScanResult(
    OreCompassScanDecision decision,
    String targetBlockId,
    int horizontalDistance,
    int verticalOffset,
    OreCompassDirection direction
) {
    public OreCompassScanResult {
        if (horizontalDistance < 0) {
            throw new IllegalArgumentException("horizontalDistance must not be negative");
        }
    }

    public boolean found() {
        return decision == OreCompassScanDecision.FOUND;
    }
}
