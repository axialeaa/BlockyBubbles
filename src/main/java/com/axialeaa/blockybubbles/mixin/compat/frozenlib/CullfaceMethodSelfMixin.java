package com.axialeaa.blockybubbles.mixin.compat.frozenlib;

import com.axialeaa.blockybubbles.compat.frozenlib.FrozenLibCompatModelLoadingPlugin;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/// {@link com.axialeaa.blockybubbles.config.CullfaceMethod#STANDARD}
@Mixin(targets = "com/axialeaa/blockybubbles/config/CullfaceMethod$2")
public class CullfaceMethodSelfMixin {

	@ModifyReturnValue(method = "test(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z", at = @At("RETURN"))
	private boolean modifyShouldCull(boolean original, @Local(argsOnly = true, name = "stateFrom") BlockState stateFrom) {
		return FrozenLibCompatModelLoadingPlugin.renderWaterLikeBubbleColumn(stateFrom) || original;
	}

}
