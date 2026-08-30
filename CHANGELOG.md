We're not even in 26.3 yet and I already have a big update for you! This one features deep compatibility with [Wilder Wild](<https://modrinth.com/mod/wilder-wild>), adds an extra option for making your bubbles even nicer to look at, and reintroduces a feature I abandoned a long time ago!

## ➕ Additions
- Translations for the following localizations: 
  - Australian English (`en_au`)
  - Canadian English (`en_ca`)
  - New Zealand English (`en_nz`)
  - Pirate Speak English (`en_pt`)
  - Shakespearean English (`en_ws`; *Incomplete. Help would be appreciated*)
  - Upside-Down English (`en_ud`)
  - LOLCAT English (`lol_us`)
- `Biome Colors` bool option
- `Compatibility` [Sodium](<https://modrinth.com/mod/sodium>) settings page (*The following options are accessible without Sodium too. They just won't be neatly categorized.*)
  - `FrozenLib & Wilder Wild` bool option
  - `Resource Pack Style` enum option (*Can be set to Vanilla (no change), [Bare Bones](<https://modrinth.com/resourcepack/bare-bones>), [Faithful 32x](<https://faithfulpack.net/faithful32x>), or [Faithful 64x](<https://faithfulpack.net/faithful64x>).*)

Some of you may remember the Faithful 32x integration from the earlier days of **Blocky Bubbles**! The only difference is that it's a configuration option now, rather than a built-in resource pack. This should help to declutter the resource pack screen in some larger modpacks.

## 🔧 Changes
- Renamed `Bubble Columns` Sodium settings page to `General`
- Renamed parent `bubble_column` model to `template_bubble_column`

## 🐛 Fixes
- Reloading resource packs with animations turned off no longer crashes the thread.
- The negative space in opaque bubble column faces is now darker than the bubbles themselves.
- Changing certain modded options in the vanilla Video Settings screen no longer immediately reloads assets. Instead, the reload will be queued up and executed when the screen is closed.
- Added the terms and conditions of the [*General Public License version 3*](https://www.gnu.org/licenses/gpl-3.0.html) to the [*LGPLv3*](https://www.gnu.org/licenses/lgpl-3.0.html) license document.
  - This sincerely should have been done from the beginning. I will be fixing this in the next updates to each of my other mods.