package com.axialeaa.blockybubbles.compat.frozenlib;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import net.fabricmc.fabric.api.client.model.loading.v1.wrapper.WrapperBlockStateModel;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.frozenblock.lib.block.api.waterlike.WaterLikeBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class InternalBubbleColumnBlockStateModel extends WrapperBlockStateModel {

    public InternalBubbleColumnBlockStateModel(BlockStateModel model) {
        super(model);
    }

    @Override
    public void emitQuads(QuadEmitter emitter, BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, Predicate<@Nullable Direction> cullTest) {
		BlockState bubbleColumn = getBubbleColumnFor(state);
	    List<BlockTintSource> tints = Minecraft.getInstance().getBlockColors().getTintSources(bubbleColumn);

		emitter.pushTransform(quad -> {
			BlockyBubblesConfig config = BlockyBubbles.getConfig();

			if (config.hasOpaqueFaces())
				quad.chunkLayer(ChunkSectionLayer.SOLID);

			return tints.isEmpty() || !config.hasBiomeColors() || tintQuad(level, pos, state, quad, tints);
		});

		try {
			this.emitCulledQuads(emitter, level, pos, state, random);
		}
		finally {
			emitter.popTransform();
		}
    }

	public static BlockState getBubbleColumnFor(BlockState waterLike) {
		return Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, WaterLikeBlock.isDraggingDownAsBubbleColumn(waterLike));
	}

	private static boolean tintQuad(BlockAndTintGetter level, BlockPos pos, BlockState state, MutableQuadView quad, List<BlockTintSource> tints) {
		int index = quad.tintIndex();

		if (index >= 0 && index < tints.size())
			quad.multiplyColor(tints.get(index).colorInWorld(state, level, pos));

		return true;
	}

	private void emitCulledQuads(QuadEmitter emitter, BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random) {
		super.emitQuads(emitter, level, pos, state, random, direction -> {
			if (direction == null)
				return false;

			BlockState stateFrom = level.getBlockState(pos.relative(direction));

			return BlockyBubbles.getConfig().getCullfaceMethod().test(stateFrom, level, BlockPos.ZERO, direction);
		});
	}

    @Override
    @Nullable
    public Object createGeometryKey(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random) {
        Object subkey = wrapped.createGeometryKey(level, pos, state, random);
		return subkey == null ? null : new WrappedKey(subkey);
	}

    public record UnbakedRoot(BlockStateModel.UnbakedRoot unbaked) implements BlockStateModel.UnbakedRoot {

        @Override
        public BlockStateModel bake(BlockState blockState, ModelBaker modelBakery) {
            return new InternalBubbleColumnBlockStateModel(unbaked.bake(blockState, modelBakery));
        }

        @Override
        public Object visualEqualityGroup(BlockState blockState) {
			return new WrappedKey(unbaked.visualEqualityGroup(blockState));
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            unbaked.resolveDependencies(resolver);
        }

    }

	/// A new record will not be equal to the subkey, but two of those new records with the same subkey will equal eachother
	private record WrappedKey(Object subkey) {}

}