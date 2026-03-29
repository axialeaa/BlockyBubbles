package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {

    @ModifyReturnValue(method = "forceOpaque", at = @At("RETURN"))
    private static boolean setOpaqueBubbleColumns(boolean original, @Local(argsOnly = true) BlockState blockState) {
        if (original)
            return true;

        BlockyBubblesConfig config = BlockyBubbles.getConfig();

        return blockState.is(Blocks.BUBBLE_COLUMN) && !config.isFancy() && config.hasOpaqueFaces();
    }

}
