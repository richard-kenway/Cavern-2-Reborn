# Caveman / Cavenia Normal Roster Boundary

This note documents the focused legacy `EntityCaveman` inspection behind the non-runtime `CaveniaSpawnProviderPolicy` normal roster entry.

The consolidated readiness and future implementation order are documented separately in:

- `docs/cavenia-active-foundation-readiness-plan.md`

The current source-of-truth notes remain:

- `docs/cavenia-spawn-provider-policy-mvp.md`
- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`
- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`
- `docs/cavenia-dimension-provider-foundation-boundary.md`

## Legacy References Inspected

- `cavern.entity.monster.EntityCaveman`
- `cavern.entity.CaveEntityRegistry`
- `cavern.api.entity.ICavenicMob`
- `cavern.client.renderer.RenderCaveman`
- `cavern.client.renderer.ModelCaveman`
- `assets/cavern/textures/entity/caveman.png`
- `assets/cavern/advancements/touch_caveman.json`
- `cavern.plugin.MCEPluginWrapper`
- `cavern.plugin.HaCPlugin`

## Exact Legacy Caveman Behavior Found

Legacy Caveman is:

- `cavern.entity.monster.EntityCaveman`
- `extends EntityMob`
- `implements ICavenicMob`

This is not a passive or villager-like base class. It is a hostile mob with custom inventory and interaction behavior.

The inspected constructor and core state set:

- `experienceValue = 5`
- `new InventoryBasic("entity.Caveman.name", false, 9 * 3)`
- standing size `0.48F x 1.85F`
- synced sitting flag through `DataParameter<Boolean> SITTING`
- `restTime` tracking

The inspected attributes are:

- `MAX_HEALTH = 30.0D`
- `FOLLOW_RANGE = 35.0D`
- `KNOCKBACK_RESISTANCE = 0.5D`
- `MOVEMENT_SPEED = 0.2875D`

The class does not set a custom attack-damage attribute in its own body.

## Exact Legacy AI And Combat Surface

The inspected goal setup is:

- `EntityAISwimming`
- `EntityAIRestrictSun`
- `EntityAIMoveTowardsRestriction`
- `EntityAIWanderAvoidWater`
- `EntityAIWatchClosest(EntityPlayer.class, 8.0F)`
- `EntityAILookIdle`

The inspected target goals are:

- `EntityAIHurtByTarget`
- `EntityAINearestAttackableTarget<>(EntityPlayer.class, true)`

The class also owns:

- `EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.15F, false)`

That melee goal is only attached when the main hand contains a combat item:

- `ItemSword`
- `ItemTool`

## Exact Legacy Backpack / GUI / Interaction Surface

Legacy Caveman is not only a combat mob. It also carries a persistent backpack inventory and opens that inventory to players.

The inspected interaction path is:

- `processInteract(EntityPlayer player, EnumHand hand)`
- requires a non-empty held stack
- requires `getDistanceSq(player) <= 3.0D`
- server side calls `player.displayGUIChest(backpackInventory)`
- also plays the pickup sound

The inspected inventory behavior is:

- 27-slot `InventoryBasic`
- NBT persistence through `writeEntityToNBT(...)` and `readEntityFromNBT(...)`
- nearby item pickup every 10 ticks from `getEntityBoundingBox().grow(0.65D)`
- `onItemStackPickup(...)` feeds the backpack inventory
- `onDeath(...)` drops the full backpack inventory with `InventoryHelper.dropInventoryItems(...)`

## Exact Legacy Sitting / Rest Behavior

Legacy Caveman also owns custom idle posture state:

- if grounded and not moving, `restTime` increases
- after `restTime > 500`, `setSitting(true)`
- moving resets `restTime` and clears sitting
- sitting changes size from `0.48F x 1.85F` to `0.48F x 1.35F`
- `getEyeHeight()` drops to `1.125F` while sitting

The custom model also consumes `isSitting()` to change arm, leg and backpack pose.

## Exact Legacy Spawn / Inventory Seeding Found

Legacy `CaveEntityRegistry.SPAWNS` includes:

- `new SpawnListEntry(EntityCaveman.class, 35, 1, 1)`

