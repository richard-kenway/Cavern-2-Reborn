package com.richardkenway.cavernreborn.data.state;

import java.util.Objects;

public record PortalReturnStateData(String portalKey, String returnDimensionId, int returnX, int returnY, int returnZ) {
    public PortalReturnStateData {
        portalKey = requireText(portalKey, "portalKey");
        returnDimensionId = requireText(returnDimensionId, "returnDimensionId");
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }
}
