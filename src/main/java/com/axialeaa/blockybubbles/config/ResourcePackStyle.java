package com.axialeaa.blockybubbles.config;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum ResourcePackStyle implements StringRepresentable {

	VANILLA("vanilla"),
	BARE_BONES("bare_bones"),
	FAITHFUL_32("faithful_32"),
	FAITHFUL_64("faithful_64");

	public static final Codec<ResourcePackStyle> CODEC = StringRepresentable.fromValues(ResourcePackStyle::values);

	private final String path;

	ResourcePackStyle(String path) {
		this.path = path;
	}

	@Override
	public String getSerializedName() {
		return this.path;
	}

}
