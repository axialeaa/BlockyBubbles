package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemBlockRenderTypes.class)
public class ItemBlockRenderTypesMixin {

    @WrapMethod(method = "getChunkRenderType")
    private static ChunkSectionLayer getBubbleColumnRenderType(BlockState blockState, Operation<ChunkSectionLayer> original) {
        return getLayer(blockState, original.call(blockState));
    }

    @ModifyExpressionValue(method = "getMovingBlockRenderType", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object getMovingBubbleColumnRenderType(Object original, BlockState blockState) {
        return getLayer(blockState, (ChunkSectionLayer) original);
    }

    @Unique
    private static ChunkSectionLayer getLayer(BlockState blockState, ChunkSectionLayer original) {
        BlockyBubblesConfig config = BlockyBubbles.getConfig();

        if (blockState.is(Blocks.BUBBLE_COLUMN) && !config.isFancy())
            return config.hasOpaqueFaces() ? ChunkSectionLayer.SOLID : ChunkSectionLayer.CUTOUT;

        return original;
    }

}
