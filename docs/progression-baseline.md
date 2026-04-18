# CAVERN Progression Baseline

This document fixes the current `CAVERN` progression baseline on `main`: the narrow backend shell plus the first player-facing and gameplay-visible layer built on top of it.

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
- Threshold crossing sends a rank-up overlay for the affected player.
- The first unlock is `Miner's Insight`.
  - source-of-truth: `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionUnlock.java`
  - unlock threshold: `apprentice`
  - gameplay consequence: counted ore breaks inside `CAVERN` grant `+1` bonus XP once the rank is unlocked
- The threshold-crossing ore already receives the unlock, because the bonus check runs against the updated persisted snapshot for that same counted mining event.
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
- Restart-safe persistence and continued progression after restart.
- Minimal server-side inspection through `/cavern progression` / `/cavern progression <player>` and player-facing status through `/cavern rank` / `/cavern rank <player>`.

## Runtime Inputs

- Source-of-truth policy and thresholds:
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionPolicy.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionRank.java`
- Persistent state model:
  - overworld-level `SavedData` file id: `cavernreborn_control_plane`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernPlayerProgressionState.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/state/CavernPersistentStateData.java`
- Runtime accounting hook:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernMiningProgressionEvents.java`
- Debug inspection path:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernProgressionCommands.java`
- Player-facing formatting and feedback:
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernPlayerProgressionStatusFormatter.java`
  - `app-neoforge/src/main/java/com/richardkenway/cavernreborn/app/progression/CavernProgressionRankUpFeedbackFormatter.java`
- Gameplay consequence policy:
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionConsequences.java`
  - `core/src/main/java/com/richardkenway/cavernreborn/core/progression/CavernProgressionUnlock.java`

## Intentional Compromises

- This pass restores only a small player-visible layer on top of the narrow progression shell, not the old full gameplay stack.
- No GUI, portal menu, shop flow, economy, mining records screen or broad player-facing polish is part of this baseline.
- No new config surface was added; counted blocks, scores and thresholds are checked-in code.
- The baseline counts qualifying block breaks, not item drops, smelting output, trading, pickups or broader activity telemetry.
- Rank is computed from score at read time and is not stored as a separate mutable field.
- The first gameplay consequence is intentionally small: one unlock and one bounded XP bonus instead of a larger perks tree or reward graph.
- Progression still does not gate portal use, worldgen, loot or broader economy/menu systems.

## Out Of Scope

- New dimensions, new content, progression UI, portal shop/menu and regeneration UI.
- Economy, rewards, unlock trees, mining leaderboards or persistence migrations for future score-table changes.
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
8. Run `/cavern progression` and confirm the debug summary matches the same persisted score/rank.
9. Mine the same ore outside `CAVERN` and confirm neither the bonus XP nor the cavern-specific progression state changes.
10. Restart the server.
11. Run `/cavern rank` or `/cavern progression` again and confirm the stored state survived restart.
12. Mine another counted ore in `CAVERN` and confirm progression and `Miner's Insight` continue from the stored value instead of resetting.
