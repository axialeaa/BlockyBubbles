package com.axialeaa.blockybubbles.util;

import com.axialeaa.blockybubbles.BlockyBubbles;
import com.axialeaa.blockybubbles.config.SodiumConfig;
import com.axialeaa.blockybubbles.config.TopFaceCullingMethod;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;

public class RenderingUtils {

    /**
     * Do not call this from the mixin plugin; it will throw a preloading error.
     */
    public static final boolean SODIUM_LOADED = BlockyBubbles.LOADER.isModLoaded("sodium");

    public static boolean isFancy() {
        if (!SODIUM_LOADED)
            return MinecraftClient.isFancyGraphicsOrBetter();

        MinecraftClient client = MinecraftClient.getInstance();
        GraphicsMode graphicsMode = client.options.getGraphicsMode().getValue();

        return SodiumConfig.getData().bubblesQuality.isFancy(graphicsMode);
    }

    public static boolean shouldAnimate() {
        return !SODIUM_LOADED || SodiumConfig.getData().animations;
    }

    /**
     * @param state The block above the bubble column.
     * @return true if the "Top Face Culling" setting allows culling when sodium is installed, otherwise true if the block above the bubble column is something other than air.
     * @see TopFaceCullingMethod
     */
    public static boolean shouldCullTopFace(BlockState state) {
        if (!SODIUM_LOADED)
            return !state.isAir();

        MinecraftClient client = MinecraftClient.getInstance();

        return SodiumConfig.getData().topFaceCullingMethod.test(state, client.world, BlockPos.ORIGIN);
    }

    /**
     * @return The render layer to use for the bubble column faces based on the "Opaque Faces" setting.
     */
    public static RenderLayer getRenderLayer() {
        return SODIUM_LOADED && SodiumConfig.getData().opaqueFaces ? RenderLayer.getSolid() : RenderLayer.getCutout();
    }

}
