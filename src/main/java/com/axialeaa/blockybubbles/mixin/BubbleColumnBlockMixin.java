package com.axialeaa.blockybubbles.mixin;

import com.axialeaa.blockybubbles.compat.SodiumCompat;
import com.axialeaa.blockybubbles.mixin.extensibility.AbstractBlockMixin;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.fabricmc.loader.api.FabricLoader;
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
public abstract class BubbleColumnBlockMixin extends AbstractBlockMixin {

	/**
	 * @return if the "Bubble Columns" setting is fancy or higher when sodium is installed, otherwise if the global graphics setting is fancy or higher.
	 * @implNote {@link SodiumCompat} only gets loaded when the if statement succeeds, so no errors are thrown when sodium is not installed.
	 */
	@Unique
	private static boolean settingIsFancy() {
		MinecraftClient client = MinecraftClient.getInstance();
		GraphicsMode graphicsMode = client.options.getGraphicsMode().getValue();

		if (FabricLoader.getInstance().isModLoaded("sodium"))
			return SodiumCompat.sodiumIsFancy(graphicsMode);
		return graphicsMode.getId() > GraphicsMode.FAST.getId();
	}

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
	 * @implNote Bedrock Edition culls the top face when there is any block above, regardless of collision box shape or visibility. This is also what is done here, though some culling specification is provided in the block model instead.
	 */
	@Override
	public void isSideInvisibleImpl(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if (!settingIsFancy() && !stateFrom.isAir())
			cir.setReturnValue(true);
	}

}