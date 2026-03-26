package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.app.portal.CavernArrivalPlacementProbe;
import com.richardkenway.cavernreborn.core.state.CavernDimensions;
import com.richardkenway.cavernreborn.core.state.CavernPlacementTarget;

class CavernArrivalPlacementResolverTest {
    @Test
    void resolveKeepsAlreadySafeTarget() {
        CavernArrivalPlacementResolver resolver = new CavernArrivalPlacementResolver();
        CavernPlacementTarget placementTarget = new CavernPlacementTarget(CavernDimensions.CAVERN_DIMENSION_ID, 0, 80, 0);

        CavernPlacementTarget resolvedTarget = resolver.resolve(placementTarget, new FakeArrivalProbe(Set.of(80)));

        assertEquals(placementTarget, resolvedTarget);
    }

    @Test
    void resolveMovesUnsafeTargetToNearestSafeY() {
        CavernArrivalPlacementResolver resolver = new CavernArrivalPlacementResolver();
        CavernPlacementTarget placementTarget = new CavernPlacementTarget(CavernDimensions.CAVERN_DIMENSION_ID, 0, 80, 0);

        CavernPlacementTarget resolvedTarget = resolver.resolve(placementTarget, new FakeArrivalProbe(Set.of(64)));

        assertEquals(new CavernPlacementTarget(CavernDimensions.CAVERN_DIMENSION_ID, 0, 64, 0), resolvedTarget);
    }

    @Test
    void resolveFallsBackToOriginalTargetWhenNoSafeArrivalFound() {
        CavernArrivalPlacementResolver resolver = new CavernArrivalPlacementResolver();
        CavernPlacementTarget placementTarget = new CavernPlacementTarget(CavernDimensions.CAVERN_DIMENSION_ID, 0, 80, 0);

        CavernPlacementTarget resolvedTarget = resolver.resolve(placementTarget, new FakeArrivalProbe(Set.of()));

        assertEquals(placementTarget, resolvedTarget);
    }

    private static final class FakeArrivalProbe implements CavernArrivalPlacementProbe {
        private final Set<Integer> safeYs;

        private FakeArrivalProbe(Set<Integer> safeYs) {
            this.safeYs = safeYs;
        }

        @Override
        public boolean isSafeArrivalAt(String targetDimensionId, int x, int y, int z) {
            return CavernDimensions.CAVERN_DIMENSION_ID.equals(targetDimensionId) && safeYs.contains(y);
        }
    }
}
