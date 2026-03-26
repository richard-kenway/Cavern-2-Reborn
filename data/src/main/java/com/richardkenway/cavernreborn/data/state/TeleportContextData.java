package com.richardkenway.cavernreborn.data.state;

import java.util.Objects;

public record TeleportContextData(
    String portalKey,
    String sourceDimensionId,
    int sourceX,
    int sourceY,
    int sourceZ,
    float entryYaw,
    float entryPitch
) {
    public TeleportContextData {
        portalKey = requireText(portalKey, "portalKey");
        sourceDimensionId = requireText(sourceDimensionId, "sourceDimensionId");
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }
}
