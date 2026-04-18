package com.richardkenway.cavernreborn.core.progression;

public record CavernRewardGrant(String itemId, int count) {
    public CavernRewardGrant {
        itemId = requireText(itemId, "itemId");
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive");
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
