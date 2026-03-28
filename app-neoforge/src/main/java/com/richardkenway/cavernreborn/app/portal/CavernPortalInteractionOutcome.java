package com.richardkenway.cavernreborn.app.portal;

import java.util.Objects;
import java.util.Optional;

import com.richardkenway.cavernreborn.core.state.CavernTravelPlan;

public record CavernPortalInteractionOutcome(boolean handled, Optional<CavernTravelPlan> travelPlan) {
    public CavernPortalInteractionOutcome {
        Objects.requireNonNull(travelPlan, "travelPlan");
    }

    public static CavernPortalInteractionOutcome handled(Optional<CavernTravelPlan> travelPlan) {
        return new CavernPortalInteractionOutcome(true, travelPlan);
    }

    public static CavernPortalInteractionOutcome unhandled() {
        return new CavernPortalInteractionOutcome(false, Optional.empty());
    }
}
