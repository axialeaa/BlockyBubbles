package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemBlockRenderTypes.class)
public class ItemBlockRenderTypesMixin {

    @WrapMethod(method = "getChunkRenderType")
    private static ChunkSectionLayer addBubbleColumnLayer(BlockState blockState, Operation<ChunkSectionLayer> original) {
        if (blockState.is(Blocks.BUBBLE_COLUMN) && !BlockyBubblesConfig.getStorage().isFancy())
            return BlockyBubblesConfig.getStorage().opaqueFaces ? ChunkSectionLayer.SOLID : ChunkSectionLayer.CUTOUT;

        return original.call(blockState);
    }

}
