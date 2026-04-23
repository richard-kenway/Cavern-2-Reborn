package com.richardkenway.cavernreborn.core.compass;

public record OreCompassTrackingResult(
    OreCompassTrackingDecision decision,
    int angleFrame,
    OreCompassDirection direction,
    int horizontalDistance,
    int verticalOffset
) {
    public boolean tracking() {
        return decision == OreCompassTrackingDecision.TRACKING;
    }
}
