package com.axialeaa.blockybubbles;

import net.fabricmc.loader.api.FabricLoader;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class BlockyBubblesMixinConfig implements IMixinConfigPlugin {

	@Override
	public void onLoad(String mixinPackage) {}

	@Override
	public @Nullable String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return isCompatibleMixin(mixinClassName, "frozenlib");
	}

	private static boolean isCompatibleMixin(String mixinClassName, String modId) {
		return !mixinClassName.contains(modId) || FabricLoader.getInstance().isModLoaded(modId);
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

	@Override
	public @Nullable List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

}