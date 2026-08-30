package com.axialeaa.blockybubbles.mixin.compat.frozenlib;

import com.axialeaa.blockybubbles.compat.frozenlib.FrozenLibCompatModelLoadingPlugin;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.lib.block.api.waterlike.WaterLikeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WaterLikeBlock.class)
public interface WaterLikeBlockMixin {

	@ModifyExpressionValue(method = "animateTickForWaterLike", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"))
	private boolean shouldAddParticles(boolean original, @Local(argsOnly = true, name = "state") BlockState state) {
		return original && !FrozenLibCompatModelLoadingPlugin.renderWaterLikeBubbleColumn(state);
	}

}
