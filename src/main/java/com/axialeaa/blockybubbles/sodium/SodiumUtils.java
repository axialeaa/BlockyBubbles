package com.axialeaa.blockybubbles.sodium;

import com.axialeaa.blockybubbles.BlockyBubbles;
import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.text.Text;

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
        return SodiumConfig.getOptionData().bubblesQuality.isFancy(graphicsMode);
    }

    /**
     * Defines culling behaviour and provides an enumerator for the config to use.
     */
    public enum CullingAwareness implements TextProvider {

        NON_AIR,
        BUBBLE_COLUMN,
        NONE;

        public Text getLocalizedName() {
            return BlockyBubbles.getOptionText("culling_awareness.enum." + this.toString().toLowerCase());
        }

        public boolean shouldCull(BlockState state) {
            return switch (this) {
                case NON_AIR -> !state.isAir(); // Vanilla Bedrock behaviour
                case BUBBLE_COLUMN -> state.getBlock() instanceof BubbleColumnBlock; // Java-esque behaviour
                case NONE -> false;
            };
        }

    }

}