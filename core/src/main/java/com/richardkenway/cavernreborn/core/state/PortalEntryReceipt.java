package com.richardkenway.cavernreborn.core.state;

import java.util.Objects;

public record PortalEntryReceipt(
    PortalReturnState returnState,
    TeleportContext teleportContext,
    PortalWorldIndex.PortalPlacement sourcePortalPlacement
) {
    public PortalEntryReceipt {
        returnState = Objects.requireNonNull(returnState, "returnState");
        teleportContext = Objects.requireNonNull(teleportContext, "teleportContext");
        sourcePortalPlacement = Objects.requireNonNull(sourcePortalPlacement, "sourcePortalPlacement");
    }
}
