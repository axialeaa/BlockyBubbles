package com.axialeaa.blockybubbles.config;

import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.axialeaa.blockybubbles.BlockyBubbles.*;

public final class ConfigHelper {

    public static final Identifier QUALITY = id("quality");
    public static final Identifier ANIMATIONS = id("animations");
    public static final Identifier OPAQUE_FACES = id("opaque_faces");
    public static final Identifier CULLFACE_METHOD = id("cullface_method");
	public static final Identifier BIOME_COLORS = id("biome_colors");
	public static final Identifier RESOURCE_PACK_STYLE = id("resource_pack_style");
	public static final Identifier FROZENLIB_COMPAT = id("frozenlib_compat");

	public static final boolean FROZENLIB_LOADED = LOADER.isModLoaded("frozenlib");

    public static final Component GENERAL_PAGE_TEXT = optionText(id("page.general"));
	public static final Component COMPATIBILITY_PAGE_TEXT = optionText(id("page.compatibility"));

    private static final int WIDTH = 150;
    private static final int HEIGHT = 20;

	private ConfigHelper() {}

	public static CycleButton<Boolean> createCyclingBoolean(Identifier option, Consumer<Boolean> setter, Supplier<Boolean> getter, CycleButton.OnValueChange<Boolean> onValueChange) {
        return CycleButton.onOffBuilder(getter.get())
            .withTooltip(_ -> Tooltip.create(optionTooltip(option)))
            .create(0, 0, WIDTH, HEIGHT, optionText(option), (button, value) -> {
                setter.accept(value);
                onValueChange.onValueChange(button, value);
                getConfig().writeToFile();
            });
    }

    public static <E extends Enum<E> & StringRepresentable> CycleButton<E> createCyclingEnum(Identifier option, E[] values, boolean pertainTooltipToValue, Consumer<E> setter, Supplier<E> getter, CycleButton.OnValueChange<E> onValueChange) {
        return CycleButton.builder(enumOptionNameProvider(option)::apply, getter.get())
            .withValues(values)
            .withTooltip(value -> Tooltip.create(pertainTooltipToValue ? enumOptionTooltipProvider(option).apply(value) : optionTooltip(option)))
            .create(0, 0, WIDTH, HEIGHT, optionText(option), (button, value) -> {
                setter.accept(value);
                onValueChange.onValueChange(button, value);
                getConfig().writeToFile();
            });
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

    public static <E extends Enum<?> & StringRepresentable> Function<E, Component> enumOptionNameProvider(Identifier option) {
        return value -> optionText(option, '.' + value.getSerializedName());
    }

    public static <E extends Enum<?> & StringRepresentable> Function<E, Component> enumOptionTooltipProvider(Identifier option) {
        return value -> optionText(option, '.' + value.getSerializedName() + ".tooltip");
    }

}
