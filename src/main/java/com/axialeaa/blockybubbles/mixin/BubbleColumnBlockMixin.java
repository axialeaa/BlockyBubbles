package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesGameOptions;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin extends AbstractBlockMixin {

	/**
	 * Makes sure the "Bubble Columns" setting is fancy or higher before creating a bubble particle.
	 */
	@WrapWithCondition(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addImportantParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	private boolean particlesWhenFancy(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		return settingIsFancy();
	}

	/**
	 * @return the default block render type (invisible) when the "Bubble Columns" setting is fancy or higher, otherwise the model render type. This allows its model to be configurable through .json files, like any other block.
	 */
	@ModifyReturnValue(method = "getRenderType", at = @At("RETURN"))
	private BlockRenderType modifyRenderType(BlockRenderType original, BlockState state) {
		return settingIsFancy() ? original : BlockRenderType.MODEL;
	}

	/**
	 * Culls the faces of the bubble column model according to the type of block next to them.
	 */
	@Override
	public void implIsSideInvisible(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		boolean shouldCull = stateFrom.getBlock() instanceof BubbleColumnBlock || stateFrom.isSideInvisible(stateFrom, direction.getOpposite());
		if (!settingIsFancy() && shouldCull)
			cir.setReturnValue(true);
	}

	/**
	 * @return whether the "Bubble Columns" setting (or the vanilla graphics setting if sodium is not loaded) is fancy or higher.
	 */
	@Unique
	public boolean settingIsFancy() {
		MinecraftClient client = MinecraftClient.getInstance();
		GraphicsMode graphicsMode = client.options.getGraphicsMode().getValue();
		BlockyBubblesGameOptions optionData = BlockyBubbles.blockyBubblesOpts.getData();

		return optionData.bubblesQuality.isFancy(graphicsMode);
	}

}