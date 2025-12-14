package com.axialeaa.blockybubbles.config;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.lwjgl.system.NonnullDefault;

@SuppressWarnings("unused")
@NonnullDefault
public enum TopFaceCullingMethod implements BlockBehaviour.StatePredicate {

    NON_AIR("non_air") {

        @Override
        public boolean test(BlockState state, BlockGetter world, BlockPos pos) {
            return !state.isAir();
        }

    },
    JAVA_ISH("java_ish") {

        @Override
        public boolean test(BlockState state, BlockGetter world, BlockPos pos) {
            return state.is(Blocks.BUBBLE_COLUMN) || state.isFaceSturdy(world, pos, Direction.DOWN) && state.canOcclude();
        }

    },
    OFF("off") {

        @Override
        public boolean test(BlockState state, BlockGetter world, BlockPos pos) {
            return false;
        }

    };

    public final String path;

    TopFaceCullingMethod(String path) {
        this.path = path;
    }

}
