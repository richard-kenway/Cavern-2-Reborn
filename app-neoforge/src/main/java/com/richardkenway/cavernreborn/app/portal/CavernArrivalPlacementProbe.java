package com.richardkenway.cavernreborn.app.portal;

public interface CavernArrivalPlacementProbe {
    boolean isSafeArrivalAt(String targetDimensionId, int x, int y, int z);
}
