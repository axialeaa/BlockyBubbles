package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.sodium.BlockyBubblesConfig;
import com.axialeaa.blockybubbles.sodium.SodiumUtils;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin extends AbstractBlockImplMixin {

	/**
	 * @return true if the "Bubble Columns" setting is fancy or higher when sodium is installed, otherwise true if the global graphics setting is fancy or higher.
	 * @implNote {@link SodiumUtils} only gets loaded when the condition succeeds, so no errors are thrown when sodium is not installed.
	 */
	@Unique
	private static boolean isFancy() {
		if (!BlockyBubbles.isSodiumLoaded)
			return MinecraftClient.isFancyGraphicsOrBetter();

		MinecraftClient client = MinecraftClient.getInstance();
		GraphicsMode graphicsMode = client.options /*$ graphics_mode >>*/ .getGraphicsMode().getValue() ;

		return SodiumUtils.isFancy(graphicsMode);
	}

	/**
	 * @param state The block next to the bubble column.
	 * @return true if the "Culling" setting allows culling when sodium is installed, otherwise true if the block next to the bubble column in question is not air.
	 * @implNote {@link SodiumUtils} only gets loaded when the condition succeeds, so no errors are thrown when sodium is not installed.
	 * @see SodiumUtils.CullingAwareness
	 */
	@Unique
	private static boolean shouldCull(BlockState state, Direction direction) {
		if (!BlockyBubbles.isSodiumLoaded)
			return !state.isAir();

		MinecraftClient client = MinecraftClient.getInstance();
		BlockPos origin = BlockPos.ORIGIN;

		return BlockyBubblesConfig.getOptionData().cullingAwareness.shouldCull(client.world, origin, state, direction);
	}

	/**
	 * Ensures the "Bubble Columns" setting is fancy or higher before creating a bubble particle.
	 */
	@WrapWithCondition(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addImportantParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	private boolean shouldSpawnParticles(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		return isFancy();
	}

	/**
	 * @return the default block render type (invisible) when the "Bubble Columns" setting is fancy or higher, otherwise the model render type. This allows its model to be configurable through .json files, like any other block.
	 */
	@ModifyReturnValue(method = "getRenderType", at = @At("RETURN"))
	private BlockRenderType modifyRenderType(BlockRenderType original, BlockState state) {
		return isFancy() ? original : BlockRenderType.MODEL;
	}

	/**
	 * Culls the faces of the bubble column model according to the type of block next to it (some culling specification is provided in the block model).
	 * @see SodiumUtils.CullingAwareness
	 */
	@Override
	public void isSideInvisibleImpl(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(!isFancy() && shouldCull(stateFrom, direction));
	}

}