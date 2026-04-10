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

    public record PortalPlacementData(int x, int y, int z, String axis) {
        public PortalPlacementData(int x, int y, int z) {
            this(x, y, z, PortalWorldIndexAxisDefaults.AXIS_X);
        }

        public PortalPlacementData {
            axis = PortalWorldIndexAxisDefaults.normalize(axis);
        }
    }

    private static final class PortalWorldIndexAxisDefaults {
        private static final String AXIS_X = "x";
        private static final String AXIS_Z = "z";

        private PortalWorldIndexAxisDefaults() {
        }

        private static String normalize(String axis) {
            String normalizedAxis = requireText(axis, "axis").toLowerCase(java.util.Locale.ROOT);
            if (AXIS_X.equals(normalizedAxis) || AXIS_Z.equals(normalizedAxis)) {
                return normalizedAxis;
            }

            throw new IllegalArgumentException("axis must be 'x' or 'z'");
        }
    }
}
