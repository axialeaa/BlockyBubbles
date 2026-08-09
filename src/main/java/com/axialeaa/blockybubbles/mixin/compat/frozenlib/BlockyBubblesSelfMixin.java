package com.axialeaa.blockybubbles.mixin.compat.frozenlib;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.compat.frozenlib.FrozenLibCompatModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockyBubbles.class)
public class BlockyBubblesSelfMixin {

	@Inject(method = "onInitializeClient", at = @At("TAIL"))
	private void addFrozenLibModelLoadingPlugin(CallbackInfo ci) {
		ModelLoadingPlugin.register(new FrozenLibCompatModelLoadingPlugin());
	}

}
