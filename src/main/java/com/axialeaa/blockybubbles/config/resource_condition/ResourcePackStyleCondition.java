package com.axialeaa.blockybubbles.config.resource_condition;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import com.axialeaa.blockybubbles.config.ResourcePackStyle;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;

import java.util.function.Function;

public class ResourcePackStyleCondition extends ConfigOptionResourceCondition<ResourcePackStyle> {

	public static final MapCodec<ResourcePackStyleCondition> CODEC = makeCodec(ResourcePackStyle.CODEC, ResourcePackStyleCondition::new);
	public static final ResourceConditionType<ResourcePackStyleCondition> TYPE = ResourceConditionType.create(BlockyBubbles.id("resource_pack_style"), CODEC);

	public ResourcePackStyleCondition(ResourcePackStyle valueToMatch) {
		super(valueToMatch);
	}

	@Override
	public ResourceConditionType<?> getType() {
		return TYPE;
	}

	@Override
	public Function<BlockyBubblesConfig, ResourcePackStyle> option() {
		return BlockyBubblesConfig::getResourcePackStyle;
	}

}
