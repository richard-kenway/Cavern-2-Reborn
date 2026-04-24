# Cavenic Bow Mode State & Cycling MVP

This slice adds the next bounded identity layer for `cavernreborn:cavenic_bow` without changing its baseline vanilla shooting path.

The first bounded Rapid-only behavior follow-up is documented separately in `docs/cavenic-bow-rapid-mode-mvp.md`.
The first bounded Snipe-only behavior follow-up is documented separately in `docs/cavenic-bow-snipe-mode-mvp.md`.
The first bounded Torch-only behavior follow-up is documented separately in `docs/cavenic-bow-torch-mode-mvp.md`.

## What This Increment Adds

- legacy-inspired mode names and stable mode order
- stack-local mode persistence on each `cavenic_bow`
- `Sneak + use` mode cycling on the bow item itself
- tooltip and actionbar feedback for the current mode
- pure core mode tests plus runtime stack-state and cycling smoke

## Legacy References Inspected

- Legacy `cavern.item.ItemBowCavenic`
- Legacy `item.bowCavenic.mode` and related bow-mode lang entries from `assets/cavern/lang/en_us.lang`
- Legacy `EntityRapidArrow`
- Legacy `EntityTorchArrow`

## Exact Mode Order

Reborn keeps the legacy mode order:

- `NORMAL -> RAPID -> SNIPE -> TORCH`

The serialized ids are pinned as:

- `normal`
- `rapid`
- `snipe`
- `torch`

Unknown or missing stored values safely fall back to `NORMAL`.

## Storage Approach

- Mode state is stored directly on the bow `ItemStack`.
- The current implementation uses `DataComponents.CUSTOM_DATA`.
- The stored key is the stack-local `cavernreborn:cavenic_bow_mode` entry under that custom-data payload.
- New bow stacks default to `NORMAL`.

## Player Input

Legacy mode switching used an older swing-driven control path that does not map cleanly onto the current bounded Reborn input surface.

This MVP uses a safe modern equivalent:

- `Sneak + use` cycles the current mode
- cycling returns a successful interaction result
- cycling does not start drawing the bow
- cycling does not consume arrows
- cycling does not damage the bow
- the new mode is shown through an actionbar message

## Bounded Runtime Behavior

- `cavenic_bow` still extends the modern vanilla bow path through `BowItem`.
- At the time of this slice, all four modes still used vanilla bow shooting behavior.
- The later bounded Rapid-only, Snipe-only and Torch-only behavior follow-ups now exist separately.
- Normal use keeps vanilla draw, charge, enchantment and arrow behavior.
- The tooltip shows the current mode as a translatable `Mode: %s` line.

## Intentionally Out Of Scope

- `EntityRapidArrow`
- `EntityTorchArrow`
- rapid-fire shooting behavior
- custom projectile entities
- custom ammo logic
- mode-specific model predicates
- keybinds, packets, GUI or client networking

This document is the state-and-UI follow-up only, and custom projectile behavior remains out of scope.

## Testing

Coverage is split across:

- pure core tests for serialized ids, legacy mode order, wraparound and unknown-value fallback;
- resource tests for item-state helper wiring, lang entries, unchanged pull-state assets and the absence of custom projectile or mode-specific model wiring;
- NeoForge GameTest runtime smoke for default mode, cycling order, stack persistence, no-durability-loss cycling, no-arrow requirement for cycling and the server-side `Sneak + use` branch.
