package com.axialeaa.blockybubbles.sodium;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.mojang.datafixers.util.Function4;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import java.util.Locale;

import /*$ sodium_package >>*/ net.caffeinemc .mods.sodium.client.gui.options.TextProvider;

/**
 * aaaaaaaaaaaaaaaaa
 * @author axia
 */
public class SodiumUtils {

    static {
        if (!BlockyBubbles.isSodiumLoaded)
            throw new RuntimeException("SodiumUtils class loaded without sodium on the client!");
    }

    public static boolean isFancy(GraphicsMode graphicsMode) {
        return BlockyBubblesConfig.getOptionData().bubblesQuality.isFancy(graphicsMode);
    }

    /**
     * Defines culling behaviour and provides an enumerator for the config to use.
     */
    public enum CullingAwareness implements TextProvider {

        /**
         * Culls the bubble column face when the adjacent block is anything other than air, including water, glass and other bubble columns. This matches the behaviour of vanilla Bedrock Edition.
         */
        NON_AIR         ((world, pos, state, direction) -> !state.isAir()),
        /**
         * Culls the bubble column face when the adjacent block is a bubble column or a full, solid, square side of an opaque block. This resembles how Java handles face culling on most other blocks.
         */
        BUBBLE_COLUMN   ((world, pos, state, direction) -> state.getBlock() instanceof BubbleColumnBlock || (state.isSideSolidFullSquare(world, pos, direction.getOpposite()) && state.isOpaque())),
        /**
         * Never culls the bubble column face under any circumstance.
         */
        NONE            ((world, pos, state, direction) -> false);

        private final Function4<ClientWorld, BlockPos, BlockState, Direction, Boolean> function;

        CullingAwareness(Function4<ClientWorld, BlockPos, BlockState, Direction, Boolean> function) {
            this.function = function;
        }

        @Override
        public Text getLocalizedName() {
            return BlockyBubbles.getOptionText("culling_awareness.enum." + this.name().toLowerCase(Locale.ROOT));
        }

        public boolean shouldCull(ClientWorld world, BlockPos pos, BlockState state, Direction direction) {
            return this.function.apply(world, pos, state, direction);
        }

    }

}