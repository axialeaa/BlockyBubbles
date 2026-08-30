package com.axialeaa.blockybubbles.config;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("unused")
public enum CullfaceMethod implements BlockBehaviour.StateArgumentPredicate<Direction>, StringRepresentable {

    NON_AIR("non_air") {

        @Override
        public boolean test(BlockState stateFrom, BlockGetter world, BlockPos pos, Direction directionFrom) {
            return !stateFrom.isAir();
        }

    },
    STANDARD("standard") {

        @Override
        public boolean test(BlockState stateFrom, BlockGetter world, BlockPos pos, Direction directionFrom) {
            return stateFrom.is(Blocks.BUBBLE_COLUMN) || stateFrom.isFaceSturdy(world, pos, directionFrom.getOpposite()) && stateFrom.canOcclude();
        }

    };

    private final String path;

    CullfaceMethod(String path) {
        this.path = path;
    }

    @Override
    public String getSerializedName() {
        return this.path;
    }

}
