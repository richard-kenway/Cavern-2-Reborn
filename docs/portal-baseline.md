# CAVERN Portal Baseline

This document fixes the current `CAVERN` portal vertical slice as the regression-protected baseline on `main`.

## Expected Behavior

- Activation is server-side and collision-based at runtime.
- Ritual activation accepts any block in `cavernreborn:cavern_portal_frames`, with explicit legacy fallback for `minecraft:mossy_cobblestone` and `minecraft:mossy_stone_bricks`.
- Ritual activation accepts any item in `cavernreborn:cavern_portal_activators`, with explicit legacy fallback for `minecraft:emerald`.
- Unsupported frame blocks or unsupported activators are rejected without partial activation.
- Player portal identity is frame-level, not per interior block; cooldown keys and return-state keys use the canonicalized frame anchor.
- Entering `CAVERN` stores player return-state plus the source portal placement in the source-world portal index.
- Returning from `CAVERN` prefers the indexed destination portal path in this order:
  1. exact indexed reuse
  2. bounded nearby relink
  3. bounded regeneration near a dead indexed anchor
  4. bounded nearby generic find
  5. bounded generic create
- If no saved player return-state exists in `CAVERN`, the system may use the bounded overworld fallback target; if that is unavailable, return is denied with feedback.
- If no safe arrival can be found for `CAVERN` entry, entry is denied and portal index / return-state stay unchanged.
- Successful player interactions apply a configurable interaction cooldown; failed entry attempts apply a shorter configurable cooldown; feedback messages use a separate suppression cooldown.
- Block-level entity portal cooldown still applies after handled collisions to stop immediate collision retrigger spam.
- Non-player transport is allowed only for alive, portal-capable, non-spectator, non-crouching, non-passenger, non-vehicle, non-boss, non-projectile entities that are not already on portal cooldown.
- Eligible non-player entities keep their own persisted portal entry receipt and can round-trip through the same portal loop.
- Player return-state and world portal indices persist across restarts through overworld-level `SavedData`.

## Supported Cases

- Activation on both legacy frame blocks: `mossy_cobblestone` and `mossy_stone_bricks`.
- Activation with the checked-in baseline activator `minecraft:emerald`.
- Repeated player round-trips through the same source portal.
- Repeated player round-trips through different source portals in the same world.
- Multiple indexed portals per portal key, with bounded history and deterministic prioritization.
- Destination portal reuse, relink, regeneration and generic create after restart.
- Portal-independent isolation: breaking one portal key does not invalidate another portal key in the same world index.
- Predictable denial on missing return-state, failed safe arrival and ineligible entity collisions.

## Out Of Scope

- New dimensions, new content, progression shell, GUI, portal shop, mining records, regeneration UI.
- Large portal architecture refactors outside a concrete portal bug fix.
- Full legacy cache parity, unbounded search radius parity or wider regeneration semantics.
- Broad modded-entity compatibility guarantees beyond the explicit eligibility policy above.
- Worldgen work outside portal-related arrival and destination placement behavior.
- Client UX work beyond the current short feedback messages.

## Runtime Inputs

- Runtime portal tuning file: `config/cavernreborn-portal.properties`
- Frame tag: `data/cavernreborn/tags/blocks/cavern_portal_frames.json`
- Activator tag: `data/cavernreborn/tags/items/cavern_portal_activators.json`

## Minimal Checklist

Run this before any larger portal or dimension change:

1. `docker compose run --rm gradle ./gradlew --no-daemon test`
2. Start the local dev server and load a persistent world.
3. Activate one portal with `mossy_cobblestone` and one with `mossy_stone_bricks`.
4. Verify `Overworld -> CAVERN -> Overworld` twice in a row without teleport spam.
5. Verify two different overworld portals return to the expected source portal.
6. Break the destination portal, re-enter, and confirm relink/regeneration/create keeps the loop usable.
7. Restart the server and verify return-state plus portal reuse still work.
