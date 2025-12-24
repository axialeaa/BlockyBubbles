package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.ConfigState;
import net.caffeinemc.mods.sodium.api.config.option.OptionFlag;
import net.caffeinemc.mods.sodium.api.config.option.OptionImpact;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;

import static com.axialeaa.blockybubbles.config.ConfigHelper.*;

/**
 * This is <b>the only place</b> where sodium packages should be referenced so as to avoid crashes when sodium is not installed!
 */
public class SodiumConfigAPICompat implements ConfigEntryPoint {

    private static final int THEME_COLOR = 0xFF77D9FF;

    @Override
    public void registerConfigLate(ConfigBuilder builder) {
        BlockyBubblesConfig config = BlockyBubbles.getConfig();

        builder.registerOwnModOptions()
            .setColorTheme(builder.createColorTheme().setBaseThemeRGB(THEME_COLOR))
            .setIcon(BlockyBubbles.id("textures/gui/config_icon.png"))
            .setName(BlockyBubbles.MOD_NAME)
            .addPage(builder.createOptionPage()
                .setName(OPTION_PAGE_TEXT)
                .addOptionGroup(builder.createOptionGroup()
                    .addOption(builder.createEnumOption(QUALITY, Quality.class)
                        .setName(optionText(QUALITY))
                        .setElementNameProvider(enumOptionNameProvider(QUALITY)::apply)
                        .setTooltip(optionTooltip(QUALITY))
                        .setStorageHandler(config::writeToFile)
                        .setBinding(config::setQuality, config::getQuality)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .setImpact(OptionImpact.MEDIUM)
                        .setDefaultValue(Quality.FAST)
                    )
                )
                .addOptionGroup(builder.createOptionGroup()
                    .addOption(builder.createBooleanOption(ANIMATIONS)
                        .setName(optionText(ANIMATIONS))
                        .setTooltip(optionTooltip(ANIMATIONS))
                        .setStorageHandler(config::writeToFile)
                        .setBinding(config::setAnimations, config::hasAnimations)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .setImpact(OptionImpact.MEDIUM)
                        .setDefaultValue(true)
                        .setEnabledProvider(SodiumConfigAPICompat::isFast, QUALITY)
                    )
                    .addOption(builder.createBooleanOption(OPAQUE_FACES)
                        .setName(optionText(OPAQUE_FACES))
                        .setTooltip(optionTooltip(OPAQUE_FACES))
                        .setStorageHandler(config::writeToFile)
                        .setBinding(config::setOpaqueFaces, config::hasOpaqueFaces)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .setImpact(OptionImpact.MEDIUM)
                        .setDefaultValue(false)
                        .setEnabledProvider(SodiumConfigAPICompat::isFast, QUALITY)
                    )
                    .addOption(builder.createEnumOption(CULLFACE_METHOD, CullfaceMethod.class)
                        .setName(optionText(CULLFACE_METHOD))
                        .setElementNameProvider(enumOptionNameProvider(CULLFACE_METHOD)::apply)
                        .setTooltip(enumOptionTooltipProvider(CULLFACE_METHOD)::apply)
                        .setStorageHandler(config::writeToFile)
                        .setBinding(config::setCullfaceMethod, config::getCullfaceMethod)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .setImpact(OptionImpact.LOW)
                        .setDefaultValue(CullfaceMethod.NON_AIR)
                        .setEnabledProvider(SodiumConfigAPICompat::isFast, QUALITY)
                    )
                )
            );
    }
    
    private static boolean isFast(ConfigState configState) {
        return configState.readEnumOption(QUALITY, Quality.class) == Quality.FAST;
    }

}
