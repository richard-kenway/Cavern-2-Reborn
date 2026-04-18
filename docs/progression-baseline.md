# CAVERN Progression Baseline

This document fixes the current minimal `CAVERN` progression shell as the regression-protected backend baseline on `main`.

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
- Player state survives restart through the same overworld-level `SavedData` control plane already used by the portal baseline.
- After restart, the same player continues from the stored score/counters instead of reinitializing.

## Supported Cases

- Empty state for a new player before any counted cavern mining.
- Cavern mining updates score, counted blocks and per-block counters.
- Mining the same baseline ore outside `CAVERN` does not affect cavern-specific progression.
- Multiple players advance independently without shared counters.
- Threshold crossing updates the derived rank deterministically.
- Restart-safe persistence and continued progression after restart.
- Minimal server-side inspection through `/cavern progression` and `/cavern progression <player>`.

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

## Intentional Compromises

- This pass restores only a minimal server-side progression shell, not the old full gameplay stack.
- No GUI, portal menu, shop flow, economy, mining records screen or player-facing polish is part of this baseline.
- No new config surface was added; counted blocks, scores and thresholds are checked-in code.
- The baseline counts qualifying block breaks, not item drops, smelting output, trading, pickups or broader activity telemetry.
- Rank is computed from score at read time and is not stored as a separate mutable field.
- Progression currently does not gate portal use, worldgen, loot or other systems.

## Out Of Scope

- New dimensions, new content, progression UI, portal shop/menu and regeneration UI.
- Economy, rewards, unlock trees, mining leaderboards or persistence migrations for future score-table changes.
- Broader non-ore activity tracking such as mob kills, exploration, crafting or item collection.
- Full legacy parity for miner rank perks or the older menu-driven rank flow.

## Minimal Checklist

Run this before any larger progression or gameplay-shell change:

1. `docker compose run --rm gradle ./gradlew --no-daemon test`
2. Start the local dev server and load a persistent world.
3. Enter `CAVERN` through the current portal flow.
4. Mine at least one counted ore block in `CAVERN`.
5. Run `/cavern progression` and confirm score, counted blocks and top block counters changed.
6. Mine the same ore outside `CAVERN` and confirm the command output does not change for the cavern-specific state.
7. Restart the server.
8. Run `/cavern progression` again and confirm the stored state survived restart.
9. Mine another counted ore in `CAVERN` and confirm progression continues from the stored value instead of resetting.
