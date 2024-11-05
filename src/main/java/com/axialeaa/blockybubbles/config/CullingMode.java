package com.axialeaa.blockybubbles.config;

import com.axialeaa.blockybubbles.util.BlockyBubblesUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Locale;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.TextProvider;

/**
 * Defines culling behaviour and provides an enumerator for the config to use.
 */
@SuppressWarnings("unused")
public enum CullingMode implements TextProvider {

    /**
     * Culls the bubble column face when the adjacent block is anything other than air, including water, glass and other bubble columns. This matches the behaviour of vanilla Bedrock Edition.
     */
    NON_AIR         ((world, pos, state, direction) -> BlockyBubblesUtils.DEFAULT_CULLING_PREDICATE.test(state)),
    /**
     * Culls the bubble column face when the adjacent block is a bubble column or a full, solid, square side of an opaque block. This resembles how Java handles face culling on most other blocks.
     */
    BUBBLE_COLUMN   ((world, pos, state, direction) -> state.getBlock() instanceof BubbleColumnBlock || (state.isSideSolidFullSquare(world, pos, direction.getOpposite()) && state.isOpaque())),
    /**
     * Never culls the bubble column face under any circumstance.
     */
    NONE            (CullingModePredicate.FALSE);

    public static final String NAME = "culling_mode";

    private final CullingModePredicate predicate;

    CullingMode(CullingModePredicate predicate) {
        this.predicate = predicate;
    }

    public boolean shouldCull(ClientWorld world, BlockPos pos, BlockState state, Direction direction) {
        return this.predicate.test(world, pos, state, direction);
    }

    @Override
    public Text getLocalizedName() {
        return BlockyBubblesConfig.getOptionText(NAME + ".enum." + this.name().toLowerCase(Locale.ROOT));
    }

    @FunctionalInterface
    public interface CullingModePredicate {

        CullingModePredicate FALSE = (world, pos, state, direction) -> false;

        boolean test(ClientWorld world, BlockPos pos, BlockState state, Direction direction);

    }

}
