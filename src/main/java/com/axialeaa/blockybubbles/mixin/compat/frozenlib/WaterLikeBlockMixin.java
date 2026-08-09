package com.axialeaa.blockybubbles.mixin.compat.frozenlib;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.lib.block.api.waterlike.WaterLikeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WaterLikeBlock.class)
public interface WaterLikeBlockMixin {

	@ModifyExpressionValue(method = "animateTickForWaterLike", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"))
	private boolean shouldAddParticles(boolean original) {
		return original && BlockyBubbles.getConfig().isFancy();
	}

}
