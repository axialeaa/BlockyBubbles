package com.axialeaa.blockybubbles.mixin.impl;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Supports invoking the isSideInvisible() method without extending + overriding, a bad mixin-ing practice which leads to a lot of inter-mod incompatibility.
 */
@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourImplMixin {

    @WrapMethod(method = "skipRendering")
    public boolean skipRenderingImpl(BlockState state, BlockState stateFrom, Direction direction, Operation<Boolean> original) {
        return original.call(state, stateFrom, direction);
    }

}