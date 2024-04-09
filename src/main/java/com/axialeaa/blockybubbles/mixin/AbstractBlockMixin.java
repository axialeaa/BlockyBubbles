package com.axialeaa.blockybubbles.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Allows instantiation of the isSideInvisible() method without extending + overriding, a bad mixin-ing practice which leads to a lot of inter-mod incompatibility.
 */
@SuppressWarnings("CancellableInjectionUsage")
@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @Inject(method = "isSideInvisible", at = @At("HEAD"), cancellable = true)
    public void isSideInvisibleImpl(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {}

}
