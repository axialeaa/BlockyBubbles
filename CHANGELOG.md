As we wait for the 1.21.10 hotfix to release and mods to finish updating to The Copper Age drop, I have done a little bit of cleaning up and bugfixing for 1.21.6 through 8!

If you want to use [Sodium 0.7.0](https://modrinth.com/mod/sodium/version/mc1.21.8-0.7.0-fabric), you will need to install this version of **Blocky Bubbles** else the game'll crash on startup.

## 🌐 Translators' Note
`zh_TW` still needs translations for the following tags:
- `blocky-bubbles.options.opaque_faces`
- `blocky-bubbles.options.opaque_faces.tooltip`

Also, "Bubble Columns" (`blocky-bubbles.options.quality`) changed to simply "Bubbles" in this update. I have updated this for all supported languages already.

## 🔧 Changes
- Moved the Sodium subsettings menu to its own tab rather than sharing the content of the Quality page.
- Reduced the verbosity of some translation tags.
  - I have updated these for all supported languages already.

## 🐛 Fixes
- #16: Updated to Sodium version 0.7.0.
  - The quality option (of type `SodiumGameOptions$GraphicsQuality`) has now been moved to its own separate enumerator, `BubblesQuality`. This also means 3 new translation tags per language file, which I have already added simply by copying from vanilla.
- The game now requires *at least* fabric loader version 0.17.2.
- Added [Reese's Sodium Options](https://modrinth.com/mod/reeses-sodium-options) to the mod runtime environment.