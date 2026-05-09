# Cavenia Mirage Entry / Access Path Contract Boundary

This note documents only the future entry/access contract around legacy Cavenia mirage-world travel.

The consolidated readiness and future implementation order are documented separately in:

- `docs/cavenia-active-foundation-readiness-plan.md`

The narrower active-foundation implementation-decision spike is documented separately in:

- `docs/cavenia-active-foundation-technical-spike.md`

It does not add:

- `data/cavernreborn/dimension/cavenia.json`
- `data/cavernreborn/dimension_type/cavenia.json`
- an active `cavernreborn:cavenia` level
- an active `mirage_book` item
- an active Cavenia teleporter
- an active Cavenia portal
- active GUI, packets, keybinds or commands for Cavenia access

The broader provider/runtime boundary remains documented in:

- `docs/cavenia-dimension-provider-foundation-boundary.md`

The narrower future dimension key/type contract remains documented in:

- `docs/cavenia-dimension-key-type-contract-boundary.md`

The narrower future biome-provider contract remains documented in:

- `docs/cavenia-biome-provider-contract-boundary.md`

The narrower future VEINS/content-pipeline contract remains documented in:

- `docs/cavenia-veins-content-pipeline-contract-boundary.md`

The narrower future chunk-generator and terrain-pipeline contract remains documented in:

- `docs/cavenia-chunk-generator-terrain-pipeline-contract-boundary.md`

The narrower future cave-carver contract remains documented in:

- `docs/cavenia-cave-carver-mapgen-contract-boundary.md`

The narrower future population/lakes/falls/shroom contract remains documented in:

- `docs/cavenia-population-lakes-falls-shroom-contract-boundary.md`

The separate spawn-provider / crazy-roster activation contract remains documented in:

- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`

The crazy-roster natural-spawn boundary remains documented in:

- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`

## Legacy References Inspected

- `cavern.item.ItemMirageBook`
- `cavern.handler.api.DimensionHandler`
- `cavern.world.CaveDimensions`
- `cavern.block.BlockPortalMirageWorlds`
- `cavern.network.client.MirageSelectMessage`
- `cavern.network.server.MirageTeleportMessage`
- `cavern.client.gui.GuiSelectMirageWorld`
- `cavern.handler.MirageEventHooks`
- `cavern.handler.CaveEventHooks`
- `cavern.data.PlayerData`
- `cavern.data.PortalCache`
- `cavern.world.mirage.WorldProviderMirageWorld`
- `cavern.config.MirageWorldsConfig`
- `cavern.item.CaveItems`
- `cavern.block.CaveBlocks`

## Exact Legacy `ItemMirageBook` Structure Found

Legacy `ItemMirageBook` is one registered item:

- `CaveItems.MIRAGE_BOOK`
- registry id `cavern:mirage_book`
- `setMaxStackSize(1)`
- `setHasSubtypes(true)`

It is not eight separate registered items.

The subtype enum is:

- `EnumType.CAVELAND`
- `EnumType.CAVENIA`
- `EnumType.FROST_MOUNTAINS`
- `EnumType.WIDE_DESERT`
- `EnumType.THE_VOID`
- `EnumType.DARK_FOREST`
- `EnumType.CROWN_CLIFFS`
- `EnumType.SKYLAND`

Each subtype carries:

- one metadata value
- one translation key
- one max-ticket count

The inspected default ticket count for every enum entry is `10`.

The exact Cavenia mapping is direct:

- `EnumType.CAVENIA` -> `CaveDimensions.CAVENIA`

It does not route that mapping through `DimensionHandler`.

## Exact Legacy Entry Path Found

Legacy Cavenia entry is not a standalone Cavenia-only right-click teleport.

The inspected entry path is the shared mirage-world portal flow:

- `BlockPortalMirageWorlds#onEntityCollidedWithBlock(...)`
- `ItemMirageBook.EnumType`
- `GuiSelectMirageWorld`
- `MirageSelectMessage`
- `MirageTeleportMessage`

`BlockPortalMirageWorlds` is the entry block:

- `CaveBlocks.MIRAGE_PORTAL`
- it extends `BlockPortalCavern`
- its own `getDimension()` returns `null`
- destination selection is deferred until runtime from the player's Mirage Books

Portal collision is server-side:

- `if (world.isRemote || entity.isDead || entity.isRiding() || entity.isBeingRidden() || !(entity instanceof EntityPlayerMP)) return;`

The portal scans the player's inventory and held equipment for `ItemMirageBook` stacks, resolves each stack with:

- `EnumType.byItemStack(stack).getDimension()`

and builds a unique destination set.

