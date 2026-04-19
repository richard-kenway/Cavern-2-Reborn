package com.richardkenway.cavernreborn.app.progression;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.richardkenway.cavernreborn.core.progression.CavernPlayerServiceState;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionRank;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;
import com.richardkenway.cavernreborn.core.progression.CavernServiceAvailability;
import com.richardkenway.cavernreborn.core.progression.CavernServiceEntry;
import com.richardkenway.cavernreborn.core.progression.CavernServiceRequestResult;
import com.richardkenway.cavernreborn.core.progression.CavernServiceStatus;

class CavernServiceRequestFeedbackFormatterTest {
    @Test
    void formatGrantedResult() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(playerId, 5, 30, CavernProgressionRank.APPRENTICE, Map.of());
        CavernServiceStatus prevStatus = new CavernServiceStatus(CavernServiceEntry.TORCH_SUPPLY, CavernServiceAvailability.AVAILABLE);
        CavernServiceStatus currStatus = new CavernServiceStatus(CavernServiceEntry.TORCH_SUPPLY, CavernServiceAvailability.ON_COOLDOWN);
        CavernServiceRequestResult result = new CavernServiceRequestResult(snapshot, prevStatus, currStatus, true);

        String formatted = CavernServiceRequestFeedbackFormatter.format(result);

        assertFalse(formatted.isEmpty());
        assertTrue(formatted.contains("Torch Supply"));
    }

    @Test
    void formatLockedResult() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(playerId, 0, 0, CavernProgressionRank.NOVICE, Map.of());
        CavernServiceStatus status = new CavernServiceStatus(CavernServiceEntry.TORCH_SUPPLY, CavernServiceAvailability.LOCKED);
        CavernServiceRequestResult result = new CavernServiceRequestResult(snapshot, status, status, false);

        String formatted = CavernServiceRequestFeedbackFormatter.format(result);

        assertFalse(formatted.isEmpty());
        assertTrue(formatted.contains("LOCKED") || formatted.contains("locked"));
    }

    @Test
    void formatCooldownResult() {
        UUID playerId = UUID.randomUUID();
        CavernProgressionSnapshot snapshot = new CavernProgressionSnapshot(playerId, 5, 30, CavernProgressionRank.APPRENTICE, Map.of());
        CavernServiceStatus status = new CavernServiceStatus(CavernServiceEntry.TORCH_SUPPLY, CavernServiceAvailability.ON_COOLDOWN);
        CavernServiceRequestResult result = new CavernServiceRequestResult(snapshot, status, status, false);

        String formatted = CavernServiceRequestFeedbackFormatter.format(result);

        assertFalse(formatted.isEmpty());
        assertTrue(formatted.contains("cooldown"));
    }
}
