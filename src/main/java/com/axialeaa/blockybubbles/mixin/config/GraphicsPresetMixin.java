package com.axialeaa.blockybubbles.mixin.config;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.axialeaa.blockybubbles.config.Quality;
import com.axialeaa.blockybubbles.config.duck.QualityButtonHolder;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.GraphicsPreset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GraphicsPreset.class)
public class GraphicsPresetMixin {

    @Shadow @Final public static GraphicsPreset FAST;
    @Shadow @Final public static GraphicsPreset CUSTOM;

    @ModifyExpressionValue(method = "apply", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/GraphicsPreset;ordinal()I"))
    private int shareOrdinal(int original, @Share("ordinal") LocalIntRef ref) {
        ref.set(original);
        return original;
    }

    @Inject(method = "apply", at = @At("RETURN"))
    private void applyPresetToBlockyBubblesOptions(Minecraft minecraft, CallbackInfo ci, @Local OptionsSubScreen optionsSubScreen, @Share("ordinal") LocalIntRef ref) {
        int ordinal = ref.get();

        if (ordinal != CUSTOM.ordinal())
            applyQualityValue(BlockyBubbles.getConfig(), optionsSubScreen, ordinal == FAST.ordinal() ? Quality.FAST : Quality.FANCY);
    }

    @Unique
    private static void applyQualityValue(BlockyBubblesConfig config, OptionsSubScreen optionsSubScreen, Quality quality) {
        if (config.getQuality() == quality)
            return;

        config.setQuality(quality);
        config.writeToFile();

        OptionsAccessor.invokeOperateOnLevelRenderer(LevelRenderer::allChanged);
        forceButtonValue(optionsSubScreen, quality);
    }

    @Unique
    private static void forceButtonValue(OptionsSubScreen optionsSubScreen, Quality quality) {
        if (!(optionsSubScreen instanceof QualityButtonHolder holder))
            return;

        CycleButton<Quality> button = holder.blocky_bubbles$get();

        if (button != null)
            button.setValue(quality);
    }

}
