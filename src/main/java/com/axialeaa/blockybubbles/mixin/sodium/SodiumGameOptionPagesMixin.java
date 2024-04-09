package com.axialeaa.blockybubbles.mixin.sodium;

import com.axialeaa.blockybubbles.sodium.SodiumCompat;
import com.axialeaa.blockybubbles.sodium.SodiumConfig;
import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import net.minecraft.text.Text;
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
    private static void addOption(CallbackInfoReturnable<OptionPage> cir, @Local(ordinal = 0) List<OptionGroup> groups) {
        groups.add(OptionGroup.createBuilder()
            .add(OptionImpl.createBuilder(SodiumGameOptions.GraphicsQuality.class, SodiumConfig.blockyBubblesOptions)
                .setName(Text.translatable("blocky-bubbles.options.bubblesQuality.name"))
                .setTooltip(Text.translatable("blocky-bubbles.options.bubblesQuality.tooltip"))
                .setControl(option -> new CyclingControl<>(option, SodiumGameOptions.GraphicsQuality.class))
                .setBinding((opts, value) -> opts.bubblesQuality = value, opts -> opts.bubblesQuality)
                .setImpact(OptionImpact.MEDIUM)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build()
            )
            .add(OptionImpl.createBuilder(SodiumCompat.CullingAwareness.class, SodiumConfig.blockyBubblesOptions)
                .setName(Text.translatable("blocky-bubbles.options.cullingAwareness.name"))
                .setTooltip(Text.translatable("blocky-bubbles.options.cullingAwareness.tooltip"))
                .setControl(option -> new CyclingControl<>(option, SodiumCompat.CullingAwareness.class))
                .setBinding((opts, value) -> opts.cullingAwareness = value, opts -> opts.cullingAwareness)
                .setImpact(OptionImpact.LOW)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build()
            ).build()
        );
    }

}
