package com.axialeaa.blockybubbles.compat.frozenlib;

import net.fabricmc.fabric.api.client.model.loading.v1.CompositeBlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public record CompositeUnbakedRootBlockStateModel(List<BlockStateModel.UnbakedRoot> roots) implements BlockStateModel.UnbakedRoot {

	@Override
	public BlockStateModel bake(BlockState blockState, ModelBaker modelBakery) {
		List<BlockStateModel> models = new ArrayList<>(this.roots.size());

		for (BlockStateModel.UnbakedRoot root : this.roots)
			models.add(root.bake(blockState, modelBakery));

		return CompositeBlockStateModel.of(models);
	}

	@Override
	public Object visualEqualityGroup(BlockState blockState) {
		List<Object> list = new ArrayList<>();

		for (BlockStateModel.UnbakedRoot root : this.roots)
			list.add(root.visualEqualityGroup(blockState));

		return list;
	}

	@Override
	public void resolveDependencies(Resolver resolver) {
		for (BlockStateModel.UnbakedRoot root : this.roots)
			root.resolveDependencies(resolver);
	}

}