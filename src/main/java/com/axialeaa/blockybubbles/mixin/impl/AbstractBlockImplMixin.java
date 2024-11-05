package com.axialeaa.blockybubbles.mixin.impl;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Supports invoking the isSideInvisible() method without extending + overriding, a bad mixin-ing practice which leads to a lot of inter-mod incompatibility.
 */
@Mixin(AbstractBlock.class)
public class AbstractBlockImplMixin {

    @WrapMethod(method = "isSideInvisible")
    public boolean isSideInvisibleImpl(BlockState state, BlockState stateFrom, Direction direction, Operation<Boolean> original) {
        return original.call(state, stateFrom, direction);
    }

}