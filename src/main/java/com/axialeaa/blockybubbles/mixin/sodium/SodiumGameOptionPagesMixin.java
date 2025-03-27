package com.axialeaa.blockybubbles.mixin.sodium;

import com.axialeaa.blockybubbles.config.SodiumOptions;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import /*$ sodium_package >>*/ me.jellysquid .mods.sodium.client.gui.SodiumGameOptionPages;
import /*$ sodium_package >>*/ me.jellysquid .mods.sodium.client.gui.options.*;

@Mixin(SodiumGameOptionPages.class)
public class SodiumGameOptionPagesMixin {

    @Inject(method = "quality", at = @At(value = "INVOKE", target = /*$ quality_target >>*/ "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup;createBuilder()Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;" , ordinal = 2, shift = At.Shift.AFTER), remap = false)
    private static void addOptionGroup(CallbackInfoReturnable<OptionPage> cir, @Local List<OptionGroup> groups) {
        groups.add(SodiumOptions.createGroup());
    }

}
