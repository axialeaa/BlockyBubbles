package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptions;
import net.caffeinemc.mods.sodium.client.gui.options.*;
import net.caffeinemc.mods.sodium.client.gui.options.control.*;
import net.minecraft.text.Text;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SodiumOptions {

    private static final Option<SodiumGameOptions.GraphicsQuality> QUALITY = ofEnum(
        "quality",
        SodiumGameOptions.GraphicsQuality.class,
        (config, value) -> config.bubblesQuality = value,
        config -> config.bubblesQuality,
        OptionImpact.MEDIUM,
        OptionFlag.REQUIRES_RENDERER_RELOAD
    );

    private static final Option<Boolean> ANIMATIONS = ofBoolean(
        "animations",
        (config, value) -> config.animations = value,
        config -> config.animations,
        OptionImpact.MEDIUM,
        OptionFlag.REQUIRES_ASSET_RELOAD
    );

    private static final Option<Boolean> OPAQUE_FACES = ofBoolean(
        "opaque_faces",
        (config, value) -> config.opaqueFaces = value,
        config -> config.opaqueFaces,
        OptionImpact.MEDIUM,
        OptionFlag.REQUIRES_RENDERER_RELOAD
    );

    private static final Option<TopFaceCullingMethod> TOP_FACE_CULLING_MODE = ofEnum(
        TopFaceCullingMethod.PATH,
        TopFaceCullingMethod.class,
        (config, value) -> config.topFaceCullingMethod = value,
        config -> config.topFaceCullingMethod,
        OptionImpact.LOW,
        OptionFlag.REQUIRES_ASSET_RELOAD
    );

    public static final OptionGroup GROUP = OptionGroup.createBuilder()
        .add(QUALITY)
        .add(ANIMATIONS)
        .add(OPAQUE_FACES)
        .add(TOP_FACE_CULLING_MODE)
        .build();

    private static Option<Boolean> ofBoolean(String path, BiConsumer<SodiumConfig, Boolean> setter, Function<SodiumConfig, Boolean> getter, OptionImpact impact, OptionFlag... optionFlags) {
        return of(path, Boolean.class, TickBoxControl::new, setter, getter, impact, optionFlags);
    }

    private static <T extends Enum<T>> Option<T> ofEnum(String path, Class<T> type, BiConsumer<SodiumConfig, T> setter, Function<SodiumConfig, T> getter, OptionImpact impact, OptionFlag... optionFlags) {
        return of(path, type, option -> new CyclingControl<>(option, type), setter, getter, impact, optionFlags);
    }

    private static <T> OptionImpl<SodiumConfig, T> of(String path, Class<T> type, Function<OptionImpl<SodiumConfig, T>, Control<T>> controlFunction, BiConsumer<SodiumConfig, T> setter, Function<SodiumConfig, T> getter, OptionImpact impact, OptionFlag... optionFlags) {
        return OptionImpl.createBuilder(type, SodiumConfig.STORAGE)
            .setName(getOptionText(path, false))
            .setTooltip(getOptionText(path, true))
            .setControl(controlFunction)
            .setBinding(setter, getter)
            .setImpact(impact)
            .setFlags(optionFlags)
            .build();
    }

    static Text getOptionText(String path) {
        return Text.translatable(BlockyBubbles.MOD_ID + ".options." + path);
    }

    static Text getOptionText(String path, boolean tooltip) {
        return getOptionText(path + "." + (tooltip ? "tooltip" : "name"));
    }

}