If the player has no Mirage Book destination at all:

- status message `cavern.message.portal.mirage`
- no teleport

If the player has destinations, the portal still filters them through a per-destination cooldown:

- `PlayerData.get(player).getLastTeleportTime(type)`
- creative bypasses the wait
- otherwise a destination is eligible only when:
  `teleportTime <= 0L || teleportTime + 6000L < world.getTotalWorldTime()`

If all held Mirage Book destinations are still cooling down:

- status message `cavern.message.portal.wait`
- no teleport

If exactly one destination remains eligible:

- immediate server transfer through `CaveItems.MIRAGE_BOOK.transferTo(type, player)`

If multiple destinations remain eligible:

- the server sends `MirageSelectMessage`
- the client opens `GuiSelectMirageWorld`
- clicking Done sends `MirageTeleportMessage`
- the server message handler calls `CaveItems.MIRAGE_BOOK.transferTo(type, player)`

So the inspected entry contract is:

- portal-based
- item-gated
- GUI-assisted only when multiple mirage destinations are available
- packet-backed for the multi-destination selection case

It is not:

- command-based
- keybind-based
- capability-unlock-based
- a standalone Cavenia-only item use path

## Exact Legacy Right-Click / Return Behavior Found

`ItemMirageBook#onItemRightClick(...)` is not the entry path into Cavenia from normal worlds.

It resolves:

- `DimensionType type = EnumType.byItemStack(stack).getDimension()`

If the book subtype has no dimension:

- returns `PASS`

If the player is not already inside that exact mirage world:

- client status message `item.mirageBook.portal`
- returns `PASS`

That means the normal right-click path is a return/exit action from the matching mirage world, not a direct outbound entry action.

On success it:

- calls `consumeTicket(stack, 1)`
- replaces the held stack with `new ItemStack(Items.BOOK)` when tickets reach zero
- records `PlayerData.get(player).setLastTeleportTime(type, world.getTotalWorldTime())`
- calls `transferTo(null, player)`

So the exact legacy return behavior includes:

- item-use based return
- one-ticket consumption per successful return
- no durability damage path
- no XP level cost
- no economy cost
- no item cooldown field beyond the ticket and teleport-time rules

`ItemMirageBook` uses an NBT ticket counter:

- tag key `Ticket`

and renders it through the durability bar helpers, but that is not real item durability.

## Exact Teleport / Arrival / Return Contract Found

`ItemMirageBook` itself implements:

- `ITeleporter`

There is no standalone inspected `CaveniaTeleporter` class.

`transferTo(@Nullable DimensionType dimNew, EntityPlayer player)` is the central access method.

When `dimNew` is `null`, it resolves the cached return destination from:

- `PortalCache`
- key `cavern:mirage_worlds`

When entering a mirage world with an explicit destination:

- if `CavernAPI.dimension.isMirageWorlds(dimNew)`, it stores the previous dimension as the last return destination
- it also stores the player's previous position for the source dimension

The exact cached-state helpers are:

- `PortalCache#setLastDim(key, dimOld)`
- `PortalCache#setLastPos(key, dimOld, player.getPosition())`

Actual dimension transfer is:

- `player.changeDimension(dimNew.getId(), this)`

Arrival placement is handled by `ItemMirageBook#placeEntity(...)`, not by a standalone portal teleporter class.

The inspected arrival order is:

1. `attemptToLastPos(...)`
2. `attemptToSkyland(...)` when entering Skyland
3. `attemptRandomly(...)`
4. `attemptToVoid(...)`

That means future Reborn access work needs a real safe-arrival and return contract rather than a shallow dimension switch.

## Exact Legacy Forced Return Behavior Found

Mirage-world return is not only manual right-click.

The inspected event hooks also force the same return path in special cases:

- `MirageEventHooks#onLivingDeath(...)`
  - if a player dies in a mirage world, the event is canceled
  - the player's health is set to `0.1F`
  - `CaveItems.MIRAGE_BOOK.transferTo(null, player)` is called
- `MirageEventHooks#onLivingUpdate(...)`
  - when a player falls below Skyland, `CaveItems.MIRAGE_BOOK.transferTo(null, player)` is called
  - follow-up fall damage is canceled through `FallTeleportMessage`

So the old mirage access line also owned its own emergency/forced exit rules.

## Exact `DimensionHandler` / `CaveDimensions` Relationship Found

`DimensionHandler` is an API facade over `CaveDimensions`.

For Cavenia it exposes:

- `getCavenia()` -> `CaveDimensions.CAVENIA`
- `isInCavenia(Entity entity)`

It also exposes the broader family helpers:

