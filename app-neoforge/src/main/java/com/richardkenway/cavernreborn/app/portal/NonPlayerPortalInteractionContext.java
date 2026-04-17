package com.richardkenway.cavernreborn.app.portal;

import java.util.Optional;

import com.richardkenway.cavernreborn.core.state.PortalEntryReceipt;

public interface NonPlayerPortalInteractionContext extends CavernPortalInteractionContext, CavernArrivalPlacementProbe {
    Optional<PortalEntryReceipt> loadPortalEntryReceipt();

    void savePortalEntryReceipt(PortalEntryReceipt entryReceipt);

    void clearPortalEntryReceipt();
}

