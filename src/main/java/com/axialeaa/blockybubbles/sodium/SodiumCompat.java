package com.axialeaa.blockybubbles.sodium;

import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.text.Text;
/*? if <=1.18.2 { *//*
import net.minecraft.text.TranslatableText;
*//*? } */

/**
 * aaaaaaaaaaaaaaaaa
 * @author axia
 */
public class SodiumCompat {

    public static boolean isFancy(GraphicsMode graphicsMode) {
        return SodiumConfig.getOptionData().bubblesQuality.isFancy(graphicsMode);
    }

    /**
     * Defines culling behaviour and provides an enumerator for the config to use.
     */
    public enum CullingAwareness implements TextProvider {

        NON_AIR,
        BUBBLE_COLUMN;

        public Text getLocalizedName() {
            /*? if >=1.19.2 { */
            return Text.translatable("blocky-bubbles.options.cullingAwareness.enum." + this.toString().toLowerCase());
            /*? } else { *//*
            return new TranslatableText("blocky-bubbles.options.cullingAwareness.enum." + this.toString().toLowerCase());
            *//*? } */
        }

        public static boolean shouldCull(BlockState stateFrom, CullingAwareness type) {
            return switch (type) {
                case NON_AIR -> !stateFrom.isAir(); // Vanilla Bedrock behaviour
                case BUBBLE_COLUMN -> stateFrom.getBlock() instanceof BubbleColumnBlock; // Java-esque behaviour
            };
        }

        public static boolean shouldCull(BlockState state) {
            return shouldCull(state, SodiumConfig.getOptionData().cullingAwareness);
        }

    }

}