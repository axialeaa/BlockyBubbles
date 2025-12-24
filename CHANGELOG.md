Happy holidays! Here's a gift just for you 🎁 💞

## 🔧 Changes
- [Sodium] is now truly optional in light of its new config API, specifically designed with addons in mind!
    - All configuration options are now available in the vanilla video settings screen as well as Sodium's redirected counterpart.
    - The master graphics presets will affect the bubble column quality setting when applied.
- `Top Face Culling Method` has been renamed to `Cullface Method`. In theory it now supports all directions for custom block models that supply cullface arguments. 
- The codebase now uses official Mojang mappings in preparation (and practice!) for 26.1.
- Added tooltips for Cullface Method settings and updated many of the localization strings. Translators will need to make sure their languages have up-to-date entries.

## ➖ Removals
- `Cullface Method > Off` has been removed. Resource packs should be used instead if you wish to turn off block face culling entirely.

[Sodium]: https://modrinth.com/mod/sodium