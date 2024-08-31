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

        NON_AIR       ((world, pos, state, direction) -> !state.isAir()), // Vanilla Bedrock behaviour
        BUBBLE_COLUMN ((world, pos, state, direction) -> state.getBlock() instanceof BubbleColumnBlock), // Java-esque behaviour
        SOLID_SIDE    ((world, pos, state, direction) -> state.isSideSolidFullSquare(world, pos, direction)),
        NONE          ((world, pos, state, direction) -> false);

        private final Function4<ClientWorld, BlockPos, BlockState, Direction, Boolean> function;

        CullingAwareness(Function4<ClientWorld, BlockPos, BlockState, Direction, Boolean> function) {
            this.function = function;
        }

        public Text getLocalizedName() {
            return BlockyBubbles.getOptionText("culling_awareness.enum." + this.toString().toLowerCase());
        }

        public boolean shouldCull(ClientWorld world, BlockPos pos, BlockState state, Direction direction) {
            return this.function.apply(world, pos, state, direction);
        }

    }

}