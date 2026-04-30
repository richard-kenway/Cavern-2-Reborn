# Crazy Skeleton Cavenic Bow Equipment MVP

This note documents the bounded Crazy Skeleton equipment-only follow-up on top of the existing Crazy Skeleton baseline.

It restores only the legacy guaranteed held-item identity:

- guaranteed `Cavenic Bow`
- guaranteed `Infinity I`
- guaranteed mainhand drop chance `1.0F`

It does not port the custom ranged AI.

## Legacy References Inspected

- `cavern.entity.monster.EntityCrazySkeleton`
- `cavern.entity.monster.EntityCavenicSkeleton`
- `cavern.entity.ai.EntityAIAttackCavenicBow`
- `cavern.item.ItemBowCavenic`
- `cavern.item.CaveItems`

## Exact Legacy Equipment Behavior Found

Legacy `EntityCrazySkeleton` restores its held-item identity through two small hooks that are independent from the broader custom goal:

- the constructor calls `setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F)`
- `setEquipmentBasedOnDifficulty(...)` creates a new `ItemStack(CaveItems.CAVENIC_BOW)`
- the exact legacy item id comes from `CaveItems.CAVENIC_BOW`
- that stack is immediately enchanted with `Enchantments.INFINITY`
- the enchanted stack is written into `EntityEquipmentSlot.MAINHAND`

I found no extra Crazy Skeleton-specific bow NBT or mode state in that equipment hook.
I found no extra offhand arrow requirement in that equipment hook either.

## Exact Legacy Custom Goal Found

The custom ranged identity is separate from the equipment hook:

- `initCustomAI()` uses `EntityAIAttackCavenicBow<>(this, 0.99D, 6.0F, 1)`
- the melee fallback speed is `1.35D`
- the broader combat-task swap still comes from the inherited `EntityCavenicSkeleton#setCombatTask()`

That means the guaranteed held item is a bounded slice that can be restored without porting the custom goal itself.

## Reborn Mapping

Reborn keeps `CrazySkeleton` as vanilla `Skeleton`, not Reborn `CavenicSkeleton`.

The equipment-only mapping is:

- constructor-level `setDropChance(EquipmentSlot.MAINHAND, 1.0F)`
- entity-local `populateDefaultEquipmentSlots(...)` override
- `super.populateDefaultEquipmentSlots(...)` still runs first
- the vanilla bow is then replaced with `createLegacyCrazySkeletonBow(...)`
- the helper builds a new `ItemStack(ModRegistries.CAVENIC_BOW.get())`
- `Infinity I` is applied through the current `Registries.ENCHANTMENT` lookup and `ItemStack#enchant(...)`
- This guarantees the Infinity enchantment on the equipped bow.

This keeps the currently accepted boundaries intact:

- `CrazySkeleton` still extends vanilla `Skeleton`
- no custom ranged AI is added
- no custom arrows are added
- no `Cavenic Bow` behavior is changed
- no natural-spawn changes are added
- no new loot/orb behavior is introduced in this equipment slice; the inherited orb-drop follow-up is documented separately in `docs/crazy-skeleton-loot-mvp.md`
- no new damage changes are introduced in this equipment slice; the inherited fall/fire damage follow-up is documented separately in `docs/crazy-skeleton-damage-behavior-mvp.md`
- no new boss-bar or sky-darkening changes are introduced in this equipment slice; the legacy boss-event follow-up is documented separately in `docs/crazy-skeleton-boss-bar-mvp.md`
- no particle trail is added

## Why The Base Class Stays Vanilla

Legacy `EntityCrazySkeleton` extends legacy `EntityCavenicSkeleton`, but the Reborn line intentionally does not extend the current Reborn `CavenicSkeleton`.

That keeps this slice honest:

- no inherited Reborn `cavenic_orb` drop
- no inherited Reborn fall/fire damage behavior
- no inherited Reborn CAVERN natural-spawn rule
- no inherited future Cavenic Skeleton follow-up behavior

Only the source-confirmed equipment branch is copied explicitly.

## Why Custom Ranged AI Remains Deferred

The legacy goal is not just a held-item assignment.
It changes:

- attack cadence
- movement/strafing behavior
- melee fallback behavior
- ranged engagement distance
- how often the held `ItemBowCavenic` fires

That makes it a separate combat-identity slice, not a safe baseline equipment detail.
`EntityAIAttackCavenicBow` remains deferred and is documented separately in `docs/crazy-skeleton-ranged-ai-boundary.md`.

## Cavenic Bow Behavior Remains Unchanged

Reborn reuses the current `cavernreborn:cavenic_bow` item exactly as it already exists.
`Cavenic Bow` behavior remains unchanged.

This slice does not change:

- mode state
- Snipe / Rapid / Torch behavior
- release semantics
- ammo handling
- projectile behavior

The bow is only equipped onto Crazy Skeleton.

## Testing

- Resource tests pin the new equipment hook, the `CAVENIC_BOW` reference, the `Infinity` enchantment lookup and the preserved absence of any `EntityAIAttackCavenicBow` port.
- Runtime GameTest smoke verifies:
  - Crazy Skeleton spawns with `cavernreborn:cavenic_bow`
  - `Infinity I` is present at runtime
  - mainhand drop chance stays `1.0F`
  - baseline attributes stay unchanged
  - vanilla skeleton loot-table baseline stays unchanged
  - natural-spawn deferral stays unchanged
  - no custom ranged-AI overrides were added
- Full validation reruns the existing Cavenic Bow test suite, so the item behavior itself stays covered independently.

## Out Of Scope

- `EntityAIAttackCavenicBow`
- Crazy Skeleton custom ranged AI
- Crazy Skeleton natural spawning
- Crazy Skeleton custom loot / `cavenic_orb` drop
- Crazy Skeleton damage behavior
- Crazy Skeleton particle trail
- Crazy Creeper / Crazy Spider
- Cavenia
