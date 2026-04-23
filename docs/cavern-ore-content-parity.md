# CAVERN Ore/Content Parity

This note fixes the first narrow ore/content tranche for `CAVERN`.

The special-ore follow-up tranche that adds `hexcite`, `randomite` and `fissured_stone` now lives in `docs/cavern-special-ore-content-parity.md`.
The bounded aquamarine tool follow-up now lives in `docs/aquamarine-tool-mvp.md`.

## Chosen Families

- `aquamarine`: the simplest direct-drop gem family from legacy `Cavern 2`.
- `magnite`: the simplest furnace-bound ingot family from legacy `Cavern 2`.

## Why These Families

- Both families already carried a clear mining identity in legacy `CAVERN`.
- Both can be restored without special-case gameplay logic, tool sets or new systems.
- Together they cover two different loops:
  - direct-drop gem ore;
  - smelted ingot ore.

## Minimal Loop In This Tranche

- `aquamarine`
  - `aquamarine_ore` generates in `CAVERN`;
  - mining yields `aquamarine`;
  - `aquamarine` compresses into `aquamarine_block` and back.
- `magnite`
  - `magnite_ore` generates in `CAVERN`;
  - mining yields the ore block itself;
  - smelting or blasting yields `magnite_ingot`;
  - `magnite_ingot` compresses into `magnite_block` and back.

## Deliberately Not In This Tranche

- Full legacy ore roster.
- Full old vein-provider parity.
- Tool, weapon, armor or progression integration.
- Special-case ore mechanics, rewards or economy hooks.

## Follow-Up Status

- aquamarine now also has a bounded tool follow-up in `docs/aquamarine-tool-mvp.md`.
- magnite now also has a bounded tool follow-up in `docs/magnite-tool-mvp.md`.
- magnite now also has a bounded armor follow-up in `docs/magnite-armor-mvp.md`.
- the first ore-family slice now has both aquamarine utility and magnite specialist follow-ups.

## Modern Reborn Slice

- Register only the blocks and items required for the two ore families.
- Keep the worldgen slice narrow through custom configured/placed ore features wired into the existing biome family.
- Reuse the current resource-driven baseline for loot, recipes, tags, models and language.
- Leave broader vein/content parity for later tranches once this first custom ore slice is stable.
