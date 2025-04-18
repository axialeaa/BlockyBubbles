package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import net.minecraft.text.Text;

import java.util.function.BiConsumer;
import java.util.function.Function;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.SodiumGameOptions;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.*;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.control.*;

public class SodiumOptions {

    private static final Option<SodiumGameOptions.GraphicsQuality> QUALITY = createEnum(
        "quality",
        SodiumGameOptions.GraphicsQuality.class,
        (config, value) -> config.bubblesQuality = value,
        config -> config.bubblesQuality,
        OptionImpact.MEDIUM,
        OptionFlag.REQUIRES_RENDERER_RELOAD
    );

    private static final Option<Boolean> ANIMATIONS = createBoolean(
        "animations",
        (config, value) -> config.animations = value,
        config -> config.animations,
        OptionImpact.MEDIUM,
        OptionFlag.REQUIRES_ASSET_RELOAD
    );

    private static final Option<Boolean> OPAQUE_FACES = createBoolean(
        "opaque_faces",
        (config, value) -> config.opaqueFaces = value,
        config -> config.opaqueFaces,
        OptionImpact.MEDIUM,
        OptionFlag.REQUIRES_RENDERER_RELOAD
    );

    private static final Option<TopFaceCullingMethod> TOP_FACE_CULLING_MODE = createEnum(
        TopFaceCullingMethod.PATH,
        TopFaceCullingMethod.class,
        (config, value) -> config.topFaceCullingMethod = value,
        config -> config.topFaceCullingMethod,
        OptionImpact.LOW,
        OptionFlag.REQUIRES_ASSET_RELOAD
    );

    private static Option<Boolean> createBoolean(String name, BiConsumer<SodiumConfig, Boolean> setter, Function<SodiumConfig, Boolean> getter, OptionImpact impact, OptionFlag... optionFlags) {
        return create(name, Boolean.class, TickBoxControl::new, setter, getter, impact, optionFlags);
    }

    private static <T extends Enum<T>> Option<T> createEnum(String path, Class<T> type, BiConsumer<SodiumConfig, T> setter, Function<SodiumConfig, T> getter, OptionImpact impact, OptionFlag... optionFlags) {
        return create(path, type, option -> new CyclingControl<>(option, type), setter, getter, impact, optionFlags);
    }

    private static <T> OptionImpl<SodiumConfig, T> create(String name, Class<T> type, Function<OptionImpl<SodiumConfig, T>, Control<T>> controlFunction, BiConsumer<SodiumConfig, T> setter, Function<SodiumConfig, T> getter, OptionImpact impact, OptionFlag... optionFlags) {
        return OptionImpl.createBuilder(type, SodiumConfig.STORAGE)
            .setName(getOptionText(name, false))
            .setTooltip(getOptionText(name, true))
            .setControl(controlFunction)
            .setBinding(setter, getter)
            .setImpact(impact)
            .setFlags(optionFlags)
            .build();
    }

    protected static Text getOptionText(String path) {
        return Text.translatable(BlockyBubbles.MOD_ID + ".options." + path);
    }

    protected static Text getOptionText(String path, boolean tooltip) {
        return getOptionText(path + "." + (tooltip ? "tooltip" : "name"));
    }

    public static OptionGroup createGroup() {
        return OptionGroup.createBuilder()
            .add(QUALITY)
            .add(ANIMATIONS)
            .add(OPAQUE_FACES)
            .add(TOP_FACE_CULLING_MODE)
            .build();
    }

}
