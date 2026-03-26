package com.richardkenway.cavernreborn.data.state;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record PortalWorldIndexData(Map<String, Set<PortalPlacementData>> portalsByKey) {
    public PortalWorldIndexData {
        portalsByKey = copyIndex(portalsByKey);
    }

    public static PortalWorldIndexData empty() {
        return new PortalWorldIndexData(Map.of());
    }

    private static Map<String, Set<PortalPlacementData>> copyIndex(Map<String, Set<PortalPlacementData>> source) {
        Objects.requireNonNull(source, "portalsByKey");
        Map<String, Set<PortalPlacementData>> copy = new LinkedHashMap<>();
        source.forEach((portalKey, placements) -> copy.put(
            requireText(portalKey, "portalKey"),
            Collections.unmodifiableSet(new LinkedHashSet<>(Objects.requireNonNull(placements, "placements")))
        ));
        return Collections.unmodifiableMap(copy);
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }

    public record PortalPlacementData(int x, int y, int z) {
    }
}
