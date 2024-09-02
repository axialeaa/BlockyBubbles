package com.axialeaa.blockybubbles.mixin.sodium;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.sodium.SodiumUtils;
import com.axialeaa.blockybubbles.sodium.BlockyBubblesConfig;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.SodiumGameOptionPages;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.SodiumGameOptions;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.*;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.control.CyclingControl;
import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.control.TickBoxControl;

import static com.axialeaa.blockybubbles.sodium.SodiumUtils.*;

@Mixin(SodiumGameOptionPages.class)
public class SodiumGameOptionPagesMixin {

    /**
     * Adds the "Bubble Columns" option to Sodium's video settings screen.
     */
    @Inject(method = "quality", at = @At(value = "INVOKE", target = "L" + /*$ sodium_package_target >>*/ "net/caffeinemc" + "/mods/sodium/client/gui/options/OptionGroup;createBuilder()L" + /*$ sodium_package_target >>*/ "net/caffeinemc" + "/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 2, shift = At.Shift.AFTER), remap = false)
    private static void addOption(CallbackInfoReturnable<OptionPage> cir, @Local List<OptionGroup> groups) {
        groups.add(OptionGroup.createBuilder()
            .add(OptionImpl.createBuilder(SodiumGameOptions.GraphicsQuality.class, BlockyBubblesConfig.STORAGE)
                .setName(BlockyBubbles.getOptionTextWithSuffix(quality, "name"))
                .setTooltip(BlockyBubbles.getOptionTextWithSuffix(quality, "tooltip"))

                .setControl(option -> new CyclingControl<>(option, SodiumGameOptions.GraphicsQuality.class))
                .setBinding((opts, value) -> opts.bubblesQuality = value, opts -> opts.bubblesQuality)
                .setImpact(OptionImpact.MEDIUM)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build()
            )
            .add(OptionImpl.createBuilder(Boolean.class, BlockyBubblesConfig.STORAGE)
                .setName(BlockyBubbles.getOptionTextWithSuffix(enable_animations, "name"))
                .setTooltip(BlockyBubbles.getOptionTextWithSuffix(enable_animations, "tooltip"))

                .setControl(TickBoxControl::new)
                .setBinding((opts, value) -> opts.enableAnimations = value, opts -> opts.enableAnimations)
                .setImpact(OptionImpact.MEDIUM)
                .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                .build()
            )
            .add(OptionImpl.createBuilder(SodiumUtils.CullingMode.class, BlockyBubblesConfig.STORAGE)
                .setName(BlockyBubbles.getOptionTextWithSuffix(culling_mode, "name"))
                .setTooltip(BlockyBubbles.getOptionTextWithSuffix(culling_mode, "tooltip"))

                .setControl(option -> new CyclingControl<>(option, SodiumUtils.CullingMode.class))
                .setBinding((opts, value) -> opts.cullingMode = value, opts -> opts.cullingMode)
                .setImpact(OptionImpact.LOW)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build()
            )
            .build()
        );
    }

}
