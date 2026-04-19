# CAVERN Progression Baseline

This document fixes the current `CAVERN` progression baseline on `main`: the narrow backend shell plus the first player-facing, gameplay-visible, reward-visible and compact catalog-visible layers built on top of it.

It is not a claim of full legacy gameplay parity. It documents the bounded progression slice that is currently implemented, persisted and test-covered.

## Expected Behavior

- Progression is player-scoped and cavern-scoped.
- A new player reads as an empty progression state with `score = 0`, `counted_blocks = 0` and `rank = novice`.
- The runtime input is the uncanceled server-side `BlockEvent.BreakEvent` for a non-creative player.
- Only qualifying block breaks inside `cavernreborn:cavern` count toward this baseline.
- The current counted block table is fixed in code and intentionally small:
  - coal and copper ores: `+1`
  - iron and redstone ores: `+2`
  - gold and lapis ores: `+3`
  - `raw_copper_block`: `+4`
  - diamond ores: `+5`
  - emerald ores and `raw_iron_block`: `+6`
- Unsupported blocks, block breaks outside `CAVERN`, creative-mode mining and non-player activity do not advance progression.
- Each counted mining event increments `counted_blocks` by exactly `1` and adds the deterministic block score to `progression_score`.
- Rank is derived from the persisted score with fixed thresholds:
  - `novice`: `0`
  - `apprentice`: `25`
  - `journeyman`: `75`
  - `veteran`: `175`
  - `master`: `325`
- `/cavern rank` exposes the same persisted state through a compact player-facing summary, while `/cavern progression` remains the verbose developer/debug view.
- `/cavern rewards` exposes the same persisted state through a compact reward summary, while `/cavern claim <reward>` is the current bounded claim path.
- `/cavern services` exposes available services with their availability state, while `/cavern request <service>` is the bounded service use path.
- `/cavern catalog` aggregates the current reward and service surface into one compact shop-like summary, while `/cavern use <entry>` is the unified use path for the same checked-in entries.
- The first service is `torch_supply`, available at `apprentice`, repeatable with a 10-minute cooldown and granting torch x16.
- Threshold crossing sends a rank-up overlay for the affected player.
- The first unlock is `Miner's Insight`.
  - source-of-truth: `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionUnlock.java`
  - unlock threshold: `apprentice`
  - gameplay consequence: counted ore breaks inside `CAVERN` grant `+1` bonus XP once the rank is unlocked
- The threshold-crossing ore already receives the unlock, because the bonus check runs against the updated persisted snapshot for that same counted mining event.
- The first reward is `apprentice_supply_cache`.
  - source-of-truth: `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionReward.java`
  - reward threshold: `apprentice`
  - claim mode: one-time
  - current grant bundle: `torch x16, bread x8`
- Reward eligibility is derived from the same persisted score/rank as `/cavern rank`; the reward layer does not store a second rank or unlock field.
- Only claimed reward ids are persisted. Eligibility and current status are recomputed from the stored progression score plus the claimed reward set.
- Only claimed reward ids and per-service last-use timestamps are persisted. Catalog eligibility and current status are recomputed from the stored progression score, claimed reward ids and service timestamps.
- Player state survives restart through the same overworld-level `SavedData` control plane already used by the portal baseline.
- After restart, the same player continues from the stored score/counters instead of reinitializing.

## Supported Cases

- Empty state for a new player before any counted cavern mining.
- Cavern mining updates score, counted blocks and per-block counters.
- Mining the same baseline ore outside `CAVERN` does not affect cavern-specific progression.
- Multiple players advance independently without shared counters.
- Threshold crossing updates the derived rank deterministically.
- Threshold crossing shows player-facing rank-up feedback exactly once for that crossing event.
- `/cavern rank` stays consistent with `/cavern progression` because both read the same persisted snapshot.
- `Miner's Insight` remains active after restart because it is derived from the restored score/rank, not from a separate saved unlock flag.
- A new player below `apprentice` sees `apprentice_supply_cache` as locked.
- Reaching `apprentice` makes `apprentice_supply_cache` available without adding a second rank-state.
- Claiming `apprentice_supply_cache` marks it as claimed exactly once and does not mutate the stored progression score/rank.
- Repeated claim attempts stay safe and predictable by reporting that the reward is already claimed.
- Claimed reward state survives restart and remains consistent with `/cavern rank`, `/cavern progression` and `/cavern rewards`.
- A new player below `apprentice` sees `torch_supply` as locked.
- Reaching `apprentice` makes `torch_supply` available for the first use.
- Using `torch_supply` marks the service as on cooldown for 10 minutes.
- During cooldown, subsequent requests to `torch_supply` report the cooldown state.
- After 10 minutes, `torch_supply` becomes available again for another use.
- Service state survives restart and remains consistent with `/cavern rank` and `/cavern services`.
- `/cavern catalog` shows rewards and services side-by-side without introducing a second rank, reward or service model.
- `/cavern use apprentice_supply_cache` claims the same one-time reward as `/cavern claim apprentice_supply_cache`.
- `/cavern use torch_supply` requests the same repeatable service as `/cavern request torch_supply`.
- `/cavern rank`, `/cavern rewards`, `/cavern services`, `/cavern catalog`, `/cavern claim`, `/cavern request` and `/cavern use` are player-facing interaction paths built on the same progression snapshot.
- One-time rewards and repeatable services do not interfere with each other's eligibility logic.
- Restart-safe persistence and continued progression after restart.
- Minimal server-side inspection through `/cavern progression` / `/cavern progression <player>` and player-facing status through `/cavern rank`, `/cavern rewards` and `/cavern claim <reward>`.

