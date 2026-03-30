package com.richardkenway.cavernreborn.app.portal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SafeArrivalWorldProbeTest {
    @Test
    void constructorRejectsNullLevel() {
        assertThrows(NullPointerException.class, () -> new SafeArrivalWorldProbe(null));
    }

    @Test
    void passableForBodyRejectsNullState() {
        assertFalse(SafeArrivalWorldProbe.isPassableForBody(null));
    }

    @Test
    void safeGroundRejectsNullState() {
        assertFalse(SafeArrivalWorldProbe.isSafeGround(null));
    }
}
