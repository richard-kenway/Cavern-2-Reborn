package com.richardkenway.cavernreborn.core.combat;

public enum CavenicBowMode {
    NORMAL("normal"),
    RAPID("rapid"),
    SNIPE("snipe"),
    TORCH("torch");

    private final String serializedId;

    CavenicBowMode(String serializedId) {
        this.serializedId = serializedId;
    }

    public String serializedId() {
        return serializedId;
    }

    public CavenicBowMode next() {
        CavenicBowMode[] modes = values();
        return modes[(ordinal() + 1) % modes.length];
    }

    public static CavenicBowMode fromSerializedId(String serializedId) {
        if (serializedId == null || serializedId.isBlank()) {
            return NORMAL;
        }

        for (CavenicBowMode mode : values()) {
            if (mode.serializedId.equals(serializedId)) {
                return mode;
            }
        }

        return NORMAL;
    }
}
