package com.axialeaa.blockybubbles.mixin.config;

import net.minecraft.client.Options;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Consumer;

@Mixin(Options.class)
public interface OptionsAccessor {

    @Invoker("setGraphicsPresetToCustom")
    void invokeSetGraphicsPresetToCustom();

    @Invoker("operateOnLevelRenderer")
    static void invokeOperateOnLevelRenderer(Consumer<LevelRenderer> consumer) {}

}
