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
@Mixin(
    /*? if >=1.20.6 { */
    BlockDustParticle.class
    /*? } else { *//*
    BlockDustParticle.Factory.class
    *//*? } */
)
public class BlockDustParticleMixin {

    @ModifyExpressionValue(method =
        /*? if >=1.20.6 { */
        "create",
        /*? } else { *//*
        "createParticle(Lnet/minecraft/particle/BlockStateParticleEffect;Lnet/minecraft/client/world/ClientWorld;DDDDDD)Lnet/minecraft/client/particle/Particle;",
        *//*? } */
        at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private /*? if >=1.20.6 { */ static /*? } */ boolean shouldBypassDustCreation(boolean original, @Local BlockState blockState) {
        return original || blockState.isOf(Blocks.BUBBLE_COLUMN);
    }

}
