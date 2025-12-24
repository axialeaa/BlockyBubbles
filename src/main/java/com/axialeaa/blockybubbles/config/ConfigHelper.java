package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.mixin.config.OptionsAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ConfigHelper {

    public static final Identifier QUALITY = BlockyBubbles.id("quality");
    public static final Identifier ANIMATIONS = BlockyBubbles.id("animations");
    public static final Identifier OPAQUE_FACES = BlockyBubbles.id("opaque_faces");
    public static final Identifier CULLFACE_METHOD = BlockyBubbles.id("cullface_method");

    public static final Component OPTION_PAGE_TEXT = optionText(BlockyBubbles.id("page.bubble_columns"));

    private static final int WIDTH = 150;
    private static final int HEIGHT = 20;

    public static CycleButton<Boolean> createCyclingBoolean(Identifier option, Consumer<Boolean> setter, Supplier<Boolean> getter, CycleButton.OnValueChange<Boolean> onValueChange) {
        return CycleButton.onOffBuilder(getter.get())
            .withTooltip(value -> Tooltip.create(optionTooltip(option)))
            .create(0, 0, WIDTH, HEIGHT, optionText(option), (button, value) -> {
                setter.accept(value);
                onValueChange.onValueChange(button, value);
                BlockyBubbles.getConfig().writeToFile();
            });
    }

    public static <E extends Enum<E> & StringRepresentable> CycleButton<@NonNull E> createCyclingEnum(Identifier option, E[] values, boolean pertainTooltipToValue, Consumer<E> setter, Supplier<E> getter, CycleButton.OnValueChange<@NonNull E> onValueChange) {
        return CycleButton.builder(enumOptionNameProvider(option)::apply, getter.get())
            .withValues(values)
            .withTooltip(value -> Tooltip.create(pertainTooltipToValue ? enumOptionTooltipProvider(option).apply(value) : optionTooltip(option)))
            .create(0, 0, WIDTH, HEIGHT, optionText(option), (button, value) -> {
                setter.accept(value);
                onValueChange.onValueChange(button, value);
                BlockyBubbles.getConfig().writeToFile();
            });
    }

    static Component optionTooltip(Identifier option) {
        return optionText(option, ".tooltip");
    }

    static Component optionText(Identifier option) {
        return optionText(option, "");
    }

    static Component optionText(Identifier option, String suffix) {
        return Component.translatable(option.withPrefix("options.").toLanguageKey() + suffix);
    }

    static <E extends Enum<?> & StringRepresentable> Function<E, Component> enumOptionNameProvider(Identifier option) {
        return value -> optionText(option, '.' + value.getSerializedName());
    }

    static <E extends Enum<?> & StringRepresentable> Function<E, Component> enumOptionTooltipProvider(Identifier option) {
        return value -> optionText(option, '.' + value.getSerializedName() + ".tooltip");
    }

    public static <T> CycleButton.OnValueChange<T> reloadRenderer() {
        return (button, value) -> OptionsAccessor.invokeOperateOnLevelRenderer(LevelRenderer::allChanged);
    }

    public static <T> CycleButton.OnValueChange<T> reloadAssets() {
        return (button, value) -> Minecraft.getInstance().reloadResourcePacks();
    }

}
