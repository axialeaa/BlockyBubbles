package com.axialeaa.blockybubbles.util;

import com.axialeaa.blockybubbles.config.BlockyBubblesOptionsStorage;
import net.minecraft.client.option.GraphicsMode;

/**
 * aaaaaaaaaaaaaaaaa
 * @author axia
 */
public class ClassToMakeSodiumBloodyWork {

    public static final BlockyBubblesOptionsStorage blockyBubblesOpts = new BlockyBubblesOptionsStorage();

    public static boolean sodiumIsFancy(GraphicsMode graphicsMode) {
        return blockyBubblesOpts.getData().bubblesQuality.isFancy(graphicsMode);
    }

}