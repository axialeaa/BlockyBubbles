package com.axialeaa.blockybubbles.tint;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.block.state.BlockState;

public class BubbleColumnTintSource implements BlockTintSource {

	@Override
	public int color(BlockState state) {
		return -1;
	}

	@Override
	public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
		return ARGB.opaque(BiomeColors.getAverageWaterColor(level, pos));
	}

}
