Hey there! Happy Spring to Life Drop! I know many of you will be wanting to play right away so here's a quick update for you, featuring a new configuration setting and many small changes to make the codebase and your user experience even nicer than before.

Now that the config is growing a bit bigger, I'll take the opportunity to officially endorse [Reese's Sodium Options][rso]! It makes the Sodium option pages a little bit easier to navigate, and also stops the options from running off the screen entirely on certain GUI scales and screen resolutions.

Before talking about the content in this update, I need to make an important announcement...

## 🪦 End-of-Life Versions
A little while ago, I stopped supporting versions below 1.20 due to multiple versions becoming more and more difficult to maintain as the game itself updates faster. Well, I plan to do it again. Assuming no further hotfix, **Update 1.5.0** will be the final release for the following versions:

- 1.20.2
- 1.20.4
- 1.20.6

These versions don't have very much demand compared to 1.20.1, 1.21.1 and the latest drop, so those will be staying. I'm also keeping 1.21.3 and 1.21.4 to allow the players on those versions some time to migrate to 1.21.5 if they so choose. The versions mentioned will be deprecated unless a hotfix needs to happen before deprecation. Make sure to report any issues you encounter on [GitHub][issues]; the clock is ticking!

---
Now onto the content...

## ➖ Removals
The in-built 32x resource pack has been removed in this update. Simply put, managing built-in resource packs on multi-version projects like this one is freaking *annoying*, and they somewhat bloat the resource pack menu. Fret not though, I plan to publish a standalone resource pack on my Modrinth profile which you can download and install to upscale the textures of *all of my mods* at the same time. It will also be handled on a separate GitHub repository which you will all be able to contribute to!

It's not quite ready yet, but I will work on getting it out as soon as I can after updating each of my mods to 1.21.5. Thank you for your patience! ~🌸

## ➕ Additions
There is a new configuration option in this update: **Opaque Faces**!

This allows you to render the bubble column models in an even more performant way than before, by filling in the transparent gaps with a hardcoded blue color.

![A world made of opaque bubble columns](run/screenshots/2025-03-27_12.53.59.png)

> [!NOTE]
> This resembles how Fast Leaves work!

I'll be honest, *I don't think this doesn't look very good* lol. However, it may still appeal to you if you're trying to debug something or if you play with **Animations** disabled. I'll let you be the judge of that!

## 🔧 Changes
Some option names have changed in this update. You will need to re-enter any values you changed before this update:

- "Culling" (`culling_mode`) ➡ "Top Face Culling" (`top_face_culling_method`)
  - This was changed because only the top faces of the bubble column can actually cull. This fact has also been made more apparent in the codebase. 
- "Enable Animations" (`enable_animations`) ➡ "Animations" (`animations`)
  - This was changed because the boolean option type is already implied by the tickbox control. 
- "Culling"..."Bubble Columns" (Java-like) (`bubble_columns`) ➡ "Top Face Culling"..."Java-ish" (`java_ish`)
  - This was changed because the culling behavior extended beyond just culling against other bubble columns. I felt it was more apt to suggest it simply worked like other blocks in Java Edition which is what I was going for anyway.
- "Culling"..."None" (`none`) ➡ "Top Face Culling"..."Off" (`off`)
    - This was changed to make more sense grammatically, and to be more clear about what it actually does.

<details>
	<summary>❗<b>Message for my mods' translators</b></summary>
	<p>I have updated the translation keys for all languages already, but I will leave it up to you to make sure the text is up-to-date. I am open to releasing localization fixes under a hotfix update; accessibility will forever be important to me! Just remember that I'll be deprecating certain game versions soon</p>
</details>

## 🐛 Fixes
- The game logs will no longer show a warning for a missing "particle" texture key in the bubble column model file.
- Replaced 0%-opacity #FFFFFF pixels in the bubble column block textures with #000000. (blame Bedrock Edition for that blunder xd)
- Added a log message for when **Blocky Bubbles** initializes.
- Cleaned the codebase, improved readability, reduced Stonecutter swaps, took advantage of vanilla functions.

[rso]: https://modrinth.com/mod/reeses-sodium-options
[issues]: https://github.com/axialeaa/BlockyBubbles/issues