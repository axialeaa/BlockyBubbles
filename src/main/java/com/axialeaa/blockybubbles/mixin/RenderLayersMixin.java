package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.util.RenderingUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayers.class)
public class RenderLayersMixin {

    @Inject(method = "getBlockLayer", at = @At("HEAD"), cancellable = true)
    private static void addBubbleColumnLayer(BlockState state, CallbackInfoReturnable<BlockRenderLayer> cir) {
        if (state.isOf(Blocks.BUBBLE_COLUMN))
            cir.setReturnValue(RenderingUtils.getBlockRenderLayer());
    }

}
