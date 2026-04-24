# Cavenic Melee MVP

This slice gives `cavernreborn:cavenic_orb` its first practical equipment use by adding:

- `cavernreborn:cavenic_sword`
- `cavernreborn:cavenic_axe`

## Legacy Reference

Legacy Cavern 2 defined the CAVENIC tool material as harvest level 2, 278 durability, 7.0 mining speed, 2.5 attack damage bonus and 30 enchantability. Reborn maps that to `ModToolTiers.CAVENIC` with an iron-like incorrect-tool tag and `cavernreborn:cavenic_orb` as the repair ingredient.

Legacy `ItemSwordCavenic` cleared the target hurt-resistant timer on hit and consumed durability. Reborn maps that to a server-side reset of the modern target hit cooldown fields when a cooldown is active.

Legacy `ItemAxeCavenic` damaged nearby hostile mobs for 4.0 damage in a 2.5 block area, skipped owned mobs, applied knockback and bounded durability cost. Reborn keeps those core rules with a cap of six extra hostile targets.

## Bounded MVP Behavior

- The sword only applies on the server and only when the living target has an active hurt or invulnerability cooldown.
- The sword costs one durability only when the cooldown reset applies.
- The axe only applies on the server against nearby hostile monster entities.
- The axe never affects the primary target twice and never affects the attacker.
- The axe excludes owned, tamed, friendly and passive entities.
- The axe radius is 2.5 blocks, extra affected targets are capped at six and durability cost is clamped to one through three.

## Out Of Scope

This increment does not add `cavenic_bow`, bow modes, custom arrows, client predicates, cavenic mobs, Cavenia, cavenic shroom transformation systems, economy hooks, GUI, packets, keybinds, worldgen changes or broad combat rewrites.

## Testing

Coverage is split across:

- pure core policy tests for sword cooldown reset eligibility and axe target, radius, cap and durability rules;
- resource tests for registration, models, textures, recipes, tags, localization and the absence of `cavenic_bow`;
- NeoForge GameTest runtime smoke for registry resolution, repair behavior, cavenic item tags, recipe manager resolution and small server-side hit behavior checks.
