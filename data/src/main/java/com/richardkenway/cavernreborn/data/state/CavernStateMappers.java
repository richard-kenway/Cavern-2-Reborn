package com.richardkenway.cavernreborn.data.state;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.richardkenway.cavernreborn.core.state.PortalReturnState;
import com.richardkenway.cavernreborn.core.state.PortalWorldIndex;
import com.richardkenway.cavernreborn.core.state.TeleportContext;

public final class CavernStateMappers {
    private CavernStateMappers() {
    }

    public static PortalReturnStateData toData(PortalReturnState state) {
        return new PortalReturnStateData(state.portalKey(), state.returnDimensionId(), state.returnX(), state.returnY(), state.returnZ());
    }

    public static PortalReturnState fromData(PortalReturnStateData data) {
        return new PortalReturnState(data.portalKey(), data.returnDimensionId(), data.returnX(), data.returnY(), data.returnZ());
    }

    public static TeleportContextData toData(TeleportContext context) {
        return new TeleportContextData(
            context.portalKey(),
            context.entryOffsetX(),
            context.entryOffsetY(),
            context.entryOffsetZ(),
            context.approachFacing()
        );
    }

    public static TeleportContext fromData(TeleportContextData data) {
        return new TeleportContext(
            data.portalKey(),
            data.entryOffsetX(),
            data.entryOffsetY(),
            data.entryOffsetZ(),
            data.approachFacing()
        );
    }

    public static PortalWorldIndexData toData(PortalWorldIndex index) {
        Map<String, Set<PortalWorldIndexData.PortalPlacementData>> portalsByKey = new LinkedHashMap<>();
        index.portalsByKey().forEach((portalKey, placements) -> {
            Set<PortalWorldIndexData.PortalPlacementData> mappedPlacements = new LinkedHashSet<>();
            for (PortalWorldIndex.PortalPlacement placement : placements) {
                mappedPlacements.add(new PortalWorldIndexData.PortalPlacementData(placement.x(), placement.y(), placement.z()));
            }
            portalsByKey.put(portalKey, mappedPlacements);
        });

        return new PortalWorldIndexData(portalsByKey);
    }

    public static PortalWorldIndex fromData(PortalWorldIndexData data) {
        Map<String, Set<PortalWorldIndex.PortalPlacement>> portalsByKey = new LinkedHashMap<>();
        data.portalsByKey().forEach((portalKey, placements) -> {
            Set<PortalWorldIndex.PortalPlacement> mappedPlacements = new LinkedHashSet<>();
            for (PortalWorldIndexData.PortalPlacementData placement : placements) {
                mappedPlacements.add(new PortalWorldIndex.PortalPlacement(placement.x(), placement.y(), placement.z()));
            }
            portalsByKey.put(portalKey, mappedPlacements);
        });

        return new PortalWorldIndex(portalsByKey);
    }
}
