# Ore Compass MVP

This note fixes the first bounded Ore Compass slice for `CAVERN` and the bounded tracking UX follow-up on top of it.

It builds on top of `docs/miner-orb-mvp.md` and keeps the current mining/progression loop unchanged.

## Added In This MVP

- item id: `cavernreborn:ore_compass`
- a shaped recipe that uses `cavernreborn:miner_orb`
- a small server-side ore scan with text feedback inside `cavernreborn:cavern`
- stored last target state directly on the `ore_compass` item stack
- a bounded client-visible pointing follow-up for the last successful scan target

## Bounded Modern Scope

- This is a bounded Ore Compass MVP, not full legacy Ore Compass parity.
- The current item works only inside `cavernreborn:cavern`.
- Right-click scans nearby loaded blocks only.
- The scan radius is `32` horizontal blocks and `24` vertical blocks.
- The runtime path does not load or generate chunks.
- The scan target tag is `cavernreborn:ore_compass_targets`.
- The curated target list includes valuable custom ores plus higher-value vanilla ores.
- Common noisy ores such as coal, iron and copper are intentionally excluded.
- `fissured_stone`, storage blocks and portal blocks are intentionally excluded.
- The item has a `20` second cooldown after a real scan attempt.
- Wrong-dimension use only reports a quiet-dimension message and does not apply the full scan cooldown.
- Wrong-dimension use keeps any previously stored target unchanged, but that stored target remains invalid for tracking until the holder returns to the stored dimension and the other validation rules pass again.
- The last successful scan is stored directly on the item stack instead of a world-level saved state or player capability.
- The tracking radius is `50` horizontal blocks.
- The client-visible pointing follow-up only tracks while the holder stays in the same dimension, within tracking range and while the currently loaded block at the stored target position still matches the stored block id.
- wrong dimension, out of range, block mismatch and unloaded client context all invalidate confident tracking and fall back to an unreliable/spinning state instead of pretending the target is still valid.
- no GUI or target selector, no client packets and no automatic rescanning every tick are added here.
- No `cavenic_orb` is added here.

## Runtime Coverage

- NeoForge GameTest runtime smoke checks the `ore_compass` runtime registry id.
- Runtime smoke verifies `ore_compass_targets` tag resolution at runtime.
- Runtime smoke verifies nearest-target scanner behavior.
- Runtime smoke verifies stored target state round-trip at runtime.
- Runtime smoke verifies tracking policy/runtime ids.
- Runtime smoke verifies unsupported, fissured-stone and storage-block exclusion.
- Runtime smoke verifies the helper path that stores a found target and clears it on a no-target scan.
- Source/resource tests pin the cooldown wiring in `OreCompassItem`, and manual smoke still covers the actual right-click cooldown feel and client-visible needle feel because the GameTest server does not expose a usable `CAVERN` item-use path or real client rendering path in this project setup.

## Manual Smoke Checklist

- craft `ore_compass` from `miner_orb`, compass, hexcite and redstone
- enter `CAVERN`
- right-click the compass near known hexcite, randomite or diamond targets
- verify the reported target, distance, direction and vertical relation are plausible
- verify the stored target becomes the visual needle target
- move around the target and verify the needle direction updates plausibly
- move out of tracking range and verify the compass no longer behaves like a confident valid target
- mine or remove the tracked target and verify the compass stops behaving like it still has a valid target
- verify cooldown prevents repeated spam
- verify wrong-dimension use stays quiet outside `CAVERN`
- verify the item is not consumed or damaged

## Asset Provenance

- The copied legacy source code baseline was GPLv3.
- The copied `ore_compass` item textures and `ore_compass_00..31` frame assets came from the original `Cavern 2` asset set, which was distributed under CC-BY-NC 4.0.
- The current follow-up reuses those legacy frame assets for a bounded 32-frame pointing model instead of building a new art pipeline.
- This repository keeps its main code license baseline unchanged; this note only records provenance for the imported legacy textures.
