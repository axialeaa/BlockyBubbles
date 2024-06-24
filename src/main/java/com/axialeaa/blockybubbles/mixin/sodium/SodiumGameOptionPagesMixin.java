package com.axialeaa.blockybubbles.mixin.sodium;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.sodium.SodiumUtils;
import com.axialeaa.blockybubbles.sodium.BlockyBubblesConfig;
import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SodiumGameOptionPages.class)
public class SodiumGameOptionPagesMixin {

    /**
     * Adds the "Bubble Columns" option to Sodium's video settings screen.
     */
    @Inject(method = "quality", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup;createBuilder()Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 2, shift = At.Shift.AFTER), remap = false)
    private static void addOption(CallbackInfoReturnable<OptionPage> cir, @Local List<OptionGroup> groups) {
        groups.add(OptionGroup.createBuilder()
            .add(OptionImpl.createBuilder(SodiumGameOptions.GraphicsQuality.class, BlockyBubblesConfig.STORAGE)
                .setName(BlockyBubbles.getOptionText("quality", false))
                .setTooltip(BlockyBubbles.getOptionText("quality", true))

                .setControl(option -> new CyclingControl<>(option, SodiumGameOptions.GraphicsQuality.class))
                .setBinding((opts, value) -> opts.bubblesQuality = value, opts -> opts.bubblesQuality)
                .setImpact(OptionImpact.MEDIUM)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build()
            )
            .add(OptionImpl.createBuilder(Boolean.class, BlockyBubblesConfig.STORAGE)
                .setName(BlockyBubbles.getOptionText("enable_animations", false))
                .setTooltip(BlockyBubbles.getOptionText("enable_animations", true))

                .setControl(TickBoxControl::new)
                .setBinding((opts, value) -> opts.enableAnimations = value, opts -> opts.enableAnimations)
                .setImpact(OptionImpact.MEDIUM)
                .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                .build()
            )
            .add(OptionImpl.createBuilder(SodiumUtils.CullingAwareness.class, BlockyBubblesConfig.STORAGE)
                .setName(BlockyBubbles.getOptionText("culling_awareness", false))
                .setTooltip(BlockyBubbles.getOptionText("culling_awareness", true))

                .setControl(option -> new CyclingControl<>(option, SodiumUtils.CullingAwareness.class))
                .setBinding((opts, value) -> opts.cullingAwareness = value, opts -> opts.cullingAwareness)
                .setImpact(OptionImpact.LOW)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build()
            )
            .build()
        );
    }

}
