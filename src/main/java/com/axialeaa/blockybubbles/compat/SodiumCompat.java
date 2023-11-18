package com.axialeaa.blockybubbles.compat;

import com.axialeaa.blockybubbles.config.BlockyBubblesOptionsStorage;
import net.minecraft.client.option.GraphicsMode;

/**
 * aaaaaaaaaaaaaaaaa
 * @author axia
 */
public class SodiumCompat {

    public static final BlockyBubblesOptionsStorage blockyBubblesOpts = new BlockyBubblesOptionsStorage();

    public static boolean sodiumIsFancy(GraphicsMode graphicsMode) {
        return blockyBubblesOpts.getData().bubblesQuality.isFancy(graphicsMode);
    }

}