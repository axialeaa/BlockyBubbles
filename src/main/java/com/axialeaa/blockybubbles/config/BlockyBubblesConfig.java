package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.ConfigState;
import net.caffeinemc.mods.sodium.api.config.option.OptionFlag;
import net.caffeinemc.mods.sodium.api.config.option.OptionImpact;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class BlockyBubblesConfig implements ConfigEntryPoint {
    
    private static final Identifier QUALITY = BlockyBubbles.id("quality");
    private static final Identifier ANIMATIONS = BlockyBubbles.id("animations");
    private static final Identifier OPAQUE_FACES = BlockyBubbles.id("opaque_faces");
    private static final Identifier TOP_FACE_CULLING_METHOD = BlockyBubbles.id("top_face_culling_method");
    private static final int THEME_COLOR = 0xFF77D9FF;

    private static BlockyBubblesConfigStorage storage = null;

    @Override
    public void registerConfigLate(ConfigBuilder configBuilder) {
        configBuilder.registerOwnModOptions()
            .setColorTheme(configBuilder.createColorTheme().setBaseThemeRGB(THEME_COLOR))
            .setIcon(BlockyBubbles.id("textures/gui/config_icon.png"))
            .setName(BlockyBubbles.MOD_NAME)
            .addPage(configBuilder.createOptionPage()
                .setName(optionText(BlockyBubbles.id("page.bubble_columns")))
                .addOptionGroup(configBuilder.createOptionGroup()
                    .addOption(configBuilder.createEnumOption(QUALITY, BubblesQuality.class)
                        .setName(optionText(QUALITY))
                        .setElementNameProvider(value -> optionText(QUALITY, '.' + value.path))
                        .setTooltip(optionTooltip(QUALITY))
                        .setStorageHandler(getStorage())
                        .setBinding(value -> getStorage().quality = value, () -> getStorage().quality)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .setImpact(OptionImpact.MEDIUM)
                        .setDefaultValue(BubblesQuality.FAST)
                    )
                )
                .addOptionGroup(configBuilder.createOptionGroup()
                    .addOption(configBuilder.createBooleanOption(ANIMATIONS)
                        .setName(optionText(ANIMATIONS))
                        .setTooltip(optionTooltip(ANIMATIONS))
                        .setStorageHandler(getStorage())
                        .setBinding(value -> getStorage().animations = value, () -> getStorage().animations)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .setImpact(OptionImpact.MEDIUM)
                        .setDefaultValue(true)
                        .setEnabledProvider(BlockyBubblesConfig::isFast, QUALITY)
                    )
                    .addOption(configBuilder.createBooleanOption(OPAQUE_FACES)
                        .setName(optionText(OPAQUE_FACES))
                        .setTooltip(optionTooltip(OPAQUE_FACES))
                        .setStorageHandler(getStorage())
                        .setBinding(value -> getStorage().opaqueFaces = value, () -> getStorage().opaqueFaces)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .setImpact(OptionImpact.MEDIUM)
                        .setDefaultValue(false)
                        .setEnabledProvider(BlockyBubblesConfig::isFast, QUALITY)
                    )
                    .addOption(configBuilder.createEnumOption(TOP_FACE_CULLING_METHOD, TopFaceCullingMethod.class)
                        .setName(optionText(TOP_FACE_CULLING_METHOD))
                        .setElementNameProvider(value -> optionText(TOP_FACE_CULLING_METHOD, '.' + value.path))
                        .setTooltip(value -> optionText(TOP_FACE_CULLING_METHOD, '.' + value.path + ".tooltip"))
                        .setStorageHandler(getStorage())
                        .setBinding(value -> getStorage().topFaceCullingMethod = value, () -> getStorage().topFaceCullingMethod)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .setImpact(OptionImpact.LOW)
                        .setDefaultValue(TopFaceCullingMethod.NON_AIR)
                        .setEnabledProvider(BlockyBubblesConfig::isFast, QUALITY)
                    )
                )
            );
    }

    public static BlockyBubblesConfigStorage getStorage() {
        if (storage == null)
            storage = BlockyBubblesConfigStorage.loadFromFile();

        return storage;
    }
    
    private static boolean isFast(ConfigState configState) {
        return configState.readEnumOption(QUALITY, BubblesQuality.class) == BubblesQuality.FAST;
    }

    public static Component optionTooltip(Identifier option) {
        return optionText(option, ".tooltip");
    }

    public static Component optionText(Identifier option) {
        return optionText(option, "");
    }

    public static Component optionText(Identifier option, String suffix) {
        return Component.translatable(option.withPrefix("options.").toLanguageKey() + suffix);
    }

}
