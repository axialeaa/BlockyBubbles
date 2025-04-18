package com.axialeaa.blockybubbles.config;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.Locale;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.TextProvider;

/**
 * Defines culling behaviour and provides an enumerator for the config to use.
 */
@SuppressWarnings("unused")
public enum TopFaceCullingMethod implements TextProvider, AbstractBlock.ContextPredicate {

    /**
     * Culls the bubble column face when the adjacent block is anything other than air, including water, glass and other bubble columns. This matches the behaviour of vanilla Bedrock Edition.
     */
    NON_AIR {

        @Override
        public boolean test(BlockState state, BlockView world, BlockPos pos) {
            return !state.isAir();
        }

    },
    /**
     * Culls the bubble column face when the block above is a bubble column or a full, solid, square side of an opaque block. This resembles how Java handles face culling on most other blocks.
     */
    JAVA_ISH {

        @Override
        public boolean test(BlockState state, BlockView world, BlockPos pos) {
            if (state.isOf(Blocks.BUBBLE_COLUMN))
                return true;

            return state.isSideSolidFullSquare(world, pos, Direction.DOWN) && state.isOpaque();
        }

    },
    /**
     * Never culls the bubble column face under any circumstance.
     */
    OFF {

        @Override
        public boolean test(BlockState state, BlockView world, BlockPos pos) {
            return false;
        }

    };

    public static final String PATH = "top_face_culling_method";

    @Override
    public Text getLocalizedName() {
        return SodiumOptions.getOptionText(PATH + ".option." + this.name().toLowerCase(Locale.ROOT));
    }

}
