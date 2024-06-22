package com.axialeaa.blockybubbles.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.BlockDustParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * This mixin fixes a bug where sprinting on upward bubble columns created dust particles with weird textures.
 */
@Mixin(BlockDustParticle /*? if <=1.20.4 >>*/ /*.Factory*/ .class)
public class BlockDustParticleMixin {

    @ModifyExpressionValue(method = /*$ create_method >>*/ "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private /*? if >=1.20.6 >>*/ static boolean shouldBypassDustCreation(boolean original, @Local BlockState blockState) {
        return original || blockState.isOf(Blocks.BUBBLE_COLUMN);
    }

}
