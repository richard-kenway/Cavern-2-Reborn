package com.richardkenway.cavernreborn.core.state;

import java.util.Objects;
import java.util.Optional;

public record CavernTravelPlan(
    CavernTravelDirection direction,
    String targetDimensionId,
    int targetX,
    int targetY,
    int targetZ,
    Optional<PortalEntryReceipt> entryReceipt,
    Optional<PortalReturnOperation> returnOperation
) {
    public CavernTravelPlan {
        direction = Objects.requireNonNull(direction, "direction");
        targetDimensionId = requireText(targetDimensionId, "targetDimensionId");
        entryReceipt = Objects.requireNonNull(entryReceipt, "entryReceipt");
        returnOperation = Objects.requireNonNull(returnOperation, "returnOperation");

        switch (direction) {
            case ENTER_CAVERN -> {
                if (entryReceipt.isEmpty() || returnOperation.isPresent()) {
                    throw new IllegalArgumentException("ENTER_CAVERN plan requires only an entry receipt");
                }
            }
            case RETURN_HOME -> {
                if (returnOperation.isEmpty() || entryReceipt.isPresent()) {
                    throw new IllegalArgumentException("RETURN_HOME plan requires only a return operation");
                }
            }
            default -> throw new IllegalStateException("Unsupported travel direction: " + direction);
        }
    }

    public static CavernTravelPlan enterCavern(PortalEntryReceipt entryReceipt) {
        Objects.requireNonNull(entryReceipt, "entryReceipt");
        return new CavernTravelPlan(
            CavernTravelDirection.ENTER_CAVERN,
            CavernDimensions.CAVERN_DIMENSION_ID,
            CavernDimensions.CAVERN_ENTRY_X,
            CavernDimensions.CAVERN_ENTRY_Y,
            CavernDimensions.CAVERN_ENTRY_Z,
            Optional.of(entryReceipt),
            Optional.empty()
        );
    }

    public static CavernTravelPlan returnHome(PortalReturnOperation returnOperation) {
        Objects.requireNonNull(returnOperation, "returnOperation");
        return new CavernTravelPlan(
            CavernTravelDirection.RETURN_HOME,
            returnOperation.targetDimensionId(),
            returnOperation.targetX(),
            returnOperation.targetY(),
            returnOperation.targetZ(),
            Optional.empty(),
            Optional.of(returnOperation)
        );
    }

    public boolean isEnterCavern() {
        return direction == CavernTravelDirection.ENTER_CAVERN;
    }

    public boolean isReturnHome() {
        return direction == CavernTravelDirection.RETURN_HOME;
    }

    public boolean usesWorldIndex() {
        return returnOperation.map(PortalReturnOperation::usesWorldIndex).orElse(false);
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

        return value;
    }
}
