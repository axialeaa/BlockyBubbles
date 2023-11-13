package com.axialeaa.blockybubbles.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RenderLayers.class)
public class RenderLayersMixin {

    @Shadow @Final private static Map<Block, RenderLayer> BLOCKS;

    /**
     * Defines the bubble column render layer, allowing it to show up when the "Bubble Columns" setting is set to fast.
     */
    @Inject(method = "<clinit>*", at = @At("RETURN"))
    private static void addBubbleColumn(CallbackInfo info) {
        BLOCKS.put(Blocks.BUBBLE_COLUMN, RenderLayer.getCutoutMipped());
    }

}
