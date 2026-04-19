package com.richardkenway.cavernreborn.app.progression;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.richardkenway.cavernreborn.core.progression.CavernCatalogEntry;
import com.richardkenway.cavernreborn.core.progression.CavernCatalogUseResult;
import com.richardkenway.cavernreborn.core.progression.CavernInteractionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionService;
import com.richardkenway.cavernreborn.core.progression.CavernProgressionSnapshot;

import net.minecraft.server.level.ServerPlayer;

public final class CavernCatalogAccess {
    private final CavernProgressionService progressionService;
    private final CavernInteractionService interactionService;
    private final CavernRewardGranter rewardGranter;

    public CavernCatalogAccess(
        CavernProgressionService progressionService,
        CavernInteractionService interactionService,
        CavernRewardGranter rewardGranter
    ) {
        this.progressionService = Objects.requireNonNull(progressionService, "progressionService");
        this.interactionService = Objects.requireNonNull(interactionService, "interactionService");
        this.rewardGranter = Objects.requireNonNull(rewardGranter, "rewardGranter");
    }

    public CavernCatalogView inspect(UUID playerId, long currentTimeMillis) {
        UUID normalizedPlayerId = Objects.requireNonNull(playerId, "playerId");
        CavernProgressionSnapshot snapshot = progressionService.inspect(normalizedPlayerId);
        return new CavernCatalogView(
            snapshot,
            interactionService.inspectCatalog(snapshot, currentTimeMillis)
        );
    }

    public Optional<CavernCatalogUseResult> use(UUID playerId, String entryId, long currentTimeMillis) {
        UUID normalizedPlayerId = Objects.requireNonNull(playerId, "playerId");
        return interactionService.useCatalogEntry(
            progressionService.inspect(normalizedPlayerId),
            entryId,
            currentTimeMillis
        );
    }

    public void grant(ServerPlayer player, CavernCatalogUseResult result) {
        ServerPlayer normalizedPlayer = Objects.requireNonNull(player, "player");
        CavernCatalogUseResult normalizedResult = Objects.requireNonNull(result, "result");
        if (!normalizedResult.granted()) {
            return;
        }
        if (normalizedResult.rewardEntry()) {
            rewardGranter.grant(normalizedPlayer, normalizedResult.reward());
            return;
        }
        rewardGranter.grantService(normalizedPlayer, normalizedResult.service());
    }

    public record CavernCatalogView(
        CavernProgressionSnapshot snapshot,
        List<CavernCatalogEntry> entries
    ) {
        public CavernCatalogView {
            snapshot = Objects.requireNonNull(snapshot, "snapshot");
            entries = List.copyOf(Objects.requireNonNull(entries, "entries"));
        }
    }
}
