package com.axialeaa.blockybubbles.config.resource_condition;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;

import java.util.function.Function;

public class BiomeColorsResourceCondition extends ConfigOptionResourceCondition<Boolean> {

	public static final MapCodec<BiomeColorsResourceCondition> CODEC = makeCodec(Codec.BOOL, BiomeColorsResourceCondition::new);
	public static final ResourceConditionType<BiomeColorsResourceCondition> TYPE = ResourceConditionType.create(BlockyBubbles.id("biome_colors"), CODEC);

	public BiomeColorsResourceCondition(boolean valueToMatch) {
		super(valueToMatch);
	}

	@Override
	public ResourceConditionType<?> getType() {
		return TYPE;
	}

	@Override
	public Function<BlockyBubblesConfig, Boolean> option() {
		return BlockyBubblesConfig::hasBiomeColors;
	}

}