- `isInMirageWorlds(Entity entity)`
- `isMirageWorlds(DimensionType type)`

The inspected `ItemMirageBook` does not use `DimensionHandler` for its subtype mapping.

Instead:

- `EnumType.CAVENIA` maps directly to `CaveDimensions.CAVENIA`

So `DimensionHandler` is useful as a public API surface, but it is not the core teleport implementation.

## Exact Broader Mirage-World Family Relationship Found

Legacy Cavenia is one member of a uniform mirage-world family.

The inspected family entries are:

- `caveland`
- `cavenia`
- `frost_mountains`
- `wide_desert`
- `the_void`
- `dark_forest`
- `crown_cliffs`
- `skyland`

`DimensionHandler#isMirageWorlds(...)`, `ItemMirageBook.EnumType`, `BlockPortalMirageWorlds`, `GuiSelectMirageWorld` and `CaveDimensions` all treat them as one shared access line.

Cavenia is not special in the access item itself.

Its distinction comes from:

- its own dimension id and provider
- its own terrain/biome/content pipeline
- its own crazy-roster spawn callback

not from a unique entry item or unique GUI flow.

## Exact Presence / Absence Of Legacy Side Systems

The inspected access path does include:

- GUI: `GuiSelectMirageWorld`
- client packet: `MirageSelectMessage`
- server packet: `MirageTeleportMessage`
- portal block: `BlockPortalMirageWorlds`
- item-based destination gating: `ItemMirageBook`
- existing player capabilities for return state and cooldown bookkeeping:
  - `PortalCache`
  - `PlayerData`

The inspected access path does not require:

- MCEconomy or shop state to permit travel
- the magic-book system
- progression unlocks
- advancements as a hard gate
- commands
- keybinds
- a dedicated Cavenia-only player capability

Legacy MCEconomy integration only sold the book in a shop:

- `MCEPluginWrapper` registers `CaveItems.MIRAGE_BOOK` as a purchase item

That is not an entry permission check.

Legacy advancements such as `enter_the_cavenia` and `mirage_trip` are observational:

- they reference `cavern:mirage_book`
- they do not gate the travel code

## Current Reborn Boundary

Current Reborn still has:

- no active `cavenia` dimension
- inert `CAVENIA_LOCATION` only
- inert `CAVENIA_LEVEL_KEY` only
- no `ItemMirageBook`
- no `mirage_book` registry id
- no `CaveniaTeleporter`
- no `CaveniaPortal`
- no Cavenia access GUI
- no Cavenia access packet
- no keybind-driven or command-driven Cavenia access flow

Current Reborn does already have active `CAVERN` access code:

- `app.portal` runtime portal logic
- `CavernPortalBlock`
- `CavernCatalogGuiOpener`

That existing line is a bounded `CAVERN` portal/catalog path.

It is not an active mirage-world family port.

## Why This Increment Does Not Add Active Access Code

This increment does not add:

- an active Mirage Book item
- an active `cavenia` portal
- a teleporter class
- GUI or packet plumbing for Cavenia travel
- player access capabilities
- invented unlocks, economy costs or progression gates

because the legacy access contract depends on runtime pieces Reborn still does not have:

- an active `cavernreborn:cavenia` level
- an honest arrival target and return target contract
- a decision on whether future mirage access should stay family-wide or be split per dimension
- a decision on whether the old portal-plus-book UX should be preserved or intentionally replaced

Adding a `mirage_book` now would be misleading because the legacy book is not just flavor text. It assumes:

- a real mirage destination family
- packet-backed multi-destination selection
- per-destination cooldown tracking
- return-state caching
- arrival placement fallback logic

## Future Modern Mapping Requirements

An honest future Reborn implementation likely needs:

- an active Cavenia dimension key/type
- a safe arrival contract
- a return/exit contract
- a mirage-world item/access contract
- a decision on whether to keep portal-based entry
- GUI/packet work only if multi-destination selection remains part of the UX
- persistent return-state storage compatible with the chosen travel system

If future Reborn keeps the old mirage-family shape, the implementation should probably be generic to all mirage worlds instead of inventing a Cavenia-only access item.

If future Reborn chooses a different UX, it still needs to document the intentional deviation rather than silently replacing the old shared portal-plus-book contract.

## Explicit Current Boundary

Current Reborn behavior remains:

- no active `cavenia` dimension
- no active Cavenia entry/access pipeline
- no `mirage_book`
- no Cavenia teleporter
- no Cavenia portal
- no Cavenia access GUI or packet flow
- no economy or magic-book dependency in the current Reborn line
- no invented progression or unlock system for Cavenia access