## Runtime Inputs

- Source-of-truth policy and thresholds:
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionPolicy.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionRank.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionReward.java`
- Persistent state model:
  - overworld-level `SavedData` file id: `cavernreborn_control_plane`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernPlayerProgressionState.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernPlayerRewardState.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/state/CavernPersistentStateData.java`
- Runtime accounting hook:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernMiningProgressionEvents.java`
- Debug inspection path:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernProgressionCommands.java`
- Player-facing formatting and feedback:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernPlayerProgressionStatusFormatter.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernPlayerRewardStatusFormatter.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernPlayerCatalogStatusFormatter.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernRewardClaimFeedbackFormatter.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernCatalogUseFeedbackFormatter.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernProgressionRankUpFeedbackFormatter.java`
- Gameplay consequence policy:
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionConsequences.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionUnlock.java`
- Reward policy and claim semantics:
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernRewardService.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernRewardGranter.java`
- Service and interaction surface:
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernCatalogEntry.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernCatalogUseResult.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernServiceEntry.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernInteractionService.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernPlayerServiceState.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernServiceStatus.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernServiceRequestResult.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernPlayerServiceStatusFormatter.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernServiceRequestFeedbackFormatter.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/SavedDataBackedPlayerServiceStateRepository.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/InMemoryPlayerServiceStateRepository.java`
- Service state persistence:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/state/CavernPersistentStateData.java`

## Intentional Compromises

- This pass restores only a small player-visible layer on top of the narrow progression shell, not the old full gameplay stack.
- No GUI, portal menu, shop flow, economy, mining records screen or broad player-facing polish is part of this baseline.
- No new config surface was added; counted blocks, scores and thresholds are checked-in code.
- The baseline counts qualifying block breaks, not item drops, smelting output, trading, pickups or broader activity telemetry.
- Rank is computed from score at read time and is not stored as a separate mutable field.
- The first gameplay consequence is intentionally small: one unlock and one bounded XP bonus instead of a larger perks tree or reward graph.
- The first reward surface is intentionally small: one one-time bundle and one claim path instead of a wider reward tree, currency or shop stack.
- The first service surface is intentionally small: one repeatable service with a simple cooldown instead of a wider service catalog or currency-based shop.
- The first catalog surface is intentionally small: it aggregates existing rewards and services, but it still is not a currency shop, GUI menu or broader transaction system.
- Progression still does not gate portal use, worldgen, loot or broader economy/menu systems.

## Out Of Scope

- New dimensions, new content, progression UI, portal shop/menu and regeneration UI.
- Economy, broader rewards trees, service catalogs, unlock trees, mining leaderboards or persistence migrations for future score-table changes.
- Broader non-ore activity tracking such as mob kills, exploration, crafting or item collection.
- Full legacy parity for miner rank perks, the older menu-driven rank flow or a larger unlock/perks stack.

## Minimal Checklist

Run this before any larger progression or gameplay-shell change:

1. `docker compose run --rm gradle ./gradlew --no-daemon test`
2. Start the local dev server and load a persistent world.
3. Enter `CAVERN` through the current portal flow.
4. Mine at least one counted ore block in `CAVERN`.
5. Run `/cavern rank` and confirm the player-facing summary shows the current rank, score and next threshold.
6. Continue mining until `apprentice` and confirm the rank-up overlay appears and the unlock message mentions `Miner's Insight`.
7. Mine another counted ore in `CAVERN` and confirm the XP bar receives the extra `+1` bonus XP from `Miner's Insight`.
8. Run `/cavern rewards` and confirm `apprentice_supply_cache` is available.
9. Run `/cavern catalog` and confirm `apprentice_supply_cache` and `torch_supply` are both listed as available.
10. Run `/cavern use apprentice_supply_cache` and confirm the reward is granted once.
11. Repeat `/cavern use apprentice_supply_cache` and confirm the command reports an already-claimed state instead of duplicating the reward.
12. Run `/cavern use torch_supply` and confirm the service grant works and the catalog/service views move to cooldown.
13. Run `/cavern progression` and confirm the debug summary still matches the same persisted score/rank.
14. Mine the same ore outside `CAVERN` and confirm neither the bonus XP nor the cavern-specific progression state changes.
15. Restart the server.
16. Run `/cavern rank`, `/cavern progression`, `/cavern rewards`, `/cavern services` or `/cavern catalog` again and confirm the stored progression, claimed reward state and service cooldown state survived restart.
17. Mine another counted ore in `CAVERN` and confirm progression and `Miner's Insight` continue from the stored value instead of resetting.
