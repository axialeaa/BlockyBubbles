package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.axialeaa.blockybubbles.mixin.impl.BlockBehaviourImplMixin;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin extends BlockBehaviourImplMixin {

	@WrapWithCondition(method = "animateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addAlwaysVisibleParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
	private boolean shouldAddParticles(Level instance, ParticleOptions parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		return BlockyBubbles.getConfig().isFancy();
	}

	@ModifyReturnValue(method = "getRenderShape", at = @At("RETURN"))
	private RenderShape modifyRenderShape(RenderShape original, BlockState state) {
		return BlockyBubbles.getConfig().isFancy() ? original : RenderShape.MODEL;
	}

	@Override
	public boolean skipRenderingImpl(BlockState state, BlockState stateFrom, Direction direction, Operation<Boolean> original) {
		if (super.skipRenderingImpl(state, stateFrom, direction, original))
			return true;

		BlockyBubblesConfig config = BlockyBubbles.getConfig();

		if (config.isFancy())
			return false;

		ClientLevel level = Minecraft.getInstance().level;

		return level != null && config.getCullfaceMethod().test(stateFrom, level, BlockPos.ZERO, direction);
	}

}