package com.axialeaa.blockybubbles;

import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;

public class BlockyBubblesMixinConfig implements IMixinConfigPlugin {

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return isCompatibleMixin(mixinClassName, "frozenlib");
	}

	private static boolean isCompatibleMixin(String mixinClassName, String modId) {
		return !mixinClassName.contains(modId) || FabricLoader.getInstance().isModLoaded(modId);
	}

}