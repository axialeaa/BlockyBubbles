package com.axialeaa.blockybubbles.tint;

import com.axialeaa.blockybubbles.BlockyBubbles;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.block.state.BlockState;

public class BubbleColumnTintSource implements BlockTintSource {

	public static final int DEFAULT_TINT = 0xFF77D9FF;

	@Override
	public int color(BlockState state) {
		return DEFAULT_TINT;
	}

	@Override
	public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
		return BlockyBubbles.getConfig().hasBiomeColors() ? ARGB.opaque(BiomeColors.getAverageWaterColor(level, pos)) : BlockTintSource.super.colorInWorld(state, level, pos);
	}

}
