package com.axialeaa.blockybubbles.mixin.sodium;

import com.axialeaa.blockybubbles.config.Options;
import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = SodiumOptionsGUI.class, remap = false)
public class SodiumOptionsGUIMixin {

    @Shadow @Final private List<OptionPage> pages;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/gui/SodiumGameOptionPages;advanced()Lnet/caffeinemc/mods/sodium/client/gui/options/OptionPage;"))
    private void addOptionPage(Screen prevScreen, CallbackInfo ci) {
        this.pages.add(Options.PAGE);
    }

}
