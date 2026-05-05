# Cavenic Bow Rapid Low-Armor Hurt-Resistance MVP

This slice restores the only gameplay-significant legacy `EntityRapidArrow` behavior without adding `EntityRapidArrow`, `cavernreborn:rapid_arrow`, or a custom projectile renderer.

It keeps `RAPID` on the vanilla `minecraft:arrow` entity path and restores only the low-armor hurt-resistance reset.

## Legacy References Inspected

- Legacy `cavern.entity.projectile.EntityRapidArrow`
- Legacy `cavern.item.ItemBowCavenic`
- Legacy `cavern.entity.projectile.EntityTorchArrow`

## Exact Legacy Behavior Found

Legacy `EntityRapidArrow#arrowHit(EntityLivingBase living)`:

- calls `super.arrowHit(living)` first
- checks `living.getTotalArmorValue() < 20`
- sets `living.hurtResistantTime = 0`

I found no extra legacy checks for shooter type, difficulty, enchantments, damage amount, player versus mob target, armor toughness, potion effects, dimension, Cavenia, or progression.

I found no direct legacy changes to damage, velocity, gravity, pickup rules, critical hits, knockback, flame, block-hit behavior, renderer, or texture.

Legacy `EntityRapidArrow` was created only by player `ItemBowCavenic` `RAPID` shots, not by skeleton attack paths.

## Exact Reborn Mapping

Reborn restores this behavior without adding a projectile entity:

- `CavenicBowItem` now applies a `cavernreborn:cavenic_bow_rapid` scoreboard-tag marker only to arrows fired from `RAPID` mode.
- The fired entity still stays `minecraft:arrow`.
- `CavenicBowRapidEvents` listens on `LivingDamageEvent.Post`, which is the safe modern post-damage hook matching the legacy `super.arrowHit(...)` then reset order.
- When the direct damage source is a marked Rapid arrow and the damaged target is a `LivingEntity` with `getArmorValue() < 20`, Reborn sets `target.invulnerableTime = 0`.

This restores only the modern equivalent of the old hurt-resistance cooldown reset.

## Why Reborn Keeps Vanilla Arrow Identity

The accepted projectile boundary already pinned that Reborn should not widen the active bow path with:

- `EntityRapidArrow`
- `cavernreborn:rapid_arrow`
- custom projectile renderers

The legacy gameplay delta was narrow enough to restore through:

- a Rapid-only arrow marker
- a post-damage server event

That keeps the already accepted guarantees stable:

- `RAPID` still spawns a vanilla arrow entity
- `SNIPE` remains separate
- `TORCH` remains separate
- Crazy Skeleton stays on its current vanilla-compatible projectile bridge

## Exact Threshold And Scope

- armor threshold remains strictly `< 20`
- the reset is server-side only
- the reset applies only after a successful damage event
- unmarked arrows do not trigger it
- `SNIPE` arrows do not trigger it
- `TORCH` arrows do not trigger it
- vanilla bow arrows do not trigger it
- armored targets at `20` or above do not trigger it

## What Stays Unchanged

- no `EntityRapidArrow`
- no `cavernreborn:rapid_arrow`
- no `EntityTorchArrow`
- no `EntityCavenicArrow`
- no projectile renderer registrations
- no Rapid velocity/power ramp changes
- no Rapid damage multiplier changes
- no Snipe changes
- no Torch placement changes
- no Cavenic Bow release-semantics changes
- no Crazy Skeleton ranged-AI cadence or equipment changes
- no natural-spawn changes

## Testing

Coverage is split across:

- resource tests pinning:
  - the Rapid arrow marker methods
  - `CavenicBowRapidEvents`
  - `LivingDamageEvent.Post` wiring
  - continued absence of `rapid_arrow`, `torch_arrow`, `cavenic_arrow` and projectile renderers
- documentation tests pinning:
  - the exact legacy `EntityRapidArrow#arrowHit(...)` finding
  - the `invulnerableTime` mapping
  - the preserved no-entity boundary
- NeoForge GameTest runtime smoke for:
  - `RAPID` still spawning a vanilla arrow entity
  - `RAPID` arrows getting only the Rapid marker
  - low-armor targets having invulnerability reset to `0`
  - armored targets keeping invulnerability unchanged
  - `SNIPE`, `TORCH` and vanilla arrows not triggering the Rapid reset
  - Torch placement and Crazy Skeleton ranged-AI stability
