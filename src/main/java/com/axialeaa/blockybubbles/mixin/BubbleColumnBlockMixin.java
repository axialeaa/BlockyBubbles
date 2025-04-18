package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.util.RenderingUtils;
import com.axialeaa.blockybubbles.mixin.impl.AbstractBlockImplMixin;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin extends AbstractBlockImplMixin {

	@WrapWithCondition(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addImportantParticle" /*? if >=1.21.5 >>*/ +"Client" + "(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	private boolean shouldSpawnParticles(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		return RenderingUtils.isFancy();
	}

	@ModifyReturnValue(method = "getRenderType", at = @At("RETURN"))
	private BlockRenderType modifyRenderType(BlockRenderType original, BlockState state) {
		return RenderingUtils.isFancy() ? original : BlockRenderType.MODEL;
	}

	@Override
	public boolean isSideInvisibleImpl(BlockState state, BlockState stateFrom, Direction direction, Operation<Boolean> original) {
		if (super.isSideInvisibleImpl(state, stateFrom, direction, original))
			return true;

		return !RenderingUtils.isFancy() && direction == Direction.UP && RenderingUtils.shouldCullTopFace(stateFrom);
	}

}