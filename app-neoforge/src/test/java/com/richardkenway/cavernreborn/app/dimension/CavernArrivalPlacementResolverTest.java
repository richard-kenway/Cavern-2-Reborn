package com.richardkenway.cavernreborn.app.dimension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
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

        Optional<CavernPlacementTarget> resolvedTarget = resolver.resolve(placementTarget, new FakeArrivalProbe(Set.of(new SafeArrival(0, 80, 0))));

        assertTrue(resolvedTarget.isPresent());
        assertEquals(placementTarget, resolvedTarget.get());
    }

    @Test
    void resolveMovesUnsafeTargetToNearestSafeY() {
        CavernArrivalPlacementResolver resolver = new CavernArrivalPlacementResolver();
        CavernPlacementTarget placementTarget = new CavernPlacementTarget(CavernDimensions.CAVERN_DIMENSION_ID, 0, 80, 0);

        Optional<CavernPlacementTarget> resolvedTarget = resolver.resolve(placementTarget, new FakeArrivalProbe(Set.of(new SafeArrival(0, 64, 0))));

        assertTrue(resolvedTarget.isPresent());
        assertEquals(new CavernPlacementTarget(CavernDimensions.CAVERN_DIMENSION_ID, 0, 64, 0), resolvedTarget.get());
    }

    @Test
    void resolveSearchesNeighboringColumnsDeterministically() {
        CavernArrivalPlacementResolver resolver = new CavernArrivalPlacementResolver();
        CavernPlacementTarget placementTarget = new CavernPlacementTarget(CavernDimensions.CAVERN_DIMENSION_ID, 0, 80, 0);

        Optional<CavernPlacementTarget> resolvedTarget = resolver.resolve(
            placementTarget,
            new FakeArrivalProbe(Set.of(new SafeArrival(1, 80, 0)))
        );

        assertTrue(resolvedTarget.isPresent());
        assertEquals(new CavernPlacementTarget(CavernDimensions.CAVERN_DIMENSION_ID, 1, 80, 0), resolvedTarget.get());
    }

    @Test
    void resolveReturnsEmptyWhenNoSafeArrivalFound() {
        CavernArrivalPlacementResolver resolver = new CavernArrivalPlacementResolver();
        CavernPlacementTarget placementTarget = new CavernPlacementTarget(CavernDimensions.CAVERN_DIMENSION_ID, 0, 80, 0);

        Optional<CavernPlacementTarget> resolvedTarget = resolver.resolve(placementTarget, new FakeArrivalProbe(Set.of()));

        assertTrue(resolvedTarget.isEmpty());
    }

    private static final class FakeArrivalProbe implements CavernArrivalPlacementProbe {
        private final Set<SafeArrival> safeArrivals;

        private FakeArrivalProbe(Set<SafeArrival> safeArrivals) {
            this.safeArrivals = safeArrivals;
        }

        @Override
        public boolean isSafeArrivalAt(String targetDimensionId, int x, int y, int z) {
            return CavernDimensions.CAVERN_DIMENSION_ID.equals(targetDimensionId)
                && safeArrivals.contains(new SafeArrival(x, y, z));
        }
    }

    private record SafeArrival(int x, int y, int z) {
    }
}
