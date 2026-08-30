package com.axialeaa.blockybubbles.config.resource_condition;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.resources.RegistryOps;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public abstract class ConfigOptionResourceCondition<T> implements ResourceCondition {

	private final T valueToMatch;

	public ConfigOptionResourceCondition(T valueToMatch) {
		this.valueToMatch = valueToMatch;
	}

	public abstract Function<BlockyBubblesConfig, T> option();

	@Override
	public boolean test(RegistryOps.@Nullable RegistryInfoLookup registryInfo) {
		return this.option().apply(BlockyBubbles.getConfig()).equals(this.valueToMatch);
	}

	public T getValueToMatch() {
		return this.valueToMatch;
	}

	public static <T, C extends ConfigOptionResourceCondition<T>> MapCodec<C> makeCodec(Codec<T> valueCodec, Function<T, C> to) {
		return valueCodec.xmap(to, ConfigOptionResourceCondition::getValueToMatch).fieldOf("value");
	}

}
