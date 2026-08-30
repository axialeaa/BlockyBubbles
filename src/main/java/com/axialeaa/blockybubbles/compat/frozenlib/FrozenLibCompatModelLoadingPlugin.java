package com.axialeaa.blockybubbles.compat.frozenlib;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.BlockyBubblesConfig;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.frozenblock.lib.block.api.waterlike.WaterLikeBlock;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class FrozenLibCompatModelLoadingPlugin implements ModelLoadingPlugin {

	private static final Identifier DOWN = BlockyBubbles.id("block/bubble_column_down");
	private static final Identifier UP = BlockyBubbles.id("block/bubble_column_up");

	@Override
	public void initialize(Context pluginContext) {
		pluginContext.modifyBlockModelOnLoad().register((model, blockContext) -> {
			BlockState blockState = blockContext.state();

			if (renderWaterLikeBubbleColumn(blockState)) {
				Identifier id = getModelIdFor(blockState);

				BlockStateModel.UnbakedRoot root = new SingleVariant.Unbaked(new Variant(id)).asRoot();
				BlockStateModel.UnbakedRoot bubbleColumnModel = new InternalBubbleColumnBlockStateModel.UnbakedRoot(root);

				return new CompositeUnbakedRootBlockStateModel(List.of(model, bubbleColumnModel));
			}

			return model;
		});
	}

	private static Identifier getModelIdFor(BlockState waterLike) {
		return WaterLikeBlock.isDraggingDownAsBubbleColumn(waterLike) ? DOWN : UP;
	}

	public static boolean renderWaterLikeBubbleColumn(BlockState state) {
		BlockyBubblesConfig config = BlockyBubbles.getConfig();
		return !config.isFancy() && config.hasFrozenLibCompat() && WaterLikeBlock.hasBubbleColumn(state);
	}

}