Legacy registry wiring also adds:

- `registry.register(createEntry(EntityCaveman.class, "caveman", "Caveman", 0xAAAAAA, 0xCCCCCC))`

Legacy `onInitialSpawn(...)` seeds:

- one main-hand item from `getInitialHeldItem()`
- up to two extra backpack combat items from `getInitialInventoryItems()`
- `Blocks.TORCH`, count `3..10`
- `Items.BREAD`, count `1..3`
- `ItemMirageBook.getRandomBook()` at `5%`
- `ItemMagicBook.getRandomBook()` at `5%`

The inspected held-item roll is:

- `35%` `Items.IRON_SWORD`
- `35%` `Items.IRON_PICKAXE`
- `15%` `Items.IRON_AXE`
- `15%` `Items.IRON_SHOVEL`

Legacy `getMaxSpawnedInChunk()` is also Cavenia-aware:

- returns `4` when `CavernAPI.dimension.isInCavenia(this)`
- otherwise returns `1`

## Exact Legacy Damage / Rendering Surface

The inspected damage behavior is:

- fall damage multiplied by `0.35F`
- fire damage rejected through `!source.isFireDamage()`

The inspected renderer and assets are:

- `RenderCaveman`
- `ModelCaveman`
- `textures/entity/caveman.png`

I also found a dedicated legacy advancement trigger:

- `assets/cavern/advancements/touch_caveman.json`

Legacy plugin integrations also reference Caveman:

- `MCEPluginWrapper` adds `EntityCaveman.class` as a purchase entity with value `15`
- `HaCPlugin` registers Caveman resistance data

Those plugin hooks do not force the deferral by themselves, but they do confirm that legacy Caveman sat inside a wider system surface than a bare hostile mob shell.

## Why Reborn Keeps Caveman Deferred

Current Reborn keeps `EntityCaveman -> deferred:caveman` in `CaveniaSpawnProviderPolicy` because the inspected class is not a small hostile baseline that can be restored honestly without widening scope.

The blocking surface is source-confirmed:

- active backpack inventory behavior
- active chest-GUI player interaction through `displayGUIChest(...)`
- custom sitting/rest state with a dedicated model contract
- random Mirage Book inventory seeding
- random Magic Book inventory seeding
- Cavenia-aware max-spawn-per-chunk behavior

Reborn intentionally does not add:

- `cavernreborn:caveman`
- a Caveman renderer or model
- a Caveman spawn egg
- a Caveman spawn placement
- a Caveman biome modifier or biome tag
- a fake partial backpack GUI or menu layer
- Mirage Book or Magic Book support just to satisfy Caveman inventory seeding

That keeps this increment within the current non-runtime Cavenia roster policy scope instead of widening into GUI, magic-book or active spawn work.

## Exact Current Reborn Boundary

Current Reborn still has:

- no active `cavernreborn:cavenia` level
- no active Cavenia spawn provider
- no active Cavenia normal spawning
- no registered `cavernreborn:caveman` entity type
- `EntityCaveman` represented only as `deferred:caveman` inside `CaveniaSpawnProviderPolicy.normalRoster()`

This increment does not add:

- active Cavenia runtime
- active Cavenia spawning
- normal `CAVERN` Caveman spawning
- crazy natural spawning
- fake normal `CAVERN` crazy spawning

## Future Follow-Up Required For Honest Caveman Parity

An honest future Caveman slice would need a deliberate decision on:

- whether to restore the backpack interaction as an actual menu/container flow
- whether to restore or intentionally replace Mirage Book inventory seeding
- whether to restore or intentionally replace Magic Book inventory seeding
- how much of the sitting/backpack model contract belongs in a baseline MVP
- whether Caveman should wait for active Cavenia runtime before any gameplay restoration

Until those decisions exist, the non-runtime policy entry remains correctly deferred.

## Related Notes

- `docs/cavenia-spawn-provider-policy-mvp.md`
- `docs/cavenia-spawn-provider-crazy-roster-activation-contract-boundary.md`
- `docs/cavenia-crazy-roster-natural-spawn-boundary.md`
- `docs/cavenia-dimension-provider-foundation-boundary.md`
