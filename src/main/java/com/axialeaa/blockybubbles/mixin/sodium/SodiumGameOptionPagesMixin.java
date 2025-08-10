package com.axialeaa.blockybubbles.mixin.sodium;

import com.axialeaa.blockybubbles.config.SodiumOptions;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptionPages;
import net.caffeinemc.mods.sodium.client.gui.options.OptionGroup;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SodiumGameOptionPages.class)
public class SodiumGameOptionPagesMixin {

    @Inject(method = "quality", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/gui/options/OptionGroup;createBuilder()Lnet/caffeinemc/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 2, shift = At.Shift.AFTER), remap = false)
    private static void addOptionGroup(CallbackInfoReturnable<OptionPage> cir, @Local List<OptionGroup> groups) {
        groups.add(SodiumOptions.GROUP);
    }

}
