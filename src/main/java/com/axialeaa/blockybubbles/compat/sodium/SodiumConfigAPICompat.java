package com.axialeaa.blockybubbles.compat.sodium;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.*;
import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionGroupBuilder;
import net.minecraft.network.chat.Component;

import static com.axialeaa.blockybubbles.config.ConfigHelper.*;

/**
 * This is <b>the only place</b> where sodium packages should be referenced so as to avoid crashes when sodium is not installed!
 */
public class SodiumConfigAPICompat implements ConfigEntryPoint {

	private static final int THEME_COLOR = 0xFF77D9FF;

    @Override
    public void registerConfigLate(ConfigBuilder builder) {
        BlockyBubblesConfig config = BlockyBubbles.getConfig();

		OptionGroupBuilder header = builder.createOptionGroup()
			.addOption(SodiumOptionBuilders.quality(builder, config));

		OptionGroupBuilder visuals = builder.createOptionGroup()
			.addOption(SodiumOptionBuilders.animations(builder, config))
			.addOption(SodiumOptionBuilders.opaqueFaces(builder, config))
			.addOption(SodiumOptionBuilders.cullfaceMethod(builder, config))
			.addOption(SodiumOptionBuilders.biomeColors(builder, config));

		OptionGroupBuilder integrations = builder.createOptionGroup()
			.addOption(SodiumOptionBuilders.resourcePackStyle(builder, config));

		OptionGroupBuilder modCompat = builder.createOptionGroup()
			.addOption(SodiumOptionBuilders.frozenLibCompat(builder, config));

		builder.registerOwnModOptions()
            .setColorTheme(builder.createColorTheme().setBaseThemeRGB(THEME_COLOR))
            .setIcon(BlockyBubbles.id("textures/gui/config_icon.png"))
            .setName(Component.translatable("blocky-bubbles.mod_name").getString())
            .addPage(builder.createOptionPage()
                .setName(GENERAL_PAGE_TEXT)
                .addOptionGroup(header)
                .addOptionGroup(visuals)
            )
			.addPage(builder.createOptionPage()
				.setName(COMPATIBILITY_PAGE_TEXT)
				.addOptionGroup(integrations)
				.addOptionGroup(modCompat)
			);
    }

}
